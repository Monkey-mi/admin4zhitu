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
			'maxId' : maxId
		},
		loadPageData(1);
	},
	htmTableTitle = "收件箱信息列表", //表格标题
	loadDataURL = "./admin_user/msg_queryRecipientMsgBox?userId="+userId,
	deleteURI = "./admin_user/msg_deleteRecipientMsgBox?userId="+userId+"&ids=", //删除请求地址
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
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'phoneCode',title : '客户端',align : 'center',width : 50,
			formatter: function(value,row,index){
				var sender = row['senderInfo'],
					code = sender['phoneCode'],
					ver = sender['ver'],
					phoneVer = sender['phoneVer'],
					phoneSys = sender['phoneSys'],
					phone = "IOS";
				if(code == 1) {
					phone = '安卓';
				}
				return "<span class='updateInfo' title='版本号:"+ver+" || 系统:"+phoneSys+" v"+phoneVer+"'>" 
					+ phone + "</span>";
			}
		},
		{field : 'ver', title:'版本号',align:'center', width:60,
			formatter: function(value,row,index){
				return row['senderInfo']['ver'];
			}	
		},
		{field : 'userAvatar', title: '头像', alien:'center', width:45,
			formatter: function(value, row, index) {
				uri = 'page_user_userInfo?userId='+row['senderId'],
				imgSrc = baseTools.imgPathFilter(row['senderInfo']['userAvatar'],'../base/images/no_avatar_ssmall.jpg'),
				content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
				if(row['senderInfo']['star'] == 1) {
					content = content + "<img title='明星标记' class='avatar_tag' src='./common/images/star_tag.png'/>";
				}
				return "<a class='updateInfo' href='javascript:void(0);'>"+"<span>" + content + "</span>"+"</a>";		
			}	
		},
		{field : 'userName', title:'昵称',align:'center', width:120, 
			formatter: function(value,row,index){
				return row['senderInfo']['userName'];
			}
		},
		{field : 'content', title:'内容',align:'left', width:480},
		{field : 'state',title : '状态',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(row['ck'] == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已读' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='未读' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
		{field : 'opt', title: '操作', align:'center', width:45,
			formatter: function(value, row, index) {
				uri = 'page_user_userMsg?userId='+userId + '&otherId='+row['senderId'] + '&index=' + index;
				return "<a title='回复消息' class='updateInfo' href='javascript:showMsg(\"" + uri+ "\",\"" + index + "\")'>"+"回复"+"</a>";		
			}	
		},
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
	
	activityMaxSerial = 0,
	activityQueryParams = {
		'maxSerial':activityMaxSerial
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
		
		$('#ids_appmsg').combogrid({
			panelWidth : 340,
		    panelHeight : 280,
		    loadMsg : '加载中，请稍后...',
			pageList : [5,10,20],
			pageSize : 5,
			toolbar:"#appMsgUser_tb",
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
		    queryParams:appMsgUserQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > appMsgUserMaxId) {
						appMsgUserMaxId = data.maxId;
						appMsgUserQueryParams.maxId = appMsgUserMaxId;
					}
				}
		    },
		});
		var p = $('#ids_appmsg').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					appMsgUserMaxId = 0;
					appMsgUserQueryParams.maxId = appMsgUserMaxId;
				}
			}
		});
		
		$('#htm_appmsg').window({
			modal : true,
			width : 520,
			height : 280,
			top: 10,
			title : '推送App消息',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				commonTools.clearFormData($("#appmsg_form"));
				$(".phoneType_appmsg").attr('checked', 'checked');
				$("#property_sys_appmsg").attr('checked', 'checked');
				$('#activityName_appmsg').combogrid('clear');
				$("#ids_appmsg").combogrid('clear');
				$("#tr_worldId_appmsg").hide();
				$("#tr_activityName_appmsg").hide();
				$('#htm_appmsg .opt_btn').show();
				$('#htm_appmsg .loading').hide();
			}
		});
		
		$("#property_sys_appmsg").click(function() {
			$("#worldId_appmsg").val('');
			$('#activityName_appmsg').combogrid('clear');
			$("#tr_worldId_appmsg, #tr_activityName_appmsg").hide();
		});
		
		$("#property_worldId_appmsg").click(function() {
			$("#tr_worldId_appmsg").show();
			$('#activityName_appmsg').combogrid('clear');
			$("#tr_activityName_appmsg").hide();
		});
		
		$("#property_activityName_appmsg").click(function() {
			$("#tr_activityName_appmsg").show();
			$("#worldId_appmsg").val('');
			$("#tr_worldId_appmsg").hide();
		});
		
		$('#activityName_appmsg').combogrid({
			panelWidth:460,
		    panelHeight:300,
		    mode: 'remote',
		    loadMsg:'加载中，请稍后...',
			pageList: [4,10,20],
			pageSize:4,
			editable: false,
		    multiple: false,
		    idField:'activityName',
		    textField:'activityName',
		    url:'./admin_op/op_querySquarePushActivity',
		    queryParams:activityQueryParams,
		    pagination:true,
		    columns:[[
				{field : 'serial',title : '活动序号',align : 'center', width : 60, hidden:true},
		  		{field : 'id',title : '活动ID',align : 'center', width : 60},
		  		{field : 'activityName',title : '活动名称',align : 'center', width : 120},
				{field : 'activityDate',title :'添加日期',align : 'center',width : 150},
				{ field : 'titlePath',title : '封面', align : 'center',width : 90, height:53,
					formatter:function(value,row,index) {
						return "<img width='80px' height='53px' alt='' title='点击编辑' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}	
				}
		    ]],
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxSerial > activityMaxSerial) {
						activityMaxSerial = data.maxSerial;
						activityQueryParams.maxSerial = activityMaxSerial;
					}
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
		'width'				: '10',
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
	.formValidator({onshow:"请输入消息（必填）",onfocus:"2-16个字符",oncorrect:"输入正确！"})
	.inputValidator({min:2,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"请输入2-16个字符"});
	
	$("#worldId_appmsg")
	.formValidator({onshow:"请输入织图id",onfocus:"请输入消息",oncorrect:"输入正确！"});
	
	$("#activityName_appmsg")
	.formValidator({onshow:"请选择活动",onfocus:"请选择活动",oncorrect:"输入正确！"});
	
	$("#ids_appmsg")
	.formValidator({onshow:"请输入用户id（可选）",onfocus:"例如:12,13",oncorrect:"输入正确！"});
	
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" plain="true" title="删除信息" iconCls="icon-cut">删除</a>
			<select id="ss-phoneCode" class="easyui-combobox" style="width:100px;">
		        <option value="">所有客户端</option>
		        <option value="0">IOS</option>
		        <option value="1">Android</option>
	   		</select>
	   		<span class="search_label">用户：</span>
			<input id="ss_userId" style="width:150px;" />
			<a href="javascript:void(0);" onclick="javascript:sendMsg();" class="easyui-linkbutton" plain="true" title="发私信" iconCls="icon-add">发私信</a>
			<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
				<a href="javascript:void(0);" onclick="javascript:initAppMsgWindow();" class="easyui-linkbutton" title="即向全部用户发送通知" iconCls="icon-add">群推送</a>
			</div>
   		</div>
	</div>  
	
	
	<!-- app消息发送窗口 -->
	<div id="htm_appmsg">
		<form id="appmsg_form" action="./admin_user/msg_saveAppMsg" method="post">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">消息：</td>
						<td><textarea name="content" id="content_appmsg" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="content_appmsgTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">指定用户：</td>
						<td><input name="ids" id="ids_appmsg" onchange="validateSubmitOnce=true;" /></td>
						<td class="rightTd"><div id="ids_appmsgTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">平台：</td>
						<td colspan="2">
							<input class="phoneType_appmsg" type="checkbox" name="phoneType" value="IOS" checked="checked" style="width:13px;"/>IOS
							<input class="phoneType_appmsg" type="checkbox" name="phoneType" value="ANDROID" checked="checked" style="width:13px;" />Android
						</td>
					</tr>
					<tr>
						<td class="leftTd">消息属性：</td>
						<td colspan="2">
							<input name="property" type="radio" id="property_sys_appmsg" checked="checked" style="width: 13px;"/>普通
							<input name="property" type="radio" id="property_activityName_appmsg" style="width: 13px;"/>活动
							<input name="property" type="radio" id="property_worldId_appmsg" style="width: 13px;"/>织图
						</td>
					</tr>
					<tr id="tr_worldId_appmsg" class="none">
						<td class="leftTd">织图ID：</td>
						<td><input name="worldId" id="worldId_appmsg" onchange="validateSubmitOnce=true;" /></td>
						<td class="rightTd"><div id="worldId_appmsgTip" class="tipDIV"></div></td>
					</tr>
					<tr id="tr_activityName_appmsg" class="none">
						<td class="leftTd">活动：</td>
						<td><input name="activityName" id="activityName_appmsg" onchange="validateSubmitOnce=true;" /></td>
						<td class="rightTd"><div id="activityName_appmsgTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#appmsg_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_appmsg').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">保存中...</span>
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