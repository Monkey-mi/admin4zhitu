<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head></head>
 <body>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
  <title>频道红人管理</title> 
  <jsp:include page="../common/header.jsp"></jsp:include> 
  <jsp:include page="../common/CRUDHeader.jsp"></jsp:include> 
  <link type="text/css" rel="stylesheet" href="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.css" /> 
  <script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.fancybox-1.3.4.pack.js"></script> 
  <script type="text/javascript" src="${webRootPath }/base/js/jquery/fancybox/jquery.mousewheel-3.0.4.pack.js"></script> 
  <script type="text/javascript" src="${webRootPath }/common/js/user2014022101.js?ver=${webVer} "></script> 
  <script type="text/javascript">
	var maxId = 0;
	var channelId = baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID") ? baseTools.getCookie("CHANNEL_WORLD_CHANNEL_ID") : "";
	batchEnableTip = "您确定要使已选中的用户生效吗？", batchDisableTip = "您确定要使已选中的用户失效吗？",

	myQueryParams['channelId'] = channelId;
	
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if (pageNumber <= 1) {
			maxId = 0;
			myQueryParams['maxId'] = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if (data.result == 0) {
			if (data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams['maxId'] = maxId;
			}
		}
	};

	toolbarComponent = '#tb';

	// 数据装载请求地址
	loadDataURL = "./admin_op/channelmember_queryChannelMember";
	addRecommendMsgURL = "./admin_op/channelmember_addChannelStarsRecommendMsg";
	// 在封装的方法中缺少指定的字段造成Ui渲染不出来
 	myIdField = "channelMemberId";
	recordIdKey = "channelMemberId";
	hideIdColumn = true; 

	columnsFields = [
			{
				field : 'ck',
				checkbox : true
			},
			{
				field : 'channelMemberId',
				title : '频道成员表id',
				align : 'center'
			},
			{
				field : 'channelStar',
				title : '是否红人',
				align : 'center',
				formatter : function(value, row, index) {
					switch(value) {
		  				case true: return "红人";
		  				default: return "";
	  				}
				}
			},
			{
				field : 'userId',
				title : '用户ID',
				align : 'center'
			},
			phoneCodeColumn,
			userAvatarColumn,
			userNameColumn,
			sexColumn,
			userLabelColumn,
			concernCountColumn,
			followCountColumn,
			{
				field : 'worldCount',
				title : '织图',
				align : 'center',
				formatter : function(value, row, index) {
					var uri = "page_user_userWorldInfo?userId=" + row.userId;
					return "<a title='显示织图' class='updateInfo' href='javascript:showUserWorld(\"" + uri + "\")'>" + value + "</a>";
				}
			},
			{
				field : 'degree',
				title : '用户等级',
				align : 'center',
				formatter : function(value, row, index) {
					switch(value){
						case 0 : return "普通用户";
						case 1 : return "频道主";
						case 2 : return "管理员";
						default: return "普通用户";
					}
				}
			},
			{
				field : 'notified',
				title : '通知状态',
				align : 'center',
				formatter : function(value, row, index) {
	  				switch(value) {
	  				case 1:
	  					img = "./common/images/ok.png";
	  					tip = "已通知";
	  					break;
	  				default:
	  					img = "./common/images/tip.png";
	  					tip = "未通知";
	  					break;
	  				}
	  				return "<img title='" + tip + "' class='htm_column_img' src='" + img + "'/>";
				}
			},
			{
				field : 'shield',
				title : '是否屏蔽',
				align : 'center',
				formatter : function(value, row, index) {
					switch(value) {
	  				case 1:
	  					img = "./common/images/ok.png";
	  					tip = "已屏蔽";
	  					break;
	  				default:
	  					img = "./common/images/tip.png";
	  					tip = "未屏蔽 ";
	  					break;
	  				}
					return "<img title='" + tip + "' class='htm_column_img' src='" + img + "'/>";
				}
			},
			{
				field : 'weight',
				title : '置顶为最新红人',
				align : 'center',
				formatter : function(value, row, index) {
					img = "./common/images/edit_add.png";
					title = "点击设置为最新红人";
					return "<img title='" + title + "' class='htm_column_img pointer' onclick='setChannelStarTop("+ row.channelMemberId + ")' src='" + img + "'/>";
				}
			}];

	/**
	 * 显示用户织图
	 */
	function showUserWorld(uri) {
		$.fancybox({
			'margin' : 10,
			'width' : '100%',
			'height' : '100%',
			'autoScale' : true,
			'transitionIn' : 'none',
			'transitionOut' : 'none',
			'type' : 'iframe',
			'href' : uri
		});
	};

	onAfterInit = function() {
		$('#ss-verifyId').combogrid({
			panelWidth : 460,
			panelHeight : 310,
			loadMsg : '加载中，请稍后...',
			multiple : false,
			required : false,
			idField : 'id',
			textField : 'verifyName',
			url : './admin_user/verify_queryAllVerify?addAllTag=true',
			pagination : false,
			remoteSort : false,
			sortName : 'serial',
			sortOrder : 'desc',
			columns : [[
					{
						field : 'id',
						title : 'ID',
						align : 'center',
						width : 60
					},
					{
						field : 'verifyIcon',
						title : '图标',
						align : 'center',
						width : 60,
						formatter : function(value, row, index) {
							return "<img title='" + row['verifyName'] +  "' class='htm_column_img' src='" + value + "'/>";
						}
					}, {
						field : 'verifyName',
						title : '名称',
						align : 'center',
						width : 80
					}, {
						field : 'verifyDesc',
						title : '描述',
						align : 'center',
						width : 180
					}, {
						field : 'serial',
						title : '序号',
						align : 'center',
						width : 60,
						sorter : function(a, b) {
							return (a > b ? 1 : -1);
						}
					}

			]]
		});

		$('#htm_indexed').window({
			title : '重新排序',
			modal : true,
			width : $(document).width() * 0.7,
			height : $(document).height() * 0.5,
			shadow : false,
			closed : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			iconCls : 'icon-converter',
			resizable : false
		});
		
		/*
		 * 根据业务需要，不展示秒，只要精确到分钟就可以
		 */
		$("#schedulaDateCalendar").datetimebox({
			showSeconds: false,
			onSelect: timeCompare
		});
	};
	
	/**
	 * 时间比较方法，若选择的日期小于当日则进行替换
	 */
	function timeCompare(date) {
		var currentHour = $("#schedulaDateCalendar").datetimebox("spinner").timespinner("getHours");
		var currentMinute = $("#schedulaDateCalendar").datetimebox("spinner").timespinner("getMinutes");
		var currentTime = currentHour + ":" + currentMinute;
		
		var todayDate = baseTools.simpleFormatDate(new Date());
		var selectDate = baseTools.simpleFormatDate(date);
		
		// 若选择日期小于当天，则设置成当天时间
		if(selectDate < todayDate) {
			$("#schedulaDateCalendar").datetimebox('setValue', todayDate + " " + currentTime);
		} else {
			$("#schedulaDateCalendar").datetimebox('setValue', selectDate + " " + currentTime);
		}
	}

	/**
	 * mishengliang 批量增加删除数据
	 */
	function addOrDelete(valid) {
		var rows = $('#htm_table').datagrid('getSelections');
		if (rows.length > 0) {
			var tip = batchEnableTip;
			if (valid == 0)
				tip = batchDisableTip;
			$.messager.confirm('更新记录',tip,
				function(r) {
					if (r) {
						var ids = [];
						for (var i = 0; i < rows.length; i++) {
							ids.push(rows[i]['channelMemberId']);
						}
						// mishengliang add in 08-17-2015
						// 根据valid判断是添加还是删除
						var opUrl = "";
						if (valid == 1) {
							opUrl = "./admin_op/channelmember_addMembersToStar";
						} else if (valid == 0) {
							opUrl = "./admin_op/channelmember_deleteChannelStars";
						}
						$.post(opUrl, {"ids" : ids.toString()}, 
							function(result) {
								$('#htm_table').datagrid('loaded');
								if (result['result'] == 0) {
									// 成功后显示的提示信息
									$.messager.alert('提示', result['msg'] + ids.length + "条记录！");
								} else {
									$.messager.alert('提示', result['msg']);
								}
								$("#htm_table").datagrid("clearSelections");
								$("#htm_table").datagrid("reload");
							});
					}
				});
		} else {
			$.messager.alert('更新失败', '请先选择记录，再执行更新操作!', 'error');
		}
	}

	/**
	 * 批量通知操作方法
	 */
	function addChannelStarsRecommendMsg() {
		var rows = $('#htm_table').datagrid('getSelections');
		if (rows.length > 0) {
			$.messager.confirm('添加通知', '你确定要批量添加通知', function(r) {
				if (r) {
					var ids = [];
					for (var i = 0; i < rows.length; i += 1) {
						ids.push(rows[i]['channelMemberId']);
					}
					$('#htm_table').datagrid('clearSelections'); // 清除所有已选择的记录，避免重复提交id值
					$('#htm_table').datagrid('loading');
					$.post(addRecommendMsgURL, {ids : ids.toString()}, 
						function(result) {
							$('#htm_table').datagrid('loaded');
							if (result['result'] == 0) {
								$.messager.alert('提示', result['msg'] + ids.length + "条记录！");
								$("#htm_table").datagrid("reload");
							} else {
								$.messager.alert('提示', result['msg']);
							}
						});
				}
			});
		} else {
			$.messager.alert('通知失败', '请先选择记录，再执行更新操作!', 'error');
		}
	}

	/**
	 * 重新排序
	 */
	function reIndexed() {
		$('#htm_indexed .opt_btn').show();
		$('#htm_indexed .loading').hide();
		clearReIndexedForm();
		var rows = $('#htm_table').datagrid('getSelections');
		var resultRows = [];
		
		// 过滤掉非红人的数据，因为重新排序只针对于红人进行操作
		for (var i=0; i < rows.length; i++) {
			if ( rows[i].channelStar ) {
				resultRows.push(rows[i]);
			}
		}
		
		// 展示用户id到form的序号展示框中
		$('#indexed_form .reindex_column').each(function(j) {
			if (j < resultRows.length)
				$(this).val(resultRows[j]['userId']);
		});
		
		// 为form表单中channelId设值
		$('#indexed_form #channelId_indexed').val(channelId);
		
		// 打开添加窗口
		$("#htm_indexed").window('open');
	}

	/**
	 * 清空重新排序表格中所有数据
	 */
	function clearReIndexedForm() {
		$("#indexed_form").find('input[name="reIndexId"]').val('');
		$("#schedulaDateCalendar").datetimebox('clear');
	}

	function submitReIndexForm() {
		
		var $form = $('#indexed_form');
		if ($form.form('validate')) {
			$('#htm_indexed .opt_btn').hide();
			$('#htm_indexed .loading').show();
			$('#indexed_form').form('submit', {
				url : $form.attr('action'),
				success : function(data) {
					var result = $.parseJSON(data);
					$('#htm_indexed .opt_btn').show();
					$('#htm_indexed .loading').hide();
					if (result['result'] == 0) {
						$('#htm_indexed').window('close'); // 关闭添加窗口
						$('#htm_table').datagrid('clearSelections'); // 清除所有已选择的记录
						maxId = 0;
						myQueryParams['maxId'] = maxId;
						loadPageData(1);
					} else {
						$.messager.alert('错误提示', result['msg']); // 提示添加信息失败
					}
				}
			});
		}
	}

	/*
	 * 根据查询条件查找数据
	 */
	function searchMemberV2() {
		maxId = 0;
		myQueryParams['maxId'] = maxId;
		myQueryParams['userStarId'] = $('#ss-verifyId').combobox('getValue');
		myQueryParams['notified'] = $('#ss-notified').combobox('getValue');
		myQueryParams['shield'] = $('#ss-shield').combobox('getValue');
		$("#htm_table").datagrid("load", myQueryParams);
	}
	
	/**
	 * 根据频道id或者名称（支持模糊）查询
	 */
	function searchMemberByNameOrId(){
		var memberNameOrId = $("#searchMemberByNameOrId").searchbox('getValue');
		var params = {};
		if(isNaN(memberNameOrId)){
			params['userName'] = memberNameOrId;
		}else{
			params['userId'] = memberNameOrId;
		}
		params['channelId'] = channelId;
		$('#htm_table').datagrid('load',params);
		
	};
	
	/**
	 * 设置频道红人在排序的最新一位
	 */
	function setChannelStarTop(channelMemberId) {
		$.post("./admin_op/channelmember_setChannelStarTop", {id : channelMemberId}, 
			function(result) {
				if (result['result'] == 0) {
					$.messager.alert('提示', result['msg']);
					$("#htm_table").datagrid("reload");
				} else {
					$.messager.alert('提示', result['msg']);
				}
			});
	};
	
</script>

  <table id="htm_table"></table>
  
  <div id="tb" style="padding: 5px; height: auto" class="none"> 
  		<a href="javascript:void(0);" onclick="javascript:addOrDelete(1);" class="easyui-linkbutton" title="批量添加" plain="true" iconcls="icon-ok">批量添加红人</a> 
	  	<a href="javascript:void(0);" onclick="javascript:addOrDelete(0);" class="easyui-linkbutton" title="批量刪除" plain="true" iconcls="icon-ok">批量删除红人</a> 
	  	<a href="javascript:void(0);" onclick="javascript:addChannelStarsRecommendMsg();" class="easyui-linkbutton" title="批量通知" plain="true" iconcls="icon-ok">批量通知</a> 
	  	<a href="javascript:void(0);" onclick="javascript:reIndexed();" class="easyui-linkbutton" title="推荐用户排序" plain="true" iconcls="icon-converter" id="reIndexedBtn">重新排序</a> 
	  	<input id="ss-verifyId"  style="width: 100px" /> 
	  	<select id="ss-notified" class="easyui-combobox"  style="width: 100px;"> 
	   		<option value="">所有通知状态(只针对红人)</option>
	   		<option value="0">未通知</option>
	   		<option value="1">已通知</option>
	   	</select> 
	   	<select id="ss-shield" class="easyui-combobox" style="width: 100px;">
	   		<option value="">是否屏蔽</option>
	   		<option value="0">是</option>
	   		<option value="1">否</option>
	   	</select> 
	   	<a href="javascript:void(0);" onclick="javascript:searchMemberV2();" class="easyui-linkbutton" plain="true" iconcls="icon-search" id="searchBtn">查询</a> 
	   	<span style="display: inline-block; vertical-align: middle; float: right;"> <input id="searchMemberByNameOrId" class="easyui-searchbox" searcher="searchMemberByNameOrId" prompt="输入用户ID或名称搜索" style="width: 120px;" /> </span> 
  </div>
  
  <!-- 重排索引 --> 
  <div id="htm_indexed"> 
  	<form id="indexed_form" action="./admin_op/channelmember_sortChannelStarsSchedule" method="post"> 
    <table class="htm_edit_table" width="650"> 
     <tbody> 
      <tr> 
       <td class="leftTd">序号：</td> 
       <td>
       		<input name="reIndexId" class="easyui-validatebox reindex_column" required="true" />
       		<input name="reIndexId" class="reindex_column" />
       		<input name="reIndexId" class="reindex_column" />
       		<input name="reIndexId" class="reindex_column" />
       		<input name="reIndexId" class="reindex_column" />
       		<input name="reIndexId" class="reindex_column" />
       		<input name="reIndexId" class="reindex_column" /> 
       		<input name="reIndexId" class="reindex_column" /> 
       		<input name="reIndexId" class="reindex_column" /> 
       		<input name="reIndexId" class="reindex_column" />
       		<br />
       		<input name="reIndexId" class="reindex_column" />
       		<input name="reIndexId" class="reindex_column" />
       		<input name="reIndexId" class="reindex_column" />
       		<input name="reIndexId" class="reindex_column" />
       		<input name="reIndexId" class="reindex_column" /> 
       		<input name="reIndexId" class="reindex_column" /> 
       		<input name="reIndexId" class="reindex_column" /> 
       		<input name="reIndexId" class="reindex_column" /> 
       		<input name="reIndexId" class="reindex_column" /> 
       		<input name="reIndexId" class="reindex_column" />
       </td> 
      </tr>
      
      <tr>
      	<td class="leftTd">计划更新时间：</td>
      	<td><input id="schedulaDateCalendar" name="schedulaDate" class="easyui-datetimebox"></td>
      </tr>
      
      <tr class="none">
      	<td colspan="2"><input type="text" name="channelId" id="channelId_indexed" /></td> 
      </tr>
      
      <tr> 
       <td class="opt_btn" colspan="2" style="text-align: center; padding-top: 10px;">
       	<a class="easyui-linkbutton" iconcls="icon-ok" onclick="submitReIndexForm();">确定</a> 
       	<a class="easyui-linkbutton" iconcls="icon-redo" onclick="clearReIndexedForm();">清空</a> 
       	<a class="easyui-linkbutton" iconcls="icon-cancel" onclick="$('#htm_indexed').window('close');">取消</a>
       </td> 
      </tr>
      
      <tr class="loading none"> 
       <td colspan="2" style="text-align: center; padding-top: 10px; vertical-align: middle;"> 
       	<img src="./common/images/loading.gif" style="vertical-align: middle;" /> 
       	<span style="vertical-align: middle;">排序中...</span> 
       </td> 
      </tr>
     </tbody> 
    </table> 
   </form>
  </div>
  
 </body>
</html>