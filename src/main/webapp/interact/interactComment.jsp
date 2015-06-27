<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评论库管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
var maxId = 0,
	worldId = 0,
	htmTableTitle = "评论列表", //表格标题
	loadDataURL = "./admin_interact/comment_queryCommentListByLabel", //数据装载请求地址
	deleteURI = "./admin_interact/comment_deleteCommentByIds?ids=",
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId,
			'labelId':1
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
		{field:'content',title:'评论内容',width:520},
		{field:'count',title:'使用数',width:80,align:'center',}
	],
	addWidth = 500, //添加信息宽度
	addHeight = 220, //添加信息高度
	addTitle = "添加评论", //添加信息标题
	
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$("#labelId_add").combotree({
			url:'./admin_interact/comment_queryAllLabelTree',
		});
		$("#ss_labelId").combotree({
			url:'./admin_interact/comment_queryLabelTree?hasTotal=true',
			onSelect:function(rec) {
				loadComment(rec.id);
			}
		});
		$('#ss_comment').searchbox({
		    'searcher':searchComment,
		   	'prompt':'搜索评论'
		});
		
		$('#ss_searchLabel').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_interact/comment_queryAllLabel',
			icons:[{
                iconCls:'icon-add'
            },{
                iconCls:'icon-cut'
            }]
		});
		
		$($('#ss_searchLabel').combobox('textbox')).unbind("keyup").bind("keyup", function (e) {
	        if (e.keyCode == 13) {
	        	var boxLabelId = $("#ss_searchLabel").combobox('getValue');
//				var treeLabelId = $("#labelId_add").combotree('clear');
				var boxLabelText = $("#ss_searchLabel").combobox('getText');
				if(boxLabelId=="" || boxLabelText == boxLabelId){
					//若combobox里输入的text与value一致，表明所输入的text在combobox列表中不存在，则清空输入，不在combotree上增加
					$("#ss_searchLabel").combobox('clear');
					return;
				}
				
				$("#labelId_add").combotree('setValue',boxLabelId);
				$("#ss_searchLabel").combobox('clear');
	        }
	    });
		
		removePageLoading();
		$("#main").show();
	};
	
//初始化添加窗口
function initAddWindow() {
	$("#htm_add form").show();
	var addForm = $('#add_form');
	$('#htm_add .opt_btn').show();
	$('#htm_add .loading').hide();
	clearFormData(addForm);
	
	$("#labelId_add").combotree('setValue', 1);
	
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
				addForm.ajaxSubmit({
			        success:  function(result){
			        	formSubmitOnce = true;
						$('#htm_add .opt_btn').show();
						$('#htm_add .loading').hide();
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							var labelId = $("#labelId_add").combotree('getValue');
							$("#ss_labelId").combotree('setValue', labelId);
							maxId = 0;
							myQueryParams.maxId = maxId;
							myQueryParams.labelId = labelId;
							myQueryParams.comment = "";
							loadPageData(1); //重新装载第1页数据
							clearFormData(addForm);  //清空表单数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
			        },
			        url:       addForm.attr("action"),
			        type:      'post',
			        dataType:  'json', 
				}); 
				return false;
			}
		}
	});
	
	
	$("#content_add")
	.formValidator({empty:true,onshow:"请输入评论内容",onfocus:"例如:呵呵",oncorrect:"输入正确"})
	.inputValidator({min:1,onerror:"请输入评论内容"});
	
}

/**
 * 加载评论
 */
function loadComment(labelId) {
	maxId = 0;
	myQueryParams.maxId = maxId;
	myQueryParams.labelId = labelId;
	myQueryParams.comment = "";
	loadPageData(1);
}

/**
 * 搜索评论
 */
function searchComment() {
	var comment = $("#ss_comment").searchbox("getValue"),
		labelId = $("#ss_labelId").combotree("getValue");
	maxId = 0;
	myQueryParams.maxId = maxId;
	myQueryParams.labelId = labelId;
	myQueryParams.comment = comment;
	loadPageData(1);
}


</script>

</head>
<body>
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加评论" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除评论" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<div style="display: inline; vertical-align:middle; float:right;">
				<span class="search_label">评论标签：</span><input id="ss_labelId" style="width:150px;" />
				<input id="ss_comment" class="easyui-searchbox" prompt="搜索评论" style="width:150px;" />
   			</div>
   		</div>
	</div>  
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_interact/comment_saveComment" method="post" class="none">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">标签：</td>
						<td colspan="2">
					        <input id="labelId_add" name="labelId" style="width:100px;" />
					        <input id="ss_searchLabel" style="width:100px;" ></input>
						</td>
					</tr>
					<tr>
						<td class="leftTd">内容：</td>
						<td><textarea name="content" id="content_add" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="content_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">批量上传：</td>
						<td><input id="commentFile_add" type="file" name="commentFile" /></td>
						<td class="rightTd"><div id="commentFile_addTip" class="tipDIV"></div></td>
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