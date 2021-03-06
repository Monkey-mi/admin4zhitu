<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  String itemSetId =  request.getParameter("itemSetId");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加商品</title>
<jsp:include page="/common/header.jsp"></jsp:include>
<!-- 添加商品模块jsp引用  -->
<jsp:include page="/item/showItemWindowAndreOrder.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript">
	var itemSetId = <%= itemSetId%>;
	
	myQueryParams = {
		"item.itemSetId":itemSetId
	};
	addToItemSetURL = "./admin_trade/item_saveSetItem";
	
	var columnsFields = [
			{field: "ck", checkbox:true},
			{field: "id", title: "商品ID", align: "center"},
			{field: "name", title: "商品名称", align: "center",width:30},
 			{field: "summary", title: "简介", align: "center",width:30},
 			{field: "description", title: "详情描述", align: "center",width:30},
 			{field: "price", title: "价格", align: "center"},
 			{field: "link", title: "链接", align: "center",
 				formatter: function(value,row,index) {
 	  				return "<a href='"+value+"' target='_blank'>"+value+"</a>";
 	  			}
 			},
 			{field: "worldId", title: "关联织图id", align: "center"},
 			{field: "likeNum", title: "点赞数", align: "center"},
			{field: "imgPath", title: "商品图片", align: "center",
				formatter: function(value,row,index) {
	  				return "<a onmouseover='setShowItemInfoTimer("+ row.id +")' onmouseout='javascript:clearShowItemInfo();'><img width='50px' height='50px' class='htm_column_img' src='" + value + "'/></a>";
	  			}
			},
			{field: "isThisSet", title: "操作", align: "center",
				formatter: function(value,row,index) {
					return "<a style='text-decoration : none' href='javascript:void(0);' onclick='javascript:addItemToSet("+ row.id + ",event)'>【添加】</a>";
				}
			},
		];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "商品库",
			width: $(document.body).width(),
			url: "./admin_trade/item_queryItemList",
			toolbar: "#tb",
			idField: "id",
			queryParams:myQueryParams,
			rownumbers: true,
			columns: [columnsFields],
			fitColumns: true,
			autoRowHeight: true,
			checkOnSelect: false,
			selectOnCheck: true,
			pagination: true,
			pageNumber: 1, //指定当前页面为1
			pageSize: 5,
			pageList: [5,10,20]
		});
		
	});
	
	function addItemToSet(itemId){
		$.post(addToItemSetURL,{
			'itemId':itemId,
			'itemSetId':itemSetId
		},function(result){
			if (result.result == 0) {
				$("#htm_table_set").datagrid("reload");
				$("#htm_table").datagrid('load');
			} else {
				
			}
		},'json');
	}
	
	function searchByItemName(){
		var itemName = $('#item_name').searchbox('getValue');
		myQueryParams['item.name'] = itemName;
		$("#htm_table").datagrid('load');
	}
	
	/*  
	***************************************************************
	上面为所有商品展示列表；下面为本集合下的商品展示列表
	***************************************************************
	*/
	var myQueryParamsSet = {
		'item.itemSetId':itemSetId
	};
	var batchDeleteItemFromSetURL = "./admin_trade/item_batchDeleteSetItem";
	
	$(function(){
		// 主表格
		$("#htm_table_set").datagrid({
			title: "专题商品",
			width: $(document.body).width(),
			url: "./admin_trade/item_querySetItem",
			toolbar: "#tb_set",
			idField: "id",
			queryParams:myQueryParamsSet,
			rownumbers: true,
			columns: [columnsFieldsForSet],
			fitColumns: true,
			autoRowHeight: true,
			pagination: true,
			pageNumber: 1, //指定当前页面为1
			pageSize: 5,
			pageList: [5,10,20]
		});
		// 展示界面
		$("#main").show();
	});
	
		var columnsFieldsForSet = [
			{field: "ck", checkbox:true},
			{field: "id", title: "商品ID", align: "center"},
			{field: "name", title: "商品名称", align: "center",width:30},
 			{field: "summary", title: "简介", align: "center",width:30},
 			{field: "description", title: "详情描述", align: "center",width:30},
 			{field: "price", title: "价格", align: "center"},
 			{field: "link", title: "链接", align: "center",
 				formatter: function(value,row,index) {
 	  				return "<a href='"+value+"' target='_blank'>"+value+"</a>";
 	  			}
 			},
 			{field: "worldId", title: "关联织图id", align: "center"},
 			{field: "likeNum", title: "点赞数", align: "center"},
			{field: "imgPath", title: "商品图片", align: "center",
				formatter: function(value,row,index) {
	  				return "<a onmouseover='setShowItemInfoTimer("+ row.id +")' onmouseout='javascript:clearShowItemInfo();'><img width='50px' height='50px' class='htm_column_img' src='" + value + "'/></a>";
	  			}
			}
		];
		
		function batchDeleteItemFromSet(){
			var rows = $("#htm_table_set").datagrid("getSelections");
			var deleteIds = [];
			for(var i = 0; i < rows.length; i++){
				deleteIds[i] = rows[i].id;
			}
			$.post(batchDeleteItemFromSetURL,{
				'itemSetId':itemSetId,
				'ids':deleteIds.join(",")
			},function(result){
				if (result.result == 0) {
					$("#htm_table_set").datagrid("reload");
					$("#htm_table").datagrid("reload");
				} else {

				}
			},"json");
		}
</script>
</head>
<body>

	
	<div id="main" style="display: none;">
	
			<!--  显示本集合下的商品 -->
			<table id="htm_table_set"></table>
			<!--  本集合下的商品toolbar -->
			<div id="tb_set" style="padding:5px;height:auto" class="none">
				<span>
					<a href="javascript:void(0);" onclick="javascript:batchDeleteItemFromSet();" class="easyui-linkbutton" plain=true  iconCls="icon-cut">批量删除</a>
					<a href="javascript:void(0);" onclick="javascript:reSuperbForSet();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">重新排序</a>
				</span>
			</div>
		
			<!--  集合商品列表 -->
			<div style="height:20px;width:auto"></div>
		
			<!--  显示全部商品数据 -->
			<table id="htm_table"></table>
			<!-- toolbar -->
			<div id="tb" style="padding:5px;height:auto" class="none">
				<span>
					<!-- 
					<a href="javascript:void(0);" onclick="javascript:$('#add_item_window').window('open');" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
					 -->
					<input id="item_name" searcher="searchByItemName" class="easyui-searchbox" prompt="输入商品名搜索" style="width:150px;" />
				</span>
			</div>
			
	</div>
	
</body>
</html>
</html>