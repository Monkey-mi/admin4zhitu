/**
 * htm信息表格增、删、改、查公用JS配置文件
 *
 * 时间：2012-3-23
 * @author zhutianjie
 * @version v1.0
 */

/*
 * 模块配置参数,根据特定模块进行配置
 */
var adminKey = "zhangjiaxing";
var isWindowAdd = true; //是否以窗口信息添加

var htmTableTitle = "htm信息表格"; //表格标题 
var loadDataURL = ""; //表格数据请求Url
var deleteURI = ""; //删除数据请求URI
var viewURI = ""; //查看数据请求URI
var updateURI = ""; //更新数据请求URI
var addTitle = "";
var addWidth = 0; //添加信息宽度
var addHeight = 0; //添加信息高度
var addTitle = ""; //添加信息标题

var viewWidth = 0; //查看信息宽度
var viewHeight = 0; //查看信息高度
var viewTitle = ""; //查看信息标题

var updateWidth = 0; //修改信息宽度
var updateHeight = 0; //修改信息高度
var updateTitle = ""; //修改信息标题
var recordIdKey = "id";
var hideIdColumn = true;

var initPage = 1,	//初始化载入页码
	onBeforeInit = function() {},
	init = function() { //载入表格时的初始化方法
		loadPageData(initPage);
	},
	onAfterInit = function(){};


var formSubmitOnce = true;	//用于限制添加和修改表单只提交一次
var validateSubmitOnce = true; //用于限制验证字段时只提交一次请求,当要验证的字段改变时该值设为true,避免只能验证一次

//表格字段,必须重载
var columnsFields = [];

/*
 * 默认系统常量
 */
var ishtmTablePager = true; //是否分页,默认为true
var isToolBarView = true; //是否需要显示添加、删除按钮
var isPagination = true; //是否支持分页
var htmTableIcon = "icon-search"; //标题图标
var htmTablePageList = [5,10,15,20,25,30]; //页面最大显示记录条数设置
var isRowNumbers = true; //是否显示行号
var mySortName = "id"; //排序字段名
var mySortOrder = "desc"; //排序设置,"asc"--正序和"desc"--倒序
//mishengliang idField没有值时，选择只能为一行
var myIdField = "id"; //id字段
var isRemoteSort = true; //是否远程排序
var isCollapsible = false; //是否可以收缩
var isNowrap = true; //数据是否在一行显示
var isStriped = false; //显示表格条纹
var myQueryParams = {};
var myOnLoadBefore = function() {};
var myOnLoadSuccess = function(data){};
var myOnBeforeRefresh = function(pageNumber, pageSize) {};
var myLoadMsg = "处理中,请等待...",
	myDisplayMsg = '第 {from} 到 {to} 共 {total} 条记录',
	myBeforePageText = "页码",
	myAfterPageText = '共 {pages} 页';
var myRowStyler = function(index,row) {};
var myOnClickCell = function(rowIndex, field, value){};
var myOnCheck = function(rowIndex, rowData){};
var myOnCheckAll = function(rows){};
var myOnUncheck = function(rowIndex, rowData){};
var myOnUncheckAll = function(rows){};
var pageButtons = [];
var myPageSize = 10;

var htmUI = {
		htmWindowAdd : function() {
			initAddWindow();
			// 打开添加窗口
			$("#htm_add").window('open');
			loadAddFormValidate();
		}
	};

//分页组件,可以重载
var toolbarComponent = [{
		id:'btnadd',
		text:'添加',
		iconCls:'icon-add',
		handler:htmUI.htmWindowAdd
	},'-',{
		id:'btncut',
		text:'删除',
		iconCls:'icon-cut',
		handler:function(){
			htmDelete(recordIdKey);
		}
	}];

$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});
	
/**
 * 页面初始化成功后载入表格
 */
$(function() {
	onBeforeInit();
	init();
	// srts添加信息
	if($('#htm_add').length>0){
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
			resizable : false
		});
	}

	// srts查看信息
	if($('#htm_view').length>0){
		$('#htm_view').window({
			title : viewTitle,
			modal : true,
			width : viewWidth,
			height : viewHeight,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-search',
			resizable : false
		});
	}

	// srts修改信息
	if($('#htm_update').length>0){
		$('#htm_update').window({
			title : updateTitle,
			modal : true,
			width : updateWidth,
			height : updateHeight,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false
		});
	}
	onAfterInit();
	
});
		
/**
 * 载入数据
 * @param pageNum 显示的页码
 * 在添加成功后必须调用此方法返回第一页，让用户确定记录已经添加成功
 */
function loadPageData(pageNum) {
	if(!isToolBarView) {
		toolbarComponent = null; //取消添加、删除按钮
	}
	$('#htm_table').datagrid({
		title: htmTableTitle,
		iconCls: htmTableIcon,
		width: $(document.body).width(),
		fitColumns: true,
		autoRowHeight: true,
		url:loadDataURL, //加载数据的URL
		rowStyler:myRowStyler,
		pageList: htmTablePageList,
		onClickCell:onClickCell,
		nowrap: isNowrap,
		striped: isStriped,
		collapsible: isCollapsible,
		sortName: mySortName,
		queryParams:myQueryParams,
		sortOrder: mySortOrder,
		remoteSort: isRemoteSort,
		idField: myIdField,
		rownumbers: isRowNumbers,
		columns:[columnsFields],
		loadMsg: myLoadMsg,
		toolbar: toolbarComponent,
		pagination: isPagination,
		pageNumber: pageNum, //指定当前页面为1
		pageSize: myPageSize,
		onBeforeLoad : myOnLoadBefore,
		onLoadSuccess : myOnLoadSuccess,
		onCheck : myOnCheck,
		onCheckAll : myOnCheckAll,
		onUncheck : myOnUncheck,
		onUncheckAll : myOnUncheckAll
	});
	if(hideIdColumn) {
		$('#htm_table').datagrid('hideColumn', recordIdKey); //隐藏id字段
	}
	var p = $('#htm_table').datagrid('getPager');
	p.pagination({
		beforePageText : myBeforePageText,
		afterPageText : myAfterPageText,
		displayMsg: myDisplayMsg,
		buttons:pageButtons,
		onBeforeRefresh:myOnBeforeRefresh
	});
	
}


/**
 * 以窗口形式添加记录
 */
function htmWindowAdd() {
	initAddWindow();
	// 打开添加窗口
	$("#htm_add").window('open');
	loadAddFormValidate();
}

/**
 * 以窗口形式添加记录
 * @param addURI
 */
function htmWindowAdd(addURI) {
	$.post(addURI,function(result){
		if(result['result'] == 0) {
			initAddWindow(result['msg']);
		} else {
			$.messager.alert('提示',result['msg']);
		}
	},"json");
	// 打开添加窗口
	$("#htm_add").window('open');
	loadAddFormValidate();
}

/**
 * 以窗口形式查看记录
 */
function htmWindowView(viewURI) {
	$.post(viewURI,function(result){
		if(result['result'] == 0) {
			initViewWindow(result['msg']);
		} else {
			$.messager.alert('提示',result['msg']);
		}
	},"json");
	$("#htm_view").window('open');
}

/**
 * 以窗口形式更新记录
 */
function htmWindowUpdate(updateURI) {
	$.post(updateURI,function(result){
		if(result['result'] == 0) {
			initUpdateWindow(result['msg']);
		} else {
			$.messager.alert('提示',result['msg']);
		}
	},"json");
	// 打开更新窗口
	$("#htm_update").window('open');
	loadUpdateFormValidate();
}

/**
 * 数据记录
 */
function htmDelete(idKey) {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(isSelected(rows)){
		$.messager.confirm('删除记录', '您确定要删除已选中的记录?', function(r){ 	
			if(r){				
				var ids = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i][idKey]);	
					rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
				}	
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				$('#htm_table').datagrid('loading');
				$.post(deleteURI + ids,function(result){
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
	}	
}

/**
 * 判断是否选中要删除的记录
 */
function isSelected(rows) {
	if(rows.length > 0){
		return true;
	}else{
		$.messager.alert('操作失败','请先选择记录，再执行操作!','error');
		return false;
	}
}

/**
 * 清空表单数据
 * @param form
 */
function clearFormData(form) {
	$(form).find(':input').each(function() {
		switch (this.type) {
		case 'passsword':
		case 'select-multiple':
		case 'select-one':
		case 'text':
		case 'file':
		case 'textarea':
			$(this).val('');
			break;
		case 'checkbox':
		case 'radio':
			this.checked = false;
		}
	});
}

var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){
    	return true; 
    }
    if ($('#htm_table').datagrid('validateRow', editIndex)){
        $('#htm_table').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

function onClickCell(index, field){
    if (endEditing()){
        $('#htm_table').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
        editIndex = index;
    }
}

function conditionSearch() {
	var searchForm = $("#search_form");
	var searchItems = searchForm.find('input');
	var searchParams = {};
	searchItems.each(function(){
		var name = $(this).attr('name');
		var value = $(this).val();
		if((name && name != "default") && (value && value != "default")) {
			searchParams[name] = value;
		}
	});
	$("#htm_table").datagrid({
		queryParams : searchParams
	});
	
}

/**
 * 更新指定值
 * @param index
 * @param key
 * @param value
 */
function updateValue(index, key, value) {
	var $dg = $("#htm_table");
	$dg.datagrid('rejectChanges'); // 取消所有编辑操作
	$dg.datagrid('unselectAll'); // 取消所有选择
	$dg.datagrid('selectRow', index);
	var data = $dg.datagrid('getSelected');
	data[key] = value;
	$dg.datagrid('updateRow',{
		index: index,
		row: data
	});
	$dg.datagrid('refreshRow',index);
	$dg.datagrid('acceptChanges');
	$dg.datagrid('unselectRow',index);
}

function updateValues(index, keys, values) {
	var $dg = $("#htm_table");
	$dg.datagrid('rejectChanges'); // 取消所有编辑操作
	$dg.datagrid('unselectAll'); // 取消所有选择
	$dg.datagrid('selectRow', index);
	var data = $dg.datagrid('getSelected');
	for(var i = 0; i < keys.length; i++) {
		data[keys[i]] = values[i];
	}
	$dg.datagrid('updateRow',{
		index: index,
		row: data
	});
	$dg.datagrid('refreshRow',index);
	$dg.datagrid('acceptChanges');
	$dg.datagrid('unselectRow',index);
}

function reset(form) {
	form.reset;
}

/**
 * 计算总页数
 * @param pageSize
 * @param total
 */
function calculateTotalPage(pageSize, total) {
	var page = parseInt(total / pageSize);
	if(total % pageSize == 0) {
		return page;
	} else {
		return page + 1;
	}
}

/**
 * 弹窗显示指定URI链接
 * 
 * @param uri
 */
function showURI(uri) {
	$.fancybox({
		'margin'			: 20,
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

/**
 * 显示载入提示
 */
function showPageLoading() {
	var $loading = $("<div></div>");
	$loading.text('载入中...').addClass('page_loading_tip');
	$("body").append($loading);
}

/**
 * 移除载入提示
 */
function removePageLoading() {
	$(".page_loading_tip").remove();
}

