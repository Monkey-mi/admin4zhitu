<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String autoResponseId= request.getParameter("autoResponseId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自动回复对话</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript">

var maxId = 0,
	dialogQueryParams = {};
	autoResponseId = <%=autoResponseId %>,
//	loadDataURL = "./admin_interact/autoResponse_queryResponseGroupById", //数据装载请求地址
	updatePageContentURL = "./admin_interact/autoResponse_updateCommentContentByRowJson",
	dialogInit = function() {
		toolbarComponent = '#tb';
		dialogQueryParams.autoResponseId = autoResponseId;
		dialogLoadPageData(1);
	};
	var editIndex = undefined;
	function endEditing(){
	    if (editIndex == undefined){
	    	return true; 
	    }
	    if ($('#html_dialog_table').datagrid('validateRow', editIndex)){
	        $('#html_dialog_table').datagrid('endEdit', editIndex);
	        editIndex = undefined;
	        return true;
	    } else {
	        return false;
	    }
	}

	function onClickCell(index, field){
	    if (endEditing()){
	        $('#html_dialog_table').datagrid('selectRow', index)
	                .datagrid('editCell', {index:index,field:field});
	        editIndex = index;
	    }
	}
	
	function dialogLoadPageData(pageNum){
		$("#html_dialog_table").datagrid({
			title  :"自动回复列表",
			width  :970,
			pageSize : [20,50,100,500],
			loadMsg:"加载中....",
			url	   :"./admin_interact/autoResponse_queryResponseGroupById",
			queryParams : dialogQueryParams,
			pagination: true,
			pageNumber: pageNum,
			onClickCell: onClickCell,
			columns: [[
				{field : 'authorName',title: '评论者',align : 'center',width : 110},
				{field : 'commentId',title:'id',width:60},
				{field : 'context', title:'回复内容', width : 660,editor:'text'},
				{field : 'commentDate', title:'评论时间',align : 'center' ,width : 130,
					formatter:function(value,row,index){
						if(value)
							return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
						else
							return "";
					}
				}
				//{field : 'responseId',hidden:true}
				
			]]
		
		});
		var p = $('#html_dialog_table').datagrid('getPager');
		p.pagination({
			beforePageText : "页码",
			afterPageText : '共 {pages} 页',
			displayMsg: '第 {from} 到 {to} 共 {total} 条记录',
			buttons : [{
		        iconCls:'icon-save',
		        text:'保存',
		        handler:function(){
		        	endEditing();
		        	var rows = $("#html_dialog_table").datagrid('getChanges', "updated"); 
		        	$("#html_dialog_table").datagrid('loading');
		        	$.post(updatePageContentURL,{
		        		'rowJson':JSON.stringify(rows)
		        		},function(result){
		        			if(result['result'] == 0) {
		        				$("#html_dialog_table").datagrid('acceptChanges');
		        			} else {
		        				$.messager.alert('失败提示',result['msg']);  //提示失败信息
		        			}
		        			$("#html_dialog_table").datagrid('loaded');
		        		},"json");
		        }
		    },{
		        iconCls:'icon-undo',
		        text:'取消',
		        handler:function(){
		        	$("#html_dialog_table").datagrid('rejectChanges');
		        	$("#html_dialog_table").datagrid('loaded');
		        }
		    }]
		});
	}
	
	/**
	 * 页面初始化成功后载入表格
	 */
	$(function() {
		dialogInit();
	});
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

</script>

</head>
<body>
	<table id="html_dialog_table"></table>
	<div id="tb">
	</div>	
</body>
</html>