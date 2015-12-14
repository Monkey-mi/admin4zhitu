<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品集合公告管理页</title>
<jsp:include page="/common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}"></link>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/common.css"></link>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript">

	// 行是否被勾选
	var IsCheckFlag = true;
	
	var columnsFields = [
			{field: "ck", checkbox:true},
			{field: "id", title: "ID", align: "center", width: 30},
			{field: "title", title: "标题", align: "center", width: 80},
			{field: "description", title: "描述", align: "center", width: 100},
			{field: "path", title: "商品集合图片", align: "center", width: 80,
				formatter: function(value,row,index) {
					return "<img width='200px' height='90px' class='htm_column_img' src='" + value + "'/>";
				}
			},
			{field: "opt", title: "操作", align: "center", width: 70,
				formatter : function(value, row, index ) {
					var rtn = "<span>";
					rtn += "<a class='updateInfo' href='javascript:void(0);' onclick='javascript:updateItemSet("+ row.id + ")'>【修改】</a>";
					rtn += "<a class='updateInfo' href='javascript:void(0);' onclick='openAddItemToItemSet("+ row.id + ")'>【添加商品】</a>";
					rtn += "</span>";
					return rtn;
				}
			},
			{field: "isSeckill", title: "是否秒杀", align: "center",
				formatter : function(value, row, index ) {
	  				// 若已经为秒杀商品集合，则点击会取消秒杀商品
	  				if ( value == 1 ) {
	  					return "<img title='已为秒杀,点击取消秒杀' class='htm_column_img pointer' onclick='cancelSeckill("+ row.id +")' src='./common/images/ok.png'/>";
	  				} else {
	  					return "<img title='点击成为秒杀商品集合' class='htm_column_img pointer' onclick='openSeckillCacheWin("+ row.id +")' src='./common/images/edit_add.png'/>";
	  				}
				}
			},
			{field: "deadline", title: "截止时间", align: "center",
				formatter:function(value,row,index){
					// 若不为秒杀商品集合，则返回空
					if ( row.isSeckill == 0 ) {
						return "";
					} else {
						return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
					}
				}
			},
			{field: "operator", title: "最后修改者", align: "center"},
			{field: "modifyTime", title: "最后修改时间", align: "center",
				formatter:function(value,row,index){
					return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
				}
			}
		];
	
	$(function(){
		// 主表格
		$("#htm_table").datagrid({
			title: "商品集合公告列表",
			width: $(document.body).width(),
			url: "./admin_trade/itemSet_buildItemSetList",
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
			pageSize: 5,
			pageList: [5,10,20],
			onClickCell: function(rowIndex, field, value) {
				IsCheckFlag = false;
			},
			onSelect: function(rowIndex, rowData) {
				// 选择操作时刷新展示重新排序所选择的数量
				$("#reSerialCount").text($(this).datagrid("getSelections").length);
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("unselectRow", rowIndex);
				}
			},
			onUnselect: function(rowIndex, rowData) {
				// 选择操作时刷新展示重新排序所选择的数量
				$("#reSerialCount").text($(this).datagrid("getSelections").length);
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
		
		$("#ss_isCache").combobox({
		    data: [{
			        "id": "",
			        "text": "全部",
			        "selected": true
		    	},{
			        "id": 1,
			        "text": "限时秒杀正在展示"
			    },
			    {
			        "id":2,
			        "text": "推荐商品正在展示"
			    }],
		    valueField: "id",
		    textField: "text",
		    onSelect: function(rec){
		    	var param = {
	    			cacheFlag: rec.id
		    	};
		    	$("#htm_table").datagrid("load",param);
	        }
		});
		
		$("#add_itemSet_window").window({
			title : '添加商品专题',
			modal : true,
			width : 650,
			height : 530,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-add',
			resizable : false
		});
		
		$("#refresh_seckill_window").window({
			title : '刷新秒杀商品集合',
			modal : true,
			width : 650,
			height : 300,
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
		
		$.formValidator.initConfig({
			formid : $('#add_itemSet_form').attr("id")	
		});
		
		$("#itemSet_path")
		.formValidator({empty:false, onshow:"请选图片（必填）",onfocus:"请选图片",oncorrect:"正确！"})
		.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
		
		$("#itemSet_thumb")
		.formValidator({empty:false, onshow:"请选图片（必填）",onfocus:"请选图片",oncorrect:"正确！"})
		.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
		
		$("#itemSet_title")
		.formValidator({empty:false, onshow:"请输入标题（必填）",onfocus:"请输入标题",oncorrect:"正确！"});
		
		$("#itemSet_desc")
		.formValidator({empty:true, onshow:"请输入描述（可选）",onfocus:"请输入描述",oncorrect:"正确！"});
	});
	
	/**
	 * 更新商品集合
	 * @author zhangbo	2015-12-08
	 */
	function updateItemSet(itemSetId) {
		$("#htm_table").datagrid("selectRecord", itemSetId);
		var row = $("#htm_table").datagrid("getSelected");
		
		$("#itemSet_id").val(row.id);
		$("#itemSet_title").val(row.title);
		$("#itemSet_desc").val(row.description);
		$("#itemSet_path").val(row.path);	// 为path设值
		$("#itemSet_path_edit").attr("src", row.path);	// 展示商品集合图片
		$("#itemSet_thumb").val(row.thumb);	// 为thumb设值
		$("#itemSet_thumb_edit").attr("src", row.thumb);	// 展示商品集合缩略图
		$("#itemSet_type").combobox("select", row.type);
		$("#itemSet_link").val(row.link);
		
		$("#add_itemSet_window").window("open");
		$("#htm_table").datagrid("clearSelections");
		$("#htm_table").datagrid("clearChecked");
	};
	
	/**
	 * 添加商品到商品集合
	 * @author zhangbo	2015-12-10
	 */
	function openAddItemToItemSet(itemSetId) {
		var url = "./page_item_itemAddToItemSet";
		url += "?itemSetId=" + itemSetId;
		$.fancybox({
			href: url,
			width: "98%",
			height: "98%",
			autoScale: true,
			type: "iframe",
			transitionIn: "none",	// 打开无动画
			transitionOut: "elastic"	// 关闭动画：伸缩
		});
	}
	
	/**
	 * 提交商品集合
	 * @author zhangbo	2015-12-08
	 */
	function formSubmit() {
		if( $('#add_itemSet_form').form('validate') ) {
			$('#add_itemSet_form').form('submit', {
				url: "./admin_trade/itemSet_saveItemSet",
				success: function(data){
					var result = $.parseJSON(data);
					if(result['result'] == 0) {
						$('#add_itemSet_window').window('close');  // 关闭添加窗口
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
	
	/**
	 * 批量删除商品集合banner
	 * @author zhangbo 2015-12-08
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
					$.post("./admin_trade/itemSet_batchDelete", params, function(result){
						$.messager.alert("温馨提示","删除" + rows.length + "条记录");
						// 清除所有已选择的记录，避免重复提交id值
						$("#htm_table").datagrid("clearSelections");
						$("#htm_table").datagrid("clearChecked");
						// 批量删除刷新当前页
						$("#htm_table").datagrid("reload");
					});
					// 删除后清空提示数量
					$("#reSerialCount").text(0);
				}
			});	
		}else{
			$.messager.alert("温馨提示","请先选择，再执行批量删除操作!");
		}
	};
	
	/**
	 * 重新排序
	 * @author zhangbo 2015-12-11
	 */
	function reorder() {
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			var ids = [];
			for(var i=0;i<rows.length;i++){
				ids[i] = rows[i].id;
			}
			var params = {
					ids: ids.toString()
				};
			$.post("./admin_trade/itemSet_reorder", params, function(result){
				$.messager.alert("温馨提示","排序" + rows.length + "条记录");
				// 清除所有已选择的记录，避免重复提交id值
				$("#htm_table").datagrid("clearSelections");
				$("#htm_table").datagrid("clearChecked");
				// 批量删除刷新当前页
				$("#htm_table").datagrid("reload");
				$("#reSerialCount").text(0);
			});
		}else{
			$.messager.alert("温馨提示","请先选择，再执行重新排序!");
		}
	};
	
	/**
	 * 打开设置秒杀window
	 * @author zhangbo 2015-12-10
	 */
	function openSeckillCacheWin(itemSetId) {
		$("#refresh_seckill_window").window("open");
		$("#seckill_itemSetId").val(itemSetId);
	};
	
	/**
	 * 提交限时秒杀缓存
	 * @author zhangbo	2015-12-11
	 */
	function seckillSetCacheSubmit() {
		if( $('#refresh_seckill_form').form('validate') ) {
			$('#refresh_seckill_form').form('submit', {
				url: "./admin_trade/itemSet_addSeckill",
				success: function(data){
					var result = $.parseJSON(data);
					if(result['result'] == 0) {
						$('#refresh_seckill_window').window('close');  // 关闭添加窗口
						$("#htm_table").datagrid("reload");
					} else {
						$.messager.alert("温馨提示",result['msg']);  // 提示添加信息失败
					}
				}
			});
		}
	};
	
	/**
	 * 刷新redis缓存
	 * @author zhangbo 2015-12-10
	 */
	function refreshItemSetCache() {
		$.messager.confirm("温馨提示", "您确定要更新商品集合吗？确定后，选中的商品集合将会在客户端生效", function(r){
			if(r){
				$.post("./admin_trade/itemSet_refreshItemSetCache", function(result){
					$.messager.alert("温馨提示",result.msg);
				});
			}
		});
	};
	
	/**
	 * 取消秒杀
	 * @param itemSetId	商品集合id
	 * @author zhangbo	2015-12-12
	 */
	function cancelSeckill(itemSetId) {
		var params = {
				id: itemSetId
			};
		$.post("./admin_trade/itemSet_cancelSeckill", params, function(result){
			if(result.result == 0) {
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
			}
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
				<a href="javascript:void(0);" onclick="javascript:$('#add_itemSet_window').window('open');commonTools.clearFormData($('#add_itemSet_form'));" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="reorder()" class="easyui-linkbutton" plain="true" iconCls="icon-converter">重新排序<span id="reSerialCount" type="text" style="font-weight:bold;">0</span></a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
		   		<input id="ss_isCache" class="easyui-combobox">
		   		<a href="javascript:void(0);" onclick="refreshItemSetCache()" class="easyui-linkbutton" plain="true" iconCls="icon-converter">刷新缓存</a>
			</span>
		</div>
		
		<!-- 添加商品集合 -->
		<div id="add_itemSet_window">
			<form id="add_itemSet_form" method="post">
				<table class="htm_edit_table" width="580">
					<tr>
						<td class="leftTd">图片路径：</td>
						<td  style="width:130">
							<input id="itemSet_path" name="path" class="none" type="text" readonly="readonly" required="true"/>
							<a id="itemSet_path_upload_btn" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="itemSet_path_edit" src="${webRootPath }/base/images/bg_empty.png" width="220px" height="90px">
							<div id="itemSet_path_edit_upload_status" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="itemSet_pathTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">缩略图路径：</td>
						<td >
							<input id="itemSet_thumb" name="thumb" class="none" type="text" readonly="readonly" required="true"/>
							<a id="itemSet_thumb_upload_btn" style="position: absolute; margin:30px 0 0 100px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="itemSet_thumb_edit" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="itemSet_thumb_edit_upload_status" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="itemSet_thumbTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">标题：</td>
						<td>
							<textarea id="itemSet_title" name="title" style="width:260px;" rows="5"></textarea>
						</td>
						<td class="rightTd">
							<div id="itemSet_titleTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td colspan="2">
							<textarea id="itemSet_desc" name="description" style="width:450px;height:120px;" rows="20"></textarea>
						</td>
					</tr>
					<tr>
						<td class="none"><input id="itemSet_id" name="id"></td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="formSubmit();">确定</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<!-- 设置秒杀商品集合到redis缓存 -->
		<div id="refresh_seckill_window">
			<form id="refresh_seckill_form" method="post">
				<table class="htm_edit_table" width="480">
					<tr>
						<td class="leftTd">商品集合ID：</td>
						<td>
							<input id="seckill_itemSetId" name="id" style="width:220px;" >
						</td>
					</tr>
					<tr>
						<td class="leftTd">截止日期：</td>
						<td>
							<input name="deadline" class="easyui-datetimebox" required="true">
						</td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="seckillSetCacheSubmit();">确定</a>
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
        browse_button: 'itemSet_path_upload_btn',
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
            	$("#itemSet_path_upload_btn").hide();
            	$("#itemSet_path_edit").hide();
            	var $status = $("#itemSet_path_edit_upload_status");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            },
            'BeforeUpload': function(up, file) {
            },
            'UploadProgress': function(up, file) {
            	var $status = $("#itemSet_path_upload_btn");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(file.percent);
            },
            'UploadComplete': function() {
            	$("#itemSet_path_upload_btn").show();
            	$("#itemSet_path_edit").show();
            	$("#itemSet_path_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
            	$("#itemSet_path_edit").attr('src', url);
            	$("#itemSet_path").val(url);
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
        browse_button: 'itemSet_thumb_upload_btn',
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
            	$("#itemSet_thumb_upload_btn").hide();
            	$("#itemSet_thumb_edit").hide();
            	var $status = $("#itemSet_thumb_edit_upload_status");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            },
            'BeforeUpload': function(up, file) {
            },
            'UploadProgress': function(up, file) {
            	var $status = $("#itemSet_thumb_upload_btn");
            	// 按照页面布局顺序，icon，sub_icon，banner都配置了upload_progress样式，icon获取第一个
            	$status.find('.upload_progress:eq(0)').text(file.percent);
            },
            'UploadComplete': function() {
            	$("#itemSet_thumb_upload_btn").show();
            	$("#itemSet_thumb_edit").show();
            	$("#itemSet_thumb_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/' + $.parseJSON(info).key;
            	$("#itemSet_thumb_edit").attr('src', url);
            	$("#itemSet_thumb").val(url);
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