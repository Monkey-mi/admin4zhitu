<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String labelId = request.getParameter("labelId");
	String labelName = new String(request.getParameter("labelName").getBytes(), "UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=labelName%></title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js"></script>
<script type="text/javascript">
var maxId = 0,
	labelId = <%=labelId%>,
	htmTableTitle = "<%=labelName%>", //表格标题
	htmTableWidth = 1070,
	worldKey = 'id',
	loadDataURL = "./admin_ztworld/label_queryLabelWorld?valid=1", //数据装载请求地址
	deleteURI = "./admin_ztworld/label_deleteLabelWorld?labelId="+labelId+"&valid=0&ids="; //删除请求地址
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId,
			'labelId' : labelId
		},
		loadPageData(initPage);
	},
	myOnLoadBefore = function() {
		interacts = {};
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
	myRowStyler = function(index,row){
		if(row.interacted)
			return interactedWorld;
		return null;
	},
	//分页组件,可以重载
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : recordIdKey,title : 'id',align : 'center', width : 120},
		phoneCodeColumn,
		authorAvatarColumn = {field : 'authorAvatar',title : '头像',align : 'left',width : 45, 
			formatter: function(value, row, index) {
				userId = row['authorId'];
				var uri = 'page_user_userInfo?userId='+ userId;
				imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
					content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
				if(row.star >= 1) {
					content = content + "<img title='" + row['verifyName'] + "' class='avatar_tag' src='" + row['verifyIcon'] + "'/>";
				}
				return "<a class='updateInfo' href='javascript:void(0);)'>"+"<span>" + content + "</span>"+"</a>";	
			}
		},
		authorIdColumn,
		authorNameColumn,
		clickCountColumn,
		likeCountColumn,
		commentCountColumn,
		worldURLColumn,
		worldIdColumn,
		worldDescColumn,
		titleThumbPathColumn,
		worldLabelColumn,
		dateModified
	],
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		
		removePageLoading();
		$("#main").show();
	};
	
/**
 * 显示评论
 * @param uri
 */
function showComment(realUri,worldId) {
	var url="./admin_interact/interact_queryIntegerIdByWorldId";
	var uri;
	$.post(url,{'worldId':worldId},function(result){
		if(result['interactId']){
			uri = realUri+"&interactId="+result['interactId'];
		}else{
			uri = realUri;
		}
		$.fancybox({
			'margin'			: 20,
			'width'				: '10',
			'height'			: '100%',
			'autoScale'			: true,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'href'				: uri
		});
		return false;
	},"json");
}

/**
*显示用户信息
*/
function showUserInfo(uri){
	$.fancybox({
		'margin'			: 20,
		'width'				: '10',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

function addLatestValid(worldId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/ztworld_updateLatestValid",{
		'id':worldId,
		'valid':1
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'latestValid','1');	
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

function removeLatestValid(worldId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/ztworld_updateLatestValid",{
		'id':worldId,
		'valid':0
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'latestValid','0');	
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}


</script>
</head>
<body>
	<div id="main">
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" style="vertical-align:middle;" title="删除织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
   		</div>
	</div>  
	</div>
	
</body>
</html>