<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PV统计</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
var maxId = 0,
init = function() {
	toolbarComponent = '#tb';
	myQueryParams = {
		'pv.maxId' : maxId
	}
	loadPageData(initPage);
},
hideIdColumn = false,
htmTableTitle = "pv列表", //表格标题
loadDataURL = "./admin_statistics/pv_queryPv",

htmTablePageList = [50, 20, 100];

myOnBeforeRefresh = function(pageNumber, pageSize) {
	if(pageNumber <= 1) {
		maxId = 0;
		myQueryParams['pv.maxId'] = maxId;
	}
},
myOnLoadSuccess = function(data) {
	if(data.result == 0) {
		if(data.maxId > maxId) {
			maxId = data.maxId;
			myQueryParams['pv.maxId'] = maxId;
		}
	}
},

columnsFields = [
	{field:'id',title:'ID'},
	   {field:'pvkey',title:'一级模块'},
       {field:'subkey',title:'二级模块'},
       {field:'keyname',title:'统计名称'},
       {field:'pv',title:'pv数量'},
       {field:'pvtime',title:'时间',
       	formatter : function(value, row, index ) {
       		return new Date(parseInt(value)).format("yyyy-MM-dd");
	}}
];

onBeforeInit = function() {
	showPageLoading();
},
onAfterInit = function() {
	
	$("#ss-pvkey").combobox({
       	url: "./admin_statistics/pv_queryPvKey",
        	valueField: "pvkey",
       	textField: "keyname", 
       });
	
	removePageLoading();
	$("#main").show();
};

function queryPv() {
	maxId = 0;
	myQueryParams['pv.maxId'] = maxId;
	myQueryParams['pv.pvkey'] = $('#ss-pvkey').combobox('getValue');
	myQueryParams['beginDateStr'] = $('#ss-beginDate').datebox('getValue');
	myQueryParams['endDateStr'] = $('#ss-endDate').datebox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}	

</script>
</head>
<body>
	<div id="main" class="none">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		
			<span class="search_label">统计类型：</span>
			<input id="ss-pvkey" type="text" />
			
			<span class="search_label">起始时间：</span>
			<input id="ss-beginDate" type="text" class="easyui-datebox" />
			
			<span class="search_label">结束时间：</span>
			<input id="ss-endDate" type="text" class="easyui-datebox" />
			<a href="javascript:void(0);" onclick="javascript:queryPv();" plain="true" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		</div> 
	</div>
</body>
</html>