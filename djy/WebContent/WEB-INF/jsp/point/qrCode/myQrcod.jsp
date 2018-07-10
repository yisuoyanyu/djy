<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>我的二维码</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	    <script type="text/javascript">
        $(function() {
            wx.config({
                //debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
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
            				
            				if ( ${not empty user.coPartner} ) {
            					// 分享朋友圈
            					if (checkRes.onMenuShareTimeline) {
                					wx.onMenuShareTimeline({
                							title: '我的商家二维码--油惠站', // 分享标题
                    						link: '${ wxConfig.serverUrl }'+'point/qrCode/myQrcod.do?spreadCode='+${user.spreadCode}, 		// 分享链接
                    						imgUrl: '${user.qrcodeAddress}', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
            					
            					// 分享给朋友
                				if (checkRes.onMenuShareAppMessage) {
                					wx.onMenuShareAppMessage({
                						title: '我的商家二维码--油惠站', // 分享标题
                						desc: '领先全国积分兑换系统', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'point/qrCode/myQrcod.do?spreadCode='+${user.spreadCode}, 		// 分享链接
                						imgUrl: '${user.qrcodeAddress}', // 分享图标
                						type: '', // 分享类型,music、video或link，不填默认为link
                						dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                						success: function () {
                							// 用户确认分享后执行的回调函数
                						},
                						cancel: function () { 
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
                				
                				// 分享到QQ
                				if (checkRes.onMenuShareQQ) {
                					wx.onMenuShareQQ({
                						title: '我的商家二维码--油惠站', // 分享标题
                						desc: '领先全国积分兑换系统', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'point/qrCode/myQrcod.do?spreadCode='+${user.spreadCode}, 		// 分享链接
                						imgUrl: '${user.qrcodeAddress}', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
                				
                				// 分享到腾讯微博
                				if (checkRes.onMenuShareWeibo) {
                					wx.onMenuShareWeibo({
                						title: '我的商家二维码--油惠站', // 分享标题
                						desc: '领先全国积分兑换系统', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'point/qrCode/myQrcod.do?spreadCode='+${user.spreadCode}, 		// 分享链接
                						imgUrl: '${user.qrcodeAddress}', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
                				
                				// 分享到QQ空间
                				if (checkRes.onMenuShareQZone) {
                					wx.onMenuShareQZone({
                						title: '我的商家二维码--油惠站', // 分享标题
                						desc: '领先全国积分兑换系统', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'point/qrCode/myQrcod.do?spreadCode='+${user.spreadCode}, 		// 分享链接
                						imgUrl: '${user.qrcodeAddress}', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
            					
            				} else {
            					// 分享朋友圈
            					if (checkRes.onMenuShareTimeline) {
                					wx.onMenuShareTimeline({
    										title: '油惠站积分系统', // 分享标题
                    						link: '${ wxConfig.serverUrl }'+'point/qrCode/myQrcod.do?spreadCode='+${user.spreadCode}, 		// 分享链接
                    						imgUrl: '${ wxConfig.serverUrl }'+'point/'+'img/zbhqrcode.jpg', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
            					// 分享给朋友
                				if (checkRes.onMenuShareAppMessage) {
                					wx.onMenuShareAppMessage({
                						title: '我的商家二维码--油惠站', // 分享标题
                						desc: '领先全国积分兑换系统', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'point/qrCode/myQrcod.do?spreadCode='+${user.spreadCode}, 		// 分享链接
                						imgUrl: '${ wxConfig.serverUrl }'+'point/'+'img/zbhqrcode.jpg', // 分享图标
                						type: '', // 分享类型,music、video或link，不填默认为link
                						dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                						success: function () {
                							// 用户确认分享后执行的回调函数
                						},
                						cancel: function () { 
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
                				
                				// 分享到QQ
                				if (checkRes.onMenuShareQQ) {
                					wx.onMenuShareQQ({
                						title: '我的商家二维码--油惠站', // 分享标题
                						desc: '领先全国积分兑换系统', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'point/qrCode/myQrcod.do?spreadCode='+${user.spreadCode}, 		// 分享链接
                						imgUrl: '${ wxConfig.serverUrl }'+'point/'+'img/zbhqrcode.jpg', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
                				
                				// 分享到腾讯微博
                				if (checkRes.onMenuShareWeibo) {
                					wx.onMenuShareWeibo({
                						title: '我的商家二维码--油惠站', // 分享标题
                						desc: '领先全国积分兑换系统', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'point/qrCode/myQrcod.do?spreadCode='+${user.spreadCode}, 		// 分享链接
                						imgUrl: '${ wxConfig.serverUrl }'+'point/'+'img/zbhqrcode.jpg', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
                				// 分享到QQ空间
                				if (checkRes.onMenuShareQZone) {
                					wx.onMenuShareQZone({
                						title: '我的商家二维码--油惠站', // 分享标题
                						desc: '领先全国积分兑换系统', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'point/qrCode/myQrcod.do?spreadCode='+${user.spreadCode}, 		// 分享链接
                						imgUrl: '${ wxConfig.serverUrl }'+'point/'+'img/zbhqrcode.jpg', // 分享图标
                						success: function () {
                							// 用户确认分享后执行的回调函数
                						},
                						cancel: function () {
                							// 用户取消分享后执行的回调函数
                						}
                					});
                				}
            					
							}
            				
            				
            			}
            			
            		}
            	});
            });

            wx.error(function(res){
            	
            });
        });
        
    </script>
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
   <div style="color: #476df6;font-weight: bolder;text-align: center;width: 100%;font-size: 20px;position: absolute;top: 35%;left: 0%;">收款二维码</div>
   <div class="myqr_mian_div"> 
   <c:if test="${user.type != 2}">
   <img class="myqr_mian_img" src="${user.qrcodeAddress}" /> 
   </c:if>
    <c:if test="${user.type == 2}">
    <img class="myqr_mian_img" src="${user.qrcodeAddress}" /> 
    </c:if>
   </div> 
   <div style="color: #476df6;text-align: center;width: 100%;font-size: 12px;position: absolute;top: 84%;">备注：通过扫描二维码付款可享受优惠</div>
    
  </div>  
 </body>
</html>