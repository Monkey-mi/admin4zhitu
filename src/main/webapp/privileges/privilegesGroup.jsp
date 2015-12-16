<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限分组管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/uploadify/jquery.uploadify.js"></script>
<link rel="stylesheet" type="text/css" href="${webRootPath }/base/js/jquery/uploadify/uploadify.css">
<script type="text/javascript">
var htmTableTitle = "权限分组信息维护", //表格标题
	loadDataURL = "./admin/privileges_queryPrivilegesGroup", //数据装载请求地址
	deleteURI = "./admin/privileges_deletePrivilegesGroup?ids=", //删除请求地址
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'id',align : 'center',width : 120},
		{field : 'groupName',title : '分组名',align :'center',width :120},
		{field : 'groupDesc',title : '分组描述',align : 'center',width : 200},
		{field : 'icon',title : '图标',align : 'center',width : 120},
		{field : 'opt',title : '操作',width : 150,align : 'center',rowspan : 1,
			formatter : function(value, rowData, rowIndex ) {
				var uri = './admin/privileges_queryPrivilegesGroupById?id='+ rowData.id; //更新窗口请求地址			
				return "<a title='修改信息' class='updateInfo' href='javascript:htmWindowUpdate(\""
						+ uri + "\")'>【修改】</a>";
			}
		}
	],
	addWidth = 550, //添加信息宽度
	addHeight = 180, //添加信息高度
	addTitle = "添加权限分组信息", //添加信息标题

	updateWidth = 550, //修改信息宽度
	updateHeight = 210, //修改信息高度
	updateTitle = "修改权限分组信息"; //修改信息标题


//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	commonTools.clearFormData(addForm);
	$("#icon_add").val('icon-sys');
	$("#groupName_add").focus();  //光标定位
	
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
	
	$("#groupName_add").formValidator({empty:false,onshow:"请输入分组名称",onfocus:"例如:织图管理",oncorrect:"该名称可用！",onerror:"不能为空"})
	.inputValidator({min:2,max:50,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由汉字及字母组成"})
	.ajaxValidator({type:"post",url:"./admin/privileges_checkPrivilegesGroupNameExist",datatype:"json",
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
		onwait:"正在校验..."});;
	
	$("#groupDesc_add").formValidator({empty:true,onshow:"请输入1~140个描述字符",onfocus:"请输入1~140个描述字符",oncorrect:"描述成功"})
	.inputValidator({max:140,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"请输入1~140个描述字符"});
	
	$("#icon_add").formValidator({empty:true,onshow:"请输入图标链接",onfocus:"例如:icon-sys",oncorrect:"图标链接正确"})
	.inputValidator({max:140,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由字母和横线组成"});
}


//初始化更新窗口
function initUpdateWindow(json) {
	var updateForm = $('#update_form');
	commonTools.clearFormData(updateForm);
	$("#id_update").val(json['id']);
	$("#groupName_update").val(json['groupName']);
	$("#groupDesc_update").val(json['groupDesc']);
	$("#icon_update").val(json['icon']);
	$("#serial_update").val(json['serial']);
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
	
	$("#groupName_update").formValidator({empty:false,onshow:"请输入分组名称",onfocus:"例如:织图管理",oncorrect:"该名称可用！",onerror:"不能为空"})
	.inputValidator({min:2,max:50,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由汉字及字母组成"});
	
	$("#groupDesc_update").formValidator({empty:true,onshow:"请输入1~140个描述字符",onfocus:"请输入1~140个描述字符",oncorrect:"描述成功"})
	.inputValidator({max:140,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"请输入1~140个描述字符"});
	
	$("#icon_update").formValidator({empty:true,onshow:"请输入图标链接",onfocus:"例如:icon-sys",oncorrect:"图标链接正确"})
	.inputValidator({max:140,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由字母和横线组成"});
	
	$("#serial_update").formValidator({empty:false,onshow:"请输入权限序号",onfocus:"例如:1",oncorrect:"序号正确"})
	.inputValidator({min:1,max:140,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
}


</script>
</head>
<body>
	<table id="htm_table"></table>

	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin/privileges_savePrivilegesGroup" method="post">
			<table id="htm_edit_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">分组名称：</td>
						<td><input type="text" name="privilegesGroup.groupName" id="groupName_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="groupName_addTip" class="tipDIV"></div></td>
					
					</tr>
					<tr>
						<td class="leftTd">分组描述：</td>
						<td><input type="text" name="privilegesGroup.groupDesc" id="groupDesc_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="groupDesc_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">ICON：</td>
						<td><input type="text" name="privilegesGroup.icon" id="icon_add" onchange="validateSubmitOnce=true;" value="icon-sys" /></td>
						<td class="rightTd"><div id="icon_addTip" class="tipDIV"></div></td>
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
		<form id="update_form" action="./admin/privileges_updatePrivilegesGroup" method="post">
			<table id="htm_update_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">分组名称：</td>
						<td><input type="text" name="privilegesGroup.groupName" id="groupName_update" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="groupName_updateTip" class="tipDIV"></div></td>
					
					</tr>
					<tr>
						<td class="leftTd">分组描述：</td>
						<td><input type="text" name="privilegesGroup.groupDesc" id="groupDesc_update" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="groupDesc_updateTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">ICON：</td>
						<td><input type="text" name="privilegesGroup.icon" id="icon_update" onchange="validateSubmitOnce=true;" value="icon-sys" /></td>
						<td class="rightTd"><div id="icon_updateTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">序号：</td>
						<td><input type="text" name="privilegesGroup.serial" id="serial_update" onchange="validateSubmitOnce=true;" value="icon-sys" /></td>
						<td class="rightTd"><div id="serial_updateTip" class="tipDIV"></div></td>
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