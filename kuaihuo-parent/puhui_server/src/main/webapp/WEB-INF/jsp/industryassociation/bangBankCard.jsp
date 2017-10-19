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
.table_2 tr td{
		width:20%;
	}
.input_1{
		  width: 165px;
		  height: 26px;
		  border-radius: 3px;
		  border: 1px solid #aaa;
}
-->
</style>
<div class="easyui-layout" data-options="fit:true">
 
<script type="text/javascript">
	 
	 $(function(){
			 createBankInfo($("#bankName"));
		 
		      $("#bankName").on("change",function(){
				 var bankName =$("#select2-bankName-container").html();
				 $("input[name='bankName']").val(bankName);
			 }) 
	 })
	
	 



	//发送原手机短信验证码
	function getPhoneMesg(){
		var bangPhone = $("#bangPhone").val();
		if(bangPhone==null || bangPhone==""){
			alert("请输入手机号!");
			return;
		}
		
		$.ajax({
			type : 'GET',
			url : '${ctx}/pc/after/industryassociation/sendMsgForBangPhone?bangPhone='+bangPhone,
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
			$('#bangbankcardform').form("submit", {
				success : function(data) {
					data = $.parseJSON(data);
					$.messager.show({
						title : '操作提示',
						msg : data.msg,
						showType : 'show'
					});
					if (data.code == 1) {
						$('#openBangCard').window('close');
					}
				}
			});
		
	} 
	
	$(function(){
        /* 发送短信倒计时 */
        $('.verification_3').click(function(){
            var time = 60;
            var s = time+1;
            var timer = null;
            function countDown(){
                s--;
                $('.verification_3').val('('+s+')正在发送');
                $('.verification_3').attr("disabled",true);
                $('.verification_3').css('background','#ccc');
                if(s==0){
                    clearInterval(timer);
                    $('.verification_3').val('重新发送');
                    $('.verification_3').attr("disabled",false);
                    $('.verification_3').css('background','#6ACF33');
                    s=time+1;
                }
              }
            countDown();
            timer = setInterval(countDown,1000);})
	});
	
</script>
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
			<form id="bangbankcardform" method="post" action="${ctx}/pc/after/industryassociation/addbankcard">
				
		        <input id="industryAssociationId" type="hidden" name="industryAssociationId" value="${industryAssociation.id}" 
					<fieldset>
						
						<table style="  width: 440px;margin-top: 50px;margin-left: 34px;">
							<tr>
								<td align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">开户人姓名：</span></td>
								<td>
									<input class="input_1" readonly="readonly" id="payname" name="payname" value="${industryAssociation.payname}" />
								</td>
							</tr>
							<tr>
								<td  align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">身份证号：</span></td>
								<td>
									<input class="input_1" readonly="readonly" id="card" name="card" value="${industryAssociation.card}" />
								</td>
							</tr>
							<tr>
								<td align="right" width="30%"><span style="font-size: 13px; color: rgb(2, 48, 97);">银行卡号：</span></td>
								<td>
									<input class="input_1" id="bankNo" name="bankNo" value="" />
									
								</td>
							</tr>
							<tr>
								<td align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">开户银行：</span></td>
								<td >
									<select  class="form-control select2"  style="height:34px;  width: 167px; "    id="bankName" >
									
									</select> 
	                                
	                                <input class="input_1" type="hidden"  name="bankName" id="bankName_hidden" />  
	                                <span id="tishiName" style="color: red; display:none; float:right;"></span>
								</td>
								
								
								
							</tr>
							<tr>
								<td align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">手机号：</span></td>
								<td>
									<input class="input_1" id="bangPhone" name="bangPhone" value=""/>
									<input type="button" onclick="getPhoneMesg()" value="获取验证码" class="verification_3">
								</td>
							</tr>
							<tr>
								<td align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">验证码：</span></td>
								<td>
									<input class="input_1" id="phoneCode" name="phoneCode" value=""/>
								</td>
							</tr>
						</table>
					</fieldset>
				
			</form>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="text-align: center; padding: 5px 0 5px;">
	
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submituserpassForm()">确定</a>
			
	</div> 
</div>

