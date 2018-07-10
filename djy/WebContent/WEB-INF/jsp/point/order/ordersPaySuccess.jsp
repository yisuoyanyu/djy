<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>已完成订单</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
	</head>
	<body class="bg_main">
    <!-- 页签 -->
    <div class="bar h3 tabs_bar posi_fixed border_b" style="height: 5rem;background-color: #476df6;">
	        <ul class="am-avg-sm-3" style="border-bottom: 0px solid #DDDDDD; height: 5rem;position: fixed;z-index: 999;">
        <li style="height: 5rem;">
          <a href="javascript:location.replace('order/ordersToPay.do');" style="height: 5rem; line-height: 5rem;">未付款</a></li>
        <li  class="on" style="height: 5rem;">
          <a href="javascript:void(0);" style="height: 5rem; line-height: 5rem;">付款成功</a></li>
        <li style="height: 5rem;">
          <a href="javascript:location.replace('order/ordersCancel.do');" style="height: 5rem; line-height: 5rem;">已取消</a></li>
      </ul>
    </div>
    <section data-am-widget="accordion" class="am-accordion am-accordion-gapped" data-am-accordion='{  }'>
     
     <c:forEach var="i" items="${orderVos}" varStatus="status">
      
       <c:if test="${status.index==0}">
       <dl class="am-accordion-item am-active" style="background-color:white;border-radius:10px; -webkit-box-shadow: 3px 3px 3px;-moz-box-shadow: 3px 3px 3px;box-shadow: 3px 3px 1px #cddbf8;  ">
       </c:if>
       <c:if test="${status.index!=0}">
       <dl class="am-accordion-item" style="background-color:white;border-radius:10px; -webkit-box-shadow: 3px 3px 3px;-moz-box-shadow: 3px 3px 3px;box-shadow: 3px 3px 1px #cddbf8;  ">
       </c:if>
       
         <dt class="am-accordion-title" style="border-top-left-radius:10px;border-top-right-radius:10px;padding:0rem 0rem 0rem 0rem">
          <div class="am-g" style="font-size:4vw;">
            <div class="am-u-sm-2" style="padding-right:0rem;">
              <i class="iconfont icon-jifen" style="font-size:9vw;display: inline-block; background-size: contain;background-repeat: no-repeat;width: 7vw;height: 7vw;"></i>
            </div>
            <div class="am-u-sm-10" style="padding-left:0rem;padding-top:3vw">
              <span class="bold_font">编号:${i.no}</span></div>
            <div class="am-u-sm-12" style="padding-left: 16%;margin-top:-3%">
              <font size="3vw"><fmt:formatDate value="${i.insertTime}"  pattern="YYYY年MM月dd日 HH:mm:ss"/></font></div>
          </div>
        </dt>
        
          <c:if test="${status.index==0}">
       <dd class="am-accordion-bd am-collapse am-in">
       </c:if>
       <c:if test="${status.index!=0}">
       <dd class="am-accordion-bd am-collapse">
       </c:if>        

          <!-- 规避 Collapase 处理有 padding 的折叠内容计算计算有误问题， 加一个容器 -->
          <div class="am-accordion-content" style="overflow:hidden">
            <div style="width:100%">
              <div class="half_left">
                <div class="order_mian_font">
                  <span>购买积分</span></div>
                <div class="order_mian_font">
                  <span>${i.point}</span></div>
              </div>
              <div style="width:50%;float:right">
                <div class="order_mian_font">
                  <span>支付金额(元)</span></div>
                <div class="order_mian_font">
                  <span>${i.price}</span></div>
              </div>
            </div>
          </div>
        </dd>
      </dl>
      </c:forEach>

	  
    </section>
  </body>

</html>