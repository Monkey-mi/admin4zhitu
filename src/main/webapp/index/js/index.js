var t1;

$(document).ready(function() {
	indexAjax.queryLatestWeekRecommend();
});

/**
 * 首页模块异步方法
 */
var indexAjax = {
	queryLatestWeekRecommend : function() {
		$.post("./operations/ztworld_queryLatestWeekRecommend",{
			start:1,
			limit:5
		},function(result) {
			if(result['result'] == 0) {
				var recommends = result['htworld'],
				$worldPics = $("div.world_pic");
				for(var i = 0; i < recommends.length; i++) {
					var recommend = recommends[i],
						$worldPic = $worldPics.eq(i);
					if(recommend != null) {
						var authorName = baseTools.nameFilter(recommend['authorName']),
							thumbPath = recommend['titleThumbPath'];
							if(thumbPath == null || thumbPath == "") {
								thumbPath = recommend['titlePath'];
							}
							titleThumbPath = baseTools.imgPathFilter(thumbPath, "../images/world_tip_bg.png"),
							authorAvatar = baseTools.imgPathFilter(recommend['authorAvatar'], "../../base/images/no_avatar_ssmall.jpg");
						
						$worldPic.attr('link',recommend['worldURL']).attr('author',authorName).attr('stime',recommend['dateModified']).attr('avatar',authorAvatar);
						$worldPic.children('img').first().attr('src',titleThumbPath);
						$worldPic
						.bind('click',indexUI.worldPicClick)
						.bind('mouseenter',indexUI.worldPicMouseEnter)
						.bind('mouseleave',indexUI.worldPicMouseLeave);
					}
				}
			}
		});
	}
};

/**
 * 首页模块UI操作
 */
var indexUI = {
		
	/**
	 * 精选世界鼠标悬浮事件
	 */
	worldPicMouseEnter : function(){
		clearTimeout(t1);
		var $this = $(this),
			authorName = $this.attr('author'),
			avatar = $this.attr('avatar'),
			stime = $this.attr('stime');
		$("#tip_table #autho_avatar").attr('src',avatar);
		$("#tip_table #author_name").text(authorName);
		$("#tip_table #share_date").text(stime);
		var o = $this.offset(),
			l = o.left - $("#tip_table").width() / 2 + 92,
			t = o.top - $("#tip_table").height();
		$("#tip_table").show().offset({left:l,top:t});
	},
	
	/**
	 * 精选世界鼠标离开事件
	 */
	worldPicMouseLeave : function(event) {
		event.stopPropagation(); 
		clearTimeout(t1);
		t1 = setTimeout(function() {
				$("#tip_table").hide();
				clearTimeout(t1);
			},500);
	},
	/**
	 * 精选世界点击事件
	 */
	worldPicClick : function () {
		var link = $(this).attr('link');
		window.open(link,target='blank');
	}
};