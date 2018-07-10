<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>每月提成列表</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script type="text/javascript" src="../point/js/jquery.more.js"></script>
		<script src="../point/js/public.js"></script>
		<link rel="stylesheet" href="../point/css/zbhpublic.css">
		    <script>  
				 window.onload=function (){  
				 function auto_height()  
				 {  
				 var boxLen = document.getElementById("box").offsetHeight;
				 var scenLen = document.documentElement.clientHeight;
				 if(boxLen<=scenLen){
				  document.getElementById("box").style.minHeight=(document.documentElement.clientHeight)+"px";  
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
	.am-fl { float: left;width:50%}
	.am-fr {float: left;width: 50%;}
	</style>
	</head>
	 <body class="bg_main"> 
	 
  <div id="box" class="listCstmConsume" style="height: auto;"> 
    
    <ul class="am-list am-margin-xs" id="detaildiv">
    	<c:forEach var="i" items="${bonusStats}" varStatus="status">
    	<li name="csli">
    	<a href="bouns/accBounsList.do?getdate=${i.statsDate}">
		   <div style="margin-left: 0rem;">
		    <div class="am-cf">
		     <span class="am-fl" style="color: black;">${nickName}</span>
		     <span class="am-fr red">提成：￥<fmt:formatNumber value="${i.bonus}" pattern="0.00" type="number" /></span>
		    </div>
		    <div class="am-cf">
		     <span class="am-fl am-text-sm gray"><fmt:formatDate pattern="yyyy年MM月dd日" value="${i.statsDate}" /></span>
		     <span class="am-fr am-text-sm gray">营业额:￥<fmt:formatNumber value="${i.totalCstmConsume}" pattern="0.00" type="number" /></span>
		    </div>
		   </div>
		   </a>
       </li>
    </c:forEach>
    </ul>
    
  </div>
  
 </body>
</html>