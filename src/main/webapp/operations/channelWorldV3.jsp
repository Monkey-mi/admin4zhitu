<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道织图管理-瀑布流模式</title>
<jsp:include page="../common/headerJQuery11.jsp"></jsp:include>
<jsp:include page="../common/bootstrapHeader.jsp"></jsp:include>
<jsp:include page="../common/tourlistHeader.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<link type="text/css" rel="stylesheet" href="${webRootPath }/htworld/css/htworldListV2.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<script type="text/javascript" src="${webRootPath }/operations/js/channelWorldV3.js"></script>
<script type="text/javascript">
var maxId = 0,
	channelId = 0,
	init = function() {
	},
	myRowStyler = function(index, row) {
		if(!row.valid)
			return inValidWorld;
		return null;
	},
	hideIdColumn = true,
	myIdField = "channelWorldId",
	recordIdKey = "channelWorldId",
	uidKey = "userId",
	loadDataURL = "./admin_op/channel_queryChannelWorld", //数据装载请求地址
	updateValidURL = "./admin_op/channel_updateWorldValidByWID",
	updateSuperbURL = "./admin_op/channel_updateWorldSuperbByWID",
	searchChannelMaxId = 0,
	searchChannelQueryParams = {
		'maxId':searchChannelMaxId
	},
	 onBeforeInit = function() {
		showPageLoading();
	},
	
	onAfterInit = function() {
	
		initWorldBoxWidth();
		
		$("#pagination").pagination({
			pageList: [30,50,100,10],
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					maxId = 0;
					myQueryParams.maxId = maxId;
				}
			},
			onRefresh : function(pageNumber, pageSize) {
				loadData(pageNumber, pageSize);
			},
			onSelectPage : function(pageNumber, pageSize) {
				loadData(pageNumber, pageSize);
			},
			onChangePageSize : function(pageSize) {
				loadData(1, pageSize);
			}
		});
		
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
		
		getChannelIdFromCookie();
		
		removePageLoading();
		$("#main").show();
		
	};
	
/**
 * 查询织图
 */
function queryWorld() {
	maxId = 0;
	myQueryParams['world.maxId'] = maxId;
	myQueryParams['world.channelId'] = $('#ss-channel').combogrid('getValue');
	myQueryParams['world.valid'] = $('#ss-valid').combobox('getValue');
	myQueryParams['world.superb'] = $('#ss-superb').combobox('getValue');
	loadData(myQueryParams.page, myQueryParams.rows);
}

/**
 * 从Cookie获取频道id
 */
function getChannelIdFromCookie(){
	var channelId = baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID");
	var channelName = baseTools.getCookie("CHANNEL_WORLD_CHANNEL_NAME");
	if(channelId){
		myQueryParams['world.channelId'] = channelId;
		$("#ss-channel").combogrid('setValue', channelId);
		$("#ss-channel").combogrid('setText', channelName);
		loadData(1,30);
	}
}

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
function updateValid(channelId, worldId, valid, index) {
	$.post(updateValidURL,{
		"channelId":channelId,
		"worldId":worldId,
		"valid":valid
	}, function(result){
		if(result['result'] == 0) {
			updateValue(index, 'channelWorldValid', valid);
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
	},"json");
}

/**
 * 更新精选标记
 */
function updateSuperb(channelId, worldId, superb, index) {
	$.post(updateSuperbURL,{
		"channelId":channelId,
		"worldId":worldId,
		"superb":superb
	}, function(result){
		if(result['result'] == 0) {
			updateValue(index, 'superb', superb);
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
	},"json");
}

/**
 * 更新删除状态
 */
function updateDeleteStatus(channelId, worldId, valid, index) {
	var tip = "确定要删除吗?";
	if(valid != 2) {
		tip = "确定要恢复吗?";
	}
	$.messager.confirm('提示', tip, function(r){
	if (r){
		updateValid(channelId, worldId, valid, index);
	}
});
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<img id="page-loading" alt="" src="${webRootPath }/common/images/girl-loading.gif" />
	<nav id="top" class="navbar navbar-default navbar-fixed-top">
	  	<div class="container">
	  		<a href="./page_operations_channelWorldV3" title="瀑布流模式">
			<img class="switch-icon" src="./htworld/images/grid-icon.png" /></a>
			<a href="./page_operations_channelWorld" title="列表模式">
				<img class="switch-icon" src="./htworld/images/list-icon.png" /></a>
	  		<span class="search_label">频道: </span>
	  		<input id="ss-channel" style="width:150px;" />
	  		<span class="search_label">有效状态: </span>
	  		<select id="ss-valid" class="easyui-combobox" name="phoneCode" style="width:100px;">
				<option value="">所有状态</option>
		        <option value="1">生效</option>
		        <option value="0">未生效</option>
		        <option value="2">已经删除</option>
	  		</select>
	  		<span class="search_label">加精状态: </span>
	  		<select id="ss-superb" class="easyui-combobox" name="phoneCode" style="width:100px;">
				<option value="">所有状态</option>
		        <option value="1">精选</option>
		        <option value="0">非精选</option>
	  		</select>
	  		<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryWorld();">查询</a>
	  		<div id="pagination" style="display:inline-block; vertical-align:middle; margin:0 0 1px 20px;">
			</div>
	  	</div>
	</nav>
	 
	<div id="world-box">
	</div>

	<div id="search-channel-tb" style="padding:5px;height:auto" class="none">
		<input id="channel-searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
	</div>
	
	</div>
</body>
</html>
