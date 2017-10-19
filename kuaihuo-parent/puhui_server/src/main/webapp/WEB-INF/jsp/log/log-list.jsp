<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	function viewState(val, row) {
		if (val == 0) {
			return "<span style='color:red;'>在线</span>";
		} else if (val == 1) {
			return "退出";
		}
	}

	function logQuery() {
		$('#logTable').datagrid("reload", {
			search_GTD_loginTime : $('#loginTime').datetimebox('getValue'),
			search_LTD_logoutTime : $('#logoutTime').datetimebox('getValue'),
			search_EQ_state : $('#userlong-state').val(),
			search_LIKE_loginName:$('#userlog_loginName').val()
		});
	}
</script>
<table id="logTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'itemid',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:true,url:'${ctx}/log/log-list-data',toolbar:'#logTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',hidden:true">id</th>
			<th data-options="field:'loginName'" width="80" align="center">用户名</th>
			<th data-options="field:'roleName'" width="80" align="center">角色</th>
			<th data-options="field:'browser'" width="80" align="center">浏览器</th>
			<th data-options="field:'loginIp'" width="80" align="center">登录ip</th>
			<th data-options="field:'state'" align="center" formatter="viewState" width="80">在线状态</th>
			<th data-options="field:'loginTime'" align="center" width="80">登录时间</th>
			<th data-options="field:'logoutTime'" align="center" width="80">退出时间</th>
		</tr>
	</thead>
</table>
<div id="logTool" style="padding: 5px; height: auto">

	登录时间：<input class="easyui-datetimebox" id="loginTime"
		data-options="showSeconds:false,formatter:myformatterYmdhms"
		style="width: 150px"> 退出时间：<input class="easyui-datetimebox"
		id="logoutTime" data-options="showSeconds:false,formatter:myformatterYmdhms"
		style="width: 150px">用户名：<input type="text" id="userlog_loginName" value="${search_LIKE_loginName}"
		style="width: 80px"></input> 在线状态：<select id="userlong-state"
		name="userlong-state" style="width: 150px"><option value="">请选择</option>
		<option value="0">在线</option>
		<option value="1">退出</option></select> <a href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-search" plain="true"
		onclick="logQuery();">查询</a>
</div>

<div id="logWindow" class="easyui-window"
	style="width: 450px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
