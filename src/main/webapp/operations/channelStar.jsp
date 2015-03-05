<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道红人管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer} "></script>
<script type="text/javascript">
var maxId = 0,
	channelId = 0,
	notifyIndex = 0,
	batchEnableTip = "您确定要使已选中的用户生效吗？",
	batchDisableTip = "您确定要使已选中的用户失效吗？",
	init = function() {
		loadPageData(initPage);
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['star.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['star.maxId'] = maxId;
			}
		}
	},
	myRowStyler = function(index, row) {
	},
	hideIdColumn = true,
	htmTableTitle = "频道红人列表", //表格标题
	htmTableWidth = 1300,
	toolbarComponent = '#tb',
	myIdField = "channelStarId",
	recordIdKey = "channelStarId",
	uidKey = "userId",
	loadDataURL = "./admin_op/channel_queryStar", //数据装载请求地址
	deleteURI = "./admin_op/channel_deleteStar?ids=", //删除请求地址
	updateValidURL = "./admin_op/channel_updateStarValid?ids=",
	addRecommendMsgURL = "./admin_op/channel_addStarRecommendMsgs?ids=",
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : '序号',align : 'center',width : 60},
		{field : 'userId',title : '用户ID',align : 'center',width : 60},
		phoneCodeColumn,
		userAvatarColumn,
		userNameColumn,
		sexColumn,
		userLabelColumn,
		concernCountColumn,
		followCountColumn,
		{field : 'worldCount',title:'织图',align : 'center', width : 60,
			formatter: function(value,row,index){
				var uri = "page_user_userWorldInfo?userId="+row.userId;
				return "<a title='显示织图' class='updateInfo' href='javascript:showUserWorld(\""+uri
					+"\")'>"+value+"</a>"; 
			}
		},
		{field : 'channelName',title : '频道名',align : 'center', width : 60},
		{field : 'channelIcon',title : '频道icon',align : 'center', width : 60,
			formatter: function(value,row,index) {
				return "<img title='" + row.channelName +  "' width=30 height=30 class='htm_column_img' src='" + value + "'/>";
  			}
		},
		{field : 'notified',title : '通知状态',align : 'center', width : 60,
			formatter: function(value,row,index) {
  				if(value >= 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已经通知' class='htm_column_img' src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
		},
		{field : 'valid',title : '有效性',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
  		{field : 'weight',title : '置顶状态',align : 'center', width : 60,
			formatter: function(value,row,index) {
				img = "./common/images/edit_add.png";
  				title = "点击置顶";
  				if(value > 0) {
  					img = "./common/images/undo.png";
  					title = "已置顶，数字越大越靠前，点击撤销";
  					return "<img title='" + title +  "' class='htm_column_img pointer' onclick='updateWeight(\"" + index + "\",\"" + row[recordIdKey] + "\",\"" + false + "\")' src='" + img + "'/>+"+value;
  				}
  				return "<img title='" + title +  "' class='htm_column_img pointer' onclick='updateWeight(\"" + index + "\",\"" + row[recordIdKey] + "\",\"" + true + "\")' src='" + img + "'/>";
  			}
		},
		{field : 'superbScore',title : '平均几天上精选*3', align : 'center', width : 90},
		{field : 'channelScore',title : '平均几天上频道*3', align : 'center', width : 90},
		{field : 'registerScore',title : '注册多少个周/2', align : 'center', width : 90},
		{field : 'lastWorldScore',title : '最后发图时间*2', align : 'center', width : 90}
		],
	addWidth = 520, //添加信息宽度
	addHeight = 160, //添加信息高度
	addTitle = "添加频道红人", //添加信息标题
	
	pageButtons = [],
    onBeforeInit = function() {
		showPageLoading();
	},
	
	channelMaxId = 0,
	channelQueryParams = {},
	
	onAfterInit = function() {
		$('#htm_indexed').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 165,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
		});
		
		$('#htm_notify').window({
			title : '通知用户',
			modal : true,
			width : 360,
			height : 225,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false
		});
		
		$('#ss-channel').combogrid({
			panelWidth : 320,
		    panelHeight : 490,
		    loadMsg : '加载中，请稍后...',
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'channelName',
		    url : './admin_op/channel_queryChannel',
		    pagination : true,
		    remoteSort : false,
		    columns:[[
				{field : 'id',title : 'ID', align : 'center',width : 60},
				{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
					formatter:function(value,row,index) {
						return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				},
				{field : 'channelName',title : '频道名称',align : 'center',width : 120},
			]],
			onSelect:function(index, row) {
				$("#htm_opt_btn").show();
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				maxId = 0;
				channelId = row.id;
				myQueryParams['star.maxId'] = maxId;
				myQueryParams['star.channelId'] = channelId;
				myQueryParams['star.userId'] = "";
				myQueryParams['star.notified'] = "";
				myQueryParams['star.valid'] = "";
				myQueryParams['star.weight'] = "";
				loadPageData(initPage);
			}
		});
		
		var p = $('#ss-channel').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					channelMaxId = 0;
					channelQueryParams['channel.maxId'] = userMaxId;
				}
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	
	
	
//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	clearFormData(addForm);
	var channelName = $('#ss-channel').combogrid('getText');
	var channelId = $('#ss-channel').combogrid('getValue');
	$("#channelName_add").text(channelName);
	$("#channelId_add").val(channelId);
	$("#valid_add").val(0);
	$("#notified_add").val(0);
	$("#add_form .opt_btn").show();
	$("#add_form .loading").hide();
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	formSubmitOnce = true;
	$("#userId_add").focus();  //光标定位
	$.formValidator.initConfig({
		formid : addForm.attr("id"),
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$("#add_form .opt_btn").hide();
				$("#add_form .loading").show();
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						formSubmitOnce = true;
						$("#add_form .opt_btn").show();
						$("#add_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							maxId = 0;
							myQueryParams['star.maxId'] = maxId;
							myQueryParams['star.channelId'] = channelId;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});

	$("#userId_add")
	.formValidator({empty:false,onshow:"请输入用户id",onfocus:"例如:12",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
}


/**
 * 更新有效性
 */
function updateValid(valid) {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(rows.length > 0){
		var tip = batchEnableTip;
		if(valid == 0) 
			tip = batchDisableTip;
		$.messager.confirm('更新记录', tip, function(r){ 	
			if(r){				
				var ids = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i][recordIdKey]);
				}	
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				$('#htm_table').datagrid('loading');
				$.post(updateValidURL + ids,{
					"valid" : valid
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
	}else{
		$.messager.alert('更新失败','请先选择记录，再执行更新操作!','error');
	}
}

/**
 * 重排推荐
 */
function reIndexed() {
	$('#htm_indexed .opt_btn').show();
	$('#htm_indexed .loading').hide();
	var channelId = $('#ss-channel').combogrid('getValue');
	$("#channelId_indexed").val(channelId);
	clearReIndexedForm();
	// 打开添加窗口
	$("#htm_indexed").window('open');
}

/**
 * 清空索引排序
 */
function clearReIndexedForm() {
	$("#indexed_form").find('input[name="reIndexId"]').val('');	
}

function submitReIndexForm() {
	var $form = $('#indexed_form');
	if($form.form('validate')) {
		$('#htm_indexed .opt_btn').hide();
		$('#htm_indexed .loading').show();
		$('#indexed_form').form('submit', {
			url: $form.attr('action'),
			success: function(data){
				var result = $.parseJSON(data);
				$('#htm_indexed .opt_btn').show();
				$('#htm_indexed .loading').hide();
				if(result['result'] == 0) { 
					$('#htm_indexed').window('close');  //关闭添加窗口
					maxId = 0;
					myQueryParams['star.maxId'] = maxId;
					loadPageData(1);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			}
		});
	}
}

/**
 * 刷新推荐用户列表
 */
function refresh() {
	$.messager.confirm('提示', '确定要刷新红人列表缓存？刷新该频道下最新15个红人将生效！', function(r){
		if (r){
			$('#htm_table').datagrid('loading');
			$.post('./admin_op/channel_updateStarCache',{
				"star.channelId": channelId
			},function(result){
					$('#htm_table').datagrid('loaded');
					if(result['result'] == 0) {
						$.messager.alert('提示',result['msg']);  //提示添加信息成功
					} else {
						$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
					}
				},"json");				
		};
	});
}


/**
 * 搜索推荐用户
 */
function searchStar() {
	maxId = 0;
	myQueryParams['star.maxId'] = maxId;
	myQueryParams['star.userId'] = "";
	myQueryParams['star.channelId'] = $('#ss-channel').combogrid('getValue');
	myQueryParams['star.notified'] = $('#ss-notified').combobox('getValue');
	myQueryParams['star.valid'] = $('#ss-valid').combobox('getValue');
	myQueryParams['star.weight'] = $('#ss-weight').combobox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}

/**
 * 根据用户名搜索推荐用户
 */
function searchByUID() {
	maxId = 0;
	myQueryParams['star.maxId'] = maxId;
	myQueryParams['star.userId'] = $("#ss-userId").searchbox('getValue');
	myQueryParams['star.channelId'] = $('#ss-channel').combogrid('getValue');
	myQueryParams['star.notified'] = "";
	myQueryParams['star.weight'] = "";
	myQueryParams['star.valid'] = "";
	$("#htm_table").datagrid("load",myQueryParams);
}


//显示用户织图
function showUserWorld(uri){
	$.fancybox({
		'margin'			: 20,
		'width'				: '10',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

function batchNotify() {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(rows.length > 0){
		$.messager.confirm('添加通知', '你确定要批量添加通知', function(r){ 	
			if(r){				
				var ids = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i][recordIdKey]);
				}	
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				$('#htm_table').datagrid('loading');
				$.post(addRecommendMsgURL + ids,{
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
	}else{
		$.messager.alert('通知失败','请先选择记录，再执行更新操作!','error');
	}
}

function updateWeight(index, id, isAdd) {
	$("#htm_table").datagrid("loading");
	$.post("./admin_op/channel_updateStarWeight",{
				'id':id,
				'isAdd':isAdd
			},function(result){
				$("#htm_table").datagrid("loaded");
				if(result['result'] == 0){
					var weight = 0;
					if(isAdd) {
						weight = result['weight'];
					}
					
					loadPageData(1);
					
					//if(!isAdd && result['recommendWeight'] == -1)
					//	$("#htm_table").datagrid("loaded");
					//else 
					//	updateRecommendWeight(result['userId'], isAdd);
				}else{
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			},"json");
}

// 修改推荐用户置顶状态
function updateRecommendWeight(uid, isAdd) {
	
	$.messager.confirm('操作提示', '是否同步达人置顶', function(r){ 	
			if(r){				
				$.post("./admin_op/user_updateRecommendWeightByUID",{
					'userId':uid,
					'isAdd':isAdd
				},function(result){
					$("#htm_table").datagrid("loaded");
					if(result['result'] == 0){
						$.messager.alert('提示', '同步成功');
					}else{
						$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
					}
				},"json");				
			}	
		});	
}


</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<span class="search_label">请选频道：</span>
			<input id="ss-channel" style="width:100px;" />
			<span id="htm_opt_btn" class="none">
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加频道红人" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除频道红人" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效" plain="true" iconCls="icon-ok">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效" plain="true" iconCls="icon-tip">批量失效</a>
			<a href="javascript:void(0);" onclick="javascript:batchNotify();" class="easyui-linkbutton" title="批量通知" plain="true" iconCls="icon-ok">批量通知</a>
			<a href="javascript:void(0);" onclick="javascript:reIndexed();" class="easyui-linkbutton" title="推荐用户排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">重新排序</a>
			<a href="javascript:void(0);" onclick="javascript:refresh();" class="easyui-linkbutton" title="刷新缓存" plain="true" iconCls="icon-reload">刷新缓存</a>
			<select id="ss-notified" class="easyui-combobox" style="width:100px;">
		        <option value="">所有通知状态</option>
		        <option value="0">未通知</option>
		        <option value="1">已通知</option>
	   		</select>
	   		<select id="ss-valid" class="easyui-combobox" style="width:100px;">
		        <option value="">所有生效状态</option>
		        <option value="0">未生效</option>
		        <option value="1">生效</option>
	   		</select>
	   		<select id="ss-weight" class="easyui-combobox" style="width:100px;">
	   			<option value="">置顶和未置顶</option>
	   			<option value="1">已置顶</option>
	   			<option value="0">未置顶</option>
	   		</select>
	   		<a href="javascript:void(0);" onclick="javascript:searchStar();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	   		<span style="display: inline-block; vertical-align:middle; float: right;">
		        <input id="ss-userId" class="easyui-searchbox" searcher="searchByUID" prompt="输入用户ID搜索" style="width:100px;" />
			</span>
			</span>
   		</div>
	</div> 

	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_op/channel_saveStar" method="post">
			<table class="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">频道：</td>
						<td><span id="channelName_add"></span></td>
						<td class="rightTd"></td>
					</tr>
					<tr>
						<td class="leftTd">用户id：</td>
						<td><input type="text" name="star.userId" id="userId_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userId_addTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none">
						<td colspan="3"><input type="text" name="star.channelId" id="channelId_add" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr class="none">
						<td colspan="3"><input type="text" name="star.valid" id="valid_add" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr class="none">
						<td colspan="3"><input type="text" name="star.notified" id="notified_add" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">添加</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">提交中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 重排索引 -->
	<div id="htm_indexed">
		<form id="indexed_form" action="./admin_op/channel_updateStarSerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">用户ID：</td>
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
							<input name="reIndexId" class="reindex_column"/>
						</td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="channelId" id="channelId_indexed" /></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReIndexForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-redo" onclick="clearReIndexedForm();">清空</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_indexed').window('close');">取消</a>
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
	
	</div>
</body>
</html>