<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>个人中心</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	
</head>

<body class="gray-bg">

	<div class="wrapper wrapper-content">
	
		<div class="ibox float-e-margins">
			
			<div class="ibox-title">
				<h5>
					个人中心
				</h5>
				
				<div class="ibox-tools">
					<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
				</div>
			</div>
			
			<div class="ibox-content">
				
				<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<input id="id" name="id" type="hidden" value="${ sysEmpl.id }" />
					<div class="container">
					
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="status">状态</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ sysEmpl.statusName }
									</p>
								</div>
							</div>
							
							
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="userMobile">关联会员手机</label>
								<div class="col-sm-8">
									<input id="userId" name="userId" value="${ sysEmpl.user.id }" type="hidden" class="form-control"/>
									<p class="form-control-static">
											${ sysEmpl.user.mobile }
									</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="emplID">工号</label>
								<div class="col-sm-8">
									<p class="form-control-static">
											${ sysEmpl.emplID }
									</p>
								</div>
							</div>
						
						</div>
					
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="initPassword">密码</label>
								<div class="col-sm-8">
									<input id="initPassword" name="initPassword" type="password" class="form-control" 
										<c:if test="${ empty sysEmpl.id }">
										data-rule-required="true" data-msg-required="请输入密码"
										</c:if> 
									/>
								</div>
							</div>
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="confInitPassword">确认密码</label>
								<div class="col-sm-8">
									<input id="confInitPassword" name="confInitPassword" type="password" class="form-control" 
										<c:if test="${ empty sysEmpl.id }">
										data-rule-required="true" data-msg-required="请输入确认密码"
										</c:if> 
										data-rule-equalTo="#initPassword" data-msg-equalTo="两次密码输入不一致" 
									/>
								</div>
							</div>
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="realName">姓名</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ sysEmpl.realName }
									</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="idcard">身份证号</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ sysEmpl.idcard }
									</p>
								</div>
							</div>
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="mobile">手机号</label>
								<div class="col-sm-8">
									<input id="mobile" name="mobile" 
										type="text" value="${ sysEmpl.mobile }" maxlength="11" class="form-control" />
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="email">邮箱</label>
								<div class="col-sm-8">
									<input id="email" name="email" 
										type="text" value="${sysEmpl.email }" class="form-control" 
										data-rule-email="true" data-msg-email="请输入正确格式的电子邮箱" 
									/>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="indexPercent">指标比例</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ sysEmpl.indexPercent } %
									</p>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="bonusPercent">提成比例</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										${ sysEmpl.bonusPercent } %
									</p>
								</div>
							</div>
							
						</div>
						
					</div>
					
				</form>
					
				<div class="hr-line-dashed"></div>
				
				<div class="form-group text-center">
					
					<button id="btn_submit" class="btn btn-primary">提交</button>
					
					<button id="btn_cancel" class="btn btn-white">取消</button>
					
				</div>
					
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
                'sysEmpl/submitSysEmpl.json', 
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