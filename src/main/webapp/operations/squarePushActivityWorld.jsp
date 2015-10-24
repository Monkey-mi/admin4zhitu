<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动织图审核</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintainfunction20151023.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js"></script>
<script type="text/javascript">
var maxId = 0,
	worldKey = 'id',
	hideIdColumn = false,
	toolbarComponent = '#tb',
	init = function() {
		myQueryParams = {
			'maxId' : maxId
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
	myRowStyler = function(index,row){
		if(row.interacted)
			return interactedWorld;
		return null;
	},
	htmTableTitle = "活动织图列表", //表格标题
	loadDataURL = "./admin_op/op_querySquarePushActivityWorld", //数据装载请求地址
	deleteURI = "./admin_op/op_deleteSquarePushs?ids=", //删除请求地址
	updatePageURL = "./admin_op/op_updateSquarePushByJSON",
	saveWinnerURL = "./admin_op/op_saveActivityWinner",
	updateWinnerURL = "./admin_op/op_updateActivityWinnerAward",
	batchCheckURI = "./admin_op/op_checkActivityWorlds?ids=", //删除请求地址
	
	columnsFields = [
		{field : 'ck',checkbox : true},
  		phoneCodeColumn,
  		authorAvatarColumn,
  		{field : 'authorId',title:'用户ID',align : 'center', width:60},
  		authorNameColumn,
  		clickCountColumn,
  		likeCountColumn,
  		commentCountColumn,
/*   		worldURLColumn = {field : 'worldURL',title : '链接',align : 'center',
  				styler: function(value,row,index){ return 'cursor:pointer;';},
  				formatter : function(value, row, rowIndex ) {
  					var url = value;
  					if(value == '' || value == undefined) {
  						var slink;
  						if(row['shortLink'] == '')
  							slink = row[worldKey];
  						else 
  							slink = row['shortLink'];
  						url = worldURLPrefix + slink;
  					}
  					return "<a title='播放织图' class='updateInfo' href='javascript:showWorld(\""
  					+ url + "\")'>"+url+"</a>";
  				}
  			}, */
  		{
  			field: "id",
  			title: "织图ID",
  			align: "center",
  			sortable: true,
  			width : 60
  		},
  		{
  			field: "titleThumbPath",
  			title: "预览",
  			align: "center",
  			formatter: function(value,row,index){
				var url = row.worldURL;
				if(row.worldURL == '' || row.worldURL == undefined) {
					var slink;
					if(row['shortLink'] == '')
						slink = row[worldKey];
					else 
						slink = row['shortLink'];
					url = worldURLPrefix + slink;
				}
  				
  				return "<a title='播放织图' class='updateInfo' href='javascript:showWorldWithURI(\"" + url + "\")'><img width='60px' height='60px' src='" + baseTools.imgPathFilter(value,'../base/images/bg_empty.png') + "' /></a>";
  			}
  		},
  		worldLabelColumn,
  		{field : 'valid',title : '状态',align : 'center',width : 45,
  			formatter: function(value,row,index){
  				switch(value) {
  				case 1:
  					img = "./common/images/ok.png";
  					title = "审核通过";
  					break;
  				case 2:
  					img = "./common/images/cancel.png",
  					title = "审核不通过";
  					break;
  				default:
  					img = "./common/images/tip.png";
					title = "等待审核";
  					break;
  				}
  				return "<img title='" +title+ "' class='htm_column_img pointer' onclick='javascript:initCheckWindow(\""+ row.id + "\",\"" 
  						 + row.authorId +"\",\"" + row.authorName +  "\",\"" + row.activityWorldId +  "\",\"" + row.activityId +"\",\"" + index + "\")' src='" + img + "'/>";
  			}
  		},
  		{field : 'superb',title : '是否精选',align : 'center',width : 45,
  			formatter: function(value,row,index){
  				var superb;
  				switch(value) {
  				case 1:
  					img = "./common/images/ok.png";
  					title = "已经成为精选";
  					superb = 0;
  					break;
  				default:
  					img = "./common/images/tip.png";
  					title = "未成为精选";
  					superb = 1;
  					break;
  				}
  				return "<img title='" +title+ "' class='htm_column_img pointer' onclick='javascript:setActivityWorldSuperb(" + row.activityWorldId + "," + superb + ")' src='" + img + "'/>";
  			}
  		},
  		
  		{field : 'awardName',title : '奖品',align : 'center',width : 120,
  			formatter: function(value,row,index){
  				return "<a title='点击修改奖品' class='updateInfo pointer' href='javascript:void(0);' onclick='javascript:initWinnerWindow(\"" 
  				 + row.id +"\",\"" + row.authorId + "\",\"" + row.activityId + "\"," + true + ",\"" + index + "\")'>" + row.awardName + "</a>";
  			}
  		},
  		
  		{field : 'isWinner',title : '评奖',align : 'center',width : 45,
  			formatter: function(value,row,index){
  				if(value == 0) {
  					img = "./common/images/edit_add.png";
  					return "<img title='添加到获奖列表' class='htm_column_img pointer' onclick='javascript:initWinnerWindow(\"" 
						 + row.id + "\",\"" + row.authorId + "\",\"" + row.activityId + "\"," + false +  ",\"" + index + "\")' src='" + img + "'/>";
  				} else {
  					img = "./common/images/undo.png";
  					return "<img title='从获奖列表移除' class='htm_column_img pointer' onclick='javascript:removeWinner(\"" 
						 + row.id +"\",\"" + row.activityId + "\",\"" + index + "\")' src='" + img + "'/>";
  				}
  			}
  		},
  		dateModified
    ],
    activityMaxSerial = 0,
    currentActivityName = "",
    currentWinnerUpdate = false,
    currentIndex = 0;
	activityQueryParams = {
		'maxSerial':activityMaxSerial,
	},
	activityAwardQueryParams = {
		'activityId':0
	},
	
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$('#htm_check').window({
			title : '审核织图',
			modal : true,
			width : 360,
			height : 285,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose: function() {
				$('#htm_check .opt_btn').show();
				$('#htm_check .loading').hide();
			}
		});
		
		$('#ss_activityId').combogrid({
			panelWidth:760,
		    panelHeight:420,
		    loadMsg:'加载中，请稍后...',
			pageList: [5,10,20],
			pageSize:5,
			editable: true,
		    multiple: false,
		    idField:'id',
		    textField:'activityName',
		    url:'./admin_op/op_querySquarePushActivity',
		    queryParams:activityQueryParams,
		    pagination:true,
		    columns:[[
				{field : 'serial',title : '活动序号',align : 'center', width : 60, hidden:true},
		  		{field : 'id',title : '活动ID',align : 'center', width : 60},
		  		{field : 'activityName',title : '活动名称',align : 'center', width : 120},
				{field : 'activityDesc',title : '活动简介',align : 'center', width : 270, 
		  			formatter : function(value, row, rowIndex ) {
						return "<span title='" + value + "' class='updateInfo'>"+value+"</span>";
					}	
				},
				{field : 'activityDate',title :'添加日期',align : 'center',width : 150},
				{ field : 'titlePath',title : '封面', align : 'center',width : 90, height:53,
					formatter:function(value,row,index) {
						return "<img width='80px' height='53px' alt='' title='点击编辑' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}	
				}
		    ]],
		    onSelect:function(record) {
		    	currentActivityName = $("#ss_activityId").combo('getText');
		    	var activityId = $("#ss_activityId").combo('getValue');
		    	activityAwardQueryParams.activityId = activityId;
		    	$("#awardId_winner").combogrid('clear');
		    	$("#awardId_winner").combogrid('grid').datagrid("load",activityAwardQueryParams);
		    	
		    },
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxSerial > activityMaxSerial) {
						activityMaxSerial = data.maxSerial;
						activityQueryParams.maxSerial = activityMaxSerial;
					}
				}
		    }
		});
		
		$('#htm_winner').window({
			title : '活动评奖',
			modal : true,
			width : 360,
			height : 215,
			top : 10,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose: function() {
			}
		});
		
		$('#awardId_winner').combogrid({
			panelWidth:560,
		    panelHeight:400,
		    loadMsg:'加载中，请稍后...',
			editable: false,
		    multiple: false,
		    idField:'id',
		    textField:'awardName',
		    url:'./admin_op/op_queryActivityAllAward',
		    queryParams:activityAwardQueryParams,
		    pagination:false,
		    columns:[[
				{field : 'id',title : 'ID',align : 'center', width : 60, sortable: false},
				{field : 'iconThumbPath',title : '缩略图', align : 'center', width : 60,
					formatter:function(value,row,index) {
						return "<img width='60px' alt='缩略图'  class='htm_column_img' src='" + value + "'/>";
					}
				},
				{field : 'awardName',title : '名称',align : 'center', width : 120},
				{field : 'price',title : '价格',align : 'center', width : 80},
				{field : 'total',title : '总数',align : 'center', width : 80},
				{field : 'remain',title : '剩余',align : 'center', width : 80}
		    ]]
		});
		
		$("#pass_check").click(function() {
			$("#checkTip_check").val("您的织图通过活动#" + currentActivityName + "#审核");
		});
		
		$("#fail_check").click(function() {
			$("#checkTip_check").val("您的织图不符合活动#" + currentActivityName + "#，很抱歉");
		});
		
		removePageLoading();
		$("#main").show();
	};


/**
 * 初始化审核窗口
 */
function initCheckWindow(worldId, authorId, authorName, activityWorldId, activityId, index) {
	currentIndex = index;
	$("#pass_check").attr('checked','checked');
	$("#fail_check").removeAttr('checked');
	$("#activityName_check").text("#" + currentActivityName + "#");
	$("#activityWorldId_check").val(activityWorldId);
	$("#activityId_check").val(activityId);
	$("#authorName_check").val(authorName);
	$("#authorId_check").val(authorId);
	$("#worldId_check").val(worldId);
	$("#checkTip_check").val("您的织图通过活动审核#" + currentActivityName + "#");
	//$("#checked_check").attr('checked',true);
	$("#check_form").show();
	$('#htm_check').window('open');
}

/**
 * 提交审核表单
 */
function submitCheckForm() {
	var $form = $('#activity_form');
	$('#htm_check .opt_btn').hide();
	$('#htm_check .loading').show();
	$('#check_form').form('submit', {
		url: $form.attr('action'),
		success: function(data){
			var result = $.parseJSON(data);
			$('#htm_check .opt_btn').show();
			$('#htm_check .loading').hide();
			if(result['result'] == 0) { 
				var valid = result['valid'];
				updateValue(currentIndex, 'valid', valid);
				$('#htm_check').window('close');  //关闭添加窗口
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		}
	});
}

function initWinnerWindow(worldId, userId, activityId, isUpdate, index) {
	currentIndex = index;
	currentWinnerUpdate = isUpdate;
	$("#activityName_winner").text(currentActivityName);
	$("#activityId_winner").val(activityId);
	$("#worldId_winner").val(worldId);
	$("#userId_winner").val(userId);
	$('#htm_winner').window('open');
	console.log(isUpdate);
	if(isUpdate) {
		$.post("./admin_op/op_queryActivityWinnerAward",{
			"activityId":activityId,
			"worldId":worldId,
		}, function(result){
			if(result['result'] == 0) {
				var award = result['award'];
				$("#awardId_winner").combogrid('setValue',award);
				$("#winner_loading").hide();
				$("#winner_form").show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$("#awardId_winner").combogrid('clear');
		$("#winner_loading").hide();
		$("#winner_form").show();
	}
}

function submitWinnerForm() {
	var submitURL = saveWinnerURL;
	if(currentWinnerUpdate) {
		submitURL = updateWinnerURL;
	}
	
	$('#htm_winner .opt_btn').hide();
	$('#htm_winner .loading').show();
	$('#winner_form').form('submit', {
		url: submitURL,
		success: function(data){
			var result = $.parseJSON(data);
			$('#htm_winner .opt_btn').show();
			$('#htm_winner .loading').hide();
			if(result['result'] == 0) { 
				if(!currentWinnerUpdate) {
					updateValue(currentIndex, 'isWinner', 1);
				}
				updateValue(currentIndex, 'awardName', $("#awardId_winner").combogrid('getText'));
				$('#htm_winner').window('close');  //关闭添加窗口
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		}
	});
}


/**
 * 添加获奖织图
 */
function addWinner(worldId, activityId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_op/op_saveActivityWinner",{
		'worldId':worldId,
		'activityId':activityId,
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'isWinner',1);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 移除获奖织图
 */
function removeWinner(worldId, activityId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_op/op_deleteActivityWinner",{
		'worldId':worldId,
		'activityId':activityId,
		},function(result){
			if(result['result'] == 0) {
				updateValues(index, ['isWinner','awardName'], ['0','']);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

function searchWorld() {
	
	var actId = $("#ss_activityId").combobox('getValue');
	if(actId == "" || actId == undefined) {
		$.messager.alert('错误提示', "请选择活动");  //提示失败信息
		return;
	}
	
	var isWinner = $("#ss_isWinner").combobox('getValue');
	if(isWinner == 1) {
		$("#ss_valid").combobox('setValue', '');
	}
	
	maxId = 0;
	myQueryParams.worldId = "";
	myQueryParams.maxId = maxId;
	myQueryParams.activityId = actId;
	myQueryParams.isWinner = isWinner;
	myQueryParams.valid = $("#ss_valid").combobox('getValue');
	myQueryParams.superb = $("#ss_superb").combobox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
	
}

function searchByWorldId() {
	var actId = $("#ss_activityId").combobox('getValue');
	if(actId == "" || actId == undefined) {
		$.messager.alert('错误提示', "请选择活动");  //提示失败信息
		return;
	}
	
	var worldId = $('#ss_worldId').searchbox('getValue');
	
	maxId = 0;
	myQueryParams.isWinner = "";
	myQueryParams.maxId = maxId;
	myQueryParams.activityId = actId;
	myQueryParams.worldId = worldId;
	$("#htm_table").datagrid("load", myQueryParams);
}

function searchByUserId() {
	var actId = $("#ss_activityId").combobox('getValue');
	if(actId == "" || actId == undefined) {
		$.messager.alert('错误提示', "请选择活动");  //提示失败信息
		return;
	}
	
	var userIdOrUserName = $('#ss_userIdOrUserName').searchbox('getValue');
	
	maxId = 0;
	myQueryParams.isWinner = "";
	myQueryParams.maxId = maxId;
	myQueryParams.activityId = actId;
	myQueryParams.userIdOrUserName = userIdOrUserName;
	$("#htm_table").datagrid("load", myQueryParams);
}

function checkWorlds(idKey, valid) {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(isSelected(rows)){
		$.messager.confirm('审核织图', '您确定要审核已选中的记录?', function(r){ 	
			if(r){			
				var ids = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i][idKey]);	
					rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
				}	
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				$('#htm_table').datagrid('loading');
				$.post(batchCheckURI + ids,{
					'valid':valid
				},function(result){
					$('#htm_table').datagrid('loaded');
					if(result['result'] == 0) {
						$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
						$("#htm_table").datagrid("reload");
					} else {
						$.messager.alert('提示',result['msg']);
					}
					
				});				
			}	
		});		
	}	
}

function setActivityWorldSuperb(id, superb) {
	$.messager.confirm('对活动织图加精', '您确定要使选中的织图成为精选?', function(r){ 	
		if(r){
			$.post("./admin_op/op_updateActivityWorldSuperb",{
				'id': id,
				'superb':superb
			},function(result){
				if(result['result'] == 0) {
					$("#htm_table").datagrid("reload");
				} else {
					$.messager.alert('提示',result['msg']);
				}
			});				
		}	
	});
}
</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<a href="javascript:void(0);" onclick="javascript:checkWorlds('activityWorldId','1');" class="easyui-linkbutton" 
		title="批量拒绝" plain="true" iconCls="icon-ok" id="rejectBtn">批量通过</a>
		
		<a href="javascript:void(0);" onclick="javascript:checkWorlds('activityWorldId','2');" class="easyui-linkbutton" 
		title="批量拒绝" plain="true" iconCls="icon-cancel" id="rejectBtn">批量拒绝</a>
		
		<span class="search_label">请选择活动：</span>
		<input id="ss_activityId"  style="width:100px;"/>			
		<span class="search_label">有效性：</span>
		<select id="ss_valid" class="easyui-combobox" style="width:80px;">
   			<option value="">所有状态</option>
   			<option value="0">未生效</option>
   			<option value="1">生效</option>
   			<option value="2">拒绝</option>
   		</select>
   		<span class="search_label">活动精选：</span>
   		<select id="ss_superb" class="easyui-combobox" style="width:80px;">
   		<option value="">所有状态</option>
   		<option value="0">未加精</option>
   		<option value="1">加精</option>
   		</select>
   		<span class="search_label">获奖状态：</span>
		<select id="ss_isWinner" class="easyui-combobox" style="width:80px;">
   			<option value="">所有状态</option>
   			<option value="1">已经获奖</option>
   		</select>
   		<a href="javascript:void(0);" onclick="javascript:searchWorld();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
   		
   		<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
			<input id="ss_userIdOrUserName" searcher="searchByUserId" class="easyui-searchbox" prompt="输入用户ID或用户名搜索" style="width:150px;" />
		</div>
   		<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
			<input id="ss_worldId" searcher="searchByWorldId" class="easyui-searchbox" prompt="输入织图ID搜索" style="width:150px;" />
		</div>
	</div> 
	
	<!-- 审核操作 -->
	<div id="htm_check">
		<form id="check_form" action="./admin_op/op_checkSquarePushActivityWorld" method="post" class="none">
			<table class="htm_edit_table" width="340">
				<tbody>
					<tr class="none">
						<td class="leftTd">活动织图ID：</td>
						<td>
							<input id="activityWorldId_check" name="activityWorldId" type="text" />
						</td>
					</tr>
					<tr class="none">
						<td class="leftTd">活动ID：</td>
						<td>
							<input id="activityId_check" name="activityId" type="text" />
						</td>
					</tr>
					<tr class="none">
						<td class="leftTd">作者ID：</td>
						<td>
							<input id="authorId_check" name="userId" type="text" />
						</td>
					</tr>
					<tr>
						<td class="leftTd">活动名：</td>
						<td>
							<span id="activityName_check"></span>
						</td>
					</tr>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td>
							<input id="worldId_check" name="worldId" type="text" readyonly="readyonly" />
						</td>
					</tr>
					<tr>
						<td class="leftTd">作者：</td>
						<td>
							<input id="authorName_check" name="userName" type="text" readyonly="readyonly" />
						</td>
					</tr>
					<tr>
						<td class="leftTd">审核：</td>
						<td>
							<input id="pass_check" type="radio" name="valid" value="1" checked="checked" style="width:13px;"/>通过
							<!-- 
							<input id="fail_check" type="radio" name="valid" value="2" style="width:13px;" />不通过
							 -->
						</td>
					</tr>
					<tr>
						<td class="leftTd">推送提示：</td>
						<td>
							<textarea id="checkTip_check" name="notifyTip" ></textarea>
						</td>
					</tr>
					<!--
					<tr>
						<td class="leftTd"><input id="checked_check" type="checkbox" name="checked" checked=true style="width:16px;" /></td>
						<td>
							通知用户
						</td>
					</tr>
					-->
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitCheckForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_check').window('close');">取消</a>
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
	
	<!-- 奖品更新操作 -->
	<div id="htm_winner">
		<span id="winner_loading" style="margin:140px 0 0 440px; position:absolute;">加载中...</span>
		<form id="winner_form" action="./admin_op/op_checkSquarePushActivityWorld" method="post" class="none">
			<table class="htm_edit_table" width="340">
				<tbody>
					<tr>
						<td class="leftTd">活动名称：</td>
						<td>
							<span id="activityName_winner"></span>
						</td>
					</tr>
					<tr class="none">
						<td class="leftTd">活动ID：</td>
						<td>
							<input id="activityId_winner" name="activityId" type="text" />
						</td>
					</tr>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td>
							<input id="worldId_winner" name="worldId" type="text" />
						</td>
					</tr>
					<tr>
						<td class="leftTd">用户ID：</td>
						<td>
							<input id="userId_winner" name="userId" type="text" />
						</td>
					</tr>
					<tr>
						<td class="leftTd">奖品：</td>
						<td>
							<input id="awardId_winner" name="awardId" type="text" />
						</td>
					</tr>
					
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitWinnerForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_winner').window('close');">取消</a>
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
	
	</div>

</body>
</html>