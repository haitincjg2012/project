$(function(){
	$('#scoreDetail').DataTable({
		"sDom" : '<"">t<"F"ip>',
		"bServerSide" : true,
		"sPaginationType" : "full_numbers",
		//"bJQueryUI": true,
		//"sAjaxSource": "${ctx}/api/user/list", //ajax调用接口
		//<div class="btn-group" style="text-align:left;"><button type="button" class="btn btn-info dropdown-toggle"data-toggle="dropdown">操作 <span class="caret"></span></button>
		"sAjaxSource" : "/web/scoredetail/getscoredetail", //ajax调用接口
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
				"sTitle" : "会员账号",
				"mData" : "telPhone"
			},
			{
				"sTitle" : "会员类型",
				"mData" : "memberType",
				"mRender" : function(data, type, full) {
					var text;
					if (full.memberType == 1) {
						text = '<span style=\"color:black\">推广师</span>';
					} else if (full.memberType == 2) {
						text = '<span style=\"color:black\">会员</span>';
					} else {
						text = '<span style=\"color:gray\">未知会员类型</span>';
					}
					return text;
				}
			},
			{
				"sTitle" : "会员名称",
				"mData" : "memberName"
			},
			{
				"sTitle" : "来源",
				"mData" : "source"
			},
			{
				"sTitle" : "操作时间",
				"mData" : "createTime"
			},
			{
				"sTitle" : "积分",
				"mData" : "score"
			},
			{
				"sTitle" : "手续费",
				"mData" : "handingCharge"
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
					'memberType' : $("#memberType").val(),
					'transCode' : $("#userSource").val(),
					'telPhone' : $("#telPhone").val(),
					'memberId' :$("#memberId").val()
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