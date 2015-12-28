<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推荐默认背景图片管理</title>
<jsp:include page="/common/header.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}"></link>
<script type="text/javascript">

	// 行是否被勾选
	var IsCheckFlag = true;
	
	var columnsFields = [
			{field: "ck", checkbox:true},
			{field: "id", title: "ID", align: "center"},
			{field: "background", title: "标题", align: "center",
				formatter : function(value, row, index ) {
  					return "<img width='250px' height='100px' class='htm_column_img pointer' src='" + value + "'/>";
				}
			}
		];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "默认用户背景列表",
			width: $(document.body).width(),
			url: "./admin_user/user_queryDefaultBackground",
			toolbar: "#tb",
			idField: "id",
			rownumbers: true,
			columns: [columnsFields],
			fitColumns: true, 
			autoRowHeight: true,
			checkOnSelect: false,
			selectOnCheck: true,
			pagination: true,
			pageNumber: 1, //指定当前页面为1
			pageSize: 10,
			pageList: [5,10,20],
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
		
		
		$("#add_background_window").window({
			title : '添加商品专题',
			modal : true,
			width : 650,
			height : 300,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false
		});
		
		
		// 展示界面
		$("#main").show();
		
	});
	
	function formSubmit() {
		if( $('#add_background_form').form('validate') ) {
			$('#add_background_form').form('submit', {
				url: "./admin_user/user_saveDefaultBackground",
				success: function(data){
					var result = $.parseJSON(data);
					if(result['result'] == 0) {
						$('#add_background_window').window('close');  // 关闭添加窗口
						$("#htm_table").datagrid("reload");
					} else {
						$.messager.alert("温馨提示",result['msg']);  // 提示添加信息失败
					}
				}
			});
		} else {
			$.messager.alert("温馨提示","请补全需要填写的字段");
		}
	};
	
	function batchDelete(){
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示", "您确定要删除已选中的默认背景吗？", function(r){
				if(r){
					var ids = [];
					for(var i=0;i<rows.length;i++){
						ids[i] = rows[i].id;
					}
					var params = {
							ids: ids.toString()
						};
					$.post("./admin_user/user_batchDeleteDefaultBackground", params, function(result){
						$.messager.alert("温馨提示","删除" + rows.length + "条记录");
						// 清除所有已选择的记录，避免重复提交id值
						$("#htm_table").datagrid("clearSelections");
						$("#htm_table").datagrid("clearChecked");
						// 批量删除刷新当前页
						$("#htm_table").datagrid("reload");
					});
				}
			});	
		}else{
			$.messager.alert("温馨提示","请先选择，再执行批量删除操作!");
		}
	};
	
	/**
	 * 刷新缓存
	 * @author zhangbo	2015-12-28
	 */
	function refreshCache() {
		$.post("./admin_user/user_refreshDefaultBackgroundCache", function(result){
			$.messager.alert("温馨提示",result['msg']);
		});
	};
	
</script>
</head>
<body>
	
	<img id="page-loading" src="${webRootPath}/common/images/girl-loading.gif"/>
	<div id="main" style="display: none;">
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<span>
				<a href="javascript:void(0);" onclick="javascript:$('#add_background_window').window('open');commonTools.clearFormData($('#add_background_form'));" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
				<a href="javascript:void(0);" onclick="refreshCache()" class="easyui-linkbutton" plain="true" iconCls="icon-converter">刷新缓存</a>
			</span>
		</div>
		
		<!-- 添加商品集合 -->
		<div id="add_background_window">
			<form id="add_background_form" method="post">
				<table class="htm_edit_table" width="580">
					<tr>
						<td class="leftTd">图片路径：</td>
						<td  style="width:130">
							<input id="bg_path" name="background" class="none" type="text" readonly="readonly" required="true"/>
							<a id="bg_path_upload_btn" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="bg_path_edit" src="${webRootPath }/base/images/bg_empty.png" width="220px" height="90px">
							<div id="bg_path_edit_upload_status" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="formSubmit();">确定</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
	</div>
	
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/plupload.full.min.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/js/plupload/i18n/zh_CN.js"></script>
	<script type="text/javascript" src="${webRootPath }/base/js/jquery/qiniu/qiniu.min.js"></script>
	<script type="text/javascript">
	
	  // 此为展示图片上传组件 01
	Qiniu.uploader({
        runtimes: 'html5,flash,html4',
        browse_button: 'bg_path_upload_btn',
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
            	$("#bg_path_upload_btn").hide();
            	$("#bg_path_edit").hide();
            	var $status = $("#bg_path_edit_upload_status");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            },
            'BeforeUpload': function(up, file) {
            },
            'UploadProgress': function(up, file) {
            	var $status = $("#bg_path_upload_btn");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(file.percent);
            },
            'UploadComplete': function() {
            	$("#bg_path_upload_btn").show();
            	$("#bg_path_edit").show();
            	$("#bg_path_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
            	$("#bg_path_edit").attr('src', url);
            	$("#bg_path").val(url);
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