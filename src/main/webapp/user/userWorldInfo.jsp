<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String userId=request.getParameter("userId"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户织图列表</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">
var userId = <%=userId%>;
var maxId = 0,
	init = function() {
		myQueryParams = {
			'maxId' : maxId,
			'authorName':userId
		},
		myLoadDataPage(initPage);
	},
	myRowStyler = function(index, row) {
		if(row.interacted == 1) {
			return 'background-color:#e3fbff';
		}
	},
	htmTableTitle = "用户织图列表", //表格标题
	loadDataURL = "./admin_ztworld/ztworld_queryHTWorldList", //数据装载请求地址,若userId为空的时候，就显示全部用户，若userId不为空，则显示该用户
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
			{field : 'id',title:'id',align : 'center', width : 60},
			{field : 'authorAvatar',title : '头像',align : 'left',width : 45, 
				formatter: function(value, row, index) {
					imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg');
					return "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
				}
			},
			{field:'authorId', title:'作者id',align:'center',width:60},
			{field : 'authorName',title : '作者',align : 'center',width : 100},
			{field : 'userlevel',title:'用户等级',align : 'center',width:60,
				formatter:function(value,row,index){
					if(value){
						return value.level_description;
					}else{
						return '';
					}
				}
			},
			{field : 'clickCount',title:'播放数',align : 'center', sortable: true, width:60, editor:'text'},
			likeCountColumn,
			commentCountColumn,
			worldURLColumn,
			worldIdColumn,
			worldDescColumn,
			titleThumbPathColumn,
			{field : 'channelName',title :'频道',align : 'center',width:100,
	  			formatter: function(value,row,index) {
	  				if(value == "NO_EXIST" || value=="") {
	  					img = "./common/images/edit_add.png";
	  					return "<img title='已添加' class='htm_column_img'  src='" + img + "' onclick='saveToChannelWorld(\""+row.worldId+"\",\""+index +"\")'/>";
	  				}
	  				
	  				return value;
	  			}
			},
			worldLabelColumn,
			{field : 'activityrecd',title : '活动',align : 'center',width : 45,
				styler: function(value,row,index){
					if (value == 1){
						return 'background-color:#fdf9bb;';
					}
				}
			},
			dateModified
		],
	htmTablePageList = [50,100,150],
    onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$("#htm_channel").window({
			title : '频道织图添加',
			modal : true,
			width : 420,
			height : 150,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function(){
				$("#channelId").combobox('clear');
				$("#worldId_channel").val('');
				$("#rowIndex").val('');
			}
		});
		removePageLoading();
		$("#htm_userWorldInfo_main").show();
	};
	
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


function myLoadDataPage(pageNum){
	$('#htm_userWorldInfo_table').datagrid({
		title: htmTableTitle,
		iconCls: htmTableIcon,
		width: $(document.body).width(),
		url:loadDataURL, //加载数据的URL
		rowStyler:myRowStyler,
		pageList: htmTablePageList,
		onClickCell:onClickCell,
		nowrap: isNowrap,
		striped: isStriped,
		collapsible: isCollapsible,
//		sortName: mySortName,
		queryParams:myQueryParams,
//		sortOrder: mySortOrder,
//		remoteSort: isRemoteSort,
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
//		onCheck : myOnCheck,
//		onCheckAll : myOnCheckAll
	});
	if(hideIdColumn) {
		$('#htm_userWorldInfo_table').datagrid('hideColumn', recordIdKey); //隐藏id字段
	}
	var p = $('#htm_userWorldInfo_table').datagrid('getPager');
	p.pagination({
		beforePageText : "页码",
		afterPageText : '共 {pages} 页',
		displayMsg: '第 {from} 到 {to} 共 {total} 条记录',
		buttons : [{
	        iconCls:'icon-save',
	        text:'保存',
	        handler:function(){
	        	endEditing();
	        }
	    },{
	        iconCls:'icon-undo',
	        text:'取消',
	        handler:function(){
	        	$("#htm_userWorldInfo_table").datagrid('rejectChanges');
	        	$("#htm_userWorldInfo_table").datagrid('loaded');
	        }
	    }],
		onBeforeRefresh:myOnBeforeRefresh
	});
}
function saveToChannelWorld(worldId,index){
	$("#rowIndex").val(index);
	$("#worldId_channel").val(worldId);
	$('#htm_channel .opt_btn').hide();
	$('#htm_channel .loading').show();
	$("#htm_channel").window('open');
	$('#htm_channel .opt_btn').show();
	$('#htm_channel .loading').hide();
	
}
function saveChannelWorldSubmit(){
	var index = $("#rowIndex").val();
	var channelName = $("#channelId").combobox('getText');
	var channelId = $("#channelId").combobox('getValue');
	var worldId = $("#worldId_channel").val();
	//成为频道用户
	$.post("./admin_op/chuser_addChannelUserByWorldId",{
		'worldId':worldId,
		'channelId':channelId
	},function(result){
		return false;
	},"json");
	//该织图进入频道
	$.post("./admin_op/channel_saveChannelWorld",{
		'world.channelId':channelId,
		'world.worldId'  :worldId,
		'world.valid'	 :0,
		'world.notified' :0
	},function(result){
		if(result['result'] == 0){
			updateValue(index,'channelName',channelName);
		}else{
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
		$("#htm_channel").window('close');
		return false;
	},"json");
	
}


</script>
</head>
<body>
	<div id="htm_userWorldInfo_main" style="display: none;">
	<table id="htm_userWorldInfo_table"></table>
	<!-- 添加到频道 -->
	<div id="htm_channel">
		<span id="channel_loading" style="margin:60px 0 0 220px; position:absolute; display:none">加载中...</span>
		<form id="channel_form" action="./admin_op/channel_saveChannelWorld" method="post" >
			<table class="htm_edit_table" width="400">
				<tbody>
					<tr>
						<td class="leftTd">频道名称：</td>
						<td ><input id="channelId" name="world.channelId" class="easyui-combobox" 
							data-options="valueField:'id',textField:'channelName',url:'./admin_op/channel_queryAllChannel'"/></td>
					</tr>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td >
							<input id="worldId_channel" name="world.worldId" type="text" readonly="readonly" style="width:171px;"/>
						</td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="world.valid" id="valid_add" value="1" /></td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="world.notified" id="notified_add" value="0" /></td>
					</tr>
					<tr class="none">
						<td colspan="2"><input id="rowIndex" /></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="saveChannelWorldSubmit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_channel').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">保存中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	</div>
</body>
</html>