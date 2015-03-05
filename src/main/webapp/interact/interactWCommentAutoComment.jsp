<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String interactId = request.getParameter("interactId"); %>
    <% String worldId = request.getParameter("worldId"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图评论管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<link rel="stylesheet" href="${webRootPath }/base/js/jquery/emotion/rl_exp.css" />
<script type="text/javascript" src="${webRootPath }/base/js/jquery/emotion/rl_exp.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer}"></script>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer }" />
<style type="text/css">
body {
		font-size: 14px;
	}
	.xm-bq{margin:10px 0;font-size:14px;color:#333;}
	.xm-bq a{color:#09c;margin:0 5px;}
	.xm-bq a:hover{color:#E10602;}
	.rl_exp {margin-left:10px; width:624px}
	.rl_exp_main {height:130px; overflow-y: scroll;}
	.comment-main{
		text-align:left;
	}
	.comment-main textarea{width:100%;border:1px solid #dcdcdc;}
	.comment-main textarea:focus{outline:none;border-color:#4bf;}
	.comment-main a{font-size:12px;text-decoration:none;color:#09c;}
	.comment-main a:hover{color:#E10602;}
	.none {
		display: none;
	}
	#rl_exp_input {
		width: 650px;
		margin-left:10px;
		margin-top: 15px;
	}
	#rl_bq {
		text-align: center;
	}
	.opt_layout {
		position: absolute;
		display: inline-block;
		top: 8px;
		right: 10px;
	}
</style>

<script type="text/javascript">
	var worldMaxId=0;
	worldId=<%=worldId%>,
	worldQueryparams={},
	userMaxId = 0,
	userQueryParams = {
		'maxId':userMaxId
	},
	worldInit = function() {
		if(worldId != null) {
			worldQueryparams.worldId = worldId;
			worldQueryparams.maxId = worldMaxId;
		}
		worldLoadPageData(1);
	},
	worldOnBeforeInit = function() {
		showPageLoading();
	},
	worldOnAfterInit = function() {
		$('#authorId_add').combogrid({
			panelWidth : 340,
		    panelHeight : 250,
		    loadMsg : '加载中，请稍后...',
			pageList : [4,10,20],
			pageSize : 4,
			toolbar:"#user_tb",
		    multiple : false,
		    required : true,
		   	idField : 'id',
		    textField : 'id',
		    url : './admin_user/user_queryUser',
		    pagination : true,
		    columns:[[
				userAvatarColumn,
				{field : 'id',title : 'ID',align : 'center', width : 60},
				userNameColumn,
				sexColumn
		    ]],
		    queryParams:userQueryParams,
		    onLoadSuccess:function(data) {
		    	if(data.result == 0) {
					if(data.maxId > userMaxId) {
						userMaxId = data.maxId;
						userQueryParams.maxId = userMaxId;
					}
				}
		    },
		});
		var p = $('#authorId_add').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					userMaxId = 0;
					userQueryParams.maxId = userMaxId;
				}
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	function worldLoadPageData(pageNum){
		$("#worldComment").datagrid({
			title  :"评论列表维护",
			width  :970,
			pageSize : [10,20],
			loadMsg:"加载中....",
			toolbar:"#worldTB",
			url	   :"./admin_ztworld/interact_queryComment",
			queryParams : worldQueryparams,
			pagination: true,
			pageNumber: pageNum,
			columns:[[
				{field : 'id',title : 'id',align : 'center',width : 60},
				{field : 'authorAvatar',title : '头像',align : 'center',width : 45, 
					formatter: function(value, row, index) {
						imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
							content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
						if(row.star >= 1) {
							content = content + "<img title='"+row['verifyName']+"' class='avatar_tag' src='"+row['verifyIcon']+"'/>";
						}
						return "<span>" + content + "</span>";	
					}
				},
				{field : 'authorId',title:'用户ID',align : 'center', width:60, editor:'text'},
				{field : 'content', title:'评论内容', align : 'left',width : 640, 
					formatter: function(value,row,index){
						var authorName = "";
						if(row.authorId != 0) {
							if(row.trust == 1) {
								authorName = "<a title='移出信任列表' class='passInfo pointer' href='javascript:removeTrust(\"" + row["authorId"] + "\",\"" + index + "\")'>"+row.authorName+"</a>";
							} else {
								authorName = "<a title='添加到信任列表' class='updateInfo pointer' href='javascript:addTrust(\"" + row["authorId"] + "\",\"" + index + "\")'>"+row.authorName+"</a>";					
							}
						} else if(baseTools.isNULL(value)) {
							authorName = "织图用户";
						}
						return authorName + value;
					}
				},
				{field : 'reply', title : '回复',align : 'center',width : 40,
					formatter: function(value,row,index){
						return "<a title='点击回复评论' class='updateInfo' href='javascript:addReply(\""+ row.id + "\",\""+ row.reAuthorId + "\",\""+ row.authorName + "\")'>" + '回复'+ "</a>";
					}
				},
				{field : 'commentDate', title:'评论日期', align : 'center',width : 75, formatter:function(value,row,index){
					return baseTools.parseDate(value).format("MM/dd hh:mm");
				}},
				{field : 'shield',title : '操作',align : 'center',width : 45,
					formatter: function(value,row,index){
						if(value == 1) {
							img = "./common/images/undo.png";
							return "<img title='解除屏蔽' class='htm_column_img pointer' onclick='javascript:unShield(\""+ row.id + "\",\"" + index + "\")' src='" + img + "'/>";
						}
						if(row.valid == 0) {
							return '';
						}
						return "<a title='点击屏蔽织图' class='updateInfo' href='javascript:shield(\""+ row.id + "\",\"" + index + "\")'>" + '屏蔽'+ "</a>";
					}
				}
			]],
			onLoadSuccess:function(data){
				if(data.result == 0) {
					if(data.maxId > worldMaxId) {
						worldMaxId = data.maxId;
						worldQueryparams.maxId = worldMaxId;
					}
				}
			},
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					worldMaxId = 0;
					worldQueryparams.maxId = worldMaxId;
				}
			},
		});
		var p = $('#worldComment').datagrid('getPager');
		p.pagination({
			beforePageText : "页码",
			afterPageText : '共 {pages} 页',
			displayMsg: '第 {from} 到 {to} 共 {total} 条记录'
		});
		$('#worldComment').datagrid('hideColumn', 'id'); //隐藏id字段
	}
	
	
	/**
	 * 移除信任
	 * 
	 * @param userId
	 * @param index
	 */
	function removeTrust(userId, index) {
		$("#worldComment").datagrid('loading');
		$.post("./admin_user/user_updateTrust",{
			'userId':userId,
			'trust':0,
			},function(result){
				if(result['result'] == 0) {
					updateValue(index,'trust',0);
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
				$("#worldComment").datagrid('loaded');
			},"json");
	}
	
	/**
	 * 添加信任
	 * 
	 * @param userId
	 * @param index
	 */
	function addTrust(userId, index) {
		$("#worldComment").datagrid('loading');
		$.post("./admin_user/user_updateTrust",{
			'userId':userId,
			'trust':1,
			},function(result){
				if(result['result'] == 0) {
					updateValue(index,'trust',1);
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
				$("#worldComment").datagrid('loaded');
			},"json");
	}
	
	/**
	 * 添加回复
	 */
	function addReply(reId, reAuthorId, authorName) {
		initWorldAddWindow();
		if(reAuthorId != 0) 
			$("#authorId_add").combogrid('setValue', reAuthorId);
		$("#reId_add").val(reId);
		$("#rl_exp_input").val(' @'+authorName+" : ");
		// 打开添加窗口
		$("#htm_add").window('open');
	}
	
	/**
	 * 解除屏蔽
	 */
	function unShield(id,index) {
		$("#worldComment").datagrid('loading');
		$.post("./admin_ztworld/interact_unShieldComment",{
			'id':id
			},function(result){
				if(result['result'] == 0) {
					updateValue(index,'shield',0);
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
				$("#worldComment").datagrid('loaded');
			},"json");
	}
	
	/**
	 * 屏蔽织图
	 */
	function shield(id,index) {
		$("#worldComment").datagrid('loading');
		$.post("./admin_ztworld/interact_shieldComment",{
			'id':id
			},function(result){
				if(result['result'] == 0) {
					updateValue(index,'shield',1);
				} else {
					$.messager.alert('失败提示',result['msg']);  //提示失败信息
				}
				$("#worldComment").datagrid('loaded');
			},"json");
	}
	
	
	/**
	 * 更新指定值
	 * @param index
	 * @param key
	 * @param value
	 */
	function updateValue(index, key, value) {
		var $dg = $("#worldComment");
		$dg.datagrid('rejectChanges'); // 取消所有编辑操作
		$dg.datagrid('unselectAll'); // 取消所有选择
		$dg.datagrid('selectRow', index);
		var data = $dg.datagrid('getSelected');
		data[key] = value;
		$dg.datagrid('updateRow',{
			index: index,
			row: data
		});
		$dg.datagrid('refreshRow',index);
		$dg.datagrid('acceptChanges');
		$dg.datagrid('unselectRow',index);
	}
	
	
	
	//初始化添加窗口
	function initWorldAddWindow() {
		$("#rl_exp_input").val('');
		$("#worldId_add").val(worldId);
	}

	/**
	 * 添加评论
	 */
	function addComment() {
		var addForm=$("add_form");
		initWorldAddWindow();
		clearFormData(addForm);
		// 打开添加窗口
		$("#htm_add").window('open');
	}
	function searchUser() {
		userMaxId = 0;
		var userName = $('#ss_user').searchbox('getValue');
		userQueryParams = {
			'maxId' : userMaxId,
			'userName' : userName
		};
		$("#authorId_add").combogrid('grid').datagrid("load",userQueryParams);
	}

	function submitComment() {
		var $form = $('#add_form');
		$form.form('submit', {
		    url:$form.attr("action"),
		    onSubmit: function(){
		    	$("#saveBtn").hide();
				$(".opt_layout .loading").show();
		    	var isValid = $(this).form('validate');
				if (!isValid){
					$("#saveBtn").show();
					$(".opt_layout .loading").hide();
					$.messager.progress('close');	// hide progress bar while the form is invalid
				}
				return isValid;
		    },
		    success:function(data){
		    	$("#saveBtn").show();
				$(".opt_layout .loading").hide();
				var result = $.parseJSON(data);
				if(result['result'] == 0) {
					$('#htm_add').window('close');  //关闭添加窗口
					$.messager.alert('提示',result['msg']);  //提示添加信息成功
					worldQueryparams.maxId = 0;
					worldLoadPageData(1); //重新装载第1页数据
				} else {
					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
				}
		    }
		});
	}

	/**
	*********************************************************************************************
	*上面是world comment,下面是interact comment 我是华丽的分割线
	*********************************************************************************************
	*/
	
	var interactMaxId=0,
	interactId = <%=interactId%>,
	interactQueryParams = {},
	interactCommentUpdatePageURL = "./admin_interact/comment_updateCommentByJSON",
	interactCommentDeleteURI = "./admin_interact/interact_deleteInteractCommentByIds?interactWorldCommentIds=",
	interactInit = function() {		
		interactQueryParams.interactId=interactId,
		interactQueryParams.maxId=interactMaxId;
		interactLoadPageData(1);
	},
	interactOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			interactMaxId = 0;
			interactQueryParams.maxId = interactMaxId;
		}
	},
	interactOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > interactMaxId) {
				interactMaxId = data.maxId;
				interactQueryParams.maxId = interactMaxId;
			}
		}
	};
	function interactLoadPageData(pageNum){
		$("#interactComment").datagrid({
			title  :"评论互动列表",
			width  :970,
			pageSize : [10,20],
			loadMsg:"加载中....",
			toolbar:"#interactTB",
			url	   :"./admin_interact/interact_queryCommentList",
			queryParams : interactQueryParams,
			pagination: true,
			pageNumber: pageNum,
			onClickCell: onClickCell,
			columns:[[
					{field:'ck',checkbox:true},
			  		{field : 'id',title : 'ID',align : 'center', width : 45},
			  		{field : 'commentId',title : '评论ID',hidden:'true'},
					{field : 'userId',title : '用户ID',align : 'center',width : 80},
					{field : 'content',title : '评论',align : 'left',width : 430,editor:'text'},
					{field : 'dateSchedule', title:'计划日期', align : 'center',width : 150, 
						formatter: function(value,row,index){
							return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
						}
					},
					{field : 'dateAdded', title:'添加日期', align : 'center',width : 150, 
						formatter: function(value,row,index){
							return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
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
			  		{field : 'finished',title : '完成',align : 'center', width: 45,
			  			formatter: function(value,row,index) {
			  				if(value == 1) {
			  					img = "./common/images/ok.png";
			  					return "<img title='已完成' class='htm_column_img'  src='" + img + "'/>";
			  				}
			  				img = "./common/images/tip.png";
			  				return "<img title='等待中' class='htm_column_img' src='" + img + "'/>";
			  			}
			  		},
				]],
				onLoadSuccess:interactOnLoadSuccess,
				onBeforeRefresh : interactOnBeforeRefresh
		});
		
		var q = $('#interactComment').datagrid('getPager');
		q.pagination({
			beforePageText : "页码",
			afterPageText : '共 {pages} 页',
			displayMsg: '第 {from} 到 {to} 共 {total} 条记录',
			buttons : [{
		        iconCls:'icon-save',
		        text:'保存',
		        handler:function(){
		        	endEditing();
		        	var rows = $("#interactComment").datagrid('getChanges', "updated"); 
		        	$("#interactComment").datagrid('loading');
		        	$.post(interactCommentUpdatePageURL,{
		        		'interactCommentJSON':JSON.stringify(rows)
		        		},function(result){
		        			if(result['result'] == 0) {
		        				$("#interactComment").datagrid('acceptChanges');
		        			} else {
		        				$.messager.alert('失败提示',result['msg']);  //提示失败信息
		        			}
		        			$("#interactComment").datagrid('loaded');
		        		},"json");
		        }
		    },{
		        iconCls:'icon-undo',
		        text:'取消',
		        handler:function(){
		        	$("#interactComment").datagrid('rejectChanges');
		        	$("#interactComment").datagrid('loaded');
		        }
		    }]
		});
		$('#interactComment').datagrid('hideColumn', 'id'); //隐藏id字段
	}
	
	/**
	*删除
	*/
	function interactDelete(idKey){
		var rows = $('#interactComment').datagrid('getSelections');	
		if(isSelected(rows)){
			$.messager.confirm('删除记录', '您确定要删除已选中的记录?', function(r){ 	
				if(r){				
					var ids = [];
					for(var i=0;i<rows.length;i+=1){		
						ids.push(rows[i][idKey]);	
						rowIndex = $('#interactComment').datagrid('getRowIndex',rows[i]);				
					}	
					$('#interactComment').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
					$('#interactComment').datagrid('loading');
					$.post(interactCommentDeleteURI + ids,function(result){
						$('#interactComment').datagrid('loaded');
						if(result['result'] == 0) {
							$.messager.alert('提示',result['msg'] + ids.length + "条记录！");
							$("#interactComment").datagrid("reload");
						} else {
							$.messager.alert('提示',result['msg']);
						}
						
					});				
				}	
			});		
		}	
	}
	var editIndex = undefined;
	function endEditing(){
	    if (editIndex == undefined){
	    	return true; 
	    }
	    if ($('#interactComment').datagrid('validateRow', editIndex)){
	        $('#interactComment').datagrid('endEdit', editIndex);
	        editIndex = undefined;
	        return true;
	    } else {
	        return false;
	    }
	}

	function onClickCell(index, field){
	    if (endEditing()){
	        $('#interactComment').datagrid('selectRow', index)
	                .datagrid('editCell', {index:index,field:field});
	        editIndex = index;
	    }
	}
	
	//这里需要修改,不明白下面这段代码的意思
	$.extend($.fn.datagrid.methods, {
	    editCell: function(jq,param){
	        return jq.each(function(){
	            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid('getColumnOption', fields[i]);
	                col.editor1 = col.editor;
	                if (fields[i] != param.field){
	                    col.editor = null;
	                }
	            }
	            $(this).datagrid('beginEdit', param.index);
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid('getColumnOption', fields[i]);
	                col.editor = col.editor1;
	            }
	        });
	    }
	});
	
	/**
	 * 显示载入提示
	 */
	function showPageLoading() {
		var $loading = $("<div></div>");
		$loading.text('载入中...').addClass('page_loading_tip');
		$("body").append($loading);
	}

	/**
	 * 移除载入提示
	 */
	function removePageLoading() {
		$(".page_loading_tip").remove();
	}
	/**
	 * 判断是否选中要删除的记录
	 */
	function isSelected(rows) {
		if(rows.length > 0){
			return true;
		}else{
			$.messager.alert('删除失败','请先选择记录，再执行删除操作!','error');
			return false;
		}
	}
	
	/**
	 * 清空表单数据
	 * @param form
	 */
	function clearFormData(form) {
		$(form).find(':input').each(function() {
			switch (this.type) {
			case 'passsword':
			case 'select-multiple':
			case 'select-one':
			case 'text':
			case 'file':
			case 'textarea':
				$(this).val('');
				break;
			case 'checkbox':
			case 'radio':
				this.checked = false;
			}
		});
	}

	
	/**
	 * 页面初始化成功后载入表格
	 */
	$(function() {
		worldOnBeforeInit();
		worldInit();
		interactInit();
		
		$('#htm_add').window({
			title : "添加评论信息",
			modal : true,
			width : 690,
			height : 360,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false
		});
		
		worldOnAfterInit();
	});
</script>

</head>
<body>
  <div id="main">
  
  	<!-- 织图评论 -->
	<div id="worldTB" style="padding:5px;height:auto" class="none">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:addComment();">添加</a>
	</div>
	<!-- 添加记录 -->
	<div id="htm_add">
		<form id="add_form" action="./admin_ztworld/interact_saveComment" method="post">
			<div id="comment" class="comment-main">
				<textarea name="content" id="rl_exp_input" rows="5" class="easyui-validatebox" data-options="required:true"></textarea>
			</div>
			<div class="rl_exp" id="rl_bq" style="display:none;">
				<ul class="rl_exp_tab clearfix">
					<li><a href="javascript:void(0);" class="selected">emoji</a></li>
				</ul>
				<div class="opt_layout">
					<input id="authorId_add" name="authorId" style="width:100px;" />
					<a id="saveBtn" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitComment();" title="保存评论">确定</a>
					<span class="loading none">
					<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
					<span style="vertical-align:middle;">请稍后...</span>
					</span>
					<a class="easyui-linkbutton" iconCls="icon-cancel" title="关闭窗口" onclick="$('#htm_add').window('close');">取消</a>
				</div>
				<ul class="rl_exp_main clearfix rl_selected"></ul>
			</div>
			
			<input class="none" type="text" name="reId" id="reId_add" onchange="validateSubmitOnce=true;" value="0"/>
			<input class="none" type="text" name="worldId" id="worldId_add" onchange="validateSubmitOnce=true;" value="0"/>
		</form>
	</div>
	<div id="user_tb" style="padding:5px;height:auto" class="none">
		<input id="ss_user" searcher="searchUser" class="easyui-searchbox" prompt="用户名/ID搜索" style="width:200px;"/>
	</div>
	<table id="worldComment"></table>
	<div style="height:20px;width:auto"></div>
	
	<!-- 互动评论 -->
	
	<div id="interactTB" style="padding:5px;height:auto" class="none">
		<a href="javascript:void(0);" onclick="interactDelete('id');" class="easyui-linkbutton" title="长处用户等级" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
	</div>
	<table id="interactComment"></table>
  </div>
</body>
</html>