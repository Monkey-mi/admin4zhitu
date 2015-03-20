<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Access-Control-Allow-Origin" content="http://openapi.baidu.com">
<title>字幕列表</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript">

var maxId = 0,
	t, // 翻译延迟全局变量
	init = function() {
		myQueryParams['title.transTo'] = 'all';
		loadPageData(initPage);
	},
	hideIdColumn = false,
	htmTableTitle = "字幕列表", //表格标题
	htmTableWidth = 1170,
	toolbarComponent = '#tb',
	loadDataURL = "./admin_ztworld/subtitle_queryTitle", //数据装载请求地址
	deleteURI = "./admin_ztworld/subtitle_deleteTitles?ids=", //删除请求地址
	saveTitleURL = "./admin_ztworld/subtitle_saveTitle", // 保存地址
	updateTitleByIdURL = "./admin_ztworld/subtitle_updateTitle", // 更新地址
	queryTitleById = "./admin_ztworld/subtitle_queryTitleById", // 根据id查询分类
	deleteURI = "./admin_ztworld/subtitle_deleteTitleByIds?ids=",
	
	languages = 
	[
	{
	    "id":"all",
	    "text":"所有语言",
	    "selected":true
	},{
	    "id":"en",
	    "text":"英语"
	},{
	    "id":"jp",
	    "text":"日语"
	},{
	    "id":"kor",
	    "text":"韩语"
	}],
	
	languages2 = 
	[
	{
	    "id":"en",
	    "text":"英语",
	    "selected":true
	},{
	    "id":"jp",
	    "text":"日语"
	},{
	    "id":"kor",
	    "text":"韩语"
	}],
	
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['title.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['title.maxId'] = maxId;
			}
		}
	},
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'id',align : 'center',width : 60},
		{field : 'subtitle',title : '字幕',align : 'center',width : 400},
		{field : 'subtitleEn',title : '翻译',align : 'center',width : 400},
		{field : 'transTo',title : '类型',align : 'center',width : 60,
			formatter:function(value,row,index) {
				if(value == 'en') return '英语';
				if(value == 'kor') return '韩语';
				if(value == 'jp') return '日语';
				return '未知';
			}
		},
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
			title: '添加分类',
			modal : true,
			width : 720,
			height : 260,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				var $form = $('#edit_form');
				$("#subtitle_edit").val("");
				$("#subtitleEn_edit").val("");
				$("#id_edit").val("");
				$("#serial_edit").val("");
				$("#edit_form .opt_btn").show();
				$("#edit_form .loading").hide();
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		$('#htm_file').window({
			title: '批量添加字幕',
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
		
		$('#htm_refresh').window({
			title: '刷新缓存',
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
		
		$("#subtitle_edit").keyup(function() {
			clearTimeout(t);
			t = setTimeout(trans, 200);
		});
		
		removePageLoading();
		$("#main").show();
	};
	
/**
 * 初始化分类信息编辑窗口
 */
function initEditWindow(id, index, isUpdate) {
	$("#subtitle_edit").focus();  //光标定位
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改分类信息');
		$('#htm_edit').window('open');
		$.post(queryTitleById,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#subtitle_edit").val(obj['subtitle']);
				$("#subtitleEn_edit").val(obj['subtitleEn']);
				$("#transTo_edit").combobox('setValue', obj['transTo']);
				
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
		$("#transTo_edit").combobox('setValue', 'en');
		$('#htm_edit').panel('setTitle', '添加字幕');
		$('#htm_edit').window('open');
		$("#edit_loading").hide();
		$("#edit_form").show();
	}
}

//提交表单，以后补充装载验证信息
function loadEditFormValidate(index, isUpdate) {
	var url = saveTitleURL;
	if(isUpdate) {
		url = updateTitleByIdURL;
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
							myQueryParams['title.maxId'] = maxId;
							if(isUpdate) {
								$("#ss-transTo").combobox('setValue', 'all');
								myQueryParams['title.transTo'] = 'all';
								$("#htm_table").datagrid('reload');
							} else {
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
	
	$("#subtitle_edit")
	.formValidator({empty:false,onshow:"字幕（必填）",onfocus:"请输入字幕",oncorrect:"设置成功"});
	
	$("#subtitleEn_edit")
	.formValidator({empty:false,onshow:"翻译（必填）",onfocus:"请输入翻译",oncorrect:"设置成功"});
	
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
					myQueryParams['title.maxId'] = maxId;
					$("#ss-transTo").combobox('setValue', 'all');
					myQueryParams['title.transTo'] = 'all';
					loadPageData(initPage);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			}
		});
	}
}

function searchTranshTo() {
	maxId = 0;
	myQueryParams['title.maxId'] = maxId;
	myQueryParams['title.transTo'] = $('#ss-transTo').combobox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}

/**
 * 刷新分类缓存
 */
function refresh() {
	$('#htm_refresh .opt_btn').show();
	$('#htm_refresh .loading').hide();
	$('#limit_refresh').val(32);
	// 打开窗口
	$("#htm_refresh").window('open');
	loadRefreshFormValidate();
}

function loadRefreshFormValidate() {
	var $form = $('#refresh_form');
	
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#refresh_form .opt_btn").hide();
				$("#refresh_form .loading").show();
				//验证成功后以异步方式提交表单
				$.post($form.attr('action'),$form.serialize(),
					function(result){
						formSubmitOnce = true;
						$("#refresh_form .opt_btn").show();
						$("#refresh_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_refresh').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#limit_refresh")
	.formValidator({empty:false,onshow:"刷新记录（必填）",onfocus:"请输入刷新记录",oncorrect:"设置成功"});
}

function trans() {
	var subtitle = $("#subtitle_edit").val().replace(/\ /g,"");
	if(subtitle == '') {
		$("#subtitleEn_edit").val('');
		return;
	}
	var to = $("#transTo_edit").combobox('getValue');
	$.post('http://openapi.baidu.com/public/2.0/bmt/translate',{
		'client_id':'S5WmdLBAIx43wu8im29kUa0L',
		'from':'zh',
		'to':to,
		'q':subtitle
		
	}, function(result){
		if(result['error_code'] != undefined) {
			$.messager.alert('翻译错误',result['error_msg']);
		} else {
			$("#subtitleEn_edit").val(result['trans_result'][0]['dst']);
		}
		
	},"jsonp");
	
}

//初始化添加窗口
function initFileWindow() {
	$("#htm_file form").show();
	var $form = $('#file_form');
	$('#htm_file .opt_btn').show();
	$('#htm_file .loading').hide();
	clearFormData($form);
	$("#transTo_file").combobox('setValue', 'en');
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
							myQueryParams['title.maxId'] = maxId;
							$("#ss-transTo").combobox('setValue', 'all');
							myQueryParams['title.transTo'] = 'all';
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
			<a href="javascript:void(0);" onclick="javascript:refresh();" class="easyui-linkbutton" title="刷新缓存" plain="true" iconCls="icon-reload">刷新缓存</a>
			<span class="search_label">语言：</span>
   			<input id="ss-transTo" class="easyui-combobox" style="width:100px;" data-options="
   				valueField: 'id',
				textField: 'text',
				data: languages,
				onSelect: function(ref) {
					searchTranshTo();
				}"/>
	   		
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_ztworld/subtitle_saveTitle" method="post">
				<table id="htm_edit_table" width="680">
					<tbody>
						<tr>
							<td class="leftTd">语言：</td>
							<td><input id="transTo_edit" name="title.transTo" class="easyui-combobox" style="width:100px;" data-options="
				   				valueField: 'id',
								textField: 'text',
								data: languages2,
								onSelect: function(rec){
									trans();
						        }"/></td>
							<td class="rightTd"><div id="typeName_editTip" class="tipDIV"></div></td>
						</tr>
						<tr>
							<td class="leftTd">字幕：</td>
							<td><textarea id="subtitle_edit" name="title.subtitle" style="width:360px;"></textarea></td>
							<td class="rightTd"><div id="subtitle_editTip" class="tipDIV"></div></td>
						</tr>
						
						<tr>
							<td class="leftTd">翻译：</td>
							<td><textarea id="subtitleEn_edit" name="title.subtitleEn" style="width:360px;vertical-align:middle;"></textarea>
								<a href="javascript:void(0);" onclick="javascript:trans();" class="easyui-linkbutton" 
									title="翻译" iconCls="icon-search">翻译</a>
							</td>
							<td class="rightTd"><div id="subtitleEn_editTip" class="tipDIV"></div></td>
						</tr>
						<tr class="none">
							<td colspan="3"><input type="text" name="title.id" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						<tr class="none">
							<td colspan="3"><input type="text" name="title.serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
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
			<form id="file_form" action="./admin_ztworld/subtitle_saveTitleByFile" method="post">
				<table id="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">语言：</td>
							<td><input id="transTo_file" name="transTo" class="easyui-combobox" style="width:100px;" data-options="
				   				valueField: 'id',
								textField: 'text',
								data: languages2"/></td>
							<td class="rightTd"><div id="transTo_fileTip" class="tipDIV"></div></td>
						</tr>
						<tr>
							<td class="leftTd">翻译文件：</td>
							<td><input type="file" id="titleFile_file" name="titleFile"/></td>
							<td class="rightTd"><div id="titleFile_fileTip" class="tipDIV"></div></td>
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
		
		<div id="htm_refresh">
			<form id="refresh_form" action="./admin_ztworld/subtitle_updateTitleCache" method="post">
				<table id="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">刷新条数：</td>
							<td><input type="text" id="limit_refresh" name="limit"/></td>
							<td class="rightTd"><div id="limit_refreshTip" class="tipDIV"></div></td>
						</tr>
						
						<tr class="opt_btn">
							<td colspan="3" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#refresh_form').submit();">提交</a>
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
			<form id="serial_form" action="./admin_ztworld/subtitle_updateTitleSerial" method="post">
				<table class="htm_edit_table" width="580">
					<tbody>
						<tr>
							<td class="leftTd">分类ID：</td>
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