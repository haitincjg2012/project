<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//图标添加修改窗口
	function openiconWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#iconTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择图标信息', 'warning');
			} else {
				$('#iconWindow').window({
					title : title
				});
				$('#iconWindow').window('open');
				$('#iconWindow').window('refresh', url + '?id=' + rows.id);
				$('#iconTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#iconWindow').window({
				title : title
			});
			$('#iconWindow').window('open');
			$('#iconWindow').window('refresh', url);
			$('#iconTable').datagrid('clearSelections');
		}
	}

	function deleteicons() {
		var rows = $('#iconTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的图标信息！', 'warning');
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
							'你确定要删除选中的图标信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/icon/icon-delete?id="
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
													$('#iconTable').datagrid("reload");
													$('#iconTable').datagrid('clearSelections');
												}
											});
								}
			$('#iconTable').datagrid('clearSelections');
							});
		}
	}

	function iconQuery() {
		$('#iconTable').datagrid("reload", {
			search_LIKE_name : $('#icon_name').val(),
			search_EQ_typeicon : $('#icon_typeicon').val()
		});
	}

	//显示性别
	function viewurl(val, row) {
		return "<img border=\"0\" src=\"${ctx}/javascript/commons/accordion/images/"+val+"\">";
	}
	//删除、修改 icon信息
	function operateIcon(val, row) {
		var rowId = row.id;
		return "<security:authorize ifAnyGranted="ROLE_ICON_EDIT"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByIconId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'图标修改\' title=\'图标修改\' /> </a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="ROLE_ICON_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByIconId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'图标删除\' title=\'图标删除\' /></a></security:authorize>";
	}

	//删除图标信息
	function deleteByIconId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的图标信息！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的图标信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/icon/icon-delete?id="
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
													$('#iconTable').datagrid("reload");
													$('#iconTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#iconTable').datagrid('clearSelections');
	}

	//图标修改 
	function updateByIconId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择图标信息', 'warning');
		} else {
			$('#iconWindow').window({
				title : '修改图标'
			});
			$('#iconWindow').window('open');
			$('#iconWindow').window('refresh',
					'${ctx}/icon/icon-input?id=' + rowId);
			$('#iconTable').datagrid('clearSelections');
		}
	}
</script>
<table id="iconTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/icon/icon-list-data',toolbar:'#iconTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'name'" width="80" align="center">图标名称</th>
			<th data-options="field:'urlicon'" width="80" align="center"
				formatter="viewurl">图标</th>
			<th data-options="field:'typeicon'" width="80" align="center">类型</th>
			<th data-options="field:'null'" width="100" align='center'
				formatter="operateIcon">操作</th>
		</tr>
	</thead>
</table>
<div id="iconTool" style="padding: 5px; height: auto">
	<security:authorize ifAnyGranted="ROLE_ICON_INPUT">
		<a href="javascript:void(0)"
			onclick="openiconWindow('添加图标','${ctx}/icon/icon-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_ICON_DELETE">
		<a href="javascript:void(0)" onclick="deleteicons()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	</security:authorize>
	图标名称：<input type="text" id="icon_name" value="${search_LIKE_name}"
		style="width: 80px"></input> 类型：<input type="text" id="icon_typeicon"
		value="${search_EQ_typeicon}" style="width: 80px"></input> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true" onclick="iconQuery();">查询</a>
</div>

<div id="iconWindow" class="easyui-window"
	style="width: 480px; height: 290px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
