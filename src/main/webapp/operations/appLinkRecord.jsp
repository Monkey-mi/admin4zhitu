<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String appId = request.getParameter("appId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>换量访问记录管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

var maxId = 0,
	appId = <%=appId%>,
	toolbarComponent = [],
	init = function() {
		myQueryParams = {
			'record.maxId' : maxId,
			'record.appId' : appId
		},
		loadPageData(initPage);
	},
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['record.maxId'] = maxId;
		}
	},
	
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['record.maxId'] = maxId;
			}
		}
	},
	
	htmTableTitle = "点击记录列表", //表格标题
	loadDataURL = "./admin_op/ad_queryAppLinkRecord", //数据装载请求地址
	
	columnsFields = [
		{field : 'id',title : 'ID',align : 'center', width : 60},
		{field : 'recordIp',title : 'IP',align : 'center', width : 180},
		{field : 'recordDate',title : '日期',align : 'center', width : 180}
    ];
</script>
</head>
<body>
	<table id="htm_table"></table>
</body>
</html>