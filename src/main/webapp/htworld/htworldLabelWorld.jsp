<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String labelId = request.getParameter("labelId");
	String labelName = new String(request.getParameter("labelName").getBytes(), "UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=labelName%></title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js"></script>
<script type="text/javascript">
var maxId = 0,
	labelId = <%=labelId%>,
	htmTableTitle = "<%=labelName%>", //表格标题
	worldKey = 'id',
	channelQueryParam={},
	loadDataURL = "./admin_ztworld/label_queryLabelWorld?valid=1", //数据装载请求地址
	deleteURI = "./admin_ztworld/label_deleteLabelWorld?labelId="+labelId+"&valid=0&ids="; //删除请求地址
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId,
			'labelId' : labelId
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
	myRowStyler = function(index,row){
		if(row.interacted)
			return interactedWorld;
		return null;
	},
	//分页组件,可以重载
	columnsFields = [
		{field : 'ck',checkbox : true},
		{field : recordIdKey,title : 'id',align : 'center', width : 120},
		phoneCodeColumn,
		authorAvatarColumn,
		authorIdColumn,
		authorNameColumn,
		clickCountColumn,
		likeCountColumn,
		commentCountColumn,
		worldURLColumn,
		worldIdColumn,
		worldDescColumn,
		titleThumbPathColumn,
		worldLabelColumn,
		dateModified
	],
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
	
		$('#ss-channel').combogrid({
			panelWidth : 490,
		    panelHeight : 490,
		    loadMsg : '加载中，请稍后...',
		    multiple : false,
		    required : false,
		   	idField : 'channelId',
		    textField : 'channelName',
		    url : './admin_op/v2channel_queryOpChannelByAdminUserId',
		    pagination : true,
		    remoteSort : false,
		    columns:[[
				{field : 'channelId',title : 'ID', align : 'center',width : 60},
				{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
					formatter:function(value,row,index) {
						return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				},
				{field : 'channelName',title : '频道名称',align : 'center',width : 120},
				{field : 'channelName',title : '频道名称',align : 'center',width : 120},
				{field : 'channelDesc',title : '频道描述',align : 'center',width : 120},
			]],
			queryParams:channelQueryParam,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > channelMaxId) {
						channelMaxId = data.maxId;
						channelQueryParam.maxId = channelMaxId;
					}
				}
		    }
		});
		
		var p = $('#ss-channel').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					channelMaxId = 0;
					channelQueryParams['channel.maxId'] = userMaxId;
				}
			}
		});
		
		$("#addWorldToChannelDiv").window({
			modal : true,
			width : 350,
			top : 10,
			height : 150,
			title : '添加织图到频道',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$("#ss-channel").combogrid('setValue','');
			}
		});
		removePageLoading();
		$("#main").show();
	};
	
/**
 * 显示评论
 * @param uri
 */
function showComment(realUri,worldId) {
	var url="./admin_interact/interact_queryIntegerIdByWorldId";
	var uri;
	$.post(url,{'worldId':worldId},function(result){
		if(result['interactId']){
			uri = realUri+"&interactId="+result['interactId'];
		}else{
			uri = realUri;
		}
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
		return false;
	},"json");
}

/**
*显示用户信息
*/
function showUserInfo(uri){
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

function addLatestValid(worldId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/ztworld_updateLatestValid",{
		'id':worldId,
		'valid':1
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'latestValid','1');	
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

function removeLatestValid(worldId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/ztworld_updateLatestValid",{
		'id':worldId,
		'valid':0
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'latestValid','0');	
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

function addToChannelInit(){
	$('#addWorldToChannelDiv').window('open');
}

function addWorldToChannelSubmit(){
	var rows = $('#htm_table').datagrid('getSelections');	
	var channelId = $("#ss-channel").combogrid('getValue');
		if(rows.length > 0){
			var ids = [];
			for(var i=0;i<rows.length;i+=1){		
				ids.push(rows[i]['worldId']+'-'+rows[i]['authorId']);
			}	
			$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
			$('#htm_table').datagrid('loading');
			$.post("./admin_op/v2channel_batchInsertWorldToChannel?worldAndAuthorIdsStr=" + ids,{
				'channelId':channelId
			},function(result){
				$('#htm_table').datagrid('loaded');
				$.messager.alert('提示',result['msg']);
				$('#addWorldToChannelDiv').window('close');
			});				
		}else{
			$.messager.alert('更新失败','请先选择记录，再执行更新操作!','error');
		}
}

</script>
</head>
<body>
	<div id="main">
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" style="vertical-align:middle;" title="删除织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:addToChannelInit();" class="easyui-linkbutton" style="vertical-align:middle;" title="添加到频道" plain="true" iconCls="icon-add" id="addToChannelBtn">添加到频道</a>
   		</div>
	</div>  
	
	<!-- 调教机器人 -->
	<div id="addWorldToChannelDiv" >
		<form  id="addWorldToChannelForm"  method="post">
			<table id="html_addWorldToChannel_table" width="320px">
				<tbody>
					<tr>
						<td  colspan="2" style="text-align: center;padding-top: 10px;">添加所选中的织图到频道中？</td>
					</tr>
					<tr>
						<td class="leftTd" >频道:</td>
						<td><input id="ss-channel" style="width:200px;" ></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addWorldToChannelSubmit();">确定</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#addWorldToChannelDiv').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	</div>
	
</body>
</html>