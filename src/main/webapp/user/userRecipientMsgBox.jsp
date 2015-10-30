<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String userId=request.getParameter("userId"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小秘书收件箱管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer}"></script>
<link rel="stylesheet" href="${webRootPath }/base/js/jquery/emotion/rl_exp.css" />
<style type="text/css">
body {
	font-size: 14px;
}
.xm-bq{margin:10px 0;font-size:14px;color:#333;}
.xm-bq a{color:#09c;margin:0 5px;}
.xm-bq a:hover{color:#E10602;}
.rl_exp {margin-left:10px; width:624px}
.rl_exp_main {height:130px; overflow-y: scroll;}
.comment-main{
	text-align:left;
}
.comment-main textarea{width:100%;border:1px solid #dcdcdc;}
.comment-main textarea:focus{outline:none;border-color:#4bf;}
.comment-main a{font-size:12px;text-decoration:none;color:#09c;}
.comment-main a:hover{color:#E10602;}

#rl_exp_input {
	width: 650px;
	margin-left:10px;
	margin-top: 15px;
}
#rl_bq {
	text-align: center;
}
.opt_layout {
	position: absolute;
	display: inline-block;
	top: 8px;
	right: 10px;
}

#multi-user-wrap {
	width: 650px;
	margin-left:10px;
	margin-top: 15px;
}

#multi-user {
	width: 600px;
	height:30px;
	margin-left:10px;
	margin-top: 15px;
}


</style>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/emotion/rl_exp.js"></script>
<script type="text/javascript">
var userId = <%=userId%>,
	maxId = 0,
	recordIdKey = "contentId",
	hideIdColumn = true,
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'conver.maxId' : maxId
		},
		loadPageData(1);
	},
	htmTableTitle = "收件箱信息列表", //表格标题
	loadDataURL = "./admin_user/msg_queryConversation",
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['conver.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId >= maxId) {
				maxId = data.maxId;
				myQueryParams['conver.maxId'] = maxId;
			}
		}
	},
	columnsFields = [
		{field : "contentId", title : 'ID'},
		{field : 'phoneCode',title : '客户端',align : 'center',width : 50,
			formatter: function(value,row,index){
				var phone = "IOS";
				if(value == 1) {
					phone = '安卓';
				}
				return "<span class='updateInfo' title='版本号:"+row.ver+" || 系统:"+row.phoneSys+" v"+row.phoneVer+"'>" 
					+ phone + "</span>";
			}
		},
		{field : 'ver', title:'版本号',align:'center', width:60},
		{field : 'userAvatar', title: '头像', alien:'center', width:45,
			formatter: function(value, row, index) {
				uri = 'page_user_userInfo?userId='+row['otherId'],
				imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
				content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
				if(row.star == 1) {
					content = content + "<img title='明星标记' class='avatar_tag' src='./common/images/star_tag.png'/>";
				}
				return "<a title='查看用户信息' class='updateInfo' href='javascript:showUserInfo(\""+uri+"\")'>"+"<span>" + content + "</span>"+"</a>";		
			}	
		},
		{field : 'otherId', title:'用户ID',align:'center', width:60},
		{field : 'userName', title:'昵称',align:'center', width:120},
		{field : 'content', title:'内容',align:'left', width:480},
		{field : 'unreadCount',title : '未读数',align : 'center', width: 45,
			styler: function(value,row,index){
				if (value > 0){
					return 'color:red;font-weight:900;';
				}
			}
		},
		{field : 'opt', title: '操作', align:'center', width:45,
			formatter: function(value, row, index) {
				uri = 'page_user_userMsg?userId='+userId + '&otherId='+row['otherId'] + '&index=' + index;
				return "<a title='回复消息' class='updateInfo' href='javascript:showMsg(\"" + uri+ "\",\"" + index + "\")'>"+"回复"+"</a>";		
			}	
		},
		{field : 'msgDate', title:'消息日期', align : 'center',width : 120,
			formatter: function(value,row,index){
				if(value == null || value == '') {
					return '';
				}
				return new baseTools.parseDate(value).format("yyyy/MM/dd hh:mm"); 
			}	
		}
		],
		
	addWidth = 690; //添加信息宽度
	addHeight = 360; //添加信息高度
	addTitle = "群发小秘书通知", //添加信息标题
	
	htmTablePageList = [10,20,50],
    onBeforeInit = function() {
		showPageLoading();
	},
	
	userMaxId = 0,
	userQueryParams = {
		'maxId':userMaxId
	},
	
	
	multiUserId = 0,
	multiUserMaxId = 0,
	multiUserQueryParams = {
		'maxId':userMaxId
	},
	
	onAfterInit = function() {
		
		$('#ss_userId').combogrid({
			panelWidth : 340,
		    panelHeight : 450,
		    loadMsg : '加载中，请稍后...',
			pageList : [10,20,30],
			pageSize : 10,
			toolbar:"#user_tb",
		    multiple : false,
		   	idField : 'id',
		    textField : 'userName',
		    url : './admin_user/user_queryUser',
		    pagination : true,
		    columns:[[
				userAvatarColumn,
				{field : 'id',title : 'ID',align : 'center', width : 60},
				userNameColumn,
				sexColumn
		    ]],
		    queryParams:userQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > userMaxId) {
						userMaxId = data.maxId;
						userQueryParams.maxId = userMaxId;
					}
				}
		    },
		});
		var p = $('#ss_userId').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					userMaxId = 0;
					userQueryParams.maxId = userMaxId;
				}
			}
		});
		
		$('#multi-user').combogrid({
			panelWidth : 640,
		    panelHeight : 450,
		    loadMsg : '加载中，请稍后...',
			pageList : [10,20,30],
			pageSize : 10,
			toolbar:"#multi-user-tb",
		    multiple : true,
		   	idField : 'id',
		    textField : 'userName',
		    url : './admin_user/user_queryUser',
		    pagination : true,
		    columns:[[
				userAvatarColumn,
				{field : 'id',title : 'ID',align : 'center', width : 60},
				userNameColumn,
				sexColumn
		    ]],
		    queryParams:multiUserQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > multiUserMaxId) {
						multiUserMaxId = data.maxId;
						userQueryParams.maxId = multiUserMaxId;
					}
				}
		    },
		});
		var p = $('#multi-user').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					multiUserMaxId = 0;
					userQueryParams.maxId = multiUserMaxId;
				}
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	
/**
*显示用户信息
*/
function showUserInfo(uri){
	$.fancybox({
		'margin'			: 10,
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

/**
 * 显示消息列表
 */
function showMsg(uri, index) {
	$.fancybox({
		'margin'			: 20,
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri,
		onClosed			: function() {
			updateValue(index, 'unreadCount', 0);
		}
	});
}

/**
 * 发送私信
 */
function sendMsg() {
	var otherId = $("#ss_userId").combogrid('getValue');
	if(otherId == '') {
		$.messager.alert('发送失败','请先选择用户ID，再发私信!','error');		
	} else {
		var uri = 'page_user_userMsg?userId='+userId + '&otherId=' + otherId;
		showURI(uri);
	}
}

/**
 * 搜索用户
 */
function searchUser() {
	userMaxId = 0;
	var userName = $('#ss_user').searchbox('getValue');
	userQueryParams = {
		'maxId' : userMaxId,
		'userName' : userName
	};
	$("#ss_userId").combogrid('grid').datagrid("load",userQueryParams);
}

/**
 * 搜索对话
 */
function searchConver(){
	maxId = 0;
	var otherId = $("#ss_userId").combogrid('getValue');
	var myQueryParams = {
			'conver.maxId':maxId,
			'conver.otherId':otherId
	};
	$("#htm_table").datagrid('load',myQueryParams);
}

/**
 * 重新加载表格
 */
function reload() {
	$("#htm_table").datagrid('reload');
}

/**
 * 给多个用户发私信
 */
function multiMsg() {
	// 打开添加窗口
	$("#htm_add").window('open');
}

/**
 * 搜索用户
 */
function searchMultiUser() {
	multiUserMaxId = 0;
	var userName = $('#ss-multi-user').searchbox('getValue');
	multiUserQueryParams = {
		'maxId' : multiUserMaxId,
		'userName' : userName
	};
	$("#multi-user").combogrid('grid').datagrid("load",multiUserQueryParams);
}

/**
 * 提交消息
 */
function submitMultiMsg() {
	var $form = $('#add_form');
	$form.form('submit', {
	    url:$form.attr("action"),
	    onSubmit: function(){
	    	$("#saveBtn").hide();
			$(".opt_layout .loading").show();
	    	var isValid = $(this).form('validate');
			if (!isValid){
				$("#saveBtn").show();
				$(".opt_layout .loading").hide();
				$.messager.progress('close');	// hide progress bar while the form is invalid
			}
			return isValid;
	    },
	    success:function(data){
	    	$("#saveBtn").show();
			$(".opt_layout .loading").hide();
			var result = $.parseJSON(data);
			if(result['result'] == 0) {
				maxId = 0;
				myQueryParams['conver.maxId'] = maxId;
				reload();
				$('#htm_add').window('close');  //关闭添加窗口
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
	    }
	});
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:multiMsg();" class="easyui-linkbutton" plain="true" title="群发通知" iconCls="icon-add">群发通知</a>
	   		<span class="search_label">用户ID</span>
			<input id="ss_userId" style="width:150px;" />
			<a href="javascript:void(0);" onclick="javascript:searchConver();" class="easyui-linkbutton" title="查询" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
   		</div>
	</div>  
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_user/msg_sendMultiMsg" method="post">
			<div id="multi-user-wrap">
				用户IDs:<input id="multi-user" name="ids" class="easyui-combogrid" data-options="required:true" />
			</div>
			<div id="comment" class="comment-main">
				<textarea name="content" id="rl_exp_input" rows="5" class="easyui-validatebox" data-options="required:true"></textarea>
			</div>
			<div class="rl_exp" id="rl_bq" style="display:none;">
				<ul class="rl_exp_tab clearfix">
					<li><a href="javascript:void(0);" class="selected">emoji</a></li>
				</ul>
				<div class="opt_layout">
					<a id="saveBtn" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitMultiMsg();" title="保存评论">确定</a>
					<span class="loading none">
					<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
					<span style="vertical-align:middle;">请稍后...</span>
					</span>
					<a class="easyui-linkbutton" iconCls="icon-cancel" title="关闭窗口" onclick="$('#htm_add').window('close');">取消</a>
				</div>
				<ul class="rl_exp_main clearfix rl_selected"></ul>
			</div>
			
			<input class="none" type="text" name="userId" id="userId_add" onchange="validateSubmitOnce=true;" value="0"/>
			<input class="none" type="text" name="otherId" id="otherId_add" onchange="validateSubmitOnce=true;" value="0"/>
		</form>
	</div>
	
	<div id="user_tb" style="padding:5px;height:auto" class="none">
		<input id="ss_user" searcher="searchUser" class="easyui-searchbox" prompt="用户名/ID搜索" style="width:200px;"/>
	</div>
	
	<div id="multi-user-tb" style="padding:5px;height:auto" class="none">
		<input id="ss-multi-user" searcher="searchMultiUser" class="easyui-searchbox" prompt="用户名/ID搜索" style="width:200px;"/>
	</div>
	
	</div>
</body>
</html>