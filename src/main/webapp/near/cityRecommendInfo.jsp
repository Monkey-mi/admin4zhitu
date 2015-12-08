<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>城市推荐信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript">

	// 行是否被勾选
	var IsCheckFlag = false;
	
	var columnsFields = [
             {field: "ck",checkbox: true },
             {field: "id",title: "id",align: "center"},
             {field: "cityId",title: "城市id",align: "center"},
             {field: "cityName",title: "城市名称",align: "center"},
             {field: "cityGroupId",title: "分组id",align: "center"},
             {field: "cityGroupName",title: "分组名称",align: "center"}
	    ];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "推荐城市列表",
			width: $(document.body).width(),
			url: "./admin_op/near_queryRecommendCity",
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
		
		$("#city_group").combobox({
        	url: "./admin_op/near_getCityGroup",
         	valueField: "id",
        	textField: "description", 
        	onSelect:function(rec){
        		var params = {
        			cityGroupId:rec.id
        		};
        		
        		$("#htm_table").datagrid("load",params);
        	}
        });
		
		$("#cityGroupId_add").combobox({
        	url: "./admin_op/near_getCityGroup",
         	valueField: "id",
        	textField: "description", 
        });
		
		$('#add_city_window').window({
			title : "添加城市",
			modal : true,
			width : 520,
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
	 * 新增推荐城市
	 * @author zhangbo 2015-12-04
	 */
	function addRecommendCity() {
		var $form = $('#add_city_form');
		if($form.form('validate')) {
			$('#add_city_form .opt_btn').hide();
			$('#add_city_form .loading').show();
			$form.form('submit', {
				url: $form.attr("action"),
				success: function(data){
					var result = $.parseJSON(data);
					$('#add_city_form .opt_btn').show();
					$('#add_city_form .loading').hide();
					if(result['result'] == 0) {
						// 关闭添加窗口，刷新页面 
						$('#add_city_window').window('close');
						$("#htm_table").datagrid("load");
					} else {
						$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
					}
					
				}
			});
		}
	};
	
	/**
	 * 批量删除商家
	 * @author zhangbo 2015-11-19
	 */
	function batchDelete(){
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示", "您确定要删除已选中的城市吗？", function(r){
				if(r){
					var cityIds = [];
					for(var i=0;i<rows.length;i++){
						cityIds[i] = rows[i].id;
					}
					var params = {
							idsStr: cityIds.toString()
						};
					$.post("./admin_op/near_batchDeleteRecommendCity", params, function(result){
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
	
	<div id="main" style="display: none;">
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<span>
				<a href="javascript:void(0);" onclick="javascript:$('#add_city_window').window('open');" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
				<input id="city_group" style="width:120px" />
			</span>
		</div>
		
		<!-- 添加推荐城市窗口 -->
		<div id="add_city_window">
			<form id="add_city_form" action="./admin_op/near_addRecommendCity" method="post">
				<table class="htm_edit_table" width="480" style="margin-top:10px;">
					<tr>
						<td class="leftTd" style="text-align:right">城市：</td>
						<td>
							<input name="cityId" class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'./admin_op/addr_getCityMap'"/>
						</td>
						<td class="rightTd">
							请选择城市
						</td>
					</tr>
					<tr>
						<td class="leftTd" style="text-align:right">地区：</td>
						<td>
							<input id="cityGroupId_add" type="text" name="cityGroupId"/>
						</td>
						<td>
							请选择地区
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addRecommendCity()">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#add_city_window').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading" style="display:none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">操作中...</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
	</div>
</body>
</html>
</html>