<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道维护</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<script type="text/javascript">
var channelQueryParam={};
var channelMaxId = 0;
var queryChannelByIdURL = "./admin_op/v2channel_queryOpChannelById";// 根据id查询频道

$(document).ready(function(){
	$('#ss-channel').combogrid({
		panelWidth : 320,
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
			{field : 'channelDesc',title : '频道描述',align : 'center',width : 200},
		]],
		queryParams:channelQueryParam,
	    onLoadSuccess:function(data) {
	    	if(data.result == 0) {
				if(data.maxId > channelMaxId) {
					channelMaxId = data.maxId;
					channelQueryParam.maxId = channelMaxId;
				}
			}
	    },
	    onSelect:function(index,row){
	    	baseTools.setCookie("CHANNEL_WORLD_CHANNEL_ID",row.channelId,10*24*60*60*1000);
	    	baseTools.setCookie("CHANNEL_WORLD_CHANNEL_NAME",row.channelName,10*24*60*60*1000);
	    	queryChannelById(row.channelId);
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
 * 根据频道Id查询频道
 */
function queryChannelById(channelId){
	if(channelId){
		$.post(queryChannelByIdURL,{
			"channelId":channelId
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				var createTime = new Date(obj['createTime']);
				$("#channelIcon").attr('src', obj['channelIcon']);
				$("#channelName").text(obj['channelName']);
				$("#channelIdSpan").text(obj['channelId']);
				$("#channelCreateTime").text(createTime.format("yyyy-MM-dd hh:mm:ss"));
				//$('#ss-channel').combogrid('setValue',obj['channelId']);
				
				$("#channelDesc").text(obj['channelDesc']);
				$("#totalWorldCount").text(obj['worldCount']);
				$("#totalConcernCount").text(obj['memberCount']);
				$("#channelLabels a").remove();
				if(obj['channelLabelNames'] && obj['channelLabelNames'].length>0 ){
					var labelNameArray = obj['channelLabelNames'].split(",");
					var labelIdArray = obj['channelLabelIds'].split(",");
					for(i=0;i<labelNameArray.length;i++){
						if(labelNameArray[i].trim() != "" && labelIdArray[i].trim() != ""){
							var labelSpan = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;overflow:hidden;padding:2px 4px 2px 4px;' labelId='"+labelIdArray[i]
											+"' labelName='"+labelNameArray[i]+"'>"+labelNameArray[i]+"</a>").click(function(){
								$(this).remove();
							});
							$("#channelLabels").append(labelSpan);
						}
					}
				}
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	}
}
</script>
<style type="text/css">
	.channelInfo{
		width:100px;
		text-align: center;
		display:inline-block;
	}
	.infotitle{
		color:#3e3e3e;
	}
</style>
</head>
<body>
	<div style="width:900px;margin: auto;">
		<table>
			<tbody>
				<tr>
					<td style="width:20%;height:110px; text-align: center;"><img alt="" id="channelIcon" src="./base/images/bg_empty.png" style="width:80px;height:80px;"><td>
					<td style="width:80%;">
						<div style="width:100%;height:60px;">
							<span class="search_label">请选择频道：</span>
							<input id="ss-channel" style="width:200px;" >
							<div style="width:100%;padding-top:10px;">
								<span class="infotitle">频道名称:<span id="channelName" style="width:120px;display: inline-block;"></span></span>
								<span class="infotitle">创建时间:<span id="channelCreateTime" style="width:180px;display: inline-block;">0000-00-00 00:00:00</span></span>
								<span class="infotitle">频道  ID:<span id="channelIdSpan" style="width:80px;display: inline-block;">0</span></span>
							</div>
						</div>
						<div style="width:100%;height:50px;">
							<div style="height:30px;">
								<span class="channelInfo infotitle">昨日新增关注</span>
								<span class="channelInfo infotitle">昨日新增织图</span>
								<span class="channelInfo infotitle">总织图数</span>
								<span class="channelInfo infotitle">总关注人数</span>
								<span class="channelInfo infotitle">昨日签到数</span>
							</div>
							<div style="height:30px;">
								<span class="channelInfo" id="newAddConcern">0</span>
								<span class="channelInfo" id="newAddWorld">0</span>
								<span class="channelInfo" id="totalWorldCount">0</span>
								<span class="channelInfo" id="totalConcernCount">0</span>
								<span class="channelInfo" id="signInCount">0</span>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div style="width:100%;height: 400px;padding: 40px 0 0 30px;overflow: hidden;border-top: dashed 1px;">
			<div class="">频道介绍</div>
			<div style="width:720px;height: 100px;background-color: #eeeeee;margin-left:40px;margin-bottom:20px; overflow: hidden;color: #5e5e5e" id="channelDesc"></div>
			<div>关联频道</div>
			<div style="width:720px;height: 100px;background-color: #eeeeee;margin-left:40px;margin-bottom:20px; overflow: hidden;" id="channelRelative"></div>
			<div>频道标签</div>
			<div style="width:720px;height: 100px;background-color: #eeeeee;margin-left:40px; overflow: hidden;" id="channelLabels"></div>
		</div>
	</div>
	<div></div>
</body>
</html>