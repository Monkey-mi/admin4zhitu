<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贴纸维护</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<style type="text/css">
	.circle-btn {
		border-radius: 50px;
		-webkit-border-radius: 50px;
		-moz-border-radius: 50px;
		width:15px; 
		height:15px;
		border:1px solid #4f4f4f;
		display:inline-block;
		vertical-align:middle;
	}
	
	.window {
		position:fixed;
	}
	.messager-window {
		position:absolute;	
	}
	
	#fancybox-content {
		background:#4f4f4f;
	}
	
</style>
<script type="text/javascript">

var maxId = 0,
	selectedIds = [],
	labelMaxId = 0,
	labelQueryParams = {},
	
	setMaxId = 0,
	setQueryParams = {},
	
	editSetMaxId = 0,
	editSetQueryParams = {},
	
	hideIdColumn = false,
	htmTableTitle = "贴纸列表", //表格标题
	batchEnableTip = "您确定要使已选中的贴纸生效吗？",
	batchDisableTip = "您确定要使已选中的贴纸失效吗？",
	toolbarComponent = '#tb',
	htmTableTitle = "贴纸列表", //表格标题
	loadDataURL = "./admin_ztworld/sticker_querySticker", //数据装载请求地址
	deleteURI = "./admin_ztworld/sticker_deleteStickers?ids=", //删除请求地址
	saveStickerURL = "./admin_ztworld/sticker_saveSticker", // 保存贴纸地址
	updateStickerByIdURL = "./admin_ztworld/sticker_updateSticker", // 更新贴纸地址
	queryStickerByIdURL = "./admin_ztworld/sticker_queryStickerById", // 根据id查询贴纸
	updateValidURL = "./admin_ztworld/sticker_updateStickerValid?ids=",
	
	htmTablePageList = [15,10,20,30,50],
	myPageSize = 6,
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['sticker.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['sticker.maxId'] = maxId;
			}
		}
		$(".sticker-img").fancybox({
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'titleShow':false
		});
	},
	myOnCheck = function(rowIndex, rowData) {
		selectedIds.push(rowData.id);
		updateSortingCount(selectedIds.length);
	},
	myOnUncheck = function(rowIndex, rowData) {
		for(var i = 0; i < selectedIds.length; i++)
			if(rowData.id == selectedIds[i])
				selectedIds.splice(i,1);
		updateSortingCount(selectedIds.length);
	},
	myOnCheckAll = function(rows) {
		selectedIds = [];
		for(var i = 0; i < rows.length; i++)
			selectedIds.push(rows[i]['id']);
		updateSortingCount(selectedIds.length);
	},
	myOnUncheckAll = function(rows) {
		selectedIds = [];
		updateSortingCount(0);
	}
	stickerBgColor = "#4f4f4f";
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'id',align : 'center',width : 60},
		{field : 'stickerThumbPath',title : '缩略图', align : 'center',width : 60, height:60,
			formatter:function(value,row,index) {
				return "<a class='sticker-img' target='_blank' rel='sticker-group' title='点击放大' href="+row.stickerPath+"><img width='50px' height='50px' alt='' class='htm_column_img' src='" + value + "'/></a>";
			},
			styler: function(value,row,index){
				return 'background-color:' + stickerBgColor;
			}
		},
		{field : 'typeName',title : '分类', align : 'center',width : 60},
		{field : 'setName',title : '系列', align : 'center',width : 60},
		{field : 'stickerDemoPath',title : '示例', align : 'center',width : 60, height:60,
			formatter:function(value,row,index) {
				if(value == '' || value == undefined)
					return '无';
				else
					return "<a class='sticker-img' target='_blank' rel='sticker-demo-group' href="+value+"><img height='60px' alt='' class='htm_column_img' src='" + value + "'/></a>";
			},
			styler: function(value,row,index){
				if(value == '' || value == undefined)
					return '';
				return 'background-color:' + stickerBgColor;
			}
		},
		{field : 'hasLock',title : '加锁',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='有锁' class='htm_column_img'  src='" + img + "'/>";
  				}
  				return "no";
  			}
  		},
  		{field : 'fill',title : '全屏',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='全屏' class='htm_column_img'  src='" + img + "'/>";
  				}
  				return "no";
  			}
  		},
  		{field : 'stickerName',title : '名字',align : 'center',width : 120},
  		{field : 'stickerDesc',title : '描述',align : 'center',width : 220},
  		{field : 'opt',title : '操作',width : 80,align : 'center',rowspan : 1,
			formatter : function(value, row, index ) {
				return "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row.id + "\",\"" + index + "\"," + true + ")'>【修改】</a>";
			}
		},
		{field : 'weight',title : '推荐状态',align : 'center', width : 60,
			formatter: function(value,row,index) {
				img = "./common/images/edit_add.png";
  				title = "点击推荐";
  				if(value > 0) {
  					img = "./common/images/undo.png";
  					title = "已推荐，数字越大越靠前，点击撤销";
  					return "<img title='" + title +  "' class='htm_column_img pointer' onclick='updateWeight(\"" + index + "\",\"" + row[recordIdKey] + "\",\"" + 0 + "\")' src='" + img + "'/>+"+value;
  				}
  				return "<img title='" + title +  "' class='htm_column_img pointer' onclick='updateWeight(\"" + index + "\",\"" + row[recordIdKey] + "\",\"" + 1 + "\")' src='" + img + "'/>";
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
			height : 255,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
		});
		
		$('#htm_edit').window({
			title: '添加贴纸',
			modal : true,
			width : 1040,
			height : 455,
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
				$("#stickerImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#stickerThumbImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#stickerDemoImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#labelId_edit").combogrid('clear');
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		$('#labelId_edit').combogrid({
			panelWidth : 340,
		    panelHeight : 250,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#user_tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'id',
		    url : './admin_ztworld/label_queryLabel',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'ID',align : 'center', width : 60},
				{field : 'labelName',title : '名称',align : 'center', width : 120},
		    ]],
		    queryParams:labelQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxSerial > labelMaxId) {
						labelMaxId = data.maxSerial;
						labelQueryParams.maxSerial = labelMaxId;
					}
				}
		    },
		});
		var p = $('#labelId_edit').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					labelMaxId = 0;
					labelQueryParams.maxId = labelMaxId;
				}
			}
		});
		
		$('#labelId_edit').combogrid({
			panelWidth : 340,
		    panelHeight : 250,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#user_tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'id',
		    url : './admin_ztworld/label_queryLabel',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'ID',align : 'center', width : 60},
				{field : 'labelName',title : '名称',align : 'center', width : 120},
		    ]],
		    queryParams:labelQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxSerial > labelMaxId) {
						labelMaxId = data.maxSerial;
						labelQueryParams.maxSerial = labelMaxId;
					}
				}
		    },
		});
		var p = $('#labelId_edit').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					labelMaxId = 0;
					labelQueryParams.maxId = labelMaxId;
				}
			}
		});
		
		$('#ss-typeId').combobox({
		   	valueField: 'id',
		    textField : 'typeName',
		    url : './admin_ztworld/sticker_queryAllType?addAllTag=true',
		});
		
		$('#htm_refresh').window({
			title : '更新缓存',
			modal : true,
			width : 520,
			height : 155,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-reload',
			resizable : false,
		});
		
		$('#typeIds_refresh').combobox({
			valueField:'id',
			textField:'typeName',
			multiple:'true',
			required:'true',
			url:'./admin_ztworld/sticker_queryAllType?addAllTag=true&type.weight=1',
			onSelect:function(record) {
				if(record.id == 0) {
					$(this).combobox('setValue', 0);
				} else {
					$(this).combobox('unselect', 0);
				}
			}
		});
		
		$('#ss-setId').combogrid({
			panelWidth : 440,
		    panelHeight : 350,
		    loadMsg : '加载中，请稍后...',
			pageList : [10,30,50],
			pageSize : 10,
			toolbar:"#search-set-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'setName',
		    url : './admin_ztworld/sticker_querySet',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'id',align : 'center',width : 80},
				{field : 'setName',title : '系列名称',align : 'center',width : 280}
		    ]],
		    queryParams:setQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > setMaxId) {
						setMaxId = data.maxId;
						setQueryParams['stickerSet.maxId'] = setMaxId;
					}
				}
		    },
		});
		var p = $('#ss-setId').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					setMaxId = 0;
					setQueryParams['stickerSet.maxId'] = setMaxId;
				}
			}
		});
		
		$('#setId_edit').combogrid({
			panelWidth : 440,
		    panelHeight : 350,
		    loadMsg : '加载中，请稍后...',
			pageList : [10,30,50],
			pageSize : 10,
			toolbar:"#edit-search-set-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'setName',
		    url : './admin_ztworld/sticker_querySet',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'id',align : 'center',width : 80},
				{field : 'setName',title : '系列名称',align : 'center',width : 280}
		    ]],
		    queryParams:editSetQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > editSetMaxId) {
						editSetMaxId = data.maxId;
						editSetQueryParams['stickerSet.maxId'] = editSetMaxId;
					}
				}
		    },
		});
		var p2 = $('#setId_edit').combogrid('grid').datagrid('getPager');
		p2.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					editSetMaxId = 0;
					editSetQueryParams['stickerSet.maxId'] = editSetMaxId;
				}
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	
/**
 * 初始化贴纸信息编辑窗口
 */
function initEditWindow(id, index, isUpdate) {
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改贴纸信息');
		$('#htm_edit').window('open');
		$.post(queryStickerByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#stickerPath_edit").val(obj['stickerPath']);
				$("#stickerImg_edit").attr('src', obj['stickerPath']);
				$("#stickerThumbPath_edit").val(obj['stickerThumbPath']);
				$("#stickerThumbImg_edit").attr('src', obj['stickerThumbPath']);
				$("#stickerDemoPath_edit").val(obj['stickerDemoPath']);
				$("#stickerDemoImg_edit").attr('src', obj['stickerDemoPath']);
				$("#typeId_edit").combobox('setValue', obj['typeId']);
				$("#setId_edit").combogrid('setValue', obj['setId']);
				if(obj['hasLock'] == 0) {
					$("#unlock_edit").attr('checked', 'checked');
				} else {
					$("#lock_edit").attr('checked', 'checked');
				}
				
				if(obj['fill'] == 0) {
					$("#unfill_edit").attr('checked', 'checked');
				} else {
					$("#fill_edit").attr('checked', 'checked');
				}
				
				$("#stickerName_edit").val(obj['stickerName']);
				$("#stickerDesc_edit").val(obj['stickerDesc']);
				
				if(obj['labelId'] != 0) {
					$('#labelId_edit').combogrid('setValue',obj['labelId']);
				}
				
				$("#id_edit").val(obj['id']);
				$("#valid_edit").val(obj['valid']);
				$("#weight_edit").val(obj['weight']);
				$("#topWeight_edit").val(obj['topWeight']);
				$("#serial_edit").val(obj['serial']);
				
				$("#edit_loading").hide();
				$("#edit_form").show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$("#id_edit").val(id);
		$("#valid_edit").val(1);
		$("#weight_edit").val(0);
		$("#topWeight_edit").val(0);
		var currTypeId = $('#ss-typeId').combobox('getValue');
		if(currTypeId != '' && currTypeId != 0) {
			$("#typeId_edit").combobox('setValue', currTypeId);
		} else {
			var data = $("#typeId_edit").combobox('getData');
			$("#typeId_edit").combobox('select', data[0]['id']);
		}
		
		var currSetId = $('#ss-setId').combogrid('getValue');
		if(currSetId != '' && currSetId != 0) {
			$("#setId_edit").combogrid('setValue', currSetId);
		} else {
			$("#setId_edit").combogrid('setValue', 1);
		}
		
		$("#lock_edit").attr('checked', 'checked');
		$("#unfill_edit").attr('checked', 'checked');
		$("#un_edit").attr('checked', 'checked');
		$('#htm_edit').panel('setTitle', '添加贴纸');
		$('#htm_edit').window('open');
		$("#edit_loading").hide();
		$("#edit_form").show();
	}
}

//提交表单，以后补充装载验证信息
function loadEditFormValidate(index, isUpdate) {
	var url = saveStickerURL;
	if(isUpdate) {
		url = updateStickerByIdURL;
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
						formSubmitOnce = true;
						$("#edit_form .opt_btn").show();
						$("#edit_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_edit').window('close');  //关闭添加窗口
							
							if(isUpdate) {
								$("#htm_table").datagrid('reload');
							} else {
								maxId = 0;
								myQueryParams['sticker.maxId'] = maxId;
								$("#htm_table").datagrid('load',myQueryParams);
							}
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#stickerPath_edit")
	.formValidator({empty:false, onshow:"请选贴纸（必填）",onfocus:"请选贴纸",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#stickerThumbPath_edit")
	.formValidator({empty:false, onshow:"请选缩略图（必填）",onfocus:"请选缩略图",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#stickerDemoPath_edit")
	.formValidator({empty:true, onshow:"请选示例图（可选）",onfocus:"请选示例图",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	
	$("#typeId_edit")
	.formValidator({empty:false, onshow:"请选分类（必填）",onfocus:"请选分类",oncorrect:"该分类可用！"});
	
	$("#setId_edit")
	.formValidator({empty:false, onshow:"请选系列（必填）",onfocus:"请选系列",oncorrect:"该系列可用！"});
	
	$("#stickerName_edit")
	.formValidator({empty:false, onshow:"请输入名字（可选）",onfocus:"最多15个字符",oncorrect:"正确！"})
	.inputValidator({max:15, onerror : "最多15个字符"});
	
	$("#stickerDesc_edit")
	.formValidator({empty:false, onshow:"请输入描述（可选）",onfocus:"最多140个字符",oncorrect:"正确！"})
	.inputValidator({max:140, onerror : "最多140个字符"});
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
					ids.push(rows[i]['id']);
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
	if(selectedIds.length > 0) {
		for(var i = 0; i < selectedIds.length; i++) {
			$("#serial_form").find('input[name="reIndexId"]').eq(i).val(selectedIds[i]);
			$("#serial_form").form('validate');
		}
	}
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
					myQueryParams['sticker.maxId'] = maxId;
					loadPageData(1);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			}
		});
	}
}

function updateWeight(index, id, isAdd) {
	$("#htm_table").datagrid("loading");
	$.post("./admin_ztworld/sticker_updateStickerWeight",{
				'id':id,
				'weight':isAdd
			},function(result){
				$("#htm_table").datagrid("loaded");
				if(result['result'] == 0){
					var weight = 0;
					if(isAdd == 1) {
						weight = result['weight'];
					}
					updateValue(index,'weight',weight);
				}else{
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			},"json");
}

function updateTopWeight(index, id, isAdd) {
	$("#htm_table").datagrid("loading");
	$.post("./admin_ztworld/sticker_updateStickerTopWeight",{
				'id':id,
				'weight':isAdd
			},function(result){
				$("#htm_table").datagrid("loaded");
				if(result['result'] == 0){
					var weight = 0;
					if(isAdd == 1) {
						weight = result['weight'];
					}
					updateValue(index,'topWeight',weight);
				}else{
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			},"json");
}

function searchSticker() {
	myQueryParams['sticker.stickerName'] = '';
	maxId = 0;
	myQueryParams['sticker.maxId'] = maxId;
	myQueryParams['sticker.weight'] = $('#ss-weight').combobox('getValue');
	myQueryParams['sticker.typeId'] = $('#ss-typeId').combobox('getValue');
	myQueryParams['sticker.setId'] = $('#ss-setId').combogrid('getValue');
	//myQueryParams['sticker.valid'] = $('#ss-valid').combobox('getValue');
	//myQueryParams['sticker.hasLock'] = $('#ss-hasLock').combobox('getValue');
	//myQueryParams['sticker.fill'] = $('#ss-fill').combobox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}

function bgWhite() {
	stickerBgColor = "#ffffff";
	$("#htm_table").datagrid("reload");
	$("#btn-bg-white").hide();
	$("#btn-bg-black").show();
}

function bgBlack() {
	stickerBgColor = "#4f4f4f";
	$("#htm_table").datagrid("reload");
	$("#btn-bg-black").hide();
	$("#btn-bg-white").show();
}

function searchLabel() {
	labelMaxId = 0;
	var labelName = $('#ss_label').searchbox('getValue');
	labelQueryParams = {
		'maxSerial' : labelMaxId,
		'labelName' : labelName
	};
	$("#labelId_edit").combogrid('grid').datagrid("load",labelQueryParams);
}

/**
 * 刷新分类缓存
 */
function refresh() {
	$('#htm_refresh').window('open');
}

function submitRefreshForm() {
	var $form = $("#refresh_form");
	var flag = $form.form('validate');
	if(flag) {
		$("#refresh_form .opt_btn").hide();
		$("#refresh_form .loading").show();
		$.post($form.attr('action'),$form.serialize(),
			function(result){
				$("#refresh_form .opt_btn").show();
				$("#refresh_form .loading").hide();
				if(result['result'] == 0) {
					$('#htm_refresh').window('close');  //关闭添加窗口
					$.messager.alert('提示',result['msg']);  //提示添加信息成功
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			},"json");		
	}
}

function updateSortingCount(count) {
	$("#sorting-count").text(count);
}

function searchSet() {
	setMaxId = 0;
	setQueryParams['stickerSet.maxId'] = setMaxId;
	setQueryParams['stickerSet.setName'] = $('#set-searchbox').searchbox('getValue');
	$("#ss-setId").combogrid('grid').datagrid("load",setQueryParams);
}

function searchEditSet() {
	editSetMaxId = 0;
	editSetQueryParams['stickerSet.maxId'] = editSetMaxId;
	editSetQueryParams['stickerSet.setName'] = $('#edit-set-searchbox').searchbox('getValue');
	$("#setId_edit").combogrid('grid').datagrid("load",editSetQueryParams);
}

function searchStickerByName() {
	maxId = 0;
	myQueryParams['sticker.maxId'] = maxId;
	myQueryParams['sticker.stickerName'] = $('#ss-stickerName').searchbox('getValue');
	myQueryParams['sticker.weight'] = '';
	myQueryParams['sticker.typeId'] = '';
	myQueryParams['sticker.setId'] = '';
	//myQueryParams['sticker.valid'] = '';
	//myQueryParams['sticker.hasLock'] = '';
	//myQueryParams['sticker.fill'] = '';
	$("#htm_table").datagrid("load",myQueryParams);
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" title="添加织图到广场" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效贴纸！" plain="true" iconCls="icon-ok">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效贴纸！" plain="true" iconCls="icon-tip">批量失效</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" 
			title="重排排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序+<span id="sorting-count">0</span></a>
			<a href="javascript:void(0);" onclick="javascript:refresh();" class="easyui-linkbutton" title="刷新缓存" plain="true" iconCls="icon-reload">刷新缓存</a>
			<span class="search_label">分类：</span>
			<input id="ss-typeId" />
			<span class="search_label">系列：</span>
			<input id="ss-setId" />
			<span class="search_label">推荐：</span>
   			<select id="ss-weight" class="easyui-combobox" style="width:100px;">
	   			<option value="">所有推荐状态</option>
	   			<option value="1">推荐</option>
	   			<option value="0">未推荐</option>
   			</select>
   			<!-- 
   			<select id="ss-valid" class="easyui-combobox" style="width:80px;">
	   			<option value="">所有状态</option>
	   			<option value="1">生效</option>
	   			<option value="0">未生效</option>
   			</select>
   			
   			<select id="ss-hasLock" class="easyui-combobox" style="width:100px;">
	   			<option value="">所有锁状态</option>
	   			<option value="1">有锁</option>
	   			<option value="0">无锁</option>
   			</select>
   			<select id="ss-fill" class="easyui-combobox" style="width:100px;">
	   			<option value="">全屏状态</option>
	   			<option value="0">no全屏</option>
	   			<option value="1">全屏</option>
   			</select>
   			 -->
   			<a href="javascript:void(0);" onclick="javascript:searchSticker();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
   			<input id="ss-stickerName" searcher="searchStickerByName" class="easyui-searchbox" prompt="输入名字搜索" />
   			<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
   				<a href="javascript:void(0);" title="白色背景" onclick="javascript:bgWhite();" class="circle-btn" id="btn-bg-white"
	   				style="background:#ffffff;"></a>
	   			<a href="javascript:void(0);" title="黑色背景" onclick="javascript:bgBlack();" class="circle-btn " id="btn-bg-black"
	   				style="background:#4f4f4f; display:none;"></a>
			</div>
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_ztworld/sticker_saveSticker" method="post">
				<table id="htm_edit_table" width="960">
					<tbody>
						<tr>
							<td class="leftTd">贴纸大图：</td>
							<td style="height: 90px; background: #4f4f4f;">
								<input class="none" type="text" name="sticker.stickerPath" id="stickerPath_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
								<a id="stickerPath_edit_upload_btn" style="position: absolute; margin:30px 0 0 100px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
								<img id="stickerImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
								<div id="stickerPath_edit_upload_status" class="update_status none" style="text-align: left; padding-left: 97px;">
									上传中...<span class="upload_progress"></span><span>%</span>
								</div>
							</td>
							<td class="rightTd">
								<div id="stickerPath_editTip" style="display: inline-block;" class="tipDIV"></div>
							</td>
							
							<td class="leftTd">示例图：</td>
							<td style="height: 90px; background: #4f4f4f;">
								<input class="none" type="text" name="sticker.stickerDemoPath" id="stickerDemoPath_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
								<a id="stickerDemoPath_edit_upload_btn" style="position: absolute; margin:30px 0 0 100px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
								<img id="stickerDemoImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
								<div id="stickerDemoPath_edit_upload_status" class="update_status none" style="text-align: left; padding-left: 97px;">
									上传中...<span class="upload_progress"></span><span>%</span>
								</div>
							</td>
							<td class="rightTd">
								<div id="stickerDemoPath_editTip" style="display: inline-block;" class="tipDIV"></div>
							</td>
							
						</tr>
						<tr>
							<td class="leftTd">缩略图：</td>
							<td style="height: 90px; background: #4f4f4f;">
								<input class="none" type="text" name="sticker.stickerThumbPath" id="stickerThumbPath_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
								<a id="stickerThumbPath_edit_upload_btn" style="position: absolute; margin:12px 0 0 70px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
								<img id="stickerThumbImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="50px" height="50px">
								<div id="stickerThumbPath_edit_upload_status" class="update_status none" style="text-align: left; padding-left:70px;">
									上传中...<span class="upload_progress"></span><span>%</span>
								</div>
							</td>
							<td class="rightTd">
								<div id="stickerThumbPath_editTip" style="display: inline-block;" class="tipDIV"></div>
							</td>
							
							<td class="leftTd">贴纸名：</td>
							<td>
								<input type="text" name="sticker.stickerName" id="stickerName_edit"  onchange="validateSubmitOnce=true;"/>
							</td>
							<td class="rightTd">
								<div id="stickerName_editTip" class="tipDIV"></div>
							</td>
							
						</tr>
						
						<tr>
							<td class="leftTd">分类：</td>
							<td>
								<input name="sticker.typeId" id="typeId_edit" class="easyui-combobox" 
									data-options="valueField:'id',textField:'typeName',url:'./admin_ztworld/sticker_queryAllType' "/>
							</td>
							<td class="rightTd">
								<div id="typeId_editTip" style="display: inline-block;" class="tipDIV"></div>
							</td>
							
							<td class="leftTd">描述：</td>
							<td>
								<textarea name="sticker.stickerDesc" id="stickerDesc_edit"  onchange="validateSubmitOnce=true;"></textarea>
							</td>
							<td class="rightTd">
								<div id="stickerDesc_editTip" class="tipDIV"></div>
							</td>
							
							
						</tr>
						
						<tr>
							<td class="leftTd">系列：</td>
							<td>
								<input type="text" name="sticker.setId" id="setId_edit"  onchange="validateSubmitOnce=true;"/>
							</td>
							<td class="rightTd">
								<div id="setId_editTip" class="tipDIV"></div>
							</td>
							<td class="rightTd" colspan="4">
							</td>
							
						</tr>
						<tr>
							<td class="leftTd">锁状态：</td>
							<td>
								<input id="lock_edit" class="radio" type="radio" name="sticker.hasLock" value="1" checked="checked" />加锁
								<input id="unlock_edit" class="radio" type="radio" name="sticker.hasLock" value="0" />无锁
							</td>
							<td class="rightTd">
							</td>
							
							<td class="leftTd">活动：</td>
							<td>
								<input type="text" name="sticker.labelId" id="labelId_edit"  onchange="validateSubmitOnce=true;"/>
							</td>
							<td class="rightTd">
								<div id="labelId_editTip" class="tipDIV"></div>
							</td>
						</tr>
						<tr>
							<td class="leftTd">全屏状态：</td>
							<td>
								<input id="unfill_edit" class="radio" type="radio" name="sticker.fill" checked="checked" value="0" />no全屏
								<input id="fill_edit" class="radio" type="radio" name="sticker.fill" value="1" />全屏
							</td>
							<td class="rightTd" colspan="4">
							</td>
						</tr>
						<tr class="none">
							<td colspan="６"><input type="text" name="sticker.id" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						<tr class="none">
							<td colspan="6"><input type="text" name="sticker.valid" id="valid_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="6"><input type="text" name="sticker.serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="6"><input type="text" name="sticker.weight" id="weight_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="6"><input type="text" name="sticker.topWeight" id="topWeight_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="opt_btn">
							<td colspan="6" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#edit_form').submit();">提交</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_edit').window('close');">取消</a>
							</td>
						</tr>
						<tr class="loading none">
							<td colspan="6" style="text-align: center; padding-top: 10px; vertical-align:middle;">
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
			<form id="serial_form" action="./admin_ztworld/sticker_updateStickerSerial" method="post">
				<table class="htm_edit_table" width="580">
					<tbody>
						<tr>
							<td class="leftTd">贴纸ID：</td>
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
		
		<!-- 更新缓存 -->
		<div id="htm_refresh">
			<form id="refresh_form" action="./admin_ztworld/sticker_updateStickerCache" method="post">
				<table class="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">选择推荐分类：</td>
							<td>
								<input id="typeIds_refresh" name="typeId" style="width:300px;"/>
							</td>
						</tr>
						<tr>
							<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitRefreshForm();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_refresh').window('close');">取消</a>
							</td>
						</tr>
						<tr class="loading none">
							<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
								<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
								<span style="vertical-align:middle;">更新中...</span>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div id="user_tb" style="padding:5px;height:auto" class="none">
			<input id="ss_label" searcher="searchLabel" class="easyui-searchbox" prompt="名称搜索" style="width:200px;"/>
		</div>
		
		<div id="search-set-tb" style="padding:5px;height:auto" class="none">
			<input id="set-searchbox" searcher="searchSet" class="easyui-searchbox" prompt="系列名搜索" style="width:200px;"/>
		</div>
		
		<div id="edit-search-set-tb" style="padding:5px;height:auto" class="none">
			<input id="edit-set-searchbox" searcher="searchEditSet" class="easyui-searchbox" prompt="系列名搜索" style="width:200px;"/>
		</div>
	</div>
	
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
	<script type="text/javascript">
	
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'stickerPath_edit_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        init: {
            'FilesAdded': function(up, files) {
            	$("#stickerPath_edit_upload_btn").hide();
            	$("#stickerImg_edit").hide();
            	var $status = $("#stickerPath_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#stickerPath_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#stickerPath_edit_upload_btn").show();
            	$("#stickerImg_edit").show();
            	$("#stickerPath_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#stickerImg_edit").attr('src', url);
            	$("#stickerPath_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "world/sticker/" + timestamp+suffix;
                return key;
            }
        }
    });
    
    Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'stickerThumbPath_edit_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        init: {
            'FilesAdded': function(up, files) {
            	$("#stickerThumbPath_edit_upload_btn").hide();
            	$("#stickerThumbImg_edit").hide();
            	var $status = $("#stickerThumbPath_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#stickerThumbPath_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#stickerThumbPath_edit_upload_btn").show();
            	$("#stickerThumbImg_edit").show();
            	$("#stickerThumbPath_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#stickerThumbImg_edit").attr('src', url);
            	$("#stickerThumbPath_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "world/sticker/" + timestamp+suffix;
                return key;
            }
        }
    });
    
    Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'stickerDemoPath_edit_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://static.imzhitu.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        init: {
            'FilesAdded': function(up, files) {
            	$("#stickerDemoPath_edit_upload_btn").hide();
            	$("#stickerDemoImg_edit").hide();
            	var $status = $("#stickerDemoPath_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#stickerDemoPath_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#stickerDemoPath_edit_upload_btn").show();
            	$("#stickerDemoImg_edit").show();
            	$("#stickerDemoPath_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#stickerDemoImg_edit").attr('src', url);
            	$("#stickerDemoPath_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "world/sticker/" + timestamp+suffix;
                return key;
            }
        }
    });
	
	</script>
</body>
</html>