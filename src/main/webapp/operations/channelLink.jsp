<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划评论</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css" />
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
	var maxId = 0,
	htmTableTitle = "频道关联管理", //表格标题
	htmTableWidth = 1140,
	myPageSize = 10,
	myRowStyler= 0,
	loadDataURL = "./admin_op/channelLink_queryOpChannelLink", //数据装载请求地址
	deleteURI = "./admin_op/channelLink_batchDeleteOpChannelLink";//删除
	myQueryParams={},
	channelQueryParams={},
	channelMaxId=0,
	linkChannelQueryParams={},
	linkChannelMaxId=0,
	addWidth = 420, //添加信息宽度
	addHeight = 380, //添加信息高度
	hideIdColumn = false,
	addTitle = "添加频道关联"; //添加信息标题
	init = function() {
		toolbarComponent = '#tb';
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
		{field : 'channelId',title : '频道ID',align : 'center', width : 80},
		{field : 'channelName',title: '频道名称',align: 'center', width : 200},
		{field : 'linkChannelId',title : '关联频道ID',align : 'center',width : 80},
		{field : 'linkChannelName',title : '关联频道名称',align : 'center',width : 200},
	],
	
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
	
		$('#ss-channel').combogrid({
			panelWidth : 320,
		    panelHeight : 490,
		    loadMsg : '加载中，请稍后...',
		    multiple : false,
		    required : false,
		   	idField : 'channelId',
		    textField : 'channelName',
		    url : './admin_op/v2channel_queryOpChannel',
		    pagination : true,
		    remoteSort : false,
		    columns:[[
				{field : 'channelId',title : 'ID', align : 'center',width : 60},
				{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
					formatter:function(value,row,index) {
						return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				},
				{field : 'channelName',title : '频道名称',align : 'center',width : 120},
				{field : 'channelName',title : '频道名称',align : 'center',width : 120},
				{field : 'channelDesc',title : '频道描述',align : 'center',width : 200},
			]],
			queryParams:channelQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > channelMaxId) {
						channelMaxId = data.maxId;
						channelQueryParams.maxId = channelMaxId;
					}
				}
		    }
		});
		
		$('#ss-channel-link').combogrid({
			panelWidth : 320,
		    panelHeight : 490,
		    loadMsg : '加载中，请稍后...',
		    multiple : false,
		    required : false,
		   	idField : 'channelId',
		    textField : 'channelName',
		    url : './admin_op/v2channel_queryOpChannel',
		    pagination : true,
		    remoteSort : false,
		    columns:[[
				{field : 'channelId',title : 'ID', align : 'center',width : 60},
				{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
					formatter:function(value,row,index) {
						return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				},
				{field : 'channelName',title : '频道名称',align : 'center',width : 120},
				{field : 'channelName',title : '频道名称',align : 'center',width : 120},
				{field : 'channelDesc',title : '频道描述',align : 'center',width : 200},
			]],
			queryParams:linkChannelQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > channelMaxId) {
						linkChannelMaxId = data.maxId;
						linkChannelQueryParams.maxId = linkChannelMaxId;
					}
				}
		    }
		});
		
	
		$("#addChannel").window({
			title : '新增计划评论标签',
			modal : true,
			width : 420,
			height : 300,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function(){
				$("#groupIdInput").combobox('clear');
				$("#contentInput").val("");
			}
		});
		removePageLoading();
		$("#main").show();
		
		$("#groupIdInput").combobox({
			valueField:'id',
			textField :'description',
			url		  :'./admin_interact/planCommentLabel_queryPlanCommentLabel',
			onSelect  : function(rec){
				myQueryParams.groupId = rec.id;
				myQueryParams.maxId   = 0;
				$("#htm_table").datagrid('loading');
				$("#htm_table").datagrid('reload',myQueryParams);
				$("#htm_table").datagrid('loaded');
			}
		});
	};
	
	function addSubmit(){
		var addForm = $('#add_form');
		var channelId = $("#ss-channel").combogrid('getValue');
		var linkId = $("#ss-channel-link").combogrid('getValue');
		if(!channelId || !linkId){
			alert("频道和关联频道都不能为空");
			return false;
		}
		$('#addChannel .opt_btn').hide();
		$('#addChannel .loading').show();
		$.post(addForm.attr("action"),{
			'channelId'	:	channelId,
			'linkId'	:	linkId
		},function(result){
			if(result['result'] == 0) {
				$("#htm_table").datagrid('reload');
				$('#addChannel .opt_btn').show();
				$('#addChannel .loading').hide();
				$('#addChannel').window('close');  //关闭添加窗口
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				$('#addChannel .opt_btn').show();
				$('#addChannel .loading').hide();
			}
		},"json");
	}
	
	
	function initAddWindow(){
		$('#addChannel').window('open');
	}
	
	/**
	 * 数据记录
	 */
	function del() {
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			$.messager.confirm('删除记录', '您确定要删除已选中的记录?', function(r){ 	
				if(r){				
					$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
					$('#htm_table').datagrid('loading');
					$.post(deleteURI,{
						'rowJson':JSON.stringify(rows)
					},function(result){
						$('#htm_table').datagrid('loaded');
						if(result['result'] == 0) {
							$.messager.alert('提示',result['msg'] + row.length + "条记录！");
							$("#htm_table").datagrid("reload");
						} else {
							$.messager.alert('提示',result['msg']);
						}
						return false;
					});	
				}	
			});		
		}	
	}
	
	function searchContent()
	{
		var content = $("#ss_conten").searchbox('getValue');
		myQueryParams.content = content;
		$("#htm_table").datagrid('load',myQueryParams);
	}
	
</script>
</head>
<body>
	<div id="main">
		<table id="htm_table">
		</table>
		<div id="tb" style="padding:5px;height:auto" class="none">
			<div>
				<a href="javascript:void(0);" onclick="javascript:initAddWindow();" class="easyui-linkbutton" title="添加频道关联" plain="true" iconCls="icon-add" id="addBtn">添加</a>
				<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除频道关联" plain="true" iconCls="icon-cut" id="delBtn">批量删除</a>
	   		</div>
		</div>  
		<!-- 添加计划评论标签 -->
		
		<div id="addChannel">
			<form action="./admin_op/channelLink_insertOpChannelLink" id="add_form"  method="post" display="none">
				<table id="htm_add_table" width="400">
					<tbody>
						<tr>
							<td class="leftTd">频道：</td>
							<td><input id="ss-channel" style="width:200px;" required='true'></td>
						</tr>
						<tr>
							<td class="leftTd">关联频道：</td>
							<td><input id="ss-channel-link" style="width:200px;" required='true'></td>
						</tr>
						<tr>
							<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit()">添加</a> 
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#addChannel').window('close');">取消</a>
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