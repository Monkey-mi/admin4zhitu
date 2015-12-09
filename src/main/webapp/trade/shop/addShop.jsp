<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商家信息</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath}/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath }/common/js/commonTools.js"></script>
<script type="text/javascript">

	$(function(){
		// 主表格
		$("#htm_table").datagrid({});
		
		// 展示界面
		$("#main").show();
	});
	
	/**
	 * 提交数据
	 * @author zhangbo 2015-11-20
	 */
	function submitForm(){
		$("#add_shop_form").form("submit", {
			url: $("#add_shop_form").attr("action"),
			success: function(data){
				// 此页面一般是通过fancybox打开，提交成功后则关闭
				parent.$.fancybox.close();
			}
		});
	};

</script>
</head>
<body>
	
	<div id="main" style="display: none;">
		
		<!-- 添加商家主界面 -->
		<form id="add_shop_form" action="./admin_trade/shop_addShop" method="post">
			<table class="htm_edit_table" width="700">
				<tr>
					<td class="leftTd">名称：</td>
					<td>
						<input name="shopName" type="text" class="easyui-validatebox" data-options="required:true">
					</td>
					<td class="leftTd">类型：</td>
					<td>
						<input name="typeIds" class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'./admin_trade/shopType_queryShopType,multiple:true'">
					</td>
				</tr>
				<tr>
					<td class="leftTd">省：</td>
					<td>
						<input name="provinceId" class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'./admin_op/addr_getProvinceMap'">
					</td>
					<td class="leftTd">手机：</td>
					<td>
						<input name="phone" type="text">
					</td>
				</tr>
				<tr>
					<td class="leftTd">市：</td>
					<td>
						<input name="cityId" class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'./admin_op/addr_getCityMap'">
					</td>
					<td class="leftTd">电话：</td>
					<td>
						<input name="telephone" type="text">
					</td>
				</tr>
				<tr>
					<td class="leftTd">区：</td>
					<td>
						<input name="districtId" class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'./admin_op/addr_getDistrictMap'">
					</td>
					<td class="leftTd">QQ：</td>
					<td>
						<input name="qq" type="text">
					</td>
				</tr>
				<tr>
					<td class="leftTd">地址：</td>
					<td>
						<input name="address" type="text">
					</td>
					<td class="leftTd">邮编：</td>
					<td>
						<input name="zipcode" type="text">
					</td>
				</tr>
				<tr>
					<td class="leftTd">E-mail：</td>
					<td>
						<input name="email" type="text">
					</td>
					<td class="leftTd">网站：</td>
					<td>
						<input name="website" type="text">
					</td>
				</tr>
				<tr>
					<td class="leftTd">描述：</td>
					<td colspan="3">
						<textarea name="description" rows="4" cols="25"></textarea>
					</td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="4" style="text-align: center;padding-top: 10px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="submitForm();">确定</a>
					</td>
				</tr>
			</table>
		</form>
		
	</div>
	
</body>
</html>
</html>