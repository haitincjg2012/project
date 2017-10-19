<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">

	function loggingeventQuery() {
		$('#loggingeventTable').datagrid("reload", {
			search_GTE_timestmp : $('#loggingeventinTime').datetimebox('getValue'),
			search_LTE_timestmp : $('#loggingeventoutTime').datetimebox('getValue'),
		});
	}
</script>
<table id="loggingeventTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'itemid',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:true,url:'${ctx}/loggingevent/loggingevent-list-data',toolbar:'#loggingeventTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'id',hidden:true">id</th>
			<th data-options="field:'timestmp'" width="20" align="center">时间</th>
			<th data-options="field:'formattedMessage'" width="100" align="center">操作</th>
		</tr>
	</thead>
</table>
<div id="loggingeventTool" style="padding: 5px; height: auto">

	操作开始时间：<input class="easyui-datetimebox" id="loggingeventinTime"
		data-options="showSeconds:false,formatter:myformatterYmdhms"
		style="width: 150px"> 操作结束时间：<input class="easyui-datetimebox"
		id="loggingeventoutTime" data-options="showSeconds:false,formatter:myformatterYmdhms"
		style="width: 150px"><a href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-search" plain="true"
		onclick="loggingeventQuery();">查询</a>
</div>
