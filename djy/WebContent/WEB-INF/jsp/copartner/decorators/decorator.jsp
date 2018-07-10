<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page pageEncoding="UTF-8" %>

<!doctype html>
<html>
	<head>
		<title><decorator:title />--互联网油惠站</title>
		
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="description" content="">
		<meta name="keywords" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<!-- Set render engine for 360 browser -->
		<meta name="renderer" content="webkit">

		<!-- No Baidu Siteapp-->
		<meta http-equiv="Cache-Control" content="no-siteapp"/>

		<!-- Add to homescreen for Chrome on Android -->
		<meta name="mobile-web-app-capable" content="yes">
		<%
			String basePath = request.getContextPath() + "/copartner/";
		%>
		<base href="<%=basePath%>"/>
		
		<link rel="icon" sizes="192x192" href="img/logo/app-icon72x72@2x.png">

		<!-- Add to homescreen for Safari on iOS -->
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="apple-touch-icon-precomposed" href="img/logo/app-icon72x72@2x.png">
		
		<!-- Tile icon for Win8 (144x144 + tile color) -->
		<meta name="msapplication-TileImage" content="img/logo/app-icon72x72@2x.png">
		<meta name="msapplication-TileColor" content="#0e90d2">
		<meta name="format-detection" content = "telephone=no">

		<%
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
			java.util.Date currentTime = new java.util.Date();	//得到当前系统时间
			String t = format.format(currentTime);
		%>
		
	    <link rel="stylesheet" href="frame/AmazeUI/css/amazeui.css?t=<%=t%>">
	    <link rel="stylesheet" href="frame/AmazeUI/css/app.css?t=<%=t%>">
	    <script src="js/jquery.min.js?t=<%=t%>"></script>
	    <script src="js/public.js?t=<%=t%>"></script>
	    <script src="frame/AmazeUI/js/amazeui.min.js?t=<%=t%>"></script>
	    <link rel="stylesheet" href="css/public.css?t=<%=t%>">
		
		<script type="text/javascript">
		<!--弹出框和确定框的样式js-->
     (function ($) {
	  $.extend({
	    _isalert:0,
	    alert:function(){
	      if(arguments.length){
	        $._isalert=1;
	        $.confirm.apply($,arguments);
	      }
	    },
	    confirm:function(){
	      var args=arguments;
	      if(args.length&&(typeof args[0] == 'string')&&!$('#alert_overlay').length){
	        if(!$('#alert_style').length) $('body').append('<style id="alert_style" type="text/css">#alert_overlay{position:fixed;width:100%;height:100%;top:0;left:0;z-index:999;background:#000;filter:alpha(opacity=5);opacity:.05}#alert_msg{position:fixed;width:80vw;margin-left:-40vw;left:50%;top:30%;z-index:1000;border:1px solid #aaa;box-shadow:0 2px 15px rgba(0,0,0,.3);background:#fff}#alert_content{padding:20px;font-size:14px;text-align:left}#alert_buttons{padding:10px;border-top:1px solid #aaa;text-align:right;box-shadow:0 1px 0 #fff inset;background:#eee;-moz-user-select:none;-webkit-user-select:none;-ms-user-select:none}#alert_buttons .alert_btn{padding:5px 12px;margin:0 2px;border:1px solid #aaa;background:#eee;cursor:pointer;border-radius:2px;font-size:14px;outline:0;-webkit-appearance:none}#alert_buttons .alert_btn:hover{border-color:#bbb;box-shadow:0 1px 2px #aaa;background:#eaeaea}#alert_buttons .alert_btn:active{box-shadow:0 1px 2px #aaa inset;background:#e6e6e6}</style>');
	        var dialog=$('<div id="alert_overlay"></div><div id="alert_msg"><div id="alert_content">'+args[0]+'</div><div id="alert_buttons"><button class="alert_btn alert_btn_ok">确定</button><button class="alert_btn alert_btn_cancel">取消</button></div></div>');
	        if($._isalert) dialog.find('.alert_btn_cancel').hide();
	        dialog.on('contextmenu',function(){
	          return !1;
	        }).on('click','.alert_btn_ok',function(){
	          dialog.remove();
	          if(typeof args[1]=='function') args[1].call($,!0);
	        }).on('click','.alert_btn_cancel',function(){
	          dialog.remove();
	          if(typeof args[1]=='function') args[1].call($,!1);
	        }).appendTo('body');
	      }
	      $._isalert=0;
	    }
	  });
	})($);

</script>

<script>  
function DrawImage(ImgD,iwidth,iheight){      
    //参数(图片,允许的宽度,允许的高度)      
    var image=new Image();      
    image.src=ImgD.src;      
    if(image.width>0 && image.height>0){      
      if(image.width/image.height>= iwidth/iheight){      
          if(image.width>iwidth){        
              ImgD.width=iwidth;      
              ImgD.height=(image.height*iwidth)/image.width;      
          }else{      
              ImgD.width=image.width;        
              ImgD.height=image.height;      
          }      
      }else{      
          if(image.height>iheight){        
              ImgD.height=iheight;      
              ImgD.width=(image.width*iheight)/image.height;              
          }else{      
              ImgD.width=image.width;        
              ImgD.height=image.height;      
          }      
      }      
    }      
}     
</script>

		<decorator:head />
	</head>
	
	<body class="<decorator:getProperty property="body.class" />" style="<decorator:getProperty property="body.style" />">
		<decorator:body />
	</body>
</html>