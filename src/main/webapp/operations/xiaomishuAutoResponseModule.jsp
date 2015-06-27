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
	htmTableTitle = "小秘书模块列表", //表格标题
	recordIdKey='moduleId',
	hideIdColumn = false,
	loadDataURL = "./admin_op/xmsResponse_queryXiaoMiShuResponseModuleForTable", //数据装载请求地址
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
		{field : 'moduleId',title : '模块ID',align : 'center',width : 75},
		{field : 'moduleName',title: '模块',align : 'center',width : 80},
		{field : 'moduleValid',title : '有效性',width : 70,
			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
		},
		{field : 'operatorName',title: '最后修改者',align : 'center',width : 80},
		{field : 'modifyDate', title:'最后修改时间日期', align : 'center',width : 150, 
			formatter: function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		}
		
	],
	onAfterInit = function() {
		//$("#add_module_form").show();
		$("#add_module").window({
			modal : true,
			width : 450,
			top : 10,
			height : 160,
			title : '新增小秘书回复模块',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$("#module_name").val("");
			}
		});
		
	
	};
	
	function addModule(){
		$("#add_module").window('open');
	}
	
	function addModuleSubmit(){
		var form = $("#add_module_form");
		$('#add_module .opt_btn').hide();
		$('#add_module .loading').show();
		$.post(form.attr("action"),form.serialize(),function(result){
			$('#add_module .opt_btn').show();
			$('#add_module .loading').hide();
			if(result['result'] == 0) {
				$('#add_module').window('close');  //关闭添加窗口
				$("#htm_table").datagrid('reload');
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
		<a href="javascript:void(0);" onclick="javascript:addModule();" class="easyui-linkbutton" title="增加小秘书模块" plain="true" iconCls="icon-add" id="delBtn">新增</a>
		<a href="javascript:void(0);" onclick="javascript:delModule();" class="easyui-linkbutton" title="删除小秘书模块" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
	</div>
	
	<div id="add_module">
		<form id="add_module_form" action="./admin_op/xmsResponse_insertResponseModule" method="post">
			<table class="htm_edit_table" width="400">
				<tbody>
					<tr>
						<td class="leftTd">模块名：</td>
						<td><input type="text" name="moduleName" id="module_name" /></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addModuleSubmit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#add_module').window('close');">取消</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
</body>
</html>