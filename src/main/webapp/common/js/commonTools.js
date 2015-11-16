var commonTools = window.NameSpace || {};

/**
 * 打开织图互动展示（频道织图使用）页面
 * 
 * @param worldId	织图id
 * @param worldURL	织图在网页中展示的短链，即一个http链接
 * @param channelWorldValid	频道织图是否生效
 * 
 * @author zhangbo 2015-11-06
 */
commonTools.openWorldInteractForChannelWorldPage = function(worldId, worldURL, channelWorldValid) {
	var url = "page_htworld_htworldShowForChannelWorld";
	url += "?worldId=" + worldId;
	url += "&worldURL=" + worldURL;
	url += "&valid=" + channelWorldValid;
	
	$.fancybox({
		href: url,
		width: "98%",
		height: "98%",
		autoScale: true,
		type: "iframe",
		transitionIn: "none",	// 打开无动画
		transitionOut: "elastic"	// 关闭动画：伸缩
	});
};

/**
 * 打开用户详细信息页面
 * 
 * @param userId	用户id
 * 
 * @author zhangbo 2015-11-06
 */
commonTools.openUserInfoPage = function(userId) {
	var url = "page_user_userInfo?userId=" + userId;
	$.fancybox({
		href: url,
		width: "100%",
		height: "100%",
		autoScale: true,
		type: "iframe",
		transitionIn: "none",	// 打开无动画
		transitionOut: "elastic"	// 关闭动画：伸缩
	});
};

/**
 * 打开织图喜欢详细信息页面
 * @param worldId	织图id
 * @author zhangbo 2015-11-06
 */
commonTools.openWorldLikedPage = function(worldId) {
	var url = "page_htworld_htworldLiked?worldId=" + worldId;
	$.fancybox({
		href: url,
		margin: 20,
		width: "100%",
		height: "100%",
		autoScale: true,
		type: "iframe",
		transitionIn: "none",	// 打开无动画
		transitionOut: "elastic"	// 关闭动画：伸缩
	});
};

/**
 * 打开织图评论详细信息页面（包括正在计划的评论）
 * @param worldId	织图id
 * @author zhangbo 2015-11-06
 */
commonTools.openWorldCommentsPage = function(worldId) {
	var url = "page_interact_interactWCommentAutoComment?worldId=" + worldId;
	$.fancybox({
		href: url,
		margin: 20,
		width: "100%",
		height: "100%",
		autoScale: true,
		type: "iframe",
		transitionIn: "none",	// 打开无动画
		transitionOut: "elastic"	// 关闭动画：伸缩
	});
};

/**
 * 打开织图评论详细信息页面（包括正在计划的评论）
 * @param shortLink	织图短链
 * @author zhangbo 2015-11-06
 */
commonTools.showWorld = function(shortLink) {
	// 根据短链构造真实织图链接
	var url = "http://www.imzhitu.com/DT" + shortLink;
	$.fancybox({
		href: url,
		title: url,
		titlePosition: 'over',
		titleShow: true,
		margin: 20,
		width: "100%",
		height: "100%",
		autoScale: true,
		type: "iframe",
		transitionIn: "none",	// 打开无动画
		transitionOut: "elastic"	// 关闭动画：伸缩
	});
}