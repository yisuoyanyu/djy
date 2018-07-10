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
<title>确认订单</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body style="background-color:#fff;">
     <header data-am-widget="header" class="am-header am-header-fixed am-header-default">
		<div class="am-header-left am-header-nav">
			<a href="javascript:history.back(-1);">
				<i class="am-header-icon am-icon-angle-left am-icon-sm"></i>
			</a>
		</div>
		<c:if test="${noPayOrderNum > 0}">
		<h1 class="am-header-title" style="width:90%;margin:0 13%;text-decoration: underline;">
			<a href="javascript:location.replace('consumeOrder/getOrderListByStatus.do?orderStatus=1');" style="text-decoration:underline"><marquee style="text-decoration : sDecoration;color: red;font-size: 15px;" scrollamount=5 >您在本店有尚未支付的${noPayOrderNum}个订单，点击继续支付</marquee></a>
		</h1>
		</c:if>
		<c:if test="${noPayOrderNum == 0}">
		<h1 class="am-header-title">
				<a href="javascript:void(0);">确认订单</a>
			</h1>
		</c:if>
	</header>
    <!-- 总金额 -->
    <div class="bg_white payconfirm-totalprice">
      <span class="payconfirm-totalpriceicon">￥</span>
	  <input type="number" onchange="limitInput();" onkeyup="this.value=this.value.replace(/[^\w]/g,'');" onfocus="this.placeholder=''" onblur="this.placeholder='0'" id="price" style="font-size:12vw;width:70vw;margin-top:-8vw;border:none;background-color:transparent;" placeholder="0">
    </div>
    <div class="blank3" style="background-color:#efefef;"></div>
    <!--订单详情展示-->
	
	    <div class="Wallet main_top" style="border-bottom: 1px solid #efefef;">
      <a href="javascript:copartnerDetail(${coPartner.id})">
        <em class="iconfont icon-dianpu payconfirm-img"></em>
        <dl class="border_bottm personal-list">
          <dt>${coPartner.name}</dt>
          <dd style="margin-right:1rem;">${coPartner.name}</dd></dl>
      </a>
      
          <c:if test="${discountNum>0}">
          <a href="javascript:getCoudiscount(${coPartner.id});">
	        <em class="iconfont icon-lingyouhuiquan payconfirm-img"></em>
	        <dl class="border_bottm personal-list">
	          <dt>优惠券</dt>
	           <dd style="background-image:none;margin-right:0rem;font-size:12px;font-weight:bolder;margin-top:0em; " id="discount1">请选择优惠券</dd>
             </dl>
           </a>
          </c:if>
          <c:if test="${discountNum==0}">
          <a href="javascript:void(0);">
	        <em class="iconfont icon-lingyouhuiquan payconfirm-img"></em>
	        <dl class="border_bottm personal-list">
	          <dt>优惠券</dt>
	          <dd style="margin-right:1rem;color:#fe0100;font-size:12px;font-weight:bolder;margin-top:0em;">0张</dd>
             </dl>
           </a>
          </c:if>
         
       <c:if test="${discountNum>0}">
      <a href="javascript:void(0);">
        <em class="iconfont icon-dazhe" style="text-align:center;font-size: 2rem;line-height:4rem;color:#fc97a6;"></em>
        <dl class="border_bottm personal-list">
          <dt style="font-size:12px;color:#8e8e8e;font-weight:normal;" id="discount2">请选择优惠</dt>
          <dd style="background-image:none;margin-right:0rem;font-size:12px;font-weight:bolder;margin-top:0em;" id="discountPrice">￥0</dd></dl>
      </a>
      </c:if>
	        <a href="javascript:void(0);">
        <em class="iconfont icon-qianbao payconfirm-img"></em>
        <dl class="border_bottm personal-list">
          <dt>实付</dt>
          <dd style="background-image:none;margin-right:0rem;color:#fe0100;font-size:12px;font-weight:bolder;margin-top:0em;" id="realPrice">￥0</dd></dl>
      </a>
      <c:if test="${not empty coPartnerEmpls}">
      <a href="javascript:void(0);">
        <em class="iconfont icon-zhengtonggongzuorenyuan payconfirm-img"></em>
        <dl class="border_bottm personal-list1" >
          <dt>加油员</dt>
          <dd style="background-image:none;margin-right:0rem;color:#fe0100;font-size:12px;font-weight:bolder;margin-top:0em;width: 75%;" id="realPrice">
           <select style="width: 100%;height: 8vw;" name="empl" id="empl">
           <option value="0" selected="selected">请选择</option>
			  <c:forEach var="i" items="${coPartnerEmpls}" varStatus="status">
			  <option value="${i.id}">${i.realName}</option>
			  </c:forEach>
			 </select>
          </dd>
        </dl>
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
				 <c:if test="${discountNum>0}">
				<li class="am-u-sm-4 am-padding-0 am-vertical-align">
					<span class="am-vertical-align-middle" style="font-size: 12px;">
						已优惠：<span class="am-text-xs">￥</span><span id="ID_SumConvert">0</span>
					</span>
				</li>
				</c:if>
				<c:if test="${discountNum>0}">
				<li class="am-u-sm-5 am-padding-0 am-vertical-align" style="text-align:right;">
					<span class="am-vertical-align-middle" style="padding-right:1vw;font-size: 12px;">
						待支付：<red><span class="am-text-xs">￥</span><span id="ID_SumToPay">0</span></red>
					</span>
				</li>
				</c:if> 
				<c:if test="${discountNum==0}">
				<li class="am-u-sm-5 am-padding-0 am-vertical-align" style="text-align:right;margin-left: 33%;">
					<span class="am-vertical-align-middle" style="padding-right:1vw;font-size: 12px;">
						待支付：<red><span class="am-text-xs">￥</span><span id="ID_SumToPay">0</span></red>
					</span>
				</li>
				</c:if>
				<li class="am-u-sm-3 am-padding-0">
					<div id="ID_Submit"><span class="btn_block btn_red" style="background-color:#3a88ef;font-size:1.5rem;">确定支付</span></div>
				</li>
			</ul>
		</div>

<script type="text/javascript">
var chuanPrice = 0;
$(function(){
	
	var percent = sessionStorage.getItem("percent");
	var percents;
	var conDiscountId;
	if (percent == null || percent == undefined || percent == '') {//表示从详情页进来的，没有选择其他任何优惠券
		
		if (${percent} <= 10) {
			percents = ${percent};
			conDiscountId = ${conDiscountId};
			 sessionStorage.setItem("percent", percents);
			 percent = percents;
			 sessionStorage.setItem("conDiscountId", conDiscountId);
		}
		
	 }
	
	
	var price = sessionStorage.getItem("price");
	 
	 if (price == null || price == undefined || price == '') {
		 price = 0;
	 }
	 
	if (percent == null || percent == undefined || percent == '') { //表示还没有选择打折券
		var realPrice = price;
	    $('#realPrice').text('￥'+realPrice);
	    $('#ID_SumToPay').text(realPrice);
	    if(price > 0){
	    	 $('#price').val(price);
	    }
	   
	}else{
		var realPrice = price;
		var discountPrice = (realPrice-(realPrice*percent/10)).toFixed(2);
		if(price > 0){
	    	 $('#price').val(price);
	    }
	    $('#realPrice').text('￥'+(realPrice*percent/10).toFixed(2));
	    $('#ID_SumToPay').text((realPrice*percent/10).toFixed(2));
	    $('#discountPrice').text('￥'+discountPrice);
	    $('#ID_SumConvert').text(discountPrice);
	    $('#discount1').text(percent+'折优惠');
	    $('#discount1').css("color","#fe0100");
	    $('#discount2').text(percent+'折优惠');
	}
	
	}); 

function limitInput(){
    var	limitPrice = $('#price').val();
    var min=2;
    if(parseInt(limitPrice)<min){
        Alert('请输入大于2的值');
        $('#price').val(10);
        $('#realPrice').text('￥'+0);
	    $('#ID_SumToPay').text(0);
	    $('#discountPrice').text('￥'+0);
	    $('#ID_SumConvert').text(0);
    }
}

$('#price').bind('input propertychange', function() {  
	chuanPrice = $('#price').val();
	var price = $('#price').val();
	sessionStorage.setItem("price", price);
	var percent = sessionStorage.getItem("percent");
	if (percent == null || percent == undefined || percent == '') { //表示还没有选择打折券
		var realPrice = price;
	    $('#realPrice').text('￥'+realPrice);
	    $('#ID_SumToPay').text(realPrice);
	}else{
		var realPrice = price;
		var discountPrice = (realPrice-(realPrice*percent/10)).toFixed(2);
	    $('#realPrice').text('￥'+(realPrice*percent/10).toFixed(2));
	    $('#ID_SumToPay').text((realPrice*percent/10).toFixed(2));
	    $('#discountPrice').text('￥'+discountPrice);
	    $('#ID_SumConvert').text(discountPrice);
	    $('#discount1').text(percent+'折优惠');
	    $('#discount1').css("color",":#fe0100");
	    $('#discount2').text(percent+'折优惠');
	}
});

function paySubmit(){
	
	var consumeAmount = $('#price').val();
	var totalPrice = $('#realPrice').text();
	var payAmount = totalPrice.substring(1);
	var conDiscountId = sessionStorage.getItem("conDiscountId");
	var copartnerId = ${coPartner.id};
	
	var emplValue = $('#empl option:selected') .val();//选中的值


	if(consumeAmount == 0){
		Alert('您输入的金额不能为0');
		return;
	}else if (emplValue != null || emplValue != undefined || emplValue != '') {
		if(emplValue == 0){
			Alert('请您选择加油员！');
			return;
		}
		$('#ID_Submit').unbind("click");
		$.ajax({
			type : 'post',
			url : 'getConWehcatPay.do',
			data : {
				'consumeAmount': consumeAmount,
				'payAmount': payAmount,
				'conDiscountId': conDiscountId,
				'copartnerId': copartnerId,
				'emplId':emplValue
			},
			dataType:'json',
			async : true,
			success : function(ret) {
				var d = ret.data;
				if (ret.success) {
					onBridgeReady(d.appId, d.timeStamp, d.nonceStr, d.packages, d.signType, d.paySign);//成功则调取支付界面
				}else{
					Alert(d);
				}
			}
		});
	}else{
		$('#ID_Submit').unbind("click");
		$.ajax({
			type : 'post',
			url : 'getConWehcatPay.do',
			data : {
				'consumeAmount': consumeAmount,
				'payAmount': payAmount,
				'conDiscountId': conDiscountId,
				'copartnerId': copartnerId,
				'emplId':emplValue
			},
			dataType:'json',
			async : true,
			success : function(ret) {
				var d = ret.data;
				if (ret.success) {
					onBridgeReady(d.appId, d.timeStamp, d.nonceStr, d.packages, d.signType, d.paySign);//成功则调取支付界面
				}else{
					Alert(d);
				}
			}
		});
	}
	
}

$('#ID_Submit').bind('click', function(){
	paySubmit();
});

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
			sessionStorage.setItem("price", '');
	        sessionStorage.setItem("percent", '');
	    	sessionStorage.setItem("conDiscountId", '');
	    	
	    	$.ajax({
				type : 'post',
				url : '../wxPayCharge/payFinish.do',
				data : {
				    'timeStamp': timeStamp, 
					'nonceStr': nonceStr
				},
				dataType:'json',
				async : true,
				success : function(ret) {
					if (ret.success) {
						
						window.location.replace("consumeOrder/paySuccess.do?orderId="+ret.data);

					}else{

					}
				}
			});
			
		} else if (res.err_msg == "get_brand_wcpay_request:fail") {
			// $('#ID_Submit').bind("click",paySubmit);
			$('#ID_Submit').bind("click", function() { paySubmit(); });
			Alert('您的支付失败');
		} else if (res.err_msg == "get_brand_wcpay_request:cancel") {

			$('#ID_Submit').bind("click", function() { paySubmit(); });
			
			sessionStorage.setItem("price", '');
	        sessionStorage.setItem("percent", '');
	    	sessionStorage.setItem("conDiscountId", '');
			Alert({
				msg : "您已取消支付",
				ok : function() {
					
					window.location.replace("consumeOrder/getOrderListByStatus.do?orderStatus=1");
					
				}
			});
		} else {
			
		}
	});
}

function getCoudiscount(copartnerId){
	window.location.href = "getCoudiscount.do?copartnerId="+copartnerId+"&chuanPrice="+chuanPrice;
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