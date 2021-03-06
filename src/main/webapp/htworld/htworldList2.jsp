<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!DOCTYPE html>
	<html lang="zh-CN">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>织图管理</title>
			<jsp:include page="../common/headerJQuery11.jsp"></jsp:include>
			<jsp:include page="../common/bootstrapHeader.jsp"></jsp:include>
			<jsp:include page="../common/tourlistHeader.jsp"></jsp:include>
			<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
			<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
			<link type="text/css" rel="stylesheet" href="${webRootPath }/htworld/css/htworldListV2.css"></link>
			<script type="text/javascript" src="${webRootPath}/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
			<script type="text/javascript" src="${webRootPath}/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
			<script type="text/javascript" src="${webRootPath}/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
			<script type="text/javascript" src="${webRootPath}/common/js/commonTools.js"></script>
			<script type="text/javascript" src="${webRootPath}/htworld/js/htworldList2.js?ver=${webVer}"></script>
		</head>

		<body>
			<div id="main" class="none">
				<img id="page-loading" alt="" src="${webRootPath }/common/images/girl-loading.gif" />
				<nav id="top" class="navbar navbar-default navbar-fixed-top">
					<div class="container">
						<a href="./page_htworld_htworldList2" title="瀑布流模式">
							<img class="switch-icon" src="./htworld/images/grid-icon.png" /></a>
						<a href="./page_htworld_htworldList" title="列表模式">
							<img class="switch-icon" src="./htworld/images/list-icon.png" /></a>
						<span class="search_label">起始时间:</span>
						<input id="startTime" style="width:100px">
						<span class="search_label">结束时间:</span>
						<input id="endTime" style="width:100px">
						<span class="search_label">客户端:</span>
						<select id="phoneCode" class="easyui-combobox" name="phoneCode" style="width:75px;">
							<option value="">所有</option>
							<option value="0">IOS</option>
							<option value="1">安卓</option>
						</select>
						<span class="search_label">有效状态:</span>
						<select id="valid" class="easyui-combobox" name="valid" style="width:75px;">
							<option value="1" selected="selected">有效</option>
							<option value="0">无效</option>
						</select>
						<span class="search_label">屏蔽状态:</span>
						<select id="shield" class="easyui-combobox" name="shield" style="width:75px;">
							<option value="" selected="selected">所有状态</option>
							<option value="1">屏蔽</option>
							<option value="0">非屏蔽</option>
						</select>
						<input class="easyui-combobox" id="search_adminUserDateSpan" onchange="validateSubmitOnce=true;" style="width:120px" />
						<input class="easyui-combobox" name="user_level_id" id="search_userLevelId" onchange="validateSubmitOnce=true;" style="width:120px" data-options="valueField:'id',textField:'level_description',url:'./admin_interact/userlevel_QueryUserLevel'" />
						
						<!-- mishengliang -->
					<br>
						<span class="search_label">是否马甲:</span>
						<select id="isZombie" class="easyui-combobox" name="isZombie" style="width:75px">
							<option value="" selected="selected">所有状态</option>
							<option value="1">马甲</option>
							<option value="0">用户</option>
						</select> 
						
						<div style="display:inline-block; vertical-align:middle;">
							<a href="#top" id="searchBtn">查询</a>
							<div id="mm" style="width:100px;">
								<div id="searchTodayBtn">今日分享</div>
								<div id="searchWeekBtn">本周分享</div>
								<div id="searchLastWeekBtn">上周分享</div>
								<div id="searchMonthBtn">本月分享</div>
								<div id="searchLastMonthBtn">上月分享</div>
							</div>
						</div>
						<input id="ss_worldId" searcher="searchByWorldId" class="easyui-searchbox" prompt="输入织图ID" style="width:80px;" />
						<input id="ss_authorName" searcher="searchByAuthorName" class="easyui-searchbox" prompt="输入用户昵称或ID搜索" style="width:80px;" />
						<input id="ss_worldDesc" searcher="searchByWorldDesc" class="easyui-searchbox" prompt="输入描述模糊搜索" style="width:80px;" />
						<input id="ss_worldLocation" searcher="searchByWorldLocation" class="easyui-searchbox" prompt="输入地理位置模糊搜索" style="width:80px;" />
						<div id="pagination" style="display:inline-block; vertical-align:middle;">
						</div>
					</div>
				</nav>
				<div id="world-box">
				</div>

				<!-- 添加活动 -->
				<div id="htm_activity">
					<span id="activity_loading" style="margin:140px 0 0 220px; position:absolute; display:none; ">加载中...</span>
					<form id="activity_form" action="./admin_op/op_saveSquarePushActivityWorld" method="post" class="none">
						<table class="htm_edit_table" width="280">
							<tbody>
								<tr>
									<td class="leftTd">活动：</td>
									<td>
										<input id="activityIds_activity" name="ids" type="text" style="width:205px;" />
									</td>
								</tr>
								<tr>
									<td class="leftTd">织图ID：</td>
									<td>
										<input id="worldId_activity" name="worldId" type="text" readonly="readonly" />
									</td>
								</tr>
								<tr>
									<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
										<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitActivityForm();">确定</a>
										<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_activity').window('close');">取消</a>
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

				<!-- 头像hover -->
				<div id="avatar_hover" style="font-size:14px;display:none;position:absolute;z-index:1000;border:solid 2px #eeeeee;box-shadow:3px 3px 3px;background:#ffffff;">
					<table>
						<tbody>
							<tr>
								<td style="height:22px;width:110px;border-right:dotted 1px #9e9e9e;">相册： <span id="hover_worldCount"></span></td>
								<td style="width:110px;border-right:dotted 1px #9e9e9e;">精选： <span id="hover_superb"> </span> </td>
								<td style="width:110px;border-right:dotted 1px #9e9e9e;">关注： <span id="hover_concernCount"></span> </td>
								<td style="width:110px;">粉丝： <span id="hover_followCount"></span></td>
							</tr>
							<tr>
								<td colspan='2' style="height:22px;border-right:dotted 1px #9e9e9e;">绑定： <span id="hover_platformCode" style="vertical-align: top;"></span> </td>
								<td colspan='2'>城市： <span id="hover_address"></span></td>
							</tr>
							<tr>
								<td colspan='4' style="height:22px;">签名： <span id="hover_signature"></span> </td>
							</tr>
							<tr>
								<td colspan='4' style="height:22px;" id="avarta_hover_registe_date">注册时间： <span id="hover_registerDate"></span> </td>
							</tr>
							<tr>
								<td colspan='4' style="height:22px;">个人标签： <span id="hover_userLabel"></span> </td>
							</tr>
						</tbody>
					</table>
				</div>

			</div>
		</body>

	</html>