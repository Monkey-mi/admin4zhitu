<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自动回复列表管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<script type="text/javascript">
var maxId = 0,
	myRowStyler= 0,
	htmTableTitle = "自动回复列表", //表格标题
	myIdField='keyId',
	hideIdColumn = false,
	loadDataURL = "./admin_op/xmsResponse_queryXiaoMiShuResponseForTable", //数据装载请求地址
	deleteURI = "./admin_op/xmsResponse_batchDelResponseKey?keyIdStr=",//删除
	htmTablePageList = [20,30,50,100,200],
	myQueryParams={},
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams.maxId = maxId;
		
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
		{field : 'keyId',title : '关键字ID',align : 'center', width : 55},
		{field : 'moduleId',title : '模块ID',align : 'center',width : 75},
		{field : 'moduleName',title: '模块',align : 'center',width : 80},
		{field : 'key', title:'关键词',align : 'center' ,width : 120,editor:'text'},
		{field : 'responseId',title : '回复内容ID',align : 'center',width : 80},
		{field : 'content',title : '回复内容',width : 440},
		{field : 'keyValid',title : '有效性',width : 60,
			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
		},
		{field : 'operatorName',title: '最后修改者',align : 'center',width : 80},
		{field : 'modifyDate', title:'最后修改时间日期', align : 'center',width : 150, 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		}
	],
	onAfterInit = function() {
		
	};
	
	/**
	 * 删除数据记录
	 */
	function del() {
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			$.messager.confirm('删除记录', '您确定要删除已选中的记录?', function(r){ 	
				if(r){				
					var ids = [];
					for(var i=0;i<rows.length;i+=1){		
						ids.push(rows[i]['keyId']);	
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
	
	function searchContentKey(){
		var moduleId = $("#module_id").combobox('getValue');
		var keyId = $("#ss_contenKey").searchbox('getValue');
		myQueryParams.maxId = 0;
		myQueryParams.moduleId = moduleId;
		myQueryParams.keyId=keyId;
		$('#htm_table').datagrid('load',myQueryParams);
	}
		
		
</script>

</head>
<body>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="批量删除key" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<input class="easyui-combobox"  id="module_id" name="moduleId" onchange="validateSubmitOnce=true;" style="width:204px"
								data-options="valueField:'moduleId',textField:'moduleName',url:'./admin_op/xmsResponse_queryResponseModule'"/>
			<input id="ss_contenKey" searcher="searchContentKey" class="easyui-searchbox" prompt="输入关键字ID" style="width:120px;">
   		</div>
	</div> 
	<table id="htm_table"></table>		
</body>
</html>