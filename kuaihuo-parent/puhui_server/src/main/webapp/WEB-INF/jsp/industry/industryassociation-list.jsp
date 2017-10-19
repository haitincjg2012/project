<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<style>
	 .datagrid-cell>img{
	 		width: 48px;
	 	  height:43px;
  		  margin: 5px;
	 }
</style>
<script type="text/javascript">
	//用户添加修改窗口
	function openUserWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#industryAssoncationTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择用户');
			} else {
				$('#associationWindow').window({
					title : title
				});
				$('#associationWindow').window('open');
				$('#associationWindow').window('refresh', url + '?id=' + rows.id);
				$('#industryAssoncationTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#associationWindow').window({
				title : title
			});
			$('#associationWindow').window('open');
			$('#associationWindow').window('refresh', url);
			$('#industryAssoncationTable').datagrid('clearSelections');
		}
	}

	function deleteUsers() {
		var rows = $('#industryAssoncationTable').datagrid('getChecked');
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
													$('#industryAssoncationTable').datagrid("reload");
													$('#industryAssoncationTable').datagrid('clearSelections');
												}
											});
								}
								$('#industryAssoncationTable').datagrid('clearSelections');
							});
		}
	}

	function associationQuery() {
		$('#industryAssoncationTable').datagrid("reload", {
			search_LIKE_name : $('#assoncation_name').val(),
			search_LIKE_phone : $('#assoncation_phone').val(),
		});
	}
	
	//删除、修改 用户信息
	function operateUser(val, row) {
		var rowId = row.id;
		return "<security:authorize ifAnyGranted="ROLE_ASSOCIATION_DETAIL"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByAssId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_detail.png\' alt=\'查看\' title=\'查看\' /></a> &nbsp; &nbsp;</security:authorize>";
	}

	//删除用户信息
	function deleteByUserId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的行业分类！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的行业分类吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/pc/after/industry/industrytype-delete?id="
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
													$('#industryAssoncationTable').datagrid("reload");
													$('#industryAssoncationTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#industryAssoncationTable').datagrid('clearSelections');
	}

	//用户修改 
	function updateByAssId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择公会信息', 'warning');
		} else {
			$('#associationWindow').window({
				title : '查看行业公会'
			});
			$('#associationWindow').window('open');
			$('#associationWindow').window('refresh',
					'${ctx}/pc/after/industryassociation/industry-association-detail?id=' + rowId);
			
			$('#industryAssoncationTable').datagrid('clearSelections');
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

<table id="industryAssoncationTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/pc/after/industryassociation/industry-association-data',toolbar:'#industryAssociationTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'name'" align="center" width="60">行业公会名称</th>
			<th data-options="field:'phone'" width="20" align="center">手机号</th>
		<!-- 	<th data-options="field:'payname'" align="center" width="30">收款人姓名</th>
			<th data-options="field:'card'" align="center" width="30">身份证</th> -->
			<th data-options="field:'null'" width="20" align='center'
				formatter="operateUser">操作</th>
		</tr>
	</thead>
</table>  
<div id="industryAssociationTool" style="padding: 5px; height: auto">
	<security:authorize ifAnyGranted="ROLE_ASSOCIATION_INPUT">
		<a href="javascript:void(0)"
			onclick="openUserWindow('行业公会','${ctx}/pc/after/industryassociation/industry-association-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_USER_DELETE">
		<!-- <a href="javascript:void(0)" onclick="deleteusers()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a> -->
	</security:authorize>
	 		行业公会名称：<input type="text" id="assoncation_name" value="${search_LIKE_name}" style="width: 80px"></input> 
			手机号：<input type="text" id="assoncation_phone" value="${search_LIKE_phone}" style="width: 80px"></input>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-search" plain="true" onclick="associationQuery();">查询</a> 
</div>

<div id="associationWindow" class="easyui-window"
	style="width: 500px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
