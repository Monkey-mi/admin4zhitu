<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String userId = request.getParameter("userId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图用户管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer}"></script>
<script type="text/javascript">
var maxId = 0,
	userId = <%=userId%>,
	init = function() {
		toolbarComponent = [];	
		myQueryParams = {
			'maxId' : maxId,
			'userId' : userId
		},
		loadPageData(initPage);
	},
	htmTableTitle = "关注列表", //表格标题
	loadDataURL = "./admin_user/interact_queryConcern", //数据装载请求地址
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	},
	uidKey = "concernId",
	columnsFields = [
		{field : recordIdKey,title : 'id',align : 'center',width : 120},
		userAvatarColumn,
		{field : 'concernId',title : '用户ID',align : 'center', sortable: true, width : 60},
		userNameColumn,
		sexColumn,
		concernCountColumn,
		followCountColumn,
		{field : 'worldCount',title:'织图',align : 'center', width : 60,
			formatter: function(value,row,index){
				var uri = "page_user_userWorldInfo?userId="+row.concernId;
				return "<a title='显示织图' class='updateInfo' href='javascript:showUserWorld(\""+uri
					+"\")'>"+value+"</a>"; 
			}
		},
		likedCountColumn,
		keepCountColumn,
		phoneCodeColumn,
		platformCodeColumn,
		registerDateColumn
		],
	htmTablePageList = [10,20,50];
	
	//显示用户织图
	function showUserWorld(uri){
		$.fancybox({
			'margin'			: 20,
			'width'				: '100%',
			'height'			: '100%',
			'autoScale'			: true,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'href'				: uri
		});
	}
	
</script>
</head>
<body>
	<table id="htm_table"></table>
</body>
</html>