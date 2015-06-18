<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道维护</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">

var maxId = 0;

	init = function() {
		// 以下两个属性由于在onBeforeInit方法中设置不生效，所以设置在这里
		htmTableHeight = undefined;	// 取消高度设置，然后在datagrid中设置自动高度
		htmTableWidth = $(document.body).width();	// 表格宽度设置为当前页面宽度
		
		loadPageData(initPage);
	};
	hideIdColumn = false;
	htmTableTitle = "频道列表"; // 表格标题
	toolbarComponent = '#tb';
	loadDataURL = "./admin_op/v2channel_queryOpChannel"; // 数据装载请求地址
	saveChannelURL = "./admin_op/v2channel_insertOpChannel"; // 保存频道地址
	updateChannelByIdURL = "./admin_op/v2channel_updateOpChannel"; // 更新频道地址
	queryChannelByIdOrNameURL = "./admin_op/v2channel_queryOpChannelByIdOrName"; // 根据id查询频道
	
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
		{field : 'ck',checkbox : true},
		{field : 'channelId',title : 'id',align : 'center'},
		{field : 'channelIcon',title : '封面', align : 'center',
			formatter:function(value,row,index) {
				return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
			}
		},
		{field : 'ownerName',title : '频道主',align : 'center',width:80,
			formatter: function(value,row,index) {
  				return "<span title='" + value + "'>" + value + "</span>";
  			}
		},
		{field : 'ownerId',title : '频道主ID',align : 'center'},
		{field : 'channelName',title : '频道名称',align : 'center',width:120,
			formatter: function(value,row,index) {
  				return "<span title='" + value + "'>" + value + "</span>";
  			}
		},
		{field : 'channelDesc',title : '频道描述',align : 'center',width:100,
			formatter: function(value,row,index) {
  				return "<span title='" + value + "'>" + value + "</span>";
  			}
		},
		{field : 'channelTypeId',title:'频道类型',align : 'center',
			formatter:function(value,row,index){
				switch(value){
					case 1 :return "活动频道";
					case 2 :return "贴纸频道";
					case 3 :return "屏蔽频道";
					default:return "默认频道";
				}
			}},
		{field : 'themeName',title:'所属专题',align : 'center'},
		{field : 'channelLabelNames',title:'标签',align : 'center',
			formatter: function(value,row,index) {
				var ret = "";
				if (value == "" || value == null) {
					ret = "<img title='点击打开标签页' class='htm_column_img pointer' onclick='channelLabelEdit("+row.channelId+")' src='./common/images/edit_add.png'/>"
				} else {
					ret += "<a title='点击打开标签页' class='updateInfo' href='javascript:void(0);' onclick='channelLabelEdit("+row.channelId+")'>";
					var valueArray = value.split(",");
					ret += "(" + valueArray.length + ")" + valueArray[0] + "</a>";
				}
  				return ret;
  			}
		},
		{field : 'worldCount',title:'织图数',align : 'center'},
		{field : 'worldPictureCount',title:'图片数',align : 'center'},
		{field : 'memberCount',title:'关注数',align : 'center'},
		{field : 'superbCount',title:'精选数',align : 'center'},
  		{field : 'opt',title : '操作',align : 'center',rowspan : 1,
			formatter : function(value, row, index ) {
				return "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row.channelId + "\",\"" + index + "\"," + true + ")'>【修改】</a>";
			}
		},
		{field : 'relatedChannel',title:'关联频道',align : 'center',
			formatter: function(value,row,index) {
				var ret = "";
				if (value == "") {
					ret = "<img title='点击打开关联频道页' class='htm_column_img pointer' onclick='relatedChannelEdit("+row.channelId+")' src='./common/images/edit_add.png'/>"
				} else {
					ret += "<a title='点击打开关联频道页' class='updateInfo' href='javascript:void(0);' onclick='relatedChannelEdit("+row.channelId+")'>";
					var valueArray = value.split(",");
					ret += "(" + valueArray.length + ")" + valueArray[0] + "</a>";
				}
  				return ret;
  			}
		},
  		{field : 'superb',title : '精选',align : 'center',
  			formatter: function(value,row,index) {
  				var superb;
  				switch(value) {
  				case 1:
  					tip = "已加精,点击取消加精";
  					img = "./common/images/ok.png";
  					superb = 0;
  					break;
  				default:
  					tip = "点击加精";
  					img = "./common/images/tip.png";
  					superb = 1;
  					break;
  				}
  				return "<img title='"+ tip + "' class='htm_column_img pointer' onclick='updateChannelSuperbOp("+ row.channelId +","+ superb +")' src='" + img + "'/>";
  			}
  		},
  		{field : 'top',title : '置顶推荐',align : 'center',
  			formatter: function(value,row,index) {
  				var top;
  				switch(value) {
  				case 1:
  					tip = "已置顶,点击取消置顶";
  					img = "./common/images/ok.png";
  					top = 0;
  					break;
  				default:
  					tip = "点击置顶";
  					img = "./common/images/tip.png";
  					top = 1;
  					break;
  				}
  				return "<img title='"+ tip + "' class='htm_column_img pointer' onclick='updateChannelTopOp("+ row.channelId +","+ top +")' src='" + img + "'/>";
  			}
  		},
  		{field : 'valid',title : '有效性',align : 'center',
  			formatter: function(value,row,index) {
  				var valid;
  				switch(value) {
  				case 1:
  					tip = "设置为不生效";
  					img = "./common/images/ok.png";
  					valid = 0;
  					break;
  				default:
  					tip = "点击设为有效";
  					img = "./common/images/tip.png";
  					valid = 1;
  					break;
  				}
  				return "<img title='"+ tip + "' class='htm_column_img pointer' onclick='updateChannelValidOp("+ row.channelId +","+ valid +")' src='" + img + "'/>";
  			}
  		},
  		{field : 'danmu',title : '弹幕',align : 'center',
  			formatter: function(value,row,index) {
  				var danmu;
  				switch(value) {
  				case 1:
  					tip = "点击设为非弹幕频道";
  					img = "./common/images/ok.png";
  					danmu = 0;
  					break;
  				default:
  					tip = "点击设为有弹幕频道";
  					img = "./common/images/tip.png";
  					danmu = 1;
  					break;
  				}
  				return "<img title='"+ tip + "' class='htm_column_img pointer' onclick='updateChannelDanmuOp("+ row.channelId +","+ danmu +")' src='" + img + "'/>";
  			}
  		},
  		{field : 'moodFlag',title : '心情',align : 'center',
  			formatter: function(value,row,index) {
  				var moodFlag;
  				switch(value) {
  				case 1:
  					tip = "点击设为非心情频道";
  					img = "./common/images/ok.png";
  					moodFlag = 0;
  					break;
  				default:
  					tip = "点击设为有心情频道";
  					img = "./common/images/tip.png";
  					moodFlag = 1;
  					break;
  				}
  				return "<img title='"+ tip + "' class='htm_column_img pointer' onclick='updateChannelMoodOp("+ row.channelId +","+ moodFlag +")' src='" + img + "'/>";
  			}
  		},
  		{field : 'worldFlag',title : '织图',align : 'center',
  			formatter: function(value,row,index) {
  				var worldFlag;
  				switch(value) {
  				case 1:
  					tip = "点击设为非织图频道";
  					img = "./common/images/ok.png";
  					worldFlag = 0;
  					break;
  				default:
  					tip = "点击设为有织图频道";
  					img = "./common/images/tip.png";
  					worldFlag = 1;
  					break;
  				}
  				return "<img title='"+ tip + "' class='htm_column_img pointer' onclick='updateChannelWorldOp("+ row.channelId +","+ worldFlag +")' src='" + img + "'/>";
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
			width : 600,
			height : 430,
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
				
				$('#edit_form').form('reset');
				
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		$('#ownerId_edit').combogrid({
			panelWidth : 340,
		    panelHeight : 250,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#user_tb",
		    multiple : false,
		    required : true,
		   	idField : 'id',
		    textField : 'id',
		    url : './admin_user/user_queryUser',
		    pagination : true,
		    columns:[[
				{field : 'userAvatar',title : '头像',align : 'left',width : 45,
					formatter: function(value, row, index) {
						imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
						content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
						if(row.star >= 1) {
							content = content + "<img title='" + row['verifyName'] + "' class='avatar_tag' src='" + row['verifyIcon'] + "'/>";
						}
						return "<span>" + content + "</span>";	
					}		
				},
				{field : 'id',title : 'ID',align : 'center', width : 60},
				{field : 'userName',title : '昵称',align : 'center',width : 100
				},
				{field : 'sex',title:'性别',align : 'center', width:40, 
					formatter: function(value, row, index) {
						if(value == 1) {
							return "男";
						} else if(value == 2) {
							return "女";
						}
						return "";
					}
				}
		    ]],
		    queryParams:userQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > userMaxId) {
						userMaxId = data.maxId;
						userQueryParams.maxId = userMaxId;
					}
				}
		    },
		});
		
		removePageLoading();
		$("#main").show();
	};
	
/**
 * 初始化频道信息编辑窗口
 */
function initEditWindow(id, index, isUpdate) {
	$("#channelName_edit").focus();  // 光标定位
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改频道信息');
		$('#htm_edit').window('open');
		$.post(queryChannelByIdOrNameURL,{
			"channelId":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#channelIcon_edit").val(obj['channelIcon']);
				$("#channelImg_edit").attr('src', obj['channelIcon']);
				$("#channelName_edit").val(obj['channelName']);
				$("#id_edit").val(obj['channelId']);
				$("#valid_edit").val(obj['valid']);
				$("#serial_edit").val(obj['serial']);
				$("#ownerId_edit").val(obj['ownerId']);
				$("#ownerId_edit").combogrid('setValue',obj['ownerId']);
				$("#channelDesc_edit").val(obj['channelDesc']);
				$("#channel_type_id").combobox('setValue',obj['channelTypeId']);
				$("#channelThemeId").combobox('setValue',obj['themeId']);
				
				$("#edit_loading").hide();
				$("#edit_form").show();
			} else {
				$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
			}
			
		},"json");
	} else {
		$('#htm_edit').panel('setTitle', '添加频道');
		$('#htm_edit').window('open');
		$("#edit_loading").hide();
		$("#edit_form").show();
	}
}

// 提交表单，以后补充装载验证信息
function loadEditFormValidate(index, isUpdate) {
	var url = saveChannelURL;
	if(isUpdate) {
		url = updateChannelByIdURL;
	}
	var $form = $('#edit_form');
	formSubmitOnce = true; // 每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				// 第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#edit_form .opt_btn").hide();
				$("#edit_form .loading").show();
				// 验证成功后以异步方式提交表单
				$.post(url,$form.serialize(),
					function(result){
						$("#edit_form .opt_btn").show();
						$("#edit_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_edit').window('close');  // 关闭添加窗口
							maxId = 0;
							myQueryParams['channel.maxId'] = maxId;
							loadPageData(initPage);
							$.messager.alert('提示',result['msg']);  // 提示添加信息成功
						} else {
							$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#channelIcon_edit")
	.formValidator({empty:false, onshow:"请选icon（必填）",onfocus:"请选icon",oncorrect:"该封面可用！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#channelName_edit")
	.formValidator({empty:false,onshow:"频道名称（必填）",onfocus:"请输入名称",oncorrect:"设置成功"});
	
//	$("#channelsubtitle")
//	.formValidator({empty:true,onshow:"标题（必填）",onfocus:"请输入副标题",oncorrect:"设置成功"});
};

/**
 * 搜索用户
 */
function searchUser() {
	userMaxId = 0;
	var userName = $('#ss_user').searchbox('getValue');
	userQueryParams = {
		'maxId' : userMaxId,
		'userName' : userName
	};
	$("#ownerId_edit").combogrid('grid').datagrid("load",userQueryParams);
}

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
			$(this).val(rows[i]['channelId']);
	});
	// 打开添加窗口
	$("#htm_serial").window('open');
}

/**
 * 刷新频道缓存
 */
function refreshCache() {
	$.messager.confirm('提示', '确定要刷新频道缓存？刷新后频道置顶、精选、频道主题的数据将生效！', function(r){
		if (r){
			$('#htm_table').datagrid('loading');
			$.post('./admin_op/v2channel_refreshCache',{},function(result){
				if(result['result'] == 0) {
					$('#htm_table').datagrid('loaded');
					$.messager.alert('提示', '刷新成功');  // 提示添加信息成功
				} else {
					$('#htm_table').datagrid('loaded');
					$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
				}
			},"json");				
		}
	});
};

/**
 * 查看置顶推荐
 */
function showTop(){
	myQueryParams.topFlag = true;
	$('#htm_table').datagrid('load',myQueryParams);
};

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

function addChannelSubmit(){
	var url;
	var channelId = $("#id_edit").val();
	if(channelId == ""){
		url = "./admin_op/v2channel_insertOpChannel";
	}else{
		url = "./admin_op/v2channel_updateOpChannel";
	}
	var channelIcon = $("#channelIcon_edit").val();
	var channelName = $("#channelName_edit").val();
	var channelTypeId = $("#channel_type_id").combobox('getValue');
	var ownerId = $("#ownerId_edit").combogrid('getValue');
	var channelDesc = $("#channelDesc_edit").val();
	var themeId = $("#channelThemeId").combobox('getValue');
	
	$('#htm_table').datagrid('loading');
	$.post(url,{
		'channelId':channelId,
		'channelIcon':channelIcon,
		'channelName':channelName,
		'channelDesc':channelDesc,
		'channelTypeId':channelTypeId,
		'ownerId':ownerId,
		'themeId':themeId
		},function(result){
			$('#htm_table').datagrid('loaded');
			if(result['result'] == 0){
				$("#htm_table").datagrid('reload');
				$("#htm_edit").window('close');
			}else{
				$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
			}
			
	},"json");
};

/**
 * 按条件查询：频道主题，有效性，精选，频道ID，频道名称（模糊）
 */
function searchChannel(){
	var params = {};
	params['maxId'] = maxId;
	params['themeId'] = $('#ss_channelTheme').combobox('getValue');
	params['valid'] = $('#ss_valid').combobox('getValue');
	params['superb'] = $('#ss_superb').combobox('getValue');
	$('#htm_table').datagrid('load',params);
};

/**
 * 根据频道id或者名称（支持模糊）查询
 */
function searchChannelByNameOrId(){
	var channelNameOrId = $("#ss_channelNameOrId").searchbox('getValue');
	var params = {};
	if(isNaN(channelNameOrId)){
		params['channelName'] = channelNameOrId;
	}else{
		params['channelId'] = channelNameOrId;
	}
	$('#htm_table').datagrid('load',params);
};

/**
 * 标签编辑页
 */
function channelLabelEdit(channelId){
	
	$.fancybox({
		'margin'			: 20,
		'width'				: '75%',
		'height'			: '80%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: 'page_operations_channelLabelEdit?channelId=' + channelId,
		onClosed			: function(){}
	});
};

/**
 * 相关频道编辑
 */
function relatedChannelEdit(channelId){
	
	$.fancybox({
		'margin'			: 20,
		'width'				: '75%',
		'height'			: '80%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: 'page_operations_relatedChannelEdit?channelId=' + channelId,
		onClosed			: function(){}
	});
};

/**
 * 更新频道加精操作
 */
function updateChannelSuperbOp(channelId,superb) {
	$.post("./admin_op/v2channel_updateOpChannel",{channelId:channelId,superb:superb}, function(result){
		if(result['result'] == 0) {
			$("#htm_table").datagrid("reload");
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
	});
};

/**
 * 更新频道置顶操作
 */
function updateChannelTopOp(channelId,top) {
	var url = "";
	// 若置顶，进行保存操作，否则进行删除操作
	if (top == 1) {
		url = "./admin_op/v2channel_saveChannelTop";
	} else if (top == 0) {
		url = "./admin_op/v2channel_deleteChannelTop";
	} else {
		$.messager.alert('提示',"传参出现问题，请联系技术人员");
		return;
	}
	$.post(url,{channelId:channelId}, function(result){
		if(result['result'] == 0) {
			$("#htm_table").datagrid("reload");
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
	});
};

/**
 * 更新频道有效性操作
 */
function updateChannelValidOp(channelId,valid) {
	$.post("./admin_op/v2channel_updateOpChannel",{channelId:channelId,valid:valid}, function(result){
		if(result['result'] == 0) {
			$("#htm_table").datagrid("reload");
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
	});
};

/**
 * 更新频道是否有弹幕操作
 */
function updateChannelDanmuOp(channelId,danmu) {
	$.post("./admin_op/v2channel_updateOpChannel",{channelId:channelId,danmu:danmu}, function(result){
		if(result['result'] == 0) {
			$("#htm_table").datagrid("reload");
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
	});
};

/**
 * 更新频道是否有心情操作
 */
function updateChannelMoodOp(channelId,moodFlag) {
	$.post("./admin_op/v2channel_updateOpChannel",{channelId:channelId,moodFlag:moodFlag}, function(result){
		if(result['result'] == 0) {
			$("#htm_table").datagrid("reload");
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
	});
};

/**
 * 更新频道是否有织图操作
 */
function updateChannelWorldOp(channelId,worldFlag) {
	$.post("./admin_op/v2channel_updateOpChannel",{channelId:channelId,worldFlag:worldFlag}, function(result){
		if(result['result'] == 0) {
			$("#htm_table").datagrid("reload");
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
	});
};

</script>
</head>
<body>
	<div id="main" style="display: none;">
		<table id="htm_table"></table>
		<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<a href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" title="添加织图到广场" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:reSerial();" class="easyui-linkbutton" title="重排活动排序" plain="true" iconCls="icon-converter" id="reSerialBtn">重新排序
			<span id="reSerialCount" type="text" style="font-weight:bold;">0</span></a>
			<a href="javascript:void(0);" onclick="javascript:refreshCache();" class="easyui-linkbutton" plain="true" iconCls="icon-converter" id="refreshCacheBtn">刷新缓存</a>
			<span class="search_label">所属专题过滤：</span>
			<input id="ss_channelTheme" class="easyui-combobox" data-options="valueField:'themeId',textField:'themeName',url:'./admin_op/v2channel_queryChannelThemeList'" style="width:150px">
			<span class="search_label">有效性过滤：</span>
			<select id="ss_valid" class="easyui-combobox" style="width:80px;">
	   			<option value="">所有状态</option>
	   			<option value="1">生效</option>
	   			<option value="0">未生效</option>
   			</select>
   			<span class="search_label">加精过滤：</span>
   			<select id="ss_superb" class="easyui-combobox" style="width:80px;">
	   			<option value="">所有状态</option>
	   			<option value="1">精选</option>
	   			<option value="0">非精选</option>
			</select>
   			<a href="javascript:void(0);" onclick="javascript:searchChannel();" plain="true" class="easyui-linkbutton" iconCls="icon-search" id="search_btn">查询</a>
   			<input id="ss_channelNameOrId" type="text" class="easyui-searchbox" data-options="searcher:searchChannelByNameOrId,iconCls:'icon-search',prompt:'请填写频道名称或频道ID'" style="width:200px"></input>
   			<a href="javascript:void(0);" onclick="javascript:showTop();" class="easyui-linkbutton" style="float:right;" plain="true" iconCls="icon-search" id="showTop">查看置顶推荐</a>
   		</div>
		</div> 
	
		<!-- 添加记录 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_op/v2channel_insertOpChannel" method="post">
				<table id="htm_edit_table" style="width:100%;height:100px;">
					<tbody>
						<tr>
							<td class="leftTd">ICON：</td>
							<td style="height: 90px;">
								<input class="none" type="text" name="channelIcon" id="channelIcon_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
								<a id="channelIcon_edit_upload_btn" style="position: absolute; margin:30px 0 0 100px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
								<img id="channelImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
								<div id="channelIcon_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">
									上传中...<span class="upload_progress"></span><span>%</span>
								</div>
							</td>
							<td class="rightTd">
								<div id="channelIcon_editTip" style="display: inline-block;" class="tipDIV"></div>
							</td>
						</tr>
						<tr>
							<td class="leftTd">频道名称：</td>
							<td><input id="channelName_edit" name="channelName" onchange="validateSubmitOnce=true;"/></td>
							<td class="rightTd"><div id="channelName_editTip" class="tipDIV"></div></td>
						</tr>
						 <tr>
							<td class="leftTd">频道描述：</td>
							<td colspan="2"><textarea name="channelDesc" id="channelDesc_edit" onchange="validateSubmitOnce=true;" style="width:80%;height:100px;"></textarea></td>
						</tr>
						 
						<tr>
							<td class="leftTd">拥有者ID：</td>
							<td><input id="ownerId_edit" name="ownerId" onchange="validateSubmitOnce=true;" style="width:206px;" /></td>
						</tr>
						
						<tr>
							<td class="leftTd">专属主题：</td>
							<td>
								<input id="channelThemeId" name="themeId" 
									class="easyui-combobox" onchange="validateSubmitOnce=true;" 
										data-options="valueField:'themeId',textField:'themeName',url:'./admin_op/v2channel_queryChannelThemeList'" style="width:206px;">
							</td>
						</tr>
						
						<tr>
							<td class="leftTd">频道类型：</td>
							<td>
								<select name="channelTypeId" id="channel_type_id" class="easyui-combobox" onchange="validateSubmitOnce=true;" style="width: 206px;">
									<option value="0">默认频道</option>
						   			<option value="1">活动频道</option>
						   			<option value="2">贴纸频道</option>
						   			<option value="3">屏蔽频道</option>
								</select>
							</td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="channelId" id="id_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="valid" id="valid_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="none">
							<td colspan="3"><input type="text" name="serial" id="serial_edit" onchange="validateSubmitOnce=true;"/></td>
						</tr>
						
						<tr class="opt_btn">
							<td colspan="3" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addChannelSubmit();">提交</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_edit').window('close');">取消</a>
							</td>
						</tr>
						<tr class="loading none">
							<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
								<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
								<span style="vertical-align:middle;">保存中...</span>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<!-- 拥有者IDcombogrid用到的toolbar，用于查找用户 -->
		<div id="user_tb" style="padding:5px;height:auto" class="none">
			<input id="ss_user" searcher="searchUser" class="easyui-searchbox" prompt="用户名/ID搜索" style="width:200px;"/>
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

	
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
	<script type="text/javascript">
	
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'channelIcon_edit_upload_btn',
        max_file_size: '100mb',
        flash_swf_url: 'js/plupload/Moxie.swf',
        chunk_size: '4mb',
        uptoken_url: './admin_qiniu/uptoken',
        domain: 'http://imzhitu.qiniudn.com/',
        unique_names: false,
        save_key: false,
        auto_start: true,
        init: {
            'FilesAdded': function(up, files) {
            	$("#channelIcon_edit_upload_btn").hide();
            	$("#channelImg_edit").hide();
            	var $status = $("#channelIcon_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#channelIcon_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#channelIcon_edit_upload_btn").show();
            	$("#channelImg_edit").show();
            	$("#channelIcon_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://imzhitu.qiniudn.com/'+$.parseJSON(info).key;
            	$("#channelImg_edit").attr('src', url);
            	$("#channelIcon_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  // 提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/channel/" + timestamp+suffix;
                return key;
            }
        }
    });
    
	</script>
</body>
</html>