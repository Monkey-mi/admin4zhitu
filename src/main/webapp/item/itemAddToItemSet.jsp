<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  String itemSetId =  request.getParameter("itemSetId");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加商品</title>
<jsp:include page="/common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript">
	var itemSetId = <%= itemSetId%>;
	
	addToItemSetURL = "./admin_trade/itemSet_insertItemToSet";
	
	var columnsFields = [
			{field: "ck", checkbox:true},
			{field: "id", title: "商品ID", align: "center"},
			{field: "name", title: "商品名称", align: "center"},
			{field: "summary", title: "简介", align: "center"},
			{field: "imgPath", title: "商品图片", align: "center",
				formatter: function(value,row,index) {
	  				return "<img width='100px' height='40px' class='htm_column_img' src='" + value + "'/>";
	  			}
			},
			{field: "imgThumb", title: "商品缩略图", align: "center",
				formatter: function(value,row,index) {
					return "<img width='100px' height='40px' class='htm_column_img' src='" + value + "'/>";
				}
			},
			{field: "isThisSet", title: "本集合", align: "center",
				formatter: function(value,row,index) {
					return "<a style='text-decoration : none' href='javascript:void(0);' onclick='javascript:addItemToSet('"+ row.id +"')'>【添加】</a>";
				}
			},
		];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "商品列表",
			width: $(document.body).width(),
			url: "./admin_trade/item_buildItemList",
			toolbar: "#tb",
			idField: "id",
			rownumbers: true,
			columns: [columnsFields],
			fitColumns: true,
			autoRowHeight: true,
			checkOnSelect: false,
			selectOnCheck: true,
			pagination: true,
			pageNumber: 1, //指定当前页面为1
			pageSize: 10,
			pageList: [10,20,50],
			onSelect: function(rowIndex, rowData) {
			},
			onUnselect: function(rowIndex, rowData) {
			},
			onLoadSuccess: function(data) {
			}
		});
		
	});
	
	function addToItemSet(){
		var rows = $("#htm_table").datagrid("getSelections");
		var ids = [];
		for(var i = 0;i < rows.length; i++){
			ids[i] = rows[i].id;
		}
		$.post(addToItemSetURL,{
			itemIds:ids.join(','),
			id:itemSetId
		},function(result){
			if (result.result == 0) {
				$.messager.alert('温馨提示','添加成功！');
			} else {
				
			}
		},'json');
	}
	
	/*  
	***************************************************************
	上面为所有商品展示列表；下面为本集合下的商品展示列表
	***************************************************************
	*/
	var myQueryParams = {itemSetId:itemSetId};
	
	$(function(){
		// 主表格
		$("#htm_table_set").datagrid({
			title: "集合商品列表",
			width: $(document.body).width(),
			url: "./admin_trade/item_buildItemList",
			toolbar: "#tb_set",
			idField: "id",
			queryParams:myQueryParams,
			rownumbers: true,
			columns: [columnsFieldsForSet],
			fitColumns: true,
			autoRowHeight: true,
			checkOnSelect: false,
			selectOnCheck: true,
			pagination: true,
			pageNumber: 1, //指定当前页面为1
			pageSize: 10,
			pageList: [10,20,50],
			onSelect: function(rowIndex, rowData) {
			},
			onUnselect: function(rowIndex, rowData) {
			},
			onLoadSuccess: function(data) {
			}
		});
		// 展示界面
		$("#main").show();
	});
	
		var columnsFieldsForSet = [
			{field: "ck", checkbox:true},
			{field: "id", title: "商品ID", align: "center"},
			{field: "name", title: "商品名称", align: "center"},
			{field: "summary", title: "简介", align: "center"},
			{field: "imgPath", title: "商品图片", align: "center",
				formatter: function(value,row,index) {
	  				return "<img width='100px' height='40px' class='htm_column_img' src='" + value + "'/>";
	  			}
			},
			{field: "imgThumb", title: "商品缩略图", align: "center",
				formatter: function(value,row,index) {
					return "<img width='100px' height='40px' class='htm_column_img' src='" + value + "'/>";
				}
			}
		];
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
				</span>
			</div>
		
			<!--  集合商品列表 -->
			<div style="height:20px;width:auto"></div>
		
			<!--  显示全部商品数据 -->
			<table id="htm_table"></table>
			<!-- toolbar -->
			<div id="tb" style="padding:5px;height:auto" class="none">
				<span>
					<a href="javascript:void(0);" onclick="javascript:addToItemSet();" class="easyui-linkbutton" plain=true  iconCls="icon-add">批量添加</a>
				</span>
			</div>
			
	</div>
	
		
	
</body>
</html>
</html>