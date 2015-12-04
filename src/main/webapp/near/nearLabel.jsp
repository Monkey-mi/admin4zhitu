<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附近标签管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript">

	// 行是否被勾选
	var IsCheckFlag = false;
	
	var columnsFields = [
             {field: "ck",checkbox: true},
             {field: "id",title: "id",align: "center"},
             {field: "cityId",title: "城市id",align: "center"},
             {field: "cityName",title: "城市名称",align: "center"},
             {field: "labelName",title: "标签名称",align: "center"},
             {field: "longitude",title: "经度",align: "center"},
             {field: "latitude",title: "纬度",align: "center"},
             {field: "description",title: "标签描述",align: "center"},
             {field: "bannerUrl",title: "图标",align: "center",
             	formatter: function(value,row,index) {
			  				return "<img title='无效' width='174px' height='90px' class='htm_column_img' src='" + value + "'/>";
			  			}
			  },
             {field: "serial",title: "序列号",align: "center"}
	    ];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "城市分组列表",
			width: $(document.body).width(),
			url: "./admin_op/near_queryNearLabel",
			toolbar: "#tb",
			idField: "id",
			rownumbers: true,
			columns: [columnsFields],
			fitColumns: true,
			autoRowHeight: true,
			checkOnSelect: false,
			selectOnCheck: true,
			onClickCell: function(rowIndex, field, value) {
				IsCheckFlag = false;
			},
			onSelect: function(rowIndex, rowData) {
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("unselectRow", rowIndex);
				}
			},
			onUnselect: function(rowIndex, rowData) {
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("selectRow", rowIndex);
				}
			},
			onLoadSuccess: function(data) {
				// 数据加载成功，loading动画隐藏
				$("#page-loading").hide();
			}
		});
		
		$('#htm_serial').window({
			title : '重新排序',
			modal : true,
			width : 650,
			height : 155,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
		});
		
		// 展示界面
		$("#main").show();
	});
	
	/**
	 * 新增附近标签
	 * @author zhangbo 2015-12-04
	 */
	function addnearLabel() {
		var $form = $('#add_nearLabel_form');
		if($form.form('validate')) {
			$('#add_nearLabel_form .opt_btn').hide();
			$('#add_nearLabel_form .loading').show();
			$form.form('submit', {
				url: $form.attr("action"),
				success: function(data){
					var result = $.parseJSON(data);
					$('#add_nearLabel_form .opt_btn').show();
					$('#add_nearLabel_form .loading').hide();
					if(result['result'] == 0) {
						// 关闭添加窗口，刷新页面 
						$('#add_nearLabel_window').window('close');
						$("#htm_table").datagrid("load");
					} else {
						$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
					}
					
				}
			});
		}
	};
	
	/**
	 * 批量删除附近标签
	 * @author zhangbo 2015-12-04
	 */
	function batchDelete() {
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示", "您确定要删除已选中的城市组吗？删除后，附近标签与附近标签织图的关联关系也将被删除。", function(r){
				if(r){
					var nearLabelIds = [];
					for(var i=0;i<rows.length;i++){
						nearLabelIds[i] = rows[i].id;
					}
					var params = {
							idsStr: nearLabelIds.toString()
						};
					$.post("./admin_op/near_batchDeleteNearLabel", params, function(result){
					    if(result['result'] == 0){
							$.messager.alert("温馨提示","删除" + rows.length + "条记录");
							// 清除所有已选择的记录，避免重复提交id值
							$("#htm_table").datagrid("clearSelections");
							// 批量删除刷新当前页
							$("#htm_table").datagrid("reload");
						}else{
							alert(result['msg']);
						}
					});
				}
			});	
		}else{
			$.messager.alert("温馨提示","请先选择，再执行批量删除操作!");
		}
	};
	
</script>
</head>
<body>
	
	<img id="page-loading" src="${webRootPath}/common/images/girl-loading.gif"/>
	<div id="main" style="display: none;">
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<span>
				<a href="javascript:void(0);" onclick="javascript:$('#add_nearLabel_window').window('open');" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
			</span>
		</div>
		
		<!-- 添加城市分组窗口 -->
		<div id="add_nearLabel_window">
			<form id="add_nearLabel_form" action="./admin_op/near_insertNearLabel" method="post">
				<table class="htm_edit_table" width="580">
					<tr>
						<td class="leftTd">banner图片：</td>
						<td>
							<input id="i-path" name="bannerUrl" class="none" readonly="readonly" >
							<a id="nearLabel_upload_btn" style="position: absolute; margin:30px 0 0 200px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="nearLabel_img_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="174px" height="90px">
							<div id="nearLabel_img_upload_status" class="update_status none" style="width: 205px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">城市Id：</td>
						<td class="leftTd">
							<input type="text" name="cityId"/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">标签名称：</td>
						<td class="leftTd">
							<input type="text" name="labelName"/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">纬度：</td><!-- 经纬度是从城市那里获取的。接受工作的人，敬请悉知 -->
						<td class="leftTd">
							<input  name="longitude" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">经度：</td>
						<td class="leftTd">
							<input  name="latitude" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td class="leftTd">
							<input type="text" name="description"/>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addnearLabel()">确定</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">排序中...</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
	</div>
	<script type="text/javascript">
		Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'nearLabel_thumb_upload_btn',
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
            	$("#nearLabel_thumb_upload_btn").hide();
            	$("#nearLabel_thumb_img_edit").hide();
            	var $status = $("#nearLabel_thumb_img_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#nearLabel_thumb_img_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#nearLabel_thumb_upload_btn").show();
            	$("#nearLabel_thumb_img_edit").show();
            	$("#nearLabel_thumb_img_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#nearLabel_thumb_img_edit").attr('src', url);
            	$("#i-thumb").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  // 提示添加信息失败
            },
            'Key': function(up, file) {
            	var timestamp = Date.parse(new Date());
            	var suffix = /\.[^\.]+/.exec(file.name);
                var key = "op/notice/" + timestamp+suffix;
                return key;
            }
        }
    });
	</script>
</body>
</html>
</html>