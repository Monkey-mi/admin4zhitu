<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广告屏蔽关键字管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

	var maxId=0;
	htmTableTitle = "广告屏蔽关键字管理";
	hideIdColumn = false;

	loadDataURL = "./admin_ztworld/adComment_queryADKeywordList";	// 数据装载请求地址
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	};
	
	// 定义工具栏
	toolbarComponent = '#tb';
	
	columnsFields = [
	     			{field:'checkbox',checkbox:'true',align:'center'},
	    			{field:'id',title:'ID',align:'center'},      
	    			{field:'ADKeyword',title:'广告关键词',align:'center'},
	    			{field:'hitCount',title:'命中数',align:'center'}
	    	    ];
	
	onAfterInit = function() {
		$('#htm_addKeyword').window({
			title : '广告关键词添加',
			modal : true,
			width : 650,
			height : 220,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false,
			onClose : function() {
				$('#addKeyword_form').form('reset');
			}
		});
	}
	
	/**
	 * 批量删除广告关键词
	 */
	function delADKeywords() {
		var rows = $("#htm_table").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert('提示',"请选择要删除的广告关键词");
			return;
		}
		var ids = [];
		for (var i=0; i<rows.length; i++) {
			ids.push(rows[i].id);
		}
		$.post("./admin_ztworld/adComment_deleteADKeywords",{ids:ids.toString()}, function(result){
			if(result['result'] == 0) {
				$("#htm_table").datagrid("clearSelections");
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
			}
		});
	};
	
	/**
	 * 提交添加广告关键词表单
	 */
	function submitAddKeywordForm() {
		var $form = $('#addKeyword_form');
		if($form.form('validate')) {
			$('#addKeyword_form .opt_btn').hide();
			$('#addKeyword_form .loading').show();
			$form.form('submit', {
				url: $form.attr('action'),
				success: function(data){
					var result = $.parseJSON(data);
					$('#addKeyword_form .opt_btn').show();
					$('#addKeyword_form .loading').hide();
					if(result['result'] == 0) {
						$('#htm_addKeyword').window('close');  // 关闭添加窗口
						myQueryParams.maxId = 0;
						loadPageData(1);
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
	<div id="tb">
		<a id="addBtn" href="javascript:void(0);" onclick="javascript:$('#htm_addKeyword').window('open');" class="easyui-linkbutton" title="批量新增广告关键词" plain="true" iconCls="icon-cut">添加</a>
		<a id="delBtn" href="javascript:void(0);" onclick="javascript:delADKeywords();" class="easyui-linkbutton" title="批量删除广告关键词" plain="true" iconCls="icon-cut">删除</a>
	</div>
	<table id="htm_table"></table>
	
	<!-- 广告关键词添加  -->
	<div id="htm_addKeyword">
		<form id="addKeyword_form" action="./admin_ztworld/adComment_addADKeywords" method="post">
			<table class="htm_edit_table" width="600">
				<tbody>
					<tr>
						<td style="width:60px">关键词：</td>
						<td>
							<input name="keyword" class="easyui-validatebox" required="true"/>
							<input name="keyword"/>
							<input name="keyword"/>
							<input name="keyword"/>
							<br />
							<input name="keyword"/>
							<input name="keyword"/>
							<input name="keyword"/>
							<input name="keyword"/>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitAddKeywordForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-remove" onclick="javascript:$('#addKeyword_form').form('reset');">清空</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_addKeyword').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">操作中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
</body>
</html>