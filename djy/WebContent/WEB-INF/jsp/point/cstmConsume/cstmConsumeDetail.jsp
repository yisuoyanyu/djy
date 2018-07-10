<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>会员消费明细</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script type="text/javascript" src="js/jquery.more.js"></script>
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
	<style>
	.jiazai{ width:97%; height:50px; margin:auto }
	.jiazai em{ display:block; width:25%; float:left; height:24px; border-bottom:1px solid #ccc; margin-bottom:24px;}
	.jiazai span{ display:block; width:50%; float:left; height:50px;font-size:14px; line-height:50px; text-align:center; color:#999}
	.more_loader_spinner { text-align: center; font-size:14px; float:left; width: 100%}
	</style>
	</head>
	 <body class="bg_main"> 
  <div class="bg_topmain" id="topmain"> 
   <div class="am-vertical-align" style="text-align:center"> 
    <div class="success_pay_div"> 
     <div class="cstmConsumeDetail_topleft_div"> 
      <div style="width:100%;padding-top:5vw"> 
       <span class="top_points_show"> 
         ¥ <fmt:formatNumber value="${ coPartner.coPartnerAccount.totalCstmConsume }" type="number" maxFractionDigits="2" /> 元
       </span> 
      </div> 
      <div style="width:100%"> 
       <span class="white_span">顾客消费总额 </span> 
      </div> 
     </div> 
     <div class="success_pay_right_div">
      
      <c:if test="${ coPartner.coModeCode == 'perOrder' }">
      <div style="width:100%;padding-top:5vw">
       <span class="top_points_show">
         ¥ <fmt:formatNumber value="${ coPartner.coPartnerAccount.totalCstmSettle }" type="number" maxFractionDigits="2" /> 元
       </span>
      </div>
      <div style="width:100%">
       <span class="white_span">实际收款总额 </span>
      </div>
      </c:if>
      
      <c:if test="${ coPartner.coModeCode == 'sysDeposit' }">
      <div style="width:100%;padding-top:5vw">
       <span class="top_points_show">
         ¥ <fmt:formatNumber value="${ coPartner.coPartnerAccount.totalSysDeposit }" type="number" maxFractionDigits="2" />
       </span>
      </div>
      <div style="width:100%">
       <span class="white_span">平台充值总额 </span>
      </div>
      </c:if>
      
     </div> 
    </div> 
    <img src="img/icon_jifen.png" class="meal_div_point_bg" /> 
   </div> 
   <img src="img/top-buytaocan.jpg" class="meal_div_top_img" /> 
  </div> 
  <div id="box" class="listCstmConsume" style="height: auto;"> 
    
    <ul class="am-list am-margin-xs" id="detaildiv">

	<a class="product single_item info"></a>
	<a href="javascript:;" class="get_more"> </a>

    	
    </ul>
    
  </div>
  
  <script type="text/javascript">
  var url = 'cstmConsume/getConsumes.do?page=';
	$('#detaildiv').more({
		'address' : url
	});
	
	$(function(){ 
		　　$("li[name='csli']").each(  
			function(){  
			$(this).parent().remove();
			}) 
		}); 
  </script>  
 </body>
</html>