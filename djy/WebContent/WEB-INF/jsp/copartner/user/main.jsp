<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width" />
<title>个人中心</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
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
</head>
<body>
    <div class="am-padding-top-lg am-padding-right-sm am-padding-left-sm personal-top-div" >
     
     <div class="am-vertical-align">
        <img src="${user.wechatUser.headimgUrl}" class="am-inline-block am-vertical-align-middle" style="width:62px;height: auto; border-radius: 50%;"/>
        <div class="am-inline-block am-vertical-align-middle" style="padding-left:2vw;">
          <div>
            <span class="white">${user.wechatUser.nickname}</span></div>
        </div>
        <!--<span class="am-inline-block am-vertical-align-middle white personal-top-point-div">
          <span class="am-padding-horizontal-xs personal-top-point-span">
            <a href="personalSet.html">
              <white>
                <span>积分：666</span>
			 </white>
            </a>
          </span>
        </span>-->
      </div>
     
      <div class="ico_btn_bar am-padding-top-sm" style="padding-top:0rem;">
        <ul class="am-avg-sm-2">
          <li class="am-text-center personal-top-rli" style="margin-bottom: 2vw;">
            <a href="javascript:goto(${user.userAccount.canReceiveCoupons});">
              <span>
                <i class="quan" style="width:2rem;height:2rem;">
                  <c:if test="${not empty user.userAccount.canReceiveCoupons && user.userAccount.canReceiveCoupons > 0}">
                   <strong>${user.userAccount.canReceiveCoupons}</strong>
                  </c:if>
                  </i>
              </span>
              <span style="color:white;top:0vw;">领券机会</span></a>
          </li>
          <li class="am-text-center personal-top-lli" style="margin-bottom: 2vw;">
          <c:if test="${not empty user.coPartner}">
          <a href="../point/index.do">
           <span>
                <i class="market" style="width:2rem;height:2rem;"></i>
              </span>
              <span style="color:white;top:0vw;">入驻油站</span></a>
          </c:if>
            <c:if test="${empty user.coPartner}">
            <a href="coPartnerApply/coApply.do">
             <span>
                <i class="market" style="width:2rem;height:2rem;"></i>
              </span>
              <span style="color:white;top:0vw;">申请加盟</span></a>
            </c:if>
             
          </li>
        </ul>
      </div>
    </div>
    <!-- 节约金额 -->
    <div class="bg_white personal-save-div">
      <div id="left" class="personal-save-imgdiv">
        <img src="../copartner/img/apersonal-gift.png" class="personal-save-img"></div>
      <div id="right" class="personal-save-rightdiv">
        <div class="personal-save-righttopdiv">
          <span style="font-size:5vw">油惠站已为您节省金额(元)</span></div>
        <div class="personal-save-rightbottomdiv">
          <span class="personal-saveprice">
          <fmt:formatNumber value="${user.userAccount.totalEconomise}" pattern="0.00" type="number" />
          </span>
       </div>
      </div>
    </div>
    <div class="blank3"></div>
    <!-- 我的订单 -->
    <div class="bg_white">
      <hr class="am-margin-0" />
      <div class="ico_btn_bar am-padding-top-sm">
        <ul class="am-avg-sm-3">
          <li class="am-text-center">
            <a href="couponDiscount/noUserList.do">
              <span>
                <i class="youhuiquan">
                <c:if test="${noUserCouNum != 0}">
                 <strong>${noUserCouNum}</strong>
                </c:if>
                 </i>
              </span>
              <span style="top:0vw;">优惠券</span></a>
          </li>
          <li class="am-text-center">
            <a href="consumeOrder/getOrderListByStatus.do?orderStatus=1">
              <span>
                <i class="dingdan"></i>
              </span>
              <span>订单</span></a>
          </li>
          <li class="am-text-center">
            <a href="user/mySpread.do">
              <span>
                <i class="youli"></i>
              </span>
              <span>邀请有礼</span></a>
          </li>
        </ul>
      </div>
    </div>
    <div class="blank3"></div>
    <div class="Wallet main_top">
      <a href="userSiteMsg/msg.do">
        <i class="icon iconfont icon-xiaoxi" style="float: left;line-height: 50px;padding-left: 3.5vw;color: #3886ef">
        </i>
        <dl class="border_bottm personal-list" style="float: right;height: 50px;">
          <dt>
          	消息
          	<c:if test="${noReadMsgNum > 0 }">
          		（<font style="color:red;">${noReadMsgNum}条未读</font>）
          	</c:if>
          </dt>
          <dd style=" margin-top: 0rem;font-size: 1rem;">
          	&nbsp;
          </dd>
        </dl>
      </a>
      <c:if test="${not empty user.mobile}">
      <a href="../point/bindPhone/bindPhone.do" style="text-align: center;">
        <i class="icon iconfont icon-shouji" style="float: left;line-height: 50px;padding-left: 3.5vw;color: #3886ef;"></i>
        <dl class="border_bottm personal-list" style="float: right;height: 50px;">
          <dt>更换手机</dt>
          <dd style="font-size: 14px;margin-top: 0rem;">${user.mobile}</dd></dl>
      </a>
      </c:if>
      <c:if test="${empty user.mobile}">
      <a href="../point/bindPhone/bindPhone.do" style="text-align: center;">
        <i class="icon iconfont icon-shouji" style="float: left;line-height: 50px;padding-left: 3.5vw;color: #3886ef;"></i>
        <dl class="border_bottm personal-list" style="float: right;height: 50px;">
          <dt>绑定手机</dt>
          <dd style="font-size: 14px;margin-top: 0rem;">&nbsp;</dd></dl>
      </a>
      </c:if>
      <a href="javascript:callPhone();" style="text-align: center;">
        <i class="icon iconfont icon-dianhua" style="float: left;line-height: 50px;padding-left: 3.5vw;color: #3886ef;"></i>
        <dl class="border_bottm personal-list" style="float: right;height: 50px;">
          <dt>油惠站联系电话</dt>
          <dd style="font-size: 14px;margin-top: 0rem;">010-64399622</dd></dl>
      </a>
      <a href="coPartnerApply/coApplyList.do" style="text-align: center;">
        <i class="icon iconfont icon-jilu" style="float: left;line-height: 50px;padding-left: 3.5vw;color: #3886ef;"></i>
        <dl class="border_bottm personal-list" style="float: right;height: 50px;">
          <dt>油站申请记录</dt>
          <dd style="font-size: 14px;margin-top: 0rem;">&nbsp;</dd></dl>
      </a>
      <c:if test="${not empty user.sysEmpl}">
      <a href="bouns/bounsList.do?page=1" style="text-align: center;">
        <i class="icon iconfont icon-anonymous-iconfont" style="float: left;line-height: 50px;padding-left: 3.5vw;color: #3886ef;"></i>
        <dl class="border_bottm personal-list" style="float: right;height: 50px;">
          <dt>我的提成</dt>
          <dd style="font-size: 14px;margin-top: 0rem;">&nbsp;</dd></dl>
      </a>
      </c:if>
       <c:if test="${not empty user.coPartnerEmpl}">
      <a href="consumeOrder/getEmplOrderList.do?page=1" style="text-align: center;">
        <i class="icon iconfont icon-anonymous-iconfont" style="float: left;line-height: 50px;padding-left: 3.5vw;color: #3886ef;"></i>
        <dl class="border_bottm personal-list" style="float: right;height: 50px;">
          <dt>我的营业额</dt>
          <dd style="font-size: 14px;margin-top: 0rem;">&nbsp;</dd></dl>
      </a>
      </c:if>
      <c:if test="${not empty user.spreadPromoter && user.spreadPromoter.status == 1}">
      <a href="user/mySpreadQrCode.do" style="text-align: center;">
        <i class="icon iconfont icon-anonymous-iconfont" style="float: left;line-height: 50px;padding-left: 3.5vw;color: #3886ef;"></i>
        <dl class="border_bottm personal-list" style="float: right;height: 50px;">
          <dt>我的推广</dt>
          <dd style="font-size: 14px;margin-top: 0rem;">&nbsp;</dd></dl>
      </a>
      </c:if>
      <br>
    </div>
    <br>
    <div style="height:11vw; line-height:11vw; clear:both;"></div>
    <footer class="v_nav">
      <div class="vf_nav">
        <ul class="am-avg-sm-5">
          <li>
            <a href="index.do">
              <i class="icon iconfont icon-shouye" style="color: #5d5d5d;"></i>
              <span style="color: #5d5d5d;">首页</span></a>
          </li>
          <li>
            <a href="copartnerad/adList.do">
              <i class="icon iconfont icon-huodong" style="color: #5d5d5d;"></i>
              <span style="color: #5d5d5d;">活动</span></a>
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
          <li class="on">
            <a href="javascript:void(0);">
              <i class="icon iconfont icon-wode1"></i>
              <span>我的</span></a>
          </li>
        </ul>
      </div>
    </footer>
    
    	<!-- 恭喜您层 -->
		<div id="ID_VoucherPrompt" class="am-modal"  tabindex="-1">
			<div class="am-modal-dialog voucherPrompt" style="width: 85%;vertical-align:top;">
				<i class="close" data-am-modal-close></i>
				<img src="../copartner/img/back-lingquancon.png" class="con-bgimg">
				<a href="user/mySpread.do" style="position:absolute;top: 75%;left:15%;background-color: #fdcd10;color: white;padding: 3px;padding-left: 5px; padding-right: 5px; border-radius: 6px;">立即分享</a>
				<a href="actCoupon/actCouponList.do" style="position:absolute;top: 75%;right:15%;background-color: #ff5336;color: white;padding: 3px;padding-left: 5px; padding-right: 5px; border-radius: 6px;">立即领取</a>
				<span class="con-span">拥有${user.userAccount.canReceiveCoupons}次领取优惠券的机会,分享可获更多领取机会。</span>
			</div>
		</div>
		
		<!-- 遗憾层 -->
		<div id="ID_VoucherPrompts" class="am-modal" tabindex="-1">
			<div class="am-modal-dialog voucherPrompt" style="width: 85%;vertical-align:top;">
				<i class="close" data-am-modal-close></i>
				<img src="../copartner/img/back-lingquanpity.png" class="con-bgimg">
				<a href="user/mySpread.do" style="position:absolute;top: 75%;left:40%;background-color: #fdcd10;color: white;padding: 3px;padding-left: 5px; padding-right: 5px; border-radius: 6px;">立即分享</a>
				<span class="con-span">您暂无领取优惠券的机会,分享可获更多领取机会。</span>
			</div>
		</div>
		 <!-- 打电话层 -->
    <div id="ID_VoucherPrompt1" class="am-modal" tabindex="-1">
      <div class="am-modal-dialog voucherPrompt con-callphoneceng">
        <a href="tel:01064399622" style="font-size:2rem;">
        <div class="con-callphonecengtop">
          010-64399622
		</div>
		</a>
        <div class="con-callphonecengbottom" data-am-modal-close>
          <a href="javascript:void();" style="font-size:2rem;">取消</a>
		</div>
      </div>
    </div>
    <script type="text/javascript">
      
      function getHeight() {
        if ($("#left")[0].offsetHeight >= $("#right")[0].offsetHeight) {
          $("#right")[0].style.height = $("#left")[0].offsetHeight + "px";
        } else {
          $("#left")[0].style.height = $("#right")[0].offsetHeight + "px";
          var a = $("#right")[0].offsetHeight;
          document.getElementById('left').style.lineHeight = a + "px";
        }
      }
      window.onload = function() {
        getHeight();
      }
      
      function goto(vouNum){
    	  if(vouNum>0){
    		  $("#ID_VoucherPrompt").modal();
    	  }else{
    		  $('#ID_VoucherPrompts').modal();
    	  }
      }
      
      function callPhone() {
          var a = $('#ID_VoucherPrompt1');
          a.modal();
        }
      
      function showPoint(){
    	  Alert("您是消费会员，如需成为联盟油站，请联系客服！");
      }
      
      
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