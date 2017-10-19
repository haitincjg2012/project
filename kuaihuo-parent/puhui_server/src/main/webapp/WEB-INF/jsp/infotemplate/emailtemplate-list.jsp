<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//模板添加修改窗口
	function openEmailTemplateWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#emailTemplateTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择模板内容', 'warning');
			} else {
				$('#emailTemplateWindow').window({
					title : title
				});
				$('#emailTemplateWindow').window('open');
				$('#emailTemplateWindow').window('refresh', url + '?id=' + rows.id);
				$('#emailTemplateTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#emailTemplateWindow').window({
				title : title
			});
			$('#emailTemplateWindow').window('open');
			$('#emailTemplateWindow').window('refresh', url);
			$('#emailTemplateTable').datagrid('clearSelections');
		}
	}

	function deleteemailTemplates() {
		var rows = $('#emailTemplateTable').datagrid('getSelections');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的模板内容！', 'warning');
		} else {
			var ids = '';
			for ( var i = 0; i < rows.length; i++) {
				if (ids != '')
					ids += ',';
				ids += rows[i].id;
			}
			$.messager.confirm(
					'温馨提示','你确定要删除选中的模板内容吗？',
							function(r) {
								if (r) {
									$.ajax({
												type : "POST",
												url : "${ctx}/emailtemplate/emailtemplate-delete?id="
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
													$('#emailTemplateTable').datagrid("reload");
													$('#emailTemplateTable').datagrid('clearSelections');
												}
											});
								}
								$('#emailTemplateTable').datagrid('clearSelections');
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

	function emailTemplateQuery() {
		$('#emailTemplateTable').datagrid('clearSelections');
		$('#emailTemplateTable').datagrid("reload", {
			search_LIKE_emailTitle: $('#emailTemplate_emailTitle').val(),
			search_LIKE_emailEnglishName: $('#emailTemplate_emailEnglishName').val(),
			search_GTD_createdTime:$('#emailTemplate_createdTime_start').datebox('getValue')==""?"":$('#emailTemplate_createdTime_start').datebox('getValue')+" 00:00:00",
			search_LTD_createdTime:$('#emailTemplate_createdTime_end').datebox('getValue')==""?"":$('#emailTemplate_createdTime_end').datebox('getValue')+" 23:59:59"
		});
	}
	
	//查看、删除、修改 模板信息
	function viewemailTemplate(val,row){
		 var rowId = row.id;
		 return "<a onclick='clickemailTemplateIdDetail("+row.id+")' style='cursor:pointer'>"
		+"<img src=\'${ctx}/themes/operate/icon_detail.png\' alt=\'查看详情\' title=\'查看详情\' /></a>"
		+"&nbsp;&nbsp;&nbsp;"+"<a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateByemailTemplateId("+rowId+")\' style=\'cursor:pointer\''>"
		 +"<img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'修改模板\' title=\'修改模板\' /></a>"
		+"&nbsp;&nbsp;&nbsp;"+"<a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByemailTemplateId("+rowId+")\' style=\'cursor:pointer\''>"
		 +"<img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'删除模板\' title=\'删除模板\' /></a>";
	}
	
	// 查看 模板信息
	function clickemailTemplateIdDetail(id){
		$('#emailTemplateInfosWindow').window({
			title : '查看模板详情'
		});
		$('#emailTemplateInfosWindow').window('open');
		$('#emailTemplateInfosWindow').window('refresh', '${ctx}/emailtemplate/emailtemplate-info?id='+id);
		$('#emailTemplateTable').datagrid('clearSelections');
	}
	//删除单条模板信息
	function deleteByemailTemplateId(rowId){
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的模板！');
		} else {
			$.messager.confirm(
					'温馨提示','你确定要删除选中的模板吗？',
							function(r) {
								if (r) {
									$.ajax({
												type : "POST",
												url : "${ctx}/emailtemplate/emailtemplate-delete?id="
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
													$('#emailTemplateTable').datagrid("reload");
													$('#emailTemplateTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#emailTemplateTable').datagrid('clearSelections');
	}
	
	//模板修改 
	function updateByemailTemplateId(rowId){
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择模板信息');
		} else {
			$('#emailTemplateWindow').window({
				title : '修改模板信息'
			});
			$('#emailTemplateWindow').window('open');
			$('#emailTemplateWindow').window('refresh','${ctx}/emailtemplate/emailtemplate-input?id=' +rowId);
			$('#emailTemplateTable').datagrid('clearSelections');
		}
	}

	
</script>
<table id="emailTemplateTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/emailtemplate/emailtemplate-list-data',toolbar:'#emailTemplateTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'emailTitle'" width="150" align="center">邮件模板标题</th>
			<th data-options="field:'emailEnglishName'" width="150" align="center">邮件模板英文名</th>
			<th data-options="field:'createdTime'" width="100" align="center">发布时间</th>
			<th data-options="field:'operate'" width="50" align="center" formatter="viewemailTemplate">操  作</th>
		</tr>
	</thead>
</table>
<div id="emailTemplateTool" style="padding: 5px; height: auto">
		<a href="javascript:void(0)"
			onclick="openEmailTemplateWindow('添加模板','${ctx}/emailtemplate/emailtemplate-input','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
<%-- 		<a href="javascript:void(0)"
			onclick="openemailTemplateWindow('修改模板','${ctx}/emailTemplate/emailTemplate-input','edit')"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> --%>
		<a href="javascript:void(0)" onclick="deleteemailTemplates()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	模板标题：<input type="text" id="emailTemplate_emailTitle" value="${search_LIKE_emailTitle}"
		style="width: 120px"></input>
	模板英文标题：<input type="text" id="emailTemplate_emailEnglishName" value="${search_LIKE_emailEnglishName}"
		style="width: 120px"></input> 
	发布时间：<input class="easyui-datebox" id="emailTemplate_createdTime_start"
		data-options="showSeconds:false,formatter:myformatter" name="createdTime"
		style="width: 130px">---<input class="easyui-datebox" name="createdTime"
		id="emailTemplate_createdTime_end" data-options="showSeconds:false,formatter:myformatter"
		style="width: 130px">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true" onclick="emailTemplateQuery();">查询</a>
</div>

<!-- 新增和修改的弹出框 -->
<div id="emailTemplateWindow" class="easyui-window"
	style="width:540px; height: 420px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
<!-- 查看模板的弹出框 -->
<div id="emailTemplateInfosWindow" class="easyui-window"
	style="width: 650px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
<!-- 选择所属类型的信息  -->
<div id="infoTypeWindow" class="easyui-window"
	style="width: 540px; height: 420px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>