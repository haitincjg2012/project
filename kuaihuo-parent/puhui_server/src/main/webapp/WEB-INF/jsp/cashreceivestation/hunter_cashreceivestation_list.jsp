<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
	

<script type="text/javascript">
	 
	 function userQuery() {
		$('#huntercashreceivestationTable').datagrid("reload", {
			search_phone : $('#phone').val(),
			search_merSeqId : $('#merSeqId').val(),
			search_startTime : $('#startTime').val(),
			search_endTime : $('#endTime').val()
		});
	} 
	//显示提现状态
	function viewStatus(val, row) {
		if (val == 1) {
			return "已申请 ";
		} else if (val == 2) {
			return "打款成功";
		} else if (val == 3) {
			return "打款失败";
		} 
	} 
</script>
<table id="huntercashreceivestationTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[10,20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/pc/after/cashreceivestation/hunter_cashreceivestation_list_data',toolbar:'#hunterTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'phone'" width="80" align="center">账号</th>
			<th data-options="field:'merSeqId'" width="80" align="center">提现订单号</th>
			<th data-options="field:'money',formatter: function(value,row,index){
											return value==null?0.00:(value/100).toFixed(2);
								}" align="center" width="80">提现金额</th>
			<th data-options="field:'feeIncome',formatter: function(value,row,index){
											return value==null?0.00:(value/100).toFixed(2);
								}" align="center" width="80">手续费</th>
			<th data-options="field:'haveDepositMoney',formatter: function(value,row,index){
											return value==null?0.00:(value/100).toFixed(2);
								}" align="center" width="80">已提现总额</th>
			<th data-options="field:'status'" width="80" align="center"
				formatter="viewStatus">订单状态</th>
			<th data-options="field:'createdTime'" width="80" align="center">订单创建时间</th>
		</tr>
	</thead>
</table>
<div id="hunterTool" style="padding: 5px; height: auto">
	
	 账号：<input type="text" id="phone" value="${search_phone}" style="width: 80px;margin-right: 20px;"></input>
	 提现订单号：<input type="text" id="merSeqId" value="${search_merSeqId}" style="width: 80px;margin-right: 20px;"></input>
	开始日期：<input  type="text" id="startTime" value="${search_startTime}" style="width: 120px;margin-right: 20px;" readonly/>
	 结束日期：<input type="text" id="endTime" value="${search_endTime}" style="width: 120px" readonly/>
	   
	 
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-search" plain="true" onclick="userQuery();">查询</a>
</div>  


<script type="text/javascript">
$(function(){
	try{
		
	$("#startTime").jeDate({
	    isinitVal:true,
	    festival:false,
	    ishmsVal:false,
	    minDate: '2016-06-16 23:59:59',
	    maxDate: $.nowDate(0),
	    format:"YYYY-MM-DD hh:mm:ss",
	    zIndex:3000,
	})
	$("#endTime").jeDate({
	    isinitVal:true,
	    festival:false,
	    ishmsVal:false,
	    minDate: '2016-06-16 23:59:59',
	    maxDate:$.nowDate(0),
	    format:"YYYY-MM-DD hh:mm:ss",
	    zIndex:3000,
	})
	}catch(err){
		console.log(err);
	}
})
	
</script>