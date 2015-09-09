<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String worldId = request.getParameter("worldId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图评论计划互动页面</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript">

	var worldId = <%= worldId %>;

	var maxId=0;
	hideIdColumn = false;
	htmTablePageList = [25,50,100];

	loadDataURL = "./admin_interact/comment_queryCommentListByLabel";	//数据装载请求地址
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	};
	
	// 定义工具栏
	toolbarComponent = "#comment_tb";
	
	columnsFields = [
		{field : 'ck',checkbox : true },
        {field:'id',title:'ID',width:60},
        {field:'content',title:'内容',width:400},
        {field:'opt', title:'操作', align:'center', width:60,
        	formatter: function(value,row,index){
    			return "<a title='点击更新评论' class='updateInfo' href='javascript:addComment(\""+ row['id'] + "\",\"" + index + "\")'>" + '更新'+ "</a>";
    		}	
        }
    ];
	
	onAfterInit = function(){
		
		$("#htm_table").datagrid({
		    onSelect:function(index,row){
		    	$('#selectionCount').text($("#htm_table").datagrid("getSelections").length);
		    },
		    onUnselect:function(index,row){
		    	$('#selectionCount').text($("#htm_table").datagrid("getSelections").length);
		    }
		});
		
		$("#labelId_interact").combotree({
			url:'./admin_interact/comment_queryLabelTree?hasTotal=true',
			onSelect:function(rec) {
				loadComment(rec.id,rec.labelName);
			}
		});
		
		$('#ss_searchLabel').combobox({
			valueField:'id',
			textField:'labelName',
			selectOnNavigation:false,
			url:'./admin_interact/comment_queryAllLabel',
			icons:[{
                iconCls:'icon-add'
            },{
                iconCls:'icon-cut'
            }],
			onSelect:function(record){
				loadComment(record.id,record.labelName);
				$('#ss_searchLabel').combobox('clear');
			}
		});
		
		
		/*  
		mishengliang
		2015-09-08
		*/
		
		$("#addComment").window({
			title : '批量添加评论',
			modal : true,
			width : 420,
			height : 190,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false,
		});
	};
	
	/**
	 * 搜索评论 
	 */
	function searchComment() {
		var comment = $("#ss_comment").searchbox("getValue");
		myQueryParams.maxId = 0;
		myQueryParams.comment = comment;
		myQueryParams.labelId = 0;
		$("#htm_table").datagrid('load',myQueryParams);
		$("#ss_comment").searchbox('clear');
	};
	
	/**
	 * 加载评论
	 */
	function loadComment(labelId,labelName) {
		myQueryParams.maxId = 0;
		myQueryParams.labelId = labelId;
		myQueryParams.comment = "";
		$("#htm_table").datagrid('load',myQueryParams);
	};

	/**
	 * 添加评论
	 */
	function addComment(id, index) {
		$.fancybox({
			'width'				: '70%',
			'height'			: '75%',
			'autoScale'			: true,
			'type'				: 'iframe',
			'href'				: "page_interact_addComment?id="+id,
			'titlePosition'		: 'inside',
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'hideOnOverlayClick': false,
			'showCloseButton'   : false,
			'titleShow'			: false,
			'onClosed'			:function(){
				$("#htm_table").datagrid('reload');
			}
		});
	};
	
	/**
	 * 选择互动评论添加到计划中
	 */
	function submitComment() {
		var rows = $("#htm_table").datagrid("getSelections");
		var ids = [];
		for (var i=0; i<rows.length; i++) {
			ids.push(rows[i].id);
		}
		var params = {
			comments:ids.toString(),
			worldId:worldId,
			duration:120	// duration固定参数设置为120，意为2小时
		};
		$.post("./admin_interact/interact_saveInteract", params, function(result){
			if(result['result'] == 0) {
				$("#htm_table").datagrid("clearSelections");
				$('#selectionCount').text(0);
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示失败信息
			}
		});
	};
	
	//打开添加文件的窗口
	function addComments(){
		$('#addComment').window('open');
		$('#zombieWorldId').val(worldId);
	}
	
	//mishengliang
	//点击确定添加后的操作
	function addComment(){
		var addCommentsFile = $('#addCommentsFile');
		$('#addComment').window('close');
		addCommentsFile.ajaxSubmit({
			success:function(result){
			if (result['result'] == 0) {
				$.messager.alert('提示',"成功！");
			} else {
				$.messager.alert('提示',result['msg']);
			}
			},
			url : addCommentsFile.attr('action'),
			type : 'post',
			dataType : 'json'
		});
	}
	
</script>
</head>
<body>

	<table id="htm_table"></table>
	
	<div id="comment_tb" style="padding:5px;" class="none">
		<a href="#htm_add_comment" onclick="addComment(0);" class="easyui-linkbutton" style="vertical-align:middle;" title="添加评论" plain="true" iconCls="icon-add" id="addCommentBtn">添加</a>
				<a href="#htm_add_comment" onclick="addComments();" class="easyui-linkbutton" style="vertical-align:middle;" title="添加评论" plain="true" iconCls="icon-add" id="addCommentBtn">批量添加评论</a>
		<a href="#htm_submit_comment" onclick="submitComment();" class="easyui-linkbutton" style="vertical-align:middle;" title="计划互动评论" plain="true" iconCls="icon-ok" id="addCommentBtn">计划互动评论</a>
		<span>已选：<span id="selectionCount">0</span></span>
		<span>&nbsp;&nbsp;</span>
		<div style="display: inline-block;margin-right: 5px; margin-top:3px;">
			<input id="labelId_interact" name="labelId" style="width:120px;" />
			<input id="ss_searchLabel" style="width:120px;" ></input>
			<input id="ss_comment" searcher="searchComment" class="easyui-searchbox" prompt="搜索评论" style="width:150px;"></input>
			</div>
		<div id="worldLabelDiv" style="margin:8px 0 5px 3px;"></div>
	</div>

  <div id='addComment'>
			<form id='addCommentsFile' action="" method='post'>
			<table class="htm_edit_table" width="340">
			<tbody>
			<tr>
				<td style='height:100'>
				马甲织图ID：<input type="text" id="zombieWorldId" name='zombieWorldId' readonly="readonly">
				</td>
			</tr>
<!-- 			<tr>
				<td>织图等级&nbsp;&nbsp;&nbsp;: <input style="width:120px" class="easyui-combobox" id="levelId" name="id"  onchange="validateSubmitOnce=true;" 
					data-options="valueField:'id',textField:'level_description',url:'./admin_interact/worldlevel_QueryoWorldLevel'"/></td>
			</tr> -->
			<tr>
				<td style='height:100' >
				<input id='commentsFile' type="file" name='commentsFile' >
				</td>
			</tr>
			<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 20px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addComment();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#addComment').window('close');">取消</a>
						</td>
			</tr>
			</tbody>
			</table>
			</form>
		</div> 
</body>
</html>