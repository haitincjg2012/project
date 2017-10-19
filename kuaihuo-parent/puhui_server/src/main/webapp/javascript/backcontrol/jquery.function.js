/**
* lincy
*
**/
//打开指定窗口
function openWindow(title,url,windowId){
	$('#'+windowId).window({  
	    title: title
	}); 
	$('#'+windowId).window('open');
	$('#'+windowId).window('refresh', url);
}

//打开添加修改窗口
function openEditWindow(title,url,type,windowId,tableId){
	if(type == 'edit'){
		var rows = $('#'+tableId).datagrid('getSelected');
		if(rows == null){
			 $.messager.alert('提示','请选择信息!','warning');
		}else{
			$('#'+windowId).window({  
			    title: title
			}); 
			$('#'+windowId).window('open');
			$('#'+windowId).window('refresh', url+rows.id);
		}
	}else if(type == 'add'){
		$('#'+windowId).window({  
		    title: title
		}); 
		$('#'+windowId).window('open');
		$('#'+windowId).window('refresh', url);
	}
	
}

//打开添加修改窗口(树)
function openEditWindowTree(title,url,type,windowId,treeId){
	if(type == 'edit'){
		var rows = $('#'+treeId).tree('getSelected');
		if(rows == null){
			 $.messager.alert('提示','请选择信息!','warning');
		}else{
			$('#'+windowId).window({  
			    title: title
			}); 
			$('#'+windowId).window('open');
			$('#'+windowId).window('refresh', url+rows.id);
		}
	}else if(type == 'add'){
		$('#'+windowId).window({  
		    title: title
		}); 
		$('#'+windowId).window('open');
		$('#'+windowId).window('refresh', url);
	}
	
}

//打开添加修改窗口
function openDetailWindow(title,url,windowId,tableId,serial){
		var rows = $('#'+tableId).datagrid('getSelected');
		if(rows == null){
			 $.messager.alert('提示','请选择信息!','warning');
		}else{
			$('#'+windowId).window({  
			    title: title
			}); 
			$('#'+windowId).window('open');
			$('#'+windowId).window('refresh', url+rows.id+"/"+serial);
		}
}

function formatterOpenWindow(title,url,windowId){
	$('#'+windowId).window({  
	    title: title
	}); 
	$('#'+windowId).window('open');
	$('#'+windowId).window('refresh', url);
}

//提交表单
function submitPhone(formId,phoneId,contentId,tableId,windowId){
	var str = $('#'+phoneId).val();
	var bool = false;
	if(!str){
		$.messager.alert('出错','请输入正确号码!','error'); 
	}else if(str.length<11){
		$.messager.alert('出错','单个短信号码长度为11位数字!','error'); 
		bool = false;
	}else if(checkMobileList(str)){
		var content = $('#'+contentId).val();
		if(!content){
			$.messager.alert('出错','请输入短信内容!','error'); 
			bool = false;
		}else{
			bool = true;
		}
	}else {
		$.messager.alert('出错','手机号码串错误,请修改!','error'); 
		bool = false;
	}

	
	if(bool){
		//提交表单
		$('#'+formId).form("submit", {
	        success:function(data){
	        	data = $.parseJSON(data);
	        	 $.messager.show({
	                 title:'操作提示',
	                 msg:data.message,
	                 showType:'slide',
	                 style:{  
	                     right:'',  
	                     top:document.body.scrollTop+document.documentElement.scrollTop,  
	                     bottom:''  
	                 }  
	             });
	        	 if(data.success){
		        	if(tableId != null && tableId != ""){
		        		$('#'+windowId).window('close');
		        		$('#'+tableId).datagrid("reload");
		        	}else{
     			    	clearForm(formId);
    			    }
	        	 }
	        }
	    });
	}
	
}
function checkMobileList(mobileValue){
	var matchText =/^((((13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|2|3|5|6|7|8|9])\d{8})|((059[0-9])\d{7})),)*(((13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|2|3|5|6|7|8|9])\d{8})|((059[0-9])\d{7}))$/;
	if(matchText.test(mobileValue)){
		return true;
	}
	return false;
}

//提交表单刷新表格
function submitForm(formId,windowId,tableId){
	//提交表单
	$('#'+formId).form("submit", {
        success:function(data){
        	data = $.parseJSON(data);
        	 $.messager.show({
                 title:'操作提示',
                 msg:data.message,
                 showType:'slide',
                 style:{  
                     right:'',  
                     top:document.body.scrollTop+document.documentElement.scrollTop,  
                     bottom:''  
                 }  
             });
        	 if(data.success){
        		 $('#'+windowId).window('close');
                 $('#'+tableId).datagrid("reload");
        	 }
        }
    });
}

//提交表单刷新表格
function submitLoginForm(formId,windowId){
	//提交表单
	$('#'+formId).form("submit", {
        success:function(data){
        	data = $.parseJSON(data);
        	 $.messager.show({
                 title:'操作提示',
                 msg:data.message,
                 showType:'slide',
                 style:{  
                     right:'',  
                     top:document.body.scrollTop+document.documentElement.scrollTop,  
                     bottom:''  
                 }  
             });
        	 if(data.success){
        		 $('#'+windowId).window('close');
        	 }
        }
    });
}

//提交表单刷新表格
function submitFormTree(formId,windowId,treeId){
	//提交表单
	$('#'+formId).form("submit", {
        success:function(data){
        	data = $.parseJSON(data);
        	 $.messager.show({
                 title:'操作提示',
                 msg:data.message,
                 showType:'slide',
                 style:{  
                     right:'',  
                     top:document.body.scrollTop+document.documentElement.scrollTop,  
                     bottom:''  
                 }  
             });
        	 if(data.success){
	            $('#'+windowId).window('close');
	            $('#'+treeId).tree("reload");
        	 }
        }
    });
}

//提交异步树表单
function submitFormAsynTree(formId,windowId,treeId,title,url){
	//提交表单
	$('#'+formId).form("submit", {
        success:function(data){
        	data = $.parseJSON(data);
        	 $.messager.show({
                 title:'操作提示',
                 msg:data.message,
                 showType:'slide',
                 style:{  
                     right:'',  
                     top:document.body.scrollTop+document.documentElement.scrollTop,  
                     bottom:''  
                 }  
             });
        	 if(data.success){
	            $('#'+windowId).window('close');
	            toPages(title,url);
//	            var node = $('#'+treeId).tree('getSelected');
//	            if(data.operate){
//	            	$('#'+treeId).tree("append",{  
//	                    parent: node.target,  
//	                    data: [{  
//	                    	"id":data.id,
//	                        "text":data.name
//	                    }]  
//	                });  
//	            }else{
//	            	node.id = data.id;
//	            	node.text = data.name;
//	            	$('#'+treeId).tree("update", node);
//	            }
                
        	 }
        }
    });
}	

//删除单个选选
function deleteSelect(tableId,url,msg){
	var rows = $('#'+tableId).datagrid('getSelected');
	if(rows == null || rows == ''){
		 $.messager.alert('提示','请选择'+msg,'warning');
	}else{
		$.messager.confirm('提示', '你确定要删除吗？', function(r){  
            if (r){  
            	$.ajax({
     			   type: "POST",
     			   url: url + rows.id,
     			   success: function(data){
     				 data = $.parseJSON(data);
     			     $.messager.show({
     	                 title:'操作提示',
     	                 msg:data.message,
     	                 showType:'slide',
     	                 style:{  
		                     right:'',  
		                     top:document.body.scrollTop+document.documentElement.scrollTop,  
		                     bottom:''  
		                 }  
     	             });
     			     $('#'+tableId).datagrid("reload");
     			   }
     			});  
            }  
        });  
	}
}

//删除单个选选(树)
function deleteSelectTree(treeId,url,msg,delemsg){
	var rows = $('#'+treeId).tree('getSelected');
	if(rows == null || rows == ''){
		 $.messager.alert('提示','请选择'+msg,'warning');
	}else{
		$.messager.confirm('提示', delemsg+'你确定要删除吗？', function(r){  
            if (r){  
            	$.ajax({
     			   type: "POST",
     			   url: url + rows.id,
     			   success: function(data){
     			   data = $.parseJSON(data);
     			     $.messager.show({
     	                 title:'操作提示',
     	                 msg:data.message,
     	                 showType:'slide',
     	                 style:{  
		                     right:'',  
		                     top:document.body.scrollTop+document.documentElement.scrollTop,  
		                     bottom:''  
		                 }  
     	             });
     			     $('#'+treeId).tree("reload");
     			   }
     			});  
            }  
        });  
	}
}

//批量删除
function deleteSelectAll(tableId,url,msg){
	var rows = $('#'+tableId).datagrid('getSelections');
	if(rows == null || rows == ''){
		 $.messager.alert('提示','请选择'+msg,'warning');
	}else{
		var ids = '';  
        for(var i=0; i<rows.length; i++){  
            if (ids != '') ids += ',';  
            ids += rows[i].id;  
        } 
        $.messager.confirm('提示', '你确定要删除吗？', function(r){  
            if (r){  
				$.ajax({
				   type: "POST",
				   url: url+ids,
				   success: function(data){
				   data = $.parseJSON(data);
				     $.messager.show({  
		                 title:'操作提示',  
		                 msg:data.message,  
		                 showType:'slide',
		                 style:{  
		                     right:'',  
		                     top:document.body.scrollTop+document.documentElement.scrollTop,  
		                     bottom:''  
		                 }    
		             }); 
				     if(data.success){
				    	 $('#'+tableId).datagrid("reload");
				     }
				   }
				});
            }
        });
	}
}

//批量删除
function deleteSmsSelectAllBySerial(tableId,url,msg,serial){
	var rows = $('#'+tableId).datagrid('getSelections');
	if(rows == null || rows == ''){
		 $.messager.alert('提示','请选择'+msg,'warning');
	}else{
		var ids = '';  
        for(var i=0; i<rows.length; i++){  
            if (ids != '') ids += ',';  
            ids += rows[i].id;  
        } 
        $.messager.confirm('提示', '你确定要删除吗？', function(r){  
            if (r){  
				$.ajax({
				   type: "POST",
				   url: url+ids+"/"+serial,
				   success: function(data){
				   data = $.parseJSON(data);
				     $.messager.show({  
		                 title:'操作提示',  
		                 msg:data.message,  
		                 showType:'slide',
		                 style:{  
		                     right:'',  
		                     top:document.body.scrollTop+document.documentElement.scrollTop,  
		                     bottom:''  
		                 }    
		             }); 
				     $('#'+tableId).datagrid("reload");
				   }
				});
            }
        });
	}
}

//删除异步树单个选项
function deleteSelectAsynTree(treeId,url,msg,delemsg){
	var rows = $('#'+treeId).tree('getSelected');
	if(rows == null || rows == ''){
		 $.messager.alert('提示','请选择'+msg,'warning');
	}else{
		$.messager.confirm('提示', delemsg+'你确定要删除吗？', function(r){  
            if (r){  
            	$.ajax({
     			   type: "POST",
     			   url: url + rows.id,
     			   success: function(data){
     				  data = $.parseJSON(data);
     			     $.messager.show({
     	                 title:'操作提示',
     	                 msg:data.message,
     	                 showType:'slide',
     	                 style:{  
		                     right:'',  
		                     top:document.body.scrollTop+document.documentElement.scrollTop,  
		                     bottom:''  
		                 }  
     	             });
     			     if(data.success){
     			    	var node = $('#'+treeId).tree("getSelected");
     			       $('#'+treeId).tree('remove', node.target);  
     			     }
     			   }
     			});  
            }  
        });  
	}
}

//批量操作
function selectAll(tableId,url,msg){
	var rows = $('#'+tableId).datagrid('getSelections');
	if(rows == null || rows == ''){
		 $.messager.alert('提示','请选择选项','warning');
	}else{
		var ids = '';  
        for(var i=0; i<rows.length; i++){  
            if (ids != '') ids += ',';  
            ids += rows[i].id;  
        } 
        $.messager.confirm('提示', msg, function(r){  
            if (r){  
				$.ajax({
				   type: "POST",
				   url: url+ids,
				   success: function(data){
				   data = $.parseJSON(data);
				     $.messager.show({  
		                 title:'操作提示',  
		                 msg:data.message,  
		                 showType:'slide',
		                 style:{  
		                     right:'',  
		                     top:document.body.scrollTop+document.documentElement.scrollTop,  
		                     bottom:''  
		                 }    
		             }); 
				     if(data.success){
				    	 $('#'+tableId).datagrid("reload");
				     }
				   }
				});
            }
        });
	}
}

//清空表单
function clearForm(formId){
	var form = document.forms[formId].elements;
    for(var i=0;i<form.length;i++){
        if(form[i].type != 'hidden') {
            form[i].value = '';
        }
    }
}

//封装是否禁用按钮
function buttonDisabled(buttonId,disabled){
	$('#'+buttonId).linkbutton({  
		disabled: disabled 
	});
}

//展开折叠树
function collapseExpand(treeId,value){
	$('#'+treeId).tree(value);  
}
//重置时间表单
function resetTimeText(beginTimeId,endTimeId){
	$('#'+beginTimeId).attr("value","");
	$('#'+endTimeId).attr("value","");
}

