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
<title>信息中心</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<script src="js/jquery.more.js"></script>
<link rel="stylesheet" href="../copartner/css/shangpinxiangqing.css">
<style type="text/css">
.am-list > li > a {
  display: inline-block;
  padding: 1rem 0;
}
</style>	
</head>
	<body>
		<header data-am-widget="header" class="am-header am-header-fixed am-header-default">
			<div class="am-header-left am-header-nav">
				<a href="javascript:history.back();">
					<i class="am-header-icon am-icon-angle-left am-icon-sm"></i>
				</a>
			</div>
			<h1 class="am-header-title">
				<a href="">系统消息</a>
			</h1>
		</header>
  <div data-am-widget="list_news" class="am-list-news am-list-news-default" >
  <div class="am-list-news-bd">
  <ul class="am-list" id="msgList">
    <a class="product single_item info" style=""></a>
	<a href="javascript:;" class="get_more"> </a>

    </ul>
  </div>

    </div>
		<script type="text/javascript">
		 var url = 'userSiteMsg/getUserSiteMsg.do?type=${type}&page=';
			$('#msgList').more({
				'address' : url
			});
			
			function addTimeAndgo(url,msgId){
				if (url != null || url != undefined || url != '') { 
					window.location.href = url;
				}
				
				 $.ajax({
						type : 'post',
						url : 'userSiteMsg/addReadTime.do',
						data : {
							'msgId': msgId
						},
						dataType:'json',
						async : true,
						success : function(data) {
							
						}
					});
			}
			
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