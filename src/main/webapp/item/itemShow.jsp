<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>买家商品展示</title>
<jsp:include page="/common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}"></link>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript">

	// 行是否被勾选
	var IsCheckFlag = true;
	var myQueryParams = {};
	var addItemShowURL =  "./admin_trade/itemShow_addItemShowList";
	var buildItemSetListByIdOrNameURL = "./admin_trade/itemSet_buildItemSetListByIdOrName";
	
	
	var columnsFields = [
	        {field: "ck", checkbox:true},  
	        {field:"id",title:"ID",align:"center",width:50},
			{field: "worldId", title: "织图ID", align: "center", width: 80},
			{field: "itemSetId", title: "集合ID", align: "center", width: 100},
			{field: "serial", title: "serial", align: "center", width: 80}
		];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "商品集合公告列表",
			width: $(document.body).width(),
			url: "./admin_trade/itemShow_bulidItemShowList",
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
			pageList: [5,10,20],
			onClickCell: function(rowIndex, field, value) {
			},
			onSelect: function(rowIndex, rowData) {
			},
			onUnselect: function(rowIndex, rowData) {
			},
			onLoadSuccess: function(data) {
			}
		});
		
		$("#add_itemSet_window").window({
			title : '添加商品专题',
			modal : true,
			width : 400,
			height : 150,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false
		});
		
		$('#item_set_searcher').combogrid({
		    panelWidth : 330,
		    panelHeight : 330,
		    loadMsg : '加载中，请稍后...',
			pageList : [10,20],
			toolbar:"#search-itemSet-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'title',
		    url : './admin_trade/itemSet_buildItemSetListByIdOrName',
		    pagination : true,
		    columns:[[
		  			{field: "id", title: "ID", align: "center", width: 30},
					{field: "title", title: "标题", align: "center", width: 80},
					{field: "description", title: "描述", align: "center", width: 100},
					{field: "path", title: "商品集合图片", align: "center", width: 80,
						formatter: function(value,row,index) {
			  				return "<img width='80px' height='35px' class='htm_column_img' src='" + value + "'/>";
			  			}
					},
		    ]],
		    onSelect : function(row){
		    	var itemSetId = $('#item_set_searcher').combogrid("getValue");
		    	myQueryParams = {
		    			'itemSetId':itemSetId
		    	};
		    	$("#htm_table").datagrid("reload",myQueryParams);
		    }
		});
		
		$('#item_set').combogrid({
		    panelWidth : 330,
		    panelHeight : 330,
		    loadMsg : '加载中，请稍后...',
			pageList : [10,20],
			toolbar:"#itemSet-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'title',
		    url : './admin_trade/itemSet_buildItemSetListByIdOrName',
		    pagination : true,
		    columns:[[
		  			{field: "id", title: "ID", align: "center", width: 30},
					{field: "title", title: "标题", align: "center", width: 80},
					{field: "description", title: "描述", align: "center", width: 100},
					{field: "path", title: "商品集合图片", align: "center", width: 80,
						formatter: function(value,row,index) {
			  				return "<img width='80px' height='35px' class='htm_column_img' src='" + value + "'/>";
			  			}
					},
		    ]]
		});
		
		// 展示界面
		$("#main").show();
		
 		//排序窗口初始化
		$('#htm_superb_set').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 145,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-tip',
			resizable : false
		}).show(); 
		
/* 		$.formValidator.initConfig({
			formid : $('#add_itemSet_form').attr("id")	
		}); */
		
/* 		$("#itemSet_path")
		.formValidator({empty:false, onshow:"请选图片（必填）",onfocus:"请选图片",oncorrect:"正确！"})
		.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
		
		$("#itemSet_thumb")
		.formValidator({empty:false, onshow:"请选图片（必填）",onfocus:"请选图片",oncorrect:"正确！"})
		.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
		
		$("#itemSet_title")
		.formValidator({empty:false, onshow:"请输入标题（必填）",onfocus:"请输入标题",oncorrect:"正确！"});
		
		$("#itemSet_desc")
		.formValidator({empty:true, onshow:"请输入描述（可选）",onfocus:"请输入描述",oncorrect:"正确！"}); */
	});
	
	
/**
 * 提交商品秀信息
 * mishengliang
 */
	function formSubmit() {
		var worldId = $("#world_id").val();
		var itemSetId = $("#item_set").combogrid("getValue");
		$.post(addItemShowURL,{
			'worldId':worldId,
			'itemSetId':itemSetId
		},function(data){
			if(data.result == 0){
				$("#add_itemSet_window").window("close");
				$.messager.alert("温馨提示","添加记录成功。");
				$("#htm_table").datagrid("reload");
			}else{
				$.messager.alert("错误提示","！");
			}
		});
	};
	
	/**
	 * 批量删除商品集合banner
	 * @author mishengliang 2015-12-21
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
							'ids': ids.toString()
						};
					$.post("./admin_trade/itemShow_batchDelete", params, function(result){
						$.messager.alert("温馨提示","删除" + rows.length + "条记录");
						// 清除所有已选择的记录，避免重复提交id值
						$("#htm_table").datagrid("clearSelections");
						$("#htm_table").datagrid("clearChecked");
						// 批量删除刷新当前页
						$("#htm_table").datagrid("reload");
					});
					// 删除后清空提示数量
					$("#reSerialCount").text(0);
				}
			});	
		}else{
			$.messager.alert("温馨提示","请先选择，再执行批量删除操作!");
		}
	};
	
	/**
	 * 重新排序
	 *mishengliang
	 */
     function reorder() {
    	var itemSetId = $("#item_set_searcher").combogrid("getValue");
    	
    	if(itemSetId != "" && itemSetId != null){
         	$("#superb_form_set").find('input[name="reIndexId"]').val('');
         	$('#htm_superb .opt_btn').show();
         	$('#htm_superb .loading').hide();
         	$('#itemSetId').val(itemSetId);
         	var rows = $("#htm_table").datagrid('getSelections');
         	$('#superb_form_set .reindex_column').each(function(i){
         		if(i<rows.length)
         			$(this).val(rows[i]['worldId']);
         	});
         	// 打开添加窗口
         	$("#htm_superb_set").window('open');
    	}else{
    		$.messager.alert("温馨提示","请选择商品集合!");
    	}

     }
	
	//重新排序
     function submitReSuperbFormForSet() {
		var itemSetId = $("#item_set_searcher").combogrid("getValue");
     	var $form = $('#superb_form_set');
     	if($form.form('validate')) {
     		$('#htm_superb_set .opt_btn').hide();
     		$('#htm_superb_set .loading').show();
     		$('#superb_form_set').form('submit', {
     			url: $form.attr('action'),
     			data:{},
     			success: function(data){
     				var result = $.parseJSON(data);
     				$('#htm_superb_set .opt_btn').show();
     				$('#htm_superb_set .loading').hide();
     				if(result['result'] == 0) { 
     					$('#htm_table').datagrid('clearSelections');
     					$('#htm_table').datagrid('reload');
     					$('#htm_superb_set').window('close');  //关闭添加窗口
     				} else {
     					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
     				}
     			}
     		});
     	} 
     	
     } 
	
	function searchShowByWorldId(){
		var worldId = $("#world_id_searcher").searchbox("getValue");
		myQueryParams = {
				'worldId':worldId	
		};
		$("#htm_table").datagrid("reload",myQueryParams);
	}

	function searchItemSet() {
		var itemSetId = $("#item_set_name").searchbox("getValue");
		var combogridParams = {
			'idOrName' : itemSetId
		}
		$("#item_set_searcher").combogrid('grid').datagrid("load",
				combogridParams);
	}

	function searchItemSetForAdd() {
		var itemSetId = $("#itemSet_name").searchbox("getValue");
		var combogridParams = {
			'idOrName' : itemSetId
		}
		$("#item_set").combogrid('grid').datagrid("load", combogridParams);
	}
</script>
</head>
<body>
	
	<img id="page-loading" src="${webRootPath}/common/images/girl-loading.gif"/>
	<div id="main" style="display: none;">
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<span>
				<a href="javascript:void(0);" onclick="javascript:$('#add_itemSet_window').window('open');commonTools.clearFormData($('#add_itemSet_form'));" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="reorder()" class="easyui-linkbutton" plain="true" iconCls="icon-converter">重新排序</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
				<input id = "item_set_searcher" name="item_set_searcher" class="easyui-combogrid">
				<input id = "world_id_searcher" name="world_id_searcher"  searcher = "searchShowByWorldId" class="easyui-searchbox" prompt="输入织图ID搜索">
			</span>
		</div>
		
		<!-- 添加商品集合 -->
		<div id="add_itemSet_window">
			<form id="add_itemSet_form" method="post">
				<table class="htm_edit_table" width="400">
					<tr>
						<td>织图ID：</td>
						<td><input id= "world_id" name="world_id"></td>
					</tr>
					<tr>
						<td>商品集合：</td>
						<td><input id = "item_set" name= "item_set" class="easyui-combogrid"></td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="formSubmit();">确定</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
	</div>

	<div id="search-itemSet-tb" style="padding: 5px; height: auto" class="none">
		<input id="item_set_name" searcher="searchItemSet" class="easyui-searchbox" prompt="集合名搜索" style="width: 200px;" />
	</div>

	<div id="itemSet-tb" style="padding: 5px; height: auto" class="none">
		<input id="itemSet_name" searcher="searchItemSetForAdd" class="easyui-searchbox" prompt="集合名搜索" style="width: 200px;" />
	</div>
	
	<!-- 集合重排模块 -->
	<div id="htm_superb_set" hidden=true>
		<form id="superb_form_set" action="./admin_trade/itemShow_updateSetItemSerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">买家秀ID：</td>
						<td>
							<input name="reIndexId" class="easyui-validatebox reindex_column" required="true"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReSuperbFormForSet();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_superb_set').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">排序中...</span>
						</td>
					</tr>
					<tr hidden=true>
						<td><input id="itemSetId"  name="itemSetId" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
</body>
</html>