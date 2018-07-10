<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
	
	<c:if test="${ not empty ReadCoSysDepositLog || not empty ReadCoPartner || not empty ReadActCoupon || not empty ReadCoPartnerAd || not empty ReadCoSysDepositOrder || not empty ReadCoSettleOrder }">
		<li>
			<a href="#">
				<i class="fa fa-spoon"></i>
				<span class="nav-label">商家管理</span>
				<span class="fa arrow"></span>
			</a>
			
			<ul class="nav nav-second-level">
                 <c:if test="${not empty ReadCoPartner }">
                    <li>
		                <a href="#">
		                	<i class="fa fa-male"></i>
		                	<span class="nav-label">合作商家</span> 
		                	<span class="fa arrow"></span>
		                </a>
		                <ul class="nav nav-third-level">
		                    <li>
		                    	<a class="J_menuItem" href="coPartner/coPartnerList.do?status=0" 
		                    		data-tab-title="未填资料合作商家" data-tab-show-refresh="true">
		                    		<i class="fa fa-credit-card"></i>
		                    		<span class="nav-label">未填资料</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coPartner/coPartnerList.do?status=1" 
		                    		data-tab-title="待审核合作商家" data-tab-show-refresh="true">
		                    		<i class="fa fa-stack-overflow"></i>
		                    		<span class="nav-label">待审核</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coPartner/coPartnerList.do?status=2" 
		                    		data-tab-title="正常合作商家" data-tab-show-refresh="true">
		                    		<i class="fa fa-check"></i>
		                    		<span class="nav-label">正常</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coPartner/coPartnerList.do?status=3" 
		                    		data-tab-title="禁用合作商家" data-tab-show-refresh="true">
		                    		<i class="fa fa-close"></i>
		                    		<span class="nav-label">禁用</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coPartner/coPartnerList.do" 
		                    		data-tab-title="全部合作商家" data-tab-show-refresh="true">
		                    		<i class="fa fa-bars"></i>
		                    		<span class="nav-label">全&nbsp;&nbsp;&nbsp;部</span>
		                    	</a>
		                    </li>
		                </ul>
	            	</li>
                    
                 </c:if>
                  
                 <c:if test="${not empty ReadActCoupon }">
                    <li>
                    	<a class="J_menuItem" href="actCoupon/actCouponList.do"  
                    		data-tab-title="送券活动" data-tab-show-refresh="true">
                    		<i class="fa fa-list-ul"></i>
							<span class="nav-label">送券活动</span>
                    	</a>
                    </li>
                 </c:if>
                   
                 <c:if test="${not empty ReadCoPartnerAd }">
                    <li>
                    	<a class="J_menuItem" href="coPartnerAd/coPartnerAdList.do"  
                    		data-tab-title="广告活动" data-tab-show-refresh="true">
                    		<i class="fa fa-ticket"></i>
							<span class="nav-label">广告活动</span>
                    	</a>
                    </li>
                 </c:if>
                 
                 <c:if test="${not empty ReadCoSysDepositOrder }">
					<li>
		                <a href="#">
		                	<i class="fa fa-exchange"></i>
		                	<span class="nav-label">平台预存</span> <span class="fa arrow"></span>
		                </a>
		                <ul class="nav nav-third-level">
		                    <li>
		                    	<a class="J_menuItem" href="coSysDepositOrder/coSysDepositOrderList.do?status=1" 
		                    		data-tab-title="未付款平台预存单" data-tab-show-refresh="true">
		                    		<i class="fa fa-credit-card"></i>
		                    		<span class="nav-label">未付款</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coSysDepositOrder/coSysDepositOrderList.do?status=2" 
		                    		data-tab-title="付款中平台预存单" data-tab-show-refresh="true">
		                    		<i class="fa fa-stack-overflow"></i>
		                    		<span class="nav-label">付款中</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coSysDepositOrder/coSysDepositOrderList.do?status=3" 
		                    		data-tab-title="已成功平台预存单" data-tab-show-refresh="true">
		                    		<i class="fa fa-check"></i>
		                    		<span class="nav-label">已成功</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coSysDepositOrder/coSysDepositOrderList.do?status=4" 
		                    		data-tab-title="付失败平台预存单" data-tab-show-refresh="true">
		                    		<i class="fa fa-close"></i>
		                    		<span class="nav-label">付失败</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coSysDepositOrder/coSysDepositOrderList.do?status=5" 
		                    		data-tab-title="已取消平台预存单" data-tab-show-refresh="true">
		                    		<i class="fa fa-trash-o"></i>
		                    		<span class="nav-label">已取消</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coSysDepositOrder/coSysDepositOrderList.do" 
		                    		data-tab-title="全部平台预存单" data-tab-show-refresh="true">
		                    		<i class="fa fa-bars"></i>
		                    		<span class="nav-label">全&nbsp;&nbsp;&nbsp;部</span>
		                    	</a>
		                    </li>
		                </ul>
	            	</li>
            	</c:if>
            	
            	<c:if test="${not empty ReadCoSettleOrder }">
	            	<li>
		                <a href="#">
		                	<i class="fa fa-pencil-square-o"></i>
		                	<span class="nav-label">商家结算</span> <span class="fa arrow"></span>
		                </a>
		                <ul class="nav nav-third-level">
		                    <li>
		                    	<a class="J_menuItem" href="coSettleOrder/coSettleOrderList.do?status=1" 
		                    		data-tab-title="未付款商家结算单" data-tab-show-refresh="true">
		                    		<i class="fa fa-credit-card"></i>
		                    		<span class="nav-label">未付款</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coSettleOrder/coSettleOrderList.do?status=2" 
		                    		data-tab-title="付款中商家结算单" data-tab-show-refresh="true">
		                    		<i class="fa fa-stack-overflow"></i>
		                    		<span class="nav-label">付款中</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coSettleOrder/coSettleOrderList.do?status=3" 
		                    		data-tab-title="已成功商家结算单" data-tab-show-refresh="true">
		                    		<i class="fa fa-check"></i>
		                    		<span class="nav-label">已成功</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coSettleOrder/coSettleOrderList.do?status=4" 
		                    		data-tab-title="付失败商家结算单" data-tab-show-refresh="true">
		                    		<i class="fa fa-close"></i>
		                    		<span class="nav-label">付失败</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coSettleOrder/coSettleOrderList.do?status=5" 
		                    		data-tab-title="已取消商家结算单" data-tab-show-refresh="true">
		                    		<i class="fa fa-trash-o"></i>
		                    		<span class="nav-label">已取消</span>
		                    	</a>
		                    </li>
		                    <li>
		                    	<a class="J_menuItem" href="coSettleOrder/coSettleOrderList.do" 
		                    		data-tab-title="合作商家结算单" data-tab-show-refresh="true">
		                    		<i class="fa fa-bars"></i>
		                    		<span class="nav-label">全&nbsp;&nbsp;&nbsp;部</span>
		                    	</a>
		                    </li>
		                </ul>
		            </li>
	            </c:if>
                 
                 <c:if test="${not empty ReadCoSysDepositLog }">
                    <li>
                    	<a class="J_menuItem" href="coSysDepositLog/coSysDepositLogList.do"  
                    		data-tab-title="预存款明细" data-tab-show-refresh="true">
                    		<i class="fa fa-list-alt"></i>
							<span class="nav-label">预存款明细</span>
                    	</a>
                    </li>
                 </c:if>
			</ul>
			
		</li>
	</c:if>
	
	<c:if test="${not empty ReadConsumeOrder }">
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
	</c:if>
	
	<c:if test="${not empty ReadUser }">
	
		<li>
			<a href="#">
				<i class="fa fa-users "></i>
				<span class="nav-label">会员管理</span>
				<span class="fa arrow"></span>
			</a>
			<ul class="nav nav-second-level">
				<li>
					<a class="J_menuItem" href="user/userList.do" data-tab-title="全部会员" data-tab-show-refresh="true">
						<i class="fa fa-user"></i>
						<span class="nav-label">全部会员</span>
					</a>
				</li>
				<li>
					<a class="J_menuItem" href="consumeOrder/firstConsumeUserList.do" data-tab-title="首次消费会员" data-tab-show-refresh="true">
						<i class="fa fa-user-secret"></i>
						<span class="nav-label">首次消费会员</span>
					</a>
				</li>
			</ul>
			
		</li>
		
		
	</c:if>
	<c:if test="${not empty ReadCmsCatg || not empty ReadCmsArticle }">
		<li>
			<a href="#">
				<i class="fa fa-newspaper-o"></i>
				<span class="nav-label">内容管理</span>
				<span class="fa arrow"></span>
			</a>
			<ul class="nav nav-second-level">
				<c:if test="${not empty ReadCmsCatg }">
					<li>
						<a class="J_menuItem" href="cmsCatg/cmsCatgTree.do" data-tab-show-refresh="true">
							<i class="fa fa-random"></i>
							<span class="nav-label">文章分类</span>
						</a>
					</li>
				</c:if>
				<c:if test="${ not empty ReadCmsArticle }">
					<li>
						<a class="J_menuItem" href="cmsArticle/cmsArticleList.do" data-tab-show-refresh="true">
							<i class="fa  fa-file-text-o"></i>
							<span class="nav-label">文章列表</span>
						</a>
					</li>
				</c:if>
			</ul>
		</li>
	</c:if>
	
	<c:if test="${not empty ReadSysUser || not empty ReadSysPrtTmpl }">
		<li>
			<a href="#">
				<i class="fa fa-gear"></i>
				<span class="nav-label">系统管理</span>
				<span class="fa arrow"></span>
			</a>
			<ul class="nav nav-second-level">
				<c:if test="${ not empty ReadSysUser}">
					<li>
						<a class="J_menuItem" href="sysUser/sysUserList.do" data-tab-title="工作人员">
							<i class="fa fa-users"></i>
							<span class="nav-label">工作人员</span>
						</a>
					</li>
				</c:if>
				<li>
					<a class="J_menuItem" href="sysEmpl/sysEmplList.do" data-tab-title="市场职员">
						<i class="fa fa-child"></i>
						<span class="nav-label">市场职员</span>
					</a>
				</li>
				<li>
					<a class="J_menuItem" href="spreadPromoter/spreadPromoterList.do" data-tab-title="推广人">
						<i class="fa fa-user"></i>
						<span class="nav-label">推广人</span>
					</a>
				</li>
				<c:if test="${ not empty ReadSysPrtTmpl}">
					<li>
						<a class="J_menuItem" href="sysPrtTmpl/sysPrtTmplList.do" data-tab-title="套打模板">
							<i class="fa fa-file-word-o"></i>
							套打模板
						</a>
					</li>
				</c:if>
			</ul>
		</li>
	</c:if>
	
	<li>
		<a href="#">
			<i class="fa fa-jpy "></i>
			<span class="nav-label">推广佣金</span>
			<span class="fa arrow"></span>
		</a>
		<ul class="nav nav-second-level">
	           
	           <li>
                	<a class="J_menuItem" href="spreadCommission/spreadCommissionList.do?status=1" 
                		data-tab-title="未付款推广佣金" data-tab-show-refresh="true">
                		<i class="fa fa-credit-card"></i>
                		<span class="nav-label">未付款</span>
                	</a>
                </li>
                <li>
                	<a class="J_menuItem" href="spreadCommission/spreadCommissionList.do?status=2" 
                		data-tab-title="付款中推广佣金" data-tab-show-refresh="true">
                		<i class="fa fa-stack-overflow"></i>
                		<span class="nav-label">付款中</span>
                	</a>
                </li>
                <li>
                	<a class="J_menuItem" href="spreadCommission/spreadCommissionList.do?status=3" 
                		data-tab-title="已成功推广佣金" data-tab-show-refresh="true">
                		<i class="fa fa-check"></i>
                		<span class="nav-label">已成功</span>
                	</a>
                </li>
                <li>
                	<a class="J_menuItem" href="spreadCommission/spreadCommissionList.do?status=4" 
                		data-tab-title="付失败推广佣金" data-tab-show-refresh="true">
                		<i class="fa fa-close"></i>
                		<span class="nav-label">付失败</span>
                	</a>
                </li>
                <li>
                	<a class="J_menuItem" href="spreadCommission/spreadCommissionList.do?status=5" 
                		data-tab-title="已取消推广佣金" data-tab-show-refresh="true">
                		<i class="fa fa-trash-o"></i>
                		<span class="nav-label">已取消</span>
                	</a>
                </li>
                <li>
                	<a class="J_menuItem" href="spreadCommission/spreadCommissionList.do" 
                		data-tab-title="全部推广佣金" data-tab-show-refresh="true">
                		<i class="fa fa-bars"></i>
                		<span class="nav-label">全&nbsp;&nbsp;&nbsp;部</span>
                	</a>
                </li>
		</ul>
	</li>
	
	<li>
		<a class="J_menuItem" href="bonusStats/bonusDayStatsList.do" data-tab-title="提成统计">
			<i class="fa fa-bar-chart "></i>
			<span class="nav-label">提成统计</span>
		</a>
	</li>
	
	
	<c:if test="${ not empty ReadSysUser }">
		<li>
			<a class="J_menuItem" href="sysUser/sysUserRecord.do?id=${loginUser.id }" data-tab-title="个人中心">
				<i class="fa fa-user"></i>
				<span class="nav-label">个人中心</span>
			</a>
		</li>
	</c:if>
	
	