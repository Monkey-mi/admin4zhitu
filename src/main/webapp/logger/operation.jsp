<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
var maxSerial = 0,
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
	htmTableTitle = "标签列表", //表格标题
	htmTableWidth = 1170,
	loadDataURL = "./admin_logger/operation_queryOperation", //数据装载请求地址
	deleteURI = "./admin_logger/operation_deleteOperation?ids=", //删除请求地址
	updateSerialRL = "./admin_logger/operation_updateOperationSerial?ids=", // 更新有效新请求地址
	queryOperationByIdURL = "./admin_logger/operation_queryOperationById",  // 根据id查询活动
	updateOperationURL = "./admin_logger/operation_updateOperation", // 更新活动
	saveOperationURL = "./admin_logger/operation_saveOperation",
	addWidth = 520, //添加信息宽度
	addHeight = 280, //添加信息高度
	addTitle = "添加操作信息", //添加信息标题
	hideIdColumn = false,
	
	columnsFields = [
  		{field : 'ck',checkbox : true},
  		{field : 'id',title : 'ID', align : 'center',width : 80},
  		{field : 'optName',title : '操作名称',align : 'center',width : 120},
  		{field : 'optInterface', title : '操作接口', align : 'center', width : 520},
  		{field : 'optDesc', title : '描述', align : 'center',width : 320},
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
 * 添加活动标签
 */
function initAddWindow(id, index, isUpdate) {
	var $form = $("#add_form");
	clearFormData($form);
	$("#optName_add").focus();  //光标定位
	loadaddFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_add').panel('setTitle', '修改操作信息');
		$('#htm_add').window('open');
		$.post(queryOperationByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var opt = result['msg'];
				$("#id_add").val(opt['id']);
				$("#optName_add").val(opt['optName']);
				$("#optInterface_add").val(opt['optInterface']);
				$("#optDesc_add").val(opt['optDesc']);
				$("#serial_add").val(opt['serial']);
				$("#add_loading").hide();
				$form.show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$('#htm_add').panel('setTitle', '添加操作信息');
		$('#htm_add').window('open');
		$("#add_loading").hide();
		$form.show();
	}
}

//提交表单，以后补充装载验证信息
function loadaddFormValidate(index, isUpdate) {
	var url = saveOperationURL;
	if(isUpdate) {
		url = updateOperationURL;
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
	
	$("#id_add")
	.formValidator({empty:false,onshow:"操作ID",onfocus:"1-11位数字",oncorrect:"设置成功"})
	.inputValidator({min:1,max:11,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"})
	.regexValidator({regexp:"num", datatype:"enum", onerror:"请输入数字"});
	
	$("#optName_add")
	.formValidator({empty:false,onshow:"操作名称",onfocus:"例如:示例操作",oncorrect:"设置成功"})
	.inputValidator({min:1,max:10,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由文字和数字组成"});
	
	$("#optInterface_add")
	.formValidator({empty:false,onshow:"操作接口",onfocus:"例如:void demo(int)",oncorrect:"设置成功"})
	.inputValidator({min:1,max:150,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由文字和数字组成"});
	
	$("#optDesc_add")
	.formValidator({empty:true,onshow:"认证描述（可选）",onfocus:"例如:xxx",oncorrect:"设置成功"});
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initAddWindow(0,0,false);" class="easyui-linkbutton" title="添加操作" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除操作" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter">重新排序</a>
   		</div>
	</div> 

	<!-- 排序窗口 -->
	<div id="htm_serial">
		<form id="serial_form" action="./admin_logger/operation_updateOperationSerial" method="post">
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
	
	
	<!-- 添加或修改认证信息-->
	<div id="htm_add">
		<span id="add_loading" style="margin:60px 0 0 230px; position:absolute;">加载中...</span>
		<form id="add_form" action="./admin_logger/operation_saveOperation" method="post" class="none">
			<table class="htm_edit_table" width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">ID：</td>
						<td><input type="text" name="id" id="id_add" onchange="validateSubmitOnce=true;" /></td>
						<td class="rightTd"><div id="id_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">名称：</td>
						<td><input type="text" name="optName" id="optName_add" onchange="validateSubmitOnce=true;" /></td>
						<td class="rightTd"><div id="optName_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">接口：</td>
						<td><textarea rows="" name="optInterface" id="optInterface_add" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="optInterface_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td><textarea rows="" name="optDesc" id="optDesc_add" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="optDesc_addTip" class="tipDIV"></div></td>
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
</body>
</html>