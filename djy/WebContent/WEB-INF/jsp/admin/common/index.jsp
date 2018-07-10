<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="renderer" content="webkit">
	
	<%
		String basePath = request.getContextPath() + "/admin/";
	%>
	
	<title>首页</title>
	
	<!-- 全局js -->
	<script src="plugins/metisMenu/jquery.metisMenu.js?v=1.1.3" type="text/javascript"></script>
	<script src="plugins/slimscroll/jquery.slimscroll.min.js?v=1.3.0" type="text/javascript"></script>
	
	<!-- 自定义js -->
	<script src="frames/hplus/js/hplus.js?v=4.1.0" type="text/javascript"></script>
	<script src="frames/hplus/js/contabs.js" type="text/javascript"></script>
	
	<!-- pace插件，用于页面加载进度 -->
	<script src="plugins/pace/pace.min.js?v=0.5.1" type="text/javascript"></script>
	
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">

	<div id="wrapper">
		<!-- 左侧导航开始 -->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>
			</div>
			<div class="sidebar-collapse">
				<ul class="nav" id="side-menu">
					
					<li class="nav-header">
						<div class="dropdown profile-element">
							<span><img alt="image" class="img-circle" src="img/ico_portrait.png" style="padding-bottom:8px;"/></span>
							<span>${loginUser.sysRoleName}</span>
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<span class="clear">
									<span class="block m-t-xs"><strong class="font-bold">用户名：${loginUser.username}</strong></span>
								</span>
							</a>
						</div>
						<div class="logo-element">
							<i class="fa fa-arrow-right"></i>
						</div>
					</li>
					
					<li>
						<a class="J_menuItem" href="common/home.do">
							<i class="fa fa-home"></i>
							<span class="nav-label">主页</span>
						</a>
					</li>
					
					<%@include file="nav.jsp"%>
					
				</ul>
			</div>
		</nav>
		<!-- 左侧导航结束 -->
		
		<!-- 右侧部分开始 -->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0px; min-height:40px;">
					<div class="navbar-header">
						<span class="navbar-minimalize minimalize-styl-2 btn btn-primary ">
							<i class="fa fa-bars"></i>
						</span>
					</div>
					<ul class="nav navbar-top-links navbar-right">
						<li class="dropdown hidden-xs">
							<a class="right-sidebar-toggle" aria-expanded="false">
								<i class="fa fa-tasks"></i> 主题
							</a>
						</li>
					</ul>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs" style="width:auto;">
					<div class="page-tabs-content">
						<a href="javascript:;" class="active J_menuTab" data-url="common/home.do" data-index="0">主页</a>
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						操作<span class="caret"></span>
					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
						<li class="divider"></li>
						<li class=""><a href="javascript:history.back(-1);">返回上一级页面</a></li>
					</ul>
				</div>
				<a href="javascript:logout();" class="roll-nav roll-right J_tabExit">
					<i class="fa fa fa-sign-out"></i> 退出
				</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" 
					name="J_iframe_0" src="common/home.do" 
					data-index="0" data-url="common/home.do" 
					width="100%" height="100%" frameborder="0" seamless></iframe>
			</div>
			<!-- 
			<div class="footer">
				<div class="pull-right">
					&copy; 2017-2020 
					<a href="http://www.z8h.com.cn/" target="_blank">油惠站</a>
				</div>
			</div>
			 -->
		</div>
		<!-- 右侧部分结束 -->
		
		<!-- 右侧边栏开始 -->
		<div id="right-sidebar">
			<div class="sidebar-container">

				<ul class="nav nav-tabs navs-3">
					<li class="active">
						<a data-toggle="tab" href="#tab-1">
							<i class="fa fa-gear"></i> 主题
						</a>
					</li>
				</ul>

				<div class="tab-content">
					
					<div id="tab-1" class="tab-pane active">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> 主题设置
							</h3>
							<small>
								<i class="fa fa-tim"></i>
								你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。
							</small>
						</div>
						<div class="skin-setttings">
							<div class="title">主题设置</div>
							<div class="setings-item">
								<span>收起左侧菜单</span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
										<label class="onoffswitch-label" for="collapsemenu">
											<span class="onoffswitch-inner"></span>
											<span class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span>固定顶部</span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
										<label class="onoffswitch-label" for="fixednavbar">
											<span class="onoffswitch-inner"></span>
											<span class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span> 固定宽度 </span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
											<label class="onoffswitch-label" for="boxedlayout">
											<span class="onoffswitch-inner"></span>
											<span class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="title">皮肤选择</div>
							<div class="setings-item default-skin nb">
								<span class="skin-name ">
									<a href="#" class="s-skin-0">默认皮肤 </a>
								</span>
							</div>
							<div class="setings-item blue-skin nb">
								<span class="skin-name ">
									<a href="#" class="s-skin-1">蓝色主题 </a>
								</span>
							</div>
							<div class="setings-item yellow-skin nb">
								<span class="skin-name ">
									<a href="#" class="s-skin-3">黄色/紫色主题 </a>
								</span>
							</div>
						</div>
					</div>
					
				</div>

			</div>
		</div>
		<!-- 右侧边栏结束 -->
		
	</div>


<script type="text/javascript">

	function logout() {
		Confirm({
			msg : '确认退出系统？',
			ok : function() {
				$.ajax({
					type : "POST",
					url : "authen/doLogout.json",
					data : {
					},
					dataType : "json",
					success : function(data, textStatus) {
						if (data.success) {
							location.replace('<%=basePath%>authen/login.do');
						} else if(data.msg != null) {
							Alert(data.msg);
						} else {
							Alert('程序错误，请联系管理员！');
						}
					},
					error : function(xmlHttpRequest, textStatus, errorThrown) {
						//Alert('登录失效，请重新登录！');
						
						location.replace('authen/login.do');
					}
				});
			}
		});
	}

</script>


</body>
</html>