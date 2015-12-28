<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>城市行政区信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}" />
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript">

	// 行是否被勾选
	var IsCheckFlag = true;
	
	// 查询条件默认查询深圳的行政区
	var myQueryParams = {};
	myQueryParams['city.id'] = 21760;
	
	// 查询行政区参数
	var distictParams = {};
	
	var columnsFields = [
             {field: "ck",checkbox: true },
             {field: "id",title: "id",align: "center"},
             {field: "cityName",title: "城市名称",align: "center"},
             {field: "distictName",title: "行政区名称",align: "center"},
             {field: "longitude",title: "经度",align: "center"},
             {field: "latitude",title: "纬度",align: "center"}
	    ];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "城市行政区列表",
			width: $(document.body).width(),
			url: "./admin_op/addr_queryCityDistictList",
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
				// 清除所有已选择的记录，避免重复提交id值
				$("#htm_table").datagrid("clearSelections");
				$("#htm_table").datagrid("clearChecked");
				
			}
		});
		
		$("#ss_city").combobox({
        	url: "./admin_op/addr_getCityMap",
         	valueField: "id",
        	textField: "name", 
        	onSelect:function(rec){
        		myQueryParams['city.id'] = rec.id;
        		$("#htm_table").datagrid("load",myQueryParams);
        	}
        });
		
		$('#add_city_distict_window').window({
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
		
		$("#ss_form_city").combobox({
			url: "./admin_op/addr_getCityMap",
			valueField: "id",
			textField: "name", 
			onSelect:function(rec){
				distictParams['city.id'] = rec.id;
				// 选择联动查询区域
				$("#ss_distict").combobox("reload","./admin_op/addr_getDistrictMap?city.id=" + rec.id);
			}
		});
		
		$("#ss_distict").combobox({
		    multiple : true,
		    valueField : "id",
		    textField : "name",
		    queryParams: distictParams,
		    url : "./admin_op/addr_getDistrictMap"
		});
		
		// 展示界面
		$("#main").show();
	});
	
	function addCityDistict() {
		var $form = $('#add_city_distict_form');
		if($form.form('validate')) {
			$('#add_city_distict_form .opt_btn').hide();
			$('#add_city_distict_form .loading').show();
			$form.form('submit', {
				url: $form.attr("action"),
				success: function(data){
					var result = $.parseJSON(data);
					$('#add_city_distict_form .opt_btn').show();
					$('#add_city_distict_form .loading').hide();
					if(result['result'] == 0) {
						// 关闭添加窗口，刷新页面 
						$('#add_city_distict_window').window('close');
						$("#htm_table").datagrid("load");
					} else {
						$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
					}
				}
			});
		}
	};
	
	/**
	 * 批量删除
	 * @author zhangbo 2015-12-25
	 */
	function batchDelete(){
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示", "您确定要删除已选中的城市行政区吗？", function(r){
				if(r){
					var distictIds = [];
					for(var i=0;i<rows.length;i++){
						distictIds[i] = rows[i].id;
					}
					var params = {
							idsStr: distictIds.toString()
						};
					$.post("./admin_op/addr_batchDeleteCityDistict", params, function(result){
						if(result['result'] == 0){
							$.messager.alert("温馨提示","删除" + rows.length + "条记录");
							// 清除所有已选择的记录，避免重复提交id值
							$("#htm_table").datagrid("clearSelections");
							// 批量删除刷新当前页
							$("#htm_table").datagrid("reload");
						}else{
							$.messager.alert(result["msg"]);
						}
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
				<a href="javascript:void(0);" onclick="javascript:$('#add_city_distict_window').window('open');$('#add_city_distict_form').form('reset');" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
				<input id="ss_city" style="width:120px" />
			</span>
		</div>
		
		<!-- 添加推荐城市窗口 -->
		<div id="add_city_distict_window">
			<form id="add_city_distict_form" action="./admin_op/addr_addCityDistict" method="post">
				<table class="htm_edit_table" width="480" style="margin-top:10px;">
					<tr>
						<td class="leftTd" style="text-align:right">城市：</td>
						<td>
							<input id="ss_form_city" name="city.id" class= "easyui-combobox" data-options="valueField:'id',textField:'name',url:'./admin_op/addr_getCityMap'"/>
						</td>
						<td class="rightTd">
							请选择城市
						</td>
					</tr>
					<tr>
						<td class="leftTd" style="text-align:right">地区：</td>
						<td>
							<input id="ss_distict" name="idsStr"/>
						</td>
						<td>
							请选择行政区
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addCityDistict()">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#add_city_distict_window').window('close');">取消</a>
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