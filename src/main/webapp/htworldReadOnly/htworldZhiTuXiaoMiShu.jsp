<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小秘书管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js"></script>
<script type="text/javascript">
	var maxId=0,
	loadDataURL = "./admin_ztworld/zhituxiaomishu_queryWorld",
	deleteURI = "./admin_ztworld/zhituxiaomishu_delWorldByWId?idsStr=",
	htmTableTitle = "小秘书维护", //表格标题
	htmTableWidth = 600,
	addWidth = 500, //添加信息宽度
	addHeight = 170, //添加信息高度
	addTitle = "添加小秘书织图", //添加信息标题
	
	init = function() {
		toolbarComponent = '#tb',
		myQueryParams = {
			'maxId' : maxId
		},
		loadPageData(initPage);
	},
	myOnLoadBefore = function() {
		interacts = {};
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
	},
	columnsFields = [
	         		{field : 'ck',checkbox : true},
	         		{field : recordIdKey,title : 'id',align : 'center', width : 120},
	         		phoneCodeColumn,
	         		authorAvatarColumn = {field : 'authorAvatar',title : '头像',align : 'left',width : 45, 
						formatter: function(value, row, index) {
							userId = row['authorId'];
							var uri = 'page_user_userInfo?userId='+ userId;
							imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
								content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
							if(row.star >= 1) {
								content = content + "<img title='" + row['verifyName'] + "' class='avatar_tag' src='" + row['verifyIcon'] + "'/>";
							}
							return "<a class='updateInfo' href='javascript:void(0);)'>"+"<span>" + content + "</span>"+"</a>";	
						}
					},
	         		worldURLColumn,
	         		{field : 'worldDesc',title : '织图描述',align : 'center',width : 80, 
	        			formatter: function(value,row,index){
	        				if(value != null && value != '') {
	        					return "<a href='#' title=" + value + " class='viewInfo easyui-tooltip' onclick='initEditDesc(\""+row[worldKey]+"\",\""+value+"\")'>" + value + "</a>";
	        				}
	        				img = "./common/images/edit_add.png";
	        				return "<img title='编辑描述' class='htm_column_img pointer' onclick='initEditDesc(\""+row[worldKey]+"\",\""+value+"\")' src='" + img + "'>";
	        			}
	        		},
	         		titleThumbPathColumn,
	         		{field : 'worldLabel',title : '标签',align : 'center',width : 80, 
	        			formatter: function(value,row,index){
	        				if(value != null && value != '') {
	        					return "<a href='#' title=" + value + " class='viewInfo easyui-tooltip' onclick='initEditLabel(\""+row[worldKey]+"\",\""+value+"\")'>" + value + "</a>";
	        				}
	        				img = "./common/images/edit_add.png";
	        				return "<img title='编辑描述' class='htm_column_img pointer' onclick='initEditLabel(\""+row[worldKey]+"\",\""+value+"\")' src='" + img + "'>";
	        			}
	        		}
	         	],
	         	
	         	onBeforeInit = function() {
	        		showPageLoading();
	        	},
	        	onAfterInit = function() {
	        		$('#edit_desc').window({
	        			title : '织图描述编辑',
	        			modal : true,
	        			width : 400,
	        			height : 245,
	        			shadow : false,
	        			closed : true,
	        			minimizable : false,
	        			maximizable : false,
	        			collapsible : false,
	        			iconCls : 'icon-tip',
	        			resizable : false
	        		});
	        		
	        		$('#edit_label').window({
	        			title : '织图标签编辑',
	        			modal : true,
	        			width : 400,
	        			height : 155,
	        			shadow : false,
	        			closed : true,
	        			minimizable : false,
	        			maximizable : false,
	        			collapsible : false,
	        			iconCls : 'icon-tip',
	        			resizable : false
	        		});
	        		
	        		removePageLoading();
	        		$("#main").show();
	        	};
	        	
	        	
	function initEditDesc(worldId,desc){
		var editDescForm = $("#edit_desc_form");
		clearFormData(editDescForm);
		$("#edit_desc").window('open');
		$("#edit_desc_worldId").val(worldId);
		$("#desc").val(desc);
		$("#edit_desc form").show();
		$("#edit_desc opt_btn").show();
	}
	
	function initEditLabel(worldId,worldLabel){
		var editLabelForm = $("#edit_label_form");
		clearFormData(editLabelForm);
		$("#edit_label").window('open');
		$("#worldLabel").val(worldLabel);
		$("#edit_label_worldId").val(worldId);
		$("#edit_label form").show();
		$("#edit_label opt_btn").show();
	}
	        	
	//初始化添加窗口
	function initAddWindow() {
		$("#htm_add form").show();
		var addForm = $('#add_form');
		$('#htm_add .opt_btn').show();
		$('#htm_add .loading').hide();
		clearFormData(addForm);
	}
	
		function loadAddFormValidate() {
			var addForm = $("#add_form");
			formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
			$.formValidator.initConfig({
				formid:addForm.attr("id"),
				onsuccess : function() {
					if(formSubmitOnce == true){
						formSubmitOnce = false;
						$('#htm_add .opt_btn').hide();
						$('#htm_add .loading').show();
						$.post(addForm.attr("action"),addForm.serialize(),
								function(result){
							formSubmitOnce=true;
							$('#htm_add .opt_btn').show();
							$('#htm_add .loading').hide();
							if(result['result'] == 0) {
								$('#htm_add').window('close');  //关闭添加窗口
								$.messager.alert('提示',result['msg']);  //提示添加信息成功
								clearFormData(addForm);  //清空表单数据							
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
		}
		
		function labelFormSubmit(){
			var editLabelForm = $("#edit_label_form");
			$.post(editLabelForm.attr("action"),editLabelForm.serialize(),
					function(result){
				if(result['result'] == 0){
					$('#edit_label').window('close');  //关闭添加窗口
					$.messager.alert('提示',result['msg']);  //提示添加信息成功
					clearFormData(editLabelForm);  //清空表单数据							
					maxId = 0;
					myQueryParams.maxId = maxId;
					loadPageData(1); //重新装载第1页数据
				}else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			},"json");
			return false;
		}
		
		function DescFormSubmit(){
			var descForm = $("#edit_desc_form");
			$.post(descForm.attr("action"),descForm.serialize(),
					function(result){
				if(result['result'] == 0){
					$('#edit_desc').window('close');  //关闭添加窗口
					$.messager.alert('提示',result['msg']);  //提示添加信息成功
					clearFormData(descForm);  //清空表单数据							
					maxId = 0;
					myQueryParams.maxId = maxId;
					loadPageData(1); //重新装载第1页数据
				}else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			},"json");
			return false;
		}
	         	
</script>
<style type="text/css">
	.leftTd{width:60px;}
	.rightTd{width:200px;}
	.rightTd input{width:200px;}
	#desc{width:200px;height:100px;margin:0;padding:0;}
	#worldLabel{width:204px;height:24px;}
</style>
</head>
<body>
	<div id="main">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="长处用户等级" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
   		</div>
	</div> 
	
	<!-- 添加记录 -->
	<div id="htm_add">
		<form action="./admin_ztworld/zhituxiaomishu_saveWorld" id="add_form" class="none" method="post">
			<table id="htm_edit_table" width="380">
			  <tbody>
				<tr>
					<td class="leftTd"> 织图id：</td>
					<td><input name="worldId"/></td>
					<td><div></div></td>
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
	
	<div id="edit_desc">
		<form action="./admin_ztworld/zhituxiaomishu_updateWorldDescByWid" id="edit_desc_form" class="none" method="post">
			<table id="edit_desc_table" width="380">
				<tbody>
					<tr>
						<td class="leftTd">织图id：</td>
						<td class="rightTd"><input name="worldId" readonly="readonly" id="edit_desc_worldId"/></td>
					</tr>
					<tr>
						<td class="leftTd">织图描述：</td>
						<td class="rightTd"><textarea name="worldDesc" id="desc"></textarea></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="DescFormSubmit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#edit_desc').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<div id="edit_label">
		<form action="./admin_ztworld/zhituxiaomishu_updateWorldLabelByWid" id="edit_label_form" class="none" method="post">
			<table id="edit_label_table" width="380">
				<tbody>
					<tr>
						<td class="leftTd">织图id：</td>
						<td class="rightTd"><input name="worldId" readonly="readonly" id="edit_label_worldId"/></td>
					</tr>
					<tr>
						<td class="leftTd">织图标签：</td>
						<td class="rightTd">
							<select name="worldLable" id="worldLabel">
								<option value=""></option>
								<option value="旅游,travel">旅游,Travel</option>
								<option value="故事,story">故事,Story</option>
								<option value="心情,Mood">心情,Mood</option>
								<option value="美食,Food">美食,Food</option>
								<option value="穿搭,Outfit">穿搭,Outfit</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="labelFormSubmit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#edit_label').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	</div>
</body>
</html>