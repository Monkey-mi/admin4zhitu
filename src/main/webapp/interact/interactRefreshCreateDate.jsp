<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String autoResponseId= request.getParameter("autoResponseId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>刷新马甲织图信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript">
	var loadDateUrl="",
	updateUrl="./admin_interact/refreshZombieHtworld_updateZombieHtWorld?uidsStr=",
	autoUpdateUrl="./admin_interact/refreshZombieHtworld_autoUpdateZombieHtWorld?uidsStr=",
	tableQueryParams = {},
	recordIdKey = "worldId",
	tableInit = function() {
		tableLoadDate(1);
	};
	
	function tableLoadDate(pageNum){
		$("#htm_refresh_table").datagrid(
				{
					title  :"刷新马甲织图列表",
					width  :790,
					pageList : [30,50,100,150],
					loadMsg:"加载中....",
					url	   :"./admin_interact/refreshZombieHtworld_queryZombieHtWorld",
					queryParams : tableQueryParams,
					pagination: true,
					pageNumber: pageNum,
					toolbar:'#tb',
					columns: [[
						{field :'ck',checkbox:true},
						{field :recordIdKey,title:'织图ID',align:'center',width:90},
						{field : 'userId',title: '用户id',align : 'center',width : 150},
						{field : 'shortLink', title:'短链',align : 'center',width : 320,
							styler: function(value,row,index){ 
								return 'cursor:pointer;';
							},
							formatter : function(value, row, rowIndex ) {
								return "<a title='播放织图' class='updateInfo' href='javascript:showWorld(\""
										+ value + "\")'>"+value+"</a>";
							}
						},
						{field : 'createDate', title:'分享时间',align : 'center' ,width : 150,
							formatter:function(value,row,index){
								return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
							}
						}
						
					]]
				
				}	
		);
		var p = $("#htm_refresh_table").datagrid("getPager");
		p.pagination({
		});
	}
	
	$(function(){
		tableInit();
	});
	//根据输入的天数，查询n天前至今的记录
	function queryZombieHtworldByDay(){
		var days=$("#daysInput").searchbox('getValue');
		myQueryParams = {
				'days' : days
			};
		$("#htm_refresh_table").datagrid("load",myQueryParams);
	}
	
	function autoRefreshZombieHtworld(){
		update(autoUpdateUrl);
	}
	
	function update(url){
		var rows = $('#htm_refresh_table').datagrid('getSelections');	
		var minDaySpan = $('#minDaySpan').numberbox('getValue');
		var maxDaySpan = $('#maxDaySpan').numberbox('getValue');
		var days       = $('#days').numberbox('getValue');
		if(isSelected(rows)){
			var ids = [];
			for(var i=0;i<rows.length;i+=1){		
				ids.push(rows[i]['userId']);	
				rowIndex = $('#htm_refresh_table').datagrid('getRowIndex',rows[i]);				
			}	
			$('#htm_refresh_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
			$('#htm_refresh_table').datagrid('loading');
			$.post(url+ids,{
				'minDaySpan' : minDaySpan,
				'maxDaySpan' : maxDaySpan,
				'days'		 : days
			},function(result){
				$('#htm_refresh_table').datagrid('loaded');
				if(result['result'] == 0) {
					$.messager.alert('提示',result['msg']);
					$("#htm_refresh_table").datagrid("reload");
				} else {
					$.messager.alert('提示',result['msg']);
				}
				
			});				
		}	
	}
	
	function queryZombieHtworldByDay(){
		var days = $("#daysInput").searchbox("getValue");
		tableQueryParams.days=days;
		$('#htm_refresh_table').datagrid('loading');
		$('#htm_refresh_table').datagrid('load',tableQueryParams);
		$('#htm_refresh_table').datagrid('loaded');
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
	
	function showWorld(uri) {
		$.fancybox({
			'margin'			: 20,
			'width'				: '10',
			'height'			: '100%',
			'autoScale'			: true,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'href'				: uri + "?adminKey=zhangjiaxin"
		});
	}
</script>
</head>
<body>
	<div id="main">
		<div id="tb">
			<span>最小时间间隔(单位：天)：</span>
			<input id="minDaySpan" type="text" class="easyui-numberbox" value="7" data-options="min:0,max:30" style="width:50px;"/>
			<span>最大时间间隔(单位：天)：</span>
			<input id="maxDaySpan" type="text" class="easyui-numberbox" value="30" data-options="min:10,max:100" style="width:50px;">
			<span>更新到前n天：</span>
			<input id="days" type="text" class="easyui-numberbox" value="2" data-options="min:0,max:100" style="width:50px;">
			<a href="javascript:void(0);" onclick="autoRefreshZombieHtworld();" class="easyui-linkbutton" title="更新" plain="true" iconCls="icon-add" id="addBtn">更新</a>
			<input id="daysInput" searcher="queryZombieHtworldByDay" class="easyui-searchbox" prompt="输入天数，查询前n天记录" style="width:150px;float:right;"/>
		</div>
		<table id="htm_refresh_table"></table>
	</div>
</body>
</html>