<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html class="no-js">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width" />
<title>加盟合作</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
</head>
 <body class="bg_white">
	 <div class="am-g join-div">
      <div class="am-u-sm-2" style="padding-right:0rem;" >
        <img src="../copartner/img/shangjia.png" class="join-logoimg">
      </div>
      <div class="am-u-sm-6 join-title">
       <span class="join-title-detail1">商家入驻</span>
	   <span class="join-title-detail3">油惠站向您导流客户</span>
      </div>
	  <div class="am-u-sm-4 join-title1">
	 <div class="am-vertical-align" style="height: 11vw;">
	  <div class="am-vertical-align-middle">
		<span class="join-title-detail2" onclick="javascript:applyCopartner();">立即申请</span>
	  </div>
	</div>
      </div>
    </div>
	
	
 <div class="am-g join-div">
      <div class="am-u-sm-2" style="padding-right:0rem;" >
        <img src="../copartner/img/aityagent.png" class="join-logoimg">
      </div>
      <div class="am-u-sm-6 join-title1">
       <span class="join-title-detail1">城市代理申请</span>
	   <span class="join-title-detail3">免费加盟代理市级或地方门店</span>
      </div>
	  <div class="am-u-sm-4 join-title1" >
	 <div class="am-vertical-align" style="height: 11vw;">
	  <div class="am-vertical-align-middle">
		<span class="join-title-detail2" onclick="javascript:applyCityAgent();">立即申请</span>
	  </div>
	</div>
      </div>
    </div>
	
	 <div class="am-g join-div">
      <div class="am-u-sm-2" style="padding-right:0rem;" >
        <img src="../copartner/img/citycopartner.png" class="join-logoimg">
      </div>
      <div class="am-u-sm-6 join-title1">
       <span class="join-title-detail1">城市合伙人申请</span>
	   <span class="join-title-detail3">每个城市招募两人</span>
      </div>
	  <div class="am-u-sm-4 join-title1">
	 <div class="am-vertical-align" style="height: 11vw;">
	  <div class="am-vertical-align-middle">
		<span class="join-title-detail2" onclick="javascript:applyCityPartner();">立即申请</span>
	  </div>
	</div>
      </div>
    </div>
	
	<div class="am-g join-div">
      <div class="am-u-sm-2" style="padding-right:0rem;line-height:17vw;" >
        <img src="../copartner/img/xiaoshourouct.png" class="join-logoimg">
      </div>
      <div class="am-u-sm-10 join-title1">
       <span class="join-title-detail1">销售产品合作</span>
	   <span class="join-title-detail3">如果您想将您的商品入驻油惠站，请联系陈经理:130-5533-3765</span>
      </div>
    </div>
	
	<div class="am-g join-div">
      <div class="am-u-sm-2" style="padding-right:0rem;" >
        <img src="../copartner/img/brand.png" class="join-logoimg">
      </div>
      <div class="am-u-sm-10 join-title1">
       <span class="join-title-detail1">品牌广告合作</span>
	   <span class="join-title-detail3">如果有相关广告品牌方面的业务，请拨打010-64399622</span>
      </div>
    </div>
<script type="text/javascript">
  function applyCityPartner(){
	  window.location.href="apply/applyCityPartner.do";
  }
  function applyCopartner(){
	  window.location.href="apply/applyCopartner.do";
  }
  function applyCityAgent(){
	  window.location.href="apply/applyCityAgent.do";
  }
</script>
  </body>

</html>