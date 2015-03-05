<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自动回复列表管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<script type="text/javascript">
var maxId = 0,
	myRowStyler= 0,
	htmTableTitle = "自动回复列表", //表格标题
	htmTableWidth =1240,
	loadDataURL = "./admin_interact/autoResponse_queryUncompleteResponse", //数据装载请求地址
	deleteURI = "./admin_interact/autoResponse_delAutoResponseByIds?idsStr=",
	updateValidURL = "./admin_interact/autoResponse_updateResponseCompleteByIds?idsStr=",
	updatePageURL = "./admin_interact/autoResponse_updateCommentContentByRowJson",
	htmTablePageList = [20,30,50,100,200],
	myQueryParams={},
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams.maxId = maxId;
		
		loadPageData(initPage);
	},
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	};
	//分页组件,可以重载
	columnsFields = [
		{field : 'ck',checkbox : true },
		{field : recordIdKey,title : 'ID',align : 'center', width : 45},
		{field : 'worldId',title : '织图ID',align : 'center',width : 75},
		worldURLColumn,
		{field : 'authorName',title: '马甲',align : 'center',width : 80},
		{field : 'reAuthorName',title : '被回复者',align : 'center',width : 80},
		{field : 'preComment',title : '上上次内容',width : 240},
		{field : 'reContext',title : '被回复内容',width : 240},
		{field : 'context', title:'回复内容', width : 250,editor:'text'},
		{field : 'operation', title:'操作',align : 'center' ,width : 75,
			formatter:function(value,row,index){
				return "<a title='查看' href='javascript:viewDialog(" + row.id + ")'>查看记录</a>";
			}
		},
		{field : 'responseId',hidden:true}
	],
	pageButtons = [{
	        iconCls:'icon-save',
	        text:'保存',
	        handler:function(){
	        	endEditing();
	        	var rows = $("#htm_table").datagrid('getChanges', "updated"); 
	        	$("#htm_table").datagrid('loading');
	        	$.post(updatePageURL,{
	        		'rowJson':JSON.stringify(rows)
	        		},function(result){
	        			if(result['result'] == 0) {
	        				$("#htm_table").datagrid('acceptChanges');
	        			} else {
	        				$.messager.alert('失败提示',result['msg']);  //提示失败信息
	        			}
	        			
	        		},"json");
	        	
	        	var ids = [];
				var responseIds = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i]['id']);
					responseIds.push(rows[i]['responseId'])
//					rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
				}	
				$.post(updateValidURL + ids + "&responseIdsStr=" + responseIds,function(result){
					$('#htm_table').datagrid('loaded');
					if(result['result'] == 0) {
//						$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
						$("#htm_table").datagrid("reload");
					} else {
						$.messager.alert('提示',result['msg']);
					}
				});	
				$("#htm_table").datagrid('loaded');
	        }
	    },{
	        iconCls:'icon-undo',
	        text:'取消',
	        handler:function(){
	        	$("#htm_table").datagrid('rejectChanges');
	        	$("#htm_table").datagrid('loaded');
	        }
	}],
	onAfterInit = function() {
		$("#teachRobot form").show();
		$("#teachRobot").window({
			modal : true,
			width : 450,
			top : 10,
			height : 300,
			title : '调教机器人',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$("#question_textarea").val("");
				$("#answer_textarea").val("");
			}
		});
		
	
	};
		
	function searchByUserLevel() {
		var userLevelId = $("#userLevelId_userLevel").combobox('getValue');
		myQueryParams.maxId=0;
		myQueryParams.userLevelId = userLevelId;
		
		$('#htm_table').datagrid('load',myQueryParams);
		
	}

	function viewDialog(autoResponseId) {
		uri = "page_interact_interactResponseDialog?autoResponseId="+autoResponseId;
		$.fancybox({
			'margin'			: 20,
			'width'				: '10',
			'height'			: '100%',
			'autoScale'			: true,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'href'				: uri
		});
	}
	
	/**
	 * 使选中的分类有效
	 */
	function updateValid() {
		var rows = $('#htm_table').datagrid('getSelections');	
		if(rows.length > 0){
			$.messager.confirm('更新记录', '您确定要更新已选中的记录有效性?', function(r){ 	
				if(r){				
					var ids = [];
					var responseIds = [];
					for(var i=0;i<rows.length;i+=1){		
						ids.push(rows[i]['id']);
						responseIds.push(rows[i]['responseId'])
	//					rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
					}	
					$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
					$('#htm_table').datagrid('loading');
					$.post(updateValidURL + ids + "&responseIdsStr=" + responseIds,function(result){
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
			$.messager.alert('更新失败','请先选择记录，再执行更新操作!','error');
		}
				
	}
	
	function teachRobot(){
		$("#teachRobot").window("open");
	}
	function teachRobotSubmit(){
		var teachForm = $("#teachRobotForm");
		$.post(teachForm.attr("action"),teachForm.serialize(),function(result){
			if(result["result"] == 0){
				$.messager.alert('提示',result["content"]);

				$("#question_textarea").val("");
				$("#answer_textarea").val("");
			}else{
				$.messager.alert('错误提示',result['msg']);
			}
		});
	}
	
</script>

</head>
<body>
	<table id="htm_table"></table>	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除分类织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
		<a href="javascript:void(0);" onclick="javascript:updateValid();" class="easyui-linkbutton" title="更新有效性" plain="true" iconCls="icon-reload" id="updateValidBtn">更新有效性</a>
		<a href="javascript:void(0);" onclick="javascript:teachRobot();" class="easyui-linkbutton" title="调教机器人" plain="true" iconCls="icon-add" id="updateValidBtn">调教机器人</a>
		<input class="easyui-combobox"  id="userLevelId_userLevel" onchange="validateSubmitOnce=true;" style="width:150px"
								data-options="valueField:'id',textField:'level_description',url:'./admin_interact/userlevel_QueryUserLevel'"/>
		<a href="javascript:void(0);" onclick="javascript:searchByUserLevel();" class="easyui-linkbutton" title="查询" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
	</div>
	
	<!-- 调教机器人 -->
	<div id="teachRobot" >
		<form action="./admin_interact/autoResponse_teachTuLingRobot" id="teachRobotForm" class="none" method="post">
			<table id="html_teachRobot_table" width="420px">
				<tbody>
					<tr>
						<td class="leftTd" width="100px">问题:</td>
						<td><textarea name="question"  id="question_textarea" maxlength="128" style="width:205px;height:90px;"></textarea></td>
					</tr>
					<tr>
						<td class="leftTd" >答案:</td>
						<td><textarea name="answer"  id="answer_textarea" maxlength="128" style="width:205px;height:90px;"></textarea></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="teachRobotSubmit();">确定</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#teachRobot').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>