<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String activityId = request.getParameter("activityId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动奖品管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
var activityId = <%=activityId%>,
	activityName = decodeURI(baseTools.getQueryString("activityName")),
	maxSerial = 0,
	hideIdColumn = false,
	toolbarComponent = '#tb',
	init = function() {
		myQueryParams = {
			'maxSerial' : maxSerial
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
	htmTableTitle = "#"+activityName+"#奖品列表", //表格标题
	loadDataURL = "./admin_op/op_queryActivityAward?activityId="+activityId, //数据装载请求地址
	deleteURI = "./admin_op/op_deleteActivityAward?ids=", //删除请求地址
	queryAwardURL = "./admin_op/op_queryActivityAwardById", // 根据id查询奖品
	addAwardURL = "./admin_op/op_saveActivityAward",
	updateAwardURL = "./admin_op/op_updateActivityAward",
	updateSerialURL = "./admin_op/op_updateActivityAwardSerial",
	
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'ID',align : 'center', width : 60, sortable: false},
		{field : 'iconThumbPath',title : '缩略图', align : 'center', width : 60,
			formatter:function(value,row,index) {
				return "<img width='60px' alt='缩略图'  class='htm_column_img' src='" + value + "'/>";
			}
		},
		{field : 'awardName',title : '名称',align : 'center', width : 120},
		{field : 'price',title : '价格',align : 'center', width : 80},
		{field : 'total',title : '总数',align : 'center', width : 80},
		{field : 'remain',title : '剩余',align : 'center', width : 80},
		{field : 'awardLink',title : '链接',align : 'center', width : 180},
		{field : 'opt',title : '操作',align : 'center', width: 80,
  			formatter: function(value,row,index){
  				return "<a title='更新奖品信息' class='updateInfo' href='javascript:void(0)' onclick='javascript:initAddWindow(true,\"" + row.id + "\")'>更新</a>";
  			}
  		},
    ],
    
    addWidth = 530, //添加信息宽度
	addHeight = 470, //添加信息高度
	addTitle = "添加奖品", //添加信息标题
	
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
			iconCls : 'icon-tip',
			resizable : false
		});
		
		removePageLoading();
		$("#main").show();
	};
	
//初始化添加窗口
function initAddWindow(isUpdate, id) {
	var addForm = $('#add_form');
	$("#htm_add .opt_btn").show();
	$("#htm_add .loading").hide();
	clearFormData(addForm);
	$("#iconThumbImg_add").attr("src", "./base/images/bg_empty.png");
	$("#iconImg_add").attr("src", "./base/images/bg_empty.png");
	loadAddFormValidate(isUpdate);
	
	if(isUpdate) {
		$('#htm_actlabel').window('open');
		$.post(queryAwardURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var award = result['award'];
				$("#id_add").val(id);
				$("#iconPath_add").val(award['iconPath']);
				$("#iconImg_add").attr('src', award['iconPath']);
				$("#iconThumbPath_add").val(award['iconThumbPath']);
				$("#iconThumbImg_add").attr('src', award['iconThumbPath']);
				$("#awardName_add").val(award['awardName']);
				$("#awardDesc_add").val(award['awardDesc']);
				$("#price_add").val(award['price']);
				$("#awardLink_add").val(award['awardLink']);
				$("#total_add").val(award['total']);
				$("#remain_add").val(award['remain']);
				$("#serial_add").val(award['serial']);
				$("#activityId_add").val(award['activityId']);
				$("#tr_remain_add").show();
				$('#htm_add').window('open');
				$("#add_loading").hide();
				$("#add_form").show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$("#id_add").val(id);
		$("#activityId_add").val(activityId);
		$("#tr_remain_add").hide();
		$('#htm_add').window('open');
		$("#add_loading").hide();
		$("#add_form").show();
	}
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate(isUpdate) {
	var url = addAwardURL;
	if(isUpdate) {
		url = updateAwardURL;
	}
	
	var $form = $('#add_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			$("#htm_add .opt_btn").hide();
			$("#htm_add .loading").show();
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$.post(url, $form.serialize(),
					function(result){
						formSubmitOnce = true;
						$("#htm_add .opt_btn").show();
						$("#htm_add .loading").hide();
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData($form);  //清空表单数据	
							if(!isUpdate) {
								myQueryParams.maxSerial = 0;
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
	
	$("#iconThumbPath_add")
	.formValidator({empty:false, onshow:"请上传小图",onfocus:"请上传小图",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#iconPath_add")
	.formValidator({empty:false, onshow:"请上传大图",onfocus:"请上传大图",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#awardDesc_add")
	.formValidator({empty:false, onshow:"请输入描述",onfocus:"请输入描述",oncorrect:"输入正确！"})
	.inputValidator({min:1,onerror:"不能为空"});
	
	$("#price_add")
	.formValidator({empty:false, onshow:"请输入价格",onfocus:"例如:100",oncorrect:"输入正确！"});
	
	$("#total_add")
	.formValidator({empty:false, onshow:"请输入总数",onfocus:"请输入数字",oncorrect:"输入正确！"});
	
	$("#remain_add")
	.formValidator({empty:true, onshow:"请输入剩余数",onfocus:"请输入数字",oncorrect:"输入正确！"});
	
	$("#awardLink_add")
	.formValidator({empty:true, onshow:"请输入详情链接",onfocus:"允许为空",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
}

/**
 * 初始化排序窗口
 */
function initSerialWindow() {
	clearSerialForm();
}

/**
 * 排序
 */
function reSerial() {
	$('#htm_serial .opt_btn').show();
	$('#htm_serial .loading').hide();
	initSerialWindow();
	// 打开添加窗口
	$("#htm_serial").window('open');
}

/**
 * 清空排序表单
 */
function clearSerialForm() {
	$("#serial_form").find('input[name="reIndexId"]').val('');	
}

/**
 * 提交排序
 */
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
	
</script>
</head>
<body>
	<div id="main">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initAddWindow(false,0);" class="easyui-linkbutton" title="添加奖品" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete('id');" class="easyui-linkbutton" title="删除奖品" plain="true" iconCls="icon-cancel" id="delBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重新排序" plain="true" iconCls="icon-converter" id="reSerialBtn">排序</a>
   		</div>
	</div> 
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<span id="add_loading" style="margin:140px 0 0 220px; position:absolute;">加载中...</span>
		<form id="add_form" action="./admin_op/op_saveActivityAward" method="post" class="none">
			<table id="htm_edit_table" width="510">
				<tbody>
					<tr class="none">
						<td class="leftTd">奖品id：</td>
						<td colspan="2"><input type="text" name="id" id="id_add"/></td>
					</tr>
					<tr class="none">
						<td class="leftTd">活动序号：</td>
						<td colspan="2"><input type="text" name="serial" id="serial_add"/></td>
					</tr>
					<tr class="none">
						<td class="leftTd">活动id：</td>
						<td colspan="2"><input type="text" name="activityId" id="activityId_add"/></td>
					</tr>
					<tr>
						<td class="leftTd">缩略图：</td>
						<td style="height: 80px;">
							<input class="none" type="text" name="iconThumbPath" id="iconThumbPath_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="iconThumbPath_add_upload_btn" style="position: absolute; margin:30px 0 0 1px" class="easyui-linkbutton" iconCls="icon-add">上传小图</a> 
							<img id="iconThumbImg_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="80px" height="80px">
							<div id="iconThumbPath_add_upload_status" class="update_status none" style="width: 205px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="iconThumbPath_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">大图：</td>
						<td style="height: 80px;">
							<input class="none" type="text" name="iconPath" id="iconPath_add"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="iconPath_add_upload_btn" style="position: absolute; margin:30px 0 0 30px" class="easyui-linkbutton" iconCls="icon-add">上传大图</a> 
							<img id="iconImg_add"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="142px" height="80px">
							<div id="iconPath_add_upload_status" class="update_status none" style="width: 205px; text-align: left;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="iconPath_addTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">奖品名称：</td>
						<td><input name="awardName" id="awardName_add" onchange="validateSubmitOnce=true;" /></td>
						<td class="rightTd"><div id="awardName_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">奖品描述：</td>
						<td><textarea name="awardDesc" id="awardDesc_add" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="awardDesc_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">价格：</td>
						<td><input type="text" name="price" id="price_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="price_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">总数：</td>
						<td><input type="text" name="total" id="total_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="total_addTip" class="tipDIV"></div></td>
					</tr>
					<tr class="none" id="tr_remain_add">
						<td class="leftTd">剩余数量：</td>
						<td><input type="text" name="remain" id="remain_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="remain_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">详情链接：</td>
						<td><input type="text" name="awardLink" id="awardLink_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="awardLink_addTip" class="tipDIV"></div></td>
					</tr>
					<tr class="opt_btn">
						<td  colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
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
	
	<!-- 重排精品 -->
	<div id="htm_serial">
		<form id="serial_form" action="./admin_op/op_updateActivityAwardSerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">id：</td>
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
	
	var uploader = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'iconPath_add_upload_btn',
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
            	$("#iconPath_add_upload_btn").hide();
            	$("#iconImg_add").hide();
            	var $status = $("#iconPath_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#iconPath_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#iconPath_add_upload_btn").show();
            	$("#iconImg_add").show();
            	$("#iconPath_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#iconImg_add").attr('src', url);
            	$("#iconPath_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/activity/award/" + timestamp+suffix;
                return key;
            }
        }
    });
	
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'iconThumbPath_add_upload_btn',
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
            	$("#iconThumbPath_add_upload_btn").hide();
            	$("#iconThumbImg_add").hide();
            	var $status = $("#iconThumbPath_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#iconThumbPath_add_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#iconThumbPath_add_upload_btn").show();
            	$("#iconThumbImg_add").show();
            	$("#iconThumbPath_add_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#iconThumbImg_add").attr('src', url);
            	$("#iconThumbPath_add").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  //提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/activity/award/" + timestamp+suffix;
                return key;
            }
        }
    });
	
	</script>
</body>
</html>