<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类备选管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<script type="text/javascript">
var maxId = 0,
	hideIdColumn = false,
	toolbarComponent = '#tb',
	init = function() {
		myQueryParams = {
		},
		loadPageData(1);
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
		}
	},
	myRowStyler = function(index, row) {
		if(row.interacted == 1) {
			return 'background-color:#e3fbff';
		}
	},
	htmTableTitle = "分类织图列表", //表格标题
	htmTableWidth = 1410,
	worldKey = "worldId",
	loadDataURL = "./admin_interact/typeOptionWorld_queryTypeOptionWorldForList", //数据装载请求地址
	deleteURI = "./admin_interact/typeOptionWorld_delTypeOptionWorldByIds?idsStr=", //删除请求地址
	updateValidURL = "./admin_interact/typeOptionWorld_updateTypeOptionWorld?ids=", // 更新有效新请求地址
	addWidth = 500, //添加信息宽度
	addHeight = 160, //添加信息高度
	addTitle = "添加分类织图", //添加信息标题
	
	columnsFields = [
  		{field : 'ck',checkbox : true},
  		{field : 'authorAvatar',title : '头像',align : 'left',width : 45, 
			formatter: function(value, row, index) {
				userId = row['userId'];
				var uri = 'page_user_userInfo?userId='+ userId;
				imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
					content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
				if(row.star >= 1) {
					content = content + "<img title='" + row['verifyName'] + "' class='avatar_tag' src='" + row['verifyIcon'] + "'/>";
				}
				return "<a onmouseover='setAuthorAvatarTimer(" + userId + ",event);' onmouseout='javascript:clearAuthorAvatarTimer();'  class='updateInfo' href='javascript:showUserInfo(\""+uri+"\")'>"+"<span>" + content + "</span>"+"</a>";	
			}
		},
  		{field:'userId', title:'作者id',align:'center',width:60,
			formatter:function(value,row,index){
				return "<a title='添加等级用户' class='updateInfo' href='javascript:initUserLevelAddWindow(\""+ value + "\",\"" + index + "\")'>"+value+"</a>";
			}
		},
  		{field : 'userName',title : '作者',align : 'center',width : 100,formatter: function(value, row, index) {
			if(row.authorId != 0) {
				if(row.trust == 1) {
					return "<a title='移出信任列表.\n推荐人:"
						+row.trustOperatorName+"\n最后修改时间:"
						+row.trustModifyDate+"' class='passInfo pointer' href='javascript:removeTrust(\"" 
						+ row.authorId + "\",\"" + index + "\")'>"
						+value
						+ "<sup><span style='border: solid 1px red;webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;-webkit-box-shadow: #666 0px 0px 10px;-moz-box-shadow: #666 0px 0px 10px;box-shadow: #666 0px 0px 10px;'>"
						+row.trustOperatorId+"</span></sup></a>";
				}else if(row.trustOperatorId == 0){
					return "<a title='添加到信任列表' class='updateInfo pointer' href='javascript:addTrust(\"" + row.authorId + "\",\"" + index + "\")'>"+value+"</a>";
				}
				return "<a title='移出信任列表.\n删除信任的人:"
						+row.trustOperatorName+"\n最后修改时间:"
						+row.trustModifyDate+"' class='updateInfo pointer' href='javascript:addTrust(\"" 
						+ row.authorId + "\",\"" + index + "\")'>"
						+value
						+ "<sup><span style='border: solid 1px red;webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;-webkit-box-shadow: #666 0px 0px 10px;-moz-box-shadow: #666 0px 0px 10px;box-shadow: #666 0px 0px 10px;'>"
						+row.trustOperatorId+"</span></sup></a>";
			} else if(baseTools.isNULL(value)) {
				return "织图用户";
			}
		}},
		{field : 'userLevelDesc',title:'用户等级',align : 'center',width:60,
			formatter:function(value,row,index){
				if(value){
					return value.level_description;
				}else{
					return '';
				}
			}
		},
  		clickCountColumn,
  		likeCountColumn,
  		commentCountColumn,
  		worldURLColumn,
  		worldIdColumn,
  		titleThumbPathColumn,
  		{field : 'reView',title:'点评',align : 'center',width:160,
			formatter:function(value,row,index){
				var img = "./base/js/jquery/jquery-easyui-1.3.2/themes/icons/pencil.png";
				var str = "<img title='修改' class='htm_column_img pointer' src='" + img + "' onclick='updateReviewInit(\""+row.worldId+"\",\""+index +"\")'/>";
				if(value == undefined || value == "") {
					return str;
				} else if(value.length > 20) {
					return value.substr(0,18) +"..."+str;
				} else {
					return value+str;
				}
			}
		},
  		worldLabelColumn,
  		{field : 'channelName',title :'频道',align : 'center',width:100,
  			formatter: function(value,row,index) {
  				if(value == "NO_EXIST" || value=="") {
  					img = "./common/images/edit_add.png";
  					return "<img title='已添加' class='htm_column_img'  src='" + img + "' onclick='saveToChannelWorld(\""+row.worldId+"\",\""+index +"\")'/>";
  				}
  				
  				return value;
  			}
		},
  		{field : 'valid',title : '有效性',align : 'center', width: 45,
  			formatter: function(value,row,index) {
  				if(value == 1) {
  					img = "./common/images/ok.png";
  					return "<img title='已生效' class='htm_column_img'  src='" + img + "'/>";
  				}
  				img = "./common/images/tip.png";
  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
  			}
  		},
  		{field : 'superb',title : '精品',align : 'center',width : 45,
  			formatter: function(value,row,index){
  				if(value == 1) {
  					img = "./common/images/undo.png";
  					return "<img title='从精品列表选除' class='htm_column_img pointer' onclick='javascript:removeSuperb(\""+ row.id + "\",\"" + index + "\")'  src='" + img + "'/>";
  				}
  				img = "./common/images/edit_add.png";
  				return "<img title='添加到精品列表' class='htm_column_img pointer' onclick='javascript:addSuperb(\""+ row.id + "\",\"" + index + "\")' src='" + img + "'/>";
  			}
  		},
  		{field : 'top',title : '置顶',align : 'center',width : 45,
  			formatter: function(value,row,index){
  				if(value > 0) {
  					img = "./common/images/undo.png";
  					return "<img title='从置顶列表选除' class='htm_column_img pointer' onclick='javascript:removeTop(\""+ row.id + "\",\"" + index + "\")'  src='" + img + "'/>";
  				}
  				img = "./common/images/edit_add.png";
  				return "<img title='添加到置顶列表' class='htm_column_img pointer' onclick='javascript:addTop(\""+ row.id + "\",\"" + index + "\")' src='" + img + "'/>";
  			}
  		},
  		{field : 'addDate', title:'添加时间',align : 'center' ,width : 130,
			formatter:function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		},
  		{field : 'operatorName',title : '最后操作者',align : 'center',width : 70}
  		
    ],
    onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		$('#htm_superb').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 245,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-tip',
			resizable : false
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
		
		$("#labelId_interact").combotree({
			url:'./admin_interact/comment_queryLabelTree?hasTotal=true',
			onSelect:function(rec) {
				loadComment(rec.id,rec.labelName);
			}
		});
		$('#labelId').combotree({
			multiple:true,
			url:'./admin_interact/comment_queryAllLabelTree'
		});
		$('#labelIdSearch').combobox({
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
		
		$('#htm_interact').window({
			modal : true,
			width : 500,
			top : 10,
			height : 310,
			title : '添加互动信息',
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
			onClose : function() {
				$('#comments_interact').combogrid('clear');
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
		
		$('#typeId_type').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_ztworld/label_queryLabelForCombobox',
		});
		
		$('#htm_type').window({
			modal : true,
			width : 500,
			top : 10,
			height : 170,
			title : '添加到分类',
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
			}
		});
		
		$("#schedula").datetimebox({
			showSeconds: false
		});
		
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
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function(){
				$("#channelId").combobox('clear');
				$("#worldId_channel").val('');
				$("#rowIndex").val('');
			}
		});
		
		$("#htm_updateReview").window({
			title : '频道织图添加',
			modal : true,
			width : 420,
			height : 200,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-pencil',
			resizable : false,
			onClose : function(){
				$("#reviewContent").val('');
				$("#worldId_review").val('');
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	

/**
 * 使选中的分类有效
 */
function updateValid() {
	var rows = $('#htm_table').datagrid('getSelections');	
	if(rows.length > 0){
		$.messager.confirm('更新记录', '您确定要更新已选中的记录有效性?', function(r){ 	
			if(r){				
				var ids = [];
				var wids = [];
				for(var i=0;i<rows.length;i+=1){		
					ids.push(rows[i]['id']);
					wids.push(rows[i]['worldId']);
					rowIndex = $('#htm_table').datagrid('getRowIndex',rows[i]);				
				}	
				$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
				$('#htm_table').datagrid('loading');
				$.post(updateValidURL + ids+"&wids="+wids,function(result){
					$('#htm_table').datagrid('loaded');
					if(result['result'] == 0) {
						$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
						$("#htm_table").datagrid("reload");
					} else {
						$.messager.alert('提示',result['msg']);
					}
				});				
			}	
		});	
	}else{
		$.messager.alert('更新失败','请先选择记录，再执行更新操作!','error');
	}
			
}

/**
 * 添加精品
 */
function addSuperb(id,index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/type_updateTypeWorldSuperb",{
		'id':id,
		'superb' : 1
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'superb',1);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 移除精品
 */
function removeSuperb(id,index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_ztworld/type_updateTypeWorldSuperb",{
		'id':id,
		'superb' : 0
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'superb',0);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 添加权重
 */
function addTop(id,index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_interact/typeOptionWorld_updateTypeOptionWorld",{
		'id':id,
		'top' : 1
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'top',1);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}

/**
 * 移除权重
 */
function removeTop(id,index) {
	$("#htm_table").datagrid('loading');
	$.post("./admin_interact/typeOptionWorld_updateTypeOptionWorld",{
		'id':id,
		'top' : 0
		},function(result){
			if(result['result'] == 0) {
				updateValue(index,'top',0);
			} else {
				$.messager.alert('失败提示',result['msg']);  //提示失败信息
			}
			$("#htm_table").datagrid('loaded');
		},"json");
}
	

/**
 * 重新排序
 */
function reSuperb() {
	$("#superb_form").find('input[name="reIndexId"]').val('');
	$("#schedula").datetimebox('clear');
	$('#htm_superb .opt_btn').show();
	$('#htm_superb .loading').hide();
	
	var rows = $("#htm_table").datagrid('getSelections');
	$('#superb_form .reindex_column').each(function(i){
		if(i<rows.length)
			$(this).val(rows[i]['worldId']);
	});
	
	// 打开添加窗口
	$("#htm_superb").window('open');
	
}

function submitReSuperbForm() {
	var $form = $('#superb_form');
	if($form.form('validate')) {
		$('#htm_superb .opt_btn').hide();
		$('#htm_superb .loading').show();
		$('#superb_form').form('submit', {
			url: $form.attr('action'),
			success: function(data){
				var result = $.parseJSON(data);
				$('#htm_superb .opt_btn').show();
				$('#htm_superb .loading').hide();
				if(result['result'] == 0) { 
					$('#htm_superb').window('close');  //关闭添加窗口
					mySortName = "serial";
					mySortOrder = "desc";
					loadPageData(1);
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
				
			}
		});
	}
	
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
					'worldType':"旅行",
					'typeId':"1",
					'isAdd':isAdd
					},function(result){
						formSubmitOnce = true;
						$("#htm_type .opt_btn").show();
						$("#htm_type .loading").hide();
						if(result['result'] == 0) {
							updateValues(index,['recommenderId','worldType'],['1','旅行']);
						} else {
							$.messager.alert('失败提示',result['msg']);  //提示失败信息
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
						 $('#htm_type').window('close');  //关闭添加窗口
						 updateValues(index,['worldLabel'],[worldType]);
					 }else{
						 $.messager.alert('失败提示',result['msg']);  //提示失败信息
					 }
				 },"json");
				return false;
			}
		}
	});
	
	$("#typeId_type")
	.formValidator({empty:false,onshow:"请选择分类",onfocus:"设置分类",oncorrect:"设置成功"});;
}

	
//初始化添加窗口
function initAddWindow() {
	var addForm = $('#add_form');
	clearFormData(addForm);
	$("#typeId_add").combobox('setValue','1');
	$("#worldIds_add").focus();  //光标定位
	
}


//提交表单，以后补充装载验证信息
function loadAddFormValidate() {
	var addForm = $('#add_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : addForm.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$('#htm_table').datagrid('loading');
				//验证成功后以异步方式提交表单
				$.post(addForm.attr("action"),addForm.serialize(),
					function(result){
						$('#htm_table').datagrid('loaded');
						if(result['result'] == 0) {
							$('#htm_add').window('close');  //关闭添加窗口
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							clearFormData(addForm);  //清空表单数据	
							loadPageData(1); //重新装载第1页数据
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#worldIds_add")
	.formValidator({empty:false,onshow:"请输入织图IDs",onfocus:"例如:12,13",oncorrect:"设置成功"})
	.inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"由数字组成"});
	
	$("#typeId_add")
	.formValidator({empty:false,onshow:"请选择分类"});
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

function updateReviewSubmit(){
	var index = $("#revieRowIndex").val();
	var worldId = $("#worldId_review").val();
	var review  = $("#reviewContent").val();
	$.post("./admin_interact/typeOptionWorld_updateTypeOptionWorldReview",{
		'worldId':worldId,
		'review':review
	},function(result){
		if(result['result'] == 0){
			updateValue(index,'reView',review);
			$("#htm_updateReview").window('close');
		}else{
			$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
		}
	},"json");
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

function updateReviewInit(worldId,index){
	$("#revieRowIndex").val(index);
	$("#worldId_review").val(worldId);
	$("#reviewContent").val('');
	$('#htm_updateReview .opt_btn').hide();
	$('#htm_updateReview .loading').show();
	$("#htm_updateReview").window('open');
	$('#htm_updateReview .opt_btn').show();
	$('#htm_updateReview .loading').hide();
	
}

/**
 * 查询广场
 */
function searchTypeOptionWorld() {
	myQueryParams.maxId = 0;
	myQueryParams.valid = $('#ss-valid').combobox('getValue');
	myQueryParams.top = $("#ss-top").combobox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}


</script>
</head>
<body>
	<div id="main" style="display: none;">
	<table id="htm_table"></table>
	
	<div id="tb" style="padding:5px;height:auto" class="none">
		<div>
			<!-- <a href="javascript:htmUI.htmWindowAdd();" class="easyui-linkbutton" title="添加织图到分类" plain="true" iconCls="icon-add" id="addBtn">添加</a> -->
			<a href="javascript:void(0);" onclick="javascript:htmDelete(recordIdKey);" class="easyui-linkbutton" title="删除分类织图" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<a href="javascript:void(0);" onclick="javascript:reSuperb();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">重新排序</a>
			<select id="ss-valid" class="easyui-combobox" name="valid" style="width:100px;">
		        <option value="">有效和无效</option>
		        <option value="1">有效</option>
		        <option value="0">无效</option>
	   		</select>
	   		<select id="ss-top" class="easyui-combobox" name="weight" style="width:100px;">
		        <option value="">置顶和非置顶</option>
		        <option value="1">置顶</option>
		        <option value="0">非置顶</option>
	   		</select>
	   		<select id="ss-isSorted" class="easyui-combobox" name="isSorted" style="width:120px;">
	   			<option value="">已排序和未排序</option>
	   			<option value="0">未排序</option>
	   			<option value="1">已排序</option>
	   		</select>
	   		<a href="javascript:void(0);" onclick="javascript:searchTypeOptionWorld();" class="easyui-linkbutton" plain="true" iconCls="icon-search" id="searchBtn">查询</a>
   		</div>
	</div> 

	<!-- 重排精品 -->
	<div id="htm_superb">
		<form id="superb_form" action="./admin_interact/typeOptionWorld_reSort" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td>
							<input name="reIndexId" class="easyui-validatebox reindex_column" required="true"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">计划更新时间：</td>
						<td><input id="schedula" name="schedula" class="easyui-datetimebox"></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReSuperbForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_superb').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">排序中...</span>
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
	

	<!-- 添加互动 -->
	<div id="htm_interact">
		<span id="interact_loading" style="margin:140px 0 0 220px; position:absolute; display:none;  ">加载中...</span>
		<form id="interact_form" action="./admin_interact/worldlevelList_addWorldlevelList" method="post" class="none">
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
						<td><input type="text" name="comment_ids" id="comments_interact" style="width:205px;"/></td>
						<td class="rightTd"><div id="comments_interactTip" class="tipDIV">已选：<span id="selected_comment_count">0</span></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图id：</td>
						<td><input type="text" id="worldId_interact" name="world_id"  onchange="validateSubmitOnce=true;" readonly="readonly" /></td>
						<td class="rightTd"><div id="worldId_Tip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">织图等级：</td>
						<td><input style="width:204px" class="easyui-combobox" id="levelId" name="world_level_id"  onchange="validateSubmitOnce=true;" 
							data-options="valueField:'id',textField:'level_description',url:'./admin_interact/worldlevel_QueryoWorldLevel'"/></td>
						<td class="rightTd"><div id="levelId_Tip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">评论标签：</td>
						<td><input style="width:204px"  name="label_ids" id="labelId" onchange="validateSubmitOnce=true;" /></td>
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
	
	<div id="type_tb">
		<form id="activity_form" action="./admin_op/op_saveSquarePushActivityWorld" method="post" class="none">
			<table class="htm_edit_table" width="280">
				<tbody>
					<tr>
						<td class="leftTd">活动：</td>
						<td>
							<input id="activityIds_activity" name="ids" type="text" readonly="readonly" style="width:205px;"/>
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
		<span id="type_loading" style="margin:60px 0 0 220px; position:absolute; ">加载中...</span>
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
	
	<!-- 添加到频道 -->
	<div id="htm_channel">
		<span id="channel_loading" style="margin:60px 0 0 220px; position:absolute; display:none">加载中...</span>
		<form id="channel_form" action="./admin_op/channel_saveChannelWorld" method="post" >
			<table class="htm_edit_table" width="400">
				<tbody>
					<tr>
						<td class="leftTd">频道名称：</td>
						<td ><input id="channelId" name="world.channelId" class="easyui-combobox" 
							data-options="valueField:'id',textField:'channelName',url:'./admin_op/channel_queryAllChannel'"/></td>
					</tr>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td >
							<input id="worldId_channel" name="world.worldId" type="text" readonly="readonly" style="width:171px;"/>
						</td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="world.valid" id="valid_add" value="1" /></td>
					</tr>
					<tr class="none">
						<td colspan="2"><input type="text" name="world.notified" id="notified_add" value="0" /></td>
					</tr>
					<tr class="none">
						<td colspan="2"><input id="rowIndex" /></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="saveChannelWorldSubmit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_channel').window('close');">取消</a>
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
	
	<!-- 更新精选点评 -->
	<div id="htm_updateReview">
		<span id="review_loading" style="margin:60px 0 0 220px; position:absolute; display:none">加载中...</span>
		<form id="review_form" action="./admin_interact/typeOptionWorld_updateTypeOptionWorldReview" method="post" >
			<table class="htm_edit_table" width="400">
				<tbody>
					<tr>
						<td class="leftTd">精选点评：</td>
						<td ><textarea id="reviewContent" name="review" 
							style="width:171px;height:50px;multiple:true;" rows="2" cols="20" onKeyDown='if (this.value.length>=20){event.returnValue=false}'></textarea>
						</td>
					</tr>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td >
							<input id="worldId_review" name="worldId" type="text" readonly="readonly" style="width:171px;"/>
						</td>
					</tr>
					<tr class="none">
						<td colspan="2"><input id="revieRowIndex" /></td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="updateReviewSubmit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_updateReview').window('close');">取消</a>
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
	
	<div id="comment_tb" style="padding:5px;height:auto" class="none">
		<a href="#htm_add_comment" onclick="addComment(0);" class="easyui-linkbutton" style="vertical-align:middle;" title="添加评论" plain="true" iconCls="icon-add" id="addCommentBtn">添加</a>
		<a href="javascript:void(0);" onclick="deleteComment();" class="easyui-linkbutton" style="vertical-align:middle;" title="删除评论" plain="true" iconCls="icon-cut">删除</a>
		<div style="display: inline-block;float: right; margin-right: 5px; margin-top:3px;">
			<span class="search_label">评论标签：</span><input id="labelId_interact" name="labelId" style="width:120px;" />
			<span class="search_label">搜索标签：</span><input id="ss_searchLabel" style="width:100px;"></input>
			<input id="ss_comment" searcher="searchComment" class="easyui-searchbox" prompt="搜索评论" style="width:100px;"></input>
   		</div>
	</div>
	</div>
</body>
</html>