<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定账号与马甲</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<%-- <jsp:include page="../common/CRUDHeader.jsp"></jsp:include> --%>
<script type="text/javascript">

$(document).ready(function() {
	
	// 定义工具栏
	var toolbar = [{
		id: 'update_btn',
		text: '更新',
		iconCls: 'icon-edit',
		handler: function(){
// 			$('#show_relationship').
		}
	},'-',{
		id: 'delete_btn',
		text: '删除',
		iconCls: 'icon-cut',
		handler: function(){
			
		}
	}];
	
	$('#show_relationship').datagrid({
		url: './admin_user/admin_user_queryAdminAndUserRelationship',
	    columns: [[
			{field:'checkbox',checkbox:'true',align:'center'},
			{field:'id',title:'ID',width:80},      
	        {field:'adminUserName',title:'管理员',width:150},
	        {field:'adminUserId',title:'管理员ID',width:150},
	        {field:'userName',title:'织图用户',width:150},
	        {field:'userId',title:'织图用户ID',width:150},
	        {field:'createTime',title:'创建时间',width:150},
	        {field:'updateTime',title:'更新时间',width:150}
	    ]],
	    toolbar: toolbar,
	    singleSelect: false,
	    checkOnSelect: false
	});
	
	$('#show_relationship').datagrid('hideColumn', 'id');
});

/*
 * 提交表单
 */
function submitForm(){
    $('#admin_user_relationship').form('submit', {
		url: $(this).attr('action'),
		success: function(data){
			
			var result = $.parseJSON(data);
			$.messager.alert('错误提示',result.msg);
			
			// 提示后清空输入框中值
			$('#form_userId').attr("value","");
		},
		error: function(data){
			alert("请求失败了！刷新一下页面，重新提交");
		}
	});
}

</script>
</head>
<body>
	<div id="main_panel" class="easyui-panel"
		style="height:70px;padding:10px;background:#fafafa;">
		<form id="admin_user_relationship" action="./admin_user/admin_user_createAdminAndUserRelationship" method="post">
		    <table cellpadding="5">
                <tr>
                    <td>织图用户Id:</td>
                    <td><input id="form_userId" class="easyui-textbox" type="text" data-options="required:true"></input></td>
                    <td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">绑定</a></td>
                </tr>
            </table>
		</form>
	</div>
	<div style="height:20px"></div>
	<table id="show_relationship"></table>
</body>
</html>