<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道红人管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer} "></script>
<script type="text/javascript">

	var maxId = 0;
	var channelId = baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID") ? baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID") : "";
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['star.maxId'] = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['star.maxId'] = maxId;
			}
		}
	};
	
	toolbarComponent = '#tb';
	
	// 数据装载请求地址
	loadDataURL = "./admin_op/channelmember_queryChannelMember";
	addRecommendMsgURL = "./admin_op/channel_addStarRecommendMsgs",
	
	columnsFields = [
     		{field: 'ck', checkbox: true },
     		{field:'channelMemberId', title: '频道成员表id', align: 'center'},
     		{field:'channelStarId', title: '频道红人表id', align: 'center'},
     		{field:'userId', title: '用户ID', align: 'center'},
     		phoneCodeColumn,
     		userAvatarColumn,
     		userNameColumn,
     		sexColumn,
     		userLabelColumn,
     		concernCountColumn,
     		followCountColumn,
     		{
     			field : 'worldCount',title:'织图',align : 'center', width : 60,
	     			formatter: function(value,row,index){
	     				var uri = "page_user_userWorldInfo?userId="+row.userId;
	     				return "<a title='显示织图' class='updateInfo' href='javascript:showUserWorld(\""+uri
	     					+"\")'>"+value+"</a>"; 
	     			}
     		},
     		{
     			field: 'degree', title: '用户等级', align: 'center'
     		},
     		{
     			field : 'notified',title : '通知状态',align : 'center', width : 60,
	     			formatter: function(value,row,index) {
	       				if(value >= 1) {
	       					img = "./common/images/ok.png";
	       					return "<img title='已经通知' class='htm_column_img' src='" + img + "'/>";
	       				}
	       				img = "./common/images/tip.png";
	       				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
	       			}
     		},
     		{
     			field : 'valid',title : '有效性',align : 'center', width: 45,
	       			formatter: function(value,row,index) {
	       				if(value == 1) {
	       					img = "./common/images/ok.png";
	       					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
	       				}
	       				img = "./common/images/tip.png";
	       				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
	       			}
       		},
       		{
       			field : 'weight',title : '置顶为最新红人',align : 'center', width : 60,
	     			formatter: function(value,row,index) {
	     				img = "./common/images/edit_add.png";
	       				title = "点击设置为最新红人";
	       				return "<img title='" + title +  "' class='htm_column_img pointer' onclick='updateWeight(\"" + index + "\",\"" + row.channelStarId + ")' src='" + img + "'/>";
	       			}
     		}
	];
	
	/**
	 * 显示用户织图  
	 */
	function showUserWorld(uri){
		$.fancybox({
			'margin'			: 10,
			'width'				: '100%',
			'height'			: '100%',
			'autoScale'			: true,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'href'				: uri
		});
	};
	
	/**
	 * 批量通知操作方法
	 */
	function batchNotify() {
		var rows = $('#htm_table').datagrid('getSelections');	
		if(rows.length > 0){
			$.messager.confirm('添加通知', '你确定要批量添加通知', function(r){ 	
				if(r){				
					var ids = [];
					for(var i=0;i<rows.length;i+=1){		
						ids.push(rows[i][recordIdKey]);
					}	
					$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
					$('#htm_table').datagrid('loading');
					$.post(addRecommendMsgURL, {ids: ids}, function(result){
						$('#htm_table').datagrid('loaded');
						if(result['result'] == 0) {
							$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
							$("#htm_table").datagrid("reload");
						} else {
							$.messager.alert('提示',result['msg']);
						}
					});				
				}	
			});	
		}else{
			$.messager.alert('通知失败','请先选择记录，再执行更新操作!','error');
		}
	}

</script>
</head>
<body>
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<a href="javascript:void(0);" onclick="javascript:batchNotify();" class="easyui-linkbutton" title="批量通知" plain="true" iconCls="icon-ok">批量通知</a>
	</div>

</body>
</html>