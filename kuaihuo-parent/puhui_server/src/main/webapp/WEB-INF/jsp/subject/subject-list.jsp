<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/multiple.css"/>
<div id="subjectTool">
	<a href="javascript:void(0)" onclick="insertSubject()"
	   class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
</div>

<table id="subjectTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,toolbar:'#subjectTool',singleSelect:false,url:'${ctx}/pc/view/subject/subject-list-data',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'name'" width="80" align="center">专题名称</th>
			<th  width="80" align="center"  data-options="field:'address',formatter: function(value,row,index){
							return '<img width=16 height=16 src=\'${ctx_ali}/'+row.address+'\'/>';
				}" align="center" width="40">图片</th>
			<th data-options="field:'null'" width="100" align='center'
				formatter="operateByFeed">操作</th>
		</tr>
	</thead>
</table>

<div id="subjectWindow" class="easyui-window"
	title="专题修改" style="width: 700px; height: 400px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
	<div class="easyui-form" id="subjectForm" data-options="region:'center',split:true">
		<input id="subjectId" name="subjectId" type="hidden">
		<input type="hidden" id="updateSubjectFileId">
		<table style="width: 700px; ">
			<tr>
				<td>
					<fieldset name="fieldset27">
						<legend>
							<span class="legend">专题修改</span>
						</legend>
		<table>
			<tr>
				<td width="15%" height="28" align="right" bgcolor="#ececec">
					<span style="font-size: 15px; color: rgb(2, 48, 97);">专题名称：</span>
				</td>
				<td>
					<input  id="subjectName" name="name">
				</td>
			</tr>

			<tr>
				<td width="15%" height="28" align="right" bgcolor="#ececec">
					<span style="font-size: 15px; color: rgb(2, 48, 97);">专题图片：</span></td>
				<td>
					<input type="file" id="updateSubjectFile" name="address">
					<img width=16 height=16 id="pictureurl" alt="" src=""></td>

			</tr>
			<%--一级行业--%>
			<tr>
				<td width="15%" height="28" align="right" bgcolor="#ececec">
					<span style="font-size: 15px; color: rgb(2, 48, 97);">一级行业：</span></td>
				<td><input class="easyui-combobox" id="first" ></td>
			</tr>

			<%--一级行业--%>
			<tr>
				<td width="15%" height="28" align="right" bgcolor="#ececec"><span style="font-size: 15px; color: rgb(2, 48, 97);">二级行业：</span></td>
				<td><input class="easyui-combobox" id="second" ></td>
			</tr>

			<tr>
				<td width="15%" height="28" align="right" bgcolor="#ececec"> <span style="font-size: 15px; color: rgb(2, 48, 97);">批发商搜索：</span></td>
				<td><input width="20" id="searchHunter" onkeyup="searchHunter()"/></td>
			</tr>

			<tr>

				<td width="15%" height="28" align="right" bgcolor="#ececec"><span style="font-size: 15px; color: rgb(2, 48, 97);">相关批发商：</span></td>
				<td width="15%" height="28" align="right" bgcolor="#ececec"><div class="content" style="width:100%;" >
					<select multiple="multiple" id="select1" style="width:100%;">
					</select>
					<span id="add" style="float:left;">选中右移>></span> <%--<span id="add_all">全部右移>></span>--%></div></td>

				<td width="15%" height="28" align="right" bgcolor="#ececec"><span style="font-size: 15px; color: rgb(2, 48, 97);">已选择批发商：</span></td>
				<td><div class="content" style="width:100%;">
					<select multiple="multiple" id="select2" style="width:100%;" onclick="getIndustryInfo();">
					</select>
					<span id="remove" style="float:left;">选中左移>></span><%--<span id="remove_all">全部左移>></span>--%>
				</div></td>
			</tr>
		</table>
					</fieldset>
				</td>
			</tr>
		</table>

	</div>
	<div data-options="region:'south',border:false"
		 style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		   href="javascript:void(0)" onclick="confirmSubject();">确定</a>

		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
		   href="javascript:void(0)" onclick="closeSubjectWindow();">返回</a>


	</div>
</div>

<div id="subjectInsertWindow" class="easyui-window" title="专题分类添加"
	 style="width: 400px; height: 220px"
	 data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">

	<div class="easyui-form" id="subjectInsertForm" data-options="region:'center',split:true">
		<input type="hidden" id="subjectFileId">
		<table style="width: 400px; ">
			<tr>
				<td>
					<fieldset name="fieldset26">
						<legend>
							<span class="legend">专题添加</span>
						</legend>
						<table>
							<tr>
								<td width="25%" height="28" align="right" bgcolor="#ececec">
									<span style="font-size: 15px; color: rgb(2, 48, 97);" >专题名称：</span>
								</td>

								<td>
									<input class="easyui-validatebox" id="sbname" >
								</td>
							</tr>

							<tr>
								<td width="25%" height="28" align="right" bgcolor="#ececec">
									<span style="font-size: 15px; color: rgb(2, 48, 97);">专题图片：</span>
								</td>

								<td>
									<input type="file" name="urlfile"
										   id="subjectFile" />
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
		<div style="padding: 10px;">




		</div>
	</div>
	<div data-options="region:'south',border:false"
		 style="text-align: center; padding: 5px 0 5px;">

		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
		   href="javascript:void(0)" onclick="saveSubject();">确定</a>

		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
		   href="javascript:void(0)" onclick="closeSaveSubjectWindow();">返回</a>

	</div>
</div>
<script type="text/javascript" src="${ctx}/js/multiple.js"></script>
<script type="text/javascript">
    $(function() {
        $("#subjectFile").uploadify({
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
                $("#subjectFileId").val(json.id);
               // $("#pictureurl").attr('src','${ctx_ali}/'+json.address);
            }
        });


        $("#updateSubjectFile").uploadify({
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
                $("#updateSubjectFileId").val(json.id);
                // $("#pictureurl").attr('src','${ctx_ali}/'+json.address);
            }
        });
    });
    function operateByFeed(val, row) {
        var rowId = row.id;
        return " <a onclick=\'editSubject("
            + rowId
            + ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'专题分类编辑\' title=\'专题分类编辑\' /></a> &nbsp; &nbsp; <a onclick=\'deleteSubject("
            + rowId
            + ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'专题分类删除\' title=\'专题分类删除\' />";
    }

    function deleteSubject(rowId){
        if (rowId == null || rowId == '') {
            $.messager.alert('温馨提示', '请选择要删除的专题分类信息！', 'warning');
        } else {
            $.messager
                .confirm(
                    '温馨提示',
                    '你确定要删除选中的专题分类信息吗？',
                    function(r) {
                        if (r) {
                            $.ajax({
                                type : "POST",
                                url : "${ctx}/pc/view/subject/delete?id="
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
                                    $('#subjectTable').datagrid("reload");
                                    $('#subjectTable').datagrid('clearSelections');
                                }
                            });
                        }
                    });
        }
        $('#subjectTable').datagrid('clearSelections');
    }

    //修改专题
    function editSubject(rowId){

        $('#first').val('');
        $('#second').combobox('setValue','');
        $('#select1').html('');
        $('#select2').html('');
        $.post('${ctx}/pc/view/subject/getAllHunterOptions',{sid:rowId},function (data) {
            $('#select2').append(data);
        });
        $.post('${ctx}/pc/view/subject/detail',{id:rowId},function (data) {
            var pdata = $.parseJSON(data);
            $('#subjectId').val(pdata.id);
            $('#subjectName').val(pdata.name);
            $('#updateSubjectFileId').val(pdata.aid);
            $('#pictureurl').attr('src','${ctx_ali}/'+pdata.address);
        });
        $('#first').combobox({
            url:'${ctx}/pc/view/subject/getIndustryType',
            valueField:'id',
            textField:'name',
            onSelect:function(re){
                var id =re.id;
                $('#second').combobox({
                    url:'${ctx}/pc/view/subject/getIndustryType?pid='+id,
                    valueField:'id',
                    textField:'name',
                    onSelect:function (rw) {
                        var sid = rw.id;
                        $('#select1').html('');
                        var url ='${ctx}/pc/view/subject/getHunterByIndustryType';
                        $.post(url,{tid:sid},function(hdata){
                            var pdata = $.parseJSON(hdata);
                            var len = pdata.length;
                            var opts='';
                            for(var i=0;i<len;i++){
                              opts+='<option value='+pdata[i].id+'>'+pdata[i].name+'</option>';
							}
                            $('#select1').append(opts);
						});
                    }
                });
            }
        });


        $('#subjectWindow').window('open');
    }

    function confirmSubject(){

        var subjectid = $('#subjectId').val();
        var subjectName= $('#subjectName').val();
        var fileId=$('#updateSubjectFileId').val();

        if (subjectName == '' || subjectName ==null ){
            $.messager.alert("提示","专题名称不能为空","warning");
            return;
        }
        var ids='';
        $('#select2 option').each(function(){
            var val = $(this).val();
            ids += val+",";
        });
/*
        if (hunterVal == '' || hunterVal==null){
            $.messager.alert("提示","已选择批发商不能为空","warning");
            return;
        }
*/

        $.post('${ctx}/pc/view/subject/save',{id:subjectid,hids:ids,name:subjectName,aid:fileId},function(data){
            var pdata= $.parseJSON(data);
            closeSubjectWindow();
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
            $('#subjectTable').datagrid("reload");
        });


    }

    function closeSubjectWindow(){
        $('#subjectWindow').window('close');
    }

   function insertSubject() {
	   $('#subjectInsertWindow').window('open');
   }

   function closeSaveSubjectWindow() {
       $('#subjectInsertWindow').window('close');
   }

    //添加热门推荐
    function saveSubject(){
        var sname = $('#sbname').val();
        if (sname==''){
            $.messager.alert("提示","专题名称不能为空","warning");
            return;
        }
        var fileId = $('#subjectFileId').val();
        /*if (fileId==''){
            $.messager.alert("提示","附件上传失败","warning");
            return;
        }*/
        $.post('${ctx}/pc/view/subject/insertSubject',{name:sname,aid:fileId},function(data){
            var pdata= $.parseJSON(data);
            $('#subjectInsertWindow').window('close');
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
            $('#subjectTable').datagrid("reload");

        });
    }

    function searchHunter(){
       var sid =$('#subjectId').val();
       var searchHunter=$('#searchHunter').val();
       var industryTypeId =$('#second').combobox('getValue');
       if (industryTypeId==null || industryTypeId==''){
          $.messager.alert('警告','请二级行业分类','warning');
          return;
	   }
       var url = '${ctx}/pc/view/subject/getHunterOptions';
       $.post(url,{"name":searchHunter,"inId":industryTypeId},function(data){
             $('#select1').text('');
             $('#select1').append(data);
	   });
	}

	function getIndustryInfo(){
        var val = $('#select2').val();
        if (val == null || val==''){
            return ;
		}
        var $v = val[0];
        var url ='${ctx}/pc/view/subject/getIndustryInfo';
        $.post(url,{hid:$v},function(data){
            var code = data.code;
            if (code==1){
                var content = data.content.id;
                var ids = content.split(",");
                var pid = ids[0];
                var id = ids[1];
                $('#first').combobox('setValue',pid);
                $('#second').combobox({
                    url:'${ctx}/pc/view/subject/getIndustryType?pid='+pid,
                    valueField:'id',
                    textField:'name',
                    onSelect:function (rw) {
                        var sid = rw.id;
                        $('#select1').html('');
                        var url ='${ctx}/pc/view/subject/getHunterByIndustryType';
                        $.post(url,{tid:sid},function(hdata){
                            var pdata = $.parseJSON(hdata);
                            var len = pdata.length;
                            var opts='';
                            for(var i=0;i<len;i++){
                                opts+='<option value='+pdata[i].id+'>'+pdata[i].name+'</option>';
                            }
                            $('#select1').append(opts);
                        });
                    }
                });
                $('#second').combobox('setValue',id);
                var url ='${ctx}/pc/view/subject/getHunterByIndustryType';
                $.post(url,{tid:id},function(hdata){
                    $('#select1').html('');
                    var pdata = $.parseJSON(hdata);
                    var len = pdata.length;
                    var opts='';
                    for(var i=0;i<len;i++){
                        opts+='<option value='+pdata[i].id+'>'+pdata[i].name+'</option>';
                    }
                    $('#select1').append(opts);
                });
			} else {
                var txt = data.msg;
                $.messager.alert('错误',txt,"error");
			}
		},"json");

	}
</script>