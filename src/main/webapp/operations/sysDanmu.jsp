<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Access-Control-Allow-Origin" content="http://openapi.baidu.com">
<title>弹幕列表</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer}"></script>
<script type="text/javascript">

var maxId = 0,
	init = function() {
		myQueryParams['sysDanmu.channelId'] = '0';
		loadPageData(initPage);
	},
	hideIdColumn = false,
	htmTableTitle = "系统弹幕列表", //表格标题
	toolbarComponent = '#tb',
	loadDataURL = "./admin_op/danmu_querySysDanmu", //数据装载请求地址
	deleteURI = "./admin_op/danmu_deleteSysDanmus?ids=", //删除请求地址
	saveURL = "./admin_op/danmu_saveSysDanmu", // 保存地址
	updateByIdURL = "./admin_op/danmu_updateSysDanmu", // 更新地址
	queryById = "./admin_op/danmu_querySysDanmuById", // 根据id查询分类
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['sysDanmu.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['sysDanmu.maxId'] = maxId;
			}
		}
	},
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'id',align : 'center',width : 60},
		userAvatarColumn,
		{field : 'userName',title : '用户名',align : 'center', width : 120},
		{field : 'content',title : '弹幕',align : 'left', width : 500},
		{field : 'channelId',title : '频道ID',align : 'center',width : 80},
		{field : 'channelName',title : '频道',align : 'center',width : 180},
		{field : 'opt',title : '操作',width : 80,align : 'center',rowspan : 1,
			formatter : function(value, row, index ) {
				return "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row.id + "\",\"" + index + "\"," + true + ")'>【修改】</a>";
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
			title: '添加弹幕',
			modal : true,
			width : 720,
			height : 230,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				var $form = $('#edit_form');
				$("#sysDanmu_edit").val("");
				$("#id_edit").val("");
				$("#serial_edit").val("");
				$("#edit_form .opt_btn").show();
				$("#edit_form .loading").hide();
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		$('#htm_file').window({
			title: '批量添加弹幕',
			modal : true,
			width : 520,
			height : 160,
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
 * 初始化分类信息编辑窗口
 */
function initEditWindow(id, index, isUpdate) {
	$("#content_edit").focus();  //光标定位
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改弹幕信息');
		$('#htm_edit').window('open');
		$.post(queryById,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#content_edit").val(obj['content']);
				$("#channelId_edit").val(obj['channelId']);
				$("#authorId_edit").val(obj['authorId']);
				
				
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
		$('#htm_edit').panel('setTitle', '添加弹幕');
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
							maxId = 0;
							myQueryParams['sysDanmu.maxId'] = maxId;
							if(isUpdate) {
								$("#htm_table").datagrid('reload');
							} else {
								myQueryParams['sysDanmu.channelId'] = '0';
								loadPageData(initPage);
							}
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#content_edit")
	.formValidator({empty:false,onshow:"弹幕（必填）",onfocus:"请输入弹幕",oncorrect:"设置成功"});
	
	$("#channelId_edit")
	.formValidator({empty:false,onshow:"频道id（必填）",onfocus:"请输入频道id",oncorrect:"设置成功"});
	
	$("#authorId_edit")
	.formValidator({empty:false,onshow:"用户id（必填）",onfocus:"请输入用户id",oncorrect:"设置成功"});
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
					myQueryParams['sysDanmu.maxId'] = maxId;
					loadPageData(initPage);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			}
		});
	}
}

function searchDanmu() {
	maxId = 0;
	myQueryParams['sysDanmu.maxId'] = maxId;
	myQueryParams['sysDanmu.channelId'] = $('#ss-channelId').val();
	$("#htm_table").datagrid("load",myQueryParams);
}

//初始化添加窗口
function initFileWindow() {
	$("#htm_file form").show();
	var $form = $('#file_form');
	$('#htm_file .opt_btn').show();
	$('#htm_file .loading').hide();
	clearFormData($form);
	loadFileFormValidate();
	$('#htm_file').window('open');
}

//提交表单，以后补充装载验证信息
function loadFileFormValidate() {
	var $form = $('#file_form');
	formSubmitOnce = true;
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$('#htm_file .opt_btn').hide();
				$('#htm_file .loading').show();
				$form.ajaxSubmit({
			        success:  function(result){
			        	formSubmitOnce = true;
						$('#htm_file .opt_btn').show();
						$('#htm_file .loading').hide();
						if(result['result'] == 0) {
							$('#htm_file').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							maxId = 0;
							myQueryParams['sysDanmu.maxId'] = maxId;
							myQueryParams['sysDanmu.channelId'] = '0';
							loadPageData(initPage);
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
			        },
			        url:       $form.attr("action"),
			        type:      'post',
			        dataType:  'json', 
				}); 
				return false;
			}
		}
	});
	
	$("#titleFile_file")
	.formValidator({empty:false,onshow:"字幕文件（必选）",onfocus:"请选择字幕文件",oncorrect:"设置成功"});
}
	
</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" title="添加字幕" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete('id');" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:initFileWindow();" class="easyui-linkbutton" title="批量添加字幕" plain="true" iconCls="icon-add">批量添加</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重新排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序</a>
			<span class="search_label">频道id:</span><input type="text" id="ss-channelId" style="width:75px;"/>
			<a href="javascript:void(0);" onclick="javascript:searchDanmu();" class="easyui-linkbutton" title="查询" plain="true" iconCls="icon-search">查询</a>
	   		
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_op/sysDanmu_saveSysDanmu" method="post">
				<table id="htm_edit_table" width="680">
					<tbody>
						<tr>
							<td class="leftTd">弹幕：</td>
							<td><textarea id="content_edit" name="sysDanmu.content" style="width:360px;"></textarea></td>
							<td class="rightTd"><div id="content_editTip" class="tipDIV"></div></td>
						</tr>
						<tr>
							<td class="leftTd">频道id：</td>
							<td><input type="text" name="sysDanmu.channelId" id="channelId_edit" onchange="validateSubmitOnce=true;"/></td>
							<td class="rightTd"><div id="channelId_editTip" class="tipDIV"></div></td>
						</tr>
						<tr>
							<td class="leftTd">用户id：</td>
							<td><input type="text" name="sysDanmu.authorlId" id="authorId_edit" onchange="validateSubmitOnce=true;"/></td>
							<td class="rightTd"><div id="authorId_editTip" class="tipDIV"></div></td>
						</tr>
						<tr class="none">
							<td colspan="3"><input type="text" name="sysDanmu.id" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						<tr class="none">
							<td colspan="3"><input type="text" name="sysDanmu.serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
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
		
		<div id="htm_file">
			<form id="file_form" action="./admin_op/danmu_saveSysDanmuByFile" method="post">
				<table id="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">弹幕文件：</td>
							<td><input type="file" id="sysDanmu_file" name="sysDanmuFile"/></td>
							<td class="rightTd"><div id="sysDanmu_fileTip" class="tipDIV"></div></td>
						</tr>
						
						<tr>
							<td class="leftTd">频道id：</td>
							<td><input type="text" name="channelId" id="channelId_file" onchange="validateSubmitOnce=true;"/></td>
							<td class="rightTd"><div id="channelId_editTip" class="tipDIV"></div></td>
						</tr>
						
						<tr class="opt_btn">
							<td colspan="3" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#file_form').submit();">提交</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_file').window('close');">取消</a>
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
			<form id="serial_form" action="./admin_op/danmu_updateSysDanmuSerial" method="post">
				<table class="htm_edit_table" width="580">
					<tbody>
						<tr>
							<td class="leftTd">弹幕ID：</td>
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
	
</body>
</html>