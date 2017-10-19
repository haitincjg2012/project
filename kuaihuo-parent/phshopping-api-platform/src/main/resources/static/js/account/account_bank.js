$(function() {
	var bankName = $("#bankName").val();
	if (!bankName) {
		//		$("#cardNo").attr("disabled", false);
		$("#saveCard").show();
		$("#disCard").hide();
	} else {
		$("#saveCard").hide();
		$("#disCard").show();
	}

});

function unbind() {
//	$("#cardNo").attr("disabled", false);
	
	var verificationCode = $("#verificationCode").val();
	if (!verificationCode) {
		Dialog.alertWarn("请输入验证码!");
		return;
	}

	var params = $("#bankForm").serializeJson();
	$.ajax({
		url : '/web/bankcard/unbindcard',
		type : 'post',
		data : params,
		dataType : 'json',
		success : function(result) {
			if (result.code == 200) {
				Dialog.alertRefreshInfo(result.message);
			} else {
				Dialog.alertWarn(result.message);
			}
		},
		error : function(wrong) {
			Dialog.alertError("系统繁忙,请稍后再试!");
		}
	});
}

function saveOrEditCard() {
	var ownerName = $("#ownerName").val();
	if (!ownerName) {
		Dialog.alertWarn("账户名不能为空!");
		return;
	}

	var idCardNo = $("#idCardNo").val();
	if (!idCardNo) {
		Dialog.alertWarn("身份证号不能为空!");
		return;
	}

	var cardNo = $("#cardNo").val();
	if (!cardNo) {
		Dialog.alertWarn("银行卡号不能为空!");
		return;
	}

	var bankName = $("#bankName").val();
	if (!bankName) {
		Dialog.alertWarn("银行名称不能为空!");
		return;
	}


	var verificationCode = $("#verificationCode").val();
	if (!verificationCode) {
		Dialog.alertWarn("请输入验证码!");
		return;
	}

	var params = $("#bankForm").serializeJson();
	params["bankName"] = bankName;

	$.ajax({
		url : '/web/bankcard/bindcard',
		type : 'post',
		data : params,
		dataType : 'json',
		success : function(result) {
			if (result.code == 200) {
				Dialog.alertRefreshInfo(result.message);
			} else {
				Dialog.alertWarn(result.message);
			}
		},
		error : function(result) {
			Dialog.alertError("系统繁忙,请稍后再试!");
		}
	});
}
//校验银行名称
function findBankName() {
	var cardNo = $("#cardNo").val();
	if (!cardNo) {
		return;
	}

	$("#bankName").attr('placeholder', '正在查询对应的银行名称....');

	var params = $("#bankForm").serializeJson();
	$.ajax({
		url : '/web/bankcard/getbankname',
		type : 'post',
		data : params,
		dataType : 'json',
		success : function(result) {
			if (result.code == 200) {
				$("#bankName").val(result.data.bankName);
				$("#bankCodenameDataId").val(result.data.id);
			} else {
				Dialog.alertWarn(result.message);
				$("#bankName").attr('placeholder', '');
			}
		},
		error : function(wrong) {
			Dialog.alertError("系统繁忙,请稍后再试!");
			$("#bankName").attr('placeholder', '');
		}
	});
}

//获取验证码
function getSmsCode() {
	var ownerName = $("#ownerName").val();
	if (!ownerName) {
		Dialog.alertWarn("账户名不能为空!");
		return;
	}

	var idCardNo = $("#idCardNo").val();
	if (!idCardNo) {
		Dialog.alertWarn("身份证号不能为空!");
		return;
	}

	var cardNo = $("#cardNo").val();
	if (!cardNo) {
		Dialog.alertWarn("银行卡号不能为空!");
		return;
	}

	var bankName = $("#bankName").val();
	if (!bankName) {
		Dialog.alertWarn("银行名称不能为空!");
		return;
	}
	
	Dialog.alertInfo("验证码已发送");
	
	ReadTime.readTime("smsId");
	
	var sourceCode;
	var roleCode = $("#roleCode").val();
	if (roleCode == 2) {
		sourceCode = "2";
	} else if (roleCode == 3 || roleCode == 4 || roleCode == 5) {
		sourceCode = "1";
	} else if (roleCode == 6) {
		sourceCode = "3";
	}

	var codeType;
	var telPhone = $("#telPhone").val();
	var strType = $("#saveCard").val();
	if (strType == "绑定") {
		codeType = "PH20170102";
	} else {
		codeType = "PH20170103";
	}
	var data = {
		"telPhone" : $("#telPhone").val(),
		"smsCodeTypeCode" : codeType,
		"sourceCode" : sourceCode
	};
	$.post("/web/sms/sendSmsHaveCode", data, function(data, textStatus, req) {
		if (!data.success) {
			Dialog.alertWarn(data.message);
		}
	}, 'json');
}