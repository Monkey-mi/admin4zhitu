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
	var maxId = 0;
	var uidKey = "userId";
//	myRowStyler = function(index, row) {
//		if(!row.valid) {
//			return inValidWorld;
//		}
//		return null;
//	};
	hideIdColumn = true,
	htmTableTitle = "频道织图列表", //表格标题
	toolbarComponent = '#tb',
	htmTablePageList = [10,30,50,100];
	myIdField = "channelWorldId",
	recordIdKey = "channelWorldId",
	loadDataURL = "./admin_op/channel_queryChannelWorld"; //数据装载请求地址
	deleteURI = "./admin_op/channel_deleteChannelWorld?ids=", //删除请求地址
	updateValidURL = "./admin_op/channel_updateChannelWorldValid?ids=",
	addRecommendMsgURL = "./admin_op/channel_addChannelWorldRecommendMsgs?ids=",
	queryChannelURL = "./admin_op/channel_queryChannelById",
	queryChannelByIdOrNameURL = "./admin_op/v2channel_queryOpChannelByIdOrName",// 根据id或民称查询频道
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['world.maxId'] = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['world.maxId'] = maxId;
			}
		}
	};
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
  				switch(value) {
  				case 1:
  					img = "./common/images/ok.png";
  					break;
  				default:
  					img = "./common/images/tip.png";
  					break;
  				}
  				return "<img class='htm_column_img pointer' src='" + img + "'/>";
  			}
  		},
  		{field : 'validSchedula',title : '有效性计划',align : 'center', width: 50,
  			formatter: function(value,row,index) {
  				switch(value) {
  				case 0:
  					return "<img title='已加入有效性计划，未完成' class='htm_column_img'  src='./common/images/tip.png'/>";
  					break;
  				case 1:
  					return "<img title='有效性计划已经完成' class='htm_column_img'  src='./common/images/ok.png'/>";
  					break;
  				default:
  					return "";
  					break;
  				}
  			}  			
  		},
  		{field : 'superbSchedula',title : '加精计划',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				switch(value) {
  				case 0:
  					return "<img title='已加入加精计划，未完成' class='htm_column_img'  src='./common/images/tip.png'/>";
  					break;
  				case 1:
  					return "<img title='加精计划已经完成' class='htm_column_img'  src='./common/images/ok.png'/>";
  					break;
  				default:
  					return "";
  					break;
  				}
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
			title : '按照时间计划重新排序，并生效',
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
		
		$('#batch_to_superb_win').window({
			title : '按照时间计划加精',
			modal : true,
			width : 660,
			height : 235,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false,
			onClose : function() {
				// 关闭时重置批量计划加精的form，因为时间与form中隐藏的ids都要重新赋值
				$('#batch_to_superb_form').form('reset');
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
				myQueryParams['world.maxId'] = maxId;
				myQueryParams['world.channelId'] = row.id;
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
		
		$("#htm_batch_channel").window({
			title : '批量频道织图添加',
			modal : true,
			width : 450,
			height : 150,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function(){
				$("#ss_batch_channel").combobox('clear');
			}
		});
		
		$('#ss_batch_channel').combogrid({
			panelWidth : 440,
			panelHeight : 330,
			loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#search-batch-to-channel-tb",
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
		});
		
		var pp = $('#ss_batch_channel').combogrid('grid').datagrid('getPager');
		pp.pagination({
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

/**
 * 更新有效性
 */
function updateValid(valid) {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(rows.length > 0){
		var tip = "您确定要使已选中的织图生效吗？";
		if(valid == 0) {
			tip = "您确定要使已选中的织图失效吗？";
		}
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
	$("#channelId_indexed").val($('#ss-channel').combogrid('getValue'));
	
	var rows = $("#htm_table").datagrid('getSelections');
	// 定义重新排序织图id集合
	var worldIds = [];
	for(var i=0;i<rows.length;i++){
		worldIds.push(rows[i].worldId);
	}
	
	$("#batch_to_valid_worldIds").val(worldIds.toString());
	
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
	// 频道织图瀑布流模式中，若选择“生效”，即代表，要查询频道织图生效，并且过滤掉织图被用户删除掉，所以flag指定为1
	if ( $('#ss-valid').combobox('getValue') == 1 ) {
		myQueryParams['flag'] = 1;
	}
	// 若选择“未生效”，即代表，要查询频道织图未生效，并且过滤掉织图被用户删除掉，所以flag指定为2
	else if ( $('#ss-valid').combobox('getValue') == 0 ) {
		myQueryParams['flag'] = 2;
	}
	// 若选择“小编删除”，即代表，要查询频道织图被小编删除，所以flag指定为3
	else if ( $('#ss-valid').combobox('getValue') == 2 ) {
		myQueryParams['flag'] = 3;
	}
	// 若选择“用户删除织图”，即代表，要查询织图被用户删除，所以flag指定为4
	else if ( $('#ss-valid').combobox('getValue') == 3 ) {
		myQueryParams['flag'] = 4;
	}
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

/**
 * 批量添加织图到频道
 * @author zhangbo 2015-09-10
 */
function batchToChannel(){
	$("#htm_batch_channel").window('open');
};

/**
 * 批量添加到频道功能的搜索频道
 * @author zhangbo 2015-09-10
 */
function searchBatchToChannel() {
	var params = {
			maxId: 0,
			query: $('#batch-channel-searchbox').searchbox('getValue')
	};
	$("#ss_batch_channel").combogrid('grid').datagrid("load",params);
}

/**
 * 批量保存频道织图到所选频道中
 * @author zhangbo 2015-09-10
 */
function saveBatchWorldToChannelSubmit(){
	var rows = $("#htm_table").datagrid('getSelections');
	for (var i=0;i<rows.length;i++) {
		//该织图进入频道
		$.post("./admin_op/channel_saveChannelWorld",{
			'world.channelId': $("#ss_batch_channel").combogrid('getValue'),
			'world.worldId'  : rows[i].worldId,
			'world.valid'	 : 0,
			'world.notified' : 0
		},function(result){
			if(result['result'] != 0){
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},"json");
	}
	$("#htm_table").datagrid('reload');
	$("#htm_table").datagrid('clearSelections');
	$("#htm_batch_channel").window('close');
}

/**
 * 计划加精设置时间窗口打开
 * @author zhangbo 2015-09-11
 */
function openScheduleSuperbWindow(){
	// 设置
	$("#batch_to_superb_channelId").val($('#ss-channel').combogrid('getValue'));
	var rows = $("#htm_table").datagrid('getSelections');
	var worldIds = [];
	for (var i=0;i<rows.length;i++) {
		//提示添加信息失败
		if ( rows[i].valid == 0 ) {
			$.messager.alert("温馨提示","你所选择的频道织图中有未生效的，不能进行计划加精操作，请选择已生效的织图来加精！");
			$("#batch_to_superb_win").window('close');
			return;
		}
		worldIds.push(rows[i].worldId);
	}
	$("#batch_to_superb_worldIds").val(worldIds.toString());
	
	$("#htm_table").datagrid('clearSelections');
	$("#batch_to_superb_win").window('open');
};

/**
 * 计划加精提交
 * @author zhangbo	2015-09-11
 */
function batchChannelWorldToSuperbSubmit() {
	var $form = $('#batch_to_superb_form');
	if($form.form('validate')) {
		$('#batch_to_superb_form .opt_btn').hide();
		$('#batch_to_superb_form .loading').show();
		$form.form('submit', {
			url: $form.attr('action'),
			success: function(data){
				var result = $.parseJSON(data);
				$('#batch_to_superb_form .opt_btn').show();
				$('#batch_to_superb_form .loading').hide();
				if(result['result'] == 0) {
					$('#batch_to_superb_win').window('close');  // 关闭计划加精设置时间窗口
				} else {
					$.messager.alert('错误提示',result['msg']);  // 提示失败信息
				}
			}
		});
	}
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
	
		<div id="tb" style="padding:5px;height:auto" class="none">
			<div>
<!-- 				<a href="./page_operations_channelWorldV3" title="瀑布流模式">
				<img class="switch-icon" src="./htworld/images/grid-icon.png" style="width:15px;vertical-align:middle;"  /></a>
				<a href="./page_operations_channelWorld" title="列表模式">
				<img class="switch-icon" src="./htworld/images/list-icon.png" style="width:15px;vertical-align:middle;"  /></a> -->
				<span class="search_label">请选择频道：</span>
				<input id="ss-channel" style="width:100px;" />
				<span id="htm_opt_btn" class="none">
				<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除频道红人" plain="true" iconCls="icon-cut">删除</a>
				<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效" plain="true" iconCls="icon-ok">批量生效</a>
				<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效" plain="true" iconCls="icon-tip">批量失效</a>
				<a href="javascript:void(0);" onclick="javascript:batchNotify();" class="easyui-linkbutton" title="批量通知" plain="true" iconCls="icon-ok">批量通知</a>
				<a href="javascript:void(0);" onclick="javascript:reIndexed();" class="easyui-linkbutton" title="按照勾选顺序重新排序，并且生效" plain="true" iconCls="icon-converter" id="reIndexedBtn">计划重新排序并生效</a>
				<a href="javascript:void(0);" onclick="javascript:openScheduleSuperbWindow();" class="easyui-linkbutton" title="按照计划的时间，使频道织图加精" plain="true" iconCls="icon-converter">计划加精</a>
				<select id="ss-notified" class="easyui-combobox" style="width:100px;">
			        <option value="">所有通知状态</option>
			        <option value="0">未通知</option>
			        <option value="1">已通知</option>
		   		</select>
		   		<select id="ss-valid" class="easyui-combobox" style="width:100px;">
			        <option value="">所有状态</option>
			        <option value="1">生效</option>
			        <option value="0">未生效</option>
			        <option value="2">小编删除</option>
			        <option value="3">用户删除织图</option>
		   		</select>
		   		<a href="javascript:void(0);" onclick="javascript:searchWorld();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
		   		
		   		<div style="display:inline-block; vertical-align:middle;">
	   				<a href="javascript:void(0);" onclick="javascript:batchToChannel();" class="easyui-linkbutton" plain="true" iconCls="icon-add">批量添加到频道</a>
	   			</div>
	   			
		   		<span style="display: inline-block; vertical-align:middle; float: right;">
			        <input id="ss-worldId" class="easyui-searchbox" searcher="searchByWID" prompt="输入织图ID搜索" style="width:150px;" />
				</span>
				</span>
	   		</div>
		</div> 

		<!-- 计划重新排序并生效 -->
		<div id="htm_indexed">
			<form id="indexed_form" action="./admin_op/cwSchedula_batchChannelWorldToSortAndValidSchedula" method="post">
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
						<td colspan="2"><input type="text" name="wids" id="batch_to_valid_worldIds" /></td>
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
		
		<!-- 计划加精 -->
		<div id="batch_to_superb_win">
			<form id="batch_to_superb_form" action="./admin_op/cwSchedula_batchChannelWorldToSuperbSchedula" method="post">
				<table class="htm_edit_table" width="660">
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
						<td colspan="2"><input type="text" name="channelId" id="batch_to_superb_channelId" /></td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="wids" id="batch_to_superb_worldIds" /></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="batchChannelWorldToSuperbSubmit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#batch_to_superb_win').window('close');$('#htm_table').datagrid('clearSelections');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">排序中...</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
	
		<!-- 批量添加到频道 -->
		<div id="htm_batch_channel">
			<table class="htm_edit_table" width="450">
				<tr>
					<td class="leftTd">频道名称：</td>
					<td><input id="ss_batch_channel" name="channelId" style="width:200px;" /></td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBatchWorldToChannelSubmit();">确定</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_batch_channel').window('close');">取消</a>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 频道织图toolbar上的搜索频道 -->
		<div id="search-channel-tb" style="padding:5px;height:auto" class="none">
			<input id="channel-searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
		</div>
		
		<div id="search-batch-to-channel-tb" style="padding:5px;height:auto" class="none">
			<input id="batch-channel-searchbox" searcher="searchBatchToChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
		</div>
		
	</div>
</body>
</html>
