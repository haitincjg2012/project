<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	function pushQuery() {
		$('#pushTable').datagrid("reload", {
			search_GTD_createdTime: $('#pushTime').datetimebox('getValue'),
			search_LTD_createdTime: $('#pushTime2').datetimebox('getValue'),
			search_EQ_state: $('#state').val(),
			search_EQ_type: $('#type').val(),
			search_LIKE_senderName: $('#senderName').val(),
			search_LIKE_receiveName: $('#receiveName').val()
		});
	}
	function deletePushs() {
		var rows = $('#pushTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的推送信息！', 'warning');
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
							'你确定要删除选中的推送信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/push/push-delete?id="
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
													$('#pushTable').datagrid("reload");
													$('#pushTable').datagrid('clearSelections');
												}
											});
								}
								$('#pushTable').datagrid('clearSelections');
							});
		}
	}
	

	function operateByFeed(val, row) {
		var rowId = row.id;
		return " <a onclick=\'searchByPushId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_detail.png\' alt=\'推送信息查看\' title=\'推送信息查看\' /></a> &nbsp; &nbsp; <a onclick=\'deleteByPushId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'推送信息删除\' title=\'推送信息删除\' />";
	}
	function searchByPushId(rowId){
		if(rowid==null){
			$.messager.alert('温馨提示', '请选择推送信息信息', 'warning');
		}else{
			$('#pushWindow').window({
				title : '查看推送信息信息'
			});
			$('#pushWindow').window('open');
			$('#pushWindow').window('refresh',
					'${ctx}/push/push-info?id=' + rowId);
			$('#pushTable').datagrid('clearSelections');
		}
	}
	function deleteByPushId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的推送信息信息！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的推送信息信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/push/push-delete?id="
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
													$('#pushTable').datagrid("reload");
													$('#pushTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#pushTable').datagrid('clearSelections');
	}
	function stateShow(val){
		if(val==2)
		{
			return '已接收';
		}
		else if(val==1)
		{
			return '已发送';
		}
		return '待发送';
	}
	function typeShow(val){
		if(val==2)
		{
			return '推送';
		}
		else if(val==1)
		{
			return '群发';
		}
		return '单发';
	}
	 function narrowPush(val){
		 if(val.length>18){
				return 	val=val.substring(0,18)+" ...";
			}else{
				return val;
			}
	 }
</script>

<table id="pushTable" class="easyui-datagrid" border="0"
	fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/push/push-list-data',toolbar:'#pushTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'pushKey'" width="110" align="center">用户Id</th>
			<th data-options="field:'senderName'" width="110" align="center">发送者</th>
			<th data-options="field:'receiveName'" width="110" align="center">接收者</th>
			<th data-options="field:'title'" width="110" align="center">标题</th>
			<th data-options="field:'content'" width="100" align="center" formatter="narrowPush">消息内容</th>
			<th data-options="field:'state'" width="40" align="center"
				formatter="stateShow">发送状态</th>
			<th data-options="field:'type'" width="40" align="center"
				formatter="type">发送类型</th>
				<th data-options="field:'createdTime'" width="80" align="center">消息时间</th>
			<th data-options="field:'deleteOne'" width="50" align='center'
				formatter="operateByFeed">操作</th>
		</tr>
	</thead>
</table>
<div id="pushTool" style="padding: 5px; height: auto">
		<a href="javascript:void(0)" onclick="deletePushs()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	

	
		推送信息时间：<input id="pushTime" 
		 	class="easyui-datetimebox" value="${search_GTD_createdTime}"
			style="width: 150px" />--
			<input id="pushTime2" 
		 	class="easyui-datetimebox" value="${search_LTD_createdTime}"
			style="width: 150px" />
	发送状态：<select id="state" name="state">
	<option value="">全部</option>
	<option value="0">待发送</option>
	<option value="1">已发送</option>
	<option value="2">已接收</option>
	   </select>
	  推送信息类型：<select id="type" name="type">
	<option value="">全部</option>
	<option value="0">单发</option>
	<option value="1">群发</option>
	<option value="2">推送</option>
	   </select>
	发送者：<input type="text" id="senderName" value="${search_LIKE_senderName}"
		style="width: 80px" />
	接收者：<input type="text" id="receiveName" value="${search_LIKE_receiveName}"
		style="width: 80px" />
		 <a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true" onclick="pushQuery();">查询</a>

</div>
<div id="pushWindow" class="easyui-window"
	style="width: 700px; height: 480px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>