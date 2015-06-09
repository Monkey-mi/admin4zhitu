<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道基数管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

	var maxId = 0;
	loadDataURL = "./admin_op/channelBaseCount_buildChannelBaseCountList";
	deleteURI = "./admin_op/channelBaseCount_deleteChannelBaseCount";
	
	myIdField = "channelId";
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	};

	//定义工具栏
	toolbarComponent = '#tb';
	
	columnsFields = [
         {field:'checkbox',checkbox:'true',align:'center'},
         {field:'channelId',title:'频道ID',width:100},      
         {field:'channelName',title:'频道名称',width:150},      
         {field:'worldBaseCount',title:'织图基数',width:100},
         {field:'trueWorldCount',title:'频道真实织图数',width:100},
         {field:'childBaseCount',title:'所有图片基数',width:100},
         {field:'trueChildCount',title:'所有图片真实数',width:100},
         {field:'memberBaseCount',title:'频道成员基数',width:100},
         {field:'trueMemberCount',title:'频道成员真实数',width:100},
         {field:'superbBaseCount',title:'加精基数',width:100},
         {field:'trueSuperbCount',title:'加精真实数',width:100}
	];
	
	
	/*
	 * 提交表单
	 */
	function submitForm(){
		if (validateForm()) {
			$('#channel_base_count').form('submit', {
				url: $(this).attr('action'),
				success: function(data){
					
					var result = $.parseJSON(data);
					$.messager.alert("提示",result.msg);
					
					// 触发隐藏的reset
					$("input[type=reset]").trigger("click");
					
					$('#htm_table').datagrid('reload');
				},
				error: function(data){
					$.messager.alert("提示","请求失败了！刷新一下页面，重新提交");
				}
			});
		}
	}
	
	/*
	 * 对表单字段进行校验
	 */
	function validateForm(){
		var inputArray = $("#channel_base_count input[type=text]");
		for (var i=0;i<inputArray.length;i++) {
			if (inputArray[i].value == "") {
				$.messager.alert("提示","填入的值都不能为空");
				return false;
			}
		}
		return true;
	}
	
	/*
	 * 批量删除
	 */
	function deleteData(){
		var rows = $('#htm_table').datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("提示", "请勾选要删除的数据");
		} else {
			var channelIds = [];
			for(var i=0;i<rows.length;i++){	
				channelIds.push(rows[i].channelId);
			}
			$.post(deleteURI, {'channelIds' : channelIds.toString()}, function(data){
				$.messager.alert("提示", data.msg);
				$('#htm_table').datagrid('reload');
			});
		}
	}
	
	/**
	 * 根据频道Id或名字查询频道
	 */
	function queryChannelByIdOrName(value,name){
		// 若为非数字值，则输入的为名称
		if (isNaN(value)) {
			myQueryParams['baseCountDto.channelName'] = value;
		} else {
			myQueryParams['baseCountDto.channelId'] = value;
		}
		$('#htm_table').datagrid('load',myQueryParams);
		// 查询完，条件置空
		myQueryParams = {};
	}

</script>
</head>
<body>
	<div id="main_panel" class="easyui-panel"
		style="padding:10px;background:#fafafa;">
		<form id="channel_base_count" action="./admin_op/channelBaseCount_saveChannelBaseCount" method="post">
		    <table cellpadding="5">
	            <tr>
	                <td>频道Id:</td>
	                <td><input name="baseCountDto.channelId" class="easyui-textbox" type="text"></input></td>
	                <td>织图基数:</td>
	            	<td><input name="baseCountDto.worldBaseCount" class="easyui-textbox" type="text"></input></td>
	            </tr>
	            <tr>
	            	<td>所有图片基数:</td>
	            	<td><input name="baseCountDto.childBaseCount" class="easyui-textbox" type="text"></input></td>
	            	<td>频道成员基数:</td>
	            	<td><input name="baseCountDto.memberBaseCount" class="easyui-textbox" type="text"></input></td>
	            </tr>
	            <tr>
	            	<td>加精基数:</td>
	            	<td><input name="baseCountDto.superbBaseCount" class="easyui-textbox" type="text"></input></td>
	            </tr>
	            <tr>
	            	<td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">保存</a></td>
	            	<input type="reset" style="display:none;"></input>
	            </tr>
	        </table>
		</form>
	</div>
	<div style="height:10px"></div>
	<table id="htm_table"></table>
	<div id="tb" style="padding:10px;height:auto" class="none">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cut'" onclick="deleteData()">删除</a>
		<input id="ss-channelSearch" class="easyui-searchbox" style="width:300px;" prompt="输入频道ID或者频道名称（名称支持模糊搜索）" searcher="queryChannelByIdOrName">
	</div>
</body>
</html>