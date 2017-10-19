function getDrawCash() {
	var cardNo = $("#cardNo").val();
	if (!cardNo) {
		Dialog.alertWarn("请先绑定银行卡!");
		return;
	}
	
	var score = $("#score").val();
	var balance = $("#balance").text();

	if (!score) {
		Dialog.alertWarn("金额不能为空!");
		return;
	}

	if (score > parseFloat(balance)) {
		Dialog.alertWarn("余额不足!");
		return;
	}

	if (!Dialog.isInteger(score)) {
		Dialog.alertWarn("请输入整数金额!");
		return;
	} 

	var ownerName = $("#ownerName").val();
	var cardNo = $("#cardNo").val();
	var bankName = $("#bankName").val();

	var params = {
		drawCashScore : score,
		receiver : ownerName,
		bankNo : cardNo,
		bankName : bankName
	};

	var userId = $("#userId").val();

	var url = "/web/useraccountmanager/adduserdrawcashrecord";
	$.post(url, params, function(data, textStatus, req) {
		if (data.code == 200) {
			//跳转到账户余额页面
			location.href = "/web/useraccountmanager/touserbalancetradepage";
		}

		if (data.code != 200) {
			Dialog.alertWarn(data.message);
		}

	}, 'json')
}