<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$(function() {
	$("#iconUrlfile").uploadify({
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
			$.messager.alert("温馨提示",'取消上传 '+file.name + ' 成功');
		},
		'onUploadSuccess' : function(file, data, response) {
			var json = $.parseJSON(data);
			$("#attachmentId").val(json.msg);
		}
	});
}); 
	
	//提交表单
	function submiticonForm(){
		//提交表单
    	$('#iconform').form("submit", {
	        success:function(data){
	        	 data = $.parseJSON(data);
	        	 $.messager.show({
	                 title:'操作提示',
	                 msg:data.msg,
	                 showType:'show'
	             });
	        	 if(data.code==1){
	           		  $('#iconWindow').window('close');
	          		  $('#iconTable').datagrid("reload");
	        	 }
	        }
	    });
    }

	function cleariconForm() {
		$('#iconform').form('reset');
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
		<form id="iconform" method="post" action="${ctx}/icon/icon-add">
		<input type="hidden" name="id" value="${icon.id}" />
		
		<fieldset>
		<legend>
			<span class="legend">图 标 信 息</span>
		</legend>
				<table style="width:400px;">
					<tr>
						<td bgcolor="#ececec" width="33%" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">图标名称：</span></td>
						<td><input id="iconname" value="${icon.name}"
							required="required" validType="length[2,20]"
							invalidMessage="图标名称必须在2到20之间，请重新输入" missingMessage="请输入图标名称"
							name="name" class="easyui-validatebox" type="text"
							style="width: 160px;" /></td>
					</tr>
					<tr>
						<td bgcolor="#ececec" width="100" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">图标地址：</span></td>
						<td>
							<div id="fileQueue"></div> <input type="file" name="urlfile"
							id="iconUrlfile" /> <a
							href="javascript:$('#iconUrlfile').uploadify('upload');">上传文件</a> <a
							href="javascript:$('#iconUrlfile').uploadify('cancel');">取消上传队列</a>
							<div id="result"></div>
						</td>
					</tr>
					<c:if test="${icon.id!=null}">
					<tr>
						<td bgcolor="#ececec" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">图标显示：</span></td>
						<td><img border="0" src="${ctx}/javascript/commons/accordion/images/${icon.urlicon}"></td>
					</tr>
					</c:if>
				</table>
				</fieldset>
				
				</form>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="submiticonForm()">确定</a> <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="cleariconForm()">重置</a>
	</div>
</div>