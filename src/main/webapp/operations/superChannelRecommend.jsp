<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>精选频道推荐</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
	var maxId=0,
	channelMaxId = 0,
	channelQueryParam ={},
	
	loadDateUrl="./admin_op/superbChannel_querySuperbChannelRecommend",
	addUrl="./admin_op/superbChannel_insertSuperbChannelRecommend",
	delUrl="./admin_op/superbChannel_batchDeleteSuperbChannelRecommend?idsStr=",
	updateUrl="./admin_op/superbChannel_batchUpdateSuperbChannelRecommendValid?idsStr=",
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
		$("#htm_table").datagrid(
				{
					title  :"精选频道推荐列表",
					width  :1200,
					pageList : [10,30,50,100],
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
						{field : 'channelId',title: '频道ID',align : 'center',width : 80},
						{field : 'channelName',title: '频道名称',align : 'center',width : 100},
						{field : 'valid',title : '有效性',align : 'center', width: 45,
				  			formatter: function(value,row,index) {
				  				if(value == 1) {
				  					img = "./common/images/ok.png";
				  					return "<img title='有效' class='htm_column_img'  src='" + img + "'/>";
				  				}
				  				img = "./common/images/tip.png";
				  				return "<img title='无效' class='htm_column_img' src='" + img + "'/>";
				  			}
				  		},
						{field : 'addDate', title:'创建时间',align : 'center' ,width : 130	},
						{field : 'modifyDate', title:'最后修改时间',align : 'center' ,width : 130},
						{field : 'operatorName',title: '最后操作者',align : 'center',width : 80}
						
					]],
					onLoadSuccess:myOnLoadSuccess,
					onBeforeRefresh : myOnBeforeRefresh
				
				}	
		);
	}
	
	
	$(function(){
		$('#ss-channel').combogrid({
			panelWidth : 320,
		    panelHeight : 490,
		    loadMsg : '加载中，请稍后...',
		    multiple : false,
		    required : false,
		   	idField : 'channelId',
		    textField : 'channelName',
		    url : './admin_op/v2channel_queryOpChannelByAdminUserId',
		    pagination : true,
		    remoteSort : false,
		    columns:[[
				{field : 'channelId',title : 'ID', align : 'center',width : 60},
				{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
					formatter:function(value,row,index) {
						return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				},
				{field : 'channelName',title : '频道名称',align : 'center',width : 120},
				{field : 'channelName',title : '频道名称',align : 'center',width : 120},
				{field : 'channelDesc',title : '频道描述',align : 'center',width : 200},
			]],
			queryParams:channelQueryParam,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > channelMaxId) {
						channelMaxId = data.maxId;
						channelQueryParam.maxId = channelMaxId;
					}
				}
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
	
		$('#htm_add').window({
			title : '添加频道精选推荐',
			modal : true,
			width : 490,
			height : 150,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-tip',
			resizable : false
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
	
	
	function del(){
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			var ids = [];
			for(var i=0;i<rows.length;i+=1){		
				ids.push(rows[i]['id']);	
				rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
			}	
			$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
			$('#htm_table').datagrid('loading');
			$.post(delUrl+ids,function(result){
				$('#htm_table').datagrid('loaded');
				if(result['result'] == 0) {
					$.messager.alert('提示',result['msg']);
					$("#htm_table").datagrid("reload");
				} else {
					$.messager.alert('提示',result['msg']);
				}
				
			});				
		}	
	}
	
	function update(valid){
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			var ids = [];
			for(var i=0;i<rows.length;i+=1){		
				ids.push(rows[i]['id']);	
				rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
			}	
			$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
			$('#htm_table').datagrid('loading');
			$.post(updateUrl+ids,{
				'valid':valid
			},function(result){
				$('#htm_table').datagrid('loaded');
				if(result['result'] == 0) {
					$.messager.alert('提示',result['msg']);
					$("#htm_table").datagrid("reload");
				} else {
					$.messager.alert('提示',result['msg']);
				}
				
			});				
		}	
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
	
	
	function addSubmit(){
		var channelId = $("#ss-channel").combogrid('getValue');
		if(channel == "")return;
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		$.post(addUrl,{
			'channelId':channelId
		},function(result){
			$('#htm_add .opt_btn').show();
			$('#htm_add .loading').hide();
			if(result['result'] == 0) {
				$.messager.alert('提示',result['msg']);
				$("#htm_table").datagrid("reload");
				$('#htm_add').window('close');
			} else {
				$.messager.alert('提示',result['msg']);
			}
		},"json");
		
		
	}
	
	function updateSuperbChannelRecommendCache(){
		$('#htm_table').datagrid('loading');
		$.post("./admin_op/superbChannel_updateSuperbChannnelRecommendCache",function(result){
			if(result['result'] == 0) {
				$.messager.alert('提示',result['msg']);
				$("#htm_table").datagrid("loaded");
			} else {
				$.messager.alert('提示',result['msg']);
			}
		});
	}
</script>
</head>
<body>
	<div id="main">
		<div id="tb">
			<!-- copy from starRecommendSchedula.jsp -->
			<a href="javascript:void(0);" onclick="javascript:addInit();" class="easyui-linkbutton" title="添加" plain="true" iconCls="icon-add" id="delBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">批量删除</a>
			<a href="javascript:void(0);" onclick="javascript:update(1);" class="easyui-linkbutton" title="批量有效" plain="true" iconCls="icon-ok" id="reIndexedBtn">批量有效</a>
			<a href="javascript:void(0);" onclick="javascript:update(0);" class="easyui-linkbutton" title="批量失效" plain="true" iconCls="icon-tip" id="reIndexedBtn">批量失效</a>
			<a href="javascript:void(0);" onclick="javascript:updateSuperbChannelRecommendCache();" class="easyui-linkbutton" title="刷新缓存" plain="true" iconCls="icon-converter" id="reIndexedBtn">刷新缓存</a>
		</div>
		<table id="htm_table"></table>
		 
		 <!-- 添加记录 -->
		<div id="htm_add">
			<form id="add_form"  method="post">
				<table class="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">频道：</td>
							<td><input id="ss-channel" style="width:200px;" ></td>
							
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
</body>
</html>