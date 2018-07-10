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
<title>支付成功</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
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
    										title: '我在${conName}加油享受${discount}折优惠，关注赢取${discount}折扣券！', // 分享标题
                    						link: '${ wxConfig.serverUrl }'+'copartner/user/toFollow.do?spreadCode=${spreadCode}', 		// 分享链接
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
                						title: '我在${conName}加油享受${discount}折优惠，关注赢取${discount}折扣券！', // 分享标题
                						desc: '我在${conName}消费${consumeAmount}，节省${disAmount},关注赢取${discount}折扣券！', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'copartner/user/toFollow.do?spreadCode=${spreadCode}', 		// 分享链接
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
                						title: '我在${conName}加油享受${discount}折优惠，关注赢取${discount}折扣券！', // 分享标题
                						desc: '我在${conName}消费${consumeAmount}，节省${disAmount},关注赢取${discount}折扣券！', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'copartner/user/toFollow.do?spreadCode=${spreadCode}', 		// 分享链接
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
                						title: '我在${conName}加油享受${discount}折优惠，关注赢取${discount}折扣券！', // 分享标题
                						desc: '我在${conName}消费${consumeAmount}，节省${disAmount},关注赢取${discount}折扣券！', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'copartner/user/toFollow.do?spreadCode=${spreadCode}', 		// 分享链接
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
                						title: '我在${conName}加油享受${discount}折优惠，关注赢取${discount}折扣券！', // 分享标题
                						desc: '我在${conName}消费${consumeAmount}，节省${disAmount},关注赢取${discount}折扣券！', // 分享描述
                						link: '${ wxConfig.serverUrl }'+'copartner/user/toFollow.do?spreadCode=${spreadCode}', 		// 分享链接
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
 <body class="bg_main" style="background-color: #e2eaff;"> 
  <div class="success_main_div"> 
   <div class="success_main_imgdiv"> 
    <img src="../copartner/img/paySuccess.png" class="success_main_img" /> 
   </div> 
   <div class="success_maindown_div"> 
    <span class="success_maindown_span">支付成功!</span> 
    <br /> 
   </div> 
   <div class="success_pay_div"> 
    <div class="success_pay_left_div"> 
     <div class="success_pay_leftin_div"> 
      <span class="success_payup_span">实付金额(￥)</span> 
      <br /> 
      <span class="success_paydown_span">${consumeAmount}</span> 
     </div> 
    </div> 
    <div class="success_pay_right_div"> 
     <div class="success_pay_leftin_div"> 
      <span class="success_payup_span">节省金额(￥)</span> 
      <br /> 
      <span class="success_paydown_span">${disAmount}</span> 
     </div> 
    </div> 
   </div> 
  </div> 
  <div class="point_success_downdiv"> 
   <span class="btn_border" style="float:left;width:45%;box-shadow:0 4px 15px #58a1ff;height:40px;line-height:32px;font-size:21px;margin-left:3%" onclick="javascript:location.replace('consumeOrder/getOrderListByStatus.do?orderStatus=3');">查看订单</span> 
   <span class="btn_border" style="float:right;width:45%;box-shadow:0 4px 15px #58a1ff;height:40px;line-height:32px;font-size:21px;background-color:red;border: 1px solid red;margin-right:3%" onclick="shouFenxiang();">分享领券</span> 
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