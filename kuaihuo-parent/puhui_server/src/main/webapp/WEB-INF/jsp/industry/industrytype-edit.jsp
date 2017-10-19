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

	/* if($("#citys"+sum).val()==""){
		
		alert("请添加省市");
		return;
	} */
	sum++;
	sec++;
	//$(".TianJia5").remove();
	//$(".remover").append("<input type='button' class='TianJia5' onclick='addMore()' value=' - 删除'>");
	$(".adds").append("<tr id='shanchu"+sum+"' class='fuji'><td bgcolor='#ececec' width='33%' align='right' ><span style='font-size: 13px; color: rgb(2, 48, 97);'>二级行业分类名称：</span></td><td><input id='iconname"+ sum +"' value='${icon.name}'required='required' validType='length[1,10]' invalidMessage='行业名称必须在1到10之间，请重新输入' missingMessage='请输入行业名称' name='secondType["+sec+"].name' class='easyui-validatebox' type='text' style='width: 160px;' /> <input type='button' class='TianJia5' onclick='deleteAdds(this,\"shanchu"+sum+"\")' value='删除'><input type='button' class='TianJia5' onclick='deleteAdds_2(this)' value='置顶'><input type='hidden' class='sortnum'  name='secondType["+sec+"].sortnum' value='0'></td></tr>");
	$("#iconname"+sum).validatebox();   
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
function deleteAdds(obj,pro,_id){
	if($(".fuji").length==1){
		$.messager.show({
            title:'操作提示',
            msg:'至少有一个二级分类无法删除,请必填此项',
            showType:'show'
        });
		return;
	}

	if(_id!=null){
			$.ajax({
				url : "${ctx}/pc/after/industry/industrytype-delete",
				type : 'POST',
				data: {"id":_id},
				success : function(result) {
					var appResult = eval("(" + result + ")");
					var message = appResult.msg;
					var code = appResult.code;
					if(code==1){
						$("#"+pro).remove();
						$(obj).remove();
					}
					 $.messager.show({
		                 title:'操作提示',
		                 msg:message,
		                 showType:'show'
		             });
					
				}
			});
	}else{
		$("#"+pro).remove();
		$(obj).remove();
	}

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
			$("#attachmentid").val(json.id);
			$("#pictureurl").attr('src','${ctx_ali}/'+json.address); 
		}
	});
}); 
	
	//提交表单置换使用此方法失效
/* 	function submiticonForm(){
		//提交表单
    	$('#editform').form("submit", {
	        success:function(data){
	        	 data = $.parseJSON(data);
	        	 $.messager.show({
	                 title:'操作提示',
	                 msg:data.msg,
	                 showType:'show'
	             });
	        	 if(data.code==1){
	           		  $('#industryTypeWindow').window('close');
	          		  $('#industryTypeTable').datagrid("reload");
	        	 }
	        }
	    });
    } */
    
    function submiticonForm()

    {
      var AjaxURL= "${ctx}/pc/after/industry/industrytype-add";    
        $.ajax({
          type: "POST",
          url: AjaxURL,
          data: $('#editform').serialize(),
          success:function(data){
         	 data = $.parseJSON(data);
         	 $.messager.show({
                  title:'操作提示',
                  msg:data.msg,
                  showType:'show'
              });
         	 if(data.code==1){
            		  $('#industryTypeWindow').window('close');
           		  $('#industryTypeTable').datagrid("reload");
         	 }
         }

        });

    }

	function cleariconForm() {
		$('#editform').form('reset');
	}
	var shu="${secondtype[0].sortnum==null?0:secondtype[0].sortnum}";
	function deleteAdds_2(w){
		strp="<input type='button' class='TianJia5 trewq' value='取消'>"
		//var shu=$(w).parents('.fuji').find('.sortnum').val();
		shu++;
		$(w).parents('.fuji').find('.sortnum').val(shu);
		var quxiao=$(w).parents('.fuji').find('.trewq').size();
		if(quxiao==1){
		}else{
			$(w).after(strp);
		}
		strw=$(w).parents('.fuji').clone();
		$('.jixu').after(strw);
		$(w).parents('.fuji').remove();
		/* $('.fuji').each(function(i){
			if(i==0){
			$(this).find('.TianJia5').css('display','none');
			}else{
			$(this).find('.TianJia5').css('display','inline-block');
			}
		}); */ 
		//alert(owd);
	}
	$(function(){
		$(document).on('click','.trewq',function(){
			$(this).siblings('.sortnum').val(0);
			$(this).remove();
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
		<form id="editform" method="post" action="${ctx}/pc/after/industry/industrytype-add">
		<input type="hidden" name="firstType.id" value="${firsttype.id}" />
		<input id="attachmentid" type="hidden" name="firstType.attachment.id" value="${firsttype.attachment.id}" />
		
		<fieldset>
		<legend>
			<span class="legend">行业分类添加</span>
		</legend>
				<table style="width:400px;"  class="adds">
					<tr>
						<td bgcolor="#ececec" width="100" align="right"><span style="font-size: 13px; color: rgb(2, 48, 97);">行业分类Logo：</span></td>
						<td>
							<div id="fileQueue"></div> <input type="file" name="urlfile"
							id="editIndusTypeUrlfile" /> <!-- <a
							href="javascript:$('#editIndusTypeUrlfile').uploadify('upload');">上传文件</a> <a
							href="javascript:$('#editIndusTypeUrlfile').uploadify('cancel');">取消上传队列</a> -->
							<div id="result">
							<span id="pictureview">
								<img id="pictureurl" alt="" src="${ctx_ali}/${firsttype.attachment.address}">
								</span>
							</div>
						</td>
					</tr>
					<tr class="jixu">
						<td bgcolor="#ececec" width="33%" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">一级行业分类名称：</span></td>
						<td><input id="iconname1" value="${firsttype.name}"
							required="required" validType="length[1,10]"
							invalidMessage="行业名称必须在1到10之间，请重新输入" missingMessage="请输入图标名称"
							name="firstType.name" class="easyui-validatebox" type="text"
							style="width: 160px;" />
							<input type="button" class="TianJia5" onclick="addMore()" value=" + 添加二级分类">
							</td>
							
					</tr>
					<tr id="shanchu0" class="fuji">
						<td bgcolor="#ececec" width="33%" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">二级行业分类名称：</span></td>
						<td><input id="iconname2" value="${secondtype[0].name}"
							required="required" validType="length[1,10]"
							invalidMessage="行业名称必须在1到10之间，请重新输入" missingMessage="请输入行业名称"
							name="secondType[0].name" class="easyui-validatebox" type="text"
							style="width: 160px;" />
							<input class='sortnum' name='secondType[0].sortnum' type='hidden' value="${secondtype[0].sortnum==null?0:secondtype[0].sortnum}">
							<input type='button' class='TianJia5' onclick="deleteAdds(this,'shanchu0','${secondtype[0].id}')" value='删除'>
							<input  type='button' class='TianJia5' onclick='deleteAdds_2(this)' value='置顶'>
							<c:if test="${secondtype[0].sortnum>0}">
							<input type='button' class='TianJia5 trewq' value='取消'>
							</c:if>
							</td>
							<input type="hidden" name="secondType[0].id" value="${secondtype[0].id}" />
					</tr>
					<c:forEach items="${secondtype}" var="second"  varStatus="status" begin="1">
					<tr id='shanchu${status.count+2}'  class='fuji'>
						<td bgcolor="#ececec" width="33%" align="right" ><span style="font-size: 13px; color: rgb(2, 48, 97);">二级行业分类名称：</span></td>
						<td><input id="iconname${status.count+2}" value="${second.name}"
							required="required" validType="length[1,10]"
							invalidMessage="行业名称必须在1到10之间，请重新输入" missingMessage="请输入行业名称"
							name="secondType[${status.count}].name" class="easyui-validatebox" type="text"
							style="width: 160px;" />
							<input class='sortnum' name='secondType[${status.count}].sortnum' type='hidden' value="${second.sortnum==null?0:second.sortnum}">
							<input type='button' class='TianJia5' onclick="deleteAdds(this,'shanchu${status.count+2}','${second.id}')" value='删除'>
							<input type='button' class='TianJia5' onclick='deleteAdds_2(this)' value='置顶'>
							<c:if test="${second.sortnum>0}">
							<input type='button' class='TianJia5 trewq' value='取消'>
							</c:if>
							</td>
							<input type="hidden" name="secondType[${status.count}].id" value="${second.id}" />
					</tr>
					</c:forEach>
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
			href="javascript:void(0)" onclick="submiticonForm()">确定</a> <!-- <a
			class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
			href="javascript:void(0)" onclick="cleariconForm()">重置</a> -->
	</div>
</div>