<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推荐用户管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer} "></script>
<script type="text/javascript">
var maxId = 0,
	notifyIndex = 0,
	acceptTipHead = '恭喜!您被推荐为',
	acceptTipFoot = '了!',
	passTipHead = '恭喜!您被推荐为',
	passTipFoot = '，赶快到广场查看吧!',
	rejectTipHead = '抱歉,您还未满足',
	rejectTipFoot = '条件,小织暂时还不能通过您的申请',
	batchAcceptTip = "您确定要通过已选中的用户吗?",
	batchRejectTip = "您确定要拒绝以选中的用户吗?",
	init = function() {
		myQueryParams = {
			'maxId' : maxId
		},
		loadPageData(initPage);
	},
	myRowStyler = function(index, row) {
		if(row.interacted == 1) {
			return 'background-color:#e3fbff';
		}
	},
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
	htmTableTitle = "推荐用户列表", //表格标题
	toolbarComponent = '#tb',
	recordIdKey = "id",
	uidKey = "userId",
	loadDataURL = "./admin_op/user_queryRecommendUser", //数据装载请求地址
	deleteURI = "./admin_op/user_deleteRecommendUsers?ids=", //删除请求地址
	updateSysAcceptURL = "./admin_op/user_updateRecommendSysAccept?ids=", //更新请求地址
	updatePageURL = "./admin_op/user_updateRecommendUserByJSON", // 更新推荐用户信息URL
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : 'id',align : 'center',width : 120},
		phoneCodeColumn,
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
		userAvatarColumn,
		userIdColumn,
		userNameColumn,
		sexColumn,
		userLabelColumn,
		concernCountColumn,
		followCountColumn,
		{field : 'worldCount',title:'织图',align : 'center', width : 60,
			formatter: function(value,row,index){
				var uri = "page_user_userWorldInfo?userId="+row.userId;
				return "<a title='显示织图' class='updateInfo' href='javascript:showUserWorld(\""+uri
					+"\")'>"+value+"</a>"; 
			}
		},
		{field : 'verifyName',title : '认证名称',align : 'center', width : 80, 
			formatter : function(value, row, index ) {
				return "<href title='点击更新认证类型' class='updateInfo pointer' onclick='updateVerify(\""+ row.id + "\",\"" + row.verifyId + "\",\"" + index + "\")'>"+value+"</span>";
			}		
		},
		{field : 'recommendDesc',title : '推荐描述',align : 'center', width : 180, editor:'textarea', 
			formatter : function(value, row, rowIndex ) {
				return "<span title='" + value + "' class='updateInfo' >"+value+"</span>";
			}			
		},
		{field : 'notified',title : '通知状态',align : 'center', width : 60,
			formatter: function(value,row,index) {
  				if(value >= 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已经通知，点击再次发送' class='htm_column_img pointer' onclick='notify(\"" + index + "\")' src='" + img + "'/>" + "+" + value;
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中，点击通知' class='htm_column_img pointer' onclick='notify(\"" + index + "\")' src='" + img + "'/>";
  			}
		},
		{field : 'userAccept',title : '用户状态',align : 'center', width : 60,
			formatter: function(value,row,index) {
				
  				if(value == 2) {
  					img = "./common/images/cancel.png";
  					title = "已拒绝";
  					return "<img title='" + title +  "' class='htm_column_img' src='" + img + "'/>";
  				} else if(value == 0){
  					img = "./common/images/tip.png";
  					title = "等待中";
  					return "<img title='" + title +  "' class='htm_column_img' src='" + img + "' onclick='userAcceptDirect(\"" +row['userId'] + "\",\"" + row['verifyId'] + "\",\"" + index + "\")'/>";
  				}else{
  					title = '已接受',
  					img = "./common/images/ok.png";
  					return "<img title='" + title +  "' class='htm_column_img' src='" + img + "'/>";
  				}
  				
  			}	
		},
		{field : 'sysAccept',title : '系统状态',align : 'center', width : 60,
			formatter: function(value,row,index) {
				title = '已接受',
				img = "./common/images/ok.png";
  				if(value == 2) {
  					img = "./common/images/cancel.png";
  					title = "已拒绝";
  				} else if(value == 0) {
  					img = "./common/images/tip.png";
  					title = "等待中";
  				}
  				return "<img title='" + title +  "' class='htm_column_img' src='" + img + "'/>";
  			}	
		},
		{field : 'weight',title : '置顶状态',align : 'center', width : 60,
			formatter: function(value,row,index) {
				img = "./common/images/edit_add.png";
  				title = "点击置顶";
  				if(value > 0) {
  					img = "./common/images/undo.png";
  					title = "已置顶，数字越大越靠前，点击撤销";
  					return "<img title='" + title +  "' class='htm_column_img pointer' onclick='updateWeight(\"" + index + "\",\"" + row[recordIdKey] + "\",\"" + false + "\")' src='" + img + "'/>+"+value;
  				}
  				return "<img title='" + title +  "' class='htm_column_img pointer' onclick='updateWeight(\"" + index + "\",\"" + row[recordIdKey] + "\",\"" + true + "\")' src='" + img + "'/>";
  			}
		},
		{field : 'recommender',title : '推荐人',align : 'center', width : 55}
		],
	addWidth = 520, //添加信息宽度
	addHeight = 130, //添加信息高度
	addTitle = "添加推荐用户", //添加信息标题
	
	verifyMaxSerial = 0,
	verifyQueryParams = {
		'maxSerial':0
	},
	pageButtons = [{
        iconCls:'icon-save',
        text:'保存',
        handler:function(){
        	endEditing();
        	var rows = $("#htm_table").datagrid('getChanges', "updated"); 
        	$("#htm_table").datagrid('loading');
        	$.post(updatePageURL,{
        		'userJSON':JSON.stringify(rows)
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
    },{
    	iconCls:'icon-add',
    	text:'添加到用户池',
    	handler:function(){
    		var rows = $("#htm_table").datagrid('getSelections');
    		if(isSelected(rows)){
    			for(var i=0; i<rows.length;i++){
    				var row = rows[i];
    				$.post("./admin_op/chuser_addChannelUserByVerifyId",{
    					'userId':row['userId'],
    					'verifyId':row['verifyId']
    				},function(result){
    					
    				},"json");
    			}
    			$("#htm_table").datagrid('clearSelections');
    			$.messager.alert("添加完毕","添加完毕");
    		}
    	}
    }],
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
		
		$('#htm_label').window({
			modal : true,
			width : 520,
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
		
		$('#htm_indexed').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 215,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
		});
		
		$('#htm_notify').window({
			title : '通知用户',
			modal : true,
			width : 360,
			height : 225,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false
		});
		
		$('#htm_uinteract').window({
			modal : true,
			width : 520,
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
		
		$('#htm_delete').window({
			modal : true,
			width : 320,
			height : 190,
			title : '删除推荐',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			resizable : false,
			onClose : function() {
				$('#htm_delete .opt_btn').show();
				$('#htm_delete .loading').hide();
				$("#star_delete").removeAttr('checked');
				$("#insert_message").removeAttr('checked');
			}
		});
		
		$('#ss-verifyId').combogrid({
			panelWidth : 460,
		    panelHeight : 310,
		    loadMsg : '加载中，请稍后...',
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'verifyName',
		    url : './admin_user/verify_queryAllVerify?addAllTag=true',
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
		$('#ss-verifyId').combogrid('setValue',0);
		
		$('#htm_recommend').window({
			modal : true,
			width : 520,
			height : 140,
			top :10,
			title : '更新认证',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
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
	
	
	
//初始化添加窗口
function initAddWindow(json) {
	var addForm = $('#add_form');
	formSubmitOnce = true;
	clearFormData(addForm);
	$("#userRecommend_add").focus();  //光标定位
	$.formValidator.initConfig({
		formid : addForm.attr("id"),
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$('#htm_table').datagrid('loading');
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						formSubmitOnce = true;
						$('#htm_table').datagrid('loaded');
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							maxId = 0;
							myQueryParams.maxId = maxId;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	$("#userRecommend_add")
	.formValidator({empty:false,onshow:"请输入用户id",onfocus:"例如:12,13",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
}

/**
 * 重排推荐
 */
function reIndexed() {
	$('#htm_indexed .opt_btn').show();
	$('#htm_indexed .loading').hide();
	clearReIndexedForm();
	// 打开添加窗口
	$("#htm_indexed").window('open');
}

/**
 * 清空索引排序
 */
function clearReIndexedForm() {
	$("#indexed_form").find('input[name="reIndexId"]').val('');	
}

function submitReIndexForm() {
	var $form = $('#indexed_form');
	if($form.form('validate')) {
		$('#htm_indexed .opt_btn').hide();
		$('#htm_indexed .loading').show();
		$('#indexed_form').form('submit', {
			url: $form.attr('action'),
			success: function(data){
				var result = $.parseJSON(data);
				$('#htm_indexed .opt_btn').show();
				$('#htm_indexed .loading').hide();
				if(result['result'] == 0) { 
					$('#htm_indexed').window('close');  //关闭添加窗口
					maxId = 0;
					myQueryParams.maxId = maxId;
					loadPageData(1);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			}
		});
	}
}

/**
 * 刷新推荐用户列表
 */
function refresh() {
	$.messager.confirm('提示', '确定要刷新推荐列表？刷新后所有数据将生效！', function(r){
		if (r){
			$('#htm_table').datagrid('loading');
			$.post('./admin_op/user_refreshRecommendUser',{
				"maxId":maxId
			},function(result){
					if(result['result'] == 0) {
						$('#htm_add').window('close');  //关闭添加窗口
						$.messager.alert('提示',result['msg']);  //提示添加信息成功
						$('#htm_table').datagrid('reload');
					} else {
						$('#htm_table').datagrid('loaded');
						$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
					}
				},"json");				
		};
	});
}

function notify(index) {
	var rows = $("#htm_table").datagrid('getRows');
	var row = rows[index],
		sysAccept = row['sysAccept'],
		ver = parseFloat(row['ver']),
		verifyName = row['verifyName'];
	$('#htm_notify .opt_btn').show();
	$('#htm_notify .loading').hide();
	$("#id_notify").val(row['id']);
	$("#userAccept_notify").val(row['userAccept']);
	$("#userId_notify").val(row['userId']);
	$("#userName_notify").val(row['userName']);
	$("#recommendType_notify").val(verifyName);
	notifyIndex = index;
	if(sysAccept == 2) {
		$("#reject_check").attr('checked','checked');
		$("#accept_check").removeAttr('checked');
		$("#notifyTip_notify").val(rejectTip);
	} else {
		var passTip = passTipHead + verifyName + passTipFoot,
			acceptTip = acceptTipHead + verifyName + acceptTipFoot,
			rejectTip = rejectTipHead + verifyName + rejectTipFoot;
		
		$("#reject_check").click(function() {
			$("#notifyTip_notify").val(rejectTip);
		});
		if(ver < 2.0803) { // 2.0803版本后可以接受邀请
			$("#accept_check").click(function() {
				$("#notifyTip_notify").val(passTip);
			});
			$("#notifyTip_notify").val(passTip);
		} else {
			$("#accept_check").click(function() {
				$("#notifyTip_notify").val(acceptTip);
			});
			
			$("#notifyTip_notify").val(acceptTip);
		}
		$("#accept_check").attr('checked','checked');
		$("#reject_check").removeAttr('checked');
		
	}
	$("#notifyTip_notify").focus();
	// 打开添加窗口
	$("#htm_notify").window('open');
}

/**
 * 提交通知表单
 */
function submitNotifyForm(index) {
	var $form = $('#notify_form');
	if($form.form('validate')) {
		$('#htm_notify .opt_btn').hide();
		$('#htm_notify .loading').show();
		$form.form('submit', {
			url: $form.attr('action'),
			success: function(data){
				var result = $.parseJSON(data);
				$('#htm_notify .opt_btn').show();
				$('#htm_notify .loading').hide();
				if(result['result'] == 0) { 
					var rows = $("#htm_table").datagrid('getRows'),
						row = rows[notifyIndex],
						notified = row['notified'];
					updateValue(notifyIndex,'notified',++notified);
					$('#htm_notify').window('close');  //关闭添加窗口
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			}
		});
	}
}

/**
 * 批量提醒
 */
function batchNotify(){
	var rows = $('#htm_table').datagrid('getSelections');
	if(rows.length > 0){
		$.messager.confirm('批量提醒',"确定要批量提醒所选中的"+rows.length+"个用户么？",function(r){
			if(r){
				$('#htm_table').datagrid('loading');
				for(var i=0;i<rows.length;i++){
					var row = rows[i],
					notifyTip = "",
					id 	= row['id'],
					sysAccept = row['sysAccept'],
					ver = parseFloat(row['ver']),
					verifyName = row['verifyName'],
					userAccept = row['userAccept'],
					userId = row['userId'],
					userName = row['userName'],
					passTip = passTipHead + verifyName + passTipFoot,
					acceptTip = acceptTipHead + verifyName + acceptTipFoot;
					if(sysAccept != 2 && userAccept == 0){
						if(ver < 2.0803) { // 2.0803版本后可以接受邀请
							notifyTip = passTip;
						} else {
							notifyTip = acceptTip;
						}
						$.post("./admin_op/user_notifyRecommendUser",{
							'id':id,
							'userAccept':userAccept,
							'userId':userId,
							'userName':userName,
							'accepted':true,
							'notifyTip':notifyTip,
							'recommendType':verifyName
						},function(result){
							if(result['result'] == 0) { 
							} 
							return false;
						},"json");
						
					}
				}
				$('#htm_table').datagrid('clearSelections');
				$('#htm_table').datagrid('reload');
				$('#htm_table').datagrid('loaded');
			}
		});
	}else{
		$.messager.alert('提醒失败','请先选择记录，再执行提醒操作!','error');
	}
}
 
/**
 * 使选中的分类有效
 */
function updateSysAccept(accept) {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(rows.length > 0){
		var tip = batchAcceptTip;
		if(accept == 2) 
			tip = batchRejectTip;
		$.messager.confirm('更新记录', tip, function(r){ 	
			if(r){				
				var ids = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i]['id']);
				}	
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				$('#htm_table').datagrid('loading');
				$.post(updateSysAcceptURL + ids,{
					"sysAccept" : accept
				},function(result){
					$('#htm_table').datagrid('loaded');
					if(result['result'] == 0) {
						$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
						$("#htm_table").datagrid("reload");
					} else {
						$.messager.alert('提示',result['msg']);
					}
				});				
			}	
		});	
	}else{
		$.messager.alert('更新失败','请先选择记录，再执行更新操作!','error');
	}
}

/**
 * 搜索推荐用户
 */
function searchRecommend() {
	maxId = 0;
	myQueryParams.maxId = maxId;
	myQueryParams.userName = "";
	myQueryParams.userAccept = $('#ss-userAccept').combobox('getValue');
	myQueryParams.sysAccept = $('#ss-sysAccept').combobox('getValue');
	myQueryParams.notified = $('#ss-notified').combobox('getValue');
	myQueryParams.weight = $('#ss-weight').combobox('getValue');
	myQueryParams.verifyId = $('#ss-verifyId').combobox('getValue');
	myQueryParams.lastUsed = $('#ss-lastUsed').combobox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}

/**
 * 根据用户名搜索推荐用户
 */
function searchByName() {
	maxId = 0;
	myQueryParams.maxId = maxId;
	myQueryParams.userName = $("#ss-userName").searchbox('getValue');
	myQueryParams.userAccept = "";
	myQueryParams.sysAccept = "";
	myQueryParams.notified = "";
	myQueryParams.weight = "";
	myQueryParams.verifyId = "";
	$("#htm_table").datagrid("load",myQueryParams);
}

/**
 * 初始化删除窗口
 */
function initDeleteWindow() {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(isSelected(rows)){
		$("#htm_delete").window('open');
	}
}

/**
 * 提交删除操作
 */
function submitDelete() {
	$('#htm_delete .opt_btn').hide();
	$('#htm_delete .loading').show();
	var deleteStar = false;
	var insertMessage = false;
	if($("#star_delete").attr('checked') == 'checked') {
		deleteStar = true;
	}
	if($("#insert_message").attr('checked') == 'checked') {
		insertMessage = true;
	}
	
	var rows = $('#htm_table').datagrid('getSelections');
	var ids = [];
	for(var i=0;i<rows.length;i+=1){		
		ids.push(rows[i][recordIdKey]);	
		rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
	}	
	$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
	$.post(deleteURI + ids,{
			'deleteStar':deleteStar,
			'insertMessage':insertMessage
		},function(result){
			$('#htm_delete .opt_btn').show();
			$('#htm_delete .loading').hide();
			if(result['result'] == 0) {
				$("#htm_delete").window('close');
				$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('提示',result['msg']);
			}
		}, 'json');	
}

function updateVerify(id, verifyId, index){
}

/**
 * 添加到推荐用户
 */
function updateVerify(id, verifyId, index) {
	$("#id_recommend").val(id);
	$("#verifyId_recommend").combogrid('setValue', verifyId);
	loadUserRecommendValidate(index);
	$("#htm_recommend").window('open');
	
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
							clearFormData($form);  //清空表单数据
							var g = $('#verifyId_recommend').combogrid('grid');
							var r = g.datagrid('getSelected');
							updateValues(index, ['verifyId', 'verifyName', 'verifyIcon'], [r.verifyId, r.verifyName, r.verifyIcon]);
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


//直接让用户接受
function userAcceptDirect(userId,verifyId,index){
	$.messager.confirm("用户接受","确定使用户直接接受邀请？",function(r){
		if(r){
			$.post("./admin_op/user_userAcceptRecommendDirect",{
				'userId':userId,
				'verifyId':verifyId
			},function(result){
				if(result['result'] == 0){
					updateValues(index,['userAccept'],[1]);
				}else{
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			},"json");
		}
	});
}

function updateWeight(index, id, isAdd) {
	$("#htm_table").datagrid("loading");
	$.post("./admin_op/user_updateRecommendWeight",{
				'id':id,
				'isAdd':isAdd
			},function(result){
				$("#htm_table").datagrid("loaded");
				if(result['result'] == 0){
					var weight = 0;
					if(isAdd) {
						weight = result['weight'];
					}
					updateValue(index,'weight',weight);
				}else{
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			},"json");
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initDeleteWindow();" class="easyui-linkbutton" title="删除推荐用户" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:updateSysAccept(1);" class="easyui-linkbutton" title="批量通过推荐申请！" plain="true" iconCls="icon-ok" id="refreshBtn">批量通过</a>
			<a href="javascript:void(0);" onclick="javascript:updateSysAccept(2);" class="easyui-linkbutton" title="批量拒绝推荐申请！" plain="true" iconCls="icon-cancel" id="rejectBtn">批量拒绝</a>
			<a href="javascript:void(0);" onclick="javascript:reIndexed();" class="easyui-linkbutton" title="推荐用户排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">重新排序</a>
			<a href="javascript:void(0);" onclick="javascript:batchNotify();" class="easyui-linkbutton" title="批量提醒" plain="true" iconCls="icon-converter" id="batchNotifyBtn">批量提醒</a>
			<input id="ss-verifyId" value="所有认证类型" style="width:100px" />
			<select id="ss-sysAccept" class="easyui-combobox" style="width:80px;">
		        <option value="">所有系统状态</option>
		        <option value="0">待定</option>
		        <option value="1">通过</option>
		        <option value="2">拒绝</option>
	   		</select>
	   		<select id="ss-userAccept" class="easyui-combobox" style="width:90px;">
		        <option value="">所有用户状态</option>
		        <option value="0">待定</option>
		        <option value="1">通过</option>
		        <option value="2">拒绝</option>
	   		</select>
	   		<select id="ss-notified" class="easyui-combobox" style="width:90px;">
		        <option value="">所有通知状态</option>
		        <option value="0">未通知</option>
		        <option value="1">已通知</option>
	   		</select>
	   		<select id="ss-weight" class="easyui-combobox" style="width:90px;">
	   			<option value="">置顶和未置顶</option>
	   			<option value="1">已置顶</option>
	   			<option value="0">未置顶</option>
	   		</select>
	   		<select id="ss-lastUsed" class="easyui-combobox" style="width:90px;">
	   			<option value="">所有登录时间</option>
	   			<option value="1">一个月没登录</option>
	   		</select>
	   		<a href="javascript:void(0);" onclick="javascript:searchRecommend();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	   		
	   		<div style="display: inline-block; vertical-align:middle; float: right;">
		        <input id="ss-userName" class="easyui-searchbox" searcher="searchByName" prompt="请输入昵称/ID搜索用户" style="width:150px;" />
			</div>
   		</div>
	</div> 

	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_op/user_saveRecommendUsers" method="post">
			<table class="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">用户id：</td>
						<td><input type="text" name="userIds" id="userRecommend_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userRecommend_addTip" class="tipDIV"></div></td>
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
	
	<!-- 添加记录 -->
	<div id="htm_notify">
		<form id="notify_form" action="./admin_op/user_notifyRecommendUser" method="post">
			<table class="htm_edit_table" width="340">
				<tbody>
					<tr class="none">
						<td class="leftTd">推荐id：</td>
						<td>
							<input id="id_notify" name="id" type="text"/>
						</td>
					</tr>
					<tr class="none">
						<td class="leftTd">用户状态：</td>
						<td>
							<input id="userAccept_notify" name="userAccept" type="text"/>
						</td>
					</tr>
					<tr class="none">
						<td class="leftTd">用户ID：</td>
						<td>
							<input id="userId_notify" name="userId" type="text" style="width:250px;" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">用户名：</td>
						<td>
							<input id="userName_notify" name="userName" type="text" readonly="readonly" style="width:250px;"/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">状态：</td>
						<td>
							<input id="accept_check" type="radio" name="accepted" value="true" checked="checked" style="width:13px;"/>通过
							<input id="reject_check" type="radio" name="accepted" value="false" style="width:13px;" />拒绝
						</td>
					</tr>
					<tr>
						<td class="leftTd">提示：</td>
						<td>
							<textarea id="notifyTip_notify" class="easyui-validatebox" name="notifyTip" data-options="required:true" style="width:250px;" ></textarea>
						</td>
					</tr>
					<tr class="none">
						<td class="leftTd">类型：</td>
						<td>
							<input id="recommendType_notify" class="easyui-validatebox" name="recommendType" />
						</td>
					</tr>
					
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitNotifyForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_notify').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">保存中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 重排索引 -->
	<div id="htm_indexed">
		<form id="indexed_form" action="./admin_op/user_reIndexRecommendUser" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">用户ID：</td>
						<td>
							<input name="reIndexId" class="easyui-validatebox reindex_column" required="true"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReIndexForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-redo" onclick="clearReIndexedForm();">清空</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_indexed').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">排序中...</span>
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
						<td class="leftTd">用户ID：</td>
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
	
	<!-- 添加记录 -->
	<div id="htm_delete">
		<table class="htm_edit_table" width="300">
			<tbody>
				<tr>
					<td>确定要从推荐列表删除吗？</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="star" id="star_delete" style="width: 13px; height: 13px; vertical-align: middle;"/>同时删除明星标记</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="insertMessage" id="insert_message" style="width: 13px; height: 13px; vertical-align: middle;"/>同时给用户发送删除消息</td>
				</tr>
				<tr>
					<td class="opt_btn" style="text-align: center;padding-top: 10px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitDelete();">确定</a> 
						<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_delete').window('close');">取消</a>
					</td>
				</tr>
				<tr class="loading none">
					<td style="text-align: center; padding-top: 10px; vertical-align:middle;">
						<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
						<span style="vertical-align:middle;">请稍后...</span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<!-- 推荐用户 -->
	<div id="htm_recommend">
		<form id="recommend_form" action="./admin_op/user_updateRecommendVerify" method="post">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr class="none">
						<td class="leftTd">id：</td>
						<td><input type="text" name="id" id="id_recommend" readonly="readonly" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"></td>
					</tr>
					<tr>
						<td class="leftTd">认证类型：</td>
						<td><textarea name="verifyId" id="verifyId_recommend" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="verifyId_recommendTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#recommend_form').submit();">更新</a> 
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
	</div>
</body>
</html>