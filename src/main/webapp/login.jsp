<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图信息管理系统v1.0</title>
<link rel="stylesheet" type="text/css" href="${webRootPath }/index/css/index20131112.css" />
<link rel="stylesheet" type="text/css" href="${webRootPath }/index/css/intor.css" />
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.tipInput.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#loginCode").tipInput("登陆账号");
	$("#password").tipInput("密码");
});
</script>
<style type="text/css">
body {
	background: url("common/images/bg.png");
}

#container {
	margin: auto;
	width: 409px;
	height: 208px;
	text-align: center;
	background: url("common/images/loginbg.png") no-repeat;
}

#title {
	padding-top: 10px;
}

#loginForm_wrap {
	width: 780px;
	height: 44px;
	line-height: 44px;
	margin-left: 90px;
	margin-right: auto;
}

#loginForm {
	margin-top: 40px;
}

#loginCode,#password {
	background: url('./index/images/login.png');
	height: 16px;
	vertical-align: middle;
	width: 300px;
	margin-right: auto;
	border: 0;
	padding-left: 10px;
	padding-right: 10px;
	outline: none;
	font-size: 16px;
	font-weight: 400;
	margin-left: 10px;
	padding: 14px 10px;
	font-size: 14px;
}

#loginForm input:ACTIVE {
	border: none;
}

#loginBtn {
	background: url("./index/images/login_btn_light.png");
	width: 78px;
	height: 38px;
	border: none;
	margin-top: 10px;
	color: #7797a3;
	font-weight: 600;
	cursor: pointer;
	margin-left: 10px;
	font-size: 12px;
}

#loginBtn:HOVER {
	background: url("./index/images/login_btn_hover.png");
}

#loginBtn:ACTIVE {
	background: url("./index/images/login_btn_dark.png");
}

#zhitu {
	background: url('./index/images/zhitu.png');
	width: 200px;
	height: 120px;
	margin-right: 20px;
}

#div_remember_me {
	margin-left: 10px;
	margin-top: 10px;
	margin-right: 30px;
	width: 100px;
	font-size: 14px;
	color: white;
	height: 16px;
	line-height: 16px;
	vertical-align: middle;
	text-align: right;
}

#remember_me {
	width: 16px;
	height: 16px;
	line-height: 16px;
	vertical-align: middle;
	margin-left: 10px;
}

#remember_me_label {
	height: 16px;
	line-height: 16px;
	vertical-align: middle;
	color:rgb(136, 136, 136);
}
</style>
</head>
<body>
	<div class="main_box">
		<div class="content_box">
			<img class="bg_content_box"
				src="./index/images/content_background20130901.jpg">
			<div class="content clearfix">
				<div class="title">
					<ul class="title_text">
					</ul>
				</div>
				<div class="clearfix">
					<div style="margin-left: 88px; margin-top: 50px;">
						<div id="zhitu"></div>
						<div class="introduce_text" style="margin-left: 12px;">织图信息管理系统v1.0</div>
					</div>
				</div>
				<div id="loginForm_wrap">
					<form id="loginForm" action="j_spring_security_check" method="post" >
						<input id="loginCode" type="text" name="j_username" /> 
						<input id="password" type="password" name="j_password" /> 
						<input id="loginBtn" value="登陆" type="submit" name="submit"/><br>
						<input id="remember_me" name="_spring_security_remember_me" type="checkbox" checked="checked" />
						<label id="remember_me_label" for="remember_me" class="inline">记住登录状态</label>
					</form>
				</div>
			</div>
		</div>
		<div class="foot_box"></div>
		<div class="copyright_box">
			<div class="copyright_info">
				<a href="zhitu_about">关于织图</a>&nbsp;|&nbsp;<a href="zhitu_agreement">用户协议</a>&nbsp;|&nbsp;<a href="zhitu_joinus">招聘信息</a>&nbsp;|&nbsp;<a href="zhitu_contact">联系我们</a>
			</div>
		   	<div class="copyright_div">
		       <span class="copyright">服务条款</span>
		       <span class="copyright">|&nbsp;粤ICP备 1208784</span> <span class="copyright">©2013 imzhitu.com.All Rights Reserved</span>
		   	</div>	
		</div>
	</div>
</body>
</html>