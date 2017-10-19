<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//角色添加修改窗口
	function openroleWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#roleTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择角色', 'warning');
			} else {
				$('#roleWindow').window({
					title : title
				});
				$('#roleWindow').window('open');
				$('#roleWindow').window('refresh', url + '?id=' + rows.id);
				$('#roleTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#roleWindow').window({
				title : title
			});
			$('#roleWindow').window('open');
			$('#roleWindow').window('refresh', url);
			$('#roleTable').datagrid('clearSelections');
		}
	}

	function deleteroles() {
		var rows = $('#roleTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的角色！', 'warning');
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
							'你确定要删除选中的角色吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/role/role-delete?id="
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
													$('#roleTable').datagrid("reload");
													$('#roleTable').datagrid('clearSelections');
												}
											});
								}
								$('#roleTable').datagrid('clearSelections');
							});
		}
	}

	function roleQuery() {
		$('#roleTable').datagrid("reload", {
			search_LIKE_name : $('#role_name').val(),
			search_LIKE_descript : $('#role_typerole').val()
		});
	}

	function viewrolemanage(val, row) {
		return "<a href='#' onclick='setfunbyrole(" + row.id
				+ ")' style='cursor:pointer' ><font style='color:green' >"
				+ "【权限管理】" + "</a>";
	}
	function setfunbyrole(id) {
		$('#function-panel').panel("refresh",
				"${ctx}/role/tree-role-list?roleId=" + id);

	}

	//删除、修改 角色信息
	function operaterole(val, row) {
		var rowId = row.id;
		return "<security:authorize ifAnyGranted="ROLE_ROLE_EDIT"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByroleId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'角色修改\' title=\'角色修改\' /></a>  &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="ROLE_ROLE_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByroleId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'角色删除\' title=\'角色删除\' /></a></security:authorize>";
	}

	//删除角色信息
	function deleteByroleId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的角色！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的角色吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/role/role-delete?id="
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
													$('#roleTable').datagrid("reload");
													$('#roleTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
			$('#roleTable').datagrid('clearSelections');

	}

	//角色修改 
	function updateByroleId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择角色信息', 'warning');
		} else {
			$('#roleWindow').window({
				title : '修改角色'
			});
			$('#roleWindow').window('open');
			$('#roleWindow').window('refresh',
					'${ctx}/role/role-input?id=' + rowId);
			$('#roleTable').datagrid('clearSelections');
		}
	}
</script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<table id="roleTable" class="easyui-datagrid" border="0" fit="true"
			data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/role/role-list-data',toolbar:'#roleTool',pagination:true">
			<thead>
				<tr>
					<th data-options="field:'id',checkbox:true">id</th>
					<th data-options="field:'name'" width="80" align="center">角色名称</th>
					<th data-options="field:'descript'" width="80" align="center">角色描述</th>
					<security:authorize ifAnyGranted="ROLE_ROLE_RESOURCE">
						<th data-options="field:'null'" align="center"
							formatter="viewrolemanage" width="80">权限管理</th>
					</security:authorize>
						<th data-options="field:'createdTime'" width="100" align='center'
				formatter="operaterole">操作</th>
				</tr>
			</thead>
		</table>
		<div id="roleTool" style="padding: 5px; height: auto">
			<security:authorize ifAnyGranted="ROLE_ROLE_INPUT">
				<a href="javascript:void(0)"
					onclick="openroleWindow('添加角色','${ctx}/role/role-input','add')"
					class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			</security:authorize>
			<security:authorize ifAnyGranted="ROLE_ROLE_DELETE">
				<a href="javascript:void(0)" onclick="deleteroles()"
					class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
			</security:authorize>
			角色名称：<input type="text" id="role_name" value="${search_LIKE_name}"
				style="width: 80px"></input> 角色描述：<input type="text"
				id="role_typerole" value="${search_EQ_typerole}" style="width: 80px"></input>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-search" plain="true" onclick="roleQuery();">查询</a>
		</div>

		<div id="roleWindow" class="easyui-window"
			style="width: 420px; height: 220px"
			data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
		</div>
	</div>
	<security:authorize ifAnyGranted="ROLE_ROLE_RESOURCE">
		<div region="east" style="width: 300px;" split="true">
			<div tools="#tt" class="easyui-panel" title="权限设置"
				style="padding: 10px;" fit="true" border="false" id="function-panel">

			</div>
		</div>
		<div id="tt" style="padding: 5px; height: auto"></div>
	</security:authorize>
</div>

