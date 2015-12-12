package com.imzhitu.admin.common.database;

/**
 * @author zhangbo	2015年7月17日
 *
 */
public class Admin {
	
	public static final String KEYGEN = "keygen";

	public static final String ADMIN_USER_INFO = "`hts_admin`.`admin_user_info`";
	
	public static final String ADMIN_ROLE = "`hts_admin`.`admin_role`";
	
	public static final String ADMIN_USER_INFO_ROLE = "`hts_admin`.`admin_user_info_role`";
	
	public static final String ADMIN_PRIVILEGES = "`hts_admin`.`admin_privileges`";
	
	public static final String ADMIN_PRIVILEGES_GROUP = "`hts_admin`.`admin_privileges_group`";
	
	public static final String ADMIN_ROLE_PRIVILEGES_GROUP = "`hts_admin`.`admin_role_privileges_group`";
	
	public static final String ADMIN_USER_LOGIN_PERSISTENT = "`hts_admin`.`admin_user_login_persistent`";
	
	public static final String ADMIN_INTERACT_WORLD_LABEL_COMMENT_LABEL = "hts_admin.interact_world_label_comment_label";
	
	public static final String HTS_USER_LABEL = "hts.user_label";
	
	public static final String HTS_WORLD_TYPE = "hts.htworld_type";
	
	public static final String HTS_WORLD_LABEL = "hts.htworld_label";
	
	public static final String HTS_OPERATIONS_USER_ZOMBIE = "hts.operations_user_zombie";
	
	/**
	 * 织图互动维护
	 */
	public static final String INTERACT_WORLD = "interact_world";
	
	/**
	 * 织图互动评论库表
	 */
	public static final String INTERACT_COMMENT = "interact_comment";
	
	/**
	 * 织图活动评论库标签表
	 */
	public static final String INTERACT_COMMENT_LABEL = "interact_comment_label";
	
	/**
	 * 织图互动评论表
	 */
	public static final String INTERACT_WORLD_COMMENT = "interact_world_comment";
	
	/**
	 * 织图互动喜欢表
	 */
	public static final String INTERACT_WORLD_LIKE = "interact_world_liked";
	
	/**
	 * 织图互动点击表
	 */
	public static final String INTERACT_WORLD_CLICK = "interact_world_click";
	
	/***
	 * 用户互动表
	 */
	public static final String INTERACT_USER = "interact_user";

	/**
	 * 用户互动粉丝表
	 */
	public static final String INTERACT_USER_FOLLOW = "interact_user_follow";
	
	/**
	 * 互动跟踪表
	 */
	public static final String INTERACT_TRACKER = "interact_tracker";
	
	/**
	 * 织图举报表
	 */
	public static final String INTERACT_WORLD_REPORT = "interact_world_report";
	
	/**
	 * 用户等级表
	 */
	public static final String INTERACT_USER_LEVEL = "interact_user_level";
	
	/**
	 * 织图等级表
	 */
	public static final String INTERACT_WORLD_LEVEL = "interact_world_level";
	
	/**
	 * 等级织图列表
	 */
	public static final String INTERACT_WORLD_LEVEL_LIST = "hts_admin.interact_world_level_list";
	
	/**
	 * 织图用户等级列表
	 */
	public static final String INTERACT_USER_LEVEL_LIST = "interact_user_levle_list";
	
	/**
	 * 织图用户标签--评论标签关联表
	 */
	public static final String INTERACT_WORLD_LABEL_COMMENT_LABEL = "interact_world_label_comment_label";
	
	/**
	 * 广场分类计划表
	 */
	public static final String INTERACT_TYPE_WORLD_SCHEDULA = "hts_admin.interact_type_schedula";
	
	/*
	 * 自动回复
	 */
	public static final String INTERACT_AUTO_RESPONSE = "hts_admin.interact_auto_response";
	
	/**
	 * 活动是否被操作过
	 */
	public static final String INTERACT_ACTIVE_OPERATED = "hts_admin.interact_active_operated";
	
	/**
	 * 织图等级对应的织图评论
	 */
	public static final String INTERACT_WORLD_LEVEL_WORLD_COMMENT = "hts_admin.interact_world_level_world_comment";
	
	/**
	 * 织图等级对应的织图标签
	 */
	public static final String INTERACT_WORLD_LEVEL_WORLD_LABEL = "hts_admin.interact_world_level_world_label";
	
	/**
	 * 操作日志表
	 */
	public static final String LOGGER_OPERATION = "`hts_admin`.`logger_operation`";
	
	/**
	 * 用户操作日志表
	 */
	public static final String LOGGER_USER_OPERATION = "`hts_admin`.`logger_user_operation`";
	
	/**
	 * 用户信任记录表
	 */
	public static final String USER_TRUST = "hts_admin.user_trust";
	
	/**
	 * 计划评论表
	 */
	public static final String INTERACT_PLAN_COMMENT = "hts_admin.interact_plan_comment";
	
	/**
	 * 计划评论标签
	 */
	public static final String INTERACT_PLAN_COMMENT_LABEL = "hts_admin.interact_plan_comment_label";
	
	/*
	 *************************** 
	 * 用户主键生成KEY
	 ***************************
	 */
	
	/**
	 * 织图互动id
	 */
	public static final Integer KEYGEN_INTERACT_WORLD_ID = 1;
	
	/**
	 * 用户互动id
	 */
	public static final Integer KEYGEN_INTERACT_USER_ID = 2;
	
	/**
	 * interact_comment keyId
	 */
	public static final Integer INTERACT_COMMENT_KEYID = 3;
	
	/**
	 * logger_operation serial
	 */
	public static final Integer KEYGEN_LOGGER_OPERATION_SERIAL = 4;
	
	public static final Integer KEYGEN_ZOMBIE_WORLD =5;
	
	public static final Integer KEYGEN_ZOMBIE_CHILD_WORLD=6;
	
	/**
	 * 小秘书自动回复id
	 */
	public static final Integer KEYGEN_XIAOMISHU_RESPONSE_ID = 7;
	/**
	 * 小秘书自动回复keyId
	 */
	public static final Integer KEYGEN_XIAOMISHU_RESPONSE_KEY_ID = 8;
	
	/**
	 * 织图字幕id
	 */
	public static final Integer KEYGEN_WORLD_SUBTITLE_ID = 9;
	
	/**
	 * 小秘书自动回复moduleId
	 */
	public static final Integer KEYGEN_XIAOMISHU_RESPONSE_MODULE_ID = 10;
	
	/**
	 * 系统弹幕id
	 */
	public static final Integer KEYGEN_OP_SYS_DANMU_ID = 11;
	
	/**
	 * 频道公告排序
	 */
	public static final Integer KEYGEN_OP_MSG_BULLETIN_SERIAL = 12;
	
	/**
	 * 启动页排序
	 */
	public static final Integer KEYGEN_OP_MSG_START_PAGE_SERIAL = 13;
	
	/**
	 * 分类织图SERIAL
	 * 
	 */
	public static final Integer KEYGEN_ZT_TYPEWORLD_SERIAL	= 14;
	
	/**
	 * 商品集合serial流水
	 * @author zhangbo	2015年12月12日
	 */
	public static final Integer KEYGEN_ITEM_SET_SERIAL	= 15;
	
	/**
	 * 商品集合id流水
	 * @author zhangbo	2015年12月12日
	 */
	public static final Integer KEYGEN_ITEM_SET_ID	= 16;
	
	
	/**
	 * admin系统与daemon守护系统共同操作redis的key值
	 * @author zhangbo	2015年7月17日
	 */
	public static final String ADMIN_DAEMON_ADCACHEKEY = "admin:daemon:ADCacheKey";
	
	/**
	 * 批量推送lock标记位,用于批量发送消息时,禁止daemon查询查询历史推送数据
	 */
	public static final String ADMIN_BATCH_PUSH_LOCK = "admin:batchPushLock";
	
	/**
	 * 织图官方账号id
	 * 
	 * @author zhangbo	2015年8月18日
	 */
	public static final Integer ZHITU_UID = 2063;
	
	/**
	 * 织图被选入频道给用户发送通知的标记位
	 * 
	 * @author zhangbo	2015年9月2日
	 */
	public static final String NOTICE_WORLD_INTO_CHANNEL = "world_into_channel";
	
	/**
	 * 频道织图被选为精选给用户发送通知的标记位
	 * 
	 * @author zhangbo	2015年9月2日
	 */
	public static final String NOTICE_CHANNELWORLD_TO_SUPERB = "channelworld_to_superb";
	
	/**
	 * 频道成员被选为红人给用户发送通知的标记位
	 * 
	 * @author zhangbo	2015年9月2日
	 */
	public static final String NOTICE_CHANNELMEMBER_TO_STAR = "channelmember_to_star";
	
	public static final String worldURLPrefix = "http://www.imzhitu.com/DT";
	
}
