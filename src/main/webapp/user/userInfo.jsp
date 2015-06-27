<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String userId=request.getParameter("userId"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图用户管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/uploadify/jquery.uploadify.js"></script>
<link rel="stylesheet" type="text/css" href="${webRootPath }/base/js/jquery/uploadify/uploadify.css">
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer}"></script>
<script type="text/javascript">
var userId = <%=userId%>;
var loadOneDataURL = "./admin_user/user_queryUserByUserId?userId="+userId;
var loadAllDataURL = "./admin_user/user_queryUser";
var maxId = 0,
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId
		},
		myLoadDataPage(initPage);
	},
	myRowStyler = function(index, row) {
		if(row.interacted == 1) {
			return 'background-color:#e3fbff';
		}
	},
	htmTableTitle = "织图用户维护", //表格标题
	loadDataURL = userId==null?loadAllDataURL:loadOneDataURL, //数据装载请求地址,若userId为空的时候，就显示全部用户，若userId不为空，则显示该用户
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
		{field : recordIdKey,title : 'id',align : 'center',width : 120},
		phoneCodeColumn,
		userAvatarColumn,
		userIdColumn,
		userNameColumn,
		sexColumn,
		concernCountColumn,
		followCountColumn,
		{field : 'worldCount',title:'织图',align : 'center', width : 60,
			formatter: function(value,row,index){
				var uri = "page_user_userWorldInfo?userId="+row.userId;
				return "<a title='显示织图' class='updateInfo' href='javascript:showUserWorld(\""+uri
					+"\")'>"+value+"</a>"; 
			}
		},
		likedCountColumn,
		keepCountColumn,
		{field : 'signature', title:'签名',align:'center', width:100,editor:'text'},
		userLabelColumn,
		{field : 'beStarRecommend',title:'达人推荐',align:'center',width:60,
			formatter: function(value,row,index){
				img = "./common/images/edit_add.png";
				return "<img title='达人推荐' class='htm_column_img pointer' onclick='javascript:toBeStarRecommend("+row.userId+")' src='" + img + "'/>";
			}	
		},
		{field : 'userrecd',title : '推荐',align : 'center',width : 45,
			formatter: function(value,row,index){
				// 未添加进推荐列表
				if(row.sysAccept == -1) {
					img = "./common/images/edit_add.png";
					return "<img title='添加到推荐列表' class='htm_column_img pointer' onclick='javascript:addUserRecommend(\""+ row.id + "\",\"" + row.ver + "\",\"" + index + "\")' src='" + img + "'/>";
					
				// 一方拒绝
				} else if(row.userAccept == 2 || row.sysAccept == 2) {
					img = "./common/images/cancel.png";
					return "<img title='已拒绝' class='htm_column_img' src='" + img + "'/>";
					
				// 用户未接受，允许撤销
				} else if(row.userAccept == 0 || row.ver < 2.0803) {
					img = "./common/images/undo.png";
					return "<img title='用户还未接受，点击撤销推荐' class='htm_column_img pointer' onclick='javascript:removeUserRecommend(\""+ row.id + "\",\"" + index + "\")'  src='" + img + "'/>";
					
				// 用户已接受
				} else {
					if(row.sysAccept == 0) {
						img = "./common/images/tip.png";
						title = "用户申请，等待审核";
					} else {
						img = row['verifyIcon'];
						title = row['verifyName'];
					}
					return "<img title='" + title + "' class='htm_column_img'  src='" + img + "'/>";
				}
			}
		},
		{field : 'shield',title : '操作',align : 'center',width : 45,
			formatter: function(value,row,index){
				if(value == 1) {
					img = "./common/images/undo.png";
					return "<img title='解除屏蔽' class='htm_column_img pointer' onclick='javascript:unShield(\""+ row[recordIdKey] + "\",\"" + index + "\")' src='" + img + "'/>";
				}
				if(row.valid == 0) {
					return '';
				}
				return "<a title='点击屏蔽织图' class='updateInfo' href='javascript:InitShieldWindow(\""+ row[recordIdKey] + "\",\"" + index + "\")'>" + '屏蔽'+ "</a>";
			}
		},
		{field : 'platformCode',title:'社交平台',align : 'center', width:60, formatter: function(value, row, index) {
			var platformCode = "织图";
			switch(value) {
			case 1:
				platformCode = "微信";
				break;
			case 2:
//				platformCode = "新浪";
				platformCode = "<a href='http://www.weibo.com/u/"+row['loginCode'] + "' target='_blank'>新浪</a>";
				break;
			case 3:
				platformCode = "人人";
				break;
			case 4:
				platformCode = "QQ空间";
				break;
			default:
				break;
			}
			return platformCode;
		}},
		registerDateColumn		
//		{field : 'msg',title : '发消息',align : 'center',width : 45,
//			formatter: function(value,row,index){
//				img = "./common/images/edit_add.png";
//				return "<img title='发消息' class='htm_column_img pointer' onclick='javascript:initMsgWindow(\""+ row.id + "\")' src='" + img + "'/>";
//			}
//		}
	
		],
	htmTablePageList = [50,100,300],
	addWidth = 530; //添加信息宽度
	addHeight = 220; //添加信息高度
	addTitle = "添加用户信息", //添加信息标题,
    onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$("#labelIds_label").combotree({
			url:'./admin_user/label_queryUserLabelTree',
			multiple:true,
			required:true,
			cascadeCheck:false,
			onlyLeafCheck:true,
			onCheck:checkLabel
		});
		$('#htm_delStar').window({
			modal 	: true,
			width	:	400,
			top		:	10,
			height	:	120,
			title	:	'删除明星',
			shadow	:	false,
			closed	: 	true,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			iconCls	:	'icon-cut',
			resizable  :false,
			onClose	: 	function(){
				$("#delStar_userId").val("");
			}
		});
		$('#htm_shieldUser').window({
			modal 	: true,
			width	:	400,
			top		:	10,
			height	:	155,
			title	:	'屏蔽用户',
			shadow	:	false,
			closed	: 	true,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			iconCls	:	'icon-cut',
			resizable  :false,
			onClose	: 	function(){
				$("#comment_userId").val('');
				$("#rowIndex").val('');
			}
		});
		
		$('#htm_label').window({
			modal : true,
			width : 500,
			top : 10,
			height : 190,
			title : '更新标签',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$("#label_form").hide();
				$("#label_loading").show();
				$("#labelCount_label").text('0');
				$("#sex_label").text('');
				$("#userName_label").text('');
				$("#htm_label .opt_btn").show();
				$("#htm_label .loading").hide();
				$("#labelIds_label").combotree('clear');
				$("#labelIds_label").combotree('tree').tree('collapseAll');
			}
		});
		
		$('#htm_uinteract').window({
			modal : true,
			width : 500,
			top : 10,
			height : 220,
			title : '添加用户互动信息',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false
		});
		
		
		$('#htm_msg').window({
			modal : true,
			width : 500,
			height : 160,
			title : '推送消息',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				clearFormData($("#msg_form"));
				$('#htm_msg .opt_btn').show();
				$('#htm_msg .loading').hide();
			}
		});
		
		$('#htm_recommend').window({
			modal : true,
			width : 520,
			top : 10,
			height : 160,
			title : '添加认证',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
			}
		});
		
		$('#verifyId_recommend').combogrid({
			panelWidth : 460,
		    panelHeight : 290,
		    loadMsg : '加载中，请稍后...',
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'verifyName',
		    url : './admin_user/verify_queryAllVerify?addAllTag=false',
		    pagination : false,
		    remoteSort : false,
		    sortName : 'serial',
		    sortOrder : 'desc',
		    columns:[[
				{field : 'id',title : 'ID', align : 'center',width : 60},
				{field : 'verifyIcon',title : '图标', align : 'center',width : 60,
					formatter: function(value,row,index) {
		  				return "<img title='" + row['verifyName'] +  "' class='htm_column_img' src='" + value + "'/>";
		  			}	
				},
		  		{field : 'verifyName', title : '名称', align : 'center',width : 80},
		  		{field : 'verifyDesc', title : '描述', align : 'center',width : 180},
		  		{field : 'serial', title : '序号', align : 'center',width : 60,
		  			sorter:function(a,b){  
		  				return (a>b?1:-1); 
					} 
		  		}
		    ]]
		});
		
		removePageLoading();
		$("#main").show();
	};
	
	
/**
 * 添加到推荐用户
 */
function addUserRecommend(userId, ver, index) {
	$("#userId_recommend").val(userId);
	$("#verifyId_recommend").combogrid('setValue', 1);
	loadUserRecommendValidate(index);
	$("#htm_recommend").window('open');
	
}

function loadUserRecommendValidate(index) {
	var $form = $('#recommend_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				$('#htm_recommend .opt_btn').hide();
				$('#htm_recommend .loading').show();
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$.post($form.attr("action"),$form.serialize(),
					function(result){
						formSubmitOnce = true;
						$('#htm_recommend .opt_btn').show();
						$('#htm_recommend .loading').hide();
						if(result['result'] == 0) {
							$('#htm_recommend').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData($form);  //清空表单数据
							$("#htm_table").datagrid('reload');
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");
				return false;
			}
		}
	});
	
	$("#verifyId_recommend")
	.formValidator({empty:false,onshow:"请选择认证类型",oncorrect:"设置成功"});
}


/**
 * 从推荐用户移除
 */
function removeUserRecommend(userId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_op/user_deleteRecommendUserByUserId",{
		'userId':userId
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'sysAccept',-1);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	clearFormData(addForm);
	$("#userName_add").focus();  //光标定位
	
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	$("#userName_add").focus();  //光标定位
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							myQueryParams.maxId = 0;
							myLoadDataPage(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");
				return false;
			}
		}
	});
	
	$("#userName_add").formValidator({onshow:"请输入用户名字",onfocus:"例如:lynch",oncorrect:"该名字可用！"})
	.inputValidator({min:2,max:20,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由汉字及字母组成"})
	.ajaxValidator({type:"post",url:"./admin_user/./admin_user/user_checkUserNameExists",datatype:"json",
		beforesend:function(){
			if(validateSubmitOnce==true){
				validateSubmitOnce=false;
			}else{
				return false;
			}
		},
		success:function(json){
			if(json['result']==0){
				return true;
			}else{
				return false;
			}
		},
		error:function(){alert("连接错误，请重试...");},onerror:"该用户名已被使用",
		onwait:"正在校验用户名，请稍候..."});
	
	$("#loginCode_add").formValidator({onshow:"请输入邮箱账号",onfocus:"例:123456",oncorrect:"该账号可用！"})
	.inputValidator({min:4,max:50,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"至少大于4个字符"})
	.functionValidator({fun:cheEmail, onerror:"邮箱格式错误"})
	.ajaxValidator({type:"post",url:"./admin_user/./admin_user/user_checkLoginCodeExists",datatype:"json",
		beforesend:function(){
			if(validateSubmitOnce==true){
				validateSubmitOnce=false;
			}else{
				return false;
			}
		},
		success:function(json){
			if(json['result']==0){
				return true;
			}else{
				return false;
			}
		},
		error:function(){alert("连接错误，请重试...");},onerror:"该用账号已被使用",
		onwait:"正在校验..."});
	
	$("#userAvatar_add")
	.formValidator({onshow:"请输入头像链接",onfocus:"例:http://test.png",oncorrect:"该链接可用！"})
	.inputValidator({min:2,max:150,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"链接错误"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#userAvatarL_add")
	.formValidator({onshow:"请输入头像链接",onfocus:"例:http://test.png",oncorrect:"该链接可用！"})
	.inputValidator({min:2,max:150,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"链接错误"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
}

function cheEmail(e){
    var RegExp = /^\w+@\w+.\w+.?\w+$/; //我这里只做了简单的验证高级验证请自行补全
    if(!RegExp.test(e)) {
        return false;
    }
    return true;
}

function search() {
	maxId = 0;
	var userName = $('#ss').searchbox('getValue');
	myQueryParams = {
		'maxId' : maxId,
		'userName' : userName
	};
	$("#htm_table").datagrid("load",myQueryParams);
}

function myLoadDataPage(pageNum){
	$('#htm_table').datagrid({
		title: htmTableTitle,
		iconCls: htmTableIcon,
		width: $(document.body).width(),
		fitColumns: true,
		autoRowHeight: true,
		url:loadDataURL, //加载数据的URL
		rowStyler:myRowStyler,
		pageList: htmTablePageList,
		onClickCell:onClickCell,
		nowrap: isNowrap,
		striped: isStriped,
		collapsible: isCollapsible,
		sortName: mySortName,
		queryParams:myQueryParams,
		sortOrder: mySortOrder,
		remoteSort: isRemoteSort,
		idField: myIdField,
		rownumbers: isRowNumbers,
		columns:[columnsFields],
		loadMsg: myLoadMsg,
		toolbar: toolbarComponent,
		pagination: isPagination,
		pageNumber: pageNum, //指定当前页面为1
		pageSize: myPageSize,
		onBeforeLoad : myOnLoadBefore,
		onLoadSuccess : myOnLoadSuccess,
		onCheck : myOnCheck,
		onCheckAll : myOnCheckAll
	});
	if(hideIdColumn) {
		$('#htm_table').datagrid('hideColumn', recordIdKey); //隐藏id字段
	}
	var p = $('#htm_table').datagrid('getPager');
	p.pagination({
		beforePageText : "页码",
		afterPageText : '共 {pages} 页',
		displayMsg: '第 {from} 到 {to} 共 {total} 条记录',
		buttons : [{
	        iconCls:'icon-save',
	        text:'保存',
	        handler:function(){
	        	endEditing();
	        	var rows = $("#htm_table").datagrid('getChanges', "updated"); 
	        	var updateSignatureURL = "./admin_user/user_updateSignature";
	        	$("#htm_table").datagrid('loading');
	        	$.post(updateSignatureURL,{
	        		'userInfoJSON':JSON.stringify(rows)
	        		},function(result){
	        			if(result['result'] == 0) {
	        				$("#htm_table").datagrid('acceptChanges');
	        			} else {
	        				$.messager.alert('失败提示',result['msg']);  //提示失败信息
	        			}
	        			$("#htm_table").datagrid('loaded');
	        		},"json");
	        }
	    },{
	        iconCls:'icon-undo',
	        text:'取消',
	        handler:function(){
	        	$("#htm_table").datagrid('rejectChanges');
	        	$("#htm_table").datagrid('loaded');
	        }
	    }],
		onBeforeRefresh:myOnBeforeRefresh
	});
}

//初始化添加窗口
function initMsgWindow(userId) {
	$("#ids_msg").val(userId);
	$("#content_msg").focus();  //光标定位
	loadMsgFormValidate();
	$('#htm_msg').window('open');
	
}

//显示用户织图
function showUserWorld(uri){
	$.fancybox({
		'margin'			: 20,
		'width'				: '10',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

//提交表单，以后补充装载验证信息
function loadMsgFormValidate() {
	var form = $('#msg_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				$('#htm_msg .opt_btn').hide();
				$('#htm_msg .loading').show();
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$.post(form.attr("action"),form.serialize(),
					function(result){
						$('#htm_msg .opt_btn').show();
						$('#htm_msg .loading').hide();
						if(result['result'] == 0) {
							$('#htm_msg').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#content_msg")
	.formValidator({onshow:"请输入消息",onfocus:"请输入消息",oncorrect:"输入正确！"})
	.inputValidator({min:2,max:150,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"输入错误"});
	
}


	function delStar(){
		$("#htm_delStar").window('open');
	}
	
	function delStarSubmit(){
		var delStarTable = $("#delStar_form");
		$('#delStar_form .opt_btn').hide();
		$('#delStar_form .loading').show();
		$.post(delStarTable.attr("action"),delStarTable.serialize(),function(result){
			$('#delStar_form .opt_btn').show();
			$('#delStar_form .loading').hide();
			if(result['result'] == 0){
				$.messager.alert('提示',result['msg']);
			}else{
				$.messager.alert('错误提示',result['msg']);
			}
			return false;
		});
		$("#htm_delStar").window('close');
	}

	function InitShieldWindow(userId,index){
		$("#comment_userId").val(userId);
		$("#rowIndex").val(index);
		$("#htm_shieldUser").window('open');
	}
	
	/**
	 * 屏蔽用户
	 */
	function shield() {
		$("#htm_table").datagrid('loading');
		var userId = $("#comment_userId").val();
		var index = $("#rowIndex").val();
		//屏蔽用户评论
		if($("#commentShield").attr('checked') == 'checked'){
			$.post("./admin_ztworld/interact_shieldCommentByUserId",{
				'authorId':userId
				},function(result){
					if(result['result'] == 0) {
						
					} else {
						$.messager.alert('失败提示',result['msg']);  //提示失败信息
					}
				},"json");
		}
		
		//屏蔽用户
		$.post("./admin_user/user_shieldUser",{
			'userId':userId
			},function(result){
				if(result['result'] == 0) {
					updateValue(index,'shield',1);
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
				$("#htm_table").datagrid('loaded');
			},"json");
		$("#htm_shieldUser").window('close');
	}

	/**
	 * 解除屏蔽
	 */
	function unShield(userId,index) {
		$("#htm_table").datagrid('loading');
		$.post("./admin_user/user_unShieldUser",{
			'userId':userId
			},function(result){
				if(result['result'] == 0) {
					updateValue(index,'shield',0);
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
				$("#htm_table").datagrid('loaded');
			},"json");
	}

	function toBeStarRecommend(userId){
		$.post("./admin_op/starRecommend_addStarRecommend",{
			'userId':userId
		},function(result){
			$.messager.alert('提示',result['msg']);
		},"json");
	}
</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<!-- <a href="#" class="easyui-linkbutton" style="visibility: hidden;" iconCls="icon-add" plain="true" onclick="javascript:htmWindowAdd();">添加</a>-->
	   		<input id="ss" class="easyui-searchbox" searcher="search" prompt="请输入昵称/ID搜索用户" style="width:200px;"></input>
	   		<a href="javascript:void(0);" onclick="javascript:delStar();" class="easyui-linkbutton" title="删除明星" plain="true" iconCls="icon-cut" id="delStar">删除明星</a>
   		</div>
	</div>  
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_user/user_saveUser" method="post">
			<table id="htm_edit_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">用户名：</td>
						<td><input type="text" name="userName" id="userName_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userName_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">邮箱：</td>
						<td><input type="text" name="loginCode" id="loginCode_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="loginCode_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">头像：</td>
						<td><input type="text" name="userAvatar" id="userAvatar_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userAvatar_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">大头像：</td>
						<td><input type="text" name="userAvatarL" id="userAvatarL_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userAvatarL_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

	<!-- 用户标签设置 -->	
	<div id="htm_label">
		<span id="label_loading" style="margin:60px 0 0 220px; position:absolute; display:none; ">加载中...</span>
		<form id="label_form" action="./admin_user/label_saveLabelUser" method="post" class="none">
			<table class="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">用户名：</td>
						<td colspan="2">
							<span id="userName_label"></span>
						</td>
					</tr>
					<tr>
						<td class="leftTd">性别：</td>
						<td colspan="2">
							<span id="sex_label"></span>
						</td>
					</tr>
					<tr>
						<td class="leftTd">标签：</td>
						<td>
							<select id="labelIds_label" name="labelId" style="width:205px;" ></select>
						</td>
						<td class="rightTd">
						已选择标签：<span id="labelCount_label">0</span>/5
						<a class="easyui-linkbutton" style="vertical-align: middle; margin-bottom: 3px;" title="清空标签" plain="true" iconCls="icon-remove" onclick="clearLabelSelect();"></a>
						</td>
						
					</tr>
					<tr class="none">
						<td class="leftTd">指定用户：</td>
						<td colspan="2">
							<input id="userId_label" name="userId" type="text" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitLabelForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_label').window('close');">取消</a>
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
	
	<!-- 添加记录 -->
	<div id="htm_uinteract">
		<span id="uinteract_loading" style="margin:70px 0 0 220px; position:absolute; display:none;  ">加载中...</span>
		<form id="uinteract_form" action="./admin_interact/interact_saveUserInteract" method="post" class="none">
			<table class="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">已添加：</td>
						<td colspan="2">粉丝【<span id="followSum_uinteract">0</span>】</td>
					</tr>
					<tr>
						<td class="leftTd">粉丝数：</td>
						<td><input type="text" name="followCount" id="followCount_uinteract" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="followCount_uinteractTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">为期：</td>
						<td><input id="duration_uinteract" name="duration"  /></td>
						<td class="rightTd"><div id="duration_uinteractTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">用户ID：</td>
						<td><input type="text" name="userId" id="userId_uinteract" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userId_uinteractTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#uinteract_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_uinteract').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 向单个用户发送消息 -->
	<div id="htm_msg">
		<form id="msg_form" action="./admin_user/msg_saveSysMsg" method="post">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr class="none">
						<td class="leftTd">用户Id：</td>
						<td><input type="text" name="ids" id="ids_msg" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"></td>
					</tr>
					<tr>
						<td class="leftTd">消息：</td>
						<td><textarea name="content" id="content_msg" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="content_msgTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#msg_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_msg').window('close');">取消</a>
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
	
	<!-- 推荐用户 -->
	<div id="htm_recommend">
		<form id="recommend_form" action="./admin_op/user_saveRecommendUser" method="post">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">用户Id：</td>
						<td><input type="text" name="userId" id="userId_recommend" readonly="readonly" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"></td>
					</tr>
					<tr>
						<td class="leftTd">认证类型：</td>
						<td><textarea name="verifyId" id="verifyId_recommend" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="verifyId_recommendTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#recommend_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_recommend').window('close');">取消</a>
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
	
	<!-- 删除明星 -->
	<div id="htm_delStar">
		<form id="delStar_form" action="./admin_op/user_delStar" method="post">
			<table id="htm_delStar_table" width="380">
				<tbody>
					<tr>
						<td class="leftTd">用户id：</td>
						<td><input id="delStar_userId" name="userId" class="easyui-numberbox"></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align:center;padding-top:10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="delStarSubmit();">删除</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#delStar_form').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">删除中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 屏蔽用户-->
	<div id="htm_shieldUser">
		<form id="shieldUser_form" method="post">
			<table id="htm_shieldUser_table" width="380">
				<tbody>
					<tr>
						<td colspan="2" style="padding: 10px 0 10px 0"><span style="padding-left:130px;">确定要屏蔽该用户么？</span></td>
					</tr>
					<tr>
						<td colspan="2"><span style="padding-left:100px"><input type="checkbox"  id="commentShield" style="width: 13px; height: 13px; vertical-align: middle;"/>同时屏蔽该用户所有评论</span></td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text"  id="comment_userId" value="0" /></td>
					</tr>
					<tr class="none">
						<td colspan="2"><input id="rowIndex" /></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align:center;padding-top:10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="shield();">删除</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_shieldUser').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">删除中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	</div>
</body>
</html>