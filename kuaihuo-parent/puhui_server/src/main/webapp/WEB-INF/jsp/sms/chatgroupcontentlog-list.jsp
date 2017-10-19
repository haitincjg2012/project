<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	function chatGroupContentLogQuery() {
		$('#chatGroupContentLogTable').datagrid("reload", {
			search_GTD_smsSendTime: $('#chatGroupContentLogTime').datetimebox('getValue'),
			search_LTD_smsSendTime: $('#chatGroupContentLogTime2').datetimebox('getValue'),
			search_EQ_state: $('#state1').val(),
			search_LIKE_smsSenderName: $('#smsSenderName1').val(),
			search_LIKE_smsReceiveName: $('#smsReceiveName1').val()
		});
	}
	function deleteChatGroupContentLogs() {
		var rows = $('#chatGroupContentLogTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的群聊消息日志！', 'warning');
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
							'你确定要删除选中的群聊消息日志吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/chatgroupcontentlog/chatgroupcontentlog-delete?id="
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
													$('#chatGroupContentLogTable').datagrid("reload");
													$('#chatGroupContentLogTable').datagrid('clearSelections');
												}
											});
								}
								$('#chatGroupContentLogTable').datagrid('clearSelections');
							});
		}
	}
	

	function operateByFeed(val, row) {
		var rowId = row.id;
		return " <a onclick=\'searchByChatGroupContentLogId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_detail.png\' alt=\'群聊消息日志查看\' title=\'群聊消息日志查看\' /></a> &nbsp; &nbsp; <a onclick=\'deleteByChatGroupContentLogId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'群聊消息日志删除\' title=\'群聊消息日志删除\' />";
	}
	function searchByChatGroupContentLogId(rowId){
		if(rowid==null){
			$.messager.alert('温馨提示', '请选择群聊消息日志信息', 'warning');
		}else{
			$('#chatGroupContentLogWindow').window({
				title : '查看群聊消息日志信息'
			});
			$('#chatGroupContentLogWindow').window('open');
			$('#chatGroupContentLogWindow').window('refresh',
					'${ctx}/chatgroupcontentlog/chatgroupcontentlog-info?id=' + rowId);
			$('#chatGroupContentLogTable').datagrid('clearSelections');
		}
	}
	function deleteByChatGroupContentLogId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的群聊消息日志信息！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的群聊消息日志信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/chatgroupcontentlog/chatgroupcontentlog-delete?id="
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
													$('#chatGroupContentLogTable').datagrid("reload");
													$('#chatGroupContentLogTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#chatGroupContentLogTable').datagrid('clearSelections');
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
	function msgTypeShow(val)
	{
		if(val==4)
		{
			return '视频';
		}
		else if(val==3)
		{
			return '语音';
		}
		else if(val==2)
		{
			return '图片';
		}
		return '普通';
	}
	function stepWatchShow(val)
	{
		if(val!=null&&val>0)
			return val/1000+'s';
		return '0s';
	}
	 function narrowChatGroupContentLog(val){
		 if(val.length>18){
				return 	val=val.substring(0,18)+" ...";
			}else{
				return val;
			}
	 }
	function timesShow(val)
	{
		if(val==null)
			return '0';
		return val;
	}
</script>

<table id="chatGroupContentLogTable" class="easyui-datagrid" border="0"
	fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/chatgroupcontentlog/chatgroupcontentlog-list-data',toolbar:'#chatgroupcontentlogTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'smsSenderName'" width="110" align="center">发送者</th>
			<th data-options="field:'smsReceiveName'" width="110" align="center">接收者</th>
			<th data-options="field:'content'" width="100" align="center" formatter="narrowChatGroupContentLog">消息内容</th>
			<th data-options="field:'state'" width="60" align="center"
				formatter="typeShow">发送类型</th>
			<th data-options="field:'msgType'" width="60" align="center"
				formatter="msgTypeShow">内容类型</th>
			<th data-options="field:'times'" width="60" align="center" formatter="timesShow">发送次数</th>
			<th data-options="field:'stepWatch'" width="80" align="center" formatter="stepWatchShow">延迟秒数</th>
			<th data-options="field:'smsSendTime'" width="80" align="center">发送时间</th>
			<th data-options="field:'deleteOne'" width="50" align='center'
				formatter="operateByFeed">操作</th>
		</tr>
	</thead>
</table>
<div id="chatgroupcontentlogTool" style="padding: 5px; height: auto">
		<a href="javascript:void(0)" onclick="deleteChatGroupContentLogs()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	

	
		群聊消息日志时间：<input id="chatGroupContentLogTime" 
		 	class="easyui-datetimebox" value="${search_GTD_smsSendTime}"
			style="width: 150px" />--
			<input id="chatGroupContentLogTime2" 
		 	class="easyui-datetimebox" value="${search_LTD_smsSendTime}"
			style="width: 150px" />
	  群聊消息日志类型：<select id="state1" name="state1">
	<option value="">全部</option>
	<option value="0">单发</option>
	<option value="1">群发</option>
	<option value="2">推送</option>
	   </select>
	发送者：<input type="text" id="smsSenderName1" value="${search_LIKE_smsSenderName}"
		style="width: 80px" />
	接收者：<input type="text" id="smsReceiveName1" value="${search_LIKE_smsReceiveName}"
		style="width: 80px" />
		 <a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true" onclick="chatGroupContentLogQuery();">查询</a>

</div>
<div id="chatGroupContentLogWindow" class="easyui-window"
	style="width: 700px; height: 480px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>