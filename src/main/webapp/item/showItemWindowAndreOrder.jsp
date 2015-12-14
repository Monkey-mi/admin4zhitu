<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  String itemSetId =  request.getParameter("itemSetId");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}" />
<script type="text/javascript">
	var itemSetId = <%= itemSetId%>;
	var showItemInfoTimer;
	var showItemSetInfoTimer;
	
	$(function(){
		
 		//排序窗口初始化
		$('#htm_superb_set').window({
			title : '重新排序',
			modal : true,
			width : 600,
			height : 145,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-tip',
			resizable : false
		}).show(); 
 		
		$('#itemSetId').val(itemSetId);
	});
	
 	//集合商品的重新排序模块
        function reSuperbForSet() {
        	$("#superb_form_set").find('input[name="reIndexId"]').val('');
        	$("#schedula").datetimebox('clear');
        	$('#htm_superb .opt_btn').show();
        	$('#htm_superb .loading').hide();
        	
        	var rows = $("#htm_table_set").datagrid('getSelections');
        	$('#superb_form_set .reindex_column').each(function(i){
        		if(i<rows.length)
        			$(this).val(rows[i]['id']);
        	});
        	// 打开添加窗口
        	$("#htm_superb_set").window('open');
        	
        }
        
        function submitReSuperbFormForSet() {
        	var $form = $('#superb_form_set');
        	if($form.form('validate')) {
        		$('#htm_superb_set .opt_btn').hide();
        		$('#htm_superb_set .loading').show();
        		$('#superb_form_set').form('submit', {
        			url: $form.attr('action'),
        			data:{'itemSetId':itemSetId},
        			success: function(data){
        				var result = $.parseJSON(data);
        				$('#htm_superb_set .opt_btn').show();
        				$('#htm_superb_set .loading').hide();
        				if(result['result'] == 0) { 
        					$('#htm_table_set').datagrid('clearSelections');
        					$('#htm_table_set').datagrid('reload');
        					$('#htm_table').datagrid('reload');
        					$('#htm_superb_set').window('close');  //关闭添加窗口
        				} else {
        					$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
        				}
        			}
        		});
        	} 
        	
        } 
        
        function setShowItemInfoTimer(itemId){
        	if(showItemInfoTimer != ''){
        		clearShowItemInfoTimer();
        	}
        	showItemInfoTimer = setTimeout("showItemInfo"+"("+ itemId +")",400);
        }
        
        function clearShowItemInfoTimer(){
        	$("#imgPath_show").attr("src",null);
        	$("#summary_show").text('');
        	$("#description_show").text('');
        	clearTimeout(showItemInfoTimer);
        }
        
        function showItemInfo(itemId){
        	$.post("./admin_trade/item_queryItemById",{
        		'id':itemId
        	},function(result){
        		var row = result.obj;
        		if(result.result == 0){
		        	$("#imgPath_show").attr("src",row.imgPath);
		        	$("#summary_show").text(row.summary);
		        	$("#description_show").text(row.description);
        		}else{
		        	$("#imgPath_show").attr("src",null);
		        	$("#summary_show").text('');
		        	$("#description_show").text('');
        		}
        	},"json");
    		$("#showItemInfoWindow").css('left', ($(window).width() - 600)/2 + 'px');
    		$("#showItemInfoWindow").css('top',$(document).scrollTop() + ($(window).height() - 300)/2  + 'px');
    		$("#showItemInfoWindow").css('display','');
    		return false;
        }
        
        function clearShowItemInfo(){
        	clearTimeout(showItemInfoTimer);
        	$("#showItemInfoWindow").css('display','none');
        }
        
        
        /* 浮动窗口显示商品集合信息 */
        
        function setShowItemSetInfoTimer(itemSetId){
        	if(showItemSetInfoTimer != ''){
        		clearShowItemSetInfoTimer();
        	}
        	showItemSetInfoTimer = setTimeout("showItemSetInfo"+"("+ itemSetId +")",400);
        }
        
        function clearShowItemSetInfoTimer(){
        	$("#set_imgPath_show").attr("src",null);
        	$("#set_summary_show").text('');
        	$("#set_description_show").text('');
        	clearTimeout(showItemSetInfoTimer);
        }
        
        function showItemSetInfo(itemSetId){
        	//ID获取行值
        	$('#htm_table').datagrid("selectRecord",itemSetId);
        	var row = $('#htm_table').datagrid("getSelected");
        	//赋值给浮动页面
        	$("#set_imgPath_show").attr("src",row.path);
        	$("#set_summary_show").text(row.title);
        	$("#set_description_show").text(row.description);
        	//设置浮动页面显示位置
    		$("#showItemSetInfoWindow").css('left', ($(window).width() - 630)/2 + 'px');
    		$("#showItemSetInfoWindow").css('top',$(document).scrollTop() + ($(window).height() - 220)/2  + 'px');
    		$("#showItemSetInfoWindow").css('display','');
    		return false;
        }
        
        function clearShowItemSetInfo(){
        	$('#htm_table').datagrid("clearSelections");
        	clearTimeout(showItemSetInfoTimer);
        	$("#showItemSetInfoWindow").css('display','none');
        }
        
</script>
</head>
<body>
		
	<!-- 集合重排模块 -->
<div id="htm_superb_set" hidden=true>
		<form id="superb_form_set" action="./admin_trade/item_updateSetItemSerial" method="post">
			<table class="htm_edit_table" width="580">
				<tbody>
					<tr>
						<td class="leftTd">商品ID：</td>
						<td>
							<input name="reIndexId" class="easyui-validatebox reindex_column" required="true"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<br />
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
							<input name="reIndexId" class="reindex_column"/>
						</td>
					</tr>
					<tr>
						<td class="opt_btn" colspan="2" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitReSuperbFormForSet();">确定</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_superb_set').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="2" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">排序中...</span>
						</td>
					</tr>
					<tr hidden=true>
						<td><input id="itemSetId"  name="itemSetId" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	
	<!-- 浮动商品信息展示 -->
	<div id="showItemInfoWindow" style="display:none;height:320px;width:630px;position:absolute;z-index:1000;background:#ffffff;box-shadow: 0px 0px 10px #888888;">
		<div style="margin:10px;width:300px;"><img id="imgPath_show" alt="" src="" style="width:300px;height:300px;" /></div>
		<div style="width:300px;margin:-300px 10px 10px 320px">
			<div id="summary_show" style="font-size:12px;text-align: left;color:#fffff;margin-bottom:8px"></div>
			<div id="description_show" style="color:#797373;font-size:10px;text-align: justify;"></div>
		</div>
	</div>
		
		
	<!--  浮动商品集合信息展示 -->	
	<div id="showItemSetInfoWindow" style="display:none;height:220px;width:630px;position:absolute;z-index:1000;background:#ffffff;box-shadow: 0px 0px 10px #888888;">
		<div style="margin:10px;width:400px;"><img id="set_imgPath_show" alt="" src="" style="width:400px;height:200px;" /></div>
		<div style="width:200px;margin:-200px 10px 10px 420px">
			<div id="set_summary_show" style="font-size:12px;text-align: left;color:#fffff;margin-bottom:8px"></div>
			<div id="set_description_show" style="color:#797373;font-size:10px;text-align: justify;"></div>
		</div>
	</div>
		
</body>
</html>
</html>