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
    
    <table id="dg" title="主题信息" style="height:716px"></table>
    <div id="toolbar" style="display:none">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="destroyUser()">删除</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:620px;height:540px;padding:3px 15px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" style="display:none">
<!--             <div class="fitem">
                <label>背景色:</label>
                <input name="backgroundColor" class="easyui-textbox"  data-options="width:20px">
            </div> -->
            <table border=0	>
            	<tr>
            		<td>
            		 <label>banner图:</label>
            		</td>
            		<td colspan="3">
            					<input class="none" type="text" name="bannerPic" id="channelBanner_edit"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
								<a id="channelBanner_edit_upload_btn" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">长Banner</a> 
								<img id="channelBannerImg_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="205px" height="90px">
								<div id="channelBanner_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span></div>
            		</td>
            		<td>
            		 <label>分享图:</label>
            		</td>
            		<td>
            					<input class="none" type="text" name="shareBanner" id="channelBanner_edit01"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
								<a id="channelBanner_edit_upload_btn01" style="position: absolute; margin:30px 0 0 6px" class="easyui-linkbutton" iconCls="icon-add">方Banner</a> 
								<img id="channelBannerImg_edit01"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
								<div id="channelBanner_edit_upload_status01" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span></div>
            		</td>
            	</tr>
            	<tr>
            		<td>
            		<label>文章类型:</label>
            		</td>
            		<td>
            		<select id="topicType" name="topicType" class="easyui-combobox"   panelHeight=50>
            			<option value=1>达人推荐</option>
            			<option value=2>文章阅读</option>
            		</select>
            		</td>
            		<td>
            		<label>是否织图:</label>
            		</td>
            		<td>
            		<select id="isWorld" name="isWorld" class="easyui-combobox"   panelHeight=50>
            			<option value=0>图片类型</option>
            			<option value=1>织图类型</option>
            		</select>
            		</td>
            	</tr>
            	<tr>
            		<td >
            		<label>主题名:</label>
            		</td>
            		<td colspan="3">
            		<textarea rows="2" cols="20" name="title" id="title"></textarea>
            		</td>
            	</tr>
            	<tr>
            		<td>
            		<label>前介绍:</label>
            		</td>
            		<td colspan="3">
            		<textarea rows="4" cols="20" name="introduceHead" id="introduceHead"></textarea>
            		</td>
            	</tr>
            	  <tr>
            		<td>
            		<label>后介绍:</label>
            		</td>
            		<td colspan="3">
            		<textarea rows="4" cols="20" name="introduceFoot" id="introduceFoot"></textarea>
            		</td>
            	</tr>
            	<tr>
            		<td>
            		<label>发图按钮:</label>
            		</td>
            		<td colspan="3">
            		<textarea rows="2" cols="20" name="stickerButton" id="stickerButton" ></textarea>
            		</td>
            	</tr>
            	 <tr>
            		<td>
            		<label>分享按钮:</label>
            		</td>
            		<td colspan="3">
            		<textarea rows="2" cols="20" name="shareButton" id="shareButton"></textarea>
            		</td>
            	</tr>
<!--             	 <tr>
            		<td>
            		<label>顺序值:</label>
            		</td>
            		<td colspan="3">
            		<textarea rows="1" cols="20" name="orderIndex" id="orderIndex"></textarea>
            		</td>
            	</tr>   -->          	
<!--             	<tr>
            		<td>
            		<label>来自织图:</label>
            		</td>
            		<td>
            		<textarea rows="2" cols="20" name="foot" id="foot"></textarea>
            		</td>
            	</tr>             -->	
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
        var myQueryParams = {};
        var maxId = 0;
        
        function newUser(){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','增加主题');
            
    		//用$('#fm').form('clear'); 会影响图片的上传，用这种笨拙的办法代替
    		$('#channelBannerImg_edit').attr('src','${webRootPath }/base/images/bg_empty.png');
    		$('#channelBannerImg_edit01').attr('src','${webRootPath }/base/images/bg_empty.png');
            $('#title').val('');
            $('#introduceHead').val('');
            $('#introduceFoot').val('');
            $('#stickerButton').val('我也来一发');
            $('#shareButton').val('分享出去');
/*        $('#foot').val(''); */
            
            $('#fm').show();
            url = './admin_interact/starRecommendTopic_add';
        }
        function editUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑主题');
                $('#fm').form('load',row);
                $("#channelBannerImg_edit").attr('src', row.bannerPic);
                $("#channelBannerImg_edit01").attr('src', row.shareBanner);
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
            var rows = $('#dg').datagrid('getSelections');
            if (rows != "" && rows != null ){
            	$.messager.defaults = { ok: "当然", cancel: "手抖了" };
                $.messager.confirm('Confirm','同学，你真的要删除这期吗?',function(r){
                    if (r){
		            	for(var i = 0; i < rows.length; i++){
	                        $.post('./admin_interact/starRecommendTopic_destroy',{id:rows[i].id,isWorld:rows[i].isWorld},function(result){
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
                    }
                });
            }
        }
        
        //更改主题的有效性
        function updateValidate(valid,rowId){
        	var tip = "./common/images/tip.png";
        	if($("#validImg"+rowId).attr("src") != tip){
        		$("#validImg"+rowId).attr("src","./common/images/tip.png");
        	}else{
        		$("#validImg"+rowId).attr("src","./common/images/ok.png");
        	}
        	
    		$.post("./admin_interact/starRecommendTopic_updateValidForTopic",{valid:valid,id:rowId},function(data){
    			if(data.result == 0){
    				$('#dg').datagrid('reload');
    			}
    		},"json");
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
            url:"./admin_interact/starRecommendTopic_get",
            idField:"id",
            toolbar:"#toolbar",
            pagination:true,
            singleSelect:false,
            checkOnSelect:false,
            rownumbers:true,
            //fitColumns:true,
             queryParams:myQueryParams,
            columns:[[
				{field:'id',title:'ID',width:100,align:"center",hidden:true}, 
				{field : 'ck',checkbox : true },
			/* 	{field : 'orderIndex',title:'顺序值',align : 'center', sortable: true, editor:'text'}, */
				{field : 'valid',title : '有效性',align : 'center', width: 45,
			  			formatter: function(value,row,index) {
			  				if(value == 1) {
			  					img = "./common/images/ok.png";
			  					return "<img id='validImg"+row.id+"' title='有效' class='htm_column_img' onclick='javascript:updateValidate(0,"+row.id+")'  src='" + img + "'/>";
			  				}
			  				img = "./common/images/tip.png";
			  				return "<img id='validImg"+row.id+"' title='无效' onclick='javascript:updateValidate(1,"+row.id+")'  class='htm_column_img' src='" + img + "'/>";
			  			}
  				},
                {field:'title',title:'主题',width:100,align:"center"},
                {field:'topicType',title:'文章类型',width:100,align:"center",
            		formatter:function(value,row,index) {
            			if( value == 1){
            				return "达人推荐";
            			}else if(value == 2){
            				return "文章阅读";
            			}else{
            				return;
            			}
        			}		
                },
                {field:'isWorld',title:'是否织图',width:100,align:"center",
            		formatter:function(value,row,index) {
            			if( value == 0){
            				return "图片类型";
            			}else if(value == 1){
            				return "织图类型";
            			}else{
            				return;
            			}
        			}		
                },
                {field:'shareBanner',title:'方banner图',width:100,align:"center",
        			formatter:function(value,row,index) {
        				return "<img width='50px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";	
        			}	
                },
                {field:'bannerPic',title:'长banner图',width:100,align:"center",
        			formatter:function(value,row,index) {
        				return "<img width='100px' height='50px' alt='' class='htm_column_img' style='margin:3px 0 3px 0;' src='" + value + "'/>";	
        			}	
                },
                {field:'introduceHead',title:'前介绍',width:150,align:"center"},
                {field:'introduceFoot',title:'后介绍',width:250,align:"center"},
                {field:'stickerButton',title:'发图按钮',width:100,align:"center"},
                {field:'shareButton',title:'分享按钮',width:100,align:"center"},
   /*         {field:'foot',title:'来自织图',width:100,align:"center"}, */
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
    		buttons:pageButtons,
    		onBeforeRefresh:myOnBeforeRefresh,
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
        
        // 此为频道banner上传组件 
        Qiniu.uploader({
        	runtimes: 'html5,flash,html4',
        	browse_button: 'channelBanner_edit_upload_btn01',
        	max_file_size: '200kb',
        	flash_swf_url: 'js/plupload/Moxie.swf',
        	chunk_size: '4mb',
        	uptoken_url: './admin_qiniu/uptoken',
        	domain: 'http://static.imzhitu.com/',
        	unique_names: false,
        	save_key: false,
        	auto_start: true,
        	init: {
        		'FilesAdded': function(up, files) {
        			$("#channelBanner_edit_upload_btn01").hide();
        			$("#channelBannerImg_edit01").hide();
        			var $status = $("#channelBanner_edit_upload_status01");
        			// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，banner获取第三个
        			$status.find('.upload_progress:eq(2)').text(0);
        			$status.show();
        			
        		},
        		'BeforeUpload': function(up, file) {
        		},
        		
        		'UploadProgress': function(up, file) {
        			var $status = $("#channelBanner_edit_upload_status01");
        			// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，banner获取第三个
        			$status.find('.upload_progress:eq(2)').text(file.percent);
        			
        		},
        		'UploadComplete': function() {
        			$("#channelBanner_edit_upload_btn01").show();
        			$("#channelBannerImg_edit01").show();
        			$("#channelBanner_edit_upload_status01").hide();
        		},
        		'FileUploaded': function(up, file, info) {
          			var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
        			$("#channelBannerImg_edit01").attr('src', url);
        			$("#channelBanner_edit01").val(url);   
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