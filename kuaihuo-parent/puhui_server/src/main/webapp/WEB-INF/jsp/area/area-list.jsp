<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>


<script type="text/javascript">

$(function(){
	
		//省
	    var province = $('#province').combobox({
	        valueField:'id',
	        textField:'name',
	        editable:false,
	        url:'${ctx}/area/area-json',
	         onChange:function(newValue, oldValue){
	            $.post('${ctx}/area/area-json',{areaId:newValue},function(data){
	            	$('#city').combobox("clear").combobox('loadData',data);
	            	$('#county').combobox("clear");
	            },'json');
	        }, 
	        onLoadSuccess:function(){//默认加载显示
	        	var target = $(this);
	    	    var data = target.combobox("getData");
	    	    var options = target.combobox("options"); 
	    	    if(data && data.length>0){
	    	        var fs = data[0];
	    	        target.combobox("setValue",fs[options.valueField]);
	    	    }
	        }
	    });
		
		//市
	    var city = $('#city').combobox({
	        valueField:'id',
	        textField:'name',
	        editable:false,
	        onChange:function(newValue, oldValue){
	            $.post('${ctx}/area/area-json',{areaId:newValue},function(data){
	            	$('#county').combobox("clear").combobox('loadData',data);
	            },'json');
	        },
	        onLoadSuccess:function(){//默认加载显示
	        	var target = $(this);
	    	    var data = target.combobox("getData");
	    	    var options = target.combobox("options"); 
	    	    if(data && data.length>0){
	    	        var fs = data[0];
	    	        target.combobox("setValue",fs[options.valueField]);
	    	    }
	        }
	    });
	     
		//县/区
	    var county = $('#county').combobox({
	        valueField:'id',
	        textField:'name',
	        editable:false,
	        onLoadSuccess:function(){//默认加载显示
	        	var target = $(this);
	    	    var data = target.combobox("getData");
	    	    var options = target.combobox("options"); 
	    	    if(data && data.length>0){
	    	        var fs = data[0];
	    	        target.combobox("setValue",fs[options.valueField]);
	    	    }
	        }
	    });
	    
		
	});


	function onLoadSuccess(){
	    var target = $(this);
	    var data = target.combobox("getData");
	    var options = target.combobox("options"); 
	    if(data && data.length>0){
	        var fs = data[0];
	        target.combobox("setValue",fs[options.valueField]);
	    }
	}

</script>


<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',split:true" style="width: 300px">
		<div style="padding: 10px;">
				<table>
				<tr>
					<td colspan="2"><h1>Combox级联演示</h1></td>
				</tr>
					<tr>
						<td>省：</td>
						<td><input id="province" class="easyui-combobox" name="province"/> </td>
					</tr>
					<tr>
						<td>市：</td>
						<td><input id="city" name="city"  class="easyui-combobox" /></td>
					</tr>
					<tr>
						<td>县：</td>
						<td><input id="county" name="county"  class="easyui-combobox"/></td>
					</tr>
				</table>
		</div>
	</div>
</div>
