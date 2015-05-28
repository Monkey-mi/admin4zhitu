<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定账号与马甲</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

	var maxId=0;

	loadDataURL = "./admin_op/worldChannel_queryRecyclerdData";	//数据装载请求地址
	myQueryParams.channelId = baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID"); // 从cookie中获取频道id，并赋值给数据装载请求参数
	
	// 指定idfield
	myIdField = "channelWorldId";
	// 定义要隐藏的列
	recordIdKey = "channelWorldId";
	
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
	toolbarComponent = [{
		id: 'recover_btn',
		text: '恢复',
		iconCls: 'icon-reload',
		handler: function(){
			debugger;
			var rows = $('#htm_table').datagrid('getSelections');
			var ids = [];
			for(var i=0;i<rows.length;i++){		
				ids.push(rows[i].channelWorldId);
			}
			$.post('./admin_op/worldChannel_setValidOperation', {'ids' : ids.toString(), isValid:true}, function(data){
				$.messager.alert("提示", data.msg);
				$('#htm_table').datagrid('reload');
			});
		}
	},{
		id: 'recover_btn',
		text: '永久删除',
		iconCls: 'icon-cut',
		handler: function(){
			$.messager.confirm("提示", "是否彻底删除？", function(r){
				if (r){
					var rows = $('#htm_table').datagrid('getSelections');
					var ids = [];
					for(var i=0;i<rows.length;i++){	
						ids.push(rows[i].channelWorldId);
					}
					$.post('./admin_op/worldChannel_setEmptyByIds', {'ids' : ids.toString()}, function(data){
						$.messager.alert("提示", data.msg);
						$('#htm_table').datagrid('reload');
					});
				}
			});
		}
	}];
	
	columnsFields = [
	     			{field:'checkbox',checkbox:'true',align:'center'},
	    			{field:'channelWorldId',title:'ID',width:80},      
	    	        {field:'worldId',title:'织图ID',width:100,sortable:true},
	    	        {field:'thumbPath',title:'缩略图',width:350,
	    	        	formatter: function(value,row,index){
	    	        		return "<img src='"+value+"'>";
	    	        	}
	    	        },
	    	        {field:'worldDesc',title:'织图描述',width:150},
	    	        {field:'authorId',title:'用户ID',width:100},
	    	        {field:'deleteReason',title:'删除理由',width:150},
	    	        {field:'updateTime',title:'处理时间',width:150}
	    	    ];
	
function search(value,name){
	if ( value == undefined || value == "" ) {
		$.messager.alert("提示","请填写内容，再进行搜索");
	} else {
		var reg = /^[0-9]*[1-9][0-9]*$/;	// 正整数
		if ( !reg.test(value) && (name == "world_id" || name == "user_id") ) {
			$.messager.alert("提示","id不能包含字符，请输入数字！");
		} else {
			if ( name == "worldId" ) {
				myQueryParams.worldId = value;
			} else if ( name == "authorId" ) {
				myQueryParams.authorId = value;
			} else if ( name == "keyword" ) {
				myQueryParams.keyword = value;
			}
		}
		$('#htm_table').datagrid('load');
	}
}

</script>
</head>
<body>
	<div style="height:20px"></div>
	<input id="searchBox" class="easyui-searchbox" style="width:400px"
        data-options="searcher:search,prompt:'Please Input Value',menu:'#selectData'"></input>
        
    <div id="selectData" style="width:120px">
        <div data-options="name:'worldId'">织图ID</div>
        <div data-options="name:'authorId'">用户ID</div>
        <div data-options="name:'keyword'">关键字</div>
    </div>
	<div style="height:20px"></div>
	<table id="htm_table"></table>
</body>
</html>