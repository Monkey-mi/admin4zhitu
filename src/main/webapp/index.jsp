<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>织图信息管理系统</title>
    <jsp:include page="./common/header.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="${webRootPath }/common/css/htmCRUD20131111.css?ver=${webVer}" />
    <link href="${webRootPath }/jquery-easyui-layout/css/default20131110.css?ver=${webVer}" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src='${webRootPath }/jquery-easyui-layout/js/outlook4.js?ver=${webVer}'> </script>
    <script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery.danmu.min.js"></script>
    <script type="text/javascript">
		var maxDmId = 0;
		   		
        $(function() {
        	$('body').fadeIn();
            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
                    if (r) {
                    	location.href = 'logout';
                    }
                });
            });
            
            $("#changePass").click(function() {
        		loadPassFormValidate();
            	$('#htm_pass').window('open');
            });
            
            $('#htm_pass').window({
    			modal : true,
    			width : 520,
    			height : 160,
    			title : '修改密码',
    			shadow : false,
    			closed : true,
    			minimizable : false,
    			maximizable : false,
    			collapsible : false,
    			iconCls : 'icon-edit',
    			resizable : false,
    			onClose : function() {
    				$("#password_pass, #confirm_pass").val('');
    				$('#htm_pass .opt_btn').show();
            		$('#htm_pass .loading').hide();
    			}
    		});
    		
    		var dmWidth = $(window).width()-280-200;
    		$("#danmu").danmu({
				left: 280,    //区域的起始位置x坐标
				top: 8 ,  //区域的起始位置y坐标
				height: 62, //区域的高度 
				width: dmWidth, //区域的宽度 
				zindex :9999, //div的css样式zindex
				speed:30000, //弹幕速度，飞过区域的毫秒数 
				sumtime:9999999999 , //弹幕运行总时间
				danmuss:{}, //danmuss对象，运行时的弹幕内容 
				default_font_color:"#FFFFFF", //弹幕默认字体颜色 
				font_size_small:14, //小号弹幕的字体大小,注意此属性值只能是整数
				font_size_big:18, //大号弹幕的字体大小 
				opacity:"0.9", //弹幕默认透明度 
				top_botton_danmu_time:10000 //顶端底端弹幕持续时间 
			});
			$('#danmu').danmu('danmu_start'); //如果你传递了合法的danmuss对象进去（不是必须的），刷新页面后就可以在div中看到弹幕运行的效果啦。
			fetchDanmu();
        });
        
        var formSubmitOnce = true;
      //提交表单，以后补充装载验证信息
        function loadPassFormValidate() {
        	var $form = $('#pass_form');
        	formSubmitOnce = true; //每次打开窗口formSubmitOnce都重新设为true
        	$.formValidator.initConfig({
        		formid : $form.attr("id"),			
        		onsuccess : function() {
        			if(formSubmitOnce==true){
        				//第一次提交表单前formSubmitOnnce设为false，避免重复提交表单
        				formSubmitOnce = false;
        				$('#htm_pass .opt_btn').hide();
    	        		$('#htm_pass .loading').show();
        				//验证成功后以异步方式提交表单
        				$.post($form.attr("action"),$form.serialize(),
        					function(result){
	        					$('#htm_pass .opt_btn').show();
	        	        		$('#htm_pass .loading').hide();
        						if(result['result'] == 0) {
        							$('#htm_pass').window('close');  //关闭添加窗口
        							$.messager.alert('提示',result['msg']);  //提示添加信息成功
        							location.href = 'logout';
        						} else {
        							$.messager.alert('错误提示',result['msg']);  //提示添加信息失败
        						}
        					},"json");				
        				return false;
        			}
        		}
        	});
        	
        	$("#password_pass")
        	.formValidator({empty:false,onshow:"请输入6-12位密码",onfocus:"例如:123456",oncorrect:"设置成功"})
        	.inputValidator({min:6,max:12,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"请输入6-12位密码"});
        	
        	$("#confirm_pass")
        	.formValidator({empty:false,onshow:"请确认密码",onfocus:"请确认密码",oncorrect:"设置成功"})
        	.inputValidator({min:6,max:12,empty:{leftempty:false,rightempty:false,emptyerror:"两边不能输入空格"},onerror:"请输入6-12位密码"})
        	.functionValidator({fun: function(val){
        		var $password = $("#password_pass");
        		var password = $password.val();
        		if(password == '') {
        			$password.focus();
        			return '请先输入新密码';
        		} else if(password != val) {
        			return '两次输入密码不一致';
        		}
        		return true;
        	}});
        }
        
        function fetchDanmu() {
        	$.post("./admin_user/msg_queryDanmu", {
        		'maxId':maxDmId,
        		'start':1,
        		'limit':10,
        	}, function(result){
        		if(result['result'] == 0) {
        			var msgs = result['msg'];
        			if(msgs.length > 0) {
        				var now = $('#danmu').data("nowtime");
        				if(msgs.length >= 10) {
	        				var tip = '{ "text":"最新一波用户反馈正在靠近","color":"red","size":"1","position":"1","time":' + now + '}';
	        				var tipObj = eval('(' + tip + ')');
							$('#danmu').danmu("add_danmu", tipObj);
						}
        				for(var i in msgs) {
        					var msg = msgs[i];
        					msg['time'] = '' + now +'';
							$('#danmu').danmu("add_danmu", msg);
							now = now + 10;
							if(i == 0) {
								maxDmId = msg['id'];
							}
        				} 
        			}
        		}
        	}, "json");
        }
    </script>
    <style type="text/css">
	    body {
			overflow-y: hidden;
			margin:0; 
			scroll=no;
	    }
    	#header-inner {
			text-align: left;
			width: 100%;
			padding: 10px 0;
			font-size:14px !important;
		}
		
		#topmenu {
			text-align: right;
		}
		
		#topmenu a {
			display: inline-block;
			padding: 1px 3px;
			text-decoration: none;
			color: #fff;
			font-size:12px;
		}
    	
    	#footer {
    		height: 30px;
    		background: #D2E0F2;
    	 	display: none;
    	}
    	#indexTab {
    		display: none;
    	}
    	#indexTab ul li{
    		margin-top:10px;
    		font-size: 14px;
    		line-height: 20px;
    	}
    	#loading {
    		position:absolute;
			width:300px;
			height:50px;
			top:50%;
			left:50%;
			margin:-25px 0 0 -150px;
			text-align:center;
			z-index:99999;
			font-size: 16px;
			color: #000;
    	}
    </style>

</head>
<body class="easyui-layout">
    <div region="north" border="false" style="background-color: rgb(102, 102, 102); color:white; text-align: center; width: 100%;  background-position: initial initial; background-repeat: initial initial;" title="" class="panel-body panel-body-noheader panel-body-noborder layout-body ">
       <div id="header-inner">
			<table cellpadding="0" cellspacing="0" style="width:100%;">
				<tbody>
					<tr>
						<td rowspan="2" style="width:20px;">
						</td>
						<td style="height:52px;">
							<div style="color:#fff;font-size:22px;font-weight:bold;">
								<a href="#" style="color:#fff;font-size:22px;font-weight:bold;text-decoration:none">织图信息管理系统</a>
							</div>
							<div style="color:#fff">
								<a href="#" style="color:#fff;text-decoration:none">You have a dream, you got to protect it.</a>
							</div>
						</td>
						<td style="padding-right:5px;text-align:right;vertical-align:bottom;">
							<div id="topmenu">
								<a href="javascript:void(0);"><sec:authentication property="principal.name" /></a>
								<a href="javascript:void(0);" id="changePass">修改密码</a>
								<a href="javascript:void(0);" id="loginOut">安全退出</a>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="danmu"></div>
    </div>
    <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
	<div id="nav" class="easyui-accordion" fit="true" border="false">
	</div>
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden;">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
		</div>
    </div>
    
	<div id="mm" class="easyui-menu" style="width:150px; display:none;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>

 	<div id="loading">
 		加载中...
 	</div>
 	
 	<!-- 更新密码窗口 -->
	<div id="htm_pass">
		<form id="pass_form" action="./admin/user_updatePassword" method="post">
			<table class="htm_edit_table"  width="480" class="none">
				<tbody>
					<tr>
						<td class="leftTd">新密码：</td>
						<td><input type="password" name="adminUser.password" id="password_pass" style="width:205px;" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="password_passTip" class="tipDIV"></div></td>
					</tr>
					<tr>
						<td class="leftTd">确认密码：</td>
						<td><input type="password" name="confirm" id="confirm_pass" style="width:205px;" onchange="validateSubmitOnce=true;"/></td>
						<td class="rightTd"><div id="confirm_passTip" class="tipDIV"></div></td>
					</tr>
					<tr class="opt_btn">
						<td colspan="3" style="text-align: center;padding-top: 10px;">
							<a class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#pass_form').submit();">确定</a> 
							<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#htm_pass').window('close');">取消</a>
						</td>
					</tr>
					<tr class="loading none">
						<td colspan="3" style="text-align: center; padding-top: 10px; vertical-align:middle;">
							<img alt="" src="./common/images/loading.gif" style="vertical-align:middle;">
							<span style="vertical-align:middle;">请稍后...</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

</body>
</html>