<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String worldId = request.getParameter("worldId"); %>
<% String worldURL = request.getParameter("worldURL"); %>
<% String valid = request.getParameter("valid"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图互动展示（频道织图使用）</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/emotion/rl_exp.js"></script>
<link rel="stylesheet" href="${webRootPath }/base/js/jquery/emotion/rl_exp.css" />

<style type="text/css">
	body {
		font-size: 14px;
	}
	.rl_exp {
		margin-left: 10px;
		width: 624px;
	}
	.rl_exp_main {
		height: 230px;
		overflow-y: scroll;
	}
	.comment-main {
		height: 230px;
		float: left;
		text-align: left;
		margin-top: 15px;
	}
	.comment-main textarea {
		width: 70%;
		float:right;
		border: 1px solid #dcdcdc;
	}
	.comment-main textarea:focus {
		outline: none;
		border-color: #4bf;
	}
	
</style>

<script type="text/javascript">
	var commentMaxId=0;
	var worldId = <%= worldId %>;
	var valid = <%= valid %>;
	var channelId = baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID") ? baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID") : "";
	var commentQueryParams = {};
	
	function tableLoadDate(pageNum){
		$("#comments_interact_table").datagrid({
			width: 600,
			height: 320,
			pageList: [100,150,300],
			loadMsg: "加载中....",
			url: "./admin_interact/comment_queryCommentListByLabel",
			queryParams : commentQueryParams,
			pagination: true,
			pageNumber: pageNum,
			toolbar:'#comment_tb',
			columns:[[
						{field : 'ck',checkbox : true },
				        {field:'id',title:'ID',width:60},
				        {field:'content',title:'内容',width:400}
				    ]],
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > commentMaxId) {
						commentMaxId = data.maxId;
						commentQueryParams.maxId = commentMaxId;
					}
				}
		    },
		    onSelect:function(index,row){
		    	var  comments	= $('#comments_interact').combobox('getValues');
		    	var flag = false;
		    	var i;
		    	for(i=0;i<comments.length;i++){
		    		if(comments[i] == row['id']){
		    			flag = true;
		    			break;
		    		}
		    	}
		    	if(flag == false){
		    		var len;
		    		if($('#comments_interact').combobox('getValue')){
		    			comments.push(row['id']);
			    		$('#comments_interact').combobox('setValues',comments);//设置值combo的值
			    		len = comments.length;
		    		}else{
		    			$('#comments_interact').combobox('setValue',row['id']);
		    			len = 1;
		    		}
		    		
		   			var $selectCount = $("#selected_comment_count");
		    		$selectCount.text(len);//设置显示已经添加了多少
		    	}
		    },
		    onUnselect:function(index,row){
		    	var  comments	= $('#comments_interact').combobox('getValues');
		    	var flag = false;
		    	var i;
		    	for(i=0;i<comments.length;i++){
		    		if(comments[i] == row['id']){
		    			flag = true;
		    			break;
		    		}
		    	}
		    	if(flag == true){
		    		comments.splice(i,1);
		    		$('#comments_interact').combobox('clear');
		    		$('#comments_interact').combobox('setValues',comments);//设置值combo的值
		    		var len = comments.length,
		   			$selectCount = $("#selected_comment_count");
		    		$selectCount.text(len);//设置显示已经添加了多少
		    	}
		    }
		
		});
		var p = $('#comments_interact_table').datagrid('getPager');
		p.pagination({
			beforePageText : "页码",
			afterPageText : '共 {pages} 页',
			displayMsg: '第 {from} 到 {to} 共 {total} 条记录',
			buttons : [{
		    	iconCls:'icon-cut',
		    	text:'删除',
		    	handler:function(){
		    		deleteComment();
		    	}
		    }],
		    onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					commentMaxId = 0;
					commentQueryParams.maxId = commentMaxId;
				}
			}
		});
	}
	
	$(function(){
		showPageLoading();
		
		$('#ss_searchLabel').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_interact/comment_queryAllLabel',
			onSelect:function(record){
				loadComment(record.id,record.labelName);
				$('#ss_searchLabel').combobox('clear');
			},
		});
		
		buildWorldLabel();
		
		//加载表格
		tableLoadDate(1);
		
		//查询已添加多少互动
		$.post("./admin_interact/interact_queryInteractSum",{
			'worldId':worldId
		},function(result){
			if(result['result'] == 0) {
				$("#clickSum_interact").text(result["clickCount"]);
				$("#likedSum_interact").text(result["likedCount"]);
				$("#commentSum_interact").text(result["commentCount"]);
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},"json");
		
		$("#labelId_interact").combotree({
			url:'./admin_interact/comment_queryLabelTree?hasTotal=true',
			onSelect:function(rec) {
				loadComment(rec.id,rec.labelName);
			}
		});
		
		removePageLoading();
		$("#main").show();
		$(".loading").hide();
		
		// 刷新播放喜欢评论数
		refreshInteractSum(worldId);
	});
	
	
	/**
	 * 判断是否选中要删除的记录
	 */
	function isSelected(rows) {
		if(rows.length > 0){
			return true;
		}else{
			$.messager.alert('操作失败','请先选择记录，再执行操作!','error');
			return false;
		}
	}
	
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
	
	/**
	 * 删除评论
	 */
	function deleteComment() {
		var rows = $('#comments_interact_table').datagrid('getSelections');	
		if(isSelected(rows)){
			$.messager.confirm('删除记录', '您确定要删除已选中的记录?', function(r){ 	
				if(r){
					var ids = [];
					for(var i=0;i<rows.length;i+=1){		
						ids.push(rows[i]['id']);	
						rowIndex = $('#comments_interact_table').datagrid('getRowIndex',rows[i]);				
					}	
					$('#comments_interact_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
					$('#comments_interact_table').datagrid('loading');
					$('#comments_interact').combobox('clear');
		   			$("#selected_comment_count").text('0');
					$.post("./admin_interact/comment_deleteCommentByIds?ids="+ids,function(result){
						$('#comments_interact_table').datagrid('loaded');
						if(result['result'] == 0) {
							$.messager.alert('提示',result['msg']);
							$("#comments_interact_table").datagrid("reload");
						} else {
							$.messager.alert('提示',result['msg']);
						}
						
					});	
				}
			});
		}	
	}
	
	/**
	*搜索评论
	*/
	function searchComment() {
		var comment = $("#ss_comment").searchbox("getValue");
		commentMaxId = 0;
		commentQueryParams.maxId = commentMaxId;
		commentQueryParams.comment = comment;
		commentQueryParams.labelId = 0;
		var tmp = $("#comments_interact").combobox('getValues');
		$("#comments_interact_table").datagrid('load',commentQueryParams);
		$("#ss_comment").searchbox('clear');
		$("#comments_interact").combobox('setValues',tmp);
	}
	
	/**
	 * 加载评论
	 */
	function loadComment(labelId,labelName) {
		commentMaxId = 0;
		commentQueryParams.maxId = commentMaxId;
		commentQueryParams.labelId = labelId;
		commentQueryParams.comment = "";
		$("#comments_interact_table").datagrid('load',commentQueryParams);
	}
	
	/**
	*互动提交
	*/
	function interactSubmit(){
		var interactForm = $('#interact_form');
		var url = "";
		// 若织图是未生效的，则进行频道织图
		if ( valid == 0 ) {
			url = "./admin_interact/channelWorldInteract_addChannelWorldInvalidInteract";
		} else {
			url = "./admin_interact/channelWorldInteract_addChannelWorldValidInteract";
		}
		
		$(".opt_btn").hide();
		$(".loading").show();
		// 为form表单中的评论字符串与频道id设值
		$('#commentStrs_interact').val($("#rl_exp_input").val());
		$('#channelId_interact').val(channelId);
		interactForm.ajaxSubmit({
			success: function(result){
				if(result['result'] == 0) {
					$('#comments_interact_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
					$('#comments_interact').combobox('clear');
		   			$("#selected_comment_count").text('0');
				} else {
					alert(result['msg']);  //提示添加信息失败
				}
				$(".opt_btn").show();
				$(".loading").hide();
				parent.$.fancybox.close();
				return false;
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){  
				$(".opt_btn").show();
				$(".loading").hide();
                alert( "error !\n Status = " + textStatus + "\nerrorThrown = " + errorThrown);  
            },  
			url: url,
			type:'post',
			dateType:'json'
		});
		
	}
	
	/**
	* 取消按钮
	*/
	function btnCancel(){
		parent.$.fancybox.close();
	}
	
	/**
	* 标签生成
	*/
	function buildWorldLabel(){
		$("#worldLabelDiv").append("<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(231,\"旅游\");'><span style='line-height:24px;'>旅游</span></a>&nbsp;&nbsp;");
		$("#worldLabelDiv").append("<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(232,\"美食\");'><span style='line-height:24px;'>美食</span></a>&nbsp;&nbsp;");
		$("#worldLabelDiv").append("<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(637,\"自拍\");'><span style='line-height:24px;'>自拍</span></a>&nbsp;&nbsp;");
		$("#worldLabelDiv").append("<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(233,\"穿搭\");'><span style='line-height:24px;'>穿搭</span></a>&nbsp;&nbsp;");
		$("#worldLabelDiv").append("<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(340,\"摄影\");'><span style='line-height:24px;'>摄影</span></a>&nbsp;&nbsp;");
		$("#worldLabelDiv").append("<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(638,\"心情\");'><span style='line-height:24px;'>心情</span></a>&nbsp;&nbsp;");
		$("#worldLabelDiv").append("<br/>");
	}
	
	function refreshInteractSum(worldId) {
		// 查询的频道id来自于缓存中
		var param = {
				channelId: channelId,
				worldId: worldId
		};
		$.post("./admin_interact/channelWorldInteract_queryChannelWorldUNInteractCount", param, function(data){
				if(data["result"] == 0){
				$("#unValid_clickSum_interact").text(data["clickCount"]);
				$("#unValid_likedSum_interact").text(data["likedCount"]);
				$("#unValid_commentSum_interact").text(data["commentCount"]);
				}else{
					$.messager.alert('错误提示',data['msg']);  //提示添加信息失败
				}
		},"json");
		
		$.post("./admin_interact/interact_queryInteractSum",{
			worldId: worldId
		},function(result){
			if(result['result'] == 0) {
				$("#clickSum_interact").text(result["clickCount"]);
				$("#likedSum_interact").text(result["likedCount"]);
				$("#commentSum_interact").text(result["commentCount"]);
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},"json");
	}
	
</script>
</head>
<body>
	<div id="main">
		<div style="height:450px;width:40%;float:left">
			<iframe src="<%=worldURL %>?adminKey=zhangjiaxin" style="float:right;height:100%;width:70%;"></iframe>
		</div>
		<!-- 添加互动 -->
		<div style="height:450px;width:60%;float:right">
			<div id="htm_interact" style="font-size:12px;margin-left:10px;height:100%;width:100%">
				<form id="interact_form" method="post" class="none">
					<table class="htm_edit_table"  width="600px;" class="none">
						<tbody>
							<tr style="display:none;">
								<td colspan="3">
									<input type="text" name="worldId" value="<%=worldId%>"/>
								</td>
							</tr>
							<tr>
								<td class="leftTd">已添加互动：</td>
								<td colspan="2">播放【<span id="clickSum_interact">0</span>】&nbsp;喜欢【<span id="likedSum_interact">0</span>】&nbsp;评论【<span id="commentSum_interact">0</span>】</td>
							</tr>
							<tr>
								<td class="leftTd">
									频道织图计划互动：
								</td>
								<td colspan="2">
									播放【<span id="unValid_clickSum_interact">0</span>】&nbsp;喜欢【<span id="unValid_likedSum_interact">0</span>】&nbsp;评论【<span id="unValid_commentSum_interact">0</span>】
								</td>
							</tr>
							<tr>
								<td class="leftTd" style="padding-top:8px;" width="120px">
									评论：
								</td>
								<td style="padding-top:8px;"  width="30%">
									<input class="easyui-combobox" data-options="hasDownArrow:false" name="commentIds" id="comments_interact" style="width:200px;"/>
								</td>
								<td class="leftTd" style="padding-top:8px;">
									<div id="comments_interactTip" style="padding-left:10px;">
										已选：
										<span id="selected_comment_count">0</span>
									</div>
								</td>
							</tr>
							<tr class="opt_btn">
								<td style="text-align: center;padding-top: 15px;" colspan="3">
									<a class="easyui-linkbutton" iconCls="icon-ok" onclick="interactSubmit();">添加</a> 
									<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="btnCancel();">取消</a>
								</td>
							</tr>
							<tr style="display:none;">
								<td colspan="3"><input name="commentStrs" id="commentStrs_interact"></input></td>
							</tr>
							<tr style="display:none;">
								<td colspan="3"><input name="channelId" id="channelId_interact"></input></td>
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
				<br>
				<table id="comments_interact_table" style="float:right;margin-top:20px;"></table>
			</div>
		</div>
		
		<!-- 添加评论 -->
		<div id="div-add-comment">
			<!-- 添加评论 -->
			<div id="comment" class="comment-main" style="width:40%;">
					<textarea id="rl_exp_input" rows="13"></textarea>
			</div>
			
			<!-- emoji表情 -->
			<div style="width:60%;height:230px;float:right;margin-top:15px;">
				<div  class="rl_exp" id="rl_bq" style="position: static;">
					<ul class="rl_exp_main"></ul>
				</div>
			</div>
		</div>
		
		
		<div id="comment_tb" style="padding:5px;height:auto">
				<div style="display:inline-block; margin-right:5px; margin-top:3px;">
					<input id="labelId_interact" style="width:120px;" />
					<input id="ss_searchLabel" style="width:120px;" ></input>
					<input id="ss_comment" searcher="searchComment" class="easyui-searchbox" prompt="搜索评论" style="width:120px;"></input>
		   		</div>
	   		<div id="worldLabelDiv" style="margin:8px 0 5px 3px;"></div>
		</div>
		
	</div>
</body>
</html>