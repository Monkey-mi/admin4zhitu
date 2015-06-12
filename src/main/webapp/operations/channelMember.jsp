<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道用户管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
	var maxId=0,
	loadDateUrl="./admin_op/channelmember_queryChannelMember",
	tableQueryParams = {},
	tableInit = function() {
		tableLoadDate(1);
	},
	searchChannelMaxId = 0,
	searchChannelQueryParams = {
		'maxId':searchChannelMaxId
	},
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
					title  :"频道用户管理",
					width  :1200,
					pageList : [10,30,50,100],
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
						{field : 'id',title:'ID',align:'center',width:80},
						{field : 'channelId',title: '频道ID',align : 'center',width : 80},
						{field : 'channelName',title: '频道名称',align : 'center',width : 130},
						{field : 'userId',title: '用户ID',align : 'center',width : 100},
						{field : 'userName',title: '用户名称',align : 'center',width : 130},
						{field : 'degree',title: '用户等级',align : 'center',width : 200,
							formatter:function(value,row,index){
								switch(value){
									case 1:
										return "【群主】";
									default:
										return "普通用户";
								}
							}	
						},
				  		{field : 'subTime', title:'加入时间',align : 'center' ,width : 130,
							formatter:function(value,row,index){
								var subTime = new Date(value);
								return subTime.format("yyyy/MM/dd hh:mm:ss");
							}
						}
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
		
		$('#ss-channel').combogrid({
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
		    	tableQueryParams.channelId = $("#ss-channel").combogrid('getValue');
		    	$("#htm_table").datagrid("load",tableQueryParams);
		    }
		});
		var p = $('#ss-channel').combogrid('grid').datagrid('getPager');
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
	
	
	function searchByUID(){
		var userId = $("#ss-userId").searchbox('getValue');
		var queryParams = {
				'userId':userId
		};
		$("#htm_table").datagrid("load",queryParams);
		
	}
	
	/**
	 * 搜索频道名称
	 */
	function searchChannel() {
		searchChannelMaxId = 0;
		var query = $('#channel-searchbox').searchbox('getValue');
		searchChannelQueryParams.maxId = searchChannelMaxId;
		searchChannelQueryParams.query = query;
		$("#ss-channel").combogrid('grid').datagrid("load",searchChannelQueryParams);
	}
</script>
</head>
<body>
	<div id="main">
		<div id="tb">
	   		<input id="ss-userId" class="easyui-searchbox" searcher="searchByUID" prompt="输入用户ID搜索" style="width:150px;" />
	   		<span class="search_label">频道: </span>
	  		<input id="ss-channel" style="width:150px;" />
		</div>
		<table id="htm_table"></table>
		<div id="search-channel-tb" style="padding:5px;height:auto" class="none">
			<input id="channel-searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
		</div>
	</div>
</body>
</html>