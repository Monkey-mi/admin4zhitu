<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主题信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
</head>
<body>
    
    <table id="dg" title="主题信息" style="height:680px"></table>
    <div id="toolbar" style="display:none">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="destroyUser()">删除</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:500px;height:500px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" style="display:none">
<!--             <div class="fitem">
                <label>背景色:</label>
                <input name="backgroundColor" class="easyui-textbox"  data-options="width:20px">
            </div> -->
            <table>
            	<tr>
            		<td>
            		 <label>banner图:</label>
            		</td>
            		<td>
            					<input class="none" type="text" name="bannerPic" id="channelBanner_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
								<a id="channelBanner_edit_upload_btn" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
								<img id="channelBannerImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="205px" height="90px">
								<div id="channelBanner_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span></div>
            		</td>
            	</tr>
            	<tr>
            		<td>
            		<label>主题名:</label>
            		</td>
            		<td>
            		<textarea rows="2" cols="20" name="title"></textarea>
            		</td>
            	</tr>
            	<tr>
            		<td>
            		<label>前介绍:</label>
            		</td>
            		<td>
            		<textarea rows="2" cols="20" name="introduceHead"></textarea>
            		</td>
            	</tr>
            	  <tr>
            		<td>
            		<label>后介绍:</label>
            		</td>
            		<td>
            		<textarea rows="2" cols="20" name="introduceFoot"></textarea>
            		</td>
            	</tr>
            	<tr>
            		<td>
            		<label>发图按钮:</label>
            		</td>
            		<td>
            		<textarea rows="2" cols="20" name="stickerButton" id="stickerButton" ></textarea>
            		</td>
            	</tr>
            	 <tr>
            		<td>
            		<label>分享按钮:</label>
            		</td>
            		<td>
            		<textarea rows="2" cols="20" name="shareButton" id="shareButton"></textarea>
            		</td>
            	</tr>
            	<tr>
            		<td>
            		<label>来自织图:</label>
            		</td>
            		<td>
            		<textarea rows="2" cols="20" name="foot"></textarea>
            		</td>
            	</tr>            	
            </table>
        </form>
    </div>
    <div id="dlg-buttons" style="display:none;text-align:center">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
    </div>
    <script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
    <script type="text/javascript">
        var url;
        function newUser(){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','增加主题');
    /*          $('#fm').form('clear');  */
            $('#stickerButton').val('我也来一发');
            $('#shareButton').val('分享出去');
            $('#fm').show();
            url = './admin_interact/starRecommendTopic_add';
        }
        function editUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('center').dialog('setTitle','Edit User');
                $('#fm').form('load',row);
                $("#channelBanner_edit").attr('src', row.pics);
                $('#fm').show();
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
            $.messager.defaults = { ok: "当然", cancel: "手抖了" };
            if (row){
                $.messager.confirm('Confirm','同学，你真的要删除这期吗?',function(r){
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
            //fitColumns:true,
            columns:[[
				{field:'id',title:'ID',width:100,align:"center"},
                {field:'title',title:'主题',width:100,align:"center"},
                {field:'bannerPic',title:'banner图',width:100,align:"center",
        			formatter:function(value,row,index) {
        				return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";	
        			}	
                },
                {field:'introduceHead',title:'前介绍',width:100,align:"center"},
                {field:'introduceFoot',title:'后介绍',width:300,align:"center"},
                {field:'stickerButton',title:'发图按钮',width:100,align:"center"},
                {field:'shareButton',title:'分享按钮',width:100,align:"center"},
                {field:'foot',title:'来自织图',width:100,align:"center"},
                {field:'link',title:'访问链接',width:300,align:"center",formatter:function(value,row,index){
                	return "<a style='text-decoration:none;'  href="+value+">" + value + "</a>";
                }}
            ]]
        });
        
        var p = $('#dg').datagrid('getPager'); 
        $(p).pagination({ 
            pageNumber: 1,
            pageSize: 10,//每页显示的记录条数，默认为10 
            pageList: [10,15,20],//可以设置每页记录条数的列表 
            beforePageText: '第',//页数文本框前显示的汉字 
            afterPageText: '页    共 {pages} 页', 
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
        }); 
        
        // 此为频道banner上传组件 
        Qiniu.uploader({
        	runtimes: 'html5,flash,html4',
        	browse_button: 'channelBanner_edit_upload_btn',
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
        			$("#channelBanner_edit_upload_btn").hide();
        			$("#channelBannerImg_edit").hide();
        			var $status = $("#channelBanner_edit_upload_status");
        			// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，banner获取第三个
        			$status.find('.upload_progress:eq(2)').text(0);
        			$status.show();
        			
        		},
        		'BeforeUpload': function(up, file) {
        		},
        		
        		'UploadProgress': function(up, file) {
        			var $status = $("#channelBanner_edit_upload_status");
        			// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，banner获取第三个
        			$status.find('.upload_progress:eq(2)').text(file.percent);
        			
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
        			var key = "op/sticker/test/" + timestamp+suffix;
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
            width:85px;
        }
        .fitem input{
            width:160px;
        }
    </style>
    
    
</body>
</html>