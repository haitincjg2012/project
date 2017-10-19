$(function() {
	$('#balanceDetail').DataTable({
		"sDom" : '<"">t<"F"ip>',
		"bServerSide" : true,
		"sPaginationType" : "full_numbers",
		//"bJQueryUI": true,
		//"sAjaxSource": "${ctx}/api/user/list", //ajax调用接口
		//<div class="btn-group" style="text-align:left;"><button type="button" class="btn btn-info dropdown-toggle"data-toggle="dropdown">操作 <span class="caret"></span></button>
		"sAjaxSource" : "/web/balancedetail/getbalancedetaillist", //ajax调用接口
		"aoColumnDefs" : [ {
			sDefaultContent : '',
			aTargets : [ '_all' ]
		} ],
		"aoColumns" : [
			{
				"sTitle" : "序号",
				"mData" : "seq",
				"sWidth" : "50px"
			},
			{
				"sTitle" : "供链账号",
				"mData" : "telPhone",
				"sWidth" : "120px"
			},
			{
				"sTitle" : "企业类型",
				"mData" : "enterpriseType",
				"sWidth" : "90px"
			},
			{
				"sTitle" : "来源",
				"mData" : "source",
				"sWidth" : "230px"
			},
			{
				"sTitle" : "订单号",
				"mData" : "orderNo",
				"sWidth" : "250px"
			},
			{
				"sTitle" : "操作时间",
				"mData" : "createTime",
				"sWidth" : "160px"
			},
			{
				"sTitle" : "金额",
				"mData" : "score",
				"sWidth" : "90px"
			},
			{
				"sTitle" : "手续费",
				"mData" : "handingCharge",
				"sWidth" : "80px"
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
					'startDate' : $("#startDate").val(),
					'endDate' : $("#endDate").val(),
					'enterpriseType' : $("#enterpriseType").val(),
					'transCode' : $("#userSource").val(),
					'telPhone' : $("#telPhone").val(),
					'orderNo' : $("#orderNo").val(),
					'userId' : $("#userId").val()
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