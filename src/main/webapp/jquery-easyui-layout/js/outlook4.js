var navMenus;

$(function(){
	init();
});
/*
var result = {"result":0,"msg":[
	{
		"icon":"icon-sys",	
		"menuid":11,
		"menuname":"织图管理",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":196,"menuname":"分享列表","url":"page_htworldReadOnly_htworldList"},
		 	{"icon":"icon-nav","menuid":197,"menuname":"广场分类","url":"page_htworldReadOnly_htworldType"},
		 	{"icon":"icon-nav","menuid":203,"menuname":"活动审核","url":"page_operationsReadOnly_squarePushActivityWorld"},
		 	{"icon":"icon-nav","menuid":212,"menuname":"举报管理","url":"page_htworldReadOnly_htworldReport"},
		 	{"icon":"icon-nav","menuid":228,"menuname":"小秘书织图","url":"page_htworld_htworldZhiTuXiaoMiShu"},
		 	{"icon":"icon-nav","menuid":229,"menuname":"缩略图形状管理","url":"page_htworld_htworldChildType"}
		 ]
	},{
		"icon":"icon-sys",
		"menuid":14,
		"menuname":"用户管理",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":198,"menuname":"用户信息","url":"page_user_userInfo"},
		 	{"icon":"icon-nav","menuid":199,"menuname":"推荐用户","url":"page_operations_userRecommend"},
		 	{"icon":"icon-nav","menuid":202,"menuname":"马甲管理","url":"page_operations_userZombie"},
		 	{"icon":"icon-nav","menuid":225,"menuname":"消息管理","url":"page_user_userRecipientMsgBox?userId=13"},
		 	{"icon":"icon-nav","menuid":231,"menuname":"认证类型","url":"page_user_userVerify"}
		 ]
	},{
		"icon":"icon-sys",
		"menuid":15,
		"menuname":"开放平台",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":200,"menuname":"友盟统计","url":"http:\/\/www.umeng.com\/apps"},
		 	{"icon":"icon-nav","menuid":201,"menuname":"个推","url":"http:\/\/dev.igetui.com"}
		]
	},{
		"icon":"icon-sys",
		"menuid":9,
		"menuname":"管理员信息",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":195,"menuname":"管理列表","url":"page_userInfo_userInfo"},
		 	{"icon":"icon-nav","menuid":239,"menuname":"用户交换","url":"page_userInfo_userInfoExchange"}
		 ]
	},{
		"icon":"icon-sys",
		"menuid":10,
		"menuname":"权限管理",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":192,"menuname":"角色管理","url":"page_privileges_role"},
		 	{"icon":"icon-nav","menuid":193,"menuname":"权限分组","url":"page_privileges_privilegesGroup"},
		 	{"icon":"icon-nav","menuid":194,"menuname":"权限管理","url":"page_privileges_privileges"},
		 	{"icon":"icon-nav","menuid":235,"menuname":"操作日志","url":"page_logger_operation"},
		 	{"icon":"icon-nav","menuid":241,"menuname":"用户权限管理","url":"page_privileges_userPrivileges"}
		 ]
	},{
		"icon":"icon-sys",
		"menuid":13,
		"menuname":"广告管理",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":204,"menuname":"App推荐","url":"page_operations_appLink"},
		    {"icon":"icon-nav","menuid":205,"menuname":"开放链接","url":"page_operations_appLinkOpen"}
		]
	},{
		"icon":"icon-sys",
		"menuid":18,
		"menuname":"织图信息",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":213,"menuname":"分享信息","url":"page_htworldReadOnly_htworldList"}
		]
	},{
		"icon":"icon-sys",
		"menuid":19,
		"menuname":"用户信息",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":218,"menuname":"用户列表","url":"page_userReadOnly_userInfo"}
		]
	},{
		"icon":"icon-sys",
		"menuid":20,
		"menuname":"数据统计",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":233,"menuname":"汇总分析","url":"page_statistics_summary"}
		]
	},{
		"icon":"icon-sys",
		"menuid":22,
		"menuname":"公告维护",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":236,"menuname":"公告列表","url":"page_operations_notice"}
		]
	},{
		"icon":"icon-sys",
		"menuid":24,
		"menuname":"频道维护",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":411,"menuname":"频道列表","url":"page_operations_channel"},
		 	{"icon":"icon-nav","menuid":412,"menuname":"频道织图","url":"page_operations_channelWorld"},
		 	{"icon":"icon-nav","menuid":413,"menuname":"频道红人","url":"page_operations_channelStar"},
		 	{"icon":"icon-nav","menuid":414,"menuname":"Top红人榜","url":"page_operations_channelTopOne"}
    		]
        }
]};      
*/
var result = {"result":0,"msg":[
	{
		"icon":"icon-sys",	
		"menuid":11,
		"menuname":"织图管理",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":196,"menuname":"分享列表","url":"page_htworldReadOnly_htworldList"},
		 	{"icon":"icon-nav","menuid":197,"menuname":"广场分类","url":"page_htworldReadOnly_htworldType"},
		 	{"icon":"icon-nav","menuid":203,"menuname":"活动审核","url":"page_operationsReadOnly_squarePushActivityWorld"},
		 	{"icon":"icon-nav","menuid":212,"menuname":"举报管理","url":"page_htworldReadOnly_htworldReport"},
		 	{"icon":"icon-nav","menuid":228,"menuname":"小秘书织图","url":"page_htworldReadOnly_htworldZhiTuXiaoMiShu"},
		 	{"icon":"icon-nav","menuid":229,"menuname":"缩略图形状管理","url":"page_htworld_htworldChildType"}
		 ]
	},{
		"icon":"icon-sys",
		"menuid":14,
		"menuname":"用户管理",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":198,"menuname":"用户信息","url":"page_userReadOnly_userInfo"},
		 	{"icon":"icon-nav","menuid":199,"menuname":"推荐用户","url":"page_operationsReadOnly_userRecommend"},
		 	{"icon":"icon-nav","menuid":225,"menuname":"消息管理","url":"page_userReadOnly_userRecipientMsgBox?userId=13"},
		 	{"icon":"icon-nav","menuid":231,"menuname":"认证类型","url":"page_userReadOnly_userVerify"}
		 ]
	},{
		"icon":"icon-sys",
		"menuid":13,
		"menuname":"广告管理",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":204,"menuname":"App推荐","url":"page_operations_appLink"},
		    {"icon":"icon-nav","menuid":205,"menuname":"开放链接","url":"page_operations_appLinkOpen"}
		]
	},{
		"icon":"icon-sys",
		"menuid":22,
		"menuname":"公告维护",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":236,"menuname":"公告列表","url":"page_operations_notice"}
		]
	},{
		"icon":"icon-sys",
		"menuid":24,
		"menuname":"频道维护",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":411,"menuname":"频道列表","url":"page_operationsReadOnly_channel"},
		 	{"icon":"icon-nav","menuid":412,"menuname":"频道织图","url":"page_operationsReadOnly_channelWorld"},
		 	{"icon":"icon-nav","menuid":413,"menuname":"频道红人","url":"page_operationsReadOnly_channelStar"},
		 	{"icon":"icon-nav","menuid":414,"menuname":"Top红人榜","url":"page_operationsReadOnly_channelTopOne"}
		]
    },{
		"icon":"icon-sys",
		"menuid":25,
		"menuname":"数据统计",
		"menus":
		[
		 	{"icon":"icon-nav","menuid":511,"menuname":"发图统计","url":"page_statisticsReadOnly_summary"},
		]
    }
	]};                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    

//初始化左侧
function init() {
	if(getMode() == '2') {
		$.post("./admin/privileges_initNavMenu", function(data) {
			doInit(data);
		},'json');
	} else {
		doInit(result);
	}
}

function getMode() {
	var reg = new RegExp("(^|&)tu=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

function doInit(result) {
	if(result['result'] == 0) {
		navMenus = result['msg'];
		for(var i = 0 ; i < navMenus.length; i++) {
			var n = navMenus[i];
			var menulist ='';
			menulist +='<ul>';
			var subMenus = n.menus;
			for(var k = 0; k < subMenus.length; k++) {
				var o = subMenus[k];
				menulist += '<li><div><a ref="' + o.menuid + '" href="#" rel="' + o.url + '" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.menuname + '</span></a></div></li>';
			}
			menulist += '</ul>';
			$('#nav').accordion('add', {
	            title: n.menuname,
	            content: menulist,
	            iconCls: 'icon ' + n.icon
	        });
		}

	    tabClose();
		tabCloseEven();
		$("#loading").fadeOut(function() {
			$('#tabs').tabs({});
			$("#header,#footer,#indexTab").fadeIn();
			$("#nav").accordion({animate:true});
			$('.easyui-accordion li a').click(function(){
				selectTab($(this));
			}).hover(function(){
				$(this).parent().addClass("hover");
			},function(){
				$(this).parent().removeClass("hover");
			});
			
			selectTab($('.easyui-accordion li a').eq(0));
			
			//选中第一个
			/**
			var panels = $('#nav').accordion('panels');
			if(panels.length > 0) {
				var t = panels[0].panel('options').title;
			    $('#nav').accordion('select', t);
			}
			*/
		});
	}
}
//获取左侧导航的图标
function getIcon(menuid){
	var icon = 'icon ';
	for(var i = 0 ; i < navMenus.length; i++) {
		var n = navMenus[i];
		var subMenus = n.menus;
		for(var k = 0; k < subMenus.length; k++) {
			var o = subMenus[k];
			if(o.menuid == menuid){
				icon += o.icon + ' ';
			}
		}
	}
	return icon;
}

function addTab(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:icon
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	});
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();
		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven()
{
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		});
	});
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	});
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	});
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

function selectTab($tab) {
	var tabTitle = $tab.children('.nav').text();

	var url = $tab.attr("rel");
	var menuId = $tab.attr("ref");
	var icon = getIcon(menuId);

	addTab(tabTitle,url,icon);
	$('.easyui-accordion li div').removeClass("selected");
	$tab.parent().addClass("selected");
}
