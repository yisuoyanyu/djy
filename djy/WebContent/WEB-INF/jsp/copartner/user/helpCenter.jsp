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
<title>帮助中心</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport"
	content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
</head>
<body style="background-color: #edf3fe;">

	<header data-am-widget="header"
		class="am-header am-header-fixed am-header-default"
		style="background-color: #3a88ef;">
		<div class="am-header-left am-header-nav">
			<span style="color: white;">疑难解答</span>
		</div>
	</header>

	<div class="am-container" style="margin-top: 10px;">
		<!--一-->

         <c:forEach var="i" items="${faqs}" varStatus="status">
         <div class="am-panel am-panel-default mybox ">
			<div class="am-panel-hd"
				style="background-color: #3a88ef; color: #fff;">
				<i class="iconfont icon-wen"></i> <span style="font-size: 5vw;">${i.question}</span>
				<i name="tou" class="iconfont icon-xiala" style="float: right"></i>
			</div>
			<section data-am-widget="accordion"
				class="am-accordion am-accordion-basic" style="margin: 0.5rem;"
				data-am-accordion='{}'>

				<dl class="am-accordion-item">
					<table>
						<tbody>
							<tr>
								<td style="vertical-align: top;"><i
									class="iconfont icon-da helpcenter-icon"></i></td>
								<td class="helpcenter_answer_div">
									<div class="helpcenter_answerspan_div"></div> 
									<span>${i.answer}</span>
									<div class="helpcenter_answerspan_div"></div>
								</td>
							</tr>
						</tbody>
					</table>
				</dl>

			</section>
		</div>
         </c:forEach>

	</div>
	<script type="text/javascript">
		$('.mybox').find('section').hide();//第一步隐藏所有要显示的答案问题

		$('.am-panel-hd').click(function() {
			$('.mybox').find('section').hide();

			$("[name='tou']").removeClass("icon-shangla");
			$("[name='tou']").addClass("icon-xiala");

			$(this).parent().find('section').toggle();
			$(this).parent().find('.icon-xiala').removeClass("icon-xiala");
			$(this).parent().find('.iconfont').addClass("icon-shangla");
		});
	</script>

</body>
</html>