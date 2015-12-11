<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	.leftTd{
		text-align:right;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品管理页</title>
<jsp:include page="/common/header.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}" />
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
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
			{field: "itemId", title: "织图id", align: "center"},
			{field: "price", title: "价格", align: "center"},
			{field: "sale", title: "促销价", align: "center"},
			{field: "sales", title: "销售量", align: "center"},
			{field: "stock", title: "库存量", align: "center"},
			{field: "categoryId", title: "类别", align: "center"},
			{field: "brandId", title: "品牌", align: "center"},
			{field: "like", title: "点赞数", align: "center"},
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
/* 			{field: "createTime", title: "创建时间", align: "center",
				formatter:function(value,row,index){
					return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
				}
			}, */
			{field: "opt", title: "操作", align: "center",
				formatter : function(value, row, index ) {
					return "<a class='updateInfo' href='javascript:void(0);' onclick='javascript:updateitem("+ row.id + ")'>【修改】</a>";
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
		});
		
		// 展示界面
		$("#main").show();
	});
	
	/**
	 * 更新商品集合
	 * @author zhangbo	2015-12-08
	 */
	function updateitem(itemId) {
		$("#htm_table").datagrid("selectRecord", itemId);
		var row =  $("#htm_table").datagrid("getSelected");
		$("#item_id").val(row.id);
		$("#item_path").val(row.imgPath);
		$("#item_thumb").val(row.imgThumb);
		$("#item_name").val(row.name);
		$("#item_summary").val(row.summary);
		$("#item_description").val(row.description);
		$("#item_worldId").val(row.worldId);
		$('#item_trueItemId').val(row.itemId);
		$("#item_price").val(row.price);
		$("#item_sale").val(row.sale);
		$("#item_sales").val(row.sales);
		$("#item_stock").val(row.stock);
		$("#item_categoryId").val(row.categoryId);
		$("#item_brandId").val(row.brandId);
		$("#item_like").val(row.like);
		
		$('#add_item_window').window('open');
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
						$('#add_item_window').window('close');  // 关闭添加窗口
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
				<a href="javascript:void(0);" onclick="javascript:$('#add_item_window').window('open');" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
			</span>
		</div>
		
		<!-- 添加商品集合 -->
		<div id="add_item_window" >
			<form id="add_item_form" method="post">
				<table class="htm_edit_table" width="760px" >
					<tr>
						<td  class="leftTd" style="width:50px">图片：</td>
						<td  style="width:130">
							<input class="none" type="text" name="pics" id="channelIcon_edit01"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="pic_edit_upload_btn01" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="channelImg_edit01"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="220px" height="90px">
							<div id="channelIcon_edit_upload_status01" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td  class="leftTd">缩略图：</td>
						<td >
							<input class="none" type="text" name="pic02" id="channelIcon_edit02"  onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="pic_edit_upload_btn02" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="channelImg_edit02"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="220px" height="90px">
							<div id="channelIcon_edit_upload_status02" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">名称：</td>
						<td>
							<input id="item_name" name="name" style="width:220px;" >
						</td>
						<td class="leftTd">销售量：</td>
						<td>
							<input id="item_sales" name="sales" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">简介：</td>
						<td>
							<input id="item_summary" name="summary" style="width:220px;" >
						</td>
						<td class="leftTd">库存量：</td>
						<td>
							<input id="item_stock" name="stock" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td>
							<textarea id="item_description" name="description" rows="1" style="width:220px;" ></textarea>
						</td>
						<td class="leftTd">链接：</td>
						<td>
							<input id="item_Link" name="Link" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">织图id：</td>
						<td>
							<input id="item_worldId" name="worldId" style="width:220px;" >
						</td>
						<td class="leftTd">点赞数：</td>
						<td>
							<input id="item_like" name="like" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">价格：</td>
						<td>
							<input id="item_price" name="price" style="width:220px;" >
						</td>
						<td class="leftTd">促销价：</td>
						<td>
							<input id="item_sale" name="sale" style="width:220px;" >
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
						<td class="leftTd">类别：</td>
						<td>
							<input id="item_categoryId" name="categoryId" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">淘宝ID：</td>
						<td>
							<input id="item_trueItemId" name="trueItemId" style="width:220px;" >
						</td>
						<td class="leftTd">品牌：</td>
						<td>
							<input id="item_brandId" name="brandId" style="width:220px;" >
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
                var key = "trade/item/" + timestamp+suffix;
                return key;
            }
        }
    });
    
	</script>
	
</body>
</html>
</html>