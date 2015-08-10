<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>精品马甲管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<%-- <jsp:include page="../common/CRUDHeader.jsp"></jsp:include>  --%>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript">
	var maxId=0,
	loadDateUrl="./admin_op/zombie_queryZombie",
	addUrl="./admin_op/zombie_insertZombie",
	updateUrl = "./admin_op/zombie_updateZombie?id=",
	delUrl="./admin_op/zombie_batchDeleteZombie?idsStr=",
	tableQueryParams = {},
	tableInit = function() {
		tableLoadDate(1);
	};
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			tableQueryParams.maxId = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				tableQueryParams.maxId = maxId;
			}
		}
	};
	
	//显示用户织图
	//add by mishengliang 07-31-2015
	function showURI(uri){
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
	
	/*
	add by mishengliang 08-03-2015
	增加点击单元格处理函数
	*/
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
	
	var editIndex = undefined;
	function endEditing(){
	    if (editIndex == undefined){
	    	return true; 
	    }
	    if ($('#htm_table').datagrid('validateRow', editIndex)){
	        $('#htm_table').datagrid('endEdit', editIndex);
	        editIndex = undefined;
	        return true;
	    } else {
	        return false;
	    }
	}

	function onClickCell(index, field){
	    if (endEditing()){
	        $('#htm_table').datagrid('selectRow', index)
	                .datagrid('editCell', {index:index,field:field});
	        editIndex = index;
	    }
	}
	
	var Address = [{ "value": "1", "text": "男" }, { "value": "2", "text": "女" }];
	
	//mishengliang end	
	
	function tableLoadDate(pageNum){
		
		sexFormatter = function(value, row, index) {
			if(value == 1) {
				return "男";
			} else if(value == 2) {
				return "女";
			}
			return "";
		},
		
		$("#htm_table").datagrid({
			title  :"精品马甲管理",
			width  :1200,
			pageList : [10,30,50,100,300],
			pageSize : 10,
			loadMsg:"加载中....",
			url	   :	loadDateUrl,
			queryParams : tableQueryParams,
			remoteSort: true,
			pagination: true,
			idField   :'id',
			pageNumber: pageNum,
			toolbar:'#tb',
		  onClickCell:onClickCell, /*mishengliang*/
			columns: [[
 				{field :'ck',checkbox:true}, 
				{field : 'userAvatar',title : '头像',align : 'left',width : 40,
					formatter: function(value, row, index) {
						imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
							content = "<img width='30px' height='30px' class='htm_column_img' src='" + imgSrc + "'/>";
						if(row.star >= 1) {
							content = content + "<img title='" + row['verifyName'] + "' class='avatar_tag' src='" + row['verifyIcon'] + "'/>";
						}
						return "<span>" + content + "</span>";	
					}		
				},
				{field :'id',title:'ID',align:'center',width:80},
				{field :'userId',title:'马甲ID',align:'center',width:80},
				{field :'userName',title:'马甲名称',align:'center',width:80},
				{field : 'sex',title:'性别',align : 'center', width:40, formatter: sexFormatter,editor: { type: 'combobox', options: { data: Address, valueField: "value", textField: "text" },panelHeight:10}},
				{field : 'signature', title:'签名',align:'center', width:100,editor:'text'},
				{field : 'degreeName',title: '等级名称',align : 'center',width : 180},
				{field : 'concernCount',title:'关注',align : 'center', width : 80,
						formatter : function(value, row, rowIndex ) {
							var uri = 'page_user_concern?userId='+ row.userId; 			
							return "<a title='显示关注' class='updateInfo' href='javascript:showURI(\""
									+ uri + "\")'>"+value+"</a>";
						}
					},
					{field : 'followCount',title:'粉丝',align : 'center', width : 60, 
						formatter : function(value, row, rowIndex ) {
							var uri = 'page_user_follow?userId='+ row.userId; 	
							return "<a title='显示粉丝' class='updateInfo' href='javascript:showURI(\""
									+ uri + "\")'>"+value+"</a>";
						}		
					},
				{field : 'worldCount',title: '织图',align : 'center',width : 180,
					formatter: function(value,row,index){
						var uri = "page_user_userWorldInfo?userId="+row.userId;
						return "<a title='显示织图' class='updateInfo' href='javascript:showURI(\""+uri
							+"\")'>"+value+"</a>"; 
					}	
				},
				{field : 'userAvatarL',title : '大头像',align : 'left',width : 70,
					formatter: function(value, row, index) {
						imgSrc = baseTools.imgPathFilter(value,'../base/images/no_avatar_ssmall.jpg'),
							content = "<img width='60px' height='60px' class='htm_column_img' src='" + imgSrc + "'/>";
						return "<span>" + content + "</span>";	
					}		
				},
			]],
			onLoadSuccess:myOnLoadSuccess,
			onBeforeRefresh : myOnBeforeRefresh
		});
		var p = $("#htm_table").datagrid("getPager");
		p.pagination({
			buttons : [{
		        iconCls:'icon-save',
		        text:'保存',
		        handler:function(){
		        	endEditing();
		        	var rows = $("#htm_table").datagrid('getChanges', "updated"); 
		        	var updateSignatureURL = "./admin_op/zombieUpdate_updateSexAndSignature";
		        	$("#htm_table").datagrid('loading');
		        	$.post(updateSignatureURL,{
		        		'zombieInfoJSON':JSON.stringify(rows)
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
		    }]
		});
	}
	
	
	$(function(){
		$('#htm_add').window({
			title : '添加马甲等级',
			modal : true,
			width : 490,
			height : 170,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function(){
				$("#i-degreeId").combobox('clear');
				$("#i-userId").val('');
				$("#i-id").val('');		
			}
		});
		
		tableInit();
		$("#main").show();
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
	* 批量删除
	*/
	function del(){
		$.messager.confirm('更新缓存','确定要将选中的内容删除?',function(r){
			if(r){
				update(delUrl);
			}
		});
	}
	
	
	
	function update(url){
		var rows = $('#htm_table').datagrid('getSelections');	
		var ids = [];
		for(var i=0;i<rows.length;i+=1){		
			ids.push(rows[i]['id']);	
		}	
		$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
		$('#htm_table').datagrid('loading');
		$.post(url+ids,function(result){
			$('#htm_table').datagrid('loaded');
			if(result['result'] == 0) {
				$.messager.alert('提示',result['msg']);
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('提示',result['msg']);
			}
			
		});	
	}
	
	/**
	 * 判断是否选中要删除的记录
	 */
	function isSelected(rows) {
		if(rows.length > 0){
			return true;
		}else{
			$.messager.alert('操作失败','请先选择记录，再执行操作!','error');
			return false;
		}
	}
	
	
	function addInit(){
		$('#htm_add').window('open');
	}
	
	function updateInit(id,userId){
		$("#i-id").val(id);
		$("#i-userId").val(userId);
		$('#htm_add').window('open');
	}
	
	function addSubmit(){
		var degreeId = $("#i-degreeId").combobox('getValue');
		var userId = $("#i-userId").val();
		var id = $("#i-id").val();
		var url="";
		if(id){
			url = updateUrl+id;
		}else{
			url = addUrl;
		}
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		$.post(url,{
			'degreeId':degreeId,
			'userId':userId
		},function(result){
			$('#htm_add .opt_btn').show();
			$('#htm_add .loading').hide();
			if(result['result'] == 0) {
				tableQueryParams.maxId=0;
				$("#htm_table").datagrid("reload");
				$('#htm_add').window('close');
			} else {
				$.messager.alert('提示',result['msg']);
			}
		},"json");
	}
	
	function searchByUserId(){
		var userId = $("#ss_userId").searchbox('getValue');
		tableQueryParams.userId = userId;
		$("#htm_table").datagrid("load",tableQueryParams);
	}
	
</script>
</head>
<body>
	<div id="main" class="none">
		<div id="tb">
			<a href="javascript:void(0);" onclick="javascript:addInit();" class="easyui-linkbutton" title="添加" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<input id="ss_userId" type="text" class="easyui-searchbox" data-options="searcher:searchByUserId,iconCls:'icon-search',prompt:'请填写用户ID'" style="width:200px"></input>
		</div>
		<table id="htm_table"></table>
		<!-- 添加记录 -->
		<div id="htm_add">
			<form id="add_form"  method="post">
				<table class="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">用户ID：</td>
							<td>
								<input id="i-userId" style="width:220px;">
							</td>
						</tr>
						<tr>
							<td class="leftTd">等级名称：</td>
							<td>
								<input id="i-degreeId" style="width:220px;" class="easyui-combobox" 
									data-options="valueField:'id',textField:'degreeName',url:'./admin_op/zbDegree_queryAllZombieDegree'" >
							</td>
						</tr>
						<tr>
							<td class="none"><input id="i-id"></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit();">添加</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	
</body>
</html>