<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/multiple.css"/>
<div id="hotTool">
	<a href="javascript:void(0)" onclick="insertHot()"
	   class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
</div>
<table id="hotTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/pc/view/hot/hotList',toolbar:'#hotTool',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'name'" width="80" align="center">热门推荐名称</th>
			<th data-options="field:'null'" width="100" align='center'
				formatter="operateByFeed">操作</th>
		</tr>
	</thead>
</table>

<div id="hotWindow" class="easyui-window" title="热门推荐修改"
	style="width: 700px; height: 300px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
	<div class="easyui-form" id="HotForm" data-options="region:'center',split:true">
		<input id="sid" name="sid" type="hidden">
		<div style="padding: 10px;">
			<table style="width: 640px; ">
				<tr>
					<td>
						<fieldset name="fieldset28">
							<legend>
								<span class="legend">热门推荐修改</span>
							</legend>
                        <table>
							<tr>
								<td width="15%" height="28" align="right" bgcolor="#ececec"><span style="font-size: 15px; color: rgb(2, 48, 97);">推荐名称：</span></td>
								<td width="15%">
									<input class="easyui-validatebox" id="sname" name="name">
								</td>
							</tr>
                            <tr>
                                <td width="15%" height="28" align="right" bgcolor="#ececec"><span style="font-size: 15px; color: rgb(2, 48, 97);">商品搜索：</span></td>
                                <td width="15%"><input id="searchHot" onkeyup="searchHot();"/></td>
                            </tr>
							<tr>
								<td width="15%" height="28" align="right" bgcolor="#ececec"><span style="font-size: 15px; color: rgb(2, 48, 97);">相关商品：</span></td>
								<td width="15%"><div class="content" style="width:100%;text-align: -webkit-center;" >
									<select multiple="multiple" id="select4" style="width: 100%;float: left;">
									</select>
									<span id="add" style="width:100%;float: left;">选中右移>></span> <%--<span id="add_all">全部右移>></span>--%></div></td>

								<td width="15%" height="28" align="right" bgcolor="#ececec"><span style="font-size: 15px; color: rgb(2, 48, 97);">已选商品：</span></td>
								<td width="100%" ><div class="content" style="width: 100%;">
									<select multiple="multiple" id="select3" style="width: 100%;">
									</select>
									<span id="remove">选中左移>></span><%--<span id="remove_all">全部左移>></span>--%>
								</div></td>
							</tr>
						</table>
						</fieldset>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		 style="text-align: center; padding: 5px 0 5px;">

		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		   href="javascript:void(0)" onclick="confirmHot();">确定</a>

		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
		   href="javascript:void(0)" onclick="closeHotWindow();">返回</a>

	</div>
</div>


<div id="hotInsertWindow" class="easyui-window" title="热门推荐添加"
	 style="width: 450px; height: 180px"
	 data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
	<div class="easyui-form" id="HotInsertForm" data-options="region:'center',split:true">
		<div style="padding: 10px;">
			<table style="width: 440px; ">
				<tr>
					<td>
						<fieldset name="fieldset27">
							<legend>
								<span class="legend">热门推荐添加</span>
							</legend>
			<table>

				<tr>
					<td width="25%" height="28" align="right" bgcolor="#ececec">
						<span style="font-size: 15px; color: rgb(2, 48, 97);">推荐名称：</span>
					</td>

					<td>
						<input id="tname" class="easyui-validatebox">
					</td>
				</tr>

			</table>
						</fieldset>
					</td>
				</tr>
			</table>

		</div>
	</div>
	<div data-options="region:'south',border:false"
		 style="text-align: center; padding: 5px 0 5px;">

		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		   href="javascript:void(0)" onclick="insertHotForm();">确定</a>

		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
		   href="javascript:void(0)" onclick="closeHotFormWindow();">返回</a>

	</div>
</div>
<script type="text/javascript" src="${ctx}/js/multiple2.js"></script>
<script type="text/javascript">
    function operateByFeed(val, row) {
        var rowId = row.id;
        return " <a onclick=\'editHot("
            + rowId
            + ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'热门推荐编辑\' title=\'热门推荐编辑\' /></a> &nbsp; &nbsp; <a onclick=\'deleteHot("
            + rowId
            + ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'热门推荐删除\' title=\'热门推荐删除\' />";
    }

    function deleteHot(rowId){
        if (rowId == null || rowId == '') {
            $.messager.alert('温馨提示', '请选择要删除的热门推荐信息！', 'warning');
        } else {
            $.messager
                .confirm(
                    '温馨提示',
                    '你确定要删除选中的热门推荐信息吗？',
                    function(r) {
                        if (r) {
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/pc/view/hot/delete?id="
                                + rowId,
                                success : function(data) {
                                    data = $.parseJSON(data);
                                    $.messager
                                        .show({
                                            title : '操作提示',
                                            msg : data.msg,
                                            showType : 'slide',
                                            style : {
                                                right : '',
                                                top : document.body.scrollTop
                                                + document.documentElement.scrollTop,
                                                bottom : ''
                                            }
                                        });
                                    $('#hotTable').datagrid("reload");
                                    $('#hotTable').datagrid('clearSelections');
                                }
                            });
                        }
                    });
        }
        $('#hotTable').datagrid('clearSelections');
    }


    function editHot(rowId){
        $('#select3').html('');
        $.post('${ctx}/pc/view/hot/getAllProductOptionByHotId',{hid:rowId},function (data) {

            $('#select3').append(data);
        });

        $.post('${ctx}/pc/view/hot/detail',{id:rowId},function (data) {
            var pdata = $.parseJSON(data);
            $('#sid').val(pdata.id);
            $('#sname').val(pdata.name);
        });

        var purl='${ctx}/pc/view/hot/getProductList';
        $.post(purl,function(data) {
           var len =data.length;
           var opts='';
           for(var i=0;i<len;i++){
              opts+= '<option value='+data[i].id+'>'+data[i].name+'</option>';
		   }
		   $('#select4').html(opts);
        },"json");

        $('#hotWindow').window('open');
    }

    function confirmHot(){

        var id = $('#sid').val();
        var sname= $('#sname').val();
        if (sname == ''){
            $.messager.alert("提示","推荐名称不能为空","warning");
            return;
        }
      /*  if (val == '' || val==null ){
            $.messager.alert("提示","选择商品不能为空","warning");
            return;
        }*/
        var ids='';
        $('#select3 option').each(function(){
            var val = $(this).val();
            ids += val+",";
        });
        $.post('${ctx}/pc/view/hot/save',{"rid":id,"pids":ids,"name":sname},function(data){
            var pdata= $.parseJSON(data);
            $('#hotWindow').window('close');
            $.messager
                .show({
                    title : '操作提示',
                    msg : pdata.msg,
                    showType : 'slide',
                    style : {
                        right : '',
                        top : document.body.scrollTop
                        + document.documentElement.scrollTop,
                        bottom : ''
                    }
                });
            $('#hotTable').datagrid("reload");

        });

    }

    function closeHotWindow(){

        $('#hotWindow').window('close');
    }

    function insertHot() {
        $('#tname').val('');
        $('#hotInsertWindow').window('open');
    }
    function closeHotFormWindow(){
        $('#hotInsertWindow').window('close');
	}
    //添加热门推荐
    function insertHotForm(){
          var sname = $('#tname').val();
          if (sname==''){
              $.messager.alert("提示","推荐名称不能为空","warning");
              return;
		  }
        $.post('${ctx}/pc/view/hot/insertHot',{name:sname},function(data){
            var pdata= $.parseJSON(data);
            $('#hotInsertWindow').window('close');
            $.messager
                .show({
                    title : '操作提示',
                    msg : pdata.msg,
                    showType : 'slide',
                    style : {
                        right : '',
                        top : document.body.scrollTop
                        + document.documentElement.scrollTop,
                        bottom : ''
                    }
                });
            $('#hotTable').datagrid("reload");

        });
	}

	function searchHot(){
        var name = $('#searchHot').val();
        var url ='${ctx}/pc/view/hot/getProductOptions';
        $.post(url,{"name":name},function(data){
             $('#select4').text('');
             $('#select4').html(data);
		});
	}
</script>