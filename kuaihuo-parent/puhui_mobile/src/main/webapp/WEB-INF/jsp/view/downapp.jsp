<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>批发商商城</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
   	<link rel="stylesheet" type="text/css" href="${ctx}/css/base.css">
    <link rel="stylesheet" href="${ctx}/css/download.css"/>
    <script src="${ctx }/js/jquery-1.7.2.js"></script>
</head>

<body>
    <div class="download">
        <img src="${ctx}/img/download.jpg" alt=""/>
        <a class="android" href="javascript:;" target="_blank" onclick="androidlink()"></a>
        <a class="ios" href="javascript:;" onclick="open_1()"></a>
    </div>
    <div id="test"></div>
    <div id="dialog">
        <div class="message_1">
            <p class="msg">开发中 暂未开放</p>
            <div class="btn_33">
                <a class="queding_1" href="javascript:;" onclick="close_1()">确 定</a>
            </div>
        </div>
    </div>
    <script>
        (function(win,doc){
            var resize='orientationchange' in window?'orientationchange':'resize';

            function change(){
                var html=doc.documentElement;
                var deviceWidth = html.clientWidth;

                html.style.fontSize=deviceWidth/36+'px';
            }
            win.addEventListener(resize,change,false);
            win.addEventListener('load',change,false);
            doc.addEventListener('DOMContentLoaded',false);
        })(window,document);


        function open_1(){
            var s = document.getElementById("test");
            s.style.display = "block";

            var l = document.getElementById("dialog");
            l.style.display = "block";
        }
        function close_1(){
        	$(".msg").html("开发中 暂未开放");
            var s = document.getElementById("test");
            s.style.display = "none";

            var l = document.getElementById("dialog");
            l.style.display = "none";
        }
        
        function androidlink(){
        	var address ="${address.url}";
        	if(address==null||address==""){
        	    $(".msg").html("开发中 暂未开放");
        		open_1()
        	}else{
        	   if(isWeiXin()){
        		 $(".msg").html("当前浏览器不支持下载，请点击右上角选择其他浏览器下载");
        		 open_1();
        		 return;
        	   }
        		 window.location.href =address;
        	}
        }
        function isWeiXin(){
            var ua = window.navigator.userAgent.toLowerCase();
            if(ua.match(/MicroMessenger/i) == 'micromessenger'){
                return true;
            }else{
                return false;
            }
        }
    </script>
</body>
