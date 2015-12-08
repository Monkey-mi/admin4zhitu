<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>地区信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include> 
<script type="text/javascript">
	var maxId = 0,
	init = function() {
		toolbarComponent = '#tb';
		loadPageData(initPage);
	},
	hideIdColumn = false,
	htmTableTitle = "地区列表", //表格标题
	loadDataURL = "./admin_op/near_queryCityGroupList",
	deleteURI = "./admin_op/near_batchDeleteCityGroup?idsStr=",
	mySortName = '',
	columnsFields = [
             {field:'ck',checkbox:true},
             {field: "id",title: "ID",align: "center"},
             {field: "description",title: "组名",align: "center"}
	    ];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "城市分组列表",
			width: $(document.body).width(),
			url: "./admin_op/near_queryCityGroupList",
			toolbar: "#tb",
			idField: "id",
			rownumbers: true,
			columns: [columnsFields],
			fitColumns: true,
			autoRowHeight: true,
			checkOnSelect: false,
			selectOnCheck: true,
			onClickCell: function(rowIndex, field, value) {
				IsCheckFlag = false;
			},
			onSelect: function(rowIndex, rowData) {
				// 选择操作时刷新展示重新排序所选择的数量
				$("#reSerialCount").text($(this).datagrid('getSelections').length);
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("unselectRow", rowIndex);
				}
			},
			onUnselect: function(rowIndex, rowData) {
				$("#reSerialCount").text($(this).datagrid('getSelections').length);
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("selectRow", rowIndex);
				}
				// 选择操作时刷新展示重新排序所选择的数量
			},
			onLoadSuccess: function(data) {
				// 数据加载成功，loading动画隐藏
				$("#page-loading").hide();
			}
		});
		
		
		$('#add_cityGroup_window').window({
			title : '添加地区',
			modal : true,
			width : 520,
			height : 155,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
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
		
		// 展示界面
		$("#main").show();
	});
	
	function addCityGroup() {
		loadAddCityGroupFormValidate();
		$('#add_cityGroup_window').window('open');
	}
	
	/**
	 * 新增城市分组
	 * @author zhangbo 2015-12-04
	 */
	function loadAddCityGroupFormValidate() {
		var $form = $('#add_cityGroup_form');
		formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
		$.formValidator.initConfig({
			formid : $form.attr("id"),			
			onsuccess : function() {
				if(formSubmitOnce==true){
					//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
					formSubmitOnce = false;
					$("#add_cityGroup_form .opt_btn").hide();
					$("#add_cityGroup_form .loading").show();
					//验证成功后以异步方式提交表单
					$.post($form.attr("action"), $form.serialize(),
						function(result){
							formSubmitOnce = true;
							$("#add_cityGroup_form .opt_btn").show();
							$("#add_cityGroup_form .loading").hide();
							if(result['result'] == 0) {
								// 关闭添加窗口，刷新页面 
								$('#add_cityGroup_window').window('close');
								$("#htm_table").datagrid("load");
							} else {
								$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
							}
						},"json");				
					return false;
				}
			}
		});
		
		$("#desctiption_edit")
		.formValidator({empty:true, onshow:"请输入分组名（必填）",onfocus:"请输入分组名",oncorrect:"正确！"});
	}
	
	/**
	 * 重新排序
	 */
	function reSerial() {
		$('#htm_serial .opt_btn').show();
		$('#htm_serial .loading').hide();
		$("#serial_form").find('input[name="reIndexId"]').val('');
		// 获取频道表格中勾选的集合
		var rows = $("#htm_table").datagrid('getSelections');
		$('#serial_form .reindex_column').each(function(i){
			if(i<rows.length)
				$(this).val(rows[i]['id']);
		});
		// 打开添加窗口
		$("#htm_serial").window('open');
	}
	
	/**
	 * 提交重新排序
	 */
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
						 /* myQueryParams['channel.maxId'] = maxId;  */
						loadPageData(1);
						$('#htm_table').datagrid("unselectAll");
						$("#reSerialCount").text(0);
					} else {
						$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
					}
					
				}
			});
		}
	}
	
</script>
</head>
<body>
	
	<div id="main" style="display: none;">
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<a href="javascript:void(0);" onclick="javascript:addCityGroup();" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="htmDelete('id')" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序
			<span id="reSerialCount" type="text" style="font-weight:bold;">0</span></a>
		</div>
		
		<!-- 添加城市分组窗口 -->
		<div id="add_cityGroup_window">
			<form id="add_cityGroup_form" action="./admin_op/near_addCityGroup" method="post">
				<table class="htm_edit_table" width="480">
					<tr>
						<td class="leftTd">地区名：</td>
						<td>
							<textarea id="desctiption_edit" name="description" onchange="validateSubmitOnce=true;"></textarea>
						</td>
						<td class="rightTd">
							<div id="desctiption_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#add_cityGroup_form').submit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#add_cityGroup_window').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading" style="display:none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">添加中...</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<!-- 频道重新排序 -->
		<div id="htm_serial">
			<form id="serial_form" action="admin_op/near_updateNearCityGroupSerial" method="post">
				<table class="htm_edit_table" width="580">
					<tbody>
						<tr>
							<td class="leftTd">分组ID：</td>
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
</html>