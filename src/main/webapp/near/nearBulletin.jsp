<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附近专题管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
	var maxId = 0,
	cityId = 0,
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'nearBulletin.maxId' : maxId
		}
		loadPageData(initPage);
	},
	hideIdColumn = false,
	htmTableTitle = "附近专题列表", //表格标题
	loadDataURL = "./admin_op/msgBulletin_queryNearBulletin",
	deleteURI = "./admin_op/msgBulletin_deleteCityBulletinByIds?idsStr=", //删除请求地址
	saveURL = "./admin_op/msgBulletin_saveNearBulletin",
	updateByIdURL = "./admin_op/msgBulletin_updateNearBulletin",
	queryByIdURL = "./admin_op/msgBulletin_queryNearBulletinById",
	delByIdURL = "./admin_op/msgBulletin_deleteNearBulletinById",
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['nearBulletin.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['nearBulletin.maxId'] = maxId;
			}
			
			if(cityId == 0) {
				$("#htm_table").datagrid("hideColumn", "ck");
				$("#htm_table").datagrid("showColumn", "opt");
				$("#htm_table").datagrid("showColumn", "cities");
				$("#del-btn").hide();
				$("#add-btn").show();
				$("#reserial-btn").hide();
			} else {
				$("#htm_table").datagrid("showColumn", "ck");
				$("#htm_table").datagrid("hideColumn", "opt");
				$("#htm_table").datagrid("hideColumn", "cities");
				$("#del-btn").show();
				$("#add-btn").hide();
				$("#reserial-btn").show();
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
  			{field: "ck", checkbox:true, hidden: true},
 			{field: "id", title: "ID", align: "center"},
 			{field: "bulletinPath", title: "Banner", align: "center",
 				formatter: function(value,row,index) {
	  				return "<img width='180px' height='100px' class='htm_column_img' src='" + value + "'/>";
 	  			}
 			},
 			{field: "bulletinName", title: "专题名", align: "center"},
 			{field: "bulletinType", title: "跳转类型", align: "center",
 				formatter: function(value,row,index) {
 					switch(value) {
 					case 1:
 						return "网页链接";
 					case 3:
 						return "用户ID";
 					case 4:
 						return "活动标签";
 					}
 					return value;
 	  			}
 			},
 			{field: "link", title: "跳转到", align: "center",
 				formatter: function(value,row,index) {
 	  				return "<a href='"+value+"' target='_blank'>"+value+"</a>";
 	  			}
 			},
 			{field: "cities", title: "所在城市", align: "center"},
 			{field : 'opt',title : '操作',align : 'center',rowspan : 1,
 				formatter : function(value, row, index ) {
 					if(cityId == 0)
 						return "<a title='修改' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row.id + "\",\"" + index + "\"," + true + ")'>【修改】</a>"
 							+ "<a title='删除' class='deleteInfo' href='javascript:void(0);' onclick='javascript:deleteById(\""+ row.id + "\")'>【删除】</a>";
					else
						return "";
 				}
 			}
 		],
 		
 		
	cityMaxId = 0,
	cityQueryParams = {},
	
	editCityMaxId = 0,
	editCityQueryParams = {},
	
	selectedIds = [],
    
    
    onBeforeInit = function() {
		showPageLoading();
	},
	
	onAfterInit = function() {
		
		$('#htm_edit').window({
			title : '添加附近专题',
			modal : true,
			width : 620,
			height : 480,
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
				$("#bulletinImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#thumbImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#bulletinType_edit").combobox("setValue", 1);
				$("#cityIds_edit").combogrid("clear");
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		
		$('#ss-cityId').combogrid({
			panelWidth : 340,
		    panelHeight : 250,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#search-city-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'name',
		    url : './admin_addr/addr_queryCity',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'ID',align : 'center', width : 60},
				{field : 'name',title : '名称',align : 'center', width : 180},
		    ]],
		    queryParams:cityQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > cityMaxId) {
						cityMaxId = data.maxId;
						cityQueryParams['city.maxId'] = cityMaxId;
					}
				}
		    },
		    onChange : function(newValue, oldValue) {
		    	maxId = 0;
		    	cityId = newValue;
		    	myQueryParams = {
	    			'nearBulletin.maxId' : maxId,
	    			'nearBulletin.cityId' : cityId
		    	};
		    	loadPageData(1);
		    }
		});
		var p = $('#ss-cityId').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					cityMaxId = 0;
					cityQueryParams['city.maxId'] = cityMaxId;
				}
			}
		});
		
		$('#cityIds_edit').combogrid({
			panelWidth : 340,
		    panelHeight : 250,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#edit-search-city-tb",
		    multiple : true,
		    required : false,
		   	idField : 'id',
		    textField : 'name',
		    url : './admin_addr/addr_queryCity',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'ID',align : 'center', width : 60},
				{field : 'name',title : '名称',align : 'center', width : 180},
		    ]],
		    queryParams:editCityQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > editCityMaxId) {
						editCityMaxId = data.maxId;
						editCityQueryParams['city.maxId'] = editCityMaxId;
					}
				}
		    },
		});
		var p = $('#cityIds_edit').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					editCityMaxId = 0;
					editCityQueryParams['city.maxId'] = editCityMaxId;
				}
			}
		});
		
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
		
		
		removePageLoading();
		$("#main").show();
	};
	
/**
 * 初始化贴纸信息编辑窗口
 */
function initEditWindow(id, index, isUpdate) {
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改专题');
		$('#htm_edit').window('open');
		$.post(queryByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#bulletinPath_edit").val(obj['bulletinPath']);
				$("#bulletinImg_edit").attr('src', obj['bulletinPath']);
				$("#thumb_edit").val(obj['bulletinThumb']);
				$("#thumbImg_edit").attr('src', obj['bulletinThumb']);
				
				$("#link_edit").val(obj['link']);
				$("#bulletinName_edit").val(obj['bulletinName']);
				
				$("#cityIds_edit").combogrid("setValues", obj["cityIds"]);
				$("#cityIds_edit").combogrid("setText", obj["cities"]);
				
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
		$("#taobaoType_edit").combobox('setValue', 1);
		
		$('#htm_edit').panel('setTitle', '添加专题');
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
								$("#htm_table").datagrid('reload');
							} else {
								maxId = 0;
								myQueryParams['nearBulletin.maxId'] = maxId;
								$("#htm_table").datagrid('load',myQueryParams);
							}
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#bulletinPath_edit")
	.formValidator({empty:false, onshow:"可自定义,上传大图后生成",onfocus:"请输入大图链接",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#thumb_edit")
	.formValidator({empty:false, onshow:"可自定义,上传缩略图后生成",onfocus:"请输入缩略图链接",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#bulletinName_edit")
	.formValidator({empty:true, onshow:"输入专题名,方便搜索哦",onfocus:"请输入专题名",oncorrect:"正确！"});
	
	$("#link_edit")
	.formValidator({empty:false, onshow:"url|用户id|活动标签（必填）",onfocus:"请输入跳转链接",oncorrect:"正确！"});
	
	$("#bulletinType_edit")
	.formValidator({empty:true, onshow:"请选择跳转类型（必选）",onfocus:"请选择跳转类型",oncorrect:"正确！"});
	
	$("#cityIds_edit")
	.formValidator({empty:true, onshow:"请选择城市（可多选）",onfocus:"可多选",oncorrect:"正确！"});
	
}

function searchByName() {
	maxId = 0;
	myQueryParams['nearBulletin.maxId'] = maxId;
	myQueryParams['nearBulletin.bulletinName'] = $('#ss-bulletinName').searchbox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}


function searchCity() {
	cityMaxId = 0;
	cityQueryParams['city.maxId'] = cityMaxId;
	cityQueryParams['city.name'] = $('#city-searchbox').searchbox('getValue');
	$("#ss-cityId").combogrid('grid').datagrid("load",cityQueryParams);
}

function searchEditCity() {
	editCityMaxId = 0;
	editCityQueryParams['city.maxId'] = editCityMaxId;
	editCityQueryParams['city.name'] = $('#edit-city-searchbox').searchbox('getValue');
	$("#cityIds_edit").combogrid('grid').datagrid("load",editCityQueryParams);
}

function deleteById(id) {
	$.messager.confirm('删除记录', '您确定要删除已选中的专题?删除后在所有城市都看不到此专题!', function(r){ 	
		if(r){				
			$.post(delByIdURL, {
				'id' : id
			}, function(result) {
				if(result['result'] == 0) {
					$.messager.alert('提示',"删除成功");
					$("#htm_table").datagrid("reload");
				} else {
					$.messager.alert('提示',result['msg']);
				}
			});				
		}	
	});	
}

function updateSortingCount(count) {
	$("#sorting-count").text(count);
}

function clearSortingCount() {
	$("#sorting-count").text(0);
	commonTools.clearFormData($('#serial_form'));
}

/**
 * 重新排序
 */
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
					myQueryParams['nearBulletin.maxId'] = maxId;
					clearSortingCount();
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
	<div id="main" style="display: none;">
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<a id="add-btn" href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
			<a id="del-btn" style="display:none;" href="javascript:void(0);" onclick="htmDelete('id')" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
			<a id="reserial-btn" style="display:none;" href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" 
				title="重排排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序+<span id="sorting-count">0</span></a>
			<span class="search_label">选择城市：</span>
			<input id="ss-cityId" />
			<input id="ss-bulletinName" searcher="searchByName" class="easyui-searchbox" prompt="输入名字搜索" />
			
			
		</div>
		
		<!-- 添加城市分组窗口 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_op/near_insertNearLabel" method="post">
				<table class="htm_edit_table" width="580px" >
					<tr>
						<td  class="leftTd">上传大图：</td>
						<td>
							<a id="bulletinPath_edit_upload_btn" style="position: absolute; margin:30px 0 0 200px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="bulletinImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="180px" height="90px">
							<div id="bulletinPath_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							规格:1240x640px
						</td>
					</tr>
					
					<tr>
						<td  class="leftTd">上传缩略图：</td>
						<td>
							<a id="thumb_edit_upload_btn" style="position: absolute; margin:20px 0 0 100px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="thumbImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="60px" height="60px">
							<div id="thumb_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							规格:300x300px
						</td>
					</tr>
					
					<tr>
						<td  class="leftTd">大图链接：</td>
						<td>
							<input id="bulletinPath_edit" name="nearBulletin.bulletinPath" style="width:280px;"/>
						</td>
						<td class="rightTd">
							<div id="bulletinPath_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
						
					</tr>
					
					<tr>
						<td  class="leftTd">缩略图链接：</td>
						<td>
							<input id="thumb_edit" name="nearBulletin.bulletinThumb" style="width:280px;"/>
						</td>
						<td class="rightTd">
							<div id="thumb_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">类型：</td>
						<td>
							<select id="bulletinType_edit" name="nearBulletin.bulletinType" class="easyui-combobox" style="width:173px;" >
	        					<option value="1">网页</option>
	        					<option value="3">用户主页</option>
	        					<option value="4">活动页</option>
							</select>
						</td>
						<td>
							<div id="bulletinType_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">链接：</td>
						<td>
							<textarea id="link_edit" name="nearBulletin.link" style="width:280px;" onchange="validateSubmitOnce=true;"></textarea>
						</td>
						<td>
							<div id="link_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">城市：</td>
						<td>
							<input type="text" id="cityIds_edit" name="cityId"  style="width:286px;" onchange="validateSubmitOnce=true;"/>
						</td>
						<td class="rightTd">
							<div id="cityIds_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					
					<tr>
						<td class="leftTd">名字：</td>
						<td>
							<input type="text" id="bulletinName_edit" name="nearBulletin.bulletinName"  onchange="validateSubmitOnce=true;"/>
						</td>
						<td class="rightTd">
							<div id="bulletinName_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					
					<tr style="display:none">
						<td colspan="3">
							<input id="id_edit" name="nearBulletin.id" />
						</td>
					</tr>
					
					<tr style="display:none">
						<td colspan="3">
							<input id="serial_edit" name="nearBulletin.serial" />
						</td>
					</tr>
					
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#edit_form').submit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_edit').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">加载中...</span>
						</td>
					</tr>
					
				</table>
			</form>
		</div>
		
		
		<!-- 重排索引 -->
		<div id="htm_serial">
			<form id="serial_form" action="./admin_op/msgBulletin_updateCityBulletinSerial" method="post">
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
							<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitSerialForm();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-undo" onclick="clearSortingCount()();">清空</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_serial').window('close');">取消</a>
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
		
		<div id="search-city-tb" style="padding:5px;height:auto" class="none">
			<input id="city-searchbox" searcher="searchCity" class="easyui-searchbox" prompt="输入名称搜索" style="width:200px;"/>
		</div>
		
		<div id="edit-search-city-tb" style="padding:5px;height:auto" class="none">
			<input id="edit-city-searchbox" searcher="searchEditCity" class="easyui-searchbox" prompt="输入名称搜索" style="width:200px;"/>
		</div>
		
	</div>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
	<script type="text/javascript">
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'bulletinPath_edit_upload_btn',
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
            	$("#bulletinPath_edit_upload_btn").hide();
            	$("#bulletinImg_edit").hide();
            	var $status = $("#bulletinPath_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#bulletinPath_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#bulletinPath_edit_upload_btn").show();
            	$("#bulletinImg_edit").show();
            	$("#bulletinPath_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#bulletinImg_edit").attr('src', url);
            	$("#bulletinPath_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  // 提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/notice/" + timestamp+suffix;
                return key;
            }
        }
    });
	
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'thumb_edit_upload_btn',
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
            	$("#thumb_edit_upload_btn").hide();
            	$("#thumbImg_edit").hide();
            	var $status = $("#thumb_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#thumb_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#thumb_edit_upload_btn").show();
            	$("#thumbImg_edit").show();
            	$("#thumb_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#thumbImg_edit").attr('src', url);
            	$("#thumb_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  // 提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/notice/" + timestamp+suffix;
                return key;
            }
        }
    });
	
	</script>
</body>
</html>
</html>