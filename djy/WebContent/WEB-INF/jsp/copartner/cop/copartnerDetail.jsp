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
<title>${coPartner.name}</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RICZXAsZd6DBPrWiAsM37HA1NiynsqSP"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<script src="js/jquery.more.js"></script>
<style type="text/css">
	.warp{width:100%; max-width: 480px; margin: 0 auto; height: 50px; background: white; overflow: hidden; border-bottom: 1px solid #CCC; clear:both;} 
	.inner{ line-height: 50px; width:888px; height: 50px; position: relative; overflow:hidden;} 
	.inner a{display: block;padding: 0 10px; float: left; font-size:18px;color:black;} 
	.inner .on {color:blue;} 
	#talkbubble { top:-2vw; margin-left:0vw; width: 12vw; height: 6vw; background: #fd455e; position: relative; -moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius: 2vw; line-height:5vw; text-align:center; } 
	#talkbubble:before { content:""; position: absolute; right: 67%; top: 95%; width: 0; height: 0; border-left: 1vw solid transparent; border-right: 1vw solid transparent; border-top: 2vw solid #fd455e; }
    .listVoucher .item:hover {border: 0px solid #ffa800;}
</style>
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
    <div class="am-slider am-slider-b1" data-am-flexslider="{slideshow: false}" >
			<ul class="am-slides">
      <c:forEach var="z" items="${coPartner.coPartnerImgs}" varStatus="status">
      <li>
          <img src="${z.imgSrc}" style="max-height:210px;">
        </li>
      </c:forEach>
        
      </ul>
</div>
    
    
    <div class="am-g bg_white" >
      <div class="am-u-sm-9" id="left">
        <div class="am-padding-top-sm am-padding-right-sm am-padding-left-sm am-padding-bottom-sm">
          <span class="con-name">${coPartner.name}</span>
          <span class="con-adr" onclick="gotoAdr();">${coPartner.county}${coPartner.town}${coPartner.address}
            <img src="../copartner/img/location.png" class="con-adricon"></span></div>
      </div>
      <div class="am-u-sm-3 con-callphone" id="right">
        <a href="javascript:callPhone();">
          <img src="../copartner/img/call-phone.png" class="con-callphoneimg"></a>
      </div>
    </div>
    <div class="listVoucher am-padding-top-sm" style="padding-top: 0rem;">
      <c:if test="${not empty copartnerDetailActCouVos}">
      
      <ul class="am-list am-margin-0">
       <li class="am-padding-xs am-padding-top-0" style="padding-bottom: 0rem;">
       <c:if test="${copartnerDetailActCouVos[0].status == 1}">
        <a href="javascript:void(0);">
       </c:if>
        <c:if test="${copartnerDetailActCouVos[0].status == 0}">
        <a href="javascript:getAct(${copartnerDetailActCouVos[0].actCoupon.id});">
       </c:if>
            <div class="am-padding-xs item">
              <img src="../copartner/img/ayhqback.png" class="am-img-responsive" style="width:100%;" />
              <div class="c1" style="height:20vw;width:20vw;" align="center">
                <img src="../copartner/img/dzq1.png" class="voucher-img" /></div>
              <div class="c2" style="left: 30%;">
                <div>${copartnerDetailActCouVos[0].actCoupon.coPartner.name}</div></div>
              <div class="am-text-xs end">
                <c:if test="${not empty copartnerDetailActCouVos[0].actCoupon.useEndDate}">
                 	有效期至：<fmt:formatDate pattern="yyyy.MM.dd" value="${copartnerDetailActCouVos[0].actCoupon.useEndDate}" />
                </c:if>
                <c:if test="${empty copartnerDetailActCouVos[0].actCoupon.useEndDate}">
                 	有效期：无限制
                </c:if>                            
              </div>
              <c:if test="${copartnerDetailActCouVos[0].status == 1}">
              <i class="icon iconfont icon-yilingqu" style="position: absolute;top: 13%;right: 32%;font-size: 13vw;"></i>
              </c:if>
              <div class="status">
              <c:if test="${copartnerDetailActCouVos[0].status == 0}">
              <div class="voucher-percent">${copartnerDetailActCouVos[0].actCoupon.discountPercent/10}折</div>
              <c:if test="${not empty copartnerDetailActCouVos[0].actCoupon.useMinConsume}">
              <div class="voucher-totalprice">满${copartnerDetailActCouVos[0].actCoupon.useMinConsume}可用</div>
              </c:if>
              <c:if test="${empty copartnerDetailActCouVos[0].actCoupon.useMinConsume}">
              <div class="voucher-totalprice">&nbsp;</div>
              </c:if>
                
                <div class="voucher-get-div">
                  <span class="voucher-get-span">&nbsp;&nbsp;立即领取&nbsp;&nbsp;</span></div>
              </c:if>
                <c:if test="${copartnerDetailActCouVos[0].status == 1}">
              <div class="voucher-percentlose">${copartnerDetailActCouVos[0].actCoupon.discountPercent/10}折</div>
              <c:if test="${not empty copartnerDetailActCouVos[0].actCoupon.useMinConsume}">
              <div class="voucher-totalpricelose">满${copartnerDetailActCouVos[0].actCoupon.useMinConsume}可用</div>
              </c:if>
              <c:if test="${empty copartnerDetailActCouVos[0].actCoupon.useMinConsume}">
              <div class="voucher-totalpricelose">&nbsp;</div>
              </c:if>
                <div class="voucher-get-div">
                  <span class="voucher-get-spanlose">&nbsp;&nbsp;已领取&nbsp;&nbsp;</span></div>
              </c:if>
              </div>
            </div>
          </a>
        </li>
      </ul>
      </c:if>
      
      <c:if test="${fn:length(copartnerDetailActCouVos) > 1}">
      	<div id="collapse_Orders_Details_2" style="padding-left: 0rem;padding-right: 0rem; margin-top: -0.5rem;padding-top: 0rem;" class="am-panel-collapse am-collapse am-padding-horizontal-xs listVoucher am-padding-top-sm">
     <ul class="am-list am-margin-0">
      <c:forEach var="i" items="${copartnerDetailActCouVos}" varStatus="status">
       
       <c:if test="${(status.index) != 0}">
       <li class="am-padding-xs am-padding-top-0" style="margin-top: -0.5rem;padding-bottom: 0rem;">
       <c:if test="${i.status == 1}">
        <a href="javascript:void(0);">
       </c:if>
        <c:if test="${i.status == 0}">
        <a href="javascript:getAct(${i.actCoupon.id});">
       </c:if>
            <div class="am-padding-xs item">
              <img src="../copartner/img/ayhqback.png" class="am-img-responsive" style="width:100%;" />
              <div class="c1" style="height:20vw;width:20vw;" align="center">
                <img src="../copartner/img/dzq1.png" class="voucher-img" /></div>
              <div class="c2" style="left: 30%;">
                <div>${i.actCoupon.coPartner.name}</div></div> 
              <div class="am-text-xs end">
                <c:if test="${not empty i.actCoupon.useEndDate}">
                 	有效期至：<fmt:formatDate pattern="yyyy.MM.dd" value="${i.actCoupon.useEndDate}" />
                </c:if>
                <c:if test="${empty i.actCoupon.useEndDate}">
                 	有效期：无限制
                </c:if>                            
              </div>
              
               <c:if test="${i.status == 1}">
              <i class="icon iconfont icon-yilingqu" style="position: absolute;top: 13%;right: 32%;font-size: 13vw;"></i>
              </c:if>         
              <div class="status">
              <c:if test="${i.status == 0}">
              <div class="voucher-percent">${i.actCoupon.discountPercent/10}折</div>
              <c:if test="${not empty i.actCoupon.useMinConsume}">
              <div class="voucher-totalprice">满${i.actCoupon.useMinConsume}可用</div>
              </c:if>
                <c:if test="${empty i.actCoupon.useMinConsume}">
              <div class="voucher-totalprice">&nbsp;</div>
              </c:if>
                <div class="voucher-get-div">
                  <span class="voucher-get-span">&nbsp;&nbsp;立即领取&nbsp;&nbsp;</span></div>
              </c:if>
                <c:if test="${i.status == 1}">
              <div class="voucher-percentlose">${i.actCoupon.discountPercent/10}折</div>
              <c:if test="${not empty i.actCoupon.useMinConsume}">
              <div class="voucher-totalpricelose">满${i.actCoupon.useMinConsume}可用</div>
              </c:if>
              <c:if test="${empty i.actCoupon.useMinConsume}">
              <div class="voucher-totalpricelose">&nbsp;</div>
              </c:if>
                <div class="voucher-get-div">
                  <span class="voucher-get-spanlose">&nbsp;&nbsp;已领取&nbsp;&nbsp;</span></div>
              </c:if>
              </div>
            </div>
          </a>
        </li>
        </c:if>
      </c:forEach>
      </ul>
		</div>
        <div class="am-text-center" data-am-collapse="{target: '#collapse_Orders_Details_2'}">
			<span class="am-text-sm gray">点击展示剩余抵用券</span>
		</div>
      </c:if>
    </div>

    <div class="bg_white" style="position: absolute;margin-top: 0.5vw;width: 100%;">
      <div data-am-widget="tabs" class="am-tabs am-tabs-default con-tab">
        <ul class="am-tabs-nav am-cf con-tabul" style="background-color:#fff;">
          <li class="am-active">
            <a href="[data-tab-panel-0]" style="border-top-left-radius:1vw;border-bottom-left-radius:1vw;">实时消费</a></li>
          <li class="">
            <a href="[data-tab-panel-1]" style="border-top-right-radius:1vw;border-bottom-right-radius:1vw;">店内活动</a></li>
        </ul>
        <div class="am-tabs-bd" style="border: 0px solid #ddd;">
          <div data-tab-panel-0 class="am-tab-panel am-active" style="padding: 10px 0px 15px;">
           <c:forEach var="m" items="${consumeOrders}" varStatus="status">
             <c:if test="${((status.index)+1 != fn:length(consumeOrders)) || (status.index == 0)}">
             <div class="am-g" style="background-color:#fff;line-height: 24px;">
              <!--第一个及其他的列表项-->
              <div class="am-u-sm-2" id="left1" style="padding-left: 0rem;">
                <div class="am-vertical-align-middle" style="width: 400px;height: 53px;display: table-cell; vertical-align: middle;text-align: center;">
                  <img src="${m.user.wechatUser.headimgUrl}" class="am-circle" height="35" /></div>
              </div>
              <div class="am-u-sm-10 con-consumlist" id="right1">
                <ul class="am-list am-margin-0">
                  <li class="am-padding-xs" style="border-top:0px;padding: 0rem;font-size:12px;">
                    <div class="am-cf">
                      <span class="am-fl" style="color:#d4d4d4;">${m.user.wechatUser.nickNameStar}</span>
                      <span class="am-fr" style="color:#d4d4d4;">本单节省</span></div>
                    <div class="am-cf" style="margin-top: 2vw;">
                      <span class="am-fl"><fmt:formatDate pattern="HH:mm" value="${m.insertTime}"/>在此店消费&nbsp;￥${m.consumeAmount}</span>
                      <span class="am-fr" style="color:red;">￥
                      <fmt:formatNumber value="${m.consumeAmount-m.payAmount}" pattern="0.00" type="number" />
                      </span></div>
                  </li>
                </ul>
              </div>
            </div>
             </c:if>
             
             <c:if test="${status.index != 0 && status.index+1 == fn:length(consumeOrders)}">
              <div class="am-g" style="background-color:#fff;line-height: 24px;">
              <!--最后一个列表项-->
              <div class="am-u-sm-2" id="left1" style="padding-left: 0rem;">
                <div class="am-vertical-align-middle" style="width: 400px;height: 53px;display: table-cell; vertical-align: middle;text-align: center;">
                  <img src="${m.user.wechatUser.headimgUrl}" class="am-circle" height="35" /></div>
              </div>
              <div class="am-u-sm-10 con-consumlist" id="right1">
                <ul class="am-list am-margin-0">
                  <li class="am-padding-xs" style="border-top:0px;padding: 0rem;font-size:12px;border:0px;">
                    <div class="am-cf">
                      <span class="am-fl" style="color:#d4d4d4;">${m.user.wechatUser.nickNameStar}</span>
                      <span class="am-fr" style="color:#d4d4d4;">本单节省</span></div>
                    <div class="am-cf" style="margin-top: 2vw;">
                      <span class="am-fl"><fmt:formatDate pattern="HH:mm" value="${m.insertTime}" />在此店消费&nbsp;￥${m.consumeAmount}</span>
                      <span class="am-fr" style="color:red;">￥
                      <fmt:formatNumber value="${m.consumeAmount-m.payAmount}" pattern="0.00" type="number" /></span></div>
                  </li>
                </ul>
              </div>
            </div>
             </c:if>
           </c:forEach>
            
          </div>
          <div data-tab-panel-1 class="am-tab-panel ">
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
                  <img src="${n.coPartner.logoSrc}" class="am-img-responsive" /></div>
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
          </div>
        </div>
      </div>
      <hr style="margin-top:0rem;">

     <div class="v_nav" style="line-height:12vw;z-index: 999;box-shadow: 0 0px 0px #58a1ff;">
      <div class="vf_nav">
        <div class="btn-div">
          <span class="button blue bigrounded" onclick="payConfirm();">　立即支付　</span></div>
      </div>
      </div>
      
    </div>
    <!-- 打电话层 -->
    <div id="ID_VoucherPrompt" class="am-modal" tabindex="-1">
      <div class="am-modal-dialog voucherPrompt con-callphoneceng">
       <a href="tel:${coPartner.contactMobile}" style="font-size:2rem;">
        <div class="con-callphonecengtop">
          ${coPartner.contactMobile}
		</div>
		</a>
        <div class="con-callphonecengbottom" data-am-modal-close>
          <a href="javascript:void();" style="font-size:2rem;" >取消</a>
		</div>
      </div>
    </div>
    <script type="text/javascript">
	$(function() {
        getHeight();
        sessionStorage.setItem("price", '');
        sessionStorage.setItem("percent", '');
    	sessionStorage.setItem("conDiscountId", '');
      });

      function callPhone() {
        var a = $('#ID_VoucherPrompt');
        a.modal();
      }
      
      function gotoAdr(){
    	  
    	  window.location.href = "getAdr.do?copartnerId=${coPartner.id}";
    	 
    	 
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
      
      function getAct(actId){
			$.ajax({
				type : 'post',
				url : 'actCoupon/getAct.do',
				data : {
					'actId': actId
				},
				dataType:'json',
				async : true,
				success : function(data) {
					if (data.success) {
						 location.reload();
					}else{
						Alert(data.data);
					}
				}
			});
		}
      
      function payConfirm(){
    	  if(${isFit}){
    		  window.location.replace('payConfirm.do?copartnerId=${coPartner.id}');
    	  }else{
    		  Confirm({
  				msg : '您是否要在${coPartner.name}加油？',
  				cancelBtn : '取消',
  				okBtn : '继续',
  				cancel : function() { },
  				ok : function() {
  					window.location.replace('payConfirm.do?copartnerId=${coPartner.id}');
  				},
  				relatedTarget: this
  			});
    	  }
    	  
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