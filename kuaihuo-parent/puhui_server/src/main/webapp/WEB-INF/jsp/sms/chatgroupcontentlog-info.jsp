<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function clearChatGroupContentLogForm() {
	$('#chatGroupContentLogWindow').window('close');
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
								<span class="legend">群 聊 消 息 日 志 信 息</span>
							</legend>
							<table>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">消息类型：</span>
									</td>
									<td>
									<c:if test="${chatGroupContentLog.state==0}">
									<span style="color: red;font-size: 14px">单发</span>
									</c:if>
									<c:if test="${chatGroupContentLog.state==1}">
									<span style="color: red;font-size: 14px">群发</span>
									</c:if>
									<c:if test="${chatGroupContentLog.state==2}">
									<span style="color: red;font-size: 14px">推送</span>
									</c:if>
									</td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">发送人：</span>
									</td>
									<td>
									<span style="color: red;font-size: 14px">
									${chatGroupContentLog.smsSenderName}</span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">接收人：</span>
									</td>
									<td>
									<span style="color: red;font-size: 14px">
									${chatGroupContentLog.smsReceiveName}</span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">发送次数：</span>
									</td>
									<td>
									<span style="color: red;font-size: 14px">
									${chatGroupContentLog.times==null?0:chatGroupContentLog.times}</span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">间隔秒数：</span>
									</td>
									<td>
									<span style="color: red;font-size: 14px">
									${chatGroupContentLog.stepWatch==null?0:chatGroupContentLog.stepWatch/1000}s</span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">唯一标识：</span>
									</td>
									<td>
									<span style="color: red;font-size: 14px">
									${chatGroupContentLog.uniqueKey}</span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">消息时间：</span>
									</td>
									<td><span style="color: red;font-size: 14px"><fmt:formatDate value="${chatGroupContentLog.smsSendTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">消息内容：</span>
									</td>
									<td>
									<textarea rows="6" cols="50" style="color: red; font-size: 14px;" id="content" readonly="readonly">${chatGroupContentLog.content}</textarea>
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
			href="javascript:void(0)" onclick="clearChatGroupContentLogForm()">关闭窗口</a>
	</div>
</div>