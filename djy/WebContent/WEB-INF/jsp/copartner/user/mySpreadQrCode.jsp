<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>我的推广二维码</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script type="text/javascript" src="../point/js/jquery.more.js"></script>
		<script src="../point/js/public.js"></script>
		<link rel="stylesheet" href="../point/css/zbhpublic.css">
		    <script>  
				 window.onload=function (){  
				 function auto_height()  
				 {  
				 var boxLen = document.getElementById("box").offsetHeight;
				 var scenLen = document.documentElement.clientHeight;
				 if(boxLen<=scenLen){
				  document.getElementById("box").style.minHeight=(document.documentElement.clientHeight)+"px";  
				 }else{  
				    }
				 }  
				    auto_height();  
				    onresize=auto_height;  
				}  
			</script>
	</head>
	 <body class="bg_main"> 
    <!-- 页签 -->
    <div class="bar h3 tabs_bar posi_fixed border_b">
      <ul class="am-avg-sm-2" style="position:relative;">
         <li class="on">
          <a href="javascript:void(0);" style="color: white;">我的二维码</a></li>
        <li>
          <a href="javascript:location.replace('spreadCommission/myCommission.do?page=1');">推广佣金</a></li>
      </ul>
    </div>
  <div id="box" class="listCstmConsume" style="height: auto;"> 
    
    <img src="${myQrcode}" style="width: 70%;text-align: center;margin-left: 15%;margin-top: 7%;">
    <br>
    <div style="text-align: center;width: 100%;font-size: 24px;font-weight: bolder;">【推荐扫描二维码关注】</div>
  </div>

  
 </body>
</html>