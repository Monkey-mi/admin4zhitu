var worldSettings;

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
		shield = $("#shield").combobox('getValue');
		user_level_id = $("#search_userLevelId").combobox('getValue');
		
		$.post("./admin_ztworld/ztworld_queryHTWorldList",{
			'maxId' : maxId,
			'startTime':startTime,
			'endTime':endTime,
			'phoneCode':phoneCode,
			'valid':valid,
			'shield':shield,
			'user_level_id':user_level_id
			}, function(result){
				if(result['result'] == 0) {
					$(".superb").remove();
					var worlds = result['rows'];
					var $superbBox = $('#superb-box');
					for(var i = 0; i < worlds.length; i++) {
			
						var setting = worldSettings;
						setting.worldId = worlds[i]['id'];
						setting.desc = worlds[i]['worldDesc'];
						setting.ver = worlds[i]['ver'];
			
						var $superb = $(
								'<div class="superb"><div class="zt-container">'
								+'<div class="zt-title">'
								+'	<img alt="">'
								+'</div>'
								+'<div class="zt-desc-hide" title="隐藏描述"></div>'
								+'<div class="zt-desc-show" title="显示描述"></div>'
								+'<div class="zt-desc">'
								+'	<div class="zt-desc-text"></div>'
								+'	<div class="zt-desc-bg"></div>'
								+'</div>'
								+'<div class="zt-loading">'
								+'<div class="zt-loading-bg"></div>'
								+'<div class="zt-loading-icon"></div>'
								+'<div><span>加载中...</span><span id="zt-progress">0</span><span>%</span></div>'
								+'</div>'
								+'</div></div>');
						if(((i+1)%3) != 0)
							$superb.addClass('superb-margin');
						$superbBox.append($superb);
						if(worldSettings.ver == 1) {
							$superb.find(".zt-title:eq(0)").show().children('img').attr('src', worlds[i]['titlePath']);
						}
						$superb.find(".zt-container:eq(0)").htszoomtour(setting);
			
					}
				}
			
			}, "json");
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


$(document).ready(function() {
	
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

var scale = 320 / 640,
width = 320,
height = 320,
radiu = parseInt(40 * scale),
config = {w:width,h:height,r:radiu,s:scale,key:"zhangjiaxin"};

worldSettings = {
	adminKey			: config.key,
	worldId 			: 11704, // 浏览的世界ID
	scale 				: config.s, // 缩放比例
	width 				: config.w, // 显示框宽度
	height 				: config.h, // 显示宽长度
	radius				: config.r,	//圆圈半径
	limit 				: 5, // 每页查询条数
	url 				: './admin_ztworld/ztworld_queryTitleChildWorldPage', // 数据获取地址
	loadMoreURL			: './admin_ztworld/ztworld_queryChildWorldPage', // 加载更多子世界地址
	inZoomfactor 		: 5, // 背景图渐变过程中放大的倍数
	inSpeedfactor 		: 650, // 渐变速度
	inImgdelayfactor 	: 3, // 渐变延迟时间
	outZoomfactor 		: 3, // 背景图渐变过程中放大的倍数
	outSpeedfactor 		: 650, // 渐变速度
	tagTopPadding		: 1.5*scale,
	outImgdelayfactor 	: 0,
	multiple			: true
};

search();

});


