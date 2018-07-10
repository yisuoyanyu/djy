<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>我的推广会员</title>
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
	</head>
	 <body class="bg_main"> 
    <!-- 页签 -->
    <div class="bar h3 tabs_bar posi_fixed border_b">
      <ul class="am-avg-sm-2" style="position:relative;">
         <li class="on">
          <a href="javascript:void(0);" style="color: white;">推广会员</a></li>
        <li>
          <a href="javascript:location.replace('spreadCommission/myCommission.do?page=1');">推广佣金</a></li>
           </ul>
    </div>
  <div id="box" class="listCstmConsume" style="height: auto;"> 
    
    <ul class="am-list am-margin-xs" id="detaildiv">
    	<c:forEach var="i" items="${spreadUserVos}" varStatus="status">
    	<li name="csli">
		   <div style="margin-left: 0rem;">
		    <div class="am-cf">
		     <span class="am-fl" style="color: black;">${i.nickName}</span>
		     <span class="am-fr red">佣金状态:${i.statusName}</span>
		    </div>
		    <div class="am-cf">
		     <span class="am-fl am-text-sm gray"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${i.insertTime}" /></span>
		     <span class="am-fr am-text-sm gray">佣金金额:￥<fmt:formatNumber value="${i.commision}" pattern="0.00" type="number" /></span>
		    </div>
		   </div>
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
										<a href="user/mySpreadUser.do?page=${lastPage}">上一页</a>
									</c:if>
								</div>
								<div class="pagenav-cur" style="vertical-align: bottom">
									<div class="pagenav-text">
										<span>${page}/${totalPages}</span> <i> </i>
									</div>
									<select class="pagenav-select" onchange="location.href=this.options[this.selectedIndex].value;">
										<c:forEach var="i" begin="${startShowPage}" end="${endShowPage}" step="1">
											<c:if test="${i == page}">
												<option selected="selected" value="user/mySpreadUser.do?page=${i}">第${i}页</option>
											</c:if>
											<c:if test="${i != page}">
												<option value="user/mySpreadUser.do?page=${i}">第${i}页</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
								<div class="p-next">
									<c:if test="${nextPage == 1}">
										<a href="JavaScript:void(0);">下一页</a>
									</c:if>
									<c:if test="${nextPage != 1}">
										<a href="user/mySpreadUser.do?page=${nextPage}">下一页</a>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</section>
  
 </body>
</html>