<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>举报管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<script type="text/javascript">
var maxId = 0,
	myRowStyler= 0,
	init = function() {
		myQueryParams = {
			'maxId' : maxId
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
	htmTableTitle = "举报管理", //表格标题
	htmTableWidth = 850,
	loadDataURL = "./admin_ztworld/interact_queryReport",
	deleteURI = "./admin_ztworld/interact_deleteReportById?ids=",	//删除		
	htmTablePageList = [10,20,30],
	myPageSize = 10,
	
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',hidden:true},
		{field : 'authorAvatar',title : '头像',align : 'center',width : 40, 
			formatter: function(value, row, index) {
				imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
					content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
				if(row.star == 1) {
					content = content + "<img title='明星标记' class='avatar_tag' src='./common/images/star_tag.png'/>";
				}
				return "<span>" + content + "</span>";	
			}
		},
		worldURLColumn,
		titleThumbPathColumn,
		{ field : 'worldLabel',title : '标签',align : 'center', width : 60},
  		{field : 'authorName',title : '举报者',align :'center',width: 120},
		{ field : 'report_content',title : '举报内容',align : 'center', width : 120,  
  			formatter : function(value, row, rowIndex ) {
				return "<span title='" + value + "' class='updateInfo'>"+value+"</span>";
			}	
		},
		{ field : 'report_date',title :'被举报日期',align : 'center',width : 150},
/*   		{field : 'reportValid',title : '有效性',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		} */
    ];
		

</script>
</head>
<body>
	<table id="htm_table"></table>	
</body>
</html>