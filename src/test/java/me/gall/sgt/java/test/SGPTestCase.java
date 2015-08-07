
package me.gall.sgt.java.test;

import java.util.HashMap;

import me.gall.sgp.sdk.entity.Server;
import me.gall.sgp.sdk.entity.User;
import me.gall.sgp.sdk.entity.app.SgpPlayer;
import me.gall.sgp.sdk.service.RouterService;
import me.gall.sgp.sdk.service.SgpPlayerService;
import me.gall.sgt.java.core.SGTManager;

import org.junit.Assert;
import org.junit.Test;

public class SGPTestCase {

    /**
     * 二次分服测试
     * 
     * @version 1.0
     * @author 黄承开 update 2014年4月28日 上午11:02:20
     */
    @Test
    public void mainTest() {
        // context = new Context();
        try {
            SGTManager manager = SGTManager.getInstance("zsd_sgp", true, false);
            manager.getSgtContext().setRequestTimeout(5000);
            User user = manager.login("SdkTest2", "abcd1234");
            // System.out.println(user.getImei());
            // Assert.assertEquals(user.getImei(), device.getDeviceIMEI());
            HashMap<String, String> params = new HashMap<String, String>();
            params.put(RouterService.APP_SIGN, null);
            params.put(RouterService.CHANNEL_ID, "zsdDEBUG");
            params.put(RouterService.USER_ID, user.getUserid());
            params.put(RouterService.CREATE_TIME, String.valueOf(user.getCreateTime()));
            Server server = manager.updateRouting(params);
            Assert.assertNotNull(server);
            manager = SGTManager.getInstance("zsd_game", true, false);
            // user = manager.login("SdkTest2", "abcd1234");
            // System.out.println(user.getImei());
            // Assert.assertEquals(user.getImei(), device.getDeviceIMEI());
            params = new HashMap<String, String>();
            params.put(RouterService.APP_SIGN, null);
            params.put(RouterService.CHANNEL_ID, "zsdDEBUG");
            // params.put(RouterService.USER_ID, user.getUserid());
            params.put(RouterService.CREATE_TIME, String.valueOf(user.getCreateTime()));
            server = manager.updateRouting(params);
            Assert.assertNotNull(server);
            manager.getLogger().debug(server.getAddress());
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testUserLogin() {
        // context = new Context();
        try {
            SGTManager manager = SGTManager.getInstance("xmj2_sgt", true, false);
            User user = manager.login("zhangzhe", "zhangzhe");
            manager.getLogger().debug(user.getUserid());
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testInvitationCode(){
        // context = new Context();
            try {
                SGTManager manager = SGTManager.getInstance("ZumaFighter1", true, false);
                User user = manager.login("ZumaFighter1_97961620", "000000");
                // System.out.println(user.getImei());
                // Assert.assertEquals(user.getImei(), device.getDeviceIMEI());
                Server server = manager.routeByChannel("xmjwz_zmzs");
                System.out.println(server.getAddress());
                String code = manager.getInvitationCodeService().getInvitationCode("00000025912");
                System.out.println(code);
                Assert.assertNotNull(code);
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
    }

    /**
     * 测试Player的实体版本兼容性
     * 
     * @version 1.0
     * @author 黄承开 update 2014年4月28日 上午11:04:09
     */
    @Test
    public void testPlayerVersionCompitiable() {
        // context = new Context();
        try {
            SGTManager manager = SGTManager.getInstance("zsd_sgp", true, false);
            User user = manager.login("panjinghao", "123456");
            // System.out.println(user.getImei());
            // Assert.assertEquals(user.getImei(), device.getDeviceIMEI());
            HashMap<String, String> params = new HashMap<String, String>();
            params.put(RouterService.APP_SIGN, null);
            params.put(RouterService.CHANNEL_ID, "zsdDEBUG");
            params.put(RouterService.USER_ID, user.getUserid());
            params.put(RouterService.CREATE_TIME, String.valueOf(user.getCreateTime()));
            Server server = manager.updateRouting(params);
            Assert.assertNotNull(server);
            // manager = SGTManager.getInstance(context, "zsd_game", true,
            // false);
            // user = manager.login("SdkTest2", "abcd1234");
            // System.out.println(user.getImei());
            // Assert.assertEquals(user.getImei(), device.getDeviceIMEI());
            // params = new HashMap<String, String>();
            // params.put(RouterService.APP_SIGN, null);
            // params.put(RouterService.CHANNEL_ID, "zsdDEBUG");
            // // params.put(RouterService.USER_ID, user.getUserid());
            // params.put(RouterService.CREATE_TIME,
            // String.valueOf(user.getCreateTime()));
            // server = manager.updateRouting(params);
            // Assert.assertNotNull(server);
            // user= userService .register("IMEI00123456789", "Fake-ICCID",
            // "Fake-Mac");
            SgpPlayerService sgpPlayerService = manager.getSgpPlayerService();
            // sgpPlayerService.searchPlayersByLastLogin(1,10,new String[]{""});
            SgpPlayer[] s = sgpPlayerService.getByUserId(user.getUserid());
            for (SgpPlayer ss : s) {
                manager.getSgtContext().getLogger().debug(ss.getId());
            }

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
