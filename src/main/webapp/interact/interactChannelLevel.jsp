<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>精品马甲管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
	var maxId=0,
	searchChannelMaxId = 0,
	searchChannelQueryParams = {
		'maxId':searchChannelMaxId
	},
	loadDateUrl="./admin_interact/channelLevel_queryChannelLevel",
	addUrl="./admin_interact/channelLevel_insertChannelLevel",
	delUrl="./admin_interact/channelLevel_batchDeleteChannelLevel?idsStr=",
	updateUrl = "./admin_interact/channelLevel_updateChannelLevel?id=",
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
		$("#htm_table").datagrid({
			title  :"精品马甲管理",
			width  :1200,
			pageList : [10,30,50,100,300],
			pageSize : 10,
			loadMsg:"加载中....",
			url	   :	loadDateUrl,
			queryParams : tableQueryParams,
			remoteSort: true,
			pagination: true,
			idField   :'id',
			pageNumber: pageNum,
			toolbar:'#tb',
			columns: [[
				{field :'ck',checkbox:true},
				{field :'id',title:'ID',align:'center',width:80},
				{field : 'channelName',title: '频道名称',align : 'center',width : 180},
				{field : 'unSuperMinCommentCount',title:'非精选最小评论数',align:'center',width : 100	},
				{field : 'unSuperMmaxCommentCount',title:'非精选最大评论数',align:'center',width : 100	},
				{field : 'superMinCommentCount',title:'精选最小评论数',align:'center',width : 100	},
				{field : 'superMaxCommentCount',title:'精选最大评论数',align:'center',width : 100	},
				{field : 'minuteTime',title:'时间(分钟)',align:'center',width : 100	},
				{field : 'opt',title : '操作',width : 120,align : 'center',rowspan : 1,
					formatter : function(value, row, index ) {
						var retStr="<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:updateInit(\""+ row.id + "\",\"" 
									+ row.channelId + "\",\""  + row.unSuperMinCommentCount + "\",\""  
									+ row.unSuperMmaxCommentCount + "\",\""  
									+ row.superMinCommentCount + "\",\"" 
									+ row.superMaxCommentCount + "\",\"" 
									+ row.minuteTime
									+"\")'>【修改】</a>";
						return retStr;
					}
				}
				
			]],
			onLoadSuccess:myOnLoadSuccess,
			onBeforeRefresh : myOnBeforeRefresh
		});
		var p = $("#htm_table").datagrid("getPager");
		p.pagination({
		});
	}
	
	
	$(function(){
		$('#htm_add').window({
			title : '添加',
			modal : true,
			width : 490,
			height : 300,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function(){
				$("#i-channelId").combogrid('clear');
				$("#i-unsMin").val('');
				$("#i-unsMax").val('');
				$("#i-sMin").val('');
				$("#i-sMax").val('');
				$("#i-time").val('');
				$("#i-id").val('');		
			}
		});
		
		
		$('#i-channelId').combogrid({
			panelWidth : 440,
		    panelHeight : 330,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#search-channel-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'channelName',
		    url : './admin_op/channel_searchChannel',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'id',align : 'center',width : 80},
				{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
					formatter:function(value,row,index) {
						return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				},
				{field : 'channelName',title : '频道名称',align : 'center',width : 280}
		    ]],
		    queryParams:searchChannelQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > searchChannelMaxId) {
						searchChannelMaxId = data.maxId;
						searchChannelQueryParams.maxId = searchChannelMaxId;
					}
				}
		    },
		    onSelect:function(rec){
		    	tableQueryParams.channelId = $("#i-channelId").combogrid('getValue');
		    }
		});
		var p = $('#i-channelId').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					searchChannelMaxId = 0;
					searchChannelQueryParams.maxId = searchChannelMaxId;
				}
			}
		});
		
		tableInit();
		$("#main").show();
	});
	
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
	* 批量删除
	*/
	function del(){
		$.messager.confirm('删除','确定要将选中的内容删除?',function(r){
			if(r){
				update(delUrl);
			}
		});
	}
	
	
	
	function update(url){
		var rows = $('#htm_table').datagrid('getSelections');	
		var ids = [];
		for(var i=0;i<rows.length;i+=1){		
			ids.push(rows[i]['id']);	
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
	
	
	function addInit(){
		$('#htm_add').window('open');
	}
	
	function updateInit(id,channelId,unsMin,unsMax,sMin,sMax,time){
		$("#i-channelId").combogrid('setValue',channelId);
		$("#i-unsMin").val(unsMin);
		$("#i-unsMax").val(unsMax);
		$("#i-sMin").val(sMin);
		$("#i-sMax").val(sMax);
		$("#i-time").val(time);
		$("#i-id").val(id);
		$('#htm_add').window('open');
	}
	
	function addSubmit(){
		var channelId = $("#i-channelId").combogrid('getValue');
		var unSuperMinCommentCount = $("#i-unsMin").val();
		var unSuperMaxCommentCount = $("#i-unsMax").val();
		var superMinCommentCount   = $("#i-sMin").val();
		var superMaxCommentCount   = $("#i-sMax").val();
		var minuteTime 			   = $("#i-time").val();
		var id = $("#i-id").val();
		var url="";
		if(id){
			url = updateUrl+id;
		}else{
			url = addUrl;
		}
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		$.post(url,{
			'channelId'					: channelId,
			'unSuperMinCommentCount'	: unSuperMinCommentCount,
			'unSuperMaxCommentCount'	: unSuperMaxCommentCount,
			'superMinCommentCount'		: superMinCommentCount,
			'superMaxCommentCount'		: superMaxCommentCount,
			'minuteTime'				: minuteTime
		},function(result){
			$('#htm_add .opt_btn').show();
			$('#htm_add .loading').hide();
			if(result['result'] == 0) {
				tableQueryParams.maxId=0;
				$("#htm_table").datagrid("load",tableQueryParams);
				$('#htm_add').window('close');
			} else {
				$.messager.alert('提示',result['msg']);
			}
		},"json");
	}
	
	/**
	 * 搜索频道名称
	 */
	function searchChannel() {
		searchChannelMaxId = 0;
		var query = $('#channel-searchbox').searchbox('getValue');
		searchChannelQueryParams.maxId = searchChannelMaxId;
		searchChannelQueryParams.query = query;
		$("#i-channelId").combogrid('grid').datagrid("load",searchChannelQueryParams);
	}
</script>
</head>
<body>
	<div id="main" class="none">
		<div id="tb">
			<a href="javascript:void(0);" onclick="javascript:addInit();" class="easyui-linkbutton" title="添加" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
		</div>
		<table id="htm_table"></table>
		<!-- 添加记录 -->
		<div id="htm_add">
			<form id="add_form"  method="post">
				<table class="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">频道：</td>
							<td>
								<input id="i-channelId"/>
							</td>
						</tr>
						<tr>
							<td class="leftTd">非精选最小评论数：</td>
							<td><input id="i-unsMin" style="width:170px;" ></td>
						</tr>
						<tr>
							<td class="leftTd">非精选最大评论数：</td>
							<td><input id="i-unsMax" style="width:170px;" ></td>
						</tr>
						<tr>
							<td class="leftTd">精选最小评论数：</td>
							<td><input id="i-sMin" style="width:170px;" ></td>
						</tr>
						<tr>
							<td class="leftTd">精选最大评论数：</td>
							<td><input id="i-sMax" style="width:170px;" ></td>
						</tr>
						<tr>
							<td class="leftTd">时间(分钟)：</td>
							<td><input id="i-time" style="width:170px;" ></td>
						</tr>
						<tr>
							<td class="none"><input id="i-id"></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit();">添加</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div id="search-channel-tb" style="padding:5px;height:auto" class="none">
			<input id="channel-searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
		</div>
	</div>
	
</body>
</html>