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
<title>活动详情</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RICZXAsZd6DBPrWiAsM37HA1NiynsqSP"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<style>.am-titlebar-default .am-titlebar-title:before {border-left: 4px solid #3a88ef;height:1.8rem;margin-top: 1vw;}</style>
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
  
  <body>
    <!-- 顶部图片 -->
    <div class="am-slider am-slider-a1" data-am-flexslider="{playAfterPaused: 8000}" style="-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0);">
      <ul class="am-slides">
        
        <c:forEach var="i" items="${coPartnerAd.coPartner.coPartnerImgs}">
         <li><img src="${i.imgSrc}" style="max-height:210px;"></li>
        </c:forEach>
         
      </ul>
    </div>
    <div data-am-widget="titlebar" class="am-titlebar am-titlebar-default" style="border-bottom: 1px solid #efefef;">
      <h2 class="am-titlebar-title ">活动详情</h2>
	</div>
    <div class="activity-timecontent">
    <c:forEach var="m" items="${coPartnerAd.coPartnerAdTags}" varStatus="status">
    <c:if test="${status.first}">
     <div class="activity-content">
    </c:if>
    <c:if test="${!status.first}">
     <div class="activity-content1">
    </c:if>
    <c:if test="${m.type == 1}">
     <span class="activity-jianspan">减</span>
    </c:if>
      <c:if test="${m.type == 2}">
     <span class="activity-zhespan">折</span>
    </c:if>
    <c:if test="${m.type == 3}">
     <span class="activity-songspan">送</span>
    </c:if>
   
      ${m.title}
               
    </div>
    </c:forEach>
      
      
      <div class="activity-time">
        <i class="icon iconfont icon-shijian" style="font-size:18px;margin-right:2vw;"></i>
        <span style="color:#8e8e8e;">活动时间:
            <c:if test="${not empty coPartnerAd.startTime}">
            <fmt:formatDate value="${coPartnerAd.startTime}" pattern="yyyy/MM/dd"/>
            </c:if>
            <c:if test="${empty coPartnerAd.startTime}">
                                           现在
            </c:if>
            -
             <c:if test="${not empty coPartnerAd.endTime}">
            <fmt:formatDate value="${coPartnerAd.endTime}" pattern="yyyy/MM/dd"/>
            </c:if>
            <c:if test="${empty coPartnerAd.endTime}">
                                           无限制
            </c:if>
         </span>
       </div>
    </div>
    <div class="am-g bg_white" style="border-top:1px solid #efefef;">
      <div class="am-u-sm-9" id="left">
        <div class="am-padding-top-sm am-padding-right-sm am-padding-left-sm am-padding-bottom-sm">
          <span class="con-name">${coPartnerAd.coPartner.name}</span>
          <span class="con-adr" onclick="gotoAdr();">${coPartnerAd.coPartner.county}${coPartnerAd.coPartner.town}${coPartnerAd.coPartner.address}
            <img src="../copartner/img/location.png" class="con-adricon"></span></div>
      </div>
      <div class="am-u-sm-3 con-callphone" id="right">
        <a href="javascript:callPhone();">
          <img src="../copartner/img/call-phone.png" class="con-callphoneimg"></a>
      </div>
    </div>
    <div data-am-widget="titlebar" class="am-titlebar am-titlebar-default active-other">
      <h2 class="am-titlebar-title ">其他活动</h2></div>
    <!-- 信息列表 -->
    <div class="listInfo bg_white">
      <ul class="am-list am-margin-0">
      
      <c:forEach var="n" items="${coPartnerAds}" varStatus="status">
      <li class="am-padding-xs" style="border-top: 1px solid #dedede;">
          
          <c:if test="${empty n.url}">
          <a href="copartnerad/activityDetail.do?activityId=${n.id}">
          </c:if>
          <c:if test="${not empty n.url}">
          <a href="${n.url}">
          </c:if>
          
            <div class="item">
              <div class="am-g">
                <div class="am-u-sm-4 am-padding-xs" style="padding-top:3vw;">
                  <img src="${n.coPartner.logoSrc}" class="am-img-responsive" style="height: 90px;"/></div>
                <div class="am-u-sm-8 am-padding-xs">
                  <div class="title" style="font-size:4.5vw;">${n.coPartner.name}</div>
                  <span class="activity-tradename">[${n.coPartner.town}]${n.coPartner.slogan}</span>
                <c:forEach var="z" items="${n.coPartnerAdTags}" varStatus="status">
                <c:if test="${status.first}">
                 <div class="am-text-sm" style="font-size:4vw;padding-top:8vw;">
                    <c:if test="${z.type == 1}">
                    <span class="activity-jian">减</span>&nbsp;&nbsp;${z.title}
                   </c:if>
                   <c:if test="${z.type == 2}">
                    <span class="activity-zhe">折</span>&nbsp;&nbsp;${z.title}
                   </c:if>
                   <c:if test="${z.type == 3}">
                    <span class="activity-song">送</span>&nbsp;&nbsp;${z.title}
                   </c:if>
                  </div>
                </c:if>
                
                <c:if test="${!status.first}">
                 <div class="am-text-sm" style="font-size:4vw;">
                    <c:if test="${z.type == 1}">
                    <span class="activity-jian">减</span>&nbsp;&nbsp;${z.title}
                   </c:if>
                   <c:if test="${z.type == 2}">
                    <span class="activity-zhe">折</span>&nbsp;&nbsp;${z.title}
                   </c:if>
                   <c:if test="${z.type == 3}">
                    <span class="activity-song">送</span>&nbsp;&nbsp;${z.title}
                   </c:if>
                  </div>
                </c:if>
                </c:forEach>
                </div>
              </div>
            </div>
          </a>
        </li>
      </c:forEach>
      
      </ul>
    </div>
    <!-- 打电话层 -->
    <div id="ID_VoucherPrompt" class="am-modal" tabindex="-1">
      <div class="am-modal-dialog voucherPrompt con-callphoneceng">
        <div class="con-callphonecengtop">
          <a href="tel:${coPartnerAd.coPartner.contactMobile}" style="font-size:2rem;">${coPartnerAd.coPartner.contactMobile}</a>
		</div>
        <div class="con-callphonecengbottom" data-am-modal-close>
          <a href="javascript:void();" style="font-size:2rem;" >取消</a>
		</div>
      </div>
    </div>
    <div style="height:11vw; line-height:11vw; clear:both;"></div>
    <div class="v_nav" style="line-height:12vw;">
      <div class="vf_nav">
        <div class="btn-div">
          <span class="button blue bigrounded" onclick="copartnerDetail(${coPartnerAd.coPartner.id})">　前去支持　</span></div>
      </div>
    </div>
    <script type="text/javascript">$(function() {
        getHeight();
      });
      function callPhone() {
        var a = $('#ID_VoucherPrompt');
        a.modal();
      }
      
      function gotoAdr(){
				
				 window.location.href = "getAdr.do?copartnerId=${coPartnerAd.coPartner.id}";
      }
      
      function getHeight() {
        if (document.getElementById('left').offsetHeight >= document.getElementById('right').offsetHeight) {
          document.getElementById('right').style.height = document.getElementById('left').offsetHeight + "px";
          var a = document.getElementById('left').offsetHeight;
          document.getElementById('right').style.lineHeight = a + "px";
        } else {
          document.getElementById('left').style.height = document.getElementById('right').offsetHeight + "px";
        }
      }
      
      function copartnerDetail(copartnerId){
 		　var currentLons;
 		   var currentLats;
 		   currentLats = sessionStorage.getItem("currentLat");
 		   currentLons = sessionStorage.getItem("currentLon");
 		   
 		window.location.href = 'copartnerDetail.do?id='+copartnerId+'&lat='+currentLats+'&lon='+currentLons;
 		
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