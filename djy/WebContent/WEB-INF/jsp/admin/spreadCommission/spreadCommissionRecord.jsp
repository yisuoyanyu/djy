<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>推广佣金信息</title>
	<%@include file="../decorators/formHead.jsp"%>
	
</head>
<body class="gray-bg">
	
	<!-- 表单内容 -->
	<div class="formBody">
		<div class="wrapper wrapper-content">
		
			<div class="ibox float-e-margins">
				
				<div class="ibox-title">
					<h5>
						推广佣金信息
					</h5>
					
				</div>
				
				<div class="ibox-content">
					
					<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<% /*这个hidden的input需要输出到浏览器端*/ %>
					<input id="id" name="id" value="${ spreadCommission.id }" type="hidden" />
					
					<div class="container">
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="no">订单编号</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ spreadCommission.no }</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="amount">金额</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatNumber value="${ spreadCommission.amount }" type="number" maxFractionDigits="2" /> 元
									</p>
								</div>
							</div>
							
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="realName">推广人</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ spreadCommission.spreadPromoter.realName }</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="mobile">推广人手机</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ spreadCommission.spreadPromoter.mobile }</p>
								</div>
							</div>
							
							
						</div>
						
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="consumeOrderNo">消费订单</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ spreadCommission.consumeOrder.no }
									</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="consumeOrderAmount">消费金额</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ spreadCommission.consumeOrder.consumeAmount }
									</p>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="statusName">订单状态</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ spreadCommission.statusName }
									</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="insertTime">插入时间</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatDate value="${ spreadCommission.insertTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
									</p>
								</div>
							</div>
							
						</div>
						
						
					</div>
					
				</form>
				</div>
				
			</div>
	
		</div>
	</div>
				
</body>
</html>