<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>App链接管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">
var maxSerial = 0,
	toolbarComponent = '#tb',
	init = function() {
		myQueryParams = {
			'maxSerial' : maxSerial,
			'phoneCode' : 0
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
	
	htmTableTitle = "App链接维护", //表格标题
	recordIdKey = "serial",
	loadDataURL = "./admin_op/ad_queryAppLink", //数据装载请求地址
	deleteURI = "./admin_op/ad_deleteAppLinks?ids=", //删除请求地址
	updatePageURL = "./admin_op/ad_updateAppLinkByJSON",	
	addWidth = 500, //添加信息宽度
	addHeight = 275, //添加信息高度
	addTitle = "添加App链接", //添加信息标题
	
	phoneCode = [{
        "id":0,
        "text":"IOS",
        "selected":true
    },{
        "id":1,
        "text":"安卓"
    }],
	
	columnsFields = [
		{field : 'ck',checkbox : true},
  		{field : 'serial',title : '序号',align : 'center', width : 60},
  		{ field : 'phoneCode',title :'客户端',align : 'center',width : 60, 
  			editor:{type:'combobox',options:{valueField:'id',textField:'text',data:phoneCode}},
			formatter: function(value,row,index){
				var phone = "IOS";
				if(value == 1) {
					phone = '安卓';
				}
				return phone;
			}
		},
  		{ field : 'appIcon',title : '图标', align : 'center',width : 40,  editor:{type:'validatebox',options:{ required:'true',validType:'url'}},
			formatter:function(value,row,index) {
				return "<img width='30px' height='30px' alt='' title='点击编辑' class='htm_column_img'  src='" + value + "'/>";
			}
		},
		{field : 'id',title : 'AppID',align : 'center', width : 60},
		{ field : 'appName',title : '名称',align : 'center', width : 60, editor:{type:'validatebox',options:{ required:'true'}}},
		{ field : 'appDesc',title : '描述',align : 'center', width : 180, editor:{type:'validatebox',options:{ required:'true'}}, 
  			formatter : function(value, row, rowIndex ) {
				return "<span title='" + value + "'>"+value+"</span>";
			}	
		},
		{ field : 'appLink',title : '链接',align : 'center', width : 180, editor:{type:'validatebox',options:{ required:'true',validType:'url'}},
			formatter : function(value, row, rowIndex ) {
				return "<span title='" + value + "'>"+value+"</span>";
			}	
		},
		{ field : 'clickCount',title : '播放次数',align : 'center', width : 60,
			formatter : function(value, row, rowIndex ) {
				var uri = 'page_operations_appLinkRecord?appId='+ row['id']; //评论管理地址			
				return "<a title='显示播放次数' class='updateInfo' href='javascript:showRecord(\""
						+ uri + "\")'>"+value+"</a>";
			}
		}
    ],
    pageButtons = [{
        iconCls:'icon-save',
        text:'保存',
        handler:function(){
        	endEditing();
        	var rows = $("#htm_table").datagrid('getChanges', "updated"); 
        	$("#htm_table").datagrid('loading');
        	$.post(updatePageURL,{
        		'json':JSON.stringify(rows)
        		},function(result){
        			if(result['result'] == 0) {
        				$("#htm_table").datagrid('acceptChanges');
        			} else {
        				$.messager.alert('失败提示',result['msg']);  //提示失败信息
        			}
        			$("#htm_table").datagrid('loaded');
        		},"json");
        }
    },{
        iconCls:'icon-undo',
        text:'取消',
        handler:function(){
        	$("#htm_table").datagrid('rejectChanges');
        	$("#htm_table").datagrid('loaded');
        }
    }],
    
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
		
		$('#phoneCode').combobox({
			valueField:'id',
		    textField:'text',
		    onSelect:function(record) {
		    	myQueryParams.phoneCode = record['id'];
		    	loadPageData(initPage);
		    },
		    data:phoneCode
		});
		
		removePageLoading();
		$("#main").show();
	};
	
	
/**
 * 显示点击记录
 * @param uri
 */
function showRecord(uri) {
	$.fancybox({
		'margin'			: 20,
		'width'				: 520,
		'height'			: 480,
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}
	

/**
 * 初始化
 */
function initSerialWindow() {
	clearSerialForm();
}

/**
 * 重排活动
 */
function reSerial() {
	$('#htm_serial .opt_btn').show();
	$('#htm_serial .loading').hide();
	initSerialWindow();
	// 打开添加窗口
	$("#htm_serial").window('open');
}

/**
 * 清空索引排序
 */
function clearSerialForm() {
	$("#serial_form").find('input[name="reIndexId"]').val('');	
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


//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	$('#htm_add .opt_btn').show();
	$('#htm_add .loading').hide();
	clearFormData(addForm);
	var pcode = $("#phoneCode").combobox('getValue');
	if(pcode == "" || pcode=='undefined') pcode = 0;
	$("#phoneCode_add").combobox('setValue',pcode);
	
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
							maxSerial = 0;
							var pcode = $("#phoneCode_add").combobox('getValue');
							$("#phoneCode").combobox('setValue',pcode);
							myQueryParams.maxSerial = maxSerial;
							myQueryParams.phoneCode = pcode;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#appName_add")
	.formValidator({empty:false,onshow:"请输入App名称",onfocus:"设置名称",oncorrect:"设置成功"});
	
	$("#appIcon_add")
	.formValidator({empty:false,onshow:"请输入图标链接",onfocus:"例:http://tubiao.png",oncorrect:"该链接可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#appDesc_add")
	.formValidator({empty:false,onshow:"请输入App描述",onfocus:"设置描述",oncorrect:"设置成功"});
	
	$("#appLink_add")
	.formValidator({empty:false, onshow:"请输入App短链",onfocus:"例:http://duanlian",oncorrect:"该链接可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加链接" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete('id');" class="easyui-linkbutton" title="删除类型" plain="true" iconCls="icon-cut">删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排链接" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序</a>
			<input id="phoneCode" class="easyui-combobox" name="phoneCode" style="width:60px;" />
			
   		</div>
	</div> 

	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_op/ad_saveAppLink" method="post">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">名称：</td>
						<td><input type="text" name="appName" id="appName_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="appName_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">图标：</td>
						<td><input type="text" name="appIcon" id="appIcon_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="appIcon_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td><textarea name="appDesc" id="appDesc_add" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="appDesc_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">链接：</td>
						<td><input type="text" name="appLink" id="appLink_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="appLink_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">客户端：</td>
						<td colspan="2">
							<select id="phoneCode_add" class="easyui-combobox" name="phoneCode" onchange="validateSubmitOnce=true;" style="width:205px;" >
					        <option value="0" selected>IOS</option>
					        <option value="1">安卓</option>
					        </select>
						</td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">添加</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
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
		<form id="serial_form" action="./admin_op/ad_updateAppLinkSerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">AppID：</td>
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
							<a class="easyui-linkbutton" iconCls="icon-redo" onclick="clearSerialForm();">清空</a>
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