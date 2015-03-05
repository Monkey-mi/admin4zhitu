<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String userId = request.getParameter("userId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>粉丝管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer}"></script>
<script type="text/javascript">
var maxId = 0,
	userId = <%=userId%>,
	init = function() {
		myQueryParams = {
			'maxId' : maxId,
			'userId' : userId
		},
		loadPageData(initPage);
	},
	htmTableTitle = "粉丝列表", //表格标题
	htmTableWidth = 980,
	loadDataURL = "./admin_user/interact_queryFollow", //数据装载请求地址
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId
			}
		}
	},
	uidKey = "userId",
	columnsFields = [
		{field : recordIdKey,title : 'id',align : 'center',width : 120},
		userAvatarColumn,
		{field : 'userId',title : '用户ID',align : 'center', sortable: true, width : 60},
		userNameColumn,
		sexColumn,
		concernCountColumn,
		followCountColumn,
		{field : 'worldCount',title:'织图',align : 'center', width : 60,
			formatter: function(value,row,index){
				var uri = "page_user_userWorldInfo?userId="+row.userId;
				return "<a title='显示织图' class='updateInfo' href='javascript:showUserWorld(\""+uri
					+"\")'>"+value+"</a>"; 
			}
		},
		likedCountColumn,
		keepCountColumn,
		phoneCodeColumn,
		platformCodeColumn,
		registerDateColumn
		],
	htmTablePageList = [10,20,50];
	var addWidth = 530; //添加信息宽度
	var addHeight = 160; //添加信息高度
	var addTitle = "添加粉丝信息"; //添加信息标题
	
//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	clearFormData(addForm);
	$("#userId_add").val(userId);
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

//显示用户织图
function showUserWorld(uri){
	$.fancybox({
		'margin'			: 20,
		'width'				: '10',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

</script>
</head>
<body>
	<table id="htm_table"></table>
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_user/interact_saveFollows" method="post">
			<table id="htm_edit_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">马甲IDs：</td>
						<td><input type="text" name="ids" id="ids_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="ids_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">粉丝总数：</td>
						<td><input type="text" name="count" id="count_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="count_addTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none">
						<td class="leftTd">用户ID：</td>
						<td><input type="text" name="userId" id="userId_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userId_addTip" class="tipDIV"></div></td>
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