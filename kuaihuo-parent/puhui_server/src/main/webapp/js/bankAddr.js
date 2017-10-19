/**
 * 
 */
var bankAddr=[
{"id":"ICBC","text":"中国工商银行"},
{"id":"ABC","text":"中国农业银行"},
{"id":"BOC","text":"中国银行"},
{"id":"CCB","text":"中国建设银行"},
{"id":"PSBC","text":"中国邮政储蓄银行"},
{"id":"CGB","text":"广发银行"},
{"id":"CEB","text":"中国光大银行"},
{"id":"BCM","text":"交通银行"},
{"id":"CMB","text":"招商银行"},
{"id":"CIB","text":"兴业银行"},
{"id":"PAB","text":"平安银行"},
{"id":"CITIC","text":"中信银行"},
{"id":"CMBC","text":"中国民生银行"},
{"id":"HXB","text":"华夏银行"},
{"id":"BOB","text":"北京银行"},
{"id":"BOS","text":"上海银行"},
{"id":"NBCB","text":"宁波银行"},
{"id":"HZB","text":"杭州银行"},
{"id":"GZCB","text":"广州银行"},
{"id":"JSB","text":"江苏银行"}];

function createBankInfo(_obj){
	try{
		
	$(_obj).select2({
		  data: bankAddr
	})
	}catch(err)
	{
		console.log(err);
	}
}

function getIdFromListByText(_txt){
	
	
	for (var int = 0; int < bankAddr.length; int++) {
		if(bankAddr[int].text==_txt)
		{
			console.log(bankAddr[int].id);
			return bankAddr[int].id;
		}
	}
	console.log("no_result");
	return "no_result";
}

//
try{
	albank();
}catch(err){
	
}
function albank(){
	var a=document.getElementById("bankName").value;
	for(var i=0;i<bankAddr.length;i++){
		if(bankAddr[i].text==a){
			var b=bankAddr[i].id;
			document.getElementById("bankAddr").value=b;
			break;
		}
	}
}
