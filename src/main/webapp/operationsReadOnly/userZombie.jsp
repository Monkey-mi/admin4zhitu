<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>马甲用户管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js"></script>
<script type="text/javascript">
var htmTableTitle = "马甲用户维护", //表格标题
	htmTableWidth = 1150,
	recordIdKey = "recommendId",
	uidKey = "id",
	loadDataURL = "./admin_op/user_queryZombieUser", //数据装载请求地址
	deleteURI = "./admin_op/user_deleteZombieUsers?ids=", //删除请求地址
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : 'id',align : 'center',width : 120},
		phoneCodeColumn,
		userAvatarColumn,
		{field : 'id',title : '用户ID',align : 'center', sortable: true, width : 60},
		userNameColumn,
		sexColumn,
		concernCountColumn,
		followCountColumn,
		worldCountColumn,
		likedCountColumn,
		keepCountColumn,
		signatureColumn,
		userLabelColumn,
		{field : 'shield',title : '操作',align : 'center',width : 45,
			formatter: function(value,row,index){
				if(value == 1) {
					img = "./common/images/undo.png";
					return "<img title='解除屏蔽' class='htm_column_img pointer' onclick='javascript:unShield(\""+ row['id'] + "\",\"" + index + "\")' src='" + img + "'/>";
				}
				if(row.valid == 0) {
					return '';
				}
				return "<a title='点击屏蔽马甲' class='updateInfo' href='javascript:shield(\""+ row['id'] + "\",\"" + index + "\")'>" + '屏蔽'+ "</a>";
			}
		},
		platformCodeColumn,
		recommenderColumn
		],
	addWidth = 530, //添加信息宽度
	addHeight = 220, //添加信息高度
	addTitle = "添加马甲用户", //添加信息标题
	
	htmTablePageList = [10],
	toolbarComponent = [{
		id:'btnadd',
		text:'添加',
		iconCls:'icon-add',
		handler:htmUI.htmWindowAdd
		},'-',{
			id:'btncut',
			text:'转换',
			iconCls:'icon-add',
			handler:function(){
				initAddIdWindow();
				$("#add_id").window('open');
				loadAddIdFormValidate();
			}
		},'-',{
			id:'btncut',
			text:'删除',
			iconCls:'icon-cut',
			handler:function(){
				htmDelete(recordIdKey);
			}
		}
		],
		onBeforeInit = function() {
			showPageLoading();
		},
		onAfterInit = function() {
			$("#labelIds_label").combotree({
				url:'./admin_user/label_queryUserLabelTree',
				multiple:true,
				required:true,
				cascadeCheck:false,
				onlyLeafCheck:true,
				onCheck:checkLabel
			});
			
			$('#htm_label').window({
				modal : true,
				width : 500,
				top : 10,
				height : 190,
				title : '更新标签',
				shadow : false,
				closed : true,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				iconCls : 'icon-add',
				resizable : false,
				onClose : function() {
					$("#label_form").hide();
					$("#label_loading").show();
					$("#labelCount_label").text('0');
					$("#sex_label").text('');
					$("#userName_label").text('');
					$("#htm_label .opt_btn").show();
					$("#htm_label .loading").hide();
					$("#labelIds_label").combotree('clear');
					$("#labelIds_label").combotree('tree').tree('collapseAll');
				}
			});
			
			$('#add_id').window({
				title : '转换用户为马甲',
				modal : true,
				width : addWidth,
				height : 130,
				shadow : false,
				closed : true,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				iconCls : 'icon-add',
				resizable : false
			});
			removePageLoading();
			$("#main").show();
		};
	
//初始化添加窗口
function initAddWindow(json) {
	var addForm = $('#add_form');
	clearFormData(addForm);
	$("#userName_add").focus();  //光标定位
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
				$('#htm_table').datagrid('loading');
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						formSubmitOnce = true;
						$('#htm_table').datagrid('loaded');
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							myQueryParams.maxId = 0;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#userName_add").formValidator({onshow:"请输入用户名字",onfocus:"例如:lynch",oncorrect:"该名字可用！"})
	.inputValidator({min:2,max:20,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由汉字及字母组成"})
	.ajaxValidator({type:"post",url:"./admin_user/user_checkUserNameExists",datatype:"json",
		beforesend:function(){
			if(validateSubmitOnce==true){
				validateSubmitOnce=false;
			}else{
				return false;
			}
		},
		success:function(json){
			if(json['result']==0){
				return true;
			}else{
				return false;
			}
		},
		error:function(){alert("连接错误，请重试...");},onerror:"该用户名已被使用",
		onwait:"正在校验用户名，请稍候..."});
	
	$("#loginCode_add").formValidator({onshow:"请输入邮箱账号",onfocus:"例:123456",oncorrect:"该账号可用！"})
	.inputValidator({min:4,max:50,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"至少大于4个字符"})
	.functionValidator({fun:cheEmail, onerror:"邮箱格式错误"})
	.ajaxValidator({type:"post",url:"./admin_user/user_checkLoginCodeExists",datatype:"json",
		beforesend:function(){
			if(validateSubmitOnce==true){
				validateSubmitOnce=false;
			}else{
				return false;
			}
		},
		success:function(json){
			if(json['result']==0){
				return true;
			}else{
				return false;
			}
		},
		error:function(){alert("连接错误，请重试...");},onerror:"该用账号已被使用",
		onwait:"正在校验..."});
	
	$("#userAvatar_add")
	.formValidator({onshow:"请输入头像链接",onfocus:"例:http://test.png",oncorrect:"该链接可用！"})
	.inputValidator({min:2,max:150,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"链接错误"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#userAvatarL_add")
	.formValidator({onshow:"请输入头像链接",onfocus:"例:http://test.png",oncorrect:"该链接可用！"})
	.inputValidator({min:2,max:150,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"链接错误"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
}

function cheEmail(e){
    var RegExp = /^\w+@\w+.\w+.?\w+$/; //我这里只做了简单的验证高级验证请自行补全
    if(!RegExp.test(e)) {
        return false;
    }
    return true;
}

//初始化添加窗口
function initAddIdWindow() {
	var addForm = $('#add_id_form');
	clearFormData(addForm);
	$("#ids_add").focus();  //光标定位
}

//提交表单，以后补充装载验证信息
function loadAddIdFormValidate() {
	var addForm = $('#add_id_form');
	$("#ids_add").focus();  //光标定位
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$('#htm_table').datagrid('loading');
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						$('#htm_table').datagrid('loaded');
						if(result['result'] == 0) {
							$('#add_id').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							myQueryParams.maxId = 0;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#ids_add")
	.formValidator({empty:false,onshow:"请输入用户id",onfocus:"例如:12,13",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
}

/**
 * 屏蔽织图
 */
function shield(userId,index) {
	$("#htm_table").datagrid('loading');
	$.post("././admin_op/user_shieldZombie",{
		'userId':userId
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'shield',1);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 解除屏蔽
 */
function unShield(userId,index) {
	$("#htm_table").datagrid('loading');
	$.post("././admin_op/user_unShieldZombie",{
		'userId':userId
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'shield',0);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}


</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_op/user_saveZombieUser" method="post">
			<table id="htm_edit_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">用户名：</td>
						<td><input type="text" name="userName" id="userName_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userName_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">邮箱：</td>
						<td><input type="text" name="loginCode" id="loginCode_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="loginCode_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">头像：</td>
						<td><input type="text" name="userAvatar" id="userAvatar_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userAvatar_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">大头像：</td>
						<td><input type="text" name="userAvatarL" id="userAvatarL_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userAvatarL_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<div id="add_id">
		<form id="add_id_form" action="./admin_op/user_saveZombieUserByIds" method="post">
			<table class="htm_edit_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">用户IDs：</td>
						<td><input type="text" name="ids" id="ids_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="ids_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_id_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#add_id').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 用户标签设置 -->
	<div id="htm_label">
		<span id="label_loading" style="margin:60px 0 0 220px; position:absolute; display:none; ">加载中...</span>
		<form id="label_form" action="./admin_user/label_saveLabelUser" method="post" class="none">
			<table class="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">用户名：</td>
						<td colspan="2">
							<span id="userName_label"></span>
						</td>
					</tr>
					<tr>
						<td class="leftTd">性别：</td>
						<td colspan="2">
							<span id="sex_label"></span>
						</td>
					</tr>
					<tr>
						<td class="leftTd">标签：</td>
						<td>
							<select id="labelIds_label" name="labelId" style="width:205px;" ></select>
						</td>
						<td class="rightTd">
						已选择标签：<span id="labelCount_label">0</span>/5
						<a class="easyui-linkbutton" style="vertical-align: middle; margin-bottom: 3px;" title="清空标签" plain="true" iconCls="icon-remove" onclick="clearLabelSelect();"></a>
						</td>
					</tr>
					<tr class="none">
						<td class="leftTd">用户ID：</td>
						<td colspan="2">
							<input id="userId_label" name="userId" type="text" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitLabelForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_label').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">保存中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	</div>

</body>
</html>