<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%
		String basePath = request.getContextPath() + "/partner/";
	%>
	<title>商家管理后台登录</title>
	<script> if (window.top !== window.self) { window.top.location = window.location; } </script>
	
	<script src="js/jquery.cookie.js" type="text/javascript"></script>
</head>
<body class="gray-bg">

	<div class="text-center middle-box loginscreen  animated fadeInDown">
		
		<div>
			
			<h3>欢迎使用商家管理后台</h3>
			
			<div class="m-t" role="form">
				
				<div class="form-group">
					<input id="account" name="account" type="text" class="form-control" placeholder="账号" required="required" />
				</div>
				
				<div class="form-group">
					<input id="password" name="password" type="password" class="form-control" placeholder="密码" required="required" />
				</div>
				
				<div class="form-group">
					<input id="captcha" name="captcha" type="text" class="form-control" style="width:170px;float:left;" placeholder="图形验证码" required="required" />
					<img id="captchaImg" alt="点击获取" style="width: 100px;height:33px;border:1px solid #e5e6e7;" />
					<script type="text/javascript">
						$(function() {
						    setTimeout(function(){
						        $('#captchaImg').attr('src', 'captcha/randomCode.do');
						        $('#captchaImg').click(function(){
						            $(this)[0].src += '?'
						        });
						    }, 1500);
						    
						    if ($.cookie("rmbUser") == "true") {
						        $("#rmbUser").attr("checked", true);
						        $("#account").val($.cookie("username"));
						    }
						    
						});
						
					</script>
				</div>
				
				<button class="btn btn-primary block full-width m-b" onclick="login()">登录</button>
				
				<div class="form-group">
                    <div class="checkbox i-checks">
                        <label class="no-padding">
                            <input type="checkbox" id="rmbUser" name="rmbUser"  style="margin-top:2px;"><i></i>记住账号</label>
                    </div>
                </div>
				
			</div>
			
		</div>
		
	</div>

<script type="text/javascript">
	function validate() {
		
		return true;
	}
	
	function login() {
		if ( !validate() ) 
			return false;
		
		$.ajax({
			type : 'POST',
			url : 'authen/doLogin.json',
			data : {
				'account' : $('#account').val(),
				'password' : $('#password').val(),
				'captcha' : $('#captcha').val()
			},
			dataType : 'json',
			success : function(data, textStatus) {
				if (data.success) {
					
					if($('#rmbUser').is(':checked')) {
						
						var account = $('#account').val();
						
						$.cookie("rmbUser", "true", { expires: 7 }); //存储一个带7天期限的cookie
						$.cookie("username", account, { expires: 7 });
					} else {
				　　　　$.cookie("rmbUser", "false", { expire: -1 });
				　　　　$.cookie("username", "", { expires: -1 });
				　　}
					
					location.replace('<%=basePath%>common/index.do');
				} else if (data.msg != null) {
					Alert({
						msg : data.msg,
						ok : function() {
							location.reload();
						}
					});
				} else {
					Alert({
						msg : '程序错误，请联系管理员！',
						ok : function() {
							location.reload();
						}
					});
				}
			},
			error : function(xmlHttpRequest, textStatus, errorThrown) {
				Alert('连接错误，请联系管理员！');
			}
		});
		
	}
</script>

</body>
</html>