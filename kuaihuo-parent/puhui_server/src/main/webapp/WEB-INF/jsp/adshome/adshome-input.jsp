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
var editor = null;
$(function() {
	if (CKEDITOR.env.ie && CKEDITOR.env.version < 9)
		
		//alert(CKEDITOR);
		CKEDITOR.tools.enableHtml5Elements(document);
	// The trick to keep the editor in the sample quite small
	// unless user specified own height.
	editor = CKEDITOR.replace("detail_html");
});




//========================================
/* 添加 */
	sum=2;
	var sec=0
function addMore(){

	/* if($("#citys"+sum).val()==""){
		
		alert("请添加省市");
		return;
	} */
	/* sum++;
	sec++; */
	//$(".TianJia5").remove();
	//$(".remover").append("<input type='button' class='TianJia5' onclick='addMore()' value=' - 删除'>");
/* 	$(".adds").append("<tr id='shanchu"+sum+"'><td bgcolor='#ececec' width='33%' align='right' ><span style='font-size: 13px; color: rgb(2, 48, 97);'>二级行业分类名称：</span></td><td><input id='iconname"+ sum +"' value='${icon.name}'required='required' validType='length[2,5]' invalidMessage='行业名称必须在2到5之间，请重新输入' missingMessage='请输入行业名称' name='secondType["+sec+"].name' class='easyui-validatebox' type='text' style='width: 160px;' /> <input type='button' class='TianJia5' onclick='deleteAdds(this,\"shanchu"+sum+"\")' value=' - 删除'></td></tr>");
	$("#iconname"+sum).validatebox();    */
/* 	$("#provs"+sum).change(function(){
		var city = $.ajax({
			url : "${ctx}/pc/after/merchant/findAreaListByAreaId?prov_id=" + $("#provs"+sum).val()+"&CSRFToken="+CSRFToken,
			type : 'POST',
			success : function(result) {
				var appResult = eval("(" + result + ")");
				var data = appResult.content;
				$("#citys"+sum).empty().append("<option value=''>请选择市</option>");
				$.each(data, function() {
					$("#citys"+sum).append(
							"<option value="+this.id+">" + this.name
									+ "</option>");
				});
			}
		});
	})  */
}
function deleteAdds(obj,pro){
	$("#"+pro).remove();
	$(obj).remove();
}
$(function() {
	$("#iconUrlfile").uploadify({
        'buttonImage' : '${ctx}/uploadfy/upload.png',
		'fileObjName' : 'urlfile',
		'auto' : true, //是否自动上传
		'buttonText' : '选择文件',
		'swf' : '${ctx}/uploadfy/uploadify.swf',
		'uploader' : '${ctx}/pc/after/adshome/picture',
		 'multi': false,
		'simUploadLimit' : 1, //一次同步上传的文件数目  
		//'fileTypeExts': '*.jpg;*.jpeg;*.png;*.bmp;*.gif',//允许的格式 
		//'fileTypeDesc': '支持格式:jpg/jpeg/png/gif', 
		'onCancel' : function(file) {
			$.messager.alert("温馨提示",'取消上传 '+file.name + ' 成功');
		},
		'onUploadSuccess' : function(file, data, response) {
			var json = $.parseJSON(data);
			$("#attachmentid").val(json.id);
			$("#pictureurl").attr('src','${ctx_ali}/'+json.address); 
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
	           		  $('#adshome').window('close');
	          		  $('#adsTable').datagrid("reload");
	        	 }
	        }
	    });
    }

	function cleariconForm() {
		$('#iconform').form('reset');
	}
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 1800px">
		<div style="padding: 10px;">
		<form id="iconform" method="post" action="${ctx}/pc/after/adshome/addpicture">
		<%-- <input type="hidden" name="id" value="${icon.id}" /> --%>
		 <input id="attachmentid" type="hidden" name="id" value="" />
		
		<fieldset>
		<legend>
			<span class="legend">轮播图片添加</span>
		</legend>
				<table style="width:1100px;"  class="adds">
					<tr>
						<td bgcolor="#ececec" width="800" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">首页分播图：</span></td>
						<td >
							<div id="fileQueue"></div> <input type="file" name="urlfile"
							id="iconUrlfile" /> <a
							href="javascript:$('#iconUrlfile').uploadify('upload');">上传文件</a> <a
							href="javascript:$('#iconUrlfile').uploadify('cancel');">取消上传队列</a>
							<div id="result">
							<span id="pictureview" style=" text-align:left; display: block;">
								<img id="pictureurl" alt="" src="">
								</span>
								    <hr width="300px" />
							</div>
							
						</td>
					</tr>
				</table>
				</fieldset>
				    <div class="RightTu_1 clearfix">
				<label class="control-label" for="detail_html">图文详情：</label>
				<div class="controls">
					<textarea   id="detail_html" name="detailContent"></textarea>
				</div>
				
			</div>
				</form>
		</div>
		<div>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="submiticonForm()">确定</a> <!-- <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="cleariconForm()">重置</a> -->
	</div>
</div>