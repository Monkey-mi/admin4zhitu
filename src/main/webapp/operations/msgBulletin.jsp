<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频道banner管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/common/css/htmCRUD20131111.css"></link>
<script type="text/javascript">
	var maxId=0,
	loadDateUrl="./admin_op/msgBulletin_queryMsgBulletin",
	delUrl="./admin_op/msgBulletin_batchDeleteMsgBulletin?idsStr=",
	addUrl="./admin_op/msgBulletin_insertMsgBulletin",
	updateUrl = "./admin_op/msgBulletin_updateMsgBulletin?id=",
	batchUpdateValidUrl = "./admin_op/msgBulletin_batchUpdateMsgBulletinValid",
	updateCacheUrl = "./admin_op/msgBulletin_updateMsgBulletinCache?idsStr=",
	tableQueryParams = {},
	tableInit = function() {
		tableLoadDate(1);
	};
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			tableQueryParams.maxId = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				tableQueryParams.maxId = maxId;
			}
		}
	};
	
	function tableLoadDate(pageNum){
		$("#htm_table").datagrid({
			title  :"频道banner管理",
			width  :1200,
			pageList : [5,10,30,50],
			pageSize : 5,
			loadMsg:"加载中....",
			url	   :	loadDateUrl,
			queryParams : tableQueryParams,
			remoteSort: true,
			pagination: true,
			idField   :'id',
			pageNumber: pageNum,
			toolbar:'#tb',
			columns: [[
				{field :'ck',checkbox:true},
				{field :'id',title:'ID',align:'center',width:80},
				{field : 'bulletinPath',title: '链接图片路径',align : 'center',width : 180,
					formatter: function(value,row,index) {
		  				return "<img title='无效' width='174px' height='90px' class='htm_column_img' src='" + value + "'/>";
		  			}
				},
				{field : 'bulletinType',title:'链接类型',align:'center',width : 100,
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
				{field : 'link',title: '链接',align : 'center',width : 130},
				/*
				{field : 'valid',title : '有效性',align : 'center', width: 45,
		  			formatter: function(value,row,index) {
		  				if(value == 1) {
		  					img = "./common/images/ok.png";
		  					return "<img title='有效' class='htm_column_img'  src='" + img + "'/>";
		  				}else if(value == 0){
			  				img = "./common/images/tip.png";
			  				return "<img title='无效' class='htm_column_img' src='" + img + "'/>";
		  				}
		  				return "";
		  			}
		  		},*/
				{field : 'addDate', title:'创建时间',align : 'center' ,width : 130,
					formatter:function(value,row,index){
						if(value){
							return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
						}else{
							return "";
						}
					}
				},
				{field : 'modifyDate', title:'最后修改时间',align : 'center' ,width : 130,
					formatter:function(value,row,index){
						if(value){
							return baseTools.parseDate(value).format("yyyy/MM/dd hh:mm:ss");
						}else{
							return "";
						}
					}
				},
				{field : 'operatorName',title: '最后操作者',align : 'center',width : 80},
				{field : 'opt',title : '操作',width : 120,align : 'center',rowspan : 1,
					formatter : function(value, row, index ) {
						var retStr="";
						if(row.valid == 0 || row.valid == 1){
							retStr = "<a title='修改信息' class='updateInfo' href='javascript:void(0);' onclick='javascript:updateInit(\""+ row.id + "\",\"" + row.bulletinPath + "\",\"" + row.bulletinType + "\",\"" + row.link + "\")'>【修改】</a>";
						}
						return retStr;
					}
				},
				
			]],
			onLoadSuccess:myOnLoadSuccess,
			onBeforeRefresh : myOnBeforeRefresh
		});
		var p = $("#htm_table").datagrid("getPager");
		p.pagination({
		});
	}
	
	
	$(function(){
	
		$("#ss_isCache").combobox({
			onSelect:function(rec){
				tableQueryParams.isCache = rec.value;
				$('#htm_table').datagrid('load',tableQueryParams);
			}
		});
		
		$('#htm_add').window({
			title : '添加频道精选推荐',
			modal : true,
			width : 490,
			height : 260,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function(){
				$("#i-path").val('');
				$("#bulletin_img_edit").attr("src", "./base/images/bg_empty.png");
				$("#s-type").combobox('setValue',0);
				$("#i-link").val('');
				$("#i-id").val('');		
			}
		});
		
		tableInit();
		$("#main").show();
	});
	
	/**
	 * 显示载入提示
	 */
	function showPageLoading() {
		var $loading = $("<div></div>");
		$loading.text('载入中...').addClass('page_loading_tip');
		$("body").append($loading);
	}

	/**
	 * 移除载入提示
	 */
	function removePageLoading() {
		$(".page_loading_tip").remove();
	}
	
	/**
	* 批量删除
	*/
	function del(){
		$.messager.confirm('更新缓存','确定要将选中的内容删除?',function(r){
			if(r){
				update(delUrl);
			}
		});
	}
	
	/**
	* 批量更新有效性
	*/
	function batchUpdateValid(valid){
		var url = batchUpdateValidUrl+"?valid="+valid+"&idsStr=";
		update(url);
	}
	
	/**
	* 更新缓存
	*/
	function updateCache(){
		$.messager.confirm('更新缓存','确定要将选中的内容更新到缓存?',function(r){
			if(r){
				update(updateCacheUrl);
			}
		});
	}
	
	function update(url){
		var rows = $('#htm_table').datagrid('getSelections');	
		var ids = [];
		for(var i=0;i<rows.length;i+=1){		
			ids.push(rows[i]['id']);	
		}	
		$('#htm_table').datagrid('clearSelections'); //清除所有已选择的记录，避免重复提交id值	
		$('#htm_table').datagrid('loading');
		$.post(url+ids,function(result){
			$('#htm_table').datagrid('loaded');
			if(result['result'] == 0) {
				$.messager.alert('提示',result['msg']);
				$("#htm_table").datagrid("reload");
			} else {
				$.messager.alert('提示',result['msg']);
			}
			
		});	
	}
	
	/**
	 * 判断是否选中要删除的记录
	 */
	function isSelected(rows) {
		if(rows.length > 0){
			return true;
		}else{
			$.messager.alert('操作失败','请先选择记录，再执行操作!','error');
			return false;
		}
	}
	
	
	function addInit(){
		$('#htm_add').window('open');
	}
	
	function updateInit(id,path,type,link){
		$("#i-path").val(path);
		$("#bulletin_img_edit").attr('src',path);
		$("#s-type").combobox('setValue',type);
		$("#i-link").val(link);
		$("#i-id").val(id);
		$('#htm_add').window('open');
	}
	
	function addSubmit(){
		var path = $("#i-path").val();
		var type = $("#s-type").combobox('getValue');
		var link = $("#i-link").val();
		var id = $("#i-id").val();
		var url="";
		if(id){
			url = updateUrl+id;
		}else{
			url = addUrl;
		}
		$('#htm_add .opt_btn').hide();
		$('#htm_add .loading').show();
		$.post(url,{
			'path':path,
			'type':type,
			'link':link
		},function(result){
			$('#htm_add .opt_btn').show();
			$('#htm_add .loading').hide();
			if(result['result'] == 0) {
				tableQueryParams.maxId=0;
				$("#htm_table").datagrid("reload");
				$('#htm_add').window('close');
			} else {
				$.messager.alert('提示',result['msg']);
			}
		},"json");
		
		
	}
	
	
</script>
</head>
<body>
	<div id="main" class="none">
		<div id="tb">
			<a href="javascript:void(0);" onclick="javascript:addInit();" class="easyui-linkbutton" title="添加" plain="true" iconCls="icon-add" id="addBtn">添加</a>
			<a href="javascript:void(0);" onclick="javascript:del();" class="easyui-linkbutton" title="删除" plain="true" iconCls="icon-cut" id="delBtn">删除</a>
			<!-- 暂时注释掉有效性
			<a href="javascript:void(0);" onclick="javascript:batchUpdateValid(1);" class="easyui-linkbutton" title="批量生效" plain="true" iconCls="icon-ok" id="updateTrueBtn">批量生效</a>
			<a href="javascript:void(0);" onclick="javascript:batchUpdateValid(0);" class="easyui-linkbutton" title="批量失效" plain="true" iconCls="icon-tip" id="updateFalseBtn">批量失效</a>
			 -->
			<a href="javascript:void(0);" onclick="javascript:updateCache();" class="easyui-linkbutton" title="更新缓存" plain="true" iconCls="icon-converter" id="updateCacheBtn">更新缓存</a>
			<select id="ss_isCache" class="easyui-combobox"  style="width:100px;">
		        <option value="" selected="selected">全部</option>
		        <option value="0">正在展示</option>
	   		</select>
		</div>
		<table id="htm_table"></table>
		<!-- 添加记录 -->
		<div id="htm_add">
			<form id="add_form"  method="post">
				<table class="htm_edit_table" width="480">
					<tbody>
						<tr>
							<td class="leftTd">链接图片路径：</td>
							<td>
								<input id="i-path" class="none" readonly="readonly" >
								<a id="bulletin_upload_btn" style="position: absolute; margin:30px 0 0 200px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
								<img id="bulletin_img_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="174px" height="90px">
								<div id="bulletin_img_upload_status" class="update_status none" style="width: 205px; text-align: center;">
									上传中...<span class="upload_progress"></span><span>%</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="leftTd">链接类型：</td>
							<td>
								<select id="s-type" class="easyui-combobox" style="width:223px;" >
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
							<td><input id="i-link" style="width:220px;" ></td>
						</tr>
						<tr>
							<td class="none"><input id="i-id"></td>
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
        domain: 'http://imzhitu.qiniudn.com/',
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
            	var url = 'http://imzhitu.qiniudn.com/'+$.parseJSON(info).key;
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
    
	</script>
</body>
</html>