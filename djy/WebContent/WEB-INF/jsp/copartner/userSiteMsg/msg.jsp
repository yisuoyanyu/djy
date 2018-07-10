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
<title>消息中心</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<link rel="stylesheet" href="../copartner/css/shangpinxiangqing.css">
</head>
 <body style="background-color:#fff;">
		
		<header data-am-widget="header" class="am-header am-header-fixed am-header-default">
			<div class="am-header-left am-header-nav">
				<a href="javascript:history.back();">
					<i class="am-header-icon am-icon-angle-left am-icon-sm"></i>
				</a>
			</div>
			<h1 class="am-header-title">
				<a href="javascript:void(0);">消息中心</a>
			</h1>
		</header>
		

			<div class="goodsInfo">
			<ul class="am-list">
			
			
				<li class="am-g item">
				<a style="padding: 0rem 0;" href="userSiteMsg/msgList.do?type=1">
				<div>
				<div class="am-u-sm-3 lc" style="text-align:right;right: 2vw;">
				
						<img style="max-width: 88%;" src="../copartner/img/icon_massage.png">
				</div>	
				<div>
				<strong class="massagestrong">${msgSysNum}</strong>
				</div>
				</div>	
					<div class="am-u-sm-9 rc msg">
						<div class="massagetitle">系统通知
						<c:if test="${msgSysNum>0}">
						<span class="massagetime"><fmt:formatDate value="${sysSiteMsg.insertTime}" pattern="MM月dd日"/></span>
						</c:if>
						</div>
						
						<div class="am-list-item-text">北京油惠站推出强有力的优惠活动，您可点击...</div>
					</div>
				</a>
				</li>
				
				
				
				<li class="am-g item">
				<a style="padding: 0rem 0;" href="userSiteMsg/msgList.do?type=2">
				<div>
				<div class="am-u-sm-3 lc" style="text-align:right;right: 2vw;">
				
						<img style="max-width: 88%;" src="../copartner/img/icon_deal.png">
				</div>	
				<div>
				<strong class="massagestrong">${msgOrderNum}</strong>
				</div>
				</div>	
					<div class="am-u-sm-9 rc msg">
						<div class="massagetitle">订单消息
						<c:if test="${msgOrderNum>0}">
						<span class="massagetime"><fmt:formatDate value="${OrderMsg.insertTime}" pattern="MM月dd日"/></span>
						</c:if>
						</div>
						
						<div class="am-list-item-text">
                         <c:if test="${msgOrderNum>0}">
                         ${fn:substring(OrderMsg.content, 0, 20)}
                         </c:if>
                         <c:if test="${msgOrderNum==0}">
                                                                                   您所有的订单消息都在此处，
                         </c:if>
                                                                        点击查看详细情况....</div>
					</div>
				</a>
				</li>
				
				
                <li class="am-g item">
				<a style="padding: 0rem 0;" href="userSiteMsg/msgList.do?type=3">
				<div>
				<div class="am-u-sm-3 lc" style="text-align:right;right: 2vw;">
				
						<img style="max-width: 88%;" src="../copartner/img/ico_yhq.png">
				</div>	
				<div>
				<strong class="massagestrong">${msgVoudiscountNum}</strong>
				</div>
				</div>	
					<div class="am-u-sm-9 rc msg">
						<div class="massagetitle">优惠券
						<c:if test="${msgVoudiscountNum>0}">
						<span class="massagetime"><fmt:formatDate value="${VouMsg.insertTime}" pattern="MM月dd日"/></span>
						</c:if>
						</div>
						
						<div class="am-list-item-text">
                         <c:if test="${msgVoudiscountNum>0}">
                         ${fn:substring(VouMsg.content, 0, 20)}
                         </c:if>
                         <c:if test="${msgVoudiscountNum==0}">
                                                                                   您所有的优惠券消息都在此处，
                         </c:if>
                                                                       点击查看详细情况...</div>
					</div>
				</a>
				</li>

			</ul>
		</div>
		
	</body>
</html>