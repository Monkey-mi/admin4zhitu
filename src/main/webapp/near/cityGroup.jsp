<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>城市分组信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript">

	// 行是否被勾选
	var IsCheckFlag = false;
	
	var columnsFields = [
             {field: "ck",checkbox: true},
             {field: "id",title: "id",align: "center"},
             {field: "description",title: "组名",align: "center"},
             {field: "cityCount",title: "城市数量",align: "center"}
	    ];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "城市分组列表",
			width: $(document.body).width(),
			url: "./admin_op/near_queryCityGroupList",
			toolbar: "#tb",
			idField: "id",
			rownumbers: true,
			columns: [columnsFields],
			fitColumns: true,
			autoRowHeight: true,
			checkOnSelect: false,
			selectOnCheck: true,
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
		
		$('#add_cityGroup_window').window({
			title : '添加城市分组',
			modal : true,
			width : 650,
			height : 155,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
		});
		
		// 展示界面
		$("#main").show();
	});
	
	/**
	 * 新增城市分组
	 * @author zhangbo 2015-12-04
	 */
	function addCityGroup() {
		var $form = $('#add_cityGroup_form');
		if($form.form('validate')) {
			$('#add_cityGroup_form .opt_btn').hide();
			$('#add_cityGroup_form .loading').show();
			$form.form('submit', {
				url: $form.attr("action"),
				success: function(data){
					var result = $.parseJSON(data);
					$('#add_cityGroup_form .opt_btn').show();
					$('#add_cityGroup_form .loading').hide();
					if(result['result'] == 0) {
						// 关闭添加窗口，刷新页面 
						$('#add_cityGroup_window').window('close');
						$("#htm_table").datagrid("load");
					} else {
						$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
					}
					
				}
			});
		}
	};
	
	/**
	 * 批量删除城市分组
	 * @author zhangbo 2015-12-04
	 */
	function batchDelete() {
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示", "您确定要删除已选中的城市组吗？删除后，城市与此分组的关联关系也将被删除。", function(r){
				if(r){
					var cityGroupIds = [];
					for(var i=0;i<rows.length;i++){
						cityGroupIds[i] = rows[i].id;
					}
					var params = {
							cityGroupIds: cityGroupIds.toString()
						};
					$.post("./admin_op/near_batchDeleteCityGroup", params, function(result){
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
				<a href="javascript:void(0);" onclick="javascript:$('#add_cityGroup_window').window('open');" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
			</span>
		</div>
		
		<!-- 添加城市分组窗口 -->
		<div id="add_cityGroup_window">
			<form id="add_cityGroup_form" action="./admin_op/near_addCityGroup" method="post">
				<table class="htm_edit_table" width="580">
					<tr>
						<td class="leftTd">城市分组名称：</td>
						<td class="leftTd">
							<input type="text" name="cityGroupName"/>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addCityGroup()">确定</a>
						</td>
					</tr>
					<tr class="loading" style="display:none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">排序中...</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
	</div>
</body>
</html>
</html>