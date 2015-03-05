<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图等级用户等级关联管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript">
	var maxId=0,
	loadDateUrl="./admin_interact/worldLevelUserLevel_queryWorldLevelUserLevel",
//	updateUrl="./admin_interact/refreshZombieHtworld_updateZombieHtWorld?uidsStr=",
	delUrl="./admin_interact/worldLevelUserLevel_delWorldLevelUserLevel?idsStr=",
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
		$("#htm_table").datagrid(
				{
					title  :"刷新马甲织图列表",
					width  :1140,
					pageList : [10,30,50,100],
					loadMsg:"加载中....",
					url	   :	loadDateUrl,
					queryParams : tableQueryParams,
					pagination: true,
					pageNumber: pageNum,
					toolbar:'#tb',
					columns: [[
						{field :'ck',checkbox:true},
						{field :'id',title:'ID',align:'center',width:90},
						{field : 'worldLevelId',title: '织图等级ID',align : 'center',width : 130},
						{field : 'worldLevelDesc',title: '织图等级',align : 'center',width : 130},
						{field : 'userLevelId',title: '用户等级ID',align : 'center',width : 130},
						{field : 'userLevelDesc',title: '用户等级',align : 'center',width : 130},
						{field : 'validity',title : '有效性',align : 'center', width: 45,
				  			formatter: function(value,row,index) {
				  				if(value == 1) {
				  					img = "./common/images/ok.png";
				  					return "<img title='有效' class='htm_column_img'  src='" + img + "'/>";
				  				}
				  				img = "./common/images/tip.png";
				  				return "<img title='无效' class='htm_column_img' src='" + img + "'/>";
				  			}
				  		},
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
						{field : 'operatorName',title: '最后操作者',align : 'center',width : 130}
						
					]],
					onLoadSuccess:myOnLoadSuccess,
					onBeforeRefresh : myOnBeforeRefresh
				
				}	
		);
		var p = $("#htm_table").datagrid("getPager");
		p.pagination({
		});
	}
	
	$(function(){
		tableInit();
		$("#htm_add").window({
			title : '新增织图等级用户等级关联',
			modal : true,
			width : 420,
			height : 150,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function(){
				$("#wlId").combobox('clear');
				$("#ulId").combobox('clear');
			}
		});
		removePageLoading();
		$("#main").show();
	});
	
	
	function del(){
		update(delUrl);
	}
	
	function update(url){
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			var ids = [];
			for(var i=0;i<rows.length;i+=1){		
				ids.push(rows[i]['id']);	
				rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
			}	
			$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
			$('#htm_table').datagrid('loading');
			$.post(url+ids,function(result){
				$('#htm_table').datagrid('loaded');
				if(result['result'] == 0) {
					$.messager.alert('提示',result['msg']);
					$("#htm_table").datagrid("reload");
				} else {
					$.messager.alert('提示',result['msg']);
				}
				
			});				
		}	
	}
	
	/**
	 * 判断是否选中要删除的记录
	 */
	function isSelected(rows) {
		if(rows.length > 0){
			return true;
		}else{
			$.messager.alert('操作失败','请先选择记录，再执行操作!','error');
			return false;
		}
	}
	
	function htmWindowAdd(){
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		$("#htm_add").window('open');
		$('#htm_add .opt_btn').show();
		$('#htm_add .loading').hide();
	}
	
	function addSubmit(){
		var addForm = $('#add_form');
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		addForm.ajaxSubmit({
			success: function(result){
				$('#htm_add .opt_btn').show();
				$('#htm_add .loading').hide();
				if(result['result'] == 0) {
					$('#htm_add').window('close');  //关闭添加窗口
					$.messager.alert('成功提示',result['msg']);
					$("#htm_table").datagrid('load');
					return false;
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
					$('#htm_add').window('close');  //关闭添加窗口
				}
				return false;
			},
			url:addForm.attr("action"),
			type:'post',
			dateType:'json'
		});
	}
	
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
</script>
</head>
<body>
	<div id="main">
		<div id="tb">
			<a href="javascript:void(0);" onclick="htmWindowAdd();" class="easyui-linkbutton" title="添加" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
		</div>
		<table id="htm_table"></table>
		<div id="htm_add" >
			<form action="./admin_interact/worldLevelUserLevel_addWorldLevelUserLevel" id="add_form"  method="post">
			<table id="htm_edit_table" width="400">
			  <tbody>
			  	<tr>
					<td class="leftTd">织图等级：</td>
					<td><input id="wlId" name="worldLevelId" class="easyui-combobox" 
							data-options="valueField:'id',textField:'level_description',url:'./admin_interact/worldlevel_QueryoWorldLevel'"/></td>
				</tr>
				<tr>
					<td class="leftTd">用户等级：</td>
					<td><input id="ulId" name="userLevelId" class="easyui-combobox" 
							data-options="valueField:'id',textField:'level_description',url:'./admin_interact/userlevel_QueryUserLevel'"/></td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit();">添加</a> 
						<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
					</td>
				</tr>
				<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
				</tr>
			 </tbody>
			</table>
		</form>
		</div>
	</div>
</body>
</html>