<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//更新添加修改窗口
	function openonemenuWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#onemenuTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择一级菜单信息', 'warning');
			} else {
				$('#onemenuWindow').window({
					title : title
				});
				$('#onemenuWindow').window('open');
				$('#onemenuWindow').window('refresh', url + '?id=' + rows.id);
				$('#onemenuTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#onemenuWindow').window({
				title : title
			});
			$('#onemenuWindow').window('open');
			$('#onemenuWindow').window('refresh', url);
			$('#onemenuTable').datagrid('clearSelections');
		}
	}

	function deleteonemenus() {
		var rows = $('#onemenuTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的一级菜单信息！', 'warning');
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
												url : "${ctx}/onemenu/onemenu-delete?id="
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
													$('#onemenuTable').datagrid("reload");
													$('#onemenuTable').datagrid('clearSelections');
												}
											});
								}
			$('#onemenuTable').datagrid('clearSelections');
							});
		}
	}

	function onemenuQuery() {
		$('#onemenuTable').datagrid("reload", {
			search_LIKE_title : $('#onemenu_title').val(),
			search_EQ_typeonemenu : $('#onemenu_typeonemenu').val()
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
		$('#onemenuWindow').window({
			title : title
		});
		$('#onemenuWindow').window('open');
		$('#onemenuWindow').window('refresh',
				"${ctx}/merchant/window-merchant-list");
	}
	function select(onemenuName, id) {
		$("#onemenu").val(onemenuName);
		$("#onemenuid").val(id);
		$('#onemenuWindow').window('close');
	}
	function isShow(val,row){
		if(val==1){
			return "<a style='color:red' >是</a>";
		}else{
			return "<a style='color:green' >否</a>";
		}
	}
	//
	function down(val, row) {
		return "<a style='color:red' href='${ctx}/"+val+"' >下载</a>";
	}
	function viewattachment(val, row) {
		return "<a href='${ctx}/upload/onemenu/"+val.address+"' style='cursor:pointer' ><font style='color:green' >"
				+ "【" + val.name + "】" + "</a>";
	}
	//删除、修改 一级菜单信息
	function operateonemenu(val, row) {
		var rowId = row.id;
		return "<security:authorize ifAnyGranted="ROLE_SOFT_EDIT"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'onemenuByonemenuId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'一级菜单修改\' title=\'一级菜单修改\' /></a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="ROLE_SOFT_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByonemenuId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'一级菜单删除\' title=\'一级菜单删除\' /></a></security:authorize>";
	}

	//删除一级菜单信息
	function deleteByonemenuId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的一级菜单信息！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的一级菜单信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/onemenu/onemenu-delete?id="
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
													$('#onemenuTable').datagrid("reload");
													$('#onemenuTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#onemenuTable').datagrid('clearSelections');
	}

	//一级菜单修改 
	function onemenuByonemenuId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择一级菜单信息', 'warning');
		} else {
			$('#onemenuWindow').window({
				title : '修改一级菜单'
			});
			$('#onemenuWindow').window('open');
			$('#onemenuWindow').window('refresh',
					'${ctx}/onemenu/onemenu-input?id=' + rowId);
			$('#onemenuTable').datagrid('clearSelections');
		}
	}
</script>
<table id="onemenuTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/onemenu/onemenu-list-data',toolbar:'#onemenuTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'name'" width="80" align="center">一级菜单标题</th>
			<th data-options="field:'imageName'" width="80" align="center">菜单图片名称前缀</th>
			<th data-options="field:'isShow'" width="80" align="center" formatter="isShow" >默认显示</th>
			<th data-options="field:'null'" width="100" align='center'
				formatter="operateonemenu">操作</th>
		</tr>
	</thead>
</table>
<div id="onemenuTool" style="padding: 5px; height: auto">
	<security:authorize ifAnyGranted="ROLE_ONEMENU_INPUT">
		<a href="javascript:void(0)"
			onclick="openonemenuWindow('添加更新','${ctx}/onemenu/onemenu-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_ONEMENU_DELETE">
		<a href="javascript:void(0)" onclick="deleteonemenus()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	</security:authorize>
</div>

<div id="onemenuWindow" class="easyui-window"
	style="width: 480px; height: 340px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
