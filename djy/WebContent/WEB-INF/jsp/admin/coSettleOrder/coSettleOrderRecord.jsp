<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>商家结算订单信息</title>
	<%@include file="../decorators/formHead.jsp"%>
	
</head>
<body class="gray-bg">
	
	<!-- 表单内容 -->
	<div class="formBody">
		<div class="wrapper wrapper-content">
		
			<div class="ibox float-e-margins">
				
				<div class="ibox-title">
					<h5>
						商家结算订单信息
					</h5>
					
				</div>
				
				<div class="ibox-content">
					
					<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<% /*这个hidden的input需要输出到浏览器端*/ %>
					<input id="id" name="id" value="${ coSettleOrder.id }" type="hidden" />
					
					<div class="container">
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="no">订单编号</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ coSettleOrder.no }</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="statusName">订单状态</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ coSettleOrder.statusName }
									</p>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="coPartnerName">合作商家</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ coSettleOrder.coPartner.name }</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="amount">预存金额</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatNumber value="${ coSettleOrder.amount  }" type="number" maxFractionDigits="2" /> 元
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