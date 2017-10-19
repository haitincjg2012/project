<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//资源添加修改窗口
	function openresourceWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#resourceTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择资源', 'warning');
			} else {
				$('#resourceWindow').window({
					title : title
				});
				$('#resourceWindow').window('open');
				$('#resourceWindow').window('refresh', url + '?id=' + rows.id);
				$('#resourceTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#resourceWindow').window({
				title : title
			});
			$('#resourceWindow').window('open');
			$('#resourceWindow').window('refresh', url);
			$('#resourceTable').datagrid('clearSelections');
		}
	}

	function deleteresources() {
		var rows = $('#resourceTable').datagrid('getSelections');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的资源！', 'warning');
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
							'你确定要删除选中的资源吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/resource/resource-delete?id="
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
													$('#resourceTable').treegrid("reload");
													$('#resourceTable').datagrid('clearSelections');
												}
											});
								}
								$('#resourceTable').datagrid('clearSelections');
							});
		}
	}
	function viewIcon(val, row) {
		return "<img border=\"0\" src=\"${ctx}/javascript/commons/accordion/images/"+val.urlicon+"\">";
	}

	function viewIsMenu(val, row) {
		if (val == 0) {
			return "是菜单";
		} else if (val == 1) {
			return "不是菜单";
		}
	}

	//删除、修改 资源信息
	function operateresource(val, row) {
		var rowId = row.id;
		return "<security:authorize ifAnyGranted="ROLE_RESOURCE_EDIT"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByresourceId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'资源修改\' title=\'资源修改\' /> </a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="ROLE_RESOURCE_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByresourceId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'资源删除\' title=\'资源删除\' /></a></security:authorize>";
	}

	//删除资源信息
	function deleteByresourceId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的资源！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的资源吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/resource/resource-delete?id="
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
													$('#resourceTable').datagrid("reload");
													$('#resourceTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
			$('#resourceTable').datagrid('clearSelections');
	}

	//资源修改 
	function updateByresourceId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择资源信息', 'warning');
		} else {
			$('#resourceWindow').window({
				title : '修改资源'
			});
			$('#resourceWindow').window('open');
			$('#resourceWindow').window('refresh',
					'${ctx}/resource/resource-input?id=' + rowId);
			$('#resourceTable').datagrid('clearSelections');
		}
	}
</script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<table id="resourceTable" class="easyui-treegrid" border="0"
			fit="true"
			data-options="treeField:'resourceRealName',iconCls: 'icon-ok',fitColumns:true,idField:'id',method:'get',rownumbers:true,singleSelect:true,url:'${ctx}/resource/resource-list-data',toolbar:'#resourceTool'">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true" align="center">id</th>
					<th data-options="field:'resourceRealName'" width="80">资源描述</th>
					<th data-options="field:'resourceName'" align="center" width="80">资源名称</th>
					<th data-options="field:'isMenu'" formatter="viewIsMenu"
						align="center" width="30">是否菜单</th>
					<th data-options="field:'icon'" formatter="viewIcon" align="center"
						width="30">资源图标</th>
					<th data-options="field:'resourceUrl'" width="120" align="center">资源地址</th>
					<th data-options="field:'resourceOrder'" width="30" align="center">资源排序</th>
					<th data-options="field:'null'" width="100" align='center'
						formatter="operateresource">操作</th>
				</tr>
			</thead>
		</table>
		<div id="resourceTool" style="padding: 5px; height: auto">
			<security:authorize ifAnyGranted="ROLE_RESOURCE_INPUT">
				<a href="javascript:void(0)"
					onclick="openresourceWindow('添加资源','${ctx}/resource/resource-input','add')"
					class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			</security:authorize>
			<%-- <security:authorize ifAnyGranted="ROLE_RESOURCE_DELETE">
				<a href="javascript:void(0)" onclick="deleteresources()"
					class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
			</security:authorize> --%>
		</div>

		<div id="resourceWindow" class="easyui-window"
			style="width: 480px; height: 340px"
			data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
		</div>
	</div>
</div>

