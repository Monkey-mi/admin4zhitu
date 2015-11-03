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
	$("#pagination").pagination("refresh", {
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
					var world = worlds[i];
					dataList.push(world);
					var $worldOpt = $('<div class="world-opt-wrap"></div>');
					drawWorld($worldOpt, worlds, i);
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
function drawWorld($worldOpt, worlds, index) {
	var world = worlds[index];
	var worldId = world['id'];
	
	// 织图ID点击需要的织图短链
	var wurl;
	if(world.worldURL == "" || world.worldURL == undefined){
		var slink;
		if(world.shortLink == "") {
			slink = world.worldKey;
		} else {
			slink = world.shortLink;
		} 
		wurl = "http://www.imzhitu.com/DT" + slink;
	} else {
		wurl = world.worldURL;
	}
	
	// 生成织图信息
	var $authorInfo = $('<div class="world-author">'
			+'<span>'+authorAvatarColumn.formatter(world['authorAvatar'],world,index)+'</span>'
			+'<span class="world-author-name">'+getAuthorName(world['authorName'],world,index) +'</span>'
			+'<span>'+phoneCodeColumn.formatter(world['phoneCode'],world,index) +'</span>'
			+'<hr class="divider"></hr>'
			+'<div>织图ID:' 
			+ "<a title='打开互动页面' class='updateInfo' href='javascript:openHtworldShowForChannelWorldPage("+world.worldId+",\""+wurl+"\","+world.valid+")'>"+world.worldId+"</a>"
			+'<span class="world-count world-date">'+dateAddedFormatter(world['dateModified'], world, index)+'</span>'
			+'</div>'
			+'<div>用户ID:'+world['authorId']
			+'</div>'
			+'</div>');
	var $worldInfo = $('<div class="world-info">'
		+'<div class="world-label">#'
		+ world['worldLabel'] +'</div>'
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

	$worldOpt.addClass('world-margin');
	$worldOpt.append($authorInfo);
	$worldOpt.append($world);
	$worldOpt.append($worldInfo);
	drawOptArea($worldOpt, worlds, index);
	$world.appendtour({
		'width': 250,
		'worldId': worldId,
		'ver': world.ver,
		'worldDesc': world.worldDesc,
		'titlePath': world.titlePath,
		'url':'./admin_ztworld/ztworld_queryTitleChildWorldPage',
		'loadMoreURL':'./admin_ztworld/ztworld_queryChildWorldPage'
	});
};

/**
 * 绘制操作区域
 * 
 * @param $worldOpt
 * @param worlds
 * @param index
 * @return
 */
function drawOptArea($worldOpt, worlds, index) {
	var world = worlds[index];
	// 声明操作区域对象 
	var $opt = $('<div class="world-opt-area"></div>');
	
	// 添加第一行操作title
	var $opt1LineTitle = $('<div class="world-opt-head-wrap">'
			+ '<span class="world-opt-head">生效</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">精选</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">操作</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">计划</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">完成</span>'
			+'</div>');
	
	// 添加第一行操作title对应的按钮
	var $opt1LineBtn = $('<div class="world-opt-btn-wrap">'
			+ '<span class="world-opt-btn">'
			+ getChannelWorldValid(world['channelWorldValid'], world, index)
			+ '</span>'+ '<span>|</span>'
			+ '<span class="world-opt-btn">'
			+ getSuperb(world['superb'], world, index)
			+ '</span>'+ '<span>|</span>'
			+ '<span class="world-opt-btn">'
			+ getDeleteStatusOpt(world['channelWorldValid'], world, index)
			+ '</span>'+ '<span>|</span>'
			+ '<span class="world-opt-btn">'
			+ getBeSchedulaOpt(world['beSchedula'], world, index)
			+ '</span>'+ '<span>|</span>'
			+ '<span class="world-opt-btn">'
			+ getSchedulaCompleteOpt(world['schedulaComplete'], world, index)
			+ '</span>'
			+ '</div>');
	
	// 添加分隔线
	var $optDivider = $('<hr class="divider"></hr>');
	
	// 添加第二行操作title
	var $opt2LineTitle = $('<div class="world-opt-head-wrap">'
			+ '<span class="world-opt-head">评论</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">标签</span>'
			+ '</div>');
	
	// 添加第二行操作title对应的按钮
	var $opt2LineBtn = $('<div class="world-opt-btn-wrap"></div>');
	// 向第二行按钮中添加元素
	$line2CommentBtn = $('<span class="world-opt-btn">'
			+ getCommentInteractOpt(world['worldId'], world, index)
			+ '</span>'
			+ '<span>|</span>');
	$opt2LineBtn.append($line2CommentBtn);
	drawChannelWorldLabelBtn($opt2LineBtn, worlds, index);
	   
	$opt.append($opt1LineTitle);
	$opt.append($opt1LineBtn);
	$opt.append($optDivider);
	$opt.append($opt2LineTitle);
	$opt.append($opt2LineBtn);
	
	$worldOpt.append($opt);
}

/**
 * 查询频道织图互动过的标签，若是没有频道互动过，则查询该频道绑定的标签
 * @param $worldOpt
 * @param worlds
 * @param index
 * @return
 */
function drawChannelWorldLabelBtn($opt2LineBtn, worlds, index) {
	var world = worlds[index];
	if(world.valid == 0 || world.shield == 1) {
		return '';
	}
	
	var $labelDIV = $('<div style="display: inline-block;"></div>');
	var ret = '';
	$.post("./admin_interact/channelWorldLabel_queryChannelWorldLabel",{
		'channelId':world['channelId'],
		'worldId':world['id']},function(result){
			if(result['result'] == 0){
				var data = result.labelInfo;
				if (!result.interact) {
					for (var i=0; i<data.length; i++) {
						var labelId = data[i].commentLabelId;
						var labelName = data[i].commentLabelName;
						ret += '<span id="commentLabel-'+world.id+'-'+labelId+'" class="label-btn-unclc-bg" data-label="'+labelId+'">'
								+ '<a href="javascript:void(0)" style="text-decoration:none;" onclick="putWorldCommentLabelId(' 
								+ world.id + ',' + labelId + ',' + index + ')">#' + labelName + '</a>'
								+ '<input type="hidden" value="' + labelId + '"></input>'
								+ '</span>&nbsp;';
						if (i != 0 && i%3 == 0) {
							ret += '<br>';
						}
					}
					ret += '<a href="javascript:void(0)"><img src="./common/images/ok.png" onclick="submitAddChannelWorldLabel('+ world.id + ', ' + index + ')"></img></a>'
					+ '<input id="worldCommentLabel-' + world.id + '" type="hidden"></input>';
				} else {
					for (var i=0; i<data.length; i++) {
						ret += '<span>#' + data[i].commentLabelName + '</span>&nbsp;';
					}
				}
			}
			$labelDIV.append($(ret))
			$opt2LineBtn.append($labelDIV);
		},"json");
}

/**
 * 点击标签触发：将点击的标签ID放入指定的存储位置
 * @param worldId
 * @param labelId
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
 * 提交添加频道织图标签
 * @param worldId
 * @param labelIds
 * @return
 */
function submitAddChannelWorldLabel(worldId, index){
	$.post("./admin_interact/channelWorldLabel_insertChannelWorldLabel",{
		'channelId'	: baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID"),
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
 * 移除操作区域
 * 
 * @param $worldOpt
 * @return
 */
function removeWorldOptArea($worldOpt) {
	$worldOpt.children('.world-opt-area:eq(0)').remove();
};

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
};

/**
 * 更新记录字段
 * 
 * @param index
 * @param key
 * @param value
 * @return
 */
function updateValue(index, key, value) {
	dataList[index][key] = value;
	var $worldOpt = $(".world-opt-wrap:eq("+index+")");
	removeWorldOptArea($worldOpt);
	drawOptArea($worldOpt, dataList, index);
};

/**
 * 批量更新记录字段
 * 
 * @param index
 * @param keys
 * @param values
 * @return
 */
function updateValues(index, keys, values) {
	var $worldOpt = $(".world-opt-wrap:eq("+index+")");
	for(var i = 0; i < keys.length; i++) {
		dataList[index][keys[i]] = values[i];
	}
	removeWorldOptArea($worldOpt);
	drawOptArea($worldOpt, dataList, index);
};

/**
 * 获取织图有效操作
 * 
 * @param value
 * @param row
 * @param index
 * @return
 */
function getChannelWorldValid(value, row, index) {
	if(row.valid == 0 || row.shield == 1 || value == 2) {
		return '';
	}
	switch(value) {
		case 1:
			tip = "已经生效,点击取消";
			img = "./common/images/ok.png";
			valid = 0;
			break;
		default:
			tip = "点击生效";
			img = "./common/images/tip.png";
			valid = 1;
			break;
	}
	return "<img title='"+ tip + "' class='htm_column_img pointer' " 
		+ "onclick='javascript:updateValid(\""+ row.channelId + "\",\"" + row.worldId + "\","+ valid + "," + index + ")' " 
		+ "src='" + img + "'/>";
};

/**
 * 获取精选操作
 * 
 * @param value
 * @param row
 * @param index
 * @return
 */
function getSuperb(value, row, index) {
	if(row.valid == 0 || row.shield == 1) {
		return '';
	}
	switch(value) {
	case 1:
		tip = "已加入精选,点击移出";
		img = "./common/images/ok.png";
		superb = 0;
		break;
	default:
		tip = "点击加入精选";
		img = "./common/images/edit_add.png";
		superb = 1;
		break;
	}
	return "<img title='"+ tip + "' class='htm_column_img pointer' " 
		+ "onclick='javascript:updateSuperb(\""+ row.channelId + "\",\"" + row.worldId + "\","+ superb + "," + index + ")' " 
		+ "src='" + img + "'/>";
};

/**
 * 获取删除操作
 * 
 * @param value
 * @param row
 * @param index
 * @return
 */
function getDeleteStatusOpt(value, row, index) {
	if(value != 2) {
		tip = "从频道删除织图";
		opt = "删除";
		valid = 2;
	} else {
		tip = "恢复至未生效状态";
		opt = "恢复";
		valid = 0;
	}
	
	return "<a href=javascript:void(0); title='"+tip+"' class='updateInfo pointer' " 
		+ "onclick='javascript:updateDeleteStatus(\""+ row.channelId + "\",\"" + row.worldId + "\","+ valid + "," + index + ")'>"
		+ opt+"</a>";
}

/**
 * 获取被计划状态
 * 
 * @param value
 * @param row
 * @param index
 * @return
 */
function getBeSchedulaOpt(value, row, index) {
	if(value == 0) {
			img = "./common/images/ok.png";
			return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
		}
		img = "./common/images/tip.png";
		return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
}

/**
 * 获取计划完成状态
 * 
 * @param value
 * @param row
 * @param index
 * @return
 */
function getSchedulaCompleteOpt(value, row, index) {
	if(value == 1 && row.beSchedula == 0) {
		img = "./common/images/ok.png";
		return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
	}else if(value == 0 && row.beSchedula == 0){
		img = "./common/images/tip.png";
		return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
	}
	return '';
}

/**
 * 绘制互动评论按钮
 * @param worldId	织图id
 * @param row
 * @param index
 */
function getCommentInteractOpt(worldId, row, index) {
	if(row.valid == 0 || row.shield == 1) {
		return '';
	} else {
		return "<img title='点击打开织图计划互动评论' class='htm_column_img pointer' " 
		+ "onclick='javascript:openCommentsInteractPage(\""+ worldId + "\")' " 
		+ "src='./common/images/edit_add.png'/>";
	}
}

/**
 * 打开评论互动页面
 * @param worldId	织图id
 * @param row
 * @param index
 */
function openCommentsInteractPage(worldId) {
	var uri = "page_operations_htworldCommentInteract?worldId=" + worldId;
	$.fancybox({
		'href'				: uri,
		'margin'			: 20,
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
};

/**
 * 打开织图互动展示（频道织图使用）页面
 * 
 * @param worldId	织图id
 * @param worldURL	织图在网页中展示的短链，即一个http链接
 * 
 * @author zhangbo 2015-10-27
 */
function openHtworldShowForChannelWorldPage(worldId, worldURL, valid) {
	var uri = "page_htworld_htworldShowForChannelWorld";
	
	uri += "?worldId=" + worldId;
	uri += "&worldURL=" + worldURL;
	uri += "&valid=" + valid;
	$.fancybox({
		'href'				: uri,
		'margin'			: 20,
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
}
