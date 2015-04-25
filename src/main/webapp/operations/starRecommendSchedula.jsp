<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>达人推荐计划管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
	var maxId=0,
	loadDateUrl="./admin_op/schedulaStarRecommend_queryStarRecommendSchedula",
	delUrl="./admin_op/schedulaStarRecommend_batchDelStarRecommendSchedula?idStr=",
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
					title  :"达人推荐计划管理",
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
						{field :'ck',checkbox:true},
						{field :'id',title:'ID',align:'center',width:80},
						{field : 'osrId',title: '推荐ID',align : 'center',width : 80},
						{field : 'userId',title: '用户ID',align : 'center',width : 100},
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
				  		{field : 'schedula', title:'计划时间',align : 'center' ,width : 130},
						{field : 'addDate', title:'创建时间',align : 'center' ,width : 130	},
						{field : 'modifyDate', title:'最后修改时间',align : 'center' ,width : 130},
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
		$('#htm_resort').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 235,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-tip',
			resizable : false
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
	
	/**
	 * 重新排序
	 */
	function reSortInit() {
		$("#resort_form").find('input[name="reIndexId"]').val('');
		//$("#schedula").datetimebox('clear');
		$('#htm_resort .opt_btn').show();
		$('#htm_resort .loading').hide();
		
		var rows = $("#htm_table").datagrid('getSelections');
		$('#resort_form .reindex_column').each(function(i){
			if(i<rows.length)
				$(this).val(rows[i]['osrId']);
		});
		$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值
		// 打开添加窗口
		$("#htm_resort").window('open');
		
	}

	function submitReSortForm() {
		var $form = $('#resort_form');
		if($form.form('validate')) {
			$('#htm_resort .opt_btn').hide();
			$('#htm_resort .loading').show();
			$('#resort_form').form('submit', {
				url: $form.attr('action'),
				success: function(data){
					var result = $.parseJSON(data);
					$('#htm_resort .opt_btn').show();
					$('#htm_resort .loading').hide();
					if(result['result'] == 0) { 
						$('#htm_resort').window('close');  //关闭添加窗口
						$("#htm_table").datagrid("reload");
					} else {
						$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
					}
					
				}
			});
		}
		
	}
	
	function searchStarRecommendSchedula(){
		var valid = $("#ss_valid").combobox('getValue');
		var finish = $("#ss_finish").combobox('getValue');
		var queryParams = {
				'valid':valid,
				'finish':finish
		};
		$("#htm_table").datagrid("load",queryParams);
		
	}
	
	function searchByUserId(){
		var userId = $("#ss_userId").searchbox('getValue');
		var queryParams = {
				'userId':userId
		}
		$("#htm_table").datagrid("load",queryParams);
	}
	
	function searchByOrsId(){
		var osrId = $("#ss_orsId").searchbox('getValue');
		var queryParams = {
				'osrId':osrId
		}
		$("#htm_table").datagrid("load",queryParams);
	}
	
</script>
</head>
<body>
	<div id="main">
		<div id="tb">
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSortInit();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">重新排序</a>
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
	   		<a href="javascript:void(0);" onclick="javascript:searchStarRecommendSchedula();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	   		<input id="ss_userId" searcher="searchByUserId" class="easyui-searchbox" prompt="输入用户ID" style="width:150px;" />
	   		<input id="ss_orsId" searcher="searchByOrsId" class="easyui-searchbox" prompt="输入推荐ID" style="width:150px;" />
		</div>
		<table id="htm_table"></table>
		
		<!-- 重排频道织图计划 -->
		<div id="htm_resort">
			<form id="resort_form" action="./admin_op/schedulaStarRecommend_reSort" method="post">
				<table class="htm_resort_table" width="580">
					<tbody>
						<tr>
							<td class="leftTd">织图ID：</td>
							<td>
								<input name="reIndexId" class="easyui-validatebox reindex_column" required="true"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<br />
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<br />
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<br />
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<input name="reIndexId" class="reindex_column"/>
								<br/>
							</td>
						</tr>
						<tr>
							<td class="leftTd">计划更新时间：</td>
							<td><input id="schedula" name="schedula" class="easyui-datetimebox"><span>持续时长（分钟）</span><input id='timeMinute' name='timeMinute' value="120"></td>
						</tr>
						<tr>
							<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReSortForm();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_resort').window('close');">取消</a>
							</td>
						</tr>
						<tr class="loading none">
							<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
								<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
								<span style="vertical-align:middle;">排序中...</span>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div> 
	</div>
</body>
</html>