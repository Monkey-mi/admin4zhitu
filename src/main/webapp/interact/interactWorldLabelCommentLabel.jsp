<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户标签--评论标签关联管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">
var maxId = 0,
	worldId = 0,
	htmTableTitle = "用户标签--评论标签关联列表", //表格标题
	loadDataURL = "./admin_interact/worldlabelcommentlabel_QueryWorldLabelCommentLabelList", //数据装载请求地址
	deleteURI = "./admin_interact/worldlabelcommentlabel_DeleteWorldLabeCommentLabel?ids=",//删除请求地址
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId,
		},
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
				myQueryParams.maxId = maxId;
			}
		}
	};
	//分页组件,可以重载
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : 'ID',align : 'center', width : 45},
		{field : 'world_label_id',hidden:'true'},
		{field : 'comment_label_id',hidden:'true'},
		
		{field : 'label_name', title:'织图标签', align : 'center',width : 150},
		{field : 'commentLabelName', title:'评论标签', align : 'center',width : 150},
	],
	
	addWidth = 500, //添加信息宽度
	addHeight = 190, //添加信息高度
	addTitle = "添加织图标签-评论标签关联"; //添加信息标题
	
	
//初始化添加窗口
function initAddWindow() {
	$('#htm_add .opt_btn').show();
	$('#htm_add .loading').hide();
	var addForm = $('#add_form');
	clearFormData(addForm);
	$("#userLabelId").combotree('clear');
	$('#commentLabelId').combotree('clear');
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$('#htm_add .opt_btn').hide();
				$('#htm_add .loading').show();
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						formSubmitOnce = true;
						$('#htm_add .opt_btn').show();
						$('#htm_add .loading').hide();
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							maxId = 0;
							myQueryParams.maxId = maxId;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
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
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加用户到互动列表" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
   		</div>
	</div>  
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_interact/worldlabelcommentlabel_AddWorldLabelCommentLabel" method="post">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">织图标签</td>
						<td><input class="easyui-combotree" name="userLabelId" id="userLabelId" onchange="validateSubmitOnce=true;"
						 	data-options="url:'./admin_interact/worldlabelcommentlabel_GetWorldLabelTree?hasTotal=false',required: true"/></td>
						<td class="rightTd"><div id="userLabelId_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">评论标签</td>
						<td><input class="easyui-combotree" id="commentLabelId" name="commentLabelId" onchange="validateSubmitOnce=true;"  
							data-options="url:'./admin_interact/comment_queryLabelTree?hasTotal=false',required: true"/></td>
						<td class="rightTd"><div id="commentLabelId_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
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
	
</body>
</html>