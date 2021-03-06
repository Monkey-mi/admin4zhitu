<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	String userId = request.getParameter("userId"); 
    	String otherId = request.getParameter("otherId");
    	String index = request.getParameter("index");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>对话列表</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
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

#keep-conver {
	vertical-align: middle;
}

</style>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/emotion/rl_exp.js"></script>
<script type="text/javascript">
var userId = <%=userId%>,
	otherId = <%=otherId%>,
	reply = false,
	maxId = 0,
	isNowrap=false,//自动换行
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId,
			'userId' : userId,
			'otherId' : otherId
		},
		loadPageData(1);
	},
	htmTableTitle = "对话列表", //表格标题
	loadDataURL = "./admin_user/msg_queryMsg",
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	},
	myRowStyler = function(index, row) {
		if(row['senderId'] == userId) {
			return 'background-color:#e3fbff;';
		}
	},
	columnsFields = [
		{field : recordIdKey,title : 'id',align : 'center',width : 60},
		{field : 'otherAvatar', title: '', alien:'center', width:40,
			formatter: function(value, row, index) {
				uid = row['senderId'];
				if(uid == otherId) {
					uri = 'page_user_userInfo?userId='+uid,
					imgSrc = baseTools.imgPathFilter(row['senderInfo']['userAvatar'],'../base/images/no_avatar_ssmall.jpg'),
					content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
					if(row['senderInfo']['star'] == 1) {
						content = content + "<img title='明星标记' class='avatar_tag' src='./common/images/star_tag.png'/>";
					}
					return "<a title='查看用户信息' class='updateInfo' href='javascript:showUserInfo(\""+uri+"\")'>"+"<span>" + content + "</span>"+"</a>";
				}
				return '';
			}
		},
		{field : 'content', title:'',align:'left', width:759, height: 100 ,
			styler: function(value,row,index){
				uid = row['senderId'];
				if (uid == userId){
					return 'text-align:right !important;';
				}
			}	
		},
		{field : 'userAvatar', title: '', alien:'center', width:40,
			formatter: function(value, row, index) {
				uid = row['senderId'];
				if(uid == userId) {
					uri = 'page_user_userInfo?userId='+uid,
					imgSrc = baseTools.imgPathFilter(row['senderInfo']['userAvatar'],'../base/images/no_avatar_ssmall.jpg'),
					content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
					if(row['senderInfo']['star'] == 1) {
						content = content + "<img title='明星标记' class='avatar_tag' src='./common/images/star_tag.png'/>";
					}
					return "<a title='查看用户信息' class='updateInfo' href='javascript:showUserInfo(\""+uri+"\")'>"+"<span>" + content + "</span>"+"</a>";
				}
				return '';
			}
		},
		{field : 'msgDate',title:'日期',algin:'center',width:100}
		],
	htmTablePageList = [10,20,50],
	addWidth = 690; //添加信息宽度
	addHeight = 360; //添加信息高度
	addTitle = "回复", //添加信息标题
	
    onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		removePageLoading();
		$("#main").show();
	};
	
/**
*显示用户信息
*/
function showUserInfo(uri){
	$.fancybox({
		'margin'			: 20,
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

//初始化添加窗口
function initAddWindow() {
	$("#rl_exp_input").val('');
	$("#userId_add").val(userId);
	$("#otherId_add").val(otherId);
}

/**
 * 发送私信
 */
function sendMsg() {
	initAddWindow();
	// 打开添加窗口
	$("#htm_add").window('open');
}

/**
 * 提交消息
 */
function submitMsg() {
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
				$('#htm_add').window('close');  //关闭添加窗口
				parent.reload();
				parent.$.fancybox.close();
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
			<a href="javascript:void(0);" onclick="javascript:sendMsg();" class="easyui-linkbutton" plain="true" title="回复" iconCls="icon-add">回复</a>
   		</div>
	</div>  
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_user/msg_sendMsg" method="post">
			<div id="comment" class="comment-main">
				<textarea name="content" id="rl_exp_input" rows="5" class="easyui-validatebox" data-options="required:true"></textarea>
			</div>
			<div class="rl_exp" id="rl_bq" style="display:none;">
				<ul class="rl_exp_tab clearfix">
					<li><a href="javascript:void(0);" class="selected">emoji</a></li>
				</ul>
				<div class="opt_layout">
					<input id="keep-conver" type="checkbox" name="keep"　style="margin-top:2px;" />保留对话
					<a id="saveBtn" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitMsg();" title="保存评论">确定</a>
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

	</div>
</body>
</html>