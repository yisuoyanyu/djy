<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>内容分类信息</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	
</head>

<body class="gray-bg">

	<div class="wrapper wrapper-content">
	
		<div class="ibox float-e-margins">
			
			<div class="ibox-title">
				<h5>
					<c:if test="${ empty cmsCatg.id }">新增内容分类</c:if>
					<c:if test="${ not empty cmsCatg.id }">编辑内容分类</c:if>
				</h5>
				
				<div class="ibox-tools">
					<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
				</div>
			</div>
			
			<div class="ibox-content">
				
				<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<!-- 这个hidden的input需要输出到浏览器端 -->
					<input type="hidden" id="id" name="id" value="${cmsCatg.id }" />
					
						<div class="container">
							
							<div class="row">
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="parentId">上级目录：</label>
									<div class="col-sm-8">
										<p class="form-control-static">
											<c:if test="${ empty cmsCatg.parentCmsCatg }">
												无
											</c:if>
											<c:if test="${ not empty cmsCatg.parentCmsCatg }">
												${ cmsCatg.parentCmsCatg.name }
												<input type="hidden" id="parentId" name="parentId" value="${ cmsCatg.parentCmsCatg.id }" />
											</c:if>
										</p>
										
									</div>
								</div>
								
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="status">状态：</label>
									<div class="col-sm-8">
									
										<c:if test="${ empty cmsCatg.id && not empty AddCmsCatg 
												|| not empty cmsCatg.id && not empty EditCmsCatg }">
											<c:forEach var="i" items="${ cmsCatgStatus }" varStatus="s">
												<label class="checkbox-inline i-checks">
													<input name="status" type="radio" value="${ i.id }" 
														<c:if test="${ i.id==cmsCatg.status || empty cmsCatg.status && s.first }">checked</c:if> 
														<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择状态"</c:if> 
													/>
													${ i.value }
												</label>
											</c:forEach>
										</c:if>
										
										<c:if test="${ !(empty cmsCatg.id && not empty AddCmsCatg 
													|| not empty cmsCatg.id && not empty EditCmsCatg) }">
											<p class="form-control-static">${statusName }</p>
										</c:if>
									
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="name">名称：</label>
									<div class="col-sm-8">
									
										<c:if test="${ empty cmsCatg.id && not empty AddCmsCatg 
												|| not empty cmsCatg.id && not empty EditCmsCatg }">
											<input id="name" name="name" type="text" value="${cmsCatg.name }" class="form-control" 
												data-rule-required="true" data-msg-required="请输入名称" />
										</c:if>
										
										<c:if test="${ !(empty cmsCatg.id && not empty AddCmsCatg 
													|| not empty cmsCatg.id && not empty EditCmsCatg) }">
											<p class="form-control-static">${cmsCatg.name }</p>
										</c:if>
										
									</div>
								</div>
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="sortNumber">排序号：</label>
									<div class="col-sm-8">
										<c:if test="${ empty cmsCatg.id && not empty AddCmsCatg 
												|| not empty cmsCatg.id && not empty EditCmsCatg }">
											<input id="sortNumber" name="sortNumber" type="text" value="${cmsCatg.sortNumber }" class="form-control" 
												data-rule-required="true" data-msg-required="请输入排序号" />
											<input type="hidden" id="oldSortNumber" name="oldSortNumber" value="${ cmsCatg.sortNumber }" />
										</c:if>
										
										<c:if test="${ !(empty cmsCatg.id && not empty AddCmsCatg 
													|| not empty cmsCatg.id && not empty EditCmsCatg) }">
											<p class="form-control-static">${cmsCatg.sortNumber }</p>
										</c:if>
										
									</div>
								</div>
							</div>
							
						</div>
				</form>
					
				<div class="hr-line-dashed"></div>
				<c:if test="${not empty AddCmsCatg  || not empty EditCmsCatg}">
					<div class="form-group text-center">
						
						<button id="btn_submit" class="btn btn-primary">提交</button>
						
						<button id="btn_cancel" class="btn btn-white">取消</button>
						
					</div>
				</c:if>
			</div>
			
		</div>

	</div>

				
<script type="text/javascript">

	$(function() {
		
		// 格式化单选框样式
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green'
        });
		
		function submitForm() {
			
			$.post(
				'cmsCatg/submitCmsCatg.json', 
				$('#form').serializeArray(), 
				function(ret) {
					if (ret.success) {
						Alert({
							msg : '提交成功',
							ok : function() {
								closeForm({ refreshOpener : true });
							}
						});
					} else {
						Alert(ret.msg);
					}
				}
			);
			
		}
		
		$('#btn_submit').click(function(){
            // 输入校验
            if ( !$('#form').valid() )
                return false;
            submitForm();
        });
		
		 
		$('#btn_cancel').click(function(){
			window.location.reload();
		});
		
	});
</script>

</body>
</html>