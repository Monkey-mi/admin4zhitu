<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>频道与认证对应管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
	var maxId=0,
	loadDateUrl="./admin_op/chIdverId_queryChannelIdVerifyIdForList",
	delUrl="./admin_op/chIdverId_batchDeleteChannelIdVerifyId?idsStr=",
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
					title  :"频道用户管理",
					width  :740,
					pageList : [10,30,50,100],
					loadMsg:"加载中....",
					url	   :	loadDateUrl,
					queryParams : tableQueryParams,
					pagination: true,
					pageNumber: pageNum,
					toolbar:'#tb',
					columns: [[
						{field :'ck',checkbox:true},
						{field :'id',title:'ID',align:'center',width:90},
						{field : 'channelId',title: '频道ID',align : 'center',width : 130},
						{field : 'channelName',title: '频道名称',align : 'center',width : 130},
						{field : 'verifyId',title: '认证ID',align : 'center',width : 130},
						{field : 'verifyName',title: '认证名称',align : 'center',width : 130}
					]],
					onLoadSuccess:myOnLoadSuccess,
					onBeforeRefresh : myOnBeforeRefresh
				}	
		);
		var p = $("#htm_table").datagrid("getPager");
		p.pagination({
		});
	}
	
	
	$(function(){
		tableInit();
		$("#htm_add").window({
			title : '频道与认证关联',
			modal : true,
			width : 420,
			height : 150,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function(){
				$("#wlId").combobox('clear');
				$("#ulId").combobox('clear');
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
		update(delUrl);
	}
	
	function update(url){
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			$.messager.confirm('删除记录','确定要删除所选中的记录',function(r){
				if(r){
					var ids = [];
					for(var i=0;i<rows.length;i+=1){		
						ids.push(rows[i]['id']);	
						rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
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
	
	function htmWindowAdd(){
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		$("#htm_add").window('open');
		$('#htm_add .opt_btn').show();
		$('#htm_add .loading').hide();
	}
	
	function addSubmit(){
		var addForm = $('#add_form');
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		addForm.ajaxSubmit({
			success: function(result){
				$('#htm_add .opt_btn').show();
				$('#htm_add .loading').hide();
				if(result['result'] == 0) {
					$('#htm_add').window('close');  //关闭添加窗口
					$.messager.alert('成功提示',result['msg']);
					$("#htm_table").datagrid('load');
					return false;
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
					$('#htm_add').window('close');  //关闭添加窗口
				}
				return false;
			},
			url:addForm.attr("action"),
			type:'post',
			dateType:'json'
		});
	}
</script>
</head>
<body>
	<div id="main">
		<div id="tb">
			<a href="javascript:void(0);" onclick="htmWindowAdd();" class="easyui-linkbutton" title="添加" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
		</div>
		<table id="htm_table"></table>
		
		<!-- 增加面板 -->
		<div id="htm_add">
			<form action="./admin_op/chIdverId_insertChannelIdVerifyId" id="add_form"  method="post">
			<table id="htm_edit_table" width="400">
			  <tbody>
			  	<tr>
					<td class="leftTd">频道名称：</td>
					<td><input id="wlId" name="channelId" class="easyui-combobox" 
							data-options="valueField:'id',textField:'channelName',url:'./admin_op/channel_queryAllChannel'"/></td>
				</tr>
				<tr>
					<td class="leftTd">认证类型：</td>
					<td><input id="ss-verifyId"  name="verifyId" /></td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit();">添加</a> 
						<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
					</td>
				</tr>
				<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
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