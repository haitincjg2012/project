<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<div id="accountOrderTool" style="padding: 5px; height: auto">

	订单号：<input id="accountOrderNo"
			   class="easyui-validatebox"
			   style="width: 150px" name="orderNo" />


	结算时间	<input id="accountStart"
				   class="easyui-datebox"
				   style="width: 100px" />-
	<input id="accountEnd"
		   class="easyui-datebox"
		   style="width: 100px" />
	<a href="javascript:void(0)" class="easyui-linkbutton"
	   iconCls="icon-search" plain="true" onclick="orderAccountQuery();">查询</a>

</div>
<table id="accountTable" class="easyui-datagrid" border="0"
	fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,50],rownumbers:true,singleSelect:false,toolbar:'#accountOrderTool',url:'${ctx}/order/account-list',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'order_no'" width="110" align="center">订单号</th>
			<th data-options="field:'total_price'" width="110" align="center">订单总价</th>
			<%--<th data-options="field:'puhui'" width="100" align="center" formatter="puhui" >普惠账号</th>--%>
			<th data-options="field:'name'" width="110" align="center" >结算账号</th>
			<th data-options="field:'role'" width="110" align="center" >角色</th>
			<th data-options="field:'money'" width="110" align="center" >结算金额</th>
			<th data-options="field:'after_money'" width="110" align="center" >收入总额</th>
			<th data-options="field:'created_time',sortable:true" width="110" align="center" >结算时间</th>
		</tr>
	</thead>
</table>

<script type="text/javascript">
	
	function orderAccountQuery() {
        $('#accountTable').datagrid("reload", {
            startTime:$('#accountStart').datebox('getValue'),
            endTime:$('#accountEnd').datebox('getValue'),
            orderNo: $('#accountOrderNo').val()
        });
    }
	
</script>