$(function() {
	$('#memberScoreTable').DataTable({
		"sDom" : '<"">t<"F"ip>',
		"bServerSide" : true,
		"sPaginationType" : "full_numbers",
		//"bJQueryUI": true,
		//"sAjaxSource": "${ctx}/api/user/list", //ajax调用接口
		//<div class="btn-group" style="text-align:left;"><button type="button" class="btn btn-info dropdown-toggle"data-toggle="dropdown">操作 <span class="caret"></span></button>
		"sAjaxSource" : "/web/supplychainbalance/getsupplychainbalance", //ajax调用接口
		"aoColumnDefs" : [ {
			sDefaultContent : '',
			aTargets : [ '_all' ]
		} ],
		"aoColumns" : [
			{
				"sTitle" : "序号",
				"mData" : "seq"
			},
			{
				"sTitle" : "供链账号",
				"mData" : "telPhone"
			},
			{
				"sTitle" : "供链类型",
				"mData" : "enterpriseType"
			},
			{
				"sTitle" : "供链名称",
				"mData" : "enterpriseName"
			},
			{
				"sTitle" : "balanceId",
				"mData" : "balanceId",
				"bvisible" : false
			},
			{
				"sTitle" : "userId",
				"mData" : "userId",
				"bvisible" : false
			},
			{
				"sTitle" : "账户余额",
				"mData" : "score"
			},
			{
				"sTitle" : "在线充值",
				"mData" : "oncharge"
			}, {
				"sTitle" : "余额提现",
				"mData" : "drawcash"
			},
			{
				"sTitle" : "线下订单-服务费用",
				"mData" : "serviceCost"
			},
			{
				"sTitle" : "线下订单-订单结算",
				"mData" : "unlineSettle"
			},
			{
				"sTitle" : "线下订单-管理分润",
				"mData" : "unlineManageProfit"
			},
			{
				"sTitle" : "线上订单-订单结算",
				"mData" : "onlineSettle"
			},
			{
				"sTitle" : "线上订单-管理分润",
				"mData" : "onlineManageProfit"
			},
			{
				"sTitle" : "线上订单-供链分润",
				"mData" : "onlineChainProfit"
			}, {
				"sTitle" : "供链订单-订单结算",
				"mData" : "supplyChainSettle"
			},
			{
				"sTitle" : "供链订单-余额支付",
				"mData" : "supplyChainBalancePay"
			},
			{
				"sTitle" : "批发商订单-管理分润",
				"mData" : "hunterManageProfit"
			},
			{
				"sTitle" : "批发商订单-供链分润",
				"mData" : "hunterChainProfit"
			},
			{
				"sTitle" : "供链订单-订单退款",
				"mData" : "supplyChainRefund"
			},
			{
				"sTitle" : "平衡差",
				"mData" : "balanceDifference"
			},
			{
				"sTitle" : "状态",
				"mData" : "status",
				"mRender" : function ( data, type, full ) {
	            	 var text;
	            	 if(full.status == 0){
	            		 text = '<span style=\"color:black\">正常</span>';
	            	 }else if(full.status == 1){
	            		 text = '<span style=\"color:gray\">冻结</span>';
	            	 } else {
	            		 text = '<span style=\"color:gray\">未知支付状态</span>';
	            	 }
	            	 return text;
	                 }
			},
			{
				"sWidth" : "110px",
				"sTitle" : "操作",
				"mData" : "id",
				"sClass" : "ph_tableShow",
				"mRender" : function(data, type, full) {
					var text = '<div class="ph_operateBox"><input class="ph_operate" name="" type="button" value="操作选项">';
					text = text + '<ul class="ph_operateList hide" role="menu">';
					text += '<li><a href="javascript:void(0);" onclick="toBalanceDetail(\'' + full.userId + '\')">余额明细</a></li>';
					text += '<li><a href="javascript:void(0);" onclick="doFrozen(\'' + full.balanceId + '\',\'' + full.status + '\')">冻结/解冻</a></li>';
					text = text + '</ul></div>';
					return text;
				}
			}

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
					'enterpriseType' : $("#enterpriseType").val(),
					'status' : $("#status").val(),
					'telPhone' : $("#telPhone").val(),
					'enterpriseName' : $("#enterpriseName").val()
				},
				"success" : fnCallback
			});
		},
		"fnDrawCallback" : function() {
			var api = this.api();
			var startIndex = api.context[0]._iDisplayStart; //获取到本页开始的条数
			api.column(0).nodes().each(function(cell, i) {
				cell.innerHTML = startIndex + i + 1;
			});
		}
	});
});