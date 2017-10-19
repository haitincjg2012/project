<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">


	function clearconstantManagerinputform() {
		$('#constantmanagerWindow').window('close');
	}
	 
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 400px">
		<div style="padding: 10px;">
		<form id="constanttypeinfoform">
			<table style="width: 400px">
			<tr>
		<td>
			<fieldset>
				<legend>
					<span class="legend"> 常量信 息</span>
				</legend>
			<table>
				<tr>
					<td width="40%" height="28" align="right" bgcolor="#ececec">
					<span style="font-size: 15px; color: rgb(2, 48, 97);">中文名称：</span>
					</td>
					<td>
					<span style="color: red;font-size: 14px">${constantManager.chinaName}</span>
					</td>
				</tr>		
				<tr>
					<td width="40%" height="28" align="right" bgcolor="#ececec">
					<span style="font-size: 15px; color: rgb(2, 48, 97);">常量名称：</span>
					</td>
					<td>
					<span style="color: red;font-size: 14px">${constantManager.englishName}</span>
					</td>
				</tr>		
				<tr>
					<td width="40%" height="28" align="right" bgcolor="#ececec">
					<span style="font-size: 15px; color: rgb(2, 48, 97);">常量值：</span>
					</td>
					<td>
					<span style="color: red;font-size: 14px">${constantManager.constantValue}</span>
					</td>
				</tr>				
				<tr>
					<td width="40%" height="28" align="right" bgcolor="#ececec">
					<span style="font-size: 15px; color: rgb(2, 48, 97);">常量单位：</span>
					</td>
					<td>
					<span style="color: red;font-size: 14px">${constantManager.unit}</span>
					</td>
				</tr>		
				<tr>
					<td width="50%" height="28" align="right" bgcolor="#ececec">
					<span style="font-size: 15px; color: rgb(2, 48, 97);">是否放入内存：</span>
					</td>
					<td>
					<span style="color: red;font-size: 14px">
						<c:choose>
							<c:when test="${constantManager.isMemory ==0}">否</c:when>
							<c:otherwise>
								是
							</c:otherwise>
						</c:choose>
					</span>
					</td>
				</tr>		
               <tr>
				<td width="30%" height="28" align="right" bgcolor="#ececec">
				<span style="font-size: 15px; color: rgb(2, 48, 97);">创建时间：</span>
						</td>
				<td><span style="color: red;font-size: 14px"><fmt:formatDate value="${constantManager.createdTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</td>
				</tr>
					</table>
			</fieldset>
		</td>
	</tr>
			</table>
			</form>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearconstantManagerinputform()">关  闭</a>
	</div>
</div>