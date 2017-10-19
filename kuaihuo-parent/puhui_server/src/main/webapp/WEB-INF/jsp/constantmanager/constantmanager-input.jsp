<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	//提交表单
	function submitconstantinputform() {
		//提交表单
		$('#constantinputform').form("submit", {
			success : function(data) {
				data = $.parseJSON(data);
				$.messager.show({
					title : '操作提示',
					msg : data.msg,
					showType : 'show'
				});
				if (data.code == 1) {
					$('#constantmanagerWindow').window('close');
					$('#constantmanagerTable').datagrid("reload");
				}
			}
		});
	}

	function clearconstantinputform() {
		$('#constantinputform').form('reset');
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
			<form id="constantinputform" method="post"
				action="${ctx}/constantmanager/constantmanager-add">
				<input type="hidden" name="id" value="${constantManager.id}" />
				<input type="hidden" name="constantType.id" value="${constantTypeId}" />
				
					<fieldset>
					<legend>
						<span class="legend">常 量 信 息</span>
					</legend>
						<table style="width:370px;">
							<tr>
								<td bgcolor="#ececec" width="33%" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">中文名称：</span></td>
								<td><input id="constantinput_chinaName"
									value="${constantManager.chinaName}" required="required"
									validType="length[1,30]" placeholder="请输入中文名称"
									invalidMessage="中文名称必须在1到30个字符之间，请重新输入"
									missingMessage="请输入中文名称" name="chinaName"
									class="easyui-validatebox" type="text" style="width: 160px;" /></td>
							</tr>
							<tr>
								<td bgcolor="#ececec" width="100" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">常量名称：</span></td>
								<td><input id="constantinput_englishName"
									value="${constantManager.englishName}" required="required"
									validType="length[2,30]" placeholder="请输入常量名称"
									invalidMessage="常量名称必须在2到30个字符之间，请重新输入"
									missingMessage="请输入常量名称" name="englishName"
									class="easyui-validatebox" type="text" style="width: 160px;" /></td>
							</tr>
							<tr>
								<td bgcolor="#ececec" width="100" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">常量值：</span></td>
								<td><input id="constantinput_constantValue" placeholder="请输入常量值"
									value="${constantManager.constantValue}" required="required"
									validType="length[1,50]" style="width: 160px;"
									invalidMessage="常量值必须在1到50个字符之间，请重新输入" missingMessage="请输入常量值"
									name="constantValue" class="easyui-validatebox" type="text" /></td>

							</tr>
							<tr>
								<td bgcolor="#ececec" width="100" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">常量单位：</span></td>
								<td><input id="constantinput_unit" placeholder="请输入常量单位"
									value="${constantManager.unit}" required="required"
									validType="length[1,20]" style="width: 160px;"
									invalidMessage="常量单位必须在1到10个字符之间，请重新输入" missingMessage="请输入常量单位"
									name="unit" class="easyui-validatebox" type="text" /></td>
							</tr>
							<tr>
		               			<td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">是否放入内存：</span></td>
		                		<td>
					             	 <input id="isMemory" type="radio" name="isMemory" value="0" <c:if test="${constantManager.isMemory==0}">checked="checked"</c:if>/> <label for="one-top">否</label>
					             	 <input <c:if test="${constantManager.isMemory==null}">checked="checked"</c:if> id="isMemory" type="radio" name="isMemory" value="1" <c:if test="${constantManager.isMemory==1}">checked="checked"</c:if>/>
					             	 <label for="one-top">是</label>
		               			</td>
		          	  		</tr>
		          	  		<%-- <tr>
							<td align="right" bgcolor="#ececec"><span style="font-size: 13px; color: rgb(2, 48, 97);">所属类型：</span></td>
							<td><select id="constantType.id" name="constantType.id" style="width: 160px;">
									<c:forEach items="${constantTypes}" var="constantType">
										<option value="${constantType.id}"
											<c:if test="${constantType.id==constantManager.constantType.id }">selected="selected"</c:if>>${constantType.typeName}</option>
									</c:forEach>
							</select></td>
						</tr> --%>
						</table>
					</fieldset>
				
			</form>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="submitconstantinputform()">确定</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearconstantinputform()">重置</a>
	</div>
</div>