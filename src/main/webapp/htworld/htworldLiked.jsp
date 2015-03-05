<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String worldId = request.getParameter("worldId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>喜欢用户管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js"></script>
<script type="text/javascript">
var maxId = 0,
	worldId = <%=worldId%>,
	init = function() {
		myQueryParams = {
			'maxId' : maxId,
			'worldId' : worldId
		};
		loadPageData(initPage);
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId
			}
		}
	},
	htmTableTitle = "喜欢用户维护", //表格标题
	htmTableWidth = 980,
	recordIdKey = "recommendId",
	worldKey = "id",
	loadDataURL = "./admin_ztworld/interact_queryLikedUser", //数据装载请求地址
	columnsFields = [
		{field : recordIdKey,title : 'id',align : 'center',width : 120},
		userAvatarColumn,
		{field : 'userId',title : '用户ID',align : 'center', sortable: true, width : 60},
		userNameColumn,
		sexColumn,
		concernCountColumn,
		followCountColumn,
		worldCountColumn,
		likedCountColumn,
		keepCountColumn,
		signatureColumn,
		phoneCodeColumn,
		platformCodeColumn,
		],
	addWidth = 530, //添加信息宽度
	addHeight = 155, //添加信息高度
	addTitle = "添加喜欢", //添加信息标题
	
	htmTablePageList = [10],
	toolbarComponent = [{
		id:'btnadd',
		text:'添加',
		iconCls:'icon-add',
		handler:htmUI.htmWindowAdd
	}];
	
//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	clearFormData(addForm);
	$("#worldId_add").val(worldId);
	$("#ids_add").focus();  //光标定位
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
	
	$("#ids_add")
	.formValidator({empty:false,onshow:"请输入马甲id",onfocus:"例如:12,13",oncorrect:"设置成功"});
	
	$("#count_add")
	.formValidator({empty:false,onshow:"请输入总数",onfocus:"例如:10",oncorrect:"设置成功"});
}


</script>
</head>
<body>
	<table id="htm_table"></table>
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_ztworld/interact_saveLikedUser" method="post">
			<table id="htm_edit_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">马甲IDs：</td>
						<td><input type="text" name="ids" id="ids_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="ids_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">喜欢总数：</td>
						<td><input type="text" name="count" id="count_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="count_addTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none">
						<td class="leftTd">织图ID：</td>
						<td><input type="text" name="worldId" id="worldId_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="worldId_addTip" class="tipDIV"></div></td>
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
</body>
</html>