<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>打折券信息</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	
</head>
<body class="gray-bg">

	<div class="wrapper wrapper-content">
	
		<div class="ibox float-e-margins">
			
			<div class="ibox-title">
				<h5>
					打折券信息
				</h5>
				
				<div class="ibox-tools">
					<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
				</div>
			</div>
			
			<div class="ibox-content">
				
				<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<% /*这个hidden的input需要输出到浏览器端*/ %>
					<input id="id" name="id" value="${ couponDiscount.id }" type="hidden" />
					
					<div class="container">
						
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="username">用户名称</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ couponDiscount.user.wechatUser.nickname }</p>
								</div>
							</div>
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="copartnerName">商家名称</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ couponDiscount.coPartner.name }</p>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="title">标题</label>
								<div class="col-sm-8">
									<p class="form-control-static">${couponDiscount.title }</p>
								</div>
							</div>
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="no">券号</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ couponDiscount.no }</p>
								</div>
							</div>
						</div>
							
						<div class="row">
						
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="discountPercent">折扣百分比（%）</label>
								<div class="col-sm-8">
									<p class="form-control-static">${couponDiscount.discountPercent }</p>
								</div>
							</div>
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="useMinConsume">最低消费金额（元）</label>
								<div class="col-sm-8">
									<p class="form-control-static">${couponDiscount.useMinConsume }</p>
								</div>
							</div>
							
						</div>
						
						<div class="row">
		
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="useStartDate">可使用开始日期</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatDate value="${ couponDiscount.useStartDate }" type="both" pattern="yyyy-MM-dd" />
									</p>
								</div>
							</div>
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="useEndDate">可使用结束日期</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatDate value="${ couponDiscount.useEndDate }" type="both" pattern="yyyy-MM-dd" />
									</p>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="useEndDate">使用时间</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatDate value="${ couponDiscount.usedTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
									</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="statusName">状态</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ couponDiscount.statusName }
									</p>
								</div>
							</div>
							
						</div>
						
					</div>
					
				</form>
				
				
				<div class="hr-line-dashed"></div>
				
			</div>
			
		</div>

	</div>

				
<script type="text/javascript">
	
	$(function() {
		$('#btn_cancel').click(function(){
			window.location.reload();
		});
		
	});
</script>

</body>
</html>