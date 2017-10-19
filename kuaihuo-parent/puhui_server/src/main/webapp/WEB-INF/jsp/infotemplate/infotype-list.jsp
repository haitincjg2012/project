<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//模板添加修改窗口
	function openInfoTypeWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#infoTypeTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择模板类型内容', 'warning');
			} else {
				$('#infoTypeWindow').window({
					title : title
				});
				$('#infoTypeWindow').window('open');
				$('#infoTypeWindow').window('refresh', url + '?id=' + rows.id);
				$('#infoTypeTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#infoTypeWindow').window({
				title : title
			});
			$('#infoTypeWindow').window('open');
			$('#infoTypeWindow').window('refresh', url);
			$('#infoTypeTable').datagrid('clearSelections');
		}
	}

	function deleteInfoTypes() {
		var rows = $('#infoTypeTable').datagrid('getSelections');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的模板类型信息！', 'warning');
		} else {
			var ids = '';
			for ( var i = 0; i < rows.length; i++) {
				if (ids != '')
					ids += ',';
				ids += rows[i].id;
			}
			$.messager.confirm(
					'温馨提示','你确定要删除选中的模板类型吗？',
							function(r) {
								if (r) {
									$.ajax({
												type : "POST",
												url : "${ctx}/infotype/infotype-delete?id="
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
													$('#infoTypeTable').datagrid("reload");
													$('#infoTypeTable').datagrid('clearSelections');
												}
											});
								}
								$('#infoTypeTable').datagrid('clearSelections');
							});
		}
	}
	
	
	//时间控件
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		var h = date.getHours(); //hour  
		var n = date.getMinutes(); //minute  
		var s = date.getSeconds();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'+ (d < 10 ? ('0' + d) : d);//年月日
	}

	function infoTypeQuery() {
		$('#infoTypeTable').datagrid('clearSelections');
		$('#infoTypeTable').datagrid("reload", {
			search_LIKE_infoTypeName: $('#infoType_infoTypeName').val(),
		});
	}
	
	//查看、删除、修改 模板信息
	function viewInfoType(val,row){
		 var rowId = row.id;
		 return "<a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByInfoTypeId("+rowId+")\' style=\'cursor:pointer\''>"
		 +"<img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'修改模板类型\' title=\'修改模板类型\' /></a>"
		+"&nbsp;&nbsp;&nbsp;"+"<a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByInfoTypeId("+rowId+")\' style=\'cursor:pointer\''>"
		 +"<img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'删除模板类型\' title=\'删除模板类型\' /></a>";
	}
	//删除单条模板信息
	function deleteByInfoTypeId(rowId){
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的模板类型！');
		} else {
			$.messager.confirm(
					'温馨提示','你确定要删除选中的模板类型吗？',
							function(r) {
								if (r) {
									$.ajax({
												type : "POST",
												url : "${ctx}/infotype/infotype-delete?id="
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
													$('#infoTypeTable').datagrid("reload");
													$('#infoTypeTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#infoTypeTable').datagrid('clearSelections');
	}
	
	//模板修改 
	function updateByInfoTypeId(rowId){
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择模板类型');
		} else {
			$('#infoTypeWindow').window({
				title : '修改模板类型'
			});
			$('#infoTypeWindow').window('open');
			$('#infoTypeWindow').window('refresh','${ctx}/infotype/infotype-input?id=' +rowId);
			$('#infoTypeTable').datagrid('clearSelections');
		}
	}

	
</script>
<table id="infoTypeTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/infotype/infotype-list-data',toolbar:'#infoTypeTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'infoTypeName'" width="150" align="center">邮件类型名</th>
			<th data-options="field:'infoTypeEnglishName'" width="150" align="center">邮件类型英文名</th>
			<th data-options="field:'createdTime'" width="100" align="center">创建时间</th>
			<th data-options="field:'operate'" width="50" align="center" formatter="viewInfoType">操  作</th>
		</tr>
	</thead>
</table>
<div id="infoTypeTool" style="padding: 5px; height: auto">
		<a href="javascript:void(0)"
			onclick="openInfoTypeWindow('添加模板','${ctx}/infotype/infotype-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
<%-- 		<a href="javascript:void(0)"
			onclick="openinfoTypeWindow('修改模板','${ctx}/infoType/infoType-input','edit')"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> --%>
		<a href="javascript:void(0)" onclick="deleteInfoTypes()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	模板类型名：<input type="text" id="infoType_infoTypeName" value="${search_LIKE_infoTypeName}"
		style="width: 120px"></input>
	模板类型英文名：<input type="text" id="infoType_infoTypeEnglishName" value="${search_LIKE__infoTypeEnglishName}"
		style="width: 120px"></input>
	发布时间：<input class="easyui-datebox" id="infoType_createdTime_start"
		data-options="showSeconds:false,formatter:myformatter" name="createdTime"
		style="width: 130px">---<input class="easyui-datebox" name="createdTime"
		id="infoType_createdTime_end" data-options="showSeconds:false,formatter:myformatter"
		style="width: 130px">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true" onclick="infoTypeQuery();">查询</a>
</div>

<!-- 新增和修改的弹出框 -->
<div id="infoTypeWindow" class="easyui-window"
	style="width:540px; height: 420px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
<!-- 查看模板的弹出框 -->
<div id="infoTypeInfosWindow" class="easyui-window"
	style="width: 650px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>