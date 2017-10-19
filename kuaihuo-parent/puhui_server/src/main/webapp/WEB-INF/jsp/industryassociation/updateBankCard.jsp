<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<style>

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
		  width: 160px;
		  height: 26px;
		  border-radius: 3px;
		  border: 1px solid #aaa;
	}
</style>

<script type="text/javascript">
	
	 	$(function(){
			createBankInfo($("#bankName"));//加载银行名称 
			//点击选中 
			 $("#bankName").on("change",function(){
				 var bankName =$(this).find("option:selected").text();//li标签中取值 
				// var bankName =$("#select2-bankName-container").html();//div 标签中取值 
				 
				 $("input[name='bankName']").val(bankName);
			 })
			 //回显银行名称 
			 $("#bankName").select2({'width':'162px', 'initSelection' : function (element, callback) {
					var bankName="${bankCard.bank}";
					var id=getIdFromListByText(bankName);
					console.log(id);
			        var data = {id: id, text:bankName};
			        $("input[name='bankName']").val(bankName);
			        callback(data);
			    }});
		}); 
 

	//修改银行卡发送手机短信验证码 
	function getPhoneMesg(){
		var updatePhone = $("#updatePhone").val();
		if(updatePhone==null || updatePhone==""){
			alert("请输入手机号!");
			return;
		}
		
		$.ajax({
			type : 'GET',
			url : '${ctx}/pc/after/industryassociation/sendMsgForUpdatePhone?updatePhone='+updatePhone,
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
			 $('#updatebankcardform').form("submit", {
				success : function(data) {
					data = $.parseJSON(data);
					$.messager.show({
						title : '操作提示',
						msg : data.msg,
						showType : 'show'
					});
					if (data.code == 1) {
						$('#updateBangCard').window('close');
					}
				}
			}); 
		
	} 
	$(function(){
        /* 发送短信倒计时 */
        $('.verification_2').click(function(){
            var time = 60;
            var s = time+1;
            var timer = null;
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
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
			<form id="updatebankcardform" method="post" action="${ctx}/pc/after/industryassociation/updatebankcard">
				
		        <input id="industryAssociationId" type="hidden" name="industryAssociationId" value="${industryAssociation.id}" />
		         <input id="bankCardId" type="hidden" name="bankCardId" value="${bankCard.id}" />
		         <input id="count" type="hidden" name="count" value="${count}" />
					<fieldset>
						<span style="color:red; text-align: center;display: block;font-size: 18px;margin-top: 20px;">提示：您已绑定过银行卡，每月只允许修改2次银行卡信息，本月已修改${count}次</span>
						<table class="table_2" style="width:440px;  margin-top: 20px;">
							<tr>
								<td align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">开户人姓名：</span></td>
								<td>
									<input class="input_1" readonly="readonly" id="payname" name="payname" value="${bankCard.name}" />
								</td>
							</tr>
							<tr>
								<td align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">身份证号：</span></td>
								<td>
									<input class="input_1" readonly="readonly" id="card" name="card" value="${bankCard.card}" />
								</td>
							</tr>
							<tr>
								<td align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">银行卡号：</span></td>
								<td>
									<input class="input_1" id="bankNo" name="bankNo" value="${bankCard.bankNo}" />
									
								</td>
							</tr>
							<tr>
								<td align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">开户银行：</span></td>
								<td >
									<select  class="form-control select2"  style="height:34px;  width: 167px; "    id="bankName" >
									
									</select> 
	                                
	                                <input type="hidden"  name="bankName" id="bankName_hidden" />  
	                                <span id="tishiName" style="color: red; display:none; float:right;"></span>
								</td>
							</tr>
							<tr>
								<td align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">手机号：</span></td>
								<td>
									<input class="input_1" id="updatePhone" name="updatePhone" value=""/>
									<input type="button" onclick="getPhoneMesg()" value="获取验证码" class="verification_2">
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
