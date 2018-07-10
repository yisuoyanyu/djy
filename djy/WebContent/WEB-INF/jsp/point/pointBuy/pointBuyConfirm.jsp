<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>购买确认</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript">
		
		function onBridgeReady(){
			   WeixinJSBridge.invoke(
			       'getBrandWCPayRequest', {
			           "appId":"${appid}",     //公众号名称，由商户传入     
			           "timeStamp":"${timeStamp}",         //时间戳，自1970年以来的秒数     
			           "nonceStr":"${nonceStr}", //随机串     
			           "package":"${packageValue}",     
			           "signType":"${signType}",         //微信签名方式：     
			           "paySign":"${sign}" //微信签名 
			       },
			       function(res){
			    	   alert( JSON.stringify( res ) );
			    	// 使用以下方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
			    		if(res.err_msg == "get_brand_wcpay_request:ok") {
			    			var thisPoint = $("#thisPoint").text();
			    			  var orderNo = $("#orderNo").val();
			    			  var afterPoint = $('#afterPoint').val();
			    			  window.location.href="pointBuy/pointBuySuccess.do?thisPoint="+thisPoint+"&orderNo="+orderNo+"&afterPoint="+afterPoint;
			           }else if(res.err_msg == "get_brand_wcpay_request:fail"){
			       			Alert('支付失败');
			       		}else if(res.err_msg == "get_brand_wcpay_request:cancel"){
			       			Alert('支付取消');
			       		}else{
			       			alert(res.err_msg);
			       		}
			       }
			   ); 
			}
		
		if (typeof('WeixinJSBridge') == "undefined"){
			   if( document.addEventListener ){
			       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
			   }else if (document.attachEvent){
			       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
			       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			   }
			}else{ 
			   onBridgeReady();
			}	  	
		</script>
	</head>
	 <body class="bg_main"> 
	 <input type="hidden" name="afterPoint" id="afterPoint" value="${afterPoint}">
  <div class="confim_top_div"> 
   <span class="confim_top_div_span">购买积分信息确认</span> 
  </div> 
  <div class="confim_mian_div"> 
   <div class="confim_points_div"> 
    <div class="confim_points_div_left"> 
     <span class="confim_points_div_span">到账积分：<span id="thisPoint" name="thisPoint">${totalPoint}</span></span> 
    </div> 
    <div style="height:60px;line-height:60px;text-align:left;"> 
     <span class="confim_points_div_span">实付金额：<span id="totalPrice" name="totalPrice">${totalPrice}</span>元</span> 
     <input type="hidden" id="orderNo" name="orderNo" value="${orderNo}">
    </div> 
   </div> 
   <div class="confim_points_wechat_div"> 
    <div style="padding-top:2%">
     <span class="confim_points_div_span">支付方式</span> 
    </div> 
    <div class="confim_points_wechat_span_div"> 
     <img src="img/ico_pay_WeChat.png" class="am-inline-block am-vertical-align-bottom" style="max-height:4rem;" /> 
     <span class="am-inline-block am-vertical-align-bottom"> 
      <div class="am-text-lg">
        微信支付 
      </div> 
      <div class="am-text-sm">
        微信安全支付 
      </div> </span> 
    </div> 
   </div> 
  </div> 
  <div style="text-align:center;padding-top:10%"> 
   <span class="button blue bigrounded" onclick="onBridgeReady(); this.disabled=true; return true;">　确认支付　</span> 
  </div> 
  
  <script type="text/javascript">
  document.body.addEventListener('touchstart', function () {});
  function submit(){
	  var thisPoint = $("#thisPoint").text();
	  var orderNo = $("#orderNo").val();
	  var afterPoint = $('#afterPoint').val();
	  window.location.href="pointBuy/pointBuySuccess.do?thisPoint="+thisPoint+"&orderNo="+orderNo+"&afterPoint="+afterPoint;
  }
  </script> 
 </body>
</html>