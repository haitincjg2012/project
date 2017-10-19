<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//提交表单
	function submitsensitivityForm() {
		//提交表单
		$('#sensitivityform').form("submit", {
			success : function(data) {
				data = $.parseJSON(data);
				$.messager.show({
					title : '操作提示',
					msg : data.msg,
					showType : 'show'
				});
				if (data.code == 1) {
					$('#sensitivityWindow').window('close');
					$('#sensitivityTable').datagrid("reload");
				}
			}
		});
	}

	function clearsensitivityForm() {
		$('#sensitivityform').form('reset');
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true"
		style="width: 300px; height: 100px">

		<div style="padding: 10px;">
			<form id="sensitivityform" method="post"
				action="${ctx}/sensitivity/sensitivity-add">
				<input type="hidden" name="id" value="${sensitivity.id}" />
				
				<fieldset>
				<legend>
					<span class="legend">敏 感 词 信 息</span>
				</legend>
					<table style="width:300px;">
						<tr>
							<td width="35%" bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">敏感词：</span></td>
							<td><input id="content" value="${sensitivity.name }"
								required="required" validType="length[1,50]"
								invalidMessage="敏感词必须在6到50之间，请重新输入" missingMessage="请输入内容"
								name="name" class="easyui-validatebox" type="text"
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
			href="javascript:void(0)" onclick="submitsensitivityForm()">确定</a> <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearsensitivityForm()">重置</a>
	</div>
</div>
