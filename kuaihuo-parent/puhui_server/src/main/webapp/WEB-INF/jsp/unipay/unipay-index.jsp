<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	/* 编写脚本 */
	
	/* 提交支付表单 */
	function submitUnipayForm(){
		$("#unipayform").submit();
	}
</script>
<form id="unipayform" method="post"
	action="${ctx}/pay/tounipay" target="_blank" >
	<fieldset>
		<legend>
			<span class="legend">输入金额 </span>
		</legend>
		<table>
			<tr>
				<td width="32%" bgcolor="#ececec" align="right"><span
					style="font-size: 13px; color: rgb(2, 48, 97);">输入金额：</span></td>

				<td><input id="paynum" value=""
					required="required" 
					name="paynum" class="easyui-validatebox" type="text"
					style="width: 180px;" /></td>
			</tr>
			<tr>
				<td width="32%" colspan="1" >
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
				 href="javascript:void(0)" onclick="submitUnipayForm()">去银联支付</a>
				</td>
			</tr>
		</table>
	</fieldset>
</form>