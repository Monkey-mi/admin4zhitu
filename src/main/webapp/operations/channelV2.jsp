<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道维护</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

var maxId = 0,
	init = function() {
		loadPageData(initPage);
	},
	hideIdColumn = false,
	htmTableTitle = "频道列表", //表格标题
	htmTableWidth = 1410,
	batchEnableTip = "您确定要使已选中的频道生效吗？",
	batchDisableTip = "您确定要使已选中的频道失效吗？",
	toolbarComponent = '#tb',
	htmTableTitle = "频道列表", //表格标题
	loadDataURL = "./admin_op/v2channel_queryOpChannel", //数据装载请求地址
	deleteURI = "./admin_op/channel_deleteChannels?ids=", //删除请求地址
	saveChannelURL = "./admin_op/v2channel_insertOpChannel", // 保存频道地址
	updateChannelByIdURL = "./admin_op/v2channel_updateOpChannel", // 更新频道地址
	queryChannelByIdOrNameURL = "./admin_op/v2channel_queryOpChannelByIdOrName", // 根据id查询频道
	updateValidURL = "./admin_op/v2channel_batchUpdateChannelValid?channelIdsStr=",
	
	htmTablePageList = [6,10,20],
	myIdField = 'channelId',
	myPageSize = 6,
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['maxId'] = maxId;
			}
		}
	},
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'channelId',title : 'id',align : 'center',width : 60},
		{field : 'ownerId',title : '拥有着ID',align : 'center',width : 80},
		
		{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
			formatter:function(value,row,index) {
				return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
			}
		},
		{field : 'channelName',title : '频道名称',align : 'center',width : 120},
		{field : 'channelDesc',title : '频道描述',align : 'center',width : 200},
		{field : 'channelTypeId',title:'频道类型',align : 'center',width : 80,
			formatter:function(value,row,index){
				switch(value){
					case 1 :return "活动频道";
					case 2 :return "贴纸频道";
					case 3 :return "屏蔽频道";
					default:return "默认频道";
				}
			}},
		{field : 'themeName',title:'主题',align : 'center',width:80},
		{field : 'channelLabelNames',title:'标签',align : 'center',width:100},
		{field : 'worldCount',title:'织图总数',align : 'center',width : 60},
		{field : 'worldPictureCount',title:'图片总数',align : 'center',width : 60},
		{field : 'memberCount',title:'成员总数',align : 'center',width : 60},
		{field : 'superbCount',title:'精选总数',align : 'center',width : 60},
  		{field : 'opt',title : '操作',width : 80,align : 'center',rowspan : 1,
			formatter : function(value, row, index ) {
				return "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row.channelId + "\",\"" + index + "\"," + true + ")'>【修改】</a>";
			}
		},
  		{field : 'superb',title : '精选',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='精选' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='非精选' class='htm_column_img' src='" + img + "'/>";
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
  		{field : 'danmu',title : '弹幕',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='弹幕' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='非弹幕' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
  		{field : 'moodFlag',title : '心情',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='心情' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='非心情' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
  		{field : 'worldFlag',title : '织图',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='织图' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='非织图' class='htm_column_img' src='" + img + "'/>";
  			}
  		}
		],

	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$('#htm_serial').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 155,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
		});
		
		$('#htm_edit').window({
			title: '添加频道',
			modal : true,
			width : 520,
			height : 540,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				var $form = $('#edit_form');
				clearFormData($form);
				$("#edit_form .opt_btn").show();
				$("#edit_form .loading").hide();
				$("#channelImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#subImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#subIcon_edit_del_btn").hide();
				$("#channel_labelNames").val('');
				$("#channel_labelIds").val('');
				$("#id_edit").val('');
				$("#channelLabel").html('');
				$("#ss_isSuperb").combobox('clear');
				$("#channelThemeId").combobox('clear');
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		$('#ss_valid').combobox({
			onSelect:function(record) {
				maxId = 0;
				myQueryParams['maxId'] = maxId;
				myQueryParams['valid'] = record.value;
				loadPageData(initPage);
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
	};
	
/**
 * 初始化频道信息编辑窗口
 */
function initEditWindow(id, index, isUpdate) {
	$("#channelName_edit").focus();  //光标定位
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改频道信息');
		$('#htm_edit').window('open');
		$.post(queryChannelByIdOrNameURL,{
			"channelId":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#channelIcon_edit").val(obj['channelIcon']);
				$("#channelImg_edit").attr('src', obj['channelIcon']);
				$("#channelName_edit").val(obj['channelName']);
				$("#channelTitle_edit").val(obj['channelTitle']);
				$("#subIcon_edit").val(obj['subIcon']);
				if(obj['subIcon'] == '') {
					$("#subImg_edit").attr('src', "./base/images/bg_empty.png");	
				} else {
					$("#subIcon_edit_del_btn").show();
					$("#subImg_edit").attr('src', obj['subIcon']);	
				}
				
				if(obj['channelLabelNames'] ){
					var labelNameArray = obj['channelLabelNames'].split(",");
					var labelIdArray = obj['channelLabelIds'].split(",");
					for(i=0;i<labelNameArray.length;i++){
						if(labelNameArray[i].trim() != "" && labelIdArray[i].trim() != ""){
							var labelSpan = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;overflow:hidden;' labelId='"+labelIdArray[i]
											+"' labelName='"+labelNameArray[i]+"'>"+labelNameArray[i]+"</a>").click(function(){
								$(this).remove();
							});
							$("#channelLabel").append(labelSpan);
						}
					}
				}
				$("#id_edit").val(obj['channelId']);
				$("#valid_edit").val(obj['valid']);
				$("#serial_edit").val(obj['serial']);
				$("#ownerId_edit").val(obj['ownerId']);
				$("#channelDesc_edit").val(obj['channelDesc']);
				$("#channel_type_id").combobox('setValue',obj['channelTypeId']);
				$("#channelThemeId").combobox('setValue',obj['themeId']);
				$("#ss_isSuperb").combobox('setValue',obj['superb']);
				
				$("#edit_loading").hide();
				$("#edit_form").show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$('#htm_edit').panel('setTitle', '添加频道');
		$('#htm_edit').window('open');
		$("#edit_loading").hide();
		$("#edit_form").show();
	}
}

//提交表单，以后补充装载验证信息
function loadEditFormValidate(index, isUpdate) {
	var url = saveChannelURL;
	if(isUpdate) {
		url = updateChannelByIdURL;
	}
	var $form = $('#edit_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#edit_form .opt_btn").hide();
				$("#edit_form .loading").show();
				//验证成功后以异步方式提交表单
				$.post(url,$form.serialize(),
					function(result){
						$("#edit_form .opt_btn").show();
						$("#edit_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_edit').window('close');  //关闭添加窗口
							maxId = 0;
							myQueryParams['channel.maxId'] = maxId;
							loadPageData(initPage);
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#channelIcon_edit")
	.formValidator({empty:false, onshow:"请选icon（必填）",onfocus:"请选icon",oncorrect:"该封面可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#channelName_edit")
	.formValidator({empty:false,onshow:"频道名称（必填）",onfocus:"请输入名称",oncorrect:"设置成功"});
	
	$("#channelTitle_edit")
	.formValidator({empty:true,onshow:"标题（必填）",onfocus:"请输入标题",oncorrect:"设置成功"});
	
	$("#channelsubtitle")
	.formValidator({empty:true,onshow:"标题（必填）",onfocus:"请输入副标题",oncorrect:"设置成功"});
	
	$("#subIcon_edit")
	.formValidator({empty:true, onshow:"请选副icon（可选）",onfocus:"请选副icon",oncorrect:"该封面可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
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
					ids.push(rows[i]['channelId']);
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
 * 重排活动
 */
function reSerial() {
	$('#htm_serial .opt_btn').show();
	$('#htm_serial .loading').hide();
	$("#serial_form").find('input[name="reIndexId"]').val('');	
	// 打开添加窗口
	$("#htm_serial").window('open');
}

function submitSerialForm() {
	var $form = $('#serial_form');
	if($form.form('validate')) {
		$('#htm_serial .opt_btn').hide();
		$('#htm_serial .loading').show();
		$('#serial_form').form('submit', {
			url: $form.attr('action'),
			success: function(data){
				var result = $.parseJSON(data);
				$('#htm_serial .opt_btn').show();
				$('#htm_serial .loading').hide();
				if(result['result'] == 0) { 
					$('#htm_serial').window('close');  //关闭添加窗口
					maxId = 0;
					myQueryParams['channel.maxId'] = maxId;
					loadPageData(1);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			}
		});
	}
}

/**
 * 刷新频道缓存
 */
function refresh() {
	$.messager.confirm('提示', '确定要刷新频道缓存？刷新后所有数据将生效！', function(r){
		if (r){
			$('#htm_table').datagrid('loading');
			$.post('./admin_op/channel_updateChannelCache',{
			},function(result){
					if(result['result'] == 0) {
						$('#htm_table').datagrid('loaded');
						$.messager.alert('提示', '刷新成功');  //提示添加信息成功
					} else {
						$('#htm_table').datagrid('loaded');
						$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
					}
				},"json");				
		}
	});
}

function clearEditSubIcon() {
	$("#subIcon_edit_del_btn").hide();
	$("#subImg_edit").attr("src", "./base/images/bg_empty.png");
	$("#subIcon_edit").val('');
}

function addChannelSubmit(){
	var labelnames="";
	var labelIds="";
	$("#channelLabel a").each(function(){
		labelnames += $(this).attr("labelName");
		labelIds   += $(this).attr("labelId");
		labelnames +=",";
		labelIds   +=",";
	});
	if(labelnames.length > 0){
		labelnames = labelnames.substring(0, labelnames.length-1);
	}
	if(labelIds.length > 0){
		labelIds = labelIds.substring(0, labelIds.length-1);
	}
	var url;
	var channelId = $("#id_edit").val();
	if(channelId == ""){
		url = "./admin_op/v2channel_insertOpChannel";
	}else{
		url = "./admin_op/v2channel_updateOpChannel";
	}
	var channelIcon = $("#channelIcon_edit").val();
	var channelName = $("#channelName_edit").val();
//	var channelTitle = $("#channelTitle_edit").val();
//	var subtitle = $("#channelsubtitle").val();
	var subIcon = $("#subIcon_edit").val();
	var channelLabelNames = labelnames;
	var channelLabelIds = labelIds;
	var channelTypeId = $("#channel_type_id").combobox('getValue');
	var ownerId = $("#ownerId_edit").val();
	var channelDesc = $("#channelDesc_edit").val();
	var themeId = $("#channelThemeId").combobox('getValue');
	var superb = $("#ss_isSuperb").combobox('getValue');
	
	$('#htm_table').datagrid('loading');
	$.post(url,{
		'channelId':channelId,
		'channelIcon':channelIcon,
		'channelName':channelName,
//		'channelTitle':channelTitle,
//		'subtitle':subtitle,
		'channelDesc':channelDesc,
		'subIcon':subIcon,
		'channelLabelNames':channelLabelNames,
		'channelLabelIds':channelLabelIds,
		'channelTypeId':channelTypeId,
		'ownerId':ownerId,
		'themeId':themeId,
		'superb' :superb
		},function(result){
			$('#htm_table').datagrid('loaded');
			if(result['result'] == 0){
				$("#htm_table").datagrid('reload');
				$("#htm_edit").window('close');
			}else{
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
	},"json");
}


function searchChannelByIdOrName(){
	var channelSearchFactor = $("#ss_channelName").searchbox('getValue');
	var myQueryParam = {};
	if(channelSearchFactor){
		channelSearchFactor = channelSearchFactor.trim();
	}
	if(isNaN(channelSearchFactor)){
		myQueryParam['channelName'] = channelSearchFactor;
	}else{
		myQueryParam['channelId'] = channelSearchFactor;
	}
	
	$('#htm_table').datagrid('load',myQueryParam);
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" title="添加织图到广场" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效频道！" plain="true" iconCls="icon-ok">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效频道！" plain="true" iconCls="icon-tip">批量失效</a>
			<!-- <a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排活动排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序</a> -->
			<span class="search_label">有效性过滤：</span>
			<select id="ss_valid" style="width:80px;">
	   			<option value="">所有状态</option>
	   			<option value="1">生效</option>
	   			<option value="0">未生效</option>
   			</select>
   			<input id="ss_channelName" class="easyui-searchbox" searcher="searchChannelByIdOrName" prompt="请输入频道名或频道ID" style="width:120px;"></input>
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_op/v2channel_insertOpChannel" method="post">
				<table id="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">ICON：</td>
							<td style="height: 90px;">
								<input class="none" type="text" name="channelIcon" id="channelIcon_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
								<a id="channelIcon_edit_upload_btn" style="position: absolute; margin:30px 0 0 100px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
								<img id="channelImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
								<div id="channelIcon_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">
									上传中...<span class="upload_progress"></span><span>%</span>
								</div>
							</td>
							<td class="rightTd">
								<div id="channelIcon_editTip" style="display: inline-block;" class="tipDIV"></div>
							</td>
						</tr>
						<tr>
							<td class="leftTd">频道名称：</td>
							<td><input id="channelName_edit" name="channelName" onchange="validateSubmitOnce=true;"/></td>
							<td class="rightTd"><div id="channelName_editTip" class="tipDIV"></div></td>
						</tr>
						<!-- 
						<tr>
							<td class="leftTd">标题：</td>
							<td><textarea name="channelTitle" id="channelTitle_edit" onchange="validateSubmitOnce=true;"></textarea></td>
							<td class="rightTd"><div id="channelTitle_editTip" class="tipDIV"></div></td>
						</tr>
						<tr>
							<td class="leftTd">副标题：</td>
							<td><textarea name="subtitle" id="channelsubtitle" onchange="validateSubmitOnce=true;"></textarea></td>
							<td class="rightTd"><div id="channelsubtitle_editTip" class="tipDIV"></div></td>
						</tr>
						 -->
						 
						 <tr>
							<td class="leftTd">频道描述：</td>
							<td><textarea name="channelDesc" id="channelDesc_edit" onchange="validateSubmitOnce=true;"></textarea></td>
							<td class="rightTd"><div id="channelDesc_editTip" class="tipDIV"></div></td>
						</tr>
						 
						<tr>
							<td class="leftTd">拥有者ID：</td>
							<td><input name="ownerId" id="ownerId_edit" onchange="validateSubmitOnce=true;" style="width: 206px;"></input></td>
							<td class="rightTd"><div id="ownerId_editTip" class="tipDIV"></div></td>
						</tr>
						
						<tr>
							<td class="leftTd">频道标签：</td>
							<td>
								<div id="channelLabel" style="width: 206px;height: 60px;background-color: #b8b8b8;"></div>
								<input id="channelLabelSearch" style="width: 206px;">
								<input class="none" id="channelLabelNames_edit">
								<input class="none" id="channelLabelIds_edit">
							</td>
							
							<td class="rightTd"><div id="channelLabel_editTip" class="tipDIV"></div></td>
						</tr>
						
						<tr>
							<td class="leftTd">频道描述：</td>
							<td><textarea name="channelDesc" id="channelDesc_edit" onchange="validateSubmitOnce=true;"></textarea></td>
							<td class="rightTd"><div id="channelDesc_editTip" class="tipDIV"></div></td>
						</tr>
						
						<tr>
							<td class="leftTd">是否为精选：</td>
							<td>
								<select name="superb" id="ss_isSuperb" style="width:206px;" class="easyui-combobox">
									<option value="0">非精选</option>
						   			<option value="1">精选</option>
					   			</select>
							</td>
							<td class="rightTd"><div id="channelLabel_editTip" class="tipDIV"></div></td>
						</tr>
						
						<tr>
							<td class="leftTd">专属主题：</td>
							<td>
								<select name="themeId" id="channelThemeId" class="easyui-combobox" onchange="validateSubmitOnce=true;" style="width: 206px;">
									<option value="10000">旅行频道</option>
						   			<option value="10001">美食频道</option>
						   			<option value="10003">时尚摄影频道</option>
						   			<option value="10004">生活(情感)频道</option>
						   			<option value="10006">织图官方频道</option>
								</select>
							</td>
							<td class="rightTd"><div id="channelType_editTip" class="tipDIV"></div></td>
						</tr>
						
						<tr>
							<td class="leftTd">频道类型：</td>
							<td>
								<select name="channelTypeId" id="channel_type_id" class="easyui-combobox" onchange="validateSubmitOnce=true;" style="width: 206px;">
									<option value="0">默认频道</option>
						   			<option value="1">活动频道</option>
						   			<option value="2">贴纸频道</option>
						   			<option value="3">屏蔽频道</option>
								</select>
							</td>
							<td class="rightTd"><div id="channelType_editTip" class="tipDIV"></div></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="channelId" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="valid" id="valid_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="opt_btn">
							<td colspan="3" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addChannelSubmit();">提交</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_edit').window('close');">取消</a>
							</td>
						</tr>
						<tr class="loading none">
							<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
								<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
								<span style="vertical-align:middle;">保存中...</span>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<!-- 重排索引 -->
		<div id="htm_serial">
			<form id="serial_form" action="./admin_op/channel_updateChannelSerial" method="post">
				<table class="htm_edit_table" width="580">
					<tbody>
						<tr>
							<td class="leftTd">频道ID：</td>
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
						<tr>
							<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitSerialForm();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_serial').window('close');">取消</a>
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
	
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
	<script type="text/javascript">
	
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'channelIcon_edit_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://imzhitu.qiniudn.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        init: {
            'FilesAdded': function(up, files) {
            	$("#channelIcon_edit_upload_btn").hide();
            	$("#channelImg_edit").hide();
            	var $status = $("#channelIcon_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#channelIcon_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#channelIcon_edit_upload_btn").show();
            	$("#channelImg_edit").show();
            	$("#channelIcon_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://imzhitu.qiniudn.com/'+$.parseJSON(info).key;
            	$("#channelImg_edit").attr('src', url);
            	$("#channelIcon_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/channel/" + timestamp+suffix;
                return key;
            }
        }
    });
    
    Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'subIcon_edit_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://imzhitu.qiniudn.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        init: {
            'FilesAdded': function(up, files) {
            	$("#subIcon_edit_upload_btn").hide();
            	$("#subImg_edit").hide();
            	var $status = $("#subIcon_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#subIcon_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#subIcon_edit_upload_btn").show();
            	$("#subIcon_edit_del_btn").show();
            	$("#subImg_edit").show();
            	$("#subIcon_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://imzhitu.qiniudn.com/'+$.parseJSON(info).key;
            	$("#subImg_edit").attr('src', url);
            	$("#subIcon_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/channel/" + timestamp+suffix;
                return key;
            }
        }
    });
	
	</script>
</body>
</html>