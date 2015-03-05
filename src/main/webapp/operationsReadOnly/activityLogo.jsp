<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动Logo管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

var maxSerial = 0,
	maxActivitySerial = 0,
	activityQueryParams = {
		'maxSerial':maxActivitySerial
	},
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
	htmTableTitle = "活动Logo列表", //表格标题
	htmTableWidth = 960,
	loadDataURL = "./admin_op/op_queryActivityLogo", //数据装载请求地址
	deleteURI = "./admin_op/op_deleteActivityLogo?ids=", //删除请求地址
	updateValidURL = "./admin_op/op_updateActivityLogoValid?ids=", // 更新有效性地址
	
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : 'id',title : 'ID',align : 'center', width : 60, sortable: false},
		{field : 'logoPath',title : 'LOGO', align : 'center',width : 200, height:53, editor:{type:'validatebox',options:{ required:'true',validType:'url'}},
			formatter:function(value,row,index) {
				return "<img width='80px' height='53px' alt='' title='点击编辑' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
			}
		},
  		{field : 'activityId',title : '活动ID',align : 'center', width : 60},
  		{ field : 'activityName',title : '活动简称',align : 'center', width : 120, editor: 'textarea'},
		{ field : 'commercial',title :'活动类型',align : 'center',width : 60,
			editor:{
	            type:'combobox',
	            options:{
	            		valueField: 'id',
	                	textField: 'text',
	                	data:[{"id":0, "text":"普通活动","selected":true},{"id":1, "text":"商业活动"}],
	                	required:true
	            	}
				},
			formatter: function(value,row,index){
				if(value == 1) {
					return '商业活动';
				}
				return  '普通活动';
  			}	
		},
		{ field : 'titlePath',title : '封面', align : 'center',width : 90, height:53, editor:{type:'validatebox',options:{ required:'true',validType:'url'}},
			formatter:function(value,row,index) {
				return "<img width='80px' height='53px' alt='' title='点击编辑' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
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
    
    addWidth = 530, //添加信息宽度
	addHeight = 150, //添加信息高度
	addTitle = "添加Logo", //添加信息标题
	
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$('#htm_add').window({
			title : addTitle,
			modal : true,
			width : addWidth,
			height : addHeight,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			top:10
		});
		
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
		
		$('#activityId_add').combogrid({
			panelWidth : 650,
		    panelHeight : 380,
		    loadMsg : '加载中，请稍后...',
			pageList : [5,10,20],
			pageSize : 5,
			editable : false,
		    multiple : false,
		    required : true,
		   	idField : 'id',
		    textField : 'activityName',
		    url : './admin_op/op_queryNormalValidSquarePushActivity',
		    queryParams : activityQueryParams,
		    pagination : true,
		    columns:[[
				{field : 'serial',title : '活动序号',align : 'center', width : 60, hidden:true},
		  		{field : 'id',title : '活动ID',align : 'center', width : 60},
		  		{field : 'activityName',title : '活动名称',align : 'center', width : 120},
				{field : 'activityDesc',title : '活动简介',align : 'center', width : 200, 
		  			formatter : function(value, row, rowIndex ) {
						return "<span title='" + value + "' class='updateInfo'>"+value+"</span>";
					}	
				},
				{field : 'activityDate',title :'添加日期',align : 'center',width : 150},
				{ field : 'titlePath',title : '封面', align : 'center',width : 90, height:53,
					formatter:function(value,row,index) {
						return "<img width='80px' height='53px' alt='' title='点击编辑' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				}
		    ]],
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxSerial > maxActivitySerial) {
						maxActivitySerial = data.maxSerial;
						activityQueryParams.maxSerial = maxActivitySerial;
					}
				}
		    }
		});
		
		removePageLoading();
		$("#main").show();
	};
	
//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	clearFormData(addForm);
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	$("#logoPath_add").focus();  //光标定位
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							myQueryParams.maxSerial = 0;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#logoPath_add")
	.formValidator({empty:false, onshow:"请输入封面链接",onfocus:"例:http://test.png",oncorrect:"该链接可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
}

/**
 * 使选中记录有效
 */
function updateValid(valid) {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(rows.length > 0){
		$.messager.confirm('更新记录', '您确定要更新已选中的记录有效性?', function(r){ 	
			if(r){				
				var ids = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i]['id']);
					rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
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
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加织图到广场" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete('id');" class="easyui-linkbutton" title="删除LOGO" plain="true" iconCls="icon-cancel" id="delBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效LOGO！" plain="true" iconCls="icon-ok">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效LOGO！" plain="true" iconCls="icon-tip">批量失效</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重新排序" plain="true" iconCls="icon-converter" id="reSerialBtn">排序</a>
   		</div>
	</div> 
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_op/op_saveActivityLogo" method="post">
			<table id="htm_edit_table" width="510">
				<tbody>
					<tr>
						<td class="leftTd">活动id：</td>
						<td colspan="2"><input type="text" name="activityId" id="activityId_add"/></td>
					</tr>
					<tr>
						<td class="leftTd">Logo：</td>
						<td><input type="text" name="logoPath" id="logoPath_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="logoPath_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 重排精品 -->
	<div id="htm_serial">
		<form id="serial_form" action="./admin_op/op_updateActivityLogoSerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">LogoID：</td>
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