<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<style>
	.table_2 tr td{
		width:20%;
	}
.input_1{
		  width: 165px;
		  height: 26px;
		  border-radius: 3px;
		  border: 1px solid #aaa;
}
</style>
<script type="text/javascript">

	//发送原手机短信验证码
	function getOldPhoneMesg(){
		var oldPhone = $("#oldPhone").val();
		if(oldPhone==null || oldPhone==""){
			alert("请输入手机号!");
			return;
		}
		
		$.ajax({
			type : 'GET',
			url : '${ctx}/pc/after/industryassociation/sendMsgForOldPhone?oldPhone='+oldPhone,
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
	//发送新短信验证码
	function getNewPhoneMesg(){
		var newPhone = $("#newPhone").val();
		if(newPhone==null || newPhone==""){
			alert("请输入手机号!");
			return;
		}
		
		$.ajax({
			type : 'GET',
			url : '${ctx}/pc/after/industryassociation/sendMsgForNewPhone?newPhone='+newPhone,
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
		//验证密码相同
		var newPhone = $('#newPhone').val();
		var newCode = $('#newCode').val();
		var oldPhone = $('#oldPhone').val();
		var oldCode = $('#oldCode').val();
		
			//提交表单
			$('#updatephoneform').form("submit", {
				success : function(data) {
					data = $.parseJSON(data);
					$.messager.show({
						title : '操作提示',
						msg : data.msg,
						showType : 'show'
					});
					if (data.code == 1) {
						$('#updateUserPhone').window('close');
					}
				}
			});
		
	} 
	$(function(){
        /* 发送短信倒计时 */
        $('.verification_4').click(function(){
            var time = 60;
            var s = time+1;
            var timer = null;
            function countDown(){
                s--;
                $('.verification_4').val('('+s+')正在发送');
                $('.verification_4').attr("disabled",true);
                $('.verification_4').css('background','#ccc');
                if(s==0){
                    clearInterval(timer);
                    $('.verification_4').val('重新发送');
                    $('.verification_4').attr("disabled",false);
                    $('.verification_4').css('background','#6ACF33');
                    s=time+1;
                }
              }
            countDown();
            timer = setInterval(countDown,1000);})
	});
	$(function(){
        /* 发送短信倒计时 */
        $('.verification_5').click(function(){
            var time = 60;
            var s = time+1;
            var timer = null;
            function countDown(){
                s--;
                $('.verification_5').val('('+s+')正在发送');
                $('.verification_5').attr("disabled",true);
                $('.verification_5').css('background','#ccc');
                if(s==0){
                    clearInterval(timer);
                    $('.verification_5').val('重新发送');
                    $('.verification_5').attr("disabled",false);
                    $('.verification_5').css('background','#6ACF33');
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
			<form id="updatephoneform" method="post" action="${ctx}/pc/after/industryassociation/update_phone">
				
		        <%-- <input id="roleId" type="hidden" name="industryAssociation.id" value="${industryAssociation.id}" /> --%>
					
				
						<table style="width:440px;  margin-top: 70px;margin-left: 34px;">
							<tr>
								<td align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">原手机号：</span></td>
								<td>
									<input class="input_1" readonly="readonly" id="oldPhone" name="oldPhone" value="${industryAssociation.phone}" />
									<input type="button" onclick="getOldPhoneMesg()" value="获取验证码" class="verification_4">
								</td>
							</tr>
							<tr>
								<td align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">验证码：</span></td>
								<td>
									<input class="input_1" id="oldCode" name="oldCode" value=""/>
								</td>
							</tr>
							<tr>
								<td align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">新手机号：</span></td>
								<td>
									<input class="input_1" id="newPhone" name="newPhone" value="" />
									<input type="button" onclick="getNewPhoneMesg()" value="获取验证码" class="verification_5">
								</td>
							</tr>
							<tr>
								<td align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">验证码：</span></td>
								<td>
									<input class="input_1" id="newCode" name="newCode" value=""/>
								</td>
							</tr>
						</table>
					
				
			</form>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="text-align: center; padding: 5px 0 5px;">
	
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submituserpassForm()">确定</a>
			
	</div> 
</div>
