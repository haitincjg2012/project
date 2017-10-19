<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- <link rel="icon" href="${ctx}/themes/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx}/themes/favicon.ico"
	type="image/x-icon" />
<link rel="bookmark" href="${ctx}/themes/favicon.ico"
	type="image/x-icon" /> --%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%--	<link rel="stylesheet" type="text/css"
        href="${ctx}/javascript/plug-in/accordion/css/accordion.css">
    <link rel="stylesheet" type="text/css"
        href="${ctx}/javascript/themes/ui-cupertino/easyui.css">


<link rel="stylesheet" type="text/css"
	href="${ctx}/javascript/themes/main/style.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/javascript/themes/icon.css">--%>


<!-- 12月13日更新一个文件 -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/uploadfy/uploadify.css" />

	<%--<link rel="stylesheet" type="text/css" href="${ctx}/javascript/themes/default/easyui.css" >--%>

	<link rel="stylesheet" type="text/css" href="${ctx}/insdep/easyui.css">
	<%--<link rel="stylesheet" type="text/css" href="${ctx}/insdep/easyui_animation.css">--%>

	<%--<link rel="stylesheet" type="text/css" href="${ctx}/insdep/easyui_plus.css">--%>

	<%--<link rel="stylesheet" type="text/css" href="${ctx}/insdep/insdep_theme_default.css">--%>
	<link rel="stylesheet" type="text/css" href="${ctx}/insdep/icon.css">

	<script type="text/javascript"
			src="${ctx}/javascript/commons/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/javascript/backcontrol/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/insdep/jquery.insdep-extend.min.js"></script>



	<script type="text/javascript" src="${ctx}/js/jedate/jquery.jedate.js"></script>
	<script type="text/javascript" src="${ctx}/js/select2/js/select2.full.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/bankAddr.js"></script>
	<script type="text/javascript"
			src="${ctx}/javascript/commons/curdtools.js"></script>
	<script type="text/javascript"
			src="${ctx}/javascript/commons/lhgDialog/lhgdialog.min.js"></script>
<%--
<script type="text/javascript"
	src="${ctx}/javascript/jqueryui/jquery.easyui.min.js"></script>
--%>



	<script type="text/javascript"
	src="${ctx}/javascript/backcontrol/jquery.function.js"></script>
<script type="text/javascript"
	src="${ctx}/javascript/backcontrol/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${ctx}/javascript/fileupload/ajaxfileupload.js"></script>


<!-- 日期控件 -->
<script type="text/javascript"
	src="${ctx}/javascript//My97DatePicker/WdatePicker.js"></script>
<!-- 12月13日更新两个上传文件 -->
<script type="text/javascript" src="${ctx}/uploadfy/jquery.uploadify.js"></script>
<script type="text/javascript"
	src="${ctx}/uploadfy/jquery.uploadify.min.js"></script>
<!--echarts 图表-->
<script type="text/javascript" src="${ctx}/echarts/esl.js"></script>
<!-- ckeditor 文本编辑器 -->
<script type="text/javascript" src="${ctx }/js/ckeditor/ckeditor.js"></script>
 <script type="text/javascript" src="${ctx }/js/ckeditor/config.js"></script>
<!-- <title>卓讯信息技术有限公司二次开发平台</title> -->
<title>北京易商通科技股份有限公司</title>
<style>
  .panel {
    float: left;
    font-size: 12px;
    margin-right: 30px;
    overflow: hidden;
    text-align: left;
}

#online .panel-body{width:auto !important;}
</style>
<script type="text/javascript">
	//修改用户密码窗口
	function openuserinfoInfoWindow(title, url) {
		$('#edituserinfoWindow').window({
			title : title
		});
		$('#edituserinfoWindow').window('open');
		$('#edituserinfoWindow').window('refresh', url);
	}

	//查看公告
	function openmainnoticeWindow(title, url) {
		$('#noticeinfoWindow').window({
			title : title
		});
		$('#noticeinfoWindow').window('open');
		$('#noticeinfoWindow').window('refresh', url);
	}

	//页面跳转
	function toPages(title, url) {
		if ($('#tabs').tabs('exists', title)) {//如果tab已经存在,则选中并刷新该tab          
			$('#tabs').tabs('select', title);
			var tab = $('#tabs').tabs('getSelected'); // get selected panel
			$('#tabs').tabs('update', {
				tab : tab,
				options : {
					title : title,
					href : url
				// the new content URL
				}
			});
		} else {
			$('#tabs').tabs('add', {
				title : title,
				href : url,
				closable : true
			});
		}
	}

	$(function() {

		$.ajaxSetup({
			//关闭AJAX相应的缓存
			cache : false,
		});

		$.extend($.messager.defaults, {
			ok : "确定",
			cancel : "取消"
		});

		$('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});
	});
	//禁止左键选择拷贝

	//document.oncontextmenu=new Function('event.returnValue=false;');
	//document.onselectstart=new Function('event.returnValue=false;');
	/**
	 * 时间控件
	 */
	function myformatterYmd(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);//年月日
	}
	/**
	 * 时间控件
	 */
	function myformatterYmdhms(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();

		var h = date.getHours(); //hour  
		var n = date.getMinutes(); //minute  
		var s = date.getSeconds();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d) + ' ' + (h < 10 ? ('0' + h) : h)
				+ ':' + (n < 10 ? ('0' + n) : n) + ':' + '01';
	}
	function panelCollapase() {
		$(".easyui-layout").layout('collapse', 'north');
	}
	$(".shortcut li").live("click", function() {
		$(this).find(".imag1").hide();
		$(this).find(".imag2").show();
		$(this).siblings().find(".imag2").hide();
		$(this).siblings().find(".imag1").show();
	});
	function refresh(id) {
		$("#west").panel({
			region : 'west',
			href : '${ctx}/main/menu?oneMenuId='+id
		});
		$("#west").panel('refresh');
	}
</script>
<link rel="stylesheet" href="${ctx}/js/jedate/skin/jedate.css"> 
</head>
<body class="easyui-layout">
	<!-- 顶部-->
	<div region="north" border="false"
		style="BACKGROUND: #A8D7E9; height: 100px; padding: 1px; overflow: hidden;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr style="height: 25px;" align="right">
				<td align="left"
					style="vertical-align: middle; padding-left: 105px; padding-top: 10px;">
					<!-- <span style="font-size: 20px; font-weight: bold; color: red;">卓讯信息技术有限公司二次开发平台</span> -->
				 <span style="font-size: 40px; font-weight: bold; color: red;">北 京 易 商 通 科 技 股 份 有 限 公 司</span> 
				<td align="right" nowrap>
					<%-- 			<table>
						<tr>
							<td><span style="color: #CC33FF; padding-left: 20px;">当前用户:</span>
								<span style="color: #666633">(${user.userName})</span> <span
								style="color: #CC33FF">职务</span>: <span style="color: #666633">${user.role.descript
									}</span></td>
						</tr>
						<tr>
							<td>
								<div style="position: absolute; right: 0px; bottom: 0px;">
									<a href="javascript:void(0);" class="easyui-menubutton"
										menu="#layout_north_kzmbMenu" iconCls="icon-user">个人中心</a>
									<!-- <a	href="javascript:void(0);" class="easyui-menubutton"
									menu="#layout_north_zxMenu" iconCls="icon-back">注销登录</a> -->
									<a onclick="exit('${ctx}/logout','确定退出该系统吗 ?',1);"
										class="easyui-menubutton" iconCls="icon-logout">注销登录</a>
								</div>
								<div id="layout_north_kzmbMenu"
									style="width: 100px; display: none;">
									<div
										onclick="openuserinfoInfoWindow('个人信息','${ctx}/user/user-info')">个人信息</div>
									<div class="menu-sep"></div>
									<div
										onclick="openuserinfoInfoWindow('修改密码','${ctx}/user/user-password')">修改密码</div>
								</div> <div id="layout_north_zxMenu"
								style="width: 100px; display: none;">
								<div class="menu-sep"></div>
								<div onclick="exit('${ctx}/logout','确定退出该系统吗 ?',1);">退出系统</div>
							</div>
							</td>
						</tr>
					</table> --%>
					<table border="0" cellpadding="0" cellspacing="0">
						<tr style="height: 25px;" align="right">
							<td style="" colspan="2">
								<div
									style="background: url(${ctx}/themes/login/top_bg.jpg) no-repeat right center; float: right;">
									<div style="float: left; line-height: 25px; margin-left: 70px; margin-top: 5px;">
										<span style="color: #386780">当前用户:</span> <span
											style="color: #FFFFFF">${user.userName}</span>&nbsp;&nbsp;&nbsp;&nbsp;
										<span style="color: #386780">职务:</span> <span
											style="color: #FFFFFF">${user.role.descript}</span>
									</div>
									<div style="float: left; margin-left: 18px;">
										<div style="right: 0px; bottom: 0px;">
											<a href="javascript:void(0);" class="easyui-menubutton"
												menu="#layout_north_kzmbMenu" iconCls="icon-comturn"
												style="color: #FFFFFF">控制面板</a>&nbsp;&nbsp;<a
												href="javascript:void(0);" class="easyui-menubutton"
												menu="#layout_north_zxMenu" iconCls="icon-exit"
												style="color: #FFFFFF">注销</a>
										</div>
										<div id="layout_north_kzmbMenu"
											style="width: 100px; display: none;">
											<div
												onclick="openuserinfoInfoWindow('个人信息','${ctx}/user/user-info')">个人信息</div>
											<div class="menu-sep"></div>
											<div
												onclick="openuserinfoInfoWindow('修改密码','${ctx}/user/user-password')">修改密码</div>
										</div>
										<div id="layout_north_zxMenu"
											style="width: 100px; display: none;">
											<div onclick="exit('${ctx}/logout','确定退出该系统吗 ?',1);">退出系统</div>
										</div>
									</div>
									<div
										style="float: left; margin-left: 8px; margin-right: 5px; margin-top: 5px;">
										<img
											src="${ctx}/javascript/themes/default/images/layout_button_up.gif"
											style="cursor: pointer" onclick="panelCollapase()" />
									</div>
									<%--update-end--Author:JueYue  Date:20140616 for：首页上方可以折叠--%>
								</div>
							</td>
						</tr>
						<tr style="height: 80px;">
							<td colspan="2">
								<ul class="shortcut">
									<!-- 动态生成并赋值过来 -->
									<c:forEach items="${oneMenus}" var="oneMenu">
										<li><img onclick="refresh(${oneMenu.id})" class='imag1'
											src='${ctx}/themes/login/${oneMenu.imageName }.png' /><img class='imag2'
											src='${ctx}/themes/login/${oneMenu.imageName }_up.png'
											style='display: none;' /></li>
									</c:forEach>
								</ul>
							</td>
						</tr>
					</table>
				</td>

			</tr>
		</table>
	</div>
	<!-- 左侧-->
	<div id="west" region="west" split="true" href="${ctx}/main/menu"
		title="导航菜单" style="width: 150px; padding: 1px;"></div>
	<!-- 右侧 -->
	<div collapsed="true" region="east" iconCls="icon-reload" title="辅助工具"
		split="true" style="width: 190px;">
		<div id="calendar" class="easyui-tabs" border="false"
			style="height: 240px">
			<div title="日历" style="padding: 0px; overflow: hidden; color: red;">
				<div id="layout_east_calendar"></div>
			</div>
		</div>
		<div id="online" class="easyui-tabs" border="false">
			<div title="在线人员" align="center"
				style="padding: 20px; overflow: hidden; color: red;">
				<table>
					<c:forEach items="${userLogs}" var="userlog">
						<tr>
							<td>${userlog.loginName}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>

	</div>
	<!-- 底部 -->
	<!-- <div region="south" border="false"
		style="BACKGROUND: #E6E6FA; height: 25px; overflow: hidden;">
		<div align="center" style="color: #CC99FF; padding-top: 2px">
			&copy; 版权所有 <span class="tip"><a
				style="text-decoration: none;" href="http://www.alqsoft.com/"
			 title="厦门卓讯信息技术有限公司">厦门卓讯信息技术有限公司</a> </span> 
		</div>
	</div> -->
	 <div region="south" border="false"
		style="BACKGROUND: #E6E6FA; height: 25px; overflow: hidden;">
		<div align="center" style="color: #CC99FF; padding-top: 2px">
			&copy; 版权所有 <span class="tip"><a
				style="text-decoration: none;" 
			 title="北京易商通科技股份有限公司">北京易商通科技股份有限公司</a> </span> 
		</div>
	</div> 
	<!-- 中间-->
	<div id="mainPanle" region="center" style="overflow: hidden;">
		<div id="maintabs" class="easyui-tabs" fit="true" border="false">
			<div title="我的主页" style="padding: 30px" align="center">
				<%-- <div id="p" class="easyui-panel" title="最新公告"
					style="width: 600px; height: 400px; padding: 10px;">
					<ul>
						<c:forEach var="notice" items="${notices}">
							<li><a href="javascript:void(0)"
								onclick="openmainnoticeWindow('公告内容','${ctx}/notice/notice-info?id=${notice.id}')"
								class="easyui-linkbutton" plain="true">${notice.title}</a>-----------<fmt:formatDate
									value="${notice.pubdate}" type="both" /></li>
						</c:forEach>
					</ul>
				</div> --%>
					<c:if test="${constantMap!=null}">
						<c:if test="${constantMap.BDTJ_WEB_SHOW==true}">
							<div id="p" class="easyui-panel" title="百度web统计登录信息" style="width: 400px; height: 250px; padding: 10px; float:left;">
								<ul>
									<li><a href="http://tongji.baidu.com/web/welcome/login" class="easyui-linkbutton" plain="true" target="_blank">web统计登录地址</a></li>
									<li> 用户名： 太古粮家</li>
									<li> 密&nbsp;&nbsp;&nbsp;码： TaiGu2014</li>
								</ul>
							</div> 
						</c:if>
						
					<c:if test="${constantMap.BDTJ_APP_SHOW==true}">
						<div class="easyui-panel" title="百度客户端统计登录信息" style="width: 400px; height: 250px; padding: 10px; float:right;">
							<ul>
								<li><a href="http://mtj.baidu.com/web/welcome/login" class="easyui-linkbutton" plain="true" target="_blank">客户端统计登录地址</a></li>
								<li> 用户名： 太古粮家</li>
								<li> 密&nbsp;&nbsp;&nbsp;码： taigu2014</li>
							</ul>
						</div> 
					</c:if>
					</c:if>
				
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabupdate">刷新</div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	</div>
	<div id="userinfoWindow" class="easyui-window"
		style="width: 450px; height: 280px"
		data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
	</div>
	<div id="noticeinfoWindow" class="easyui-window"
		style="width: 650px; height: 380px"
		data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
	</div>
	<!-- 修改用户密码 -->
	<div id="edituserinfoWindow" class="easyui-window"
		style="width: 450px; height: 300px"
		data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
	</div>
</body>
</html>