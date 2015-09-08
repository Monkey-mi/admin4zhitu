<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道通知配置</title>
<style type="text/css">
.title {
	font-size: 20px;
	font-weight: 90px;
}
</style>
<link type="text/css" rel="stylesheet" href="${webRootPath}/operations/css/channelOutlineV2.css"></link>
<jsp:include page="../common/header.jsp"></jsp:include>
<script type="text/javascript">
	var queryChannelNoticeTpmlContentURL = "./admin_op/channelNotice_queryNoticeTpmlContentByChannelId";
	var saveChannelNoticeByIdURL = "./admin_op/channelNotice_saveNoticeTpmlContentByChannelId";
	var searchChannelMaxId = 0;
	var searchChannelQueryParams = {
		'maxId' : searchChannelMaxId
	};

	$(document).ready(
		function() {
			$('#ss-channel').combogrid(
				{
					panelWidth : 440,
					panelHeight : 330,
					loadMsg : '加载中，请稍后...',
					pageList : [ 4, 10, 20 ],
					pageSize : 4,
					toolbar : "#search-channel-tb",
					multiple : false,
					required : false,
					idField : 'id',
					textField : 'channelName',
					url : './admin_op/channel_searchChannel',
					pagination : true,
					columns : [ [
							{
								field : 'id',
								title : 'id',
								align : 'center',
								width : 80
							},
							{
								field : 'channelIcon',
								title : 'icon',
								align : 'center',
								width : 60,
								height : 60,
								formatter : function(
										value, row,
										index) {
									return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
								}
							}, {
								field : 'channelName',
								title : '频道名称',
								align : 'center',
								width : 280
							} ] ],
					queryParams : searchChannelQueryParams,
					onLoadSuccess : function(data) {
						if (data.result == 0) {
							if (data.maxId > searchChannelMaxId) {
								searchChannelMaxId = data.maxId;
								searchChannelQueryParams.maxId = searchChannelMaxId;
							}
						}
					},
					onSelect : function(index, record) {
						getChannelNoticeTpmlByChannelId(record.id);
						$("#channel-name").text(record.channelName);
						$("#channel-id").text(record.id);
					}

				});
			var p = $('#ss-channel').combogrid('grid').datagrid('getPager');
			p.pagination({
				onBeforeRefresh : function(pageNumber,
						pageSize) {
					if (pageNumber <= 1) {
						searchChannelMaxId = 0;
						searchChannelQueryParams.maxId = searchChannelMaxId;
					}
				}
			});
	
			$("#main").show();
	});

	/**
	 * 根据频道id获取不同的通知信息模板内容
	 * @author zhangbo
	 */
	function getChannelNoticeTpmlByChannelId(channelId) {
		// 获取织图选入频道通知模板
		$.post(queryChannelNoticeTpmlContentURL, {
			channelId : channelId,
			channelNoticeType : "add",
		}, function(result) {
			if (result['result'] == 0) {
				var noticeTemplateContent = result['msg'];
				$("#channelAdd").val(noticeTemplateContent);
			} else {
				$.messager.alert('错误提示', result['msg']); //提示添加信息失败
			}
		});
		
		// 获取精选通知模板
		$.post(queryChannelNoticeTpmlContentURL, {
			channelId : channelId,
			channelNoticeType : "superb",
		}, function(result) {
			if (result['result'] == 0) {
				var noticeTemplateContent = result['msg'];
				$("#channelSuperb").val(noticeTemplateContent);
			} else {
				$.messager.alert('错误提示', result['msg']); //提示添加信息失败
			}
		});
		
		// 获取红人通知模板
		$.post(queryChannelNoticeTpmlContentURL, {
			channelId : channelId,
			channelNoticeType : "star",
		}, function(result) {
			if (result['result'] == 0) {
				var noticeTemplateContent = result['msg'];
				$("#channelStar").val(noticeTemplateContent);
			} else {
				$.messager.alert('错误提示', result['msg']); //提示添加信息失败
			}
		});
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
		$("#ss-channel").combogrid('grid').datagrid("load",
				searchChannelQueryParams);
	}

	/*
	编辑通知文本
	 */
	function editChannelAdd() {
		if ($('#ss-channel').combogrid('getValue') == "") {
			$.messager.alert("温馨提示","请您先选择频道后做编辑操作!");
		} else {
			$('#channelAdd').attr("readonly", false);
			$('#channelAdd').focus();
		}
	}
	function editChannelStar() {
		if ($('#ss-channel').combogrid('getValue') == "") {
			$.messager.alert("温馨提示","请您先选择频道后做编辑操作!");
		} else {
			$('#channelStar').attr("readonly", false);
			$('#channelStar').focus();
		}
	}
	function editChannelSuperb() {
		if ($('#ss-channel').combogrid('getValue') == "") {
			$.messager.alert("温馨提示","请您先选择频道后做编辑操作!");
		} else {
			$('#channelSuperb').attr("readonly", false);
			$('#channelSuperb').focus();
		}
	}

	/**
	 * 保存织图通知 
	 */
	function saveAdd() {
		if ( $('#channelAdd').attr("readonly") == "readonly" ) {
			$.messager.alert("温馨提示","您未做修改。请编辑后保存！");
		} else {
			$('#channelAdd').attr("readonly", true);
			var param = {
				channelId : $('#ss-channel').combogrid("getValue"),
				channelNoticeType : "add",
				noticeTpmlContent : $('#channelAdd').val()
			};
			$.post(saveChannelNoticeByIdURL, param, function(result) {
				if (result['result'] == 0) {
					$.messager.alert("温馨提示","修改成功");
				} else {
					$.messager.alert('错误提示', result['msg']); //提示添加信息失败
				}
			}, "json");
		}
	}
	/**
	 * 保存加精通知
	 */
	function saveSuperb() {
		if ( $('#channelSuperb').attr("readonly") == "readonly" ) {
			$.messager.alert("温馨提示","您未做修改。请编辑后保存！");
		} else {
			$('#channelSuperb').attr("readonly", true);
			var param = {
				channelId : $('#ss-channel').combogrid("getValue"),
				channelNoticeType : "superb",
				noticeTpmlContent : $('#channelSuperb').val()
			};
			$.post(saveChannelNoticeByIdURL, param, function(result) {
				if (result['result'] == 0) {
					$.messager.alert("温馨提示","修改成功");
				} else {
					$.messager.alert('错误提示', result['msg']); //提示添加信息失败
				}
			}, "json");
		}
	}
	
	/**
	 * 保存红人通知 
	 */
	function saveStar() {
		if ( $('#channelStar').attr("readonly") == "readonly" ) {
			$.messager.alert("温馨提示","您未做修改。请编辑后保存！");
		} else {
			$('#channelStar').attr("readonly", true);
			var param = {
				channelId : $('#ss-channel').combogrid("getValue"),
				channelNoticeType : "star",
				noticeTpmlContent : $('#channelStar').val()
			};
			$.post(saveChannelNoticeByIdURL, param, function(result) {
				if (result['result'] == 0) {
					$.messager.alert("温馨提示","修改成功");
				} else {
					$.messager.alert('错误提示', result['msg']); //提示添加信息失败
				}
			}, "json");
		}
	}
</script>
</head>
<body>
	<div id="main" class="none">
		<div id="title">
			<span id="channel-name">请先选择频道</span> <span id="channel-id-wrap">
				<span>频道ID:</span><span id="channel-id">0</span>
			</span> <span id="ss-channel-wrap"> <label>请选择频道: </label><input
				id="ss-channel" />
			</span>
		</div>
		<div>
			<span style="font-size: 20px">提示：</span>
			输入时请用<span style="color: red">@{userName}</span>代替<span style="color: blue">用户名</span>，用<span style="color: red">@{channelName}</span>代替<span style="color: blue">频道名</span>。
			<br />
			<span style="font-size: 20px">例如：</span>
			亲爱的<span style="color: red">@{userName}</span>，由于你的织图帮帮哒，已被收录到我们的频道<span style="color: red">@{channelName}</span>中了，再接再厉，你可以织出更好的图奥！
			<br />
			<p style="font-size: 20px">
			（织图通知对同一频道同一个用户的多次织图<span style="color: red">一周 </span>内只发一次通知）
			</p>
		</div>
		<hr />
		<div id="channel-info-wrap">
			<div id="channel-info">
				<p>
					<span class="title" id="">织图通知</span> <input id="channelAddEdit"
						type="button" onclick="editChannelAdd()" value="编辑"> <input
						id="channelAddSave" type="button" onclick="saveAdd()" value="保存">
				</p>
				<p>
					<textarea id="channelAdd" class="textarea" rows="3" cols="60" readonly="true">请输入填入频道的通知文本</textarea>
				</p>
			</div>
		</div>
		
		<hr />

		<div id="channel-info-wrap">
			<div id="channel-info">
				<p>
					<span class="title" id="">精选通知</span> <input id="channelSuperbEdit"
						type="button" onclick="editChannelSuperb()" value="编辑"> <input
						id="channelSuperbSave" type="button" onclick="saveSuperb()" value="保存">
				</p>
				<p>
					<textarea id="channelSuperb" class="textarea" rows="3" cols="60" readonly=true>请输入捡入精选的通知文本</textarea>
				</p>
			</div>
		</div>
		
		<hr />
		
		<div id="channel-info-wrap">
			<div id="channel-info">
				<p>
					<span class="title" id="">红人通知</span> <input id="channelStarEdit"
						type="button" onclick="editChannelStar()" value="编辑"> <input
						id="channelStarSave" type="button" onclick="saveStar()" value="保存">
				</p>
				<p>
					<textarea id="channelStar" class="textarea" rows="3" cols="60" readonly=true>请输入添加红人的通知文本</textarea>
				</p>
			</div>
		</div>
		
		<div id="search-channel-tb" style="padding: 5px; height: auto"
			class="none">
			<input id="channel-searchbox" searcher="searchChannel"
				class="easyui-searchbox" prompt="频道名/ID搜索" style="width: 200px;" />
		</div>
	</div>
</body>
</html>