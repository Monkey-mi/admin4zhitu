<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>App链接管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">
var maxId = 0,
	selectedIds = [],
	hideIdColumn = false,
	toolbarComponent = '#tb',
	init = function() {
		myQueryParams = {
			'link.phoneCode' : 0
		},
		loadPageData(initPage);
	},
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['link.maxId'] = maxId;
		}
	},
	
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['link.maxId'] = maxId;
			}
		}
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
	htmTableTitle = "App链接维护", //表格标题
	recordIdKey = "id",
	loadDataURL = "./admin_op/ad_queryAppLink", //数据装载请求地址
	deleteURI = "./admin_op/ad_deleteAppLinks?ids=", //删除请求地址
	queryByIdURL = "./admin_op/ad_queryAppLinkById",
	updateByIdURL = "./admin_op/ad_updateAppLink",
	saveURL = "./admin_op/ad_saveAppLink",
	updateValidURL = "./admin_op/ad_updateAppLinkValid?ids=",
	
	batchEnableTip = "您确定要使已选中的链接生效吗？",
	batchDisableTip = "您确定要使已选中的链接失效吗？",
	
	phoneCode = [{
        "id":0,
        "text":"IOS",
        "selected":true
    },{
        "id":1,
        "text":"安卓"
    }],
	
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'ID',align : 'center', width : 60},
  		{ field : 'appIcon',title : '图标', align : 'center',width : 40,
			formatter:function(value,row,index) {
				return "<img width='30px' height='30px' alt='' class='htm_column_img'  src='" + value + "'/>";
			}
		},
		{ field : 'appIconL',title : 'Banner', align : 'center',width : 40,
			formatter:function(value,row,index) {
				return "<img width='58px' height='30px' alt='' class='htm_column_img'  src='" + value + "'/>";
			}
		},
		
		{ field : 'phoneCode',title :'客户端',align : 'center',width : 60, 
			formatter: function(value,row,index){
				var phone = "IOS";
				if(value == 1) {
					phone = '安卓';
				}
				return phone;
			}
		},
		{ field : 'appName',title : '名称',align : 'center', width : 60},
		{ field : 'appDesc',title : '描述',align : 'center', width : 180, 
  			formatter : function(value, row, rowIndex ) {
				return "<span title='" + value + "'>"+value+"</span>";
			}	
		},
		{ field : 'appLink',title : '链接',align : 'center', width : 180,
			
			formatter : function(value, row, index ) {
				return "<a title='"+value+"' class='updateInfo' target='_blank' href='"+value+"'>"+value+"</a>";
			}
		
		},
		{ field : 'clickCount',title : '播放次数',align : 'center', width : 60,
			formatter : function(value, row, rowIndex ) {
				var uri = 'page_operations_appLinkRecord?appId='+ row['id']; //评论管理地址			
				return "<a title='显示播放次数' class='updateInfo' href='javascript:showRecord(\""
						+ uri + "\")'>"+value+"</a>";
			}
		},
		{field : 'open',title : '有效性',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
		{field : 'opt',title : '操作',width : 80,align : 'center',rowspan : 1,
			formatter : function(value, row, index ) {
				return "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row.id + "\",\"" + index + "\"," + true + ")'>【修改】</a>";
			}
		},
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
			title: '添加链接',
			modal : true,
			width : 620,
			height : 485,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				var $form = $('#edit_form');
				commonTools.clearFormData($form);
				$("#edit_form .opt_btn").show();
				$("#edit_form .loading").hide();
				$("#appIconImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#appIconLImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#phoneCode_edit").combobox('clear');
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	
	
/**
 * 显示点击记录
 * @param uri
 */
function showRecord(uri) {
	$.fancybox({
		'margin'			: 20,
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}
	
/**
 * 初始化
 */
function initSerialWindow() {
	clearSerialForm();
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

/**
 * 清空索引排序
 */
function clearSerialForm() {
	$("#serial_form").find('input[name="reIndexId"]').val('');
	selectedIds = [];
	updateSortingCount(0);
}

function updateSortingCount(count) {
	$("#sorting-count").text(count);
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
					clearSerialForm();
					maxId = 0;
					myQueryParams['link.maxId'] = maxId;
					loadPageData(1);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			}
		});
	}
}


/**
 * 初始化贴纸信息编辑窗口
 */
function initEditWindow(id, index, isUpdate) {
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改链接');
		$('#htm_edit').window('open');
		$.post(queryByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#appIcon_edit").val(obj['appIcon']);
				$("#appIconImg_edit").attr('src', obj['appIcon']);
				$("#appIconL_edit").val(obj['appIconL']);
				$("#appIconLImg_edit").attr('src', obj['appIconL']);
				
				$("#appName_edit").val(obj['appName']);
				$("#appDesc_edit").val(obj['appDesc']);
				$("#appLink_edit").val(obj['appLink']);
				$("#shortLink_edit").val(obj['shortLink']);
				$('#phoneCode_edit').combobox('setValue',obj['phoneCode']);
				
				$("#id_edit").val(obj['id']);
				$("#open_edit").val(obj['open']);
				$("#serial_edit").val(obj['serial']);
				
				$("#edit_loading").hide();
				$("#edit_form").show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$("#id_edit").val(0);
		$("#open_edit").val(1);
		$("#serial_edit").val(0);
		
		$('#htm_edit').panel('setTitle', '添加链接');
		$('#htm_edit').window('open');
		$("#edit_loading").hide();
		$("#edit_form").show();
	}
}

//提交表单，以后补充装载验证信息
function loadEditFormValidate(index, isUpdate) {
	var url = saveURL;
	if(isUpdate) {
		url = updateByIdURL;
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
								myQueryParams['link.maxId'] = maxId;
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
	
	$("#appIcon_edit")
	.formValidator({empty:false, onshow:"300x300(jpg)",onfocus:"请选图标",oncorrect:"正确！"});
	
	$("#appIconL_edit")
	.formValidator({empty:false, onshow:"375x192(jpg)",onfocus:"请选Banner",oncorrect:"正确！"});
	
	$("#appName_edit")
	.formValidator({empty:false, onshow:"请输入名字（必填）",onfocus:"最多15个字符",oncorrect:"正确！"})
	.inputValidator({max:30, onerror : "最多30个字符"});
	
	$("#appDesc_edit")
	.formValidator({empty:false, onshow:"请输入描述（可选）",onfocus:"最多140个字符",oncorrect:"正确！"})
	.inputValidator({max:140, onerror : "最多140个字符"});
	
	$("#appLink_edit")
	.formValidator({empty:false, onshow:"请输入App链接（必填）",onfocus:"最多140个字符",oncorrect:"正确！"});
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

function searchAppLink() {
	maxId = 0;
	myQueryParams['link.maxId'] = maxId;
	myQueryParams['link.phoneCode'] = $('#ss-phoneCode').combobox('getValue');
	myQueryParams['link.open'] = $('#ss-open').combogrid('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" title="添加链接" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete('id');" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效" plain="true" iconCls="icon-ok">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效" plain="true" iconCls="icon-tip">批量失效</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重新排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序+<span id="sorting-count">0</span></a>
			<select id="ss-phoneCode" class="easyui-combobox" style="width:100px;">
	   			<option value="">所有客户端</option>
	   			<option value="0" selected="selected">IOS</option>
	   			<option value="1">安卓</option>
   			</select>
   			<select id="ss-open" class="easyui-combobox" style="width:100px;">
	   			<option value="">所有生效状态</option>
	   			<option value="1">已生效</option>
	   			<option value="0">未生效</option>
   			</select>
   			<a href="javascript:void(0);" onclick="javascript:searchAppLink();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
   			
   		</div>
	</div> 

	<!-- 添加记录 -->
	<div id="htm_edit">
		<form id="edit_form" action="./admin_op/ad_saveAppLink" method="post">
			<table id="htm_edit_table" width="580">
				<tbody>
					
					<tr>
						<td class="leftTd">图标：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="link.appIcon" id="appIcon_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="appIcon_edit_upload_btn" style="position: absolute; margin:30px 0 0 100px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="appIconImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="appIconPath_edit_upload_status" class="update_status none" style="text-align: left; padding-left: 97px;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="appIcon_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					
					<tr>
						<td class="leftTd">Banner：</td>
						<td style="height: 90px;">
							<input class="none" type="text" name="link.appIconL" id="appIconL_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="appIconL_edit_upload_btn" style="position: absolute; margin:30px 0 0 50px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="appIconLImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="175px" height="90px">
							<div id="appIconL_edit_upload_status" class="update_status none" style="text-align: left; padding-left: 97px;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="appIconL_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
				
					<tr>
						<td class="leftTd">名称：</td>
						<td><input type="text" name="link.appName" id="appName_edit" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="appName_editTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td><textarea name="link.appDesc" id="appDesc_edit" onchange="validateSubmitOnce=true;" style="width:300px;"></textarea></td>
						<td class="rightTd"><div id="appDesc_editTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">链接：</td>
						<td><textarea name="link.appLink" id="appLink_edit" onchange="validateSubmitOnce=true;" style="width:300px;"></textarea></td>
						<td class="rightTd"><div id="appLink_editTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">客户端：</td>
						<td colspan="2">
							<select id="phoneCode_edit" class="easyui-combobox" name="link.phoneCode" onchange="validateSubmitOnce=true;" style="width:205px;" >
					        <option value="0" selected>IOS</option>
					        <option value="1">安卓</option>
					        </select>
						</td>
					</tr>
					
					<tr class="none">
						<td colspan="3"><input type="text" name="link.id" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					<tr class="none">
						<td colspan="3"><input type="text" name="link.open" id="open_edit" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					
					<tr class="none">
						<td colspan="3"><input type="text" name="link.serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#edit_form').submit();">确定</a>
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
		<form id="serial_form" action="./admin_op/ad_updateAppLinkSerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">AppID：</td>
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
							<a class="easyui-linkbutton" iconCls="icon-redo" onclick="clearSerialForm();">清空</a>
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
        browse_button: 'appIcon_edit_upload_btn',
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
            	$("#appIcon_edit_upload_btn").hide();
            	$("#appIconImg_edit").hide();
            	var $status = $("#appIcon_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#appIcon_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#appIcon_edit_upload_btn").show();
            	$("#appIconImg_edit").show();
            	$("#appIcon_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#appIconImg_edit").attr('src', url);
            	$("#appIcon_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/applink/" + timestamp+suffix;
                return key;
            }
        }
    });
    
    Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'appIconL_edit_upload_btn',
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
            	$("#appIconL_edit_upload_btn").hide();
            	$("#appIconLImg_edit").hide();
            	var $status = $("#appIconL_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#appIconL_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#appIconL_edit_upload_btn").show();
            	$("#appIconLImg_edit").show();
            	$("#appIconL_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#appIconLImg_edit").attr('src', url);
            	$("#appIconL_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/applink/" + timestamp+suffix;
                return key;
            }
        }
    });
    </script>
	
</body>
</html>