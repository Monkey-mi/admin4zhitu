<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String autoResponseId= request.getParameter("autoResponseId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户权限管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript">
	var getSubordinatesUrl = "./admin/privileges_querySubordinatesByUserId";
	addFormSubmitUrl = "./admin/privileges_addPrivilegesToUser?privilegeIdsStr=";
	deleteUrl = "./admin/privileges_deleteUserPrivileges?privilegeIdsStr=";
	recordIdKey = "worldId",
	tableQueryParams = {},
	tableInit = function() {
		tableLoadDate(1);
	};
	
	function tableLoadDate(pageNum){
		$("#htm_privileges_table").datagrid(
				{
					title  :"权限列表",
					width  :750,
					loadMsg:"加载中....",
					url	   :"./admin/privileges_queryUserPrivilegesByUserId",
					pagination: true,
					pageNumber: pageNum,
					pageList : [20,50,100,150],
//					queryParams : tableQueryParams,
					toolbar:'#tb',
					columns: [[
						{field : 'ck',checkbox:true},
						{field : 'privilegeId',title:'权限ID',align:'center',width:90,hidden:true},
						{field : 'privilegeName',title: '权限名称',align : 'center',width : 250},
						{field : 'valid',title: '有效性',align : 'center',width : 50,
							formatter: function(value,row,index) {
				  				if(value == 1) {
				  					img = "./common/images/ok.png";
				  					return "<img title='有效' class='htm_column_img'  src='" + img + "'/>";
				  				}
				  				img = "./common/images/tip.png";
				  				return "<img title='无效' class='htm_column_img' src='" + img + "'/>";
				  			}
						},
						{field : 'addDate', title:'添加时间',align : 'center' ,width : 150,
							formatter:function(value,row,index){
								return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
							}
						},
						{field : 'modifyDate', title:'最后修改时间',align : 'center' ,width : 150,
							formatter:function(value,row,index){
								return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
							}
						},
						{field : 'operatorName',title: '最后修改者',align : 'center',width : 150}
						
						
					]]
				
				}	
		);
		var p = $("#htm_privileges_table").datagrid("getPager");
		p.pagination({
		});
	}
	
	$(function(){
		showPageLoading();
		$('#userIdInput').combobox({
			valueField:'id',
			textField:'userName',
//			selectOnNavigation:false,
			url:'./admin/privileges_querySubordinatesByUserId',
			onSelect:function(rec){
				queryUserPrivilegesByUid(rec.id);
			}
		});
		$("#htm_add").window({
			title : '新增计划评论标签',
			modal : true,
			width : 480,
			height : 150,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function(){
				$('#addUserIdInput').combobox('clear');
				$("#privilegesInput").combogrid('clear');
				$("#privilegesInput").combogrid('grid').datagrid('unselectAll');
			}
		});
		
		tableInit();
		removePageLoading();
	});

	
	function allocation(){
		update(autoUpdateUrl);
	}
	
	
	//增加权限
	function addSubmit(){
		$("#htm_add .loading").show();
		$("#htm_add .opt_btn").hide();
		var privilegeIds = $("#privilegesInput").combogrid('getValues');
		var userId=$('#addUserIdInput').combobox('getValue');
		$.post(addFormSubmitUrl+privilegeIds,{
			'userId' : userId,
		},function(result){
			$('#add_privileges_table').datagrid('loaded');
			if(result['result'] == 0) {
				$.messager.alert('提示',result['msg']);
				$("#add_privileges_table").datagrid("reload");
			} else {
				$.messager.alert('提示',result['msg']);
			}
			$("#htm_add .loading").hide();
			$("#htm_add .opt_btn").show();
			$("#htm_add").window('close');
		});	
	}
	
	function queryUserPrivilegesByUid(userId){
		tableQueryParams.userId=userId;
		$('#htm_privileges_table').datagrid('loading');
		$('#htm_privileges_table').datagrid('load',tableQueryParams);
		$('#htm_privileges_table').datagrid('loaded');
	}
	
	
	function initAddWindow(){
		$('#privilegesInput').combogrid({
			panelWidth:240,
		    panelHeight:500,
		    loadMsg:'加载中...',
			pageList: [100,200],
			editable: false,
		    multiple: true,
		   	toolbar:"",
		   	url:"./admin/privileges_queryUserPrivilegesByUserIdForTable",
		    idField:'id',
		    textField:'id',
		    pagination:false,
		    columns: [[
						{field : 'ck',checkbox:true},
						{field : 'id',title:'权限ID',align:'center',width:90,hidden:true},
						{field : 'privilegesName',title: '权限名称',align : 'center',width : 200},
					]]
		});
		$('#addUserIdInput').combobox({
			valueField:'id',
			textField:'userName',
			selectOnNavigation:false,
			url:'./admin/privileges_querySubordinatesByUserId',
			onBeforeLoad:function(param){
				$("#htm_add .opt_btn").hide();
				$("#htm_add .loading").show();
			},
			onLoadSuccess:function(){
				$("#htm_add .loading").hide();
				$("#htm_add .opt_btn").show();
			}
			
		});
		$("#htm_add").window('open');
		
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
	
	function deleteUserPrivileges(){
		var rows = $('#htm_privileges_table').datagrid('getSelections');
		var userId=$('#userIdInput').combobox('getValue');
		if(isSelected(rows)){
			$.messager.confirm('删除记录', '您确定要删除已选中的记录?', function(r){ 	
				if(r){				
					var ids = [];
					for(var i=0;i<rows.length;i+=1){		
						ids.push(rows[i]['privilegeId']);	
						rowIndex = $('#htm_privileges_table').datagrid('getRowIndex',rows[i]);				
					}	
					$('#htm_privileges_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
					$('#htm_privileges_table').datagrid('loading');
					$.post(deleteUrl + ids,{
						'userId':userId
					},function(result){
						$('#htm_privileges_table').datagrid('loaded');
						if(result['result'] == 0) {
							$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
							$("#htm_privileges_table").datagrid("reload");
						} else {
							$.messager.alert('提示',result['msg']);
						}
						
					});				
				}	
			});		
		}
	}
	
	
</script>
</head>
<body>
	<div id="main">
		<div id="tb" style="padding:5px;height:auto" class="none">
			<input id="userIdInput" class="easyui-combobox">
			<a href="javascript:void(0);" onclick="initAddWindow();" class="easyui-linkbutton" title="给用户分配权限" plain="true" iconCls="icon-add" id="addBtn">分配权限</a>
			<a href="javascript:void(0);" onclick="deleteUserPrivileges();" class="easyui-linkbutton" title="删除用户权限" plain="true" iconCls="icon-cut" id="delBtn">删除权限</a>
		</div>
		<table id="htm_privileges_table"></table>
		
		<div id="htm_add">
			<form action="./admin/privileges_addPrivilegesToUser" id="add_form"  method="post">
				<table id="htm_add_table" width="420">
					<tbody>
						<tr>
							<td class="leftTd">给他分配权限：</td>
							<td><input id="addUserIdInput" class="easyui-combobox" style="width:205px;"></td>
						</tr>
						<tr>
							<td class="leftTd">权限：</td>
							<td><input type="text"  id="privilegesInput" style="width:205px;"/></td>
						</tr>
						<tr>
							<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit()">添加</a> 
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
							</td>
						</tr>
						<tr class="loading none" style="display:none;">
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