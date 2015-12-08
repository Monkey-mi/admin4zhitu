<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String worldId = request.getParameter("worldId"); %>
<% String worldAuthorId = request.getParameter("worldAuthorId"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jquery -->
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.8.2.min.js"></script>
<!-- easy ui -->
<link rel="stylesheet" type="text/css" href="${webRootPath }/base/js/jquery/jquery-easyui-1.3.5/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${webRootPath }/base/js/jquery/jquery-easyui-1.3.5/themes/icon.css" />
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-easyui-1.3.2/plugins/jquery.combogrid.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
<script language="javascript" src="${webRootPath }/base/js/baseTools.js" type="text/javascript"></script>
<title>织图附近标签</title>
<style>
	.current-btn{
		overflow: hidden;
    	font-size: 16px;
	}
	
	.add-into-btn{
		overflow: hidden;
    	font-size: 16px;
	}
	
</style>
<script type="text/javascript">
	var worldId = <%= worldId %>;
	var worldAuthorId = <%= worldAuthorId %>;
	/**
	 * jquery初始化
	 */
	$(function() {
		
	    $("#htm_channel").window({
	    	title: "织图添加到附近标签",
	    	iconCls:'icon-add',
 	        width: $(window).width(),
	        height: $(window).height(), 
	        collapsible: false,
	        minimizable: false,
	        maximizable: false,
	        closable: false
	    });
		
		$('#ss-labelName').combogrid({
		    panelWidth : 330,
		    panelHeight : 330,
		    loadMsg : '加载中，请稍后...',
			pageList : [10,20],
			toolbar:"#search-label-tb",
		    multiple : false,
		    required : false,
		   	idField : 'id',
		    textField : 'labelName',
		    url : './admin_op/near_queryNearLabel',
		    pagination : true,
		    onClickRow :channelComboboxOnSelect ,
		    columns:[[
				{field : 'id',title : 'id',align : 'center',width : 80},
				{field : 'labelName',title : '标签名', align : 'center',width : 180, height:60},
				{field : 'cityName',title : '城市名',align : 'center',width : 60}
		    ]]
		});
		var p = $('#ss-labelName').combogrid('grid').datagrid('getPager');
		p.pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
/* 				if(pageNumber <= 1) {
					searchChannelMaxId = 0;
					searchChannelQueryParams.maxId = searchChannelMaxId;
				} */
			}
		});
		
		// 页面打开后打开选择标签gridpanel
		 $("#ss-labelName").combogrid('showPanel'); 
		
		 loadCurrentLabel();
	});
	
	/**
	 * 频道选择下拉框触发onselect方法
	 * @param record	选中的item
	 * @author mishengliang 2015-12-07
	 */
	function channelComboboxOnSelect(index,record) {
    	var isExist = isExistInCurrentChannel(record.id);
    	
    	if (!isExist) {
    		// 添加将要进入的标签，样式设定为add-into-btn，为了区分当前所在标签，还是将要加入的标签
    		var labelNameBtn = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn add-into-btn' labelId='"
    				+ record.id + "'>" + record.labelName + "</a>")
    				.click(function(){
    					$(this).remove();
    				});
    		$("#label_area").append(labelNameBtn);
    		
    	}
    	
    };
	
	/**
	 * 加载织图当前所在标签
	 * @author mishengliang 2015-12-08
	 */
	function loadCurrentLabel() {
		$.post("admin_op/near_queryNearLabelWorld",{
			'worldId':worldId
		},function(result){
			if(result['result'] == 0){
				var labelNameArray = result.rows;
				for (var i=0; i<labelNameArray.length; i++) {
					// 添加当前所在频道，样式设定为current-btn，为了区分当前所在频道，还是将要加入的频道
					var currentLabelBtn = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn current-btn' labelWorldId='"+ labelNameArray[i].id +"'  labelId='" 
							+ labelNameArray[i].nearLabelId + "'>" + labelNameArray[i].nearLabelName + "</a>")
							.click(function(){
								// 要把当前<a>标签赋值，不然提示confirm中调不到
								var currentBtn = $(this);
								$.messager.confirm("温馨提示","确定要删除当前所在频道？",function(r){
									if (r) {
										// 调用删除频道织图关联关系方法
										deleteLabelWorld(currentBtn.attr("labelWorldId"));
										
										currentBtn.remove();
									}
								});
							});
					$("#label_area").append(currentLabelBtn);
				}
			}else{
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},"json");
	};
	
	/**
	 * 删除织图与标签的关联关系
	 * @author mishengliang 2015-12-08
	 */
	function deleteLabelWorld(deleteId) {
		// 删除标签织图
		$.post("admin_op/near_batchDeleteNearLabelWorld",{
			idsStr: deleteId
		},function(result){
			if(result['result'] == 0){
				$.messager.alert("温馨提示",result.msg);
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		});
	};
	
	/**
	 * 判断频道id是否在标签区域中
	 * @return true：在当前标签中，false：不在当前标签中
	 * @author mishengliang 2015-12-07
	 */
	function isExistInCurrentChannel(labelId){
		var isExist = false;
		
		// 遍历当前所在标签区域对应的button，对比labelId是否存在
		$("#label_area a").each(function(){
			if ( labelId == $(this).attr("labelId") ) {
				isExist = true;
			}
		});
		
		return isExist;
	};
	
	/**
	 * 保存织图到选择的标签中
	 * @author mishengliang 2015-12-07
	 */
	function saveLabelWorldSubmit(){
		var labelIds = [];
		
		//mishengliang
	    var labelData = 	$("#ss-labelName").combogrid("grid").datagrid("getData").rows;
		var channelNameInText = $("#ss-labelName").combogrid("textbox").val(); 
	    for(var i = 0; i < labelData.length; i++){
	    	if(channelNameInText ==  labelData[i].channelName && !isExistInCurrentChannel(labelData[i].id)){
	    		labelIds.push(labelData[i].id);
	    		break;
	    	}
	    }
	  
		// 遍历标签区域中，将要添加进标签的button
		$("#label_area .add-into-btn").each(function(){
			labelIds.push($(this).attr("labelId"));
		});
		
		if ( labelIds.length == 0 ) {
			$.messager.alert("温馨提示","请选择要添加进入的频道！若无，请关闭窗口。");
		} else {
			//该织图进入标签
			$.post("admin_op/near_addNearLabelWorlds",{
				nearLabelIds: labelIds.toString(),
				worldId: worldId,
				worldAuthorId:worldAuthorId
			},function(result){
				if(result.result == 0){
					parent.$.fancybox.close();
				} else {
					$.messager.alert("错误提示",result.msg);  //提示添加信息失败
				}
			});
		}
	};
	
	//通过ID查询标签
	function searchLabelById() {
		labelMaxId = 0;
		var labelName = $('#label-searchbox').searchbox('getValue');
		labelQueryParams = {
			'maxId' : labelMaxId,
			'nearLabel.labelName' : labelName
		};
		$("#ss-labelName").combogrid('grid').datagrid("load",labelQueryParams);
	}
	
</script>
</head>
<body>
	<!-- 添加到标签 -->
	<div id="htm_channel">
		<table class="htm_edit_table" align="center" border="0">
			<tbody>
				<tr>
					<td class="leftTd">标签：</td>
					<td>
						<div id="label_area" style="width:300px;height:100px;border:solid 1px #4796EF;"></div>
					</td>
				</tr>
				<tr>
					<td class="leftTd">标签名称：</td>
					<td>
						<input id="ss-labelName" style="width:300px;" />
					</td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="saveLabelWorldSubmit()">确定</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div id="search-label-tb" style="padding:5px;height:auto" class="none">
		<input id="label-searchbox" searcher="searchLabelById" class="easyui-searchbox" prompt="标签名 搜索" style="width:200px;"/>
	</div>
	
</body>
</html>