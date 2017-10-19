<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//模型添加修改窗口
	function selectInfoTypeWindow() {
				var rows = $('#selectInfoTypeTable').datagrid('getSelected');
				parent.getSelInfoType(rows.infoTypeEnglishName,rows.infoTypeName);
				 $('#infoTypeWindow').window('close'); 
	}
	function windowgroupQuery() {
		$('#selectInfoTypeTable').datagrid("reload", {
			search_LIKE_infoTypeName:$('#select_infotype_infoTypeName').val(),
			search_LIKE_infoTypeEnglishName:$('#select_infotype_infoTypeEnglishName').val()
		});
	}
</script>
<table id="selectInfoTypeTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'itemid',method:'post',pageList:[20,50,100],rownumbers:true,singleSelect:true,url:'${ctx}/infotype/infotype-list-data',toolbar:'#selectInfoTypeTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',hidden:true">id</th>
			<th data-options="field:'infoTypeName'" width="80" align="center">模板类型名</th>
			<th data-options="field:'infoTypeEnglishName'" width="80" align="center">模板类型英文名</th>
		</tr>
	</thead>
</table>
<div id="selectInfoTypeTool" style="padding: 5px; height: auto">
	<a href="javascript:selectInfoTypeWindow()"
		class="easyui-linkbutton" iconCls="icon-add" plain="true">选择</a>
	模板类型名：<input type="text" id="select_infotype_infoTypeName" value="${search_LIKE_infoTypeName}"
		style="width: 100px"></input>
	模板类型英文名：<input type="text" id="select_infotype_infoTypeEnglishName" value="${search_LIKE_infoTypeEnglishName}"
		style="width: 100px"></input>
	 <a href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-search" plain="true"
		onclick="windowgroupQuery();">查询</a>
</div>

