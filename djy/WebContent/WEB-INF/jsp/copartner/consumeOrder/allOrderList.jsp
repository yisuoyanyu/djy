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
<title>全部订单</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
</head>
<body>
    <!-- 顶部图片 -->
    <div class="am-slider am-slider-a1" data-am-flexslider="{playAfterPaused: 8000}" style="-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0);">
      <ul class="am-slides">
        <li>
          <img src="../copartner/img/dingdanbannaer.jpg"></li>
      </ul>
    </div>
    <img onclick="javascript:window.location.href='user/mySpread.do'" src="../copartner/img/personal-youhuiquan-fx.png" style="width:32%;position:absolute;top:21vw;right:25vw;">
    <!-- 页签 -->
    <div class="bar h3 tabs_bar posi_fixed border_b">
      <ul class="am-avg-sm-4" style="position:relative;">
        <li class="on">
          <a href="javascript:void(0);">全部</a></li>
        <li>
          <a href="javascript:location.replace('consumeOrder/getOrderListByStatus.do?status=3');">已完成</a></li>
        <li>
          <a href="javascript:location.replace('consumeOrder/getOrderListByStatus.do?status=1');">待付款</a></li>
        <li>
          <a href="javascript:location.replace('consumeOrder/getOrderListByStatus.do?status=5');">已取消</a></li>
      </ul>
    </div>
    <!-- 信息列表 -->
    <div class="listInfo bg_white">
      <ul class="am-list am-margin-0">
      
      <c:forEach var="i" items="${consumeOrders}" varStatus="status">
      <li class="am-padding-xs" style="border-top: 1px solid #dedede;">
          <h2 class="hstore-name">
            <em class="emstore"></em>
            <span class="span-store-name">${i.coPartner.name}</span>
            <strong class="strong-state">
            <c:if test="${i.status == 1}">
           	 待付款
            </c:if>
            <c:if test="${i.status == 3}">
           	 已完成
            </c:if>
            <c:if test="${i.status == 5}">
           	 已取消
            </c:if>
            <c:if test="${i.status == 2}">
                                      付款中
            </c:if>
            </strong></h2>
            <c:if test="${i.status == 1}">
            <a href="javascript:goonPay(${i.id});">
            </c:if>
            <c:if test="${i.status != 1}">
            <a href="javascript:copartnerDetail(${i.coPartner.id})">
            </c:if>
            <div class="item">
              <div class="am-g">
                <div class="am-u-sm-4 am-padding-xs" style="padding-top:3vw;">
                  <img src="${i.coPartner.logoSrc}" class="am-img-responsive" style="height: 90px;" /></div>
                <div class="am-u-sm-8 am-padding-xs" style="padding-top:1rem;">
                  <div class="title" style="font-size:4.5vw;font-weight:bolder;">
                    <span class="am-text-sm" style="font-size:4vw;">
                      <span class="activity-zhe">折</span>&nbsp;&nbsp;<fmt:formatNumber value="${(i.payAmount/i.consumeAmount)*10}" pattern="0.0" type="number" />折优惠</span>
                    <span class="activity-distance" style="color:black;">-￥<fmt:formatNumber value="${i.consumeAmount-i.payAmount}" pattern="0.00" type="number" /></span></div>
                  <div class="totaldiv">
                    <span class="am-text-sm" style="font-size:4vw;">
                      <span>总价:￥${i.consumeAmount}</span></span>
                    <span class="activity-distance" style="font-weight:bolder;">实付:
                      <span style="color:red;">￥${i.payAmount}</span></span>
                  </div>
                  <div class="totaldiv" style="font-size: 3vw;">下单时间：<fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${i.insertTime}" /></div></div>
              </div>
            </div>
          </a>
        </li>
        <c:if test="${i.status == 1}"><!-- 表示未付款的状态 -->
        <div class="anniu">
          <a class="four-normal" href="javascript:cancelOrder(${i.id});">取消订单</a>
          <a class="del-anniu" href="javascript:goonPay(${i.id});">付款</a></div>
        </c:if>
        <c:if test="${i.status == 3}"><!-- 表示已完成的状态 -->
              <div class="anniu">
          <a class="two-normal" href="javascript:delOrder(${i.id});">删除</a></div>
        </c:if>
        <div class="blank1" style="background-color:#efefef;"></div>
      </c:forEach>
      
      </ul>
    </div>
    <!-- “取消订单”弹出确认框 -->
    <div id="ID_Confirm" class="am-modal am-modal-confirm confirm" tabindex="-1">
      <div class="am-modal-dialog">
        <div class="am-text-left am-padding-horizontal-sm am-padding-vertical">取消订单后，此订单将移到已取消。是否继续？</div>
        <div>
          <ul class="am-avg-sm-2">
            <li>
              <span data-am-modal-cancel class="am-modal-btn cancel">否</span></li>
            <li>
              <span data-am-modal-confirm class="am-modal-btn ok">是</span></li>
          </ul>
        </div>
      </div>
    </div>
    
    <!-- “删除订单”弹出确认框 -->
    <div id="ID_DelConfirm" class="am-modal am-modal-confirm confirm" tabindex="-1">
      <div class="am-modal-dialog">
        <div class="am-text-left am-padding-horizontal-sm am-padding-vertical">删除订单后，此订单将消失。是否继续？</div>
        <div>
          <ul class="am-avg-sm-2">
            <li>
              <span data-am-modal-cancel class="am-modal-btn cancel">否</span></li>
            <li>
              <span data-am-modal-confirm class="am-modal-btn ok">是</span></li>
          </ul>
        </div>
      </div>
    </div>
    <script type="text/javascript">
    function cancelOrder(orderid){
    	 $('#ID_Confirm').modal({
             relatedTarget: this,
             onConfirm: function(options) {
               $.ajax({
					type : 'post',
					url : 'consumeOrder/cancelCouOrder.do',
					data : {
						'orderid': orderid
					},
					dataType:'json',
					async : true,
					success : function(data) {
						if (data.success) {
							 location.reload();
						}
					}
				});
               
             },
             onCancel: function() {
              
             }
           });
        }
    
    
    function delOrder(orderid){
   	 $('#ID_DelConfirm').modal({
            relatedTarget: this,
            onConfirm: function(options) {
              $.ajax({
					type : 'post',
					url : 'consumeOrder/delCouOrder.do',
					data : {
						'orderid': orderid
					},
					dataType:'json',
					async : true,
					success : function(data) {
						if (data.success) {
							 location.reload();
						}
					}
				});
              
            },
            onCancel: function() {
             
            }
          });
       }
    
    function goonPay(orderId){
    	window.location.replace("goonPay.do?orderId="+orderId);
    }
    
    function copartnerDetail(copartnerId){
		　var currentLons;
		   var currentLats;
		   currentLats = sessionStorage.getItem("currentLat");
		   currentLons = sessionStorage.getItem("currentLon");
		   
		window.location.href = 'copartnerDetail.do?id='+copartnerId+'&lat='+currentLats+'&lon='+currentLons;
		
	}
    </script>
    <!-- “取消订单”弹出确认框 end -->
   </body>
</html>