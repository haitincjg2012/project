<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<style>
	.datagrid-cell>img{
	 		width: 48px;
	 	  height:43px;
  		  margin: 5px;
	 }
</style>
<script type="text/javascript">
	//用户添加修改窗口
	function openUserWindow(title, url, type) {
		if (type == 'edit') {
			var rows = $('#adsTable').datagrid('getSelected');
			if (rows == null) {
				$.messager.alert('温馨提示', '请选择用户');
			} else {
				$('#adshome').window({
					title : title
				});
				$('#adshome').window('open');
				$('#adshome').window('refresh', url + '?id=' + rows.id);
				$('#adsTable').datagrid('clearSelections');
			}
		} else if (type == 'add') {
			$('#adshome').window({
				title : title,
				width:1300,
			    height:600
			});
			$('#adshome').window('open');
			$('#adshome').window('refresh', url);
			$('#adsTable').datagrid('clearSelections');
		}
	}
	
	var easyuiPanelOnOpen = function (left, top) {
	    var iframeWidth = $(this).parent().parent().width();
	   
	    var iframeHeight = $(this).parent().parent().height();

	    var windowWidth = $(this).parent().width();
	    var windowHeight = $(this).parent().height();

	    var setWidth = (iframeWidth - windowWidth) / 2;
	    var setHeight = (iframeHeight - windowHeight) / 2;
	    $(this).parent().css({/* 修正面板位置 */
	        left: setWidth,
	        top: setHeight
	    });

	    if (iframeHeight < windowHeight)
	    {
	        $(this).parent().css({/* 修正面板位置 */
	            left: setWidth,
	            top: 0
	        });
	    }
	    $(".window-shadow").hide();
	};
	$.fn.window.defaults.onOpen = easyuiPanelOnOpen;
	//删除、修改 用户信息
	 function operateUser(val, row) {
		var rowId = row.id;
		/*  return " <security:authorize ifAnyGranted="SCROLL_IMG_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByUserId("
				+ rowId
				+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'图片删除\' title=\'图片删除\' /></a></security:authorize>";
	}   */
	/* return "<security:authorize ifAnyGranted="SCROLL_IMG_UPDATE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updatePictureHeaderById("
	+ rowId
	+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'轮播图查看header修改\' title=\'轮播图查看header修改\' /></a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="SCROLL_IMG_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByUserId("
	+ rowId
	+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'轮播图删除\' title=\'轮播播图删除\' /></a></security:authorize>";
  } */
  return "<security:authorize ifAnyGranted="SCROLL_IMG_UPDATE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updatePictureHeaderById("
	+ rowId
	+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'轮播图查看header修改\' title=\'轮播图查看header修改\' /></a> &nbsp; &nbsp;</security:authorize> <security:authorize ifAnyGranted="SCROLL_IMG_DELETE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'deleteByUserId("
	+ rowId
	+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_del.png\' alt=\'轮播播图删除\' title=\'轮播播图删除\' /> &nbsp; &nbsp;</a></security:authorize> <security:authorize ifAnyGranted="SCROLL_IMG_CODE"><a class=\'easyui-linkbutton\' iconCls=\'icon-remove\' onclick=\'updateCodeByUserId("
	+ rowId
	+ ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_edit.png\' alt=\'图片源码修改\' title=\'图片源码修改\' /></a></security:authorize>"
	;
}
	//删除用户信息
	function deleteByUserId(rowId) {
		if (rowId == null || rowId == '') {
			$.messager.alert('温馨提示', '请选择要删除轮播图图片！', 'warning');
		} else {
			$.messager
					.confirm(
							'温馨提示',
							'你确定要删除选中的图片？',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : "${ctx}/pc/after/adshome/delete?id="
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
													$('#adsTable').datagrid("reload");
													$('#adsTable').datagrid('clearSelections');
												}
											});
								}
							});
		}
		$('#adsTable').datagrid('clearSelections');
	}

	//用户修改 header
	function updatePictureHeaderById(rowId) {
		if (rowId == null) {

			
			$.messager.alert('温馨提示', '请选择用户信息', 'warning');
		} else {
			$('#adshome').window({
				title : '图片标题',
				//调控框的大小
				width:498,
			    height:380
			});
			$('#adshome').window('open');
			$('#adshome').window('refresh',
					'${ctx}/pc/after/adshome/update?id=' + rowId);
			
			$('#adsTable').datagrid('clearSelections');
		}
	}
	//详情图片的修改
	/* function updatePictureDetailById(rowId){
		if(rowId == null){
			$.message.alert('温馨提示','请选择用户信息','warning');
		}else{
			$('#adshome').window({
				title : '图片标题'
			});
			$('#adshome').window('open');
			$('#adshome').window('refresh',
					'${ctx}/pc/after/adshome/update?id=' + rowId);
			
			$('#adsTable').datagrid('clearSelections');
		}
	} */
    //修改图片的源码
    function updateCodeByUserId(rowId){
	if (rowId == null) {

			
			$.messager.alert('温馨提示', '请选择用户信息', 'warning');
		} else {
			$('#adscode').window({
				title : '图片标题',
				//调控框的大小
				width:1200,
			    height:460
			});
			$('#adscode').window('open');
			$('#adscode').window('refresh',
					'${ctx}/pc/after/adshome/code?id=' + rowId);
			
			$('#adsTable').datagrid('clearSelections');
		}
    }
</script>
<!-- 返回添加图片后的数据 -->
<table id="adsTable" class="easyui-datagrid" border="0" fit="true"
	data-options="fitColumns:true,idField:'id',method:'get',pageList:[20,30,50],rownumbers:true,singleSelect:false,url:'${ctx}/pc/after/adshomedata/ads-list-data',toolbar:'#adsAdd',pagination:true">
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true">id</th>
			<th data-options="field:'header'" width="30" align="center">图片标题名</th>
			<th  heiht="20px" data-options="field:'address',formatter: function(value,row,index){
							return '<img src=\'${ctx_ali}/'+row.address+'\'/>';
				}" align="center" width="40">图片</th>
			<th data-options="field:'null'" width="20" align='center'
				formatter="operateUser">操作</th>
		</tr>
	</thead>
</table>  
<!-- <div id="adsAdd" style="padding: 5px; height: auto"> -->
<!--添加数据图片弹出框 -->
<div id="adsAdd" style="padding: 5px; height: auto">
	<security:authorize ifAnyGranted="SCROLL_IMG_ADD">
		<a href="javascript:void(0)"
			onclick="openUserWindow('图片添加','${ctx}/pc/after/adshome/uploadurl','add')"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加数据</a>
	</security:authorize>
</div>
<!-- 修改header弹框 -->	
<div id="adshome" class="easyui-window"
	style="width: 500px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
<!-- 修改源码弹出弹框 -->
<div id="adscode" class="easyui-window"
	style="width: 500px; height: 380px"
	data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
</div>
