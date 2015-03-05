$(document).ready(function() {
	$(".hot_small_td").mouseenter(hotUI.hotMouseEnter);
	$(".hot_small_td").mouseleave(hotUI.hotMouseLeave);
	hotAjax.initCommentHot();
	hotAjax.initLikeHot();
});

/**
 * 异步动作
 */
var hotAjax = {
	/**
	 * 初始化热评
	 */
	initCommentHot : function() {
		var $loading = $("#comment_hot_loading"), $hotTable = $("#comment_hot_table");
		$.post("./htworld/worldmaintain_queryLatestHotComment",
				function(result) {
					if (result['result'] == 0) {
						var worlds = result['msg'];
						hotUI.initHot(worlds, $loading, $hotTable);
					} else {
						$loading.hide();
						alert('初始化数据失败...请重试');
					}
				});
	},
	/**
	 * 初始化热门喜欢
	 */
	initLikeHot : function() {
		var $loading = $("#like_hot_loading"), $hotTable = $("#like_hot_table");
		$.post("./htworld/worldmaintain_queryLatestHotLike", function(result) {
			if (result['result'] == 0) {
				var worlds = result['msg'];
				hotUI.initHot(worlds, $loading, $hotTable);
			} else {
				$loading.hide();
				alert('初始化数据失败...请重试');
			}
		});
	}
};
/**
 * UI时间
 */
var hotUI = {
	/**
	 * 初始化热门UI元素
	 * @param worlds
	 * @param $loading
	 * @param $hotTable
	 */
	initHot : function(worlds, $loading, $hotTable) {
		var $loadingHots = $hotTable.find('.loading_world_info');
		for ( var i = 0; i < worlds.length; i++) {
			var world = worlds[i],
				$hot = $loadingHots.eq(i),
				thumbPath = world['titleThumbPath'];
			if(thumbPath == null || thumbPath == "") {
				thumbPath = world['titlePath'];
			}
			$hot.find(".world_img:eq(0)").attr('src', thumbPath);
			$hot.find(".author_avatar_img:eq(0)").attr('src',baseTools.imgPathFilter(world['authorAvatar']),'../../base/images/no_avatar_ssmall.jpg');
			$hot.find('.author_name:eq(0)').text(baseTools.nameFilter(world['authorName']));
			$hot.find('.share_date:eq(0)').text(world['dateAdded']);
			$hot.find('.comment_count:eq(0)').text(world['commentCount']);
			$hot.find('.like_count:eq(0)').text(world['likeCount']);
			$hot.removeClass('loading_world_info');
			$hot.attr('link', world['worldURL']).attr('title', '点击查看').css({
				'cursor' : 'pointer'
			}).bind('click', hotUI.hotClick);
		}
		$loading.hide();
	},
	/**
	 * 元素点击事件
	 */
	hotClick : function() {
		var link = $(this).attr('link');
		window.open(link, target = 'blank');
	},
	/**
	 * 元素鼠标进入事件
	 */
	hotMouseEnter : function() {
		$(this).children('.hot_small_info_div:eq(0)').fadeIn();
	},
	/**
	 * 元素鼠标离开事件
	 */
	hotMouseLeave : function() {
		$(this).children('.hot_small_info_div:eq(0)').fadeOut();
	}
};