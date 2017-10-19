//跳转到余额明细
function toBalanceDetail(userId) {
	window.location.href = "/web/balancedetail/tobalancedetail?userId=" + userId;
}

function doFrozen(balanceId, status) {
	var flag;
	if(status == 0) {
		flag = 1;
	}
	if(status == 1) {
		flag = 0;
	}
	var data = {balanceId:balanceId,flag:flag};
	//更新余额状态
	$.post("/web/supplychainbalance/dofrozen", data, function(data, textStatus, req) {
		if(data.code == 200) {
			Dialog.alertInfo(data.message);
			$("#supplyChainBalance").DataTable().draw();
		} else {
			Dialog.alertWarn(data.message);
		}
	}, 'json')
	.error(function() {
		Dialog.alertError(data.message);
	});
}