<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>油惠站关注二维码</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		  <script>  
			 window.onload=function (){  
				 function auto_height()  
				 {
					 var topLen = document.getElementById("topmain").offsetHeight;
				     document.getElementById("box").style.height=document.documentElement.clientHeight-topLen+"px";  
				  
				     }  
				     auto_height();  
				     onresize=auto_height;  
				    }  
		    </script> 
	</head>
	 <body> 
  <div class="bg_topmain" id="topmain"> 
   <img src="img/bg-myqrcode.png" style="width:100%;z-index:11" /> 
  </div> 
  <div id="box" class="bg_myqr"> 
   <div class="myqr_mian_div"> 

    <img class="myqr_mian_img" src="img/zbhqrcode.jpg" /> 

   </div> 
  </div>  
 </body>
</html>