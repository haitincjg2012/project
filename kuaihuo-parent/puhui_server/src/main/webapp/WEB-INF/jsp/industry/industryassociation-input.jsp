<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	//提交表单
	function submitassciationform() {
		//提交表单
		$('#assciationform').form("submit", {
			success : function(data) {
				data = $.parseJSON(data);
				$.messager.show({
					title : '操作提示',
					msg : data.msg,
					showType : 'show'
				});
				if (data.code == 1) {
					$('#associationWindow').window('close');
					$('#industryAssoncationTable').datagrid("reload");
				}
			}
		});
	}
	
	function clearassciationform() {
		$('#assciationform').form('reset');
	}
	$(function(){
		$("#xhmc").val("");
		$("#phone").val("");
		$("#chushimima").val("");
		$("#querenmima").val("");
		$("#xingming").val("");
		$("#sfz").val("");
	})
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
			<form id="assciationform" method="post" action="${ctx}/pc/after/industryassociation/industry-association-add">
				
				<fieldset>
				<legend>
					<span class="legend">添加行业公会</span>
				</legend>
					<table>
						<tr>
							<td align="right" bgcolor="#ececec" width="45%"><span style="font-size: 13px;color: rgb(2, 48, 97)">行业公会名称：</span></td>
							<td><input id="xhmc" 
								required="required" validType="length[2,20]" placeholder="请输入行业公会名称"
								invalidMessage="行业公会名称必须在2到20之间，请重新输入" missingMessage="请输入行业公会名称"
								name="name" class="easyui-validatebox" type="text"
								style="width: 160px;" /></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec" ><span style="font-size: 13px;color: rgb(2, 48, 97)">手机号：</span></td>
							<td><input id="phone" 
								required="required" validType="length[11,11]" placeholder="请输入手机号"
								invalidMessage="手机号码格式不正确" missingMessage="请输入手机号"
								name="phone" class="easyui-validatebox" type="text"
								style="width: 160px;" /></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec" ><span style="font-size: 13px;color: rgb(2, 48, 97)">初始登录密码：</span></td>
							<td><input id="chushimima"  type="password"
								required="required" validType="length[2,20]" placeholder="请输入初始登录密码"
								invalidMessage="初始登录密码必须在2到20之间，请重新输入" missingMessage="请输入初始登录密码"
								name="onepassword" class="easyui-validatebox" type="text"
								style="width: 160px;" /></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec" ><span style="font-size: 13px;color: rgb(2, 48, 97)">确认登录密码：</span></td>
							<td><input id="querenmima"  type="password"
								required="required" validType="length[2,20]" placeholder="请输入确认登录密码"
								invalidMessage="确认登录密码必须在2到20之间，请重新输入" missingMessage="请输入确认登录密码"
								name="twopassword" class="easyui-validatebox" type="text"
								style="width: 160px;" /></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec" ><span style="font-size: 13px;color: rgb(2, 48, 97)">收款人姓名：</span></td>
							<td><input id="xingming" 
								required="required" validType="length[2,5]" placeholder="请输入收款人姓名"
								invalidMessage="收款人姓名必须在2到5之间，请重新输入" missingMessage="请输入收款人姓名"
								name="payname" class="easyui-validatebox" type="text"
								style="width: 160px;" /></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec" ><span style="font-size: 13px;color: rgb(2, 48, 97)">身份证：</span></td>
							<td><input id="sfz" 
								required="required" validType="length[15,18]" placeholder="请输入身份证"
								invalidMessage="身份证必须在15到18之间，请重新输入" missingMessage="请输入身份证"
								name="card" class="easyui-validatebox" type="text"
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
			href="javascript:void(0)" onclick="submitassciationform()">确定</a> <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearassciationform()">重置</a>
	</div>
</div>