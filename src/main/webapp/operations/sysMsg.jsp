<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String userId=request.getParameter("userId"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收件箱管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer}"></script>
<script type="text/javascript">
var userId = <%=userId%>;
var maxId = 0,
	recordIdKey = "senderId",
	hideIdColumn = false,
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'conver.maxId' : maxId
		},
		loadPageData(1);
	},
	htmTableTitle = "收件箱信息列表", //表格标题
	loadDataURL = "./admin_user/msg_queryRecipientMsgBox?conver.userId="+userId,
	deleteURI = "./admin_user/msg_deleteRecipientMsgBox?userId="+userId+"&ids=", //删除请求地址
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId >= maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	},
	columnsFields = [
		{field : 'ck',checkbox : true},
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
		{field : 'unreadCount',title : '未读数',align : 'center', width: 45},
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
	htmTablePageList = [10,20,50],
    onBeforeInit = function() {
		showPageLoading();
	},
	
	userMaxId = 0,
	userQueryParams = {
		'maxId':userMaxId
	},
	
	appMsgUserMaxId = 0,
	appMsgUserQueryParams = {
		'maxId':appMsgUserMaxId
	},
	
	onAfterInit = function() {
		$('#ss-phoneCode').combobox({
			editable : false,
			onSelect : function(rec) {
				myQueryParams.phoneCode = rec.value;
				loadPageData(1);
			}
		});
		
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
		
		$('#htm_appmsg').window({
			modal : true,
			width : 820,
			height : 520,
			top: 10,
			title : '推送App消息',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false
			/*
			onClose : function() {
				clearFormData($("#appmsg_form"));
				$(".notice_appmsg").attr('checked', 'checked');
				$("#tr_link_appmsg").hide();
				$("#objType_appmsg").combobox('setValue',4);
				$('#htm_appmsg .opt_btn').show();
				$('#htm_appmsg .loading').hide();
			}
		*/
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
	showURI(uri);
}

/**
 * 更新消息状态
 */
function updateMsgState(index) {
	updateValue(index, 'ck', '1');
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

function searchUser() {
	userMaxId = 0;
	var userName = $('#ss_user').searchbox('getValue');
	userQueryParams = {
		'maxId' : userMaxId,
		'userName' : userName
	};
	$("#ss_userId").combogrid('grid').datagrid("load",userQueryParams);
}

function searchAppMsgUser() {
	appMsgUserMaxId = 0;
	var userName = $('#ss_appMsgUser').searchbox('getValue');
	console.log(userName);
	appMsgUserQueryParams = {
		'maxId' : appMsgUserMaxId,
		'userName' : userName
	};
	$("#ids_appmsg").combogrid('grid').datagrid("load", appMsgUserQueryParams);
}


//初始化添加窗口
function initAppMsgWindow() {
	$("#content_appmsg").focus();  //光标定位
	loadAppMsgFormValidate();
	$('#htm_appmsg').window('open');
	
}

//提交表单，以后补充装载验证信息
function loadAppMsgFormValidate() {
	var form = $('#appmsg_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				$('#htm_appmsg .opt_btn').hide();
				$('#htm_appmsg .loading').show();
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$.post(form.attr("action"),form.serialize(),
					function(result){
						formSubmitOnce = true;
						$('#htm_appmsg .opt_btn').show();
						$('#htm_appmsg .loading').hide();
						if(result['result'] == 0) {
							$('#htm_appmsg').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#content_appmsg")
	.formValidator({onshow:"请输入消息（必填）",onfocus:"2-140个字符",oncorrect:"输入正确！"})
	.inputValidator({min:2,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"请输入2-140个字符"});
	
	$("#uids-str")
	.formValidator({onshow:"请输入接受者Id",onfocus:"Id之间英文逗号分隔，例如：123,124,125",oncorrect:"输入正确！"});
}

function appmsgObjTypeSelect(rec){
	$("#tr_link_appmsg").hide();
	switch(parseInt(rec.value)) {
	case 9:
		$("#tr_link_appmsg").show();
		break;
	default:
		break;
	}
}

function searchBySenderId(){
	var senderId = $("#ss_senderId").val();
//	maxId = 0;
	var myQueryParams = {
			'maxId':maxId,
			'senderId':senderId
	};
	$("#htm_table").datagrid('load',myQueryParams);
}

function reload() {
	$("#htm_table").datagrid('reload');
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<select id="ss-phoneCode" class="easyui-combobox" style="width:100px;">
		        <option value="">所有客户端</option>
		        <option value="0">IOS</option>
		        <option value="1">Android</option>
	   		</select>
	   		<span class="search_label">用户ID：</span>
			<input id="ss_userId" style="width:150px;" />
			<a href="javascript:void(0);" onclick="javascript:sendMsg();" class="easyui-linkbutton" plain="true" title="发私信" iconCls="icon-add">发私信</a>
			<input id="ss_senderId" style="width:150px;"/>
			<a href="javascript:void(0);" onclick="javascript:searchBySenderId();" class="easyui-linkbutton" title="查询" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
			<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
				<a href="javascript:void(0);" onclick="javascript:initAppMsgWindow();" class="easyui-linkbutton" title="即向全部用户发送通知" iconCls="icon-add">群推送</a>
			</div>
   		</div>
	</div>  
	
	
	<!-- app消息发送窗口 -->
	<div id="htm_appmsg">
		<form id="appmsg_form" action="./admin_op/msg_saveAppMsg" method="post">
			<table id="htm_edit_table" width="780">
				<tbody>
					<tr>
						<td class="leftTd">内容：</td>
						<td><textarea name="sysMsg.content" id="content_appmsg" onchange="validateSubmitOnce=true;" style="width:500px;height:100px;"></textarea></td>
						<td class="rightTd"><div id="content_appmsgTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">提醒位置：</td>
						<td colspan="2">
							<input class="notice_appmsg" type="checkbox" name="notice" value="inApp" checked="checked" style="width:13px;"/>系统通知列表
							<input class="notice_appmsg" type="checkbox" name="notice" value="notify" checked="checked" style="width:13px;" />通知栏
						</td>
					</tr>
					<tr>
						<td class="leftTd">接收用户：</td>
						<td >
							<textarea type="text" name="uidsStr" id="uids-str" style="width:500px;"></textarea>
						</td>
						<td class="rightTd"><div id="uids-strTip" class="tipDIV"></div></td>
					</tr>
					
					<tr>
						<td class="leftTd">消息类型：</td>
						<td colspan="2">
							<select id="objType_appmsg" name="sysMsg.objType" class="easyui-combobox" 
								 style="width:100px;">
						        <option value="4">普通文本</option>
						        <option value="9">链接</option>
						        <option value="8">频道</option>
						        <option value="2">织图推荐</option>
						        <option value="10">明星推荐</option>
						        <option value="11">活动推荐</option>
					   		</select>
						</td>
					</tr>
					<tr>
						<td class="leftTd">缩略图路径：</td>
						<td>
							<textarea name="sysMsg.thumbPath" style="width:500px;"></textarea>
						</td>
					</tr>
					<tr>
						<td class="leftTd">objMeta：</td>
						<td>
							<textarea name="sysMsg.objMeta" style="width:500px;"></textarea>
						</td>
					</tr>
					
					<tr>
						<td class="leftTd">objMeta2：</td>
						<td>
							<input type="text" name="sysMsg.objMeta2"/>
						</td>
					</tr>
					
					<tr>
						<td class="leftTd">objId：</td>
						<td>
							<input type="text" name="sysMsg.objId"/>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#appmsg_form').submit();">发送</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_appmsg').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">发送中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<div id="user_tb" style="padding:5px;height:auto" class="none">
		<input id="ss_user" searcher="searchUser" class="easyui-searchbox" prompt="用户名/ID搜索" style="width:200px;"/>
	</div>
	
	<div id="appMsgUser_tb" style="padding:5px;height:auto" class="none">
		<input id="ss_appMsgUser" searcher="searchAppMsgUser" class="easyui-searchbox" prompt="用户名/ID搜索" style="width:200px;"/>
	</div>
	
	</div>
</body>
</html>