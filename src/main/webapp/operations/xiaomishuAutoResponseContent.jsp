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
	recordIdKey='responseId',
	hideIdColumn = false,
	loadDataURL = "./admin_op/xmsResponse_queryXiaoMiShuResponseContentForTable", //数据装载请求地址
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
		{field : recordIdKey,title : '回复ID',align : 'center', width : 55},
		{field : 'moduleId',title : '模块ID',align : 'center',width : 75},
		{field : 'moduleName',title: '模块',align : 'center',width : 80},
		{field : 'content',title : '回复内容',width : 440,
			formatter:function(value,row,index){
				var img = "./base/js/jquery/jquery-easyui-1.3.2/themes/icons/pencil.png";
				var str = "<img title='修改' class='htm_column_img pointer' src='" + img + "' onclick='javascript:updateContent(\""+row[recordIdKey] +"\",\""+index +"\")'/>";
				if(value == undefined || value == "") {
					return str;
				} else if(value.length > 35) {
					return value.substr(0,33) +"..."+str;
				} else {
					return value+str;
				}
			}
		},
		{field : 'operatorName',title: '最后修改者',align : 'center',width : 80},
		{field : 'modifyDate', title:'最后修改时间日期', align : 'center',width : 150, 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		},
		{field : 'operation', title:'操作', align : 'center',width : 120, 
			formatter: function(value,row,index){
				return "<a href='#' onclick='javascript:addKeyForContent(\""+row[recordIdKey]+"\",\"" +row['moduleId'] +"\")'>添加关键字</a>"
			}
		}
	],
	onAfterInit = function() {
		$("#teachRobot form").show();
		$("#add_content").window({
			modal : true,
			width : 470,
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
				$("#module_id").combobox('clear');
				$("#content").val("");
			}
		});
		
		$("#add_contentKey").window({
			modal : true,
			width : 470,
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
				$("#contentKey").val("");
				$("#response_id").val("");
				$("#keyModuleId").val("");
			}
		});
		
		$("#update_content").window({
			modal : true,
			width : 470,
			top : 10,
			height : 250,
			title : '调教机器人',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$("#updateResponse_id").val('');
				$("#updateContent").val('');
				$("#updateIndex").val('');
			}
		});
	};
	
	//增加回复窗口初始化
	function addContent(){
		$('#add_content_form .opt_btn').hide();
		$('#add_content_form .loading').show();
		
		$("#add_content").window('open');
		$('#add_content_form .opt_btn').show();
		$('#add_content_form .loading').hide();
	}
	
	//增加回复
	function addContentSubmit(){
		var form = $("#add_content_form");
		$('#add_content .opt_btn').hide();
		$('#add_content .loading').show();
		$.post(form.attr("action"),form.serialize(),function(result){
			$('#add_content .opt_btn').show();
			$('#add_content .loading').hide();
			if(result['result'] == 0) {
				$('#add_content').window('close');  //关闭添加窗口
				$("#htm_table").datagrid('reload');
				$.messager.alert('提示',result['msg']);  //提示添加信息成功
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},"json");
	}
	
	
	//批量添加关键字窗口初始化
	function addKeyForContent(responseId,moduleId){
		$('#add_contentKey_form .opt_btn').hide();
		$('#add_contentKey_form .loading').show();
		$("#response_id").val(responseId);
		$("#keyModuleId").val(moduleId);
		$("#add_contentKey").window('open');
		$('#add_contentKey_form .opt_btn').show();
		$('#add_contentKey_form .loading').hide();
	}
	
	
	//批量为该回复添加关键字
	function addKeyForContentSubmit(){
		var form = $("#add_contentKey_form");
		$('#add_contentKey .opt_btn').hide();
		$('#add_contentKey .loading').show();
		$.post(form.attr("action"),form.serialize(),function(result){
			$('#add_contentKey .opt_btn').show();
			$('#add_contentKey .loading').hide();
			if(result['result'] == 0) {
				$('#add_contentKey').window('close');  //关闭添加窗口
				$("#htm_table").datagrid('reload');
				$.messager.alert('提示',result['msg']);  //提示添加信息成功
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},"json");
	}
	
	function updateContent(responseId,index){
		var rows = $("#htm_table").datagrid('getRows');
		var content1 = rows[index]['content'];
		$('#update_content .opt_btn').hide();
		$('#update_content .loading').show();
		$("#updateResponse_id").val(responseId);
		$("#updateContent").val(content1);
		$("#updateIndex").val(index);
		$("#update_content").window('open');
		$('#update_content .opt_btn').show();
		$('#update_content .loading').hide();
	}
	
	function updateContentSubmit(){
		var form = $("#update_content_form");
		var content = $("#updateContent").val();
		$('#update_content .opt_btn').hide();
		$('#update_content .loading').show();
		$.post(form.attr("action"),form.serialize(),function(result){
			$('#update_content .opt_btn').show();
			$('#update_content .loading').hide();
			if(result['result'] == 0) {
				$('#update_content').window('close');  //关闭添加窗口
				updateValue(index,'content',content);
				$.messager.alert('提示',result['msg']);  //提示添加信息成功
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},"json");
	}
	
</script>

</head>
<body>
	<table id="htm_table"></table>	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<a href="javascript:void(0);" onclick="javascript:addContent();" class="easyui-linkbutton" title="增加小秘书回复" plain="true" iconCls="icon-add" id="delBtn">添加</a>
		<a href="javascript:void(0);" onclick="javascript:delContent();" class="easyui-linkbutton" title="删除小秘书回复" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
	</div>
	
	<!-- 增加内容 -->
	<div id="add_content">
		<form id="add_content_form" action="./admin_op/xmsResponse_insertResponse" method="post">
			<table class="htm_edit_table" width="450">
				<tbody>
					<tr>
						<td class="leftTd">模块：</td>
						<td>
							<input class="easyui-combobox"  id="module_id" name="moduleId" onchange="validateSubmitOnce=true;" style="width:204px"
								data-options="valueField:'moduleId',textField:'moduleName',url:'./admin_op/xmsResponse_queryResponseModule'"/>
						</td>
					
					</tr>
					<tr>
						<td class="leftTd">回复内容：</td>
						<td><textarea  name="content" id="content" style="height:150px"></textarea></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addContentSubmit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#add_content').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 增加key -->
	<div id="add_contentKey">
		<form id="add_contentKey_form" action="./admin_op/xmsResponse_batchAddResponseKey" method="post">
			<table class="htm_edit_table" width="450">
				<tbody>
				
					<tr>
						<td class="leftTd">说明：</td>
						<td>
							关键字以回车作为分割符
						</td>
					
					</tr>
					<tr>
						<td class="leftTd">回复内容：</td>
						<td><textarea  name="keyStr" id="contentKey" style="height:150px"></textarea></td>
					</tr>
					<tr>
						<td><input name="responseId" id="response_id" style="display:none"/></td>
						<td><input name="moduleId" id="keyModuleId" style="display:none"/></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addKeyForContentSubmit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#add_contentKey').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 更新内容 -->
	<div id="update_content">
		<form id="update_content_form" action="./admin_op/xmsResponse_updateResponse" method="post">
			<table class="htm_edit_table" width="450">
				<tbody>
					<tr>
						<td class="leftTd">回复内容：</td>
						<td><textarea  name="content" id="updateContent" style="height:150px"></textarea></td>
					</tr>
					<tr>
						<td><input name="responseId" id="updateResponse_id" style="display:none"/></td>
						<td><input id="updateIndex" style="display:none"/></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="updateContentSubmit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#update_content').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>