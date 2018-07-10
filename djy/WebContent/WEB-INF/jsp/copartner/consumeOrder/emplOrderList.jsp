<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>个人营业额</title>
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
    	<c:forEach var="i" items="${consumeOrders}" varStatus="status">
    	<li name="csli">
		   <div style="margin-left: 0rem;">
		    <div class="am-cf">
		     <span class="am-fl" style="color: black;">${i.user.wechatUser.nickname}</span>
		     <span class="am-fr red">消费：￥<fmt:formatNumber value="${i.consumeAmount}" pattern="0.00" type="number" /></span>
		    </div>
		    <div class="am-cf">
		     <span class="am-fl am-text-sm gray"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${i.insertTime}" /></span>
		     <span class="am-fr am-text-sm gray">实付:￥<fmt:formatNumber value="${i.payAmount}" pattern="0.00" type="number" /></span>
		    </div>
		    <div class="am-cf">
		     <span class="am-fl am-text-sm gray">状态:
		     <c:if test="${i.status == 1}">
           	 待付款
            </c:if>
            <c:if test="${i.status == 3}">
           	 已完成
            </c:if>
            <c:if test="${i.status == 5}">
           	 已取消
            </c:if>
            <c:if test="${i.status == 2}">
                                      付款中
            </c:if>
		     </span>
		     
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
										<a href="consumeOrder/getEmplOrderList.do?page=${lastPage}">上一页</a>
									</c:if>
								</div>
								<div class="pagenav-cur" style="vertical-align: bottom">
									<div class="pagenav-text">
										<span>${page}/${totalPages}</span> <i> </i>
									</div>
									<select class="pagenav-select" onchange="location.href=this.options[this.selectedIndex].value;">
										<c:forEach var="i" begin="${startShowPage}" end="${endShowPage}" step="1">
											<c:if test="${i == page}">
												<option selected="selected" value="consumeOrder/getEmplOrderList.do?page=${i}">第${i}页</option>
											</c:if>
											<c:if test="${i != page}">
												<option value="consumeOrder/getEmplOrderList.do?page=${i}">第${i}页</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
								<div class="p-next">
									<c:if test="${nextPage == 1}">
										<a href="JavaScript:void(0);">下一页</a>
									</c:if>
									<c:if test="${nextPage != 1}">
										<a href="consumeOrder/getEmplOrderList.do?page=${nextPage}">下一页</a>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</section>
  
 </body>
</html>