<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<style>
	#pictureurl{
		height:60px;
		width:60px;
		
	}
	#pictureview{
		text-align: left;
  		display: block;
  		position: absolute;
  		top: -60px;
  		right: 25px;
	}
	#result{
		position: relative;
	}
</style>
<script type="text/javascript">
/* 添加 */
	sum="${num+2}";
	var sec="${num}";
function addMore(){

	
	sum++;
	sec++;
	$(".adds").append("<tr id='shanchu"+sum+"'><td bgcolor='#ececec' width='33%' align='right' ><span style='font-size: 13px; color: rgb(2, 48, 97);'>二级行业分类名称：</span></td><td><input id='iconname"+ sum +"' value='${icon.name}'required='required' validType='length[2,5]' invalidMessage='行业名称必须在2到5之间，请重新输入' missingMessage='请输入行业名称' name='secondType["+sec+"].name' class='easyui-validatebox' type='text' style='width: 160px;' /> <input type='button' class='TianJia5' onclick='deleteAdds(this,\"shanchu"+sum+"\")' value=' - 删除'></td></tr>");
	$("#iconname"+sum).validatebox();   

}
	//提交表单
	function submiticonForm(){
		//提交表单
    	$('#updateId').form("submit", {
	        success:function(data){
	        	 data = $.parseJSON(data);
	        	 $.messager.show({
	                 title:'操作提示',
	                 msg:data.msg,
	                 showType:'show'
	             });
	        	 if(data.code==1){
	           		  $('#adshome').window('close');
	          		  $('#adsTable').datagrid("reload");
	        	 }
	        }
	    });
    }

	function cleariconForm() {
		$('#iconform').form('reset');
	}
	
	$(function() {
		$("#editIndusTypeUrlfile").uploadify({
	        'buttonImage' : '${ctx}/uploadfy/upload.png',
			'fileObjName' : 'urlfile',
			'auto' : true, //是否自动上传
			'buttonText' : '选择文件',
			'swf' : '${ctx}/uploadfy/uploadify.swf',
			'uploader' : '${ctx}/pc/after/attachment/uload-attachment',
			 'multi': false,
			'simUploadLimit' : 1, //一次同步上传的文件数目  
			//'fileTypeExts': '*.jpg;*.jpeg;*.png;*.bmp;*.gif',//允许的格式 
			//'fileTypeDesc': '支持格式:jpg/jpeg/png/gif', 
			'onCancel' : function(file) {
				$.messager.alert("温馨提示",'取消上传 '+file.name + ' 成功');
			},
			'onUploadSuccess' : function(file, data, response) {
				var json = $.parseJSON(data);
				//$("#attachmentid").val(json.id);
				$("#Adpicurl").val(json.address);
				$("#pictureurl").attr('src','${ctx_ali}/'+json.address); 
			}
		});
	}); 
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 400px">
		<div style="padding: 11px;">
		<form id="updateId" method="post" action="${ctx}/pc/after/adshome/saveupdateheadername">
		<input  type="hidden" name="id" value="${udpatePicture.id}" />
		
		<fieldset>
		<legend>
			<span class="legend">图片header修改</span>
		</legend>
				<table style="width:400px;"  class="adds">
					<tr>
						<td bgcolor="#ececec" width="33%" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">原有的图片header：</span></td>
						<td><input readonly="readonly" name="oldnameheader" id="iconname1" value="${udpatePicture.header}"/></td> 
					</tr>
					<tr>
						<td bgcolor="#ececec" width="33%" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">修改后的图片header：</span></td>
							
						<td><input id="update" value="" name="name"/></td>
					</tr>
					
				</table>
			 	<table style="width:400px;">
			 	<input id="Adpicurl" type="hidden" name="adPicAddress" value="${udpatePicture.address}" />
					<tr>
						<td bgcolor="#ececec" width="100" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">广告Logo：</span></td>
						<td>
							<div id="fileQueue"></div> <input type="file" name="urlfile"
							id="editIndusTypeUrlfile" /> <!-- <a
							href="javascript:$('#editIndusTypeUrlfile').uploadify('upload');">上传文件</a> <a
							href="javascript:$('#editIndusTypeUrlfile').uploadify('cancel');">取消上传队列</a> -->
							<div id="result">
							<span id="pictureview">
								<img id="pictureurl" alt="" src="${ctx_ali}/${udpatePicture.address}">
								</span>
							</div>
						</td>
					</tr>
				</table>
				</fieldset>
				
				</form>
		</div>
		<div>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="submiticonForm()">确定</a> 
	</div>
</div>