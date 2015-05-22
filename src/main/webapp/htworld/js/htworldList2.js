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
						var worldId = worlds[i]['id'],
							ver = worlds[i]['ver'],
							worldDesc = worlds[i]['worldDesc'],
							titlePath = worlds[i]['titlePath'];
						var $superb = $('<div class="superb" />');
						if(((i+1)%3) != 0)
							$superb.addClass('superb-margin');
						$superbBox.append($superb);
						$superb.appendtour({
							'width':320,
							'worldId':worldId,
							'ver':ver,
							'worldDesc':worldDesc,
							'titlePath':titlePath,
							'url':'./admin_ztworld/ztworld_queryTitleChildWorldPage',
							'loadMoreURL':'./admin_ztworld/ztworld_queryChildWorldPage'
						});
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

search();

});


