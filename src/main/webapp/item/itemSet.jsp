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
	var IsCheckFlag = false;
	
	var columnsFields = [
			{field: "ck", checkbox:true},
			{field: "id", title: "ID", align: "center", width: 30},
			{field: "path", title: "商品集合图片", align: "center", width: 80,
				formatter: function(value,row,index) {
	  				return "<img width='200px' height='90px' class='htm_column_img' src='" + value + "'/>";
	  			}
			},
			{field: "description", title: "描述", align: "center", width: 100},
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
	  					return "<img title='点击成为秒杀商品集合' class='htm_column_img pointer' onclick='openSeckillWin("+ row.id +")' src='./common/images/tip.png'/>";
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
			title : '添加商品集合banner',
			modal : true,
			width : 650,
			height : 400,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
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
	});
	
	/**
	 * 更新商品集合
	 * @author zhangbo	2015-12-08
	 */
	function updateItemSet(itemSetId) {
		$("#htm_table").datagrid("selectRecord", itemSetId);
		var row = $("#htm_table").datagrid("getSelected");
		
		$("#itemSet_id").val(row.id);
		$("#itemSet_path").val(row.path);	// 为path设值
		$("#itemSet_path_edit").attr("src", row.path);	// 展示商品集合图片
		$("#itemSet_thumb").val(row.thumb);	// 为thumb设值
		$("#itemSet_thumb_edit").attr("src", row.thumb);	// 展示商品集合缩略图
		$("#itemSet_type").combobox("select", row.type);
		$("#itemSet_link").val(row.link);
		$("#itemSet_desc").val(row.description);
		
		$("#add_itemSet_window").window("open");
		$("#htm_table").datagrid("clearSelections");
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
						$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
					}
				}
			});
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
				$.messager.alert("温馨提示","删除" + rows.length + "条记录");
				// 清除所有已选择的记录，避免重复提交id值
				$("#htm_table").datagrid("clearSelections");
				// 批量删除刷新当前页
				$("#htm_table").datagrid("reload");
			});
		}else{
			$.messager.alert("温馨提示","请先选择，再执行重新排序!");
		}
	};
	
	/**
	 * 刷新redis缓存
	 * @author zhangbo 2015-12-10
	 */
	function openSeckillSetCacheWin() {
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$("#refresh_seckill_window").window("open");
			var ids = [];
			for(var i=0;i<rows.length;i++){
				ids[i] = rows[i].id;
			}
			$("#itemSet_ids").val(ids.toString());
		}else{
			$.messager.alert("温馨提示","请先选择，再执行更新缓存操作!");
		}
	};
	
	/**
	 * 提交限时秒杀缓存
	 * @author zhangbo	2015-12-11
	 */
	function seckillSetCacheSubmit() {
		$.messager.confirm("温馨提示", "您确定要更新商品集合吗？确定后，选中的商品集合将会在客户端生效", function(r){
			if(r){
				if( $('#refresh_seckill_form').form('validate') ) {
					$('#refresh_seckill_form').form('submit', {
						url: "./admin_trade/itemSet_refreshSeckillSetCache",
						success: function(data){
							var result = $.parseJSON(data);
							if(result['result'] == 0) {
								$('#refresh_seckill_window').window('close');  // 关闭添加窗口
								$("#htm_table").datagrid("reload");
							} else {
								$.messager.alert('错误提示',result['msg']);  // 提示添加信息失败
							}
						}
					});
				}
			}
		});
	};
	
	/**
	 * 刷新redis缓存
	 * @author zhangbo 2015-12-10
	 */
	function refreshRecommendItemSetCache() {
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示", "您确定要更新商品集合吗？确定后，选中的商品集合将会在客户端生效", function(r){
				if(r){
					var ids = [];
					for(var i=0;i<rows.length;i++){
						ids[i] = rows[i].id;
					}
					var params = {
							ids: ids.toString()
						};
					$.post("./admin_trade/itemSet_refreshRecommendItemSetCache", params, function(result){
						if (result.result == 0) {
							$.messager.alert("温馨提示",result.msg);
						}
					});
				}
			});	
		}else{
			$.messager.alert("温馨提示","请先选择，再执行更新缓存操作!");
		}
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
				<a href="javascript:void(0);" onclick="javascript:$('#add_itemSet_window').window('open');$('#add_itemSet_form').form('reset');" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
				<a href="javascript:void(0);" onclick="reorder()" class="easyui-linkbutton" plain="true" iconCls="icon-converter">重新排序</a>
				<a href="javascript:void(0);" onclick="batchDelete()" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
		   		<input id="ss_isCache" class="easyui-combobox">
		   		<a href="javascript:void(0);" onclick="refreshRecommendItemSetCache()" class="easyui-linkbutton" plain="true" iconCls="icon-converter">刷新推荐商品缓存</a>
		   		<a href="javascript:void(0);" onclick="openSeckillSetCacheWin()" class="easyui-linkbutton" plain="true" iconCls="icon-converter">刷新秒杀商品缓存</a>
			</span>
		</div>
		
		<!-- 添加商品集合 -->
		<div id="add_itemSet_window">
			<form id="add_itemSet_form" method="post">
				<table class="htm_edit_table" width="480">
					<tr>
						<td class="leftTd">商品集合图片路径：</td>
						<td  style="width:130">
							<input id="itemSet_path" name="path" class="none" type="text" onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="itemSet_path_upload_btn" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="itemSet_path_edit" src="${webRootPath }/base/images/bg_empty.png" width="220px" height="90px">
							<div id="itemSet_path_edit_upload_status" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">商品集合缩略图路径：</td>
						<td >
							<input id="itemSet_thumb" name="thumb" class="none" type="text" onchange="validateSubmitOnce=true;" readonly="readonly"/>
							<a id="itemSet_thumb_upload_btn" style="position: absolute; margin:30px 0 0 60px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="itemSet_thumb_edit" src="${webRootPath }/base/images/bg_empty.png" width="220px" height="90px">
							<div id="itemSet_thumb_edit_upload_status" class="update_status none" style="width: 90px; text-align: center;">上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">链接类型：</td>
						<td>
							<select id="itemSet_type" name="type" class="easyui-combobox" style="width:223px;" >
							<!-- 此链接类型数值，是为了以后兼容公告，故如此流水，因为公告链接类型原来有4种，此处属于追加，数值6是有奖活动，在后台逻辑写死 -->
								<option value="0" selected="selected">无需跳转</option>
	        					<option value="5">限时秒杀网页连接</option>
	        					<option value="7">好物推荐网页连接</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="leftTd">链接：</td>
						<td><input id="itemSet_link" name="link" style="width:220px;" ></td>
					</tr>
					<tr>
						<td class="leftTd">描述：</td>
						<td><input id="itemSet_desc" name="description" style="width:220px;" ></td>
					</tr>
					<tr>
						<td class="none"><input id="itemSet_id" name="id"></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;padding-top: 10px;">
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
							<input id="itemSet_ids" name="ids" style="width:220px;" >
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