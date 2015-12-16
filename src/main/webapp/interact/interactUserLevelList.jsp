<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户等级列表管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">
var maxId = 0,
	worldId = 0,
	myRowStyler = 0,
	htmTableTitle = "用户等级列表", //表格标题
	loadDataURL = "./admin_interact/userlevelList_QueryUserlevelList", //数据装载请求地址
	deleteURI = "./admin_interact/userlevelList_DeleteUserlevelByIds?ids=",
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
		{field : 'user_id',title : '用户ID',align : 'center',width : 80},
		{field : 'user_name',title: '用户名',align : 'center',width : 140},
		{field : 'user_level_id',title : '等级id',align : 'center',width : 80},
		{field : 'level_description',title : '等级描述',align : 'center',width : 80},
		{field : 'addDate',title:'添加日期',algin:'center',width:120},
  		{field : 'modifyDate',title:'修改日期',algin:'center',width:120},
  		{field : 'operatorName',title:'操作人',algin:'center',width:120},
		{field : 'validity', title:'有效性', align : 'center',width : 45,
			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='有效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='无效' class='htm_column_img' src='" + img + "'/>";
  			}
		}		
	],
	
	addWidth = 500, //添加信息宽度
	addHeight = 190, //添加信息高度
	addTitle = "添加等级用户"; //添加信息标题
	
	
//初始化添加窗口
function initAddWindow() {
	$('#htm_add .opt_btn').show();
	$('#htm_add .loading').hide();
	var addForm = $('#add_form');
	commonTools.clearFormData(addForm);
	$('#userLevelId_userLevel').combobox('clear');
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
							commonTools.clearFormData(addForm);  //清空表单数据	
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

	//根据用户id来查找用户等级
 	function searchByUid(){
 		var uid = $("#ss_uid").searchbox('getValue');
 		myQueryParams.userId = uid;
 		myQueryParams.maxId = 0;
		$("#htm_table").datagrid('load',myQueryParams);
 	}
 	
	//根据时间来查找
 	function searchByTypeTime(){
		var timeType = $("#ss_time_type").combobox("getValue");
		myQueryParams.timeType = timeType;
		myQueryParams.beginTime = $("#beginDate").datetimebox("getValue");
		myQueryParams.endTime  = $("#endDate").datetimebox("getValue");
		$("#htm_table").datagrid('load',myQueryParams);
	}


</script>

</head>
<body>
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加用户到互动列表" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<input id="ss_uid" class="easyui-searchbox" prompt="请输入用户id" style="width:150px;" searcher="searchByUid">
			<div style="float:right;">
				<select id="ss_time_type" class="easyui-combobox"  style="width:100px;">
			        <option value="1">添加日期</option>
			        <option value="2">修改日期</option>
		   		</select>
				<span>起始时间：</span>
		   		<input id="beginDate" name="beginDate" class="easyui-datetimebox"/>
		   		<span>结束时间：</span>
		   		<input id="endDate" name="endDate" class="easyui-datetimebox"/>
		   		<a href="javascript:void(0);" onclick="javascript:searchByTypeTime();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	   		</div>
   		</div>
	</div>  
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_interact/userlevelList_AddUserlevel" method="post">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">用户ID：</td>
						<td><input type="text" name="userId" id="userId_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userId_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">等级：</td>
						<td><input class="easyui-combobox" name="userLevelId" id="userLevelId_userLevel" onchange="validateSubmitOnce=true;" style="width:204px"
								data-options="valueField:'id',textField:'level_description',url:'./admin_interact/userlevel_QueryUserLevel'"/></td>
						<td class="rightTd"><div id="userLevelId_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">有效性：</td>
						<td>
							<select id="validitySelect" name="validity" style="width:204px">
								<option value="1">TRUE</option>
								<option value="0">FALSE</option>
							</select>
						</td>
						<td class="rightTd"><div id="validity_addTip" class="tipDIV"></div></td>
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