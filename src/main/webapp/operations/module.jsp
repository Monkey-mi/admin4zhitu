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
    <h2>添加小模块信息</h2>
    
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
    
    <table id="dg" title="模块信息" style="height:400px"></table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="destroyUser()">删除</a>
        <input id="topicId" >
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:800px;height:555px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" >
            <div class="fitem">
                <label>小标题:</label>
                <input name="title1" >
            </div>
            <div class="fitem">
                <label>小副标题:</label>
                <input name="title2"  >
            </div>
            <div class="fitem">
                <label>用户ID:</label>
                <input name="userId" > 	
            </div>
             <div class="fitem">
<!--                 <input name="pics"  > -->
<table>
<tr>
<td>
                <label>展示图片:</label>
</td>
<td>
			<input class="none" type="text" name="pic01" id="channelBanner_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
			<a id="pic_edit_upload_btn01" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
			<img id="channelBannerImg_edit01"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="205px" height="90px">
			<div id="channelBanner_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span></div>
</td>
<td>
			<input class="none" type="text" name="pic02" id="channelBanner_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
			<a id="pic_edit_upload_btn02" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
			<img id="channelBannerImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="205px" height="90px">
			<div id="channelBanner_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span></div>
</td>
<td>
			<input class="none" type="text" name="channelBanner" id="channelBanner_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
			<a id="pic_edit_upload_btn03" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
			<img id="channelBannerImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="205px" height="90px">
			<div id="channelBanner_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span></div>
</td>
</tr>
</table>
					
            </div>
            <div class="fitem">
                <label>模块介绍:</label>
<!--                 <input name="intro" > -->
                <textarea rows="4" cols="40" name="intro"></textarea>
            </div>
 			<div class="fitem">
				<label>主题ID:</label> 
				<input id='topicIdIn' name="topicId"  readonly="true">
			</div>
		</form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
    </div>
  
 <script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
    <script type="text/javascript">
        var url;
        
        function newUser(){
        	var topicId = $('#topicId').combobox('getValue');
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','New User');
            $('#fm').form('clear');
            $('#topicIdIn').val(topicId);
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
            fitColumns:true,
            columns:[[
				{field:'id',title:'ID',width:100,align:"center"},
                {field:'title1',title:'小标题',width:100,align:"center"},
                {field:'title2',title:'小副标题',width:100,align:"center"},
                {field:'userId',title:'用户ID',width:100,align:"center"},
                {field:'pics',title:'展示图片',width:100,align:"center"},
                {field:'intro',title:'模块介绍',width:300,align:"center"},
                {field:'topicId',title:'主题ID',width:50,align:"center"}
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
        
        
        // 此为展示图片上传组件 
        Qiniu.uploader({
        	runtimes: 'html5,flash,html4',
        	browse_button: 'pic_edit_upload_btn01',
        	max_file_size: '100mb',
        	flash_swf_url: 'js/plupload/Moxie.swf',
        	chunk_size: '4mb',
        	uptoken_url: './admin_qiniu/uptoken',
        	domain: 'http://static.imzhitu.com/',
        	unique_names: false,
        	save_key: false,
        	auto_start: true,
        	init: {
        		'FilesAdded': function(up, files) {
        			$("#pic_edit_upload_btn01").hide();
        			$("#channelBannerImg_edit01").hide();
        			var $status = $("#channelBanner_edit_upload_status");
        			// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，banner获取第三个
        			$status.find('.upload_progress:eq(0)').text(0);
        			$status.show();
        			
        		},
        		'BeforeUpload': function(up, file) {
        		},
        		
        		'UploadProgress': function(up, file) {
        			var $status = $("#channelBanner_edit_upload_status");
        			// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，banner获取第三个
        			$status.find('.upload_progress:eq(0)').text(file.percent);
        			
        		},
        		'UploadComplete': function() {
        			$("#channelBanner_edit_upload_btn").show();
        			$("#channelBannerImg_edit").show();
        			$("#channelBanner_edit_upload_status").hide();
        		},
        		'FileUploaded': function(up, file, info) {
        			var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
        			$("#channelBannerImg_edit").attr('src', url);
        			$("#channelBanner_edit").val(url);
        		},
        		'Error': function(up, err, errTip) {
        			$.messager.alert('上传失败',errTip);
        		},
        		'Key': function(up, file) {
        			var timestamp = Date.parse(new Date());
        			var suffix = /\.[^\.]+/.exec(file.name);
        			var key = "op/channel/" + timestamp+suffix;
        			return key;
        		}
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