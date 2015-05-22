<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/tourlistHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/htworld/js/htworldList2.js?ver=${webVer}"></script>
<style type="text/css">
#superb-title {
	color:#fafafa;
	font-size:36px;
	margin: 42px 0 34px 0;
	display:none;
}

#superb-box {
		margin:0 auto;
		width:1024px;
		text-align:left;
	}
.superb {
	width:320px;
	height:320px;
	display:inline-block;
	position:relative;	
	margin-bottom:32px;
}
.superb-margin {
	margin-right:32px;
}
</style>
</head>
<body>
	<div>
			<!-- <a href="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" style="vertical-align:middle;" title="删除织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a> -->
	        <span class="search_label">起始时间:</span><input id="startTime"  style="width:100px">
	        <span class="search_label">结束时间:</span><input id="endTime" style="width:100px">
	        <span class="search_label">客户端:</span>
			<select id="phoneCode" class="easyui-combobox" name="phoneCode" style="width:75px;">
				<option value="">所有</option>
		        <option value="0">IOS</option>
		        <option value="1">安卓</option>
	   		</select>
	   		<span class="search_label">有效状态:</span>
	   		<select id="valid" class="easyui-combobox" name="valid" style="width:75px;">
				<option value="" selected="selected">所有状态</option>
		        <option value="1">有效</option>
		        <option value="0">无效</option>
	   		</select>
	   		<span class="search_label">屏蔽状态:</span>
	   		<select id="shield" class="easyui-combobox" name="shield" style="width:75px;">
				<option value="" selected="selected">所有状态</option>
		        <option value="1">屏蔽</option>
		        <option value="0">非屏蔽</option>
	   		</select>
	   		<input class="easyui-combobox" name="user_level_id" id="search_userLevelId" onchange="validateSubmitOnce=true;" style="width:150px"
								data-options="valueField:'id',textField:'level_description',url:'./admin_interact/userlevel_QueryUserLevel'"/>
	   		<div style="display:inline-block; vertical-align:middle;">
	   			<a href="javascript:void(0)" id="searchBtn">查询</a>
			    <div id="mm" style="width:100px;">
				    <div id="searchTodayBtn">今日分享</div>
				    <div id="searchWeekBtn">本周分享</div>
				    <div id="searchLastWeekBtn">上周分享</div>
				    <div id="searchMonthBtn">本月分享</div>
				    <div id="searchLastMonthBtn">上月分享</div>
				</div>
	   		</div>
   		</div>
	<div id="superb-box">
	</div>
</body>
</html>