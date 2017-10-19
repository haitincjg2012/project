function auditPass(drawCashId, auditState, orderNo, type) {
	var data = {
		drawCashId : drawCashId,
		auditState : auditState,
		orderNo : orderNo,
		type : type
	};
	$.post("/web/memberdrawcash/auditpass", data, function(data, textStatus, req) {
		if (data.code == 90103) {
			Dialog.alertInfo(data.message);
			$('#memberDrawCashTable').DataTable().draw();
		} else {
			Dialog.alertWarn(data.message);
		}
	}, 'json').error(function(data) {
		Dialog.alertError("系统繁忙,请稍后再试");
	});
}