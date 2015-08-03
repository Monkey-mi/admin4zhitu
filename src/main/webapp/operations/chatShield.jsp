<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>骚扰私聊管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">

	var maxId=0;
	htmTableTitle = "骚扰私聊管理";

	loadDataURL = "./admin_op/chat_queryChatList";	// 数据装载请求地址
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	};
	
	// 定义工具栏
	toolbarComponent = '#tb';
	
	columnsFields = [
	     			{field:'checkbox',checkbox:'true',align:'center'},
	    			{field:'id',title:'ID',align:'center'},      
	    			{field:'uId',title:'发私聊人id',align:'center'},
	    			{field:'uName',title:'发私聊人名称',align:'center'},
	    	        {field:'tId',title:'接私聊人id',align:'center'},
	    	        {field:'tName',title:'接私聊人名称',align:'center'},
	    	        {field:'relationship',title:'用户关系',align:'center',
	    	        	formatter: function(value,row,index) {
	    	  				var result;
	    	  				switch(value) {
	    	  				case -1:
	    	  					result = "未关注";
	    	  					break;
	    	  				case 0:
	    	  					result = "已关注";
	    	  					break;
	    	  				case 1:
	    	  					result = "互相关注";
	    	  					break;
	    	  				default:
	    	  					result = "未关注";
	    	  					break;
	    	  				}
	    	  				return result;
	    	  			}
	    	        },
	    	        {field:'msg',title:'私聊内容',align:'center'},
	    	        {field:'date',title:'发送时间',align:'center'},
	    	        {field:'userShield',title:'发私聊用户是否被屏蔽',align:'center',
	    	        	formatter: function(value,row,index) {
	    	  				var shield;
	    	  				switch(value) {
	    	  				case 0:
	    	  					tip = "已屏蔽，点击取消屏蔽发此私聊用户";
	    	  					img = "./common/images/tip.png";
	    	  					shield = 1;
	    	  					break;
	    	  				default:
	    	  					tip = "点击使发送此私聊用户屏蔽";
	    	  					img = "./common/images/ok.png";
	    	  					shield = 0;
	    	  					break;
	    	  				}
	    	  				return "<img title='"+ tip + "' class='htm_column_img pointer' onclick='updateShieldOp("+ row.uId +","+ shield +")' src='" + img + "'/>";
	    	  			}
	    	        }
	    	    ];
	
	/**
	 * 操作评论是否失效
	 */
	function updateShieldOp(uId, shield) {
		var url = "";
		if (shield == 1) {
			url = "./admin_user/user_shieldUser";
		} else if (shield == 0) {
			url = "./admin_user/user_unShieldUser";
		}
		$.post(url, {userId:uId}, function(result){
			if(result['result'] == 0) {
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		});
	};
	
	/**
	 * 批量删除骚扰私聊
	 */
	function delChats() {
		var rows = $("#htm_table").datagrid("getSelections");
		var ids = [];
		for (var i=0; i<rows.length; i++) {
			ids.push(rows[i].id);
		}
		$.post("./admin_op/chat_deleteChats",{ids:ids.toString()}, function(result){
			if(result['result'] == 0) {
				$("#htm_table").datagrid("clearSelections");
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		});
	};
	
</script>
</head>
<body>
	<div id="tb">
		<a id="delBtn" href="javascript:void(0);" onclick="javascript:delChats();" class="easyui-linkbutton" title="批量删除骚扰私聊" plain="true" iconCls="icon-cut">删除</a>
	</div>
	<table id="htm_table"></table>
</body>
</html>