<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道织图管理-列表模式</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath}/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}" />
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript">
	// 定义主表格查询最大id
	var maxId = 0;
	// 定义主表格加载数据参数集合 
	var myQueryParams = {};
	myQueryParams["world.channelId"] = baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID");	// 默认为缓存中
	
	// 频道搜索表格查询最大id
	var searchChannelQueryParams = {};
	
	//mishengliang
	var columnsFields = [
		{field: "ck",checkbox: true },
		{field: "channelWorldId",title : "序号",align: "center",width: 60},
		{field: "worldId",title: "织图ID",align: "center", 
			formatter : function(value, row, index) {
				var url = row.worldURL;
				if(row.worldURL == '' || row.worldURL == undefined) {
					var slink;
					if(row['shortLink'] == '')
						slink = row[worldKey];
					else 
						slink = row['shortLink'];
					url = "http://www.imzhitu.com/DT" + slink;
				}
				
				return "<a title='打开互动页面' class='updateInfo' href='javascript:commonTools.openWorldInteractForChannelWorldPage("+row.worldId+",\""+url+"\","+row.channelWorldValid+")'>"+row.worldId+"</a>";
			},
			styler:function(value,row,index){
				if(row.typeInteracted == 1){
					return 'background-color:#fdf9bb;';
				}
			}
		},
		{field: "phoneCode",title: "客户端",align: "center",
			formatter: function(value,row,index){
				var phone = "IOS";
				if(value == 1) {
					phone = "安卓";
				}
				return "<span class='updateInfo' title='版本号:"+row.appVer+" || 系统:"+row.phoneSys+" v"+row.phoneVer+"'>" + phone + "</span>";
			}
		},
		{field: "authorAvatar",title: "头像",align: "left", 
			formatter: function(value, row, index) {
				var	content = "<img width='30px' height='30px' class='htm_column_img' src='" + baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg') + "'/>";
				if(row.star >= 1) {
					content = content + "<img title='" + row.verifyName + "' class='avatar_tag' src='" + row.verifyIcon+ "'/>";
				}
				return "<a class='updateInfo' href='javascript:commonTools.openUserInfoPage(" + row.authorId + ")'>"+"<span>" + content + "</span>"+"</a>";	
			}
		},
		{field: "authorId", title: "作者id",align: "center"},
		{field: "authorName",title: "作者",align: "center"},
		{field: "clickCount",title: "播放数",align: "center", editor: "text"},
		{field: "likeCount",title: "喜欢数",align: "center",
			formatter : function(value, row, rowIndex) {
				return "<a title='显示喜欢用户' class='updateInfo' href='javascript:commonTools.openWorldLikedPage(" + row.worldId + ")'>"+value+"</a>";
			}
		},
		{field: "commentCount",title: "评论数",align: "center",
			formatter : function(value, row, rowIndex ) {
				return "<a title='显示评论' class='updateInfo' href='javascript:commonTools.openWorldCommentsPage(" + row.worldId + ")'>"+value+"</a>";
			}
		},
		{field : 'titleThumbPath',title : '预览',align : 'center',
			formatter: function(value,row,index){
				var imgSrc = baseTools.imgPathFilter(value,'../base/images/bg_empty.png');
				return "<a style='cursor: hand;cursor: pointer;' onclick='javascript:commonTools.showWorld(\""+row.shortLink+"\")'> <img width='60px' height='60px' class='htm_column_img' src='" + imgSrc + "' /></a>";
			}
		},
		{field : 'worldLabel',title : '标签',align : 'center', width : 100},
		{field : 'notified',title : '通知状态',align : 'center', width : 60,
			formatter: function(value,row,index) {
  				if(value >= 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已经通知' class='htm_column_img' src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
		},
		{field : 'channelWorldValid',title : '有效性',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
  		{field : 'superb',title : '加精',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				switch(value) {
  				case 1:
  					img = "./common/images/ok.png";
  					break;
  				default:
  					img = "./common/images/tip.png";
  					break;
  				}
  				return "<img class='htm_column_img pointer' src='" + img + "'/>";
  			}
  		},
  		{field : 'validSchedula',title : '有效性计划',align : 'center', width: 50,
  			formatter: function(value,row,index) {
  				switch(value) {
  				case 0:
  					return "<img title='已加入有效性计划，未完成' class='htm_column_img'  src='./common/images/tip.png'/>";
  					break;
  				case 1:
  					return "<img title='有效性计划已经完成' class='htm_column_img'  src='./common/images/ok.png'/>";
  					break;
  				default:
  					return "";
  					break;
  				}
  			}  			
  		},
  		{field : 'superbSchedula',title : '加精计划',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				switch(value) {
  				case 0:
  					return "<img title='已加入加精计划，未完成' class='htm_column_img'  src='./common/images/tip.png'/>";
  					break;
  				case 1:
  					return "<img title='加精计划已经完成' class='htm_column_img'  src='./common/images/ok.png'/>";
  					break;
  				default:
  					return "";
  					break;
  				}
  			}  			
  		},
  		{field : 'multiple',title : '属于多个频道',align : 'center', width : 160,
  			formatter: function(value,row,index) {
  				return "<a class='viewInfo' onclick='javascript:commonTools.openWorldAddToChannelPage(" + row.worldId + ")'>" + value + "</a>";
  			}
  		},
  		{field : 'dateModified', title:'分享日期', align : 'center', 
  			formatter: function(value,row,index){
  				return baseTools.parseDate(value).format("yy/MM/dd hh:mm");
  			}
  		},
	];
		
	var IsCheckFlag = true; //标示是否是勾选复选框选中行的，true - 是 , false - 否
	
	$(function(){
		// loading动画展示
		$("#page-loading").show();
		
		// 主表格
		$("#htm_table").datagrid({
			title: "频道织图列表",
			width: $(document.body).width(),
			url: "./admin_op/channel_queryChannelWorld",
			queryParams: myQueryParams,
			toolbar: "#tb",
			sortName: "id",
			idField: "channelWorldId",
			sortName: "channelWorldId",
			rownumbers: true,
			columns: [columnsFields],
			fitColumns: true,
			autoRowHeight: true,
			checkOnSelect: false,
			selectOnCheck: true,
			loadMsg: "处理中,请等待...",
			pagination: true,
			pageNumber: 1, //指定当前页面为1
			pageSize: 10,
			pageList: [10,30,50,100],
			onClickCell: function(rowIndex, field, value) {
				IsCheckFlag = false;
			},
			onSelect: function(rowIndex, rowData) {
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("unselectRow", rowIndex);
				}
			},
			onUnselect: function(rowIndex, rowData) {
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("selectRow", rowIndex);
				}
			},
			onLoadSuccess: function(data) {
				if(data.result == 0) {
					if(data.maxId > maxId) {
						maxId = data.maxId;
						myQueryParams["world.maxId"] = maxId;
					}
				}
				// 数据加载成功，loading动画隐藏
				$("#page-loading").hide();
				// 加载成功则清除所有选择与勾选
				$(this).datagrid("clearSelections");
				$(this).datagrid("clearChecked");
			}
		});
		// 主表格分页对象
		var channelWorldTable_p = $("#htm_table").datagrid("getPager");
		channelWorldTable_p.pagination({
			beforePageText : "页码",
			afterPageText : "共 {pages} 页",
			displayMsg: "第 {from} 到 {to} 共 {total} 条记录",
			onBeforeRefresh: function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					maxId = 0;
					myQueryParams["world.maxId"] = maxId;
				}
			}
		});
		
		$("#htm_indexed").window({
			title : "按照时间计划重新排序，并生效",
			modal : true,
			width : 660,
			height : 235,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : "icon-converter",
			resizable : false
		});
		
		$("#batch_to_superb_win").window({
			title : "按照时间计划加精",
			modal : true,
			width : 660,
			height : 235,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : "icon-converter",
			resizable : false,
			onClose : function() {
				// 关闭时重置批量计划加精的form，因为时间与form中隐藏的ids都要重新赋值
				$("#batch_to_superb_form").form("reset");
			}
		});
		
		$("#ss_channel").combogrid({
			panelWidth : 440,
		    panelHeight : 330,
		    loadMsg : "加载中，请稍后...",
			pageList : [4,10,20],
			pageSize : 4,
			toolbar: "#search_channel_tb",
		    multiple : false,
		    required : false,
		   	idField : "id",
		    textField : "channelName",
		    url : "./admin_op/channel_searchChannel",
		    pagination : true,
		    columns:[[
				{field: "id",title: "id",align: "center",width: 80},
				{field: "channelIcon",title: "icon", align: "center",width: 60, height: 60,
					formatter: function(value,row,index) {
						return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				},
				{field: "channelName",title: "频道名称",align: "center",width: 280}
		    ]],
		    queryParams:searchChannelQueryParams,
		    onSelect:function(index, row) {
				$("#htm_opt_btn").show();
				$("#htm_table").datagrid("clearSelections"); //清除所有已选择的记录，避免重复提交id值
				
				// 设置缓存
				baseTools.setCookie("CHANNEL_WORLD_CHANNEL_ID", row.id, 10*24*60*60*1000);
		    	baseTools.setCookie("CHANNEL_WORLD_CHANNEL_NAME", row.channelName, 10*24*60*60*1000);
		    	
				maxId = 0;
				myQueryParams["world.maxId"] = maxId;
				myQueryParams["world.channelId"] = row.id;
				myQueryParams["world.worldId"] = "";
				myQueryParams["world.notified"] = "";
				$("#htm_table").datagrid("load",myQueryParams);
			},
		    onLoadSuccess:function(data) {
		    	$('#ss_channel').combogrid("setValue", baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID"));
		    	$('#ss_channel').combogrid("grid").datagrid("clearSelections");
		    }
		    
		});
		var ss_channel_p = $("#ss_channel").combogrid("grid").datagrid("getPager");
		ss_channel_p.pagination({
			onBeforeRefresh: function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					searchChannelQueryParams["maxId"] = 0;
				}
			}
		});
		
		$("#htm_batch_channel").window({
			title: "批量频道织图添加",
			modal: true,
			width: 450,
			height: 150,
			shadow: false,
			closed: true,
			minimizable: false,
			maximizable: false,
			collapsible: false,
			iconCls: "icon-add",
			resizable: false,
			onClose: function(){
				$("#ss_batch_channel").combobox("clear");
			}
		});
		
		$("#ss_batch_channel").combogrid({
			panelWidth: 440,
			panelHeight: 330,
			loadMsg: "加载中，请稍后...",
			pageList: [4,10,20],
			pageSize: 4,
			toolbar: "#search_batch_to_channel_tb",
			multiple: false,
			required: false,
			idField: "id",
			textField: "channelName",
			url: "./admin_op/channel_searchChannel",
			pagination: true,
			columns:[[
		          {field: "id",title: "id",align: "center",width: 80},
		          {field: "channelIcon",title: "icon", align: "center",width: 60, height: 60,
		        	  formatter: function(value,row,index) {
		        		  return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
		        	  }
		          },
		          {field: "channelName",title: "频道名称",align: "center",width: 280}
			]],
			queryParams:searchChannelQueryParams
		});
		
		var ss_batch_channel_p = $('#ss_batch_channel').combogrid('grid').datagrid('getPager');
		ss_batch_channel_p.pagination({});
		
		$("#main").show();
	});
	
/**
 * 搜索频道名称
 */
function searchChannel() {
	maxId = 0;
	searchChannelQueryParams.query = $("#channel_searchbox").searchbox("getValue");
	$("#ss_channel").combogrid("grid").datagrid("load",searchChannelQueryParams);
};

/**
 * 批量生效
 * @author zhangbo 2015-11-09
 */
function batchValid() {
	var rows = $("#htm_table").datagrid("getSelections");
	if(rows.length > 0){
		$.messager.confirm("温馨提示", "您确定要使已选中的织图生效吗？", function(r){
			if(r){
				var wids = [];
				for(var i=0;i<rows.length;i++){
					wids[i] = rows[i].worldId;
				}
				var params = {
						channelId: rows[0].channelId,	// 都处于一个频道，从第一个元素获取channelId，因为存在可能瀑布流中也会变幻频道id，故不能从缓存中获取，要在已经获取的数据中拿出channelId
						wids: wids.toString(),
						valid: 1	// 批量生效设置valid为1
					};
				$.post("./admin_op/channelWorld_batchUpdateChannelWorldValid", params, function(result){
					// 清除所有已选择的记录，避免重复提交id值
					$("#htm_table").datagrid("clearSelections");
					// 批量生效重新第一页
					$("#htm_table").datagrid("load",myQueryParams);
				});
			}
		});	
	}else{
		$.messager.alert("温馨提示","请先选择记录，再执行批量生效操作!");
	}
};

/**
 * 批量删除（小编删除）
 * @author zhangbo 2015-11-09
 */
function batchInvalid() {
	var rows = $("#htm_table").datagrid("getSelections");
	if(rows.length > 0){
		$.messager.confirm("温馨提示", "您确定要删除已选中的织图吗？", function(r){
			if(r){
				var wids = [];
				for(var i=0;i<rows.length;i++){
					wids[i] = rows[i].worldId;
				}
				var params = {
						channelId: rows[0].channelId,	// 都处于一个频道，从第一个元素获取channelId，因为存在可能瀑布流中也会变幻频道id，故不能从缓存中获取，要在已经获取的数据中拿出channelId 
						wids: wids.toString(),
						valid: 2	// 批量删除设置valid为2，因为是小编删除
					};
				$.post("./admin_op/channelWorld_batchUpdateChannelWorldValid", params, function(result){
					$.messager.alert("温馨提示","删除" + rows.length + "条记录");
					// 清除所有已选择的记录，避免重复提交id值
					$("#htm_table").datagrid("clearSelections");
					// 批量删除刷新当前页
					$("#htm_table").datagrid("reload");
				});
			}
		});	
	}else{
		$.messager.alert("温馨提示","请先选择记录，再执行批量删除操作!");
	}
};

/**
 * 重排推荐
 */
function reIndexed() {
	$("#htm_indexed .opt_btn").show();
	$("#htm_indexed .loading").hide();
	$("#channelId_indexed").val($("#ss_channel").combogrid("getValue"));
	
	var rows = $("#htm_table").datagrid("getSelections");
	// 定义重新排序织图id集合
	var worldIds = [];
	for(var i=0;i<rows.length;i++){
		worldIds.push(rows[i].worldId);
	}
	
	$("#batch_to_valid_worldIds").val(worldIds.toString());
	
	// 打开添加窗口
	$("#htm_indexed").window("open");
};

function submitReIndexForm() {
	var $form = $("#indexed_form");
	if($form.form("validate")) {
		$("#htm_indexed .opt_btn").hide();
		$("#htm_indexed .loading").show();
		$("#indexed_form").form("submit", {
			url: $form.attr("action"),
			success: function(data){
				var result = $.parseJSON(data);
				$("#htm_indexed .opt_btn").show();
				$("#htm_indexed .loading").hide();
				if(result["result"] == 0) {
					$("#htm_indexed").window("close");  //关闭添加窗口
					maxId = 0;
					myQueryParams["world.maxId"] = maxId;
					$("#htm_table").datagrid("load",myQueryParams);
				} else {
					$.messager.alert("温馨提示",result["msg"]);  //提示添加信息失败
				}
				$("#htm_table").datagrid("clearSelections");
			}
		});
	}
};

/**
 * 搜索推荐用户
 */
function searchWorld() {
	maxId = 0;
	myQueryParams["world.maxId"] = maxId;
	myQueryParams["world.worldId"] = "";
	myQueryParams["world.channelId"] = $("#ss_channel").combogrid("getValue");
	myQueryParams["world.notified"] = $("#ss-notified").combogrid("getValue");
	// 频道织图瀑布流模式中，若选择“生效”，即代表，要查询频道织图生效，并且过滤掉织图被用户删除掉，所以flag指定为1
	if ( $("#ss-valid").combobox("getValue") == 1 ) {
		myQueryParams["flag"] = 1;
	}
	// 若选择“未生效”，即代表，要查询频道织图未生效，并且过滤掉织图被用户删除掉，所以flag指定为2
	else if ( $("#ss-valid").combobox("getValue") == 0 ) {
		myQueryParams["flag"] = 2;
	}
	// 若选择“小编删除”，即代表，要查询频道织图被小编删除，所以flag指定为3
	else if ( $("#ss-valid").combobox("getValue") == 2 ) {
		myQueryParams["flag"] = 3;
	}
	// 若选择“用户删除织图”，即代表，要查询织图被用户删除，所以flag指定为4
	else if ( $("#ss-valid").combobox("getValue") == 3 ) {
		myQueryParams["flag"] = 4;
	}
	$("#htm_table").datagrid("load",myQueryParams);
};

/**
 * 根据用户名搜索推荐用户
 */
function searchByWID() {
	maxId = 0;
	myQueryParams["world.maxId"] = maxId;
	myQueryParams["world.worldId"] = $("#ss_worldId").searchbox("getValue");
	myQueryParams["world.channelId"] = baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID");	// 频道id都从缓存获取
	myQueryParams["world.valid"] = "";
	$("#htm_table").datagrid("load",myQueryParams);
};

/**
 * 根据频道Id或名字查询频道
 */
function queryChannelByIdOrName(){
	var channelIdOrName = $("#ss_channelSearch").searchbox('getValue');
	var params={};
	if(channelIdOrName){
		if(isNaN(channelIdOrName)){
			params["channelName"]=channelIdOrName;
		}else{
			maxId = 0;
			myQueryParams["world.maxId"] = maxId;
			myQueryParams["world.channelId"] = channelIdOrName;
			$("#htm_table").datagrid("load",myQueryParams);
			return;
		}
		// 根据id或名称查询频道
		$.post("./admin_op/v2channel_queryOpChannelByIdOrName", params, function(result){
			if(result["result"] == 0) {
				var obj = result["obj"];
				
				maxId = 0;
				myQueryParams["world.maxId"] = maxId;
				myQueryParams["world.channelId"] = obj["channelId"];
				$("#ss_channel").combogrid("setValue",obj["channelId"]);
				$("#htm_table").datagrid("load",myQueryParams);
			} else {
				$.messager.alert("温馨提示",result['msg']);  //提示添加信息失败
			}
		},"json");
	}
};

/**
 * 批量添加织图到频道
 * @author zhangbo 2015-09-10
 */
function batchToChannel(){
	$("#htm_batch_channel").window("open");
};

/**
 * 批量添加到频道功能的搜索频道
 * @author zhangbo 2015-09-10
 */
function searchBatchToChannel() {
	var params = {
			maxId: 0,
			query: $("#batch_channel_searchbox").searchbox("getValue")
	};
	$("#ss_batch_channel").combogrid("grid").datagrid("load",params);
};

/**
 * 批量保存频道织图到所选频道中
 * @author zhangbo 2015-09-10
 */
function saveBatchWorldToChannelSubmit(){
	var rows = $("#htm_table").datagrid('getSelections');
	for (var i=0;i<rows.length;i++) {
		//该织图进入频道
		$.post("./admin_op/channel_saveChannelWorld",{
			"world.channelId": $("#ss_batch_channel").combogrid("getValue"),
			"world.worldId"  : rows[i].worldId,
			"world.valid"	 : 0
		},function(result){
			if(result["result"] != 0){
				$.messager.alert("温馨提示",result["msg"]);  //提示添加信息失败
			}
		},"json");
	}
	$("#htm_table").datagrid("reload");
	$("#htm_table").datagrid("clearSelections");
	$("#htm_batch_channel").window("close");
};

/**
 * 计划加精设置时间窗口打开
 * @author zhangbo 2015-09-11
 */
function openScheduleSuperbWindow(){
	// 设置
	$("#batch_to_superb_channelId").val($("#ss_channel").combogrid("getValue"));
	var rows = $("#htm_table").datagrid("getSelections");
	var worldIds = [];
	for (var i=0;i<rows.length;i++) {
		//提示添加信息失败
		if ( rows[i].valid == 0 ) {
			$.messager.alert("温馨提示","你所选择的频道织图中有未生效的，不能进行计划加精操作，请选择已生效的织图来加精！");
			$("#batch_to_superb_win").window("close");
			return;
		}
		worldIds.push(rows[i].worldId);
	}
	$("#batch_to_superb_worldIds").val(worldIds.toString());
	
	$("#htm_table").datagrid("clearSelections");
	$("#batch_to_superb_win").window("open");
};

/**
 * 计划加精提交
 * @author zhangbo	2015-09-11
 */
function batchChannelWorldToSuperbSubmit() {
	var $form = $("#batch_to_superb_form");
	if($form.form("validate")) {
		$("#batch_to_superb_form .opt_btn").hide();
		$("#batch_to_superb_form .loading").show();
		$form.form("submit", {
			url: $form.attr("action"),
			success: function(data){
				var result = $.parseJSON(data);
				$("#batch_to_superb_form .opt_btn").show();
				$("#batch_to_superb_form .loading").hide();
				if(result["result"] == 0) {
					$("#batch_to_superb_win").window("close");  // 关闭计划加精设置时间窗口
				} else {
					$.messager.alert("温馨提示",result["msg"]);  // 提示失败信息
				}
			}
		});
	}
};

</script>
</head>
<body>
	
	<div id="main" style="display: none;">
		<img id="page-loading" alt="" src="${webRootPath}/common/images/girl-loading.gif"/>
		<table id="htm_table"></table>
	
		<div id="tb" style="padding:5px;height:auto" class="none">
			<span class="search_label">请选择频道：</span>
			<input id="ss_channel" style="width:100px;" />
			<span id="htm_opt_btn" class="none">
				<a href="javascript:void(0);" onclick="javascript:batchValid();" class="easyui-linkbutton" title="批量生效" plain="true" iconCls="icon-ok">批量生效</a>
				<a href="javascript:void(0);" onclick="javascript:batchInvalid();" class="easyui-linkbutton" title="批量失效" plain="true" iconCls="icon-cut">批量删除</a>
				<a href="javascript:void(0);" onclick="javascript:reIndexed();" class="easyui-linkbutton" title="按照勾选顺序重新排序，并且生效" plain="true" iconCls="icon-converter" id="reIndexedBtn">计划重新排序并生效</a>
				<a href="javascript:void(0);" onclick="javascript:openScheduleSuperbWindow();" class="easyui-linkbutton" title="按照计划的时间，使频道织图加精" plain="true" iconCls="icon-converter">计划加精</a>
				<select id="ss-notified" class="easyui-combobox" style="width:100px;">
			        <option value="">所有通知状态</option>
			        <option value="0">未通知</option>
			        <option value="1">已通知</option>
		   		</select>
		   		<select id="ss-valid" class="easyui-combobox" style="width:100px;">
			        <option value="">所有状态</option>
			        <option value="1">生效</option>
			        <option value="0">未生效</option>
			        <option value="2">小编删除</option>
			        <option value="3">用户删除织图</option>
		   		</select>
		   		<a href="javascript:void(0);" onclick="javascript:searchWorld();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
		   		
		   		<div style="display:inline-block; vertical-align:middle;">
	   				<a href="javascript:void(0);" onclick="javascript:batchToChannel();" class="easyui-linkbutton" plain="true" iconCls="icon-add">批量添加到频道</a>
	   			</div>
	   			
		   		<span style="display: inline-block; vertical-align:middle; float: right;">
			        <input id="ss_worldId" class="easyui-searchbox" searcher="searchByWID" prompt="输入织图ID搜索" style="width:150px;" />
				</span>
			</span>
		</div> 

		<!-- 计划重新排序并生效 -->
		<div id="htm_indexed">
			<form id="indexed_form" action="./admin_op/cwSchedula_batchChannelWorldToSortAndValidSchedula" method="post">
				<table class="htm_edit_table" width="660">
					<tbody>
						<tr>
							<td class="leftTd">计划更新时间：</td>
							<td><input id="schedula" name="schedula" class="easyui-datetimebox" required="true"></td>
						</tr>
						<tr>
							<td class="leftTd">时间间隔(分钟)：</td>
							<td>
								<input  name="minuteTimeSpan" class="easyui-numberbox" value="5" style="width:171px"/>
							</td>
						</tr>
						<tr class="none">
							<td colspan="2"><input type="text" name="channelId" id="channelId_indexed" /></td>
						</tr>
						<tr class="none">
						<td colspan="2"><input type="text" name="wids" id="batch_to_valid_worldIds" /></td>
						</tr>
						<tr>
							<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReIndexForm();">确定</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_indexed').window('close');$('#htm_table').datagrid('clearSelections');">取消</a>
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
		
		<!-- 计划加精 -->
		<div id="batch_to_superb_win">
			<form id="batch_to_superb_form" action="./admin_op/cwSchedula_batchChannelWorldToSuperbSchedula" method="post">
				<table class="htm_edit_table" width="660">
					<tr>
						<td class="leftTd">计划更新时间：</td>
						<td><input id="schedula" name="schedula" class="easyui-datetimebox" required="true"></td>
					</tr>
					<tr>
						<td class="leftTd">时间间隔(分钟)：</td>
						<td>
							<input  name="minuteTimeSpan" class="easyui-numberbox" value="5" style="width:171px"/>
						</td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="channelId" id="batch_to_superb_channelId" /></td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="wids" id="batch_to_superb_worldIds" /></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="batchChannelWorldToSuperbSubmit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#batch_to_superb_win').window('close');$('#htm_table').datagrid('clearSelections');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">排序中...</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
	
		<!-- 批量添加到频道 -->
		<div id="htm_batch_channel">
			<table class="htm_edit_table" width="450">
				<tr>
					<td class="leftTd">频道名称：</td>
					<td><input id="ss_batch_channel" name="channelId" style="width:200px;" /></td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBatchWorldToChannelSubmit();">确定</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_batch_channel').window('close');">取消</a>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 频道织图toolbar上的搜索频道 -->
		<div id="search_channel_tb" style="padding:5px;height:auto" class="none">
			<input id="channel_searchbox" searcher="searchChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
		</div>
		
		<div id="search_batch_to_channel_tb" style="padding:5px;height:auto" class="none">
			<input id="batch_channel_searchbox" searcher="searchBatchToChannel" class="easyui-searchbox" prompt="频道名/ID搜索" style="width:200px;"/>
		</div>
		
	</div>
</body>
</html>
