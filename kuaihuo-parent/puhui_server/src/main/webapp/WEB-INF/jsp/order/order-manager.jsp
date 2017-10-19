<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">

	function roleQuery() {
		$('#orderManagerTable').datagrid("reload", {
			search_orderNo : $('#orderNo').val(),
			search_directPhone : $('#directPhone').val(),
			search_recommendPhone : $('#recommendPhone').val(),
			search_phone : $('#phone').val(),
			search_iPhone : $('#iPhone').val()
		});
	}

</script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<table id="orderManagerTable" class="easyui-datagrid" width="100%" border="0" fit="true"
			data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/order/order-manager-list',toolbar:'#orderManagerTool',pagination:true">
			<thead>
				<tr>
					<th data-options="field:'order_no'" align="center" width="8%">订单号</th>
					<th data-options="field:'directPhone'" align="center" width="7%">直营批发商账号</th>
					<th data-options="field:'recommendPhone'" align="center" width="7%">推荐批发商账号</th>
					<th data-options="field:'phone'" align="center" width="7%">用户账号</th>
					<th data-options="field:'iPhone'" align="center" width="7%">公会账号</th>
					<th data-options="field:'puhui'" align="center" width="7%">普惠平台账号</th>
					<th data-options="field:'lietou'" align="center" width="7%">批发商平台账号</th>
					<th data-options="field:'direct_hunter_fen',formatter: function(value,row,index){
											return (value/100).toFixed(2);
								}" align="center" width="7%">批发商直营收入</th>
					<th data-options="field:'recommend_hunter_fen',formatter: function(value,row,index){
											return (value/100).toFixed(2);
								}" align="center" width="7%">批发商推荐收入</th>
					<th data-options="field:'industry_fen',formatter: function(value,row,index){
											return (value/100).toFixed(2);
								}" align="center" width="7%">所属公会收入</th>
					<th data-options="field:'puhui_fen',formatter: function(value,row,index){
											return (value/100).toFixed(2);
								}" align="center" width="7%">普惠平台收入</th>
					<th data-options="field:'terrace_fen',formatter: function(value,row,index){
											return (value/100).toFixed(2);
								}" align="center" width="7%">批发商平台收入</th>
					<th data-options="field:'total_price',formatter: function(value,row,index){
											return (value/100).toFixed(2);
								}" align="center" width="7%">订单总价</th>
					<!-- <th data-options="field:'payType'" align="center" width="8%">支付类型</th> -->
				</tr>
			</thead>
		</table>
		<div id="orderManagerTool" style="padding: 5px; height: auto">
			<%-- <security:authorize ifAnyGranted="ROLE_ROLE_INPUT">
				<a href="javascript:void(0)"
					onclick="openroleWindow('添加角色','${ctx}/role/role-input','add')"
					class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			</security:authorize>
			<security:authorize ifAnyGranted="ROLE_ROLE_DELETE">
				<a href="javascript:void(0)" onclick="deleteroles()"
					class="easyui-linkbutton" iconCls="icon-remove" plain="true">批量删除</a>
			</security:authorize> --%>
			订单号：<input type="text" id="orderNo" value="${search_orderNo}"
				style="width: 80px"></input> 
			直营批发商账号：<input type="text"
				id="directPhone" value="${search_directPhone}" style="width: 80px"></input>
			推荐批发商账号：<input type="text"
				id="recommendPhone" value="${search_recommendPhone}" style="width: 80px"></input>
			用户账号：<input type="text"
				id="phone" value="${search_phone}" style="width: 80px"></input>
			公会账号：<input type="text"
				id="iPhone" value="${search_iPhone}" style="width: 80px"></input>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-search" plain="true" onclick="roleQuery();">查询</a>
		</div>

		<div id="roleWindow" class="easyui-window"
			style="width: 420px; height: 220px"
			data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
		</div>
	</div>
	<%-- <security:authorize ifAnyGranted="ROLE_ROLE_RESOURCE">
		<div region="east" style="width: 300px;" split="true">
			<div tools="#tt" class="easyui-panel" title="权限设置"
				style="padding: 10px;" fit="true" border="false" id="function-panel">

			</div>
		</div>
		<div id="tt" style="padding: 5px; height: auto"></div>
	</security:authorize> --%>
</div>

