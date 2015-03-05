<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>汇总页面</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
var maxDate,
	hideIdColumn = false,
	init = function() {
		toolbarComponent = [];
		loadPageData(initPage);
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxDate = null;
			myQueryParams.maxDate = maxDate;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId != maxDate) {
				maxDate = data.maxId;
				myQueryParams.maxDate = maxDate;
			}
		}
	},
	htmTableTitle = "汇总列表", //表格标题
	htmTableWidth = 600,
	loadDataURL = "./admin_statistics/summary_querySummary", //数据装载请求地址
	columnsFields = [
		{field : 'date',title : '日期',align : 'center', width : 180},
		{field : 'worldCount',title : '织图总数',align : 'center', width : 180},
		{field : 'childCount',title : '图片总数',align : 'center', width : 180},
	],
	onBeforeInit = function() {
		showPageLoading();
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
	</div>
</body>
</html>