<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贴纸系列</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<style type="text/css">
	.window {
		position:fixed;
	}
	.messager-window {
		position:absolute;	
	}
	
</style>
<script type="text/javascript">

var maxId = 0,
	selectedIds = [],
	init = function() {
		loadPageData(initPage);
	},
	hideIdColumn = false,
	toolbarComponent = '#tb',
	htmTablePageList = [50,100,200,250];
	htmTableTitle = "系列列表", //表格标题
	loadDataURL = "./admin_ztworld/sticker_querySet", //数据装载请求地址
	deleteURI = "./admin_ztworld/sticker_deleteSets?ids=", //删除请求地址
	saveURL = "./admin_ztworld/sticker_saveSet", // 保存分类地址
	updateByIdURL = "./admin_ztworld/sticker_updateSet", // 更新分类地址
	queryByIdURL = "./admin_ztworld/sticker_querySetById", // 根据id查询分类
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['stickerSet.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['stickerSet.maxId'] = maxId;
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
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'id',align : 'center',width : 60},
		{field : 'setName',title : '系列名称',align : 'center',width : 120},
  		{field : 'weight',title : '推荐状态',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(row.id == 1) // 默认系列不能操作
  					return '';
  				
  				img = "./common/images/tip.png";
  				title = "点击推荐";
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					title = "已推荐，点击撤销";
  					return "<img title='" + title +  "' class='htm_column_img pointer' onclick='updateWeight(\"" 
  						+ index + "\",\"" + row[recordIdKey] + "\",\"" + 0 + "\")' src='" + img + "'/>";
  				}
  				return "<img title='" + title +  "' class='htm_column_img pointer' onclick='updateWeight(\"" 
  					+ index + "\",\"" + row[recordIdKey] + "\",\"" + 1 + "\")' src='" + img + "'/>";
  			}
  		},
  		{field : 'opt',title : '操作',width : 80,align : 'center',rowspan : 1,
			formatter : function(value, row, index ) {
				if(row.id == 1) // 默认系列不能操作
  					return '';
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
			title: '添加分类',
			modal : true,
			width : 520,
			height : 180,
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

		$("#ss-weight").combobox({
			onSelect : function(record) {
				maxId = 0;
				myQueryParams['stickerSet.weight'] = record.value;
				myQueryParams['stickerSet.maxId'] = maxId;
				$("#htm_table").datagrid("load",myQueryParams);
			}
		});		
		
		removePageLoading();
		$("#main").show();
	};
	
function initEditWindow(id, index, isUpdate) {
	$("#channelName_edit").focus();  //光标定位
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改系列');
		$('#htm_edit').window('open');
		$.post(queryByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#setName_edit").val(obj['setName']);
				
				$("#id_edit").val(obj['id']);
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
		$("#weight_edit").val(1); // 默认为推荐
		$('#htm_edit').panel('setTitle', '添加系列');
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
								$("#htm_table").datagrid("reload");
							} else {
								maxId = 0;
								myQueryParams['stickerSet.maxId'] = maxId;
								$("#htm_table").datagrid("load",myQueryParams);
							}
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#setName_edit")
	.formValidator({empty:false,onshow:"分类名称（必填）",onfocus:"请输入名称",oncorrect:"设置成功"});
	
}

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
					myQueryParams['stickerSet.maxId'] = maxId;
					$("#htm_table").datagrid("load",myQueryParams);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			}
		});
	}
}

function updateWeight(index, id, isAdd) {
	$("#htm_table").datagrid("loading");
	$.post("./admin_ztworld/sticker_updateSetWeight",{
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

function updateSortingCount(count) {
	$("#sorting-count").text(count);
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" title="添加系列" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重新排序" 
				plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序+<span id="sorting-count">0</span></span></a>
   			<select id="ss-weight" style="width:100px;">
	   			<option value="">所有推荐状态</option>
	   			<option value="1">已推荐</option>
	   			<option value="0">未推荐</option>
	   		</select>
	   		
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_ztworld/sticker_saveSet" method="post">
				<table id="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">分类名称：</td>
							<td><textarea id="setName_edit" name="stickerSet.setName" onchange="validateSubmitOnce=true;"></textarea></td>
							<td class="rightTd"><div id="setName_editTip" class="tipDIV"></div></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="stickerSet.id" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="stickerSet.serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="stickerSet.weight" id="weight_edit" onchange="validateSubmitOnce=true;"/></td>
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
			<form id="serial_form" action="./admin_ztworld/sticker_updateSetSerial" method="post">
				<table class="htm_edit_table" width="580">
					<tbody>
						<tr>
							<td class="leftTd">系列ID：</td>
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