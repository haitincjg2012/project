var ReadTime = function(){};

ReadTime.readTime = function(smsId) {
	//获取短信验证码
    var time = 60;
    var code = $("#" + smsId);
        code.addClass("disableCode");
        code.attr('disabled',true);
        code.css('cursor','default');
        var t = setInterval(function () {
            time--;
            code.val(time + "秒");
            if (time == 0) {
                clearInterval(t);
                code.val("重新获取");
                validCode = true;
                code.removeClass("disableCode");
                code.attr('disabled',false);
                code.css('cursor','pointer');
            }
        }, 1000);
}