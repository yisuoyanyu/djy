<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>
		<c:if test="${empty user.mobile}">
		绑定手机
		</c:if>
		<c:if test="${not empty user.mobile}">
		修改手机
		</c:if>
		</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		 <style type="text/css">
			input::-ms-input-placeholder {
				text-align: left;
				color:black;
			}
			
			input::-webkit-input-placeholder {
				text-align: left;
				color:black;
			}
		 </style> 
	</head>
	<body class="bg_main"> 
	
	<div class="tip_act_bar">
			<ul>
				<li>
					<a href="javascript:history.back(-1);"><i class="back"></i></a>
				</li>
			</ul>
	</div>
  <div class="bg_topmain"> 
   <div class="am-vertical-align" style="text-align:center;height:24vw"> 
    <div class="bus_top_title"> 
     <span class="bus_top_titlespan">
     <c:if test="${empty user.mobile}">
		绑定手机
		</c:if>
		<c:if test="${not empty user.mobile}">
		修改手机
		</c:if>
     </span> 
    </div> 
    <div class="bus_top_imgdiv"> 
     <img src="img/icon_tel.png" style="width:11vw;padding-top:3vw" /> 
    </div> 
   </div> 
   <img src="img/top-buytaocan.jpg" class="meal_div_top_img" /> 
  </div> 
  <div class="point_shalow_div"> 
   <div class="bindPhone_topdiv">
    <span class="bus_mian_topspan">*为了保障您的账户安全,请您进行手机绑定!</span>
   </div> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-shouji" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_main_editnameinput" type="text" id="ID_Mobile" placeholder="请输入手机号" /> 
     </div> 
    </div> 
   </div> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-yanzhengmayanzheng" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bindPhone_codeinput" type="text" id="ID_SmsCode" placeholder="请输入验证码" /> 
      <!--<span class="btn_border" style="height:10vw;line-height:8vw;margin-top:3vw;margin-left:5vw">获取验证码</span>--> 
      <button id="ID_GetVerificationCode" class="bindPhone_codebutton" onclick="getVerificationCode();">获取验证码</button> 
     </div> 
    </div> 
   </div> 
   <div class="full_ccenter_div"> 
    <span id="ID_SetMobile" class="button blue bigrounded" style="margin-top:15vw;margin-bottom:30vw">　
       <c:if test="${empty user.mobile}">
		绑定手机
		</c:if>
		<c:if test="${not empty user.mobile}">
		修改手机
		</c:if>
　</span> 
   </div> 
  </div>  
  
  <script type="text/javascript">
  document.body.addEventListener('touchstart', function () {});
	var popover = $('#ID_Mobile').popover({
		content: '请输入正确的手机号码',
		trigger: 'hover focus',
		theme: 'danger'
	});
	$('#ID_Mobile').click(function(){
		$('#ID_Mobile').popover('close');
	});
	$('#ID_Mobile').change(function(){
		if (this.value.replace(/\d{1,}/, '') != '') {
			this.value = this.value.match(/\d{1,}/)==null? "": this.value.match(/\d{1,}/);
		}
	});
	
	/**
	 * 判断手机号码格式是否正确
	 */
	function validateMobile(){
		var mobile = $("#ID_Mobile").val();
		if ( mobile == '' ) {
			return false;
		}
		
		//判断mobile是否为手机号
		var pattern = /^1((3|5|7|8){1}\d{1}|70)\d{8}$/
		if ( !pattern.test(mobile) ) {
			return false;
		}
		
		return true;
	}
	
	
	var times = 60;	//获取验证码倒计时
	var time = times;
	
	function clearTimmer(btn){
		clearInterval(interval);
		time = times;
		$(btn).text("重新获取");
		$(btn).attr("disabled",false);
	}
	
	function timmer(btn) {
		if (time > 1) {
			time = time - 1;
			$(btn).text("重新获取(" + time + ")");
			return;
		}
		clearTimmer(btn);
	}
	
	//一定时间内不让点击“获取验证码”按钮
	$('#ID_GetVerificationCode').click(function(){
		if ( !validateMobile() ) {
			$('#ID_Mobile').popover('open');
			return false;
		}
		
		var btn = this;
		$(btn).attr("disabled",true);
		window.interval = setInterval(function() {
			timmer(btn);
		}, 1000);
	});
	
	
	var smsToken;	//短信验证码对应的token
	
	function getVerificationCode(){
		//验证手机
		if ( !validateMobile() )
			return false;
		
		//ajax请求发送验证码
		$.ajax({
			type : 'POST',
			url : 'bindPhone/sendSmsCode.json',
			data : {
				"scene" : 1002, 
				"mobile" : $("#ID_Mobile").val()
			},
			dataType : "json",
			success : function(ret) {
				if (!ret.success) {
					var msg = ret.msg;
					Alert({
						msg : ret.msg,
						ok : function() {
							clearTimmer($('#ID_GetVerificationCode')[0]);
						}
					});
					return;
				}
				
				var d = ret.data;
				smsToken = d.token;
				
				var mobile = d.mobile;
				Alert("验证码已发送到手机" + mobile + "上，请注意查收！");
			},
			error : function(errorMsg) {// 发生错误时消息处理
				alert(errorMsg);
			}
		});
		
	}
	
	$('#ID_SetMobile').click(function(){
		if ( !validateMobile() ) {
			$('#ID_Mobile').popover('open');
			return false;
		}
		
		if ( $('#ID_SmsCode').val() == '' ) {
			Alert("验证码不能为空！");
			return false;
		}
		
		$.post(
			"bindPhone/bindingMobile.json", 
			{
				"mobile" : $('#ID_Mobile').val(),
				"smsToken" : smsToken,
				"smsCode" : $('#ID_SmsCode').val(),
			},
			function(ret) {
				if (!ret.success) {
					Alert(ret.msg);
					return false;
				}
				Alert({
					msg : '绑定成功',
					ok : function() {
						self.location=document.referrer;
					}
				});
			}, 
			"json"
		);
		
	});
</script>
  
 </body>
</html>