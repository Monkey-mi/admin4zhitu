var adminKey = 'zhangjiaxin',
//	worldURLPrefix = 'http://192.168.1.151/hts/DT',
	worldURLPrefix = 'http://www.imzhitu.com/DT',
	worldKey = 'worldId',
	updatePageURL = "./admin_ztworld/ztworld_updateWorldByJSON",
	inValidWorld = 'background-color:#ffe3e3',
	interactedWorld = 'background-color:#e3fbff',
	interactedInvalidWorld = 'background-color:#feeeae',
	authorAvatarTimer,
	interacts = {},
	phoneType = 
			[{
			    "id":0,
			    "text":"IOS"
			},{
			    "id":1,
			    "text":"安卓"
			}],
	worldTypes = 
		[{
		    "id":1,
		    "text":"旅行"
		},{
		    "id":2,
		    "text":"穿搭"
		},{
		    "id":3,
		    "text":"美食"
		},{
		    "id":6,
		    "text":"故事"
		},{
		    "id":7,
		    "text":"心情"
		}],
	myRowStyler = function(index,row){
		if(row.valid) {
			if(row.interacted)
				return interactedWorld;
		} else {
			if(row.interacted)
				return interactedInvalidWorld;
			else 
				return inValidWorld;
		}
		return null;
	},
	pageButtons = [{
        iconCls:'icon-save',
        text:'保存',
        handler:function(){
        	endEditing();
        	var rows = $("#htm_table").datagrid('getChanges', "updated"); 
        	$("#htm_table").datagrid('loading');
        	$.post(updatePageURL,{
        		'worldIdKey':worldKey,
        		'worldJSON':JSON.stringify(rows)
        		},function(result){
        			if(result['result'] == 0) {
        				$("#htm_table").datagrid('acceptChanges');
        			} else {
        				$.messager.alert('失败提示',result['msg']);  //提示失败信息
        			}
        			$("#htm_table").datagrid('loaded');
        		},"json");
        }
    },{
        iconCls:'icon-undo',
        text:'取消',
        handler:function(){
        	$("#htm_table").datagrid('rejectChanges');
        	$("#htm_table").datagrid('loaded');
        }
    }],
    commentLabelloader = function(param,success,error){
        $.post("./admin_interact/comment_queryAllLabel",{
		},function(result){
			success(result);
		},"json");
    },
	commentMaxId = 0,
	isFirstPage = true,
	commentQueryParams = {
		'labelId':0
	},
    labelQueryParams = {
    	'typeId':1
    };
	
function interact(worldId, index) {
	initInteractWindow(worldId);
	// 打开添加窗口
	$("#htm_interact form").hide();
	$("#interact_loading").show();
	$("#htm_interact").window('open');
	loadInteractFormValidate(worldId, index);
}

//初始化添加窗口
function initInteractWindow(worldId) {
	$("#labelId").combotree('clear');
	$("#levelId").combobox('clear');
	
	$.post("./admin_interact/worldlevelList_queryWorldUNInteractCount",{
		'world_id':worldId
		},function(data){
			if(data["result"] == 0){
			$("#unValid_clickSum_interact").text(data["clickCount"]);
			$("#unValid_likedSum_interact").text(data["likedCount"]);
			$("#unValid_commentSum_interact").text(data["commentCount"]);
			}else{
				$.messager.alert('错误提示',data['msg']);  //提示添加信息失败
			}
	},"json");
	
	$.post("./admin_interact/interact_queryInteractSum",{
		'worldId':worldId
	},function(result){
		if(result['result'] == 0) {
			$("#clickSum_interact").text(result["clickCount"]);
			$("#likedSum_interact").text(result["likedCount"]);
			$("#commentSum_interact").text(result["commentCount"]);

			$("#interact_loading").hide();
			var $addForm = $('#interact_form');
			$addForm.show();
			$('#htm_interact .opt_btn').show();
			$('#htm_interact .loading').hide();
			clearFormData($addForm);
			$("#worldId_interact").val(worldId);
			$("#comments_interact").combogrid('clear');
			$("#comments_interact").combogrid('grid').datagrid('unselectAll');
			
			$("#ss_comment").searchbox('setValue', "");
			$("#duration_interact").val('24');
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
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
	clearAuthorAvatarTimer();
}

/**
 * 初始化 设定用户等级窗口
 */
 function initUserLevelAddWindow(userId,index){
	 var userleveladdform = $("#userLevel_form");
	 clearFormData(userleveladdform);
	 $("#userLevelId_userLevel").combobox('clear');
	$("#userId_userLevel").val(userId);
	$("#htm_userLevel").window('open');
	$("#userLevel_loading").hide();
	$("#userLevel_form").show();
	$.post("./admin_interact/userlevelList_QueryUserlevelByUserId",
			{'userId':userId},
			function(result){
				if(result['user_level']){
					var userlevel = result['user_level'];
					$("#userLevelId_userLevel").combobox('setValue',userlevel['user_level_id']);
				}
	},"json");
}
/**
 * 检查设定用户等级提交的数据
 */
function userLevelAddValidity(){
	if($("#userLevelId_userLevel").combobox('getValue')){
		return true;
	}
	$.messager.alert('请选择用户等级');
	return false;
}
/**
 * 提交 增加等级用户 表格
 */
function submitUserLevelAddForm(){
	var form = $("#userLevel_form");
	if(userLevelAddValidity()){
		$('#htm_userLevel .opt_btn').hide();
		$('#htm_userLevel .loading').show();
		$("#userLevel_form").form('submit',{
			url:form.attr('action'),
			success:function(data){
				var result = $.parseJSON(data);
				$('#htm_userLevel .opt_btn').show();
				$('#htm_userLevel .loading').hide();
				if(result['result'] == 0){
					$("#htm_userLevel").window('close');
					$("#htm_table").datagrid("reload");
				}else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			}
		});
	}
}

/**
 * 加载评论
 */
function loadComment(labelId,labelName) {
	commentMaxId = 0;
	commentQueryParams.maxId = commentMaxId;
	commentQueryParams.labelId = labelId;
	commentQueryParams.comment = "";
	$("#comments_interact").combogrid('grid').datagrid('load',commentQueryParams);
}

function searchComment() {
	var comment = $("#ss_comment").searchbox("getValue");
	commentMaxId = 0;
	commentQueryParams.maxId = commentMaxId;
	commentQueryParams.comment = comment;
	var tmp = $("#comments_interact").combogrid('getValues');
	$("#comments_interact").combogrid('grid').datagrid('load',commentQueryParams);
	$("#ss_comment").searchbox('clear');
	$("#comments_interact").combogrid('setValues',tmp);
}

function refreshComent4Update() {
	$("#comments_interact").combo('panel').panel('open');
	$("#comments_interact").combogrid('grid').datagrid('reload');
}

function refreshComment4Add() {
	$("#comments_interact").combo('panel').panel('open');
	$("#labelId_interact").combotree('setValue',0);
	$("#ss_comment").searchbox("setValue","");
	commentMaxId = 0;
	commentQueryParams.maxId = commentMaxId;
	commentQueryParams.labelId = 0;
	commentQueryParams.comment = "";
	$("#comments_interact").combogrid('grid').datagrid('load',commentQueryParams);
}

//提交表单，以后补充装载验证信息
function loadInteractFormValidate(worldId, index) {
	var addForm = $('#interact_form');
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$('#htm_interact .opt_btn').hide();
				$('#htm_interact .loading').show();
				//验证成功后以异步方式提交表单
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						formSubmitOnce = true;
		            	$('#htm_interact .opt_btn').show();
						$('#htm_interact .loading').hide();
						if(result['result'] == 0) {
							$('#htm_interact').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							$('#comments_interact').combogrid('clear');
							updateValue(index, "interacted", 1);
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");	
				return false;
			}
		}
	});
	
	$("#worldId_interact")
	.formValidator({empty:false,onshow:"请输入织图ID",onfocus:"例如:10012",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#duration_interact")
	.formValidator({empty:false,onshow:"小时",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#clickCount_interact")
	.formValidator({empty:true,onshow:"请输入播放数",onfocus:"例如:120",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#likedCount_interact")
	.formValidator({empty:true,onshow:"请输入喜欢数",onfocus:"例如:120",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#commentCount_interact")
	.formValidator({empty:true,onshow:"请输入喜欢数",onfocus:"例如:120",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
}

/**
 * 初始化活动添加窗口
 */
function initTypeUpdateWindow(worldId, typeId, index, isAdd,userId,labelIsExist) {
	$("#worldId_type").val(worldId);
	if($("#userId_type").length>0){
		$("#userId_type").val(userId);
	}
	$("#htm_type").window('open');
//	if(typeId == 0) {
//		typeId = 1;
//		$("#typeId_type").combobox('setValue', typeId);
//	} else {
//		$("#typeId_type").combobox('select', typeId);
//	}
	
	/*
	if(isAdd == 'false') {
		$.post("./admin_ztworld/type_queryLabelIdByWorldId",{
			'worldId':worldId,
			},function(result){
				if(result['result'] == 0) {
					$("#labelIds_type").combogrid('setValues', result['labelInfo']);
					loadTypeUpdateFormValid(index, 'true');
					$("#type_loading").hide();
					$("#type_form").show();
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
			},"json");
	} else {
		loadTypeUpdateFormValid(index, isAdd);
		$("#type_loading").hide();
		$("#type_form").show();
	}
	*/
	loadTypeUpdateFormValid(index, isAdd,userId,labelIsExist);
	$("#type_loading").hide();
	$("#type_form").show();
}

function showWorld(uri) {
	$.fancybox({
		'margin'			: 20,
		'width'				: '10',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri + "?adminKey=" + adminKey
	});
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
 * 显示喜欢指定织图的用户
 * @param uri
 */
function showLikedUser(uri) {
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

/**
 * 屏蔽织图
 */
function shield(worldId,index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/ztworld_shieldWorld",{
		'worldId':worldId
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'shield',1);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 解除屏蔽
 */
function unShield(worldId,index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/ztworld_unShieldWorld",{
		'worldId':worldId
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'shield',0);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

function searchByShortLink() {
	maxId = 0;
	var shortLink = $('#ss_shortLink').searchbox('getValue');
	myQueryParams = {
		'shortLink' : shortLink
	};
	$("#htm_table").datagrid("load",myQueryParams);
}

function searchByAuthorName() {
	maxId = 0;
	var authorName = $('#ss_authorName').searchbox('getValue');
	myQueryParams = {
		'authorName' : authorName
	};
	$("#htm_table").datagrid("load",myQueryParams);
}

function searchByWorldDesc() {
	maxId = 0;
	var worldDesc = $('#ss_worldDesc').searchbox('getValue');
	myQueryParams = {
		'worldDesc' : worldDesc
	};
	$("#htm_table").datagrid("load",myQueryParams);
}

/**
 * 添加信任
 * 
 * @param userId
 * @param index
 */
function addTrust(userId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_user/user_updateTrust",{
		'userId':userId,
		'trust':1
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'trust',1);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('reload');
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 移除信任
 * 
 * @param userId
 * @param index
 */
function removeTrust(userId, index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_user/user_updateTrust",{
		'userId':userId,
		'trust':0
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'trust',0);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('reload');
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 添加评论
 */
function addComment(id, index) {
	$.fancybox({
		'width'				: 700,
		'height'			: 450,
		'autoScale'			: true,
		'type'				: 'iframe',
		'href'				: "page_interact_addComment?id="+id,
		'titlePosition'		: 'inside',
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'hideOnOverlayClick': false,
		'showCloseButton'   : false,
		'titleShow'			: false,
		'onClose'			:function(){
			if($("#comments_interact")){
				$("#comments_interact").combogrid('grid').datagrid('reload');
			}
			if($("#comments_type_interact")){
				$("#comments_type_interact").combogrid('grid').datagrid('reload');
			}
		}
	});
}

/**
 * 数据记录
 */
function deleteComment() {
	var $comment = $('#comments_interact');
	$comment.combo('panel').panel('close');
	var rows = $comment.combo('getValues');	
	if(isSelected(rows)){
		$.messager.confirm('删除记录', '您确定要删除已选中的记录?', function(r){ 	
			if(r){				
				$comment.combogrid('clear'); //清除所有已选择的记录，避免重复提交id值	
				$comment.combo('panel').panel('open');
				$comment.combogrid('grid').datagrid('loading');
				$.post("./admin_interact/comment_deleteCommentByIds?ids=" + rows.join(),function(result){
					$comment.combogrid('grid').datagrid('reload');
					if(result['result'] < 0) {
						$comment.combo('panel').panel('close');
						$.messager.alert('提示',result['msg']);
					}
					
				});				
			}	
		});		
	}	
}

/**
 * 头像hover 查看某用户信息
 */
function queryUserInfoAndCheckIsZombie(userId,x,y){
	$.post("./admin_user/user_queryUserByUserIdAndCheckIsZombie",{
		'userId':userId
	},function(result){
		if(result['result'] == 0){
			var platformHead = "<img id='platformImg' src='";
			var platformNail = "'/>";
			var platformImg = ["http://imzhitu.qiniudn.com/web%2Fadmin%2Fimages%2Fzhitu1.png",
			                   "微信","http://imzhitu.qiniudn.com/web%2Fadmin%2Fimages%2Fweibo.png","人人网",
			                   "http://imzhitu.qiniudn.com/web%2Fadmin%2Fimages%2Fqzone.png"];
			$("#hover_worldCount").text(result['userInfo']['worldCount']);
			$("#hover_superb").text(result['superbCount']);
			$("#hover_concernCount").text(result['userInfo']['concernCount']);
			$("#hover_followCount").text(result['userInfo']['followCount']);
//			$("#hover_platformCode").text(platformHead+platformImg[result['userInfo']['platformCode']] + platformNail);
			$("#hover_platformCode").append(platformHead+platformImg[result['userInfo']['platformCode']] + platformNail);
			$("#hover_signature").text(result['userInfo']['signature']);
			$("#hover_registerDate").text(result['userInfo']['registerDate']);
			$("#hover_userLabel").text(result['userInfo']['userLabel']);
			if(result['zombie'] == 1){
				$("#avarta_hover_registe_date").css('color','red');
			}
			
		}else{
			$("#hover_worldCount").text("");
			$("#hover_superb").text("");
			$("#hover_concernCount").text("");
			$("#hover_followCount").text("");
			$("#hover_platformCode").text("");
			$("#hover_signature").text("");
			$("#hover_registerDate").text("");
			$("#hover_userLabel").text(result['msg']);
		}
		$("#avatar_hover").css('left',x + 'px');
		$("#avatar_hover").css('top',y  + 'px');
		$("#avatar_hover").css('display','');
		return false;
	});
}

/**
 * 头像hover 延时
 */
function setAuthorAvatarTimer(userId,e){
	clearAuthorAvatarTimer();
	authorAvatarTimer = setTimeout("queryUserInfoAndCheckIsZombie("+userId +"," + e.pageX + "," + e.pageY +")",400);
}
/**
 * 头像hover 取消延时
 */
function clearAuthorAvatarTimer(){
	if($("#avatar_hover").length <= 0)return false;//若是没有该div，则不需要设置
	if(authorAvatarTimer !=''){
		clearTimeout(authorAvatarTimer);
	}
	$("#hover_worldCount").text("");
	$("#hover_superb").text("");
	$("#hover_concernCount").text("");
	$("#hover_followCount").text("");
//	$("#hover_platformCode").text("");
	if($("#platformImg").length >0)
		$("#platformImg").replaceWith("");
	$("#hover_signature").text("");
	$("#hover_registerDate").text("");
	$("#hover_userLabel").text("");
	$("#avarta_hover_registe_date").css('color','black');
	$("#avatar_hover").css('display','none');
}

/**
 * 点击预览小头像时，弹出入选频道
 */
function tobeChannelWorld(worldId){
	if($("#channel_world_div").length>0){
		
	}else{
		var divStr = "<div id='channel_world_div' style='dispaly:none'>" +
					 "<table class='htm_edit_table' width='280px'>" +
					 "<tbody>" +
					 "<tr>" +
					 "<td class='leftTd'>频道:</td>" +
					 "<td><input id='htm_channel_id'/></td>" +
					 "</tr>" +
					 "<tr>" +
					 "<td class='leftTd'>织图id:</td>" +
					 "<td><input id='htm_world_id' type='text' readonly='true' style='width:170px;'/></td>" +
					 "</tr>" +
					 "<tr>" +
					 "<td class='opt_btn' colspan='2' style='text-align:center; padding-top:10px;'>" +
					 "<a id='htm_channel_submit'  onclick='submitChannelWorld()'>确定</a>" +
					 "<a id='htm_channel_close'  onclick=\"$('#channel_world_div').window('close');\">取消</a>" +
					 "</td>" +
					 "</tr>" +
					 "</tbody>" +
					 "</table>" +
					 "</div>";
		$("body").append(divStr);
		$("#htm_channel_submit").linkbutton({
			iconCls:'icon-ok'
		});
		$("#htm_channel_close").linkbutton({
			iconCls:'icon-cancel'
		});
		$("#htm_channel_id").combobox({
			valueField:'id',
			textField:'channelName',
			selectOnNavigation:false,
			url:'./admin_op/channel_queryAllChannel',
		});
		$("#channel_world_div").window({
			title:'添加频道织图',
			modal : true,
			width : 280,
			height : 155,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				$("#htm_channel_id").combobox('clear');
			}
		});
	}
	$("#htm_world_id").val(worldId);
	$("#channel_world_div").window('open');
	$("#htm_channel_id").combobox('showPanel');
}

function submitChannelWorld(){
	var channelId = $('#htm_channel_id').combobox('getValue');
	var worldId = $('#htm_world_id').val();
	//该织图进入频道
	$.post("./admin_op/channel_saveChannelWorld",{
		'world.channelId':channelId,
		'world.worldId'  :worldId,
		'world.valid'	 :0,
		'world.notified' :0
	},function(result){
		if(result['result'] == 0){
			
		}else{
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
		$("#channel_world_div").window('close');
		return false;
	},"json");
}


var phoneCodeColumn = {field : 'phoneCode',title : '客户端',align : 'center',
		formatter: function(value,row,index){
			var phone = "IOS";
			if(value == 1) {
				phone = '安卓';
			}
			return "<span class='updateInfo' title='版本号:"+row.appVer+" || 系统:"+row.phoneSys+" v"+row.phoneVer+"'>" 
						+ phone + "</span>";
		}
	},
	authorNameFormatter = function(value, row, index) {
		if(row.authorId != 0) {
			if(row.trust == 1) {
				return "<a title='移出信任列表' class='passInfo pointer' href='javascript:removeTrust(\"" + row.authorId + "\",\"" + index + "\")'>"+value+"</a>";
			}
			return "<a title='添加到信任列表' class='updateInfo pointer' href='javascript:addTrust(\"" + row.authorId + "\",\"" + index + "\")'>"+value+"</a>";
		} else if(baseTools.isNULL(value)) {
			return "织图用户";
		}
	},
	authorNameColumn = {field : 'authorName',title : '作者',align : 'center',formatter: authorNameFormatter},
	authorAvatarColumn = {field : 'authorAvatar',title : '头像',align : 'left', 
			formatter: function(value, row, index) {
				userId = row['authorId'];
				var uri = 'page_user_userInfo?userId='+ userId;
				imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
					content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
				if(row.star >= 1) {
					content = content + "<img title='" + row['verifyName'] + "' class='avatar_tag' src='" + row['verifyIcon'] + "'/>";
				}
				return "<a onmouseover='setAuthorAvatarTimer(" + userId + ",event);' onmouseout='javascript:clearAuthorAvatarTimer();'  class='updateInfo' href='javascript:showUserInfo(\""+uri+"\")'>"+"<span>" + content + "</span>"+"</a>";	
			}
		},
	authorIdColumn = {field:'authorId', title:'作者id',align:'center',
			formatter:function(value,row,index){
				return "<a title='添加等级用户' class='updateInfo' href='javascript:initUserLevelAddWindow(\""+ value + "\",\"" + index + "\")'>"+value+"</a>";
			}
		},
	clickCountColumn = {field : 'clickCount',title:'播放数',align : 'center', sortable: true, editor:'text'},
	commentCountColumn = {field : 'commentCount',title : '评论数',align : 'center',sortable: true,
			formatter : function(value, row, rowIndex ) {
				var uri = 'page_interact_interactWCommentAutoComment?worldId='+row[worldKey];
				return "<a title='显示评论' class='updateInfo' href='javascript:showComment(\""
						+ uri + "\",\""+row[worldKey]+"\")'>"+value+"</a>";
			}
		},
	likeCountColumn = {field : 'likeCount',title:'喜欢数',align : 'center', sortable: true,
		formatter : function(value, row, rowIndex) {
			var uri = 'page_htworld_htworldLiked?worldId='+ row[worldKey]; //喜欢管理地址			
			return "<a title='显示喜欢用户' class='updateInfo' href='javascript:showURI(\"" + uri + "\")'>"+value+"</a>";
		}
	},
	keepCountColumn = {field : 'keepCount',title:'收藏数',align : 'center', sortable: true, 
		formatter : function(value, row, rowIndex) {
			var uri = 'page_htworld_htworldKeep?worldId='+ row[worldKey]; //收藏管理地址			
			return "<a title='显示喜欢用户' class='updateInfo' href='javascript:showURI(\"" + uri + "\")'>"+value+"</a>";
		}
	},
	worldURLColumn = {field : 'worldURL',title : '链接',align : 'center',
		styler: function(value,row,index){ return 'cursor:pointer;';},
		formatter : function(value, row, rowIndex ) {
			var url = value;
			if(value == '' || value == undefined) {
				var slink;
				if(row['shortLink'] == '')
					slink = row[worldKey];
				else 
					slink = row['shortLink'];
				url = worldURLPrefix + slink;
			}
			return "<a title='播放织图' class='updateInfo' href='javascript:showWorld(\""
			+ url + "\")'>"+url+"</a>";

		}
	},
	worldIdAndShowWorldColumn = {field : 'worldId',title : '织图ID',align : 'center', sortable: true, 
			formatter : function(value, row, index) {
				//精品按钮控制
				var superbFlag = 2;//显示添加精品按钮
				var trustFlag = 0;//信任标志
				var latestFlag = 0;//最新
				
				if(row.valid == 0 || row.shield == 1) {
					superbFlag = 0;	//不显示精品按钮
				} else if(row.squarerecd == 1) {
					superbFlag = 1; //显示已经是精品按钮
				} 
				if(row.trust == 1){
					trustFlag = 1;//信任
				}
				if(row.latestValid >= 1){
					latestFlag = 1;
				}
				var wurl;
				if(row['worldURL'] == '' || row['worldURL'] == undefined){
					var slink;
					if(row['shortLink'] == '')
						slink = row[worldKey];
					else 
						slink = row['shortLink'];
					wurl = worldURLPrefix + slink;
				} else {
					wurl = row['worldURL'];
				}
				
				return "<a title='添加互动' class='updateInfo' href='javascript:showWorldAndInteract(\"" 
				+ showWorldAndInteractPage + "?worldId=" + value + "&userId=" + row['authorId'] 
				+ "&index=" + index
				+ "&superbFlag=" +superbFlag + "&trustFlag=" + trustFlag 
				+ "&lastestFlag=" + latestFlag + "&worldLabel=" + row['worldLabel'] + "&shortLink=" + wurl + "\")'>"+value+"</a>";
			},
			styler:function(value,row,index){
				if(row.typeInteracted == 1){
					return 'background-color:#fdf9bb;';
				}
			}
		},
	
	
	worldIdColumn = {field : 'worldId',title : '织图ID',align : 'center', sortable: true, 
			formatter : function(value, row, index) {
				return "<a title='添加互动' class='updateInfo' href='javascript:interact(\"" + value + "\",\"" + index + "\")'>"+value+"</a>";
			},
			styler:function(value,row,index){
				if(row.typeInteracted == 1){
					return 'background-color:#fdf9bb;';
				}
			}
		},
	worldDescColumn = {field : 'worldDesc',title : '织图描述',align : 'center', width : 100,
			formatter: function(value,row,index){
				if(value != null && value != '') {
					return "<a href='#' title=" + value + " class='viewInfo easyui-tooltip'>" + value + "</a>";
				}
				return '';
			}
		},
	worldLabelColumn = {field : 'worldLabel',title : '标签',align : 'center', width : 100, 
		formatter: function(value,row,index){
			if(value != null && value != '') {
				return "<a href='#' title=" + value + " class='viewInfo easyui-tooltip'>" + value + "</a>";
			}
			return '';
		}
	},
	titleThumbPathColumn = {field : 'titleThumbPath',title : '预览',align : 'center',
		formatter: function(value,row,index){
			var imgSrc = baseTools.imgPathFilter(value,'../base/images/bg_empty.png');
			return "<a style='cursor: hand;cursor: pointer;' onclick='javascript:tobeChannelWorld(\""+row.worldId+"\")'> <img width='60px' height='60px' class='htm_column_img' src='" + imgSrc + "' /></a>";
		}
	},
	dateAddedFormatter = function(value,row,index){
		return baseTools.parseDate(value).format("MM/dd hh:mm");
	},
	dateAdded = {field : 'dateAdded', title: '分享日期', align : 'center',formatter:dateAddedFormatter},
	dateModified = {field : 'dateModified', title:'分享日期', align : 'center', formatter: dateAddedFormatter},
	shieldColumn = {field : 'shield',title : '操作',align : 'center',
		formatter: function(value,row,index){
			if(value == 1) {
				img = "./common/images/undo.png";
				return "<img title='解除屏蔽' class='htm_column_img pointer' onclick='javascript:unShield(\""+ row[recordIdKey] + "\",\"" + index + "\")' src='" + img + "'/>";
			}
			if(row.valid == 0) {
				return '';
			}
			return "<a title='点击屏蔽织图' class='updateInfo' href='javascript:shield(\""+ row[recordIdKey] + "\",\"" + index + "\")'>" + '屏蔽'+ "</a>";
		}
	},
	
	recommenderColumn = {field : 'recommender', title:'推荐人', align : 'center',width : 60},
	recommendDateColumn = {field : 'recommendDate', title:'推荐日期', align : 'center',width : 70,
		formatter: function(value,row,index){
			if(value == null || value == '') {
				return '';
			}
			return baseTools.parseDate(value).format("MM/dd");
		}	
	},
	userLevelColumn = {field : 'level_description',title:'用户等级',align : 'center',
		formatter:function(value,row,index){
			if(value){
//				return value.level_description;
				return value;
			}else{
				return '';
			}
		}
	}
	;
		