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
<script type="text/javascript" src="${webRootPath }/common/js/htmCRUD20140108.js"></script>
<title>自动回复计划管理</title>
<script type="text/javascript">
	var maxId =  0,
	myRowStyler = 0,
	hideIdColumn = false,
	init = function() {
		toolbarComponent = "#tb",
		myQueryParams = {
			'maxId' : maxId,
		},
		loadPageData(initPage);
	},
	myOnLoadBefore = function() {
		interacts = {};
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
	},
	htmTableTitle = "自动回复计划列表", //表格标题
	htmTableWidth = 1170,
	loadDataURL = "./admin_interact/autoResponseSchedula_queryAutoResponseSchedulaForTable", //数据装载请求地址
	deleteURI = "./admin_interact/autoResponseSchedula_delAutoResponseByIds?ids=", //删除请求地址


	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : '计划id',align : 'center', width : 150},
		{field : 'autoResponseId',title : '自动回复id',align : 'center', width : 150},
		{field : 'worldId',title : '织图id',align : 'center', width : 110},
		{field : 'currentComment',title : '计划评论内容',align : 'center', width : 180},
		{field : 'schedula', title:'计划日期', align : 'center',width : 150},
		{field : 'addDate',title:'添加日期',algin:'center',width:120},
  		{field : 'modifyDate',title:'修改日期',algin:'center',width:120},
  		{field : 'operatorName',title:'操作人',algin:'center',width:80},
  		{field : 'valid',title : '有效性',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已完成' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
  		{field : 'complete',title : '完成',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已完成' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		}
	];
	
	function searchById(){
		var wid=$("#ss_wid").searchbox("getValue");
		myQueryParams.autoResponseId = wid;
		$("#htm_table").datagrid('load',myQueryParams);
	}
	
	function searchAutoResponseSchedula(){
		myQueryParams.addDate = $("#beginDate").datetimebox("getValue");
		myQueryParams.modifyDate  = $("#endDate").datetimebox("getValue");
		$("#htm_table").datagrid('load',myQueryParams);
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
						ids.push(rows[i]['id']);	
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
<table id="htm_table"></table>
<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<input id="ss_wid" class="easyui-searchbox" prompt="请输入自动回复id" style="width:150px;" searcher="searchById">
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除计划评论标签" plain="true" iconCls="icon-cut" id="delBtn">批量删除</a>
			<div style="float:right;">
				<span>起始时间：</span>
		   		<input id="beginDate" name="beginDate" class="easyui-datetimebox"/>
		   		<span>结束时间：</span>
		   		<input id="endDate" name="endDate" class="easyui-datetimebox"/>
		   		<a href="javascript:void(0);" onclick="javascript:searchAutoResponseSchedula();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	   		</div>
   		</div>
	</div>  
</body>
</html>