<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>确认积分赠送</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
	</head>
	<body class="bg_main"> 
  <div class="confim_top_div"> 
   <span class="confim_top_div_span">赠送积分信息确认</span> 
  </div> 
  <div class="confim_mian_div">
  
  <c:if test="${not empty tranferUser.wechatUser.headimgUrl}">
  <div style="padding-bottom:3vw">
  <img src="${tranferUser.wechatUser.headimgUrl}" class="am-img-responsive am-circle am-inline-block am-vertical-align-middle" style="max-width: 6rem; max-height: 6rem;box-shadow: 0 4px 15px #58a1ff;" /> 
  </div>
   </c:if>
   
   <div class="confim_points_div"> 
   <div class="confim_points_div_left">
     <span class="confim_points_div_span">赠送微信名：<font color="black" id="nickname">${tranferUser.wechatUser.nickname}</font></span>
    </div>
    <div class="confim_points_div_left">
     <span class="confim_points_div_span">赠送账号：<font color="black" id="phone">${phone}</font></span>
    </div> 
    <div class="confim_points_div_left">
     <span class="confim_points_div_span">赠送积分：<span id="point">${point}</span></span>
    </div> 
	<div style="height:60px;line-height:60px;text-align:left;">
     <span class="confim_points_div_span">积分余额：<span id="surPoint">${surPoint}</span></span>
    </div> 
   </div>  
  </div> 
  <div style="text-align:center;padding-top:10%"> 
   <span class="button blue bigrounded" onclick="submit(); this.disabled=true; return true;">　确认赠送　</span> 
  </div> 
  
  <script type="text/javascript">
  
  document.body.addEventListener('touchstart', function () {});
  
  function submit(){
	  window.location = "pointTransfer/pointTransferAct.do?phone="+$("#phone").text()+"&point="+$("#point").text();
  }
  </script> 
 </body>
</html>