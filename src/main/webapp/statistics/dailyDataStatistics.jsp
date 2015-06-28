<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据统计：每日数据</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

	htmTablePageList = [25];
	
	// 下列为获取昨天的日期
	var today = new Date();
	var year = today.getFullYear();
	var month = (today.getMonth() + 1);
	var day = today.getDate() - 1;
	if(month < 10) {month = "0" + month;}
	if(day < 10) {day = "0" + day;}
	var yesterdayStr = year + '-' + month + "-" + day;

	timeCompare = function(date) {
		var startTimeStr = $("#startTime").datebox('getValue');
		var endTimeStr = $("#endTime").datebox('getValue');
		var startTime = Date.parse(startTimeStr);
		var endTime = Date.parse(endTimeStr);
		if(endTime < startTime) {
			var time = $(this).datebox('getValue');
			$("#startTime").datebox('setValue', time);
			$("#endTime").datebox('setValue', time);
		}
		
		if(endTime > today){
			$("#endTime").datebox('setValue', yesterdayStr);
		}
		
		if(startTime > today) {
			$("#startTime").datebox('setValue', yesterdayStr);
			$("#endTime").datebox('setValue', yesterdayStr);
		}
	}

	loadDataURL = "./admin_statistics/daily_queryDailyData";	//数据装载请求地址
	
	// 定义工具栏
	toolbarComponent = '#tb';
	
	columnsFields = [
	    			{field:'id',title:'ID'},
	    	        {field:'dataCollectDate',title:'日期',
	    				formatter: function(value,row,index) {
	    					return value.split('T')[0];
	    	  			}
	    	        },
	    	        {field:'channelId',title:'频道ID'},
	    	        {field:'channelName',title:'频道名称'},
	    	        {field:'pvCount',title:'访问数',sortable:true},
	    	        {field:'worldAddCount',title:'新增织图数',sortable:true},
	    	        {field:'memberAddCount',title:'新增订阅数',sortable:true},
	    	        {field:'commentAddCount',title:'新增评论数',sortable:true},
	    	        {field:'likedAddCount',title:'新增点赞数',sortable:true}
	    	    ];
	
	onAfterInit = function(){
		
		$("#startTime").datebox({
			value:yesterdayStr,
			onSelect:timeCompare
		});
		
		$("#endTime").datebox({
			value:yesterdayStr,
			onSelect:timeCompare
		});
	};
	
	/**
	 * 根据日期查询
	 */
	function searchByTime(){
		var startTime = $('#startTime').datetimebox('getValue');
		var endTime = $('#endTime').datetimebox('getValue');
		var themeId = $("#channelThemeId").combobox('getValue');
		var params = {
				startTime: startTime,
				endTime: endTime,
				themeId: themeId
		};
		$('#htm_table').datagrid('load',params);
	};
	
	/**
	 * 根据频道id或者名称（支持模糊）查询
	 */
	function searchByNameOrId(){
		var channelNameOrId = $("#ss_channelNameOrId").searchbox('getValue');
		var params = {};
		if(isNaN(channelNameOrId)){
			params['channelName'] = channelNameOrId;
		}else{
			params['channelId'] = channelNameOrId;
		}
		$('#htm_table').datagrid('load',params);
	};

</script>
</head>
<body>
	<table id="htm_table"></table>
	<div id="tb" style="padding:10px;height:auto" class="none">
		<span class="search_label">起始时间:</span><input id="startTime" style="width:100px">
    	<span class="search_label">结束时间:</span><input id="endTime" style="width:100px">
    	<span class="search_label">频道专题：</span>
    	<input id="channelThemeId" class="easyui-combobox" onchange="validateSubmitOnce=true;" 
				data-options="valueField:'themeId',textField:'themeName',url:'./admin_op/v2channel_queryChannelThemeList'" style="width:206px;">
    	<a href="javascript:void(0)" onclick="javascript:searchByTime();" id="searchBtn" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    	<input id="ss_channelNameOrId" type="text" class="easyui-searchbox" data-options="searcher:searchByNameOrId,iconCls:'icon-search',prompt:'请填写频道名称或频道ID'" style="width:200px"></input>
	</div>
</body>
</html>