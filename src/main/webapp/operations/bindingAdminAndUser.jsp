<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定账号与马甲</title>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/CRUDHeader.jsp"></jsp:include>
<script type="text/javascript">

	var maxId=0;

	loadDataURL = "./admin_user/admin_user_queryAdminAndUserRelationship";	//数据装载请求地址
	deleteURI = "./admin_user/admin_user_deleteAdminAndUserRelationship";	//删除数据请求URI
	
//	htmTableHeight = 400;
		
	myOnBeforeRefresh = function(pageNumber, pageSize) {
		if(pageNumber <= 1) {
			maxId = 0;
			myQueryParams.maxId = maxId;
		}
	};
	myOnLoadSuccess = function(data) {
		if(data.result == 0) {
			if(data.maxId > maxId) {
				maxId = data.maxId;
				myQueryParams.maxId = maxId;
			}
		}
	};
	
	// 定义工具栏
	toolbarComponent = [{
		id: 'delete_btn',
		text: '删除',
		iconCls: 'icon-cut',
		handler: function(){
			var rows = $('#htm_table').datagrid('getSelections');
			var ids = [];
			for(var i=0;i<rows.length;i++){		
				ids.push(rows[i].id);
			}
			$.ajax({
			    url: deleteURI,  
			    data: {'ids' : ids.toString()},    
			    type: 'post',
			    cache: false,
			    dataType: 'json',    
			    success: function(data) {
			    	$.messager.alert("提示",data.msg);
			    	$('#htm_table').datagrid('reload');
			     },    
			     error: function() {    
			    	 $.messager.alert("提示","请求失败了！刷新一下页面，重新提交");  
			     }    
			});
		}
	}];
	
	columnsFields = [
	     			{field:'checkbox',checkbox:'true',align:'center'},
	    			{field:'id',title:'ID',width:80},      
	    	        {field:'adminUserName',title:'管理员',width:150,sortable:true},
	    	        {field:'adminUserId',title:'管理员ID',width:150,sortable:true},
	    	        {field:'userName',title:'织图用户',width:150},
	    	        {field:'userId',title:'织图用户ID',width:150,editor:'text'},
	    	        {field:'createTime',title:'创建时间',width:150},
	    	        {field:'updateTime',title:'更新时间',width:150}
	    	    ];
	
	onAfterInit = function(){
		$('#htm_table').datagrid({
			fitColumns: true,
			autoRowHeight: true,
			singleSelect: false,
		    checkOnSelect: false,
			onAfterEdit: function(index,row,changes){
				/*
				 * 若changes.userId为undefined，则证明没有改动，什么都不进行操作
				 * 若校验不通过，则拒绝修改
				 */
				if (changes.userId && validateAfterEdit(changes.userId,index)) {
					var requestData = {'id':row.id,'userId':row.userId};
					$.post('./admin_user/admin_user_updateAdminAndUserRelationship', requestData, function(data){
						if (data.result == 0) {
							$.messager.alert("提示", data.msg);
							$('#htm_table').datagrid('reload');
						} else {
							$.messager.alert("提示","第" + (index+1) + "行，" + data.msg);
							$('#htm_table').datagrid('rejectChanges');
						}
					});
				} else {
					$('#htm_table').datagrid('rejectChanges');
				}
				row.editing = false;
				$('#htm_table').datagrid('refreshRow', index);
			}
		});
	}

/*
 * 提交表单
 */
function submitForm(){
    $('#admin_user_relationship').form('submit', {
		url: $(this).attr('action'),
		success: function(data){
			
			var result = $.parseJSON(data);
			$.messager.alert("提示",result.msg);
			
			// 提示后清空输入框中值
			$('#form_userId').attr("value","");
			
			$('#htm_table').datagrid('reload');
		},
		error: function(data){
			$.messager.alert("提示","请求失败了！刷新一下页面，重新提交");
		}
	});
}

/*
 * 校验输入的userId是否为数字，或者超长（限制为10），校验通过返回true，否则为false
 * number：校验的数字
 * index：行索引
 */
function validateAfterEdit(number,index){
	var reg = /^[0-9]*[1-9][0-9]*$/;	// 正整数
	if ( reg.test(number) ) {
		if ( number.length > 10 ) {
			$.messager.alert("提示","输入的id长度过长，不能超过10位");
			return false;
		} else {
			return true;
		}
	} else {
		$.messager.alert("提示","第"+(index+1)+"行的userId必须为数字，请检查");
		return false;
	}
}

</script>
</head>
<body>
	<div id="main_panel" class="easyui-panel"
		style="height:70px;padding:10px;background:#fafafa;">
		<form id="admin_user_relationship" action="./admin_user/admin_user_createAdminAndUserRelationship" method="post">
		    <table cellpadding="5">
                <tr>
                    <td>织图用户Id:</td>
                    <td><input id="form_userId" name="userId" class="easyui-textbox" type="text" data-options="required:true"></input></td>
                    <td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">绑定</a></td>
                </tr>
            </table>
		</form>
	</div>
	<div style="height:20px"></div>
	<table id="htm_table"></table>
</body>
</html>