<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附近织图管理</title>
<jsp:include page="../common/header.jsp"></jsp:include><!--  -->
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<script type="text/javascript">

var	htmTablePageList = [10,20],
	editCityMaxId = 0,
	editCityQueryParams={},
	worldKey = 'id',
	hideIdColumn = false,
	IsCheckFlag = false,
	hideIdColumn = false,
	htmTableTitle = "附近标签列表", //表格标题
	myIdField = "id",
	mySortName = '',
	loadDataURL = "./admin_op/near_queryNearWorldLast",
	deleteURI = "./admin_op/near_batchDeleteNearWorldLast", //删除请求地址
	saveURL = "./admin_op/near_insertNearWorldLast", // 保存贴纸地址
	batchInsertNearWorldFromLastURL = "./admin_op/near_batchInsertNearWorldFromLast",
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxSerial':0
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
			if(myQueryParams.maxSerial < data.maxSerial){
				myQueryParams.maxSerial = data.maxSerial;
			}
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
		{
  			field: "titleThumbPath",
  			title: "预览",
  			align: "center",
  			formatter: function(value,row,index){
  				return "<a title='播放织图' class='updateInfo' href='javascript:commonTools.showWorld(\"" + row.shortLink + "\")'><img width='100px' height='100px' src='" + baseTools.imgPathFilter(value,'../base/images/bg_empty.png') + "' /></a>";
 			}
  		},
		clickCountColumn,
		likeCountColumn,
		commentCountColumn,
		worldURLColumn,
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
			height : 155,
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
		
		$("#main").show();
	}

//打开增加标签织图窗口
function openAddWindow(){
	$('#htm_edit').window('open');
}

//增加标签织图
function addNearWorldLast(){
	var worldId = $('#worldId').val();
	$.post(saveURL,{
		'worldId':worldId
	},function(result){
		if(result['result'] == 0 ){
			$('#htm_edit').window('close');
			$('#htm_table').datagrid("reload");
		}
		$.messager.alert("温馨提示：",result['msg']);
	},"json");
}


//删除专属主题
function deleteNearWorld(){
	var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示", "您确定要删除已选中的城市吗？", function(r){
				if(r){
					var cityIds = [];
					for(var i=0;i<rows.length;i++){
						cityIds[i] = rows[i].id;
					}
					var params = {
							idsStr: cityIds.toString()
						};
					$.post(deleteURI, params, function(result){
						if(result['result'] == 0){
							$.messager.alert("温馨提示","删除" + rows.length + "条记录");
							// 清除所有已选择的记录，避免重复提交id值
							$("#htm_table").datagrid("clearSelections");
							// 批量删除刷新当前页
							$("#htm_table").datagrid("reload");
						}else{
							$.messager.alert(result["msg"]);
						}
					});
				}
			});	
		}else{
			$.messager.alert("温馨提示","请先选择，再执行批量删除操作!");
		}
}


function searchEditCity() {
	editCityMaxId = 0;
	editCityQueryParams['city.maxId'] = editCityMaxId;
	editCityQueryParams['city.name'] = $('#edit-city-searchbox').searchbox('getValue');
	$("#cityId_edit").combogrid('grid').datagrid("load",editCityQueryParams);
}

function searchNearWorldByCityId(){
	var cityId = $("#cityId_edit").combogrid('getValue');
	if(cityId){
		myQueryParams = {
			'cityId' : cityId,
			'maxSerial':0
		}
		$('#htm_table').datagrid("load",myQueryParams);
	}
}

function batchInsertNearWorldFromLast(){
	var rows = $('#htm_table').datagrid('getSelections');	
	if(isSelected(rows)){
		var ids = [];
		for(var i=0;i<rows.length;i+=1){		
			ids[i] = rows[i]['id'];	
		}
		
		$.post(batchInsertNearWorldFromLastURL,{
			'idsStr': ids.toString()
		},function(result){
			if(result['result'] == 0){
				$.messager.alert('提示',"成功添加"+ids.length+"条数据");
			}else{
				$.messager.alert('提示',result['msg']);
			}
		},"json");
	}else{
		$.messager.alert('提示',"请勾选要添加地理位置信息的记录");
	}
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:openAddWindow();" class="easyui-linkbutton" title="添加关系" plain="true" iconCls="icon-add" id="addBtn">添加到备选</a>
			<a href="javascript:void(0);" onclick="javascript:deleteNearWorld();" class="easyui-linkbutton" title="批量删除织图" plain="true" iconCls="icon-cut" id="cutBtn">批量删除</a>
			<a href="javascript:void(0);" onclick="javascript:batchInsertNearWorldFromLast();" class="easyui-linkbutton" title="批量添加织图" plain="true" iconCls="icon-convert" id="convertBtn">批量添加到附近</a>
			<input type="text" id="cityId_edit" name="nearLabel.cityId"  onchange="validateSubmitOnce=true;"/>
			<a href="javascript:void(0);" onclick="javascript:searchNearWorldByCityId();" plain="true" class="easyui-linkbutton" iconCls="icon-search" id="search_btn">查询</a>
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
							<td align="center">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addNearWorldLast();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_edit').window('close');">取消</a>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
	
	<div id="edit-search-city-tb" style="padding:5px;height:auto" class="none">
		<input id="edit-city-searchbox" searcher="searchEditCity" class="easyui-searchbox" prompt="输入名称搜索" style="width:200px;"/>
	</div>
</body>
</html>