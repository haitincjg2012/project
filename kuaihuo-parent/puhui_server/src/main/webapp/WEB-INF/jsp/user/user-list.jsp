<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//用户添加修改窗口
	function openUserWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#userTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择用户');
			} else {
				$('#userWindow').window({
					title : title
				});
				$('#userWindow').window('open');
				$('#userWindow').window('refresh', url + '?id=' + rows.id);
				$('#userTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#userWindow').window({
				title : title
			});
			$('#userWindow').window('open');
			$('#userWindow').window('refresh', url);
			$('#userTable').datagrid('clearSelections');
		}
	}

	function deleteUsers() {
		var rows = $('#userTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的用户！');
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
							'你确定要删除选中的用户吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/user/user-delete?id="
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
													$('#userTable').datagrid("reload");
													$('#userTable').datagrid('clearSelections');
												}
											});
								}
								$('#userTable').datagrid('clearSelections');
							});
		}
	}

	function userQuery() {
		$('#userTable').datagrid("reload", {
			search_LIKE_userName : $('#user_userName').val(),
			search_LIKE_realName : $('#user_realName').val(),
			search_LIKE_email : $('#user_email').val(),
			search_EQ_roleId : $('#user_role').val()
		});
	}
	
	//删除、修改 用户信息
	function operateUser(val, row) {
		var rowId = row.id;
		return "<security:authorize ifAnyGranted="ROLE_USER_EDIT"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByUserId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'用户修改\' title=\'用户修改\' /></a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="ROLE_USER_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByUserId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'用户删除\' title=\'用户删除\' /></a></security:authorize>";
	}

	//删除用户信息
	function deleteByUserId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的用户！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的用户吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/user/user-delete?id="
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
													$('#userTable').datagrid("reload");
													$('#userTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#userTable').datagrid('clearSelections');
	}

	//用户修改 
	function updateByUserId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择用户信息', 'warning');
		} else {
			$('#userWindow').window({
				title : '修改用户'
			});
			$('#userWindow').window('open');
			$('#userWindow').window('refresh',
					'${ctx}/user/user-input?id=' + rowId);
			
			$('#userTable').datagrid('clearSelections');
		}
	}

	//显示是否激活的信息
	function viewlock(val, row) {
		if (val == 0)
			return "激活";
		else
			return "未激活";
	}
	//显示角色下面的描述
	function viewRole(val, row) {
		return val.descript;
	}

	//显示性别
	function viewGender(val, row) {
		if (val == 0) {
			return "未知";
		} else if (val == 1) {
			return "男";
		} else if (val == 2) {
			return "女";
		}
	}
</script>
<table id="userTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/user/user-list-data',toolbar:'#userTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'userName'" width="80" align="center">用户名</th>
			<th data-options="field:'realName'" width="80" align="center">真实姓名</th>
			<th data-options="field:'mobile'" width="80" align="center">电话号码</th>
			<th data-options="field:'tel'" width="80" align="center">手机号码</th>
			<th data-options="field:'email'" width="120" align="center">邮 箱</th>
			<th data-options="field:'gender'" width="40" align="center"
				formatter="viewGender">性别</th>
			<th data-options="field:'times'" width="50" align="center">登陆次数</th>
			<th data-options="field:'isLocked'" align="center"
				formatter="viewlock" width="80">是否激活</th>
			<th data-options="field:'role'" align="center" formatter="viewRole"
				width="80">角色名称</th>
			<th data-options="field:'address'" align="center" width="80">联系地址</th>
			<th data-options="field:'null'" width="100" align='center'
				formatter="operateUser">操作</th>
		</tr>
	</thead>
</table>
<div id="userTool" style="padding: 5px; height: auto">
	<security:authorize ifAnyGranted="ROLE_USER_INPUT">
		<a href="javascript:void(0)"
			onclick="openUserWindow('添加用户','${ctx}/user/user-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_USER_DELETE">
		<a href="javascript:void(0)" onclick="deleteusers()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	</security:authorize>
	用户名：<input type="text" id="user_userName"
				value="${search_LIKE_userName}" style="width: 80px"></input> 真实姓名：<input
				type="text" id="user_realName" value="${search_LIKE_realName}"
				style="width: 80px"></input> 邮 箱：<input type="text" id="user_email"
				value="${search_LIKE_email}" style="width: 80px"></input> 角色名称：<select
				id="user_role">
				<option value="">全部</option>
				<c:forEach items="${roles}" var="myrole">
					<option value="${myrole.id}">${myrole.descript}</option>
				</c:forEach>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-search" plain="true" onclick="userQuery();">查询</a>
</div>

<div id="userWindow" class="easyui-window"
	style="width: 500px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
