<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String id = request.getParameter("id");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加互动评论</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css" />
<link rel="stylesheet" href="${webRootPath }/base/js/jquery/emotion/rl_exp.css" />
<style type="text/css">
	body {
		font-size: 14px;
	}
	.xm-bq{margin:10px 0;font-size:14px;color:#333;}
	.xm-bq a{color:#09c;margin:0 5px;}
	.xm-bq a:hover{color:#E10602;}
	.rl_exp {margin-left:10px; width:96%}
	.rl_exp_main {height:230px; overflow-y: scroll;}
	.comment-main{
		text-align:left;
	}
	.comment-main textarea{width:100%;border:1px solid #dcdcdc;}
	.comment-main textarea:focus{outline:none;border-color:#4bf;}
	.comment-main a{font-size:12px;text-decoration:none;color:#09c;}
	.comment-main a:hover{color:#E10602;}
	
	#rl_exp_input {
		width: 99%;
		margin-left:10px;
		margin-top: 15px;
	}
	#rl_bq {
		text-align: center;
	}
	.opt_layout {
		position: absolute;
		display: inline-block;
		top: 8px;
		right: 10px;
	}
	
</style>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/emotion/rl_exp.js"></script>
<script type="text/javascript">
	var id = <%=id%>;
	$(document).ready(function() {
		$("#labelId_comment").combotree({
			url:'./admin_interact/comment_queryLabelTree?hasTotal=false&selected=31',
		});
		showPageLoading();
		if(id == 0) {
			$("#saveBtn").click(function() {
				saveComment();
			});
			$("#labelId_comment").combotree('setValue',5);
			$("#rl_exp_input").focus();
			removePageLoading();
			$("#main").show();
		} else {
			$("#saveBtn").click(function() {
				updateComment(id);
			});
			$.post("./admin_interact/comment_queryCommentById",{
				"id":id
			},function(result) {
				if(result['result'] == 0) {
					var comment = result['comment'];
					$("#labelId_comment").combotree('setValue', comment['labelId']);
					$("#rl_exp_input").val(comment['content']).focus();
					removePageLoading();
					$("#main").show();
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			},'json');
		}
	});
	/**
	 * 保存评论
	 */
	function saveComment() {
		var content = $("#rl_exp_input").val(),
			labelId = $("#labelId_comment").combobox('getValue');
		if(labelId == "") {
			labelId = 31;
		}
		if(content == "") {
			$.messager.alert('提示',"请输入评论内容");
			return;
		}
		$("#saveBtn").hide();
		$(".opt_layout .loading").show();
		$.post("./admin_interact/comment_saveComment",{
			'labelId':labelId,
			'content':content
		}, function(result){
			if(result['result'] == 0) {
				$.messager.confirm(result['msg'],"还要继续添加吗？", function(r){
					if (r)
						$("#rl_exp_input").val("").focus();
					else {
						closePage();
					}
				});
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			$("#saveBtn").show();
			$(".opt_layout .loading").hide();
		},'json');
	}
	
	function updateComment(id) {
		var content = $("#rl_exp_input").val(),
			labelId = $("#labelId_comment").combobox('getValue');
		if(content == "") {
			$.messager.alert('提示',"请输入评论内容");
			return;
		}
		$("#saveBtn").hide();
		$(".opt_layout .loading").show();
		$.post("./admin_interact/comment_updateComment",{
			'id':id,
			'labelId':labelId,
			'content':content
		}, function(result){
			if(result['result'] == 0) {
				parent.$.fancybox.close();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			$("#saveBtn").show();
			$(".opt_layout .loading").hide();
		},'json');
	}
	
	/**
     * 关闭页面
     */
	function closePage() {
		parent.$.fancybox.close();
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
</script>
</head>
<body>
	<a href="javascript:void(0);" title="关闭窗口" class="close" onclick="closePage();">×</a>
	<div id="main" class="none">
	<div id="comment" class="comment-main">
		<textarea name="content" id="rl_exp_input" rows="5"></textarea>
	</div>
	<div class="rl_exp" id="rl_bq" style="display:none;">
		<ul class="rl_exp_tab clearfix">
			<li><a href="javascript:void(0);" class="selected">emoji</a></li>
		</ul>
		<div class="opt_layout">
			<input id="labelId_comment" name="labelId" style="width:100px;" />
			<a id="saveBtn" class="easyui-linkbutton" iconCls="icon-ok" title="保存评论">确定</a>
			<span class="loading none">
			<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
			<span style="vertical-align:middle;">请稍后...</span>
			</span>
			<a class="easyui-linkbutton" iconCls="icon-cancel" title="关闭窗口" onclick="closePage();">取消</a>
		</div>
		<ul class="rl_exp_main clearfix rl_selected"></ul>
	</div>
	</div>
</body>
</html>