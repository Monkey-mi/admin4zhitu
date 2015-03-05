<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
var maxSerial = 0,
	hideIdColumn = false,
	recordIdKey = "phoneCode",
	toolbarComponent = '#tb',
	init = function() {
		loadPageData(initPage);
	},
	myOnLoadBefore = function() {
		interacts = {};
	},
	htmTableTitle = "公告列表", //表格标题
	htmTableWidth = 750,
	loadDataURL = "./admin_op/msg_queryNotice", //数据装载请求地址
	deleteURI = "./admin_op/msg_deleteNotice?ids=", //删除请求地址
	queryNoticeByIdURL = "./admin_op/msg_queryNoticeByPhoneCode",  // 根据id查询活动
	updateNoticeURL = "./admin_op/msg_updateNotice", // 更新活动
	saveNoticeURL = "./admin_op/msg_saveNotice",
	addWidth = 720, //添加信息宽度
	addHeight = 240, //添加信息高度
	addTitle = "添加公告", //添加信息标题
	hideIdColumn = false,
	
	columnsFields = [
  		{field : 'ck',checkbox : true},
  		{field : 'phoneCode',title : '客户端',align : 'center',width : 50,
  			formatter: function(value,row,index){
  				var phone = "IOS";
  				if(value == 1) {
  					phone = '安卓';
  				}
  				return phone;
  			}
  		},
  		{field : 'id',title : 'ID', align : 'center',width : 50},
  		{field : 'path',title : '图片',align : 'left',width : 320, 
			formatter: function(value, row, index) {
				return "<img width='320px' height='54px' src='" + value + "'/>";;
			}
		},
		{field : 'link',title : '链接', align : 'center',width : 180},
  		{field : 'opt',title : '操作',align : 'center', width: 60,
  			formatter: function(value,row,index){
  				return "<a title='更新公告' class='updateInfo' href='javascript:void(0)' onclick='javascript:initAddWindow(\""+ row.phoneCode + "\",\"" + index + "\"," + true + ")'>更新</a>";
  			}
  		},
    ],
    
    onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		
		removePageLoading();
		$("#main").show();
	};


/**
 * 添加活动标签
 */
function initAddWindow(phoneCode, index, isUpdate) {
	var $form = $("#add_form");
	clearFormData($form);
	$("#phoneCode_add").combobox('setValue', 0);
	$("#pathImg_add").attr("src", "./base/images/bg_empty.png");
	loadaddFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_add').panel('setTitle', '修改公告');
		$('#htm_add').window('open');
		$.post(queryNoticeByIdURL,{
			"phoneCode":phoneCode
		}, function(result){
			if(result['result'] == 0) {
				var verify = result['msg'];
				$("#path_add").val(verify['path']);
				$("#pathImg_add").attr('src', verify['path']);
				$("#phoneCode_add").combobox('setValue', verify['phoneCode']);
				$("#link_add").val(verify['link']);
				$("#id_add").val(verify['id']);
				$("#add_loading").hide();
				$form.show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$('#htm_add').panel('setTitle', '添加公告');
		$('#htm_add').window('open');
		$("#add_loading").hide();
		$form.show();
	}
}

//提交表单，以后补充装载验证信息
function loadaddFormValidate(index, isUpdate) {
	var url = saveNoticeURL;
	if(isUpdate) {
		url = updateNoticeURL;
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
	
	
	$("#link_add")
	.formValidator({empty:true,onshow:"链接（可选）",onfocus:"例如:http://imzhitu.com",oncorrect:"设置成功"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initAddWindow(0,0,false);" class="easyui-linkbutton" title="添加公告" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除公告" plain="true" iconCls="icon-cut">删除</a>
   		</div>
	</div> 

	<!-- 添加或修改认证信息-->
	<div id="htm_add">
		<span id="add_loading" style="margin:60px 0 0 330px; position:absolute;">加载中...</span>
		<form id="add_form" action="./admin_op/msg_saveNotice" method="post" class="none">
			<table class="htm_edit_table" width="680" class="none">
				<tbody>
					<tr>
						<td class="leftTd">图片：</td>
						<td style="height: 40px;">
							<input class="none" type="text" name="path" id="path_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="path_add_upload_btn" style="position: absolute; margin:13px 0 0 100px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="pathImg_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="300px" height="54px">
							<div id="path_add_upload_status" class="update_status none" style="width: 100px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="path_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">链接：</td>
						<td><input type="text" name="link" id="link_add" onchange="validateSubmitOnce=true;" /></td>
						<td class="rightTd">
							<div id="link_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">客户端：</td>
						<td>
							<select id="phoneCode_add" class="easyui-combobox" name="phoneCode" style="width:75px;">
						        <option value="0">IOS</option>
						        <option value="1">安卓</option>
					   		</select>
						</td>	
						<td class="rightTd"><div id="phoneCode_addTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none">
						<td class="leftTd">ID：</td>
						<td><input type="text" name="id" id="id_add" onchange="validateSubmitOnce=true;" /></td>
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
        browse_button: 'path_add_upload_btn',
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
            	$("#path_add_upload_btn").hide();
            	$("#pathImg_add").hide();
            	var $status = $("#path_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#path_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#path_add_upload_btn").show();
            	$("#pathImg_add").show();
            	$("#path_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://imzhitu.qiniudn.com/'+$.parseJSON(info).key;
            	$("#pathImg_add").attr('src', url);
            	$("#path_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
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