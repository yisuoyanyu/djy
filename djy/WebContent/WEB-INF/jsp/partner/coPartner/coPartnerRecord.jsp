<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>合作商家修改密码</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	<%@include file="../decorators/listViewHead.jsp"%>
	
</head>

<body class="gray-bg">
	
	<!-- 表单顶部操作条 -->
	<div class="formHead">
		<nav class="navbar" role="navigation">
			
			<div class="navbar-header">
				<span>操作：</span>
			</div>
			
			<div class="navbar-collapse collapse">
				
				<ul class="nav navbar-nav">
					
					
					<c:if test="${ (empty coPartner.id || coPartner.status == normalStatus) }">
						
						<li class="active">
							<a id="btn_submit" role="button" href="javascript:;">
								<i class="fa fa-save"></i>
								提交
							</a>
						</li>
						
					</c:if>
					
					
				</ul>
				
				
				<ul class="nav navbar-right">
					<li>
						<a id="btn_refresh" href="javascript:;">
							<i class="glyphicon glyphicon-repeat"></i>
							刷新
						</a>
					</li>
				</ul>
				
			</div>
			
		</nav>
	</div>
	
	<!-- 表单内容 -->
	<div class="formBody">
	
		<div class="wrapper wrapper-content">
		
			<div class="ibox float-e-margins">
				
				<div class="ibox-title">
					<h5>
						${coPartner.name }
					</h5>
					
					<div class="ibox-tools">
						<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
					</div>
				</div>
				
				<div class="ibox-content">
					
					<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
						
						<% /*--这个hidden的input需要输出到浏览器端--*/ %>
						<input type="hidden" id="id" name="id" value="${coPartner.id }" />
						<div class="tabs-container">
							<ul class="nav nav-tabs">
								<li class="active"><a data-toggle="tab" href="#tab_basic" aria-expanded="true">修改密码</a></li>
							</ul>
							<div class="tab-content">
								
								<div id="tab_basic" class="tab-pane active">
									<div class="panel-body">
<div class="container-fluid">
	
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="userMobile">账号</label>
			<div class="col-sm-8">
				<input id="userId" name="userId" value="${ coPartner.user.id }" type="hidden" class="form-control"/>
				<p class="form-control-static">${ coPartner.user.mobile }</p>
			</div>
		</div>
		
	</div>
	
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="password">密码</label>
			<div class="col-sm-8">
				<input type="hidden" id="oldPassword" name="oldPassword"  value="${coPartner.password }"/>
				<input type="password" class="form-control" id="password" name="password"  
				<c:if test="${ empty coPartner.id }">
					data-rule-required="true" data-msg-required="请输入密码"
				</c:if> 
				/>
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="confirmPassword">确认密码</label>
			<div class="col-sm-8">
				<input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
					<c:if test="${ empty coPartner.id }">
					data-rule-required="true" data-msg-required="请输入确认密码"
					</c:if> 
					data-rule-equalTo="#password" data-msg-equalTo="两次密码输入不一致" 
				/>
			</div>
		</div>
		
	</div>	
	
</div>
					
									</div>
								</div>
							
						</div>
					</div>                        	
                            	
				</form>
					
				
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
        
        
        function validateForm() {
        	$('#form').validate({ignore:''}).form();
        	
            // 验证 页签内容没有显示隐藏项 的页签
            var keys = [ 'basic' ];
            for ( var i=0; i<keys.length; i++ ) {
                var key = keys[i];
                
                var $tab = $('#tab_' + key);
                if ( $tab.length == 0 )
                    continue;
                
                if ( ! $tab.validate({ignore:''}).form() ) {
                    $('a[href="#tab_' + key + '"]').tab('show');
                    return false;
                }
            }
            
            return true;
        }
        
        function submitForm() {
            $.post(
                'coPartner/submitCoPartner.json', 
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
            if ( ! validateForm() ) 
               return false;
            
            // 提交表单
            preUpload( {
                success : function () {
                    submitForm();
                }
            } );
            
        });
        
        $('#btn_refresh').click(function(){
            window.location.reload();
        });
    });
</script>

</body>
</html>