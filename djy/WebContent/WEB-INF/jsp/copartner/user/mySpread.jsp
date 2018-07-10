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
 <style>.am-titlebar-default .am-titlebar-title:before {border-left: 4px solid #3a88ef;}</style>
 <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	    <script type="text/javascript">
        $(function() {
            wx.config({
                debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId : '${ wxConfig.appId }', // 必填，公众号的唯一标识
                timestamp : ${ wxConfig.timestamp }, // 必填，生成签名的时间戳
                nonceStr : '${ wxConfig.nonceStr }', // 必填，生成签名的随机串
                signature : '${ wxConfig.signature }',// 必填，签名，见附录1
                jsApiList : ['onMenuShareTimeline', 'onMenuShareAppMessage']
            // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
            
            wx.ready(function(){
            	
            	wx.checkJsApi({
            		jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone'],
            		success: function(res) {
            			var checkRes = res.checkResult;
            			if (typeof(checkRes) != 'undefined' && checkRes != null) {

            					// 分享朋友圈
            					if (checkRes.onMenuShareTimeline) {
                					wx.onMenuShareTimeline({
    										title: '加油省钱，找互联网第一站油惠站', // 分享标题
                    						link: '${ wxConfig.serverUrl }'+'copartner/user/toFollow.do?spreadCode=${user.spreadCode}', 		// 分享链接
                    						imgUrl: '${ wxConfig.serverUrl }'+'point/'+'img/zbhqrcode.jpg', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                							getMoreDiscountChance();
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
            					// 分享给朋友
                				if (checkRes.onMenuShareAppMessage) {
                					wx.onMenuShareAppMessage({
                						title: '加油省钱，找互联网第一站油惠站', // 分享标题
                						desc: '加油省钱，优惠多多', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'copartner/user/toFollow.do?spreadCode=${user.spreadCode}', 		// 分享链接
                						imgUrl: '${ wxConfig.serverUrl }'+'point/'+'img/zbhqrcode.jpg', // 分享图标
                						type: '', // 分享类型,music、video或link，不填默认为link
                						dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                						success: function () {
                							// 用户确认分享后执行的回调函数
                							getMoreDiscountChance();
                						},
                						cancel: function () { 
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
                				
                				// 分享到QQ
                				if (checkRes.onMenuShareQQ) {
                					wx.onMenuShareQQ({
                						title: '加油省钱，找互联网第一站油惠站', // 分享标题
                						desc: '加油省钱，优惠多多', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'copartner/user/toFollow.do?spreadCode=${user.spreadCode}', 		// 分享链接
                						imgUrl: '${ wxConfig.serverUrl }'+'point/'+'img/zbhqrcode.jpg', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                							getMoreDiscountChance();
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
                				
                				// 分享到腾讯微博
                				if (checkRes.onMenuShareWeibo) {
                					wx.onMenuShareWeibo({
                						title: '加油省钱，找互联网第一站油惠站', // 分享标题
                						desc: '加油省钱，优惠多多', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'copartner/user/toFollow.do?spreadCode=${user.spreadCode}', 		// 分享链接
                						imgUrl: '${ wxConfig.serverUrl }'+'point/'+'img/zbhqrcode.jpg', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                							getMoreDiscountChance();
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
                				// 分享到QQ空间
                				if (checkRes.onMenuShareQZone) {
                					wx.onMenuShareQZone({
                						title: '加油省钱，找互联网第一站油惠站', // 分享标题
                						desc: '加油省钱，优惠多多', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'copartner/user/toFollow.do?spreadCode=${user.spreadCode}', 		// 分享链接
                						imgUrl: '${ wxConfig.serverUrl }'+'point/'+'img/zbhqrcode.jpg', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                							getMoreDiscountChance();
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
            					
            				
            			}
            			
            		}
            	});
            });

            wx.error(function(res){
            	
            });
        });
        
    </script>
 </head>
  
  <body style="background-color:#fbba3e;">
    <!-- 顶部图片 -->
    <div class="am-slider am-slider-a1" data-am-flexslider="{playAfterPaused: 8000}" style="-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0);">
      <div class="am-slides">
        <img src="../copartner/img/yaoqingbanner.png">
	  </div>
    </div>
    <!-- <div style="width:100%;text-align:center;padding-top:5vw;">
    <span style="border-radius:5vw;background-color:#ff4523;color:#fff;padding:3vw 15vw 3vw 15vw;box-shadow: 0 4px 15px #fbbe42;letter-spacing:2vw;font-size:2rem;">立即关注</span></div>-->
    <div class="am-g" style="padding-top:5vw;background-color: #fbb93e;">
      <div class="am-u-sm-3 yg-gifimg" id="left">
        <img src="../copartner/img/apersonal-gift.png" class="yg-gifimg1"></div>
      <div class="am-u-sm-9 y-rightdiv" id="right">
        <span class="yg-titalespan" onclick="shouFenxiang();" style="padding-left: 8vw;">
        <img alt="" src="../copartner/img/fenxiangbut.png" style="display: inline-block;height: auto;max-width: 72%;padding-top: 2vw;padding-bottom: 2vw;">
        </span>
        <span class="yg-span" style="color: white;">油惠站平台整合全国优质民营加油站，倾力打造车主加油省钱平台，您只要关注微信服务号“油惠站”，微信支付，轻松省钱！</span></div>
    </div>
    <div class="blank1" style="background-color: #fbb93e;height: 7vw;"></div>
   <div style="width:100%;text-align:center;height:10rem;background-color: #fbb93e;">
      <div class="y-bgtitleimg">
        <span class="yg-titalespan">您已邀请${totalRefnum}位好友</span>
	  </div>
   </div>
   
      <!-- 分享层 -->
    <div id="ID_VoucherPrompt" class="am-modal" tabindex="-1">
      <div class="am-modal-dialog voucherPrompt" style="padding-bottom: 18rem;">
        <div>
          <img src="../copartner/img/wechat-share.png" class="con-bgimg">
		</div>
      </div>
    </div>
    
    <script type="text/javascript">
    function shouFenxiang(){
    	  var a = $('#ID_VoucherPrompt');
          a.modal();
    }

    function getMoreDiscountChance(){
    	 Alert({
				msg : "分享成功，好友成功关注以后就可获得一次领券机会！",
				ok : function() {
					window.location.href = 'user/main.do';
				}
			});
    }
    
    </script>
    
  </body>

</html>