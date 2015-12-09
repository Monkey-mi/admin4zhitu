<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附近织图管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<script type="text/javascript">

var	htmTablePageList = [10,20],
	worldKey = 'id',
	hideIdColumn = false,
	IsCheckFlag = false,
	hideIdColumn = false,
	htmTableTitle = "附近标签列表", //表格标题
	myIdField = "id",
	mySortName = '',
	loadDataURL = "./admin_op/near_queryNearWorld",
	deleteURI = "./admin_op/near_batchDeleteNearWorld", //删除请求地址
	saveURL = "./admin_op/near_addNearWorld", // 保存贴纸地址
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'cityId' : 21760
		}
		loadPageData(initPage);
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			myQueryParams.maxSerial = 0;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			myQueryParams.maxSerial = data.maxSerial;
		}
	},
	
	
	columnsFields = [
	    {field:'ck',checkbox:true},            
		{field : 'id',title : '织图ID', hidden:false},
		
		{field : 'phoneCode',title : '客户端',align : 'center',
			formatter: function(value,row,index){
				var phone = "IOS";
				if(row.userInfo.phoneCode == 1) {
					phone = '安卓';
				}
				return "<span class='updateInfo' title='版本号:"+row.appVer+" || 系统:"+row.phoneSys+" v"+row.phoneVer+"'>" 
							+ phone + "</span>";
			}
		},
		{field : 'userAvatar',title : '头像',align : 'left', 
			formatter: function(value, row, index) {
				userId = row['authorId'];
				var uri = 'page_user_userInfo?userId='+ userId;
				imgSrc = baseTools.imgPathFilter(row.userInfo.userAvatar,'../base/images/no_avatar_ssmall.jpg'),
				content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
				return "<a  class='updateInfo' href='javascript:showUserInfo(\""+uri+"\")'>"+"<span>" + content + "</span>"+"</a>";	
			}
		},
		{field:'authorId', title:'作者id',align:'center',
			formatter:function(value,row,index){
				return "<a title='添加等级用户' class='updateInfo' href='javascript:initUserLevelAddWindow(\""+ value + "\",\"" + index + "\")'>"+value+"</a>";
			}
		},
		{field : 'userName',title : '作者',align : 'center',
			formatter : function(value, row, rowIndex) {
				return row.userInfo.userName;
			}
		},
		clickCountColumn,
		likeCountColumn,
		commentCountColumn,
		{field : 'worldDesc',title : '织图描述',align : 'center', width : 100,
			formatter: function(value,row,index){
				var uri = 'page_user_userInfo?userId=';
				var preValue = value;
				var arr = [];
				var worldId = row['worldId']?row['worldId']:row['id'];
				var regDemo = new RegExp("@.+? ","g");
				
				if(value != null && value != '') {
					//如果评论中有被@的人，则需要去获取被@人的数据
					if(regDemo.exec(value) != null){
						$.ajax({ 
							  type: 'POST', 
							  url: "./admin_ztworld/ztworld_queryCommentAt", 
							  data: {worldId:worldId}, 
							  success: function(data){
									if(data.result == 0){
										var result = data['obj'];
										arr = 	value.match(regDemo);
										if(result.length != 0){ //防止从微博中复制过来但又检测到有@格式,且数据库中没有此被@人的数据。
											for(var i = 0; i < arr.length; i++){
												value = value.replace(arr[i],"<a onmouseover='setAuthorAvatarTimer(" + result[i].atId + ",event);' onmouseout='javascript:clearAuthorAvatarTimer();'  class='updateInfo' href='javascript:showUserInfo(\""+uri+result[i].atId+"\")'>" + arr[i] + "</a>");
											}											
										}
									}else{
										$.messager.alert("数据返回错误");
									}
								}, 
							  dataType: "json", 
							  async:false 
							}); 
					}
					return "<div title=" + preValue + " class='viewInfo easyui-tooltip'>" + value + "</div>";
				}
				return '';
			}
		},
		{
  			field: "titleThumbPath",
  			title: "预览",
  			align: "center",
  			formatter: function(value,row,index){
  				return "<a title='播放织图' class='updateInfo' href='javascript:commonTools.showWorld(\"" + row.shortLink + "\")'><img width='60px' height='60px' src='" + baseTools.imgPathFilter(value,'../base/images/bg_empty.png') + "' /></a>";
 			}
  		}
	];

	 onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		removePageLoading();
		$('#htm_edit').window({
			title: '添加织图',
			modal : true,
			width : 300,
			height : 205,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				$("#edit_form .opt_btn").show();
				$("#edit_form .loading").hide();
				
				$('#edit_form').form('reset');
				$("#edit_loading").show();
			}
		});
		
		$("#main").show();
	}

//打开增加标签织图窗口
function openAddWindow(){
	$('#htm_edit').window('open');
}

//增加标签织图
function addWorldLabel(){
	var worldAuthorId = 0;
	var nearLabelId = $('#labelName').combogrid('getValue');
	var worldId = $('#worldId').val();
	$.post(saveNearLabelWorld,{
		'nearLabelId':nearLabelId,
		'worldId':worldId,
		'worldAuthorId':worldAuthorId
	},function(result){
			$('#htm_edit').window('close');
			$.messager.alert("温馨提示：","添加成功！");
			$('#htm_table').datagrid("reload");
	},"json");
}


//删除专属主题
function deleteNearWorld(){
	var ids = [];
	var rows = $('#htm_table').datagrid('getSelections');
	for(var i = 0;i < rows.length; i++){
		ids.push(rows[i]['id']);	
	}
	ids = ids.join();
	
	$.post(deleteNearLabelWorld,{
		'idsStr':ids
	},
	function(result){
		$.messager.alert("温馨提示：","删除成功！");
		$('#htm_table').datagrid("reload");
		$('#htm_table').datagrid("clearSelections");
	},"json");
}


</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:openAddWindow();" class="easyui-linkbutton" title="添加关系" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:deleteNearWorld();" class="easyui-linkbutton" title="批量删除织图" plain="true" iconCls="icon-cut" id="cutBtn">批量删除</a>
			<a href="javascript:void(0);" onclick="javascript:searchWorldByLabel();" plain="true" class="easyui-linkbutton" iconCls="icon-search" id="search_btn">查询</a>
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit" align="center">
			<form id="edit_form" >
				<table id="htm_edit_table" style="width:250px;line-height:40px;">
					<tbody>
						<tr>
							<td align="center">织图ID:<input id="worldId" name="worldId" style="width:100px" /></td>
						</tr>
						<tr>
							<td align="center">附近标签:<input id="labelName" name="labelName" style="width:100px" /></td>
						</tr>						
						<tr>
							<td align="center">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addWorldLabel();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_edit').window('close');">取消</a>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	
</body>
</html>