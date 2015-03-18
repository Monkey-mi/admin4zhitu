<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贴纸分类</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

var maxId = 0,
	init = function() {
		loadPageData(initPage);
	},
	hideIdColumn = false,
	htmTableTitle = "贴纸分类列表", //表格标题
	htmTableWidth = 1170,
	batchEnableTip = "您确定要使已选中的分类生效吗？",
	batchDisableTip = "您确定要使已选中的分类失效吗？",
	toolbarComponent = '#tb',
	htmTableTitle = "分类列表", //表格标题
	loadDataURL = "./admin_ztworld/sticker_queryType", //数据装载请求地址
	deleteURI = "./admin_ztworld/sticker_deleteTypes?ids=", //删除请求地址
	saveStickerURL = "./admin_ztworld/sticker_saveType", // 保存分类地址
	updateStickerByIdURL = "./admin_ztworld/sticker_updateType", // 更新分类地址
	queryStickerByIdURL = "./admin_ztworld/sticker_queryTypeById", // 根据id查询分类
	updateValidURL = "./admin_ztworld/sticker_updateTypeValid?ids=",
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['type.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['type.maxId'] = maxId;
			}
		}
	},
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'id',align : 'center',width : 60},
		{field : 'typeName',title : '分类名称',align : 'center',width : 120},
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
			title: '添加分类',
			modal : true,
			width : 520,
			height : 130,
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
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
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
		
		removePageLoading();
		$("#main").show();
	};
	
/**
 * 初始化分类信息编辑窗口
 */
function initEditWindow(id, index, isUpdate) {
	$("#channelName_edit").focus();  //光标定位
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改分类信息');
		$('#htm_edit').window('open');
		$.post(queryStickerByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#typeName_edit").val(obj['typeName']);
				
				$("#id_edit").val(obj['id']);
				$("#valid_edit").val(obj['valid']);
				$("#serial_edit").val(obj['serial']);
				$("#weight_edit").val(obj['weight']);
				
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
		$('#htm_edit').panel('setTitle', '添加分类');
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
							maxId = 0;
							myQueryParams['type.maxId'] = maxId;
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
	
	$("#typeName_edit")
	.formValidator({empty:false,onshow:"分类名称（必填）",onfocus:"请输入名称",oncorrect:"设置成功"});
	
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
					myQueryParams['type.maxId'] = maxId;
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
	$.post("./admin_ztworld/sticker_updateTypeWeight",{
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

function searchType() {
	maxId = 0;
	myQueryParams['type.maxId'] = maxId;
	myQueryParams['type.valid'] = $('#ss-valid').combobox('getValue');
	myQueryParams['type.weight'] = $('#ss-weight').combobox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
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
</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" title="添加分类" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效分类！" plain="true" iconCls="icon-ok">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效分类！" plain="true" iconCls="icon-tip">批量失效</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重新排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序</a>
			<a href="javascript:void(0);" onclick="javascript:refresh();" class="easyui-linkbutton" title="刷新缓存" plain="true" iconCls="icon-reload">刷新缓存</a>
			<span class="search_label">有效性过滤：</span>
			<select id="ss-valid" class="easyui-combobox" style="width:100px;">
	   			<option value="">所有状态</option>
	   			<option value="1">生效</option>
	   			<option value="0">未生效</option>
   			</select>
   			
   			<select id="ss-weight" class="easyui-combobox" style="width:100px;">
	   			<option value="">所有推荐状态</option>
	   			<option value="1">已推荐</option>
	   			<option value="0">未推荐</option>
	   		</select>
	   		<a href="javascript:void(0);" onclick="javascript:searchType();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	   		
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_ztworld/sticker_saveType" method="post">
				<table id="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">分类名称：</td>
							<td><input id="typeName_edit" name="type.typeName" onchange="validateSubmitOnce=true;"/></td>
							<td class="rightTd"><div id="typeName_editTip" class="tipDIV"></div></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="type.id" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="type.valid" id="valid_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="type.serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="type.weight" id="weight_edit" onchange="validateSubmitOnce=true;"/></td>
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
			<form id="serial_form" action="./admin_ztworld/sticker_updateTypeSerial" method="post">
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
		
		
		<!-- 更新缓存 -->
		<div id="htm_refresh">
			<form id="refresh_form" action="./admin_ztworld/sticker_updateStickerCache" method="post">
				<table class="htm_edit_table" width="580">
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
		
	</div>
	
</body>
</html>