<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专属主题</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">

var maxId = 0;

	init = function() {
		myQueryParams.valid = 1;
		loadPageData(initPage);
	};
	hideIdColumn = false;
	htmTableTitle = "主题列表"; // 表格标题
	toolbarComponent = '#tb';
	loadDataURL = "./admin_op/v2channel_queryChannelThemeList"; // 数据装载请求地址
	saveChannelThemeURL = "./admin_op/v2channel_insertChannelTheme"; // 保存主题地址
	updateChannelThemeURL = "./admin_op/v2channel_updateChannelTheme"; // 更新主题地址
	deleteChannelThemeURL = "./admin_op/v2channel_deleteChannelTheme"; // 删除主题频道
	
	htmTablePageList = [6,10,20];
	myIdField = 'channelId';
	myPageSize = 6;
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['maxId'] = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['maxId'] = maxId;
			}
		}
	};
	
	// 拥有者ID搜索中用到的参数
	var userMaxId = 0;
	var userQueryParams = {'maxId':userMaxId};
	
	columnsFields = [
	    {field:'ck',checkbox:true},            
		{field : 'id',title : 'id',align : 'center'},
		{field : 'themeName',title : '专题名', align : 'center'},
		{field:'modify',title:'编辑',align:'center',
			formatter:function(value,row,index){
				return '<a title="修改信息" class="updateInfo" href="javascript:void(0);" onclick="javascript:modifyTheme('+ row.id +');">【修改】</a>';
		}	
		}
		],

	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$('#htm_table').datagrid({
			autoRowHeight:true,
			onSelect: function(index,row){
				// 选择操作时刷新展示重新排序所选择的数量
				$("#reSerialCount").text($(this).datagrid('getSelections').length);
			},
			onUnselect: function(index,row){
				// 取消选择操作时刷新展示重新排序所选择的数量
				$("#reSerialCount").text($(this).datagrid('getSelections').length);
			}
		});
		
		$('#htm_serial').window({
			title : '重新排序',
			modal : true,
			width : 650,
			height : 155,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
		});
		
		$('#htm_edit').window({
			title: '添加频道',
			modal : true,
			width : 1000,
			height : 555,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				$("#edit_form .opt_btn").show();
				$("#edit_form .loading").hide();
				$("#channelImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#channelSubImg_edit").attr("src", "./base/images/bg_empty.png");
				$("#channelBannerImg_edit").attr("src", "./base/images/bg_empty.png");
				
				$('#edit_form').form('reset');
				
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		removePageLoading();
		$("#main").show();
	};


/**
 * 频道重排排序
 */
function reSerial() {
	$('#htm_serial .opt_btn').show();
	$('#htm_serial .loading').hide();
	$("#serial_form").find('input[name="reIndexId"]').val('');
	// 获取频道表格中勾选的集合
	var rows = $("#htm_table").datagrid('getSelections');
	$('#serial_form .reindex_column').each(function(i){
		if(i<rows.length)
			$(this).val(rows[i]['themeId']);
	});
	// 打开添加窗口
	$("#htm_serial").window('open');
}

function submitSerialForm() {
	var $form = $('#serial_form');
	if($form.form('validate')) {
		$('#htm_serial .opt_btn').hide();
		$('#htm_serial .loading').show();
		$('#serial_form').form('submit', {
			url: $form.attr('action'),
			success: function(data){
				var result = $.parseJSON(data);
				$('#htm_serial .opt_btn').show();
				$('#htm_serial .loading').hide();
				if(result['result'] == 0) { 
					$('#htm_serial').window('close');  // 关闭添加窗口
					maxId = 0;
					myQueryParams['channel.maxId'] = maxId;
					loadPageData(1);
				} else {
					$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
				}
				
			}
		});
	}
}

function modifyTheme(themeId){
	$('#htm_edit').window('open');
}

</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:addTheme();" class="easyui-linkbutton" title="添加专属主题" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:deleteTheme();" class="easyui-linkbutton" title="删除专属主题" plain="true" iconCls="icon-cut" id="addBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排主题排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序
			<span id="reSerialCount" type="text" style="font-weight:bold;">0</span></a>
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_op/v2channel_insertOpChannel" method="post">
				<table id="htm_edit_table" style="width:400">
					<tbody>
						<tr>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<!-- 频道重新排序 -->
		<div id="htm_serial">
			<form id="serial_form" action="./admin_op/channel_updateChannelSerial" method="post">
				<table class="htm_edit_table" width="580">
					<tbody>
						<tr>
							<td class="leftTd">频道ID：</td>
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
								<input name="reIndexId" class="reindex_column"/>
							</td>
						</tr>
						<tr>
							<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitSerialForm();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-remove" onclick="javascript:$('#serial_form').form('reset');$('#htm_table').datagrid('clearSelections');$('#reSerialCount').text(0);">清空</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_serial').window('close');">取消</a>
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