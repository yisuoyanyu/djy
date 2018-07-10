<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>积分赠送</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		  <style type="text/css">
			input::-ms-input-placeholder {
				text-align: left;
			}
			
			input::-webkit-input-placeholder {
				text-align: left;
			}
		  </style>
	</head>
	<body class="bg_main"> 
  <div class="bg_topmain"> 
   <div class="am-vertical-align" style="text-align:center"> 
    <div style="width:100%;padding-top:5vw"> 
     <span class="top_points_show">${user.userAccount.point}</span> 
    </div> 
    <div style="width:100%"> 
     <span class="white_span"> 当前可用积分 </span> 
    </div> 
    <img src="img/icon_jifen.png" class="meal_div_point_bg" /> 
   </div> 
   <img src="img/top-buytaocan.jpg" class="meal_div_top_img" /> 
  </div> 
  <div class="point_shalow_div"> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="point_give_phone"> 
     <span class="spantitle">手机号</span> 
     <input class="point_give_phone_input" type="tel" name="phone" id="phone" placeholder="请输入手机号" /> 
    </div> 
   </div> 
   <div class="lidiv" style="position:relative"> 
    <div class="point_give_phone"> 
     <span class="spantitle">赠送积分数</span> 
     <input class="point_give_numinput" type="number" name="point" id="point" onFocus="inputPointsChange();" placeholder="请输入赠送积分数" /> 
     <span class="point_give_allcheckbox"><input type="checkbox" name="giveAll" id="giveAll" value="" />全部兑换</span> 
    </div> 
   </div> 
  </div> 
  <div class="am-padding am-text-center" style="padding-top:15%;"> 
   <span class="button blue bigrounded" onclick="checkPhone(); this.disabled=true; return true;">　立 即 赠 送　</span> 
  </div> 
  
   <!-- 积分不足支付提示层 -->
		<div id="ID_PointNotEnough" class="am-modal" tabindex="-1">
			<div class="am-modal-dialog voucherPrompt" style="background-color:#f9faff;width:96%;border-radius:12px">
			<i class="closeout" data-am-modal-close></i>
			<div style="width:100%">
			<img src="../point/img/icon_defe.png" class="pointnotenough_top_img"/> 
			</div>
			<div style="width:100%">
			<span class="pointnotenough_top_span">尊敬的用户，实在抱歉，您的积分余额不足，您还需要<span id="notEnough">100</span>积分，为了保障你兑换成功，请您立即充值!</span>
			</div>
			<div class="pointnotenough_subdiv">
			  <span class="btn_border" style="width:50%;box-shadow:0 4px 15px #58a1ff;height:41px;line-height:35px;font-size:25px" onclick="gotoPackagesBuy();">立即充值</span> 
			</div>
			</div>
		</div>
  
  <script type="text/javascript">
  
  document.body.addEventListener('touchstart', function () {});
  
    $("#giveAll").change(function() { 
	  $("#point").val(null);
	  });
    
    function gotoPackagesBuy(){
    	window.location.href="pointBuy/packagesBuy.do";
    }
    
    function inputPointsChange(){
    $("[name='giveAll']").removeAttr("checked");//取消选中
    }
  
  
  function checkPhone() {
	if ( $("#phone").val() == "" || typeof($("#phone").val())=="undefined") {
			Alert("请您输入要赠送顾客的手机号!");
			return false;
	}else {
		if(!(/^1(3|4|5|7|8)\d{9}$/.test($("#phone").val()))){
			Alert("请输入正确格式的联系人手机号");
			return false;
		}
	}
	var giveAll=$("input[name='giveAll']:checked").val();
	  $.ajax({url: 'checkUserPhone.do', 
		  type: 'POST', 
		  data:{phone:$("#phone").val()}, 
		  dataType: 'json', 
		  timeout: 1000, 
		  error: function(){Alert('订货错误请联系管理员!'); return false;},
		  success: function(ret){
			  if(ret.success){
				  
					if ( ($("#point").val() == "" || typeof($("#point").val())=="undefined") && (typeof(giveAll)=="undefined")) {
						Alert("请您输入要赠送的积分数量或者勾选后面的全部兑换!")
						return false;
				    }else {
					if(${user.userAccount.point} < $("#point").val()){
						$('#notEnough').html($("#point").val()-${user.userAccount.point});
						$('#ID_PointNotEnough').modal();
						return false;
					}
				}
					var phone = $("#phone").val();
					var point="";
					if ($("#point").val() != "" ) {
						point = $("#point").val();
					}
					if(typeof(giveAll)!="undefined"){
						point = ${user.userAccount.point};
					}
					
					window.location="pointTransfer/pointTransferConfirm.do?point="+$.trim(point)+"&phone="+phone;
					
			  }else {
				  Alert(ret.data);
				  return false;
			    }
			  } 
		  });
	}
  </script>
   
 </body>
</html>