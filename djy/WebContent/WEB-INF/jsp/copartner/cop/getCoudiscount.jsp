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
<title>选择优惠券</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
</head>
<body style="background-color:#fff;">
         <div class="listVoucher am-padding-top-sm">
      <ul class="am-list am-margin-0">
      
      <c:forEach var="i" items="${couponDiscounts}" varStatus="status">
      <li class="am-padding-xs am-padding-top-0" style="margin-top: -0.5rem;">
          <a href="javascript:getVou(${i.discountPercent/10},${i.id});">
            <div class="am-padding-xs item">
              <img src="../copartner/img/ayhqback.png" class="am-img-responsive" style="width:100%;" />
              <div class="c1" style="height:20vw;width:20vw;" align="center">
                <img src="../copartner/img/dzq1.png" class="voucher-img" /></div>
              <div class="c2" style="left: 30%">
                <div>${coPartner.name}</div></div>
              <div class="am-text-xs end">有效期至：
             <c:if test="${not empty i.useEndDate}">
             <fmt:formatDate pattern="yyyy.MM.dd" value="${i.useEndDate}" />
             </c:if>
             <c:if test="${empty i.useEndDate}">
                                            有效期：无限制
             </c:if>
             </div>
              <div class="status">
                <div class="voucher-percent">${i.discountPercent/10}折</div>
                <c:if test="${not empty i.useMinConsume}">
                <div class="voucher-totalprice">满${i.useMinConsume}可用</div>
                </c:if>
                <c:if test="${empty i.useMinConsume}">
                <div class="voucher-totalprice">&nbsp;</div>
                </c:if>
                <div class="voucher-get-div">
                  <span class="voucher-get-span">&nbsp;&nbsp;立即使用&nbsp;&nbsp;</span></div>
              </div>
            </div>
          </a>
        </li>
      </c:forEach>
      </ul>
    </div>

	<script type="text/javascript">
	    function getVou(percent,conDiscountId){
	    	sessionStorage.setItem("percent", percent);
	    	sessionStorage.setItem("conDiscountId", conDiscountId);
	    	self.location=document.referrer;
	    }
	</script>
  </body>

</html>