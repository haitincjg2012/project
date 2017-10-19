<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
		var value = $('#resourceLevel').val();
		if (value == 0) {
			$('#viewresource').hide();
			$('#viewresource2').hide();
			$('#viewonemenu').show();
		} else if (value == 1) {
			$('#viewresource').show();
			$('#viewresource2').show();
			$('#viewonemenu').hide();
		}
	});

	function viewresource() {
		var value = $('#resourceLevel').val();
		if (value == 0) {
			$('#viewresource').hide();
			$('#viewresource2').hide();
			$('#viewonemenu').show();
		} else if (value == 1) {
			$('#viewresource').show();
			$('#viewresource2').show();
			$('#viewonemenu').hide();
		}
	}

	//提交表单
	function submitresourceForm() {
		//提交表单
		$('#resourceform').form("submit", {
			success : function(data) {
				data = $.parseJSON(data);
				$.messager.show({
					title : '操作提示',
					msg : data.msg,
					showType : 'show'
				});
				if (data.code == 1) {
					$('#resourceWindow').window('close');
					$('#resourceTable').treegrid('reload');
				}
			}
		});
	}

	function clearresourceForm() {
		$('#resourceform').form('reset');
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
			<form id="resourceform" method="post"
				action="${ctx}/resource/resource-add">
				<input type="hidden" name="id" value="${resource.id}" />
				<fieldset>
				<legend>
					<span class="legend"> 资 源 信 息 </span>
				</legend>
					<table style="width:400px;">
						<tr>
							<td align="right" bgcolor="#ececec" width="33%"><span style="font-size: 13px; color: rgb(2, 48, 97);">资源描述：</span></td>
							<td><input id="resourceRealName"
								value="${resource.resourceRealName}" required="required"
								validType="length[2,20]" invalidMessage="资源描述必须在2到20之间，请重新输入"
								missingMessage="请输入资源描述" name="resourceRealName"
								class="easyui-validatebox" type="text" style="width: 160px;" /></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec"><span style="font-size: 13px; color: rgb(2, 48, 97);">资源名称：</span></td>
							<td><input id="resourceName"
								value="${resource.resourceName}" required="required"
								validType="length[2,50]" invalidMessage="资源名称必须在2到50之间，请重新输入"
								missingMessage="请输入资源名称" name="resourceName"
								class="easyui-validatebox" type="text" style="width: 160px;" /></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec"><span style="font-size: 13px; color: rgb(2, 48, 97);">资源图标：</span></td>
							<td><select id="icon.id" name="icon.id" style="width: 160px;">
									<c:forEach items="${icons}" var="icon">
										<option value="${icon.id}"
											<c:if test="${icon.id==resource.icon.id }">selected="selected"</c:if>>${icon.name
											}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec"><span style="font-size: 13px; color: rgb(2, 48, 97);">是否是菜单：</span></td>
							<td><select id="isMenu" name="isMenu"  style="width: 160px;">
									<option value="0"
										<c:if test="${0==resource.isMenu }">selected="selected"</c:if>>是</option>
									<option value="1"
										<c:if test="${1==resource.isMenu }">selected="selected"</c:if>>否</option>
							</select></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec"><span style="font-size: 13px; color: rgb(2, 48, 97);">资源类型：</span></td>
							<td><select id="resourceLevel" name="resourceLevel"
								onchange="viewresource()" style="width: 160px;">
									<option value="0"
										<c:if test="${0==resource.resourceLevel }">selected="selected"</c:if>>一级菜单</option>
									<option value="1"
										<c:if test="${1==resource.resourceLevel }">selected="selected"</c:if>>二级菜单</option>
							</select></td>
						</tr>
						<tr id="viewresource">
							<td align="right" bgcolor="#ececec"><span style="font-size: 13px; color: rgb(2, 48, 97);">资源地址：</span></td>
							<td><input id="resourceUrl" value="${resource.resourceUrl}"
								name="resourceUrl" type="text" style="width: 160px;" /></td>
						</tr>
						<tr id="viewresource2">
							<td align="right" bgcolor="#ececec"><span style="font-size: 13px; color: rgb(2, 48, 97);">一级菜单列表：</span></td>
							<td><select name="parentId">
									<c:forEach items="${resources}" var="resourcem">
										<option value="${resourcem.id }"
											<c:if test="${resourcem.id==resource.resource.id }">selected="selected"</c:if>>${resourcem.resourceRealName
											}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr id="viewonemenu" >
							<td align="right" bgcolor="#ececec"><span style="font-size: 13px; color: rgb(2, 48, 97);">头部菜单列表：</span></td>
							<td><select name="oneMenuId">
									<c:forEach items="${oneMenus}" var="oneMenu">
										<option value="${oneMenu.id}"
											<c:if test="${oneMenu.id==resource.oneMenuId }">selected="selected"</c:if>>${oneMenu.name}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#ececec"><span style="font-size: 13px; color: rgb(2, 48, 97);">资源排序：</span></td>
							<td><input id="resourceOrder"
								value="${resource.resourceOrder}" required="required"
								validType="length[1,5]" invalidMessage="资源地址必须在1到5之间，请重新输入"
								missingMessage="请输入资源排序" name="resourceOrder"
								class="easyui-validatebox" type="text" style="width: 160px;" /></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="submitresourceForm()">确定</a> <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearresourceForm()">重置</a>
	</div>
</div>