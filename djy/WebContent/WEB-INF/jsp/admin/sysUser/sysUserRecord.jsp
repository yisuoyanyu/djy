<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>系统用户信息</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	
	<!-- jsTree -->
	<link href="plugins/jsTree/themes/default/style.min.css?v=3.3.3" rel="stylesheet">
	<script src="plugins/jsTree/jstree.min.js?v=3.3.3" type="text/javascript"></script>
	
	<script src="js/treeView.js" type="text/javascript"></script>
	
</head>
<body class="gray-bg">

	<div class="wrapper wrapper-content">
	
		<div class="ibox float-e-margins">
			
			<div class="ibox-title">
				<h5>
					<c:if test="${ empty sysUser.id }">新增系统用户</c:if>
					<c:if test="${ not empty sysUser.id }">系统用户信息</c:if>
				</h5>
				
				<div class="ibox-tools">
					<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
				</div>
			</div>
			
			<div class="ibox-content">
				
				<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<!-- 这个hidden的input需要输出到浏览器端 -->
					<input type="hidden" id="id" name="id" value="${ sysUser.id }" />
				
					<div class="container">
						
						<div <c:if test="${loginUser.sysRoleCode != 'admin' }">class="hidden"</c:if> >
							<div class="row">
								<div class="col-sm-12 form-group">
									<label class="control-label col-sm-2" for="sysRoleId">角色：</label>
									<div class="col-sm-10">
										<c:if test="${ empty sysUser.id && not empty AddSysUser 
													|| not empty sysUser.id && not empty EditSysUser }">
											<c:forEach var="i" items="${ sysRoles }" varStatus="s">
												<label class="checkbox-inline i-checks">
													<input name="sysRoleId" type="radio" value="${ i.id }" 
														<c:if test="${ i.id==sysUser.sysRole.id }">checked</c:if> 
														<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择角色"</c:if> 
													/>
													${ i.name }
												</label>
											</c:forEach>
										</c:if>	
										
										<c:if test="${ !(empty sysUser.id && not empty AddSysUser
													|| not empty sysUser.id && not empty EditSysUser) }">
											<p class="form-control-static">${ sysUser.sysRole.name }</p>
										</c:if>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="username">用户名：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="username" name="username" value="${ sysUser.username }" 
									data-rule-required="true" data-msg-required="请输入用户名"/>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="password">密码：</label>
								<div class="col-sm-8">
									<input type="password" class="form-control" id="password" name="password"  
										<c:if test="${ empty sysUser.id }">
										data-rule-required="true" data-msg-required="请输入密码"
										</c:if> 
									/>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="confirmPassword">确认密码：</label>
								<div class="col-sm-8">
									<input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
										<c:if test="${ empty sysUser.id }">
										data-rule-required="true" data-msg-required="请输入确认密码"
										</c:if> 
										data-rule-equalTo="#password" data-msg-equalTo="两次密码输入不一致" 
									/>
								</div>
							</div>
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="realName">真实姓名：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="realName" name="realName" value="${ sysUser.realName }" />
								</div>
							</div>
							
						</div>
							
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="mobile">手　机：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="mobile" name="mobile" maxlength="11" value="${ sysUser.mobile }" />
								</div>
							</div>
							
						</div>
						
					</div>
					
				</form>
					
				<div class="hr-line-dashed"></div>
				
				<c:if test="${not empty AddSysUser || not empty EditSysUser}">
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
				'sysUser/submitSysUser.json', 
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