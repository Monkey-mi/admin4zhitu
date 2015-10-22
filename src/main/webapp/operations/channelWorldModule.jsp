<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加小模块信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
</head>
<body>
    <table id="dg" title="模块信息" style="height:680px"></table>
    <div id="toolbar"  style="display:none">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="destroyUser()">删除</a>
        <input id="i-topicId" >
    </div>
    
<div id="dlg" class="easyui-dialog" style="width:460px;height:400px;padding:10px 20px;"
            closed="true" buttons="#dlg-buttons"  >
<form id="fm" method="post"  style="display:none">
        
 <table border="0">
<tr>
<td>
                <label>小标题:</label>
</td>
<td colspan="4">
<textarea rows="2"  name="title"  id="title"></textarea>
</td>
</tr>
<tr>
<td>
                <label>小副标题:</label>
</td>
<td colspan="4">
<textarea rows="2"  name="subtitle"  id="subtitle"></textarea>
</td>
</tr>
<tr>
<td>
                <label>用户ID:</label>
</td>
<td colspan="4">
<textarea rows="2"  name="userId" id="userId"></textarea>
</td>
</tr>
<tr>
<td>
                <label>织图ID:</label>
</td>
<td colspan="4">
<textarea rows="2"  name="worldId" id="worldId"></textarea>
</td>
</tr>
<tr>
<td>
                <label>模块介绍:</label>
</td>
<td colspan="4">
<textarea rows="2"  name="intro" id="intro"></textarea>
</td>
</tr>
<tr>
<td>
                <label>主题ID:</label>
</td>
<td colspan="4">
<textarea rows="2"  id="topicIdIn"  name="topicId" readonly ></textarea>
</td>
</tr>
</table> 
</form>
    </div>
    <div id="dlg-buttons" style="display:none;text-align:center" >
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px" >Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
    </div>
  
 <script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
    <script type="text/javascript">
        var url;
        var myQueryParams = {isWorld:0};
        var maxId = 0;
        
        function newUser(){
        	var topicId = $('#i-topicId').combobox('getValue');
        	if(topicId == ""){
        		$.messager.alert("温馨提示","请先选择主题");
        		return;
        	}
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','新建模块信息');
           
            $('#channelImg_edit01').attr('src','${webRootPath }/base/images/bg_empty.png');
            $('#channelImg_edit02').attr('src','${webRootPath }/base/images/bg_empty.png');
            $('#channelImg_edit03').attr('src','${webRootPath }/base/images/bg_empty.png');
            $('#channelImg_edit04').attr('src','${webRootPath }/base/images/bg_empty.png');
            $('#title').val('');
            $('#subtitle').val('');
            $('#userId').val('');
            $('#intro').val('');
            $('#topicIdIn').val(topicId);
            $('#fm').show();
            url = './admin_interact/addModule_addWorldModule';
        }
        
        function editUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑模块');
                $('#fm').form('load',row);
                $("#channelImg_edit01").attr('src', row.pics);
                $("#channelImg_edit02").attr('src', row.pic02);
                $("#channelImg_edit03").attr('src', row.pic03);
                $("#channelImg_edit04").attr('src', row.pic04);
                $('#fm').show();
                url = './admin_interact/addModule_updateWorldModule?id='+row.id;
            }
        }
        function saveUser(){
            $('#fm').form('submit',{
                url: url,
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.errorMsg){
                        $.messager.show({
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#dlg').dialog('close');        // close the dialog
                        $('#dg').datagrid('reload');    // reload the user data
                    }
                }
            });
        }
        function destroyUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('Confirm','确定要删除吗?',function(r){
                    if (r){
                        $.post('./admin_interact/addModule_destroyWorldModule',{id:row.id},function(result){
                            if (result.result == 0){
                                $('#dg').datagrid('reload');    // reload the user data
                            } else {
                                $.messager.show({    // show error message
                                    title: 'Error',
                                    msg: result.errorMsg
                                });
                            }
                        },'json');
                    }
                });
            }
        }
        
        
    	 var myOnBeforeRefresh = function(pageNumber, pageSize) {
   		if(pageNumber <= 1) {
   			maxId = 0;
   			myQueryParams.maxId = maxId;
   		}
   	}; 
   	
    	 var  myOnLoadSuccess = function(data) {
   		if(data.result == 0) {
   			if(data.maxId > maxId) {
   				maxId = data.maxId;
   				myQueryParams.maxId = maxId;
   			}
   		}
   	}; 
        
        $('#dg').datagrid({
            url:"./admin_interact/addModule_getWorldModule",
            method:'post',
            toolbar:"#toolbar",
            pagination:true,
/*        singleSelect:true, */
            rownumbers:true,
            fitColumns:true,  
            queryParams:myQueryParams,
            onLoadSuccess:myOnLoadSuccess, 
            width: $(document.body).width(),
            columns:[[
				{field:'id',title:'ID',width:100,align:"center"},
                {field:'title',title:'小标题',width:100,align:"center"},
                {field:'subtitle',title:'小副标题',width:100,align:"center"},
                {field:'userId',title:'用户ID',width:60,align:"center"},
                {field:'worldId',title:'织图ID',width:60,align:"center"},
                {field:'intro',title:'模块介绍',width:300,align:"center"},
                {field:'topicId',title:'主题ID',width:50,align:"center"}            ]]
        });
        
        var p = $('#dg').datagrid('getPager'); 
        $(p).pagination({ 
            pageNumber: 1,
            pageSize: 10,//每页显示的记录条数，默认为10 
            pageList: [10,15,20],//可以设置每页记录条数的列表 
            beforePageText: '第',//页数文本框前显示的汉字 
            afterPageText: '页    共 {pages} 页', 
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
    		buttons:pageButtons,
    		onBeforeRefresh:myOnBeforeRefresh,
        }); 

        $("#i-topicId").combobox({
        	url:"./admin_interact/starRecommendTopic_get?isWorld=1",
        	valueField:'id',
        	textField:'title',
        	onSelect:function(rec){
        		$("#dg").datagrid('load',{topicId:rec.id});
        	}
        });
    </script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .fitem input{
            width:160px;
        }
    </style>
</body>
</html>