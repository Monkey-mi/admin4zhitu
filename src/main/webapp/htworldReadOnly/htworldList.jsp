<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<script type="text/javascript">
var maxId = 0,
	count = 0,
	currentIndex = 0,
	maxActivitySerial = 0,
	mouthCount = 0,
	firstMonthDate = new Date();
	clean = function(){
		count = 0;
		mouthCount = 0;
		firstMonthDate = new Date();
	},
	today = new Date(),
	todayStr = baseTools.simpleFormatDate(today);
	
	timeCompare = function(date) {
		var startTimeStr = $("#startTime").datebox('getValue'),
		endTimeStr = $("#endTime").datebox('getValue'),
		startTime = Date.parse(startTimeStr),
		endTime = Date.parse(endTimeStr);
		if(endTime < startTime) {
			var time = $(this).datebox('getValue');
			$("#startTime").datebox('setValue', time);
			$("#endTime").datebox('setValue', time);
		}
		
		if(endTime > today){
			$("#endTime").datebox('setValue', todayStr);
		}
		
		if(startTime > today) {
			$("#startTime").datebox('setValue', todayStr);
			$("#endTime").datebox('setValue', todayStr);
		}
	},
	search = function() {
		maxId = 0;
		var startTime = $('#startTime').datetimebox('getValue'),
			endTime = $('#endTime').datetimebox('getValue'),
			phoneCode = $('#phoneCode').combobox('getValue'),
			valid = $("#valid").combobox('getValue'),
			shield = $("#shield").combobox('getValue');
		myQueryParams = {
			'maxId' : maxId,
			'startTime':startTime,
			'endTime':endTime,
			'phoneCode':phoneCode,
			'valid':valid,
			'shield':shield
		};
		$("#htm_table").datagrid("load",myQueryParams);
	},
	searchByPhoneCode = function(record) {
		var startTime = $('#startTime').datetimebox('getValue'),
		endTime = $('#endTime').datetimebox('getValue');
		myQueryParams.startTime = startTime;
		myQueryParams.endTime = endTime;
		myQueryParams.phoneCode = record.value;
		myQueryParams.maxId = maxId;
		$("#htm_table").datagrid("load",myQueryParams);
	},
	activityQueryParams = {
		'maxSerial':maxActivitySerial
	};

	
/**
 * 初始化活动添加窗口
 */
function initActivityAddWindow(worldId, activityrecd, index) {
	currentIndex = index;
	$("#worldId_activity").val(worldId);
	$("#htm_activity").window('open');
	if(activityrecd == 0) {
		$("#activity_loading").hide();
		$("#activity_form").show();
	} else {
		$.post("./admin_ztworld/label_queryLabelIdsWithoutRejectByWIDS",{
			'worldId':worldId
			},function(result){
				if(result['result'] == 0) {
					$("#activityIds_activity").combogrid('setValues', result['labelInfo']);
					$("#activity_loading").hide();
					$("#activity_form").show();
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
			},"json");
	}
}


/**
 * 提交活动表单
 */
function submitActivityForm() {
	var $form = $('#activity_form');
	if($form.form('validate')) {
		$('#htm_activity .opt_btn').hide();
		$('#htm_activity .loading').show();
		$('#activity_form').form('submit', {
			url: $form.attr('action'),
			success: function(data){
				var result = $.parseJSON(data);
				$('#htm_activity .opt_btn').show();
				$('#htm_activity .loading').hide();
				if(result['result'] == 0) { 
					updateValue(currentIndex, 'activityrecd',1);
					$('#htm_activity').window('close');  //关闭添加窗口
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			}
		});
	}
}

function removeTypeWorld(worldId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/type_deleteTypeWorldByWorldId",{
		'worldId':worldId,
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'squarerecd','0');	
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

function addLatestValid(worldId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/ztworld_updateLatestValid",{
		'id':worldId,
		'valid':1
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'latestValid','1');	
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

function removeLatestValid(worldId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/ztworld_updateLatestValid",{
		'id':worldId,
		'valid':0
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'latestValid','0');	
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

function loadTypeUpdateFormValid(index, isAdd,userId,labelIsExist) {
	var addForm = $('#type_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$('#htm_i .opt_btn').hide();
				$('#htm_interact .loading').show();
				//验证成功后以异步方式提交表单
				var $typeId = $("#typeId_type"),
					worldId = $("#worldId_type").val(),
					worldType = $typeId.combobox('getText'),
					typeId = $typeId.combobox('getValue');
				
				$("#htm_type .opt_btn").hide();
				$("#htm_type .loading").show();
				$.post("./admin_ztworld/type_saveTypeWorld",{
					'worldId':worldId,
//					'worldType':worldType,
//					'typeId':typeId,
					'worldType':"旅行",
					'typeId':"1",
					'isAdd':isAdd
					},function(result){
						formSubmitOnce = true;
						$("#htm_type .opt_btn").show();
						$("#htm_type .loading").hide();
						if(result['result'] == 0) {
							updateValues(index,['squarerecd','worldType'],['1','旅行']);
							
						} else {
							$.messager.alert('失败提示',result['msg']);  //提示失败信息
							return false;
						}
					},"json");
				//若标签为空，则更新标签
				if(labelIsExist == 0)
				 $.post("./admin_ztworld/label_updateWorldLable",{
					 'worldId':worldId,
					 'userId':userId,
					 'labelId':typeId,
					 'labelName':worldType
				 },function(result){
					 if(result['result'] == 0){
						 updateValues(index,['worldLabel'],[worldType]);
					 }else{
						 $.messager.alert('失败提示',result['msg']);  //提示失败信息
					 }
				 },"json");
				$('#htm_type').window('close');  //关闭添加窗口
				return false;
			}
		}
	});
	
	$("#typeId_type")
	.formValidator({empty:false,onshow:"请选择分类",onfocus:"设置分类",oncorrect:"设置成功"});;
	
}

var htmTableTitle = "分享列表维护", //表格标题
	mySortName = 'dateModified',
	worldKey = 'id',
	loadDataURL = "./admin_ztworld/ztworld_queryHTWorldList", //数据装载请求地址
	deleteURI = "./admin_ztworld/ztworld_deleteWorld?ids="; //删除请求地址
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId,
			'startTime':todayStr,
			'endTime':todayStr,
		},
		loadPageData(initPage);
	},
	myOnLoadBefore = function() {
		interacts = {};
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
			if(data.activityId != 'undefined') {
				activityId = data.activityId;
			} else {
				activityId = 0;
			}
		}
	},
	//分页组件,可以重载
	columnsFields = [
		//{field : 'ck',checkbox : true},
		{field : recordIdKey,title : 'id',align : 'center', width : 120},
		phoneCodeColumn,
		authorAvatarColumn = {field : 'authorAvatar',title : '头像',align : 'left',width : 45, 
			formatter: function(value, row, index) {
				userId = row['authorId'];
				var uri = 'page_user_userInfo?userId='+ userId;
				imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
					content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
				if(row.star >= 1) {
					content = content + "<img title='" + row['verifyName'] + "' class='avatar_tag' src='" + row['verifyIcon'] + "'/>";
				}
				return "<a class='updateInfo' href='javascript:void(0);)'>"+"<span>" + content + "</span>"+"</a>";	
			}
		},
		{field : 'authorName',title : '作者',align : 'center',width : 100},
		worldURLColumn,
		worldDescColumn,
		titleThumbPathColumn,
		{field : 'worldLabel',title : '标签',align : 'center',width : 120	},
		{field : 'worldType',title : '精选',align : 'center',width : 45,
			formatter: function(value,row,index){
				labelIsExist = row.worldLabel ? 1:0;
				if(row.valid == 0 || row.shield == 1) {
					return value;
				} else if(row.squarerecd == 1) {
					return "<a title='从精选列表移除' class='updateInfo pointer' onclick='javascript:removeTypeWorld(\""+ row[worldKey] + "\",\"" + index + "\",\"" + 'true' + "\")'>"+value+"</a>";
				} else if(value == '' || value == null) {
					img = "./common/images/edit_add.png";
					return "<img title='添加到精选列表' class='htm_column_img pointer' onclick='javascript:initTypeUpdateWindow(\""+ row[worldKey] + "\",\""+ row.typeId + "\",\"" + index + "\",\"" + 'true' + "\",\"" + row.authorId+ "\",\""+labelIsExist+"\")' src='" + img + "'/>";
				}
				return "<a title='添加到精选推荐列表' class='updateInfo pointer' onclick='javascript:initTypeUpdateWindow(\""+ row[worldKey] + "\",\""+ row.typeId + "\",\"" + index + "\",\"" + 'false' + "\",\"" + row.authorId+"\",\""+labelIsExist+"\")'>"+value+"</a>";;
			},
			styler: function(value,row,index){
				if (row.squarerecd == 1){
					return 'background-color:#fdf9bb;';
				}
			}
		},
		{field : 'activeOperated',title : '活动',align : 'center',width : 45,
			formatter: function(value,row,index) {
				if(row.valid == 0 || row.shield == 1) {
					return '';
				}
				switch(value) {
					case 0:
						tip = "等待审核";
						img = "./common/images/tip.png";
						break;
					case 1:
						tip = "审核通过，点击重新审核";
						img = "./common/images/ok.png";
						break;
					case 2:
						tip = "已经拒绝，点击重新审核";
						img = "./common/images/cancel.png";
						break;
					default:
						tip = "添加到活动";
						img = "./common/images/edit_add.png";
						break;
				}
				return "<img title='"+ tip + "' class='htm_column_img pointer' onclick='javascript:initActivityAddWindow(\""+ row.id + "\",\"" + value + "\",\"" + index + "\")' src='" + img + "'/>";
			}
		},
		{field : 'latestValid',title : '最新',align : 'center',width : 45,
			formatter: function(value,row,index) {
				if(value >= 1) {
					img = "./common/images/undo.png";
					return "<img title='从最新移除' class='htm_column_img pointer' onclick='javascript:removeLatestValid(\""+ row[worldKey] + "\",\"" + index + "\",\"" + 'true' + "\")' src='" + img + "'/>";
				} else if(row.valid == 0 || row.shield == 1) {
					return '';
				}
				img = "./common/images/edit_add.png";
				return "<img title='添加到最新' class='htm_column_img pointer' onclick='javascript:addLatestValid(\""+ row[worldKey] + "\",\"" + index + "\",\"" + 'true' + "\")' src='" + img + "'/>";
			}
		},
		shieldColumn
	],
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$("#labelId_interact").combotree({
			url:'./admin_interact/comment_queryLabelTree?hasTotal=true',
			onSelect:function(rec) {
				loadComment(rec.id,rec.labelName);
			}
		});
		
		$('#ss_searchLabel').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_interact/comment_queryAllLabel',
			onSelect:function(record){
				loadComment(record.id,record.labelName);
				$('#ss_searchLabel').combobox('clear');
			}
		});
		$('#ss_type_searchLabel').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_interact/comment_queryAllLabel',
			onSelect:function(record){
				loadTypeComment(record.id,record.labelName);
				$('#ss_type_searchLabel').combobox('clear');
			}
		});
		$("#labelId_type_interact").combotree({
			url:'./admin_interact/comment_queryLabelTree?hasTotal=true',
			onSelect:function(rec) {
				loadTypeComment(rec.id,rec.labelName);
			}
		});
		
		$("#searchBtn").click(function() {
			search();
		});

		$("#startTime").datebox({
			value:todayStr,
			onSelect:timeCompare
		});
		
		$("#endTime").datebox({
			value:todayStr,
			onSelect:timeCompare
		});
		
		$("#searchTodayBtn").click(function() {
			$("#startTime").datebox('setValue',todayStr);
			$("#endTime").datebox('setValue',todayStr);
			search();
			clean();
		});
		
		$("#searchWeekBtn").click(function() {
			var thisWeek = baseTools.getThisWeekDay(),
				firstDay = thisWeek.firstDay,
				lastDay = thisWeek.lastDay,
				firstDayStr = baseTools.simpleFormatDate(firstDay),
				lastDayStr = baseTools.simpleFormatDate(lastDay);
			$("#startTime").datebox('setValue',firstDayStr);
			$("#endTime").datebox('setValue',lastDayStr);
			search();
			clean();;
		});
		
		$("#searchLastWeekBtn").click(function() {
			if(count != 0){
				count++;
			} else {
				count = 1;
			}
			var lastWeek = {},
			a = new Date(),
			b = a.getDate() - a.getDay()-count*7,
			lastWeekLastDay = new Date(),
			lastWeekFirstDay = new Date();
		
			lastWeekFirstDay.setDate(b+1);//上周第一天
			lastWeekLastDay.setDate(b+7);//上周最后一天
			lastWeek.firstDay = lastWeekFirstDay;
			lastWeek.lastDay = lastWeekLastDay;
			firstDayStr = baseTools.simpleFormatDate(lastWeek.firstDay),
			lastDayStr = baseTools.simpleFormatDate(lastWeek.lastDay);
			$("#startTime").datebox('setValue',firstDayStr);
			$("#endTime").datebox('setValue',lastDayStr);
			search();
			mouthCount = 0; 
			firstMonthDate = new Date();
		});
		
		$("#searchMonthBtn").click(function() {
			var thisMonth = baseTools.getThisMonthDay(),
				firstDay = thisMonth.firstDay,
				lastDay = thisMonth.lastDay,
				firstDayStr = baseTools.simpleFormatDate(firstDay),
				lastDayStr = baseTools.simpleFormatDate(lastDay);
			$("#startTime").datebox('setValue',firstDayStr);
			$("#endTime").datebox('setValue',lastDayStr);
			search();
			clean();
		});
		
		$("#searchLastMonthBtn").click(function() {
			if(mouthCount!=0){
				mouthCount++;
			}else{
				mouthCount = 1;
			}
			var month = new Date().getMonth(),
			firstDate = new Date(),
			endDate = new Date(firstDate);
			if(mouthCount>1){
				month = firstMonthDate.getMonth();
				if(month==0){
					month = 11;
					firstMonthDate.setFullYear(firstMonthDate.getFullYear()-1);
				}else{
					month = month - 1;
					firstMonthDate.setFullYear(firstMonthDate.getFullYear());
				}
				firstDate.setFullYear(firstMonthDate.getFullYear());
				endDate.setFullYear(firstMonthDate.getFullYear());
				firstDate.setMonth(month, 1); //第一天
				endDate.setMonth(firstDate.getMonth()+1);//最后一天 
				endDate.setDate(0);
				firstMonthDate.setMonth(month);
			}else{//第一次
				if(month==0){
					month = 11;
				}else{
					month = month - 1;
				}
				firstDate.setMonth(month, 1); //第一天
				endDate.setMonth(firstDate.getMonth()+1);//最后一天 
				endDate.setDate(0);
				firstMonthDate.setMonth(firstDate.getMonth());
			}
			
			var firstDayStr = baseTools.simpleFormatDate(firstDate),
				lastDayStr = baseTools.simpleFormatDate(endDate);
			$("#startTime").datebox('setValue',firstDayStr);
			$("#endTime").datebox('setValue',lastDayStr);
			search();
			count = 0; 
		});
		
		$('#searchBtn').splitbutton({  
		    iconCls: 'icon-search',  
		    menu: '#mm'  
		});
		
		$('#htm_interact').window({
			modal : true,
			width : 500,
			top : 10,
			height : 270,
			title : '添加互动信息',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$('#labelId').combotree('clear');
				$('#levelId').combobox('clear');
				$('#comments_interact').combogrid('clear');
			}
		});
		
		$('#htm_type_interact').window({
			modal : true,
			width : 500,
			top : 10,
			height : 270,
			title : '添加分类 互动信息',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$('#type_labelId').combotree('clear');
				$('#type_levelId').combobox('clear');
				$('#comments_type_interact').combogrid('clear');
			}
		});
		
		$('#comments_interact').combogrid({
			panelWidth:600,
		    panelHeight:350,
		    loadMsg:'加载中，请稍后...',
			pageList: [10,20],
			editable: false,
		    multiple: true,
		   	toolbar:"#comment_tb",
		   	url:'./admin_interact/comment_queryCommentListByLabel',
		    idField:'id',
		    textField:'id',
		    pagination:true,
		    onClickCell: function(rowIndex, field, value){
		    	if(field == 'opt') 
		    		eee(field); // 强制报错停止脚本运行
		    },
		    columns:[[
				{field : 'ck',checkbox : true },
		        {field:'id',title:'ID',width:60},
		        {field:'content',title:'内容',width:400},
		        {field:'opt', title:'操作', align:'center', width:60,
		        	formatter: function(value,row,index){
		    			return "<a title='点击更新评论' class='updateInfo' href='javascript:addComment(\""+ row['id'] + "\",\"" + index + "\")'>" + '更新'+ "</a>";
		    		}	
		        }
		    ]],
		    queryParams:commentQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > commentMaxId) {
						commentMaxId = data.maxId;
						commentQueryParams.maxId = commentMaxId;
					}
				}
		    },
		    onChange:function(record) {
		    	var len = $('#comments_interact').combogrid('getValues').length,
		   			$selectCount = $("#selected_comment_count");
		    	$selectCount.text(len);
		    }
		});
		var p = $('#comments_interact').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					commentMaxId = 0;
					commentQueryParams.maxId = commentMaxId;
				}
			}
		});
		
		$('#comments_type_interact').combogrid({
			panelWidth:600,
		    panelHeight:350,
		    loadMsg:'加载中，请稍后...',
			pageList: [10,20],
			editable: false,
		    multiple: true,
		   	toolbar:"#comment_tb2",
		   	url:'./admin_interact/comment_queryCommentListByLabel',
		    idField:'id',
		    textField:'id',
		    pagination:true,
		    onClickCell: function(rowIndex, field, value){
		    	if(field == 'opt') 
		    		eee(field); // 强制报错停止脚本运行
		    },
		    columns:[[
				{field : 'ck',checkbox : true },
		        {field:'id',title:'ID',width:60},
		        {field:'content',title:'内容',width:400},
		        {field:'opt', title:'操作', align:'center', width:60,
		        	formatter: function(value,row,index){
		    			return "<a title='点击更新评论' class='updateInfo' href='javascript:addComment(\""+ row['id'] + "\",\"" + index + "\")'>" + '更新'+ "</a>";
		    		}	
		        }
		    ]],
		    queryParams:commentQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > commentMaxId) {
						commentMaxId = data.maxId;
						commentQueryParams.maxId = commentMaxId;
					}
				}
		    },
		    onChange:function(record) {
		    	var len = $('#comments_type_interact').combogrid('getValues').length,
		   			$selectCount = $("#selected_comment_count");
		    	$selectCount.text(len);
		    }
		});
		
		var q = $('#comments_type_interact').combogrid('grid').datagrid('getPager');
		q.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					commentMaxId = 0;
					commentQueryParams.maxId = commentMaxId;
				}
			}
		});
		
		$('#htm_userLevel').window({
			modal : true,
			width : 500,
			top : 10,
			height : 170,
			title : '添加等级用户',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$('#userLevelId_userLevel').combobox("clear");
			}
		});
		
		$('#typeId_type').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_ztworld/label_queryLabelForCombobox',
		});
		
		
		$('#htm_activity').window({
			modal : true,
			width : 340,
			top : 10,
			height : 160,
			title : '添加到活动',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$("#activity_form").hide();
				$("#activity_loading").show();
				$("#htm_activity .opt_btn").show();
				$("#htm_activity .loading").hide();
				$("#activityIds_activity").combogrid('clear');
			}
		});
		
		$('#htm_type').window({
			modal : true,
			width : 500,
			top : 10,
			height : 170,
			title : '添加到精选',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$("#type_form").hide();
				$("#type_loading").show();
				$("#htm_type .opt_btn").show();
				$("#htm_type .loading").hide();
				$("#typeId_type").combobox('clear');
				$("#labelIds_type").combogrid('clear');
			}
		});
		
		$('#labelId').combotree({
			editable:true,
			multiple:true,
			url:'./admin_interact/comment_queryAllLabelTree'
		});
		
		$('#type_labelId').combotree({
			editable:true,
			multiple:true,
			url:'./admin_interact/comment_queryAllLabelTree'
		});
		
		$('#labelIdSearch').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_interact/comment_queryAllLabel',
		});
		
		$('#type_labelIdSearch').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_interact/comment_queryAllLabel',
		});
		
		//不绑定keyup， 相当于重载回车
		$($('#labelIdSearch').combobox('textbox')).unbind("keyup").bind("keyup", function (e) {
	        if (e.keyCode == 13) {
	        	var boxLabelId = $("#labelIdSearch").combobox('getValue');
				var treeLabelId = $("#labelId").combotree('getValues');
				var boxLabelText = $("#labelIdSearch").combobox('getText');
				if(boxLabelId=="" || boxLabelText == boxLabelId){
					//若combobox里输入的text与value一致，表明所输入的text在combobox列表中不存在，则清空输入，不在combotree上增加
					$("#labelIdSearch").combobox('clear');
					return;
				}
				for(var i=0;i<treeLabelId.length;i++){
					//判断重复
					if(treeLabelId[i]==boxLabelId){
						$("#labelIdSearch").combobox('clear');
						return;
					}
				}
				if(treeLabelId!=""){
					//若原来的combotree上存在值，则使用setValues
					treeLabelId.push(boxLabelId);
					$("#labelId").combotree('setValues',treeLabelId);
				}else{
					//若原来的combotree上不存在值，则使用setValue
					$("#labelId").combotree('setValue',boxLabelId);
				}
				$("#labelIdSearch").combobox('clear');
	        }
	    });
		$($('#type_labelIdSearch').combobox('textbox')).unbind("keyup").bind("keyup", function (e) {
	        if (e.keyCode == 13) {
	        	var boxLabelId = $("#type_labelIdSearch").combobox('getValue');
				var treeLabelId = $("#type_labelId").combotree('getValues');
				var boxLabelText = $("#type_labelIdSearch").combobox('getText');
				if(boxLabelId=="" || boxLabelText == boxLabelId){
					//若combobox里输入的text与value一致，表明所输入的text在combobox列表中不存在，则清空输入，不在combotree上增加
					$("#type_labelIdSearch").combobox('clear');
					return;
				}
				for(var i=0;i<treeLabelId.length;i++){
					//判断重复
					if(treeLabelId[i]==boxLabelId){
						$("#type_labelIdSearch").combobox('clear');
						return;
					}
				}
				if(treeLabelId!=""){
					//若原来的combotree上存在值，则使用setValues
					treeLabelId.push(boxLabelId);
					$("#type_labelId").combotree('setValues',treeLabelId);
				}else{
					//若原来的combotree上不存在值，则使用setValue
					$("#type_labelId").combotree('setValue',boxLabelId);
				}
				$("#type_labelIdSearch").combobox('clear');
	        }
	    });
		
		$($('#ss_searchLabel').combobox('textbox')).unbind("keyup").bind("keyup", function (e) {
			if(e.keyCode == 13){
				var boxLabelId = $('#ss_searchLabel').combobox('getValue');
				var boxLabelName = $('#ss_searchLabel').combobox('getText');
				if(boxLabelId && boxLabelName && boxLabelId!="" && boxLabelName!=""){
					loadComment(boxLabelId,boxLabelName);
					$('#ss_searchLabel').combobox('clear');
				}
			}
		});
		
		$($('#ss_type_searchLabel').combobox('textbox')).unbind("keyup").bind("keyup", function (e) {
			if(e.keyCode == 13){
				var boxLabelId = $('#ss_type_searchLabel').combobox('getValue');
				var boxLabelName = $('#ss_type_searchLabel').combobox('getText');
				if(boxLabelId && boxLabelName && boxLabelId!="" && boxLabelName!=""){
					loadTypeComment(boxLabelId,boxLabelName);
					$('#ss_type_searchLabel').combobox('clear');
				}
			}
		});
		
		$('#activityIds_activity').combogrid({
			panelWidth : 650,
		    panelHeight : 380,
		    loadMsg : '加载中，请稍后...',
			pageList : [5,10,20],
			pageSize : 5,
			editable : false,
		    multiple : true,
		    required : true,
		   	idField : 'id',
		    textField : 'activityName',
		    url : './admin_op/op_queryNormalValidSquarePushActivity',
		    queryParams : activityQueryParams,
		    pagination : true,
		    columns:[[
				{field : 'serial',title : '活动序号',align : 'center', width : 60, hidden:true},
		  		{field : 'id',title : '活动ID',align : 'center', width : 60},
		  		{field : 'activityName',title : '活动名称',align : 'center', width : 120},
				{field : 'activityDesc',title : '活动简介',align : 'center', width : 200, 
		  			formatter : function(value, row, rowIndex ) {
						return "<span title='" + value + "' class='updateInfo'>"+value+"</span>";
					}	
				},
				{field : 'activityDate',title :'添加日期',align : 'center',width : 150},
				{ field : 'titlePath',title : '封面', align : 'center',width : 90, height:53,
					formatter:function(value,row,index) {
						return "<img width='80px' height='53px' alt='' title='点击编辑' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
					}
				}
		    ]],
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxSerial > maxActivitySerial) {
						maxActivitySerial = data.maxSerial;
						activityQueryParams.maxSerial = maxActivitySerial;
					}
				}
		    }
		});
		
		removePageLoading();
		$("#main").show();
	};
	
	/**
	 * 显示评论
	 * @param uri
	 */
	function showComment(realUri,worldId) {
		var url="./admin_interact/interact_queryIntegerIdByWorldId";
		var uri;
		$.post(url,{'worldId':worldId},function(result){
			if(result['interactId']){
				uri = realUri+"&interactId="+result['interactId'];
			}else{
				uri = realUri;
			}
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
			return false;
		},"json");
	}
	
	/**
	*显示用户信息
	*/
	function showUserInfo(uri){
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
	
	function typeInteract(worldId, index){
		$("#type_labelId").combotree('clear');
		$("#type_levelId").combobox('clear');
		$.post("./admin_interact/worldlevelList_queryWorldUNInteractCount",{
			'world_id':worldId
			},function(data){
				if(data["result"] == 0){
				$("#unValid_clickSum_type_interact").text(data["clickCount"]);
				$("#unValid_likedSum_type_interact").text(data["likedCount"]);
				$("#unValid_commentSum_type_interact").text(data["commentCount"]);
				}else{
					$.messager.alert('错误提示',data['msg']);  //提示添加信息失败
				}
		},"json");
		
		$.post("./admin_interact/interact_queryInteractSum",{
			'worldId':worldId
		},function(result){
			if(result['result'] == 0) {
				$("#clickSum_type_interact").text(result["clickCount"]);
				$("#likedSum_type_interact").text(result["likedCount"]);
				$("#commentSum_type_interact").text(result["commentCount"]);

				$("#type_interact_loading").hide();
				var $addForm = $('#type_interact_form');
				$addForm.show();
				$('#htm_type_interact .opt_btn').show();
				$('#htm_type_interact .loading').hide();
				clearFormData($addForm);
				$("#worldId_type_interact").val(worldId);
				$("#comments_type_interact").combogrid('clear');
				$("#comments_type_interact").combogrid('grid').datagrid('unselectAll');
				
				$("#ss_type_comment").searchbox('setValue', "");
				$("#duration_type_interact").val('24');
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},"json");
		
		$("#htm_type_interact form").hide();
		$("#type_interact_loading").show();
		$("#htm_type_interact").window('open');
		loadTypeInteractFormValidate(worldId, index);
	}
	
	function loadTypeInteractFormValidate(worldId, index) {
		var addForm = $('#type_interact_form');
		$.formValidator.initConfig({
			formid : addForm.attr("id"),			
			onsuccess : function() {
				if(formSubmitOnce==true){
					//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
					formSubmitOnce = false;
					//验证成功后以异步方式提交表单
					$('#htm_type_interact .opt_btn').hide();
					$('#htm_type_interact .loading').show();
					//验证成功后以异步方式提交表单
					$.post(addForm.attr("action"),addForm.serialize(),
						function(result){
							formSubmitOnce = true;
			            	$('#htm_type_interact .opt_btn').show();
							$('#htm_type_interact .loading').hide();
							if(result['result'] == 0) {
								$('#htm_type_interact').window('close');  //关闭添加窗口
								$.messager.alert('提示',result['msg']);  //提示添加信息成功
								clearFormData(addForm);  //清空表单数据	
								$('#comments_type_interact').combogrid('clear');
								$('html_table').datagrid("reload");
							} else {
								$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
							}
						},"json");	
					return false;
				}
			}
		});
	}
	
	/**
	*加载评论
	*/
	function loadTypeComment(labelId,labelName) {
		commentMaxId = 0;
		commentQueryParams.maxId = commentMaxId;
		commentQueryParams.labelId = labelId;
		commentQueryParams.comment = "";
		$("#comments_type_interact").combogrid('grid').datagrid('load',commentQueryParams);
	}
	
	/**
	*查找type评论
	*/
	function searchTypeComment() {
		var comment = $("#ss_type_comment").searchbox("getValue");
		commentMaxId = 0;
		commentQueryParams.maxId = commentMaxId;
		commentQueryParams.comment = comment;
		var tmp = $("#comments_type_interact").combogrid('getValues');
		$("#comments_type_interact").combogrid('grid').datagrid('load',commentQueryParams);
		$("#comments_type_interact").combogrid('setValues',tmp);
	}
	
	/**
	*查找标签
	*/

</script>
</head>
<body>
	<div id="main">
	<table id="htm_table"></table>
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<!-- <a href="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" style="vertical-align:middle;" title="删除织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a> -->
	        <span class="search_label">起始时间:</span><input id="startTime"  style="width:100px">
	        <span class="search_label">结束时间:</span><input id="endTime" style="width:100px">
	        <span class="search_label">客户端:</span>
			<select id="phoneCode" class="easyui-combobox" name="phoneCode" style="width:75px;">
				<option value="">所有</option>
		        <option value="0">IOS</option>
		        <option value="1">安卓</option>
	   		</select>
	   		<span class="search_label">有效状态:</span>
	   		<select id="valid" class="easyui-combobox" name="valid" style="width:75px;">
				<option value="" selected="selected">所有状态</option>
		        <option value="1">有效</option>
		        <option value="0">无效</option>
	   		</select>
	   		<span class="search_label">屏蔽状态:</span>
	   		<select id="shield" class="easyui-combobox" name="shield" style="width:75px;">
				<option value="" selected="selected">所有状态</option>
		        <option value="1">屏蔽</option>
		        <option value="0">非屏蔽</option>
	   		</select>
	   		<div style="display:inline-block; vertical-align:middle;">
	   			<a href="javascript:void(0)" id="searchBtn">查询</a>
			    <div id="mm" style="width:100px;">
				    <div id="searchTodayBtn">今日分享</div>
				    <div id="searchWeekBtn">本周分享</div>
				    <div id="searchLastWeekBtn">上周分享</div>
				    <div id="searchMonthBtn">本月分享</div>
				    <div id="searchLastMonthBtn">上月分享</div>
				</div>
	   		</div>
   		</div>
	</div>  
	
	<!-- 添加互动 -->
	<div id="htm_interact">
		<span id="interact_loading" style="margin:140px 0 0 220px; position:absolute; display:none;  ">加载中...</span>
		<form id="interact_form" action="./admin_interact/worldlevel_AddLevelWorld" method="post" class="none">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">已添加：</td>
						<td colspan="2">播放【<span id="clickSum_interact">0</span>】&nbsp;喜欢【<span id="likedSum_interact">0</span>】&nbsp;评论【<span id="commentSum_interact">0</span>】</td>
					</tr>
					<tr>
						<td class="leftTd">分类互动：</td>
						<td colspan="2">播放【<span id="unValid_clickSum_interact">0</span>】&nbsp;喜欢【<span id="unValid_likedSum_interact">0</span>】&nbsp;评论【<span id="unValid_commentSum_interact">0</span>】</td>
					</tr>
					<tr>
						<td class="leftTd">评论：</td>
						<td><input type="text" name="comments" id="comments_interact" style="width:205px;"/></td>
						<td class="rightTd"><div id="comments_interactTip" class="tipDIV">已选：<span id="selected_comment_count">0</span></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图id：</td>
						<td><input type="text" id="worldId_interact" name="world_id"  onchange="validateSubmitOnce=true;" readonly="readonly" /></td>
						<td class="rightTd"><div id="worldId_Tip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图等级：</td>
						<td><input style="width:204px" class="easyui-combobox" id="levelId" name="id"  onchange="validateSubmitOnce=true;" 
							data-options="valueField:'id',textField:'level_description',url:'./admin_interact/worldlevel_QueryoWorldLevel'"/></td>
						<td class="rightTd"><div id="levelId_Tip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">评论标签：</td>
						<td><input style="width:204px"  name="labelsStr" id="labelId" onchange="validateSubmitOnce=true;" /></td>
						<td><input  id="labelIdSearch" style="width:100px"/></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#interact_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_interact').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	
	<!-- 添加分类互动互动 -->
	<div id="htm_type_interact">
		<span id="type_interact_loading" style="margin:140px 0 0 220px; position:absolute; display:none;  ">加载中...</span>
		<form id="type_interact_form" action="./admin_interact/worldlevelList_addWorldlevelList" method="post" class="none">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">已添加：</td>
						<td colspan="2">播放【<span id="clickSum_type_interact">0</span>】&nbsp;喜欢【<span id="likedSum_type_interact">0</span>】&nbsp;评论【<span id="commentSum_type_interact">0</span>】</td>
					</tr>
					<tr>
						<td class="leftTd">分类互动：</td>
						<td colspan="2">播放【<span id="unValid_clickSum_type_interact">0</span>】&nbsp;喜欢【<span id="unValid_likedSum_type_interact">0</span>】&nbsp;评论【<span id="unValid_commentSum_type_interact">0</span>】</td>
					</tr>
					<tr>
						<td class="leftTd">评论：</td>
						<td><input type="text" name="comment_ids" id="comments_type_interact" style="width:205px;"/></td>
						<td class="rightTd"><div id="comments_type_interactTip" class="tipDIV">已选：<span id="selected_type_comment_count">0</span></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图id：</td>
						<td><input type="text" id="worldId_type_interact" name="world_id"  onchange="validateSubmitOnce=true;" readonly="readonly" /></td>
						<td class="rightTd"><div id="worldId_type_Tip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图等级：</td>
						<td><input style="width:204px" class="easyui-combobox" id="type_levelId" name="world_level_id"  onchange="validateSubmitOnce=true;" 
							data-options="valueField:'id',textField:'level_description',url:'./admin_interact/worldlevel_QueryoWorldLevel'"/></td>
						<td class="rightTd"><div id="type_levelId_Tip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">评论标签：</td>
						<td><input style="width:204px"  name="label_ids" id="type_labelId" onchange="validateSubmitOnce=true;" /></td>
						<td><input  id="type_labelIdSearch" style="width:100px"/></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#type_interact_form').submit();">添加</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_type_interact').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 给用户设定等级 -->
	<div id="htm_userLevel">
		<span id="userLevel_loading" style="margin:140px 0 0 220px; position:absolute; display:none; ">加载中...</span>
		<form id="userLevel_form" action="./admin_interact/userlevelList_AddUserlevel" method="post" class="none">
			<table class="htm_edit_table" width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">织图id：</td>
						<td><input type="text" id="userId_userLevel" name="userId"  onchange="validateSubmitOnce=true;" readonly="readonly" /></td>
						<td class="rightTd"><div id="userId_Tip" class="tipDIV"></div></td>
					</tr>
					
					<tr>
						<td class="leftTd">等级：</td>
						<td>
							<input style="width:204px" class="easyui-combobox" name="userLevelId" id="userLevelId_userLevel" onchange="validateSubmitOnce=true;"
								data-options="valueField:'id',textField:'level_description',url:'./admin_interact/userlevel_QueryUserLevel',required:true"/>
						</td>
						<td class="rightTd"><div id="userLevelId_Tip" class="tipDIV"></div></td>
					</tr>
									
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitUserLevelAddForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_userLevel').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">保存中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 添加活动 -->
	<div id="htm_activity">
		<span id="activity_loading" style="margin:140px 0 0 220px; position:absolute; display:none; ">加载中...</span>
		<form id="activity_form" action="./admin_op/op_saveSquarePushActivityWorld" method="post" class="none">
			<table class="htm_edit_table" width="280">
				<tbody>
					<tr>
						<td class="leftTd">活动：</td>
						<td>
							<input id="activityIds_activity" name="ids" type="text"  style="width:205px;"/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td>
							<input id="worldId_activity" name="worldId" type="text" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitActivityForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_activity').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">保存中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<div id="htm_type">
		<span id="type_loading" style="margin:60px 0 0 220px; position:absolute; display:none; ">加载中...</span>
		<form id="type_form" action="./admin_op/op_saveSquarePushActivityWorld" method="post" class="none">
			<table class="htm_edit_table" width="480">
				<tbody>
					<tr>
						<td class="leftTd">标签：</td>
						<td>
							<input id="typeId_type" name="typeId" type="text" style="width:205px;"  onchange="validateSubmitOnce=true;"/>
						</td>
						<td class="rightTd"><div id="typeId_typeTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td>
							<input id="worldId_type" name="worldId" type="text" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#type_form').submit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_type').window('close');">取消</a>
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
	
	<div id="comment_tb" style="padding:5px;height:auto" class="none">
		<a href="#htm_add_comment" onclick="addComment(0);" class="easyui-linkbutton" style="vertical-align:middle;" title="添加评论" plain="true" iconCls="icon-add" id="addCommentBtn">添加</a>
		<a href="javascript:void(0);" onclick="deleteComment();" class="easyui-linkbutton" style="vertical-align:middle;" title="删除评论" plain="true" iconCls="icon-cut">删除</a>
		<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
			<span class="search_label">评论标签：</span><input id="labelId_interact" name="labelId" style="width:120px;" />
			<span class="search_label">搜索标签：</span><input id="ss_searchLabel" style="width:100px;"></input>
			<input id="ss_comment" searcher="searchComment" class="easyui-searchbox" prompt="搜索评论" style="width:100px;"></input>
   		</div>
	</div>
	<div id="comment_tb2" style="padding:5px;height:auto" class="none">
		<a href="#htm_add_comment" onclick="addComment(0);" class="easyui-linkbutton" style="vertical-align:middle;" title="添加评论" plain="true" iconCls="icon-add" id="addCommentBtn2">添加</a>
		<a href="javascript:void(0);" onclick="deleteComment();" class="easyui-linkbutton" style="vertical-align:middle;" title="删除评论" plain="true" iconCls="icon-cut">删除</a>
		<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
			<span class="search_label">评论标签：</span><input id="labelId_type_interact" name="labelId" style="width:120px;" />
			<span class="search_label">搜索标签：</span><input id="ss_type_searchLabel" style="width:100px;"></input>
			<input id="ss_type_comment" searcher="searchTypeComment" class="easyui-searchbox" prompt="搜索评论" style="width:100px;"></input>
   		</div>
	</div>
	</div>
	
</body>
</html>