<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//常量数据添加修改窗口 
	function openconstanttypeWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#constanttypeTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择常量类型');
			} else {
				$('#constanttypeWindow').window({
					title : title
				});
				$('#constanttypeWindow').window('open');
				$('#constanttypeWindow').window('refresh',url + '?id=' + rows.id);
				$('#constanttypeTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#constanttypeWindow').window({
				title : title
			});
			$('#constanttypeWindow').window('open');
			$('#constanttypeWindow').window('refresh', url);
			$('#constanttypeTable').datagrid('clearSelections');
		}
	}

	
	function deleteconstanttypes() {
		var rows = $('#constanttypeTable').datagrid('getSelections');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的常量类型！', 'warning');
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
							'你确定要删除选中的常量类型吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/constanttype/constanttype-delete?id="
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
													$('#constanttypeTable').datagrid("reload");
													$('#constanttypeTable').datagrid('clearSelections');
												}
											});
								}
			$('#constanttypeTable').datagrid('clearSelections');
							});
		}
	}
	

	//查询
	function constanttypeQuery() {
		$('#constanttypeTable').datagrid("reload", {
			search_LIKE_typeName : $('#constanttype_typeName').val()
		});
	}
	
	//删除、修改 常量信息
	function operateconstantTypeManager(val, row) {
		var rowId = row.id;
		return "<a onClick='clickConstantTypeIdDetail("+row.id+")' style='cursor:pointer'>"
		+"<img src=\'${ctx}/themes/operate/icon_detail.png\' alt=\'查看常量类型详情\' title=\'查看常量类型详情\' /></a>"
		+"&nbsp;&nbsp;&nbsp;"+"<security:authorize ifAnyGranted="ROLE_CONSTANTTYPE_EDIT"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByconstanttypeId("
		+ rowId
		+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'常量类型修改\' title=\'常量类型修改\' /> </a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="ROLE_CONSTANTTYPE_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByconstanttypeId("
		+ rowId
		+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'常量类型删除\' title=\'常量类型删除\' /></a></security:authorize>";
	}
	
	
	 //查看常量类型详情
	function clickConstantTypeIdDetail(id){
		$('#constanttypeWindow').window({
			title : '查看常量类型详情'
		});
		$('#constanttypeWindow').window('open');
		$('#constanttypeWindow').window('refresh', '${ctx}/constanttype/constanttype-info?id='+id);
		$('#constanttypeTable').datagrid('clearSelections');
	}
	
	//删除常量信息
	function deleteByconstanttypeId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的常量类型！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的常量类型吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/constanttype/constanttype-delete?id="
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
													$('#constanttypeTable').datagrid("reload");
													$('#constanttypeTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#constanttypeTable').datagrid('clearSelections');
	}

	//常量类型修改 
	function updateByconstanttypeId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择常量类型', 'warning');
		} else {
			$('#constanttypeWindow').window({
				title : '修改常量类型'
			});
			$('#constanttypeWindow').window('open');
			$('#constanttypeWindow').window('refresh',
					'${ctx}/constanttype/constanttype-input?id=' + rowId);
			$('#constanttypeTable').datagrid('clearSelections');
		}
	}
	
	function viewConstantManage(val, row) {
		return "<a href='#' onclick='setfunbyConstant(" + row.id
				+ ")' style='cursor:pointer' ><font style='color:green' >"
				+ "【常量列表】" + "</a>";
	}
	//跳转到常量类型下的常量列表
	/* function setfunbyConstant(id) {
		$('#function-constant-panel').panel("refresh",
				"${ctx}/constantmanager/constantmanager-constant-list?constantTypeId=" + id);
	} */
	
	<%--跳转到常量类型下的常量列表--%>
	function setfunbyConstant(id){
			if (id == null) {
				$.messager.alert('温馨提示', '请选择项目', 'warning');
			} else {
				$('#constantManagerAlertWindow').window({
					title : '常量列表'
				});
				$('#constantManagerAlertWindow').window('open');
				$('#constantManagerAlertWindow').window('refresh',
						'${ctx}/constantmanager/constantmanager-constant-list?constantTypeId=' + id);
				$('#constanttypeTable').datagrid('clearSelections');
			}
	}
	
</script>

<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<table id="constanttypeTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/constanttype/constanttype-list-data',toolbar:'#constanttypeTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'typeName'" width="150" align="center">类型名称</th>
			<th data-options="field:'typeDescription'" width="150" align="center">类型描述</th>
			<th data-options="field:'constantManagers'" align="center" formatter="viewConstantManage" width="100">常量管理</th>
			<th data-options="field:'null'" width="100" align='center' formatter="operateconstantTypeManager">操作</th>
		</tr>
	</thead>
</table>
<div id="constanttypeTool" style="padding: 5px; height: auto">
	<security:authorize ifAnyGranted="ROLE_CONSTANTTYPE_INPUT">
		<a href="javascript:void(0)"
			onclick="openconstanttypeWindow('添加常量类型','${ctx}/constanttype/constanttype-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_CONSTANTTYPE_DELETE">
		<a href="javascript:void(0)" onclick="deleteconstanttypes()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	</security:authorize>
	常量类型名称：<input type="text" id="constanttype_typeName"
		value="${search_LIKE_typeName}" style="width: 100px"></input> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true" onclick="constanttypeQuery();">查询</a>
</div>

<!-- 新增和修改的弹出框 -->
<div id="constanttypeWindow" class="easyui-window"
	style="width: 440px; height: 240px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>

<div id="constantManagerAlertWindow" class="easyui-window"
			style="width: 1050px; height: 480px"
			data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
		</div>

</div>
	<!-- <div region="east" style="width: 700px;" split="true">
			<div tools="#tt" class="easyui-panel" title="常量列表" fit="true" border="false" id="function-constant-panel">
			</div>
	</div>
	<div id="tt" style="padding: 5px; height: auto"></div> -->
		
</div>
