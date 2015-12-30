<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商家基础数据信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath}/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript">

	// 行是否被勾选
	var IsCheckFlag = false;
	
	var myQueryParams = {};
	
	var columnsFields = [
             {field: "ck",checkbox: true },
             {field: "id",title: "商家id",align: "center"},
             {field: "name",title: "名称",align: "center"},
             {field: "address",title: "地址",align: "center"},
             {field: "PCD",title: "省/市/区",align: "center"},
             {field: "type",title: "类型",align: "center"},
             {field: "star",title: "评星",align: "center"},
             {field: "taste",title: "口味",align: "center"},
             {field: "view",title: "环境",align: "center"},
             {field: "service",title: "服务",align: "center"},
             {field: "description",title: "描述",align: "center",width:100}
	    ];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "商家基础数据列表",
			width: $(document.body).width(),
			url: "./admin_trade/shop_buildBaseShopList",
			queryParams:myQueryParams,
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
			pageList: [10,20],
			onClickCell: function(rowIndex, field, value) {
				IsCheckFlag = false;
			},
			onSelect: function(rowIndex, rowData) {
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("unselectRow", rowIndex);
				}
			},
			onUnselect: function(rowIndex, rowData) {
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("selectRow", rowIndex);
				}
			},
			onLoadSuccess: function(data) {
				// 数据加载成功，loading动画隐藏
				$("#page-loading").hide();
			}
		});
		
		$("#ss_city").combobox({
        	url: "./admin_op/addr_getCityMap",
         	valueField: "id",
        	textField: "name", 
        	onSelect:function(rec){
        		myQueryParams.cityId = rec.id;
        		$("#htm_table").datagrid("load",myQueryParams);
        	}
        });
		
		// 展示界面
		$("#main").show();
	});
	
	/**
	 * 添加到正式商铺
	 * @author zhangbo	2015-12-30
	 */
	function addShop(){
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示", "您确定要删除已选中的商家吗？", function(r){
				if(r){
					var shopids = [];
					for(var i=0;i<rows.length;i++){
						shopids[i] = rows[i].id;
					}
					var params = {
							shopids: shopids.toString()
						};
					$.post("./admin_trade/shop_addShopByBase", params, function(result){
						// 清除所有已选择的记录，避免重复提交id值
						$("#htm_table").datagrid("clearSelections");
						$("#htm_table").datagrid("clearChecked");
					});
				}
			});	
		}else{
			$.messager.alert("温馨提示","请先选择，再执行操作!");
		}
	};
	
</script>
</head>
<body>
	
	<img id="page-loading" src="${webRootPath}/common/images/girl-loading.gif"/>
	<div id="main" style="display: none;">
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<a href="javascript:void(0);" onclick="addShop()" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加到正式商铺</a>
			<input id="ss_city" style="width:120px" />
		</div>
		
	</div>
</body>
</html>
</html>