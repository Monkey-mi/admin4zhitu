<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道总概</title>
<link type="text/css" rel="stylesheet" href="${webRootPath }/operations/css/channelOutlineV2.css"></link>
<jsp:include page="../common/header.jsp"></jsp:include>
<script type="text/javascript">
var queryChannelByIdOrNameURL = "./admin_op/v2channel_queryOpChannelByIdOrName";// 根据id和名称查询频道
var searchChannelMaxId = 0,
	searchChannelQueryParams = {
		'maxId':searchChannelMaxId
	};

$(document).ready(function(){
	$('#ss-channel').combogrid({
		panelWidth : 440,
	    panelHeight : 330,
	    loadMsg : '加载中，请稍后...',
		pageList : [4,10,20],
		pageSize : 4,
		toolbar:"#search-channel-tb",
	    multiple : false,
	    required : false,
	   	idField : 'id',
	    textField : 'channelName',
	    url : './admin_op/channel_searchChannel',
	    pagination : true,
	    columns:[[
			{field : 'id',title : 'id',align : 'center',width : 80},
			{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
				formatter:function(value,row,index) {
					return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
				}
			},
			{field : 'channelName',title : '频道名称',align : 'center',width : 280}
	    ]],
	    queryParams:searchChannelQueryParams,
	    onLoadSuccess:function(data) {
	    	if(data.result == 0) {
				if(data.maxId > searchChannelMaxId) {
					searchChannelMaxId = data.maxId;
					searchChannelQueryParams.maxId = searchChannelMaxId;
				}
			}
	    },
	    onSelect:function(index,record) {
	    	queryChannelById(record.id);
	    }
	    
	});
	var p = $('#ss-channel').combogrid('grid').datagrid('getPager');
	p.pagination({
		onBeforeRefresh : function(pageNumber, pageSize) {
			if(pageNumber <= 1) {
				searchChannelMaxId = 0;
				searchChannelQueryParams.maxId = searchChannelMaxId;
			}
		}
	});
	
	$("#main").show();
	getChannelIdFromCookie();

});

/**
 * 从cookie中拿去频道ID
 */
function getChannelIdFromCookie(){
	var channelId = baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID");
	if(channelId){
		queryChannelById(channelId);
	}
}

/**
 * 根据频道Id或名字查询频道
 */
function queryChannelById(channelId){
	$.post(queryChannelByIdOrNameURL,{
		"channelId":channelId
	}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				var createTime = new Date(obj['createTime']),
					channelDesc = obj['channelDesc'];
				if(channelDesc == undefined || channelDesc == "") {
					channelDesc = "暂无频道描述~";
				}
				$("#channel-icon").attr('src', obj['channelIcon']);
				$("#channel-name").text(obj['channelName']);
				$("#channel-id").text(obj['channelId']);
				$("#create-time").text(createTime.format("yyyy-MM-dd hh:mm:ss"));
				
				$("#channel-desc").text(channelDesc);
				$("#user-id").text(obj['ownerId']);
				$("#user-avatar").attr('src', obj['userAvatarL']);
				$("#user-name").text(obj['userName']);
				$("#world-count").text(obj['worldCount']);
				$("#member-count").text(obj['memberCount']);
				$("#new-member-count").text(obj['yestodayMemberIncreasement']);
				$("#new-world-count").text(obj['yestodayWorldIncreasement']);
				
				$(".channel-label").remove();
				var $labelwrap = $("#channel-label-wrap");
				if(obj['channelLabelNames'] && obj['channelLabelNames'].length>0 ){
					var labelNameArray = obj['channelLabelNames'].split(",");
					var labelIdArray = obj['channelLabelIds'].split(",");
					for(i=0;i<labelNameArray.length;i++){
						if(labelNameArray[i] != "" && labelIdArray[i] != ""){
							var label = $("<span>#"+labelNameArray[i]+"</span>").addClass("channel-label");
							$labelwrap.append(label);
						}
					}
				} else {
					var nolabel = $("<span>#暂无标签</span>").addClass("channel-label");
					$labelwrap.append(nolabel);
				}
				
				baseTools.setCookie("CHANNEL_WORLD_CHANNEL_ID",obj['channelId'],10*24*60*60*1000);
	    		baseTools.setCookie("CHANNEL_WORLD_CHANNEL_NAME",obj['channelName'],10*24*60*60*1000);
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
}

/**
 * 搜索频道名称
 */
function searchChannel() {
	searchChannelMaxId = 0;
	maxId = 0;
	var query = $('#channel-searchbox').searchbox('getValue');
	searchChannelQueryParams.maxId = searchChannelMaxId;
	searchChannelQueryParams.query = query;
	$("#ss-channel").combogrid('grid').datagrid("load",searchChannelQueryParams);
}
</script>
</head>
<body>
	<div id="main" class="none">
	<div id="title">
		<span id="channel-name">请先选择频道</span>
		<span id="channel-id-wrap">
		<span>频道ID:</span><span id="channel-id">0</span>
		</span>
		<span id="ss-channel-wrap">
			<label>请选择频道: </label><input id="ss-channel" />
		</span>
	</div>
	<div id="stat-wrap">
		<div class="stat" id="world-stat">
			<div class="stat-count" id="world-count">0</div>
			<div class="stat-name">织图总数</div>
		</div>			
		<div class="stat" id="member-stat">
			<div class="stat-count" id="member-count">0</div>
			<div class="stat-name">关注总数</div>
		</div>
		<div class="stat" id="new-world-stat">
			<div class="stat-count" id="new-world-count">0</div>
			<div class="stat-name">昨日新增织图</div>
		</div>
		<div class="stat" id="new-member-stat">
			<div class="stat-count" id="new-member-count">0</div>
			<div class="stat-name">昨日新增关注</div>
		</div>
	</div>
	<hr />
	<div id="channel-info-wrap">
		<div id="owner-info">
			<img id="user-avatar" alt="" src="${webRootPath }/base/images/bg_empty.png">
		</div>
		<div id="channel-info">
			<p>
				<span id="user-name">频道主</span>
				<span id="user-id-wrap">
					(<span>频道主ID:</span>
					<span id="user-id">0</span>
					)
				</span>
			</p>
			<p>
				<span id="channel-desc">
					暂无频道描述~
				</span>
			</p>
			<p>
				<span id="create-time-wrap">
					<span>创建于 </span>
					<span id="create-time">00-00-00 00:00:00</span>
				</span>
			</p>
			<div>
				<div id="channel-icon-wrap">
					<img id="channel-icon" src="${webRootPath }/base/images/bg_empty.png" />
				</div>
				<div id="channel-label-wrap">
					<span class="channel-label">#暂无标签</span>
				</div>
			</div>
		</div>
	</div>
	
	<hr />
	
	<div id="link-title">相关频道</div>
	<div id="link-channel-wrap">
		<span class="link-channel">暂无相关频道</span>
	</div>

	<div id="search-channel-tb" style="padding:5px;height:auto" class="none">
		<input id="channel-searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
	</div>
	</div>
</body>
</html>