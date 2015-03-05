<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>互动跟踪信息管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
var htmTableTitle = "互动跟踪信息列表", //表格标题
	htmTableWidth = 960,
	hideIdColumn = false,
	loadDataURL = "./admin_interact/interact_queryTrackerList", //数据装载请求地址
	init = function() {
		toolbarComponent = '#tb';
		loadPageData(initPage);
	},
	//分页组件,可以重载
	columnsFields = [
		{field : recordIdKey,title : 'ID',align : 'center', width : 45},
		{field : 'interactDesc',title : '跟踪描述',align : 'center',width : 80},
		{field : 'interactStep',title : '间隔（分钟）',align : 'center',width : 80},
		{field : 'interactBegin', title:'起始（点）', align : 'center',width : 80},
		{field : 'interactStop', title:'结束（点）', align : 'center',width : 80},
		{field : 'lastInteractDate', title:'最后互动时间', align : 'center',width : 150},
		{field : 'lastTrackDate', title:'最后跟踪时间', align : 'center',width : 150},
		{field : 'valid',title : '有效性',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
	];
	
</script>

</head>
<body>
	<table id="htm_table"></table>
</body>
</html>