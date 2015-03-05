<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>互动信息管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">
var maxId = 0,
	worldId = 0,
	htmTableTitle = "互动信息列表", //表格标题
	htmTableWidth = 760,
	loadDataURL = "./admin_interact/interact_queryInteractList", //数据装载请求地址
	deleteURI = "./admin_interact/interact_deleteInteract?ids=",
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId,
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
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	};
	//分页组件,可以重载
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : 'ID',align : 'center', width : 45},
		{field : 'worldId',title : '织图ID',align : 'center',width : 80},
		{field : 'labelId',title : '标签类型',align : 'center',width : 80,
			formatter: function(value,row,index){
  				var label;
  				switch(parseInt(value)) {
  				case 2: 
  					label = '穿搭';
  					break;
  				case 3: 
  					label = '美食';
  					break;
  				case 4: 
  					label = '空间';
  					break;
  				case 5: 
  					label = '其他';
  					break;
  				default:
  					label = '旅行';
  					break;
  				}
  				return label;
  			}	
		},
		{field : 'clickCount',title : '播放',align : 'center',width : 80,
			formatter : function(value, row, rowIndex ) {
				if(value > 0) {
					var uri = 'page_interact_interactWorldClick?interactId='+ row[recordIdKey]; //播放管理地址			
					return "<a title='显示播放计划' class='updateInfo' href='javascript:showInteractClick(\""
							+ uri + "\")'>"+value+"</a>";
				}
				return value;
				
			}	
		},
		{field : 'commentCount',title : '评论',align : 'center',width : 80,
			formatter : function(value, row, rowIndex ) {
				if(value > 0) {
					var uri = 'page_interact_interactWorldComment?interactId='+ row[recordIdKey]; //评论管理地址			
					return "<a title='显示评论计划' class='updateInfo' href='javascript:showInteractComment(\""
							+ uri + "\")'>"+value+"</a>";
				}
				return value;
				
			}	
		},
		{field : 'likedCount',title : '喜欢',align : 'center',width : 80,
			formatter : function(value, row, rowIndex ) {
				if(value > 0) {
					var uri = 'page_interact_interactWorldLiked?interactId='+ row[recordIdKey]; //喜欢管理地址			
					return "<a title='显示喜欢计划' class='updateInfo' href='javascript:showInteractLiked(\""
							+ uri + "\")'>"+value+"</a>";
				}
				return value;
				
			}	
		},
		{field : 'duration', title:'为期(小时)', align : 'center',width : 80},
		{field : 'dateAdded', title:'添加日期', align : 'center',width : 150, 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
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
  		},
	],
	
	addWidth = 500, //添加信息宽度
	addHeight = 300, //添加信息高度
	addTitle = "添加互动信息" //添加信息标题
	
	commentMaxId = 0,
	commentQueryParams = {
		'labelId':1
	};
	
//初始化添加窗口
function initAddWindow() {
	$("#htm_add form").show();
	var addForm = $('#add_form');
	$('#htm_add .opt_btn').show();
	$('#htm_add .loading').hide();
	clearFormData(addForm);
	$("#comments_add").combogrid('clear');
	$("#comments_add").combogrid('grid').datagrid('unselectAll')
	$("#ss_comment").searchbox('setValue', "");
	$("#labelId_add").combobox('setValue','1');
	$("#duration_add").val('24');
	
	var labelId = $("#labelId_add").combobox('getValue');
	var labelName = $("#labelId_add").combobox('getText');
	loadComment(labelId,labelName);
	
}

//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$('#htm_add .opt_btn').hide();
				$('#htm_add .loading').show();
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						formSubmitOnce = true;
						$('#htm_add .opt_btn').show();
						$('#htm_add .loading').hide();
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							$('#comments_add').combogrid('clear');
							maxId = 0;
							myQueryParams.maxId = maxId;
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");	
				return false;
			}
		}
	});
	
	$("#worldId_add")
	.formValidator({empty:false,onshow:"请输入织图ID",onfocus:"例如:10012",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#duration_add")
	.formValidator({empty:false,onshow:"小时",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#clickCount_add")
	.formValidator({empty:true,onshow:"请输入播放数",onfocus:"例如:120",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#likedCount_add")
	.formValidator({empty:true,onshow:"请输入喜欢数",onfocus:"例如:120",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#commentCount_add")
	.formValidator({empty:true,onshow:"请输入喜欢数",onfocus:"例如:120",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
}

/**
 * 显示评论
 * @param uri
 */
function showInteractComment(uri) {
	$.fancybox({
		'margin'			: 20,
		'width'				: 968,
		'height'			: 520,
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

function showInteractLiked(uri) {
	$.fancybox({
		'margin'			: 20,
		'width'				: 538,
		'height'			: 520,
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

function showInteractClick(uri) {
	$.fancybox({
		'margin'			: 20,
		'width'				: 538,
		'height'			: 520,
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri
	});
}

function refreshInteract() {
	if(maxId > 0) {
		$.messager.confirm('提示', '确定要刷新互动列表？刷新后所有互动将生效！', function(r){
			if (r){
				$("#htm_table").datagrid('loading');
				$.post("././admin_interact/interact_refreshInteract",{
					'maxId':maxId
					},function(result){
						if(result['result'] == 0) {
							$("#htm_table").datagrid('reload');
						} else {
							$.messager.alert('失败提示',result['msg']);  //提示失败信息
							$("#htm_table").datagrid('loaded');
						}
					},"json");
			}
		});
	}
}

function searchByWID() {
	var worldId = $("#ss_worldId").searchbox('getValue');
	myQueryParams.worldId = worldId;
	$("#htm_table").datagrid("load",myQueryParams);
}

var labels =
[{
		"id":1,
    	"text":"旅行",
    	"selected":true
	},{
	    "id":2,
	    "text":"穿搭"
	},{
	    "id":3,
	    "text":"美食"
	},{
	    "id":4,
	    "text":"空间"
	},{
	    "id":5,
	    "text":"其他"
}];

/**
 * 加载评论
 */
function loadComment(labelId,labelName) {
	$("#selected_comment_label").text(labelName);
	commentMaxId = 0;
	commentQueryParams.maxId = commentMaxId;
	commentQueryParams.labelId = labelId;
	$("#comments_add").combogrid('grid').datagrid('load',commentQueryParams);
}

function searchComment() {
	var comment = $("#ss_comment").searchbox("getValue");
	commentQueryParams.comment = comment;
	var tmp = $("#comments_add").combogrid('getValues');
	$("#comments_add").combogrid('grid').datagrid('load',commentQueryParams);
	$("#comments_add").combogrid('setValues',tmp);
}

$(document).ready(function() {
	$("#labelId_add").combobox({
		url:'./admin_interact/comment_queryAllLabel',
		valueField: 'id',
        textField: 'labelName',
		onSelect:function(rec) {
			loadComment(rec.id,rec.labelName);
		}
	});
	
	$('#comments_add').combogrid({
		panelWidth:680,
	    panelHeight:280,
	    loadMsg:'加载中，请稍后...',
		pageList: [10,20],
		editable: false,
	    multiple: true,
	    toolbar:"#comment_tb",
	    idField:'id',
	    textField:'id',
	    url:'./admin_interact/comment_queryCommentListByLabel',
	    pagination:true,
	    columns:[[
			{field : 'ck',checkbox : true },
	        {field:'id',title:'ID',width:60},
	        {field:'content',title:'内容',width:400},
	        {field:'count',title:'使用数',width:80,align:'center'},
	        {field:'labelId',title:'标签类型',width:80,align:'center',
	        	formatter: function(value,row,index){
	  				var label;
	  				switch(parseInt(value)) {
	  				case 2: 
	  					label = '穿搭';
	  					break;
	  				case 3: 
	  					label = '美食';
	  					break;
	  				case 4: 
	  					label = '空间';
	  					break;
	  				case 5: 
	  					label = '其他';
	  					break;
	  				default:
	  					label = '旅行';
	  					break;
	  				}
	  				return label;
	  			}
	        }
	    ]],
	    queryParams:commentQueryParams,
	    onLoadSuccess:function(data) {
	    	if(data.result == 0) {
				if(data.maxId > commentMaxId) {
					commentMaxId = data.maxId;
					commentQueryParams.maxId = commentMaxId;
				}
			}
	    },
	    onChange:function(record) {
	    	var len = $('#comments_add').combogrid('getValues').length,
	   			$selectCount = $("#selected_comment_count");
	    	$selectCount.text(len);
	    }
	});
})
</script>

</head>
<body>
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加织图到互动列表" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:refreshInteract();" class="easyui-linkbutton" title="更新互动列表" plain="true" iconCls="icon-reload" id="refreshBtn">更新列表</a>
			<div style="display: inline; float:right;">
		        <input id="ss_worldId" class="easyui-searchbox" searcher="searchByWID" prompt="输入织图ID搜索" style="width:200px;" />
			</div>
   		</div>
	</div>  
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_interact/interact_saveInteract" method="post" class="none">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td><input type="text" name="worldId" id="worldId_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="worldId_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">标签：</td>
						<td colspan="2">
					        <input id="labelId_add" name="labelId" style="width:205px;" />
						</td>
					</tr>
					<tr>
						<td class="leftTd">为期：</td>
						<td><input id="duration_add" name="duration"  /></td>
						<td class="rightTd"><div id="duration_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">播放数：</td>
						<td><input type="text" name="clickCount" id="clickCount_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="clickCount_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">喜欢数：</td>
						<td><input type="text" name="likedCount" id="likedCount_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="likedCount_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">评论数：</td>
						<td><input type="text" name="commentCount" id="commentCount_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="commentCount_addTip" class="tipDIV"></div></td>
					</tr>					
					<tr>
						<td class="leftTd"><span id="selected_comment_label"></span>评论：</td>
						<td><input type="text" name="comments" id="comments_add" style="width:205px;"/></td>
						<td class="rightTd"><div id="comments_addTip" class="tipDIV">已选：<span id="selected_comment_count">0</span></div></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<div id="comment_tb" style="padding:5px;height:auto" class="none">
		<div>
			<input id="ss_comment" searcher="searchComment" class="easyui-searchbox" prompt="搜索评论" style="width:200px;"></input>
   		</div>
	</div>
</body>
</html>