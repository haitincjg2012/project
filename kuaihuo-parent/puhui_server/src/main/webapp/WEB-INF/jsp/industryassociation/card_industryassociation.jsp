<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	

</script>
<table id="cardindustryassociationTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[10,20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/pc/after/industryassociation/card_industryassociation_data',toolbar:'#userTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'payname'" width="80" align="center">姓名</th>
			<th data-options="field:'card'" width="80" align="center">身份证号</th>
			
		</tr>
	</thead>
</table>


