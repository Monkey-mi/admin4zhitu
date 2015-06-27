<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划评论标签</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css" />
<script type="text/javascript">
	var maxId = 0,
	worldId = 0,
	htmTableTitle = "计划评论标签", //表格标题
	myPageSize = 10,
	myRowStyler= 0,
	loadDataURL = "./admin_interact/planCommentLabel_queryPlanCommentLabelForTable", //数据装载请求地址
	deleteURI = "./admin_interact/planCommentLabel_delPlanCommentLabel?ids=",//删除
	updatePageURL = "./admin_interact/planCommentLabel_updateUserLevelByRowJson";
	addWidth = 400, //添加信息宽度
	addHeight = 260, //添加信息高度
	addTitle = "添加计划评论标签"; //添加信息标题
	init = function() {
//		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId,
		},
		loadPageData(initPage);
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	};
	//分页组件,可以重载
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : 'ID',align : 'center', width : 45},
		{field : 'description',title : '标签',align : 'center',width : 80},
		{field : 'startTime',title : '有效开始日期',align : 'center',width : 120,
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd");
			}	
		},
		{field : 'deadline',title : '有效截止日期',align : 'center',width : 120,
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd");
			}	
		},
		{field : 'workStartTime',title : '开始时间',align : 'center',width : 100,
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("hh:mm:ss");
			}	
		},
		{field : 'workEndTime',title : '结束时间',align : 'center',width : 100,
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("hh:mm:ss");
			}	
		},
		{field : 'valid',title : '有效性',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='有效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='无效' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
		{field : 'addDate', title:'添加日期', align : 'center',width : 150, 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		},
		{field : 'modifyDate', title:'最后修改日期', align : 'center',width : 150, 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		},
		{field : 'operatorName',title : '最后操作者',align : 'center', width: 80},
	],
	
	pageButtons = [{
        iconCls:'icon-save',
        text:'保存',
        handler:function(){
        	endEditing();
        	var rows = $("#htm_table").datagrid('getChanges', "updated"); 
        	$("#htm_table").datagrid('loading');
        	$.post(updatePageURL,{
        		'rowJson':JSON.stringify(rows)
        		},function(result){
        			if(result['result'] == 0) {
        				$("#htm_table").datagrid('acceptChanges');
        			} else {
        				$.messager.alert('失败提示',result['msg']);  //提示失败信息
        			}
        			$("#htm_table").datagrid('loaded');
        		},"json");
        }
    	},{
        iconCls:'icon-undo',
        text:'取消',
        handler:function(){
        	$("#htm_table").datagrid('rejectChanges');
        	$("#htm_table").datagrid('loaded');
        }
	}],
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$("#htm_add").window({
			title : '新增计划评论标签',
			modal : true,
			width : 400,
			height : 250,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function(){
				$("#descriptionInput").val("");
				$("#startTimeInput").datebox("clear");
				$("#deadlineInput").datebox("clear");
				$("#workStartTimeInput").timespinner("clear");
				$("#workEndTimeInput").timespinner("clear");
			}
		});
		removePageLoading();
		$("#main").show();
	};
	
	function addSubmit(){
		var addForm = $('#add_form');
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		$.post(addForm.attr("action"),addForm.serialize(),
			function(result){
				formSubmitOnce = true;
				$('#htm_add .opt_btn').show();
				$('#htm_add .loading').hide();
				$("#descriptionInput").val("");
				$("#startTimeInput").datebox("clear");
				$("#deadlineInput").datebox("clear");
				$("#workStartTimeInput").timespinner("clear");
				$("#workEndTimeInput").timespinner("clear");
				if(result['result'] == 0) {
					$('#htm_add').window('close');  //关闭添加窗口
					$.messager.alert('成功提示',result['msg']);
					maxId = 0;
					myQueryParams.maxId = maxId;
					loadPageData(1); //重新装载第1页数据
					return false;
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				return false;
			},"json");
	}
	
	
	function initAddWindow(){
		
	}
	
	/**
	 * 数据记录
	 */
	function del() {
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			$.messager.confirm('删除记录', '您确定要删除已选中的记录?', function(r){ 	
				if(r){				
					var ids = [];
					for(var i=0;i<rows.length;i+=1){		
						ids.push(rows[i][id]);	
						rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
					}	
					$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
					$('#htm_table').datagrid('loading');
					$.post(deleteURI + ids,function(result){
						$('#htm_table').datagrid('loaded');
						if(result['result'] == 0) {
							$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
							$("#htm_table").datagrid("reload");
						} else {
							$.messager.alert('提示',result['msg']);
						}
						return false;
					});	
				//	return false;
				}	
			});		
		}	
	}
	
</script>
</head>
<body>
	<div id="main">
		<table id="htm_table">
		</table>
		<div id="tb" style="padding:5px;height:auto" class="none">
			<div>
				<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加计划评论标签" plain="true" iconCls="icon-add" id="addBtn">添加</a>
				<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除计划评论标签" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
	   		</div>
		</div>  
		<!-- 添加计划评论标签 -->
		
		<div id="htm_add">
			<form action="./admin_interact/planCommentLabel_addPlanCommentLabel" id="add_form"  method="post">
				<table id="htm_add_table" width="380">
					<tbody>
						<tr>
							<td class="leftTd">标签：</td>
							<td><input name="description" id="descriptionInput" maxLength="55" required="required"></td>
						</tr>
						<tr>
							<td class="leftTd">有效开始日期：</td>
							<td><input name="startTime" id="startTimeInput" type="text" class="easyui-datebox" required="required"></td>
						</tr>
						<tr>
							<td class="leftTd">有效截止日期：</td>
							<td><input name="deadline" id="deadlineInput" type="text" class="easyui-datebox" required="required"></td>
						</tr>
						<tr>
							<td class="leftTd">开始时间：</td>
							<td><input name=workStartTime id="workStartTimeInput" class="easyui-timespinner" style="width:153px;"  required="required" data-options="showSeconds:true"></td>
						</tr>
						<tr>
							<td class="leftTd">结束时间：</td>
							<td><input name=workEndTime id="workEndTimeInput" class="easyui-timespinner" style="width:153px;"   required="required" data-options="showSeconds:true"></td>
						</tr>
						<tr>
							<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit()">添加</a> 
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