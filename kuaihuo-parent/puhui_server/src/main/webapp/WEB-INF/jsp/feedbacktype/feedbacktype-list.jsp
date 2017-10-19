<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">

function feedBackTypeQuery() {
	$('#feedBackTypeTable').datagrid("reload", {
		search_LIKE_typeName : $('#typeName').val()
	});
}
function viewFeedBackTypeUrl(val,row) {
	if(val==""){
		return "<span style='color:red;'>无</span>";
	}else{
	return "<a href=\"${ctx }/"+val+"\" target=\"_blank\"><img width='70px' height='60px' border='0' src=\"${ctx}/"+val+"\"></a>";
	}

}
function openFeedBackTypeWindow(title, url, type) {
	if (type == 'edit') {
		var rows = $('#feedBackTypeTable').datagrid('getSelected');
		if (rows == null) {
			$.messager.alert('提示', '请选择图标', 'warning');
		} else {
			$('#feedBackTypeWindow').window({
				title : title
			});
			$('#feedBackTypeWindow').window('open');
			$('#feedBackTypeWindow').window('refresh', url + '?id=' + rows.id);
			$('#feedBackTypeTable').datagrid('clearSelections');
		}
	} else if (type == 'add') {
		$('#feedBackTypeWindow').window({
			title : title
		});
		$('#feedBackTypeWindow').window('open');
		$('#feedBackTypeWindow').window('refresh', url);
		$('#feedBackTypeTable').datagrid('clearSelections');
	}
}
function deleteFeedBackTypes() {
	var rows = $('#feedBackTypeTable').datagrid('getChecked');
	if (rows == null || rows == '') {
		$.messager.alert('提示', '请选择要删除的意见反馈类型！', 'warning');
	} else {
		var ids = '';
		for ( var i = 0; i < rows.length; i++) {
			if (ids != '')
				ids += ',';
			ids += rows[i].id;
		}
		$.messager
				.confirm(
						'提示',
						'你确定要删除选中的意见反馈类型吗？',
						function(r) {
							if (r) {
								$
										.ajax({
											type : "POST",
											url : "${ctx}/feedbacktype/feedbacktype-delete?id="
													+ ids,
											success : function(data) {
												data = $.parseJSON(data);
												$.messager
														.show({
															title : '操作提示',
															msg : data.msg,
															showType : 'slide',
															style : {
																right : '',
																top : document.body.scrollTop
																		+ document.documentElement.scrollTop,
																bottom : ''
															}
														});
												$('#feedBackTypeTable').datagrid("reload");
												$('#feedBackTypeTable').datagrid('clearSelections');
											}
										});
							}
							$('#feedBackTypeTable').datagrid('clearSelections');
						});
	}
}
function operateByFeedBackType(val, row) {
	var rowId = row.id;
	return "<security:authorize ifAnyGranted='ROLE_FEEDBACKTYPE_EDIT'><a onclick=\'updateByFeedBackTypeId("
			+ rowId
			+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'意见反馈类型修改\' title=\'意见反馈类型修改\' /></a></security:authorize>  &nbsp &nbsp <security:authorize ifAnyGranted='ROLE_FEEDBACKTYPE_DELETE'><a onclick=\'deleteByFeedBackTypeId("
			+ rowId
			+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'意见反馈类型删除\' title=\'意见反馈类型删除\' /></security:authorize>";
}
function updateByFeedBackTypeId(rowId) {
		$('#feedBackTypeWindow').window({
			title : '修改意见反馈类型信息'
		});
		$('#feedBackTypeWindow').window('open');
		$('#feedBackTypeWindow').window('refresh',
				'${ctx}/feedbacktype/feedbacktype-input?id=' + rowId);
		$('#feedBackTypeTable').datagrid('clearSelections');
	}

function deleteByFeedBackTypeId(rowId) {
	if (rowId == null || rowId == '') {
		$.messager.alert('温馨提示', '请选择要删除的意见反馈类型信息！', 'warning');
	} else {
		$.messager
				.confirm(
						'温馨提示',
						'你确定要删除选中的意见反馈类型信息吗？',
						function(r) {
							if (r) {
								$
										.ajax({
											type : "POST",
											url : "${ctx}/feedbacktype/feedbacktype-delete?id="
													+ rowId,
											success : function(data) {
												data = $.parseJSON(data);
												$.messager
														.show({
															title : '操作提示',
															msg : data.msg,
															showType : 'slide',
															style : {
																right : '',
																top : document.body.scrollTop
																		+ document.documentElement.scrollTop,
																bottom : ''
															}
														});	
												$('#feedBackTypeTable').datagrid("reload");
												$('#feedBackTypeTable').datagrid('clearSelections');
											}
										});
							}
						});
	}
	$('#feedBackTypeTable').datagrid('clearSelections');
}
</script>
	
<table id="feedBackTypeTable" class="easyui-datagrid" border="0" fit="true"
     data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/feedbacktype/feedbacktype-list-data',toolbar:'#feedBackTypeTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'typeName'" width="150" align="center">意见反馈类型名称</th>
			<th data-options="field:'deleteOne'" width="150" align='center'
				formatter="operateByFeedBackType">操作</th>
		</tr>
	</thead>
</table>
<div id="feedBackTypeTool" style="padding: 5px; height: auto">
<security:authorize ifAnyGranted="ROLE_FEEDBACKTYPE_INPUT">
		<a href="javascript:void(0)"
			onclick="openFeedBackTypeWindow('意见反馈类型添加','${ctx}/feedbacktype/feedbacktype-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
</security:authorize>
<security:authorize ifAnyGranted="ROLE_FEEDBACKTYPE_DELETE">
		<a href="javascript:void(0)" onclick="deleteFeedBackTypes()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
</security:authorize>
	意见反馈类型名称：<input type="text" id="typeName" value="${search_LIKE_typeName}"
		style="width: 80px"></input> 
		 <a href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-search" plain="true"
		onclick="feedBackTypeQuery();">查询</a>
	
</div>

<div id="feedBackTypeWindow" class="easyui-window"
	style="width: 450px; height: 280px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>