<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="${ctx }/js/select2/css/select2.min.css">
<link rel="stylesheet" href="${ctx }/css/base.css">
<style>
	.table_1 tr{
		height:30px;
	}
</style>
<script type="text/javascript">



	

	//跳转绑定银行卡页面
	function toBangCard() {
		$('#openBangCard').window({
			title : '绑定银行卡'
		});
		$('#openBangCard').window('open');
		$('#openBangCard').window('refresh',
				'${ctx}/pc/after/industryassociation/to_bang_bankcard');
		 
	}
	
	//跳转修改银行卡页面
	function toUpdateBankCard() {
		$('#updateBangCard').window({
			title : '修改银行卡'
		});
		$('#updateBangCard').window('open');
		$('#updateBangCard').window('refresh',
				'${ctx}/pc/after/industryassociation/to_update_bankcard');
		 
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<c:if test="${code==2 }">
			<table class="table_1" style="width:300px;  margin: 50px 80px;">
					<tr>
						<td  align="right" width="30%"><span style="font-size: 13px; color: rgb(2, 48, 97);">开户人姓名：</span></td>
						<td>
							<span style="font-size: 13px; color: rgb(2, 48, 97);">${bankCard.name}</span>
						</td>
					</tr>
					<tr>
						<td  align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">身份证号：</span></td>
						<td>
							<span style="font-size: 13px; color: rgb(2, 48, 97);">${bankCard.card}</span>
						</td>
					</tr>			
					<tr>
						<td  align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">开户银行：</span></td>
						<td>
							<span style="font-size: 13px; color: rgb(2, 48, 97);">${bankCard.bank}</span>
						</td>
					</tr>	
					<tr>
						<td  align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">银行卡号：</span></td>
						<td>
							<span style="font-size: 13px; color: rgb(2, 48, 97);">${bankCard.bankNo}</span>
						</td>
					</tr>	
					<tr>
						<td></td>
						<td><button onclick="toUpdateBankCard();">修改银行卡</button></td>
					</tr>			
			</table>
		</c:if>
		<c:if test="${code==3 }">
			<div style="  width: 500px;height: 200px;border: 1px solid #000;text-align: center;margin: 60px;  border-radius: 16px;">
	        	<span style="display: block;font-size: 26px;margin-top: 50px;margin-bottom: 20px;">未绑卡，请先绑卡</span>
	        	<a style="font-size: 16px; text-decoration: underline;color:blue;" href="javascript:void(0)" onclick="toBangCard();">绑定银行卡</a>
        	</div>
        </c:if>
        <c:if test="${code==1 }">
        	<span style="text-align: center;display: block;font-size: 26px;margin-top: 140px;">您未进行身份认证</span>
        </c:if>
        <c:if test="${code==4 }">
        	<span style="text-align: center;display: block;font-size: 26px;margin-top: 140px;">查询信息有误，多条记录</span>
        </c:if> 
	</div>
	
</div> 


<div id="openBangCard" class="easyui-window"
	style="width: 500px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div> 

<div id="updateBangCard" class="easyui-window"
	style="width: 500px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div> 
