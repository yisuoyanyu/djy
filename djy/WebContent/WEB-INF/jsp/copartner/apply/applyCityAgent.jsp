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
<title>城市代理商申请</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<script type="text/javascript" src="frame/js/LArea.js"></script>
<script type="text/javascript" src="frame/js/LAreaData1.js"></script>
<link rel="stylesheet" type="text/css" href="frame/css/LArea.css" />
<style>
	.am-titlebar-default .am-titlebar-title:before {border-left: 4px solid #3a88ef;}
	.am-selected {width: 64vw;}
	</style>
</head>
  
  <body class="bg_white">
    <!-- 顶部图片 -->
    <div class="am-slider am-slider-a1" data-am-flexslider="{playAfterPaused: 8000}" style="-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0);">
      <ul class="am-slides">
        <li>
          <img src="../copartner/img/joinin1banner.png"></li>
      </ul>
    </div>
    <div data-am-widget="titlebar" class="am-titlebar am-titlebar-default">
      <h2 class="am-titlebar-title " style="color:#3a88ed;font-weight:bolder;">城市代理商在线申请表</h2></div>
    
	<form class="am-form am-form-horizontal" id="applyCityAgentForm" method="post" enctype="multipart/form-data">
	
    <div class="am-g" style="padding-bottom:1rem;">
      <div class="am-u-sm-6" style="text-align:center;">
        <label><input name="type" checked="checked" type="radio" value="1" />市级代理 </label> 
      </div>
      <div class="am-u-sm-6" style="text-align:center;">
      <label><input name="type" type="radio" value="2" />地方代理 </label> 
      </div>
    </div>
	
  <!-- am-form-group 的基础上添加了 am-form-group-sm -->
  <div class="am-form-group am-form-group-sm" style="margin-bottom:1.5rem;">
    <label for="doc-ipt-3-1" class="am-u-sm-4 am-form-label join-formlabel" style="text-align:left;">意向城市</label>
    <div class="am-u-sm-8" style="padding-left:0rem;">
  <input type="text" id="city" name="city" class="am-form-field" style="width:64vw;" placeholder="选择意向城市">
  <input id="area1" name="area1" type="hidden" />
    </div>
  </div>
  
    <div class="am-form-group am-form-group-sm" style="margin-bottom:1.5rem;">
    <label for="doc-ipt-3-1" class="am-u-sm-4 am-form-label join-formlabel" style="text-align:left;">负责人姓名</label>
    <div class="am-u-sm-8" style="padding-left:0rem;">
     <input type="text" name="contact" id="contact" class="am-form-field" style="width:64vw;" placeholder="输入负责人姓名">
    </div>
  </div>
  
      <div class="am-form-group am-form-group-sm" style="margin-bottom:1.5rem;">
    <label for="doc-ipt-3-1" class="am-u-sm-4 am-form-label join-formlabel" style="text-align:left;">联系人电话</label>
    <div class="am-u-sm-8" style="padding-left:0rem;">
      <input type="phone" name="contactMobile" id="contactMobile" class="am-form-field" style="width:64vw;" placeholder="输入联系人电话">
    </div>
  </div>
  
    <div class="am-form-group am-form-group-sm" style="margin-bottom:1.5rem;">
    <label for="doc-ipt-3-1" class="am-u-sm-4 am-form-label join-formlabel" style="text-align:left;">常用邮箱</label>
    <div class="am-u-sm-8" style="padding-left:0rem;">
      <input type="email" name="contactMail" id="contactMail" class="am-form-field" style="width:64vw;" placeholder="输入常用邮箱">
    </div>
  </div>
  

  <div class="am-form-group am-form-group-sm" style="margin-bottom:0rem;">
    <div class="am-u-sm-12">
      <div class="am-checkbox">
        <span style="color:#9a9a9a;">以上信息都为必填项，为了及时通过审核，请您如实填写，油惠站绝对不会把您的信息透漏给第三方。</span>
      </div>
    </div>
  </div>

  <div class="am-form-group" style="padding-top:1em;">
    <div class="am-u-sm-12" style="text-align:center;">
       <span class="btn_border join-spanbtn"  onclick="submit(); this.disabled=true; return true;">　立即申请　</span>
    </div>
  </div>
</form>
	<script>
		var area1 = new LArea();
		area1.init({
			'trigger' : '#city',
			//触发选择控件的文本框，同时选择完毕后name属性输出到该位置
			'valueTo' : '#area1',
			//选择完毕后id属性输出到该位置
			'keys' : {
				id : 'id',
				name : 'name'
			},
			//绑定数据源相关字段 id对应valueTo的value属性输出 name对应trigger的value属性输出
			'type' : 1,
			//数据源类型
			'data' : LAreaData
		//数据源
		});
		area1.value = [ 0, 0, 0 ]; //控制初始位置，注意：该方法并不会影响到input的value
	</script>
	
	<script type="text/javascript">	
		function checkSubmit() {
			
			if ( $("#city").val() == "" ) {
				$("#city").attr("placeholder", "必填");
				$.alert("所在城市不能为空！");
				return false;
			}
			
			if ($("#contact").val() == "") {
				$("#contact").attr("placeholder", "必填");
				$.alert("负责人不能为空！");
				return false;
			}
			
			if ($("#contactMobile").val() == "") {
				$("#contactMobile").attr("placeholder", "必填");
				$.alert("联系人手机不能为空！");
				return false;
			} else {
				if ( !(/^1(3|4|5|7|8)\d{9}$/.test($("#contactMobile").val())) ) { 
					$.alert("请输入正确格式的联系人手机号");
					return false;
				}
			}
			
			if ($("#contactMail").val() == "") {
				$("#contactMail").attr("placeholder", "必填");
				$.alert("请输入常用邮箱！");
				return false;
			} 
			
			return true;
		}
		
		function submit(){
			if ( !checkSubmit() )
				return false;
			$.post(
				"apply/saveApplyCityAgent.do", 
				$('#applyCityAgentForm').serializeArray(),
				function(ret){
					if(ret.success){
						$.alert('提交成功');
						window.history.go(-1);
					} else {
						$.alert(ret.data);
					}
				}
			);
		}
		</script>
  </body>

</html>