<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	//提交表单
	function submitroleForm() {
		//提交表单
		$('#roleform').form("submit", {
			success : function(data) {
				data = $.parseJSON(data);
				$.messager.show({
					title : '操作提示',
					msg : data.msg,
					showType : 'show'
				});
				if (data.code == 1) {
					$('#roleWindow').window('close');
					$('#roleTable').datagrid("reload");
				}
			}
		});
	}

	function clearroleForm() {
		$('#roleform').form('reset');
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
			<form id="roleform" method="post" action="${ctx}/role/role-add">
				<input type="hidden" name="id" value="${role.id}" />
				
				<fieldset>
				<legend>
					<span class="legend">角 色 信 息</span>
				</legend>
					<table>
						<tr>
							<td align="right" bgcolor="#ececec" width="45%"><span style="font-size: 13px;color: rgb(2, 48, 97)">角色名称：</span></td>
							<td><input id="rolename" value="${role.name}"
								required="required" validType="length[2,50]" placeholder="请输入角色名称"
								invalidMessage="角色名称必须在2到50之间，请重新输入" missingMessage="请输入角色名称"
								name="name" class="easyui-validatebox" type="text"
								style="width: 160px;" /></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec" ><span style="font-size: 13px;color: rgb(2, 48, 97)">角色描述：</span></td>
							<td><input id="descript" value="${role.descript}"
								required="required" validType="length[2,50]" placeholder="请输入角色描述"
								invalidMessage="角色名称必须在2到50之间，请重新输入" missingMessage="请输入角色描述"
								name="descript" class="easyui-validatebox" type="text"
								style="width: 160px;" /></td>
						</tr>
					</table>
					</fieldset>
					
			</form>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="submitroleForm()">确定</a> <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearroleForm()">重置</a>
	</div>
</div>