var worldKey = 'worldId',
	uidKey = 'userId',
	currentIndex = 0,
	inValidWorld = 'background-color:#ffe3e3;',
	myRowStyler = function(index,row){
		if(row.valid == 0) {
			return inValidWorld;
		}
	},
	checkLabel = function(node, checked) {
		var $labelIds = $("#labelIds_label");
		var $tree = $labelIds.combotree('tree'),
		length = $tree.tree('getChecked').length;
		if(checked && length > 5) {
			$tree.tree('uncheck',node['target']);
			length = length - 1;
		}
		$("#labelCount_label").text(length);
	};
	
/**
 * 屏蔽织图
 */
function shield(userId,index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_user/user_shieldUser",{
		'userId':userId
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
function unShield(userId,index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_user/user_unShieldUser",{
		'userId':userId
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'shield',0);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 初始化标签更新窗口
 */
function initLabelUpdateWindow(userId, userName, sex, index, isAdd) {
	currentIndex = index;
	$("#userId_label").val(userId);
	$("#userName_label").text(userName);
	var sexName = '未知';
	switch(parseInt(sex)) {
	case 1:
		sexName = '男';
		var $tree = $("#labelIds_label").combotree('tree'),
		nodes = $tree.tree('getRoots');
		$tree.tree('expand',nodes[0].target);
		break;
	case 2:
		sexName = '女';
		var $tree = $("#labelIds_label").combotree('tree'),
			nodes = $tree.tree('getRoots');
		$tree.tree('expand',nodes[1].target);
		break;
	default:
		break;
	}
	$("#sex_label").text(sexName);
	$("#htm_label").window('open');
	
	if(isAdd == 'false') {
		$.post("./admin_user/label_queryLabelIdByUserId",{
			'userId':userId,
			},function(result){
				if(result['result'] == 0) {
					$("#labelIds_label").combotree('setValues', result['labelInfo']);
					$("#label_loading").hide();
					$("#label_form").show();
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
			},"json");
	} else {
		$("#label_loading").hide();
		$("#label_form").show();
	}
}

/**
 * 清空选择的用户标签
 */
function clearLabelSelect() {
	$("#labelIds_label").combotree('clear');
	$("#labelCount_label").text('0');
}

/**
 * 提交标签选择表单
 */
function submitLabelForm() {
	var $form = $("#label_form"),
		$labelIds = $("#labelIds_label"),
		userId = $("#userId_label").val(),
		labelIds = $labelIds.combotree('getValues').join();
		labels = $labelIds.combo('getText');
	if($form.form('validate')) {
		$("#htm_label .opt_btn").hide();
		$("#htm_label .loading").show();
		$.post($form.attr("action"),{
			'userId':userId,
			'labelIds':labelIds,
			'labels':labels,
			'display':false
		},function(result){
				if(result['result'] == 0) {
					updateValue(currentIndex, 'userLabel', labels);
					$('#htm_label').window('close');  //关闭添加窗口
				} else {
					$("#htm_label .opt_btn").show();
					$("#htm_label .loading").hide();
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
			},"json");
	}
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
		'trust':1,
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'trust',1);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
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
		'trust':0,
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'trust',0);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 添加用户互动
 * 
 * @param userId
 * @param index
 */
function interactUser(userId, index) {
	$("#htm_uinteract form").hide();
	$("#uinteract_loading").show();
	$("#htm_uinteract").window('open');
	$.post("./admin_interact/interact_queryUserInteractByUID",{
		'userId':userId
	},function(result){
		if(result['result'] == 0) {
			$("#followSum_uinteract").text(result['userInfo']["followCount"]);
			$("#uinteract_loading").hide();
			var $addForm = $('#uinteract_form');
			$addForm.show();
			$("#htm_uinteract loading").hide();
			$("#htm_uinteract opt_btn").show();
			commonTools.clearFormData($addForm);
			$("#userId_uinteract").val(userId);
			$("#duration_uinteract").val('24');
		} else {
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
	},"json");
	loadUInteractFormValidate(userId, index);
}

//提交表单，以后补充装载验证信息
function loadUInteractFormValidate(userId, index) {
	var addForm = $('#uinteract_form');
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				//验证成功后以异步方式提交表单
				$('#htm_uinteract .opt_btn').hide();
				$('#htm_uinteract .loading').show();
				//验证成功后以异步方式提交表单
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						formSubmitOnce = true;
		            	$('#htm_uinteract .opt_btn').show();
						$('#htm_uinteract .loading').hide();
						if(result['result'] == 0) {
							$('#htm_uinteract').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							commonTools.clearFormData(addForm);  //清空表单数据	
							updateValue(index, 'interacted', 1);
							$("#htm_table").datagrid('refreshRow', index);
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");	
				return false;
			}
		}
	});
	
	$("#userId_uinteract")
	.formValidator({empty:false,onshow:"请输入用户ID",onfocus:"例如:10012",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#duration_uinteract")
	.formValidator({empty:false,onshow:"小时",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#followCount_uinteract")
	.formValidator({empty:true,onshow:"请输入粉丝数",onfocus:"例如:120",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
}


var userIdColumn = {field : uidKey,title : '用户ID',align : 'center', sortable: true, width : 60,
		formatter : function(value, row, index) {
			return "<a title='添加互动' class='updateInfo' href='javascript:interactUser(\"" + value + "\",\"" + index + "\")'>"+value+"</a>";
		}
	},
	userNameFormatter = function(value, row, index) {
		if(row.authorId != 0) {
			if(row.trust == 1) {
				return "<a title='移出信任列表' class='passInfo pointer' href='javascript:removeTrust(\"" + row[uidKey] + "\",\"" + index + "\")'>"+value+"</a>";
			}
			return "<a title='添加到信任列表' class='updateInfo pointer' href='javascript:addTrust(\"" + row[uidKey] + "\",\"" + index + "\")'>"+value+"</a>";
		} else if(baseTools.isNULL(value)) {
			return "织图用户";
		}
	},
	userNameColumn = {field : 'userName',title : '昵称',align : 'center',width : 100,formatter: userNameFormatter},
	userAvatarColumn = {field : 'userAvatar',title : '头像',align : 'left',width : 45,
		formatter: function(value, row, index) {
			imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
				content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
			if(row.star >= 1) {
				content = content + "<img title='" + row['verifyName'] + "' class='avatar_tag' src='" + row['verifyIcon'] + "'/>";
			}
			return "<span>" + content + "</span>";	
		}		
	},
	sexFormatter = function(value, row, index) {
		if(value == 1) {
			return "男";
		} else if(value == 2) {
			return "女";
		}
		return "";
	},
	sexColumn = {field : 'sex',title:'性别',align : 'center', width:40, formatter: sexFormatter},
	platformCodeFormatter = function(value, row, index) {
		var platformCode = "织图";
		switch(value) {
		case 2:
			platformCode = "新浪";
			break;
		case 3:
			platformCode = "人人";
			break;
		case 4:
			platformCode = "QQ空间";
			break;
		default:
			break;
		}
		return platformCode;
	},
	platformCodeColumn = {field : 'platformCode',title:'社交平台',align : 'center', width:60, formatter: platformCodeFormatter},
	concernCountColumn = {field : 'concernCount',title:'关注',align : 'center', width : 80,
		formatter : function(value, row, rowIndex ) {
			var uri = 'page_user_concern?userId='+ row[uidKey]; //评论管理地址			
			return "<a title='显示关注' class='updateInfo' href='javascript:showURI(\""
					+ uri + "\")'>"+value+"</a>";
		}
	},
	followCountColumn = {field : 'followCount',title:'粉丝',align : 'center', width : 60, 
		formatter : function(value, row, rowIndex ) {
			var uri = 'page_user_follow?userId='+ row[uidKey]; //评论管理地址			
			return "<a title='显示粉丝' class='updateInfo' href='javascript:showURI(\""
					+ uri + "\")'>"+value+"</a>";
		}		
	},
	worldCountColumn = {field : 'worldCount',title:'织图',align : 'center', width : 60},
	likedCountColumn = {field : 'likedCount',title:'喜欢',align : 'center', width : 60},
	keepCountColumn = {field : 'keepCount',title:'收藏',align : 'center', width : 60},
	phoneCodeColumn = {field : 'phoneCode',title : '客户端',align : 'center',width : 50,
			formatter: function(value,row,index){
				var phone = "IOS";
				if(value == 1) {
					phone = '安卓';
				}
				return "<span class='updateInfo' title='版本号:"+row.ver+" || 系统:"+row.phoneSys+" v"+row.phoneVer+"'>" 
					+ phone + "</span>";
			}
		},
	signatureColumn = {field : 'signature', title:'签名',align:'center', width:120},
	shieldColumn = {field : 'shield',title : '操作',align : 'center',width : 45,
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
	registerDateColumn = {field : 'registerDate', title:'注册日期', align : 'center',width : 120,
		formatter: function(value,row,index){
			if(value == null || value == '') {
				return '';
			}
			return new baseTools.parseDate(value).format("yyyy/MM/dd hh:mm"); 
		}	
	},
	recommenderColumn = {field : 'recommender', title:'推荐人', align : 'center',width : 60},
	recommendDateColumn = {field : 'recommendDate', title:'推荐日期', align : 'center',width : 70,
		formatter: function(value,row,index){
			if(value == null || value == '') {
				return '';
			}
			return new Date(value).format("MM/dd"); 
		}	
	},
	userLabelColumn = {field : 'userLabel', title:'标签', align : 'center',width : 70,
			formatter: function(value,row,index){
				if(value == '' || value == null) {
					img = "./common/images/edit_add.png";
					return "<img title='添加标签' class='htm_column_img pointer' onclick='javascript:initLabelUpdateWindow(\""+ row[uidKey] + "\",\"" + row.userName + "\",\"" + row.sex + "\",\"" + index + "\",\"" + 'true' + "\")' src='" + img + "'/>";
				}
				return "<a title='" + value + "(点击更新)' class='updateInfo pointer' onclick='javascript:initLabelUpdateWindow(\""
				+ row[uidKey] + "\",\"" + row.userName + "\",\"" + row.sex + "\",\"" + index + "\",\"" + 'false' + "\")'>" + value + "</a>";
			}	
		};
		