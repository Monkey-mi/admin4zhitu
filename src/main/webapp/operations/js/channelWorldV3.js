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
			+ '<span class="world-opt-head">生效</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">精选</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">操作</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">计划</span>'
			+ '<span>|</span>'
			+ '<span class="world-opt-head">完成</span>'
			+'</div>' 
			);
	
	$worldOpt.addClass('world-margin');
	$worldOpt.append($authorInfo);
	$worldOpt.append($world);
	$worldOpt.append($worldInfo);
	$worldOpt.append($opt);
	drawWorldOptBtn($worldOpt, worlds, index);
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
 * 绘制操作按钮
 * 
 * @param $worldOpt
 * @param worlds
 * @param index
 * @return
 */
function drawWorldOptBtn($worldOpt, worlds, index) {
	var world = worlds[index];
	var $optBtn = 
		$('<div class="world-opt-btn-wrap">'
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
		+ '</div>'
		);
	$worldOpt.append($optBtn);
	drawChannelWorldLabelBtn($worldOpt, worlds, index);
}

/**
 * 查询频道织图互动过的标签，若是没有频道互动过，则查询该频道绑定的标签
 * @param $worldOpt
 * @param worlds
 * @param index
 * @return
 */
function drawChannelWorldLabelBtn($worldOpt, worlds, index) {
	var world = worlds[index];
	var optBtnStr = 
		'<hr class="divider"></hr>'
		+ '<div class="world-opt-btn-wrap">'
		+ '<span class="world-opt-head">评论</span>'
		+ '<span>|</span>'
		+ '<span class="world-opt-head">标签</span>'
		+ '</div>'
		+ '<div class="world-opt-btn-wrap">'
		+ '<span class="world-opt-btn">'
		+ getCommentInteractOpt(world['worldId'], world, index)
		+ '</span>'
		+ '<span>|</span>';
	
	$.post("./admin_interact/channelWorldLabel_queryChannelWorldLabel",{
		'channelId':world['channelId'],
		'worldId':world['id']},function(result){
			
			if(result['result'] == 0){
				if (!result.interact) {
					var data=result.labelInfo;
					for (var i=0; i<data.length; i++) {
						optBtnStr += '<span><a onclick="putWorldCommentLabelId(' + world.id + ',' + data[i].commentLabelId + ')">#' + data[i].commentLabelName + '</a></span>';
					}
					
					optBtnStr += '<a id="comment_label_submit" href="#" onclick="submitAddChannelWorldLabel('
						+ world.id + ', ' + index + ');" class="easyui-linkbutton" iconCls="icon-add">确定</a>'
						+ '<input id="worldCommentLabel-' + world.id + '" type="hidden"></input>'
						+'</div>';
					
					$worldOpt.append($(optBtnStr));
				}
			}
		},"json");
}

/**
 * 点击标签触发：将点击的标签ID放入指定的存储位置
 * @param worldId
 * @param labelId
 */
function putWorldCommentLabelId(worldId, labelId) {
	var ids = [];
	if ($("#worldCommentLabel-" + worldId).val() == "") {
		ids.push(labelId);
	} else {
		
		var labelIds = $("#worldCommentLabel-" + worldId).val().split(",");
		ids = labelIds;
		for (var i=0; i<labelIds.length; i++) {
			if (labelIds[i] != labelId) {
				ids.push(labelId);
			}
		}
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
			updateChannelWorldLabelBtn(index, "labelInfo", "interact", 1);
		}else{
			$.messager.alert('失败提示',result['msg']);
		}
	},"json");
};

/**
 * 更新频道织图标签
 * @param index
 * @param key
 * @param value
 * @return
 */
function updateChannelWorldLabelBtn(index,key_level_1,key_level_2,value){
	var $worldOpt = $(".world-opt-wrap:eq("+index+")");
	dataList[index][key_level_1][key_level_2] = value;
	removeWorldOptBtn($worldOpt);
	drawWorldOptBtn($worldOpt, dataList, index);
};

/**
 * 移出操作按钮
 * 
 * @param $worldOpt
 * @return
 */
function removeWorldOptBtn($worldOpt) {
	$worldOpt.children('.world-opt-btn-wrap:eq(0)').remove();
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
	var $worldOpt = $(".world-opt-wrap:eq("+index+")");
	dataList[index][key] = value;
	removeWorldOptBtn($worldOpt);
	drawWorldOptBtn($worldOpt, dataList, index);
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
	removeWorldOptBtn($worldOpt);
	drawWorldOptBtn($worldOpt, dataList, index);
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
}
