
package me.gall.sgt.java.core;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import me.gall.sgp.sdk.entity.Server;
import me.gall.sgp.sdk.entity.User;
import me.gall.sgp.sdk.service.RouterService;
import me.gall.sgp.sdk.service.UserService;

import org.joor.Reflect;
import org.joor.ReflectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

public final class SGTContext {
    private String appId;
    private Server server;
    private User user;
    private boolean offlineMode;
    private boolean sandboxMode;
    private int requestTimeout = NEVER_TIMEOUT;
    private String appKey;
    private String appSecret;

    private static final int NEVER_TIMEOUT = 0;

    private static final String SANDBOX_GATEWAY = "http://test.sgtcloud.cn/gateway";
    private static final String PRODUCTION_GATEWAY = "http://gw.sgtcloud.cn/gateway";
    private static final String ENDPOINT_CONVENTION_FLAG = "Service";

    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }

    private final class JsonrpcHttpRequestCallable<T> implements Callable<T> {

        private String url;
        private Class<T> clazz;

        private JsonrpcHttpRequestCallable(String url, Class<T> clazz) {
            this.url = url;
            this.clazz = clazz;
        }

        @Override
        public T call() throws Exception {
            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(
                    url));
            if (requestTimeout > NEVER_TIMEOUT) {
                client.setConnectionTimeoutMillis(requestTimeout);
            }
            if (appKey != null && appSecret != null) {
                client.enableRpcSignature(appKey, appSecret);
            } else {
                logger.info("AppKey or AppSecret not setting, disable the request signature feature.");
            }
            return ProxyUtil.createClientProxy(getClass()
                    .getClassLoader(), clazz, client);
        }
    }

    public <T> T getService(Class<T> clazz, boolean connected) throws Throwable {
        if (clazz == null) {
            throw new NullPointerException("Clazz must not be null.");
        }
        if (connected) {
            String requestEndpoint = (clazz == RouterService.class || clazz == UserService.class) ? constructGatewayEndpoint(clazz)
                    : constructNodeEndpoint(clazz);
            logger.debug("Remote Call " + clazz.getName() + " at "
                    + requestEndpoint);
            JsonrpcHttpRequestCallable<T> jsonrpcHttpRequestCallable = new JsonrpcHttpRequestCallable<T>(requestEndpoint, clazz);

            try {
                if (requestTimeout <= NEVER_TIMEOUT) {
                    return jsonrpcHttpRequestCallable.call();
                } else {
                    logger.debug("Timeout count is " + requestTimeout + " ms.");
                    FutureTask<T> timeoutCountFutureTask = new FutureTask<T>(jsonrpcHttpRequestCallable);
                    new Thread(timeoutCountFutureTask).start();
                    return timeoutCountFutureTask.get(requestTimeout, TimeUnit.MILLISECONDS);
                }
            } catch (TimeoutException e) {
                e.printStackTrace();
                throw new NetworkNotAvailableException();
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        } else if (offlineMode) {
            logger.debug(
                    "Network is disable and OfflineMode is active. Try local service.");
            try {
                return (T) Reflect.on(clazz.getName() + "Impl").create();
            } catch (ReflectException e) {
                logger.error(
                        "Could not find " + clazz.getName() + "Impl.class. Unable to call local service implemention.");
                throw e;
            }

        }
        throw new NetworkNotAvailableException();
    }

    public String constructNodeEndpoint(Class clazz)
            throws IllegalAccessException {
        String className = clazz.getSimpleName();
        if (className.endsWith(ENDPOINT_CONVENTION_FLAG)) {
            className = className.substring(0,
                    className.length() - ENDPOINT_CONVENTION_FLAG.length())
                    .toLowerCase();
            if (server == null)
                throw new NullPointerException("Host is null.");
            if (appId == null)
                throw new NullPointerException("AppId is null.");
            return server.getAddress() + "/" + appId + "/" + className + ".do";
        } else {
            throw new IllegalAccessException(
                    "Not a valid node service endpoint class:" + clazz.getName());
        }
    }

    public String constructGatewayEndpoint(Class clazz) throws IllegalAccessException {
        String className = clazz.getSimpleName();
        if (clazz == RouterService.class) {
            className = "route";
        } else if (clazz == UserService.class) {
            className = "user";
        } else {
            throw new IllegalAccessException(
                    "Not a gateway valid service endpoint class:" + clazz.getName());
        }
        return (sandboxMode ? SANDBOX_GATEWAY : PRODUCTION_GATEWAY) + "/" + className;

    }

    public void setAppId(String appId) {
        if (appId == null) {
            throw new NullPointerException("AppId must not be null.");
        }
        this.appId = appId;
    }

    public boolean isSandboxMode() {
        return sandboxMode;
    }

    public void setSandboxMode(boolean sandboxMode) {
        this.sandboxMode = sandboxMode;
    }

    public boolean isOfflineMode() {
        return offlineMode;
    }

    public void setOfflineMode(boolean offlineMode) {
        this.offlineMode = offlineMode;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public String getAppId() {
        return appId;
    }

    public Server getServer() {
        return server;
    }

    public User getUser() {
        return user;
    }

    public void setCurrentServer(Server currentServer) throws Throwable {
        if (currentServer == null) {
            throw new Exception(
                    "Server is null. Call route at first to get a valid server.");
        }
        if (currentServer.getId() == null) {
            logger.info("It is a offline server.");
        }
        logger.debug("Set current server as " + currentServer.getName()
                + "[" + currentServer.getAddress() + "]");
        server = currentServer;
    }

    public void setCurrentUser(User currentUser) {
        if (currentUser.getUserid() == null) {
            logger.info("It is a offline user.");
        }
        user = currentUser;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String toString() {
        return "SGTContext:" + "[AppId=" + appId + "]"
                + "[offlineMode=" + offlineMode + "]" + "[sandbox="
                + sandboxMode + "]";
    }

    public static SGTContext constructSGTContext(String appId, String appKey, String appSecret,
            boolean sandbox, boolean offlineMode) {
        SGTContext sgtContext = new SGTContext();
        sgtContext.setAppId(appId);
        sgtContext.setSandboxMode(sandbox);
        sgtContext.setOfflineMode(offlineMode);
        sgtContext.setAppKey(appKey);
        sgtContext.setAppSecret(appSecret);
        sgtContext.setLogger(LoggerFactory.getLogger("SGT-[" + appId + "]"));
        return sgtContext;
    }
}
