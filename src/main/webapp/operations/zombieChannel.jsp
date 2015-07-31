<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>马甲频道管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
	var searchChannelMaxId = 0,
	searchChannelQueryParams = {
		'maxId':searchChannelMaxId
	};
	var maxId=0,
	loadDateUrl="./admin_op/zbChannel_queryZombieChannel",
	addUrl="./admin_op/zbChannel_insertZombieChannel",
	delUrl = "./admin_op/zbChannel_batchDeleteZombieChannel?idsStr="
	tableQueryParams = {},
	tableInit = function() {
		tableLoadDate(1);
	};
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			tableQueryParams.maxId = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				tableQueryParams.maxId = maxId;
			}
		}
	};
	
	function tableLoadDate(pageNum){
		$("#htm_table").datagrid({
			title  :"精品马甲管理",
			width  :1200,
			pageList : [10,30,50,100,300],
			pageSize : 10,
			loadMsg:"加载中....",
			url	   :	loadDateUrl,
			queryParams : tableQueryParams,
			remoteSort: true,
			pagination: true,
			idField   :'id',
			pageNumber: pageNum,
			toolbar:'#tb',
			columns: [[
				{field :'ck',checkbox:true},
				{field :'id',title:'ID',align:'center',width:80},
				{field : 'userId',title: '用户ID',align : 'center',width : 180},
				{field : 'userName',title: '用户名称',align : 'center',width : 180},
				{field : 'channelId',title: '频道ID',align : 'center',width : 180},
				{field : 'channelName',title: '频道名称',align : 'center',width : 180}
				
			]],
			onLoadSuccess:myOnLoadSuccess,
			onBeforeRefresh : myOnBeforeRefresh
		});
		var p = $("#htm_table").datagrid("getPager");
		p.pagination({
		});
	}
	
	
	
	$(function(){
		$('#htm_add').window({
			title : '添加马甲等级',
			modal : true,
			width : 490,
			height : 170,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function(){
				$("#i-userId").val('');
				$("#ss-channel").combogrid('clear');
				$("#i-id").val('');		
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
		
		tableInit();
		$("#main").show();
	});
	
	/**
	 * 显示载入提示
	 */
	function showPageLoading() {
		var $loading = $("<div></div>");
		$loading.text('载入中...').addClass('page_loading_tip');
		$("body").append($loading);
	}

	/**
	 * 移除载入提示
	 */
	function removePageLoading() {
		$(".page_loading_tip").remove();
	}
	
	/**
	* 批量删除
	*/
	function del(){
		$.messager.confirm('更新缓存','确定要将选中的内容删除?',function(r){
			if(r){
				update(delUrl);
			}
		});
	}
	
	
	
	function update(url){
		var rows = $('#htm_table').datagrid('getSelections');	
		var ids = [];
		for(var i=0;i<rows.length;i+=1){		
			ids.push(rows[i]['id']);	
		}	
		$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
		$('#htm_table').datagrid('loading');
		$.post(url+ids,function(result){
			$('#htm_table').datagrid('loaded');
			if(result['result'] == 0) {
				$.messager.alert('提示',result['msg']);
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('提示',result['msg']);
			}
			
		});	
	}
	
	/**
	 * 判断是否选中要删除的记录
	 */
	function isSelected(rows) {
		if(rows.length > 0){
			return true;
		}else{
			$.messager.alert('操作失败','请先选择记录，再执行操作!','error');
			return false;
		}
	}
	
	
	function addInit(){
		$('#htm_add').window('open');
	}
	
	function updateInit(id){
		$("#i-id").val(id);
		$('#htm_add').window('open');
	}
	
	function addSubmit(){
		var userId = $("#i-userId").val();
		var channelId = $("#ss-channel").combogrid('getValue');
		var id = $("#i-id").val();
		var url="";
		if(id){
			url = updateUrl+id;
		}else{
			url = addUrl;
		}
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		$.post(url,{
			'userId':userId,
			'channelId':channelId
		},function(result){
			$('#htm_add .opt_btn').show();
			$('#htm_add .loading').hide();
			if(result['result'] == 0) {
				tableQueryParams.maxId=0;
				$("#htm_table").datagrid("reload");
				$('#htm_add').window('close');
			} else {
				$.messager.alert('提示',result['msg']);
			}
		},"json");
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
	
	$(function() {
		
		/*
		add by mishengliang 07-31-2015
		马甲频道管理中加入频道ID搜索
		*/
		$('#channelId').combogrid(
						{
							panelWidth : 440,
							panelHeight : 330,
							loadMsg : '加载中，请稍后...',
							pageList : [ 4, 10, 20 ],
							pageSize : 4,
 							toolbar : "#search-channel-tb01", 
							multiple : false,
							required : false,
							idField : 'id',
							textField : 'channelName',
							url : './admin_op/channel_searchChannel',
							pagination : true,
							columns : [ [
									{
										field : 'id',
										title : 'id',
										align : 'center',
										width : 80
									},
									{
										field : 'channelIcon',
										title : 'icon',
										align : 'center',
										width : 60,
										height : 60,
										formatter : function(value, row, index) {
											return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
										}
									}, {
										field : 'channelName',
										title : '频道名称',
										align : 'center',
										width : 280
									} ] ],
 							queryParams : searchChannelQueryParams,
							onLoadSuccess : function(data) {
								if (data.result == 0) {
									if (data.maxId > searchChannelMaxId) {
										searchChannelMaxId = data.maxId;
										searchChannelQueryParams.maxId = searchChannelMaxId;
									}
								}
							}, 
							onSelect : function(rowIndex,rowData) {
								channelId = rowData.id;
								tableQueryParams.channelId = channelId;
								$("#htm_table").datagrid('load',tableQueryParams);
							}
						});
		
		/*
		add by mishengliang 07-31-2015
		在马甲频道管理中加入用户ID搜索
		*/
		$('#userId').searchbox({
			searcher : function(value){
				tableQueryParams.userId = value;
				$("#htm_table").datagrid("load",tableQueryParams);
			}
		});
	});
	
	function searchChannel01() {
		searchChannelMaxId = 0;
		maxId = 0;
		var query = $('#channel-searchbox01').searchbox('getValue');
		searchChannelQueryParams.maxId = searchChannelMaxId;
		searchChannelQueryParams.query = query;
		$("#channelId").combogrid('grid').datagrid("load",searchChannelQueryParams);
	}
	

	
</script>
</head>
<body>
	<div id="main" class="none">
		<div id="tb">
			<a href="javascript:void(0);" onclick="javascript:addInit();" class="easyui-linkbutton" title="添加" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<input id="userId">
			<input id="channelId">
		</div>
		<table id="htm_table"></table>
		<!-- 添加记录 -->
		<div id="htm_add">
			<form id="add_form"  method="post">
				<table class="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">马甲ID：</td>
							<td>
								<input id="i-userId" style="width:220px;">
							</td>
						</tr>
						<tr>
							<td class="leftTd">频道：</td>
							<td><input id="ss-channel" style="width:223px;"/></td>
						</tr>
						<tr>
							<td class="none"><input id="i-id"></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit();">添加</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	<div id="search-channel-tb" style="padding:5px;height:auto" class="none">
		<input id="channel-searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
	</div>
		<div id="search-channel-tb01" style="padding:5px;height:auto" class="none">
		<input id="channel-searchbox01" searcher="searchChannel01" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
	</div>
</body>
</html>