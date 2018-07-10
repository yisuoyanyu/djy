<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
<head>
<title>油站加盟申请</title>

<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width" />

<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />

<link rel="stylesheet" type="text/css" href="frame/css/LArea.css" />

<script src="frame/js/LAreaData1.js"></script>
<script src="frame/js/LArea.js"></script>

<style>
	.am-titlebar-default .am-titlebar-title:before {border-left: 4px solid blue;}
	.am-selected {width: 64vw;}
</style>
</head>
<body style="background-color: #fff;">
    <div data-am-widget="titlebar" class="am-titlebar am-titlebar-default">
      <h2 class="am-titlebar-title " style="color:#3a88ed;font-weight:bolder;">加油站在线申请</h2></div>
    
	<form class="am-form am-form-horizontal" id="applyCityAgentForm" method="post" enctype="multipart/form-data">
	
   <div class="am-form-group am-form-group-sm" style="margin-bottom:1.5rem;">
    <label for="doc-ipt-3-1" class="am-u-sm-4 am-form-label join-formlabel" style="text-align:left;">加油站名称</label>
    <div class="am-u-sm-8" style="padding-left:0rem;">
      <input type="text"  class="am-form-field" name="name" id="name" style="width:64vw;" placeholder="输入加油站名称">
    </div>
  </div>
  
      <div class="am-form-group am-form-group-sm" style="margin-bottom:1.5rem;">
    <label for="doc-ipt-3-1" class="am-u-sm-4 am-form-label join-formlabel" style="text-align:left;">所在地区</label>
    <div class="am-u-sm-8" style="padding-left:0rem;">
      <input type="text"  class="am-form-field" id="area" name="area"  style="width:64vw;" placeholder="选择所在地区">
    </div>
     <input id="city" type="hidden" />
  </div>
  
     <div class="am-form-group am-form-group-sm" style="margin-bottom:1.5rem;">
    <label for="doc-ipt-3-1" class="am-u-sm-4 am-form-label join-formlabel" style="text-align:left;">地址</label>
    <div class="am-u-sm-8" style="padding-left:0rem;">
      <input type="text"  class="am-form-field"  name="address" id="address"  style="width:64vw;" placeholder="输入加油站详细地址">
    </div>
  </div>
  
   <div class="am-form-group am-form-group-sm" style="margin-bottom:1.5rem;">
    <label for="doc-ipt-3-1" class="am-u-sm-4 am-form-label join-formlabel" style="text-align:left;">联系人</label>
    <div class="am-u-sm-8" style="padding-left:0rem;">
      <input type="text"  class="am-form-field"  name="contact" id="contact"  style="width:64vw;" placeholder="输入联系人姓名">
    </div>
  </div>
  
   <div class="am-form-group am-form-group-sm" style="margin-bottom:1.5rem;">
    <label for="doc-ipt-3-1" class="am-u-sm-4 am-form-label join-formlabel" style="text-align:left;">联系电话</label>
    <div class="am-u-sm-8" style="padding-left:0rem;">
      <input type="phone"  class="am-form-field"  name="telephone" id="telephone"  style="width:64vw;" placeholder="输入联系人电话">
    </div>
  </div>
       

  <div class="am-form-group am-form-group-sm" style="margin-bottom:0rem;">
    <div class="am-u-sm-12">
      <div class="am-checkbox">
        <span style="color:#9a9a9a;">以上信息都为必填项，为了及时通过审核，请您如实填写，中保汇绝对不会把您的信息透漏给第三方。</span>
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
        'trigger': '#area', //触发选择控件的文本框，同时选择完毕后name属性输出到该位置
        'valueTo': '#city', //选择完毕后id属性输出到该位置
        'keys': {
            id: 'id',
            name: 'name'
        }, //绑定数据源相关字段 id对应valueTo的value属性输出 name对应trigger的value属性输出
        'type': 1, //数据源类型
        'data': LAreaData //数据源
    });
    area1.value=[0,0,0];
    </script>
    
    <script type="text/javascript">	
		function checkSubmit() {
			
			if ( $("#name").val() == "" ) {
				$("#name").attr("placeholder", "必填");
				$.alert("加油站名称不能为空！");
				return false;
			}
			
			if ($("#area").val() == "") {
				$("#area").attr("placeholder", "必填");
				$.alert("加油站所在区域不能为空！");
				return false;
			}
			
			if ($("#telephone").val() == "") {
				$("#telephone").attr("placeholder", "必填");
				$.alert("联系人手机不能为空！");
				return false;
			} else {
				if ( !(/^1(3|4|5|7|8)\d{9}$/.test($("#telephone").val())) ) { 
					$.alert("请输入正确格式的联系人手机号");
					return false;
				}
			}
			
			if ($("#contact").val() == "") {
				$("#contact").attr("placeholder", "必填");
				$.alert("加油站联系人不能为空！");
				return false;
			} 
			
			if ($("#address").val() == "") {
				$("#address").attr("placeholder", "必填");
				$.alert("加油站详细地址不能为空！");
				return false;
			} 
			
			return true;
		}
		
		function submit(){
			if ( !checkSubmit() )
				return false;
			$.post(
				"coPartnerApply/saveApplyCopartner.do", 
				$('#applyCityAgentForm').serializeArray(),
				function(ret){
					if(ret.success){
						Alert({
							msg : "提交成功",
							ok : function() {
								window.location.replace("coPartnerApply/coApplyList.do");
							}
						});
					} else {
						Alert(ret.data);
					}
				}
			);
		}
		</script>

  </body>

</html>