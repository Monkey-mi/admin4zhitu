<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Top One红人管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer} "></script>
<script type="text/javascript">
var refreshTitleInterval = 3*24*60*60*1000,
	maxId = 0,
	channelId = 0,
	notifyIndex = 0,
	batchEnableTip = "您确定要使已选中的用户生效吗？",
	batchDisableTip = "您确定要使已选中的用户失效吗？",
	init = function() {
		loadPageData(initPage);
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['topOne.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['topOne.maxId'] = maxId;
			}
		}
	},
	myRowStyler = function(index, row) {
	},
	hideIdColumn = false,
	htmTableTitle = "Top One红人列表", //表格标题
	toolbarComponent = '#tb',
	myIdField = "topOneId",
	recordIdKey = "topOneId",
	uidKey = "userId",
	loadDataURL = "./admin_op/channel_queryTopOne", //数据装载请求地址
	deleteURI = "./admin_op/channel_deleteTopOne?ids=", //删除请求地址
	updateValidURL = "./admin_op/channel_updateTopOneValid?ids=",
	addRecommendMsgURL = "./admin_op/channel_addTopOneRecommendMsgs?ids=",
	saveTopOneURL = "./admin_op/channel_saveTopOne", // 保存频道地址
	updateTopOneByIdURL = "./admin_op/channel_updateTopOne", // 更新频道地址
	queryTopOneByIdURL = "./admin_op/channel_queryTopOneById", // 根据id查询频道
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : '序号',align : 'center',width : 60},
		phoneCodeColumn,
		userAvatarColumn,
		userNameColumn,
		sexColumn,
		userLabelColumn,
		{field : 'topDesc',title : '描述',align : 'center', width : 200},
		{field : 'period',title : '期数',align : 'center', width : 80},
		{field : 'opt',title : '操作',width : 80,align : 'center',rowspan : 1,
			formatter : function(value, row, index ) {
				return "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row[recordIdKey] + "\",\"" + index + "\"," + true + ")'>【修改】</a>";
			}
		},
		{field : 'notified',title : '通知状态',align : 'center', width : 60,
			formatter: function(value,row,index) {
  				if(value >= 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已经通知' class='htm_column_img' src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
		},
		{field : 'valid',title : '有效性',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
		],
	htmTablePageList = [11,22,33],
	myPageSize = 11,
	addWidth = 520, //添加信息宽度
	addHeight = 160, //添加信息高度
	addTitle = "添加TopOne红人", //添加信息标题
	
	pageButtons = [],
    onBeforeInit = function() {
		showPageLoading();
	},
	
	channelMaxId = 0,
	channelQueryParams = {},
	
	onAfterInit = function() {
		$('#htm_indexed').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 165,
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
		
		$('#htm_refreshTitle').window({
			title : '刷新频道标题',
			modal : true,
			width : 520,
			height : 185,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				var $form = $('#refresh_form');
				clearFormData($form);
			}
		});
		
		$('#topId_edit').combobox({
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'topTitle',
		    valueField : 'id',
		    url : './admin_op/channel_queryTopType',
		});
		
		$('#ss-topId').combobox({
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'topTitle',
		    valueField : 'id',
		    url : './admin_op/channel_queryTopType?addAllTag=true',
		});
		
		$('#ss-topId').combobox('setValue', '0');
		
		$('#htm_edit').window({
			title: '添加频道',
			modal : true,
			width : 520,
			height : 165,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				var $form = $('#edit_form');
				clearFormData($form);
				$("#edit_form .opt_btn").show();
				$("#edit_form .loading").hide();
				$("#topId_edit").combobox('setValue', 1);
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		$('#beginDate_refreshTitle').datebox({
			onSelect:function(date){
				$('#beginDateStr_refreshTitle').text((date.getMonth()+1)+"."+date.getDate());
				
			}
		});
		
		$('#endDate_refreshTitle').datebox({
			onSelect:function(date){
				$('#endDateStr_refreshTitle').text((date.getMonth()+1)+"."+date.getDate());
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	
/**
 * 初始化频道信息编辑窗口
 */
function initEditWindow(id, index, isUpdate) {
	$("#top_edit").focus();  //光标定位
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改红人信息');
		$('#htm_edit').window('open');
		$.post(queryTopOneByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				console.log(obj['userId']);
				$("#userId_edit").val(obj['userId']);
				$("#topId_edit").combobox('select', obj['topId']);
				
				$("#id_edit").val(obj['id']);
				$("#period_edit").val(obj['period']);
				$("#valid_edit").val(obj['valid']);
				$("#notified_edit").val(obj['notified']);
				
				$("#edit_loading").hide();
				$("#edit_form").show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$("#id_edit").val(id);
		$("#valid_edit").val(1);
		$("#notified_edit").val(0);
		$("#topId_edit").combobox('setValue', 1);
		$('#htm_edit').panel('setTitle', '添加红人信息');
		$('#htm_edit').window('open');
		$("#edit_loading").hide();
		$("#edit_form").show();
	}
}

//提交表单，以后补充装载验证信息
function loadEditFormValidate(index, isUpdate) {
	var url = saveTopOneURL;
	if(isUpdate) {
		url = updateTopOneByIdURL;
	}
	var $form = $('#edit_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#edit_form .opt_btn").hide();
				$("#edit_form .loading").show();
				//验证成功后以异步方式提交表单
				$.post(url,$form.serialize(),
					function(result){
						formSubmitOnce = true;
						$("#edit_form .opt_btn").show();
						$("#edit_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_edit').window('close');  //关闭添加窗口
							maxId = 0;
							myQueryParams['topOne.maxId'] = maxId;
							loadPageData(initPage);
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#userId_edit")
	.formValidator({empty:false,onshow:"用户id（必填）",onfocus:"请输入用户id",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"})
	.regexValidator({regexp:"num", datatype:"enum", onerror:"由数字组成"});
	
	$("#topId_edit")
	.formValidator({empty:false,onshow:"Top类型（必填）",onfocus:"请输入名称",oncorrect:"设置成功"});
	
}

/**
 * 更新有效性
 */
function updateValid(valid) {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(rows.length > 0){
		var tip = batchEnableTip;
		if(valid == 0) 
			tip = batchDisableTip;
		$.messager.confirm('更新记录', tip, function(r){ 	
			if(r){				
				var ids = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i][recordIdKey]);
				}	
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				$('#htm_table').datagrid('loading');
				$.post(updateValidURL + ids,{
					"valid" : valid
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
					myQueryParams['topOne.maxId'] = maxId;
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
	$.messager.confirm('提示', '确定要刷新红人列表缓存？刷新后所有数据将生效！', function(r){
		if (r){
			$('#htm_table').datagrid('loading');
			$.post('./admin_op/channel_updateTopOneCache',{
				"topOne.channelId": channelId
			},function(result){
					$('#htm_table').datagrid('loaded');
					if(result['result'] == 0) {
						$.messager.alert('提示',result['msg']);  //提示添加信息成功
					} else {
						$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
					}
				},"json");				
		};
	});
}


/**
 * 搜索推荐用户
 */
function searchTopOne() {
	maxId = 0;
	myQueryParams['topOne.maxId'] = maxId;
	myQueryParams['topOne.userId'] = "";
	myQueryParams['topOne.topId'] = $('#ss-topId').combobox('getValue');
	myQueryParams['topOne.notified'] = $('#ss-notified').combobox('getValue');
	myQueryParams['topOne.valid'] = $('#ss-valid').combobox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}

/**
 * 根据用户名搜索推荐用户
 */
function searchByUID() {
	maxId = 0;
	myQueryParams['topOne.maxId'] = maxId;
	myQueryParams['topOne.userId'] = $("#ss-userId").searchbox('getValue');
	myQueryParams['topOne.topId'] = "";
	myQueryParams['topOne.notified'] = "";
	myQueryParams['topOne.valid'] = "";
	$("#htm_table").datagrid("load",myQueryParams);
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

function batchNotify() {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(rows.length > 0){
		$.messager.confirm('添加通知', '你确定要批量添加通知', function(r){ 	
			if(r){				
				var ids = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i][recordIdKey]);
				}	
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				$('#htm_table').datagrid('loading');
				$.post(addRecommendMsgURL + ids,{
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
		$.messager.alert('通知失败','请先选择记录，再执行更新操作!','error');
	}
}

/**
 * 刷新
 */
function refreshTitle() {
	$('#htm_refreshTitle .opt_btn').show();
	$('#htm_refreshTitle .loading').hide();
	var today = new Date(),
		beginDate = new Date();
		todayTime = today.getTime(),
		beginTime = todayTime - refreshTitleInterval,
	beginDate.setTime(beginTime);
	var beginStr = baseTools.simpleFormatDate(beginDate),
		endStr = baseTools.simpleFormatDate(today);
	$('#beginDate_refreshTitle').datebox('setValue', beginStr);
	$('#endDate_refreshTitle').datebox('setValue', endStr);
	
	$('#beginDateStr_refreshTitle').text((beginDate.getMonth()+1)+"."+beginDate.getDate());
	$('#endDateStr_refreshTitle').text((today.getMonth()+1)+"."+today.getDate());
	
	loadrefreshTitleFormValidate();
	// 打开添加窗口
	$("#htm_refreshTitle").window('open');
}

//提交表单，以后补充装载验证信息
function loadrefreshTitleFormValidate() {
	var $form = $('#refreshTitle_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#refreshTitle_form .opt_btn").hide();
				$("#refreshTitle_form .loading").show();
				//验证成功后以异步方式提交表单
				$.post($form.attr("action"),$form.serialize(),
					function(result){
						formSubmitOnce = true;
						$("#refreshTitle_form .opt_btn").show();
						$("#refreshTitle_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_refreshTitle').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");
				return false;
			}
		}
	});
	
	$("#childCount_refreshTitle")
	.formValidator({empty:true,onshow:"不设置则自动更新",onfocus:"例如:10",oncorrect:"设置成功"})
	
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除频道红人" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效" plain="true" iconCls="icon-ok">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效" plain="true" iconCls="icon-tip">批量失效</a>
			<a href="javascript:void(0);" onclick="javascript:batchNotify();" class="easyui-linkbutton" title="批量通知" plain="true" iconCls="icon-ok">批量通知</a>
			<a href="javascript:void(0);" onclick="javascript:reIndexed();" class="easyui-linkbutton" title="推荐用户排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">重新排序</a>
			<a href="javascript:void(0);" onclick="javascript:refresh();" class="easyui-linkbutton" title="刷新缓存" plain="true" iconCls="icon-reload">刷新缓存</a>
			<a href="javascript:void(0);" onclick="javascript:refreshTitle();" class="easyui-linkbutton" title="修改标题" plain="true" iconCls="icon-edit">修改标题</a>
			<input id="ss-topId" style="width:100px;" />
			<select id="ss-notified" class="easyui-combobox" style="width:100px;">
		        <option value="">所有通知状态</option>
		        <option value="0">未通知</option>
		        <option value="1">已通知</option>
	   		</select>
	   		<select id="ss-valid" class="easyui-combobox" style="width:100px;">
		        <option value="">所有生效状态</option>
		        <option value="0">未生效</option>
		        <option value="1">生效</option>
	   		</select>
	   		<a href="javascript:void(0);" onclick="javascript:searchTopOne();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	   		<span style="display: inline-block; vertical-align:middle; float: right;">
		        <input id="ss-userId" class="easyui-searchbox" searcher="searchByUID" prompt="输入用户ID搜索" style="width:150px;" />
			</span>
			
	   		
   		</div>
	</div> 

	<!-- 添加记录 -->
	<div id="htm_edit">
		<form id="edit_form" action="./admin_op/channel_saveTopOne" method="post">
			<table class="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">Top类型：</td>
						<td><input type="text" name="topOne.topId" id="topId_edit" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="topId_editTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">用户id：</td>
						<td><input type="text" name="topOne.userId" id="userId_edit" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userId_editTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none">
						<td colspan="3"><input type="text" name="topOne.period" id="period_edit" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr class="none">
						<td colspan="3"><input type="text" name="topOne.valid" id="valid_edit" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr class="none">
						<td colspan="3"><input type="text" name="topOne.notified" id="notified_edit" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr class="none">
						<td colspan="3"><input type="text" name="topOne.id" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#edit_form').submit();">提交</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_edit').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">提交中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 重排索引 -->
	<div id="htm_indexed">
		<form id="indexed_form" action="./admin_op/channel_updateTopOneSerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">序号：</td>
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
						</td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="channelId" id="channelId_indexed" /></td>
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
	
	<!-- 刷新窗口窗口 -->
	<div id="htm_refreshTitle">
		<form id="refreshTitle_form" action="./admin_op/channel_updateTopOneTitleCache" method="post">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">修改为：</td>
						<td colspan="2">本期红人榜（<span id="beginDateStr_refreshTitle"></span>～<span id="endDateStr_refreshTitle"></span>）</td>
					</tr>
					<tr>
						<td class="leftTd">起始日期：</td>
						<td><input id="beginDate_refreshTitle" name="beginDate" type="text"></td>
						<td class="rightTd"><div id="beginDate_refreshTitleTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">结束日期：</td>
						<td><input id="endDate_refreshTitle" name="endDate" type="text"></td>
						<td class="rightTd"><div id="endDate_refreshTitleTip" class="tipDIV"></div></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#refreshTitle_form').submit();">确定</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_refreshTitle').window('close');">取消</a>
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
	</div>
</body>
</html>