
package me.gall.sgt.java.core;

import java.util.Map;

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

public interface SGTServiceInterface{

    public static final String LOG_TAG = SGTManager.class.getSimpleName();

    /**
     * 获取SGT上下文
     * 
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午3:40:02
     */
    public abstract SGTContext getSgtContext();

    public abstract void setSgtContext(SGTContext sgtContext);

    /**
     * 获取SGT的APPID
     * 
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午3:40:23
     */
    public abstract String getAppId();

    /**
     * 是否启用离线模式
     * 
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午3:40:33
     */
    public abstract boolean isOfflineMode();

    /**
     * 获取请求超时时长
     * 
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午3:40:45
     */
    public abstract int getRequestTimeout();

    /**
     * 获取当前的服务器
     * 
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午3:41:00
     */
    public abstract Server getCurrentServer();

    /**
     * 获取当前的用户账户
     * 
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午3:41:10
     */
    public abstract User getCurrentUser();

    /**
     * 初始化方法（不需要手动调用）
     * 
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午3:41:24
     */
    public abstract void init();

    /**
     * 获取路由服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:31:25
     */
    public abstract RouterService getRouterService() throws Throwable;

    /**
     * 获取用户服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:31:34
     */
    public abstract UserService getUserService() throws Throwable;

    /**
     * 获取公告服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:31:45
     */
    public abstract AnnouncementService getAnnouncementService() throws Throwable;

    /**
     * 获取角色服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:31:55
     */
    public abstract SgpPlayerService getSgpPlayerService() throws Throwable;

    /**
     * 获取排行榜服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:32:24
     */
    public abstract LeaderBoardService getLeaderBoardService() throws Throwable;

    /**
     * 获取邮件服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:32:36
     */
    public abstract MailService getMailService() throws Throwable;

    /**
     * 获取好友关系服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:32:51
     */
    public abstract FriendshipService getFriendshipService() throws Throwable;

    /**
     * 获取签到服务，请使用新版的getCheckinBoardService
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:33:00
     */
    public abstract CheckinService getCheckinService() throws Throwable;

    /**
     * 获取活动服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:33:08
     */
    public abstract CampaignService getCampaignService() throws Throwable;

    /**
     * 获取Boss服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:33:17
     */
    public abstract BossService getBossService() throws Throwable;

    /**
     * 获取抽奖服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:33:26
     */
    public abstract GachaBoxService getGachaBoxService() throws Throwable;

    /**
     * 获取黑名单服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:33:34
     */
    public abstract BlacklistService getBlacklistService() throws Throwable;

    /**
     * 获取结构化数据服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:33:47
     */
    public abstract StructuredDataService getStructuredDataService() throws Throwable;

    /**
     * 获取反馈服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:33:59
     */
    public abstract TicketService getTicketService() throws Throwable;

    /**
     * 获取代理请求服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年5月16日 下午7:55:20
     */
    public abstract DelegateDidService getDelegateDidService() throws Throwable;

    /**
     * 获取扩展角色服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年5月16日 下午7:56:29
     */
    public abstract PlayerExtraService getPlayerExtraService() throws Throwable;

    /**
     * 返回私有消息通道服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年5月16日 下午7:57:14
     */
    public abstract PrivateChannelService getPrivateChannelService() throws Throwable;

    /**
     * 返回公共消息通道服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年5月16日 下午7:57:14
     */
    public abstract PublicChannelService getPublicChannelService() throws Throwable;

    /**
     * 返回成就系统服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月13日 下午5:59:16
     */
    public abstract AchievementService getAchievementService() throws Throwable;

    /**
     * 获取每日任务服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月13日 下午6:03:18
     */
    public abstract DailyTaskService getDailyTaskService() throws Throwable;

    /**
     * 获取文件存储服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月13日 下午6:03:46
     */
    public abstract FileStorageService getFileStorageService() throws Throwable;

    /**
     * 获取好友关系扩展数据服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月13日 下午6:03:58
     */
    public abstract FriendshipExtraService getFriendshipExtraService() throws Throwable;

    /**
     * 获取兑换码服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月13日 下午6:24:32
     */
    public abstract GiftCodeService getGiftCodeService() throws Throwable;;

    /**
     * 获取通知系统服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月13日 下午6:24:50
     */
    public abstract NotificationService getNotificationService() throws Throwable;

    /**
     * 返回商城服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年5月16日 下午7:57:14
     */
    public abstract StoreService getStoreService() throws Throwable;

    /**
     * 获取时间戳服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月25日 下午12:49:40
     */
    public abstract TimestampService getTimestampService() throws Throwable;

    /**
     * 获取新版的签到服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月25日 下午12:49:59
     */
    public abstract CheckinBoardService getCheckinBoardService() throws Throwable;

    /**
     * 获取邀请码服务
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月30日 下午2:51:22
     */
    public abstract InvitationCodeService getInvitationCodeService() throws Throwable;

    /**
     * 使用用户名和密码注册新用户
     * 
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:25:39
     */
    public abstract User signup(String username, String password) throws Throwable;

    /**
     * 使用用户名和密码登录账户
     * 
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:28:19
     */
    public abstract User login(String username, String password) throws Throwable;

    /**
     * 快速登录，在local内创建一个SGT_TEMP_ACCOUNT的文件记录随机生成的用户名和密码
     * 
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月25日 下午12:51:11
     */
    public abstract User quickLogin() throws Throwable;

    /**
     * 使用路由参数更新路由服务器，需要在后台预先设置好匹配的路由规则
     * 
     * @param params 限制参数集合
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:28:51
     */
    public abstract Server updateRouting(Map<String, String> params) throws Throwable;

    /**
     * 设置当前的用户账户
     * 
     * @param user
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午3:42:10
     */
    public abstract void setCurrentUser(User user);

    /**
     * 加载自定义的服务
     * 
     * @param clazz 自定义服务类，必须以Service结尾
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午3:42:19
     */
    public abstract <T> T getService(Class<T> clazz) throws Throwable;

    /**
     * 根据渠道标识更新路由服务器
     * 
     * @param channel 渠道标识，可以在管理后台配置
     * @return
     * @throws Throwable
     * @version 1.0
     * @author 黄承开 update 2013年10月18日 下午8:29:36
     */
    public abstract Server routeByChannel(String channel) throws Throwable;

    /**
     * 回收当前的资源
     * 
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午3:43:08
     */
    public abstract void destroy();

    /**
     * 返回一个slf4j的Logger实例
     * 
     * @return
     * @version 1.0
     * @author 黄承开 update 2014年9月11日 下午6:17:42
     */
    public Logger getLogger();

}
