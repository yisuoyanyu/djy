<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>用户短信消息</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	
</head>
<body class="gray-bg">

	<div class="wrapper wrapper-content">
	
		<div class="ibox float-e-margins">
			
			<div class="ibox-title">
				<h5>
					用户短信消息
				</h5>
				
				<div class="ibox-tools">
					<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
				</div>
			</div>
			
			<div class="ibox-content">
				
				<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<% /*这个hidden的input需要输出到浏览器端*/ %>
					<input id="id" name="id" value="${ userSmsMsg.id }" type="hidden" />
					
					<div class="container">
						
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="mobile">手机号</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ userSmsMsg.mobile }</p>
								</div>
							</div>
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="content">内容</label>
								<div class="col-sm-8">
									<p class="form-control-static">${ userSmsMsg.content }</p>
								</div>
							</div>
						</div>
						
						<div class="row">
		
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="insertTime">插入时间</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatDate value="${ userSmsMsg.insertTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
									</p>
								</div>
							</div>
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="sendTime">发送时间</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatDate value="${ userSmsMsg.sendTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
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