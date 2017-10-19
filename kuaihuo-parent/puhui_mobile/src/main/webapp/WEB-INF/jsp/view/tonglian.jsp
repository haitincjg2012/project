<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通联表单提交页面</title>
</head>
<center> <h2>支付请求信息签名</h2></center>	
	
	<!--
		1、订单可以通过post方式或get方式提交，建议使用post方式；
		   提交支付请求可以使用http或https方式，建议使用https方式。
		2、通联支付网关地址、商户号及key值，在接入测试时由通联提供；
		   通联支付网关地址、商户号，在接入生产时由通联提供，key值在通联支付网关会员服务网站上设置。
	-->
	<center>
	<!--================= post 方式提交支付请求 start =====================-->
    <!--================= 测试地址为 http://ceshi.allinpay.com/gateway/index.do =====================-->
	<!--================= 生产地址请在测试环境下通过后从业务人员获取 (https://service.allinpay.com/gateway/index.do )=====================-->
	<form name="form2" action="http://ceshi.allinpay.com/gateway/index.do" method="post">
		<input type="hidden" name="inputCharset" value="${inputCharset}"/>
		<input type="hidden" name="pickupUrl" value="${pickupUrl}"/>
		<input type="hidden" name="receiveUrl" value="${receiveUrl}" />
		<input type="hidden" name="version" value="${version}"/>
		<input type="hidden" name="language" value="${language }" />
		<input type="hidden" name="signType" value="${signType}"/>
		<input type="hidden" name="merchantId" value="${merchantId}" />
		<%-- <input type="hidden" name="payerName" value="${payerName}"/>
		<input type="hidden" name="payerEmail" value="${payerEmail}" />
		<input type="hidden" name="payerTelephone" value="${payerTelephone }" />
		<input type="hidden" name="payerIDCard" value="${payerIDCard }" />
		<input type="hidden" name="pid" value="${pid}"/> --%>
		<input type="hidden" name="orderNo" value="${orderNo}" />
		<input type="hidden" name="orderAmount" value="${orderAmount}"/>
		<input type="hidden" name="orderCurrency" value="${orderCurrency}" />
		<input type="hidden" name="orderDatetime" value="${orderDatetime}" />
		<input type="hidden" name="orderExpireDatetime" value="${orderExpireDatetime}"/>
		<input type="hidden" name="ext1" value="${ext1}" />
		<%-- <input type="hidden" name="productName" value="${productName}" />
		<input type="hidden" name="productPrice" value="${productPrice}" />
		<input type="hidden" name="productNum" value="${productNum }"/>
		<input type="hidden" name="productId" value="${productId}" />
		<input type="hidden" name="productDesc" value="${productDesc}" />
		<input type="hidden" name="ext2" value="${ext2}" />
		<input type="hidden" name="pan" value="${pan }" />
		<input type="hidden" name="tradeNature" value="${tradeNature }" /> --%>
		<input type="hidden" name="payType" value="${payType}" />
		<%-- <input type="hidden" name="issuerId" value="${issuerId}" /> --%>
		<input type="hidden" name="signMsg" value="${strSignMsg}" />
		
	    <input type="submit" value="确认付款，到通联支付去啦"/>
	 </form>
	<!--================= post 方式提交支付请求 end =====================-->
	</center>
	<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
	<table border="1" cellpadding="1" cellspacing="1" align="center">
			<!-- 报文参数 -->
			<tr>
				<td width="40%">1. 编码:</td>
				<td> ${inputCharset}</td>
			</tr>
			<tr>
				<td>2. 取货地址:</td>
				<td> ${pickupUrl}</td>
			</tr>
			<tr>
				<td>3. 商户系统通知地址:</td>
				<td> ${receiveUrl}</td>
			</tr>
			<tr>
				<td>4. 报文版本号:</td>
				<td> ${version}</td>
			</tr>
			<tr>
				<td>5. 语言:</td>
				<td>${language }</td>
			</tr>
			<tr>
				<td>6. 签名方式:</td>
				<td>${signType}</td>
			</tr>
			<!-- 买卖双方信息参数 -->
			<tr>
				<td>7. 商户号:</td>
				<td> ${merchantId}</td>
			</tr>
			<tr>
				<td>8. 付款人名称:</td>
				<td> ${payerName}</td>
			</tr>
			<tr>
				<td>9. 付款人联系email:</td>
				<td> ${payerEmail}</td>
			</tr>
			<tr>
				<td>10. 付款人电话:</td>
				<td> ${payerTelephone}</td>
			</tr>
			<tr>
				<td>11. 付款人证件号:</td>
				<td><textarea cols="60" rows="1">${payerIDCard }</textarea></td>
			</tr>
			<tr>
				<td>12. 合作伙伴的商户号:</td>
				<td> ${pid }</td>
			</tr>
			<!-- 业务参数 -->
			<tr>
				<td>13. 商户系统订单号:</td>
				<td> ${orderNo }</td>
			</tr>
			<tr>
				<td>14. 订单金额(单位分):</td>
				<td> ${orderAmount}</td>
			</tr>
			<tr>
				<td>15. 金额币种:</td>
				<td> ${orderCurrency }</td>
			</tr>
			<tr>
				<td>16. 商户的订单创建时间:</td>
				<td> ${orderDatetime}</td>
			</tr>
			<tr>
				<td>17. 订单过期时间:</td>
				<td> ${orderExpireDatetime}</td>
			</tr>
			<tr>
				<td>18. 商品名称:</td>
				<td> ${productName}</td>
			</tr>
			<tr>
				<td>19. 商品单价:</td>
				<td> ${productPrice}</td>
			</tr>
			<tr>
				<td>20. 商品数量:</td>
				<td> ${productNum }</td>
			</tr>
			<tr>
				<td>21. 商品标识:</td>
				<td> ${productId}</td>
			</tr>
			<tr>
				<td>22. 商品描述:</td>
				<td> ${productDesc}</td>
			</tr>
			<tr>
				<td>23. 附加参数1:</td>
				<td> ${ext1 }</td>
			</tr>
			<tr>
				<td>24. 附加参数2:</td>
				<td> ${ext2 }</td>
			</tr>			
			<tr>
				<td>25. 支付方式:</td>
				<td> ${payType}</td>
			</tr>
			<tr>
				<td>26. 发卡行机构号:</td>
				<td> ${issuerId}</td>
			</tr>
			<tr>
				<td>27. 付款人支付卡号:</td>
				<td> <textarea cols="60" rows="1">${pan }</textarea></td>
			</tr>
			<tr>
				<td>28. 贸易类型:</td>
				<td>${tradeNature }</td>
			</tr>
			<tr>
				<td>29. 报文签名后内容:</td>
				<td> ${strSignMsg }</td>
			</tr>
	</table>
	<center>

	<div>&nbsp;</div>
	<div>组成签名信息的源串: <textarea cols="100" rows="4">${strSrcMsg}</textarea></div>
	</center>
	</body>
</html>