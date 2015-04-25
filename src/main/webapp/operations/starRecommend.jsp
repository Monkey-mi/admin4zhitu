<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>达人推荐管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer}"></script>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
var loadDateUrl = "./admin_op/starRecommend_queryStarRecommendForTable";
var deleteURI = "./admin_op/starRecommend_batchDelStarRecommend?idStr=";
var maxId = 0,
	init = function() {
		toolbarComponent = '#tb';
		tableQueryParams = {
			'maxId' : maxId
		},
		myLoadDataPage(initPage);
	},
	htmTableTitle = "达人推荐维护", //表格标题
	htmTableWidth = 1150,
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
	},
	columnsFields = [
		{field :'ck',checkbox:true},
		{field : 'id',title : 'id',align : 'center',width : 80},
		userAvatarColumn,
		userIdColumn,
		userNameColumn,
		sexColumn,
		concernCountColumn,
		followCountColumn,
		{field : 'worldCount',title:'织图',align : 'center', width : 60,
			formatter: function(value,row,index){
				var uri = "page_user_userWorldInfo?userId="+row.userId;
				return "<a title='显示织图' class='updateInfo' href='javascript:showUserWorld(\""+uri
					+"\")'>"+value+"</a>"; 
			}
		},
		likedCountColumn,
		keepCountColumn,
		{field : 'signature', title:'签名',align:'center', width:120,editor:'text'},
		userLabelColumn,
		{field : 'top',title : '置顶',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='有效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='无效' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
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
		registerDateColumn			
		],
	htmTablePageList = [50,100,300],
	addWidth = 530; //添加信息宽度
	addHeight = 220; //添加信息高度
	addTitle = "添加用户信息", //添加信息标题,
    onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
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
		
		removePageLoading();
		$("#main").show();
	};
	
	



function myLoadDataPage(pageNum){
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
				columns: [columnsFields],
				onLoadSuccess:myOnLoadSuccess,
				onBeforeRefresh : myOnBeforeRefresh
			
			}	
	);
	var p = $("#htm_table").datagrid("getPager");
	p.pagination({
	});
}

//显示用户织图
function showUserWorld(uri){
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
 * 数据记录
 */
function del() {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(isSelected(rows)){
		$.messager.confirm('删除记录', '您确定要删除已选中的记录?', function(r){ 	
			if(r){				
				var ids = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i]['id']);	
				}	
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				$('#htm_table').datagrid('loading');
				$.post(deleteURI + ids,function(result){
					$('#htm_table').datagrid('loaded');
					if(result['result'] == 0) {
						$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
						$("#htm_table").datagrid("reload");
					} else {
						$.messager.alert('提示',result['msg']);
					}
					return false;
				});	
			//	return false;
			}	
		});		
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
			$(this).val(rows[i]['id']);
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
	

</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSortInit();" class="easyui-linkbutton" title="添加达人推荐置顶计划" plain="true" iconCls="icon-converter" id="reIndexedBtn">添加达人推荐置顶计划</a>
		</div> 
		
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