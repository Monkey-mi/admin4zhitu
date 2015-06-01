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
	toolbarComponent = [{
		id: 'delete_btn',
		text: '删除',
		iconCls: 'icon-cut',
		handler: function(){
			var rows = $('#htm_table').datagrid('getSelections');
			var channelIds = [];
			for(var i=0;i<rows.length;i++){	
				channelIds.push(rows[i].channelId);
			}
			$.post(deleteURI, {'channelIds' : channelIds.toString()}, function(data){
				$.messager.alert("提示", data.msg);
				$('#htm_table').datagrid('reload');
			});
		}
	}];
	
	columnsFields = [
	                 {field:'checkbox',checkbox:'true',align:'center'},
	                 {field:'channelId',title:'频道ID',width:150},      
	                 {field:'worldBaseCount',title:'织图基数',width:150},
	                 {field:'childBaseCount',title:'所有图片基数',width:150},
	                 {field:'memberBaseCount',title:'频道成员基数',width:150},
	                 {field:'superbBaseCount',title:'加精基数',width:150},
	                 {field:'trueWorldCount',title:'频道真实织图数',width:150}
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
</body>
</html>