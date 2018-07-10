<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>套餐购买</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
	</head>
	<body class="bg_main">
    <div class="bg_topmain">
      <div class="am-vertical-align" style="text-align:center">
        <div style="width:100%;padding-top:5vw">
          <span class="top_points_show">${user.userAccount.point}</span></div>
        <div style="width:100%">
          <span class="top_points_show">当前可用积分</span></div>
        <img src="img/icon_jifen.png" class="meal_div_point_bg" /></div>
      <img src="img/top-buytaocan.jpg" class="meal_div_point_bottom" /></div>
    <div class="main_center_div">
      <span style="color:#476df6">●选择购买套餐</span>
      <div class="ico_btn_bar am-padding-top-sm">
        <ul class="am-avg-sm-3" style="padding-left:2%">
        
        <c:forEach var="i" items="${packages}" varStatus="status">
            <c:if test="${status.index % 3 == 1}">
            <li class="am-text-center" style="padding-right:2%;padding-top: 2%;">
		  <label class="am-checkbox am-packagesa" style="color:#ffb923;padding-left:0px;margin-bottom:0px;margin-top:0px">
            <div class="meal_div">
              <div class="meal_div_top">
                <img src="img/top-yellow.jpg" class="meal_div_top_img" />
                <div class="meal_div_top_precent">${i.discountedRate}折</div>
                <span class="meal_div_pointone">${i.point}</span></div>
              <div style="padding-top:10px">
                <span style="color:red;">￥${i.nowPrice}</span></div>
              <div class="meal_div_unmoneydiv">
                <span class="meal_div_unmoney_span">￥${i.origPrice}</span></div>
              <hr class="meal_div_hr" />
              
                <input type="checkbox" name="price" id="price" checked="checked" value="${i.id}" data-am-ucheck checked />&nbsp;&nbsp;${i.title}</div></label>
          </li>
            </c:if>
            <c:if test="${status.index % 3 == 2}">
            <li class="am-text-center" style="padding-right:2%;padding-top: 2%;">
		   <label class="am-checkbox am-packagesb" style="color:#476df6;padding-left:0px;margin-bottom:0px;margin-top:0px">
            <div class="meal_div">
              <div class="meal_div_top">
                <img src="img/top-blue.jpg" class="meal_div_top_img" />
                <div class="meal_div_top_precent">${i.discountedRate}折</div>
                <span class="meal_div_pointtwo">${i.point}</span></div>
              <div style="padding-top:10px">
                <span style="color:red;">￥${i.nowPrice}</span></div>
              <div class="meal_div_unmoneydiv">
                <span class="meal_div_unmoney_span">￥${i.origPrice}</span></div>
              <hr class="meal_div_hr" />
             
                <input type="checkbox" name="price" id="price" checked="checked" value="${i.id}" data-am-ucheck checked/>&nbsp;&nbsp;${i.title}</div></label>
          </li>
            </c:if>
            
            <c:if test="${status.index % 3 == 0}">
             <li class="am-text-center" style="padding-right:2%;padding-top: 2%;">
		   <label class="am-checkbox am-packagesc" style="color:#ff4c50;padding-left:0px;margin-bottom:0px;margin-top:0px">
            <div class="meal_div">
              <div class="meal_div_top">
                <img src="img/top-red.jpg" class="meal_div_top_img" />
                <div class="meal_div_top_precent">${i.discountedRate}折</div>
                <span class="meal_div_pointthree">${i.point}</span></div>
              <div style="padding-top:10px">
                <span style="color:red;">￥${i.nowPrice}</span></div>
              <div class="meal_div_unmoneydiv">
                <span class="meal_div_unmoney_span">￥${i.origPrice}</span></div>
              <hr class="meal_div_hr" />
             
                <input type="checkbox" name="price" id="price" checked="checked" value="${i.id}" data-am-ucheck checked/>&nbsp;&nbsp;${i.title}</div></label>
          </li>
            </c:if>
        </c:forEach>
          
        </ul>
        <div class="am-padding am-text-center" style="padding-top:15%;">
          <span class="button blue bigrounded"  onclick="submit(); this.disabled=true; return true;">　立 即 购 买　</span></div>
      </div>
    </div>
    
    <script type="text/javascript">
    document.body.addEventListener('touchstart', function () {});
        function submit() {
        	var packagePrice=$("input[name='price']:checked").val();
			if ( typeof(packagePrice)=="undefined" ) {
				Alert("请至少选择一个套餐购买");
				return false;
			}
			var chk_value=""; 
			$('input[name="price"]:checked').each(function(){ 
			chk_value = chk_value+($(this).val())+","; 
			}); 
			window.location="pointBuy/pointBuyConfirm.do?price="+chk_value;
		}
        
    </script>
    
  </body>
</html>