<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	//提交表单
	function submitconstantTypeInputForm() {
		//提交表单
		$('#constantTypeInputForm').form("submit", {
			success : function(data) {
				data = $.parseJSON(data);
				$.messager.show({
					title : '操作提示',
					msg : data.msg,
					showType : 'show'
				});
				if (data.code == 1) {
					$('#constanttypeWindow').window('close');
					$('#constanttypeTable').datagrid("reload");
				}
			}
		});
	}

	function clearconstantTypeInputForm() {
		$('#constantTypeInputForm').form('reset');
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
			<form id="constantTypeInputForm" method="post"
				action="${ctx}/constanttype/constanttype-add">
				<input type="hidden" name="id" value="${constantType.id}" />
				
					<fieldset>
					<legend>
						<span class="legend">常 量 类 型 信 息</span>
					</legend>
						<table style="width:370px;">
							<tr>
								<td bgcolor="#ececec" width="33%" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">常量类型名称：</span></td>
								<td><input id="constantTypeinput_typeName"
									value="${constantType.typeName}" required="required"
									validType="length[1,30]" placeholder="请输入常量类型名称"
									invalidMessage="类型名称必须在1到30个字符之间，请重新输入"
									missingMessage="请输入常量类型名称" name="typeName"
									class="easyui-validatebox" type="text" style="width: 160px;" /></td>
							</tr>
							<tr>

								<td bgcolor="#ececec" width="100" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">常量类型描述：</span></td>
								<td>									
									<textarea id="constantTypeinput_typeDescription" name="typeDescription"  placeholder="请输入常量类型描述"
							  			rows="6" cols="35" style="width: 160px;">${constantType.typeDescription}</textarea>
									</td>

							</tr>
						</table>
					</fieldset>
				
			</form>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="submitconstantTypeInputForm()">确定</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearconstantTypeInputForm()">重置</a>
	</div>
</div>