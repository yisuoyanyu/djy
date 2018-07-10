<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>套餐购买</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
	</head>
	 <body class="bg_main"> 
  <div class="success_main_div"> 
   <div class="success_main_imgdiv"> 
    <img src="img/icon_succ.png" class="success_main_img" /> 
   </div> 
   <div class="success_maindown_div"> 
    <span class="success_maindown_span">购买成功!</span> 
    <br /> 
   </div> 
   <div class="success_pay_div"> 
    <div class="success_pay_left_div"> 
     <div class="success_pay_leftin_div"> 
      <span class="success_payup_span">本次购买</span> 
      <br /> 
      <span class="success_paydown_span">${thisPoints}</span> 
     </div> 
    </div> 
    <div class="success_pay_right_div"> 
     <div class="success_pay_leftin_div"> 
      <span class="success_payup_span">剩余积分</span> 
      <br /> 
      <span class="success_paydown_span">${afterPoint}</span> 
     </div> 
    </div> 
   </div> 
  </div> 
  <div class="point_success_downdiv"> 
   <span class="button blue bigrounded" style="float:left;width:45%;margin-left:3%" onclick="goIndex();">返回个人中心</span> 
   <span class="button blue bigrounded" style="float:right;width:45%;background-color:red;border: 1px solid red;margin-right:3%;background:-webkit-gradient(linear, left top, left bottom, from(red), to(red))" onclick="goonPay();">继续购买</span> 
  </div>  

<script type="text/javascript">
    document.body.addEventListener('touchstart', function () {});
    
    function goIndex() {
        window.location.href="../mall/index.do";
    }
    
    function goonPay() {
        if ( ${ user.type } == 2) {
            window.location.href="pointBuy/packagesBuy.do";
        } else {
            window.location.href="pointBuy/pointBuy.do";
        }
    }
</script> 
 </body>
</html>