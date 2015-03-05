<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户举报维护</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

var maxId = 0,
	init = function() {
		myQueryParams = {
			'userReportDto.valid':1
		},
		loadPageData(initPage);
	},
	htmTableTitle = "举报列表", //表格标题
	loadDataURL = "./admin_user/interact_queryReport", //数据装载请求地址
	deleteURI = "./admin_user/interact_followReport?ids=", //删除请求地址
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['userReportDto.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['userReportDto.maxId'] = maxId;
			}
		}
	},
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'id',align : 'center',width : 120},
		{field : 'reportId',title : '被举报id',align : 'center',width : 120},
		{field : 'reportName',title : '被举报用户',align : 'center',width : 120},
		{field : 'userId',title : '用户id',align : 'center',width : 120},
		{field : 'userName',title : '用户名',align : 'center',width : 120},
		{field : 'reportDate',title : '举报日期',align : 'center',width : 120},
		],

	//分页组件,可以重载
	toolbarComponent = [{
		id:'btncut',
		text:'批量处理',
		iconCls:'icon-ok',
		handler:function(){
			htmDelete(recordIdKey);
		}
	}],
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