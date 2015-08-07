
package me.gall.sgt.java.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.gall.sgp.sdk.entity.Server;
import me.gall.sgp.sdk.entity.User;
import me.gall.sgp.sdk.service.AchievementService;
import me.gall.sgp.sdk.service.AnnouncementService;
import me.gall.sgp.sdk.service.BlacklistService;
import me.gall.sgp.sdk.service.BossService;
import me.gall.sgp.sdk.service.CampaignService;
import me.gall.sgp.sdk.service.CheckinBoardService;
import me.gall.sgp.sdk.service.CheckinService;
import me.gall.sgp.sdk.service.DailyTaskService;
import me.gall.sgp.sdk.service.DelegateDidService;
import me.gall.sgp.sdk.service.FileStorageService;
import me.gall.sgp.sdk.service.FriendshipExtraService;
import me.gall.sgp.sdk.service.FriendshipService;
import me.gall.sgp.sdk.service.GachaBoxService;
import me.gall.sgp.sdk.service.GiftCodeService;
import me.gall.sgp.sdk.service.InvitationCodeService;
import me.gall.sgp.sdk.service.LeaderBoardService;
import me.gall.sgp.sdk.service.MailService;
import me.gall.sgp.sdk.service.NotificationService;
import me.gall.sgp.sdk.service.PlayerExtraService;
import me.gall.sgp.sdk.service.PrivateChannelService;
import me.gall.sgp.sdk.service.PublicChannelService;
import me.gall.sgp.sdk.service.RouterService;
import me.gall.sgp.sdk.service.SgpPlayerService;
import me.gall.sgp.sdk.service.StoreService;
import me.gall.sgp.sdk.service.StructuredDataService;
import me.gall.sgp.sdk.service.TicketService;
import me.gall.sgp.sdk.service.TimestampService;
import me.gall.sgp.sdk.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SGTManager implements SGTServiceInterface {

    private static Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    /**
     * 初始化方法，强制在线，请求不加密
     * 
     * @param appId 应用唯一标识，从后台获取
     * @param sandbox 测试模式
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午6:45:27
     */
    public static SGTManager getInstance(String appId, boolean sandbox) {
        return getInstance(appId, sandbox, false);
    }

    private static final Map<String, SGTManager> cachedManagers = new ConcurrentHashMap<String, SGTManager>();

    /**
     * 初始化方法
     * 
     * @param sgtContext
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午6:44:24
     */
    public static SGTManager getInstance(SGTContext sgtContext) {
        logger.debug(sgtContext.toString());
        SGTManager sgpManager;
        if (cachedManagers.containsKey(sgtContext.getAppId())) {
            logger.debug("Return a cached [" + sgtContext.getAppId()
                    + "] instance.");
            sgpManager = cachedManagers.get(sgtContext.getAppId());
        } else {
            logger.debug("Create a new [" + sgtContext.getAppId()
                    + "] instance.");
            sgpManager = new SGTManager();
            sgpManager.setSgtContext(sgtContext);
            sgpManager.init();
            cachedManagers.put(sgtContext.getAppId(), sgpManager);
        }
        return sgpManager;
    }

    /**
     * 初始化方法，请求不加密
     * 
     * @param appId 应用唯一标识，从后台获取
     * @param sandbox 测试模式
     * @param offlineMode 离线模式
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午6:44:53
     */
    public static SGTManager getInstance(String appId, boolean sandbox,
            boolean offlineMode) {
        return getInstance(appId, null, null, sandbox, offlineMode);
    }

    /**
     * 初始化方法
     * 
     * @param appId 应用唯一标识，从后台获取
     * @param appKey 应用加密标识
     * @param appSecret 应用加密标识
     * @param sandbox 测试模式
     * @param offlineMode 离线模式
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午6:45:02
     */
    public static SGTManager getInstance(String appId, String appKey, String appSecret,
            boolean sandbox, boolean offlineMode) {
        return getInstance(SGTContext.constructSGTContext(appId, appKey, appSecret, sandbox, offlineMode));
    }

    private PlatformNetworkStateListener platformNetworkStateListener;

    public void setPlatformNetworkStateListener(PlatformNetworkStateListener platformNetworkStateListener) {
        this.platformNetworkStateListener = platformNetworkStateListener;
    }

    private SGTContext sgtContext;

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getSgtContext()
     */
    @Override
    public SGTContext getSgtContext() {
        return sgtContext;
    }

    /*
     * (non-Javadoc)
     * @see
     * me.gall.sgt.java.core.SGTServiceInterface#setSgtContext(me.gall.sgt.java
     * .core.SGTManager.SGTContext)
     */
    @Override
    public void setSgtContext(SGTContext sgtContext) {
        this.sgtContext = sgtContext;
    }

    protected SGTManager() {
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getAppId()
     */
    @Override
    public String getAppId() {
        return sgtContext.getAppId();
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#isOfflineMode()
     */
    @Override
    public boolean isOfflineMode() {
        return sgtContext.isOfflineMode();
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getRequestTimeout()
     */
    @Override
    public int getRequestTimeout() {
        return sgtContext.getRequestTimeout();
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getCurrentServer()
     */
    @Override
    public Server getCurrentServer() {
        return sgtContext.getServer();
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getCurrentUser()
     */
    @Override
    public User getCurrentUser() {
        return sgtContext.getUser();
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#init()
     */
    @Override
    public void init() {
        logger.debug("Start initilizing.");
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getRouterService()
     */
    @Override
    public RouterService getRouterService() throws Throwable {
        return sgtContext.getService(RouterService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getUserService()
     */
    @Override
    public UserService getUserService() throws Throwable {
        return sgtContext.getService(UserService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getAnnouncementService()
     */
    @Override
    public AnnouncementService getAnnouncementService() throws Throwable {
        return sgtContext.getService(AnnouncementService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getSgpPlayerService()
     */
    @Override
    public SgpPlayerService getSgpPlayerService() throws Throwable {
        return sgtContext.getService(SgpPlayerService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getLeaderBoardService()
     */
    @Override
    public LeaderBoardService getLeaderBoardService() throws Throwable {
        return sgtContext.getService(LeaderBoardService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getMailService()
     */
    @Override
    public MailService getMailService() throws Throwable {
        return sgtContext.getService(MailService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getFriendshipService()
     */
    @Override
    public FriendshipService getFriendshipService() throws Throwable {
        return sgtContext.getService(FriendshipService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getCheckinService()
     */
    @Override
    public CheckinService getCheckinService() throws Throwable {
        return sgtContext.getService(CheckinService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getCampaignService()
     */
    @Override
    public CampaignService getCampaignService() throws Throwable {
        return sgtContext.getService(CampaignService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getBossService()
     */
    @Override
    public BossService getBossService() throws Throwable {
        return sgtContext.getService(BossService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getGachaBoxService()
     */
    @Override
    public GachaBoxService getGachaBoxService() throws Throwable {
        return sgtContext.getService(GachaBoxService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getBlacklistService()
     */
    @Override
    public BlacklistService getBlacklistService() throws Throwable {
        return sgtContext.getService(BlacklistService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getStructuredDataService()
     */
    @Override
    public StructuredDataService getStructuredDataService() throws Throwable {
        return sgtContext.getService(StructuredDataService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getTicketService()
     */
    @Override
    public TicketService getTicketService() throws Throwable {
        return sgtContext.getService(TicketService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getDelegateDidService()
     */
    @Override
    public DelegateDidService getDelegateDidService() throws Throwable {
        return sgtContext.getService(DelegateDidService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getPlayerExtraService()
     */
    @Override
    public PlayerExtraService getPlayerExtraService() throws Throwable {
        return sgtContext.getService(PlayerExtraService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getPrivateChannelService()
     */
    @Override
    public PrivateChannelService getPrivateChannelService() throws Throwable {
        return sgtContext.getService(PrivateChannelService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getPublicChannelService()
     */
    @Override
    public PublicChannelService getPublicChannelService() throws Throwable {
        return sgtContext.getService(PublicChannelService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#getStoreService()
     */
    @Override
    public StoreService getStoreService() throws Throwable {
        return sgtContext.getService(StoreService.class,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#signup(java.lang.String,
     * java.lang.String)
     */
    @Override
    public User signup(String username, String password) throws Throwable {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setRegistryType(User.MANUAL);
        user = getUserService().register(user);
        // 废弃的实现
        // User user = getUserService().register(username, password);
        sgtContext.setCurrentUser(user);
        return user;
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#login(java.lang.String,
     * java.lang.String)
     */
    @Override
    public User login(String username, String password) throws Throwable {
        User user = null;
        user = getUserService().login(username, password);
        sgtContext.setCurrentUser(user);
        return user;
    }

    /*
     * (non-Javadoc)
     * @see
     * me.gall.sgt.java.core.SGTServiceInterface#updateRouting(java.util.Map)
     */
    @Override
    public Server updateRouting(Map<String, String> params) throws Throwable {
        Server server = null;
        server = getRouterService().route(getAppId(), params);
        sgtContext.setCurrentServer(server);
        return server;
    }

    /*
     * (non-Javadoc)
     * @see
     * me.gall.sgt.java.core.SGTServiceInterface#setCurrentUser(me.gall.sgp.
     * sdk.entity.User)
     */
    @Override
    public void setCurrentUser(User user) {
        sgtContext.setCurrentUser(user);
    }

    /*
     * (non-Javadoc)
     * @see
     * me.gall.sgt.java.core.SGTServiceInterface#getService(java.lang.Class)
     */
    @Override
    public <T> T getService(Class<T> clazz) throws Throwable {
        return sgtContext.getService(clazz,
                hasInternetConnection());
    }

    /*
     * (non-Javadoc)
     * @see
     * me.gall.sgt.java.core.SGTServiceInterface#routeByChannel(java.lang.String
     * )
     */
    @Override
    public Server routeByChannel(String channel) throws Throwable {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(RouterService.CHANNEL_ID, channel);
        return updateRouting(params);
    }

    /*
     * (non-Javadoc)
     * @see me.gall.sgt.java.core.SGTServiceInterface#destroy()
     */
    @Override
    public void destroy() {
        cachedManagers.remove(sgtContext.getAppId());
    }

    public boolean hasInternetConnection() {
        // TODO Auto-generated method stub
        if (platformNetworkStateListener != null) {
            return platformNetworkStateListener.hasInternetConnection();
        } else
            return true;
    }

    @Override
    public Logger getLogger() {
        // TODO Auto-generated method stub
        return logger;
    }

    @Override
    public AchievementService getAchievementService() throws Throwable {
        // TODO Auto-generated method stub
        return sgtContext.getService(AchievementService.class,
                hasInternetConnection());
    }

    @Override
    public DailyTaskService getDailyTaskService() throws Throwable {
        // TODO Auto-generated method stub
        return sgtContext.getService(DailyTaskService.class,
                hasInternetConnection());
    }

    @Override
    public FileStorageService getFileStorageService() throws Throwable {
        // TODO Auto-generated method stub
        return sgtContext.getService(FileStorageService.class,
                hasInternetConnection());
    }

    @Override
    public FriendshipExtraService getFriendshipExtraService() throws Throwable {
        // TODO Auto-generated method stub
        return sgtContext.getService(FriendshipExtraService.class,
                hasInternetConnection());
    }

    @Override
    public GiftCodeService getGiftCodeService() throws Throwable {
        // TODO Auto-generated method stub
        return sgtContext.getService(GiftCodeService.class,
                hasInternetConnection());
    }

    @Override
    public NotificationService getNotificationService() throws Throwable {
        // TODO Auto-generated method stub
        return sgtContext.getService(NotificationService.class,
                hasInternetConnection());
    }

    @Override
    public TimestampService getTimestampService() throws Throwable {
        // TODO Auto-generated method stub
        return sgtContext.getService(TimestampService.class, hasInternetConnection());
    }

    @Override
    public CheckinBoardService getCheckinBoardService() throws Throwable {
        // TODO Auto-generated method stub
        return sgtContext.getService(CheckinBoardService.class, hasInternetConnection());
    }

    @Override
    public User quickLogin() throws Throwable {
        // TODO Auto-generated method stub
        throw new Exception("Do not use this feature [quickLogin()] under pc or server.");
    }

    @Override
    public InvitationCodeService getInvitationCodeService() throws Throwable {
        // TODO Auto-generated method stub
        return sgtContext.getService(InvitationCodeService.class, hasInternetConnection());
    }

}
