<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>我的手机号</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		 <style type="text/css">
			input::-ms-input-placeholder {
				text-align: left;
				color:black;
			}
			
			input::-webkit-input-placeholder {
				text-align: left;
				color:black;
			}
		 </style> 
	</head>
	<body class="bg_main"> 
	<div class="tip_act_bar">
			<ul>
				<li>
					<a href="javascript:history.back(-1);"><i class="back"></i></a>
				</li>
			</ul>
	</div>
    <header data-am-widget="header" class="am-header am-header-fixed am-header-default am-no-layout" style="background-color: #ffffff; border-bottom: 1px solid #dddddd;">
			<div class="am-header-left am-header-nav">
				<a href="javascript:history.back(-1);">
					<i class="am-header-icon am-icon-angle-left am-icon-sm" style="color: black;"></i>
				</a>
			</div>
			<h1 class="am-header-title">
				<a href="javascript:void(0);" style="color: black;">我的手机</a>
			</h1>
		</header> 
  <div class="point_shalow_div"> 
   <div class="myphonenum_maindiv">
    <span class="bus_mian_topspan">*如果您要更换手机号码,点击下方&quot;更换手机号码&quot;,输入手机号即可!</span>
   </div> 
   <div class="myphonenumdiv" style="text-align:center"> 
    <font color="#476df6" style="font-size:9vw;font-family:Microsoft YaHei;font-weight:bold">${user.mobile}</font> 
   </div> 
   <div class="full_ccenter_div"> 
    <a href="javascript:location.replace('bindPhone/bindPhone.do');"><span class="button blue bigrounded" style="margin-top:15vw;margin-bottom:40vw">更换手机号</span></a>
   </div> 
  </div>  
  <script type="text/javascript">
  document.body.addEventListener('touchstart', function () {});
  </script>
 </body>
</html>