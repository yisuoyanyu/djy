<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<!DOCTYPE html>
<html>
<head>
	
	<title>主页</title>
		
	<!-- 自定义js -->
	<script src="frames/hplus/js/content.js" type="text/javascript"></script>
	<script type="text/javascript">

function wechatPay(){
	
	$.ajax({
		type : 'post',
		url : '../admin/common/wechatHandle.do',
		dataType:'json',
		async : true,
		success : function(ret) {
			if (ret.success) {
				
				alert(ret.data);
				$("#qrcode").attr("src",ret.data);

			}else{

			}
		}
	});
	
}
</script>
	
</head>
<body class="gray-bg">
	
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>统计</h5>
                    </div>
                    <div class="ibox-content">

				         <table class="table table-bordered" id="tj">
				             <thead>
				                 <tr>
				                     <th>统计项</th>
				                     <th>昨日</th>
				                     <th>今日</th>
				                     <th>总数</th>
				                 </tr>
				             </thead>
				             <tbody>
				                 <tr>
				                     <td>平台会员（个）</td>
				                     <td>${userAddYesterday}</td>
				                     <td>${userAddToday}</td>
				                     <td>${userAddTotal}</td>
				                 </tr>
				                 <tr>
				                     <td>会员消费订单（个）</td>
				                     <td>${consumeOrderNumAddYesterday}</td>
				                     <td>${consumeOrderNumAddToday}</td>
									 <td>${consumeOrderNumAddTotal}</td>
				                 </tr>
				                 <tr>
				                     <td>会员消费金额（元）</td>
				                     <td>${consumeAmountYesterday}</td>
				                     <td>${consumeAmountToday}</td>
				                     <td>${consumeAmountTotal}</td>
				                 </tr>
				                 <tr>
				                     <td>会员支付金额（元）</td>
				                     <td>${consumePayAmountYesterday}</td>
									 <td>${consumePayAmountToday}</td>
									 <td>${consumePayAmountTotal}</td>
				                 </tr>
				                 <tr>
				                     <td>被邀会员（个）</td>
				                     <td>${consumeUserAddYesterday}</td>
				                     <td>${consumeUserAddToday}</td>
									 <td>${consumeUserAddTotal}</td>
				                 </tr>
				                 <tr>
				                     <td>被邀会员消费订单（个）</td>
				                     <td>${consumeOrderAddYesterday}</td>
				                     <td>${consumeOrderAddToday}</td>
									 <td>${consumeOrderAddTotal}</td>
				                 </tr>
				                 <tr>
				                     <td>被邀会员消费金额（元）</td>
				                     <td>${invitedConsumeAmountYesterday}</td>
				                     <td>${invitedConsumeAmountToday}</td>
									 <td>${invitedConsumeAmountTotal}</td>
				                 </tr>
				                 <tr>
				                     <td>被邀会员支付金额（元）</td>
				                     <td>${invitedConsumePayAmountYesterday}</td>
				                     <td>${invitedConsumePayAmountToday}</td>
									 <td>${invitedConsumePayAmountTotal}</td>
				                 </tr>
				             </tbody>
				         </table>
					<button onclick="wechatPay();">点击调用微信支付</button>
					<img id="qrcode" src="">
     				</div>
     			</div>
     		</div>
     	</div>
	</div>
</body>

</html>