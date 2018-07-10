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
	</style>
	</head>
	 <body class="bg_main"> 
  <div class="bg_topmain" id="topmain"> 
   <div class="am-vertical-align" style="text-align:center"> 
    <div class="success_pay_div"> 
     <div class="cstmConsumeDetail_topleft_div"> 
      <div style="width:100%;padding-top:5vw"> 
       <span class="top_points_show"> 
         ¥ <fmt:formatNumber value="${totalCstmConsume}" type="number" maxFractionDigits="2" /> 元
       </span> 
      </div> 
      <div style="width:100%"> 
       <span class="white_span">消费总额</span> 
      </div> 
     </div> 
     <div class="success_pay_right_div">
      
      <div style="width:100%;padding-top:5vw">
       <span class="top_points_show">
         ¥ <fmt:formatNumber value="${totalBonus}" type="number" maxFractionDigits="2" />
       </span>
      </div>
      <div style="width:100%">
       <span class="white_span">提成总额 </span>
      </div>
      
     </div> 
    </div> 
    <img src="../point/img/icon_jifen.png" class="meal_div_point_bg" /> 
   </div> 
   <img src="../point/img/top-buytaocan.jpg" class="meal_div_top_img" /> 
  </div> 
  <div id="box" class="listCstmConsume" style="height: auto;"> 
    
    <ul class="am-list am-margin-xs" id="detaildiv">
    	<c:forEach var="i" items="${bonusStats}" varStatus="status">
    	<li name="csli">
    	<a href="bouns/accMonBounsList.do?getdate=${i.statsDate}">
		   <div style="margin-left: 0rem;">
		    <div class="am-cf">
		     <span class="am-fl" style="color: black;">${nickName}</span>
		     <span class="am-fr red">提成：￥<fmt:formatNumber value="${i.bonus}" pattern="0.00" type="number" /></span>
		    </div>
		    <div class="am-cf">
		     <span class="am-fl am-text-sm gray"><fmt:formatDate pattern="yyyy年MM月" value="${i.statsDate}" /></span>
		     <span class="am-fr am-text-sm gray">营业额:￥<fmt:formatNumber value="${i.totalCstmConsume}" pattern="0.00" type="number" /></span>
		    </div>
		   </div>
		   </a>
       </li>
    </c:forEach>
    </ul>
    
  </div>
  
     <section class="list-pagination">
					<div style="" class="pagenav-wrapper" id="J_PageNavWrap">
						<div class="pagenav-content">
							<div class="pagenav" id="J_PageNav">
								<div class="p-prev p-gray">
									<c:if test="${lastPage == 0}">
										<a href="JavaScript:void(0);">上一页</a>
									</c:if>
									<c:if test="${lastPage != 0}">
										<a href="bouns/bounsList.do?page=${lastPage}">上一页</a>
									</c:if>
								</div>
								<div class="pagenav-cur" style="vertical-align: bottom">
									<div class="pagenav-text">
										<span>${page}/${totalPages}</span> <i> </i>
									</div>
									<select class="pagenav-select" onchange="location.href=this.options[this.selectedIndex].value;">
										<c:forEach var="i" begin="${startShowPage}" end="${endShowPage}" step="1">
											<c:if test="${i == page}">
												<option selected="selected" value="bouns/bounsList.do?page=${i}">第${i}页</option>
											</c:if>
											<c:if test="${i != page}">
												<option value="bouns/bounsList.do?page=${i}">第${i}页</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
								<div class="p-next">
									<c:if test="${nextPage == 1}">
										<a href="JavaScript:void(0);">下一页</a>
									</c:if>
									<c:if test="${nextPage != 1}">
										<a href="bouns/bounsList.do?page=${nextPage}">下一页</a>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</section>
  
 </body>
 <script type="text/javascript">
 function getAccList(statsDate){
	 
	 window.location.href = "bouns/accBounsList.do?getdate="+statsDate;
	 
 }
 </script>
</html>