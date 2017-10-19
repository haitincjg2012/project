<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	var sessionId='<%=session.getId()%>';
	$(function() {
		$("#urlfile").uploadify({
	        'buttonImage' : '${ctx}/uploadfy/upload.png',
			'fileObjName' : 'urlfile',
			'auto' : false, //是否自动上传
			'buttonText' : '选择文件',
			'swf' : '${ctx}/uploadfy/uploadify.swf',
			'uploader' : '${ctx}/attachment/import-attachment;jsessionid='+sessionId,
			'simUploadLimit' : 1, //一次同步上传的文件数目  
			//'fileTypeExts': '*.jpg;*.jpeg;*.png;*.bmp;*.gif',//允许的格式 
			//'fileTypeDesc': '支持格式:jpg/jpeg/png/gif', 
			'onCancel' : function(file) {
				$.messager.alert("温馨提示",'取消上传 '+file.name + ' 成功');
			},
			'onUploadSuccess' : function(file, data, response) {
				var json = $.parseJSON(data);
				$("#attachmentId").val(json.msg);
			}
		});
	}); 
	//提交表单
	function submituploadForm() {
		if ($("#attachmentId").val() == "") {
			$.messager.alert('Warning', '请上传文件');
		}
		//提交表单
		$('#updateform').form("submit", {
			success : function(data) {
				data = $.parseJSON(data);
				$.messager.show({
					title : '操作提示',
					msg : data.msg,
					showType : 'show'
				});
				if (data.code == 1) {
					$('#updateWindow').window('close');
					$('#updateTable').datagrid("reload");
				}
			}
		});
	}

	function clearuploadForm() {
		$('#uploadform').form('reset');
	}
	function selupload(title) {
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

			<form id="updateform" method="post"
				action="${ctx}/update/update-import-data">
				<input id="attachmentId" type="hidden" name="attachmentId" value="${update.attachment.id }" />
				<input id="id" type="hidden" name="id" value="${update.id}"/>
				<fieldset>
				<legend>
					<span class="legend">软 件 信 息</span>
				</legend>
				<table>
					<tr>
						<td width="32%" bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">软件标题：</span></td>

						<td><input id="updatetitle" value="${update.title}"
							required="required" validType="length[2,20]"
							invalidMessage="软件标题必须在2到20之间，请重新输入" missingMessage="请输入软件标题"
							name="title" class="easyui-validatebox" type="text"
							style="width: 180px;" /></td>
					</tr>
					<tr>
						<td width="160" bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">软件版本号：</span></td>

						<td><input id="updateversion" value="${update.version}"
							required="required" validType="length[2,20]"
							invalidMessage="软件版本号必须在2到20之间，请重新输入"
							missingMessage="请输入软件版本号" name="version"
							class="easyui-validatebox" type="text" style="width: 180px;" /></td>
					</tr>
					<tr>
						<td width="160" bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">软件类型：</span></td>

						<td><select name="type">
								<option value="0"
									<c:if test="${update.type==0}">selected="selected"</c:if>>android</option>
								<option value="1"
									<c:if test="${update.type==1}">selected="selected"</c:if>>ios</option>
						</select></td>
					</tr>
					<tr>
						<td width="160" bgcolor="#ececec" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">软件地址：</span></td>
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
	<div id="myupdateWindow" class="easyui-window"
		style="width: 450px; height: 380px"
		data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
	</div>
</div>