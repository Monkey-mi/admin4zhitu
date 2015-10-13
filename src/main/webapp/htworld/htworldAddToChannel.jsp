<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String worldId = request.getParameter("worldId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jquery -->
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.8.2.min.js"></script>
<!-- easy ui -->
<link rel="stylesheet" type="text/css" href="${webRootPath }/base/js/jquery/jquery-easyui-1.3.5/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${webRootPath }/base/js/jquery/jquery-easyui-1.3.5/themes/icon.css" />
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-easyui-1.3.2/plugins/jquery.combogrid.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
<title>织图添加到频道</title>
<script type="text/javascript">
	var worldId = <%= worldId %>;
	var searchChannelQueryParams = {};
	searchChannelQueryParams.maxId = 0;
	
	/**
	 * jquery初始化
	 */
	$(function() {
		
		$('#ss-channel').combogrid({
			panelWidth : 440,
		    panelHeight : 330,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar: "#search-channel-tb",
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
		    onSelect:function(index,row){
				var channelNameBtn = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;overflow:hidden;' channelId='"
						+ row.id + "'>" + row.channelName + "</a>")
						.click(function(){
							// 先移除<a>标签后面的<br>标签，然后再移除<a>标签本身 
							$(this).next().remove();
							$(this).remove();
						});
				$("#add_into_channel").append(channelNameBtn);
				$("#add_into_channel").append($("<br>"));
			},
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
		    		searchChannelQueryParams.maxId = data.maxId;
				}
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
		
		loadCurrentChannel();
	});
	
	/**
	 * 加载织图当前所在频道
	 */
	function loadCurrentChannel() {
		$.post("./admin_ztworld/ztworld_queryWorld",{
			'worldId':worldId
		},function(result){
			if(result['result'] == 0){
				var channelNameArray = result.htworld.channelNames;
				for (var i=0; i<channelNameArray.length; i++) {
					$("#current_channel").append($("<span>" + channelNameArray[i].name + "</span>"));
					$("#current_channel").append($("<br>"));
				}
			}else{
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},"json");
	}
	
	/**
	 * 保存织图到选择的频道中
	 */
	function saveChannelWorldSubmit(){
		var channelIds = [];
		
		$("#add_into_channel a").each(function(){
			channelIds.push($(this).attr("channelId"));
		});
		
		for (var i=0; i < channelIds.length; i++) {
			//成为频道用户
			$.post("./admin_op/chuser_addChannelUserByWorldId",{
				'worldId':worldId,
				'channelId':channelIds[i]
			},function(result){
				if(result['result'] == 0){
					isSuccess = true;
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			});
			//该织图进入频道
			$.post("./admin_op/channel_saveChannelWorld",{
				'world.channelId':channelIds[i],
				'world.worldId'  :worldId,
				'world.valid'	 :0,
				'world.notified' :0
			},function(result){
				if(result['result'] == 0){
					isSuccess = true;
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			});
		}
	};
	
	/**
	 * 搜索频道名称
	 */
	function searchChannel() {
		var query = $('#channel-searchbox').searchbox('getValue');
		searchChannelQueryParams.maxId = 0;
		searchChannelQueryParams.query = query;
		$("#ss-channel").combogrid('grid').datagrid("load",searchChannelQueryParams);
	};
	
</script>
</head>
<body>
	<!-- 添加到频道 -->
	<div id="htm_channel">
		<table class="htm_edit_table" width="650">
			<tbody>
				<tr>
					<td class="leftTd">所在频道：</td>
					<td>
						<div id="current_channel" style="width:250px;height:100px;border:solid 1px #4796EF;"></div>
					</td>
				</tr>
				<tr>
					<td class="leftTd">将要添加进的频道：</td>
					<td>
						<div id="add_into_channel" style="width:250px;height:100px;border:solid 1px #4796EF;"></div>
					</td>
				</tr>
				<tr>
					<td class="leftTd">频道名称：</td>
					<td>
						<input id="ss-channel" style="width:200px;" />
					</td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="saveChannelWorldSubmit()">确定</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<!-- 频道grid搜索框 -->
	<div id="search-channel-tb" style="padding:5px;height:auto" class="none">
		<input id="channel-searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;" />
	</div>

</body>
</html>