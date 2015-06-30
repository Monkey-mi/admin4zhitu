<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js"></script>
<script type="text/javascript">

var maxSerial = 0,
	batchEnableTip = "您确定要使已选中的活动生效吗？",
	batchDisableTip = "您确定要使已选中的活动失效吗？",
	toolbarComponent = '#tb',
	init = function() {
		myQueryParams = {
			'maxSerial' : maxSerial
		},
		loadPageData(initPage);
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
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
	htmTableTitle = "活动维护", //表格标题
	recordIdKey = "serial",
	loadDataURL = "./admin_op/op_querySquarePushActivity", //数据装载请求地址
	updatePageURL = "./admin_op/op_updateSquarePushActivityByJSON",		
	updateValidURL = "./admin_op/op_updateActivityValid?ids=",	
	addWidth = 500, //添加信息宽度
	addHeight = 430, //添加信息高度
	addTitle = "添加活动", //添加信息标题
	htmTablePageList = [5,10,20],
	myPageSize = 5,
	
	columnsFields = [
		{field : 'ck',checkbox : true },
  		{field : 'serial',title : '活动序号',align : 'center', width : 60},
  		{field : 'id',title : '活动ID',align : 'center', width : 60},
  		{ field : 'activityName',title : '活动简称',align : 'center', width : 120, editor: 'textarea'},
  		{ field : 'activityTitle',title : '活动标题',align : 'center', width :180, editor: 'textarea'},
		{ field : 'activityDesc',title : '活动描述',align : 'center', width : 180, editor: 'textarea', 
  			formatter : function(value, row, rowIndex ) {
				return "<span title='" + value + "' class='updateInfo'>"+value+"</span>";
			}	
		},
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
		{field : 'activityDate',title :'添加日期',align : 'center',width : 150},
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
    
    userMaxId = 0,
	userQueryParams = {
		'maxId':userMaxId
	},
	
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
		
		$('#sponsorIds_add').combogrid({
			panelWidth : 340,
		    panelHeight : 250,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#user_tb",
		    multiple : true,
		    required : false,
		   	idField : 'id',
		    textField : 'userName',
		    url : './admin_user/user_queryUser',
		    pagination : true,
		    columns:[[
				userAvatarColumn,
				{field : 'id',title : 'ID',align : 'center', width : 60},
				userNameColumn,
				sexColumn
		    ]],
		    queryParams:userQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > userMaxId) {
						userMaxId = data.maxId;
						userQueryParams.maxId = userMaxId;
					}
				}
		    },
		});
		var p = $('#sponsorIds_add').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					userMaxId = 0;
					userQueryParams.maxId = userMaxId;
				}
			}
		});
		
		removePageLoading();
		$("#main").show();
	};

/**
 * 搜索用户
 */
function searchUser() {
	userMaxId = 0;
	var userName = $('#ss_user').searchbox('getValue');
	userQueryParams = {
		'maxId' : userMaxId,
		'userName' : userName
	};
	$("#sponsorIds_add").combogrid('grid').datagrid("load",userQueryParams);
}

/**
 * 刷新活动列表
 */
function refresh() {
	$.messager.confirm('提示', '确定要刷新活动列表？刷新后所有数据将生效！', function(r){
		if (r){
			$('#htm_table').datagrid('loading');
			$.post('./admin_op/op_refreshSquarePushActivity',{
				"maxSerial":maxSerial
			},function(result){
					if(result['result'] == 0) {
						$('#htm_add').window('close');  //关闭添加窗口
						$.messager.alert('提示',result['msg']);  //提示添加信息成功
						$('#htm_table').datagrid('reload');
					} else {
						$('#htm_table').datagrid('loaded');
						$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
					}
				},"json");				
		}
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
	$("#commercial_add").combobox('setValue', '0');
	$("#worldIds_add").focus();  //光标定位
	
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
							myQueryParams.maxSerial = maxSerial;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#activityName_add")
	.formValidator({empty:false,onshow:"请输入简称",onfocus:"设置名称",oncorrect:"设置成功"});
	
	$("#activityTitle_add")
	.formValidator({empty:true,onshow:"请输入标题",onfocus:"允许为空",oncorrect:"设置成功"});
	
	$("#activityDesc_add")
	.formValidator({empty:true,onshow:"请输入描述",onfocus:"允许为空",oncorrect:"设置成功"});
	
	$("#titlePath_add")
	.formValidator({empty:true, onshow:"请输入封面链接",onfocus:"例:http://test.png",oncorrect:"该链接可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#activityLink_add")
	.formValidator({empty:true,onshow:"请输入详情链接",onfocus:"允许为空",oncorrect:"设置成功"});
	
	
	$("#worldId_add")
	.formValidator({empty:true,onshow:"请输入织图ID",onfocus:"设置织图ID",oncorrect:"设置成功"});
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
 * 清空添加页面已经选择的发起人
 */
function clearSponsorsAdd() {
	$("#sponsorIds_add").combogrid('clear');
}


/**
 * 显示评论
 * @param uri
 */
function showLogo() {
	$.fancybox({
		'margin'			: 20,
		'width'				: 980,
		'height'			: 600,
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: 'page_operations_activityLogo'
	});
}

</script>
</head>
<body>
	<div id="main">
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加织图到广场" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(1);" class="easyui-linkbutton" title="批量生效活动！" plain="true" iconCls="icon-ok">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:updateValid(0);" class="easyui-linkbutton" title="批量失效活动！" plain="true" iconCls="icon-tip">批量失效</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排活动排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序</a>
			<a href="javascript:void(0);" onclick="javascript:showLogo();" class="easyui-linkbutton" title="LOGO管理" plain="true" iconCls="icon-edit" id="reSerialBtn">LOGO管理</a>
   		</div>
	</div> 

	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_op/op_saveSquarePushActivity" method="post">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">发起人：</td>
						<td><input id="sponsorIds_add" name="sponsorIds" style="width:205px"/></td>
						<td><a class="easyui-linkbutton" style="vertical-align: middle; margin-bottom: 3px;" title="清空发起人" plain="true" iconCls="icon-remove" onclick="clearSponsorsAdd();"></a></td>
					</tr>
					<tr>
						<td class="leftTd">活动名称：</td>
						<td><input type="text" name="activityName" id="activityName_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="activityName_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">活动标题：</td>
						<td><textarea name="activityTitle" id="activityTitle_add" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="activityTitle_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">活动描述：</td>
						<td><textarea name="activityDesc" id="activityDesc_add" onchange="validateSubmitOnce=true;"></textarea></td>
						<td class="rightTd"><div id="activityDesc_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">封面链接：</td>
						<td><input type="text" name="titlePath" id="titlePath_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="titlePath_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">详情链接：</td>
						<td><input type="text" name="activityLink" id="activityLink_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="aactivityLink_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">活动类型：</td>
						<td>
							<select id="commercial_add" class="easyui-combobox" name="commercial" style="width:100px;">
						        <option value="0" selected="selected">普通活动</option>
						        <option value="1">商业活动</option>
					   		</select>
						</td>
					</tr>
					
					<tr>
						<td class="leftTd">织图ID：</td>
						<td><input type="text" name="worldId" id="worldId_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="worldId_addTip" class="tipDIV"></div></td>
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
		<form id="serial_form" action="./admin_op/op_updateSquarePushActivitySerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">活动ID：</td>
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
	<div id="user_tb" style="padding:5px;height:auto" class="none">
		<input id="ss_user" searcher="searchUser" class="easyui-searchbox" prompt="用户名/ID搜索" style="width:200px;"/>
	</div>
	</div>
	
</body>
</html>