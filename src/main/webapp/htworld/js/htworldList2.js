// 定义查询织图标记位，由于织图更新快，在翻页查询时容易乱序，然后用maxId设定一个标记位，使翻页不乱序
var maxId = 0,
	count = 0;

// 定义织图集合
var dataList = [];

// 定义当前所操作织图，此为织图在集合中的脚标
var	currentIndex = 0,
	inValidWorld = 'background-color:#ffe3e3',
	interactedWorld = 'background-color:#e3fbff',
	interactedInvalidWorld = 'background-color:#feeeae',
	mouthCount = 0,
	firstMonthDate = new Date();
	clean = function(){
		count = 0;
		mouthCount = 0;
		firstMonthDate = new Date();
	},
	today = new Date(),
	todayStr = baseTools.simpleFormatDate(today);
	
// 时间比较方法
function timeCompare(date) {
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
	};
	
/**
 * 查询操作，查询整个区域的
 */
var	search = function() {
		maxId = 0;
		// 获取起始与结束日期
		var startTime = $('#startTime').datetimebox('getValue');
		var	endTime = $('#endTime').datetimebox('getValue');
		
		// 判断查询的起始时间与管理员时间跨度是否有冲突，若无冲突则进行查询 
		var adminUserDateSpanIdValue = $('#search_adminUserDateSpan').combogrid('getValue');
		// 若管理员时间跨度被选择
		if ( adminUserDateSpanIdValue != "" ) {
			// 查询起始时间与结束时间不是同一天，则提示冲突，不进行查询
			if ( startTime != endTime ) {
				$.messager.alert('错误提示',"你选择了管理员时间跨度查询，但是起始时间与结束时间不是同一天，请重新选择！");  // 提示冲突信息
				return;
			} else {
				// 若在同一天，则选取管理员时间跨度起始拼接到startTime上，结束拼接到endTime上
				var adminUserDateSpanDategrid = $('#search_adminUserDateSpan').combogrid("grid");
				adminUserDateSpanDategrid.datagrid("selectRecord", adminUserDateSpanIdValue);
				var adminUserDateSpanRow = adminUserDateSpanDategrid.datagrid("getSelected");
				startTime = startTime + " " + adminUserDateSpanRow.startTime;
				endTime = endTime + " " +adminUserDateSpanRow.endTime;
			}
		} else {
			// 若管理员时间跨度没有被选择，则在查询前后拼接时间，endTime拼接时间到当天末
			startTime = startTime + " " + "00:00:00";
			endTime = endTime + " " + "23:59:59";
		}
		
		var	phoneCode = $('#phoneCode').combobox('getValue');
		var	shield = $("#shield").combobox('getValue');
		var	user_level_id = $("#search_userLevelId").combobox('getValue');
		var isZombie = $("#isZombie").combobox('getValue');
		
		var	rows = myQueryParams.rows;
		myQueryParams = {
				'maxId' : maxId,
				'startTime':startTime,
				'endTime':endTime,
				'phoneCode':phoneCode,
				'shield':shield,
				'user_level_id':user_level_id,
				'isZombie':isZombie
		};
		loadData(1, rows);
		
	};

/**
 * 初始化整个织图集合所占区域宽度
 * 
 * @author zhutianjie
 * @modify zhangbo	2015-12-01
 */
function initWorldBoxWidth() {
	var length = parseInt($(window).width() / (250 + 10));
	$("#world-box").css("width",length * (250 + 10) + 20 + 'px');
}

/**
 * 刷新分页组件
 * 
 * @param total			总数
 * @param pageSize		每页数量
 * @param pageNumber	当前页数
 * @author zhutianjie
 * @modify zhangbo	2015-12-01
 */
function refreshPagination(total, pageSize, pageNumber)	{
	$("#pagination").pagination('refresh', {
	    total:total,
	    pageSize:pageSize,
	    pageNumber:pageNumber
	});
}

/**
 * 查询织图数据
 * 
 * @param pageSize		每页数量
 * @param pageNumber	当前页数
 * @author zhutianjie
 * @modify zhangbo	2015-12-01
 */
function loadData(pageNumber, pageSize) {
	scroll(0,0);
	$("#page-loading").show();
	myQueryParams['page'] = pageNumber;
	myQueryParams['rows'] = pageSize;
	myQueryParams['valid'] = $("#valid").combobox('getValue');
	
	$.post("./admin_ztworld/ztworld_queryWorldMasonry", myQueryParams, function(result){
		if(result['result'] == 0) {
			if(result.maxId > maxId) {
				maxId = result.maxId;
				myQueryParams.maxId = maxId;
			}
			if(result.activityId != 'undefined') {
				activityId = result.activityId;
			} else {
				activityId = 0;
			}
			dataList = [];
			$(".world-opt-wrap").remove();
			refreshPagination(result['total'], pageSize, pageNumber);
			var worlds = result['rows'];
			var $worldBox = $('#world-box');
			for(var i = 0; i < worlds.length; i++) {
				var world = worlds[i];
				dataList.push(world);
				var $worldOpt = $('<div class="world-opt-wrap"></div>');
				drawWorldOpt($worldOpt, world, i);
				$worldBox.append($worldOpt);
			}
			$("#page-loading").hide();
		} else {
			$.messager.alert('失败提示',result['msg']);
		}
	}, "json");
}

/**
 * 设置织图背景色
 * 
 * @param index	织图所在集合的脚标
 * @param world	每个织图信息
 * @author zhangbo	2015-12-01
 */
function setWorldStyle(index,world){
	var	valid = $("#valid").combobox('getValue');
	if(valid == 0) {
		if(world.interacted) {
			return interactedInvalidWorld;
		} else {
			return inValidWorld;
		}
	} else if(world.interacted) {
		return interactedWorld;
	} else {
		return null;
	}
};

/**
 * 绘制织图区域
 * 
 * @param $worldOpt		每个织图最上层父对象，织图展现所有信息的最外层
 * @param world			每个织图信息
 * @param index			织图所在集合的脚标
 * @author zhutianjie
 * @modify zhangbo	2015-12-01
 */
function drawWorldOpt($worldOpt, world, index) {
	var	worldId = world['id'];
	var	ver = world['ver'];
	var	worldDesc = world['worldDesc'];
	var	titlePath = world['titlePath'];
	$worldOpt.attr("style", setWorldStyle(index, world));
	var $authorInfo = $("<div class='world-author'>"
			+ "<span>" 
			+ authorAvatarColumn.formatter(world['authorAvatar'],world,index) 
			+ "</span>"
			+ "<span class='world-author-name'>" 
			+ getAuthorName(world['authorName'],world,index) 
			+ "</span>"
			+ "<span>" 
			+ phoneCodeColumn.formatter(world['phoneCode'],world,index) 
			+ "</span>"
			+ "<hr class='divider'></hr>"
			+ "<div>织图ID:" 
			+ getWorldId(world.id,world,index)
			+ "<span class='world-count world-date'>" 
			+ baseTools.parseDate(world.dateAdded).format("yy/MM/dd hh:mm")
			+ "</span>"
			+ "</div>"
			+ "<div>用户ID:" 
			+ authorIdColumn.formatter(world['authorId'],world,index) 
			+ "(" + userLevelColumn.formatter(world['level_description'],world,index) + ")"
			+ "</div>"
			+ "</div>");
	var $worldInfo = $("<div class='world-info'>"
		+ "<div class='world-label'>#"
		+ world['worldLabel'] 
		+ "</div>"
		+ "<div class='world-desc'>" 
		+ getWorldDesc(world['worldDesc'],world,index) 
		+ "</div>"
		+ "<div class='world-count-wrap'>"
		+ "<span class='glyphicon glyphicon-heart' aria-hidden='false'>"
		+ "<span class='world-count'>" 
		+ likeCountColumn.formatter(world['likeCount'],world,index) 
		+ "</span>" 
		+ "</span>"
		+ "<span class='glyphicon glyphicon-comment' aria-hidden='false'>"
		+ "<span class='world-count'>"
		+ commentCountColumn.formatter(world['commentCount'],world,index)
		+ "</span>" 
		+ "</span>"
		+ "<span class='glyphicon glyphicon-eye-open' aria-hidden='false'>"
		+ "<span class='world-count'>"
		+ world['clickCount']
		+ "</span>" 
		+ "</span>"
		+ "</div>"
		+ "<hr class='divider'></hr>"
		+ "</div>");
	var $world = $("<div class='world' />");
	
	$worldOpt.addClass("world-margin");
	$worldOpt.append($authorInfo);
	$worldOpt.append($world);
	$worldOpt.append($worldInfo);
	drawOptArea($worldOpt, world, index);
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

/**
 * 绘制操作区域
 * 
 * @param $worldOpt		每个织图最上层父对象，织图展现所有信息的最外层
 * @param world			每个织图信息
 * @param index			织图所在集合的脚标
 * @author zhangbo	2015-12-01
 */
function drawOptArea($worldOpt, world, index) {
	// 声明操作区域对象 
	var $opt = $('<div class="world-opt-area"></div>');
	
	// 添加第一行操作title
	var $opt1LineTitle = $('<div class="world-opt-head-wrap">'
			+ '<span class="world-opt-head">活动</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">最新</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">操作</span>'
			+ "<span>|</span>"
			+ "<span class='world-opt-head'>精备选</span>"
			+'</div>');
	
	// 添加第一行操作title对应的按钮
	var $opt1LineBtn = $('<div class="world-opt-btn-wrap">'
			+ '<span class="world-opt-btn">'
			+ getActiveOperated(world['activeOperated'], world, index)
			+ '</span>'+ '<span>|</span>'
			+ '<span class="world-opt-btn">'
			+ getLatestValid(world['latestValid'], world, index)
			+ '</span>'+ '<span>|</span>'
			+ '<span class="world-opt-btn">'
			+ getShield(world, index)
			+ '</span>'
			+ "<span>|</span>"
			+ "<span class='world-opt-btn'>"
			+ getSuperbReserve(world, index)
			+ "</span>"
			+ '</div>');
	
	// 添加一二行分隔线
	var $optDivider1To2 = $('<hr class="divider"></hr>');
	
	// 添加第二行操作title
	var $opt2LineTitle = $('<div class="world-opt-head-wrap">'
			+ '<span class="world-opt-head">频道</span>'
			+ '</div>');
	
	// 添加第二行操作title对应的按钮
	var $opt2LineBtn = $('<div class="world-channel">'
			+ getChannelName(world['channelName'], world, index)
			+ '</div>');
	
	// 添加二三行分隔线
	var $optDivider2To3 = $('<hr class="divider"></hr>');
	
	// 添加第三行操作title
	var $opt3LineTitle = $('<div class="world-opt-head-wrap">'
			+ '<span class="world-opt-head">评论签</span>'
			+ '</div>');
	// 向第三行按钮中添加元素
	$opt3LineBtn = $('<div class="world-channel"></div>');
	drawWorldCommentLabelBtn($opt3LineBtn, world, index);
	
	// 添加三四行分隔线
	var $optDivider3To4 = $('<hr class="divider"></hr>');
	
	// 添加第三行操作title
	var $opt4LineTitle = $('<div class="world-opt-head-wrap">'
			+ '<span class="world-opt-head">位置</span>'
			+ '</div>');
	
	// 向第四行按钮中添加元素
	// 地理位置信息，省份+城市构成
	var $opt4LineBtn = $("<div class='world-channel'>"
			+ "<span>"
			+ world.province + world.city
			+ "</span>"
			+ "</div>");
	
	$opt.append($opt1LineTitle);
	$opt.append($opt1LineBtn);
	$opt.append($optDivider1To2);
	$opt.append($opt2LineTitle);
	$opt.append($opt2LineBtn);
	$opt.append($optDivider2To3);
	$opt.append($opt3LineTitle);
	$opt.append($opt3LineBtn);
	$opt.append($optDivider3To4);
	$opt.append($opt4LineTitle);
	$opt.append($opt4LineBtn);
	
	$worldOpt.append($opt);
}

/**
 * 获取用户名称返回格式，为可点击链接
 * 
 * @param authorName	用户名称
 * @param world			每个织图信息
 * @param index			织图所在集合的脚标
 * @author zhangbo	2015-12-01
 */
function getAuthorName(authorName, world, index) {
	if(world.authorId != 0) {
		authorName = authorName.substr(0, 15);
		return "<a class='updateInfo pointer'>"
				+authorName
				+ "<sup><span style='border: solid 1px red;webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;-webkit-box-shadow: #666 0px 0px 10px;-moz-box-shadow: #666 0px 0px 10px;box-shadow: #666 0px 0px 10px;'>"
				+world.trustOperatorId+"</span></sup></a>";
	} else if(baseTools.isNULL(authorName)) {
		return "织图用户";
	}
}

/**
 * 获取织图id返回格式，为可点击链接，点击触发打开织图互动页面
 * 
 * @param worldId	织图id
 * @param world		每个织图对象信息
 * @param index		织图所在集合中的脚标
 * @author zhangbo	2015-11-30
 */
function getWorldId(worldId,world,index) {
	var wLabel = world['worldLabel'];
	if (world['worldLabel'].indexOf("'") > 0 ) {
		wLabel = wLabel.replace("'",";;qqq;;");
	}
	return "<a title='添加互动' class='updateInfo' href='javascript:commonTools.openWorldInteractPage("+worldId+","+world.authorId+",\""+world.worldURL+"\","+index+",\""+wLabel+"\")'>"+worldId+"</a>";
};

/**
 * 获取织图描述返回格式
 * 
 * @param worldDesc	织图描述
 * @param world		每个织图对象信息
 * @param index		织图所在集合中的脚标
 * @author zhangbo	2015-12-01
 */
function getWorldDesc(worldDesc,world,index) {
	var preValue = worldDesc;
	var arr = [];
	var worldId = world.id;
	var regDemo = new RegExp("@.+? ","g");
	
	if(worldDesc != null && worldDesc != '') {
		//如果评论中有被@的人，则需要去获取被@人的数据
		if(regDemo.exec(worldDesc) != null){
			$.ajax({ 
				  type: 'POST', 
				  url: "./admin_ztworld/ztworld_queryCommentAt", 
				  data: {worldId:worldId}, 
				  success: function(data){
						if(data.result == 0){
							var result = data['obj'];
							arr = 	worldDesc.match(regDemo);
							if(result.length != 0){ //防止从微博中复制过来但又检测到有@格式,且数据库中没有此被@人的数据。
								for(var i = 0; i < arr.length; i++){
									worldDesc = worldDesc.replace(arr[i],"<a class='updateInfo' href='javascript:commonTools.openUserInfoPage(\""+result[i].atId+"\")'>" + arr[i] + "</a>");
								}											
							}
						}else{
							$.messager.alert("数据返回错误");
						}
					}, 
				  dataType: "json", 
				  async:false 
				}); 
		}
		return "<div title=" + worldDesc + " class='viewInfo easyui-tooltip'>" + worldDesc + "</div>";
	}
	return '';
};

/**
 * 查询织图互动过的评论标签，若是没有互动过，则查询根据织图描述或织图标签得到的评论标签
 * 
 * @param $worldOpt		被添加内容的jQuery对象，一般为空的dom对象
 * @param world			每个织图信息
 * @param index			织图所在集合的脚标
 * @author zhangbo	2015-12-01
 */
function drawWorldCommentLabelBtn($WorldOptBtn, world, index) {
	if(world.valid == 0 || world.shield == 1) {
		return "";
	}
	
	var $labelDIV = $('<div style="display: inline-block;"></div>');
	var ret = '';
	$.post("./admin_interact/channelWorldLabel_queryWorldLabelList",{'worldId':world.id},
		function(result){
			if(result['result'] == 0){
				var data = result.labelInfo;
				if (!result.interact) {
					for (var i=0; i<data.length; i++) {
						var labelId = data[i].id;
						var labelName = data[i].labelName;
						ret += '<span id="commentLabel-'+world.id+'-'+labelId+'" class="label-btn-unclc-bg" data-label="'+labelId+'">'
								+ '<a href="javascript:void(0)" style="text-decoration:none;" onclick="putWorldCommentLabelId(' 
								+ world.id + ',' + labelId + ',' + index + ')">#' + labelName + '</a>'
								+ '<input type="hidden" value="' + labelId + '"></input>'
								+ '</span>&nbsp;';
						if (i != 0 && i%3 == 0) {
							ret += '<br>';
						}
					}
					ret += '<a href="javascript:void(0)"><img src="./common/images/ok.png" onclick="submitAddWorldCommentLabel('+ world.id + ', ' + index + ')"></img></a>'
					+ '<input id="worldCommentLabel-' + world.id + '" type="hidden"></input>';
				} else {
					for (var i=0; i<data.length; i++) {
						ret += '<span>#' + data[i].labelName + '</span>&nbsp;';
					}
				}
			}
			$labelDIV.append($(ret))
			$WorldOptBtn.append($labelDIV);
		},"json");
}

/**
 * 点击标签触发：将点击的标签ID放入指定的存储位置
 * 
 * @param worldId	织图id
 * @param labelId	织图标签id
 * @param index		织图所在集合的脚标
 * @author zhangbo	2015-12-01
 */
function putWorldCommentLabelId(worldId, labelId, index) {
	var bgColor = $("#commentLabel-" + worldId + "-" + labelId).attr("class");
	if (bgColor == "label-btn-onclc-bg") {
		$("#commentLabel-" + worldId + "-" + labelId).attr("class", "label-btn-unclc-bg");
	} else if (bgColor == "label-btn-unclc-bg") {
		$("#commentLabel-" + worldId + "-" + labelId).attr("class", "label-btn-onclc-bg");
	}
	
	var spanNods = $(".world-opt-area:eq("+index+") span.label-btn-onclc-bg");
	var ids = [];
	for (var i=0; i<spanNods.length; i++) {
		ids.push($(spanNods[i]).children('input').val());
	}
	$("#worldCommentLabel-" + worldId).val(ids.toString());
}

/**
 * 提交添加织图标签
 * 
 * @param worldId	织图id
 * @param index		织图所在集合的脚标
 * @author zhangbo	2015-12-01
 */
function submitAddWorldCommentLabel(worldId, index){
	$.post("./admin_interact/channelWorldLabel_insertWorldLabel",{
		'worldId'	: worldId,
		'labelIds'	: $("#worldCommentLabel-" + worldId).val()
	},function(result){
		if(result['result'] == 0){
			updateValue(index);
		}else{
			$.messager.alert('失败提示',result['msg']);
		}
	},"json");
};

/**
 * 得到频道名称返回值，若不存在则返回按钮，若存在返回可点击链接
 * 
 * @param channelName	织图所在频道名称值
 * @param world			每个织图信息
 * @param index			当前织图在集合中的脚标
 * @author zhangbo	2015-11-16
 */
function getChannelName(channelName, world, index) {
	if(channelName=="") {
		return "<img title='添加到频道' class='htm_column_img pointer'  src='./common/images/edit_add.png' onclick='commonTools.openWorldAddToChannelPage(" + world.id + ")'/>";
	} else {
		return "<a onclick='commonTools.openWorldAddToChannelPage(" + world.id + ")'>" + channelName + "</a>";
	}
};

/**
 * 得到精选备选按钮
 * 
 * @param world	每个织图信息
 * @param index	当前织图在集合中的脚标
 * @author zhangbo	2015-11-16
 */
function getSuperbReserve(world, index) {
	// 若已经为精选备选，则直接返回无操作的图片
	if ( world.squarerecd == 1 ) {
		return "<img title='已经为精选备选' class='htm_column_img pointer' src='./common/images/ok.png'/>";
	} else {
		var rtn = "<img title='点击成为精选备选' class='htm_column_img pointer' " +
				"src='./common/images/edit_add.png' onclick='toBeSuperbReserve("+world.id+","+world.authorId+","+index+")'/>";
		return rtn;
	}
};

/**
 * 成为精选备选
 * 
 * @param worldId	织图id
 * @param userId	用户id
 * @param index		当前织图在集合中的脚标
 * @author zhangbo	2015-11-16
 */
function toBeSuperbReserve(worldId, userId, index){
	$.post("./admin_interact/typeOptionWorld_addTypeOptionWorld",{
		worldId: worldId,
		userId: userId
	},function(result){
		if(result['result'] == 0) {
			updateSuperb(index,1);
		} else {
			$.messager.alert("温馨提示",result.msg);  //提示添加信息失败
		}
	},'json');
	
};

/**
 * 获取活动按钮
 * 
 * @param worldId	织图id
 * @param userId	用户id
 * @param index		当前织图在集合中的脚标
 * @author zhangbo	2015-12-01
 */
function getActiveOperated(value, row, index) {
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

/**
 * 得到最新织图按钮
 * 
 * @param value
 * @param row
 * @param index
 * @author zhangbo 2015-12-01
 */
function getLatestValid(value, row, index) {
	// TODO 这里要做整改，现在先都返回空
	return "";
//	if(value >= 1) {
//		img = "./common/images/undo.png";
//		return "<img title='从最新移除' class='htm_column_img pointer' onclick='javascript:removeLatestValid(\""+ row[worldKey] + "\",\"" + index + "\",\"" + 'true' + "\")' src='" + img + "'/>";
//	}
//	img = "./common/images/edit_add.png";
//	return "<img title='添加到最新' class='htm_column_img pointer' onclick='javascript:addLatestValid(\""+ row[worldKey] + "\",\"" + index + "\",\"" + 'true' + "\")' src='" + img + "'/>";
}

/**
 * 得到屏蔽按钮
 * 
 * @param world	每个织图信息
 * @param index	当前织图在集合中的脚标
 * @author zhangbo	2015-12-01
 */
function getShield(world, index) {
	if(world.shield == 1) {
		return "<img class='htm_column_img pointer' src='./common/images/undo.png'/>";
	} else {
		return "<a title='点击屏蔽织图' class='updateInfo' href='javascript:shieldWorld("+ world.id + "," + index + ")'>" + "屏蔽"+ "</a>";
	}
}

/**
 * 屏蔽织图
 */
function shieldWorld(worldId,index) {
	$.post("./admin_ztworld/ztworld_shieldWorld",{'worldId':worldId},function(result){
		if(result['result'] == 0) {
			updateValue(index,'shield',1);
		} else {
			$.messager.alert('失败提示',result['msg']);  //提示失败信息
		}
	},"json");
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

function addLatestValid(worldId, index) {
	// TODO 此方法可能不再需要，最新织图这个功能可能要做整改
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
	// TODO 此方法可能不再需要，最新织图这个功能可能要做整改
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

var htmTableTitle = "分享列表维护", //表格标题
	htmTablePageList = [10,20,50,100],
	worldKey = 'id',
	loadDataURL = "./admin_ztworld/ztworld_queryHTWorldList", //数据装载请求地址
	deleteURI = "./admin_ztworld/ztworld_deleteWorld?ids="; //删除请求地址

	init = function() {
		toolbarComponent = '#tb';
		// 查询初始时间设置为当天的00点，到当天末23点59分59秒，时间都以拼接的方式，中间添加空格拼到日期的后面
		myQueryParams = {
			'maxId' : maxId,
			'startTime': todayStr + " " + "00:00:00",
			'endTime': todayStr + " " + "23:59:59",
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
		
		/*
		 * 管理员时间跨度下拉选择，
		 */
		$("#search_adminUserDateSpan").combogrid({
			panelWidth:120,
			idField:'userNameId',
		    textField:'userName',
		    url: "./admin/privileges_queryAdminTimeManage",
		    columns:[[
		        {field:'userName', title:'管理员名称 '}
		    ]]
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
		    ]]
		});
		
		$("#pagination").pagination({
			pageList: [30,50,100,300],
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
				'width'				: '100%',
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
	 * 显示用户信息
	 */
	function showUserInfo(uri){
		$.fancybox({
			'margin'			: 20,
			'width'				: '100%',
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
	 * 查找type评论
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
	 * worldId显示界面初始化
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
			'href'				: uri
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
	
	function updateValue(index, key, value) {
		dataList[index][key] = value;
		var $worldOpt = $(".world-opt-wrap:eq("+index+")");
		removeWorldOptArea($worldOpt);
		drawOptArea($worldOpt, dataList[index], index);
	}

	function updateValues(index, keys, values) {
		var $worldOpt = $(".world-opt-wrap:eq("+index+")");
		for(var i = 0; i < keys.length; i++) {
			dataList[index][keys[i]] = values[i];
		}
		removeWorldOptArea($worldOpt);
		drawOptArea($worldOpt, dataList[index], index);
	}
	
	/**
	 * 移除操作区域
	 * 
	 * @param $worldOpt
	 * @return
	 */
	function removeWorldOptArea($worldOpt) {
		$worldOpt.children('.world-opt-area:eq(0)').remove();
	};
	
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
		// TODO 临时方法，还要整改
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
		scroll(0,0);
		$("#page-loading").show();
		myQueryParams['page'] = 1;
		myQueryParams['rows'] = 30;
		myQueryParams['valid'] = $("#valid").combobox('getValue');
		
		$.post("./admin_ztworld/ztworld_queryHTWorldList", myQueryParams, function(result){
			if(result['result'] == 0) {
				if(result.maxId > maxId) {
					maxId = result.maxId;
					myQueryParams.maxId = maxId;
				}
				if(result.activityId != 'undefined') {
					activityId = result.activityId;
				} else {
					activityId = 0;
				}
				dataList = [];
				$(".world-opt-wrap").remove();
				refreshPagination(result['total'], 1, 100);
				var worlds = result['rows'];
				var $worldBox = $('#world-box');
				for(var i = 0; i < worlds.length; i++) {
					var world = worlds[i];
					dataList.push(world);
					var $worldOpt = $('<div class="world-opt-wrap"></div>');
					drawWorldOpt($worldOpt, world, i);
					$worldBox.append($worldOpt);
				}
				$("#page-loading").hide();
			} else {
				$.messager.alert('失败提示',result['msg']);
			}
		}, "json");
	}
	
	/**
	 * 根据描述查询织图集合
	 */
	function searchByWorldDesc() {
		// TODO 临时方法，还要整改
		maxId = 0;
		var worldDesc = $('#ss_worldDesc').searchbox('getValue');
		if(worldDesc == "") {
			search();
			return
		}
		myQueryParams = {
			'worldDesc' : worldDesc
		};
		
		scroll(0,0);
		$("#page-loading").show();
		myQueryParams['page'] = 1;
		myQueryParams['rows'] = 30;
		myQueryParams['valid'] = $("#valid").combobox('getValue');
		
		$.post("./admin_ztworld/ztworld_queryHTWorldList", myQueryParams, function(result){
			if(result['result'] == 0) {
				if(result.maxId > maxId) {
					maxId = result.maxId;
					myQueryParams.maxId = maxId;
				}
				if(result.activityId != 'undefined') {
					activityId = result.activityId;
				} else {
					activityId = 0;
				}
				dataList = [];
				$(".world-opt-wrap").remove();
				refreshPagination(result['total'], 1, 100);
				var worlds = result['rows'];
				var $worldBox = $('#world-box');
				for(var i = 0; i < worlds.length; i++) {
					var world = worlds[i];
					dataList.push(world);
					var $worldOpt = $('<div class="world-opt-wrap"></div>');
					drawWorldOpt($worldOpt, world, i);
					$worldBox.append($worldOpt);
				}
				$("#page-loading").hide();
			} else {
				$.messager.alert('失败提示',result['msg']);
			}
		}, "json");
	}
	
	/**
	 * 根据织图所标记的地理位置来查询织图
	 */
	function searchByWorldLocation() {
		// TODO 临时方法，还要整改
		maxId = 0;
		var worldLocation = $('#ss_worldLocation').searchbox('getValue');
		if(worldLocation == "") {
			return
		}
		myQueryParams = {
			'worldLocation' : worldLocation
		};
		
		scroll(0,0);
		$("#page-loading").show();
		myQueryParams['page'] = 1;
		myQueryParams['rows'] = 30;
		myQueryParams['valid'] = $("#valid").combobox('getValue');
		
		$.post("./admin_ztworld/ztworld_queryHTWorldList", myQueryParams, function(result){
			if(result['result'] == 0) {
				if(result.maxId > maxId) {
					maxId = result.maxId;
					myQueryParams.maxId = maxId;
				}
				if(result.activityId != 'undefined') {
					activityId = result.activityId;
				} else {
					activityId = 0;
				}
				dataList = [];
				$(".world-opt-wrap").remove();
				refreshPagination(result['total'], 1, 30);
				var worlds = result['rows'];
				var $worldBox = $('#world-box');
				for(var i = 0; i < worlds.length; i++) {
					var world = worlds[i];
					dataList.push(world);
					var $worldOpt = $('<div class="world-opt-wrap"></div>');
					drawWorldOpt($worldOpt, world, i);
					$worldBox.append($worldOpt);
				}
				$("#page-loading").hide();
			} else {
				$.messager.alert('失败提示',result['msg']);
			}
		}, "json");
	}
	
