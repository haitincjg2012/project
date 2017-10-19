<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//模板添加修改窗口
	function openSmsTemplateWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#smsTemplateTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择模板内容', 'warning');
			} else {
				$('#smsTemplateWindow').window({
					title : title
				});
				$('#smsTemplateWindow').window('open');
				$('#smsTemplateWindow').window('refresh', url + '?id=' + rows.id);
				$('#smsTemplateTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#smsTemplateWindow').window({
				title : title
			});
			$('#smsTemplateWindow').window('open');
			$('#smsTemplateWindow').window('refresh', url);
			$('#smsTemplateTable').datagrid('clearSelections');
		}
	}

	function deleteSmsTemplates() {
		var rows = $('#smsTemplateTable').datagrid('getSelections');
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
												url : "${ctx}/smstemplate/smstemplate-delete?id="
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
													$('#smsTemplateTable').datagrid("reload");
													$('#smsTemplateTable').datagrid('clearSelections');
												}
											});
								}
								$('#smsTemplateTable').datagrid('clearSelections');
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

	function smsTemplateQuery() {
		$('#smsTemplateTable').datagrid('clearSelections');
		$('#smsTemplateTable').datagrid("reload", {
			search_LIKE_smsTitle: $('#smsTemplate_title').val(),
			search_LIKE_smsEnglishName: $('#smsTemplate_smsEnglishName').val(),
			search_GTD_createdTime:$('#smsTemplate_createdTime_start').datebox('getValue')==""?"":$('#smsTemplate_createdTime_start').datebox('getValue')+" 00:00:00",
			search_LTD_createdTime:$('#smsTemplate_createdTime_end').datebox('getValue')==""?"":$('#smsTemplate_createdTime_end').datebox('getValue')+" 23:59:59"
		});
	}
	
	//查看、删除、修改 模板信息
	function viewSmsTemplate(val,row){
		 var rowId = row.id;
		 return "<a onclick='clickSmsTemplateIdDetail("+row.id+")' style='cursor:pointer'>"
		+"<img src=\'${ctx}/themes/operate/icon_detail.png\' alt=\'查看详情\' title=\'查看详情\' /></a>"
		+"&nbsp;&nbsp;&nbsp;"+"<a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateBySmsTemplateId("+rowId+")\' style=\'cursor:pointer\''>"
		 +"<img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'修改模板\' title=\'修改模板\' /></a>"
		+"&nbsp;&nbsp;&nbsp;"+"<a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteBySmsTemplateId("+rowId+")\' style=\'cursor:pointer\''>"
		 +"<img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'删除模板\' title=\'删除模板\' /></a>";
	}
	
	// 查看 模板信息
	function clickSmsTemplateIdDetail(id){
		$('#smsTemplateInfosWindow').window({
			title : '查看模板详情'
		});
		$('#smsTemplateInfosWindow').window('open');
		$('#smsTemplateInfosWindow').window('refresh', '${ctx}/smstemplate/smstemplateinfo?id='+id);
		$('#smsTemplateTable').datagrid('clearSelections');
	}
	//删除单条模板信息
	function deleteBySmsTemplateId(rowId){
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的模板！');
		} else {
			$.messager.confirm(
					'温馨提示','你确定要删除选中的模板吗？',
							function(r) {
								if (r) {
									$.ajax({
												type : "POST",
												url : "${ctx}/smstemplate/smstemplate-delete?id="
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
													$('#smsTemplateTable').datagrid("reload");
													$('#smsTemplateTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#smsTemplateTable').datagrid('clearSelections');
	}
	
	//模板修改 
	function updateBySmsTemplateId(rowId){
		if (rowId == null) {
			$.messager.alert('温馨提示', '请选择模板信息');
		} else {
			$('#smsTemplateWindow').window({
				title : '修改模板信息'
			});
			$('#smsTemplateWindow').window('open');
			$('#smsTemplateWindow').window('refresh','${ctx}/smstemplate/smstemplateinput?id=' +rowId);
			$('#smsTemplateTable').datagrid('clearSelections');
		}
	}

	
</script>
<table id="smsTemplateTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/smstemplate/smstemplate-list-data',toolbar:'#smsTemplateTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'smsTitle'" width="150" align="center">短信模板标题</th>
			<th data-options="field:'smsEnglishName'" width="150" align="center">短信模板英文名</th>
			<th data-options="field:'createdTime'" width="100" align="center">发布时间</th>
			<th data-options="field:'operate'" width="50" align="center" formatter="viewSmsTemplate">操  作</th>
		</tr>
	</thead>
</table>
<div id="smsTemplateTool" style="padding: 5px; height: auto">
		<a href="javascript:void(0)"
			onclick="openSmsTemplateWindow('短信模板添加','${ctx}/smstemplate/smstemplateinput','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
<%-- 		<a href="javascript:void(0)"
			onclick="opensmsTemplateWindow('修改模板','${ctx}/smsTemplate/smsTemplate-input','edit')"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> --%>
		<a href="javascript:void(0)" onclick="deleteSmsTemplates()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	模板标题：<input type="text" id="smsTemplate_title" value="${search_LIKE_smsTitle}"
		style="width: 120px"></input>
	模板英文名：<input type="text" id="smsTemplate_smsEnglishName" value="${search_LIKE_smsEnglishName}"
		style="width: 120px"></input> 
	发布时间：<input class="easyui-datebox" id="smsTemplate_createdTime_start"
		data-options="showSeconds:false,formatter:myformatter" name="createdTime"
		style="width: 130px">---<input class="easyui-datebox" name="createdTime"
		id="smsTemplate_createdTime_end" data-options="showSeconds:false,formatter:myformatter"
		style="width: 130px">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true" onclick="smsTemplateQuery();">查询</a>
</div>

<!-- 新增和修改的弹出框 -->
<div id="smsTemplateWindow" class="easyui-window"
	style="width:540px; height: 420px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
<!-- 查看模板的弹出框 -->
<div id="smsTemplateInfosWindow" class="easyui-window"
	style="width: 650px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
<!-- 选择所属类型的信息  -->
<div id="infoTypeWindow" class="easyui-window"
	style="width: 540px; height: 420px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>