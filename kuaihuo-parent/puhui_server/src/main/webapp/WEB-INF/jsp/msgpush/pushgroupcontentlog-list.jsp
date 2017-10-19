<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	function pushGroupContentLogQuery() {
		$('#pushGroupContentLogTable').datagrid("reload", {
			search_GTD_pushSendTime: $('#pushGroupContentLogTime').datetimebox('getValue'),
			search_LTD_pushSendTime: $('#pushGroupContentLogTime2').datetimebox('getValue'),
			search_EQ_pushState: $('#pushState1').val(),
			search_EQ_state: $('#state2').val(),
			search_LIKE_pushReceiveName: $('#pushReceiveName1').val()
		});
	}
	function deletePushContentLogs() {
		var rows = $('#pushGroupContentLogTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的群推日志！', 'warning');
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
							'你确定要删除选中的群推日志吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/pushgroupcontentlog/pushgroupcontentlog-delete?id="
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
													$('#pushGroupContentLogTable').datagrid("reload");
													$('#pushGroupContentLogTable').datagrid('clearSelections');
												}
											});
								}
								$('#pushGroupContentLogTable').datagrid('clearSelections');
							});
		}
	}
	

	function operateByFeed(val, row) {
		var rowId = row.id;
		return " <a onclick=\'searchByPushContentLogId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_detail.png\' alt=\'群推日志查看\' title=\'群推日志查看\' /></a> &nbsp; &nbsp; <a onclick=\'deleteByPushContentLogId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'群推日志删除\' title=\'群推日志删除\' />";
	}
	function searchByPushContentLogId(rowId){
		if(rowid==null){
			$.messager.alert('温馨提示', '请选择群推日志信息', 'warning');
		}else{
			$('#pushGroupContentLogWindow').window({
				title : '查看群推日志信息'
			});
			$('#pushGroupContentLogWindow').window('open');
			$('#pushGroupContentLogWindow').window('refresh',
					'${ctx}/pushgroupcontentlog/pushgroupcontentlog-info?id=' + rowId);
			$('#pushGroupContentLogTable').datagrid('clearSelections');
		}
	}
	function deleteByPushContentLogId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的群推日志信息！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的群推日志信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/pushgroupcontentlog/pushgroupcontentlog-delete?id="
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
													$('#pushGroupContentLogTable').datagrid("reload");
													$('#pushGroupContentLogTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#pushGroupContentLogTable').datagrid('clearSelections');
	}
	function stateShow(val){
		if(val==2)
		{
			return '已接收';
		}
		else if(val==1)
		{
			return '已推送';
		}
		return '待推送';
	}
	function typeShow(val){
		if(val==1)
		{
			return '群推';
		}
		return '单推';
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
	 function narrowPushContentLog(val){
		 if(val.length>18){
				return 	val=val.substring(0,18)+" ...";
			}else{
				return val;
			}
	 }
</script>

<table id="pushGroupContentLogTable" class="easyui-datagrid" border="0"
	fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/pushgroupcontentlog/pushgroupcontentlog-list-data',toolbar:'#pushgroupcontentlogTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'pushReceiveName'" width="110" align="center">接收者</th>
			<th data-options="field:'content'" width="100" align="center" formatter="narrowPushContentLog">群推内容</th>
			<th data-options="field:'pushState'" width="60" align="center"
				formatter="stateShow">群推状态</th>
			<th data-options="field:'state'" width="60" align="center"
				formatter="typeShow">群推类型</th>
			<th data-options="field:'pushType'" width="60" align="center"
				formatter="msgTypeShow">内容类型</th>
			<th data-options="field:'stepWatch'" width="80" align="center" formatter="stepWatchShow">延迟秒数</th>
			<th data-options="field:'pushSendTime'" width="80" align="center">群推时间</th>
			<th data-options="field:'deleteOne'" width="50" align='center'
				formatter="operateByFeed">操作</th>
		</tr>
	</thead>
</table>
<div id="pushgroupcontentlogTool" style="padding: 5px; height: auto">
		<a href="javascript:void(0)" onclick="deletePushContentLogs()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	

	
		群推日志时间：<input id="pushGroupContentLogTime" 
		 	class="easyui-datetimebox" value="${search_GTD_pushSendTime}"
			style="width: 150px" />--
			<input id="pushGroupContentLogTime2" 
		 	class="easyui-datetimebox" value="${search_LTD_pushSendTime}"
			style="width: 150px" />
	群推状态：<select id="pushState" name="pushState1">
	<option value="">全部</option>
	<option value="0">待群推</option>
	<option value="1">已群推</option>
	<option value="2">已接收</option>
	   </select>
	  群推日志类型：<select id="state1" name="state2">
	<option value="">全部</option>
	<option value="0">单推</option>
	<option value="1">群推</option>
	   </select>
	接收者：<input type="text" id="pushReceiveName1" value="${search_LIKE_pushReceiveName}"
		style="width: 80px" />
		 <a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true" onclick="pushGroupContentLogQuery();">查询</a>

</div>
<div id="pushGroupContentLogWindow" class="easyui-window"
	style="width: 700px; height: 480px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>