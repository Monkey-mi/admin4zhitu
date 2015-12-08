<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附近标签织图管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<script type="text/javascript">

var maxId = 0;

	init = function() {
		myQueryParams.valid = 1;
		loadPageData(initPage);
	};
	
	hideIdColumn = false;
	htmTableTitle = "主题列表"; // 表格标题
	toolbarComponent = '#tb';
	loadDataURL = "admin_op/near_queryNearLabelWorld"; // 数据装载请求地址
	saveNearLabelWorld = "admin_op/near_addNearLabelWorld"; // 保存织图标签
	deleteNearLabelWorld = "admin_op/near_batchDeleteNearLabelWorld"; // 删除织图标签
	
	showWorldAndInteractPage="page_htworld_htworldShow";
	
	isUpdate = false;
	rowIndex = 0;
	
	htmTablePageList = [10,20];
	myIdField = 'id';
	myPageSize = 6;
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['maxId'] = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['maxId'] = maxId;
			}
		}
		if(data.total == 0){
			   $(this).datagrid('appendRow', { itemid: '<div style="text-align:center;color:red">没有相关记录！</div>' }).datagrid('mergeCells', { index: 0, field: 'itemid', colspan: 13 })
               $(this).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
		}
	};
	
	// 拥有者ID搜索中用到的参数
	var userMaxId = 0;
	var userQueryParams = {'maxId':userMaxId};
	
	columnsFields = [
	    {field:'ck',checkbox:true},            
		{field : 'id',title : 'ID', hidden:false},
		
		phoneCodeColumn,
		authorAvatarColumn,
		authorIdColumn,
		{field : 'authorName',title : '作者',align : 'center',formatter: function(value, row, index) {
			if(row.authorId != 0) {
				if(row.trust == 1) {
					return "<a title='移出信任列表.\n推荐人:"
						+row.trustOperatorName+"\n最后修改时间:"
						+row.trustModifyDate+"' class='passInfo pointer' href='javascript:removeTrust(\"" 
						+ row.authorId + "\",\"" + row.worldId + "\",\""+ row.latestValid + "\",\"" + index + "\")'>"
						+value
						+ "<sup><span style='border: solid 1px red;webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;-webkit-box-shadow: #666 0px 0px 10px;-moz-box-shadow: #666 0px 0px 10px;box-shadow: #666 0px 0px 10px;'>"
						+row.trustOperatorId+"</span></sup></a>";
				}else if(row.trustOperatorId == 0){
					return "<a title='添加到信任列表' class='updateInfo pointer' href='javascript:addTrust(\"" + row.authorId + "\",\""+row.worldId
						+ "\",\"" + row.worldId + "\",\"" + row.latestValid + "\",\"" + index + "\")'>"+value+"</a>";
				}
				return "<a title='移出信任列表.\n删除信任的人:"
						+row.trustOperatorName+"\n最后修改时间:"
						+row.trustModifyDate+"' class='updateInfo pointer' href='javascript:addTrust(\"" 
						+ row.authorId + "\",\"" + row.worldId + "\",\"" + row.latestValid + "\",\"" + index + "\")'>"
						+value
						+ "<sup><span style='border: solid 1px red;webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;-webkit-box-shadow: #666 0px 0px 10px;-moz-box-shadow: #666 0px 0px 10px;box-shadow: #666 0px 0px 10px;'>"
						+row.trustOperatorId+"</span></sup></a>";
			} else if(baseTools.isNULL(value)) {
				return "织图用户";
			}
		}},
		clickCountColumn,
		likeCountColumn,
		commentCountColumn,
		 {field : 'worldId',title : '织图ID',align : 'center'},
		worldDescColumn,
		{
  			field: "titleThumbPath",
  			title: "预览",
  			align: "center",
  			formatter: function(value,row,index){
  				return "<a title='播放织图' class='updateInfo' href='javascript:commonTools.showWorld(\"" + row.shortLink + "\")'><img width='60px' height='60px' src='" + baseTools.imgPathFilter(value,'../base/images/bg_empty.png') + "' /></a>";
 			}
  		},
  		{field : 'nearLabelName',title : '附近标签名', align : 'center'}
		],

	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$('#htm_table').datagrid({
			autoRowHeight:true,
			onSelect: function(index,row){
				// 选择操作时刷新展示重新排序所选择的数量
				$("#reSerialCount").text($(this).datagrid('getSelections').length);
			},
			onUnselect: function(index,row){
				// 取消选择操作时刷新展示重新排序所选择的数量
				$("#reSerialCount").text($(this).datagrid('getSelections').length);
			}
		});
		
		$('#htm_serial').window({
			title : '重新排序',
			modal : true,
			width : 650,
			height : 155,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
		});
		
		$('#htm_edit').window({
			title: '添加织图',
			modal : true,
			width : 300,
			height : 205,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				$("#edit_form .opt_btn").show();
				$("#edit_form .loading").hide();
				
				$('#edit_form').form('reset');
				$("#edit_loading").show();
			}
		});
		
		$('#labelName').combogrid({
		    panelWidth : 330,
		    panelHeight : 330,
		    loadMsg : '加载中，请稍后...',
			pageList : [10,20],
			toolbar:"#search-label-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'labelName',
		    url : './admin_op/near_queryNearLabel',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'id',align : 'center',width : 80},
				{field : 'labelName',title : '标签名', align : 'center',width : 180, height:60},
				{field : 'cityName',title : '城市名',align : 'center',width : 60}
		    ]]
/* 		    queryParams:searchChannelQueryParams, */
/* 		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > searchChannelMaxId) {
						searchChannelMaxId = data.maxId;
						searchChannelQueryParams.maxId = searchChannelMaxId;
					}
				}
		    	
		    	$('#ss-channel').combogrid("setValue", baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID"));
		    	$('#ss-channel').combogrid("grid").datagrid("clearSelections");
		    }, */
		});
		var p = $('#labelName').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
/* 				if(pageNumber <= 1) {
					searchChannelMaxId = 0;
					searchChannelQueryParams.maxId = searchChannelMaxId;
				} */
			}
		});
		
		
		$('#labelNameT').combogrid({
		    panelWidth : 330,
		    panelHeight : 330,
		    loadMsg : '加载中，请稍后...',
			pageList : [10,20],
			toolbar:"#search-labelT-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'labelName',
		    url : './admin_op/near_queryNearLabel',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'id',align : 'center',width : 80},
				{field : 'labelName',title : '标签名', align : 'center',width : 180, height:60},
				{field : 'cityName',title : '城市名',align : 'center',width : 60}
		    ]]
/* 		    queryParams:searchChannelQueryParams, */
/* 		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > searchChannelMaxId) {
						searchChannelMaxId = data.maxId;
						searchChannelQueryParams.maxId = searchChannelMaxId;
					}
				}
		    	
		    	$('#ss-channel').combogrid("setValue", baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID"));
		    	$('#ss-channel').combogrid("grid").datagrid("clearSelections");
		    }, */
		});
		var p = $('#labelNameT').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
/* 				if(pageNumber <= 1) {
					searchChannelMaxId = 0;
					searchChannelQueryParams.maxId = searchChannelMaxId;
				} */
			}
		});
		
		
		removePageLoading();
		$("#main").show();
	};


/**
 * 标签织图排序
 */
function reSerial() {
	$('#htm_serial .opt_btn').show();
	$('#htm_serial .loading').hide();
	$("#serial_form").find('input[name="reIndexId"]').val('');
	// 获取频道表格中勾选的集合
	var rows = $("#htm_table").datagrid('getSelections');
	$('#serial_form .reindex_column').each(function(i){
		if(i<rows.length)
			$(this).val(rows[i]['id']);
	});
	// 打开添加窗口
	$("#htm_serial").window('open');
}

function submitSerialForm() {
	var $form = $('#serial_form');
	if($form.form('validate')) {
		$('#htm_serial .opt_btn').hide();
		$('#htm_serial .loading').show();
		$('#serial_form').form('submit', {
			url: $form.attr('action'),
			success: function(data){
				var result = $.parseJSON(data);
				$('#htm_serial .opt_btn').show();
				$('#htm_serial .loading').hide();
				if(result['result'] == 0) { 
					$('#htm_serial').window('close');  // 关闭添加窗口
					maxId = 0;
					 /* myQueryParams['channel.maxId'] = maxId;  */
					loadPageData(1);
					$('#htm_table').datagrid("unselectAll");
					$("#reSerialCount").text(0);
				} else {
					$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
				}
				
			}
		});
	}
}

//打开增加标签织图窗口
function openAddWindow(){
	$('#htm_edit').window('open');
}

//增加标签织图
function addWorldLabel(){
	if(isUpdate){//更新主题,留下更新的接口
		
	}else{//增加主题
		var worldAuthorId = 0;
		var nearLabelId = $('#labelName').combogrid('getValue');
		var worldId = $('#worldId').val();
		$.post(saveNearLabelWorld,{
			'nearLabelId':nearLabelId,
			'worldId':worldId,
			'worldAuthorId':worldAuthorId
		},function(result){
				$('#htm_edit').window('close');
				$.messager.alert("温馨提示：","添加成功！");
				$('#htm_table').datagrid("reload");
		},"json");
		
	}
}


//删除专属主题
function deleteTheme(){
	var ids = [];
	var rows = $('#htm_table').datagrid('getSelections');
	for(var i = 0;i < rows.length; i++){
		ids.push(rows[i]['id']);	
	}
	ids = ids.join();
	
	$.post(deleteNearLabelWorld,{
		'idsStr':ids
	},
	function(result){
		$.messager.alert("温馨提示：","删除成功！");
		$('#htm_table').datagrid("reload");
		$('#htm_table').datagrid("unselectAll");
	},"json");
}

function searchChannel() {
	searchChannelMaxId = 0;
	maxId = 0;
	var query = $('#channel-searchbox').searchbox('getValue');
	searchChannelQueryParams.maxId = searchChannelMaxId;
	searchChannelQueryParams.query = query;
/* 	$("#ss-channel").combogrid('grid').datagrid("load",searchChannelQueryParams); */
}

function searchByWorldId() {
	var worldId = $('#label_worldId').searchbox('getValue');
	
	maxId = 0;
	nearLabelId = 0;
	myQueryParams.maxId = maxId;
	myQueryParams.nearLabelId = nearLabelId;
	myQueryParams.worldId = worldId;
	$("#htm_table").datagrid("load", myQueryParams);
}

function searchWorldByLabel(){
	var nearLabelId = $('#labelNameT').combogrid('getValue');
	maxId = 0;
	worldId = 0;
	myQueryParams.maxId = maxId;
	myQueryParams.nearLabelId = nearLabelId;
/* 	myQueryParams.worldId = worldId; */
	$("#htm_table").datagrid("load", myQueryParams);
}

function searchLabelTById() {
	labelMaxId = 0;
	var labelName = $('#labelT-searchbox').searchbox('getValue');
	labelQueryParams = {
		'maxId' : labelMaxId,
		'nearLabel.labelName' : labelName
	};
	$("#labelNameT").combogrid('grid').datagrid("load",labelQueryParams);
}

function searchLabelById() {
	labelMaxId = 0;
	var labelName = $('#label-searchbox').searchbox('getValue');
	labelQueryParams = {
		'maxId' : labelMaxId,
		'nearLabel.labelName' : labelName
	};
	$("#labelName").combogrid('grid').datagrid("load",labelQueryParams);
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:openAddWindow();" class="easyui-linkbutton" title="添加关系" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:deleteTheme();" class="easyui-linkbutton" title="批量删除织图" plain="true" iconCls="icon-cut" id="cutBtn">批量删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序
			<span id="reSerialCount" type="text" style="font-weight:bold;">0</span></a>
			标签名：<input id="labelNameT" name="labelNameT" style="width:100px" />
			<a href="javascript:void(0);" onclick="javascript:searchWorldByLabel();" plain="true" class="easyui-linkbutton" iconCls="icon-search" id="search_btn">查询</a>
			<input id="label_worldId" searcher="searchByWorldId" class="easyui-searchbox" prompt="输入织图ID搜索" style="width:150px;" />
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit" align="center">
			<form id="edit_form" >
				<table id="htm_edit_table" style="width:250px;line-height:40px;">
					<tbody>
						<tr>
							<td align="center">织图ID:<input id="worldId" name="worldId" style="width:100px" /></td>
						</tr>
						<tr>
							<td align="center">附近标签:<input id="labelName" name="labelName" style="width:100px" /></td>
						</tr>						
						<tr>
							<td align="center">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addWorldLabel();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_edit').window('close');">取消</a>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<!-- 频道重新排序 -->
		<div id="htm_serial">
			<form id="serial_form" action="admin_op/near_updateNearLabelWorldSerial" method="post">
				<table class="htm_edit_table" width="580">
					<tbody>
						<tr>
							<td class="leftTd">频道ID：</td>
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
							<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitSerialForm();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-remove" onclick="javascript:$('#serial_form').form('reset');$('#htm_table').datagrid('clearSelections');$('#reSerialCount').text(0);">清空</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_serial').window('close');">取消</a>
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
	</div>
	
	<div id="search-label-tb" style="padding:5px;height:auto" class="none">
		<input id="label-searchbox" searcher="searchLabelById" class="easyui-searchbox" prompt="标签名 搜索" style="width:200px;"/>
	</div>
	
	<div id="search-labelT-tb" style="padding:5px;height:auto" class="none">
		<input id="labelT-searchbox" searcher="searchLabelTById" class="easyui-searchbox" prompt="标签名 搜索" style="width:200px;"/>
	</div>
	
</body>
</html>