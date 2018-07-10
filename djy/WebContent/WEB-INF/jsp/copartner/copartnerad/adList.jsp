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
<title>商家活动</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<script src="js/jquery.more.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
        $(function() {
            wx.config({
                //debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId : '${ wxConfig.appId }', // 必填，公众号的唯一标识
                timestamp : ${ wxConfig.timestamp }, // 必填，生成签名的时间戳
                nonceStr : '${ wxConfig.nonceStr }', // 必填，生成签名的随机串
                signature : '${ wxConfig.signature }',// 必填，签名，见附录1
                jsApiList : [ 'scanQRCode','onMenuShareTimeline', 'onMenuShareAppMessage' ]
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
            
        });
        
    </script>
 <style>
	.am-titlebar-default .am-titlebar-title:before {border-left: 4px solid #3a88ef;height:1.8rem;margin-top: 1vw;}
	#talkbubble { top:-2vw; margin-left:0vw; width: 12vw; height: 6vw; background: #fd455e; position: relative; -moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius: 2vw; line-height:5vw; text-align:center; } 
	#talkbubble:before { content:""; position: absolute; right: 67%; top: 98%; width: 0; height: 0; border-left: 1vw solid transparent; border-right: 1vw solid transparent; border-top: 2vw solid #fd455e; }
	.listInfo .item:hover {border: 0px solid #DDDDDD;}
	</style>
  </head>
  
  <body>
    <!-- 顶部图片 -->
    <div class="am-slider am-slider-a1" data-am-flexslider="{playAfterPaused: 8000}" style="-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0);">
      <ul class="am-slides">
        <li>
          <img src="../copartner/img/aactivity_banner.jpg"></li>
      </ul>
    </div>
    <!-- 标题栏 -->
    <div data-am-widget="titlebar" class="am-titlebar am-titlebar-default" style="padding-left:35%;height:12.3vw;line-height:12.3vw;">
      <div class="classify-title-div">
      </div>
      <h2 class="classify-title" style="font-size:5vw;"><i class="icon iconfont icon-aixin" style="color: #fa5566;font-size: 6vw;"></i>&nbsp;商家优惠</h2>
	</div>
	
	<div id="adsdiv">
	<div class="product single_item info"></div>
	<a href="javascript:;" class="get_more"> </a>
	</div>
	

    <div style="height:11vw; line-height:11vw; clear:both;"></div>
    <footer class="v_nav">
      <div class="vf_nav">
        <ul class="am-avg-sm-5">
          <li>
            <a href="index.do">
              <i class="icon iconfont icon-shouye" style="color: #5d5d5d;"></i>
              <span style="color: #5d5d5d;">首页</span></a>
          </li>
          <li class="on">
            <a href="javascript:void(0);">
              <i class="icon iconfont icon-huodong"></i>
              <span>活动</span></a>
          </li>
          <li style="margin-top: -7vw;height:15vw;border-radius: 50%;background-color:rgba(255,255,255,1);    box-shadow: 0 4px 15px #58a1ff;">
            <div style="width:129%;height:100%;background-color: white;margin-top: 6.5vw;right: 3vw; position: relative;"></div>
            <a href="javascript:scanQrcode();" style="padding-right:12vw;">
              <i class="icon iconfont icon-saomiao" style="font-size:12vw;color:#4265fe;margin-top:-17vw;"></i>
            </a>
          </li>
          <li>
            <a href="actCoupon/actCouponList.do">
              <i class="icon iconfont icon-lingyouhuiquan" style="color: #5d5d5d;"></i>
              <span style="color: #5d5d5d;">领券</span></a>
          </li>
          <li>
            <a href="user/main.do">
              <i class="icon iconfont icon-wode1" style="color: #5d5d5d;"></i>
              <span style="color: #5d5d5d;">我的</span></a>
          </li>
        </ul>
      </div>
    </footer>
   <script type="text/javascript">

	   var currentLon;
	   var currentLat;
	   currentLat = sessionStorage.getItem("currentLat");
	   currentLon = sessionStorage.getItem("currentLon");
   
	   var url = 'copartnerad/getRecentCopartnerAd.do?longitude='+currentLon+'&latitude='+currentLat+'&page=';
		$('#adsdiv').more({
			'address' : url
		});
   
		function scanQrcode() {
		    wx.scanQRCode({
		        // 默认为0，扫描结果由微信处理，1则直接返回扫描结果
		        //顾客扫描二维码进行消费的时候，二维码上的信息显示为1zbh6678
		        //不符合这个规则就给它一个提示信息
		        needResult : 1,
		        desc : 'scanQRCode desc',
		        success : function(res) {
		        	 //扫码后获取结果参数赋值给Input
		            var url = res.resultStr;
		            //商品条形码，取","后面的
		            	try {
		            		var index = url.indexOf('http://weixin.qq.com');
		            		if(index==0){
		            			$.ajax({
		            				type : 'post',
		            				url : 'getScanCopartner.do',
		            				data : {
		            					'url': url
		            				},
		            				dataType:'json',
		            				async : true,
		            				success : function(data) {
		            					if (data.success) {
		            						
		            						var currentLons;
		            						   var currentLats;
		            						   currentLats = sessionStorage.getItem("currentLat");
		            						   currentLons = sessionStorage.getItem("currentLon");
		            						   
		            						window.location.href = 'copartnerDetail.do?id='+data.data+'&lat='+currentLats+'&lon='+currentLons;
		            						
		            					}else{
		            						Alert(data.data);
		            					}
		            				}
		            			});
		            		}else{
		            			Alert("请扫描店员提供的二维码！");
		            		}
						} catch (e) {
							Alert('扫描出错');
						}
		        }
		    });
		};
		
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