<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>子世界类型管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
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
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxSerial = 0;
			myQueryParams.maxSerial = maxSerial;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxSerial > maxSerial) {
				maxSerial = data.maxSerial;
				myQueryParams.maxSerial = maxSerial;
			}
		}
	},
	htmTableTitle = "类型列表", //表格标题
	myPageSize = 5,
	loadDataURL = "./admin_ztworld/child_queryChildType", //数据装载请求地址
	deleteURI = "./admin_ztworld/child_deleteChildType?ids=", //删除请求地址
	queryTypeByIdURL = "./admin_ztworld/child_queryChildTypeById",  // 根据id查询
	updateTypeByIdURL = "./admin_ztworld/child_updateChildType", // 更新活动
	saveTypeURL = "./admin_ztworld/child_saveChildType",
	addWidth = 1170, //添加信息宽度
	addHeight = 520, //添加信息高度
	addTitle = "添加类型", //添加信息标题
	hideIdColumn = false,
	
	columnsFields = [
  		{field : 'ck',checkbox : true},
  		{field : 'id',title : 'ID', align : 'center',width : 80},
  		{field : 'typePath',title : '图1', align : 'center', width : 90,
			formatter:function(value,row,index) {
				return "<img width='70px' alt='缩略图'  class='htm_column_img' src='" + value + "'/>";
			}
		},  	
		{field : 'typePath1',title : '图2', align : 'center', width : 90,
			formatter:function(value,row,index) {
				return "<img width='70px' alt='缩略图'  class='htm_column_img' src='" + value + "'/>";
			}
		},  
		{field : 'typePath2',title : '图3', align : 'center', width : 90,
			formatter:function(value,row,index) {
				return "<img width='70px' alt='缩略图'  class='htm_column_img' src='" + value + "'/>";
			}
		},  
		{field : 'typePath3',title : '图4', align : 'center', width : 90,
			formatter:function(value,row,index) {
				return "<img width='70px' alt='缩略图'  class='htm_column_img' src='" + value + "'/>";
			}
		},
		{field : 'typePath4',title : '图5', align : 'center', width : 90,
			formatter:function(value,row,index) {
				return "<img width='70px' alt='缩略图'  class='htm_column_img' src='" + value + "'/>";
			}
		},  
		{field : 'typePath5',title : '图6', align : 'center', width : 90,
			formatter:function(value,row,index) {
				return "<img width='70px' alt='缩略图'  class='htm_column_img' src='" + value + "'/>";
			}
		},  
		{field : 'descPath',title : '介绍图', align : 'center', width : 90,
			formatter:function(value,row,index) {
				return "<img width='70px' alt='介绍图'  class='htm_column_img' src='" + value + "'/>";
			}
		}, 
		{field : 'total',title : '总数', align : 'center',width : 80},
		{field : 'useCount',title : '已使用数', align : 'center',width : 80},
		{field : 'labelName',title : '关联活动', align : 'center', width : 90}, 
		{field : 'opt',title : '操作',align : 'center', width: 80,
  			formatter: function(value,row,index){
  				return "<a title='更新类型信息' class='updateInfo' href='javascript:void(0)' onclick='javascript:initAddWindow(\""
  						+ row['id'] + "\",\"" + index + "\",\"" + true + "\")'>更新</a>";
  			}
  		},
    ],
    
    activityMaxSerial = 0,
	activityQueryParams = {
		'maxSerial':activityMaxSerial
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
			title : '刷新最新类型',
			modal : true,
			width : 500,
			height : 135,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-reload',
			resizable : false
		});
		
		$('#labelName_add').combogrid({
			panelWidth:460,
		    panelHeight:300,
		    mode: 'remote',
		    loadMsg:'加载中，请稍后...',
			pageList: [4,10,20],
			pageSize:4,
			editable: false,
		    multiple: false,
		    idField:'activityName',
		    textField:'activityName',
		    url:'./admin_op/op_querySquarePushActivity',
		    queryParams:activityQueryParams,
		    pagination:true,
		    columns:[[
				{field : 'serial',title : '活动序号',align : 'center', width : 60, hidden:true},
		  		{field : 'id',title : '活动ID',align : 'center', width : 60},
		  		{field : 'activityName',title : '活动名称',align : 'center', width : 120},
				{field : 'activityDate',title :'添加日期',align : 'center',width : 150},
				{ field : 'titlePath',title : '封面', align : 'center',width : 90, height:53,
					formatter:function(value,row,index) {
						return "<img width='80px' height='53px' alt='' title='点击编辑' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}	
				}
		    ]],
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxSerial > activityMaxSerial) {
						activityMaxSerial = data.maxSerial;
						activityQueryParams.maxSerial = activityMaxSerial;
					}
				}
		    }
		});
		
		removePageLoading();
		$("#main").show();
	};

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
	$("#limit_hot").val(5);
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
	
	$("#limit_hot")
	.formValidator({empty:false,onshow:"输入最新类型数",onfocus:"例如:2",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
}
	

/**
 * 初始化添加窗口
 */
function initAddWindow(id, index, isUpdate) {
	var addForm = $('#add_form');
	$("#htm_add .opt_btn").show();
	$("#htm_add .loading").hide();
	commonTools.clearFormData(addForm);
	$("#labelName_add").combogrid('clear');
	$("#typeImg_add").attr("src", "./base/images/bg_empty.png");
	$("#typeImg1_add").attr("src", "./base/images/bg_empty.png");
	$("#typeImg2_add").attr("src", "./base/images/bg_empty.png");
	$("#typeImg3_add").attr("src", "./base/images/bg_empty.png");
	$("#typeImg4_add").attr("src", "./base/images/bg_empty.png");
	$("#typeImg5_add").attr("src", "./base/images/bg_empty.png");
	$("#typeImg6_add").attr("src", "./base/images/bg_empty.png");
	$("#typeImg7_add").attr("src", "./base/images/bg_empty.png");
	$("#typeImg8_add").attr("src", "./base/images/bg_empty.png");
	$("#typeImg9_add").attr("src", "./base/images/bg_empty.png");
	$("#typeImg10_add").attr("src", "./base/images/bg_empty.png");
	loadAddFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_add').panel('setTitle', '修改类型');
		$('#htm_add').window('open');
		$.post(queryTypeByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var type = result['type'];
				$("#typePath_add").val(type['typePath']);
				$("#typeImg_add").attr('src', type['typePath']);
				
				$("#typePath1_add").val(type['typePath1']);
				$("#typeImg1_add").attr('src', type['typePath1']);
				
				$("#typePath2_add").val(type['typePath2']);
				$("#typeImg2_add").attr('src', type['typePath2']);
				
				$("#typePath3_add").val(type['typePath3']);
				$("#typeImg3_add").attr('src', type['typePath3']);
				
				$("#typePath4_add").val(type['typePath4']);
				$("#typeImg4_add").attr('src', type['typePath4']);
				
				$("#typePath5_add").val(type['typePath5']);
				$("#typeImg5_add").attr('src', type['typePath5']);
				
				$("#typePath6_add").val(type['typePath6']);
				$("#typeImg6_add").attr('src', type['typePath6']);
				
				$("#typePath7_add").val(type['typePath7']);
				$("#typeImg7_add").attr('src', type['typePath7']);
				
				$("#typePath8_add").val(type['typePath8']);
				$("#typeImg8_add").attr('src', type['typePath8']);
				
				$("#typePath9_add").val(type['typePath9']);
				$("#typeImg9_add").attr('src', type['typePath9']);
				
				$("#typePath10_add").val(type['typePath10']);
				$("#typeImg10_add").attr('src', type['typePath10']);
				
				$("#descPath_add").val(type['descPath']);
				$("#descImg_add").attr('src', type['descPath']);
				
				$("#total_add").val(type['total']);
				$("#useCount_add").val(type['useCount']);
				$("#typeDesc_add").val(type['typeDesc']);
				$("#serial_add").val(type['serial']);
				
				$("#id_add").val(type['id']);
				$("#labelName_add").combogrid('setValue',type['labelName']);
				
				$("#add_loading").hide();
				$("#add_form").show();
				
				$("#typePath1_add_upload_btn").hide();
            	$("#typePath2_add_upload_btn").hide();
            	$("#typePath3_add_upload_btn").hide();
            	$("#typePath4_add_upload_btn").hide();
            	$("#typePath5_add_upload_btn").hide();
            	$("#typePath6_add_upload_btn").hide();
            	$("#typePath7_add_upload_btn").hide();
            	$("#typePath8_add_upload_btn").hide();
            	$("#typePath9_add_upload_btn").hide();
            	$("#typePath10_add_upload_btn").hide();
				
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$('#htm_add').panel('setTitle', '添加类型');
		$("#useCount_add").val(0);
		$("#typePath1_add_upload_btn").show();
    	$("#typePath2_add_upload_btn").show();
    	$("#typePath3_add_upload_btn").show();
    	$("#typePath4_add_upload_btn").show();
    	$("#typePath5_add_upload_btn").show();
    	$("#typePath6_add_upload_btn").show();
    	$("#typePath7_add_upload_btn").show();
    	$("#typePath8_add_upload_btn").show();
    	$("#typePath9_add_upload_btn").show();
    	$("#typePath10_add_upload_btn").show();
		$('#htm_add').window('open');
		$("#add_loading").hide();
		$("#add_form").show();
	}
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate(index, isUpdate) {
	var url = saveTypeURL;
	if(isUpdate) {
		url = updateTypeByIdURL;
	}
	var $form = $('#add_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#add_form .opt_btn").hide();
				$("#add_form .loading").show();
				//验证成功后以异步方式提交表单
				$.post(url,$form.serialize(),
					function(result){
						$("#add_form .opt_btn").show();
						$("#add_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							if(!isUpdate) {
								$.messager.alert('提示',result['msg']);  //提示添加信息成功
								maxSerial = 0;
								myQueryParams.maxSerial = maxSerial;
								loadPageData(1); //重新装载第1页数据
							} else {
								$("#htm_table").datagrid('reload');
							}
							
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#typePath_add")
	.formValidator({empty:false, onshow:"点击前",onfocus:"点击前",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#typePath1_add")
	.formValidator({empty:false, onshow:"点击后",onfocus:"点击后",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#typePath2_add")
	.formValidator({empty:false, onshow:"遮罩层",onfocus:"遮罩层",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#typePath3_add")
	.formValidator({empty:false, onshow:"相册已选形状",onfocus:"相册已选形状",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#typePath4_add")
	.formValidator({empty:false, onshow:"相册未选形状",onfocus:"相册未选形状",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#typePath5_add")
	.formValidator({empty:false, onshow:"相册选择遮罩",onfocus:"相册选择遮罩",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#typePath6_add")
	.formValidator({empty:false, onshow:"安卓遮罩",onfocus:"安卓遮罩",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#typePath7_add")
	.formValidator({empty:false, onshow:"网页边框",onfocus:"网页边框",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#typePath8_add")
	.formValidator({empty:false, onshow:"网页遮罩",onfocus:"网页遮罩",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#typePath9_add")
	.formValidator({empty:false, onshow:"手机边框",onfocus:"手机边框",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#typePath10_add")
	.formValidator({empty:false, onshow:"手机遮罩",onfocus:"手机遮罩",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#descPath_add")
	.formValidator({empty:false, onshow:"请上传介绍图",onfocus:"请上传介绍图",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#total_add")
	.formValidator({empty:false, onshow:"请输入总数",onfocus:"输入数字",oncorrect:"正确！"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#useCount_add")
	.formValidator({empty:false, onshow:"请输入已用数",onfocus:"输入数字",oncorrect:"正确！"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#labelName_add")
	.formValidator({empty:true, onshow:"（可选）关联活动",onfocus:"请选择关联活动",oncorrect:"正确！"});
	
	$("#typeDesc_add")
	.formValidator({empty:true, onshow:"请输入描述",onfocus:"请输入描述",oncorrect:"正确！"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"请输入中文"});
	
	
}


</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initAddWindow('0','0',false);" class="easyui-linkbutton" title="添加类型" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除类型" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter">重新排序</a>
			<a href="javascript:void(0);" onclick="javascript:updateHot();" class="easyui-linkbutton" title="刷新最新类型" plain="true" iconCls="icon-reload">刷新最新类型</a>
   		</div>
	</div> 
	
	<!-- 添加活动标签-->
	<div id="htm_add">
		<div style="margin: 10px 0 0 10px; font-size: 14px;">注意：除了图1，其他图片单独修改，如果想修改某一张图片，必须先上传图1</div>
		<hr />
		<span id="add_loading" style="margin:170px 0 0 380px; position:absolute;">加载中...</span>
		<form id="add_form" action="./admin_op/op_saveSquarePushActivity" method="post">
			<table class="htm_edit_table" width="1150" class="none">
				<tbody>
					 <tr>
						<td class="leftTd">图1：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath" id="typePath_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath_add_upload_btn" style="position: absolute; margin:30px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图1</a> 
							<img id="typeImg_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="typePath_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">图4：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath3" id="typePath3_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath3_add_upload_btn" style="position: absolute; margin:20px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图4</a> 
							<img id="typeImg3_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="82px" height="90px">
							<div id="typePath3_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath3_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">图7：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath6" id="typePath6_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath6_add_upload_btn" style="position: absolute; margin:30px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图7</a> 
							<img id="typeImg6_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="typePath6_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath6_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">图10：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath9" id="typePath9_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath9_add_upload_btn" style="position: absolute; margin:30px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图10</a> 
							<img id="typeImg9_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="typePath9_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath9_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">图2：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath1" id="typePath1_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath1_add_upload_btn" style="position: absolute; margin:30px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图2</a> 
							<img id="typeImg1_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="typePath1_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath1_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">图5：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath4" id="typePath4_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath4_add_upload_btn" style="position: absolute; margin:20px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图5</a> 
							<img id="typeImg4_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="82px" height="90px">
							<div id="typePath4_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath4_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">图8：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath7" id="typePath7_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath7_add_upload_btn" style="position: absolute; margin:30px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图8</a> 
							<img id="typeImg7_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="typePath7_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath7_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">图11：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath10" id="typePath10_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath10_add_upload_btn" style="position: absolute; margin:30px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图11</a> 
							<img id="typeImg10_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="typePath10_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath10_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">图3：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath2" id="typePath2_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath2_add_upload_btn" style="position: absolute; margin:30px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图3</a> 
							<img id="typeImg2_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="typePath2_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath2_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">图6：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath5" id="typePath5_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath5_add_upload_btn" style="position: absolute; margin:30px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图6</a> 
							<img id="typeImg5_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="typePath5_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath5_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">图9：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="typePath8" id="typePath8_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="typePath8_add_upload_btn" style="position: absolute; margin:30px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">上传图9</a> 
							<img id="typeImg8_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="typePath8_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="typePath8_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
						<td class="leftTd">介绍图：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="descPath" id="descPath_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="descPath_add_upload_btn" style="position: absolute; margin:30px 0 0 8px" class="easyui-linkbutton" iconCls="icon-add">介绍图</a> 
							<img id="descImg_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="descPath_add_upload_status" class="update_status none" style="width: 90px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd" style="width: 120px !important;">
							<div id="descPath_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">总数：</td>
						<td><input type="text" name="total" id="total_add" style="width: 90px;" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd" id="total_addTip"></td>
						
						<td class="leftTd" style="vertical-align: middle;">已用数：</td>
						<td><input type="text" name="useCount" id="useCount_add" style="width: 90px" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd" id="useCount_addTip"></td>
						<!-- 
						<td class="leftTd">关联活动：</td>
						<td><input type="text" name="labelName" id="labelName_add" style="width: 90px;" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd" id="labelName_addTip"></td>
						 -->
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td colspan="4"><textarea name="typeDesc" id="typeDesc_add" style="width:390px;" onchange="validateSubmitOnce=true;"></textarea></td>
						<td colspan="6" class="rightTd"><div id="typeDesc_addTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none">
						<td class="leftTd">id：</td>
						<td><input type="text" name="id" id="id_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"></td>
					</tr>
					<tr class="none">
						<td class="leftTd">serial：</td>
						<td><input type="text" name="serial" id="serial_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="12" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">确定</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="12" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

	<!-- 排序窗口 -->
	<div id="htm_serial">
		<form id="serial_form" action="./admin_ztworld/child_updateChildTypeSerial" method="post">
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
	
	<!-- 更新热门窗口 -->
	<div id="htm_hot">
		<form id="hot_form" action="./admin_ztworld/child_updateLatestChildType" method="post">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">类型数：</td>
						<td><input type="text" name="limit" id="limit_hot" style="width:205px;"/></td>
						<td class="rightTd"><div id="limit_hotTip" class="tipDIV"></div></td>
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
	
	</div>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
	<script type="text/javascript">
	
	var uploader1 = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath1_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : 1
        },
        init: {
            'FilesAdded': function(up, files) {
            	var typePath = $("#typePath_add").val();
            	if(typePath == "") {
            		$.messager.alert('提示', "必须先上传图1");
            		uploader1.stop();
            	} else {
            		$("#typePath1_add_upload_btn").hide();
                	$("#typeImg1_add").hide();
                	var $status = $("#typePath1_add_upload_status");
                	$status.find('.upload_progress:eq(0)').text(0);
                	$status.show();
            	}
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath1_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	//$("#typePath1_add_upload_btn").show();
            	$("#typeImg1_add").show();
            	$("#typePath1_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg1_add").attr('src', url);
            	$("#typePath1_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });
	
	var uploader2 = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath2_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : 2
        },
        init: {
            'FilesAdded': function(up, files) {
            	var typePath = $("#typePath_add").val();
            	if(typePath == "") {
            		$.messager.alert('提示', "必须先上传图1");
            		uploader2.stop();
            	} else {
            		$("#typePath2_add_upload_btn").hide();
                	$("#typeImg2_add").hide();
                	var $status = $("#typePath2_add_upload_status");
                	$status.find('.upload_progress:eq(0)').text(0);
                	$status.show();
            	}
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath2_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	//$("#typePath2_add_upload_btn").show();
            	$("#typeImg2_add").show();
            	$("#typePath2_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg2_add").attr('src', url);
            	$("#typePath2_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });
	
	var uploader3 = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath3_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : 3
        },
        init: {
            'FilesAdded': function(up, files) {
            	var typePath = $("#typePath_add").val();
            	if(typePath == "") {
            		$.messager.alert('提示', "必须先上传图1");
            		uploader3.stop();
            	} else {
            		$("#typePath3_add_upload_btn").hide();
                	$("#typeImg3_add").hide();
                	var $status = $("#typePath3_add_upload_status");
                	$status.find('.upload_progress:eq(0)').text(0);
                	$status.show();
            	}
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath3_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	//$("#typePath3_add_upload_btn").show();
            	$("#typeImg3_add").show();
            	$("#typePath3_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg3_add").attr('src', url);
            	$("#typePath3_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });
	
	var uploader4 = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath4_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : 4
        },
        init: {
            'FilesAdded': function(up, files) {
            	var typePath = $("#typePath_add").val();
            	if(typePath == "") {
            		$.messager.alert('提示', "必须先上传图1");
            		uploader4.stop();
            	} else {
            		$("#typePath4_add_upload_btn").hide();
                	$("#typeImg4_add").hide();
                	var $status = $("#typePath4_add_upload_status");
                	$status.find('.upload_progress:eq(0)').text(0);
                	$status.show();
            	}
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath4_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#typeImg4_add").show();
            	$("#typePath4_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg4_add").attr('src', url);
            	$("#typePath4_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });
	
	var uploader5 = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath5_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : 5
        },
        init: {
            'FilesAdded': function(up, files) {
            	var typePath = $("#typePath_add").val();
            	if(typePath == "") {
            		$.messager.alert('提示', "必须先上传图1");
            		uploader5.stop();
            	} else {
            		$("#typePath5_add_upload_btn").hide();
                	$("#typeImg5_add").hide();
                	var $status = $("#typePath5_add_upload_status");
                	$status.find('.upload_progress:eq(0)').text(0);
                	$status.show();
            	}
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath5_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#typeImg5_add").show();
            	$("#typePath5_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg5_add").attr('src', url);
            	$("#typePath5_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });
	
	var uploader6 = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath6_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : 6
        },
        init: {
            'FilesAdded': function(up, files) {
            	var typePath = $("#typePath_add").val();
            	if(typePath == "") {
            		$.messager.alert('提示', "必须先上传图1");
            		uploader6.stop();
            	} else {
            		$("#typePath6_add_upload_btn").hide();
                	$("#typeImg6_add").hide();
                	var $status = $("#typePath6_add_upload_status");
                	$status.find('.upload_progress:eq(0)').text(0);
                	$status.show();
            	}
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath6_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#typeImg6_add").show();
            	$("#typePath6_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg6_add").attr('src', url);
            	$("#typePath6_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });
	
	var uploader7 = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath7_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : 7
        },
        init: {
            'FilesAdded': function(up, files) {
            	var typePath = $("#typePath_add").val();
            	if(typePath == "") {
            		$.messager.alert('提示', "必须先上传图1");
            		uploader7.stop();
            	} else {
            		$("#typePath7_add_upload_btn").hide();
                	$("#typeImg7_add").hide();
                	var $status = $("#typePath7_add_upload_status");
                	$status.find('.upload_progress:eq(0)').text(0);
                	$status.show();
            	}
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath7_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#typeImg7_add").show();
            	$("#typePath7_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg7_add").attr('src', url);
            	$("#typePath7_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });
	
	var uploader8 = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath8_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : 8
        },
        init: {
            'FilesAdded': function(up, files) {
            	var typePath = $("#typePath_add").val();
            	if(typePath == "") {
            		$.messager.alert('提示', "必须先上传图1");
            		uploader8.stop();
            	} else {
            		$("#typePath8_add_upload_btn").hide();
                	$("#typeImg8_add").hide();
                	var $status = $("#typePath8_add_upload_status");
                	$status.find('.upload_progress:eq(0)').text(0);
                	$status.show();
            	}
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath8_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#typeImg8_add").show();
            	$("#typePath8_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg8_add").attr('src', url);
            	$("#typePath8_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });
	
	var uploader9 = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath9_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : 9
        },
        init: {
            'FilesAdded': function(up, files) {
            	var typePath = $("#typePath_add").val();
            	if(typePath == "") {
            		$.messager.alert('提示', "必须先上传图1");
            		uploader9.stop();
            	} else {
            		$("#typePath9_add_upload_btn").hide();
                	$("#typeImg9_add").hide();
                	var $status = $("#typePath9_add_upload_status");
                	$status.find('.upload_progress:eq(0)').text(0);
                	$status.show();
            	}
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath9_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#typeImg9_add").show();
            	$("#typePath9_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg9_add").attr('src', url);
            	$("#typePath9_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });
	
	var uploader10 = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath10_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : 10
        },
        init: {
            'FilesAdded': function(up, files) {
            	var typePath = $("#typePath_add").val();
            	if(typePath == "") {
            		$.messager.alert('提示', "必须先上传图1");
            		uploader10.stop();
            	} else {
            		$("#typePath10_add_upload_btn").hide();
                	$("#typeImg10_add").hide();
                	var $status = $("#typePath10_add_upload_status");
                	$status.find('.upload_progress:eq(0)').text(0);
                	$status.show();
            	}
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath10_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);
            },
            'UploadComplete': function() {
            	$("#typeImg10_add").show();
            	$("#typePath10_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg10_add").attr('src', url);
            	$("#typePath10_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });
	
	
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'descPath_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        x_vals : {
            'index' : -1
        },
        init: {
            'FilesAdded': function(up, files) {
            	$("#descPath_add_upload_btn").hide();
            	$("#descImg_add").hide();
            	var $status = $("#descPath_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#descPath_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);
            },
            'UploadComplete': function() {
            	$("#descPath_add_upload_btn").show();
            	$("#descImg_add").show();
            	$("#descPath_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#descImg_add").attr('src', url);
            	$("#descPath_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            }
        }
    });

  
	
	var uploader = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'typePath_add_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
      	x_vals : {
            'index' : 0
        },
        init: {
            'FilesAdded': function(up, files) {
            	$("#typePath_add_upload_btn").hide();
            	$("#typeImg_add").hide();
            	var $status = $("#typePath_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#typePath_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#typePath_add_upload_btn").show();
            	$("#typeImg_add").show();
            	$("#typePath_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#typeImg_add").attr('src', url);
            	$("#typePath_add").val(url);
            	$("#typeImg1_add").attr("src", "./base/images/bg_empty.png");
            	$("#typeImg2_add").attr("src", "./base/images/bg_empty.png");
            	$("#typeImg3_add").attr("src", "./base/images/bg_empty.png");
            	$("#typeImg4_add").attr("src", "./base/images/bg_empty.png");
            	$("#typeImg5_add").attr("src", "./base/images/bg_empty.png");
            	$("#typeImg6_add").attr("src", "./base/images/bg_empty.png");
            	$("#typeImg7_add").attr("src", "./base/images/bg_empty.png");
            	$("#typeImg8_add").attr("src", "./base/images/bg_empty.png");
            	$("#typeImg9_add").attr("src", "./base/images/bg_empty.png");
            	$("#typeImg10_add").attr("src", "./base/images/bg_empty.png");
            	$("#typePath1_add").val("");
            	$("#typePath2_add").val("");
            	$("#typePath3_add").val("");
            	$("#typePath4_add").val("");
            	$("#typePath5_add").val("");
            	$("#typePath6_add").val("");
            	$("#typePath7_add").val("");
            	$("#typePath8_add").val("");
            	$("#typePath9_add").val("");
            	$("#typePath10_add").val("");
            	$("#typePath1_add_upload_btn").show();
            	$("#typePath2_add_upload_btn").show();
            	$("#typePath3_add_upload_btn").show();
            	$("#typePath4_add_upload_btn").show();
            	$("#typePath5_add_upload_btn").show();
            	$("#typePath6_add_upload_btn").show();
            	$("#typePath7_add_upload_btn").show();
            	$("#typePath8_add_upload_btn").show();
            	$("#typePath9_add_upload_btn").show();
            	$("#typePath10_add_upload_btn").show();
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var index = up.settings.x_vals.index;
            	var key = "";
            	if(index == 0 || index == -1) {
            		var timestamp = Date.parse(new Date());
                	var suffix = /\.[^\.]+/.exec(file.name);
                	key = "world/thumbs/" + timestamp + suffix;
            	} else {
            		var typePath = $("#typePath_add").val();
            		var index1 = typePath.lastIndexOf('/') + 1;
            		var index2 = typePath.lastIndexOf('.');
            		var prefix = typePath.substring(index1, index2);
            		var suffix = typePath.substring(index2);
            		key = "world/thumbs/" + prefix + index + suffix;
            	}
                return key;
            }
        }
    });
	
	</script>
</body>
</html>