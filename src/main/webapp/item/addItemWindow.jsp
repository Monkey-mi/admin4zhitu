<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}" />
<script type="text/javascript">
	
	$(function(){
		
		//添加商品窗口初始化
		$("#add_item_window").window({
			title : "添加商品",
			modal : true,
			width : 820,
			height : 460,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : "icon-converter",
			resizable : false
		}).show();
		
		//排序窗口初始化
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
		}).show();
		
		//排序窗口初始化
		$('#htm_superb_set').window({
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
		}).show();
	});
	
	/**
	 * 提交商品集合
	 * @author zhangbo	2015-12-08
	 */
	function itemFormSubmit() {
		if( $('#add_item_form').form('validate') ) {
			$('#add_item_form').form('submit', {
				url: "./admin_trade/item_saveitem",
				success: function(data){
					var result = $.parseJSON(data);
					if(result['result'] == 0) {
						$('#add_item_window').window('close');  // 关闭添加窗口
						$('#htm_table').datagrid("reload");
						$('#item_id').val('');
					} else {
						$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
					}
				}
			});
		}
	};
	
	
	//商品的重新排序模块
        function reSuperb() {
        	$("#superb_form").find('input[name="reIndexId"]').val('');
        	$("#schedula").datetimebox('clear');
        	$('#htm_superb .opt_btn').show();
        	$('#htm_superb .loading').hide();
        	
        	var rows = $("#htm_table").datagrid('getSelections');
        	$('#superb_form .reindex_column').each(function(i){
        		if(i<rows.length)
        			$(this).val(rows[i]['id']);
        	});
        	// 打开添加窗口
        	$("#htm_superb").window('open');
        	
        }
	
		//提交排序
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
        					
        					mySortName = "serial";
        					mySortOrder = "desc";
        					$('#htm_table').datagrid('clearSelections');
        					$('#htm_table').datagrid('load');
        					$('#htm_superb').window('close');  //关闭添加窗口
        				} else {
        					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
        				}
        				
        			}
        		});
        	}
        	
        }
	
		
    	//集合商品的重新排序模块
        function reSuperbForSet() {
        	$("#superb_form_set").find('input[name="reIndexId"]').val('');
        	$("#schedula").datetimebox('clear');
        	$('#htm_superb .opt_btn').show();
        	$('#htm_superb .loading').hide();
        	
        	var rows = $("#htm_table_set").datagrid('getSelections');
        	$('#superb_form .reindex_column').each(function(i){
        		if(i<rows.length)
        			$(this).val(rows[i]['id']);
        	});
        	// 打开添加窗口
        	$("#htm_superb").window('open');
        	
        }
	
 		//提交排序
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
        					
        					mySortName = "serial";
        					mySortOrder = "desc";
        					$('#htm_table_set').datagrid('clearSelections');
        					$('#htm_table_set').datagrid('load');
        					$('#htm_superb_set').window('close');  //关闭添加窗口
        				} else {
        					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
        				}
        				
        			}
        		});
        	} 
        	
        }
		
        
        function submitReSuperbFormForSet() {
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
        					
        					mySortName = "serial";
        					mySortOrder = "desc";
        					$('#htm_table_set').datagrid('clearSelections');
        					$('#htm_table_set').datagrid('load');
        					$('#htm_superb').window('close');  //关闭添加窗口
        				} else {
        					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
        				}
        				
        			}
        		});
        	} 
        	
        }
</script>
</head>
<body>
		<!-- 添加商品集合 -->
		<div id="add_item_window" hidden=true >
			<form id="add_item_form" method="post">
				<table class="htm_edit_table" width="760px" >
					<tr>
						<td  class="leftTd" style="width:50px">图片：</td>
						<td  style="width:130">
							<input class="none" type="text" name="imgPath" id="channelIcon_edit01"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="pic_edit_upload_btn01" style="position: absolute; margin:30px 0 0 10px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="item_path"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="100px" height="100px">
							<div id="channelIcon_edit_upload_status01" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td  class="leftTd">缩略图：</td>
						<td >
							<input class="none" type="text" name="imgThumb" id="channelIcon_edit02"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="pic_edit_upload_btn02" style="position: absolute; margin:30px 0 0 10px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="item_thumb"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="100px" height="100px">
							<div id="channelIcon_edit_upload_status02" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">名称：</td>
						<td>
							<input id="item_name" name="name" style="width:220px;" >
						</td>
						<td class="leftTd">点赞数：</td>
						<td>
							<input id="item_like" name="like" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">简介：</td>
						<td>
							<input id="item_summary" name="summary" style="width:220px;" >
						</td>
						<td class="leftTd">织图id：</td>
						<td>
							<input id="item_worldId" name="worldId" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td>
							<textarea id="item_description" name="description" rows="1" style="width:220px;" ></textarea>
						</td>
						<td class="leftTd"></td>
						<td>
						</td>
					</tr>
					<tr>
						<td class="leftTd">价格：</td>
						<td>
							<input id="item_price" name="price" style="width:220px;" >
						</td>
						<td class="leftTd"></td>
						<td>
						</td>
					</tr>
					<tr>
						<td class="leftTd">链接：</td>
						<td>
							<input id="item_link" name="link" style="width:220px;" >
						</td>
						<td class="leftTd"></td>
						<td>
						</td>
					</tr>
					<tr>
						<td class="leftTd">类型：</td>
						<td>
							<select id="item_trueItemType" class="easyui-combobox" name="trueItemType" style="width:220px;" panelHeight="auto" >
							    <option value="1">淘宝</option>
							    <option value="2">天猫</option>
							</select>
						</td>
						<td class="leftTd"></td>
						<td>
						</td>
					</tr>
					<tr>
						<td class="leftTd">淘宝ID：</td>
						<td>
							<input id="item_trueItemId" name="trueItemId" style="width:220px;" >
						</td>
						<td class="leftTd"></td>
						<td>
						</td>
					</tr>
					<tr style="display:none">
						<td class="none" ><input id="item_id" name="id" ></td>
					</tr>
					<tr>
						<td colspan="4"   style="text-align: center;padding-top: 30px;" >
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="itemFormSubmit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#add_item_window').window('close');">取消</a>
						</td>
					</tr>
				</table>
			</form> 
		</div>
		
	<!-- 重排模块 -->
	<div id="htm_superb" hidden=true>
		<form id="superb_form" action="./admin_trade/item_reOrderIndexforItem" method="post">
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
	
	<!-- 集合重排模块 -->
	<div id="htm_superb_set" hidden=true>
		<form id="superb_form_set" action="./admin_trade/item_reOrderIndexforItem" method="post">
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
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReSuperbFormForSet();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_superb_set').window('close');">取消</a>
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
            	$("#item_path").hide();
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
            	$("#item_path").show();
            	$("#channelIcon_edit_upload_status01").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
            	$("#item_path").attr('src', url);
            	$("#channelIcon_edit01").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);
             },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "trade/item/" + timestamp+suffix;
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
            	$("#item_thumb").hide();
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
            	$("#item_thumb").show();
            	$("#channelIcon_edit_upload_status02").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
            	$("#item_thumb").attr('src', url);
            	$("#channelIcon_edit02").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);
             },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "trade/item/" + timestamp+suffix;
                return key;
            }
        }
    });
    
	</script>
	
</body>
</html>
</html>