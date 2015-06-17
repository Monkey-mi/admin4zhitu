<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String channelId = request.getParameter("channelId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签维护</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

	var maxId = 0;
	init = function() {
		// 以下两个属性由于在onBeforeInit方法中设置不生效，所以设置在这里
		htmTableHeight = undefined;	// 取消高度设置，然后在datagrid中设置自动高度
		htmTableWidth = $(document.body).width();	// 表格宽度设置为当前页面宽度
		
		loadPageData(initPage);
	};
	htmTableTitle = "标签编辑"; // 表格标题
	myIdField = 'id';
	hideIdColumn = false;
	
	myQueryParams.channelId = <%= channelId %>;
	loadDataURL = "./admin_op/v2channel_queryOpChannelLabelList";	// 数据装载请求地址
	
	toolbarComponent = [{
    	id: 'add_btn',
		text: '添加',
		iconCls: 'icon-add',
		handler: function(){
			$('#channel_label_add_window').window('open');  // 打开添加窗口
		}
    },{
    	id: 'delete_btn',
		text: '删除',
		iconCls: 'icon-cut',
		handler: function(){
			// 从表格中移去勾选的行
			var rowsArray = $('#htm_table').datagrid("getSelections");

			for (var i=0;i<rowsArray.length;i++) {
				var rowIndex = $('#htm_table').datagrid("getRowIndex",rowsArray[i]);
				$('#htm_table').datagrid("deleteRow",rowIndex);
			}
			
			// 获取表格中剩下的行，进行保存操作
			var retRows = $('#htm_table').datagrid("getRows");
			var labelnames="";
			var labelIds="";
			for (var i=0;i<retRows.length;i++) {
				if (i==0) {
					labelIds += retRows[i].id;
					labelnames += retRows[i].label_name;
				} else {
					labelIds += "," + retRows[i].id;
					labelnames += "," + retRows[i].label_name;
				}
			}
			updateChannelLabel(labelIds,labelnames);
		}
    }];
	
	columnsFields = [
	                  {field : 'ck',checkbox:true},
	                  {field:'id',title:'标签ID',align:'center',width:100,sortable:false},
	                  {field:'label_name',title:'标签名称',align:'center',width:200,sortable:false}
	        ];
	
	onAfterInit = function() {
		$('#htm_table').datagrid({
			fitColumns:true,
			autoRowHeight:true
		});
		
		$('#channel_label_add_window').window({
	    	onOpen : function(){
	    		// 设置form中channelLabel为空，因为标签选中后都是以<a>存在的，要清楚子dom
	    		$("#channelLabel a").each(function(){
	    			$(this).remove();
	    		});
	    	}
		});
		
		$('#channelLabelSearch').combobox({
			valueField:'id',
			textField:'label_name',
			onChange:function(rec){
				var url="./admin_op/v2channel_queryOpChannelLabel?channelLabelNames="+$('#channelLabelSearch').combobox('getValue');
				$('#channelLabelSearch').combobox('reload',url);
			},
			onSelect:function(rec){
				var channelLabelId = rec.id;
				var channelLabelName = rec.label_name;
				if(rec.id == -1){
					channelLabelName = channelLabelName.substring(channelLabelName.indexOf(":")+1);
					$.messager.confirm('更新记录',"是否创建标签："+channelLabelName,function(r){
						if(r){
							$.post("./admin_ztworld/label_saveLabel",{
								'labelName':channelLabelName
							},function(result){
								if(result['result'] == 0){
									channelLabelId = result['labelId'];
									var labelSpan = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;overflow:hidden;' labelId='"
											+channelLabelId+"' labelName='"+channelLabelName+"'>"+channelLabelName+"</a>").click(function(){
										$(this).remove();
									});
									$("#channelLabel").append(labelSpan);
									$('#channelLabelSearch').combobox('clear');
								}else{
									$.messager.alert('提示',result['msg']);
								}
							},"json");
						}
					});
				}else{
					var labelSpan = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn' style='vertical-align:middle;height:24px;width:52px;overflow:hidden;' labelId='"
							+channelLabelId+"' labelName='"+channelLabelName+"'>"+channelLabelName+"</a>").click(function(){
						$(this).remove();
					});
					$("#channelLabel").append(labelSpan);
					$('#channelLabelSearch').combobox('clear');
				}
			}
		});
		
	};
	
	/**
	 * 更新频道标签
	 */
	function updateChannelLabel(labelIds,labelnames){
		$.post("./admin_op/v2channel_updateOpChannelLabel",{
			'channelId':<%= channelId %>,
			'channelLabelNames':labelnames,
			'channelLabelIds':labelIds
			},function(result){
				if(result['result'] == 0){
					$("#htm_table").datagrid('reload');
				}else{
					$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
				}
				
		},"json");
	};
	
	/**
	 * 添加标签
	 */
	function submitAddChannelLabelForm(){
		var labelnames="";
		var labelIds="";
		$("#channelLabel a").each(function(i){
			if (i==0) {
				labelIds += $(this).attr("labelId");
				labelnames += $(this).attr("labelName");
			} else {
				labelIds += "," + $(this).attr("labelId");
				labelnames += "," + $(this).attr("labelName");
			}
		});
		
		// 表格中的标签要一起保存
		var retRows = $('#htm_table').datagrid("getRows");
		if (retRows) {
			for (var i=0;i<retRows.length;i++) {
				labelIds += "," + retRows[i].id;
				labelnames += "," + retRows[i].label_name;
			}
		}
		
		updateChannelLabel(labelIds,labelnames);
		$('#channel_label_add_window').window('close');
	};
	
</script>
</head>

<body>
	<table id="htm_table"></table>
	
	<!-- 相关频道添加 -->
	<div id="channel_label_add_window" class="easyui-window" title="标签编辑页-添加" style="width:500px;height:300px"
        data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,maximizable:false,collapsible:false">
		<form id="channel_label_add_form" action="./admin_op/v2channel_addRelatedChannel" method="post">
			<table style="padding:20px;">
				<tr>
					<td>标签搜索：</td>
					<td>
						<input id="channelLabelSearch" style="width:206px;">
					</td>
				</tr>
				<tr>
					<td>标签集合：</td>
					<td>
						<div id="channelLabel" style="width:206px;height:60px;background-color: #b8b8b8;"></div>
					</td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="2" style="text-align:center;padding-top:10px;">
						<a class="easyui-linkbutton" iconCls="icon-add" onclick="submitAddChannelLabelForm()">确定</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
</body>
</html>