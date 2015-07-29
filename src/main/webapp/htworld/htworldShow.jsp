<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String worldId = request.getParameter("worldId"); %>
<% String userId = request.getParameter("userId"); %>
<% String shortLink = request.getParameter("shortLink"); %>
<% String superbFlag = request.getParameter("superbFlag"); %>
<% String trustFlag = request.getParameter("trustFlag"); %>
<% String lastestFlag = request.getParameter("lastestFlag"); %>
<% String index = request.getParameter("index"); %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图显示功能</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/emotion/rl_exp.js"></script>
<link rel="stylesheet" href="${webRootPath }/base/js/jquery/emotion/rl_exp.css" />
<script type="text/javascript">
	var commentMaxId=0,
	worldId = <%= worldId %>,
	index = <%= index %>,
	userId = <%= userId %>,
	superbFlag = <%= superbFlag %>,
	lastestFlag = <%= lastestFlag %>,
	trustFlag  = <%= trustFlag  %>,
	worldLabel = "<%=new String(request.getParameter("worldLabel").getBytes("iso8859-1"),"utf-8")%>",
	expertBtnFlag=2,
	loadDateUrl="./admin_interact/comment_queryCommentListByLabel",
	commentQueryParams = {},
	tableInit = function() {
		tableLoadDate(1);
	};
	
	function tableLoadDate(pageNum){
		$("#comments_interact_table").datagrid(
				{
					width  :600,
					height :350,
					pageList : [100,150,300],
					loadMsg:"加载中....",
					url	   :	loadDateUrl,
					queryParams : commentQueryParams,
					pagination: true,
					pageNumber: pageNum,
					toolbar:'#comment_tb',
					columns:[[
								{field : 'ck',checkbox : true },
						        {field:'id',title:'ID',width:60},
						        {field:'content',title:'内容',width:400},
						        {field:'opt', title:'操作', align:'center', width:60,
						        	formatter: function(value,row,index){
						    			return "<a title='点击更新评论' class='updateInfo' href='javascript:addComment(\""+ row['id'] + "\",\"" + index + "\")'>" + '更新'+ "</a>";
						    		}	
						        }
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
				
				}	
		);
		var p = $('#comments_interact_table').datagrid('getPager');
		p.pagination(
				{
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
		$.post("./admin_user/user_queryUserInfoByUserId?userId="+userId,function(result){
			if(result['result'] == 0){
				var sysAccept = result['userInfo']['sysAccept'],
					userAccept= result['userInfo']['userAccept'],
					ver		  = result['userInfo']['ver'];
					
				// 未添加进推荐列表
				if(sysAccept == -1) {
					expertBtnFlag=2;//添加按钮
				// 一方拒绝
				} else if(userAccept == 2 || sysAccept == 2) {
					expertBtnFlag=3;//拒绝按钮
					
				// 用户未接受，允许撤销
				} else if(userAccept == 0 || ver < 2.0803) {
					expertBtnFlag=4;//等待按钮
					
				// 用户已接受
				} else {
					if(sysAccept == 0) {
						expertBtnFlag=4;//等待按钮
					} else {
						expertBtnFlag=1;//ok按钮
					}
				}
				$(".hideBtn").css('display','none');
				switch(expertBtnFlag){
					case 1:$("#okExpertBtn").css('display','');break;
					case 2:$("#addExpertBtn").css('display','');break;
					case 3:$("#rejectExpertBtn").css('display','');break;
					case 4:$("#waitExpertBtn").css('display','');break;
				}
				switch(superbFlag){
					case 2:$("#addSuperbBtn").css('display','');break;
					case 1:$("#okSuperbBtn").css('display','');break;
				}
				switch(trustFlag){
					case 0:$("#addTrust").css('display','');break;
					case 1:$("#cancelTrust").css('display','');break;
				}
				switch(lastestFlag){
					case 0:$("#addLastest").css('display','');break;
					case 1:$("#cancelLastest").css('display','');break;
				}
			}else{//失败
				
			}
		});
		
		$('#ss_searchLabel').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_interact/comment_queryAllLabel',
			icons:[{
                iconCls:'icon-add'
            },{
                iconCls:'icon-cut'
            }],
			onSelect:function(record){
				loadComment(record.id,record.labelName);
				$('#ss_searchLabel').combobox('clear');
			},
			onLoadSuccess:function(){
				buildWorldLabel();
			}
		});
		
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
	});
	
	
	function update(url){
		var rows = $('#htm_table').datagrid('getSelections');	
		if(isSelected(rows)){
			var ids = [];
			for(var i=0;i<rows.length;i+=1){		
				ids.push(rows[i]['id']);	
				rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
			}	
			$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
			$('#htm_table').datagrid('loading');
			$.post(url+ids,function(result){
				$('#htm_table').datagrid('loaded');
				if(result['result'] == 0) {
					$.messager.alert('提示',result['msg']);
					$("#htm_table").datagrid("reload");
				} else {
					$.messager.alert('提示',result['msg']);
				}
				
			});				
		}	
	}
	
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
	 * 添加评论
	 */
	function addComment(id, index) {
		$.fancybox({
			'width'				: '65%',
			'height'			: '75%',
			'autoScale'			: true,
			'type'				: 'iframe',
			'href'				: "page_interact_addComment?id="+id,
			'titlePosition'		: 'inside',
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'hideOnOverlayClick': false,
			'showCloseButton'   : false,
			'titleShow'			: false,
			'onClosed'			:function(){
				commentQueryParams.maxId = 0;
				commentQueryParams.comment = "";
				$("#comments_interact_table").datagrid('load',commentQueryParams);
			}
		});
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
		$(".opt_btn").hide();
		$(".loading").show();
		interactForm.ajaxSubmit({
			success: function(result){
				if(result['result'] == 0) {
					$.messager.alert('成功提示',result['msg']);
					$('#comments_interact_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
					$('#comments_interact').combobox('clear');
		   			$("#selected_comment_count").text('0');
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				$(".opt_btn").show();
				$(".loading").hide();
				parent.updateInteracted(index);
				parent.$.fancybox.close();
				refreshRow(index);
				return false;
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){  
				$(".opt_btn").show();
				$(".loading").hide();
                alert( "error !\n Status = " + textStatus + "\nerrorThrown = " + errorThrown);  
            },  
			url:interactForm.attr("action"),
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
	* 成为达人
	*/
	function toBeStar(){
		var uid = userId;
		var verifyId = 1;//达人id
		$(".opt_btn").hide();
		$(".loading").show();
		$.post("./admin_op/user_saveRecommendUser",{
			'userId' : uid,
			'verifyId' : verifyId
		},function(result){
			$(".opt_btn").show();
			$(".loading").hide();
			if(result['result'] == 0) {
				$(".expertBtn").css('display','none');
				$("#waitExpertBtn").css('display','');
				$.messager.alert('提示',result['msg']);  //提示添加信息成功
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},'json');
	}
	
	/**
	* 成为精选
	*/
	function toBeSuperb(){
		var wid = worldId;
		var uid = userId;
		$(".opt_btn").hide();
		$(".loading").show();
		$.post("./admin_interact/typeOptionWorld_addTypeOptionWorld",{
			'worldId':wid,
			'userId' :uid
		},function(result){
			$(".opt_btn").show();
			$(".loading").hide();
			if(result['result'] == 0) {
				$(".superbBtn").css('display','none');
				$("#okSuperbBtn").css('display','');
				parent.updateSuperb(index,1);
				$.messager.alert('提示',result['msg']);  //提示添加信息成功
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},'json');
		
	}
	
	/**
	* 标签生成
	*/
	function buildWorldLabel(){
		var labelArray = [];
		var str = "";
		var data = $("#ss_searchLabel").combobox('getData');
		var labelCount=0;
		$("#worldLabelDiv").html("");
		if(worldLabel != undefined && worldLabel != null && worldLabel != '' && typeof(worldLabel) != undefined){
			labelArray = worldLabel.split(',');
		}
		if(labelArray.length>0){
			var i,j;
			for(i=0;i<labelArray.length && i<5;i++){
				for(j=0; j<data.length ; j ++){
					if(data[j]['labelName'] == labelArray[i]){
						labelCount++;
						if(labelArray[i] != "旅游" && labelArray[i] != "美食" && labelArray[i] != "自拍" && labelArray[i] != "穿搭" && labelArray[i] != "摄影" && labelArray[i] != "心情")
						str += "<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;overflow:hidden;' title='" +data[j]['labelName'] + "' onclick='loadComment("
							+ data[j]['id'] + ",\"" + data[j]['labelName'] + "\")' ><span style='line-height:24px;'>" + data[j]['labelName'] +  "</span></a>&nbsp;&nbsp;";
						if(labelCount == 1){//匹配成功的第一个标签，用以查询评论
							commentMaxId = 0;
							commentQueryParams.maxId = commentMaxId;
							commentQueryParams.labelId = data[j]['id'];
							commentQueryParams.comment = "";
						}
						break;
					}
				}
				
			}
		}
		if(labelCount <=3){
			str += "<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(231,\"旅游\");'><span style='line-height:24px;'>旅游</span></a>&nbsp;&nbsp;"
				+  "<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(232,\"美食\");'><span style='line-height:24px;'>美食</span></a>&nbsp;&nbsp;"
				+  "<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(637,\"自拍\");'><span style='line-height:24px;'>自拍</span></a>&nbsp;&nbsp;"
				+  "<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(233,\"穿搭\");'><span style='line-height:24px;'>穿搭</span></a>&nbsp;&nbsp;"
				+  "<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(340,\"摄影\");'><span style='line-height:24px;'>摄影</span></a>&nbsp;&nbsp;"
				+  "<a href='#' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;' onclick='loadComment(638,\"心情\");'><span style='line-height:24px;'>心情</span></a>&nbsp;&nbsp;";
		}
		if(str != ""){//换行
			str += "<br/>";
			$("#worldLabelDiv").html(str);
		}
		if(labelCount == 0){//若标签没有匹配成功，则默认添加旅游标签的评论
			commentMaxId = 0;
			commentQueryParams.maxId = commentMaxId;
			commentQueryParams.labelId = 231;
			commentQueryParams.comment = "";
		}
		
		//加载表格
		tableInit();
	}
	
	/**
	* 最新。flag=1是设置为最新，flag=0是撤销最新
	*/
	function updateLatestValid( flag) {
		var wid = worldId;
		$(".opt_btn").hide();
		$(".loading").show();
		$.post("./admin_ztworld/ztworld_updateLatestValid",{
			'id':wid,
			'valid':flag
			},function(result){
				$(".opt_btn").show();
				$(".loading").hide();
				if(result['result'] == 0) {
					if(flag == 1){
						$("#cancelLastest").css('display','');
						$("#addLastest").css('display','none');
					}else{
						$("#cancelLastest").css('display','none');
						$("#addLastest").css('display','');
					}
					parent.updateLast(index,flag);
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
				$("#htm_table").datagrid('loaded');
			},"json");
	}
	
	
	/**
	 * 添加信任
	 * 
	 * @param userId
	 * @param index
	 */
	function addTrust() {
		$(".opt_btn").hide();
		$(".loading").show();
		$.post("./admin_user/user_updateTrust",{
			'userId':userId,
			'trust':1,
			},function(result){
				$(".opt_btn").show();
				$(".loading").hide();
				if(result['result'] == 0) {
					$("#addTrust").css('display','none');
					$("#cancelTrust").css('display','');
					parent.updateTrust(index,1);
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
			},"json");
	}

	/**
	 * 移除信任
	 * 
	 * @param userId
	 * @param index
	 */
	function removeTrust() {
		$(".opt_btn").hide();
		$(".loading").show();
		$.post("./admin_user/user_updateTrust",{
			'userId':userId,
			'trust':0,
			},function(result){
				$(".opt_btn").show();
				$(".loading").hide();
				if(result['result'] == 0) {
					$("#addTrust").css('display','');
					$("#cancelTrust").css('display','none');
					parent.updateTrust(index,0);
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
			},"json");
	}
	
	
	/**
	 * 保存评论
	 */
	function saveComment() {
		var commentsStr = $("#rl_exp_input").val(),
			//labelId = $("#labelId_comment").combobox('getValue');
			labelId = 5;
		if(labelId == "") {
			labelId = 5;
		}
		if(commentsStr == "") {
			$.messager.alert('提示',"请输入评论内容");
			return;
		}
		$("#saveBtn").hide();
		$("#div-add-comment .loading").show();
		$.post("./admin_interact/comment_batchSaveComment",{
			'labelId':labelId,
			'commentsStr':commentsStr
		}, function(result){
			if(result['result'] == 0) {
				$("#rl_exp_input").val('');
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			$("#saveBtn").show();
			$("#div-add-comment .loading").hide();
		},'json');
	}
	
</script>

<style type="text/css">
	body {
		font-size: 14px;
	}
	.xm-bq{margin:10px 0;font-size:14px;color:#333;}
	.xm-bq a{color:#09c;margin:0 5px;}
	.xm-bq a:hover{color:#E10602;}
	.rl_exp {margin-left:10px; width:624px}
	.rl_exp_main {height:230px; overflow-y: scroll;}
	.comment-main{
		width:502px;
		float:right;
		text-align:left;
	}
	.comment-main textarea{width:100%;border:1px solid #dcdcdc;}
	.comment-main textarea:focus{outline:none;border-color:#4bf;}
	.comment-main a{font-size:12px;text-decoration:none;color:#09c;}
	.comment-main a:hover{color:#E10602;}
	
	#rl_exp_input {
		width: 502px;
		margin-left:10px;
		margin-top: 15px;
		float:right;
	}
	#rl_bq {
		text-align: center;
	}
	.div-comment-opt{
		background-color:#f3f3f3;
		width:508px;
		margin-top: 10px;
		padding-top:15px;
		height:38px;
		text-align: center;
		float:right;
	}
</style>
</head>
<body>
	<div id="main">
		<div style="height:480px;width:50%;float:left">
			<iframe src="<%=shortLink %>?adminKey=zhangjiaxin" style="float:right;height:100%;width:502px;" id="htworld"></iframe>
		</div>
		<!-- 添加互动 -->
		<div style="height:480px;width:50%;float:right">
			<div id="htm_interact" style="font-size:12px;margin-left:10px;height:100%;width:100%">
				<form id="interact_form" action="./admin_interact/worldlevel_AddLevelWorld" method="post" class="none">
					<table class="htm_edit_table"  width="600px;" class="none">
						<tbody>
							<tr style="display:none;">
								<td class="leftTd">织图id：</td>
								<td><input type="text" id="worldId_interact" name="world_id"  onchange="validateSubmitOnce=true;" readonly="readonly" value="<%=worldId%>"/></td>
								<td class="rightTd"><div id="worldId_Tip" class="tipDIV"></div></td>
							</tr>
							
							<tr>
								<td class="leftTd"><div style="width:120px;" >织图等级：</div></td>
								<td><input style="width:120px" class="easyui-combobox" id="levelId" name="id"  onchange="validateSubmitOnce=true;" 
									data-options="valueField:'id',textField:'level_description',url:'./admin_interact/worldlevel_QueryoWorldLevel'"/></td>
								<td class="rightTd"><div id="levelId_Tip" class="tipDIV" style="padding-left:10px;">已添加：播放【<span id="clickSum_interact">0</span>】&nbsp;喜欢【<span id="likedSum_interact">0</span>】&nbsp;评论【<span id="commentSum_interact">0</span>】</div></td>
							</tr>
							<tr>
								<td class="leftTd" style="padding-top: 8px;">评论：</td>
								<td style="padding-top: 8px;"><input  class="easyui-combobox" name="comments" id="comments_interact" style="width:120px;"/></td>
								<td class="rightTd" style="padding-top: 8px;"><div id="comments_interactTip" class="tipDIV" style="width:350px;padding-left:10px;">已选：<span id="selected_comment_count">0</span></div></td>
							</tr>
							<tr class="opt_btn">
								<td style="text-align: center;padding-top: 15px;">
									<a class="easyui-linkbutton hideBtn expertBtn" style="display:none;" id="addExpertBtn" iconCls="icon-add" onclick="toBeStar();">达人</a>
									<a class="easyui-linkbutton hideBtn expertBtn" style="display:none;" id="waitExpertBtn" iconCls="icon-tip" >达人</a>
									<a class="easyui-linkbutton hideBtn expertBtn" style="display:none;" id="rejectExpertBtn" iconCls="icon-no" >达人</a>
									<a class="easyui-linkbutton hideBtn expertBtn" style="display:none;" id="okExpertBtn" iconCls="icon-ok" >达人</a>
									<a class="easyui-linkbutton hideBtn superbBtn" style="display:none;" id="addSuperbBtn" iconCls="icon-add" onclick="toBeSuperb();">精选</a>
									<a class="easyui-linkbutton hideBtn superbBtn" style="display:none;" id="okSuperbBtn" iconCls="icon-ok">精选</a>
								</td>
								<td style="text-align: center;padding-top: 15px;">
									<a class="easyui-linkbutton" iconCls="icon-ok" onclick="interactSubmit();">添加</a> 
									<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="btnCancel();">取消</a>
								</td>
								<td style="text-align: center;padding-top: 15px;">
									<a class="easyui-linkbutton hideBtn" style="display:none;" id="addLastest" iconCls="icon-add" onclick="updateLatestValid(1);">最新</a>
									<a class="easyui-linkbutton hideBtn" style="display:none;" id="cancelLastest" iconCls="icon-cancel" onclick="updateLatestValid(0);">取消最新</a>
									<a class="easyui-linkbutton hideBtn" style="display:none;" id="addTrust" iconCls="icon-add" onclick="addTrust();">信任</a>
									<a class="easyui-linkbutton hideBtn" style="display:none;" id="cancelTrust" iconCls="icon-cancel" onclick="removeTrust();">取消信任</a>
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
				<br>
				<table id="comments_interact_table" style="float:right;margin-top:20px;"></table>
			</div>
		</div>
		
		<!-- 添加评论 -->
		<div id="div-add-comment">
			<!-- 添加评论 -->
			<div style="width:50%;height: 230px;float:left;">
				<div id="comment" class="comment-main">
					<textarea name="content" id="rl_exp_input" rows="13"></textarea>
					<div class="div-comment-opt">
						<a id="saveBtn" class="easyui-linkbutton" iconCls="icon-ok" title="保存评论" onclick="javascript:saveComment()" href="javascript:void(0);">确定</a>
						<span class="loading none">
						<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
						<span style="vertical-align:middle;">请稍后...</span>
						</span>
						<a class="easyui-linkbutton" iconCls="icon-cancel" title="关闭窗口" onclick="closePage();">取消</a>
					</div>
				</div>
			</div>
			
			<!-- emoji表情 -->
			<div style="width:50%;height: 230px;float:right;margin-top:15px;">
				<div  class="rl_exp" id="rl_bq" style="position: static;">
					<ul class="rl_exp_main clearfix rl_selected"></ul>
				</div>
			</div>
		</div>
		
		
		<div id="comment_tb" style="padding:5px;height:auto" class="none">
				<a href="#htm_add_comment" onclick="addComment(0);" class="easyui-linkbutton" style="vertical-align:middle;" title="添加评论" plain="true" iconCls="icon-add" id="addCommentBtn">添加</a>
				<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
					<input id="labelId_interact" name="labelId" style="width:120px;" />
					<input id="ss_searchLabel" style="width:100px;" ></input>
					<input id="ss_comment" searcher="searchComment" class="easyui-searchbox" prompt="搜索评论" style="width:100px;"></input>
		   		</div>
	   		<div id="worldLabelDiv" style="margin:8px 0 5px 3px;"></div>
		</div>
		
	</div>
</body>
</html>