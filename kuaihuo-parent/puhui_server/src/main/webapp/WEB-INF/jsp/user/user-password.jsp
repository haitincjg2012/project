<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	//提交表单
	function submituserpassForm() {
		//验证密码相同
		var newPassword = $('#newPassword').val();
		var realPassword = $('#realPassword').val();
		if (newPassword == realPassword) {
			//提交表单
			$('#userpassform').form("submit", {
				success : function(data) {
					data = $.parseJSON(data);
					$.messager.show({
						title : '操作提示',
						msg : data.msg,
						showType : 'show'
					});
					if (data.code == 1) {
						$('#edituserinfoWindow').window('close');
					}
				}
			});
		} else {
			$.messager.show({
				title : '操作提示',
				msg : "您的新密码两次输入的不一致，请重新输入",
				showType : 'show'
			});
		}
	}

	function clearuserpassForm() {
		$('#userpassform').form('reset');
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
			<form id="userpassform" method="post"
				action="${ctx}/user/userpass-add">
				
					<fieldset>
					<legend>
						<span class="legend">
							修 改 密 码
						</span>
					</legend>
						<table style="width:300px;">
							<tr>
								<td bgcolor="#ececec" align="right" width="30%"><span style="font-size: 13px; color: rgb(2, 48, 97);">旧密码：</span></td>
								<td><input id="olePassword" value="" required="required"
									validType="length[6,30]" invalidMessage="旧密码必须在6到30个字符之间，请重新输入"
									missingMessage="请输入旧密码" name="olePassword" placeholder="请输入旧密码"
									class="easyui-validatebox" type="password"
									style="width: 160px;" /></td>
							</tr>
							<tr>
								<td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">新密码：</span></td>
								<td><input id="newPassword" value="" required="required"
									validType="length[6,30]" invalidMessage="新密码必须在6到30个字符之间，请重新输入"
									missingMessage="请输入新密码" name="newPassword" placeholder="请输入新密码"
									class="easyui-validatebox" type="password"
									style="width: 160px;" /></td>
							</tr>
							<tr>
								<td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">确认密码：</span></td>
								<td><input id="realPassword" value="" required="required"
									validType="length[6,30]" placeholder="请输入确认密码"
									invalidMessage="确认密码必须在6到30个字符之间，请重新输入"
									missingMessage="请输入确认密码" name="realPassword"
									class="easyui-validatebox" type="password"
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
			href="javascript:void(0)" onclick="submituserpassForm()">确定</a> <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearuserpassForm()">重置</a>
	</div>
</div>
