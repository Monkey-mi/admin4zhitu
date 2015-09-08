<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道织图管理-列表模式</title>
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
	currentIndex = 0,
	batchEnableTip = "您确定要使已选中的织图生效吗？",
	batchDisableTip = "您确定要使已选中的织图失效吗？",
	htmTablePageList = [10,30,50,100],
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
	toolbarComponent = '#tb',
	myIdField = "channelWorldId",
	recordIdKey = "channelWorldId",
	uidKey = "userId",
	loadDataURL = "./admin_op/channel_queryChannelWorld", //数据装载请求地址
	deleteURI = "./admin_op/channel_deleteChannelWorld?ids=", //删除请求地址
	updateValidURL = "./admin_op/channel_updateChannelWorldValid?ids=",
	addRecommendMsgURL = "./admin_op/channel_addChannelWorldRecommendMsgs?ids=",
	queryChannelURL = "./admin_op/channel_queryChannelById",
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
  		{field : 'superb',title : '加精',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				var superb;
  				switch(value) {
  				case 1:
  					tip = "已加精,点击取消加精";
  					img = "./common/images/ok.png";
  					superb = 0;
  					break;
  				default:
  					tip = "点击加精";
  					img = "./common/images/tip.png";
  					superb = 1;
  					break;
  				}
  				return "<img title='"+ tip + "' class='htm_column_img pointer' onclick='updateSchedulaSuperbOp("+ superb +","+ index +")' src='" + img + "'/>";
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
  		{field : 'multiple',title : '属于多个频道',align : 'center', width : 160},
  		dateModified,
		],
	addWidth = 520, //添加信息宽度
	addHeight = 190, //添加信息高度
	addTitle = "添加频道织图", //添加信息标题
	
	pageButtons = [],
    onBeforeInit = function() {
		showPageLoading();
	},

	searchChannelMaxId = 0,
	searchChannelQueryParams = {
		'maxId':searchChannelMaxId
	},
		
	onAfterInit = function() {
		
		$('#htm_indexed').window({
			title : '按照时间重新排序',
			modal : true,
			width : 660,
			height : 235,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
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
			panelWidth : 440,
		    panelHeight : 330,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#search-channel-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'channelName',
		    url : './admin_op/channel_searchChannel',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'id',align : 'center',width : 80},
				{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
					formatter:function(value,row,index) {
						return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				},
				{field : 'channelName',title : '频道名称',align : 'center',width : 280}
		    ]],
		    queryParams:searchChannelQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > searchChannelMaxId) {
						searchChannelMaxId = data.maxId;
						searchChannelQueryParams.maxId = searchChannelMaxId;
					}
				}
		    },
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
					searchChannelMaxId = 0;
					searchChannelQueryParams.maxId = searchChannelMaxId;
				}
			}
		});
		
		removePageLoading();
		$("#main").show();
		
	};
	
/**
 * 搜索频道名称
 */
function searchChannel() {
	searchChannelMaxId = 0;
	maxId = 0;
	var query = $('#channel-searchbox').searchbox('getValue');
	searchChannelQueryParams.maxId = searchChannelMaxId;
	searchChannelQueryParams.query = query;
	$("#ss-channel").combogrid('grid').datagrid("load",searchChannelQueryParams);
}
	
	
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
					"valid" : valid,
					"channlMsgType":"_add"
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
	
	var rows = $("#htm_table").datagrid('getSelections');
	// 定义重新排序织图id集合
	var wids = [];
	// 定义存储加精织图id集合
	var superbWids = [];
	for(var i=0;i<rows.length;i++){
		wids.push(rows[i].worldId);
		
		if (rows[i].channelWorldSchedulaSuperb==1){
			superbWids.push(rows[i].worldId);
		}
	}
	
	$("#wids_indexed").val(wids);
	$("#superbWids_indexed").val(superbWids);
	
	// 打开添加窗口
	$("#htm_indexed").window('open');
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
					$('#htm_table').datagrid('load',myQueryParams);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				$('#htm_table').datagrid('clearSelections');
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
		'width'				: '100%',
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
				$.post(addRecommendMsgURL + ids,function(result){
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

/**
 * 根据频道Id或名字查询频道
 */
function queryChannelByIdOrName(){
	var channelIdOrName = $("#ss-channelSearch").searchbox('getValue');
	var params={};
	if(channelIdOrName){
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
				$('#ss-channel').combogrid('setValue',obj['channelId']);
				$("#htm_table").datagrid("load",myQueryParams);
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	}
};

// 更新计划加精列点击后图片
function updateSchedulaSuperbOp(superb, index) {
	$('#htm_table').datagrid('updateRow',{
		index: index,
		row: {
			superb: superb
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
			<a href="./page_operations_channelWorldV3" title="瀑布流模式">
			<img class="switch-icon" src="./htworld/images/grid-icon.png" style="width:15px;vertical-align:middle;"  /></a>
			<a href="./page_operations_channelWorld" title="列表模式">
				<img class="switch-icon" src="./htworld/images/list-icon.png" style="width:15px;vertical-align:middle;"  /></a>
			<span class="search_label">请选择频道：</span>
			<input id="ss-channel" style="width:100px;" />
			<span id="htm_opt_btn" class="none">
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加频道红人" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除频道红人" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效" plain="true" iconCls="icon-ok">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效" plain="true" iconCls="icon-tip">批量失效</a>
			<a href="javascript:void(0);" onclick="javascript:batchNotify();" class="easyui-linkbutton" title="批量通知" plain="true" iconCls="icon-ok">批量通知</a>
			<a href="javascript:void(0);" onclick="javascript:reIndexed();" class="easyui-linkbutton" title="按照时间重新排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">按照时间重新排序</a>
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
						<td class="leftTd">计划更新时间：</td>
						<td><input id="schedula" name="schedula" class="easyui-datetimebox" required="true"></td>
					</tr>
					<tr>
						<td class="leftTd">时间间隔(分钟)：</td>
						<td>
							<input  name="minuteTimeSpan" class="easyui-numberbox" value="5" style="width:171px"/>
						</td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="channelId" id="channelId_indexed" /></td>
					</tr>
					<tr class="none">
					<td colspan="2"><input type="text" name="wids" id="wids_indexed" /></td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="superbWids" id="superbWids_indexed" /></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReIndexForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_indexed').window('close');$('#htm_table').datagrid('clearSelections');">取消</a>
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
	
	<div id="search-channel-tb" style="padding:5px;height:auto" class="none">
		<input id="channel-searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
	</div>
	</div>
</body>
</html>
