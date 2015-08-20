<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>互粉乎赞评论标签管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
	var maxId=0,
	searchChannelMaxId = 0,
	searchChannelQueryParams = {
		'maxId':searchChannelMaxId
	},
	loadDateUrl="./admin_interact/likeFollow_queryLikeFollowRecord",
//	addUrl="./admin_interact/likeFollow_insertLikeFollowCommentLabel",
//	delUrl="./admin_interact/likeFollow_batchDeleteLikeFollowCommentLabel?idsStr=",
	tableQueryParams = {},
	tableInit = function() {
		tableLoadDate(1);
	};
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			tableQueryParams.maxId = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				tableQueryParams.maxId = maxId;
			}
		}
	};
	
	function tableLoadDate(pageNum){
		$("#htm_table").datagrid({
			title  :"互粉乎赞评论标签管理",
			width  :1200,
			pageList : [10,30,50,100,300],
			pageSize : 10,
			loadMsg:"加载中....",
			url	   :	loadDateUrl,
			queryParams : tableQueryParams,
			remoteSort: true,
			pagination: true,
			idField   :'id',
			pageNumber: pageNum,
			toolbar:'#tb',
			columns: [[
				{field :'ck',checkbox:true},
				{field :'id',title:'ID',align:'center',width:80},
				{field :'userId',title:'用户ID',align:'center',width:80},
				{field :'worldId',title: '织图ID',align : 'center',width : 100},
				{field :'zombieId',title: '马甲ID',align : 'center',width : 100},
				{field :'type',title: '类型',align : 'center',width : 100,
					formatter: function(value,row,index) {
						switch(value){
							case 0:
								return "互粉";
							case 1:
								return "互赞";
							default:
								return "未知类型";
						}
		  			}
				},
				{field :'complete',title: '完成情况',align : 'center',width : 100,
					formatter: function(value,row,index) {
		  				if(value == 1) {
		  					img = "./common/images/ok.png";
		  					return "<img title='已完成' class='htm_column_img'  src='" + img + "'/>";
		  				}
		  				img = "./common/images/tip.png";
		  				return "<img title='未完成' class='htm_column_img' src='" + img + "'/>";
		  			}
				},
				{field :'interactWorldCommentId',title: '互动ID',align : 'center',width : 100},
				{field : 'addDate', title:'创建时间',align : 'center' ,width : 130,
					formatter:function(value,row,index){
						return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
					}
				},
				{field : 'modifyDate', title:'最后修改时间',align : 'center' ,width : 130,
					formatter:function(value,row,index){
						return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
					}
				},
			]],
			onLoadSuccess:myOnLoadSuccess,
			onBeforeRefresh : myOnBeforeRefresh
		});
		var p = $("#htm_table").datagrid("getPager");
		p.pagination({
		});
	}
	
	
	
	$(function() {
		tableInit();
		$("#main").show();
	});

	/**
	 * 显示载入提示
	 */
	function showPageLoading() {
		var $loading = $("<div></div>");
		$loading.text('载入中...').addClass('page_loading_tip');
		$("body").append($loading);
	}

	/**
	 * 移除载入提示
	 */
	function removePageLoading() {
		$(".page_loading_tip").remove();
	}

	/**
	 * 批量删除
	 */
	function del() {
		$.messager.confirm('更新缓存', '确定要将选中的内容删除?', function(r) {
			if (r) {
				update(delUrl);
			}
		});
	}

	function update(url) {
		var rows = $('#htm_table').datagrid('getSelections');
		var ids = [];
		for (var i = 0; i < rows.length; i += 1) {
			ids.push(rows[i]['id']);
		}
		$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
		$('#htm_table').datagrid('loading');
		$.post(url + ids, function(result) {
			$('#htm_table').datagrid('loaded');
			if (result['result'] == 0) {
				$.messager.alert('提示', result['msg']);
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('提示', result['msg']);
			}

		});
	}

	/**
	 * 判断是否选中要删除的记录
	 */
	function isSelected(rows) {
		if (rows.length > 0) {
			return true;
		} else {
			$.messager.alert('操作失败', '请先选择记录，再执行操作!', 'error');
			return false;
		}
	}


	 

</script>
</head>	
<body>
	<div id="main" class="none">
		<div id="tb">
			<!-- 
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			 -->
		</div>
		<table id="htm_table"></table>
	</div>
	
</body>
</html>