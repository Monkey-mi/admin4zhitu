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
	htmTableWidth = 1350,
	batchEnableTip = "您确定要使已选中的频道生效吗？",
	batchDisableTip = "您确定要使已选中的频道失效吗？",
	toolbarComponent = '#tb',
	htmTableTitle = "频道列表", //表格标题
	loadDataURL = "./admin_op/channel_queryChannel", //数据装载请求地址
	deleteURI = "./admin_op/channel_deleteChannels?ids=", //删除请求地址
	saveChannelURL = "./admin_op/channel_saveChannel", // 保存频道地址
	updateChannelByIdURL = "./admin_op/channel_updateChannel", // 更新频道地址
	queryChannelByIdURL = "./admin_op/channel_queryChannelById", // 根据id查询频道
	updateValidURL = "./admin_op/channel_updateChannelValid?ids=",
	
	htmTablePageList = [6,10,20],
	myPageSize = 6,
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['channel.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['channel.maxId'] = maxId;
			}
		}
	},
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'id',align : 'center',width : 60},
		{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
			formatter:function(value,row,index) {
				return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
			}
		},
		{field : 'channelName',title : '频道名称',align : 'center',width : 120},
		{field : 'channelTitle',title : '副标题',align : 'center',width : 280},
		{field : 'subIcon',title : '副icon', align : 'center',width : 60, height:60,
			formatter:function(value,row,index) {
				if(value == '' || value == undefined)
					return '';
				else
					return "<img width='30px' height='30px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
			}
		},
  		{field : 'opt',title : '操作',width : 80,align : 'center',rowspan : 1,
			formatter : function(value, row, index ) {
				return "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row.id + "\",\"" + index + "\"," + true + ")'>【修改】</a>";
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
			height : 375,
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
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		$('#ss_valid').combobox({
			onSelect:function(record) {
				maxId = 0;
				myQueryParams['channel.maxId'] = maxId;
				myQueryParams['channel.valid'] = record.value;
				loadPageData(initPage);
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
		$.post(queryChannelByIdURL,{
			"id":id
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
					
				
				$("#id_edit").val(obj['id']);
				$("#valid_edit").val(obj['valid']);
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
	.formValidator({empty:true,onshow:"副标题（可选）",onfocus:"请输入副标题",oncorrect:"设置成功"});
	
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
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排活动排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序</a>
			<!-- 
			<a href="javascript:void(0);" onclick="javascript:refresh();" class="easyui-linkbutton" title="刷新缓存" plain="true" iconCls="icon-reload">刷新缓存</a>
			 -->
			<span class="search_label">有效性过滤：</span>
			<select id="ss_valid" style="width:80px;">
	   			<option value="">所有状态</option>
	   			<option value="1">生效</option>
	   			<option value="0">未生效</option>
   			</select>
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_op/channel_saveChannel" method="post">
				<table id="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">ICON：</td>
							<td style="height: 90px;">
								<input class="none" type="text" name="channel.channelIcon" id="channelIcon_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
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
							<td><input id="channelName_edit" name="channel.channelName" onchange="validateSubmitOnce=true;"/></td>
							<td class="rightTd"><div id="channelName_editTip" class="tipDIV"></div></td>
						</tr>
						<tr>
							<td class="leftTd">副标题：</td>
							<td><textarea name="channel.channelTitle" id="channelTitle_edit" onchange="validateSubmitOnce=true;"></textarea></td>
							<td class="rightTd"><div id="channelTitle_editTip" class="tipDIV"></div></td>
						</tr>
						<tr>
							<td class="leftTd">副ICON：</td>
							<td style="height: 90px;">
								<input class="none" type="text" name="channel.subIcon" id="subIcon_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
								<a id="subIcon_edit_upload_btn" style="position: absolute; margin:12px 0 0 70px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
								<a id="subIcon_edit_del_btn" style="position: absolute; margin:-7px 0 0 30px" onclick="javascript:clearEditSubIcon();" class="easyui-linkbutton none" plain="true" title="删除" iconCls="icon-cancel"></a> 
								<img id="subImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="50px" height="50px">
								<div id="subIcon_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">
									上传中...<span class="upload_progress"></span><span>%</span>
								</div>
							</td>
							<td class="rightTd">
								<div id="channelIcon_editTip" style="display: inline-block;" class="tipDIV"></div>
							</td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="channel.id" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="channel.valid" id="valid_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="channel.serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="opt_btn">
							<td colspan="3" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#edit_form').submit();">提交</a>
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