<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	.leftTd{
		text-align:right;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品管理页</title>
<jsp:include page="/common/header.jsp"></jsp:include>
<!-- 添加商品模块jsp引用  -->
<jsp:include page="/item/addItemWindow.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}" />
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript">

	// 行是否被勾选
//	var IsCheckFlag = false;
	var myQueryParams = {'isForItemSet':0};
	
	var columnsFields = [
			{field: "ck", checkbox:true},
			{field: "id", title: "商品ID", align: "center"},
			{field: "name", title: "商品名称", align: "center",width:30},
			{field: "summary", title: "简介", align: "center",width:30},
			{field: "description", title: "详情描述", align: "center",width:30},
			{field: "worldId", title: "关联织图id", align: "center"},
			{field: "itemId", title: "织图id", align: "center"},
			{field: "price", title: "价格", align: "center"},
			{field: "sale", title: "促销价", align: "center"},
			{field: "sales", title: "销售量", align: "center"},
			{field: "stock", title: "库存量", align: "center"},
			{field: "categoryId", title: "类别", align: "center"},
			{field: "brandId", title: "品牌", align: "center"},
			{field: "like", title: "点赞数", align: "center"},
			{field: "imgPath", title: "商品图片", align: "center",
				formatter: function(value,row,index) {
	  				return "<img width='100px' height='100px' class='htm_column_img' src='" + value + "'/>";
	  			}
			},
			{field: "imgThumb", title: "商品缩略图", align: "center",
				formatter: function(value,row,index) {
					return "<img width='100px' height='100px' class='htm_column_img' src='" + value + "'/>";
				}
			},
			{field: "opt", title: "操作", align: "center",
				formatter : function(value, row, index ) {
					return "<a class='updateInfo' href='javascript:void(0);' onclick='javascript:updateitem("+ row.id + ")'>【修改】</a>";
				}
			}
		];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "商品列表",
			width: $(document.body).width(),
			url: "./admin_trade/item_buildItemList",
			toolbar: "#tb",
			idField: "id",
			queryParams:myQueryParams,
			rownumbers: true,
			columns: [columnsFields],
			/* fitColumns: true, */
			autoRowHeight: true,
			pagination: true,
			pageNumber: 1, //指定当前页面为1
			pageSize: 10,
			pageList: [10,20,50],
			onLoadSuccess: function(data) {
				// 数据加载成功，loading动画隐藏
				$("#page-loading").hide();
			},
		});
		
		// 展示界面
		$("#main").show();
	});
	
	
	//添加商品
	function addItem(){
		$("#item_id").val("");
		$("#imgPath").val("");
		$("#imgThumb").val("");
		$("#item_name").val("");
		$("#item_summary").val("");
		$("#item_description").val("");
		$("#item_worldId").val("");
		$('#item_trueItemId').val("");
		$("#item_price").val("");
		$("#item_sale").val("");
		$("#item_sales").val("");
		$("#item_stock").val("");
		$("#item_categoryId").val("");
		$("#item_brandId").val("");
		$("#item_like").val("");
		$("#item_link").val("");
		$('#add_item_window').window('open');
	}
	
	
	
	/**
	 * 更新商品
	 * @author zhangbo	2015-12-08
	 */
	function updateitem(itemId) {
		$("#htm_table").datagrid("selectRecord", itemId);
		var row =  $("#htm_table").datagrid("getSelected");
		$("#item_id").val(row.id);
		$("#imgPath").val(row.imgPath);
		$("#imgThumb").val(row.imgThumb);
		$("#item_path").attr("src",row.imgPath);
		$("#item_thumb").attr("src",row.imgThumb);
		$("#item_name").val(row.name);
		$("#item_summary").val(row.summary);
		$("#item_description").val(row.description);
		$("#item_worldId").val(row.worldId);
		$('#item_trueItemId').val(row.itemId);
		$("#item_price").val(row.price);
		$("#item_sale").val(row.sale);
		$("#item_sales").val(row.sales);
		$("#item_stock").val(row.stock);
		$("#item_categoryId").val(row.categoryId);
		$("#item_brandId").val(row.brandId);
		$("#item_like").val(row.like);
		$("#item_link").val(row.link);
		
		$('#add_item_window').window('open');
	};
	
	
	/**
	 * 批量删除商品
	 * @author zhangbo 2015-12-09
	 */
	function batchDelete(){
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示", "您确定要删除已选中的商品集合吗？", function(r){
				if(r){
					var ids = [];
					for(var i=0;i<rows.length;i++){
						ids[i] = rows[i].id;
					}
					var params = {
							ids: ids.toString()
						};
					$.post("./admin_trade/item_batchDelete", params, function(result){
						$.messager.alert("温馨提示","删除" + rows.length + "条记录");
						// 清除所有已选择的记录，避免重复提交id值
						$("#htm_table").datagrid("clearSelections");
						// 批量删除刷新当前页
						$("#htm_table").datagrid("reload");
					});
				}
			});	
		}else{
			$.messager.alert("温馨提示","请先选择，再执行批量删除操作!");
		}
	};
	
	

	
</script>
</head>
<body>
	
	<img id="page-loading" src="${webRootPath}/common/images/girl-loading.gif"/>
	<div id="main" style="display: none;">
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<span>
				<a href="javascript:void(0);" onclick="javascript:addItem()" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
			 	<a href="javascript:void(0);" onclick="javascript:reSuperb();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">重新排序</a>
			</span>
		</div>
	</div>
	
</body>
</html>
</html>