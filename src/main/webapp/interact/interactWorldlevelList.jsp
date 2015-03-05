<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js"></script>
<title>等级织图列表管理</title>
<script type="text/javascript">
	var maxWId = 0,
//	recordIdKey = 'world_id',
	myRowStyler = 0;
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxWId' : maxWId,
		},
		loadPageData(initPage);
	},
	myOnLoadBefore = function() {
		interacts = {};
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxWId = null;
			myQueryParams.maxWId = maxWId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxWId > maxWId) {
				maxWId = data.maxWId;
				myQueryParams.maxWId = maxWId;
			}
		}
	},
	htmTableTitle = "等级织图列表", //表格标题
	htmTableWidth = 860,
	worldKey = "worldId",
	loadDataURL = "./admin_interact/worldlevelList_queryWorldlevelList", //数据装载请求地址
	deleteURI = "./admin_interact/worldlevelList_delWorldlevelListByWIds?widsStr=", //删除请求地址
//	addWidth = 500, //添加信息宽度
//	addHeight = 160, //添加信息高度
//	addTitle = "添加分类织图", //添加信息标题

	columnsFields = [
	    {field : 'ck',checkbox : 'true'},
		{field : 'world_id',title : '织图ID',align : 'center', width : 160},
		{field : 'world_level_id', title:'等级ID', align : 'center',width : 150},
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
  		{field : 'addDate',title:'添加日期',algin:'center',width:120},
  		{field : 'modifyDate',title:'修改日期',algin:'center',width:120},
  		{field : 'operatorName',title:'操作人',algin:'center',width:120}
	];
	
	function searchByWid(){
		var wid=$("#ss_worldId").searchbox('getValue');
		myQueryParams.world_id = wid;
		$("#htm_table").datagrid('load',myQueryParams);
		
	}
	//根据时间来查找
 	function searchByTypeTime(){
		var timeType = $("#ss_time_type").combobox("getValue");
		myQueryParams.timeType = timeType;
		myQueryParams.maxWId = maxWId;
		myQueryParams.beginTime = $("#beginDate").datetimebox("getValue");
		myQueryParams.endTime  = $("#endDate").datetimebox("getValue");
		$("#htm_table").datagrid('load',myQueryParams);
	}
</script>
</head>
<body>
	<div id="main">
		<div id="tb" style="padding:5px;height:auto" class="none">
			<input id="ss_worldId" class="easyui-searchbox" prompt="请输入织图id" style="width:150px;" searcher="searchByWid">
			<div style="float:right;">
				<select id="ss_time_type" class="easyui-combobox"  style="width:100px;">
			        <option value="1">添加日期</option>
			        <option value="2">修改日期</option>
		   		</select>
				<span>起始时间：</span>
		   		<input id="beginDate" name="beginDate" class="easyui-datetimebox"/>
		   		<span>结束时间：</span>
		   		<input id="endDate" name="endDate" class="easyui-datetimebox"/>
		   		<a href="javascript:void(0);" onclick="javascript:searchByTypeTime();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	   		</div>
		</div>
		<table id="htm_table"></table>
	</div>
</body>
</html>