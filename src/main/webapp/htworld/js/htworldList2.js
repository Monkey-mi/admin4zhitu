var maxId = 0,
	count = 0,
	index = 0,
	dataList = [],
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
	todayStr = baseTools.simpleFormatDate(today),
	showWorldAndInteractPage="page_htworld_htworldShow";
	
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
			shield = $("#shield").combobox('getValue'),
			user_level_id = $("#search_userLevelId").combobox('getValue'),
			rows = myQueryParams.rows;
		myQueryParams = {
			'maxId' : maxId,
			'startTime':startTime,
			'endTime':endTime,
			'phoneCode':phoneCode,
			'valid':valid,
			'shield':shield,
			'user_level_id':user_level_id
		};
		//$("#htm_table").datagrid("load",myQueryParams);
		loadData(1, rows);
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

function initWorldBoxWidth() {
	var length = parseInt($(window).width() / (250 + 10));
	$("#world-box").css("width",length * (250 + 10) + 20 + 'px');
	
}

function refreshPagination(total, pageSize, pageNumber)	{
	$("#pagination").pagination('refresh', {
	    total:total,
	    pageSize:pageSize,
	    pageNumber:pageNumber
	});
}


function loadData(pageNumber, pageSize) {
	scroll(0,0);
	$("#page-loading").show();
	myQueryParams['page'] = pageNumber;
	myQueryParams['rows'] = pageSize;
	$.post("./admin_ztworld/ztworld_queryHTWorldList",myQueryParams,
			function(result){
			if(result['result'] == 0) {
				dataList = [];
				$(".world-opt-wrap").remove();
				refreshPagination(result['total'], pageSize, pageNumber);
				var worlds = result['rows'];
				var $worldBox = $('#world-box');
				for(var i = 0; i < worlds.length; i++) {
					var world = worlds[i],
						worldId = world['id'],
						ver = world['ver'],
						worldDesc = world['worldDesc'],
						titlePath = world['titlePath'];
					dataList.push(world);
					var $worldOpt = $('<div class="world-opt-wrap"></div>');
					drawWorldOpt($worldOpt, worlds, i);
					$worldBox.append($worldOpt);
				}
				$("#page-loading").hide();
			}
		}, "json");
}

function drawWorldOpt($worldOpt, worlds, index) {
	var world = worlds[index],
		worldId = world['id'],
		ver = world['ver'],
		worldDesc = world['worldDesc'],
		titlePath = world['titlePath'];
	$worldOpt.attr("style", myRowStyler(index, world));
	var $authorInfo = $('<div class="world-author">'
			+'<span>'+authorAvatarColumn.formatter(world['authorAvatar'],world,index)+'</span>'
			+'<span class="world-author-name">'+getAuthorName(world['authorName'],world,index)
			+'<hr class="divider"></hr>'
			+'<div>织图ID:'+worldIdAndShowWorldColumn.formatter(world['worldId'],world,index) 
			+'<span class="world-count world-date">'+dateAddedFormatter(world['dateModified'], world, index)+'</span></span>'
			+'</div>'
			+'<div>用户ID:'+authorIdColumn.formatter(world['authorId'],world,index) 
			+'('+userLevelColumn.formatter(world['level_description'],world,index)+')</span>'
			+'</div>'
			+'</div>');
	var $worldInfo = $('<div class="world-info">'
		+'<div class="world-label">#'
		+ worldLabelColumn.formatter(world['worldLabel'],world,index)+'</div>'
		+'<div class="world-desc">'+worldDescColumn.formatter(world['worldDesc'],world,index)+'</div>'
		+'<div class="world-count-wrap">'
		+'<span class="glyphicon glyphicon-heart" aria-hidden="false">'
		+'<span class="world-count">'+likeCountColumn.formatter(world['likeCount'],world,index)+'</span></span>'
		+'<span class="glyphicon glyphicon-comment" aria-hidden="false">'
		+'<span class="world-count">'+commentCountColumn.formatter(world['commentCount'],world,index)+'</span></span>'
		+'<span class="glyphicon glyphicon-eye-open" aria-hidden="false">'
		+'<span class="world-count">'+world['clickCount']+'</span></span>'
		+'</div>'
		+'<hr class="divider"></hr>'
		+'</div>');
	var $world = $('<div class="world" />');
	
	var $opt = $('<div class="world-opt-head-wrap">'
			+ '<span class="world-opt-head">互动</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">精选</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">活动</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">最新</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">操作</span>'
			+'</div>'
			+ '<div class="world-opt-btn-wrap">'
			+ '<span class="world-opt-btn">'
			+ getTypeInteract(world['typeInteract'], world, index)
			+ '</span>'+ '<span>|</span>'
			+ '<span class="world-opt-btn">'
			+ getWorldType(world['worldType'], world, index)
			+ '</span>'+ '<span>|</span>'
			+ '<span class="world-opt-btn">'
			+ getActiveOperated(world['activeOperated'], world, index)
			+ '</span>'+ '<span>|</span>'
			+ '<span class="world-opt-btn">'
			+ getLatestValid(world['latestValid'], world, index)
			+ '</span>'+ '<span>|</span>'
			+ '<span class="world-opt-btn">'
			+ shieldColumn.formatter(world['shield'], world, index)
			+ '</span>'
			+ '</div>'
			+'<hr class="divider"></hr>'
			+ '<div><span class="world-opt-head">频道</span></div>'
			+ '<div class="world-channel">'+getChannelName(world['channelName'], world, index)+'</div>'
			);
	
	$worldOpt.addClass('world-margin');
	$worldOpt.append($authorInfo);
	$worldOpt.append($world);
	$worldOpt.append($worldInfo);
	$worldOpt.append($opt);
	$world.appendtour({
		'width':250,
		'worldId':worldId,
		'ver':ver,
		'worldDesc':worldDesc,
		'titlePath':titlePath,
		'url':'./admin_ztworld/ztworld_queryTitleChildWorldPage',
		'loadMoreURL':'./admin_ztworld/ztworld_queryChildWorldPage'
	});
}

function getAuthorName(value, row, index) {
	if(row.authorId != 0) {
		if(row.trust == 1) {
			return "<a title='移出信任列表.\n推荐人:"
				+row.trustOperatorName+"\n最后修改时间:"
				+row.trustModifyDate+"' class='passInfo pointer' href='javascript:removeTrust(\"" 
				+ row.authorId + "\",\"" + row.worldId + "\",\""+ row.latestValid + "\",\"" + index + "\")'>"
				+value
				+ "<sup><span style='border: solid 1px red;webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;-webkit-box-shadow: #666 0px 0px 10px;-moz-box-shadow: #666 0px 0px 10px;box-shadow: #666 0px 0px 10px;'>"
				+row.trustOperatorId+"</span></sup></a>";
		}else if(row.trustOperatorId == 0){
			return "<a title='添加到信任列表' class='updateInfo pointer' href='javascript:addTrust(\"" + row.authorId + "\",\""+row.worldId
				+ "\",\"" + row.worldId + "\",\"" + row.latestValid + "\",\"" + index + "\")'>"+value+"</a>";
		}
		return "<a title='移出信任列表.\n删除信任的人:"
				+row.trustOperatorName+"\n最后修改时间:"
				+row.trustModifyDate+"' class='updateInfo pointer' href='javascript:addTrust(\"" 
				+ row.authorId + "\",\"" + row.worldId + "\",\"" + row.latestValid + "\",\"" + index + "\")'>"
				+value
				+ "<sup><span style='border: solid 1px red;webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;-webkit-box-shadow: #666 0px 0px 10px;-moz-box-shadow: #666 0px 0px 10px;box-shadow: #666 0px 0px 10px;'>"
				+row.trustOperatorId+"</span></sup></a>";
	} else if(baseTools.isNULL(value)) {
		return "织图用户";
	}
	
}

function getTypeInteract(value, row, index) {
	img = "./common/images/edit_add.png";
	return "<a title='添加分类互动' class='updateInfo' href='javascript:typeInteract(\"" + row[worldKey] + "\",\"" + index + "\")'>"+"<img src=\""+img+"\"/></a>";
}

function getChannelName(value, row, index) {
	if(value == "NO_EXIST" || value=="") {
		img = "./common/images/edit_add.png";
		return "<img title='添加到频道' class='htm_column_img pointer'  src='" + img + "' onclick='saveToChannelWorld(\""+row.worldId+"\",\""+index +"\")'/>";
	}
	return value;
}

function getWorldType(value, row, index) {
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
}

function getActiveOperated(value, row, index) {
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

function getLatestValid(value, row, index) {
	if(value >= 1) {
		img = "./common/images/undo.png";
		return "<img title='从最新移除' class='htm_column_img pointer' onclick='javascript:removeLatestValid(\""+ row[worldKey] + "\",\"" + index + "\",\"" + 'true' + "\")' src='" + img + "'/>";
	} else if(row.valid == 0 || row.shield == 1) {
		return '';
	}
	img = "./common/images/edit_add.png";
	return "<img title='添加到最新' class='htm_column_img pointer' onclick='javascript:addLatestValid(\""+ row[worldKey] + "\",\"" + index + "\",\"" + 'true' + "\")' src='" + img + "'/>";
}
	
/**
 * 初始化活动添加窗口
 */
function initActivityAddWindow(worldId, activityrecd, index) {
	currentIndex = index;
	$("#worldId_activity").val(worldId);
	$("#htm_activity").window('open');
	if(activityrecd == -1 || activityrecd == '') {
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
					updateValue(currentIndex, 'activeOperated',1);
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
		'worldId':worldId
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
					userId = $("#userId_type").val(),
					worldType = $typeId.combobox('getText'),
					typeId = $typeId.combobox('getValue');
					channelId = $("#channelId").combobox('getValue');
				
				$("#htm_type .opt_btn").hide();
				$("#htm_type .loading").show();
	//			if(typeId){
					$.post("./admin_interact/typeOptionWorld_addTypeOptionWorld",{
						'worldId':worldId,
						'userId':userId
						},function(result){
							formSubmitOnce = true;
							$("#htm_type .opt_btn").show();
							$("#htm_type .loading").hide();
							if(result['result'] == 0) {
								updateValues(index,['squarerecd','worldType'],['1','旅行']);
								
							} else {
								$.messager.alert('失败提示',result['msg']);  //提示失败信息
	//							return false;
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
	//				return false;
	//			}
				
				if(channelId){
					//该织图进入频道
					$.post("./admin_op/channel_saveChannelWorld",{
						'world.channelId':channelId,
						'world.worldId'  :worldId,
						'world.valid'	 :1,
						'world.notified' :0
					},function(result){
						if(result['result'] == 0){
						//	updateValue(index,'channelName',channelName);
						}else{
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
						$('#htm_type').window('close');  //关闭添加窗口
	//					return false;
					},"json");
				}
				/*
				if(channelId && typeId){
					$.post("./admin_op/cwSchedula_addChannelWorldSchedula",{
						'worldId':worldId,
						'channelId':channelId,
						'valid':0,
						'finish':0
					},function(result){
						if(result['result'] == 0){
							//	updateValue(index,'channelName',channelName);
						}else{
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
						$('#htm_type').window('close');  //关闭添加窗口
	//					return false;
					},"json");
				}*/
				
				return false;
				
			}
		}
	});
	
	$("#typeId_type")
	.formValidator({empty:false,onshow:"请选择分类",onfocus:"设置分类",oncorrect:"设置成功"});;
	
}

var htmTableTitle = "分享列表维护", //表格标题
	htmTableWidth = 1380,
	htmTablePageList = [10,20,50,100],
	worldKey = 'id',
	loadDataURL = "./admin_ztworld/ztworld_queryHTWorldList", //数据装载请求地址
	deleteURI = "./admin_ztworld/ztworld_deleteWorld?ids="; //删除请求地址
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'maxId' : maxId,
			'startTime':todayStr,
			'endTime':todayStr,
			'rows':30
		},
		loadData(1, 30);
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
	onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		
		initWorldBoxWidth();
		
		$("#htm_channel").window({
			title : '频道织图添加',
			modal : true,
			width : 420,
			height : 150,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function(){
				$("#channelId").combobox('clear');
				$("#worldId_channel").val('');
				$("#rowIndex").val('');
			}
		});
		
		$("#labelId_interact").combotree({
			url:'./admin_interact/comment_queryLabelTree?hasTotal=true',
			onSelect:function(rec) {
				loadComment(rec.id,rec.labelName);
				$("#labelId_interact").combotree('clear');
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
				$("#labelId_type_interact").combotree('clear');
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
				$('#labelId_interact').combotree('clear');
				$('#ss_searchLabel').combobox('clear');
				$('#comments_interact').combogrid('clear');
			}
		});
		
		$('#htm_type_interact').window({
			modal : true,
			width : 500,
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
				$('#labelId_type_interact').combotree('clear');
				$('#ss_type_searchLabel').combobox('clear');
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
			url:'./admin_ztworld/label_queryLabelForCombobox'
		});
		
		
		$('#htm_activity').window({
			modal : true,
			width : 340,
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
			width : 530,
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
				$("#userId_type").val('');
				$("#channelId").combobox('clear');
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
			url:'./admin_interact/comment_queryAllLabel'
		});
		
		$('#type_labelIdSearch').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_interact/comment_queryAllLabel'
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
		    multiple : false,
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
		
		$("#pagination").pagination({
			pageList: [30,50,100,10],
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					maxId = 0;
					myQueryParams.maxId = maxId;
				}
			},
			onRefresh : function(pageNumber, pageSize) {
				loadData(pageNumber, pageSize);
			},
			onSelectPage : function(pageNumber, pageSize) {
				loadData(pageNumber, pageSize);
			},
			onChangePageSize : function(pageSize) {
				loadData(1, pageSize);
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	
	/**
	 * 添加信任
	 * 
	 * @param userId
	 * @param index
	 */
	function addTrust(userId, worldId, latestValid, index) {
		$("#htm_table").datagrid('loading');
		$.post("./admin_user/user_updateTrust",{
			'userId':userId,
			'trust':1
			},function(result){
				if(result['result'] == 0) {
					if(latestValid != 0) {
						updateValue(index,'trust',1);
					} else {
						updateValues(index, ['trust','latestValid'], [1,1]);
						addLatestValid(worldId,index);//添加为最新
					}
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
				//$("#htm_table").datagrid('reload');
				//$("#htm_table").datagrid('loaded');
			},"json");
	}

	/**
	 * 移除信任
	 * 
	 * @param userId
	 * @param index
	 */
	function removeTrust(userId, worldId, latestValid, index) {
		$("#htm_table").datagrid('loading');
		$.post("./admin_user/user_updateTrust",{
			'userId':userId,
			'trust':0
			},function(result){
				if(result['result'] == 0) {
					if(latestValid > 0) {
						updateValues(index,['trust','latestValid'],[0,0]);
						removeLatestValid(worldId, index);
					} else {
						updateValue(index,'trust',0);
					}
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
				//$("#htm_table").datagrid('reload');
				//$("#htm_table").datagrid('loaded');
			},"json");
	}
	
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
	*worldId显示界面初始化
	*/
	function showWorldAndInteract(uri){
		$.fancybox({
			'margin'			: 20,
			'width'				: '100%',
			'height'			: '100%',
			'autoScale'			: true,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'href'				: uri,
			onClosed			: function(){
//				$("#htm_table").datagrid('reload');
			}
		});
	}
	
	/**
	* worl显示页面操作之后，更新分享列表数据
	*/
	function updateSuperb(index,value){
		if(value == 1){
			updateValue(index,'squarerecd',1);
			updateValue(index,'worldType','');
		}else{
			updateValue(index,'worldType','');
		}
	}
	function updateLast(index,value){
		updateValue(index,'latestValid',value);
	}
	function updateTrust(index,value){
		var rows = $("htm_table").datagrid('getRows');
		var authorname = rows[index]['authorName'];
		updateValue(index,'trust',value);
		updateValue(index,'authorName',authorname);
	}
	function updateInteracted(index){
		updateValue(index, "interacted", 1);
	}
	
	function saveToChannelWorld(worldId,index){
		$("#rowIndex").val(index);
		$("#worldId_channel").val(worldId);
		$('#htm_channel .opt_btn').hide();
		$('#htm_channel .loading').show();
		$("#htm_channel").window('open');
		$('#htm_channel .opt_btn').show();
		$('#htm_channel .loading').hide();
		
	}
	function saveChannelWorldSubmit(){
		var index = $("#rowIndex").val();
		var channelName = $("#channelId").combobox('getText');
		var channelId = $("#channelId").combobox('getValue');
		var worldId = $("#worldId_channel").val();
		//成为频道用户
		$.post("./admin_op/chuser_addChannelUserByWorldId",{
			'worldId':worldId,
			'channelId':channelId
		},function(result){
			return false;
		},"json");
		//该织图进入频道
		$.post("./admin_op/channel_saveChannelWorld",{
			'world.channelId':channelId,
			'world.worldId'  :worldId,
			'world.valid'	 :0,
			'world.notified' :0
		},function(result){
			if(result['result'] == 0){
				updateValue(index,'channelName',channelName);
			}else{
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			$("#htm_channel").window('close');
			return false;
		},"json");
		
	}
	
	function updateValue(index, key, value) {
		var $worldOpt = $(".world-opt-wrap:eq("+index+")");
		$worldOpt.empty();
		dataList[index][key] = value;
		drawWorldOpt($worldOpt, dataList, index);
	}

	function updateValues(index, keys, values) {
		var $worldOpt = $(".world-opt-wrap:eq("+index+")");
		$worldOpt.empty();
		for(var i = 0; i < keys.length; i++) {
			dataList[index][keys[i]] = values[i];
		}
		drawWorldOpt($worldOpt, dataList, index);
	}
	
	function searchByShortLink() {
		maxId = 0;
		var shortLink = $('#ss_shortLink').searchbox('getValue');
		if(shortLink == "") {
			search();
			return
		}
		var rows = myQueryParams.rows;
		myQueryParams = {
			'shortLink' : shortLink
		};
		loadData(1, myQueryParams.rows);
	}

	function searchByAuthorName() {
		maxId = 0;
		var authorName = $('#ss_authorName').searchbox('getValue');
		if(authorName == "") {
			search();
			return
		}
		var rows = myQueryParams.rows;
		myQueryParams = {
			'authorName' : authorName
		};
		loadData(1, rows);
	}
