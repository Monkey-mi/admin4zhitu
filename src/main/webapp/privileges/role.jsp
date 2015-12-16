<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function() {
	$('#groupIds_update').combobox({ 
		editable: false,
        valueField:'id',  
        textField:'groupName',  
        width: 420,
        panelWidth: 420,
        panelHeight:'auto',
        multiple:true
	});
	
	$('#groupIds_add').combobox({ 
		url:'./admin/privileges_queryAllPrivilegesGroup',
		editable: false,
        valueField:'id',  
        textField:'groupName',
        width: 420,
        panelWidth: 420,
        panelHeight:'auto',
        multiple:true,
        onLoadSuccess : function() {
        	var groups = $('#groupIds_add').combobox('getData');
        	$('#groupIds_update').combobox('loadData', groups);
        }
	});
	
});
var htmTableTitle = "系统角色信息维护", //表格标题
	loadDataURL = "./admin/privileges_queryRole", //数据装载请求地址
	deleteURI = "./admin/privileges_deleteRole?ids=", //删除请求地址
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'id',align : 'center',width : 120},
		{field : 'roleName',title : '角色名',align :'center',width :120},
		{field : 'roleDesc',title : '角色描述',align : 'center',width : 200},
		{field : 'opt',title : '操作',width : 120,align : 'center',rowspan : 1,
			formatter : function(value, rowData, rowIndex ) {
				var uri = './admin/privileges_queryRoleById?id='+ rowData.id; //更新窗口请求地址			
				return "<a title='修改信息' class='updateInfo' href='javascript:htmWindowUpdate(\""
						+ uri + "\")'>【修改】</a>";
			}
		}
	],
	addWidth = 550, //添加信息宽度
	addHeight = 180, //添加信息高度
	addTitle = "添加系统角色信息", //添加信息标题

	updateWidth = 550, //修改信息宽度
	updateHeight = 180, //修改信息高度
	updateTitle = "修改系统角色信息"; //修改信息标题


//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	commonTools.clearFormData(addForm);
	$("#roleName_add").focus();  //光标定位
	$('#groupIds_add').combobox('clear');
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							commonTools.clearFormData(addForm);  //清空表单数据	
							//$("#htm_edit_table div").each(function(){$(this).html('');});  //清除form验证缓存	
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('出错提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#roleName_add").formValidator({empty:false,onshow:"请输入角色名称",onfocus:"例如:管理员",oncorrect:"该名称可用！",onerror:"不能为空"})
	.inputValidator({min:2,max:50,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由汉字及字母组成"})
	.ajaxValidator({type:"post",url:"./admin/privileges_checkRoleNameExist",datatype:"json",
		beforesend:function(){
			if(validateSubmitOnce==true){
				validateSubmitOnce=false;
			}else{
				return false;
			}
		},
		success:function(json){
			if(json['msg']==-1){
				return true;
			}else{
				return false;
			}
		},
		error:function(){$.messager.alert('出错提示',"连接错误，请重试...");},onerror:"该名称已经存在",
		onwait:"正在校验..."});
	
	$("#roleDesc_add").formValidator({empty:true,onshow:"请输入1~140个描述字符",onfocus:"请输入1~140个描述字符",oncorrect:"描述成功"})
	.inputValidator({max:140,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"请输入1~140个描述字符"});
	
}


//初始化更新窗口
function initUpdateWindow(json) {
	var updateForm = $('#update_form');
	commonTools.clearFormData(updateForm);
	$("#id_update").val(json['id']);
	$("#roleName_update").val(json['roleName']);
	$("#roleDesc_update").val(json['roleDesc']);
	$('#groupIds_update').combobox('clear');
	var groupIds = json['groupIds'];
	for(var i = 0 ; i < groupIds.length; i++) {
		$('#groupIds_update').combobox('select', groupIds[i]);
	}
}

//提交表单，以后补充装载验证信息
function loadUpdateFormValidate() {
	var updateForm = $('#update_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : updateForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$.post(updateForm.attr("action"),updateForm.serialize(),
					function(result){
						if(result['result'] == 0) {
							$('#htm_update').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							commonTools.clearFormData(updateForm);  //清空表单数据	
							//$("#htm_update_table div").each(function(){$(this).html('');});  //清除form验证缓存	
							$("#htm_table").datagrid("reload");
						} else {
							$.messager.alert('出错提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#roleName_update").formValidator({empty:false,onshow:"请输入角色名称",onfocus:"例如:管理员",oncorrect:"该名称可用！",onerror:"不能为空"})
	.inputValidator({min:2,max:50,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由汉字及字母组成"});
	
	$("#roleDesc_update").formValidator({empty:true,onshow:"请输入1~140个描述字符",onfocus:"请输入1~140个描述字符",oncorrect:"描述成功"})
	.inputValidator({max:140,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"请输入1~140个描述字符"});
	
}


</script>
</head>
<body>
	<table id="htm_table"></table>

	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin/privileges_saveRole" method="post">
			<table id="htm_edit_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">角色名称：</td>
						<td><input type="text" name="role.roleName" id="roleName_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="roleName_addTip" class="tipDIV"></div></td>
					
					</tr>
					<tr>
						<td class="leftTd">角色描述：</td>
						<td><input type="text" name="role.roleDesc" id="roleDesc_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="roleDesc_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">权限分组：</td>
						<td colspan="2"><input type="text" name="groupIds" id="groupIds_add" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center"><a
							class="easyui-linkbutton" iconCls="icon-ok"
							onclick="$('#add_form').submit();">添加</a> <a
							class="easyui-linkbutton" iconCls="icon-undo"
							onclick="document.getElementById('add_form').reset()">重置</a> <a
							class="easyui-linkbutton" iconCls="icon-cancel"
							onclick="$('#htm_add').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>


	<!-- 更新记录 -->
	<div id="htm_update">
		<form id="update_form" action="./admin/privileges_updateRole" method="post">
			<table id="htm_update_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">角色名称：</td>
						<td><input type="text" name="role.roleName" id="roleName_update" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="roleName_updateTip" class="tipDIV"></div></td>
					
					</tr>
					<tr>
						<td class="leftTd">角色描述：</td>
						<td><input type="text" name="role.roleDesc" id="roleDesc_update" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="roleDesc_updateTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">权限分组：</td>
						<td colspan="2"><input type="text" name="groupIds" id="groupIds_update" onchange="validateSubmitOnce=true;"/></td>
					</tr> 
					<tr class="none">
						<td class="leftTd">ID(隐藏):</td>
						<td><input type="text" id="id_update" name="id" /></td>
						<td class="rightTd"><div id="id_updateTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center"><a
							class="easyui-linkbutton" iconCls="icon-ok"
							onclick="$('#update_form').submit();">修改</a> <a
							class="easyui-linkbutton" iconCls="icon-undo"
							onclick="document.getElementById('update_form').reset()">重置</a> <a
							class="easyui-linkbutton" iconCls="icon-cancel"
							onclick="$('#htm_update').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>