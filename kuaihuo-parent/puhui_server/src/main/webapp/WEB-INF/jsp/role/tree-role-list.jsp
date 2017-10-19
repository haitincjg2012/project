<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<div style="margin:10px 0;">
        <a href="#" class="easyui-linkbutton" onclick="getChecked()">提交</a> 
        <br/>
    </div>
    <ul id="tttt" class="easyui-tree" data-options="url:'${ctx}/role/view-role-tree?roleId=${roleId}',method:'get',animate:true,checkbox:true"></ul>
    <script type="text/javascript">
        function getChecked(){
            var nodes = $('#tttt').tree('getChecked');
            var s = '';
            for(var i=0; i<nodes.length; i++){
                if (s != '') s += ',';
                s += nodes[i].id;
            }
            $.get("${ctx}/role/submit-resource?roleId=${roleId}&ids="+s,function(data){
            	data = $.parseJSON(data);
	        	 $.messager.show({
	                 title:'操作提示',
	                 msg:data.msg,
	                 showType:'show'
	             });
	        	 if(data.code==1){
	          		  $('#tttt').datagrid("reload");
	        	 }
            });
        }
    </script>