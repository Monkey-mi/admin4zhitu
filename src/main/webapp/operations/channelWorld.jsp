<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道织图管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js"></script>
<script type="text/javascript">
var maxId = 0,
	channelId = 0,
	notifyIndex = 0,
	batchEnableTip = "您确定要使已选中的织图生效吗？",
	batchDisableTip = "您确定要使已选中的织图失效吗？",
	init = function() {
		loadPageData(initPage);
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['world.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['world.maxId'] = maxId;
			}
		}
	},
	myRowStyler = function(index, row) {
		if(!row.valid)
			return inValidWorld;
		return null;
	},
	hideIdColumn = true,
	htmTableTitle = "频道织图列表", //表格标题
	htmTableWidth = 1200,
	toolbarComponent = '#tb',
	myIdField = "channelWorldId",
	recordIdKey = "channelWorldId",
	uidKey = "userId",
	loadDataURL = "./admin_op/channel_queryChannelWorld", //数据装载请求地址
	deleteURI = "./admin_op/channel_deleteChannelWorld?ids=", //删除请求地址
	updateValidURL = "./admin_op/channel_updateChannelWorldValid?ids=",
	addRecommendMsgURL = "./admin_op/channel_addChannelWorldRecommendMsgs?ids=",
	queryChannelURL = "./admin_op/channel_queryChannelById",
	updateCoverCacehURL = "./admin_op/channel_updateChannelCoverCache",
	queryChannelByIdOrNameURL = "./admin_op/v2channel_queryOpChannelByIdOrName",// 根据id或民称查询频道
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : '序号',align : 'center',width : 60},
		worldIdColumn,
  		phoneCodeColumn,
  		authorAvatarColumn,
  		authorIdColumn,
  		authorNameColumn,
  		clickCountColumn,
  		likeCountColumn,
  		commentCountColumn,
  		worldURLColumn,
  		titleThumbPathColumn,
  		worldLabelColumn,
		{field : 'channelIcon',title : '频道icon',align : 'center', width : 60,
			formatter: function(value,row,index) {
				return "<img title='" + row.channelName +  "' width=30 height=30 class='htm_column_img' src='" + value + "'/>";
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
		{field : 'channelWorldValid',title : '有效性',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
  		{field : 'beSchedula',title : '计划',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 0) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}  			
  		},
  		{field : 'schedulaComplete',title : '计划完成情况',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1 && row.beSchedula == 0) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}else if(value == 0 && row.beSchedula == 0){
	  				img = "./common/images/tip.png";
	  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  				}
  				return '';
  			}   			
  		},
  		{field : 'isCover',title : '频道封面',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value > 0) {
  					img = "./common/images/undo.png";
  					return "<img title='点击取消频道封面' class='htm_column_img pointer'  src='" + img 
  						+ "' onclick='javascript:removeCover(\""+ row['channelId'] + "\",\""+ row['worldId'] + "\",\"" + index + "\")' />";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='点击设置成频道封面' class='htm_column_img pointer'  src='" + img 
  					+ "' onclick='javascript:addCover(\""+ row['channelId'] + "\",\""+ row['worldId'] + "\",\"" + index + "\")' />";
  			}
  		},
  		dateModified,
		],
	addWidth = 520, //添加信息宽度
	addHeight = 190, //添加信息高度
	addTitle = "添加频道织图", //添加信息标题
	
	pageButtons = [{
        iconCls:'icon-save',
        text:'更新封面缓存',
        handler:function(){
        	$.messager.confirm('提示', '确定更新封面缓存？', function(r){ 	
			if(r){				
				$('#htm_table').datagrid('loading');
				$.post(updateCoverCacehURL, function(result){
					$('#htm_table').datagrid('loaded');
					if(result['result'] == 0) {
						$.messager.alert('提示',result['msg']);
					} else {
						$.messager.alert('提示',result['msg']);
					}
				});				
			}	
		});	
        }
    }],
    onBeforeInit = function() {
		showPageLoading();
	},
	
	channelMaxId = 0,
	channelQueryParams = {},
	
	onAfterInit = function() {
		
		$('#htm_indexed').window({
			title : '重新排序',
			modal : true,
			width : 660,
			height : 195,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false,
			onClose : function() {
				$(".reindex_column").val('');
			}
		});
		
		$('#htm_refresh').window({
			title : '刷新频道',
			modal : true,
			width : 520,
			height : 145,
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
		
		
		$('#ss-channel').combogrid({
			panelWidth : 320,
		    panelHeight : 490,
		    loadMsg : '加载中，请稍后...',
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'channelName',
		    url : './admin_op/channel_queryChannel',
		    pagination : true,
		    remoteSort : false,
		    columns:[[
				{field : 'id',title : 'ID', align : 'center',width : 60},
				{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
					formatter:function(value,row,index) {
						return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				},
				{field : 'channelName',title : '频道名称',align : 'center',width : 120},
			]],
			onSelect:function(index, row) {
				$("#htm_opt_btn").show();
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				maxId = 0;
				channelId = row.id;
				myQueryParams['world.maxId'] = maxId;
				myQueryParams['world.channelId'] = channelId;
				myQueryParams['world.worldId'] = "";
				myQueryParams['world.notified'] = "";
				myQueryParams['world.valid'] = "";
				loadPageData(initPage);
			}
		});
		
		var p = $('#ss-channel').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					channelMaxId = 0;
					channelQueryParams['channel.maxId'] = userMaxId;
				}
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	
	
	
//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	clearFormData(addForm);
	var channelName = $('#ss-channel').combogrid('getText');
	var channelId = $('#ss-channel').combogrid('getValue');
	$("#channelName_add").text(channelName);
	$("#channelId_add").val(channelId);
	$("#valid_add").val(0);
	$("#notified_add").val(0);
	$("#add_form .opt_btn").show();
	$("#add_form .loading").hide();
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	formSubmitOnce = true;
	$("#worldId_add").focus();  //光标定位
	$.formValidator.initConfig({
		formid : addForm.attr("id"),
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$("#add_form .opt_btn").hide();
				$("#add_form .loading").show();
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						formSubmitOnce = true;
						$("#add_form .opt_btn").show();
						$("#add_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							maxId = 0;
							myQueryParams['world.maxId'] = maxId;
							myQueryParams['world.channelId'] = channelId;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});

	$("#worldId_add")
	.formValidator({empty:false,onshow:"请输入织图id",onfocus:"例如:12",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
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
						if(valid) 
							loadPageData(1);
						else
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
	var channelId = $('#ss-channel').combogrid('getValue');
	$("#channelId_indexed").val(channelId);
	clearReIndexedForm();
	
	var rows = $("#htm_table").datagrid('getSelections');
	$('#indexed_form .reindex_column').each(function(i){
		if(i<rows.length)
			$(this).val(rows[i]['worldId']);
	});
	
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
					myQueryParams['world.maxId'] = maxId;
					loadPageData(1);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			}
		});
	}
}


/**
 * 搜索推荐用户
 */
function searchWorld() {
	maxId = 0;
	myQueryParams['world.maxId'] = maxId;
	myQueryParams['world.worldId'] = "";
	myQueryParams['world.channelId'] = $('#ss-channel').combogrid('getValue');
	myQueryParams['world.notified'] = $('#ss-notified').combobox('getValue');
	myQueryParams['world.valid'] = $('#ss-valid').combobox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}

/**
 * 根据用户名搜索推荐用户
 */
function searchByWID() {
	maxId = 0;
	myQueryParams['world.maxId'] = maxId;
	myQueryParams['world.worldId'] = $("#ss-worldId").searchbox('getValue');
	myQueryParams['world.channelId'] = $('#ss-channel').combogrid('getValue');
	myQueryParams['world.notified'] = "";
	myQueryParams['world.valid'] = "";
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
function refresh() {
	$('#htm_refresh .opt_btn').show();
	$('#htm_refresh .loading').hide();
	$('#channelId_refresh').val(channelId);
	loadRefreshFormValidate();
	// 打开添加窗口
	$("#htm_refresh").window('open');
	var cid = $("#ss-channel").combogrid("getValue");
	$.post(queryChannelURL,{
		'id':cid
		},function(result){
			if(result['result'] == 0) {
				var cbase = result['obj']['childCountBase'];
				$("#childCount_refresh").val(cbase);
			} else {
				$.messager.alert('提示',result['msg']);
			}
		},"json");
	
}

//提交表单，以后补充装载验证信息
function loadRefreshFormValidate() {
	var $form = $('#refresh_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#refresh_form .opt_btn").hide();
				$("#refresh_form .loading").show();
				//验证成功后以异步方式提交表单
				$.post($form.attr("action"),$form.serialize(),
					function(result){
						formSubmitOnce = true;
						$("#refresh_form .opt_btn").show();
						$("#refresh_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_refresh').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");
				return false;
			}
		}
	});
	
	$("#childCount_refresh")
	.formValidator({empty:true,onshow:"图片数=实际图片数+基数",onfocus:"例如:10",oncorrect:"设置成功"});
	
}


function addSubmit(){
	$('#add_form').submit();
	var channelId = $("#channelId_add").val();
	var	 worldId = $("#worldId_add").val();
	$.post("./admin_op/chuser_addChannelUserByWorldId",{
		'cover.worldId':worldId,
		'cover.channelId':channelId
	},function(result){
		
	},"json");
}

function removeCover(channelId, worldId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_op/channel_deleteChannelCover",{
		'cover.channelId':channelId,
		'cover.worldId':worldId
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'isCover','0');	
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

function addCover(channelId, worldId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_op/channel_saveChannelCover",{
		'cover.channelId':channelId,
		'cover.worldId':worldId
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'isCover','1');	
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}


/**
 * 根据频道Id或名字查询频道
 */
function queryChannelByIdOrName(){
	var channelIdOrName = $("#ss-channelSearch").searchbox('getValue');
	var params={};
	if(channelIdOrName){
		channelIdOrName = channelIdOrName.trim();
		if(isNaN(channelIdOrName)){
			params['channelName']=channelIdOrName;
		}else{
			maxId = 0;
			myQueryParams['world.maxId'] = maxId;
			myQueryParams['world.channelId'] = channelIdOrName;
			$("#htm_table").datagrid("load",myQueryParams);
			return;
		}
	
		$.post(queryChannelByIdOrNameURL,params, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				
				maxId = 0;
				myQueryParams['world.maxId'] = maxId;
				myQueryParams['world.channelId'] = obj['channelId'];
				$("#htm_table").datagrid("load",myQueryParams);
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	}
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<span class="search_label">请选择频道：</span>
			<input id="ss-channel" style="width:100px;" />
			<input id="ss-channelSearch" prompt="输入频道ID或者频道名称" searcher="queryChannelByIdOrName" class="easyui-searchbox" style="width:100px;">
			<span id="htm_opt_btn" class="none">
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加频道红人" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除频道红人" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效" plain="true" iconCls="icon-ok">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效" plain="true" iconCls="icon-tip">批量失效</a>
			<a href="javascript:void(0);" onclick="javascript:batchNotify();" class="easyui-linkbutton" title="批量通知" plain="true" iconCls="icon-ok">批量通知</a>
			<a href="javascript:void(0);" onclick="javascript:reIndexed();" class="easyui-linkbutton" title="推荐用户排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">重新排序</a>
			<a href="javascript:void(0);" onclick="javascript:refresh();" class="easyui-linkbutton" title="更新图片基数" plain="true" iconCls="icon-reload">更新图片基数</a>
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
	   		<a href="javascript:void(0);" onclick="javascript:searchWorld();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	   		<span style="display: inline-block; vertical-align:middle; float: right;">
		        <input id="ss-worldId" class="easyui-searchbox" searcher="searchByWID" prompt="输入织图ID搜索" style="width:150px;" />
			</span>
			</span>
   		</div>
	</div> 

	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_op/channel_saveChannelWorld" method="post">
			<table class="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">频道：</td>
						<td><span id="channelName_add"></span></td>
						<td class="rightTd"></td>
					</tr>
					<tr>
						<td class="leftTd">织图id：</td>
						<td><input type="text" name="world.worldId" id="worldId_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="worldId_addTip" class="tipDIV"></div></td>
					</tr>
					<tr >
						<td class="leftTd">频道id：</td>
						<td ><input type="text" name="world.channelId" id="channelId_add" onchange="validateSubmitOnce=true;" readonly="readonly"/></td>
						<td class="rightTd"></td>
					</tr>
					<tr class="none">
						<td colspan="3"><input type="text" name="world.valid" id="valid_add" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr class="none">
						<td colspan="3"><input type="text" name="world.notified" id="notified_add" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit();">添加</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
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
		<form id="indexed_form" action="./admin_op/cwSchedula_batchAddChannelWorldSchedula" method="post">
			<table class="htm_edit_table" width="660">
				<tbody>
					<tr>
						<td class="leftTd">织图ID：</td>
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
					<tr>
						<td class="leftTd">计划更新时间：</td>
						<td><input id="schedula" name="schedula" class="easyui-datetimebox" required="true"></td>
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
	<div id="htm_refresh">
		<form id="refresh_form" action="./admin_op/channel_updateChannelWorldCache" method="post">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">图片基数：</td>
						<td><input type="text" name="childCountBase" id="childCount_refresh" style="width:205px;"/></td>
						<td class="rightTd"><div id="childCount_refreshTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none">
						<td><input type="text" name="world.channelId" id="channelId_refresh" style="width:205px;"/></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#refresh_form').submit();">确定</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_refresh').window('close');">取消</a>
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