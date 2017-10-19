/*
 *模块：表单验证模块
 *时间：20170510
 *作者：刘弘愿
 */
$(function(){
    //文本框失去焦点后
    var passVal;//预留的密码全局变量
    var yzmVal='663023';//预留的验证码全局变量
    function checkBlur() {
        $('form').on('blur','input',function(){
            $parent=$(this).parent();

            //各输入框正则规范
            var phone=/^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;//预留手机号判断正则
            var password=/^(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{6,16}/;
            //验证用户名
            if( $(this).is('input[name="phone"]') ){
                if( this.value=="" || this.value.length < 11 ||( this.value!="" && !phone.test(this.value) )){
                    var errorMsg = '请输入正确的手机号';
                    $(this).css('border','1px solid red');
                    $parent.find('.loginErr').css('color','#ec5644');
                    $parent.find('.loginErr').text(errorMsg);
                }else{
                    var okMsg = '输入正确.';
                    $(this).css('border','1px solid #10c8ef');
                    $parent.find('.loginErr').css('color','#10c8ef');
                    $parent.find('.loginErr').text(okMsg);
                }
            }

            //验证密码
            if( $(this).is('input[name="password"]') ){

                if( this.value=="" || ( this.value!="" && !password.test(this.value) ) ){
                    var errorMsg = '字母、数字和符号组合，6-16位';
                    $(this).css('border','1px solid red');
                    $parent.find('.loginErr').css('color','#ec5644');
                    $parent.find('.loginErr').text(errorMsg);
                }else{
                    var okMsg = '输入正确';
                    $(this).css('border','1px solid #10c8ef');
                    $parent.find('.loginErr').css('color','#10c8ef');
                    $parent.find('.loginErr').text(okMsg);
                    passVal=$('input[name="password"]').val();
                    console.log(passVal);
                }
            }
            //验证码二次输入密码是否一致
            if( $(this).is('input[name="passwordConfirm"]') ){
                if( this.value=="" || ( this.value!="" && this.value!=passVal) ){
                    var errorMsg = '密码不一致，请重新输入';
                    $(this).css('border','1px solid red');
                    $parent.find('.loginErr').css('color','#ec5644');
                    $parent.find('.loginErr').text(errorMsg);
                }else{
                    var okMsg = '输入正确.';
                    $(this).css('border','1px solid #10c8ef');
                    $parent.find('.loginErr').css('color','#10c8ef');
                    $parent.find('.loginErr').text(okMsg);
                }
            }
            //验证码二次输入密码是否一致
            if( $(this).is('input[name="yzm"]') ){
                if( this.value=="" || ( this.value!="" && this.value!=yzmVal) ){
                    var errorMsg = '验证码输入错误';
                    $(this).css('border','1px solid red');
                    $parent.find('.loginErr').css('color','#ec5644');
                    $parent.find('.loginErr').text(errorMsg);
                }else{

                    var okMsg = '输入正确.';
                    $(this).css('border','1px solid #10c8ef');
                    $parent.find('.loginErr').css('color','#10c8ef');
                    $parent.find('.loginErr').text(okMsg);
                }
            }
        }).keyup(function(){
            $(this).triggerHandler("blur");
        }).focus(function(){
            $(this).triggerHandler("blur");
        });//end blur
    }
    checkBlur();
    $('input[type="button"]').click(function () {
        $('input').trigger('blur');
    })

});

