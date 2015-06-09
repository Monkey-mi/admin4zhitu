<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图管理</title>
<jsp:include page="../common/headerJQuery11.jsp"></jsp:include>
<jsp:include page="../common/bootstrapHeader.jsp"></jsp:include>
<jsp:include page="../common/tourlistHeader.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<link type="text/css" rel="stylesheet" href="${webRootPath }/htworld/css/htworldListV2.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<script type="text/javascript" src="${webRootPath }/htworld/js/htworldList2.js?ver=${webVer}"></script>
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
  		<span class="search_label">起始时间:</span><input id="startTime"  style="width:100px">
      	<span class="search_label">结束时间:</span><input id="endTime" style="width:100px">
		<span class="search_label">客户端:</span>
		<select id="phoneCode" class="easyui-combobox" name="phoneCode" style="width:75px;">
		<option value="">所有</option>
        <option value="0">IOS</option>
        <option value="1">安卓</option>
  		</select>
  		<span class="search_label">有效状态:</span>
  		<select id="valid" class="easyui-combobox" name="valid" style="width:75px;">
		<option value="" selected="selected">所有状态</option>
        <option value="1">有效</option>
        <option value="0">无效</option>
  		</select>
  		<span class="search_label">屏蔽状态:</span>
  		<select id="shield" class="easyui-combobox" name="shield" style="width:75px;">
		<option value="" selected="selected">所有状态</option>
        <option value="1">屏蔽</option>
        <option value="0">非屏蔽</option>
  		</select>
  		<input class="easyui-combobox" name="user_level_id" id="search_userLevelId" onchange="validateSubmitOnce=true;" style="width:120px"
						data-options="valueField:'id',textField:'level_description',url:'./admin_interact/userlevel_QueryUserLevel'"/>
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
		<input id="ss_shortLink" searcher="searchByShortLink" class="easyui-searchbox" prompt="输入织图ID或短链搜索" style="width:80px;" />
		<input id="ss_authorName" searcher="searchByAuthorName" class="easyui-searchbox" prompt="输入用户昵称或ID搜索" style="width:80px;" />
		<input id="ss_worldDesc" searcher="searchByWorldDesc" class="easyui-searchbox" prompt="输入描述模糊搜索" style="width:80px;" />
  		<div id="pagination" style="display:inline-block; vertical-align:middle;">
		</div>
		</div>
	</nav>
	<div id="world-box">
	</div>
	
	<!-- 添加互动 -->
	<div id="htm_interact">
		<span id="interact_loading" style="margin:140px 0 0 220px; position:absolute; display:none;  ">加载中...</span>
		<form id="interact_form" action="./admin_interact/worldlevel_AddLevelWorld" method="post" class="none">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">已添加：</td>
						<td colspan="2">播放【<span id="clickSum_interact">0</span>】&nbsp;喜欢【<span id="likedSum_interact">0</span>】&nbsp;评论【<span id="commentSum_interact">0</span>】</td>
					</tr>
					<tr>
						<td class="leftTd">分类互动：</td>
						<td colspan="2">播放【<span id="unValid_clickSum_interact">0</span>】&nbsp;喜欢【<span id="unValid_likedSum_interact">0</span>】&nbsp;评论【<span id="unValid_commentSum_interact">0</span>】</td>
					</tr>
					<tr>
						<td class="leftTd">评论：</td>
						<td><input type="text" name="comments" id="comments_interact" style="width:205px;"/></td>
						<td class="rightTd"><div id="comments_interactTip" class="tipDIV">已选：<span id="selected_comment_count">0</span></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图id：</td>
						<td><input type="text" id="worldId_interact" name="world_id"  onchange="validateSubmitOnce=true;" readonly="readonly" /></td>
						<td class="rightTd"><div id="worldId_Tip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图等级：</td>
						<td><input style="width:204px" class="easyui-combobox" id="levelId" name="id"  onchange="validateSubmitOnce=true;" 
							data-options="valueField:'id',textField:'level_description',url:'./admin_interact/worldlevel_QueryoWorldLevel'"/></td>
						<td class="rightTd"><div id="levelId_Tip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">评论标签：</td>
						<td><input style="width:204px"  name="labelsStr" id="labelId" onchange="validateSubmitOnce=true;" /></td>
						<td><input  id="labelIdSearch" style="width:100px"/></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#interact_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_interact').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	
	<!-- 添加分类互动互动 -->
	<div id="htm_type_interact">
		<span id="type_interact_loading" style="margin:140px 0 0 220px; position:absolute; display:none;  ">加载中...</span>
		<form id="type_interact_form" action="./admin_interact/worldlevelList_addWorldlevelList" method="post" class="none">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">已添加：</td>
						<td colspan="2">播放【<span id="clickSum_type_interact">0</span>】&nbsp;喜欢【<span id="likedSum_type_interact">0</span>】&nbsp;评论【<span id="commentSum_type_interact">0</span>】</td>
					</tr>
					<tr>
						<td class="leftTd">分类互动：</td>
						<td colspan="2">播放【<span id="unValid_clickSum_type_interact">0</span>】&nbsp;喜欢【<span id="unValid_likedSum_type_interact">0</span>】&nbsp;评论【<span id="unValid_commentSum_type_interact">0</span>】</td>
					</tr>
					<tr>
						<td class="leftTd">评论：</td>
						<td><input type="text" name="comment_ids" id="comments_type_interact" style="width:205px;"/></td>
						<td class="rightTd"><div id="comments_type_interactTip" class="tipDIV">已选：<span id="selected_type_comment_count">0</span></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图id：</td>
						<td><input type="text" id="worldId_type_interact" name="world_id"  onchange="validateSubmitOnce=true;" readonly="readonly" /></td>
						<td class="rightTd"><div id="worldId_type_Tip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图等级：</td>
						<td><input style="width:204px" class="easyui-combobox" id="type_levelId" name="world_level_id"  onchange="validateSubmitOnce=true;" 
							data-options="valueField:'id',textField:'level_description',url:'./admin_interact/worldlevel_QueryoWorldLevel'"/></td>
						<td class="rightTd"><div id="type_levelId_Tip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">评论标签：</td>
						<td><input style="width:204px"  name="label_ids" id="type_labelId" onchange="validateSubmitOnce=true;" /></td>
						<td><input  id="type_labelIdSearch" style="width:100px"/></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#type_interact_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_type_interact').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 给用户设定等级 -->
	<div id="htm_userLevel">
		<span id="userLevel_loading" style="margin:140px 0 0 220px; position:absolute; display:none; ">加载中...</span>
		<form id="userLevel_form" action="./admin_interact/userlevelList_AddUserlevel" method="post" class="none">
			<table class="htm_edit_table" width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">织图id：</td>
						<td><input type="text" id="userId_userLevel" name="userId"  onchange="validateSubmitOnce=true;" readonly="readonly" /></td>
						<td class="rightTd"><div id="userId_Tip" class="tipDIV"></div></td>
					</tr>
					
					<tr>
						<td class="leftTd">等级：</td>
						<td>
							<input style="width:204px" class="easyui-combobox" name="userLevelId" id="userLevelId_userLevel" onchange="validateSubmitOnce=true;"
								data-options="valueField:'id',textField:'level_description',url:'./admin_interact/userlevel_QueryUserLevel',required:true"/>
						</td>
						<td class="rightTd"><div id="userLevelId_Tip" class="tipDIV"></div></td>
					</tr>
									
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitUserLevelAddForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_userLevel').window('close');">取消</a>
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
	
	<!-- 添加活动 -->
	<div id="htm_activity">
		<span id="activity_loading" style="margin:140px 0 0 220px; position:absolute; display:none; ">加载中...</span>
		<form id="activity_form" action="./admin_op/op_saveSquarePushActivityWorld" method="post" class="none">
			<table class="htm_edit_table" width="280">
				<tbody>
					<tr>
						<td class="leftTd">活动：</td>
						<td>
							<input id="activityIds_activity" name="ids" type="text"  style="width:205px;"/>
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
	
	<!-- 添加到精选 -->
	<div id="htm_type">
		<span id="type_loading" style="margin:60px 0 0 220px; position:absolute; display:none; ">加载中...</span>
		<form id="type_form" action="./admin_interact/typeOptionWorld_addTypeOptionWorld" method="post" class="none">
			<table class="htm_edit_table" width="520">
				<tbody>
					<tr>
						<td class="leftTd">标签：</td>
						<td style="width:140px;">
							<input id="typeId_type" name="typeId" type="text" style="width:120px;"  onchange="validateSubmitOnce=true;"/>
						</td>
						<td class="leftTd">频道：</td>
						<td>
							<input id="channelId"  class="easyui-combobox" 
							data-options="valueField:'id',textField:'channelName',url:'./admin_op/channel_queryAllChannel'"  style="width:170px;" onchange="validateSubmitOnce=true;"/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td>
							<input id="worldId_type" name="worldId" type="text" readonly="readonly" style="width:117px;"/>
							<input id="userId_type" name="userId" type="text" readonly="readonly" style="display:none;"/>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="4" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#type_form').submit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_type').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">保存中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<div id="comment_tb" style="padding:5px;height:auto" class="none">
		<a href="#htm_add_comment" onclick="addComment(0);" class="easyui-linkbutton" style="vertical-align:middle;" title="添加评论" plain="true" iconCls="icon-add" id="addCommentBtn">添加</a>
		<a href="javascript:void(0);" onclick="deleteComment();" class="easyui-linkbutton" style="vertical-align:middle;" title="删除评论" plain="true" iconCls="icon-cut">删除</a>
		<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
			<span class="search_label">评论标签：</span><input id="labelId_interact" name="labelId" style="width:120px;" />
			<span class="search_label">搜索标签：</span><input id="ss_searchLabel" style="width:100px;"></input>
			<input id="ss_comment" searcher="searchComment" class="easyui-searchbox" prompt="搜索评论" style="width:100px;"></input>
   		</div>
	</div>
	<div id="comment_tb2" style="padding:5px;height:auto" class="none">
		<a href="#htm_add_comment" onclick="addComment(0);" class="easyui-linkbutton" style="vertical-align:middle;" title="添加评论" plain="true" iconCls="icon-add" id="addCommentBtn2">添加</a>
		<a href="javascript:void(0);" onclick="deleteComment();" class="easyui-linkbutton" style="vertical-align:middle;" title="删除评论" plain="true" iconCls="icon-cut">删除</a>
		<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
			<span class="search_label">评论标签：</span><input id="labelId_type_interact" name="labelId" style="width:120px;" />
			<span class="search_label">搜索标签：</span><input id="ss_type_searchLabel" style="width:100px;"></input>
			<input id="ss_type_comment" searcher="searchTypeComment" class="easyui-searchbox" prompt="搜索评论" style="width:100px;"></input>
   		</div>
	</div>
	
	<!-- 头像hover -->
	<div id="avatar_hover" style="font-size:14px;display:none;position:absolute;z-index:1000;border:solid 2px #eeeeee;box-shadow:3px 3px 3px;background:#ffffff;">
		<table >
			<tbody>
				<tr >
					<td style="height:22px;width:110px;border-right:dotted 1px #9e9e9e;">相册：  <span id="hover_worldCount"></span></td>
					<td style="width:110px;border-right:dotted 1px #9e9e9e;">精选： <span id="hover_superb"> </span> </td>
					<td style="width:110px;border-right:dotted 1px #9e9e9e;">关注： <span id="hover_concernCount"></span> </td>
					<td style="width:110px;">粉丝：  <span id="hover_followCount"></span></td>
				</tr>
				<tr >
					<td colspan='2' style="height:22px;border-right:dotted 1px #9e9e9e;">绑定：  <span id="hover_platformCode" style="vertical-align: top;"></span>  	</td>
					<td colspan='2' >城市： <span id="hover_address"></span></td>
				</tr>
				<tr >
					<td colspan='4' style="height:22px;">签名：  <span id="hover_signature"></span>  		</td>
				</tr>
				<tr >
					<td colspan='4' style="height:22px;" id="avarta_hover_registe_date">注册时间： <span id="hover_registerDate"></span> 	</td>
				</tr>
				<tr><td colspan='4' style="height:22px;">个人标签： <span id="hover_userLabel"></span>  		</td></tr>
			</tbody>
		</table>
	</div>
	
	<!-- 添加到频道 -->
	<div id="htm_channel">
		<span id="channel_loading" style="margin:60px 0 0 220px; position:absolute; display:none">加载中...</span>
		<form id="channel_form" action="./admin_op/channel_saveChannelWorld" method="post" >
			<table class="htm_edit_table" width="400">
				<tbody>
					<tr>
						<td class="leftTd">频道名称：</td>
						<!-- 
						<td ><input id="channelId" name="world.channelId" class="easyui-combobox" 
							data-options="valueField:'id',textField:'channelName',multiple:'true',url:'./admin_op/channel_queryAllChannel'"/></td> -->
						<td><input id="ss-channel" name="world.channelId" style="width:171px;" /></td>
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
	
	<div id="search-channel-tb" style="padding:5px;height:auto" class="none">
		<input id="channel-searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
	</div>
	
	</div>
</body>
</html>