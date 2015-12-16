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
	htmTableTitle = "用户互动信息列表", //表格标题
	loadDataURL = "./admin_interact/interact_queryUserInteractList", //数据装载请求地址
	deleteURI = "./admin_interact/interact_deleteUserInteract?ids=",
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
		{field : 'userId',title : '用户ID',align : 'center',width : 80},
		{field : 'followCount',title : '粉丝',align : 'center',width : 80,
			formatter : function(value, row, rowIndex ) {
				if(value > 0) {
					var uri = 'page_interact_interactUserFollow?interactId='+ row[recordIdKey]; //播放管理地址			
					return "<a title='显示粉丝计划' class='updateInfo' href='javascript:showInteractFollow(\""
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
	addHeight = 190, //添加信息高度
	addTitle = "添加用户互动信息"; //添加信息标题
	
	
//初始化添加窗口
function initAddWindow() {
	$('#htm_add .opt_btn').show();
	$('#htm_add .loading').hide();
	var addForm = $('#add_form');
	commonTools.clearFormData(addForm);
	$("#duration_add").val('24');
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
							commonTools.clearFormData(addForm);  //清空表单数据	
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
	
	$("#userId_add")
	.formValidator({empty:false,onshow:"请输入用户ID",onfocus:"例如:10012",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#duration_add")
	.formValidator({empty:false,onshow:"小时",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#followCount_add")
	.formValidator({empty:true,onshow:"请输入粉丝数",onfocus:"例如:120",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
}

function showInteractFollow(uri) {
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

</script>

</head>
<body>
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加用户到互动列表" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
   		</div>
	</div>  
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_interact/interact_saveUserInteract" method="post">
			<table id="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">用户ID：</td>
						<td><input type="text" name="userId" id="userId_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="userId_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">粉丝数：</td>
						<td><input type="text" name="followCount" id="followCount_add" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="followCount_addTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">为期：</td>
						<td><input id="duration_add" name="duration"  /></td>
						<td class="rightTd"><div id="duration_addTip" class="tipDIV"></div></td>
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
	
</body>
</html>