$(function(){
	$('#onLineSettle').DataTable({
		"sDom" : '<"">t<"F"ip>',
		"bServerSide" : true,
		"autoWidth": false, 
		"sPaginationType" : "full_numbers",
		//"bJQueryUI": true,
		//"sAjaxSource": "${ctx}/api/user/list", //ajax调用接口
		//<div class="btn-group" style="text-align:left;"><button type="button" class="btn btn-info dropdown-toggle"data-toggle="dropdown">操作 <span class="caret"></span></button>
		"sAjaxSource" : "/web/onlinesettle/queryonlinesettle", //ajax调用接口
		"aoColumnDefs" : [ {
			sDefaultContent : '',
			aTargets : [ '_all' ]
		} ],
		"aoColumns" : [
			{
				"sTitle" : "序号",
				"sWidth" : "80px",
				"mData" : "seq"
			},
			{
				"sTitle" : "订单号",
				"sWidth" : "240px",
				"mData" : "orderNo"
			},
			{
				"sTitle" : "订单时间",
				"sWidth" : "160px",
				"mData" : "createTime"
			},
			{
				"sTitle" : "收货时间",
				"sWidth" : "160px",
				"mData" : "doneTime"
			},
			{
				"sTitle" : "结算时间",
				"sWidth" : "160px",
				"mData" : "settleTime"
			},
			{
				"sTitle" : "订单金额",
				"sWidth" : "120px",
				"mData" : "orderMoney"
			},
			{
				"sTitle" : "结算金额",
				"sWidth" : "120px",
				"mData" : "totalSettlePrice"
			},
			{
				"sTitle" : "支付方式",
				"sWidth" : "100px",
				"mData" : "payType",
				"mRender" : function ( data, type, full ) {
	            	 var text;//0=微信支付；1=积分支付；2=支付宝支付；3=银行卡支付
	            	 if(full.payType == 0){
	            		 text = '<span style=\"color:black\">微信支付</span>';
	            	 }else if(full.payType == 1){
	            		 text = '<span style=\"color:black\">积分支付</span>';
	            	 } else if(full.payType == 2) {
	            		 text = '<span style=\"color:black\">支付宝支付</span>';
	            	 } else if(full.payType == 3) {
	            		 text = '<span style=\"color:black\">银行卡支付</span>';
	            	 } else {
	            		 text = '<span style=\"color:gray\">未知支付状态</span>';
	            	 }
	            	 return text;
	                 }
			},
			{
				"sTitle" : "会员账号",
				"sWidth" : "120px",
				"mData" : "memberTelPhone"
			},
			{
				"sTitle" : "会员名称",
				"sWidth" : "100px",
				"mData" : "memberName"
			},
			{
				"sTitle" : "供应商账号",
				"sWidth" : "120px",
				"mData" : "supplierTelPhone"
			},
			{
				"sTitle" : "供应商企业名称",
				"sWidth" : "220px",
				"mData" : "supplierName"
			},
			{
				"sTitle" : "结算状态",
				"sWidth" : "100px",
				"mData" : "status",
				"mRender" : function ( data, type, full ) {
	            	 var text;
	            	 if(full.status == 0){
	            		 text = '<span style=\"color:black\">待结算</span>';
	            	 }else if(full.status == 1){
	            		 text = '<span style=\"color:black\">已结算</span>';
	            	 } else {
	            		 text = '<span style=\"color:gray\">未知状态</span>';
	            	 }
	            	 return text;
	                 }
			},
		],
		"oLanguage" : {
			"sProcessing" : "数据加载中······",
			"sLengthMenu" : "显示 _MENU_ 条记录",
			"sZeroRecords" : "没有您要搜索的内容！",
			"sEmptyTable" : "列表中无数据存在！",
			"sInfo" : "当前显示 _START_ 到 _END_ 条数据，共 _TOTAL_ 条数据",
			"sInfoEmpty" : "显示 0 到 0 条记录",
			"sInfoFiltered" : "数据列表中共  _MAX_ 条记录",
			"oPaginate" : {
				"sFirst" : "首页",
				"sPrevious" : "上一页",
				"sNext" : "下一页",
				"sLast" : "末页"
			}
		},
		"fnServerData" : function(sSource, aoData, fnCallback, oSettings) {
			oSettings.jqXHR = $.ajax({
				"dataType" : 'json',
				"type" : "GET",
				"async" : 'false',
				"url" : sSource,
				"data" : {
					'pageNum' : (aoData[3].value / aoData[4].value) + 1,
					'pageSize' : aoData[4].value,
					'startDate' : $("#startDate").val(),
					'endDate' : $("#endDate").val(),
					'status' : $("#status").val(),
					'orderNo' : $("#orderNo").val(),
					'telPhone' : $("#telPhone").val()
				},
				"success" : fnCallback
			});
		},
		"fnDrawCallback" : function(){
        	var api = this.api();
      	　　var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
      	　　api.column(0).nodes().each(function(cell, i) {
      	　　　　cell.innerHTML = startIndex + i + 1;
      	　　});
        }
	});
});

//function doSearch(type) {
//	if(type == 1) {
//		$("#type").val(1);
//	}
//	if(type ==2) {
//		$("#type").val(2);
//	}
//	$('#onLineSettle').DataTable().draw();
//}
