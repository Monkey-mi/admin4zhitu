<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加小模块信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
</head>
<body>
    <h2>Add Information</h2>
    
<!--     <table id="dg" title="My Users" class="easyui-datagrid" style="width:900px;height:400px"
             url="./admin_interact/addModule_get"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th data-options="field:'title1',width:100">Title1</th>
                <th data-options="field:'title2',width:100">Title2</th>
                <th data-options="field:'userId',width:100">UserId</th>
                <th data-options="field:'pics',width:100">Pics</th>	
                <th data-options="field:'intro',width:100">Intro</th> 
            </tr>
        </thead>
    </table> -->
    
    <table id="dg" title="My Users" style="width:1000px;height:400px"></table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove User</a>
        <input id="topicId" value="">
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle"> Information</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>Title1:</label>
                <input name="title1" class="easyui-textbox" >
            </div>
            <div class="fitem">
                <label>Title2:</label>
                <input name="title2" class="easyui-textbox" >
            </div>
            <div class="fitem">
                <label>UserId:</label>
                <input name="userId" class="easyui-textbox">
            </div>
             <div class="fitem">
                <label>Pics:</label>
                <input name="pics" class="easyui-textbox" >
            </div>
            <div class="fitem">
                <label>Intro:</label>
                <input name="intro" class="easyui-textbox">
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
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','New User');
            $('#fm').form('clear');
            url = './admin_interact/addModule_add';
        }
        function editUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('center').dialog('setTitle','Edit User');
                $('#fm').form('load',row);
                url = './admin_interact/addModule_update?id='+row.id;
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
                        $.post('./admin_interact/addModule_destroy',{id:row.id},function(result){
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
            url:"./admin_interact/addModule_get",
            method:'post',
            toolbar:"#toolbar",
            pagination:true,
            singleSelect:true,
            rownumbers:true,
         //   fitColumns:true,
            columns:[[
				{field:'id',title:'ID',width:100,align:"center"},
                {field:'title1',title:'Title1',width:100,align:"center"},
                {field:'title2',title:'Title2',width:100,align:"center"},
                {field:'userId',title:'UserId',width:100,align:"center"},
                {field:'pics',title:'Pics',width:100,align:"center"},
                {field:'intro',title:'Intro',width:300,align:"center"},
                {field:'topicId',title:'TopicId',width:50,align:"center"}
            ]]
        });
        
        $("#topicId").combobox({
        	url:"./admin_interact/starRecommendTopic_get",
        	valueField:'id',
        	textField:'id',
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