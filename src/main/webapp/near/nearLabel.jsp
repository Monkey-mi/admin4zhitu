<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附近标签管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">
	var maxId = 0,
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'nearLabel.maxId' : maxId
		}
		loadPageData(initPage);
	},
	hideIdColumn = false,
	htmTableTitle = "附近标签列表", //表格标题
	loadDataURL = "./admin_op/near_queryNearLabel",
	deleteURI = "./admin_op/near_batchDeleteNearLabel?idsStr=", //删除请求地址
	saveURL = "./admin_op/near_insertNearLabel", // 保存贴纸地址
	updateByIdURL = "./admin_op/near_updateNearLabel", // 更新贴纸地址
	queryByIdURL = "./admin_op/near_queryNearLabelById",
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['nearLabel.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['nearLabel.maxId'] = maxId;
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
    	{field: "ck",checkbox: true},
    	{field: "id",title: "id",align: "center"},
    	/*
    	{field: "cityId",title: "城市id",align: "center"},
    	*/
        {field: "cityName",title: "城市名称",align: "center"},
        {field: "labelName",title: "标签名称",align: "center"},
        /*
        {field: "longitude",title: "经度",align: "center"},
        {field: "latitude",title: "纬度",align: "center"},
        */
        {field: "description",title: "标签描述",align: "center"},
        {field: "bannerUrl",title: "图标",align: "center",
           	formatter: function(value,row,index) {
	  				return "<img title='无效' width='174px' height='90px' class='htm_column_img' src='" + value + "'/>";
	  		}
	 	},
        {field: "serial",title: "序列号",align: "center"},
        {field : 'opt',title : '操作',align : 'center',rowspan : 1,
			formatter : function(value, row, index ) {
				return "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row.id + "\",\"" + index + "\"," + true + ")'>【修改】</a>";
			}
		},
    ],
    
    htmTablePageList = [15,10,20,30,50],
    
    cityMaxId = 0,
	cityQueryParams = {},
	
	editCityMaxId = 0,
	editCityQueryParams = {},
	selectedIds = [],
    
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
			title : '添加标签',
			modal : true,
			width : 650,
			height : 345,
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
				$("#bannerImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#cityId_edit").combogrid('clear');
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
		    	myQueryParams = {
	    			'nearLabel.maxId' : maxId,
	    			'nearLabel.cityId' : newValue
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
		
		$('#cityId_edit').combogrid({
			panelWidth : 340,
		    panelHeight : 250,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#edit-search-city-tb",
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
		var p = $('#cityId_edit').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					editCityMaxId = 0;
					editCityQueryParams['city.maxId'] = editCityMaxId;
				}
			}
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
		$('#htm_edit').panel('setTitle', '修改标签');
		$('#htm_edit').window('open');
		$.post(queryByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#bannerUrl_edit").val(obj['bannerUrl']);
				$("#bannerImg_edit").attr('src', obj['bannerUrl']);
				$("#cityId_edit").combogrid('setValue', obj['cityId']);
				
				$("#labelName_edit").val(obj['labelName']);
				$("#description_edit").val(obj['description']);
				
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
		$("#serial_edit").val(0);
		
		$('#htm_edit').panel('setTitle', '添加标签');
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
								myQueryParams['nearLabel.maxId'] = maxId;
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
	
	$("#bannerUrl_edit")
	.formValidator({empty:false, onshow:"请选banner（必填）",onfocus:"请选banner",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	
	$("#cityId_edit")
	.formValidator({empty:false, onshow:"请选城市（必填）",onfocus:"请选城市",oncorrect:"正确！"});
	
	$("#labelName_edit")
	.formValidator({empty:false, onshow:"请输入名字（必填）",onfocus:"最多30个字符",oncorrect:"正确！"})
	.inputValidator({max:30, onerror : "最多15个字符"});
	
	$("#description_edit")
	.formValidator({empty:true, onshow:"请输入描述（可选）",onfocus:"最多500个字符",oncorrect:"正确！"})
	.inputValidator({max:500, onerror : "最多500个字符"});
}

function searchLabelByName() {
	maxId = 0;
	myQueryParams['nearLabel.maxId'] = maxId;
	myQueryParams['nearLabel.labelName'] = $('#ss-labelName').searchbox('getValue');
	myQueryParams['nearLabel.cityId'] = '';
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
	$("#cityId_edit").combogrid('grid').datagrid("load",editCityQueryParams);
}


function updateSortingCount(count) {
	$("#sorting-count").text(count);
}

function clearSortingCount() {
	$("#sorting-count").text(0);
	clearFormData($('#serial_form'));
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
					myQueryParams['nearLabel.maxId'] = maxId;
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
				<a href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="htmDelete('id')" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
				<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" 
				title="重排排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序+<span id="sorting-count">0</span></a>
				
				<span class="search_label">选择城市：</span>
				<input id="ss-cityId" />
				
				<input id="ss-labelName" searcher="searchLabelByName" class="easyui-searchbox" prompt="输入名字搜索" />
		</div>
		
		<!-- 添加城市分组窗口 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_op/near_insertNearLabel" method="post">
				<table class="htm_edit_table" width="580">
					<tr>
						<td class="leftTd">Banner：</td>
						<td>
							<input id="bannerUrl_edit" name="nearLabel.bannerUrl" class="none" readonly="readonly" />
							<a id="bannerUrl_edit_upload_btn" style="position: absolute; margin:30px 0 0 200px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="bannerImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="174px" height="90px">
							<div id="bannerUrl_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td>
							<div id="bannerUrl_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">城市：</td>
						<td>
							<input type="text" id="cityId_edit" name="nearLabel.cityId"  onchange="validateSubmitOnce=true;"/>
						</td>
						<td class="rightTd">
							<div id="cityId_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">标签名称：</td>
						<td>
							<textarea id="labelName_edit" type="text" name="nearLabel.labelName" style="width:300px;"  onchange="validateSubmitOnce=true;"></textarea>
						</td>
						<td class="rightTd">
							<div id="labelName_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td>
							<textarea id="description_edit" type="text" name="nearLabel.description" style="width:300px;"  onchange="validateSubmitOnce=true;"></textarea>
						</td>
						<td class="rightTd">
							<div id="description_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					
					<tr class="none">
						<td colspan="3"><input type="text" name="nearLabel.id" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
					</tr>
					
					<tr class="none">
						<td colspan="3"><input type="text" name="nearLabel.serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
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
			<form id="serial_form" action="./admin_op/near_updateNearLabelSerial" method="post">
				<table class="htm_edit_table" width="580">
					<tbody>
						<tr>
							<td class="leftTd">标签ID：</td>
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
        browse_button: 'bannerUrl_edit_upload_btn',
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
            	$("#bannerUrl_edit_upload_btn").hide();
            	$("#bannerImg_edit").hide();
            	var $status = $("#bannerUrl_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#bannerUrl_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#bannerUrl_edit_upload_btn").show();
            	$("#bannerImg_edit").show();
            	$("#bannerUrl_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#bannerImg_edit").attr('src', url);
            	$("#bannerUrl_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  // 提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/near/" + timestamp+suffix;
                return key;
            }
        }
    });
	</script>
</body>
</html>
</html>