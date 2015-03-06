<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js"></script>
<script type="text/javascript">
var maxSerial = 0,
	hideIdColumn = false,
	mySortName = 'serial',
	toolbarComponent = '#tb',
	init = function() {
		myQueryParams = {
			'maxSerial' : maxSerial,
		},
		loadPageData(initPage);
	},
	myOnLoadBefore = function() {
		interacts = {};
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxSerial = 0;
			myQueryParams.maxSerial = maxSerial;
		}
	},
	//tes
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxSerial > maxSerial) {
				maxSerial = data.maxSerial;
				myQueryParams.maxSerial = maxSerial;
			}
		}
	},
	htmTableTitle = "标签列表", //表格标题
	htmTableWidth = 1170,
	worldKey = "worldId",
	loadDataURL = "./admin_ztworld/label_queryLabel", //数据装载请求地址
	deleteURI = "./admin_ztworld/label_deleteLabels?ids=", //删除请求地址
	updateSerialRL = "./admin_ztworld/label_updateLabelSerial?ids=", // 更新有效新请求地址
	queryActivityByIdURL = "./admin_op/op_queryActivityById",  // 根据id查询活动
	updateActivityByIdURL = "./admin_op/op_updateActivity", // 更新活动
	saveActivityURL = "./admin_op/op_saveSquarePushActivity",
	deleteActivityURL = "./admin_op/op_deleteActivityById", // 根据id删除活动
	updatePageURL = "./admin_ztworld/label_updateByJSON", // 根据json批量修改页面数据
	addWidth = 500, //添加信息宽度
	addHeight = 130, //添加信息高度
	addTitle = "添加标签", //添加信息标题
	hideIdColumn = false,
	
	columnsFields = [
  		{field : 'ck',checkbox : true},
  		{field : 'id',title : 'ID', align : 'center',width : 80, sortable: true},
  		{field : 'labelName', title : '名称', align : 'center',width : 120},
  		{field : 'worldCount', title : '织图总数', align : 'center',width : 80, sortable: true, editor:'text',
  			formatter : function(value, row, rowIndex) {
				var uri = 'page_htworld_htworldLabelWorld?labelId='+ row['id'] + '&labelName=' + row['labelName']; //喜欢管理地址			
				return "<a title='显示织图' class='updateInfo' href='javascript:showWorld(\"" + uri + "\")'>"+value+"</a>";
			}	
  		},
  		{field : 'serial', title : '序号', align : 'center',width : 60, sortable: true},
  		{field : 'dateAdded', title : '添加日期', align : 'center',width : 180},
  		{field : 'labelState',title : '活动属性',align : 'center', width: 120,
  			formatter: function(value,row,index){
  				if(value == 1) {
  					return "<a title='更新活动标签' class='updateInfo' href='javascript:void(0)' onclick='javascript:initActivityLabel(\""+ row.id + "\",\"" + index + "\"," + true + ")'>更新</a>"
  						+ "&nbsp;<a title='查看奖品' class='updateInfo' href='javascript:void(0)' onclick='javascript:showActivityAward(\""+ row.id + "\",\"" + row.labelName + "\")'>奖品</a>"
  						+ "&nbsp;<a title='删除活动标签' class='updateInfo' href='javascript:void(0)' onclick='javascript:deleteActivityLabel(\""+ row.id + "\",\"" + index + "\")'>删除</a>";;
  				}
  				img = "./common/images/edit_add.png";
  				return "<img title='添加到活动标签列表' class='htm_column_img pointer' onclick='javascript:initActivityLabel(\""+ row.id + "\",\"" + index + "\"," + false + ")' src='" + img + "'/>";
  			}
  		},
    ],
    
    userMaxId = 0,
	userQueryParams = {
		'maxId':userMaxId
	},
    
    onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		
		$('#htm_serial').window({
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
		
		$('#htm_hot').window({
			title : '更新热门&活动标签缓存',
			modal : true,
			width : 500,
			height : 165,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-reload',
			resizable : false
		});
		
		$('#htm_actlabel').window({
			title: '添加活动标签',
			modal : true,
			width : 1000,
			height : 505,
			top	   : 10,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			resizable : false,
			onClose : function() {
				var $form = $('#actlabel_form');
				clearFormData($form);
				$("#actlabel_form .opt_btn").show();
				$("#actlabel_form .loading").hide();
				$("#titleImg_actlabel").attr("src", "./base/images/bg_empty.png");
				$("#titleThumbImg_actlabel").attr("src", "./base/images/bg_empty.png");
				$("#channelImg_actlabel").attr("src", "./base/images/bg_empty.png");
				$("#commercial_actlabel").combobox('setValue', 0);
				$('#sponsorIds_actlabel').combogrid('clear');
				$("#actlabel_form").hide();
				$("#actlabel_loading").show();
			}
		});
		
		$('#sponsorIds_actlabel').combogrid({
			panelWidth : 340,
		    panelHeight : 250,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#user_tb",
		    multiple : true,
		    required : false,
		   	idField : 'id',
		    textField : 'userName',
		    url : './admin_user/user_queryUser',
		    pagination : true,
		    columns:[[
				userAvatarColumn,
				{field : 'id',title : 'ID',align : 'center', width : 60},
				userNameColumn,
				sexColumn
		    ]],
		    queryParams:userQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > userMaxId) {
						userMaxId = data.maxId;
						userQueryParams.maxId = userMaxId;
					}
				}
		    },
		});
		var p = $('#sponsorIds_actlabel').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					userMaxId = 0;
					userQueryParams.maxId = userMaxId;
				}
			}
		});
		
		removePageLoading();
		$("#main").show();
	},
	pageButtons = [{
        iconCls:'icon-save',
        text:'保存',
        handler:function(){
        	endEditing();
        	var rows = $("#htm_table").datagrid('getChanges', "updated"); 
        	$("#htm_table").datagrid('loading');
        	$.post(updatePageURL,{
        		'json':JSON.stringify(rows)
        		},function(result){
        			if(result['result'] == 0) {
        				$("#htm_table").datagrid('acceptChanges');
        			} else {
        				$.messager.alert('失败提示',result['msg']);  //提示失败信息
        			}
        			$("#htm_table").datagrid('loaded');
        		},"json");
        }
    },{
        iconCls:'icon-undo',
        text:'取消',
        handler:function(){
        	$("#htm_table").datagrid('rejectChanges');
        	$("#htm_table").datagrid('loaded');
        }
    }];

/**
 * 加载列表
 */
function loadList() {
	maxSerial = 0;
	myQueryParams.maxSerial = maxSerial;
	myQueryParams.labelName = "";
	myQueryParams.labelState = $("#labelState").combobox('getValue');
	mySortName = 'serial';
	loadPageData(1); //重新装载第1页数据
}

/**
 * 重新排序
 */
function reSerial() {
	$("#serial_form").find('input[name="reIndexId"]').val('');	
	$('#htm_serial .opt_btn').show();
	$('#htm_serial .loading').hide();
	// 打开添加窗口
	$("#htm_serial").window('open');
}

/**
 * 提交重新排序表单
 */
function submitReSerialForm() {
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
					maxSerial = 0;
					myQueryParams.maxSerial = maxSerial;
					mySortName = "serial";
					mySortOrder = "desc";
					loadPageData(1);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			}
		});
	}
}

/**
 * 重新排序
 */
function updateHot() {
	$('#htm_hot .opt_btn').show();
	$('#htm_hot .loading').hide();
	$("#hotLimit_hot").val(10);
	$("#activityLimit_hot").val(5);
	loadHotFormValidate();
	// 打开添加窗口
	$("#htm_hot").window('open');
}

//提交表单，以后补充装载验证信息
function loadHotFormValidate() {
	var $form = $('#hot_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#hot_form .opt_btn").hide();
				$("#hot_form .loading").show();
				//验证成功后以异步方式提交表单
				$.post($form.attr("action"),$form.serialize(),
					function(result){
						$("#hot_form .opt_btn").show();
						$("#hot_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_hot').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");
				return false;
			}
		}
	});
	
	$("#hotLimit_hot")
	.formValidator({empty:false,onshow:"输入最新活动数",onfocus:"例如:10",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#activityLimit_hot")
	.formValidator({empty:false,onshow:"输入热门标签数",onfocus:"例如:5",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
}
	
/**
 * 搜索标签
 */
function searchLabel() {
	maxSerial = 0;
	myQueryParams.maxSerial = maxSerial;
	myQueryParams.labelName = $('#ss').searchbox('getValue');
	myQueryParams.labelState = "";
	mySortName = 'serial';
	loadPageData(1); //重新装载第1页数据
}

//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	clearFormData(addForm);
	$("#add_form .opt_btn").show();
	$("#add_form .loading").hide();
	$("#labelName_add").focus();  //光标定位
	
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#add_form .opt_btn").hide();
				$("#add_form .loading").show();
				//验证成功后以异步方式提交表单
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						$("#add_form .opt_btn").show();
						$("#add_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							mySortName = 'serial';
							maxSerial = 0;
							myQueryParams.maxSerial = maxSerial;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#labelName_add")
	.formValidator({empty:false,onshow:"标签名称",onfocus:"例如:旅途",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由文字和数字组成"});
}

/**
 * 进入标签-织图页面
 */
function showWorld(uri) {
	$.fancybox({
		'margin'			: 10,
		'width'				: 1090,
		'height'			: 480,
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

/**
 * 添加活动标签
 */
function initActivityLabel(id, index, isUpdate) {
	$("#activityDesc_actlabel").focus();  //光标定位
	loadActLabelFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_actlabel').panel('setTitle', '修改活动标签');
		$('#htm_actlabel').window('open');
		$.post(queryActivityByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var activity = result['activity'];
				$("#titlePath_actlabel").val(activity['titlePath']);
				$("#titleImg_actlabel").attr('src', activity['titlePath']);
				
				$("#titleThumbPath_actlabel").val(activity['titleThumbPath']);
				$("#titleThumbImg_actlabel").attr('src', activity['titleThumbPath']);
				
				$("#channelPath_actlabel").val(activity['channelPath']);
				$("#channelImg_actlabel").attr('src', activity['channelPath']);
				
				$("#activityDesc_actlabel").val(activity['activityDesc']);
				$("#activityTitle_actlabel").val(activity['activityTitle']);
				$("#activityDate_actlabel").datetimebox('setValue', activity['activityDate']);
				$("#deadline_actlabel").datetimebox('setValue', activity['deadline']);
				$("#commercial_actlabel").combobox('setValue', activity['commercial']);
				$("#valid_actlabel").combobox('setValue', activity['valid']);
				
				$("#shareTitle_actlabel").val(activity['shareTitle']);
				$("#shareDesc_actlabel").val(activity['shareDesc']);
				$("#activityLink_actlabel").val(activity['activityLink']);
				
				$("#labelId_actlabel").val(activity['id']);
				var sponsors = activity['sponsors'];
				var sponsorIds = [];
				for(var i = 0; i < sponsors.length; i++) {
					sponsorIds[i] = sponsors[i]['userId'];
				}
				$("#sponsorIds_actlabel").combogrid('setValues',sponsorIds);
				$("#actlabel_loading").hide();
				$("#actlabel_form").show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		var today = new Date().format("YYYY-MM-dd hh:mm:ss");
		$("#activityDate_actlabel").datetimebox('setValue', today);
		$("#labelId_actlabel").val(id);
		$("#valid_actlabel").combobox('setValue', 1);
		$('#htm_actlabel').panel('setTitle', '添加活动标签');
		$('#htm_actlabel').window('open');
		$("#actlabel_loading").hide();
		$("#actlabel_form").show();
	}
}

//提交表单，以后补充装载验证信息
function loadActLabelFormValidate(index, isUpdate) {
	var url = saveActivityURL;
	if(isUpdate) {
		url = updateActivityByIdURL;
	}
	var $form = $('#actlabel_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#actlabel_form .opt_btn").hide();
				$("#actlabel_form .loading").show();
				//验证成功后以异步方式提交表单
				$.post(url,$form.serialize(),
					function(result){
						$("#actlabel_form .opt_btn").show();
						$("#actlabel_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_actlabel').window('close');  //关闭添加窗口
							updateValue(index, "labelState", "1");
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#titlePath_actlabel")
	.formValidator({empty:false, onshow:"请选择封面（必填）",onfocus:"请选择封面",oncorrect:"该封面可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#channelPath_actlabel")
	.formValidator({empty:false, onshow:"请选择频道封面（必填）",onfocus:"请选择封面",oncorrect:"该封面可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#activityDesc_actlabel")
	.formValidator({empty:false,onshow:"活动描述（必填）",onfocus:"请输入描述",oncorrect:"设置成功"});
	
	$("#activityTitle_actlabel")
	.formValidator({empty:false,onshow:"活动标题（必填）",onfocus:"请输入标题",oncorrect:"设置成功"});
	
	$("#activityDate_actlabel")
	.formValidator({empty:false,onshow:"开始日期（必填）",onfocus:"请选择日期",oncorrect:"设置成功"});
	
	$("#deadline_actlabel")
	.formValidator({empty:true,onshow:"截止日期（可选）",onfocus:"允许为空",oncorrect:"设置成功"});
	
	$("#titleThumbPath_actlabel")
	.formValidator({empty:false, onshow:"缩略图（必填）",onfocus:"请选择缩略图",oncorrect:"该图片可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#shareTitle_actlabel")
	.formValidator({empty:false,onshow:"分享标题（必填）",onfocus:"请输入标题",oncorrect:"设置成功"});
	
	$("#shareDesc_actlabel")
	.formValidator({empty:false,onshow:"分享描述（必填）",onfocus:"请输入描述",oncorrect:"设置成功"});
	
	$("#activityLink_actlabel")
	.formValidator({empty:true, onshow:"分享链接（可选）",onfocus:"请输入url",oncorrect:"设置成功"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
}

/**
 * 删除活动
 */
function deleteActivityLabel(id, index) {
	$.messager.confirm('删除记录', '您确定要删除活动标签属性?', function(r){
		if(r){			
			$('#htm_table').datagrid('loading');
			$.post(deleteActivityURL,{
				"id":id
			},function(result){
				$('#htm_table').datagrid('loaded');
				if(result['result'] == 0) {
					updateValue(index, "labelState", "0");
					$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
				} else {
					$.messager.alert('提示',result['msg']);
				}
			});				
		}	
	});	
}

/**
 * 显示活动奖品
 */
function showActivityAward(activityId, activityName) {
	var uri = "page_operations_activityAward?activityId=" + activityId + "&activityName=" + encodeURI(encodeURI(activityName));
	showURI(uri);
}


/**
 * 搜索用户
 */
function searchUser() {
	userMaxId = 0;
	var userName = $('#ss_user').searchbox('getValue');
	userQueryParams = {
		'maxId' : userMaxId,
		'userName' : userName
	};
	$("#sponsorIds_actlabel").combogrid('grid').datagrid("load",userQueryParams);
}

/**
 * 清空添加页面已经选择的发起人
 */
function clearSponsorsActLabel() {
	$("#sponsorIds_actlabel").combogrid('clear');
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加标签" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除标签" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter">重新排序</a>
			<a href="javascript:void(0);" onclick="javascript:updateHot();" class="easyui-linkbutton" title="刷新热门&活动标签缓存" plain="true" iconCls="icon-reload">刷新热门&活动</a>
			<select id="labelState" class="easyui-combobox" name="labelState" style="width:75px;">
				<option value="">所有标签</option>
		        <option value="1">活动标签</option>
		        <option value="0">普通标签</option>
	   		</select>
	   		<div style="display:inline-block; vertical-align:middle;">
	   		<a href="javascript:void(0)" class="easyui-linkbutton" title="加载列表" plain="true" iconCls="icon-search" onclick="loadList();">查询</a>
	   		</div>
	   		<div style="display: inline-block; float: right; margin-right: 5px; margin-top:3px;">
		        <input id="ss" class="easyui-searchbox" searcher="searchLabel" prompt="请输入关键字搜索标签" style="width:200px;"></input>
			</div>
   		</div>
	</div> 

	<!-- 排序窗口 -->
	<div id="htm_serial">
		<form id="serial_form" action="./admin_ztworld/label_updateLabelSerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">ID：</td>
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
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReSerialForm();">确定</a>
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
	
	<!-- 添加窗口 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_ztworld/label_saveLabel" method="post">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">名称：</td>
						<td><input type="text" name="labelName" id="labelName_add" style="width:205px;"/></td>
						<td class="rightTd"><div id="labelName_addTip" class="tipDIV"></div></td>
					</tr>
					<!-- 
					<tr>
						<td class="leftTd"></td>
						<td colspan="2"><input type="checkbox" id="isMaxSerial_add" name="isMaxSerial" value="true" 
							style="width:13px; vertical-align: middle;"/>热门标签</td>
					</tr>
					 -->
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 更新热门窗口 -->
	<div id="htm_hot">
		<form id="hot_form" action="./admin_ztworld/label_updateHotLabel" method="post">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">活动数：</td>
						<td><input type="text" name="activityLimit" id="activityLimit_hot" style="width:205px;"/></td>
						<td class="rightTd"><div id="activityLimit_hotTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">热门数：</td>
						<td><input type="text" name="hotLimit" id="hotLimit_hot" style="width:205px;"/></td>
						<td class="rightTd"><div id="hotLimit_hotTip" class="tipDIV"></div></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#hot_form').submit();">确定</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_hot').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 添加活动标签-->
	<div id="htm_actlabel">
		<span id="actlabel_loading" style="margin:140px 0 0 440px; position:absolute;">加载中...</span>
		<form id="actlabel_form" action="./admin_op/op_saveSquarePushActivity" method="post" class="none">
			<table class="htm_edit_table" width="960" class="none">
				<tbody>
					 <tr>
						<td class="leftTd">封面：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="titlePath" id="titlePath_actlabel"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="titlePath_actlabel_upload_btn" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="titleImg_actlabel"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="205px" height="90px">
							<div id="titlePath_actlabel_upload_status" class="update_status none" style="width: 205px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="titlePath_actlabelTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">缩略图：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="titleThumbPath" id="titleThumbPath_actlabel"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="titleThumbPath_actlabel_upload_btn" style="position: absolute; margin:30px 0 0 5px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="titleThumbImg_actlabel"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="titleThumbPath_actlabel_upload_status" class="update_status none" style="width: 205px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="titleThumbPath_actlabelTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">频道封面：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="channelPath" id="channelPath_actlabel"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="channelPath_actlabel_upload_btn" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="channelImg_actlabel"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="205px" height="90px">
							<div id="channelPath_actlabel_upload_status" class="update_status none" style="width: 205px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="channelPath_actlabelTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">分享链接：</td>
						<td><input type="text" name="activityLink" id="activityLink_actlabel" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="activityLink_actlabelTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">活动描述：</td>
						<td><textarea name="activityDesc" id="activityDesc_actlabel" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="activityDesc_actlabelTip" class="tipDIV"></div></td>
						
						
						<td class="leftTd">分享标题：</td>
						<td><textarea name="shareTitle" id="shareTitle_actlabel" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="shareTitle_actlabelTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">活动标题：</td>
						<td><textarea name="activityTitle" id="activityTitle_actlabel" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="activityTitle_actlabelTip" class="tipDIV"></div></td>
						
						<td class="leftTd">分享描述：</td>
						<td><textarea name="shareDesc" id="shareDesc_actlabel" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="shareDesc_actlabelTip" class="tipDIV"></div></td>
						
					</tr>
					<tr>
						<td class="leftTd">发起人：</td>
						<td><input id="sponsorIds_actlabel" name="sponsorIds" style="width:205px"/></td>
						<td><a class="easyui-linkbutton" style="vertical-align: middle; margin-bottom: 3px;" title="清空发起人" plain="true" iconCls="icon-remove" onclick="clearSponsorsActLabel();"></a></td>
						
						<td class="leftTd">活动状态：</td>
						<td>
							<select id="valid_actlabel" class="easyui-combobox" name="valid" style="width:105px;">
						        <option value="1">进行中</option>
						        <option value="0">结束</option>
					   		</select>
						</td>
						<td class="rightTd"><div id="activityLink_actlabelTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">开始日期：</td>
						<td><input id="activityDate_actlabel" class="easyui-datetimebox" name="activityDate"  onchange="validateSubmitOnce=true;"></td>
						<td colspan="4" class="rightTd"><div id="activityDate_actlabelTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">截止日期：</td>
						<td><input id="deadline_actlabel" class="easyui-datetimebox" name="deadline"  onchange="validateSubmitOnce=true;"></td>
						<td colspan="4" class="rightTd"><div id="deadline_actlabelTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none">
						<td class="leftTd">商业标记：</td>
						<td>
							<select id="commercial_actlabel" class="easyui-combobox" name="commercial" style="width:105px;">
						        <option value="0">普通活动</option>
						        <option value="1">商业活动</option>
					   		</select>
						</td>
						<td colspan="4" class="rightTd"><div id="deadline_actlabelTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none">
						<td class="leftTd">标签id：</td>
						<td><input type="text" name="labelId" id="labelId_actlabel" onchange="validateSubmitOnce=true;"/></td>
						<td colspan="4" class="rightTd"></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="6" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#actlabel_form').submit();">确定</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_actlabel').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="6" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<div id="user_tb" style="padding:5px;height:auto" class="none">
		<input id="ss_user" searcher="searchUser" class="easyui-searchbox" prompt="用户名/ID搜索" style="width:200px;"/>
	</div>
	
	</div>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
	<script type="text/javascript">
	
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'titlePath_actlabel_upload_btn',
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
            	$("#titlePath_actlabel_upload_btn").hide();
            	$("#titleImg_actlabel").hide();
            	var $status = $("#titlePath_actlabel_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#titlePath_actlabel_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#titlePath_actlabel_upload_btn").show();
            	$("#titleImg_actlabel").show();
            	$("#titlePath_actlabel_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://imzhitu.qiniudn.com/'+$.parseJSON(info).key;
            	$("#titleImg_actlabel").attr('src', url);
            	$("#titlePath_actlabel").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/activity/" + timestamp+suffix;
                return key;
            }
        }
    });
	
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'titleThumbPath_actlabel_upload_btn',
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
            	$("#titleThumbPath_actlabel_upload_btn").hide();
            	$("#titleThumbImg_actlabel").hide();
            	var $status = $("#titleThumbPath_actlabel_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#titleThumbPath_actlabel_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#titleThumbPath_actlabel_upload_btn").show();
            	$("#titleThumbImg_actlabel").show();
            	$("#titleThumbPath_actlabel_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://imzhitu.qiniudn.com/'+$.parseJSON(info).key;
            	$("#titleThumbImg_actlabel").attr('src', url);
            	$("#titleThumbPath_actlabel").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/activity/" + timestamp+suffix;
                return key;
            }
        }
    });
    
    Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'channelPath_actlabel_upload_btn',
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
            	$("#channelPath_actlabel_upload_btn").hide();
            	$("#channelImg_actlabel").hide();
            	var $status = $("#channelPath_actlabel_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#channelPath_actlabel_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#channelPath_actlabel_upload_btn").show();
            	$("#channelImg_actlabel").show();
            	$("#channelPath_actlabel_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://imzhitu.qiniudn.com/'+$.parseJSON(info).key;
            	$("#channelImg_actlabel").attr('src', url);
            	$("#channelPath_actlabel").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/activity/" + timestamp+suffix;
                return key;
            }
        }
    });
	
	</script>
</body>
</html>
