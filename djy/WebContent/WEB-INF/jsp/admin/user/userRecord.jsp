<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>用户信息详情</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	
	
	<!-- Bootstrap Table -->
	<link href="plugins/bootstrap-table/bootstrap-table.min.css?v=1.11.1" rel="stylesheet">
	<script src="plugins/bootstrap-table/bootstrap-table.min.js?v=1.11.1" type="text/javascript"></script>
	<script src="plugins/bootstrap-table/extensions/mobile/bootstrap-table-mobile.min.js?v=1.1.0" type="text/javascript"></script>
	<script src="plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js" type="text/javascript"></script>
	
	<script src="js/listView.js" type="text/javascript"></script>
	
	
	<!-- jsTree -->
	<link href="plugins/jsTree/themes/default/style.min.css?v=3.3.3" rel="stylesheet">
	<script src="plugins/jsTree/jstree.min.js?v=3.3.3" type="text/javascript"></script>
	
	<script src="js/treeView.js" type="text/javascript"></script>
	
	
</head>

<body class="gray-bg">

	
	<!-- 表单内容 -->
	<div class="formBody">
	
		<div class="wrapper wrapper-content">
		
			<div class="ibox float-e-margins">
				
				<div class="ibox-title">
					<h5>
						用户信息详情
					</h5>
					
					<div class="ibox-tools">
						<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
					</div>
				</div>
				
				<div class="ibox-content">
					
					<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
						
						<!-- 这个hidden的input需要输出到浏览器端 -->
						<input type="hidden" id="id" name="id" value="${user.id }" />
						
						<div class="tabs-container">
							<ul class="nav nav-tabs">
								<li class="active"><a data-toggle="tab" href="#tab-basic" aria-expanded="true">基础信息</a></li>
								<li class=""><a data-toggle="tab" href="#tab-Coupon" aria-expanded="false">打折券</a></li>
								<li class=""><a data-toggle="tab" href="#tab-SiteMsg" aria-expanded="false">站内消息</a></li>
								<li class=""><a data-toggle="tab" href="#tab-SmsMsg" aria-expanded="false">短信消息</a></li>
								<li class=""><a data-toggle="tab" href="#tab-FirstConsumeUser" aria-expanded="false">首次消费会员</a></li>
							</ul>
							<div class="tab-content">
								
								<div id="tab-basic" class="tab-pane active">
									<div class="panel-body">	
<div class="container-fluid">

	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="username">昵称</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<c:if test="${ empty user.wechatUser.nickname }">
						无
					</c:if>
					<c:if test="${not empty user.wechatUser.nickname }">
						${ user.wechatUser.nickname }
					</c:if>
				</p>
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="mobile">手机</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<c:if test="${ empty user.mobile }">
						无
					</c:if>
					<c:if test="${not empty user.mobile }">
						${user.mobile }
					</c:if>
				</p>
			</div>
		</div>
		
	</div>
	
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="type">类型</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					普通用户
				</p>
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="statusName">状态</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<c:if test="${ empty statusName }">
						无
					</c:if>
					<c:if test="${not empty statusName }">
						${statusName }
					</c:if>
				</p>
			</div>
		</div>
		
	</div>
	

</div>
									
									</div>
								</div>
								
								<div id="tab-Coupon" class="tab-pane">
                            	<div class="panel-body">
<table id="listViewCoupon" 
       data-toolbar="#toolbar" 
       data-url="couponDiscount/couponDiscountListData.json?userId=${user.id}" 
       data-id-field="id" 
       data-unique-id="id" 
       data-sort-name="id" 
       data-sort-order="desc" 
       data-sortable="true" 
       data-search="true" 
       data-strict-search="true" 
       data-show-refresh="true" 
       data-show-toggle="true" 
       data-show-columns="true" 
       data-pagination="true" 
       data-page-size="10" 
       data-side-pagination="server">
	<thead>
		<th data-field="title" data-width="20%">标题</th>
		<th data-field="no" data-width="15%">券号</th>
		<th data-field="coPartnerName" data-width="25%">商家名称</th>
		<th data-field="discountPercent" data-width="15%">折扣百分比</th>
		<th data-field="useMinConsume" data-width="15%">最低消费金额（元）</th>
		<th data-field="statusName" data-width="10%">状态</th>
	</thead>
</table>
<script type="text/javascript">
    
    var oListViewCoupon;
    
	function initListViewCoupon() {
        
        if ( $.isNull( oListViewCoupon ) ) {
            
        	oListViewCoupon = new ListView({
                'id' : 'listViewCoupon', 
                'rowAttributes' : function(row, index) {
    				// 设置鼠标移到行上显示为手型
    				return {
    					'style' : 'cursor:pointer;'
    				};
    			},
    			'onClickRow' : function(row, $element, field) {       // 行点击事件
    				if ( field != 0 ) {
    					openWin({
    				        'title' : '折扣券详情',
    				        'target' : 'menuTab',
    				        'url' : 'couponDiscount/couponDiscountRecord.do?id=' + row.id
    				    });
    				}
    			}
            });
        }
        
    }
    
    
</script>
								</div>
							</div>
							
								<div id="tab-SiteMsg" class="tab-pane">
                            		<div class="panel-body">
<table id="listViewSiteMsg" 
       data-toolbar="#toolbarSiteMsg" 
       data-url="userSiteMsg/userSiteMsgListData.json?userId=${user.id}" 
       data-id-field="id" 
       data-unique-id="id" 
       data-sort-name="id" 
       data-sort-order="desc" 
       data-sortable="true" 
       data-search="true" 
       data-strict-search="true" 
       data-show-refresh="true" 
       data-show-toggle="true" 
       data-show-columns="true" 
       data-pagination="true" 
       data-page-size="10" 
       data-side-pagination="server">
	<thead>
		<th data-field="typeName" data-width="15%">类型</th>
		<th data-field="content" data-width="55%">内容</th>
		<th data-field="insertTime" data-formatter="fmtDate" data-width="15%">插入时间</th>
		<th data-field="readTime" data-sortable="true" data-formatter="fmtDate" data-width="15%">阅读时间</th>
	</thead>
</table>
<script type="text/javascript">
    
    var oListViewSiteMsg;
    
	function initListViewSiteMsg() {
        
        if ( $.isNull( oListViewSiteMsg ) ) {
            
        	 oListViewSiteMsg = new ListView({
                 'id' : 'listViewSiteMsg', 
                 'rowAttributes' : function(row, index) {
     				// 设置鼠标移到行上显示为手型
     				return {
     					'style' : 'cursor:pointer;'
     				};
     			},
     			'onClickRow' : function(row, $element, field) {       // 行点击事件
     				if ( field != 0 ) {
     					openWin({
     				        'title' : '站内消息详情',
     				        'target' : 'menuTab',
     				        'url' : 'userSiteMsg/userSiteMsgRecord.do?id=' + row.id
     				    });
     				}
     			}
             });
        }
        
    }
    
    
</script>
								</div>
							</div>
							
								<div id="tab-SmsMsg" class="tab-pane">
                            		<div class="panel-body">
<table id="listViewSmsMsg" 
       data-toolbar="#toolbarSmsMsg" 
       data-url="userSmsMsg/userSmsMsgListData.json?userId=${user.id}" 
       data-id-field="id" 
       data-unique-id="id" 
       data-sort-name="id" 
       data-sort-order="desc" 
       data-sortable="true" 
       data-search="true" 
       data-strict-search="true" 
       data-show-refresh="true" 
       data-show-toggle="true" 
       data-show-columns="true" 
       data-pagination="true" 
       data-page-size="10" 
       data-side-pagination="server">
	<thead>
		<th data-field="mobile" data-width="15%">手机号</th>
		<th data-field="content" data-width="55%">内容</th>
		<th data-field="insertTime" data-formatter="fmtDate" data-width="15%">插入时间</th>
		<th data-field="sendTime" data-sortable="true" data-formatter="fmtDate" data-width="15%">发送时间</th>
	</thead>
</table>
<script type="text/javascript">
    
    var oListViewSmsMsg;
    
	function initListViewSmsMsg() {
        
        if ( $.isNull( oListViewSmsMsg ) ) {
            
        	oListViewSmsMsg = new ListView({
                'id' : 'listViewSmsMsg', 
                'rowAttributes' : function(row, index) {
    				// 设置鼠标移到行上显示为手型
    				return {
    					'style' : 'cursor:pointer;'
    				};
    			},
    			'onClickRow' : function(row, $element, field) {       // 行点击事件
    				if ( field != 0 ) {
    					openWin({
    				        'title' : '短信消息详情',
    				        'target' : 'menuTab',
    				        'url' : 'userSmsMsg/userSmsMsgRecord.do?id=' + row.id
    				    });
    				}
    			}
            });
        }
        
    }
    
</script>
								</div>
							</div>
							
								<div id="tab-FirstConsumeUser" class="tab-pane">
                            		<div class="panel-body">
<table id="listViewFirstConsumeUser" 
       data-toolbar="#toolbarFirstConsumeUser"
       data-url="consumeOrder/firstConsumeUserListData.json?sponsorUserId=${user.id}" 
       data-id-field="id" 
       data-unique-id="id" 
       data-sort-name="id" 
       data-sort-order="desc" 
       data-sortable="true" 
       data-search="false" 
       data-strict-search="true" 
       data-show-refresh="true" 
       data-show-toggle="true" 
       data-show-columns="true" 
       data-pagination="true" 
       data-page-size="10" 
       data-side-pagination="server">
	<thead>
		<th data-field="nickname" data-width="25%">微信会员昵称</th>
		<th data-field="mobile" data-width="10%">会员电话</th>
		<th data-field="no" data-width="20%">订单编号</th>
		<th data-field="consumeAmount" data-width="15%">消费金额</th>
		<th data-field="payAmount" data-width="15%">支付金额</th>
		<th data-field="insertTime" data-sortable="true" data-formatter="fmtDate" data-width="15%">消费时间</th>
				
	</thead>
</table>
<script type="text/javascript">
    
    var oListViewFirstConsumeUser;
    
	function initListViewFirstConsumeUser() {
        
        if ( $.isNull( oListViewFirstConsumeUser ) ) {
            
        	oListViewFirstConsumeUser = new ListView({
                'id' : 'listViewFirstConsumeUser', 
                'rowAttributes' : function(row, index) {
    				return;
    			}
            });
        }
        
    }
    
</script>
								</div>
							</div>
								
								
							</div>
						</div>
						
<script type="text/javascript">
    
    // 格式化时间列
    var fmtDate = function(value) {
        return $.isNumeric(value) ? (new Date(value)).format('yyyy-MM-dd HH:mm:ss') : '';
    }
    
</script>
<script type="text/javascript">
    $('a[data-toggle="tab"]').on('shown.bs.tab',function(e){
        var target = $(e.target)[0].hash;
        if (target == '#tab-viComm') {
        	initListViewViComm();
        }
        if (target == '#tab-vipComm') {
        	initListViewVipComm();
        }
        if (target == '#tab-Coupon') {
        	initListViewCoupon();
        }
        if (target == '#tab-SiteMsg') {
        	initListViewSiteMsg();
        }
        if (target == '#tab-SmsMsg') {
        	initListViewSmsMsg();
        }
        if (target == '#tab-FirstConsumeUser') {
        	initListViewFirstConsumeUser();
        }
    });
</script>  		
					</form>
					
				</div>
				
			</div>
	
		</div>
	
	</div>
	
<script type="text/javascript">
	
	$('#btn_submit').click(function(){
		
		$.post(
			'user/assignUser.json', 
			$('#form').serializeArray(), 
			function(ret) {
				if (ret.success) {
					Alert({
						msg : '提交成功',
						ok : function() {
							closeForm({ refreshOpener : true });
						}
					});
				} else {
					Alert(ret.msg);
				}
			}
		);
		
	});
	
	$('#btn_cancel').click(function(){
		window.location.reload();
	});
		
</script>
	
</body>
</html>