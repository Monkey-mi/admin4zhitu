<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广场话题管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/uploadify/jquery.uploadify.js"></script>
<link rel="stylesheet" type="text/css" href="${webRootPath }/base/js/jquery/uploadify/uploadify.css">
<script type="text/javascript">
var htmTableTitle = "广场话题维护", //表格标题
	htmTableWidth = 630,
	htmTableHeight = 450,
	loadDataURL = "./admin_op/op_querySquarePushTopic", //数据装载请求地址
	deleteURI = "./admin_op/op_deleteSquarePushTopics?ids=", //删除请求地址
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : 'id',align : 'center',width : 120},
		{ field : 'topic',title : '话题',align : 'center',width : 320 },
		{ field : 'topicPath',title : '图片',align : 'center',width : 90, height:63,
			formatter:function(value,row,index) {
				return "<img width='80px' height='53px' class='htm_column_img' src='" + value + "'/>";
			}
		},
		{ field : 'dateAdded',title : '添加日期', align : 'center',width : 120, 
			formatter:function(value,row,index){
				return new Date(value).format("MM/dd hh:mm"); 
			}
		}],
	addWidth = 500, //添加信息宽度
	addHeight = 190, //添加信息高度
	addTitle = "添加广场话题", //添加信息标题
	htmTablePageList = [5];
	
//初始化添加窗口
function initAddWindow(json) {
	var addForm = $('#add_form');
	clearFormData(addForm);
	$("#squarePush_add").focus();  //光标定位
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
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							//$("#htm_edit_table div").each(function(){$(this).html('');});  //清除form验证缓存	
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

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	$("#topicPath_add")
	.formValidator({onshow:"请输入图片链接",onfocus:"例:http://test.png",oncorrect:"该链接可用！"})
	.inputValidator({min:2,max:150,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"链接错误"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#topicPathHd_add")
	.formValidator({onshow:"请输入图片链接",onfocus:"例:http://test.png",oncorrect:"该链接可用！"})
	.inputValidator({min:2,max:150,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"链接错误"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
}


</script>
</head>
<body>
	<table id="htm_table"></table>

	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_op/op_saveSquarePushTopic" method="post">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">文字：</td>
						<td><input type="text" name="topic" id="topic_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="topic_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">图片：</td>
						<td><input type="text" name="topicPath" id="topicPath_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="topicPath_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">高清图片：</td>
						<td><input type="text" name="topicPathHd" id="topicPathHd_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="topicPathHd_addTip" class="tipDIV"></div></td>
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