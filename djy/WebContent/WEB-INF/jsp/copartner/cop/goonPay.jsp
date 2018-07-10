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
<title>继续支付</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body style="background-color:#fff;">
   <div class="blank3" style="background-color:#efefef;"></div>
    <!-- 总金额 -->
    <div class="bg_white payconfirm-totalprice">
      <span class="payconfirm-totalpriceicon">￥</span>
	  <span style="font-size:12vw;">${consumeOrder.consumeAmount}</span>
    </div>
    <div class="blank3" style="background-color:#efefef;"></div>
    <!--订单详情展示-->
	
	    <div class="Wallet main_top" style="border-bottom: 1px solid #efefef;">
      <a href="javascript:copartnerDetail(${consumeOrder.coPartner.id})">
        <em class="iconfont icon-dianpu payconfirm-img"></em>
        <dl class="border_bottm personal-list">
          <dt>${consumeOrder.coPartner.name}</dt>
          <dd style="margin-right:1rem;">${consumeOrder.coPartner.name}</dd></dl>
      </a>
      
          <c:if test="${not empty consumeOrder.couponDiscount}">
          <a href="javascript:void(0);">
	        <em class="iconfont icon-lingyouhuiquan payconfirm-img"></em>
	        <dl class="border_bottm personal-list">
	          <dt>优惠券</dt>
	          <dd style="background-image:none;margin-right:0rem;color:#fe0100;font-size:12px;font-weight:bolder;margin-top:0em;">${(consumeOrder.couponDiscount.discountPercent)/10}折优惠</dd>
	        </dl>
           </a>
          </c:if>
          <c:if test="${empty consumeOrder.couponDiscount}">
          <a href="javascript:void(0);">
	        <em class="iconfont icon-lingyouhuiquan payconfirm-img"></em>
	        <dl class="border_bottm personal-list">
	          <dt>优惠券</dt>
	          <dd style="margin-right:1rem;color:#fe0100;font-size:12px;font-weight:bolder;margin-top:0em;">0张</dd>
             </dl>
           </a>
          </c:if>
         
	        <a href="javascript:void(0);">
        <em class="iconfont icon-qianbao payconfirm-img"></em>
        <dl class="border_bottm personal-list1" >
          <dt>实付</dt>
          <dd style="background-image:none;margin-right:0rem;color:#fe0100;font-size:12px;font-weight:bolder;margin-top:0em;" id="realPrice">￥${consumeOrder.payAmount}</dd></dl>
      </a>
      <c:if test="${not empty consumeOrder.coPartnerEmpl}">
       <a href="javascript:void(0);">
        <em class="iconfont icon-zhengtonggongzuorenyuan payconfirm-img"></em>
        <dl class="border_bottm personal-list1" >
          <dt>加油员</dt>
          <dd style="background-image:none;margin-right:0rem;color:#fe0100;font-size:12px;font-weight:bolder;margin-top:0em;" id="realPrice">${consumeOrder.coPartnerEmpl.realName}</dd></dl>
      </a>
      </c:if>
	   <div class="blank3" style="background-color:#efefef;"></div>
	   <a href="javascript:void(0);">
        <em class="iconfont icon-pay-wechat" style="text-align:center;color:#40b037;font-size: 2rem;line-height:4rem;"></em>
        <dl class="border_bottm personal-list1">
          <dt>微信支付</dt>
          <dd style="background-image:none;margin-right:0rem;color:#fe0100;font-size:12px;font-weight:bolder;margin-top:0em;">
		  <em class="iconfont icon-duihao" style="text-align:center;color:#3b89f0;font-size: 2rem;"></em>
		  </dd></dl>
      </a>
    </div>
	
			<!-- 支付按钮条 -->
		<div class="bar posi_fixed posi_fixed_b">
			<ul class="am-g" style="border-top:0px;">
				
				<li class="am-u-sm-4 am-padding-0 am-vertical-align">
					<span class="am-vertical-align-middle" style="font-size: 12px;">
						已优惠：<span class="am-text-xs">￥</span><span id="ID_SumConvert">
						<fmt:formatNumber value="${consumeOrder.consumeAmount-consumeOrder.payAmount}" pattern="#0.00" />
						</span>
					</span>
				</li>
				
				
				<li class="am-u-sm-5 am-padding-0 am-vertical-align" style="text-align:right;">
					<span class="am-vertical-align-middle" style="padding-right:1vw;font-size: 12px;">
						待支付：<red><span class="am-text-xs">￥</span><span id="ID_SumToPay">${consumeOrder.payAmount}</span></red>
					</span>
				</li>
				

				<li class="am-u-sm-3 am-padding-0">
					<a id="ID_Submit" href="javascript:paySubmit();"><span class="btn_block btn_red" style="background-color:#3a88ef;font-size:1.5rem;">确定支付</span>
				</li>
			</ul>
		</div>

	<script type="text/javascript">
		function paySubmit(){
			
			$.ajax({
				type : 'post',
				url : 'getGoonConWehcatPay.do',
				data : {
					'orderId': ${consumeOrder.id}
				},
				dataType:'json',
				async : true,
				success : function(ret) {
					if (ret.success) {
						var d = ret.data;
						onBridgeReady(d.appId, d.timeStamp, d.nonceStr, d.packages, d.signType, d.paySign);//成功则调取支付界面
					}else{
						Alert(d);
					}
				}
			});
		}
		
		function onBridgeReady(appid, timeStamp, nonceStr, packageValue, signType, sign) {
			WeixinJSBridge.invoke('getBrandWCPayRequest', {
				"appId" : appid, //公众号名称，由商户传入     
				"timeStamp" : timeStamp, //时间戳，自1970年以来的秒数     
				"nonceStr" : nonceStr, //随机串     
				"package" : packageValue,
				 "signType" : signType, //微信签名方式
				"paySign" : sign //微信签名 
			}, function(res) {
				// 使用以下方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
				if (res.err_msg == "get_brand_wcpay_request:ok") {
					
					window.location.replace("consumeOrder/paySuccess.do?orderId="+${consumeOrder.id});

				} else if (res.err_msg == "get_brand_wcpay_request:fail") {
					
					Alert('支付失败');
					
				} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
					
					Alert('支付取消');
					
				} else {
					Alert(res.err_msg);
				}
			});
		}
		
		function copartnerDetail(copartnerId){
			　var currentLons;
			   var currentLats;
			   currentLats = sessionStorage.getItem("currentLat");
			   currentLons = sessionStorage.getItem("currentLon");
			   
			window.location.href = 'copartnerDetail.do?id='+copartnerId+'&lat='+currentLats+'&lon='+currentLons;
			
		}
	</script>
  </body>

</html>