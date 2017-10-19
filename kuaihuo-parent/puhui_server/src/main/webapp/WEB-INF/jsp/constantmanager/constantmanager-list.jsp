<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//常量数据添加修改窗口 
	function openconstantmanagerWindow(title, url, type) {
		var constantTypeId='${constantTypeId}';
		if (type == 'edit') {
			var rows = $('#constantmanagerTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择常量信息');
			} else {
				$('#constantmanagerWindow').window({
					title : title
				});
				$('#constantmanagerWindow').window('open');
				$('#constantmanagerWindow').window('refresh',url + '?id=' + rows.id+"&constantTypeId="+constantTypeId);
				$('#constantmanagerTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#constantmanagerWindow').window({
				title : title
			});
			$('#constantmanagerWindow').window('open');
			$('#constantmanagerWindow').window('refresh', url + "?constantTypeId="+constantTypeId);
			$('#constantmanagerTable').datagrid('clearSelections');
		}
	}

	function deleteconstantmanagers() {
		var rows = $('#constantmanagerTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的常量信息！');
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
							'你确定要删除选中的常量信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/constantmanager/constantmanager-delete?id="
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
													$('#constantmanagerTable').datagrid("reload");
													$('#constantmanagerTable').datagrid('clearSelections');
												}
											});
								}
								$('#constantmanagerTable').datagrid('clearSelections');
							});
			
		}
	}

	//查询
	function constantmanagerQuery() {
		$('#constantmanagerTable').datagrid("reload", {
			search_LIKE_englishName : $('#constantmanager_englishName').val()
		});
	}
	//删除、修改 常量信息
	function operateconstantManager(val, row) {
		var rowId = row.id;
		return "<a onClick='clickConstantManagerIdDetail("+row.id+")' style='cursor:pointer'>"
		+"<img src=\'${ctx}/themes/operate/icon_detail.png\' alt=\'查看常量详情\' title=\'查看常量详情\' /></a>"+"&nbsp;&nbsp;&nbsp;"+"<security:authorize ifAnyGranted="ROLE_CONSTANTMANAGER_EDIT"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByconstantmanagerId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'常量修改\' title=\'常量修改\' /> </a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="ROLE_CONSTANTMANAGER_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByconstantmanagerId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'常量删除\' title=\'常量删除\' /></a></security:authorize>";
	}
	

	 //查看常量详情
	function clickConstantManagerIdDetail(id){
		$('#constantmanagerWindow').window({
			title : '查看常量详情'
		});
		$('#constantmanagerWindow').window('open');
		$('#constantmanagerWindow').window('refresh', '${ctx}/constantmanager/constantmanager-info?id='+id);
		$('#constantmanagerTable').datagrid('clearSelections');
	}
	
	 
	//删除常量信息
	function deleteByconstantmanagerId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的常量！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的常量吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/constantmanager/constantmanager-delete?id="
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
													$('#constantmanagerTable').datagrid("reload");
													$('#constantmanagerTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#constantmanagerTable').datagrid('clearSelections');
	}

	//常量修改 
	function updateByconstantmanagerId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择常量信息', 'warning');
		} else {
			var constantTypeId='${constantTypeId}';
			
			$('#constantmanagerWindow').window({
				title : '修改常量'
			});
			$('#constantmanagerWindow').window('open');
			$('#constantmanagerWindow').window('refresh',
					'${ctx}/constantmanager/constantmanager-input?id=' + rowId+"&constantTypeId="+constantTypeId);
			$('#constantmanagerTable').datagrid('clearSelections');
		}
	}
	
	function operateconstantMemory(val) {
		if(val==0){
			return "否";
		}else if(val==1){
			return "是";
		}
	}
</script>
<table id="constantmanagerTable" class="easyui-datagrid" border="0"
	fit="true"
	data-options="fitColumns:true,idField:'itemid',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/constantmanager/constantmanager-list-data?constantTypeId=${constantTypeId}',toolbar:'#constantmanagerTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',hidden:true">id</th>
			<th data-options="field:'chinaName'" width="150" align="center">中文名称</th>
			<th data-options="field:'englishName'" width="150" align="center">常量名称</th>
			<th data-options="field:'constantValue'" width="80" align="center">常量值</th>
			<th data-options="field:'unit'" width="70" align="center">常量单位</th>
			<th data-options="field:'isMemory'" width="110" align="center" formatter="operateconstantMemory">是否放入内存</th>
			<th data-options="field:'null'" width="100" align='center' formatter="operateconstantManager">操作</th>
		</tr>
	</thead>
</table>
<div id="constantmanagerTool" style="padding: 5px; height: auto">
	<security:authorize ifAnyGranted="ROLE_CONSTANTMANAGER_INPUT">
		<a href="javascript:void(0)"
			onclick="openconstantmanagerWindow('添加常量','${ctx}/constantmanager/constantmanager-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_CONSTANTMANAGER_DELETE">
		<a href="javascript:void(0)" onclick="deleteconstantmanagers()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	</security:authorize>
	常量名称：<input type="text" id="constantmanager_englishName"
		value="${search_LIKE_englishName}" style="width: 100px"></input> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true" onclick="constantmanagerQuery();">查询</a>
</div>

<!-- 新增和修改的弹出框 -->
<div id="constantmanagerWindow" class="easyui-window"
	style="width: 440px; height: 325px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
