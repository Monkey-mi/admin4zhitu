<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商家信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath}/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript">

	// 查询条件
//	var queryParams = {};
	
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
			title: "商家信息列表",
			width: $(document.body).width(),
			url: "./admin_trade/shop_buildShopList",
			toolbar: "#tb",
			idField: "id",
			sortName: "id",
			rownumbers: true,
			columns: [columnsFields],
			fitColumns: true,
			autoRowHeight: true,
			checkOnSelect: false,
			selectOnCheck: true,
			loadMsg: "处理中,请等待...",
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
		
		// 展示界面
		$("#main").show();
	});
	
	/**
	 * 批量删除商家
	 * @author zhangbo 2015-11-19
	 */
	function batchDelete(){
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
					$.post("./admin_trade/shop_batchDelete", params, function(result){
						$.messager.alert("温馨提示","删除" + rows.length + "条记录");
						// 清除所有已选择的记录，避免重复提交id值
						$("#htm_table").datagrid("clearSelections");
						// 批量删除刷新当前页
						$("#htm_table").datagrid("reload");
					});
				}
			});	
		}else{
			$.messager.alert("温馨提示","请先选择记录，再执行批量删除操作!");
		}
	};
	
</script>
</head>
<body>
	
	<div id="main" style="display: none;">
		<img id="page-loading" alt="" src="${webRootPath}/common/images/girl-loading.gif"/>
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<span>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
			</span>
		</div>
		
	</div>
</body>
</html>
</html>