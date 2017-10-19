<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<style>
<!--
.panel.window {  
    z-index: 1000 !important;  
}  

.window-shadow {  
    z-index: 999 !important;  
}  

.window-mask {  
    z-index: 998 !important;  
}  
-->
/* .control-label{
	float:left;
	  width: 87px;
	  text-align:right;
}
.controls{
	float:left;
}
.control-group{
	float:left;
}
.verification{
	float:left;
} */
.float_ll div{
	float:left;
}
.control-label{
	display: inline-block;
  width: 96px;
  text-align: right;
}
</style>
<div class="easyui-layout" data-options="fit:true">
 
<script type="text/javascript">
	 

	//发送提现手机短信验证码 
	function getPhoneMesg(){
		var tixianPhone = $("#tixianPhone").val();
		if(tixianPhone==null || tixianPhone==""){
			alert("请输入手机号!");
			return;
		}
		
		$.ajax({
			type : 'GET',
			url : '${ctx}/pc/after/cashreceivestation/sendMsgForTixianPhone?tixianPhone='+tixianPhone,
			dateType : "JSON",
			success : function(result) {
				var data = eval("(" + result + ")");
				var code = data.code;
				if (code == 1) {
				}else{
					alert(data.msg);
				}
			}
		});
	}



	//提交表单
	function submituserpassForm() {
		
		
			//提交表单
			$('#cashmoneyform').form("submit", {
				success : function(data) {
					data = $.parseJSON(data);
					$.messager.show({
						title : '操作提示',
						msg : data.msg,
						showType : 'show'
					});
					if (data.code == 1) {
						$('#openTiXian').window('close');
					}
				}
			});
		
	} 
	$(function(){
        /* 发送短信倒计时 */
        $('.verification_6').click(function(){
            var time = 60;
            var s = time+1;
            var timer = null;
            function countDown(){
                s--;
                $('.verification_6').val('('+s+')正在发送');
                $('.verification_6').attr("disabled",true);
                $('.verification_6').css('background','#ccc');
                if(s==0){
                    clearInterval(timer);
                    $('.verification_6').val('重新发送');
                    $('.verification_6').attr("disabled",false);
                    $('.verification_6').css('background','#6ACF33');
                    s=time+1;
                }
              }
            countDown();
            timer = setInterval(countDown,1000);})
	});
	
	
	
</script>
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
			<c:if test="${isBangCard==true }">
				<form id="cashmoneyform" method="post" style="  overflow: hidden;" action="${ctx}/pc/after/cashreceivestation/cashmoney">
					
			        <input id="industryAssociationId" type="hidden" name="industryAssociationId" value="${industryAssociation.id}" />
			        <input id="bankCardId" type="hidden" name="bankCardId" value="${bankCard.id}" />
			        	
			        	<div class="float_ll" style="margin-top:20px;">
			        		<span  style="color: red;display:block;margin-bottom:10px;margin-left:100px;">  单笔提现限额10元-5万元，每笔扣5元手续费</span> 
			        		<div class="control-group" style="margin-bottom:10px;">
								<label class="control-label" for="name">金额：</label>
									 <input type="text" style="width: 300px;" id="money" name="money"/>元
							</div>
							<div style="color:blue;margin-left:100px;margin-bottom:10px;float:left;">剩余可提现提现：<span style="color:red;">${bankCard.leftDepositMoney/100}</span>元</div>
			        		<div class="control-group" style="margin-bottom:10px;">
								<label class="control-label" for="login_code">银行卡号：</label>
									 <input type="text" style="width: 300px;" id="bankNo" readonly="readonly" name="bankNo" value="${bankCard.bankNo }" >
							</div>
							<div class="control-group" style="margin-bottom:10px;">
								<label class="control-label" for="prov">开户人姓名：</label>
									  <input type="text" style="width: 300px;" id="name" readonly="readonly" name="name" value="${bankCard.name }"  >
							</div>
							<div class="control-group" style="margin-bottom:10px;">
								<label class="control-label" for="prov">开户人身份证号：</label>
									  <input type="text" style="width: 300px;" id="card" readonly="readonly" name="card" value="${bankCard.card }"  >
							</div>
							<div class="control-group" style="margin-bottom:10px;">
								<label class="control-label" for="prov">开户银行：</label>
									  <input type="text" style="width: 300px;" id="bank" readonly="readonly" name="bank" value="${bankCard.bank }"  >
							</div>
					  		<div class="phonehde clearfix" style="margin-bottom:10px;">
					  			<label class="control-label" for="prov">提现手机号：</label>
					                   <input style="width: 220px;" id="tixianPhone" name="tixianPhone" value=""/>
										<input type="button" onclick="getPhoneMesg()" value="获取验证码" class="verification_6">
					         </div>
					         <div class="phonehde clearfix" style="margin-bottom:10px;">
					         	<label class="control-label" for="prov">验证码：</label>
					         		<input style="width:300px;" type="text" id="feephonecode" name="feephonecode" onBlur="checkfeephone();" placeholder="请输入验证码">
					         </div>
							
			        	
			        	</div>
						
				</form>
			
				<div data-options="region:'south',border:false" style="text-align: center; padding: 5px 0 5px;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submituserpassForm()">确定</a>
						
				</div> 
			</c:if>	
			<c:if test="${isBangCard==false }">
				<span style="display: block;font-size: 26px;margin-top: 100px;margin-bottom: 20px;margin-left: 130px;">未绑卡，请先绑卡</span>
			</c:if>
		</div>
	</div>
</div>

