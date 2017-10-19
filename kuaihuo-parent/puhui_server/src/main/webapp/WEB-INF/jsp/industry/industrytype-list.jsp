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
			var rows = $('#industryTypeTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择用户');
			} else {
				$('#industryTypeWindow').window({
					title : title
				});
				$('#industryTypeWindow').window('open');
				$('#industryTypeWindow').window('refresh', url + '?id=' + rows.id);
				$('#industryTypeTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#industryTypeWindow').window({
				title : title
			});
			$('#industryTypeWindow').window('open');
			$('#industryTypeWindow').window('refresh', url);
			$('#industryTypeTable').datagrid('clearSelections');
		}
	}

	function deleteUsers() {
		var rows = $('#industryTypeTable').datagrid('getChecked');
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
													$('#industryTypeTable').datagrid("reload");
													$('#industryTypeTable').datagrid('clearSelections');
												}
											});
								}
								$('#industryTypeTable').datagrid('clearSelections');
							});
		}
	}

	function userQuery() {
		$('#industryTypeTable').datagrid("reload", {
			search_LIKE_userName : $('#user_userName').val(),
			search_LIKE_realName : $('#user_realName').val(),
			search_LIKE_email : $('#user_email').val(),
			search_EQ_roleId : $('#user_role').val()
		});
	}
	
	//删除、修改 用户信息
	function operateUser(val, row) {
		var rowId = row.id;
		var rowsTop = row.top;
		
		
		var name;
		if(rowsTop==0 || rowsTop==null){
			name="置顶";
		}else if(rowsTop==1){
			name="取消置顶";
		}
		return "<security:authorize ifAnyGranted="ROLE_INDUSTRYTYPE_EDIT"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByTypeId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'分类查看修改\' title=\'分类查看修改\' /></a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="ROLE_INDUSTRYTYPE_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByUserId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'分类删除\' title=\'分类删除\' /></a> &nbsp; &nbsp;</security:authorize>"
				+"<security:authorize ifAnyGranted="ROLE_INDUSTRYTYPE_TOP"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'firstpAppPageTop("
				+ rowId+","+rowsTop
				+ ")\' style=\'cursor:pointer\''>"+name+"</a></security:authorize>";
				;
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
													$('#industryTypeTable').datagrid("reload");
													$('#industryTypeTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#industryTypeTable').datagrid('clearSelections');
	}
	//首页App置顶
	function firstpAppPageTop(rowId,rowsTop) {
		
		console.log(rowId);
		
		var datas=$("#industryTypeTable").datagrid("getSelections");
		console.log("数据中返回:"+datas);
		var showData;
		
		if (rowsTop==null || rowsTop==0) {
			showData='你将要置顶此信息?';
			rowsTop=1;
		} else if (rowsTop==1) {
			showData = '你将要取消置顶此信息?';
			rowsTop=0;
		}
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要置顶的信息！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							showData,
							function(r) {
								if (r) {
									$.ajax({
												type : "POST",
												url : "${ctx}/pc/after/industry/industrytype-top?id="
														+ rowId+"&top="+rowsTop,
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
													$('#industryTypeTable').datagrid("reload");
													$('#industryTypeTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#industryTypeTable').datagrid('clearSelections');
	}
	//用户修改 
	function updateByTypeId(rowId) {
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择用户信息', 'warning');
		} else {
			$('#industryTypeWindow').window({
				title : '编辑行业分类'
			});
			$('#industryTypeWindow').window('open');
			$('#industryTypeWindow').window('refresh',
					'${ctx}/pc/after/industry/industrytype-edit?id=' + rowId);
			
			$('#industryTypeTable').datagrid('clearSelections');
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

<table id="industryTypeTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/pc/after/industry/industry-typelist-data',toolbar:'#industryTypeTool',pagination:true">
	<thead>
		<tr>
		    <input type=text style="display:none" data-options="field:'top'">
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'name'" width="30" align="center">一级分类</th>
			<th  heiht="20px" data-options="field:'address',formatter: function(value,row,index){
							return '<img src=\'${ctx_ali}/'+row.address+'\'/>';
				}" align="center" width="40">图片</th>
			<th data-options="field:'null'" width="20" align='center'
				formatter="operateUser">操作</th>
		</tr>
	</thead>
</table>  
<div id="industryTypeTool" style="padding: 5px; height: auto">
	<security:authorize ifAnyGranted="ROLE_INDUSTRYTYPE_INPUT">
		<a href="javascript:void(0)"
			onclick="openUserWindow('行业分类','${ctx}/pc/after/industry/industrytype-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_USER_DELETE">
		<!-- <a href="javascript:void(0)" onclick="deleteusers()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a> -->
	</security:authorize>
	<%-- 用户名：<input type="text" id="user_userName"
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
				iconCls="icon-search" plain="true" onclick="userQuery();">查询</a> --%>
</div>

<div id="industryTypeWindow" class="easyui-window"
	style="width: 500px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
