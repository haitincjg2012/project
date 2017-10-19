<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px;">
		<div style="padding: 10px;">
				<fieldset>
				
					<legend>
						<span class="legend">个 人 信 息</span>
					</legend>
					<table style="width:380px;">
						<tr align="left"  class="black_text_k5">
							<td  width="40%" bgcolor="#ececec" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">用户名：</span></td>
							<td>${sessionScope.user.userName}</td>
						</tr>
						<tr>
							<td bgcolor="#ececec" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">真实姓名：</span></td>
							<td>${sessionScope.user.realName}</td>
						</tr>
						<tr>
							<td bgcolor="#ececec" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">角色名称：</span></td>
							<td>${sessionScope.user.role.descript}</td>
						</tr>
						<tr>
							<td bgcolor="#ececec" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">邮箱：</span></td>
							<td>${sessionScope.user.email}</td>
						</tr>
						<tr>
							<td bgcolor="#ececec" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">电话号码：</span></td>
							<td>${sessionScope.user.mobile}</td>
						</tr>
						<tr>
							<td bgcolor="#ececec" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">手机号码：</span></td>
							<td>${sessionScope.user.tel}</td>
						</tr>
						<tr>
							<td bgcolor="#ececec" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">联系地址：</span></td>
							<td>${sessionScope.user.address}</td>
						</tr>
						<tr>
							<td bgcolor="#ececec" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">性别：</span></td>
							<td><c:if test="${sessionScope.user.gender==1}">男</c:if> <c:if
									test="${sessionScope.user.gender==2}">女</c:if> <c:if
									test="${sessionScope.user.gender==0}">未知</c:if></td>
						</tr>
						<tr>
							<td bgcolor="#ececec" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">是否已激活：</span></td>
							<td><c:if test="${sessionScope.user.isLocked==0}">激活</c:if>
								<c:if test="${sessionScope.user.isLocked==1}">未激活</c:if></td>
						</tr>
					</table>
					</fieldset>
			
		</div>
	</div>
</div>
