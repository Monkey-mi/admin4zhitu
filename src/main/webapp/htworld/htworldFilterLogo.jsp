<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String worldId = request.getParameter("worldId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>滤镜logo</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
var htmTableTitle = "滤镜logo", //表格标题
	htmTableWidth = 580,
	init = function() {
		toolbarComponent = '#tb';
		loadPageData(initPage);
	},
	
	
	loadDataURL = "./admin_ztworld/ztworld_queryFilterLogoList", //数据装载请求地址
	queryLogoByIdURL = "./admin_ztworld/ztworld_queryFilterLogo",
	columnsFields = [
    	{field : 'ver',title : '版本号', align : 'center',width : 60},
     	{field : 'logoPath',title : '图标',align : 'left',width : 45,
     		formatter: function(value, row, index) {
     			return "<img class='htm_column_img' src='" + value + "'/>";
     		}		
     	},
     	{field : 'logoDesc', title : '描述', align : 'center',width : 120},
    ],
    addWidth = 520, //添加信息宽度
	addHeight = 280, //添加信息高度
	addTitle = "添加认证", //添加信息标题
	htmTablePageList = [1],
	hideIdColumn = false,
	
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		
		removePageLoading();
		$("#main").show();
	};

	
/**
 * 初始化添加窗口
 */
function initAddWindow() {
	var $form = $("#add_form");
	clearFormData($form);
	$("#valid_add").combobox('setValue', 0);
	$("#ver_add").focus();  //光标定位
	$.post(queryLogoByIdURL,{
	}, function(result){
		if(result['result'] == 0) {
			var logo = result['logo'];
			if(logo != '' && logo != undefined) {
				$("#logoPath_add").val(logo['logoPath']);
				$("#logoImg_add").attr('src', logo['logoPath']);
				$("#logoDesc_add").val(logo['logoDesc']);
				$("#valid_add").combobox('setValue',logo['valid']);
				$("#ver_add").val(logo['ver']);
				$("#add_loading").hide();
			}
			$form.show();
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
		
	},"json");
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
				$('#htm_add .opt_btn').hide();
				$('#htm_add .loading').show();
				//验证成功后以异步方式提交表单
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						formSubmitOnce = true;
						$('#htm_add .opt_btn').show();
						$('#htm_add .loading').hide();
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#ver_add")
	.formValidator({empty:false,onshow:"请输入 版本号",onfocus:"设置版本号",oncorrect:"设置成功"});
	
	$("#logoDesc_add")
	.formValidator({empty:false,onshow:"请输入文字描述",onfocus:"设置文字描述",oncorrect:"设置成功"});
	
	$("#logoPath_add")
	.formValidator({empty:false, onshow:"请上传logo",onfocus:"例:http://test.png",oncorrect:"该链接可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
}

	
</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="修改logo" plain="true" iconCls="icon-add" id="addBtn">修改</a>
   		</div>
	</div> 
	
	<div id="htm_add">
		<span id="add_loading" style="margin:60px 0 0 230px; position:absolute;">加载中...</span>
		<form id="add_form" action="./admin_ztworld/ztworld_updateFilterLogo" method="post" class="none">
			<table class="htm_edit_table" width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">版本号：</td>
						<td><input type="text" name="ver" id="ver_add" onchange="validateSubmitOnce=true;" /></td>
						<td class="rightTd"><div id="ver_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td><textarea name="logoDesc" id="logoDesc_add" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="logoDesc_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">图标：</td>
						<td style="height: 40px;">
							<input class="none" type="text" name="logoPath" id="logoPath_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="logoPath_add_upload_btn" style="position: absolute; margin:8px 0 0 45px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="logoImg_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="40px" height="40px">
							<div id="logoPath_add_upload_status" class="update_status none" style="width: 100px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="logoPath_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">图标：</td>
						<td style="height: 40px;">
							<select id="valid_add" class="easyui-combobox" name="valid" style="width:75px;">
						        <option value="0">无效</option>
						        <option value="1">有效</option>
					   		</select>
						</td>
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
        browse_button: 'logoPath_add_upload_btn',
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
            	$("#logoPath_add_upload_btn").hide();
            	$("#logoImg_add").hide();
            	var $status = $("#logoPath_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#logoPath_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#logoPath_add_upload_btn").show();
            	$("#logoImg_add").show();
            	$("#logoPath_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://imzhitu.qiniudn.com/'+$.parseJSON(info).key;
            	$("#logoImg_add").attr('src', url);
            	$("#logoPath_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "world/filter/" + timestamp+suffix;
                return key;
            }
        }
    });
	
	</script>
</body>
</html>