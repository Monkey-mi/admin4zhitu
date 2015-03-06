<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>马甲发图计划管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">
var maxId = 0,
	worldURLPrefix = 'http://www.imzhitu.com/DT',
	htmTableTitle = "马甲发图计划管理", //表格标题
	htmTablePageList = [10,30,50,100,150],
	loadDataURL = "./admin_interact/interactZombieWorld_queryZombieWorldForTable", //数据装载请求地址
	deleteURI = "./admin_interact/interactZombieWorld_batchSaveZombieWorldToHTWorld?ids=",
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
		{field : recordIdKey,title : 'ID',align : 'center', width : 45},
		{field : 'authorId',title : '马甲ID',align : 'center',width : 80},
		{field : 'thumbTitlePath',title : '缩略图',align : 'center',width : 80,
			formatter: function(value,row,index){
				var imgSrc = baseTools.imgPathFilter(value,'../base/images/bg_empty.png');
				return "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "' />";
			}
		},
		{field : 'worldDesc', title:'织图描述', align : 'center',width : 80},
		{field : 'addDate', title:'添加日期', align : 'center',width : 150, 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		},
		{field : 'modifyDate', title:'最后修改时间日期', align : 'center',width : 150, 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		},
		{field : 'complete',title : '完成',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
  		{field : 'shortLink', title:'短链',align : 'center',width : 320,
			styler: function(value,row,index){ 
				return 'cursor:pointer;';
			},
			formatter : function(value, row, rowIndex ) {
				if(value == "" || value == undefined) return "";
				return "<a title='播放织图' class='updateInfo' href='javascript:showWorld(\""
						+ worldURLPrefix + value + "\")'>" +value+"</a>";
			}
		},
		{field : 'htworldId', title:'织图ID', align : 'center',width : 80}
		
	],
	onAfterInit = function() {
		$("#batch-save").window({
			title : '批量发图',
			modal : true,
			width : 420,
			height : 150,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function(){
				$("#beginSaveTime").datetimebox('clear');
				$("#timeSpan").numberbox('clear');
			}
		});
		
		
		removePageLoading();
		$("#main").show();
	};

	
	//根据complete查询马甲织图
	function queryZombieWorldByComplete(complete){
		myQueryParams.maxId=0;
		myQueryParams.complete = complete;
		$("#htm_table").datagrid('load',myQueryParams);
	}
	
	//批量发布马甲织图窗口
	function batchSaveZombieWorldToHTWorld(){
		$('#batch-save .opt_btn').hide();
		$('#batch-save .loading').show();
		$("#batch-save").window('open');
		$('#batch-save .opt_btn').show();
		$('#batch-save .loading').hide();
	}
	
	//批量发布马甲织图
	function submitBatchSaveForm(){
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			var ids = [];
			for(var i=0;i<rows.length;i+=1){		
				ids.push(rows[i]['id']);	
				rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
			}	
			$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
			$('#htm_table').datagrid('loading');
			var begin = $("#beginSaveTime").datetimebox('getValue');
			var timeSpan = $("#timeSpan").numberbox('getValue');
			$.post(deleteURI+ids,{
				'begin':begin,
				'timeSpan':timeSpan
			},function(result){
				$('#htm_table').datagrid('loaded');
				if(result['result'] == 0) {
					$.messager.alert('提示',"正在发图中。。。");
				} else {
					$.messager.alert('提示',result['msg']);
				}
				$("#batch-save").window('close');
			});				
		}	
	}
	
	/**
	 * 判断是否选中要操作的记录
	 */
	function isSelected(rows) {
		if(rows.length > 0){
			return true;
		}else{
			$.messager.alert('操作失败','请先选择记录，再执行操作!','error');
			return false;
		}
	}
	
	/**
	* 展示织图
	*/
	function showWorld(uri) {
		$.fancybox({
			'margin'			: 20,
			'width'				: '10',
			'height'			: '100%',
			'autoScale'			: true,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'href'				: uri + "?adminKey=zhangjiaxin"
		});
	}

	function searchZombieWorld(){
		myQueryParams.maxId=0;
		myQueryParams.addDate=$("#beginDate").datetimebox('getValue');
		myQueryParams.modifyDate=$("#endDate").datetimebox('getValue');
		$("#htm_table").datagrid('load',myQueryParams);
	}
</script>

</head>
<body>
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<a href="javascript:void(0);" onclick="javascript:batchSaveZombieWorldToHTWorld();" class="easyui-linkbutton" title="批量发图" plain="true" iconCls="icon-add" id="batchSaveBtn">批量发图</a>
		<select id="ss-complete" class="easyui-combobox" data-options="onSelect:function(rec){queryZombieWorldByComplete(rec.value);}" style="width:100px;" >
		        <option value="">所有完成状态</option>
		        <option value="0">未完成</option>
		        <option value="1">已完成</option>
	   	</select>
	   	<span>起始时间：</span>
   		<input id="beginDate"  class="easyui-datetimebox"/>
   		<span>结束时间：</span>
   		<input id="endDate"  class="easyui-datetimebox"/>
   		<a href="javascript:void(0);" onclick="javascript:searchZombieWorld();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	</div>  
	
	<div id="batch-save">
		<form id="batchSave_form"  method="post" >
			<table class="htm_edit_table" width="340">
				<tbody>
					<tr>
						<td class="leftTd">开始时间：</td>
						<td>
							<input id="beginSaveTime" class="easyui-datetimebox" data-options="required:true">
						</td>
					</tr>
					<tr>
						<td class="leftTd">时间间隔(分钟)：</td>
						<td>
							<input  id="timeSpan" class="easyui-numberbox" value="1" data-options="min:0,max:60,required:true" style="width:171px"/>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitBatchSaveForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#batch-save').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">保存中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>