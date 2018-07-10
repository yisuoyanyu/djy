<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8" %>

<!doctype html>
<html>
	<head>
		<title><decorator:title /></title>
		
		<meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<meta name="keywords" content="">
		<meta name="description" content="管理后台">
		
		<%
			String basePath = "";
			// if ( "127.0.0.1".equals( request.getServerName() ) || "localhost".equals( request.getServerName() ) ) {
			if ( "127.0.0.1".equals( request.getServerName() ) ) {
				ResourceBundle bundle = ResourceBundle.getBundle("config");
				try {
					basePath = bundle.getString("sys.server.admin");
				} catch(Exception e) {
					
				}
				if ( "".equals(basePath) )
					basePath = request.getContextPath() + "/";
			} else {
				basePath = request.getScheme() + "://" + request.getServerName() + ":"+request.getServerPort() + request.getContextPath() + "/";
			}
			basePath += "admin/";
		%>
		
		<!--[if lt IE 9]>
		<meta http-equiv="refresh" content="0;url=<%=basePath%>html/ie.html" />
		<![endif]-->
		
		<base href="<%=basePath%>"/>
		
		<link rel="shortcut icon" href="img/logo/favicon.ico">
		
		<link href="frames/bootstrap/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
		<link href="frames/fontawesome/css/font-awesome.css?v=4.4.0" rel="stylesheet">
		
		<link href="frames/animate/animate.css" rel="stylesheet">
		
		<!-- 全局js -->
		<script src="frames/jquery/jquery.min.js?v=2.1.4" type="text/javascript"></script>
		<script src="frames/bootstrap/js/bootstrap.min.js?v=3.3.6" type="text/javascript"></script>
		
		<!-- layer -->
		<script src="plugins/layer/layer.min.js?v=2.1" type="text/javascript"></script>
		
		<!-- layerDate 日期时间选择器 -->
		<script src="plugins/layer/laydate/laydate.js?v=1.1" type="text/javascript"></script>
		
		<!-- iCheck -->
		<link href="plugins/iCheck/css/custom.css" rel="stylesheet">
		<script src="plugins/iCheck/js/icheck.min.js?v=1.0.2" type="text/javascript"></script>
		
		<!-- Chosen -->
		<link href="plugins/chosen/css/chosen.css?v=1.1.0" rel="stylesheet">
		<script src="plugins/chosen/js/chosen.jquery.js?v=1.1.0" type="text/javascript"></script>
		
		<script src="js/public.js" type="text/javascript"></script>
		
<decorator:head />

		<link href="frames/hplus/css/style.css?v=4.1.0" rel="stylesheet">
		<link href="css/public.css" rel="stylesheet">

	</head>
	<body class="<decorator:getProperty property="body.class" />">
		<decorator:body />
	</body>
</html>
