<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>
<!doctype html>
<html class="no-js">
<head>
<title>首页</title>
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
        $(function() {
            wx.config({
                //debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId : '${ wxConfig.appId }', // 必填，公众号的唯一标识
                timestamp : ${ wxConfig.timestamp }, // 必填，生成签名的时间戳
                nonceStr : '${ wxConfig.nonceStr }', // 必填，生成签名的随机串
                signature : '${ wxConfig.signature }',// 必填，签名，见附录1
                jsApiList : [ 'scanQRCode' ]
            // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
        });
        
    </script>
</head>
<body>
	<div
		class="am-padding-top-lg am-padding-right-sm am-padding-bottom-sm am-padding-left-lg"
		style="background-image: url(../point/img/bg_top.png); height: 27%">
		<div class="am-vertical-align"
			style="margin-bottom: 4%; margin-top: 4%">
			<c:if test="${empty user.wechatUser.headimgUrl}">
				<img src="img/ico_portrait.png"
					class="am-inline-block am-vertical-align-middle" />
			</c:if>
			<c:if test="${not empty user.wechatUser.headimgUrl }">
				<img src="${user.wechatUser.headimgUrl}"
					class="am-img-responsive am-circle am-inline-block am-vertical-align-middle"
					style="max-width: 6rem; max-height: 6rem;" />
			</c:if>

			<div class="am-inline-block am-vertical-align-middle">
				<div style="padding-left: 3vw;">
					<span class="white"> 
					<c:if test="${user.coPartner.status == 2}">
			               ${user.coPartner.name}
			        </c:if>
			         <c:if test="${user.coPartner.status != 2}">
			           ${user.wechatUser.nickname}
			        </c:if>
					</span>
				</div>
			</div>
		</div>
	</div>
	<!-- 首页主菜单-->
	<div class="bg_white">
		<div class="ico_btn_bar am-padding-top-sm">
			<ul class="am-avg-sm-3">
				<li class="am-text-center" style="margin-bottom: 10%; margin-top: 10%">
			<c:if test="${user.coPartner.status == 2}">
				<a href="qrCode/myQrcod.do"> 
				</c:if>
				<c:if test="${user.coPartner.status != 2}">
				<a href="javascript:showNo(${user.coPartner.status});"> 
				</c:if>
				
				<span><i class="myQrCode"></i></span>
						<span>我的二维码</span>
				</a>
					</li>
				<li class="am-text-center"
					style="margin-bottom: 10%; margin-top: 10%;"><a
					href="info/myInfo.do"> <span><i class="shopDetail"></i></span>
						<span>商家信息</span>
				</a></li>
				<li class="am-text-center"
					style="margin-bottom: 10%; margin-top: 10%;">
					<c:if test="${user.coPartner.status == 2}">
					<a href="cstmConsume/cstmConsumeDetail.do"> 
					</c:if>
					<c:if test="${user.coPartner.status != 2}">
					<a href="javascript:showNo(${user.coPartner.status});"> 
					</c:if>
					<span><i class="cstmConsume"></i></span> 
					<span>会员消费</span>
				</a>
				</li>
			</ul>
		</div>
	</div>
	<!-- 积分支付提示层 -->
	<div id="ID_PayPointNow" class="am-modal" tabindex="-1">
		<input type="hidden" id="sysEmpl" name="sysEmpl">
		<div class="am-modal-dialog voucherPrompt" style="position: relative">
			<i class="close" data-am-modal-close=""></i> <span
				class="pay_hint_lay_top_msg">尊敬的用户您好!您需支付积分:</span> <img
				src="../point/img/bg_pointpay.png" style="width: 100%;" />
			<div style="margin-top: -20vw;">
				<span class="btn_border"
					style="width: 61%; box-shadow: 0 4px 15px #58a1ff; height: 12vw; line-height: 10vw; font-size: 7vw"
					onclick="pointExchange()">立即支付</span>
			</div>
			<div class="pay_hint_lay_top_pointnumdiv">
				<span class="pay_hint_lay_top_pointnum" id="buyPrice">2355</span>
			</div>
		</div>
	</div>

	<!-- 积分不足支付提示层 -->
	<div id="ID_PointNotEnough" class="am-modal" tabindex="-1">
		<div class="am-modal-dialog voucherPrompt"
			style="background-color: #f9faff; width: 96%; border-radius: 12px">
			<i class="closeout" data-am-modal-close></i>
			<div style="width: 100%">
				<img src="../point/img/icon_defe.png" class="pointnotenough_top_img" />
			</div>
			<div style="width: 100%">
				<span class="pointnotenough_top_span">尊敬的用户，实在抱歉，您的积分余额不足，您还需要充值<span
					id="notEnough">100</span>积分，为了保障你兑换成功，请您立即充值!
				</span>
			</div>
			<div class="pointnotenough_subdiv">
				<span class="btn_border"
					style="width: 50%; box-shadow: 0 4px 15px #58a1ff; height: 41px; line-height: 35px; font-size: 25px"
					onclick="pointsNotEnough();">立即充值</span>
			</div>
		</div>
	</div>

	<!--<a id="ID_RedEnvelope" href="voucher/voucherListCur.html" class="redEnvelope am-hide" style="right:0rem; bottom:8.5rem;"><i></i></a>-->
	<script type="text/javascript">
			//$(function() {
				//$('#ID_VoucherPrompt').modal();
			//});
		</script>


	<script type="text/javascript">
		
		function pointExchange(){
	            var jsPost = function(action, values) {
	              var id = Math.random();
	              document.write('<form id="post' + id + '" name="post'+ id +'" action="' + action + '" method="post">');
	              for (var key in values) {
	                document.write('<input type="hidden" name="' + key + '" value="' + values[key] + '" />');
	              }
	              document.write('</form>');  
	              document.getElementById('post' + id).submit();
	            }
	            jsPost('pointExchange/pointExchange.do', {
	              'content': $("#sysEmpl").val()
	              //,
	             // 'points': $("#buyPrice").text()
	            });  
	            
		}
		
		function pointsNotEnough(){
			if (${busOrCus}  == 0) {
				window.location.href="pointBuy/packagesBuy.do";
			}else {
				window.location.href="pointBuy/pointBuy.do";
			}
		}
		
		function checkPackagesBuy(){
			$.post(
					"bindUser.do", 
					{}, 
					function(data, textStatus) {
						if (data.success) {
							location.href = 'pointBuy/packagesBuy.do';
						} else {
							Confirm({
								msg : '绑定手机才能购买积分',
								cancelBtn : '否',
								okBtn : '是',
								cancel : function() { },
								ok : function() {
									location.href = 'bindPhone/bindPhone.do';
                                  },
								relatedTarget: this
							});
							
						}
					}
				);
		}
		
		function checkPointsBuy() {
			$.post(
					"bindUser.do", 
					{}, 
					function(data, textStatus) {
						if (data.success) {
							location.href = 'pointBuy/pointBuy.do';
						} else {
							Confirm({
								msg : '绑定手机才能购买积分',
								cancelBtn : '否',
								okBtn : '是',
								cancel : function() { },
								ok : function() {
									location.href = 'bindPhone/bindPhone.do';
                                  },
								relatedTarget: this
							});
						}
					}
				);
		}
		
		function checkPointsGibe() {
			$.post(
					"bindUser.do", 
					{}, 
					function(data, textStatus) {
						if (data.success) {
							location.href = 'pointTransfer/pointTransfer.do';
						} else {
							Confirm({
								msg : '绑定手机才能赠送积分',
								cancelBtn : '否',
								okBtn : '是',
								cancel : function() { },
								ok : function() {
									location.href = 'bindPhone/bindPhone.do';
                                  },
								relatedTarget: this
							});
						}
					}
				);
		}
		
		function showNo(status){
			if(status == 0){
				Alert("请您先填写资料");
			}
			if(status == 1){
				Alert("您的资料正在审核中");
			}
			if(status == 3){
				Alert("您暂时被禁用");
			}
			
		}
			
		</script>
</body>
</html>