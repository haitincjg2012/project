<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//敏感词添加修改窗口
	function opensensitivityWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#sensitivityTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择敏感词信息', 'warning');
			} else {
				$('#sensitivityWindow').window({
					title : title
				});
				$('#sensitivityWindow').window('open');
				$('#sensitivityWindow').window('refresh',url + '?id=' + rows.id);
				$('#sensitivityTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#sensitivityWindow').window({
				title : title
			});
			$('#sensitivityWindow').window('open');
			$('#sensitivityWindow').window('refresh', url);
			$('#sensitivityTable').datagrid('clearSelections');
		}
	}

	function deletesensitivity() {
		var rows = $('#sensitivityTable').datagrid('getSelections');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择敏感词信息', 'warning');
		} else {
			for ( var i = 0; i < rows.length; i++) {
				var index = $("#sensitivityTable").datagrid("getRowIndex",
						rows[i]);
				$("#sensitivityTable").datagrid("deleteRow", index);
			}
		}
	}

	function sensitivityQuery() {
		$('#sensitivityTable').datagrid("reload", {
			search_LIKE_name : $('#sensitivity_name').val()
		});
	}
	function deletesensitivitys() {
		var rows = $('#sensitivityTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的敏感词信息！', 'warning');
		} else {
			var ids = '';
			for ( var i = 0; i < rows.length; i++) {
				if (ids != '')
					ids += ',';
				ids += rows[i].id;
			}
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的敏感词信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/sensitivity/sensitivity-delete?id="
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
													$('#sensitivityTable').datagrid("reload");
													$('#sensitivityTable').datagrid('clearSelections');
												}
											});
								}
			$('#sensitivityTable').datagrid('clearSelections');
							});
			
		}
	}
	//删除、修改 sensitivity信息
	function operatesensitivity(val, row) {
		var rowId = row.id;
		return "<security:authorize ifAnyGranted="ROLE_SENSITIVITY_EDIT"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateBysensitivityId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'敏感词修改\' title=\'敏感词修改\' /> </a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="ROLE_SENSITIVITY_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteBysensitivityId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'敏感词删除\' title=\'敏感词删除\' /></a></security:authorize>";
	}

	//删除用户信息
	function deleteBysensitivityId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的敏感词信息！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的敏感词吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/sensitivity/sensitivity-delete?id="
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
													$('#sensitivityTable').datagrid("reload");
													$('#sensitivityTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#sensitivityTable').datagrid('clearSelections');
	}

	//敏感词修改 
	function updateBysensitivityId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择用户信息', 'warning');
		} else {
			$('#sensitivityWindow').window({
				title : '修改用户'
			});
			$('#sensitivityWindow').window('open');
			$('#sensitivityWindow').window('refresh',
					'${ctx}/sensitivity/sensitivity-input?id=' + rowId);
			$('#sensitivityTable').datagrid('clearSelections');
		}
	}
</script>
<table id="sensitivityTable" class="easyui-datagrid" border="0"
	fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/sensitivity/sensitivity-list-data',toolbar:'#sensitivityTool',pagination:true">
	<thead>
		<tr>

			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'name'" width="80" align="left">敏感词</th>
			<th data-options="field:'null'" width="100" align='center'
				formatter="operatesensitivity">操作</th>
		</tr>
	</thead>
</table>
<div id="sensitivityTool" style="padding: 5px; height: auto">
	<security:authorize ifAnyGranted="ROLE_SENSITIVITY_INPUT">
		<a href="javascript:void(0)"
			onclick="opensensitivityWindow('添加敏感词','${ctx}/sensitivity/sensitivity-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_SENSITIVITY_DELETE">
		<a href="javascript:void(0)" onclick="deletesensitivitys()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	</security:authorize>
	敏感词：<input type="text" id="sensitivity_name"
		value="${search_LIKE_name}" style="width: 80px"></input> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		sensitivityCls="icon-search" plain="true" onclick="sensitivityQuery();">查询</a>
</div>

<div id="sensitivityWindow" class="easyui-window"
	style="width: 360px; height: 200px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
