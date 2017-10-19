<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//更新添加修改窗口
	function openupdateWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#updateTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择软件信息', 'warning');
			} else {
				$('#updateWindow').window({
					title : title
				});
				$('#updateWindow').window('open');
				$('#updateWindow').window('refresh', url + '?id=' + rows.id);
				$('#updateTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#updateWindow').window({
				title : title
			});
			$('#updateWindow').window('open');
			$('#updateWindow').window('refresh', url);
			$('#updateTable').datagrid('clearSelections');
		}
	}

	function deleteupdates() {
		var rows = $('#updateTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的软件信息！', 'warning');
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
							'你确定要删除选中的更新吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/update/update-delete?id="
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
													$('#updateTable').datagrid("reload");
													$('#updateTable').datagrid('clearSelections');
												}
											});
								}
			$('#updateTable').datagrid('clearSelections');
							});
		}
	}

	function updateQuery() {
		$('#updateTable').datagrid("reload", {
			search_LIKE_title : $('#update_title').val(),
			search_EQ_typeupdate : $('#update_typeupdate').val()
		});
	}

	//显示类型
	function viewtype(val, row) {
		if (val == 0) {
			return "android";
		} else if (val == 1) {
			return "ios";
		}
	}
	//显示是否激活的信息
	function viewlock(val, row) {
		if (val == 0)
			return "激活";
		else
			return "未激活";
	}
	function seluploadll(title) {
		$('#updateWindow').window({
			title : title
		});
		$('#updateWindow').window('open');
		$('#updateWindow').window('refresh',
				"${ctx}/merchant/window-merchant-list");
	}
	function select(updateName, id) {
		$("#update").val(updateName);
		$("#updateid").val(id);
		$('#updateWindow').window('close');
	}

	//
	function down(val, row) {
		return "<a style='color:red' href='${ctx}/"+val+"' >下载</a>";
	}
	function viewattachment(val, row) {
		return "<a href='${ctx}/upload/update/"+val.address+"' style='cursor:pointer' ><font style='color:green' >"
				+ "【" + val.name + "】" + "</a>";
	}
	//删除、修改 软件信息
	function operateUpdate(val, row) {
		var rowId = row.id;
		return "<security:authorize ifAnyGranted="ROLE_SOFT_EDIT"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByupdateId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'软件修改\' title=\'软件修改\' /></a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="ROLE_SOFT_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByupdateId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'软件删除\' title=\'软件删除\' /></a></security:authorize>";
	}

	//删除软件信息
	function deleteByupdateId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的软件信息！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的软件信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/update/update-delete?id="
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
													$('#updateTable').datagrid("reload");
													$('#updateTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#updateTable').datagrid('clearSelections');
	}

	//软件修改 
	function updateByupdateId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择软件信息', 'warning');
		} else {
			$('#updateWindow').window({
				title : '修改软件'
			});
			$('#updateWindow').window('open');
			$('#updateWindow').window('refresh',
					'${ctx}/update/update-input?id=' + rowId);
			$('#updateTable').datagrid('clearSelections');
		}
	}
</script>
<table id="updateTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/update/update-list-data',toolbar:'#updateTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'title'" width="80" align="center">软件标题</th>
			<th data-options="field:'version'" width="80" align="center">软件版本号</th>
			<th data-options="field:'type'" width="80" align="center"
				formatter="viewtype">软件类型</th>
			<th data-options="field:'attachment'" align="center"
				formatter="viewattachment" width="80">软件下载</th>
			<th data-options="field:'null'" width="100" align='center'
				formatter="operateUpdate">操作</th>
		</tr>
	</thead>
</table>
<div id="updateTool" style="padding: 5px; height: auto">
	<security:authorize ifAnyGranted="ROLE_SOFT_INPUT">
		<a href="javascript:void(0)"
			onclick="openupdateWindow('添加更新','${ctx}/update/update-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_SOFT_DELETE">
		<a href="javascript:void(0)" onclick="deleteupdates()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	</security:authorize>
	软件标题：<input type="text" id="update_title" value="${search_LIKE_title}"
		style="width: 80px"></input> 软件类型：<input type="text"
		id="update_typeupdate" value="${search_EQ_typeupdate}"
		style="width: 80px"></input> <a href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-search" plain="true"
		onclick="updateQuery();">查询</a>

</div>

<div id="updateWindow" class="easyui-window"
	style="width: 480px; height: 340px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
