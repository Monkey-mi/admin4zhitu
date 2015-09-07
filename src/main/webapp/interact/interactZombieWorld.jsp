<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>马甲发图计划管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
	var maxId = 0;
	var worldURLPrefix = 'http://www.imzhitu.com/DT';
	hideIdColumn = false;
	htmTableTitle = "马甲发图计划管理"; //表格标题
	htmTablePageList = [10,30,50,100,150,300,500];
	loadDataURL = "./admin_interact/interactZombieWorld_queryZombieWorldForTable"; //数据装载请求地址
	var batchSaveURI = "./admin_interact/zombieWorldSchedula_batchInsertZombieWorldSchedula?zombieWorldIdsStr=";
	var batchUpdateLabelsURL = "./admin_interact/interactZombieWorld_batchUpdateZombieWorldlabel?ids=";
	init = function() {
		myQueryParams = {
			'maxId' : maxId,
		},
		loadPageData(initPage);
		
	};
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	};
	toolbarComponent = '#tb';
	//分页组件,可以重载
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : 'id',title : 'ID',align : 'center'},
		{field : 'authorId',title : '马甲ID',align : 'center'},
		{field : 'thumbTitlePath',title : '缩略图',align : 'center',
			formatter: function(value,row,index){
				var imgSrc = baseTools.imgPathFilter(value,'../base/images/bg_empty.png');
				return "<img width='100px' height='100px' class='htm_column_img' src='" + imgSrc + "' />";
			}
		},
		{field : 'worldDesc', title:'织图描述', align : 'center',width:280,editor:'text'},
		{field : 'addDate', title:'添加日期', align : 'center', 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		},
		{field : 'modifyDate', title:'最后修改时间日期', align : 'center', 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		},
 		{field : 'complete',title : '完成',align : 'center',
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		}, 
  		{field : 'shortLink', title:'短链',align : 'center',
			styler: function(value,row,index){ 
				return 'cursor:pointer;';
			},
			formatter : function(value, row, rowIndex ) {
				if(value == "" || value == undefined) return "";
				return "<a title='播放织图' class='updateInfo' href='javascript:showWorld(\""
						+ worldURLPrefix + value + "\")'>" +value+"</a>";
			}
		},
		{field : 'htworldId', title:'织图ID', align : 'center'},
		{field : 'channelId',title:'频道ID',align : 'center',editor:'text'},
		{field : 'channelName',title:'频道名称',align:'center'},
 		{field : 'worldLabel',title:'织图标签',align:'center'}, 
		{
			field : 'commnet',
			title : '添加评论',
			align : 'center',
			width : 100,
 			formatter : function(value, row, index) {
				title = "批量添加评论";
				var completeValue = row.complete;
  			if(completeValue == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
				img = "./common/images/edit_add.png";
				return "<img title='" + title + "' class='htm_column_img pointer' onclick='addComments("+ row.id +")' src='" + img + "'/>";
			} 
		} 
		
	];
	onAfterInit = function() {
		$('#htm_table').datagrid({
			fitColumns: false,
			onAfterEdit: function(index,row,changes){
				/*
				 * 若changes.worldDesc为undefined，则证明没有改动，什么都不进行操作
				 */
				if (changes.worldDesc) {
					var requestData = {
							'id':row.id,
							'worldDesc':row.worldDesc
							};
					$.post('./admin_interact/interactZombieWorld_updateZombieWorld', requestData, function(data){
						if (data.result == -1) {
							$.messager.alert("提示","第" + (index+1) + "行，" + data.msg);
							$('#htm_table').datagrid('rejectChanges');
						}
					});
				} else if (changes.channelId) {
					var requestData = {
						'id':row.id,
						'channelId':row.channelId
						};
					$.post('./admin_interact/interactZombieWorld_updateZombieWorld', requestData, function(data){
						if (data.result == -1) {
							$.messager.alert("提示","第" + (index+1) + "行，" + data.msg);
							$('#htm_table').datagrid('rejectChanges');
						}
					});
				} else {
					$('#htm_table').datagrid('rejectChanges');
				}
				row.editing = false;
				$('#htm_table').datagrid('refreshRow', index);
			}
		});
		
		$("#batch-save").window({
			title : '批量发图',
			modal : true,
			width : 420,
			height : 190,
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
				$("#random_flag").removeAttr('checked');
			}
		});
		
		$("#batch-update-label").window({
			title : '批量更新马甲织图标签',
			modal : true,
			width : 420,
			height : 200,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function(){
				$("#channelLabel").html('');
			}
		});
		
		
		$('#channelLabelSearch').combobox({
			valueField:'id',
			textField:'label_name',
			onChange:function(rec){
				var url="./admin_op/v2channel_queryOpChannelLabel?channelLabelNames="+$('#channelLabelSearch').combobox('getValue');
				$('#channelLabelSearch').combobox('reload',url);
			},
			onSelect:function(rec){
				var channelLabelId = rec.id;
				var channelLabelName = rec.label_name;
				if(rec.id == -1){
					channelLabelName = channelLabelName.substring(channelLabelName.indexOf(":")+1);
					$.messager.confirm('更新记录',"是否创建标签："+channelLabelName,function(r){
						if(r){
							$.post("./admin_ztworld/label_saveLabel",{
								'labelName':channelLabelName
							},function(result){
								if(result['result'] == 0){
									channelLabelId = result['labelId'];
									var labelSpan = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;overflow:hidden;' labelId='"
											+channelLabelId+"' labelName='"+channelLabelName+"'>"+channelLabelName+"</a>").click(function(){
										$(this).remove();
									});
									$("#channelLabel").append(labelSpan);
									$('#channelLabelSearch').combobox('clear');
								}else{
									$.messager.alert('提示',result['msg']);
								}
							},"json");
						}
					});
				}else{
					var labelSpan = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;overflow:hidden;' labelId='"
							+channelLabelId+"' labelName='"+channelLabelName+"'>"+channelLabelName+"</a>").click(function(){
						$(this).remove();
					});
					$("#channelLabel").append(labelSpan);
					$('#channelLabelSearch').combobox('clear');
				}
			}
		});
		
		removePageLoading();
		$("#main").show();
		
		$("#addComment").window({
			title : '批量添加评论',
			modal : true,
			width : 420,
			height : 190,
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
				$("#random_flag").removeAttr('checked');
			} 
		});
		
	};

	
	//根据complete查询马甲织图
	function queryZombieWorldByComplete(complete){
		myQueryParams.complete = complete;
		$("#htm_table").datagrid('load',myQueryParams);
	}
	
	//根据schedulaFlag查询马甲织图
	function queryZombieWorldBySchedulaFlag(schedulaFlag){
		myQueryParams.schedulaFlag = schedulaFlag;
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
		var randonNum;
		var tmp;	
		if(isSelected(rows)){
			var ids = [];
			for(var i=0;i<rows.length;i+=1){		
				ids.push(rows[i]['id']);	
				rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
			}
			if($("#random_flag").attr('checked') == 'checked'){
				for(var i=0;i<ids.length;i++){
					randomNum = parseInt(Math.random()*ids.length);
					tmp = ids[i];
					ids[i] = ids[randomNum];
					ids[randomNum] = tmp;
				}
			}	
			$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
			$('#htm_table').datagrid('loading');
			var begin = $("#beginSaveTime").datetimebox('getValue');
			var timeSpan = $("#timeSpan").numberbox('getValue');
			$.post(batchSaveURI+ids,{
				'schedula':begin,
				'minuteTimeSpan':timeSpan
			},function(result){
				$('#htm_table').datagrid('loaded');
				if(result['result'] == 0) {
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
			'width'				: '100%',
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
		myQueryParams.channelId = $("#channelId").val();
		$("#htm_table").datagrid('load',myQueryParams);
	}
	
	function initBatchUpdateLabel(){
		$('#batch-update-label').window('open');
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows) == false){
			$('#batch-update-label').window('close');
		}
	}
	
	function submitBatchUpdateLabelForm(){
		var labelnames="";
		$("#channelLabel a").each(function(){
			labelnames += $(this).attr("labelName");
			labelnames +=",";
		});
		
		if(labelnames.length > 0){
			labelnames = labelnames.substring(0, labelnames.length-1);
		}else{
			$.messager.alert('操作失败','请先选择标签，再执行操作!','error');
			return;
		}
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			var ids = [];
			for(var i=0;i<rows.length;i+=1){		
				ids.push(rows[i]['id']);	
			}	
			$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
			$('#htm_table').datagrid('loading');
			$.post(batchUpdateLabelsURL+ids,{
				'worldLabel':labelnames
			},function(result){
				$('#htm_table').datagrid('loaded');
				if(result['result'] == 0) {
					$('#htm_table').datagrid('reload');
				} else {
					$.messager.alert('提示',result['msg']);
				}
				$("#batch-save").window('close');
			},"json");
		}
		
	}
	
	function del(){
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			var ids = [];
			for(var i=0;i<rows.length;i+=1){		
				ids.push(rows[i]['id']);	
			}	
			$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
			$('#htm_table').datagrid('loading');
			$.post("./admin_interact/interactZombieWorld_batchDeleteZombieWorld?ids="+ids,function(result){
				$('#htm_table').datagrid('loaded');
				if(result['result'] == 0) {
					$.messager.alert('提示',result['msg']);
					$("#htm_table").datagrid("reload");
				} else {
					$.messager.alert('提示',result['msg']);
				}
				
			});				
		}	
	}
	
	//打开添加文件的窗口
	function addComments(zombieWorldId){
		$('#addComment').window('open');
		$('#zombieWorldId').val(zombieWorldId);
	}
	
	//mishengliang
	//点击确定添加后的操作
	function addComment(){
		var addCommentsFile = $('#addCommentsFile');
		$('#addComment').window('close');
		addCommentsFile.ajaxSubmit({
			success:function(result){
			if (result['result'] == 0) {
				$.messager.alert('提示',"成功！");
			} else {
				$.messager.alert('提示',result['msg']);
			}
			},
			url : addCommentsFile.attr('action'),
			type : 'post',
			dataType : 'json'
		});
	}
	
</script>

</head>
<body>
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="批量删除" plain="true" iconCls="icon-cut" id="delBtn">批量删除</a>
		<a href="javascript:void(0);" onclick="javascript:batchSaveZombieWorldToHTWorld();" class="easyui-linkbutton" title="批量发图" plain="true" iconCls="icon-add" id="batchSaveBtn">批量发图</a>
		<a href="javascript:void(0);" onclick="javascript:initBatchUpdateLabel();" class="easyui-linkbutton" title="批量修改标签" plain="true" iconCls="icon-add" id="batchSaveBtn">批量修改标签</a>
		<select id="ss-complete" class="easyui-combobox" data-options="onSelect:function(rec){queryZombieWorldByComplete(rec.value);}" style="width:100px;" >
		        <option value="">所有完成状态</option>
		        <option value="0">未完成</option>
		        <option value="1">已完成</option>
	   	</select>
	   	<select id="ss-schedulaFlag" class="easyui-combobox" data-options="onSelect:function(rec){queryZombieWorldBySchedulaFlag(rec.value);}" style="width:100px;" >
		        <option value="">未计划</option>
		        <option value="1">已计划</option>
	   	</select>
	   	<span>起始时间：</span>
   		<input id="beginDate"  class="easyui-datetimebox"/>id
   		<span>结束时间：</span>
   		<input id="endDate"  class="easyui-datetimebox"/>
   		<span>频道ID</span>
   		<input id="channelId">
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
						<td><input type="checkbox"  id="random_flag" style="width: 13px; height: 13px; vertical-align: middle;float:right;"/></td>
						<td>随机排序</td>
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
	
	<div id="batch-update-label">
		<form id="batchUpdateLabel_form"  method="post" >
			<table class="htm_edit_table" width="340">
				<tbody>
					<tr>
						<td class="leftTd">标签：</td>
						<td>
							<div id="channelLabel" style="width: 206px;height: 60px;background-color: #b8b8b8;"></div>
							<input id="channelLabelSearch" style="width: 206px;">
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitBatchUpdateLabelForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#batch-update-label').window('close');">取消</a>
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
	
  		<div id='addComment'>
			<form id='addCommentsFile' action="./admin_interact/interactZombieWorld_addCommentsFile" method='post'>
			<table class="htm_edit_table" width="340">
			<tbody>
			<tr>
				<td style='height:100'>
				马甲织图ID：<input type="text" id="zombieWorldId" name='zombieWorldId'>
				</td>
			</tr>
			<tr>
				<td>织图等级&nbsp;&nbsp;&nbsp;: <input style="width:120px" class="easyui-combobox" id="levelId" name="id"  onchange="validateSubmitOnce=true;" 
					data-options="valueField:'id',textField:'level_description',url:'./admin_interact/worldlevel_QueryoWorldLevel'"/></td>
			</tr>
			<tr>
				<td style='height:100' >
				<input id='commentsFile' type="file" name='commentsFile'>
				</td>
			</tr>
			<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 20px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addComment();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#addComment').window('close');">取消</a>
						</td>
			</tr>
			</tbody>
			</table>
			</form>
		</div> 
		
		
</body>
</html>