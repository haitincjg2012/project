<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#urlfile").uploadify({
			'buttonImage' : '${ctx}/uploadfy/upload.png',
			'fileObjName' : 'urlfile',
			'auto' : false, //是否自动上传
			'buttonText' : '选择文件',
			'swf' : '${ctx}/uploadfy/uploadify.swf',
			'uploader' : '${ctx}/attachment/import-attachment',
			'simUploadLimit' : 1, //一次同步上传的文件数目  
			//'fileTypeExts': '*.jpg;*.jpeg;*.png;*.bmp;*.gif',//允许的格式 
			//'fileTypeDesc': '支持格式:jpg/jpeg/png/gif', 
			'onCancel' : function(file) {
				$.messager.alert("温馨提示", '取消上传 ' + file.name + ' 成功');
			},
			'onUploadSuccess' : function(file, data, response) {
				var json = $.parseJSON(data);
				$("#attachmentId").val(json.msg);
			}
		});
	});
	//提交表单
	function submituploadForm() {
/* 		if ($("#attachmentId").val() == "") {
			$.messager.alert('Warning', '请上传文件');
		} */
		//提交表单
		$('#onemenuform').form("submit", {
			success : function(data) {
				data = $.parseJSON(data);
				$.messager.show({
					title : '操作提示',
					msg : data.msg,
					showType : 'show'
				});
				if (data.code == 1) {
					$('#onemenuWindow').window('close');
					$('#onemenuTable').datagrid("reload");
				}
			}
		});
	}

	function clearuploadForm() {
		$('#uploadform').form('reset');
	}
	function selupload(title) {
		alert(11111);
		$('#myuploadWindow').window({
			title : title
		});
		$('#myuploadWindow').window('open');
		$('#myuploadWindow').window('refresh',
				"${ctx}/merchant/window-merchant-list");
	}
	function select(userName, id) {
		$("#myMerchant").val(userName);
		$("#myMerchantId").val(id);
		$('#myuploadWindow').window('close');
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">

			<form id="onemenuform" method="post"
				action="${ctx}/onemenu/onemenu-add">
				<input id="attachmentId" type="hidden" name="attachmentId" />
				<input id="id" type="hidden" name="id" value="${oneMenu.id}" />
				<fieldset>
					<legend>
						<span class="legend">一级菜单信息</span>
					</legend>
					<table>
						<tr>
							<td width="32%" bgcolor="#ececec" align="right"><span
								style="font-size: 13px; color: rgb(2, 48, 97);">菜单名称：</span></td>

							<td><input id="onemenuname" value="${oneMenu.name}"
								required="required" validType="length[2,20]"
								invalidMessage="名称必须在2到20之间，请重新输入" missingMessage="请输入软件标题"
								name="name" class="easyui-validatebox" type="text"
								style="width: 180px;" /></td>
						</tr>
						<tr>
							<td width="32%" bgcolor="#ececec" align="right"><span
								style="font-size: 13px; color: rgb(2, 48, 97);">图片名称</span></td>

							<td><input id="onemenuimagename" value="${oneMenu.imageName}"
								required="required" validType="length[2,20]"
								invalidMessage="必须在2到20之间，请重新输入" missingMessage="请输入图片名字"
								name="imageName" class="easyui-validatebox" type="text"
								style="width: 180px;" /></td>
						</tr>
						<tr>
							<td width="32%" bgcolor="#ececec" align="right"><span
								style="font-size: 13px; color: rgb(2, 48, 97);">是否默认显示</span></td>

							<td><input name="isShow" type="radio" value="1" <c:if test="${oneMenu.isShow==1}">checked=true</c:if> >是<input name="isShow" type="radio" value="0" <c:if test="${oneMenu.isShow==0}" >checked=true </c:if> >否</td>
						</tr>
						<tr>
							<td width="160" bgcolor="#ececec" align="right"><span
								style="font-size: 13px; color: rgb(2, 48, 97);">菜单图标：</span></td>
							<td>
								<div id="fileQueue"></div> <input type="file" name="urlfile"
								id="urlfile" /> <a
								href="javascript:$('#urlfile').uploadify('upload');">上传文件</a> <a
								href="javascript:$('#urlfile').uploadify('cancel');">取消上传队列</a>
								<div id="result"></div>
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
			href="javascript:void(0)" onclick="submituploadForm()">确定</a> <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="clearuploadForm()">重置</a>
	</div>
	<div id="myonemenuWindow" class="easyui-window"
		style="width: 450px; height: 380px"
		data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
	</div>
</div>