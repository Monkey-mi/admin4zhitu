<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String worldId = request.getParameter("worldId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图评论管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js"></script>
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
</style>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/emotion/rl_exp.js"></script>
<script type="text/javascript">
var	maxId = 0,
	worldId = <%=worldId%>,
	init = function() {
		toolbarComponent = '#tb';
		if(worldId != null) {
			myQueryParams.worldId = worldId;
			myQueryParams.maxId = maxId;
		}
		loadPageData(initPage);
	},
	htmTableTitle = "评论列表维护", //表格标题
	htmTableWidth = 980,
	loadDataURL = "./admin_ztworld/interact_queryComment", //数据装载请求地址
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	},
	pageButtons = [],
	columnsFields = [
		{field : 'id',title : 'id',align : 'center',width : 120},
		authorAvatarColumn,
		authorIdColumn,
		{field : 'content', title:'评论内容', align : 'left',width : 660, 
			formatter: function(value,row,index){
				var authorName = "";
				if(row.authorId != 0) {
					if(row.trust == 1) {
						authorName = "<a title='移出信任列表' class='passInfo pointer' href='javascript:removeTrust(\"" + row["authorId"] + "\",\"" + index + "\")'>"+row.authorName+"</a>";
					} else {
						authorName = "<a title='添加到信任列表' class='updateInfo pointer' href='javascript:addTrust(\"" + row["authorId"] + "\",\"" + index + "\")'>"+row.authorName+"</a>";					
					}
				} else if(baseTools.isNULL(value)) {
					authorName = "织图用户";
				}
				return authorName + value;
			}
		},
		{field : 'reply', title : '回复',align : 'center',width : 45,
			formatter: function(value,row,index){
				return "<a title='点击回复评论' class='updateInfo' href='javascript:addReply(\""+ row.id + "\",\""+ row.reAuthorId + "\",\""+ row.authorName + "\")'>" + '回复'+ "</a>";
			}
		},
		{field : 'commentDate', title:'评论日期', align : 'center',width : 80, formatter:dateAddedFormatter},
		{field : 'shield',title : '操作',align : 'center',width : 45,
			formatter: function(value,row,index){
				if(value == 1) {
					img = "./common/images/undo.png";
					return "<img title='解除屏蔽' class='htm_column_img pointer' onclick='javascript:unShield(\""+ row.id + "\",\"" + index + "\")' src='" + img + "'/>";
				}
				if(row.valid == 0) {
					return '';
				}
				return "<a title='点击屏蔽织图' class='updateInfo' href='javascript:shield(\""+ row.id + "\",\"" + index + "\")'>" + '屏蔽'+ "</a>";
			}
		}
		
	],
	addWidth = 690; //添加信息宽度
	addHeight = 360; //添加信息高度
	addTitle = "添加评论信息", //添加信息标题
	
	userMaxId = 0,
	userQueryParams = {
		'maxId':userMaxId
	},
	
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$('#authorId_add').combogrid({
			panelWidth : 340,
		    panelHeight : 250,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#user_tb",
		    multiple : false,
		    required : true,
		   	idField : 'id',
		    textField : 'id',
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
		var p = $('#authorId_add').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					userMaxId = 0;
					userQueryParams.maxId = userMaxId;
				}
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	
//初始化添加窗口
function initAddWindow() {
	$("#rl_exp_input").val('');
	$("#worldId_add").val(worldId);
}

/**
 * 添加评论
 */
function addComment() {
	initAddWindow();
	// 打开添加窗口
	$("#htm_add").window('open');
}

/**
 * 添加回复
 */
function addReply(reId, reAuthorId, authorName) {
	initAddWindow();
	if(reAuthorId != 0) 
		$("#authorId_add").combogrid('setValue', reAuthorId);
	$("#reId_add").val(reId);
	$("#rl_exp_input").val(' @'+authorName+" : ");
	// 打开添加窗口
	$("#htm_add").window('open');
}

function submitComment() {
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
				$.messager.alert('提示',result['msg']);  //提示添加信息成功
				myQueryParams.maxId = 0;
				loadPageData(1); //重新装载第1页数据
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
	    }
	});
}

/**
 * 屏蔽织图
 */
function shield(id,index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/interact_shieldComment",{
		'id':id
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'shield',1);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 解除屏蔽
 */
function unShield(id,index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/interact_unShieldComment",{
		'id':id
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'shield',0);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

function searchUser() {
	userMaxId = 0;
	var userName = $('#ss_user').searchbox('getValue');
	userQueryParams = {
		'maxId' : userMaxId,
		'userName' : userName
	};
	$("#authorId_add").combogrid('grid').datagrid("load",userQueryParams);
}
</script>
</head>
<body>
	<div id="main">
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:addComment();">添加</a>
   		</div>
	</div>  
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_ztworld/interact_saveComment" method="post">
			<div id="comment" class="comment-main">
				<textarea name="content" id="rl_exp_input" rows="5" class="easyui-validatebox" data-options="required:true"></textarea>
			</div>
			<div class="rl_exp" id="rl_bq" style="display:none;">
				<ul class="rl_exp_tab clearfix">
					<li><a href="javascript:void(0);" class="selected">emoji</a></li>
				</ul>
				<div class="opt_layout">
					<input id="authorId_add" name="authorId" style="width:100px;" />
					<a id="saveBtn" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitComment();" title="保存评论">确定</a>
					<span class="loading none">
					<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
					<span style="vertical-align:middle;">请稍后...</span>
					</span>
					<a class="easyui-linkbutton" iconCls="icon-cancel" title="关闭窗口" onclick="$('#htm_add').window('close');">取消</a>
				</div>
				<ul class="rl_exp_main clearfix rl_selected"></ul>
			</div>
			
			<input class="none" type="text" name="reId" id="reId_add" onchange="validateSubmitOnce=true;" value="0"/>
			<input class="none" type="text" name="worldId" id="worldId_add" onchange="validateSubmitOnce=true;" value="0"/>
		</form>
		</div>
		<div id="user_tb" style="padding:5px;height:auto" class="none">
			<input id="ss_user" searcher="searchUser" class="easyui-searchbox" prompt="用户名/ID搜索" style="width:200px;"/>
		</div>
	</div>
</body>
</html>