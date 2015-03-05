<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户操作记录</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
var maxId = 0,
	init = function() {
		myQueryParams = {
			'maxId' : maxId,
			'optId' : -1
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
	htmTableTitle = "用户操作记录列表", //表格标题
	htmTableWidth = 1170,
	toolbarComponent = '#tb',
	loadDataURL = "./admin_logger/operation_queryUserOperation", //数据装载请求地址
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : 'id',align : 'center',width : 120},
		{field : 'userId',title : '用户ID', align : 'center',width : 60},
		{field : 'userName',title : '用户名', align : 'center',width : 120},
  		{field : 'optName',title : '操作名称',align : 'center',width : 120,
			formatter: function(value,row,index){
  				return "<span title='" +row['optDesc']+ "' class='updateInfo'>" + value + "</span>";
  			}
  		},
  		{field : 'optInterface', title : '操作接口', align : 'center', width : 380, 
  			formatter: function(value,row,index){
  				return "<span title='" +value+ "' class='updateInfo'>" + value + "</span>";
  			}
  		},
  		{field : 'optArgs', title : '参数列表', align : 'center', width : 280, 
  			formatter: function(value,row,index){
  				return "<span title='" +value+ "' class='updateInfo'>" + value + "</span>";
  			}
  		},
  		{field : 'optDate', title : '日期', align : 'center', width : 120, 
  			formatter: function(value,row,index){
  				return "<span title='" +value+ "' class='updateInfo'>" + value + "</span>";
  			}
  		}
  	],
	addWidth = 520, //添加信息宽度
	addHeight = 130, //添加信息高度
	addTitle = "添加推荐用户", //添加信息标题
	
	verifyMaxSerial = 0,
	verifyQueryParams = {
		'maxSerial':0
	},
    onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		
		
		$('#htm_indexed').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 215,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
		});
		
		$('#htm_notify').window({
			title : '通知用户',
			modal : true,
			width : 360,
			height : 225,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false
		});
		
		$('#htm_uinteract').window({
			modal : true,
			width : 520,
			top : 10,
			height : 220,
			title : '添加用户互动信息',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false
		});
		
		
		$('#ss-optId').combogrid({
			panelWidth : 460,
		    panelHeight : 310,
		    loadMsg : '加载中，请稍后...',
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'optName',
		    url : './admin_logger/operation_queryOperation?addAllTag=true',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'ID', align : 'center',width : 120},
		  		{field : 'optName', title : '名称', align : 'center',width : 120},
		  		{field : 'optDesc', title : '描述', align : 'center',width : 180},
		    ]]
		});
		$('#ss-optId').combogrid('setValue',-1);
		
		$('#ss-userId').combogrid({
			panelWidth : 460,
		    panelHeight : 290,
		    loadMsg : '加载中，请稍后...',
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'userName',
		    url : './admin/user_queryUserInfo?adminUser.valid=1&addAllTag=true',
		    pagination : true,
		    columns:[[
		  		{field : 'userName', title : '用户名', align : 'center',width : 80}
		    ]]
		});
		
		removePageLoading();
		$("#main").show();
	};
	
/**
 * 搜索推荐用户
 */
function searchOpt() {
	maxId = 0;
	myQueryParams.maxId = maxId;
	myQueryParams.optId = $('#ss-optId').combobox('getValue');
	myQueryParams.userId = $('#ss-userId').combobox('getValue');
	myQueryParams.startDate = $('#ss-startDate').datetimebox('getValue');
	myQueryParams.endDate = $('#ss-endDate').datetimebox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}


</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			
			<span class="search_label">用户:</span><input id="ss-userId" style="width:100px" />
			<span class="search_label">操作:</span><input id="ss-optId" value="所有操作" style="width:100px" />
			<span class="search_label">起始时间:</span><input id="ss-startDate"  class="easyui-datetimebox">
	        <span class="search_label">结束时间:</span><input id="ss-endDate"  class="easyui-datetimebox">
	   		<a href="javascript:void(0);" onclick="javascript:searchOpt();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
   		</div>
	</div> 
	</div>
</body>
</html>