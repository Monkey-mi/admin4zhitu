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
        <a href="javascript:void(0);" onclick="javascript:reSuperb();" class="easyui-linkbutton" title="重排排序" plain="true" iconCls="icon-converter" id="reIndexedBtn">重新排序</a>
        <input id="i-topicId" >
    </div>
    
<div id="dlg" class="easyui-dialog" style="width:680px;height:450px;padding:10px 20px;"
            closed="true" buttons="#dlg-buttons"  >
<form id="fm" method="post"  style="display:none">
        
 <table border="0">
<tr>
<td>
                <label>展示图片:</label>
</td>
<td style="height: 90px;">
						<input class="none" type="text" name="pics" id="channelIcon_edit01"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
						<a id="pic_edit_upload_btn01" style="position: absolute; margin:30px 0 0 10px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
						<img id="channelImg_edit01"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90" height="90px">
						<div id="channelIcon_edit_upload_status01" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
						</div>
</td>
<td style="height: 90px;">
						<input class="none" type="text" name="pic02" id="channelIcon_edit02"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
						<a id="pic_edit_upload_btn02" style="position: absolute; margin:30px 0 0 10px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
						<img id="channelImg_edit02"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90" height="90px">
						<div id="channelIcon_edit_upload_status02" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
						</div>
</td>
<td style="height: 90px;">
						<input class="none" type="text" name="pic03" id="channelIcon_edit03"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
						<a id="pic_edit_upload_btn03" style="position: absolute; margin:30px 0 0 10px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
						<img id="channelImg_edit03"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90" height="90px">
						<div id="channelIcon_edit_upload_status03" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
						</div>
</td>
<td style="height: 90px;">
						<input class="none" type="text" name="pic04" id="channelIcon_edit04"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
						<a id="pic_edit_upload_btn04" style="position: absolute; margin:30px 0 0 10px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
						<img id="channelImg_edit04"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90" height="90px">
						<div id="channelIcon_edit_upload_status04" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
						</div>
</td>
</tr>
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
  
  	<!-- 重排模块 -->
	<div id="htm_superb">
		<form id="superb_form" action="./admin_interact/addModule_reOrderIndexforPic" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">织图ID：</td>
						<td>
							<input name="reIndexId" class="easyui-validatebox reindex_column" required="true"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
						</td>
					</tr>
<!-- 					<tr>
						<td class="leftTd">计划更新时间：</td>
						<td><input id="schedula" name="schedula" class="easyui-datetimebox"></td>
					</tr> -->
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReSuperbForm();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_superb').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">排序中...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
  
 <script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
    <script type="text/javascript">
        var url;
        var myQueryParams = {isWorld:0};
        var maxId = 0;
        
        /**
         * 重新排序
         */
        function reSuperb() {
        	$("#superb_form").find('input[name="reIndexId"]').val('');
        	$("#schedula").datetimebox('clear');
        	$('#htm_superb .opt_btn').show();
        	$('#htm_superb .loading').hide();
        	
        	var rows = $("#dg").datagrid('getSelections');
        	$('#superb_form .reindex_column').each(function(i){
        		if(i<rows.length)
        			$(this).val(rows[i]['id']);
        	});
        	
        	// 打开添加窗口
        	$("#htm_superb").window('open');
        	
        }
        
        function submitReSuperbForm() {
        	var $form = $('#superb_form');
        	if($form.form('validate')) {
        		$('#htm_superb .opt_btn').hide();
        		$('#htm_superb .loading').show();
        		$('#superb_form').form('submit', {
        			url: $form.attr('action'),
        			success: function(data){
        				var result = $.parseJSON(data);
        				$('#htm_superb .opt_btn').show();
        				$('#htm_superb .loading').hide();
        				if(result['result'] == 0) { 
        					$('#htm_superb').window('close');  //关闭添加窗口
        					mySortName = "serial";
        					mySortOrder = "desc";
        					$('#dg').datagrid('unselectAll');
        					$('#dg').datagrid('load');
        				} else {
        					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
        				}
        				
        			}
        		});
        	}
        	
        }
        
        
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
            url = './admin_interact/addModule_add';
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
            var rows = $('#dg').datagrid('getSelections');
            var ids = [];
            if (rows != "" && rows != null){
                $.messager.confirm('Confirm','确定要删除吗?',function(r){
                    if (r){
                    	for(var i = 0; i < rows.length; i++){
                    		ids[i] = rows[i].id;
                    	}
                    	var idsArr = ids.join(",");
                        $.post('./admin_interact/addModule_destroy',{idsArr:idsArr},function(result){
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
   		
		$('#htm_superb').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 145,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-tip',
			resizable : false
		});
   	}; 
        
        
        $('#dg').datagrid({
            url:"./admin_interact/addModule_get",
            method:'post',
            toolbar:"#toolbar",
            pagination:true,
/*        singleSelect:true, */
            rownumbers:true,
            fitColumns:true,  
            idField:'id',
            queryParams:myQueryParams,
            onLoadSuccess:myOnLoadSuccess, 
            width: $(document.body).width(),
            columns:[[
				 {field:'id',title:'ID',width:100,align:"center",sortable: true,hidden:true},
				{field : 'ck',checkbox : true },
                {field:'title',title:'小标题',width:100,align:"center"},
                {field:'subtitle',title:'小副标题',width:100,align:"center"},
                {field:'userId',title:'用户ID',width:100,align:"center"},
                {field:'pics',title:'展示图片',width:100,align:"center",
        			formatter:function(value,row,index) {
        				return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
        			}	
                },
                {field:'pic02',title:'展示图片02',width:100,align:"center",
        			formatter:function(value,row,index) {
        				if(value != null){
        				return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
        				}
        			}	
                },
                {field:'pic03',title:'展示图片03',width:100,align:"center",
        			formatter:function(value,row,index) {
        				if(value != null){
        				return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
        				}
        			}	
                },
                {field:'pic04',title:'展示图片04',width:100,align:"center",
        			formatter:function(value,row,index) {
        				if(value != null){
        				return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";
        				}
        			}	
                },
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
        	url:"./admin_interact/starRecommendTopic_get?isWorld=0",
        	valueField:'id',
        	textField:'title',
        	loadFilter:function(data){
        		return data.rows;
        	},
        	onSelect:function(rec){
        		$("#dg").datagrid('load',{topicId:rec.id});
        	}
        });
        
        
  // 此为展示图片上传组件 01
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
            	$("#channelImg_edit01").hide();
            	var $status = $("#channelIcon_edit_upload_status01");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            },
            'BeforeUpload': function(up, file) {
            },
            'UploadProgress': function(up, file) {
            	var $status = $("#pic_edit_upload_btn01");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(file.percent);
            },
            'UploadComplete': function() {
            	$("#pic_edit_upload_btn01").show();
            	$("#channelImg_edit01").show();
            	$("#channelIcon_edit_upload_status01").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
            	$("#channelImg_edit01").attr('src', url);
            	$("#channelIcon_edit01").val(url);
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
  
	  // 此为展示图片上传组件 02
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'pic_edit_upload_btn02',
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
            	$("#pic_edit_upload_btn02").hide();
            	$("#channelImg_edit02").hide();
            	var $status = $("#channelIcon_edit_upload_status02");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            },
            'BeforeUpload': function(up, file) {
            },
            'UploadProgress': function(up, file) {
            	var $status = $("#pic_edit_upload_btn02");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(file.percent);
            },
            'UploadComplete': function() {
            	$("#pic_edit_upload_btn02").show();
            	$("#channelImg_edit02").show();
            	$("#channelIcon_edit_upload_status02").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
            	$("#channelImg_edit02").attr('src', url);
            	$("#channelIcon_edit02").val(url);
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
	
	 // 此为展示图片上传组件 03
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'pic_edit_upload_btn03',
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
            	$("#pic_edit_upload_btn03").hide();
            	$("#channelImg_edit03").hide();
            	var $status = $("#channelIcon_edit_upload_status03");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            },
            'BeforeUpload': function(up, file) {
            },
            'UploadProgress': function(up, file) {
            	var $status = $("#pic_edit_upload_btn03");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(file.percent);
            },
            'UploadComplete': function() {
            	$("#pic_edit_upload_btn03").show();
            	$("#channelImg_edit03").show();
            	$("#channelIcon_edit_upload_status03").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
            	$("#channelImg_edit03").attr('src', url);
            	$("#channelIcon_edit03").val(url);
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
	
	 // 此为展示图片上传组件 04
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'pic_edit_upload_btn04',
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
            	$("#pic_edit_upload_btn04").hide();
            	$("#channelImg_edit04").hide();
            	var $status = $("#channelIcon_edit_upload_status04");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            },
            'BeforeUpload': function(up, file) {
            },
            'UploadProgress': function(up, file) {
            	var $status = $("#pic_edit_upload_btn04");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(file.percent);
            },
            'UploadComplete': function() {
            	$("#pic_edit_upload_btn04").show();
            	$("#channelImg_edit04").show();
            	$("#channelIcon_edit_upload_status04").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
            	$("#channelImg_edit04").attr('src', url);
            	$("#channelIcon_edit04").val(url);
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
            width:80px;
        }
        .fitem input{
            width:160px;
        }
    </style>
</body>
</html>