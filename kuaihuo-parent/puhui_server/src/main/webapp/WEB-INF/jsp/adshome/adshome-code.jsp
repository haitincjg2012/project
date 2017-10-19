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
var ids =null;
$(function() {
	if (CKEDITOR.env.ie && CKEDITOR.env.version < 9)
		CKEDITOR.tools.enableHtml5Elements(document);
	 
	editor = CKEDITOR.replace("detail_html");
	console.log(editor)
	 
	try{
    //返回显示页面
	$.ajax({
		url:"${ctx}/pc/after/adshome/code-ajax",
		data:"id=${udpateCode.id}",
		type:"post",
		dataType:"json",
		success:function(data){
			 CKEDITOR.instances.detail_html.setData(data.detailContent);
			console.log(ids);
			console.log(CKEDITOR.instances.detail_html.getData());
			ids = CKEDITOR.instances.detail_html.getData();
			console.log(ids);
			
		}
	})
	}catch(err){
		console.log(err)
	}
});




//========================================
/* 添加 */
	sum=2;
	var sec=0
function addMore(){
}
function deleteAdds(obj,pro){
	$("#"+pro).remove();
	$(obj).remove();
}	
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
	           		  $('#adscode').window('close');
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
		<form id="iconform" method="post" action="${ctx}/pc/after/adshome/updatecode">
		 <input id="attachmentid" type="hidden" name="id" value="${udpateCode.id}" />
				    <div class="RightTu_1 clearfix">
				<label class="control-label" for="detail_html">图文详情：</label>
				<div class="controls">
					<textarea   id="detail_html" name="name" value=ids></textarea>
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
			href="javascript:void(0)" onclick="submiticonForm()">确定</a> 
	</div>
</div>