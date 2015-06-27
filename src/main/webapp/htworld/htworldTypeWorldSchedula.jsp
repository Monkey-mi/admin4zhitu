<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/htmCRUD20140108.js"></script>
<title>分类织图计划管理</title>
<script type="text/javascript">
	var maxSchedula = null,
	myRowStyler = 0,
	recordIdKey = "type_world_id",
	myIdField = "type_world_id",
	hideIdColumn = false,
	init = function() {
		toolbarComponent = "#tb",
		myQueryParams = {
			'maxSchedula' : maxSchedula,
		},
		loadPageData(initPage);
	},
	myOnLoadBefore = function() {
		interacts = {};
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxSchedula = null;
			myQueryParams.maxSchedula = maxSchedula;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxSchedula > maxSchedula) {
				maxSchedula = data.maxSchedula;
				myQueryParams.maxSchedula = maxSchedula;
			}
		}
	},
	htmTableTitle = "分类织图计划列表", //表格标题
	loadDataURL = "./admin_ztworld/typeSchedula_queryTypeWorldSchedula", //数据装载请求地址
	deleteURI = "./admin_ztworld/typeSchedula_delTypeWorldSchedulaByIds?idStr=", //删除请求地址

	
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'type_world_id',title : '分类织图id',align : 'center', width : 150},
		{field : 'schedula', title:'计划日期', align : 'center',width : 150},
		{field : 'addDate',title:'添加日期',algin:'center',width:120},
  		{field : 'modifyDate',title:'修改日期',algin:'center',width:120},
  		{field : 'operatorName',title:'操作人',algin:'center',width:120},
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
	],
	onAfterInit = function() {
		$('#htm_resort').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 245,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-tip',
			resizable : false
		});
		removePageLoading();
		$("#main").show();
	};
	
	
	
	function searchByWid(){
		var wid=$("#ss_wid").searchbox("getValue");
		myQueryParams.type_world_id = wid;
		$("#htm_table").datagrid('load',myQueryParams);
	}
	
	function searchTypeSchedula(){
		var timeType = $("#ss_time_type").combobox("getValue");
		myQueryParams.timeType = timeType;
		myQueryParams.beginTime = $("#beginDate").datetimebox("getValue");
		myQueryParams.endTime  = $("#endDate").datetimebox("getValue");
		$("#htm_table").datagrid('load',myQueryParams);
	}
	
	/**
	 * 重新排序
	 */
	function reSortInit() {
		$("#resort_form").find('input[name="reIndexId"]').val('');
		$("#schedula").datetimebox('clear');
		$('#htm_resort .opt_btn').show();
		$('#htm_resort .loading').hide();
		
		var rows = $("#htm_table").datagrid('getSelections');
		$('#resort_form .reindex_column').each(function(i){
			if(i<rows.length)
				$(this).val(rows[i]['type_world_id']);
		});
		
		// 打开添加窗口
		$("#htm_resort").window('open');
		
	}

	function submitReSortForm() {
		var $form = $('#resort_form');
		if($form.form('validate')) {
			$('#htm_resort .opt_btn').hide();
			$('#htm_resort .loading').show();
			$('#resort_form').form('submit', {
				url: $form.attr('action'),
				success: function(data){
					var result = $.parseJSON(data);
					$('#htm_resort .opt_btn').show();
					$('#htm_resort .loading').hide();
					if(result['result'] == 0) { 
						$('#htm_resort').window('close');  //关闭添加窗口
						loadPageData(1);
					} else {
						$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
					}
					
				}
			});
		}
		
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
						ids.push(rows[i]['type_world_id']);	
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
			<input id="ss_wid" class="easyui-searchbox" prompt="请输入织图id" style="width:150px;" searcher="searchByWid">
			<a href="javascript:void(0);" onclick="javascript:reSortInit();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">重新排序</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除计划评论标签" plain="true" iconCls="icon-cut" id="delBtn">批量删除</a>
			<div style="float:right;">
				<select id="ss_time_type" class="easyui-combobox"  style="width:100px;">
			        <option value="0">计划日期</option>
			        <option value="1">添加日期</option>
			        <option value="2">修改日期</option>
		   		</select>
				<span>起始时间：</span>
		   		<input id="beginDate" name="beginDate" class="easyui-datetimebox"/>
		   		<span>结束时间：</span>
		   		<input id="endDate" name="endDate" class="easyui-datetimebox"/>
		   		<a href="javascript:void(0);" onclick="javascript:searchTypeSchedula();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	   		</div>
   		</div>
	</div> 
	
	<!-- 重排精品 -->
	<div id="htm_resort">
		<form id="resort_form" action="./admin_ztworld/typeSchedula_reSort" method="post">
			<table class="htm_resort_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td>
							<input name="reIndexId" class="easyui-validatebox reindex_column" required="true"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">计划更新时间：</td>
						<td><input id="schedula" name="schedula" class="easyui-datetimebox"></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReSortForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_resort').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">排序中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div> 
</body>
</html>