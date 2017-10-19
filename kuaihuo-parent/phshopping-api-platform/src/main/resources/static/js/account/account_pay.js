function onLinePay() {
	var flag = false;
	var score = $("#score").val();
	if (!score) {
		alert("充值金额不能为空");
		return;
	}
	if(isNaN(score)){
		alert("输入金额必须为整数数字");
		$("#score").val("");
		return;
	}
	$("#amount").val(score);
	$("#payForm").submit();

//	$.post('/web/useraccountmanager/insertuserchargerecord', {
//		score : score
//	}, function(data, textStatus, req) {
//
//		$("#orderNum").val(data.data.orderNum);
//		$("#amount").val(score);
//		$("#description").val(data.data.description);
//		$("#payForm").attr("action", data.data.payUrl);
//		flag = true;
//	//		$("#payForm").submit();
//	}, 'json');
//
//	var t = setInterval(function() {
//		if (flag) {
//			$("#payForm").submit();
//			window.clearInterval(t);
//		}
//	}, 1000)
}