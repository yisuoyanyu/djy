<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>兑换积分失败</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
	</head>
	<body class="bg_main"> 
  <div class="success_main_div"> 
   <div class="success_main_imgdiv"> 
    <img src="img/icon_defe.png" class="success_main_img" /> 
   </div> 
   <div class="success_maindown_div"> 
    <span class="success_maindown_span">兑换失败!</span>
    <br /> 
   </div> 
   <div class="success_pay_div"> 
    <div class="success_pay_left_div" style="width: 100%;border-right:solid 0px #c9c9c9;color:#476df6"> 
     <div class="success_pay_leftin_div"> 
                 非常抱歉，因为系统原因导致您本次兑换交易失败，请点击下方的“返回个人中心”按钮进入首页，再次扫描二维码进行兑换！
     </div> 
    </div>  
   </div> 
  </div> 
  <div class="point_success_downdiv"> 
  <span class="button blue bigrounded" onclick="goIndex()">返回个人中心</span> 
   </div> 
   <script type="text/javascript">
   document.body.addEventListener('touchstart', function () {});
   function goIndex(){
	   window.location.href="index.do";
   }
   </script> 
 </body>
</html>