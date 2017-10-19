<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>普惠批发商城后台管理系统</title>
    <link rel="stylesheet" href="${ctx}/css/base.css">
    <link rel="stylesheet" href="${ctx}/css/Denglu_1.css">
    <script src="${ctx}/js/jquery-1.7.2.js"></script>
</head>
<script>
function sendMsg(_obj){
	console.log("dfd");
	$.ajax({
		//url:"${ctx}/sms/sendMsg",
		url:"${ctx}/pc/view/code/sendMsg",
		data:"phone="+$("#j_username1").val(),
		type:"post",
		dataType:"json",
		success:function(data){
			console.log(data);
			if(data.code==1)
			{
				$(_obj).attr("disabled","disabled");
				time($(_obj));
				alert("发送成功");
			}else{
				alert(data.msg);
			}
		}
	});	
	
}
</script>
<body>

	<p id="toast" style="text-align: center; <c:choose>
	<c:when test="${param.error>=1}">display:block;</c:when>
	<c:otherwise>display:none;</c:otherwise>
</c:choose>
color: red;
background: black;
padding: 10px;
border-radius: 15px;
width: 200px;
margin: 30% 43% 5% 43%;
bottom: 0px;
opacity: 0.8;
position: absolute;">${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</p>
<input type="hidden" id="msg" value="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}" />
<input type="hidden" id='error1' value="${param.error}" />
<div  class="pushmima_1" <c:choose> <c:when test="${param.type!=2}">style="display:block;"</c:when><c:otherwise>style="display:none;"</c:otherwise></c:choose>>
  <form action="<c:url value='/login'/>" method="post" id="commonLogin" >
    	
    <div class="LeiTou_1">
        <div class="LeiTou_logo" style="margin-top: 50px;"></div>
	        <div class="LeiTou_2">
	            <div class="LeiTou_3">
	                <input class="LeiTou_31" name="j_username" type="text" placeholder="请输入您的用户名">
	                <img class="LeiTou_32" src="${ctx}/themes/login/LeiTou_03.jpg">
	            </div>
	            <div class="LeiTou_3">
	                <input class="LeiTou_31" name="j_password" type="password" placeholder="请输入您的密码">
	                <img class="LeiTou_32" src="${ctx}/themes/login/LeiTou_06.jpg">
	            </div>
	            <div class="LeiTou_4">
	                <input class="LeiTou_41" name="j_captcha" type="text" placeholder="请输入您的验证码">
	                <img class="LeiTou_42"  style="right:-2px;" src="<c:url value="/captcha/get-captcha" />" alt="" width="80"
								height="30" class="loginuserbutton"
								onclick="this.src='<c:url value="/captcha/get-captcha" />?rnd=' + Math.random();"
								alt="看不清请点击" />		
	            </div>
	            <div class="LeiTou_4">
				<input  type="hidden" name="j_type" value="1" />
					<!-- <a href="javascript:$('#commonLogin').css('display','none');$('#validateLogin').css('display','block');">用手机验证码登录</a> -->
				</div>
	        </div>
		<a class="yanzhengma" href="javascript:;">用手机验证码登录</a>
	
       <input type="submit" class="DengLuAn" value="登录">
     </div> 
     </div>
     </form> 
        <div class="pushmima" style="width:331px;margin:auto;<c:choose> <c:when test="${param.type==2}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>">
      	<form action="<c:url value='/login'/>" method="post" id="validateLogin" >  
		    
			        <div class="LeiTou_logo" style="margin-top: 50px;"></div>
				        <div class="LeiTou_2">
			    
						<div class="LeiTou_3">
			                <input class="LeiTou_31" name="j_username" id="j_username1" type="text" placeholder="请输入您的手机号!">
			                <img class="LeiTou_32" src="${ctx}/themes/login/LeiTou_03.jpg">
			            </div>
			            <div class="LeiTou_3">
					    	 <input type="text"  name="j_password" placeholder="验证码" id="j_password1" 
					    	                class="verification_1"> 
					        <input type="button"  value="获取验证码"  onclick="sendMsg(this)" class="verification_2">
			            </div>
			            <div class="LeiTou_4">
			                <input class="LeiTou_41" type="text" name="j_captcha" placeholder="请输入您的验证码">
			                <img class="LeiTou_42"  src="<c:url value="/captcha/get-captcha" />" alt="" width="80"
								height="30" class="loginuserbutton"
								onclick="this.src='<c:url value="/captcha/get-captcha" />?rnd=' + Math.random();"
								alt="看不清请点击" />
								<input  type="hidden" name="j_type" value="2" />
								<a class="yanzhengma_2" style="" href="javascript:;">用户名登录</a>
						        <button type="submit" class="DengLuAn"  >登录</button>
			            </div>
				</div>
		</form>
    </div>
    <style>
    .LeiTou_2 input,.pushmima input{
    	background:#fff;
    }
    	.LeiTou_3{
    		overflow: hidden;
    	}
    	.verification_1{
   		    width: 204px;
    		height: 46px;
    		padding-left: 24px;
    		border-bottom-left-radius: 50px;
    		border-top-left-radius: 50px;
    		border:none;
    		float:left;
    	}
    	.pushmima .verification_2{
   		    width: 100px;
    		height: 48px;
    		border:none;
    		float:left;
    		border-bottom-right-radius: 50px;
    		border-top-right-radius: 50px;
    		background:#ccc;}
    	.yanzhengma{
    		text-align:center;
    		color:red;
    		display: block;
    		font-size:20px;
    		padding-top:6px;
    		padding-bottom:12px;
    	}
    	.yanzhengma_2{
    		text-align:center;
    		color:red;
    		display: block;
    		font-size:20px;
    		padding-top:6px;
    		padding-bottom:12px;
    	}
    </style>
	<script>
		$(function(){
			var msg=$("#msg").val()
			if(msg!=""){
				if($("#error1").val()!=""){
					$("#toast").fadeOut(3000);  
				}
			}else{
				$("#toast").hide();  
			}
			
			$('.yanzhengma').click(function(){
				$('.pushmima_1').hide();
				$('.pushmima').show();
				$(this).hide();
				$('.yanzhengma_2').show();
                clearInterval(timer);
			});

			$('.yanzhengma_2').click(function(){
				$('.pushmima').hide();
				$('.pushmima_1').show();
				$(this).hide();
				$('.yanzhengma').show();
                clearInterval(timer);
                $('.verification_2').val('重新发送');
                $('.verification_2').attr("disabled",false);
                $('.verification_2').css('background','#6ACF33');
			});
			 /* 发送短信倒计时 */
            $('.verification_2').click(function(){
                var time = 60;
                var s = time+1;
                 timer = null;
                function countDown(){
                    s--;
                    $('.verification_2').val('('+s+')正在发送');
                    $('.verification_2').attr("disabled",true);
                    $('.verification_2').css('background','#ccc');
                    if(s==0){
                        clearInterval(timer);
                        $('.verification_2').val('重新发送');
                        $('.verification_2').attr("disabled",false);
                        $('.verification_2').css('background','#6ACF33');
                        s=time+1;
                    }
                  }
                countDown();
                timer = setInterval(countDown,1000);})
			
			
		});
	</script>
</body>
</html>