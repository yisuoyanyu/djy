<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>积分明细</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script type="text/javascript" src="js/jquery.more.js"></script>
		    <script>  
				 window.onload=function (){  
				 function auto_height()  
				 {  
				 var boxLen = document.getElementById("box").offsetHeight;
				 var scenLen = document.documentElement.clientHeight;
				 if(boxLen<=scenLen){
				  document.getElementById("box").style.minHeight =(document.documentElement.clientHeight)+"px";  
				 }else{  
				    }
				 }  
				    auto_height();  
				    onresize=auto_height;  
				}  
			</script>
	<style>
	.jiazai{ width:97%; height:50px; margin:auto }
	.jiazai em{ display:block; width:25%; float:left; height:24px; border-bottom:1px solid #ccc; margin-bottom:24px;}
	.jiazai span{ display:block; width:50%; float:left; height:50px;font-size:14px; line-height:50px; text-align:center; color:#999}
	.more_loader_spinner { text-align: center; font-size:14px; float:left; width: 100%}
	</style>
	</head>
	 <body class="bg_main"> 
  <div class="bg_topmain" id="topmain"> 
   <div class="am-vertical-align" style="text-align:center"> 
    <div class="success_pay_div"> 
     <div class="pointDetail_topleft_div"> 
      <div style="width:100%;padding-top:5vw"> 
       <span class="top_points_show"> 
         ${user.userAccount.totalRecharge}
       </span> 
      </div> 
      <div style="width:100%"> 
       <span class="white_span"> 总购买积分 </span> 
      </div> 
     </div> 
     <div class="success_pay_right_div"> 
      <div style="width:100%;padding-top:5vw"> 
       <span class="top_points_show"> 
         ${user.userAccount.point}
       </span> 
      </div> 
      <div style="width:100%"> 
       <span class="white_span"> 目前可用积分 </span> 
      </div> 
     </div> 
    </div> 
    <img src="img/icon_jifen.png" class="meal_div_point_bg" /> 
   </div> 
   <img src="img/top-buytaocan.jpg" class="meal_div_top_img" /> 
  </div> 
  <div id="box" class="pointDetail_main_div" style="height: auto;margin-bottom: 2vw;"> 
     <div class="am-g" style="padding-top:3%;font-size:4vw;font-weight:bold;border-bottom: solid 1px rgba(179, 196, 255,0.5);padding-bottom:2vw"> 
    <div class="am-u-sm-3">
     <span style="color:#476df6">积分类型</span>
    </div> 
    <div class="am-u-sm-3">
     <font color="#476df6">交易时间</font>
    </div> 
    <div class="am-u-sm-3" style="text-align:center">
      <font color="#476df6">积分数量</font>
    </div> 
	<div class="am-u-sm-3" style="text-align:center">
      <font color="#476df6">剩余积分</font>
    </div> 
   </div>
   
   <div id="detaildiv">
	<div class="product single_item info"></div>
	<a href="javascript:;" class="get_more"> </a>
	</div>

  </div>
  
  <script type="text/javascript">
  var url = 'pointDetail/getDetails.do?page=';
	$('#detaildiv').more({
		'address' : url
	});
  </script>  
 </body>
</html>