<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//提交表单
	function submitEmailTemplateForm() {
		//提交表单
		$('#smsTemplateForm').form("submit", {
			success : function(data) {
				data = $.parseJSON(data);
				$.messager.show({
					title : '操作提示',
					msg : data.msg,
					showType : 'show'
				});
				if (data.code == 1) {
					$('#smsTemplateWindow').window('close');
					$('#smsTemplateTable').datagrid("reload");
					$('#smsTemplateTable').datagrid('clearSelections');
				}
			}
		});
	}
	

	function clearEmailTemplateForm() {
		//$('#infomodelform').form('reset');
		$('#smsTemplateWindow').window('close');
	}
	
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();

		var h = date.getHours(); //hour  
		var n = date.getMinutes(); //minute  
		var s = date.getSeconds();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d) + ' ' + (h < 10 ? ('0' + h) : h)
				+ ':' + (n < 10 ? ('0' + n) : n) + ':' + '01';
	}
	
	function selectInfoType(){
		$('#infoTypeWindow').window({
			title : '选择模板类型'
		});
		$('#infoTypeWindow').window('open');
		$('#infoTypeWindow').window('refresh', '${ctx}/infotype/window-infotype-list');
	}
	function getSelInfoType(infoTypeEnglishName,infoTypeName){
		$("#smsEnglishName").val(infoTypeEnglishName);
		 $("#smsInfoTypeName").val(infoTypeName);
	}   

</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 530px">
		<div style="padding: 10px;">
			<form id="smsTemplateForm" method="post" action="${ctx}/smstemplate/smstemplate-add" >
				<input type="hidden" name="id" value="${smsTemplate.id}" />
				<input type="hidden" name="smsEnglishName" id="smsEnglishName" value="${emailTemplate.smsEnglishName}">
				<table style="width: 500px">
				<tr>
		<td>
			<fieldset>
				<legend>
					<span class="legend">短 信 模 板 信 息</span>
				</legend>
			<table>
					<tr>
						<td width="25%" align="right"  bgcolor="#ececec">
						<span style="font-size: 13px; color: rgb(2, 48, 97);">短信模板标题：</span></td>
						<td><input id="smsTitle" value="${smsTemplate.smsTitle}"
							required="required" validType="length[3,100]" placeholder="请输入短信模板标题"
							invalidMessage="短信模板标题必须在3到100之间，请重新输入" missingMessage="请输入短信模板标题"
							name="smsTitle" class="easyui-validatebox" type="text"
							style="width: 280px;" /><span style="color: red;">*</span></td>
					</tr>
					<tr>
						<td width="25%" align="right"  bgcolor="#ececec">
						<span style="font-size: 13px; color: rgb(2, 48, 97);">短信变量标签：</span></td>
						<td><input id="smsVariableTags" value="${smsTemplate.smsVariableTags}"
							required="required" validType="length[3,100]" placeholder="请输入短信变量标签"
							invalidMessage="短信变量标签必须在3到100之间，请重新输入" missingMessage="请输入短信变量标签"
							name="smsVariableTags" class="easyui-validatebox" type="text"
							style="width: 280px;" /><span style="color: red;">*</span></td>
					</tr>
					<tr>
						<td width="25%" align="right"  bgcolor="#ececec">
						<span style="font-size: 13px; color: rgb(2, 48, 97);">所属模板类型：</span></td>
						<td>
							<input id="smsInfoTypeName" value="${smsTemplate.smsInfoTypeName}"
							required="required" style="width: 180px;" onclick="selectInfoType();"
							missingMessage="请选择所属模板类型" placeholder="请选择所属模板类型"
							name="smsInfoTypeName" class="easyui-validatebox" type="text"/>
						</td>
					</tr>
					<tr>
						<td width="25%" align="right"  bgcolor="#ececec">
						<span style="font-size: 13px; color: rgb(2, 48, 97);">模板内容：</span></td>
						<td>
							<textarea id="smsContent" name="smsContent" cols="45" rows="10">${smsTemplate.smsContent}</textarea>
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
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="submitEmailTemplateForm()">确  定</a> 
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearEmailTemplateForm()">取  消</a>
	</div>
</div>
