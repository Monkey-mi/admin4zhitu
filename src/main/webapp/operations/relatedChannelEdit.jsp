<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String channelId = request.getParameter("channelId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道维护</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

	var channelId = <%= channelId %>;
	var maxId = 0;
	init = function() {
		// 以下两个属性由于在onBeforeInit方法中设置不生效，所以设置在这里
		htmTableHeight = undefined;	// 取消高度设置，然后在datagrid中设置自动高度
		htmTableWidth = $(document.body).width();	// 表格宽度设置为当前页面宽度
		
		loadPageData(initPage);
	};
	htmTableTitle = "关联频道编辑"; // 表格标题
	myIdField = 'id';
	hideIdColumn = false;
	
	myQueryParams.channelId = <%= channelId %>;
	loadDataURL = "./admin_op/v2channel_queryRelatedChannel";	// 数据装载请求地址
	
	var queryChannelByIdOrNameURL = "./admin_op/v2channel_queryOpChannelByIdOrName";// 根据id和名称查询频道
	var searchChannelMaxId = 0;
	var searchChannelQueryParams = {'maxId':searchChannelMaxId};
	
	toolbarComponent = [{
    	id: 'add_btn',
		text: '添加',
		iconCls: 'icon-add',
		handler: function(){
			$('#ss-channel').combogrid('clear');	// 清空添加频道combogrid
			$('#related_channel_add_window').window('open');  // 打开添加窗口
		}
    },{
    	id: 'delete_btn',
		text: '删除',
		iconCls: 'icon-cut',
		handler: function(){
			var rows = $('#htm_table').datagrid("getSelections"); 
			var ids = [];
			for (var i=0;i<rows.length;i++) {
				ids.push(rows[i].id);
			}
			if (ids.length == 0) {
				$.messager.alert('提示',"请勾选要删除的关联频道");
			} else {
				var params = {
						channelId:$("#related_channel_add_form input[name=channelId]").val(),
						deleteIds:ids.toString()
				};
				$.post("./admin_op/v2channel_deleteRelatedChannels",params,function(result){
					$.messager.alert('提示',result.msg);
					$('#htm_table').datagrid('reload');
				});
			}
		}
    },{
    	id: 'reIndex_btn',
		text: '排序',
		iconCls: 'icon-converter',
		handler: function(){
			$('#related_channel_serial_window .opt_btn').show();
			$('#related_channel_serial_window .loading').hide();
			$("#related_channel_serial_form").find('input[name="reIndexlinkId"]').val('');
			
			// 获取关联频道表格中勾选的集合
			var rows = $("#htm_table").datagrid('getSelections');
			$('#related_channel_serial_form .reindex_column').each(function(i){
				if(i<rows.length)
					$(this).val(rows[i]['id']);
			});
			// 打开添加窗口
			$("#related_channel_serial_window").window('open');
		}
    }];
	
	columnsFields = [
	                  {field : 'ck',checkbox:true},
	                  {field:'id',title:'关联频道ID',align:'center',width:100,sortable:false},
	                  {field:'channelName',title:'关联频道',align:'center',width:200,sortable:false}
	        ];
	
	onAfterInit = function() {
		$('#htm_table').datagrid({
			fitColumns:true,
			autoRowHeight:true
		});
		
		$('#related_channel_add_window').window({
	    	onOpen : function(){
	    		// 把form中的linkChannelId置空
	    		$("#related_channel_add_form input[name=linkChannelId]").val("");
	    	},
			onClose : function(){
				// 把form中的linkChannelId置空
				$("#related_channel_add_form input[name=linkChannelId]").val("");
			}
		});
		
		$('#ss-channel').combogrid({
			panelWidth : 440,
		    panelHeight : 330,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#search-channel-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'channelName',
		    url : './admin_op/channel_searchChannel',
		    pagination : true,
		    columns:[[
				{field : 'id',title : 'id',align : 'center',width : 80},
				{field : 'channelIcon',title : 'icon', align : 'center',width : 60, height:60,
					formatter:function(value,row,index) {
						return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				},
				{field : 'channelName',title : '频道名称',align : 'center',width : 280}
		    ]],
		    queryParams:searchChannelQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > searchChannelMaxId) {
						searchChannelMaxId = data.maxId;
						searchChannelQueryParams.maxId = searchChannelMaxId;
					}
				}
		    }
		    
		});
		var p = $('#ss-channel').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					searchChannelMaxId = 0;
					searchChannelQueryParams.maxId = searchChannelMaxId;
				}
			}
		});
		
	};
	
	/**
	 * 添加关联频道
	 */
	function submitAddRelatedChannelForm(){
		// 是否已经是关联频道标记位
		var isRelatedChannel = false;
		var rows = $('#htm_table').datagrid("getRows");
		for(var i=0;i<rows.length;i++){
			if ($('#ss-channel').combogrid('getValue') == rows[i].id) {
				isRelatedChannel = true;
				break;
			}
		}
		
		if ($("#related_channel_add_form input[name=linkChannelId]").val() == "") {
			$.messager.alert('提示',"请填写要关联的频道id");
		} else if (isRelatedChannel) {
			$.messager.alert('提示',"要添加的频道已经关联了，请选择未关联的频道！");
		} else {
			$('#related_channel_add_form').form('submit', {
				url: $(this).attr('action'),
				success: function(data){
					var result = $.parseJSON(data);
					if(result.result == 0) {
						$('#related_channel_add_window').window('close');  // 关闭添加窗口
						$('#htm_table').datagrid("reload");
					} else {
						$.messager.alert('提示',result.msg);  // 提示添加信息失败
					}
				}
			});
		}
	};
	
	/**
	 * 关联频道重新排序
	 */
	function submitRelatedChannelSerialForm(){
		if ($("#related_channel_serial_form input[name=reIndexlinkId]").val() == "") {
			$.messager.alert('提示',"请填写要关联的频道id");
		} else {
			$('#related_channel_serial_form').form('submit', {
				url: $(this).attr('action'),
				success: function(data){
					var result = $.parseJSON(data);
					if(result.result == 0) {
						$.messager.alert('提示',"关联频道重新排序成功！");
						$('#related_channel_serial_window').window('close');  // 关闭添加窗口
					} else {
						$.messager.alert('提示',result.msg);  // 提示添加信息失败
					}
				}
			});
		}
	};
	
	/**
	 * 搜索频道名称
	 */
	function searchChannel() {
		searchChannelMaxId = 0;
		maxId = 0;
		var query = $('#channel-searchbox').searchbox('getValue');
		searchChannelQueryParams.maxId = searchChannelMaxId;
		searchChannelQueryParams.query = query;
		$("#ss-channel").combogrid('grid').datagrid("load",searchChannelQueryParams);
	};
	
</script>
</head>

<body>
	<table id="htm_table"></table>
	
	<!-- 相关频道添加 -->
	<div id="related_channel_add_window" class="easyui-window" title="相关频道编辑页-添加" style="width:300px;height:150px"
        data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,maximizable:false,collapsible:false">
		<form id="related_channel_add_form" action="./admin_op/v2channel_addRelatedChannel" method="post">
			<table style="padding:20px;">
				<tr>
					<td>频道ID：</td>
					<td><input id="ss-channel" name="linkChannelId"/></td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="2" style="text-align:center;padding-top:10px;">
						<a class="easyui-linkbutton" iconCls="icon-add" onclick="submitAddRelatedChannelForm()">确定</a>
					</td>
				</tr>
				<tr><td><input name="channelId" class="none" value="<%= channelId %>"/></td></tr>
			</table>
		</form>
	</div>
	
	<!-- 相关频道添加：搜索频道搜索框 -->
	<div id="search-channel-tb" style="padding:5px;height:auto" class="none">
		<input id="channel-searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
	</div>
	
	<!-- 相关频道重新排序 -->
	<div id="related_channel_serial_window" class="easyui-window" title="相关频道编辑页-重新排序" style="width:500px;height:350px"
        data-options="iconCls:'icon-converter',closed:true,modal:true,minimizable:false,maximizable:false,collapsible:false">
		<form id="related_channel_serial_form" action="./admin_op/v2channel_updateRelatedChannelSerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">关联频道ID：</td>
						<td>
							<input name="reIndexlinkId" class="easyui-validatebox reindex_column" required="true"/>
							<input name="reIndexlinkId" class="reindex_column"/>
							<input name="reIndexlinkId" class="reindex_column"/>
							<input name="reIndexlinkId" class="reindex_column"/>
							<input name="reIndexlinkId" class="reindex_column"/>
							<input name="reIndexlinkId" class="reindex_column"/>
							<br />
							<input name="reIndexlinkId" class="reindex_column"/>
							<input name="reIndexlinkId" class="reindex_column"/>
							<input name="reIndexlinkId" class="reindex_column"/>
							<input name="reIndexlinkId" class="reindex_column"/>
							<input name="reIndexlinkId" class="reindex_column"/>
							<input name="reIndexlinkId" class="reindex_column"/>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitRelatedChannelSerialForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#related_channel_serial_window').window('close');">取消</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#related_channel_serial_form').form('reset');$('#htm_table').datagrid('clearSelections')">清空</a>
						</td>
					</tr>
					<tr><td><input name="reIndexChannelId" class="none" value="<%= channelId %>"/></td></tr>
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
	
</body>
</html>