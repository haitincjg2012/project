<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
    var list = '${feedBackTypeList}';
	function feedBackQuery() {
		$('#feedBackTable').datagrid("reload", {
			search_GTD_feedbackTime: $('#feedbackTime').datetimebox('getValue'),
			search_LTD_feedbackTime: $('#feedbackTime2').datetimebox('getValue'),
			search_EQ_isLook: $('#isLook').val(),
			search_EQ_feedBackTypeId: $('#feedBackType').val()
		});
	}
	function deleteFeedBacks() {
		var rows = $('#feedBackTable').datagrid('getChecked');
		if (rows == null || rows == '') {
			$.messager.alert('温馨提示', '请选择要删除的意见反馈！', 'warning');
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
							'你确定要删除选中的意见反馈吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/feedback/feedback-delete?id="
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
													$('#feedBackTable').datagrid("reload");
													$('#feedBackTable').datagrid('clearSelections');
												}
											});
								}
								$('#feedBackTable').datagrid('clearSelections');
							});
		}
	}
	

	function operateByFeed(val, row) {
		var rowId = row.id;
		return " <a onclick=\'searchByFeedBackId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_detail.png\' alt=\'意见反馈查看\' title=\'意见反馈查看\' /></a> &nbsp; &nbsp; <a onclick=\'deleteByFeedBackId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'意见反馈删除\' title=\'意见反馈删除\' />";
	}
	function searchByFeedBackId(rowId){
		if(rowid==null){
			$.messager.alert('温馨提示', '请选择意见反馈信息', 'warning');
		}else{
			$('#feedBackWindow').window({
				title : '查看意见反馈信息'
			});
			$('#feedBackWindow').window('open');
			$('#feedBackWindow').window('refresh',
					'${ctx}/feedback/feedback-search?id=' + rowId);
			$('#feedBackTable').datagrid('clearSelections');
		}
	}
	function deleteByFeedBackId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除的意见反馈信息！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的意见反馈信息吗？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/feedback/feedback-delete?id="
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
													$('#feedBackTable').datagrid("reload");
													$('#feedBackTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#feedBackTable').datagrid('clearSelections');
	}
	function show(val,row){
		if(val==0){
			return "<a onclick='changeFeedState("+row.id+","+val+")' style='cursor:pointer; color: green'>【<font color='red'>未查看</font>】</a>";
		}else if(val==1){
			return "<font style='color:green'>已查看</font>";
		}
	}
	 function changeFeedState(data,val){
			 $.messager.confirm('温馨提示','确定查看过了吗？',
						function(r) {
							if (r) {
					$.ajax({
						type : "POST",
						url : "${ctx}/feedback/feedback-changeState?id=" + data,
						success : function(data) {
							data = $.parseJSON(data);
							$.messager.show({
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
							$('#feedBackTable').datagrid("reload");
							$('#feedBackTable').datagrid('clearSelections');
						}
					});
				}
			});
			}
	 function narrowFeedBack(val){
		 if(val.length>18){
				return 	val=val.substring(0,18)+" ...";
			}else{
				return val;
			}
	 }
	 function feedTypeShow(val)
	 {
		return val.typeName;
	 }
</script>

<table id="feedBackTable" class="easyui-datagrid" border="0"
	fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/feedback/feedback-list-data',toolbar:'#feedbackTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'createdTime'" width="80" align="center">反馈时间</th>
			<th data-options="field:'feedbackTitle'" width="110" align="center">反馈标题</th>
			<th data-options="field:'feedbackContent'" width="100" align="center" formatter="narrowFeedBack">反馈内容</th>
			<th data-options="field:'feedBackType'" width="80" align="center" formatter="feedTypeShow">反馈类型</th>
			<th data-options="field:'isLook'" width="60" align="center"
				formatter="show">是否查看过</th>
			<th data-options="field:'deleteOne'" width="50" align='center'
				formatter="operateByFeed">操作</th>
		</tr>
	</thead>
</table>
<div id="feedbackTool" style="padding: 5px; height: auto">
		<a href="javascript:void(0)" onclick="deleteFeedBacks()"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
	

	
		反馈时间：<input id="feedbackTime" 
		 	class="easyui-datetimebox" value="${search_GTD_feedbackTime}"
			style="width: 150px" />--
			<input id="feedbackTime2" 
		 	class="easyui-datetimebox" value="${search_LTD_feedbackTime}"
			style="width: 150px" />
	是否查看过：<select id="isLook" name="isLook">
	<option value="">全部</option>
	<option value="0">未查看</option>
	<option value="1">已查看</option>
	   </select>
	 意见反馈类型：  <select id="feedBackType" name="feedBackType">
			         <option value="">全部</option>
			         <c:forEach items="${feedBackTypeList}" var="feedBackType">
				     <option value="${feedBackType.id}">${feedBackType.typeName}</option>
			</c:forEach>
		</select>
		
		 <a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true" onclick="feedBackQuery();">查询</a>

</div>
<div id="feedBackWindow" class="easyui-window"
	style="width: 700px; height: 480px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>