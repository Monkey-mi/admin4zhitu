<!--/**
 * 用户发表织图列表查看组件
 * 注：此页面基本都是通过fancybox来打开
 * 
 * 参数：
 * userId：用户id
 * 
 * @author： zhangbo 2015-10-19
 */-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户织图列表</title>
<jsp:include page="../common/headerJQuery11.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<jsp:include page="../common/bootstrapHeader.jsp"></jsp:include>
<jsp:include page="../common/tourlistHeader.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${webRootPath }/htworld/css/htworldListV2.css"></link>
<script type="text/javascript" src="${webRootPath}/common/js/worldmaintain2014021801.js?ver=${webVer}"></script>
<link type="text/css" rel="stylesheet" href="${webRootPath}/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css"></link>
<script type="text/javascript" src="${webRootPath}/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript" src="${webRootPath}/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript">

	var maxId = 0;
	
	// 定义织图数据集合
	var dataList = [];
	
	// 获取url中请求参数：userId，用户id
	var userId = <%= request.getParameter("userId")%>;
	
	// 数据装载请求地址,若userId为空的时候，就显示全部用户，若userId不为空，则显示该用户
	var loadDataURL = "./admin_ztworld/ztworld_queryHTWorldList";

	init = function() {
		myQueryParams = {
				'maxId' : maxId,
				'authorName':userId
		};
		loadData(1, 30);
	};
	
	onBeforeInit = function() {
		checkUserId();
	};
	
	onAfterInit = function() {
		$("#pagination").pagination({
			pageList: [30,50,100,300],
			onBeforeRefresh : function(pageNumber, pageSize) {
				if(pageNumber <= 1) {
					maxId = 0;
					myQueryParams.maxId = maxId;
				}
			},
			onRefresh : function(pageNumber, pageSize) {
				loadData(pageNumber, pageSize);
			},
			onSelectPage : function(pageNumber, pageSize) {
				loadData(pageNumber, pageSize);
			},
			onChangePageSize : function(pageSize) {
				loadData(1, pageSize);
			}
		});
		
		$("#main").show();
	};
	
	/**
	 * 校验用户id是否为空，若为空则弹出提示，然后关闭页面
	 * @author zhangbo 2015-10-19
	 */
	function checkUserId() {
		if ( userId == undefined || userId == "" ) {
			$.messager.alert("温馨提示", "打开的链接有误，请联系系统管理员处理！", "info", function(){
				// 此页面基本都是通过fancybox来打开的，所以通过关闭fancybox方式关闭
				parent.$.fancybox.close();
			});
		}
	};
	
	
	/**
	 * 加载数据方法
	 * @author zhangbo 2015-10-19
	 */
	function loadData(pageNumber, pageSize) {
		scroll(0,0);
		$("#page-loading").show();
		myQueryParams['page'] = pageNumber;
		myQueryParams['rows'] = pageSize;
		
		$.post(loadDataURL, myQueryParams, function(result){
				if(result['result'] == 0) {
					if(result.maxId > maxId) {
						maxId = result.maxId;
						myQueryParams.maxId = maxId;
					}
					// 移除织图整体区域
					$(".world-opt-wrap").remove();
					
					refreshPagination(result['total'], pageNumber, pageSize);
					
					// 获取查询结果，所有织图对象
					var worlds = result['rows'];
					
					// 定义所有织图展现区域的最上层父对象div
					var $worldBox = $('#world-box');
					for(var i = 0; i < worlds.length; i++) {
						// 向织图数据集合中存值
						dataList.push(worlds[i]);
						
						// 定义每个织图展现块最外层div
						var $worldOpt = $('<div class="world-opt-wrap"></div>');
						drawWorldOpt($worldOpt, worlds[i], i);
						$worldBox.append($worldOpt);
					}
					$("#page-loading").hide();
				} else {
					$.messager.alert('失败提示',result['msg']);
				}
			});
	};
	
	/**
	 * 绘制每个织图展现块
	 * 
	 * $worldOpt：每个织图展现块最外层div，即每一个织图展现整体对象
	 * world：织图对象
	 * index：织图在织图数据集合中的脚标，即位置
	 * 
	 * @author zhangbo 2015-10-19
	 */
	function drawWorldOpt($worldOpt, world, index) {
		$worldOpt.attr("style", myRowStyler(index, world));
		
		// 绘制用户信息区域
		var $authorInfo = $("<div class='world-author'>"
				+ "<span>"
				+ "<img width='30px' height='30px' class='htm_column_img' src='" + baseTools.imgPathFilter(world.authorAvatar,'../base/images/no_avatar_ssmall.jpg') + "'/>"
				+ "</span>"
				+ "<span class='world-author-name'>" + world.authorName +"</span>"
				+ "<span>" + phoneCodeColumn.formatter(world.phoneCode,world,index) + "</span>"
				+ "<hr class='divider'></hr>"
				+ "<div>织图ID:" + world.id
				+ "<span class='world-count world-date'>" + baseTools.parseDate(world.dateModified).format("yy/MM/dd hh:mm") + "</span>"
				+ "</div>"
				+ "<div>用户ID:" + world.authorId
				+ "(" + world.level_description + ")"
				+ "</div>"
				+ "</div>");
		
		// 绘制织图信息区域
		var $worldInfo = $("<div class='world-info'>"
			+ "<div class='world-label'>#" + world.worldLabel + "</div>"
			+ "<div class='world-desc'>" + world.worldDesc + "</div>"
			+ "<div class='world-count-wrap'>"
			+ "<span class='glyphicon glyphicon-heart' aria-hidden='false'>"
			+ "<span class='world-count'>" + world.likeCount + "</span>"
			+ "</span>"
			+ "<span class='glyphicon glyphicon-comment' aria-hidden='false'>"
			+ "<span class='world-count'>" + world.commentCount + "</span>"
			+ "</span>"
			+ "<span class='glyphicon glyphicon-eye-open' aria-hidden='false'>"
			+ "<span class='world-count'>" + world.clickCount + "</span>"
			+ "</span>"
			+ "</div>"
			+ "<hr class='divider'></hr>"
			+ "</div>");
		
		var $world = $('<div class="world"/>');
		
		$worldOpt.addClass('world-margin');
		
		// 展示上从上至下，放入元素：用户信息区域，织图图片及子图片展示区域，织图信息区域
		$worldOpt.append($authorInfo);
		$worldOpt.append($world);
		$worldOpt.append($worldInfo);
		
		// 调用方法，继续绘制操作区域
		drawOptArea($worldOpt, world, index);
		
		// 织图及子织图相关配置
		$world.appendtour({
			'width': 250,
			'worldId': world.id,	// 织图id
			'ver': world.ver,	// 织图版本
			'worldDesc': world.worldDesc,	// 织图描述
			'titlePath': world.titlePath,	// 织图封面图片路径
			'url':'./admin_ztworld/ztworld_queryTitleChildWorldPage',	// 首页子世界请求路径
			'loadMoreURL':'./admin_ztworld/ztworld_queryChildWorldPage'	// 下一页子世界请求路径
		});
	};
	
	/**
	 * 绘制操作区域
	 * 
	 * $worldOpt：每个织图展现块最外层div，即每一个织图展现整体对象
	 * world：织图对象
	 * index：织图在织图数据集合中的脚标，即位置
	 * 
	 * @author zhangbo 2015-10-20
	 */
	function drawOptArea($worldOpt, world, index) {
		// 声明操作区域对象 
		var $opt = $('<div class="world-opt-area"></div>');
		
		// 添加第一行操作title
		var $opt1LineTitle = $("<div class='world-opt-head-wrap'>"
				+ "<span class='world-opt-head'>操作</span>"
				+ "</div>");
		
		// 添加第一行操作title对应的按钮
		var $opt1LineBtn = $("<div class='world-opt-btn-wrap'>"
				+ "<span class='world-opt-btn'>"
				+ "<img class='htm_column_img pointer' onclick='deleteWorld("+ world.id + ")' src='./common/images/cancel.png'/>"
				+ "</span>"
				+ "</div>");
		
		$opt.append($opt1LineTitle);
		$opt.append($opt1LineBtn);
		
		$worldOpt.append($opt);
	};
	
	/**
	 * 删除织图
	 * @author zhangbo 2015-10-20
	 */
	function deleteWorld(worldId) {
		// 参数需要的为集合形式
//		var ids = [];
//		ids.push(worldId);
		var params = {ids: worldId};
		$.messager.confirm("温馨提示", "确定要删除织图？", function(r){
			if (r){
				$.post("./admin_ztworld/ztworld_deleteWorld", params, function(result){
						if(result['result'] == 0) {
							// 重新加载当前页面数据
							loadData(myQueryParams.page, myQueryParams.rows);
						} else {
							$.messager.alert('失败提示',result['msg']);  //提示失败信息
						}
				});
			}
		});
	}
	
	/**
	 * 刷新翻页相关数据
	 * total：总数
	 * pageNumber：页数
	 * pageSize：每页展示个数
	 * @author zhangbo 2015-10-19
	 */
	function refreshPagination(total, pageNumber, pageSize)	{
		$("#pagination").pagination('refresh', {
		    total:total,
		    pageNumber:pageNumber,
		    pageSize:pageSize
		    
		});
	};

</script>
</head>
<body>
	<div id="main" class="none">
		<img id="page-loading" alt="" src="${webRootPath}/common/images/girl-loading.gif"/>
		<nav id="top" class="navbar navbar-default navbar-fixed-top">
			<div id="pagination" style="display:inline-block; vertical-align:middle;">
		</nav>
		<div id="world-box">
	</div>
	
</body>
</html>