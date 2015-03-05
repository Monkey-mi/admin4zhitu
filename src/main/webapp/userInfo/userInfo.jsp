<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员账号维护</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

var maxId = 0,
	htmTableTitle = "用户账号信息维护", //表格标题
	loadDataURL = "./admin/user_queryUserInfo", //数据装载请求地址
	deleteURI = "./admin/user_deleteUserInfo?ids=", //删除请求地址
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['adminUser.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['adminUser.maxId'] = maxId;
			}
		}
	},
	columnsFields = [
		{field : 'id',title : 'id',align : 'center',width : 120},
		{field : 'userName',title : '用户名',align : 'center',width : 120},
		{field : 'loginCode',title : '账号',align : 'center',width : 120},
		{field : 'valid',title : '有效性',width : 60,align : 'center',
			formatter : function(value, rowData, rowIndex ) {
				if(value == 1) {
					return '有效';
				}
				return '无效';
			}
		},
		{field : 'opt',title : '操作',width : 120,align : 'center',rowspan : 1,
			formatter : function(value, rowData, rowIndex ) {
				var uri = './admin/user_queryUserInfoById?id='+ rowData.id; //更新窗口请求地址			
				return "<a title='修改信息' class='updateInfo' href='javascript:htmWindowUpdate(\""
						+ uri + "\")'>【修改】</a>";
			}
		}
		],
	addWidth = 550, //添加信息宽度
	addHeight = 180, //添加信息高度
	addTitle = "添加用户信息", //添加信息标题

	updateWidth = 550, //修改信息宽度
	updateHeight = 260, //修改信息高度
	updateTitle = "修改用户信息", //修改信息标题

	//分页组件,可以重载
	toolbarComponent = [{
		id:'btnadd',
		text:'添加',
		iconCls:'icon-add',
		handler:htmUI.htmWindowAdd
	}],
	onAfterInit = function() {
		$('#roleIds_update').combobox({ 
			editable: false,
	        valueField:'id',  
	        textField:'roleName',  
	        width: 420,
	        panelWidth: 420,
	        panelHeight:'auto',
	        multiple:true
		});
		
		$('#roleIds_add').combobox({ 
			url:'./admin/privileges_queryAllRole',
			editable: false,
	        valueField:'id',  
	        textField:'roleName',
	        width: 420,
	        panelWidth: 420,
	        panelHeight:'auto',
	        multiple:true,
	        onLoadSuccess : function() {
	        	var roles = $('#roleIds_add').combobox('getData');
	        	$('#roleIds_update').combobox('loadData', roles);
	        }
		});
		removePageLoading();
		$("#main").show();
	};

//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	clearFormData(addForm);
	$("#valid_add").val('1');
	$('#htm_add .opt_btn').show();
	$('#htm_add .loading').hide();
	$('#roleIds_add').combobox('clear');
	$("#userName_add").focus();  //光标定位
	
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	$("#userName_add").focus();  //光标定位
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$('#htm_add .opt_btn').hide();
				$('#htm_add .loading').show();
				//验证成功后以异步方式提交表单
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
						$('#htm_add .opt_btn').show();
						$('#htm_add .loading').hide();
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#userName_add").formValidator({onshow:"请输入用户名字",onfocus:"例如:朱天杰",oncorrect:"该名字可用！"})
	.inputValidator({min:2,max:10,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由汉字及字母组成"})
	.ajaxValidator({type:"post",url:"./admin/user_checkUserName",datatype:"json",
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
		error:function(){alert("连接错误，请重试...");},onerror:"该用户名已被使用",
		onwait:"正在校验用户名，请稍候..."});
	
	$("#loginCode_add").formValidator({onshow:"请输入登录账号",onfocus:"例:123456",oncorrect:"该账号可用！"})
	.inputValidator({min:4,max:50,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"至少大于4个字符"})
	.ajaxValidator({type:"post",url:"./admin/user_checkLoginCode",datatype:"json",
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
		error:function(){alert("连接错误，请重试...");},onerror:"该用账号已被使用",
		onwait:"正在校验..."});
	
	
}


//初始化更新窗口
function initUpdateWindow(json) {
	var updateForm = $('#update_form');
	clearFormData(updateForm);
	$("#valid_update").combobox('clear');
	$('#htm_update .opt_btn').show();
	$('#htm_update .loading').hide();
	$("#id_update").val(json['id']);
	$("#userName_update").val(json['userName']);
	$("#loginCode_update").val(json['loginCode']);
	$("#valid_update").combobox('setValue', json['valid']);
	$('#roleIds_update').combobox('clear');
	var roleIds = json['roleIds'];
	for(var i = 0; i < roleIds.length; i++) {
		$('#roleIds_update').combobox('select',roleIds[i]);
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
				$('#htm_update .opt_btn').hide();
				$('#htm_update .loading').show();
				//验证成功后以异步方式提交表单
				$.post(updateForm.attr("action"),updateForm.serialize(), function(result){
						formSubmitOnce = true;
						$('#htm_update .opt_btn').show();
						$('#htm_update .loading').hide();
						if(result['result'] == 0) {
							$('#htm_update').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(updateForm);  //清空表单数据	
							//loadPageData(1); //重新装载第1页数据
							$("#htm_table").datagrid("reload");
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示更新信息失败
						}
					},"json");				
				return false;
			}
		}
	});
}


</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>

	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin/user_register" method="post">
			<table id="htm_edit_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">用户名：</td>
						<td><input type="text" name="adminUser.userName" id="userName_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userName_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">账号：</td>
						<td><input type="text" name="adminUser.loginCode" id="loginCode_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="loginCode_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">系统角色：</td>
						<td colspan="2"><input type="text" name="roleIds" id="roleIds_add" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr class="none">
						<td class="leftTd">有效性：</td>
						<td colspan="2"><input type="text" name="adminUser.valid" id="valid_add" value="1" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>


	<!-- 更新记录 -->
	<div id="htm_update">
		<form id="update_form" action="./admin/user_updateUserInfo" method="post">
			<table id="htm_update_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">用户名：</td>
						<td><input type="text" name="adminUser.userName" id="userName_update" /></td>
						<td class="rightTd"><div id="userName_updateTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">账号：</td>
						<td><input type="text" name="adminUser.loginCode" id="loginCode_update" /></td>
						<td class="rightTd"><div id="loginCode_updateTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">密码：</td>
						<td><input type="text" name="adminUser.password" id="password_update" /></td>
						<td class="rightTd"><div id="password_updateTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">有效性：</td>
						<td>
							<select id="valid_update" class="easyui-combobox" name="adminUser.valid" style="width:75px;">
						        <option value="0">无效</option>
						        <option value="1">有效</option>
					   		</select>
						</td>
						<td class="rightTd"><div id="valid_updateTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">系统角色：</td>
						<td colspan="2"><input type="text" name="roleIds" id="roleIds_update" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr class="none">
						<td class="leftTd">ID(隐藏):</td>
						<td><input type="text" id="id_update" name="adminUser.id" /></td>
						<td class="rightTd"><div id="id_updateTip" class="tipDIV"></div></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#update_form').submit();">修改</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_update').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
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