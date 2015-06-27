<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评论标签管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
var maxId = 0,
	worldId = 0,
	htmTableTitle = "标签分组列表", //表格标题
	loadDataURL = "./admin_interact/comment_queryLabelGroup", //数据装载请求地址
	deleteURI = "./admin_interact/comment_deleteLabelGroupByIds?ids=",
	updatePageURL = "./admin_interact/comment_updateLabelByJSON",
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
		{field:recordIdKey,title:'ID',width:60},
		{field:'labelName',title:'标签名称', align:'left', width:200, editor:'text'},
	],
	addWidth = 500, //添加信息宽度
	addHeight = 130, //添加信息高度
	addTitle = "添加一级标签", //添加信息标题
	
	pageButtons = [{
        iconCls:'icon-save',
        text:'保存',
        handler:function(){
        	endEditing();
        	var rows = $("#htm_table").datagrid('getChanges', "updated"); 
        	$("#htm_table").datagrid('loading');
        	$.post(updatePageURL,{
        		'labelJSON':JSON.stringify(rows)
        		},function(result){
        			if(result['result'] == 0) {
        				$("#htm_table").datagrid('acceptChanges');
        			} else {
        				$.messager.alert('失败提示',result['msg']);  //提示失败信息
        			}
        			$("#htm_table").datagrid('loaded');
        		},"json");
        }
    },{
        iconCls:'icon-undo',
        text:'取消',
        handler:function(){
        	$("#htm_table").datagrid('rejectChanges');
        	$("#htm_table").datagrid('loaded');
        }
    }];
	
	
//初始化添加窗口
function initAddWindow() {
	$("#htm_add form").show();
	var addForm = $('#add_form');
	$('#htm_add .opt_btn').show();
	$('#htm_add .loading').hide();
	clearFormData(addForm);
	$("#groupId_add").val('0');
	
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	formSubmitOnce = true;
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
								$('#comments_add').combogrid('clear');
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
	
	
	$("#labelName_add")
	.formValidator({empty:false,onshow:"请输入标签",onfocus:"例如:旅游",oncorrect:"输入正确"})
	.inputValidator({min:1,max:20,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由文字组成"});
	
}


</script>

</head>
<body>
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加标签分组" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除标签分组" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
   		</div>
	</div>  
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_interact/comment_saveLabel" method="post" class="none">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">名称：</td>
						<td><input id="labelName_add" name="labelName" /></td>
						<td class="rightTd"><div id="labelName_addTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none">
						<td class="leftTd">分组：</td>
						<td><input id="groupId_add" name="groupId" /></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
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