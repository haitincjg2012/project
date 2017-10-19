<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function clearPushContentLogForm() {
	$('#pushContentLogWindow').window('close');
}
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 650">
		<div style="padding: 10px;">
			<table style="width: 630px; ">
				<tr>
					<td>
						<fieldset name="fieldset26">
							<legend>
								<span class="legend">单 推 消 息 日 志 信 息</span>
							</legend>
							<table>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">推送类型：</span>
									</td>
									<td>
									<c:if test="${pushContentLog.state==0}">
									<span style="color: red;font-size: 14px">单推</span>
									</c:if>
									<c:if test="${pushContentLog.state==1}">
									<span style="color: red;font-size: 14px">群推</span>
									</c:if>
									</td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">推送状态：</span>
									</td>
									<td>
									<c:if test="${pushContentLog.pushState==0}">
									<span style="color: red;font-size: 14px">待推送</span>
									</c:if>
									<c:if test="${pushContentLog.pushState==1}">
									<span style="color: red;font-size: 14px">已推送</span>
									</c:if>
									<c:if test="${pushContentLog.pushState==2}">
									<span style="color: red;font-size: 14px">已接收</span>
									</c:if>
									</td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">接收人：</span>
									</td>
									<td>
									<span style="color: red;font-size: 14px">
									${pushContentLog.pushReceiveName}</span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">推送次数：</span>
									</td>
									<td>
									<span style="color: red;font-size: 14px">
									${pushContentLog.pushTimes==null?0:pushContentLog.pushTimes}</span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">延迟秒数：</span>
									</td>
									<td>
									<span style="color: red;font-size: 14px">
									${pushContentLog.stepWatch==null?0:pushContentLog.stepWatch/1000}s</span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">唯一标识：</span>
									</td>
									<td>
									<span style="color: red;font-size: 14px">
									${pushContentLog.uniqueKey}</span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">推送时间：</span>
									</td>
									<td><span style="color: red;font-size: 14px"><fmt:formatDate value="${pushContentLog.pushSendTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">推送内容：</span>
									</td>
									<td>
									<textarea rows="6" cols="50" style="color: red; font-size: 14px;" id="content" readonly="readonly">${pushContentLog.content}</textarea>
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearPushContentLogForm()">关闭窗口</a>
	</div>
</div>