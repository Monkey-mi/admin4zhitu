<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String userId=request.getParameter("userId"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图用户管理</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}" />
<script type="text/javascript">
$(document).ready(function() {
	loadExchangeFormValidate();
});

//提交表单，以后补充装载验证信息
function loadExchangeFormValidate() {
	var $form = $('#exchange_form');
	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
	$.formValidator.initConfig({
		formid : $form.attr("id"),			
		onsuccess : function() {
			if(formSubmitOnce==true){
				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
				formSubmitOnce = false;
				$("#exchange_form .loading").show();
				$("#exchange_form .opt_btn").hide();
				//验证成功后以异步方式提交表单
				$.post($form.attr("action"),$form.serialize(),
					function(result){
						formSubmitOnce = true;
						$("#exchange_form .loading").hide();
						$("#exchange_form .opt_btn").show();
						if(result['result'] == 0) {
							$.messager.alert('提示',result['msg']);  //提示添加信息成功
							loadExchangeFormValidate();
							$("#id_exchange").val('');
							$("#toid_exchange").val('');
						} else {
							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
						}
					},"json");
				return false;
			}
		}
	});
	
	$("#id_exchange")
	.formValidator({onshow:"请输入用户id",onfocus:"例:123",oncorrect:"用户ID正确！"})
	.inputValidator({min:1,max:20,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"id错误"})
	.regexValidator({regexp:"num", datatype:"enum", onerror:"格式不正确"});
	
	$("#toid_exchange")
	.formValidator({onshow:"请输入对换id",onfocus:"例:345",oncorrect:"用户ID正确！"})
	.inputValidator({min:1,max:20,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"id错误"})
	.functionValidator({fun: function(val){
		var $id = $("#id_exchange");
		var id = $id.val();
		if(id == '') {
			$id.focus();
			return '请先输入id';
		} else if(id == val) {
			return 'id和toid不能相同';
		}
		return true;
	}});
	
}

</script>
</head>
<body>
	<form id="exchange_form" action="./admin_user/user_updateExchangeUsers" method="post">
		<table id="htm_edit_table" width="510">
			<tbody>
				<tr>
					<td class="leftTd">ID：</td>
					<td><input type="text" name="id" id="id_exchange" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="id_exchangeTip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="leftTd">TOID：</td>
					<td><input type="text" name="toId" id="toid_exchange" onchange="validateSubmitOnce=true;"/></td>
					<td class="rightTd"><div id="toid_exchangeTip" class="tipDIV"></div></td>
				</tr>
				<tr>
					<td class="opt_btn" colspan="3" style="text-align: center;padding-top: 10px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#exchange_form').submit();">确定</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_label').window('close');">取消</a>
					</td>
				</tr>
				<tr class="loading none">
					<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
						<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
						<span style="vertical-align:middle;">保存中...</span>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>