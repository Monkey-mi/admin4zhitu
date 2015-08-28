<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册数据汇总</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
var maxDate,
	isPagination = false,
	hideIdColumn = false,
	init = function() {
		toolbarComponent = '#tb';
		loadPageData(initPage);
	},
	htmTableTitle = "注册数据汇总", //表格标题
	loadDataURL = "./admin_statistics/summary_queryRegisterSum", //数据装载请求地址
	columnsFields = [
		{field : 'androidCnt',title : 'Android',align : 'center', width : 180},
		{field : 'iosCnt',title : 'IOS',align : 'center', width : 180},
		{field : 'total',title : '总共',align : 'center', width : 180},
		{field : 'start',title : '起始时间',align : 'center', width : 180},
		{field : 'end',title : '结束时间',align : 'center', width : 180},
		
	],
	onBeforeInit = function() {
		showPageLoading();
		$('#startTime').datebox({
			onSelect: function(date){
				myQueryParams.beginDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" 00:00:00";
				$("#htm_table").load(myQueryParams);
			}
		});
		var today = new Date();
		$('#startTime').datebox('setValue', today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate());
	},
	onAfterInit = function() {
		removePageLoading();
		$("#main").show();
	};

</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
	        <span class="search_label">起始时间:</span><input id="startTime" name="beginDate" />
	</div>  
	</div>
</body>
</html>