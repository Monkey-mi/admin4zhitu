<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>互粉乎赞评论标签管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
	var maxId=0,
	searchChannelMaxId = 0,
	searchChannelQueryParams = {
		'maxId':searchChannelMaxId
	},
	loadDateUrl="./admin_interact/likeFollow_queryLikeFollowCommentLabel",
	addUrl="./admin_interact/likeFollow_insertLikeFollowCommentLabel",
	delUrl="./admin_interact/likeFollow_batchDeleteLikeFollowCommentLabel?idsStr=",
	tableQueryParams = {},
	tableInit = function() {
		tableLoadDate(1);
	};
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			tableQueryParams.maxId = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				tableQueryParams.maxId = maxId;
			}
		}
	};
	
	function tableLoadDate(pageNum){
		$("#htm_table").datagrid({
			title  :"互粉乎赞评论标签管理",
			width  :1200,
			pageList : [10,30,50,100,300],
			pageSize : 10,
			loadMsg:"加载中....",
			url	   :	loadDateUrl,
			queryParams : tableQueryParams,
			remoteSort: true,
			pagination: true,
			idField   :'id',
			pageNumber: pageNum,
			toolbar:'#tb',
			columns: [[
				{field :'ck',checkbox:true},
				{field :'id',title:'ID',align:'center',width:80},
				{field :'labelId',title:'标签ID',align:'center',width:80},
				{field :'labelName',title: '标签名称',align : 'center',width : 180},
			]],
			onLoadSuccess:myOnLoadSuccess,
			onBeforeRefresh : myOnBeforeRefresh
		});
		var p = $("#htm_table").datagrid("getPager");
		p.pagination({
		});
	}
	
	
	
	$(function() {
		$('#htm_add').window({
			title : '添加马甲等级',
			modal : true,
			width : 490,
			height : 170,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				$("#i-commentLabelId").combotree('clear');
			}
		});

		

		$("#i-commentLabelId").combotree({
			url : './admin_interact/comment_queryLabelTree?hasTotal=true'
		});

		tableInit();
		$("#main").show();
	});

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
	 * 批量删除
	 */
	function del() {
		$.messager.confirm('更新缓存', '确定要将选中的内容删除?', function(r) {
			if (r) {
				update(delUrl);
			}
		});
	}

	function update(url) {
		var rows = $('#htm_table').datagrid('getSelections');
		var ids = [];
		for (var i = 0; i < rows.length; i += 1) {
			ids.push(rows[i]['id']);
		}
		$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
		$('#htm_table').datagrid('loading');
		$.post(url + ids, function(result) {
			$('#htm_table').datagrid('loaded');
			if (result['result'] == 0) {
				$.messager.alert('提示', result['msg']);
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('提示', result['msg']);
			}

		});
	}

	/**
	 * 判断是否选中要删除的记录
	 */
	function isSelected(rows) {
		if (rows.length > 0) {
			return true;
		} else {
			$.messager.alert('操作失败', '请先选择记录，再执行操作!', 'error');
			return false;
		}
	}

	function addInit() {
		$('#htm_add').window('open');
	}

	function addSubmit() {
		var commentLabelId = $("#i-commentLabelId").combotree('getValue');
		var type = $("#s-type").val();
		if(!type || !commentLabelId){
			alert("类型、标签不能为空");
		}
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		$.post(addUrl, {
			'labelId' : commentLabelId,
			'type'	  : type
		}, function(result) {
			$('#htm_add .opt_btn').show();
			$('#htm_add .loading').hide();
			if (result['result'] == 0) {
				tableQueryParams.maxId = 0;
				$("#htm_table").datagrid("load", tableQueryParams);
				$('#htm_add').window('close');
			} else {
				$.messager.alert('提示', result['msg']);
			}
		}, "json");
	}

	 

</script>
</head>	
<body>
	<div id="main" class="none">
		<div id="tb">
			<a href="javascript:void(0);" onclick="javascript:addInit();" class="easyui-linkbutton" title="添加" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="t'./admin_op/channel_searchChannel'rue" iconCls="icon-cut" id="delBtn">删除</a>
		</div>
		<table id="htm_table"></table>
		<!-- 添加记录 -->
		<div id="htm_add">
			<form id="add_form"  method="post">
				<table class="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">标签：</td>
							<td>
								<input id="i-commentLabelId" >
							</td>
						</tr>
						<tr>
							<td class="leftTd">标签：</td>
							<td>
								<select id="s-type"  style="width:173px">
									<option value="0">互赞</option>
									<option value="0">互粉</option>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit();">添加</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
	</div>
	
</body>
</html>