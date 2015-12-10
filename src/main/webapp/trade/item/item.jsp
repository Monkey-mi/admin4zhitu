<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品管理页</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath}/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript">

	// 行是否被勾选
	var IsCheckFlag = false;
	
	var columnsFields = [
			{field: "ck", checkbox:true},
			{field: "id", title: "商品ID", align: "center"},
			{field: "name", title: "商品名称", align: "center"},
			{field: "summary", title: "简介", align: "center"},
			{field: "description", title: "详情描述", align: "center"},
			{field: "worldId", title: "关联织图id", align: "center"},
			{field: "price", title: "价格", align: "center"},
			{field: "sale", title: "促销价", align: "center"},
			{field: "sales", title: "销售量", align: "center"},
			{field: "stock", title: "库存量", align: "center"},
			{field: "category", title: "类别", align: "center"},
			{field: "brand", title: "品牌", align: "center"},
			{field: "imgPath", title: "商品图片", align: "center",
				formatter: function(value,row,index) {
	  				return "<img width='174px' height='90px' class='htm_column_img' src='" + value + "'/>";
	  			}
			},
			{field: "imgThumb", title: "商品缩略图", align: "center",
				formatter: function(value,row,index) {
					return "<img width='174px' height='90px' class='htm_column_img' src='" + value + "'/>";
				}
			},
			{field: "createTime", title: "创建时间", align: "center",
				formatter:function(value,row,index){
					return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
				}
			},
			{field: "opt", title: "操作", align: "center",
				formatter : function(value, row, index ) {
					return "<a class='updateInfo' href='javascript:void(0);' onclick='javascript:$('#add_item_window').window('open'); updateitem("+ row.id + ")'>【修改】</a>";
				}
			}
		];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "商品列表",
			width: $(document.body).width(),
			url: "./admin_trade/item_buildItemList",
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
			pageList: [10,20,50],
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
		
		// 展示界面
		$("#main").show();
	});
	
	/**
	 * 更新商品集合
	 * @author zhangbo	2015-12-08
	 */
	function updateitem(itemId) {
		var row = $("#htm_table").datagrid("selectRecord", itemId);
		$("#item_id").val(row.id);
		$("#item_path").val(row.imgPath);
		$("#item_thumb").val(row.imgThumb);
		$("#item_name").val(row.name);
		$("#item_summary").val(row.summary);
		$("#item_description").val(row.description);
		$("#item_worldId").val(row.worldId);
		$("#item_price").val(row.price);
		$("#item_sale").val(row.sale);
		$("#item_sales").val(row.sales);
		$("#item_stock").val(row.stock);
		$("#item_categoryId").val(row.categoryId);
		$("#item_brandId").val(row.brandId);
	};
	
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
						$('#add_item_form').window('close');  // 关闭添加窗口
					} else {
						$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
					}
				}
			});
		}
	};
	
	/**
	 * 批量删除商品
	 * @author zhangbo 2015-12-09
	 */
	function batchDelete(){
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示", "您确定要删除已选中的商品集合吗？", function(r){
				if(r){
					var ids = [];
					for(var i=0;i<rows.length;i++){
						ids[i] = rows[i].id;
					}
					var params = {
							ids: ids.toString()
						};
					$.post("./admin_trade/item_batchDelete", params, function(result){
						$.messager.alert("温馨提示","删除" + rows.length + "条记录");
						// 清除所有已选择的记录，避免重复提交id值
						$("#htm_table").datagrid("clearSelections");
						// 批量删除刷新当前页
						$("#htm_table").datagrid("reload");
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
				<a href="javascript:void(0);" onclick="javascript:$('#add_item_window').window('open');" class="easyui-linkbutton" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" iconCls="icon-cut">批量删除</a>
				<select id="ss_isCache" class="easyui-combobox"  style="width:100px;">
			        <option value="" selected="selected">全部</option>
			        <option value="1">限时秒杀正在展示</option>
			        <option value="2">推荐商品正在展示</option>
		   		</select>
			</span>
		</div>
		
		<!-- 添加商品集合 -->
		<div id="add_item_window">
			<form id="add_item_form" method="post">
				<table class="htm_edit_table" width="480">
					<tr>
						<td class="leftTd">商品图片：</td>
						<td>
							<input id="item_path" name="imgPath" readonly="readonly" >
							<a id="item_upload_btn" style="position: absolute; margin:30px 0 0 200px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="item_img_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="174px" height="90px">
							<div id="item_img_upload_status" class="update_status none" style="width: 205px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">商品缩略图：</td>
						<td>
							<input id="item_thumb" name="imgThumb" readonly="readonly" >
							<a id="item_thumb_upload_btn" style="position: absolute; margin:30px 0 0 200px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="item_thumb_img_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="174px" height="90px">
							<div id="item_thumb_img_upload_status" class="update_status none" style="width: 205px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">名称：</td>
						<td>
							<input id="item_name" name="name" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">简介：</td>
						<td>
							<input id="item_summary" name="summary" style="width:270px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">详情描述：</td>
						<td>
							<textarea id="item_description" name="description" rows="13" style="width:220px;">
						</td>
					</tr>
					<tr>
						<td class="leftTd">关联织图id：</td>
						<td>
							<input id="item_worldId" name="worldId" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">价格：</td>
						<td>
							<input id="item_price" name="price" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">促销价：</td>
						<td>
							<input id="item_sale" name="sale" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">销售量：</td>
						<td>
							<input id="item_sales" name="sales" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">库存量：</td>
						<td>
							<input id="item_stock" name="stock" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">淘宝商品真实id：</td>
						<td>
							<input id="item_trueItemId" name="trueItemId" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">淘宝商品类型（1：淘宝，2：天猫）：</td>
						<td>
							<input id="item_trueItemType" name="trueItemType" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">类别：</td>
						<td>
							<input id="item_categoryId" name="categoryId" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">品牌：</td>
						<td>
							<input id="item_brandId" name="brandId" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">点赞数量：</td>
						<td>
							<input id="item_like" name="like" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="none"><input id="item_id" name="id"></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="itemFormSubmit();">添加</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#add_item_window').window('close');">取消</a>
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
	
		Qiniu.uploader({
	        runtimes: 'html5,flash,html4',
	        browse_button: 'item_upload_btn',
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
	            	$("#item_upload_btn").hide();
	            	$("#item_img_edit").hide();
	            	var $status = $("#item_img_upload_status");
	            	$status.find('.upload_progress:eq(0)').text(0);
	            	$status.show();
	            	
	            },
	            'BeforeUpload': function(up, file) {
	            },
	            
	            'UploadProgress': function(up, file) {
	            	var $status = $("#item_img_upload_status");
	            	$status.find('.upload_progress:eq(0)').text(file.percent);
	
	            },
	            'UploadComplete': function() {
	            	$("#item_upload_btn").show();
	            	$("#item_img_edit").show();
	            	$("#item_img_upload_status").hide();
	            },
	            'FileUploaded': function(up, file, info) {
	            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
	            	$("#item_img_edit").attr('src', url);
	            	$("#i-path").val(url);
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
	    
	    Qiniu.uploader({
	        runtimes: 'html5,flash,html4',
	        browse_button: 'item_thumb_upload_btn',
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
	            	$("#item_thumb_upload_btn").hide();
	            	$("#item_thumb_img_edit").hide();
	            	var $status = $("#item_thumb_img_upload_status");
	            	$status.find('.upload_progress:eq(0)').text(0);
	            	$status.show();
	            	
	            },
	            'BeforeUpload': function(up, file) {
	            },
	            
	            'UploadProgress': function(up, file) {
	            	var $status = $("#item_thumb_img_upload_status");
	            	$status.find('.upload_progress:eq(0)').text(file.percent);
	
	            },
	            'UploadComplete': function() {
	            	$("#item_thumb_upload_btn").show();
	            	$("#item_thumb_img_edit").show();
	            	$("#item_thumb_img_upload_status").hide();
	            },
	            'FileUploaded': function(up, file, info) {
	            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
	            	$("#item_thumb_img_edit").attr('src', url);
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