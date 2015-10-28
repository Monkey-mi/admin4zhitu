<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% String worldId = request.getParameter("worldId"); %>
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
<title>织图添加到频道</title>
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
	/**
	 * jquery初始化
	 */
	$(function() {
		
	    $("#htm_channel").window({
	    	title: "织图添加到频道",
	    	iconCls:'icon-add',
	        width: $(window).width(),
	        height: $(window).height(),
	        collapsible: false,
	        minimizable: false,
	        maximizable: false,
	        closable: false
	    });
		

	    
	    $("#ss-channel").combobox({
	        url: "./admin_op/channel_queryAllChannel",
	        valueField: "id",
	        textField: "channelName",
	        selectOnNavigation:false,
//	        multiple: true,
	        onSelect: channelComboboxOnSelect,
	        onLoadSuccess: function() {
	        	// 加载成功后，将缓存中数据放入输入框
	        	$("#ss-channel").combobox("textbox").val(baseTools.getCookie("CHANNEL_SEARCHBOX_VALUE"));
	        }
	    });
		
		// 页面打开后打开选择频道gridpanel
		$("#ss-channel").combobox('showPanel');
		
		// 然后焦点聚焦在频道输入框上
		$("#ss-channel").combobox("textbox").focus();
		
		// 加载织图当前所在频道
		loadCurrentChannel();
	});
	
	/**
	 * 频道选择下拉框触发onselect方法
	 * @param record	选中的item
	 * @author zhangbo 2015-10-22
	 */
	function channelComboboxOnSelect(record) {
    	var isExist = isExistInCurrentChannel(record.id);
    	
    	if (!isExist) {
    		// 添加将要进入的频道，样式设定为add-into-btn，为了区分当前所在频道，还是将要加入的频道
    		var channelNameBtn = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn add-into-btn' channelId='"
    				+ record.id + "'>" + record.channelName + "</a>")
    				.click(function(){
    					$(this).remove();
    				});
    		$("#channel_area").append(channelNameBtn);
    		
    	}
    	// 选择以后取消选择，进而能多次操作同一条数据
    	$("#ss-channel").combobox("unselect", record.id);
    	baseTools.setCookie("CHANNEL_SEARCHBOX_VALUE",record.channelName,7*24*60*60*1000);
    };
	
	/**
	 * 加载织图当前所在频道
	 * @author zhangbo 2015-10-22
	 */
	function loadCurrentChannel() {
		$.post("./admin_ztworld/ztworld_queryWorld",{
			'worldId':worldId
		},function(result){
			if(result['result'] == 0){
				var channelNameArray = result.htworld.channelNames;
				for (var i=0; i<channelNameArray.length; i++) {
					// 添加当前所在频道，样式设定为current-btn，为了区分当前所在频道，还是将要加入的频道
					var currentChannelBtn = $("<a href='javascript:void(0);' class='easyui-linkbutton l-btn current-btn' channelId='" 
							+ channelNameArray[i].id + "'>" + channelNameArray[i].name + "</a>")
							.click(function(){
								// 要把当前<a>标签赋值，不然提示confirm中调不到
								var currentBtn = $(this);
								$.messager.confirm("温馨提示","确定要删除当前所在频道？",function(r){
									if (r) {
										// 调用删除频道织图关联关系方法
										deleteChannelWorld(currentBtn.attr("channelId"));
										
										currentBtn.remove();
									}
								});
							});
					$("#channel_area").append(currentChannelBtn);
				}
			}else{
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		},"json");
	};
	
	/**
	 * 删除织图与频道的关联关系
	 * @author zhangbo 2015-10-22
	 */
	function deleteChannelWorld(channelId) {
		// 删除频道织图
		$.post("./admin_op/channel_deleteChannelWorldByChannelIdAndWorldId",{
			channelId: channelId,
			worldId: worldId
		},function(result){
			if(result['result'] == 0){
				$.messager.alert("温馨提示",result.msg);
			} else {
				$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
			}
		});
	};
	
	/**
	 * 判断频道id是否在频道区域中
	 * @return true：在当前频道中，false：不在当前频道中
	 * @author zhangbo 2015-10-22
	 */
	function isExistInCurrentChannel(channelId){
		var isExist = false;
		
		// 遍历当前所在频道区域对应的button，对比channelId是否存在
		$("#channel_area a").each(function(){
			if ( channelId == $(this).attr("channelId") ) {
				isExist = true;
			}
		});
		
		return isExist;
	};
	
	/**
	 * 保存织图到选择的频道中
	 * @author zhangbo 2015-10-22
	 */
	function saveChannelWorldSubmit(){
		var channelIds = [];
		
		//mishengliang
	    var channelData = 	$("#ss-channel").combobox("getData");
		var channelNameInText = $("#ss-channel").combobox("textbox").val().trim(); 
	    for(var i = 0; i < channelData.length; i++){
	    	if(channelNameInText ==  channelData[i].channelName.trim()){
	    		channelIds.push(channelData[i].id);
	    		break;
	    	}
	    }
	  
		// 遍历频道区域中，将要添加进频道的button
		$("#channel_area .add-into-btn").each(function(){
			channelIds.push($(this).attr("channelId"));
		});
		
		if ( channelIds.length == 0 ) {
			$.messager.alert("温馨提示","请选择要添加进入的频道！若无，请关闭窗口。");
		} else {
			//该织图进入频道
			$.post("./admin_op/channel_saveWorldIntoChannels",{
				ids: channelIds.toString(),
				worldId: worldId
			},function(result){
				if(result.result == 0){
					parent.$.fancybox.close();
				} else {
					$.messager.alert("错误提示",result.msg);  //提示添加信息失败
				}
			});
		}
	};
	
</script>
</head>
<body>
	<!-- 添加到频道 -->
	<div id="htm_channel">
		<table class="htm_edit_table" width="700">
			<tbody>
				<tr>
					<td class="leftTd">频道：</td>
					<td>
						<div id="channel_area" style="width:350px;height:100px;border:solid 1px #4796EF;"></div>
					</td>
				</tr>
				<tr>
					<td class="leftTd">频道名称：</td>
					<td>
						<input id="ss-channel" style="width:200px;" />
					</td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="saveChannelWorldSubmit()">确定</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
</body>
</html>