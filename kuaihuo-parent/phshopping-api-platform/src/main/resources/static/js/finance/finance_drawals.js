//function auditPass(drawCashId, auditState, score, handingCharge, orderNo, bankNo, receiver, bankName, type) {
//	var auditData = {drawCashId:drawCashId, auditState:auditState, score:score, handingCharge:handingCharge, orderNo:orderNo,
//			bankNo:bankNo, receiver:receiver, bankName:bankName, type:type};
//	$.post("/web/userdrawcash/auditpass", auditData, function(data, textStatus, req) {
//		if (data.code == 90103) {
//			Dialog.alertInfo(data.message);
//			$('#userDrawCashTable').DataTable().draw();
//		} else {
//			Dialog.alertWarn(data.message);
//		}
//
//	}, 'json').error(function(data) {
//		Dialog.alertError("系统繁忙，请稍后再试!");
//	});
//}

function auditPass(drawCashId, auditState,orderNo, type) {
	var auditData = {drawCashId:drawCashId, auditState:auditState,orderNo:orderNo, type:type};
	$.post("/web/userdrawcash/auditpass", auditData, function(data, textStatus, req) {
		if (data.code == 90103) {
			Dialog.alertInfo(data.message);
			$('#userDrawCashTable').DataTable().draw();
		} else {
			Dialog.alertWarn(data.message);
		}

	}, 'json').error(function(data) {
		Dialog.alertError("系统繁忙，请稍后再试!");
	});
}