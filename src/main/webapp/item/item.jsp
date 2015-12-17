<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<jsp:include page="/item/showItemWindowAndreOrder.jsp"></jsp:include>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript">
	var maxId = 0,
	init = function() {
		toolbarComponent = '#tb';
		myQueryParams = {
			'item.maxId' : maxId
		}
		loadPageData(initPage);
	},
	hideIdColumn = false,
	htmTableTitle = "商品列表", //表格标题
	loadDataURL = "./admin_trade/item_queryItemList",
	deleteURI = "./admin_trade/item_batchDeleteItem?ids=", //删除请求地址
	saveURL = "./admin_trade/item_saveItem",
	updateByIdURL = "./admin_trade/item_updateItem",
	queryByIdURL = "./admin_trade/item_queryItemById",
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams['item.maxId'] = maxId;
		}
	},
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['item.maxId'] = maxId;
			}
		}
	},
	columnsFields = [
  			{field: "ck", checkbox:true},
 			{field: "id", title: "商品ID", align: "center"},
 			{field: "name", title: "商品名称", align: "center",width:30},
 			{field: "summary", title: "简介", align: "center",width:30},
 			{field: "description", title: "详情描述", align: "center",width:30},
 			{field: "price", title: "价格", align: "center"},
 			{field: "link", title: "链接", align: "center",
 				formatter: function(value,row,index) {
 	  				return "<a href='"+value+"' target='_blank'>"+value+"</a>";
 	  			}
 			},
 			{field: "worldId", title: "关联织图id", align: "center"},
 			{field: "likeNum", title: "点赞数", align: "center"},
 			{field: "imgPath", title: "商品图片", align: "center",
 				formatter: function(value,row,index) {
	  				return "<a onmouseover='setShowItemInfoTimer("+ row.id +")' onmouseout='javascript:clearShowItemInfo();'><img width='100px' height='100px' class='htm_column_img' src='" + value + "'/></a>";
 	  			}
 			},
 			{field : 'opt',title : '操作',align : 'center',rowspan : 1,
 				formatter : function(value, row, index ) {
 					return "<a title='查看详情' class='updateInfo' href='javascript:void(0);' onclick='javascript:initEditWindow(\""+ row.id + "\",\"" + index + "\"," + true + ")'>【详情】</a>";
 				}
 			}
 		],
    
    
    onBeforeInit = function() {
		showPageLoading();
	},
	onAfterInit = function() {
		
		$('#htm_edit').window({
			title : '添加标签',
			modal : true,
			width : 620,
			height : 530,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-edit',
			resizable : false,
			onClose : function() {
				var $form = $('#edit_form');
				clearFormData($form);
				$("#edit_form .opt_btn").show();
				$("#edit_form .loading").hide();
				$("#img_edit").attr("src", "./base/images/bg_empty.png");
				$("#edit_form").hide();
				$("#edit_loading").show();
			}
		});
		
		removePageLoading();
		$("#main").show();
	};
	
/**
 * 初始化贴纸信息编辑窗口
 */
function initEditWindow(id, index, isUpdate) {
	loadEditFormValidate(index, isUpdate);
	if(isUpdate) {
		$('#htm_edit').panel('setTitle', '修改商品');
		$('#htm_edit').window('open');
		$.post(queryByIdURL,{
			"id":id
		}, function(result){
			if(result['result'] == 0) {
				var obj = result['obj'];
				$("#imgPath_edit").val(obj['imgPath']);
				$("#img_edit").attr('src', obj['imgPath']);
				$("#name_edit").val(obj['name']);
				$("#summary_edit").val(obj['summary']);
				$("#description_edit").val(obj['description']);
				$("#price_edit").val(obj['price']);
				$("#link_edit").val(obj['link']);
				
				$("#taobaoType_edit").combobox('setValue', obj['taobaoType']);
				$("#taobaoId_edit").val(obj['taobaoId']);
				$("#likeNum_edit").val(obj['likeNum']);
				$("#worldId_edit").val(obj['worldId']);
				
				$("#id_edit").val(obj['id']);
				
				$("#edit_loading").hide();
				$("#edit_form").show();
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
			
		},"json");
	} else {
		$("#id_edit").val(id);
		$("#taobaoType_edit").combobox('setValue', 1);
		
		$('#htm_edit').panel('setTitle', '添加商品');
		$('#htm_edit').window('open');
		$("#edit_loading").hide();
		$("#edit_form").show();
	}
}

//提交表单，以后补充装载验证信息
function loadEditFormValidate(index, isUpdate) {
	var url = saveURL;
	if(isUpdate) {
		url = updateByIdURL;
	}
	var $form = $('#edit_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#edit_form .opt_btn").hide();
				$("#edit_form .loading").show();
				//验证成功后以异步方式提交表单
				$.post(url,$form.serialize(),
					function(result){
						formSubmitOnce = true;
						$("#edit_form .opt_btn").show();
						$("#edit_form .loading").hide();
						if(result['result'] == 0) {
							$('#htm_edit').window('close');  //关闭添加窗口
							
							if(isUpdate) {
								$("#htm_table").datagrid('reload');
							} else {
								maxId = 0;
								myQueryParams['item.maxId'] = maxId;
								$("#htm_table").datagrid('load',myQueryParams);
							}
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");				
				return false;
			}
		}
	});
	
	$("#imgPath_edit")
	.formValidator({empty:false, onshow:"请选图片（必填）",onfocus:"请选图片",oncorrect:"正确！"})
	.regexValidator({regexp:"url", datatype:"enum", onerror:"链接格式不正确"});
	
	$("#name_edit")
	.formValidator({empty:true, onshow:"输入商品名,方便搜索哦",onfocus:"请输入商品名",oncorrect:"正确！"});
	
	$("#price_edit")
	.formValidator({empty:false, onshow:"请输入价格（必填）",onfocus:"请输入价格",oncorrect:"正确！"});
	
	$("#link_edit")
	.formValidator({empty:false, onshow:"请输入商品链接（必填）",onfocus:"请输入链接",oncorrect:"正确！"});
	
	$("#taobaoType_edit")
	.formValidator({empty:false, onshow:"请选择淘宝类型（必填）",onfocus:"请选择淘宝类型",oncorrect:"正确！"});
	
	$("#taobaoId_edit")
	.formValidator({empty:false, onshow:"请输入淘宝ID（必填）",onfocus:"请输入淘宝ID",oncorrect:"正确！"});
	
	$("#worldId_edit")
	.formValidator({empty:true, onshow:"以织图形式展示（可选）",onfocus:"请输入织图ID",oncorrect:"正确！"});
	
	$("#likeNum_edit")
	.formValidator({empty:true, onshow:"请输入点赞数量（可选）",onfocus:"请选输入点赞数",oncorrect:"正确！"});
	
	
}

function searchItemByName() {
	maxId = 0;
	myQueryParams['item.maxId'] = maxId;
	myQueryParams['item.name'] = $('#ss-itemName').searchbox('getValue');
	$("#htm_table").datagrid("load",myQueryParams);
}

	
</script>
</head>
<body>
	<div id="main" style="display: none;">
		
		<table id="htm_table"></table>
		
		<div id="tb" style="padding:5px;height:auto" class="none">
			<a href="javascript:void(0);" onclick="javascript:initEditWindow(0,0,false);" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加</a>
			<a href="javascript:void(0);" onclick="htmDelete('id')" class="easyui-linkbutton" plain="true" iconCls="icon-cut">批量删除</a>
			
			<input id="ss-itemName" searcher="searchItemByName" class="easyui-searchbox" prompt="输入名字搜索" />
			
		</div>
		
		<!-- 添加城市分组窗口 -->
		<div id="htm_edit">
			<form id="edit_form" action="./admin_op/near_insertNearLabel" method="post">
				<table class="htm_edit_table" width="580px" >
					<tr>
						<td  class="leftTd" style="width:50px">图片：</td>
						<td>
							<input id="imgPath_edit" name="item.imgPath" class="none" readonly="readonly" />
							<a id="imgPath_edit_upload_btn" style="position: absolute; margin:30px 0 0 100px" class="easyui-linkbutton" iconCls="icon-add">上传图片</a> 
							<img id="img_edit"  alt="" src="${webRootPath }/base/images/bg_empty.png" width="90px" height="90px">
							<div id="imgPath_edit_upload_status" class="update_status none" style="width: 205px; text-align: center;">
								上传中...<span class="upload_progress"></span><span>%</span>
							</div>
						</td>
						<td class="rightTd">
							<div id="imgPath_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">名称：</td>
						<td>
							<input id="name_edit" name="item.name" onchange="validateSubmitOnce=true;"/>
						</td>
						<td>
							<div id="name_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">标题：</td>
						<td colspan="2">
							<textarea id="summary_edit" name="item.summary" style="width:480px;" onchange="validateSubmitOnce=true;"></textarea>
						</td>
					</tr>
					
					<tr>
						<td class="leftTd">描述：</td>
						<td colspan="2">
							<textarea id="description_edit" name="item.description" style="width:480px;" required="true"></textarea>
						</td>
					</tr>
					<tr>
						<td class="leftTd">价格：</td>
						<td>
							<input id="price_edit" name="item.price" />
						</td>
						<td>
							<div id="price_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">链接：</td>
						<td>
							<input id="link_edit" name="item.link" style="width:300px;"/>
						</td>
						<td>
							<div id="link_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">类型：</td>
						<td>
							<select id="taobaoType_edit" class="easyui-combobox" name="item.taobaoType" panelHeight="auto"/>
							    <option value="1">淘宝</option>
							    <option value="2">天猫</option>
							</select>
						</td>
						<td>
							<div id="taobaoType_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						<td class="leftTd">淘宝ID：</td>
						<td>
							<input id="taobaoId_edit" name="item.taobaoId" />
						</td>
						<td>
							<div id="taobaoId_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					
					<tr>
						
						<td class="leftTd">点赞数：</td>
						<td>
							<input id="likeNum_edit" name="item.likeNum" >
						</td>
						<td>
							<div id="likeNum_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					<tr>
						
						<td class="leftTd">织图id：</td>
						<td>
							<input id="worldId_edit" name="item.worldId" >
						</td>
						<td>
							<div id="worldId_editTip" style="display: inline-block;" class="tipDIV"></div>
						</td>
					</tr>
					
					<tr style="display:none">
						<td colspan="3">
							<input id="id_edit" name="item.id" />
						</td>
					</tr>
					
					<tr>
						<td colspan="3" style="text-align: center;padding-top: 20px;" >
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#edit_form').submit();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_edit').window('close');">取消</a>
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
        browse_button: 'imgPath_edit_upload_btn',
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
            	$("#imgPath_edit_upload_btn").hide();
            	$("#img_edit").hide();
            	var $status = $("#imgPath_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(0);
            	$status.show();
            	
            },
            'BeforeUpload': function(up, file) {
            },
            
            'UploadProgress': function(up, file) {
            	var $status = $("#imgPath_edit_upload_status");
            	$status.find('.upload_progress:eq(0)').text(file.percent);

            },
            'UploadComplete': function() {
            	$("#imgPath_edit_upload_btn").show();
            	$("#img_edit").show();
            	$("#imgPath_edit_upload_status").hide();
            },
            'FileUploaded': function(up, file, info) {
            	var url = 'http://static.imzhitu.com/'+$.parseJSON(info).key;
            	$("#img_edit").attr('src', url);
            	$("#imgPath_edit").val(url);
            },
            'Error': function(up, err, errTip) {
                $.messager.alert('上传失败',errTip);  // 提示添加信息失败
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