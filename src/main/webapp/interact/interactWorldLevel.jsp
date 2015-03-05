<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图等级管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css" />
<style type="text/css">
	#htm_edit_table .leftTd{
		width:133px;
		height:26px;
		text-align:right;
	}
</style>
<script type="text/javascript">
	var maxId=0;
	htmTableTitle = "织图等级管理",
	htmTableWidth = 870,
	htmTablePageList = [5,10,20,30],
	myPageSize = 10,
	myRowStyler= 0,
	loadDataURL = "./admin_interact/worldlevel_QueryWorldlevelList",
	deleteURI = "./admin_interact/worldlevel_DeleteWorldlevelByIds?ids=",
	updatePageURL = "./admin_interact/worldlevel_updateWorldLevelByRowJson";
	addWidth = 500, //添加信息宽度
	addHeight = 400, //添加信息高度
	addTitle = "添加织图等级", //添加信息标题
	
	init = function() {
//		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId
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
	},
	
	columnsFields=[
	               {field:'ck',checkbox:true},
	               {field:recordIdKey,title:'ID',align:'center',width:45},
	               {field:'min_fans_count',title:'最小粉丝数',align:'center',width:80,editor:'numberbox'},
	               {field:'max_fans_count',title:'最大粉丝数',align:'center',width:80,editor:'numberbox'},
	               {field:'min_liked_count',title:'最小喜欢数',align:'center',width:80,editor:'numberbox'},
	               {field:'max_liked_count',title:'最大喜欢数',align:'center',width:80,editor:'numberbox'},
	               {field:'min_comment_count',title:'最小评论数',align:'center',width:80,editor:'numberbox'},
	               {field:'max_comment_count',title:'最大评论数',align:'center',width:80,editor:'numberbox'},
	               {field:'min_play_times',title:'最小播放数',align:'center',width:80,editor:'numberbox'},
	               {field:'max_play_times',title:'最大播放数',align:'center',width:80,editor:'numberbox'},
	               {field:'time',title:'为期',align:'center',width:80,editor:'numberbox'},
	               {field:'level_description',title:'等级描述',align:'center',width:80,editor:'text'},
	               {field:'weight',title:'权重',align:'center',width:80,editor:'numberbox'}
	               ],
	pageButtons = [{
        iconCls:'icon-save',
        text:'保存',
        handler:function(){
        	endEditing();
        	var rows = $("#htm_table").datagrid('getChanges', "updated"); 
        	$("#htm_table").datagrid('loading');
        	$.post(updatePageURL,{
        		'rowJson':JSON.stringify(rows)
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
		$("#minLikedCount").numberbox('clear');
		$("#maxlikedCount").numberbox('clear');
		$("#minCommentCount").numberbox('clear');
		$("#maxCommentCount").numberbox('clear');
		$("#minPlayTimes").numberbox('clear');
		$("#maxPlayTimes").numberbox('clear');
		$('#time').val("24");
	}
	//提交表单，以后补充装载验证信息
	function loadAddFormValidate() {
		var addForm = $("#add_form");
		formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
		$.formValidator.initConfig({
			formid:addForm.attr("id"),
			onsuccess : function() {
				if(formSubmitOnce == true){
					formSubmitOnce = false;
					$('#htm_add .opt_btn').hide();
					$('#htm_add .loading').show();
					$.post(addForm.attr("action"),addForm.serialize(),
							function(result){
						formSubmitOnce=true;
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
		
		$("#minLikedCount").formValidator({empty:false,onshow:"最小喜欢数",onfocus:"例如:10",oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请输入最小喜欢数"});
		$("#maxlikedCount").formValidator({empty:false,onshow:"最大喜欢数",onfocus:"例如:20",oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请输入最大喜欢数"});
		$("#minCommentCount").formValidator({empty:false,onshow:"最小评论数",onfocus:"例如:10",oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请输入最小评论数"});
		$("#maxCommentCount").formValidator({empty:false,onshow:"最大评论数",onfocus:"例如:20",oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请输入最大评论数"});
		$("#minPlayTimes").formValidator({empty:false,onshow:"最小播放数",onfocus:"例如:10",oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请输入最小播放数"});
		$("#maxPlayTimes").formValidator({empty:false,onshow:"最大播放数",onfocus:"例如:10",oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请输入最大播放数"});
		$("#time").formValidator({empty:false,onshow:"为期",onfocus:"例如:10",oncorrect:"输入正确"})
		.inputValidator({min:1,onerror:"请输入为期，最大为24"});
		$("#levelDescription").formValidator({empty:false,onshow:"等级描述",onfocus:"例如:A+",oncorrect:"输入正确"})
		.inputValidator({onerror:"请输入等级描述"});
	}
</script>
</head>
<body>
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加用户等级" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="长处用户等级" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:refreshInteract();" class="easyui-linkbutton" title="更新用户等级" plain="true" iconCls="icon-reload" id="refreshBtn">更新列表</a>
   		</div>
	</div>  
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form action="./admin_interact/worldlevel_AddWorldlevel" id="add_form" class="none" method="post">
			<table id="htm_edit_table" width="480">
			  <tbody>
			  	<tr>
					<td class="leftTd">最小粉丝数：</td>
					<td><input id="minfansCount" name="min_fans_count" class="easyui-numberbox" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="minfansCount_tip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">最大粉丝数：</td>
					<td><input id="maxfansCount" name="max_fans_count" class="easyui-numberbox" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="maxfansCount_tip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">最小喜欢数：</td>
					<td><input id="minLikedCount" name="min_liked_count" class="easyui-numberbox" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="minLikedCount_tip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">最大喜欢数：</td>
					<td><input id="maxlikedCount" name="max_liked_count" class="easyui-numberbox" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="maxlikedCount_tip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">最小评论数：</td>
					<td><input id="minCommentCount" name="min_comment_count" class="easyui-numberbox" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="minCommentCount_tip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">最大评论数：</td>						
					<td><input id="maxCommentCount" name="max_comment_count" class="easyui-numberbox" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="maxCommentCount_tip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">最小播放数：</td>
					<td><input id="minPlayTimes" name="min_play_times" class="easyui-numberbox" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="minPlayTimes_tip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">最大播放数：</td>
					<td><input id="maxPlayTimes" name="max_play_times" class="easyui-numberbox" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="maxPlayTimes_tip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">为期：</td>
					<td><input id="time" name="time" class="easyui-numberbox" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="time_tip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">等级描述：</td>
					<td><input id="levelDescription" name="level_description" type="text" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="levelDescription_tip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">权重：</td>
					<td><input id="weight" name="weight" class="easyui-numberbox" onchange="validateSubmitOnce=true;"/></td>
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