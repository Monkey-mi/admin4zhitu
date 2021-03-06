<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广告评论管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">

	var maxId=0;
	htmTableTitle = "广告评论管理";

	loadDataURL = "./admin_ztworld/adComment_queryADCommentList";	// 数据装载请求地址
	
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
	    			{field:'authorAvatar',title:'评论人头像',align:'center',
	    				formatter: function(value, row, index) {
		    				var imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg');
		    				return content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
		    			}
	    			},
	    			{field:'authorName',title:'评论人',align:'center'},
	    	        {field:'authorId',title:'评论人ID',align:'center'},
	    	        {field:'content',title:'评论内容',align:'center',
	    	        	formatter: function(value,row,index) {
	    	        		var str = value;
	    	        		// 以50个字长度为分隔，向下去整数，小数点舍去，得到结果看需要换行几次
	    	        		var lgth = 50;
	    	        		var newlineCount = Math.floor(value.length/lgth);
	    	        		if (newlineCount > 0) {
	    	        			for (var i=0; i<=newlineCount; i++) {
	    	        				if ( i==0 ) {
	    	        					str = value.substring(0,lgth-1);
	    	        				} else {
	    	        					str += "<br>";
	    	        					str += value.substring(i*lgth-1,(i+1)*lgth-1);
	    	        				}
	    	        			}
	    	        		}
	    	        		return str;
	    	  			}
	    	        },
	    	        {field:'worldId',title:'被评论织图ID',align:'center'},
	    	        {field:'commentDate',title:'评论时间',align:'center'},
	    	        {field:'valid',title:'是否生效',align:'center',
	    	        	formatter: function(value,row,index) {
	    	  				var valid;
	    	  				switch(value) {
	    	  				case 0:
	    	  					tip = "已失效，点击恢复，使评论生效";
	    	  					img = "./common/images/tip.png";
	    	  					valid = 1;
	    	  					break;
	    	  				default:
	    	  					tip = "点击使评论失效";
	    	  					img = "./common/images/ok.png";
	    	  					valid = 0;
	    	  					break;
	    	  				}
	    	  				return "<img title='"+ tip + "' class='htm_column_img pointer' onclick='updateValidOp("+ row.id +","+ valid +")' src='" + img + "'/>";
	    	  			}
	    	        }
	    	    ];
	
	/**
	 * 操作评论是否失效
	 */
	function updateValidOp(worldCommnetId,valid) {
		$.post("./admin_ztworld/adComment_updateADComment",{worldCommnetId:worldCommnetId,valid:valid}, function(result){
			if(result['result'] == 0) {
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		});
	};
	
	/**
	 * 删除广告评论
	 */
	function delADComments() {
		var rows = $("#htm_table").datagrid("getSelections");
		var ids = [];
		for (var i=0; i<rows.length; i++) {
			ids.push(rows[i].id);
		}
		$.post("./admin_ztworld/adComment_deleteADComments",{ids:ids.toString()}, function(result){
			if(result['result'] == 0) {
				$("#htm_table").datagrid("clearSelections");
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		});
	};
	
	/**
	 * 打开广告关键词维护页面
	 */
	function openADKeywordPage() {
		$.fancybox({
			'margin'			: 20,
			'width'				: '100%',
			'height'			: '100%',
			'autoScale'			: true,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'href'				: 'page_operations_adKeyword',
			onClosed			: function(){}
		});
	};
	
</script>
</head>
<body>
	<div id="tb">
		<a id="delBtn" href="javascript:void(0);" onclick="javascript:delADComments();" class="easyui-linkbutton" title="批量删除广告评论" plain="true" iconCls="icon-cut">删除</a>
		<a id="openPageBtn" href="javascript:void(0);" onclick="javascript:openADKeywordPage();" class="easyui-linkbutton" title="点击打开维护广告关键词页面" plain="true" iconCls="icon-converter">维护广告关键词</a>
	</div>
	<table id="htm_table"></table>
</body>
</html>