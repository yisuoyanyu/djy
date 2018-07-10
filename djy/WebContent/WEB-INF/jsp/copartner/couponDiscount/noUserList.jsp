<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width" />
<title>个人打折券</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<style>
.am-titlebar-default .am-titlebar-title:before {border-left: 4px solid #3a88ef;}
.listVoucher .item:hover {border: 0px solid #ffa800;}
</style>
<script src="js/coudiscount.more.js"></script>
</head>
<body>
    <!-- 顶部图片 -->
    <div class="am-slider am-slider-a1" data-am-flexslider="{playAfterPaused: 8000}" style="-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0);height: 35vw;">
      <ul class="am-slides">
        <li>
          <img src="../copartner/img/personal-youhuiquan.jpg">
		</li>
      </ul>
    </div>
	<img onclick="javascript:window.location.href='user/mySpread.do'" src="../copartner/img/personal-youhuiquan-fx.png" style="width:32%;position:absolute;top:21vw;right:25vw;">
	
       <div class="listVoucher am-padding-top-sm">
      <ul class="am-list am-margin-0" id="nouseDiv">
      
      <a class="product single_item info"></a>
	  <a href="javascript:;" class="get_more"> </a>
	  
      </ul>
    </div>
	
	   <script type="text/javascript">   
	   var url = 'couponDiscount/getNoCouponDiscount.do?page=';
		$('#nouseDiv').more({
			'address' : url
		});
   
		function copartnerDetail(copartnerId){
			　var currentLons;
			   var currentLats;
			   currentLats = sessionStorage.getItem("currentLat");
			   currentLons = sessionStorage.getItem("currentLon");
			   
			window.location.href = 'copartnerDetail.do?id='+copartnerId+'&lat='+currentLats+'&lon='+currentLons;
			
		}
      </script>
  </body>

</html>