<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	
function updatePhone() {
	$('#updateUserPhone').window({
		title : '修改手机号'
	});
	$('#updateUserPhone').window('open');
	$('#updateUserPhone').window('refresh',
			'${ctx}/pc/after/industryassociation/update_phone_page');
	 
}  

	
//删除、修改 用户信息
 function operateUser(val, row) {
	var rowId = row.id;
	return "<security:authorize ifAnyGranted="ROLE_EDITPHONEMSG"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updatePhone("
			+ rowId
			+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'修改手机号\' title=\'修改手机号\' />修改手机号</a></security:authorize> ";
} 
</script>

<table id="userphoneTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[10,20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/pc/after/industryassociation/phone_industryassociation_data',toolbar:'#userTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'phone'" width="80" align="center">手机号</th>
			<th data-options="field:'card'" width="80" align="center">身份证号</th>
			<th data-options="field:'null'" width="100" align='center' formatter="operateUser">操作</th>
		</tr>
	</thead>
</table>

<div id="updateUserPhone" class="easyui-window"
	style="width: 500px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
