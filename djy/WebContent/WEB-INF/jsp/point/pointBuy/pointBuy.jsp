<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>积分购买</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
	<script type="text/javascript">
           
        </script> 
 </head> 
 <body class="bg_main"> 
  <div class="bg_topmain"> 
   <div class="am-vertical-align" style="text-align:center"> 
    <div style="width:100%;padding-top:5vw"> 
     <span class="top_points_show">${user.userAccount.point}</span> 
    </div> 
    <div style="width:100%"> 
     <span class="top_points_show"> 当前可用积分 </span> 
    </div> 
    <img src="img/icon_jifen.png" class="meal_div_point_bg" /> 
   </div> 
   <img src="img/top-buytaocan.jpg" class="meal_div_point_bottom" /> 
  </div> 
  <div class="main_center_div"> 
   <span style="color:#476df6">●选择购买积分</span> 
   <div class="ico_btn_bar am-padding-top-sm"> 
    <ul class="am-avg-sm" style="list-style-type:none;  display:inline;" id="ul"> 
     <li onclick="change(this);" id="0" value="10" class="meal_type_div" style="margin-right:10%;margin-bottom:20px"> 10 </li> 
     <li onclick="change(this);" id="1" value="50" class="meal_type_div" style="margin-right:7%;margin-bottom:20px"> 50 </li> 
     <li onclick="change(this);" id="2" value="100" class="meal_type_div" style="margin-bottom:20px;float:right"> 100 </li> 
     <li onclick="change(this);" id="3" value="500" class="meal_type_div" style="margin-right:10%;margin-bottom:20px"> 500 </li> 
     <li onclick="change(this);" id="4" value="1000" class="meal_type_div" style="margin-right:7%;margin-bottom:20px;"> 1000 </li> 
     <li onclick="change(this);" id="5" value="5000" class="meal_type_div" style="margin-bottom:20px;float:right"> 5000 </li> 
    </ul> 
    <span style="color:#476df6">●其他积分</span> 
    <div class="enter_points_div"> 
     <input type="tel" name="tbprice" id="tbprice" placeholder="请输入大于等于1的整数" class="enter_points_div_input" onFocus="inputPriceChange();" onblur="inputPrice(this);" /> 
	</div> 
	<div class="remark_font_div1">
	 <font color="#ffab0a">备注：一积分相当于一元</font>
	</div>
    <div class="am-padding am-text-center" style="padding-top:15%;"> 
     <span class="button blue bigrounded" onclick="submit(); this.disabled=true; return true;">　立 即 购 买　</span> 
    </div> 
   </div> 
  </div> 
  <script type="text/javascript">
  document.body.addEventListener('touchstart', function () {}); 
  var price = "";
	  function inputPrice(th) {
	  if(th.value%1 !=''  ||th.value < 1 ){ 
		  Alert('必须是大于等于1的整数');th.value=100 
		}
	}
  
	  function inputPriceChange() {
		  var arr = document.getElementById("ul").getElementsByTagName("li");
	      for (var i = 0; i < arr.length; i++) {
	          var a = arr[i];
				 a.style.background = "none";
				a.style.color = "#476df6";
	      };
		}
  function change(obj){
		var jud = false;
		if (obj.style.color == "white"){
		jud = true;
		}
		if (jud){
			  obj.style.background = "none";
				obj.style.color = "#476df6";
				price="";
			}
			if (!jud){
			obj.style.background = "#476df6";
			obj.style.color = "white";
			$("#tbprice").val(null);
			price = obj.value;
			}
			
          var arr = document.getElementById("ul").getElementsByTagName("li");
          for (var i = 0; i < arr.length; i++) {
              var a = arr[i];
				if(i!=obj.id){
				 a.style.background = "none";
				a.style.color = "#476df6";
				}
             
          };
			
      }
	  
	   function submit() {
     if ( $("#tbprice").val() != "" && typeof($("#tbprice").val())!="undefined") {
				price = $("#tbprice").val()
		}
	  if (price == "" || typeof(price)=="undefined") {
		Alert("请您至少选择或者输入一个积分值！")
		return false;
	}
	  window.location="pointBuy/pointCostBuyConfirm.do?price="+$.trim(price);
  }
  </script> 
 </body>
</html>