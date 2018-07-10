<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html class="no-js">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width" />
<title>邀请好友</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
   <style>.am-titlebar-default .am-titlebar-title:before {border-left: 4px solid #3a88ef;}</style></head>
  
  <body style="background-color:#0089e0;">
    <!-- 顶部图片 -->
    <div class="am-slider am-slider-a1" data-am-flexslider="{playAfterPaused: 8000}" style="background-color: #0089e0;-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0);">
      <div class="am-slides">
        <img src="../copartner/img/guanzhu-banner.png">
	  </div>
    </div>
    <!-- <div style="width:100%;text-align:center;padding-top:5vw;">
    <span style="border-radius:5vw;background-color:#ff4523;color:#fff;padding:3vw 15vw 3vw 15vw;box-shadow: 0 4px 15px #fbbe42;letter-spacing:2vw;font-size:2rem;">立即关注</span></div>-->
    <div class="am-g" style="padding-top:5vw;background-color: #0089e0;">
      <div class="am-u-sm-3 yg-gifimg"  id="left" style="margin-top: -1rem;">
        <img src="../copartner/img/apersonal-gift.png" class="yg-gifimg1"></div>
      <div class="am-u-sm-9 yg-rigthdiv" id="right">
        <span class="g-btn" onclick="getQrcode(${user.id});">立即关注</span>
        <span class="yg-span" style="color: white;">油惠站平台整合全国优质民营加油站，倾力打造车主加油省钱平台，您只要关注微信服务号“油惠站”，微信支付，轻松省钱！</span></div>
    </div>
    <div class="blank3" style="background-color: #0089e0;"></div>
    <div class="g-bgimg" style="background-color: #background-color: #0089e0;margin-top: 0vw;">
      <div class="g-bgtitleimg">
        <span class="yg-titalespan"><img alt="" src="../copartner/img/activity-rule.png" style="display: inline-block;height: auto;max-width: 100%;"></span>
	  </div>
      <div class="g-textdiv">
        <span class="g-text" style="color: #0089e0;">1.首次关注“油惠站”微信服务号，获得一次打折加油机会！</span>
        <span class="g-text" style="color: #0089e0;">2.分享“油惠站”微信服务号给朋友，朋友关注后，您将增加一次领取加油打折券机会！分享越多，领取机会越多，上不封顶哦！</span>
	  </div>
    </div>
    
    <!--二维码层-->
		<div id="ID_Qrcodes" class="am-modal" tabindex="-1">
			<div class="am-modal-dialog voucherPrompt">
				<i class="close" data-am-modal-close style="top: 1vw;right: 1vw;"></i>
				<img src="../copartner/img/changan.png" class="con-bgimg">
				<img id="qrcode" src="" class="con-bgimg">
			</div>
		</div>
		<script type="text/javascript">
		function getQrcode(userId){
			$.ajax({
				type : 'post',
				url : 'user/getSpreadQrcode.do',
				data : {
					'userId': userId
				},
				dataType:'json',
				async : true,
				success : function(ret) {
					if (ret.success) {
						$("#qrcode").attr('src',ret.data); 
						$('#ID_Qrcodes').modal();
					}else{
						Alert(d);
					}
				}
			});
		}
		</script>
  </body>

</html>