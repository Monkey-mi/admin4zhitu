<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附近标签管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
	var maxId = 0,
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'nearLabel.maxId' : maxId
		}
		loadPageData(initPage);
	},
	htmTableTitle = "附近标签列表", //表格标题
	loadDataURL = "./admin_op/near_queryNearLabel",
	deleteURI = "./admin_op/near_deleteNeaerLabels?ids=", //删除请求地址
	saveURL = "./admin_op/near_saveNearLabel", // 保存贴纸地址
	updateByIdURL = "./admin_op/near_updateNearLabel", // 更新贴纸地址
	queryByIdURL = "./admin_op/near_queryNearLabelById",
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['nearLabel.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['nearLabel.maxId'] = maxId;
			}
		}
	},
	
	columnsFields = [
    	{field: "ck",checkbox: true},
    	{field: "id",title: "id",align: "center"},
    	{field: "cityId",title: "城市id",align: "center"},
        {field: "cityName",title: "城市名称",align: "center"},
        {field: "labelName",title: "标签名称",align: "center"},
        /*
        {field: "longitude",title: "经度",align: "center"},
        {field: "latitude",title: "纬度",align: "center"},
        */
        {field: "description",title: "标签描述",align: "center"},
        {field: "bannerUrl",title: "图标",align: "center",
           	formatter: function(value,row,index) {
	  				return "<img title='无效' width='174px' height='90px' class='htm_column_img' src='" + value + "'/>";
	  		}
	 	},
        {field: "serial",title: "序列号",align: "center"},
        {field : 'opt',title : '操作',align : 'center',rowspan : 1,
			formatter : function(value, row, index ) {
				return "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row.id + "\",\"" + index + "\"," + true + ")'>【修改】</a>";
			}
		},
          
    ],
    
    htmTablePageList = [15,10,20,30,50],
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
			title : '添加标签',
			modal : true,
			width : 650,
			height : 345,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false
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
		$('#htm_edit').panel('setTitle', '修改标签');
		$('#htm_edit').window('open');
		$.post(queryByIdURL,{
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
				
				if(obj['labelId'] != 0) {
					$('#labelId_edit').combogrid('setValue',obj['labelId']);
				}
				
				$("#id_edit").val(obj['id']);
				$("#serial_edit").val(obj['serial']);
				
				$("#edit_loading").hide();
				$("#edit_form").show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$("#id_edit").val(id);
		$("#serial_edit").val(0);
		
		//var currSetId = $('#ss-setId').combogrid('getValue');
		
		$('#htm_edit').panel('setTitle', '添加标签');
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
								myQueryParams['nearLabel.maxId'] = maxId;
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
	
	$("#bannerUrl_edit")
	.formValidator({empty:false, onshow:"请选banner（必填）",onfocus:"请选banner",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	
	$("#cityId_edit")
	.formValidator({empty:false, onshow:"请选城市（必填）",onfocus:"请选城市",oncorrect:"正确！"});
	
	$("#labelName_edit")
	.formValidator({empty:false, onshow:"请输入名字（必填）",onfocus:"最多30个字符",oncorrect:"正确！"})
	.inputValidator({max:30, onerror : "最多15个字符"});
	
	$("#description_edit")
	.formValidator({empty:true, onshow:"请输入描述（可选）",onfocus:"最多500个字符",oncorrect:"正确！"})
	.inputValidator({max:500, onerror : "最多500个字符"});
}

/**
 * 新增附近标签
 * @author zhangbo 2015-12-04
 */
function addnearLabel() {
	var $form = $('#add_nearLabel_form');
	if($form.form('validate')) {
		$('#add_nearLabel_form .opt_btn').hide();
		$('#add_nearLabel_form .loading').show();
		$form.form('submit', {
			url: $form.attr("action"),
			success: function(data){
				var result = $.parseJSON(data);
				$('#add_nearLabel_form .opt_btn').show();
				$('#add_nearLabel_form .loading').hide();
				if(result['result'] == 0) {
					// 关闭添加窗口，刷新页面 
					$('#add_nearLabel_window').window('close');
					$("#htm_table").datagrid("load");
				} else {
					$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
				}
				
			}
		});
	}
};
	
</script>
</head>
<body>
	<div id="main" style="display: none;">
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<span>
				<a href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
			</span>
		</div>
		
		<!-- 添加城市分组窗口 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_op/near_insertNearLabel" method="post">
				<table class="htm_edit_table" width="580">
					<tr>
						<td class="leftTd">大Banner：</td>
						<td>
							<input id="bannerUrl_edit" name="nearLabel.bannerUrl" class="none" readonly="readonly" >
							<a id="bannerUrl_edit_upload_btn" style="position: absolute; margin:30px 0 0 200px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="nbannerImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="174px" height="90px">
							<div id="bannerUrl_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td>
							<div id="bannerUrl_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">城市：</td>
						<td>
							<input type="text" id="cityId_edit" name="nearLabel.cityId"  onchange="validateSubmitOnce=true;"/>
						</td>
						<td class="rightTd">
							<div id="cityId_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">标签名称：</td>
						<td>
							<textarea id="labelName_edit" type="text" name="nearLabel.labelName" style="width:300px;"  onchange="validateSubmitOnce=true;"></textarea>
						</td>
						<td class="rightTd">
							<div id="labelName_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td>
							<textarea id="description_edit" type="text" name="nearLabel.description" style="width:300px;"  onchange="validateSubmitOnce=true;"></textarea>
						</td>
						<td class="rightTd">
							<div id="description_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					
					<tr class="none">
						<td colspan="3"><input type="text" name="nearLabel.id" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					
					<tr class="none">
						<td colspan="3"><input type="text" name="nearLabel.serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#edit_form').submit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_edit').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">加载中...</span>
						</td>
					</tr>
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
							<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitSerialForm();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_serial').window('close');">取消</a>
							</td>
						</tr>
						<tr class="loading none">
							<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
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
        browse_button: 'bannerUrl_edit_upload_btn',
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
            	$("#bannerUrl_edit_upload_btn").hide();
            	$("#bannerImg_edit").hide();
            	var $status = $("#bannerUrl_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#bannerUrl_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#bannerUrl_edit_upload_btn").show();
            	$("#bannerImg_edit_edit").show();
            	$("#bannerUrl_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#bannerImg_edit").attr('src', url);
            	$("#bannerUrl_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  // 提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/notice/" + timestamp+suffix;
                return key;
            }
        }
    });
	</script>
</body>
</html>
</html>