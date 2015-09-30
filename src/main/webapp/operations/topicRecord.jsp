<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主题信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
</head>
<body>
    <h2>主题信息</h2>
    
    <table id="dg" title="主题信息" style="height:400px"></table>
    <div id="toolbar" >
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="destroyUser()">删除</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" >
<!--             <div class="fitem">
                <label>背景色:</label>
                <input name="backgroundColor" class="easyui-textbox"  data-options="width:20px">
            </div> -->
            <div class="fitem">
                <label>文件名:</label>
                <input name="fileName" class="easyui-textbox" >
            </div>
            <div class="fitem">
                <label>主题名:</label>
                <input name="title" class="easyui-textbox">
            </div>
             <div class="fitem">
                <label>前介绍:</label>
                <input name="introduceHead" class="easyui-textbox" >
            </div>
            <div class="fitem">
                <label>后介绍:</label>
                <input name="introduceFoot" class="easyui-textbox">
            </div>
                   <div class="fitem">
                <label>发图按钮:</label>
                <input id="stickerButton"  name="stickerButton"  class="easyui-textbox"  >
            </div>
                   <div class="fitem">	
                <label>分享按钮:</label>
                <input id="shareButton" name="shareButton" class="easyui-textbox" >
            </div>
            <div class="fitem">
                <label>来自织图	:</label>
                <input name="foot" class="easyui-textbox">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
    </div>
    <script type="text/javascript">
        var url;
        function newUser(){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','增加主题');
            $('#fm').form('clear');
            $('#stickerButton').val('我也来一发');
            $('#shareButton').val('分享出去');
            url = './admin_interact/starRecommendTopic_add';
        }
        function editUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('center').dialog('setTitle','Edit User');
                $('#fm').form('load',row);
                url = './admin_interact/starRecommendTopic_update?id='+row.id;
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
                $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
                    if (r){
                        $.post('./admin_interact/starRecommendTopic_destroy',{id:row.id},function(result){
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
        
        $('#dg').datagrid({
            url:"./admin_interact/starRecommendTopic_get",
            toolbar:"#toolbar",
            pagination:true,
            singleSelect:true,
            rownumbers:true,
            fitColumns:true,
            columns:[[
				{field:'id',title:'ID',width:100,align:"center"},
		//		 {field:'backgroundColor',title:'背景色',width:100,align:"center"},
                {field:'fileName',title:'文件名',width:100,align:"center"},
                {field:'title',title:'主题',width:100,align:"center"},
                {field:'introduceHead',title:'前介绍',width:100,align:"center"},
                {field:'introduceFoot',title:'后介绍',width:300,align:"center"},
                {field:'stickerButton',title:'发图按钮',width:100,align:"center"},
                {field:'shareButton',title:'分享按钮',width:100,align:"center"},
                {field:'foot',title:'来自织图',width:100,align:"center"}
            ]]
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
            width:85px;
        }
        .fitem input{
            width:160px;
        }
    </style>
    
    
</body>
</html>