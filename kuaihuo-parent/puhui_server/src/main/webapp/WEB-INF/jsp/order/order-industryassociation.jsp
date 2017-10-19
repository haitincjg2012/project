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

	//跳转到提现页面 
	/* function toTiXian() {
		$('#openTiXian').window({
			title : '绑定银行卡'
		});
		$('#openTiXian').window('open');
		$('#openTiXian').window('refresh',
				'${ctx}/pc/after/cashreceivestation/to_cash_money');
		 
	} */
</script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<table id="orderManagerTable" class="easyui-datagrid" border="0" fit="true"
			data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/order/order-industryassociation-list',toolbar:'#orderManagerTool',pagination:true">
			<thead>
				<tr>
					<th data-options="field:'order_no'" align="center" width="20%">订单号</th>
					<th data-options="field:'total_price',formatter: function(value,row,index){
											return value==null?0.00:(value/100).toFixed(2);
								}" align="center" width="7=20%">订单金额</th>
					<th data-options="field:'industry_fen',formatter: function(value,row,index){
											return value==null?0.00:(value/100).toFixed(2);
								}" align="center" width="20%">结算金额</th>
					<th data-options="field:'industry_after_money',formatter: function(value,row,index){
											return value==null?0.00:(value/100).toFixed(2);
								}" align="center" width="20%">结算总额</th>
					<th data-options="field:'receive_time'" align="center" width="20%">结算日期</th>
				</tr>
			</thead>
		</table>
		<div id="orderManagerTool" style="padding: 5px; height: auto">
			累计收入:<fmt:formatNumber value="${incomeAllMoney/100}" pattern="0.00" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			已提现:<fmt:formatNumber value="${haveDepositMoney/100}" pattern="0.00" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			可提现:<fmt:formatNumber value="${leftDepositMoney/100}" pattern="0.00" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- <button href="javascript:void(0)" onclick="toTiXian();">提现</button> -->
		</div>

		<div id="roleWindow" class="easyui-window"
			style="width: 420px; height: 220px"
			data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
		</div>
	</div>
</div>

<div id="openTiXian" class="easyui-window"
	style="width: 500px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
