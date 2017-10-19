<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//提交表单
	function submitInfoTypeForm() {
		//提交表单
		$('#infoTypeForm').form("submit", {
			success : function(data) {
				data = $.parseJSON(data);
				$.messager.show({
					title : '操作提示',
					msg : data.msg,
					showType : 'show'
				});
				if (data.code == 1) {
					$('#infoTypeWindow').window('close');
					$('#infoTypeTable').datagrid("reload");
					$('#infoTypeTable').datagrid('clearSelections');
				}
			}
		});
	}
	

	function clearInfoTypeForm() {
		//$('#infomodelform').form('reset');
		$('#infoTypeWindow').window('close');
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

</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 530px">
		<div style="padding: 10px;">
			<form id="infoTypeForm" method="post" action="${ctx}/infotype/infotype-add" >
				<input type="hidden" name="id" value="${infoType.id}" />
				<table style="width: 500px">
				<tr>
		<td>
			<fieldset>
				<legend>
					<span class="legend">模 板  类 型 信 息</span>
				</legend>
			<table>
					<tr>
						<td width="25%" align="right"  bgcolor="#ececec">
						<span style="font-size: 13px; color: rgb(2, 48, 97);">模板类型名：</span></td>
						<td><input id="infoTypeName" value="${infoType.infoTypeName}"
							required="required" validType="length[3,100]" placeholder="请输入模板类型名"
							invalidMessage="模板类型名必须在3到100之间，请重新输入" missingMessage="请输入模板类型名"
							name="infoTypeName" class="easyui-validatebox" type="text"
							style="width: 280px;" /><span style="color: red;">*</span></td>
					</tr>
					<tr>
						<td width="25%" align="right"  bgcolor="#ececec">
						<span style="font-size: 13px; color: rgb(2, 48, 97);">模板类型英文名：</span></td>
						<td><input id="infoTypeEnglishName" value="${infoType.infoTypeEnglishName}"
							required="required" validType="length[3,100]" placeholder="请输入模板类型名"
							invalidMessage="模板类型名必须在3到100之间，请重新输入" missingMessage="请输入模板类型名"
							name="infoTypeEnglishName" class="easyui-validatebox" type="text"
							style="width: 280px;" /><span style="color: red;">*</span></td>
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
			href="javascript:void(0)" onclick="submitInfoTypeForm()">确  定</a> 
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearInfoTypeForm()">取  消</a>
	</div>
</div>
