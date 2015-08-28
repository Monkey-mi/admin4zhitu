<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道总概</title>
<style type="text/css">
.title {
	font-size: 20px;
	font-weight: 90px;
}
</style>
<link type="text/css" rel="stylesheet"
	href="${webRootPath }/operations/css/channelOutlineV2.css"></link>
<jsp:include page="../common/header.jsp"></jsp:include>
<script type="text/javascript">
	var queryChannelNotifyByIdURL = "./admin_properties/properties_queryNotifyByChannelId";// 根据id查询
	var addChannelNotifyByIdURL = "./admin_properties/properties_addNotifyByChannelId";
	var searchChannelMaxId = 0, searchChannelQueryParams = {
		'maxId' : searchChannelMaxId
	};

	$(document)
			.ready(
					function() {
						$('#ss-channel')
								.combogrid(
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
												queryChannelById(record.id);
												$("#channel-name").text(
														record.channelName);
												$("#channel-id")
														.text(record.id);
											}

										});
						var p = $('#ss-channel').combogrid('grid').datagrid(
								'getPager');
						p
								.pagination({
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
	 * 根据频道Id查询频道
	 */
	function queryChannelById(channelId) {
		$.post(queryChannelNotifyByIdURL, {
			"channelId" : channelId
		}, function(result) {
			if (result['result'] == 0) {
				var notifyMap = result['notifyMap'];
				$("#channelAdd").val(notifyMap.channelAdd);
				$("#channelStar").val(notifyMap.channelStar);
				$("#channelSuperb").val(notifyMap.channelSuperb);
			} else {
				$.messager.alert('错误提示', result['msg']); //提示添加信息失败
			}

		}, "json");
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
			alert("请您先选择频道后做编辑操作!");
		} else {
			$('#channelAdd').attr("readonly", false);
		}
	}
	function editChannelStar() {
		if ($('#ss-channel').combogrid('getValue') == "") {
			alert("请您先选择频道后做编辑操作!");
		} else {
			$('#channelStar').attr("readonly", false);
		}
	}
	function editChannelSuperb() {
		if ($('#ss-channel').combogrid('getValue') == "") {
			alert("请您先选择频道后做编辑操作!");
		} else {
			$('#channelSuperb').attr("readonly", false);
		}
	}

	/*
	将修改后的文本写入配置文件中
	 */
	function save() {
		if ($('#channelAdd').attr("readonly") == "readonly"
				&& $('#channelStar').attr("readonly") == "readonly"
				&& $('#channelSuperb').attr("readonly") == "readonly") {
			alert("您未做修改。");
		} else {
			$('#channelAdd').attr("readonly", true);
			$('#channelStar').attr("readonly", true);
			$('#channelSuperb').attr("readonly", true);
			var channelId = $('#ss-channel').combogrid("getValue");
			var channelAdd = $('#channelAdd').val();
			var channelStar = $('#channelStar').val();
			var channelSuperb = $('#channelSuperb').val();
			$.post(addChannelNotifyByIdURL, {
				"channelId" : channelId,
				"channelAdd" : channelAdd,
				"channelStar" : channelStar,
				"channelSuperb" : channelSuperb
			}, function(result) {
				if (result['result'] == 0) {
					$.messager.alert('修改成功', result['msg']); //提示添加信息失败
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
			<span style="font-size: 20px">提示：</span>输入时请用 <span
				style="color: red">$(userName) </span>代替 <span style="color: blue">用户名</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用 <span
				style="color: red">$(channelName) </span>代替 <span
				style="color: blue">频道名</span>。
		</div>
		<hr />
		<div id="channel-info-wrap">
			<div id="channel-info">
				<p>
					<span class="title" id="">织图通知</span> <input id="channelAddEdit"
						type="button" onclick="editChannelAdd()" value="编辑"> <input
						id="channelAddSave" type="button" onclick="save()" value="保存">
				</p>
				<p>
					<textarea id="channelAdd" class="textarea" rows="3" cols="60"
						readonly="true">请输入填入频道的通知文本</textarea>
				</p>
			</div>
		</div>
		<hr />

		<div id="channel-info-wrap">
			<div id="channel-info">
				<p>
					<span class="title" id="">精选通知</span> <input id="channelSuperbEdit"
						type="button" onclick="editChannelSuperb()" value="编辑"> <input
						id="channelSuperbSave" type="button" onclick="save()" value="保存">
				</p>
				<p>
					<textarea id="channelSuperb" class="textarea" rows="3" cols="60"
						readonly=true>请输入捡入精选的通知文本</textarea>
				</p>
			</div>
		</div>

		<hr />



		<div id="channel-info-wrap">
			<div id="channel-info">
				<p>
					<span class="title" id="">红人通知</span> <input id="channelStarEdit"
						type="button" onclick="editChannelStar()" value="编辑"> <input
						id="channelStarSave" type="button" onclick="save()" value="保存">
				</p>
				<p>
					<textarea id="channelStar" class="textarea" rows="3" cols="60"
						readonly=true">请输入添加红人的通知文本</textarea>
				</p>
			</div>
		</div>
		<hr />

		<div id="search-channel-tb" style="padding: 5px; height: auto"
			class="none">
			<input id="channel-searchbox" searcher="searchChannel"
				class="easyui-searchbox" prompt="频道名/ID搜索" style="width: 200px;" />
		</div>
	</div>
</body>
</html>