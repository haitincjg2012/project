<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function clearFeedBackForm() {
	$('#feedBackWindow').window('close');
}
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 650">
		<div style="padding: 10px;">
			<table style="width: 650px; ">
				<tr>
					<td>
						<fieldset name="fieldset26">
							<legend>
								<span class="legend">意 见 反 馈 信 息</span>
							</legend>
							<table>
								
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">反馈时间：</span>
									</td>
									<td><span style="color: red;font-size: 14px"><fmt:formatDate value="${feedback.createdTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">是否查看：</span>
									</td>
									<td>
									<c:if test="${feedback.isLook==0}">
									<span style="color: red;font-size: 14px">未查看</span>
									</c:if>
									<c:if test="${feedback.isLook==1}">
									<span style="color: red;font-size: 14px">已查看</span>
									</c:if>
									</td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">反馈标题：</span>
									</td>
									<td>
									<span style="color: red;font-size: 14px">
									${feedback.feedbackTitle}</span></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">反馈内容：</span>
									</td>
									<td>
									<textarea rows="6" cols="50" style="color: red; font-size: 14px;" id="feedbackcontent" readonly="readonly">${feedback.feedbackContent}</textarea>
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
			href="javascript:void(0)" onclick="clearFeedBackForm()">关闭窗口</a>
	</div>
</div>