<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>提成统计信息详情</title>
	<%@include file="../decorators/formHead.jsp"%>
	
</head>
<body class="gray-bg">
	
	<!-- 表单内容 -->
	<div class="formBody">
		<div class="wrapper wrapper-content">
		
			<div class="ibox float-e-margins">
				
				<div class="ibox-title">
					<h5>
						提成统计信息详情
					</h5>
					
				</div>
				
				<div class="ibox-content">
					
					<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<% /*这个hidden的input需要输出到浏览器端*/ %>
					<input id="id" name="id" value="${ bonusStats.id }" type="hidden" />
					
					<div class="container">
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="coPartnerName">商家名称</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ bonusStats.coPartner.name }</p>
								</div>
							</div>
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="statsDate">统计日期</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatDate value="${ bonusStats.statsDate }" type="both" pattern="yyyy-MM-dd" />
									</p>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="totalCstmConsume">会员消费总额</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatNumber value="${ bonusStats.totalCstmConsume }" type="number" maxFractionDigits="2" /> 元
									</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="maxSysDeposit">平台最高预存限额</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatNumber value="${ bonusStats.maxSysDeposit }" type="number" maxFractionDigits="2" /> 元
									</p>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="indexPercent">指标比例</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatNumber value="${ bonusStats.indexPercent }" type="number" maxFractionDigits="2" /> (%)
									</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="bonusPercent">提成比例</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatNumber value="${ bonusStats.bonusPercent }" type="number" maxFractionDigits="2" /> (%)
									</p>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="bonus">消费金额</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatNumber value="${ bonusStats.bonus }" type="number" maxFractionDigits="2" /> 元
									</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="emplID">所属职员</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ bonusStats.sysEmpl.emplID }
									</p>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="insertTime">插入时间</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatDate value="${ bonusStats.insertTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
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