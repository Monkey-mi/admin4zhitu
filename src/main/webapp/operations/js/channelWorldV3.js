var dataList = [];
/**
 * 初始化布局
 * 
 * @return
 */
function initWorldBoxWidth() {
	var length = parseInt($(window).width() / (250 + 10));
	$("#world-box").css("width",length * (250 + 10) + 20 + 'px');
}

/**
 * 刷新分页
 * 
 * @param total
 * @param pageSize
 * @param pageNumber
 * @return
 */
function refreshPagination(total, pageSize, pageNumber)	{
	$("#pagination").pagination('refresh', {
	    total:total,
	    pageSize:pageSize,
	    pageNumber:pageNumber
	});
}

/**
 * 加载数据
 * 
 * @param pageNumber
 * @param pageSize
 * @return
 */
function loadData(pageNumber, pageSize) {
	scroll(0,0);
	$("#page-loading").show();
	myQueryParams['page'] = pageNumber;
	myQueryParams['rows'] = pageSize;
	$.post(loadDataURL, myQueryParams,
			function(result){
			if(result['result'] == 0) {
				if(result.maxId > maxId) {
					maxId = result.maxId;
					myQueryParams.maxId = maxId;
				}
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

/**
 * 绘制织图
 * 
 * @param $worldOpt
 * @param worlds
 * @param index
 * @return
 */
function drawWorldOpt($worldOpt, worlds, index) {
	var world = worlds[index],
		worldId = world['id'],
		ver = world['ver'],
		worldDesc = world['worldDesc'],
		titlePath = world['titlePath'];
	
	// 根据标记位动态设置背景颜色
	$worldOpt.attr("style", myRowStyler(index, world));
	
	// 生成织图信息
	var $authorInfo = $('<div class="world-author">'
			+'<span>'+authorAvatarColumn.formatter(world['authorAvatar'],world,index)+'</span>'
			+'<span class="world-author-name">'+getAuthorName(world['authorName'],world,index) +'</span>'
			+'<span>'+phoneCodeColumn.formatter(world['phoneCode'],world,index) +'</span>'
			+'<hr class="divider"></hr>'
			+'<div>织图ID:'+ world['worldId']
			+'<span class="world-count world-date">'+dateAddedFormatter(world['dateModified'], world, index)+'</span>'
			+'</div>'
			+'<div>用户ID:'+world['authorId']
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

	/*添加操作按钮*/
	var $opt = $('<div class="world-opt-head-wrap">'
			+ '<span class="world-opt-head">活动</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">最新</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">操作</span>'
			+'</div>'
			+ '<div class="world-opt-btn-wrap">'
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

/**
 * 获取用户名
 * 
 * @param value
 * @param row
 * @param index
 * @return
 */
function getAuthorName(value, row, index) {
	if(row.authorId != 0) {
		return value.substr(0, 15);
	} else if(baseTools.isNULL(value)) {
		return "织图用户";
	}
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

/* 以下均为示例代码 */
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
