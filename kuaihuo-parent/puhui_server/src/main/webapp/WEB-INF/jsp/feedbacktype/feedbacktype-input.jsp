<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	//提交表单
	function submitFeedBackTypeForm(){
		//提交表单
		var typeName = $("#feedBackTypeName").val();
		if(typeName.trim().length==0)
		{			
			$.messager.alert("温馨提示","意见反馈类型名不能为空");
			return;
		}
		$('#feedBackTypeForm').form("submit", {
		        success:function(data){
		        	 data = $.parseJSON(data);
		        	 $.messager.show({
		                 title:'操作提示',
		                 msg:data.msg,
		                 showType:'show'
		             });
		        	 if(data.code==1){
		           		  $('#feedBackTypeWindow').window('close');
		          		  $('#feedBackTypeTable').datagrid("reload");
		        	 }
		        }
		    });
		}


	function clearFeedBackTypeForm() {
		 $('#feedBackTypeWindow').window('close');
		 $('#feedbacktypeTable').datagrid('clearSelections');
	}
	
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 400px">
		<div style="padding: 10px;">
		<form id="feedBackTypeForm" method="post" action="${ctx}/feedbacktype/feedbacktype-add">
				<input type="hidden" name="id" value="${feedbacktype.id}" />
					<table style="width: 380px;height: 300	">
		<tr>
			<td>
				<fieldset name="fieldset26">
					<legend>
					<span style="color: #F00;font-family: Comic Sans MS;font-weight: bold;  
            		text-shadow: #999 2px 2px 1px;text-transform: uppercase;font-size: 15px;">意见反馈类型信息</span>
					</legend>
				<table>
					<tr>
						<td width="50%" height="28" align="center"  bgcolor="#ececec">
						<span style="font-size: 13px; color: rgb(2, 48, 97);">意见反馈类型名称：</span></td>
						<td>
						<input id="feedBackTypeName" value="${feedbacktype.typeName}"
						data-options="required:true,missingMessage:'请输入意见反馈类型名',validType:'typeName'"
							name="typeName" class="easyui-validatebox" type="text" style="width: 160px;" maxlength="15" />
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
			href="javascript:void(0)" onclick="submitFeedBackTypeForm()">确定</a> <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearFeedBackTypeForm()">取消</a>
	</div>
</div>