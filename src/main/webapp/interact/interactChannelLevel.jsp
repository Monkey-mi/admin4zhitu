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
			width  :1470,
			height : 433,
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
			fit : false,
			resizable : true,
			columns: [[
				{field :'ck',checkbox:true},
				{field :'id',title:'ID',align:'center',width:50},
				{field : 'channelName',title: '频道名称',align : 'center',width : 120},
				{field : 'unSuperMinCommentCount',title:'非精选最小评论数',align:'center',width : 70	},
				{field : 'unSuperMaxCommentCount',title:'非精选最大评论数',align:'center',width : 70	},
				{field : 'superMinCommentCount',title:'精选最小评论数',align:'center',width : 70	},
				{field : 'superMaxCommentCount',title:'精选最大评论数',align:'center',width : 70	},
				
				{field : 'unSuperMinClickCount',title:'非精选最小播放数',align:'center',width : 70	},
				{field : 'unSuperMaxClickCount',title:'非精选最大播放数',align:'center',width : 70	},
				{field : 'superMinClickCount',title:'精选最小播放数',align:'center',width : 70	},
				{field : 'superMaxClickCount',title:'精选最大播放数',align:'center',width : 70	},
				
				{field : 'unSuperMinLikeCount',title:'非精选最小喜欢数',align:'center',width : 70	},
				{field : 'unSuperMaxLikeCount',title:'非精选最大喜欢数',align:'center',width : 70	},
				{field : 'superMinLikeCount',title:'精选最小喜欢数',align:'center',width : 70	},
				{field : 'superMaxLikeCount',title:'精选最大喜欢数',align:'center',width : 70	},
				
				{field : 'unSuperMinFollowCount',title:'非精选最小粉丝数',align:'center',width : 70	},
				{field : 'unSuperMaxFollowCount',title:'非精选最大粉丝数',align:'center',width : 70	},
				{field : 'superMinFollowCount',title:'精选最小粉丝数',align:'center',width : 70	},
				{field : 'superMaxFollowCount',title:'精选最大粉丝数',align:'center',width : 70	},
				{field : 'minuteTime',title:'时间(分钟)',align:'center',width : 70	},
				{field : 'opt',title : '操作',width : 60,align : 'center',rowspan : 1,
					formatter : function(value, row, index ) {
						var retStr="<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:updateInit(\""+ row.id + "\",\"" 
									+ row.channelId + "\",\""  
									+ row.unSuperMinCommentCount + "\",\""  
									+ row.unSuperMaxCommentCount + "\",\""  
									+ row.superMinCommentCount + "\",\"" 
									+ row.superMaxCommentCount + "\",\"" 
									
									+ row.unSuperMinClickCount + "\",\""  
									+ row.unSuperMaxClickCount + "\",\""  
									+ row.superMinClickCount + "\",\"" 
									+ row.superMaxClickCount + "\",\""
									
									+ row.unSuperMinLikeCount + "\",\""  
									+ row.unSuperMaxLikeCount + "\",\""  
									+ row.superMinLikeCount + "\",\"" 
									+ row.superMaxLikeCount + "\",\""
									
									+ row.unSuperMinFollowCount + "\",\""  
									+ row.unSuperMaxFollowCount + "\",\""  
									+ row.superMinFollowCount + "\",\"" 
									+ row.superMaxFollowCount + "\",\""
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
			height : 360,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function(){
				$("#i-channelId").combogrid('clear');
				$("#i-unsMinComment").val('');
				$("#i-unsMaxComment").val('');
				$("#i-sMinComment").val('');
				$("#i-sMaxComment").val('');
				
				$("#i-unsMinClick").val('');
				$("#i-unsMaxClick").val('');
				$("#i-sMinClick").val('');
				$("#i-sMaxClick").val('');
				
				$("#i-unsMinLike").val('');
				$("#i-unsMaxLike").val('');
				$("#i-sMinLike").val('');
				$("#i-sMaxLike").val('');
				
				$("#i-unsMinFollow").val('');
				$("#i-unsMaxFollow").val('');
				$("#i-sMinFollow").val('');
				$("#i-sMaxFollow").val('');
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
	
	function updateInit(id,channelId,
			unsMinComment,unsMaxComment,sMinComment,sMaxComment,
			unsMinClick,unsMaxClick,sMinClick,sMaxClick,
			unsMinLike,unsMaxLike,sMinLike,sMaxLike,
			unsMinFollow,unsMaxFollow,sMinFollow,sMaxFollow,
			time){
		$("#i-channelId").combogrid('setValue',channelId);
		$("#i-unsMinComment").val(unsMinComment);
		$("#i-unsMaxComment").val(unsMaxComment);
		$("#i-sMinComment").val(sMinComment);
		$("#i-sMaxComment").val(sMaxComment);
		
		$("#i-unsMinClick").val(unsMinClick);
		$("#i-unsMaxClick").val(unsMaxClick);
		$("#i-sMinClick").val(sMinClick);
		$("#i-sMaxClick").val(sMaxClick);
		
		$("#i-unsMinLike").val(unsMinLike);
		$("#i-unsMaxLike").val(unsMaxLike);
		$("#i-sMinLike").val(sMinLike);
		$("#i-sMaxLike").val(sMaxLike);
		
		$("#i-unsMinFollow").val(unsMinFollow);
		$("#i-unsMaxFollow").val(unsMaxFollow);
		$("#i-sMinFollow").val(sMinFollow);
		$("#i-sMaxFollow").val(sMaxFollow);
		
		$("#i-time").val(time);
		$("#i-id").val(id);
		$('#htm_add').window('open');
	}
	
	function addSubmit(){
		var channelId = $("#i-channelId").combogrid('getValue');
		var unSuperMinCommentCount = $("#i-unsMinComment").val();
		var unSuperMaxCommentCount = $("#i-unsMaxComment").val();
		var superMinCommentCount   = $("#i-sMinComment").val();
		var superMaxCommentCount   = $("#i-sMaxComment").val();
		
		var unSuperMinClickCount = $("#i-unsMinClick").val();
		var unSuperMaxClickCount = $("#i-unsMaxClick").val();
		var superMinClickCount   = $("#i-sMinClick").val();
		var superMaxClickCount   = $("#i-sMaxClick").val();
		
		var unSuperMinLikeCount = $("#i-unsMinLike").val();
		var unSuperMaxLikeCount = $("#i-unsMaxLike").val();
		var superMinLikeCount   = $("#i-sMinLike").val();
		var superMaxLikeCount   = $("#i-sMaxLike").val();
		
		var unSuperMinFollowCount = $("#i-unsMinFollow").val();
		var unSuperMaxFollowCount = $("#i-unsMaxFollow").val();
		var superMinFollowCount   = $("#i-sMinFollow").val();
		var superMaxFollowCount   = $("#i-sMaxFollow").val();
		
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
			
			'unSuperMinClickCount'		: unSuperMinClickCount,
			'unSuperMaxClickCount'		: unSuperMaxClickCount,
			'superMinClickCount'		: superMinClickCount,
			'superMaxClickCount'		: superMaxClickCount,
			
			'unSuperMinLikeCount'		: unSuperMinLikeCount,
			'unSuperMaxLikeCount'		: unSuperMaxLikeCount,
			'superMinLikeCount'			: superMinLikeCount,
			'superMaxLikeCount'			: superMaxLikeCount,
			
			'unSuperMinFollowCount'		: unSuperMinFollowCount,
			'unSuperMaxFollowCount'		: unSuperMaxFollowCount,
			'superMinFollowCount'		: superMinFollowCount,
			'superMaxFollowCount'		: superMaxFollowCount,
			
			'minuteTime'				: minuteTime
		},function(result){
			$('#htm_add .opt_btn').show();
			$('#htm_add .loading').hide();
			if(result['result'] == 0) {
				$("#htm_table").datagrid("reload");
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
	
	/*
	add by mishengliang 07-31-2015
	在频道等级中加入频道搜索框
	*/
	$(function() {
		$('#selectChannelId').combogrid(
						{
							panelWidth : 440,
							panelHeight : 330,
							loadMsg : '加载中，请稍后...',
							pageList : [ 4, 10, 20 ],
							pageSize : 4,
							toolbar : "#search-channel-tb01",
							multiple : false,
							required : false,
							idField : 'id',
							textField : 'channelName',
							url : './admin_op/channel_searchChannel',
							pagination : true,
							columns : [ [
									{
										field : 'id',
										title : 'id',
										align : 'center',
										width : 80
									},
									{
										field : 'channelIcon',
										title : 'icon',
										align : 'center',
										width : 60,
										height : 60,
										formatter : function(value, row, index) {
											return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
										}
									}, {
										field : 'channelName',
										title : '频道名称',
										align : 'center',
										width : 280
									} ] ],
 							queryParams : searchChannelQueryParams,
							onLoadSuccess : function(data) {
								if (data.result == 0) {
									if (data.maxId > searchChannelMaxId) {
										searchChannelMaxId = data.maxId;
										searchChannelQueryParams.maxId = searchChannelMaxId;
									}
								}
							}, 
							onSelect : function(rowIndex,rowData) {
								channelId = rowData.id;
								tableQueryParams.channelId = channelId;
								$("#htm_table").datagrid('load',tableQueryParams);
							}
						});
	});

	function searchChannel01() {
		var query = $('#channel-searchbox01').searchbox('getValue');
		searchChannelQueryParams.maxId = searchChannelMaxId;
		searchChannelQueryParams.query = query;
		$("#selectChannelId").combogrid('grid').datagrid("load",
				searchChannelQueryParams);
	}
</script>
</head>
<body>
	<div id="main" class="none">
		<div id="tb">
			<a href="javascript:void(0);" onclick="javascript:addInit();" class="easyui-linkbutton" title="添加" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<input id="selectChannelId">
		</div>
		<table id="htm_table"></table>
		<!-- 添加记录 -->
		<div id="htm_add">
			<form id="add_form"  method="post">
				<table class="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd" style="width:230px;"><span>频道：</span><input id="i-channelId" style="width:80px;"/></td>
							<td>
								<span>时 间 ( 分 钟 ) ：</span><input id="i-time" style="width:80px;" >
							</td>
						</tr>
						<tr>
							<td class="leftTd"><span>非精选最小评论数：</span><input id="i-unsMinComment" style="width:80px;" ></td>
							<td ><span>非精选最大评论数：</span><input id="i-unsMaxComment" style="width:80px;" ></td>
						</tr>
						<tr>
							<td class="leftTd"><span>精选最小评论数：</span><input id="i-sMinComment" style="width:80px;" ></td>
							<td ><span>精 选 最大评论数：</span><input id="i-sMaxComment" style="width:80px;" ></td>
						</tr>
						
						<tr>
							<td class="leftTd"><span>非精选最小播放数：</span><input id="i-unsMinClick" style="width:80px;" ></td>
							<td><span>非精选最大播放数：</span><input id="i-unsMaxClick" style="width:80px;" ></td>
						</tr>
						<tr>
							<td class="leftTd"><span>精选最小播放数：</span><input id="i-sMinClick" style="width:80px;" ></td>
							<td><span>精 选 最大播放数：</span><input id="i-sMaxClick" style="width:80px;" ></td>
						</tr>
						
						<tr>
							<td class="leftTd"><span>非精选最小喜欢数：</span><input id="i-unsMinLike" style="width:80px;" ></td>
							<td><span>非精选最大喜欢数：</span><input id="i-unsMaxLike" style="width:80px;" ></td>
						</tr>
						<tr>
							<td class="leftTd"><span>精选最小喜欢数：</span><input id="i-sMinLike" style="width:80px;" ></td>
							<td><span>精 选 最大喜欢数：</span><input id="i-sMaxLike" style="width:80px;" ></td>
						</tr>
						
						<tr>
							<td class="leftTd"><span>非精选最小粉丝数：</span><input id="i-unsMinFollow" style="width:80px;" ></td>
							<td><span>非精选最大粉丝数：</span><input id="i-unsMaxFollow" style="width:80px;" ></td>
						</tr>
						<tr>
							<td class="leftTd"><span>精选最小粉丝数：</span><input id="i-sMinFollow" style="width:80px;" ></td>
							<td><span>精 选 最大粉丝数：</span><input id="i-sMaxFollow" style="width:80px;" ></td>
						</tr>
						
						<tr>
							<td class="leftTd"></td>
							<td><span></span></td>
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
		<div id="search-channel-tb01" style="padding:5px;height:auto" class="none">
			<input id="channel-searchbox01" class="easyui-searchbox" searcher="searchChannel01" prompt="频道名/ID搜索" style="width:200px;"/>
		</div>
	</div>
	
</body>
</html>