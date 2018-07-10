<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>这是一个测试网页</title>
<script src="../js/jquery.min.js"></script>
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
<h2>hello every Body嗨嗨嗨</h2>
<input id="id_securityCode_input">
			<button id="scanQRCode" type="button" class="am-btn am-btn-xl am-radius am-btn-danger">开启扫描</button>
		
		<div><button id="testQrcodeImg" onclick="submitForm();">生成二维码</button></div>
               <div id="msgDetail"></div> 
               
		<script type="text/javascript">
$("#scanQRCode").click(function() {
    wx.scanQRCode({
        // 默认为0，扫描结果由微信处理，1则直接返回扫描结果
        needResult : 1,
        desc : 'scanQRCode desc',
        success : function(res) {
            //扫码后获取结果参数赋值给Input
            var url = res.resultStr;
            //商品条形码，取","后面的
            if(url.indexOf(",")>=0){
                var tempArray = url.split(',');
                var tempNum = tempArray[1];
                $("#id_securityCode_input").val(tempNum);
            }else{
                $("#id_securityCode_input").val(url);
            }
        }
    });
});


function submitForm(){
	$.post(
			"main/getMoneyQrcode.do", 
			function(ret){
				if(ret.success){
					  
					var msgDetail = document.getElementById("msgDetail");
					msgDetail.innerHTML = "<img src=\"" +ret.data+"\">";
				} else {
					alert(ret.data);
				}
			}
		);
}

</script>
</body>
</html>