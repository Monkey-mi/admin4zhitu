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
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer}"></script>
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
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxSerial > maxSerial) {
				maxSerial = data.maxSerial;
				myQueryParams.maxSerial = maxSerial;
			}
		}
	},
	htmTableTitle = "认证列表", //表格标题
	loadDataURL = "./admin_user/verify_queryVerify", //数据装载请求地址
	deleteURI = "./admin_user/verify_deleteVerify?ids=", //删除请求地址
	updateSerialRL = "./admin_user/verify_updateVerifySerial?ids=", // 更新有效新请求地址
	queryVerifyByIdURL = "./admin_user/verify_queryVerifyById",  // 根据id查询活动
	updateVerifyURL = "./admin_user/verify_updateVerify", // 更新活动
	saveVerifyURL = "./admin_user/verify_saveVerify",
	addWidth = 520, //添加信息宽度
	addHeight = 250, //添加信息高度
	addTitle = "添加认证", //添加信息标题
	hideIdColumn = false,
	
	columnsFields = [
  		{field : 'ck',checkbox : true},
  		{field : 'id',title : 'ID', align : 'center',width : 80, sortable: true},
  		{field : 'verifyIcon',title : '图标',align : 'left',width : 45,
  			formatter: function(value, row, index) {
  				return "<img title='" + row['verifyName'] + "' class='htm_column_img' src='" + value + "'/>";
  			}		
  		},
  		{field : 'verifyName', title : '名称', align : 'center',width : 120},
  		{field : 'verifyDesc', title : '描述', align : 'center',width : 120},
  		{field : 'serial', title : '序号', align : 'center',width : 60},
  		{field : 'opt',title : '操作',align : 'center', width: 60,
  			formatter: function(value,row,index){
  				return "<a title='更新认证信息' class='updateInfo' href='javascript:void(0)' onclick='javascript:initAddWindow(\""+ row.id + "\",\"" + index + "\"," + true + ")'>更新</a>"
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
			title : '更新认证列表缓存',
			modal : true,
			width : 520,
			height : 135,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-reload',
			resizable : false
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
	$("#verifyLimit_hot").val(4);
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
	
	$("#verifyLimit_hot")
	.formValidator({empty:false,onshow:"输入认证数量",onfocus:"例如:4",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
}


/**
 * 添加活动标签
 */
function initAddWindow(id, index, isUpdate) {
	var $form = $("#add_form");
	clearFormData($form);
	$("#verifyName_add").focus();  //光标定位
	loadaddFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_add').panel('setTitle', '修改认证信息');
		$('#htm_add').window('open');
		$.post(queryVerifyByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var verify = result['verify'];
				$("#verifyIcon_add").val(verify['verifyIcon']);
				$("#verifyImg_add").attr('src', verify['verifyIcon']);
				
				$("#verifyName_add").val(verify['verifyName']);
				$("#verifyDesc_add").val(verify['verifyDesc']);
				
				$("#serial_add").val(verify['serial']);
				$("#id_add").val(verify['id']);
				$("#add_loading").hide();
				$form.show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$('#htm_add').panel('setTitle', '添加认证信息');
		$('#htm_add').window('open');
		$("#add_loading").hide();
		$form.show();
	}
}

//提交表单，以后补充装载验证信息
function loadaddFormValidate(index, isUpdate) {
	var url = saveVerifyURL;
	if(isUpdate) {
		url = updateVerifyURL;
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
								maxSerial = 0;
								myQueryParams.maxSerial = maxSerial;
								loadPageData(1);
								$.messager.alert('提示',result['msg']);  //提示添加信息成功
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
	
	$("#verifyName_add")
	.formValidator({empty:false,onshow:"认证名称",onfocus:"例如:摄影达人",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由文字和数字组成"});
	
	$("#verifyDesc_add")
	.formValidator({empty:true,onshow:"认证描述（可选）",onfocus:"例如:摄影达人",oncorrect:"设置成功"});
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initAddWindow(0,0,false);" class="easyui-linkbutton" title="添加认证" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除认证" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter">重新排序</a>
			<a href="javascript:void(0);" onclick="javascript:updateHot();" class="easyui-linkbutton" title="刷新认证列表缓存" plain="true" iconCls="icon-reload">刷新认证列表</a>
   		</div>
	</div> 

	<!-- 排序窗口 -->
	<div id="htm_serial">
		<form id="serial_form" action="./admin_user/verify_updateVerifySerial" method="post">
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
		<form id="hot_form" action="./admin_user/verify_updateVerifyCache" method="post">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">认证数：</td>
						<td><input type="text" name="limit" id="verifyLimit_hot" style="width:205px;"/></td>
						<td class="rightTd"><div id="verifyLimit_hotTip" class="tipDIV"></div></td>
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
	
	<!-- 添加或修改认证信息-->
	<div id="htm_add">
		<span id="add_loading" style="margin:60px 0 0 230px; position:absolute;">加载中...</span>
		<form id="add_form" action="./admin_user/verify_saveVerify" method="post" class="none">
			<table class="htm_edit_table" width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">认证名称：</td>
						<td><input type="text" name="verifyName" id="verifyName_add" onchange="validateSubmitOnce=true;" /></td>
						<td class="rightTd"><div id="verifyName_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">认证描述：</td>
						<td><textarea name="verifyDesc" id="verifyDesc_add" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="verifyDesc_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">ICON：</td>
						<td style="height: 40px;">
							<input class="none" type="text" name="verifyIcon" id="verifyIcon_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="verifyIcon_add_upload_btn" style="position: absolute; margin:8px 0 0 45px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="verifyImg_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="40px" height="40px">
							<div id="verifyIcon_add_upload_status" class="update_status none" style="width: 100px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="verifyIcon_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr class="none">
						<td class="leftTd">序号：</td>
						<td><input type="text" name="id" id="id_add" onchange="validateSubmitOnce=true;" /></td>
					</tr>
					<tr class="none">
						<td class="leftTd">序号：</td>
						<td><input type="text" name="serial" id="serial_add" onchange="validateSubmitOnce=true;" /></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="6" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">确定</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
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
	
	</div>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
	<script type="text/javascript">
	
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'verifyIcon_add_upload_btn',
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
            	$("#verifyIcon_add_upload_btn").hide();
            	$("#verifyImg_add").hide();
            	var $status = $("#verifyIcon_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#verifyIcon_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#verifyIcon_add_upload_btn").show();
            	$("#verifyImg_add").show();
            	$("#verifyIcon_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#verifyImg_add").attr('src', url);
            	$("#verifyIcon_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "user/verify/" + timestamp+suffix;
                return key;
            }
        }
    });
	
	</script>
</body>
</html>