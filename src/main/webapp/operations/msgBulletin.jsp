<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告banner管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
	// 请求参数
	var queryParams = {};
	
	// 行是否被勾选
	var IsCheckFlag = true;
	
	// 定义展示列
	var columnsFields = [
		{field: "ck", checkbox: true},
		{field: "id", title: "ID", align: "center", width:80},
		{field: "category", title: "分类", align: "center", width: 100,
			formatter:function(value,row,index){
				switch(value){
					case 1:
						return "有奖活动";
					case 2:
						return "无奖活动";
					case 3:
						return "达人专题";
					case 4:
						return "内容专题";
					default:
						return ""; 
				}
			}
		},
		{field: "bulletinPath", title: "公告图片", align: "center", width: 180,
			formatter: function(value,row,index) {
  				return "<img width='174px' height='90px' class='htm_column_img' src='" + value + "'/>";
  			}
		},
		{field: "bulletinType", title: "链接类型", align: "center", width: 100,
			formatter:function(value,row,index){
				switch(value){
					case 0:
						return "无需跳转";
					case 1:
						return "网页连接";
					case 2:
						return "频道id";
					case 3:
						return "用户id";
					case 4:
						return "活动标签";
					default:
						return ""; 
				}
			}
		},
		{field: "link", title: "链接内容", align: "center", width: 130},
		{field: "bulletinName", title: "名字", align: "center", width: 130},
		{field: "bulletinThumb", title: "缩略图", align: "center", width: 130,
			formatter: function(value,row,index) {
  				return "<img width='174px' height='90px' class='htm_column_img' src='" + value + "'/>";
	  		}
	  	},
	  	{field: "modifyDate", title: "最后修改时间", align: "center", width: 130,
	  		formatter:function(value,row,index){
	  			return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
	  		}
	  	},
		{field: "addDate", title: "创建时间", align: "center", width: 130,
			formatter:function(value,row,index){
				return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
			}
		},
		{field: "operatorName", title: "最后操作者", align: "center", width: 80},
		{field: "opt", title: "操作", align: "center", rowspan : 1, width: 120, 
			formatter : function(value, row, index ) {
				return "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:updateInit("+ row.id + ")'>【修改】</a>";
			}
		}
	];
	
	// 初始化页面
	$(function(){
		
		$("#htm_table").datagrid({
			title: "公告banner管理",
			width: $(document.body).width(),
			loadMsg: "加载中....",
			url: "./admin_op/msgBulletin_queryMsgBulletin",
			queryParams: queryParams,
			remoteSort: true,
			pagination: true,
			idField: "id",
			pageNumber: 1,
			pageSize: 5,
			pageList: [5,10,30,50],
			toolbar: "#tb",
			columns: [columnsFields],
			onClickCell: function(rowIndex, field, value) {
				IsCheckFlag = false;
			},
			onSelect: function(rowIndex, rowData) {
				// 选择操作时刷新展示重新排序所选择的数量
				$("#reorderCount").text($(this).datagrid("getSelections").length);
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("unselectRow", rowIndex);
				}
			},
			onUnselect: function(rowIndex, rowData) {
				// 选择操作时刷新展示重新排序所选择的数量
				$("#reorderCount").text($(this).datagrid("getSelections").length);
				if ( !IsCheckFlag ) {
					IsCheckFlag = true;
					$(this).datagrid("selectRow", rowIndex);
				}
			}
		});
	
		$('#htm_add').window({
			title : '添加公告banner',
			modal : true,
			width : 490,
			height : 390,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function(){
				commonTools.clearFormData($("#htm_add"));
			}
		});
		
		$("#main").show();
	});

	/**
	* 批量删除
	*/
	function del(){
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示",'确定要将选中的内容删除?',function(r){
				if(r){
					var ids = [];
					for(var i=0;i<rows.length;i+=1){
						ids.push(rows[i]['id']);	
					}
					$("#htm_table").datagrid("clearSelections"); //清除所有已选择的记录，避免重复提交id值	
					$("#htm_table").datagrid("loading");
					$.post("./admin_op/msgBulletin_batchDeleteMsgBulletin?idsStr=" + ids, function(result){
						$('#htm_table').datagrid('loaded');
						if(result['result'] == 0) {
							$.messager.alert('提示',result['msg']);
							$("#htm_table").datagrid("reload");
						} else {
							$.messager.alert('提示',result['msg']);
						}
					});	
				}
			});
		}else{
			$.messager.alert("温馨提示", "请先选择记录，再执行操作!");
		}
	}
	
	/**
	* TODO 目前是老版本使用的更新缓存，先保留，待几个版本后要废弃，目前点击此方法，还能刷新老版本数据
	* @modify zhangbo	2015-12-18
	*/
	function updateCache(){
		var rows = $("#htm_table").datagrid("getSelections");
		if(rows.length > 0){
			$.messager.confirm("温馨提示","确定要将选中的内容更新到缓存?",function(r){
				if(r){
					var ids = [];
					for(var i=0;i<rows.length;i+=1){
						ids.push(rows[i]['id']);	
					}
					$("#htm_table").datagrid("clearSelections"); //清除所有已选择的记录，避免重复提交id值	
					$("#htm_table").datagrid("loading");
					$.post("./admin_op/msgBulletin_updateMsgBulletinCache?idsStr=" + ids, function(result){
						$('#htm_table').datagrid('loaded');
						if(result['result'] == 0) {
							$.messager.alert('提示',result['msg']);
							$("#htm_table").datagrid("reload");
						} else {
							$.messager.alert('提示',result['msg']);
						}
					});	
				}
			});
		}else{
			$.messager.alert("温馨提示", "请先选择记录，再执行操作!");
		}
	}
	
	/**
	 * 点击修改时，打开窗口，并设值
	 * @modify zhangbo	2015-12-19
	 */
	function updateInit(id){
		
		$("#htm_table").datagrid("selectRecord", id);
		var row = $("#htm_table").datagrid("getSelected");
		
		$("#i-path").val(row.bulletinPath);	// 为path设值
		$("#i-name").val(row.bulletinName);
		$("#i-thumb").val(row.bulletinThumb);	// 为thumb设值
		$("#bulletin_img_edit").attr('src', row.bulletinPath);	// 展示商品集合图片
		$("#bulletin_thumb_img_edit").attr('src', row.bulletinThumb);	// 展示商品集合缩略图
		$("#s-type").combobox('setValue', row.bulletinType);
		$("#i-link").val(row.link);
		$("#i-id").val(row.id);
		
		$('#htm_add').window('open');
		
		$("#htm_table").datagrid("clearSelections");
		$("#htm_table").datagrid("clearChecked");
	}
	
	/**
	 * 添加窗口提交方法
	 * @author zhangbo	2015-12-18
	 */
	function addSubmit(){
		$('#add_form .opt_btn').hide();
		$('#add_form .loading').show();
		
		if( $('#add_form').form('validate') ) {
			$('#add_form').form('submit', {
				url: "./admin_op/msgBulletin_saveMsgBulletin",
				success: function(data){
					var result = $.parseJSON(data);
					if(result['result'] == 0) {
						$('#add_form .opt_btn').show();
						$('#add_form .loading').hide();
						$('#htm_add').window('close');  // 关闭添加窗口
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
	 * 查询结果集
	 * @author zhangbo	2015-12-18
	 */
	function searchBulletin() {
		// 为查询条件赋值
		queryParams.isCache = $("#ss_isCache").combobox("getValue");
		queryParams.category = $("#ss_Category").combobox("getValue");
		$('#htm_table').datagrid('load',queryParams);
	};
	
	/**
	 * 将勾选的公告添加到有奖活动
	 * @author zhangbo	2015-12-08
	 */
	function addToAwardActivity() {
		var rows = $('#htm_table').datagrid('getSelections');	
		var ids = [];
		for(var i=0;i<rows.length;i+=1){
			ids.push(rows[i]['id']);
		}
		$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
		$('#htm_table').datagrid('loading');
		var param = {
				bullentinIds: ids.toString()
		};
		$.post("./admin_trade/itemSet_addAwardActivityByBullentin", param, function(result){
			$('#htm_table').datagrid('loaded');
			$.messager.alert("温馨提示",result.msg);
		});
	};
	
	/**
	 * 重新排序
	 * @author zhangbo	2015-12-19
	 */
	function reorder() {
		var rows = $('#htm_table').datagrid('getSelections');	
		var ids = [];
		for(var i=0;i<rows.length;i+=1){
			ids.push(rows[i].id);
		}
		$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
		$('#htm_table').datagrid('loading');
		var param = {
			idsStr: ids.toString()
		};
		$.post("./admin_op/msgBulletin_reorderBulletin", param, function(result){
			$('#htm_table').datagrid('loaded');
			$.messager.alert("温馨提示",result.msg);
		});
	}
	
	/**
	 * 刷新缓存
	 * 
	 * @param refreshFlag	刷新缓存的标记位，1：刷新活动专题，2：刷新达人专题，3：刷新内容专题
	 * @author zhangbo	2015-12-19
	 */
	function refreshCache(refreshFlag) {
		$('#htm_table').datagrid('loading');
		var param = {
			refreshFlag: refreshFlag
		};
		$.post("./admin_op/msgBulletin_refreshBulletinCache", param, function(result){
			$('#htm_table').datagrid('loaded');
			$.messager.alert("温馨提示",result.msg);
		});
	}
	
</script>
</head>
<body>
	<div id="main" class="none">
		<div id="tb">
			<a href="javascript:void(0);" onclick="javascript:$('#htm_add').window('open');" class="easyui-linkbutton" title="添加" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
			<a href="javascript:void(0);" onclick="javascript:reorder();" class="easyui-linkbutton" plain="true" iconCls="icon-converter">重新排序<span id="reorderCount" type="text" style="font-weight:bold;">0</span></a>
			<a href="javascript:void(0);" onclick="javascript:updateCache();" class="easyui-linkbutton" title="更新缓存" plain="true" iconCls="icon-converter" id="updateCacheBtn">更新缓存</a>
			<select id="ss_isCache" class="easyui-combobox"  style="width:100px;">
		        <option value="" selected="selected">全部</option>
		        <option value="0">正在展示</option>
	   		</select>
	   		<select id="ss_Category" class="easyui-combobox"  style="width:100px;">
		   		<option value="" selected="selected">全部</option>
		   		<option value="1">有奖活动</option>
		   		<option value="2">无奖活动</option>
		   		<option value="3">达人专题</option>
		   		<option value="4">内容专题</option>
	   		</select>
	   		<a href="javascript:void(0);" onclick="javascript:searchBulletin();" class="easyui-linkbutton" plain="true" iconCls="icon-search">查询</a>
	   		<a href="javascript:void(0);" onclick="javascript:addToAwardActivity();" class="easyui-linkbutton" title="勾选当前公告，添加到活动下有奖活动分组" plain="true" iconCls="icon-add">添加到有奖活动</a>
	   		<a href="javascript:void(0);" onclick="javascript:refreshCache(1);" class="easyui-linkbutton" title="勾选当前公告，点击刷新活动专题缓存" plain="true" iconCls="icon-converter">刷新活动缓存</a>
	   		<a href="javascript:void(0);" onclick="javascript:refreshCache(2);" class="easyui-linkbutton" title="勾选当前公告，点击刷新达人专题缓存" plain="true" iconCls="icon-converter">刷新达人缓存</a>
	   		<a href="javascript:void(0);" onclick="javascript:refreshCache(3);" class="easyui-linkbutton" title="勾选当前公告，点击刷新内容专题缓存" plain="true" iconCls="icon-converter">刷新内容缓存</a>
		</div>
		
		<table id="htm_table"></table>
		
		<!-- 添加记录 -->
		<div id="htm_add">
			<form id="add_form" method="post">
				<table class="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">链接图片路径：</td>
							<td>
								<input id="i-path" name="path" class="none" readonly="readonly" >
								<a id="bulletin_upload_btn" style="position: absolute; margin:30px 0 0 200px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
								<img id="bulletin_img_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="174px" height="90px">
								<div id="bulletin_img_upload_status" class="update_status none" style="width: 205px; text-align: center;">
									上传中...<span class="upload_progress"></span><span>%</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="leftTd">链接缩略图路径：</td>
							<td>
								<input id="i-thumb" name="thumb" class="none" readonly="readonly" >
								<a id="bulletin_thumb_upload_btn" style="position: absolute; margin:30px 0 0 200px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
								<img id="bulletin_thumb_img_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="174px" height="90px">
								<div id="bulletin_thumb_img_upload_status" class="update_status none" style="width: 205px; text-align: center;">
									上传中...<span class="upload_progress"></span><span>%</span>
								</div>
							</td>
						</tr>
						<tr>
						<td class="leftTd">分类：</td>
							<td>
								<select id="s_category" name="category" class="easyui-combobox" style="width:223px;">
									<option value="1" selected="selected">有奖活动</option>
									<option value="2">无奖活动</option>
									<option value="3">达人专题</option>
									<option value="4">内容专题</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="leftTd">链接类型：</td>
							<td>
								<select id="s-type" name="type" class="easyui-combobox" style="width:223px;" >
									<option value="0" selected="selected">无需跳转</option>
		        					<option value="1">网页连接</option>
		        					<option value="2">频道id</option>
		        					<option value="3">用户id</option>
		        					<option value="4">活动标签</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="leftTd">链接：</td>
							<td><input id="i-link" name="link" style="width:220px;" ></td>
						</tr>
						<tr>
							<td class="leftTd">名字：</td>
							<td><input id="i-name" name="bulletinName" style="width:220px;" ></td>
						</tr>
						<tr>
							<td class="none"><input id="i-id" name="id"></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center;padding-top: 10px;">
								<a class="easyui-linkbutton" iconCls="icon-ok" onclick="addSubmit();">添加</a>
								<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_add').window('close');">取消</a>
							</td>
						</tr>
					</tbody>
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
        browse_button: 'bulletin_upload_btn',
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
            	$("#bulletin_upload_btn").hide();
            	$("#bulletin_img_edit").hide();
            	var $status = $("#bulletin_img_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#bulletin_img_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#bulletin_upload_btn").show();
            	$("#bulletin_img_edit").show();
            	$("#bulletin_img_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#bulletin_img_edit").attr('src', url);
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
        browse_button: 'bulletin_thumb_upload_btn',
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
            	$("#bulletin_thumb_upload_btn").hide();
            	$("#bulletin_thumb_img_edit").hide();
            	var $status = $("#bulletin_thumb_img_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#bulletin_thumb_img_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#bulletin_thumb_upload_btn").show();
            	$("#bulletin_thumb_img_edit").show();
            	$("#bulletin_thumb_img_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#bulletin_thumb_img_edit").attr('src', url);
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