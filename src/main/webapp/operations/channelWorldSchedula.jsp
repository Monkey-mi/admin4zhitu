<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>频道用户管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<script type="text/javascript">
	var maxId=0,
	loadDateUrl="./admin_op/cwSchedula_queryChannelWorldSchedulaForList",
	delUrl="./admin_op/cwSchedula_delChannelWorldSchedula?idsStr=",
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
		$("#htm_table").datagrid(
				{
					title  :"频道织图计划管理",
					width  :1200,
					pageList : [10,30,50,100],
					loadMsg:"加载中....",
					url	   :	loadDateUrl,
					queryParams : tableQueryParams,
					pagination: true,
					pageNumber: pageNum,
					toolbar:'#tb',
					columns: [[
						{field :'ck',checkbox:true},
						{field :'id',title:'ID',align:'center',width:80},
						{field : 'channelId',title: '频道ID',align : 'center',width : 80},
						{field : 'worldId',title:'织图ID',align:'center',width : 80},
						{field : 'channelName',title: '频道名称',align : 'center',width : 130},
						{field : 'userId',title: '用户ID',align : 'center',width : 100},
						{field : 'userName',title: '用户名称',align : 'center',width : 130},
						{field : 'valid',title : '有效性',align : 'center', width: 45,
				  			formatter: function(value,row,index) {
				  				if(value == 1) {
				  					img = "./common/images/ok.png";
				  					return "<img title='有效' class='htm_column_img'  src='" + img + "'/>";
				  				}
				  				img = "./common/images/tip.png";
				  				return "<img title='无效' class='htm_column_img' src='" + img + "'/>";
				  			}
				  		},
				  		{field : 'finish',title : '完成标志',align : 'center', width: 65,
				  			formatter: function(value,row,index) {
				  				if(value == 1) {
				  					img = "./common/images/ok.png";
				  					return "<img title='有效' class='htm_column_img'  src='" + img + "'/>";
				  				}
				  				img = "./common/images/tip.png";
				  				return "<img title='无效' class='htm_column_img' src='" + img + "'/>";
				  			}
				  		},
				  		{field : 'schedulaDate', title:'计划时间',align : 'center' ,width : 130,
							formatter:function(value,row,index){
								return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
							}
						},
						{field : 'addDate', title:'创建时间',align : 'center' ,width : 130,
							formatter:function(value,row,index){
								return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
							}
						},
						{field : 'modifyDate', title:'最后修改时间',align : 'center' ,width : 130,
							formatter:function(value,row,index){
								return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
							}
						},
						{field : 'operatorName',title: '最后操作者',align : 'center',width : 80}
						
					]],
					onLoadSuccess:myOnLoadSuccess,
					onBeforeRefresh : myOnBeforeRefresh
				
				}	
		);
		var p = $("#htm_table").datagrid("getPager");
		p.pagination({
		});
	}
	
	
	$(function(){
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
	
	
	function del(){
		update(delUrl);
	}
	
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
</script>
</head>
<body>
	<div id="main">
		<div id="tb">
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
		</div>
		<table id="htm_table"></table>
	</div>
</body>
</html>