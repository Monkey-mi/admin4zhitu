<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>互动信息管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">
var maxId = 0,
	worldId = 0,
	htmTableTitle = "互动信息列表", //表格标题
	loadDataURL = "./admin_interact/interact_queryInteractList", //数据装载请求地址
	deleteURI = "./admin_interact/interact_deleteInteract?ids=",
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId,
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
	
	//分页组件,可以重载
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : 'ID',align : 'center', width : 45},
		{field : 'worldId',title : '织图ID',align : 'center',width : 80},
		{field : 'labelId',title : '标签类型',align : 'center',width : 80,
			formatter: function(value,row,index){
  				var label;
  				switch(parseInt(value)) {
  				case 2: 
  					label = '穿搭';
  					break;
  				case 3: 
  					label = '美食';
  					break;
  				case 4: 
  					label = '空间';
  					break;
  				case 5: 
  					label = '其他';
  					break;
  				default:
  					label = '旅行';
  					break;
  				}
  				return label;
  			}	
		},
		{field : 'clickCount',title : '播放',align : 'center',width : 80,
			formatter : function(value, row, rowIndex ) {
				if(value > 0) {
					var uri = 'page_interact_interactWorldClick?interactId='+ row[recordIdKey]; //播放管理地址			
					return "<a title='显示播放计划' class='updateInfo' href='javascript:showInteractClick(\""
							+ uri + "\")'>"+value+"</a>";
				}
				return value;
				
			}	
		},
		{field : 'commentCount',title : '评论',align : 'center',width : 80,
			formatter : function(value, row, rowIndex ) {
				if(value > 0) {
					var uri = 'page_interact_interactWorldComment?interactId='+ row[recordIdKey]; //评论管理地址			
					return "<a title='显示评论计划' class='updateInfo' href='javascript:showInteractComment(\""
							+ uri + "\")'>"+value+"</a>";
				}
				return value;
				
			}	
		},
		{field : 'likedCount',title : '喜欢',align : 'center',width : 80,
			formatter : function(value, row, rowIndex ) {
				if(value > 0) {
					var uri = 'page_interact_interactWorldLiked?interactId='+ row[recordIdKey]; //喜欢管理地址			
					return "<a title='显示喜欢计划' class='updateInfo' href='javascript:showInteractLiked(\""
							+ uri + "\")'>"+value+"</a>";
				}
				return value;
				
			}	
		},
		{field : 'duration', title:'为期(小时)', align : 'center',width : 80},
		{field : 'dateAdded', title:'添加日期', align : 'center',width : 150, 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		},
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
	
/**
 * 显示评论
 * @param uri
 */
function showInteractComment(uri) {
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

function showInteractLiked(uri) {
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

function showInteractClick(uri) {
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

function refreshInteract() {
	if(maxId > 0) {
		$.messager.confirm('提示', '确定要刷新互动列表？刷新后所有互动将生效！', function(r){
			if (r){
				$("#htm_table").datagrid('loading');
				$.post("././admin_interact/interact_refreshInteract",{
					'maxId':maxId
					},function(result){
						if(result['result'] == 0) {
							$("#htm_table").datagrid('reload');
						} else {
							$.messager.alert('失败提示',result['msg']);  //提示失败信息
							$("#htm_table").datagrid('loaded');
						}
					},"json");
			}
		});
	}
}

function searchByWID() {
	var worldId = $("#ss_worldId").searchbox('getValue');
	myQueryParams.worldId = worldId;
	$("#htm_table").datagrid("load",myQueryParams);
}

</script>

</head>
<body>
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:refreshInteract();" class="easyui-linkbutton" title="更新互动列表" plain="true" iconCls="icon-reload" id="refreshBtn">更新列表</a>
			<div style="display: inline; float:right;">
		        <input id="ss_worldId" class="easyui-searchbox" searcher="searchByWID" prompt="输入织图ID搜索" style="width:200px;" />
			</div>
   		</div>
	</div>
</body>
</html>