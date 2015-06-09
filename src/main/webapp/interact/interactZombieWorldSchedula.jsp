<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>马甲发图计划管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
	var maxId=0,
	loadDateUrl="./admin_interact/zombieWorldSchedula_queryZombieWorldSchedula",
	delUrl="./admin_interact/zombieWorldSchedula_batchDeleteZombieWorldSchedula?idsStr=",
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
					title  :"马甲发图计划管理",
					width  :1200,
					pageList : [10,30,50,100],
					loadMsg:"加载中....",
					url	   :	loadDateUrl,
					queryParams : tableQueryParams,
					remoteSort: true,
					pagination: true,
					idField   :'id',
					pageNumber: pageNum,
					toolbar:'#tb',
					columns: [[
						{field : 'ck',checkbox:true},
						{field : 'id',title:'ID',align:'center',width:80},
						{field : 'zombieWorldId',title: '马甲织图ID',align : 'center',width : 80},
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
				  		{field : 'finished',title : '完成标志',align : 'center', width: 65,
				  			formatter: function(value,row,index) {
				  				if(value == 1) {
				  					img = "./common/images/ok.png";
				  					return "<img title='有效' class='htm_column_img'  src='" + img + "'/>";
				  				}
				  				img = "./common/images/tip.png";
				  				return "<img title='无效' class='htm_column_img' src='" + img + "'/>";
				  			}
				  		},
				  		{field : 'schedula', title:'计划时间',align : 'center' ,width : 130,
							formatter:function(value,row,index){
								return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
							}
						},
						{field : 'addDate', title:'创建时间',align : 'center' ,width : 130,
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
	
	
	function searchZombieWorldSchedula(){
		var valid = $("#ss_valid").combobox('getValue');
		var finished = $("#ss_finish").combobox('getValue');
		var queryParams = {
				'maxId':0,
				'valid':valid,
				'finished':finished
		};
		$("#htm_table").datagrid("load",queryParams);
		
	}
</script>
</head>
<body>
	<div id="main">
		<div id="tb">
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="批量删除" plain="true" iconCls="icon-cut" id="delBtn">批量删除</a>
			<select id="ss_valid" class="easyui-combobox"  style="width:100px;">
		        <option value="" selected="selected">有效性</option>
		        <option value="0">无效</option>
		        <option value="1">有效</option>
	   		</select>
		   	<select id="ss_finish" class="easyui-combobox"  style="width:100px;">
		        <option value="" selected="selected">完成标志</option>
		        <option value="0">未完成</option>
		        <option value="1">已完成</option>
	   		</select>
	   		<a href="javascript:void(0);" onclick="javascript:searchZombieWorldSchedula();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
		</div>
		<table id="htm_table"></table>
		
	</div>
</body>
</html>