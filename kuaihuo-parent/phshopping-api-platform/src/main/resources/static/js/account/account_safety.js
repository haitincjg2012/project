function savePwd() {
	var type = $("#type").val();
	if(!type) {
		Dialog.alertWarn("系统繁忙,请联系开发人员");
		return;
	}
	
	var newPwd = $("#newPwd").val();
	var confirmPwd = $("#confirmPwd").val();
	if(!newPwd) {
		Dialog.alertWarn("请输入密码!");
		return;
	}
	if(newPwd.length < 6 ||　newPwd.length > 20) {
		Dialog.alertWarn("密码应该在6~20个字符之间!");
		return;
	}
	if(!confirmPwd) {
		Dialog.alertWarn("请再输入密码!");
		return;
	}
	if(newPwd != confirmPwd) {
		Dialog.alertWarn("两次输入密码不一致!");
		return;
	}
	
	var smsCode = $("#smsCode").val();
	if(!smsCode) {
		Dialog.alertWarn("请输入验证码!");
		return;
	}
	
	var params = $("#pwdForm").serializeJson();
	if(params.type == 1) {
		$.post("/web/safemanage/updateloginpwd",params,function(data){
			if(data.code == 200) {
				Dialog.toLogin("操作成功,请重新登录");
				Dialog.clearData("pwdForm");
			} else {
				Dialog.alertWarn(data.message);
			}
			
		},'json');
	} else if(params.type == 2) {
		$.post("/web/safemanage/updatepaypwd",params,function(data){
			if(data.code == 200) {
				Dialog.alertInfo("修改支付密码成功");
				Dialog.clearData("pwdForm");
			} else {
				Dialog.alertWarn(data.message);
			}
			
		},'json');
	} else {
		Dialog.alertError("系统内部错误，请联系开发人员!");
		return;
	}
//	
//	alert(JSON.stringify(params));
}

function setLoginType() {
	$("#type").val(1);
}

function setPayType() {
	$("#type").val(2);
}

//获取验证码
function getSmsCode() {
	var newPwd = $("#newPwd").val();
	var confirmPwd = $("#confirmPwd").val();
	if(!newPwd) {
		Dialog.alertWarn("请输入密码!");
		return;
	}
	if(newPwd.length < 6 ||　newPwd.length > 20) {
		Dialog.alertWarn("密码应该在6~20个字符之间!");
		return;
	}
	if(!confirmPwd) {
		Dialog.alertWarn("请再输入密码!");
		return;
	}
	if(newPwd != confirmPwd) {
		Dialog.alertWarn("两次输入密码不一致!");
		return;
	}
	
	Dialog.alertInfo("验证码已发送");
	
	ReadTime.readTime("smsId");
	
	var sourceCode;
	var roleCode = $("#roleCode").val();
	if(roleCode == 2) {
		sourceCode = "2";
	} else if(roleCode == 3 || roleCode ==4 || roleCode ==5) {
		sourceCode = "1";
	} else if(roleCode == 6) {
		sourceCode = "3";
	}
	
	var codeType;
	var type = $("#type").val();
	if(type == 1) {//登录密码
		codeType = "PH20170104";
	} else if(type == 2) {//支付密码
		codeType = "PH20170105";
	}
	var data = {"telPhone":$("#telPhone").val(),"smsCodeTypeCode":codeType,"sourceCode":sourceCode};
	$.post("/web/sms/sendSmsHaveCode", data, function(data, textStatus, req) {
		if(!data.success) {
			Dialog.alertWarn(data.message);
		}
	}, 'json');
}