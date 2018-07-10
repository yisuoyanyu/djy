<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>

<li>
	<a class="J_menuItem" href="coPartnerShift/coPartnerShiftList.do" data-tab-title="交接班">
		<i class="fa fa-recycle "></i>
		<span class="nav-label">交接班</span>
	</a>
</li>

<li>
	<a class="J_menuItem" href="coPartnerEmpl/coPartnerEmplList.do" data-tab-title="商家员工">
		<i class="fa fa-male "></i>
		<span class="nav-label">商家员工</span>
	</a>
</li>

<li>
	<a href="#">
		<i class="fa fa-child "></i>
		<span class="nav-label">会员消费</span>
		<span class="fa arrow"></span>
	</a>
	<ul class="nav nav-second-level">
           
           <li>
                 	<a class="J_menuItem" href="consumeOrder/consumeOrderList.do?status=1" 
                 		data-tab-title="未付款会员消费单" data-tab-show-refresh="true">
                 		<i class="fa fa-credit-card"></i>
                 		<span class="nav-label">未付款</span>
                 	</a>
                 </li>
                 <li>
                 	<a class="J_menuItem" href="consumeOrder/consumeOrderList.do?status=2" 
                 		data-tab-title="付款中会员消费单" data-tab-show-refresh="true">
                 		<i class="fa fa-stack-overflow"></i>
                 		<span class="nav-label">付款中</span>
                 	</a>
                 </li>
                 <li>
                 	<a class="J_menuItem" href="consumeOrder/consumeOrderList.do?status=3" 
                 		data-tab-title="已成功会员消费单" data-tab-show-refresh="true">
                 		<i class="fa fa-check"></i>
                 		<span class="nav-label">已成功</span>
                 	</a>
                 </li>
                 <li>
                 	<a class="J_menuItem" href="consumeOrder/consumeOrderList.do?status=4" 
                 		data-tab-title="付失败会员消费单" data-tab-show-refresh="true">
                 		<i class="fa fa-close"></i>
                 		<span class="nav-label">付失败</span>
                 	</a>
                 </li>
                 <li>
                 	<a class="J_menuItem" href="consumeOrder/consumeOrderList.do?status=5" 
                 		data-tab-title="已取消会员消费单" data-tab-show-refresh="true">
                 		<i class="fa fa-trash-o"></i>
                 		<span class="nav-label">已取消</span>
                 	</a>
                 </li>
                 <li>
                 	<a class="J_menuItem" href="consumeOrder/consumeOrderList.do" 
                 		data-tab-title="全部会员消费单" data-tab-show-refresh="true">
                 		<i class="fa fa-bars"></i>
                 		<span class="nav-label">全&nbsp;&nbsp;&nbsp;部</span>
                 	</a>
                 </li>
	</ul>
</li>

<li>
	<a class="J_menuItem" href="coPartner/coPartnerRecord.do?id=${loginPartner.id }" data-tab-title="个人中心">
		<i class="fa fa-user"></i>
		<span class="nav-label">个人中心</span>
	</a>
</li>

	