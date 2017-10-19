<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	
	//提交表单
	function submitUserForm(){
		//获取input选择参数
		var roleId = $("input[name='roleId']:checked").val();
		$('#roleId').attr("value", roleId);//赋值到隐藏表单
		//提交表单
    	$('#userform').form("submit", {
	        success:function(data){
	        	 data = $.parseJSON(data);
	        	 $.messager.show({
	                 title:'操作提示',
	                 msg:data.msg,
	                 showType:'show'
	             });
	        	 if(data.code==1){
	           		  $('#userWindow').window('close');
	          		  $('#userTable').datagrid("reload");
	        	 }
	        }
	    });
    }
	
	function clearUserForm(){
		$('#userform').form('reset');
	}

</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',split:true" style="width:350px">
		<div style="padding: 10px;">
		    <form id="userform" method="post" action="${ctx}/user/user-add">
		        <input type="hidden" name="id" value="${requestScope.user.id}" />
		        <input id="roleId" type="hidden" name="role.id" value="${requestScope.user.role.id}" />
		        <fieldset>
		        <legend>
		        	<span class="legend">用 户 信 息</span>
		        </legend>
		        <table style="width:290px;">
		            <tr>
		                <td width="50%" bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">用户名：</span></td>
		                <td>
		                	<c:if test="${requestScope.user.id==null}">
							<input id="userName" value="" required="required" validType="length[5,20]" invalidMessage="用户名必须在5到20之间，请重新输入" missingMessage="请输入用户名" placeholder="请输入用户名" name="userName" class="easyui-validatebox" type="text" style="width:160px;"/>
							</c:if>
							<c:if test="${requestScope.user.id!=null}">
								${requestScope.user.userName}
								<input id="userName" value="${requestScope.user.userName}" name="userName" type="hidden"/>
							</c:if>
		                </td>
		            </tr>
		            <tr>
		                <td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">密码：</span></td>
		                <td>
		                	<input id="userPassword" value="" required="required" validType="length[6,30]" invalidMessage="用户密码必须在6到30之间，请重新输入" missingMessage="请输入用户密码" placeholder="请输入用户密码" name="userPassword" class="easyui-validatebox" type="password" style="width:160px;"/>
		                </td>
		            </tr>
		            <tr>
		                <td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">真实姓名：</span></td>
		                <td>
		                	<input id="realName"  required="required" validType="length[0,30]" invalidMessage="真实姓名不能超过30个字符" missingMessage="请输入真实姓名" value="${requestScope.user.realName}" name="realName" class="easyui-validatebox" type="text" style="width:160px;"/>
		                </td>
		            </tr>
		             <tr>
		                <td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">邮箱：</span></td>
		                <td>
		                	<input id="email" value="${requestScope.user.email}" required="required" missingMessage="请输入邮箱" name="email" validType="email" invalidMessage="请输入正确的邮箱格式" class="easyui-validatebox" type="text" style="width:160px;"/>
		                </td>
		            </tr>
		            <tr>
		                <td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">电话号码：</span></td>
		                <td>
		                	<input id="mobile" value="${requestScope.user.mobile}" validType="length[0,20]" invalidMessage="电话号码不能超过20个字符" name="mobile" class="easyui-validatebox" type="text" style="width:160px;"/>
		                </td>
		            </tr>
		            <tr>
		                <td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">手机号码：</span></td>
		                <td>
		                	<input id="tel" value="${requestScope.user.tel}" name="tel" validType="length[0,11]" invalidMessage="手机号码不能超过12个字符" class="easyui-validatebox" type="text" style="width:160px;"/>
		                </td>
		            </tr>
		             <tr>
		                <td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">联系地址：</span></td>
		                <td>
		                	<input id="address" value="${requestScope.user.address}" name="address" validType="length[0,50]" invalidMessage="联系地址不能超过50个字符" class="easyui-validatebox" type="text" style="width:160px;"/>
		                </td>
		            </tr>
		             <tr>
		                <td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">性别：</span></td>
		                <td>
			             	 <select name="gender" style="width:160px;">
			             	 	<option value="1" <c:if test="${requestScope.user.gender==1}">selected="selected"</c:if>>男</option>
			             	 	<option value="2" <c:if test="${requestScope.user.gender==2}">selected="selected"</c:if>>女</option>
			             	 	<option value="0" <c:if test="${requestScope.user.gender==0}">selected="selected"</c:if>>未知</option>
			             	 </select>
		               	</td>
		            </tr>
		            <tr>
		                <td bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">是否已激活：</span></td>
		                <td>
			             	 <select name="isLocked" style="width:160px;">
			             	 	<option value="0" <c:if test="${requestScope.user.isLocked==0}">selected="selected"</c:if>>激活</option>
			             	 	<option value="1" <c:if test="${requestScope.user.isLocked==1}">selected="selected"</c:if>>未激活</option>
			             	 </select>
		               	</td>
		            </tr>
		        </table>
		        </fieldset>
		    </form>
		</div>
	</div>
	<div data-options="region:'center'" style="padding:15px;">
		<div class="userCheck" id="checkedRoleIds">
			<c:forEach var="myrole" items="${roles}" varStatus="stuts">
				<div><input name="roleId" type="radio" value='<c:out value="${myrole.id}"/>' 
					<c:if test="${requestScope.user.role.id==myrole.id}">checked="checked"</c:if>
				/>${myrole.descript}</div>
			</c:forEach>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 5px;">
		 <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submitUserForm()">确定</a>
	     <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="clearUserForm()">重置</a>
	</div>
</div>
