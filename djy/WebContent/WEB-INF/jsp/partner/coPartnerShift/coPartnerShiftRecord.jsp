<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>合作商家交接班信息</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	<%@include file="../decorators/listViewHead.jsp"%>
	
</head>

<body class="gray-bg">
	
	<!-- 表单顶部操作条 -->
	<div class="formHead">
		<nav class="navbar" role="navigation">
			
			<div class="navbar-header">
				<span>操作：</span>
			</div>
			
			<div class="navbar-collapse collapse">
				
				<ul class="nav navbar-right">
					<li>
						<a id="btn_refresh" href="javascript:;">
							<i class="glyphicon glyphicon-repeat"></i>
							刷新
						</a>
					</li>
				</ul>
				
			</div>
			
		</nav>
	</div>
	
	<!-- 表单内容 -->
	<div class="formBody">
	
		<div class="wrapper wrapper-content">
		
			<div class="ibox float-e-margins">
				
				<div class="ibox-title">
					<h5>
						${coPartner.name }
					</h5>
					
					<div class="ibox-tools">
						<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
					</div>
				</div>
				
				<div class="ibox-content">
					
					<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
						
						<% /*--这个hidden的input需要输出到浏览器端--*/ %>
						<input type="hidden" id="id" name="id" value="${coPartner.id }" />
						<div class="tabs-container">
							<ul class="nav nav-tabs">
								<li class="active"><a data-toggle="tab" href="#tab_basic" aria-expanded="true">基础信息</a></li>
								<li class=""><a data-toggle="tab" href="#tab_consumeOrder" aria-expanded="false">会员消费</a></li>
								<c:if test="${ not empty sysDeposit}">
									<li class=""><a data-toggle="tab" href="#tab_coSysDepositOrder" aria-expanded="false">平台预存</a></li>
									<li class=""><a data-toggle="tab" href="#tab_coSysDepositLog" aria-expanded="false">预存额明细</a></li>
								</c:if>
								
							</ul>
							<div class="tab-content">
								
								<div id="tab_basic" class="tab-pane active">
									<div class="panel-body">
<div class="container-fluid">
	
	<div class="row">
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="startTime">开始时间：</label>
			<div class="col-sm-8">
				<p class="form-control-static"><fmt:formatDate value="${ coPartnerShift.startTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></p>
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="endTime">结束时间：</label>
			<div class="col-sm-8">
				<p class="form-control-static"><fmt:formatDate value="${ coPartnerShift.endTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></p>
			</div>
		</div>
	</div>
	
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="totalCstmConsume">会员消费总额：</label>
			<div class="col-sm-8">
				<p class="form-control-static">
              		<fmt:formatNumber value="${ coPartnerShift.totalCstmConsume }" type="number" maxFractionDigits="2" /> （元）
            	</p>
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="totalCstmPay">会员实付总额：</label>
			<div class="col-sm-8">
				<p class="form-control-static">
              		<fmt:formatNumber value="${ coPartnerShift.totalCstmPay }" type="number" maxFractionDigits="2" /> （元）
            	</p>
			</div>
		</div>
		
	</div>
	
	<c:if test="${ not empty sysDeposit}">
		<div class="row">
			
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="totalSysDeposit">平台预存总额：</label>
				<div class="col-sm-8">
					<p class="form-control-static">
	              		<fmt:formatNumber value="${ coPartnerShift.totalSysDeposit }" type="number" maxFractionDigits="2" /> （元）
	            	</p>
				</div>
			</div>
			
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="totalSysDepositPay">预存实收总额：</label>
				<div class="col-sm-8">
					<p class="form-control-static">
	              		<fmt:formatNumber value="${ coPartnerShift.totalSysDepositPay }" type="number" maxFractionDigits="2" /> （元）
	            	</p>
				</div>
			</div>
			
		</div>
		<div class="row">
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="startSysDeposit">期初平台预存额：</label>
				<div class="col-sm-8">
					<p class="form-control-static">
	              		<fmt:formatNumber value="${ coPartnerShift.startSysDeposit }" type="number" maxFractionDigits="2" /> （元）
	            	</p>
				</div>
			</div>
			
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="endSysDeposit">期末平台预存额：</label>
				<div class="col-sm-8">
					<p class="form-control-static">
	              		<fmt:formatNumber value="${ coPartnerShift.endSysDeposit }" type="number" maxFractionDigits="2" /> （元）
	            	</p>
				</div>
			</div>
		</div>	
	</c:if>
		
	<c:if test="${ empty sysDeposit }">
		<div class="row">
			
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="totalCstmSettle">平台结算总额：</label>
				<div class="col-sm-8">
					<p class="form-control-static">
	              		<fmt:formatNumber value="${ coPartnerShift.totalCstmSettle }" type="number" maxFractionDigits="2" /> （元）
	            	</p>
				</div>
			</div>
			
		</div>	
	</c:if>
	
</div>
									</div>
								</div>
								
								<div id="tab_consumeOrder" class="tab-pane">
                            		<div class="panel-body">
                            		
<div id="toolbarConsumeOrder" class="btn-group" role="group">
	<h4>
		<a href="consumeOrder/exportConsumeOrderExcel.do?coPartnerShiftId=${coPartnerShift.id}"><input type="button" class="btn btn-primary"  value="导出excel"/></a>
	</h4>
</div>
                            	
 <table id="listViewConsumeOrder" 
       data-toolbar="#toolbarConsumeOrder" 
       data-url="consumeOrder/consumeOrderListData.json?coPartnerShiftId=${coPartnerShift.id}" 
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
		<th data-field="no" data-width="25%">订单编号</th>
		<th data-field="nickname" data-width="25%">会员微信昵称</th>
		<th data-field="mobile" data-width="15%">会员手机</th>
		<th data-field="consumeAmount" data-width="10%">消费金额（元）</th>
		<th data-field="payAmount" data-width="10%">实付金额（元）</th>
		<th data-field="insertTime" data-sortable="true" data-formatter="fmtDate" data-width="15%">消费时间</th>
	</thead>
</table>
<script type="text/javascript">
    
    // 格式化时间列
    var fmtDate = function(value) {
        return $.isNumeric(value) ? (new Date(value)).format('yyyy-MM-dd HH:mm:ss') : '';
    }
    
</script>
<script type="text/javascript">
	
	var oListViewConsumeOrder;
	
	function initListViewConsumeOrder() {
	    
	    if ( $.isNull( oListViewConsumeOrder ) ) {
	        
	    	oListViewConsumeOrder = new ListView({
	            'id' : 'listViewConsumeOrder', 
	            'rowAttributes' : function(row, index) {
					return;
				}
	        });
	    }
	    
	}
        
</script>
								</div>
							</div>
							
							<div id="tab_coSysDepositOrder" class="tab-pane">
                            		<div class="panel-body">
 <div id="toolbarCoSysDepositOrder" class="btn-group" role="group">
	<h4>
		<a href="coSysDepositOrder/exportCoSysDepositOrderExcel.do?coPartnerShiftId=${coPartnerShift.id}"><input type="button" class="btn btn-primary"  value="导出excel"/></a>
	</h4>
 </div>                           	
 <table id="listViewCoSysDepositOrder" 
       data-toolbar="#toolbarCoSysDepositOrder" 
       data-url="coSysDepositOrder/coSysDepositOrderListData.json?coPartnerShiftId=${coPartnerShift.id}"
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
		<th data-field="no" data-width="20%">订单编号</th>
		<th data-field="amount" data-width="20%">预存金额（元）</th>
		<th data-field="payAmount" data-width="20%">支付金额（元）</th>
		<th data-field="statusName"  data-width="20%">状态</th>
		<th data-field="insertTime" data-sortable="true" data-formatter="fmtDate" data-width="20%">预存时间</th>
	</thead>
</table>
<script type="text/javascript">
    
    // 格式化时间列
    var fmtDate = function(value) {
        return $.isNumeric(value) ? (new Date(value)).format('yyyy-MM-dd HH:mm:ss') : '';
    }
    
</script>
<script type="text/javascript">
	
	var oListViewCoSysDepositOrder;
	
	function initListViewCoSysDepositOrder() {
	    
	    if ( $.isNull( oListViewCoSysDepositOrder ) ) {
	        
	    	oListViewCoSysDepositOrder = new ListView({
	            'id' : 'listViewCoSysDepositOrder', 
	            'rowAttributes' : function(row, index) {
					return;
				}
	        });
	    }
	    
	}
        
</script>
								</div>
							</div>
								
								<div id="tab_coSysDepositLog" class="tab-pane">
                            		<div class="panel-body">
 <div id="toolbarCoSysDepositLog" class="btn-group" role="group">
	<h4>
		<a href="coSysDepositLog/exportCoSysDepositLogExcel.do?coPartnerShiftId=${coPartnerShift.id}"><input type="button" class="btn btn-primary"  value="导出excel"/></a>
	</h4>
 </div>                            	
 <table id="listViewCoSysDepositLog" 
       data-toolbar="#toolbarCoSysDepositLog" 
       data-url="coSysDepositLog/coSysDepositLogListData.json?coPartnerShiftId=${coPartnerShift.id}" 
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
		<th data-field="no" data-width="25%">订单编号</th>
		<th data-field="typeName" data-width="15%">业务类型</th>
		<th data-field="incomeExpenseAmount" data-width="25%">金额（元）</th>
		<th data-field="sysDeposit" data-width="20%">平台预存额（元）</th>
		<th data-field="insertTime" data-sortable="true" data-formatter="fmtDate" data-width="15%">登记时间</th>
	</thead>
</table>
<script type="text/javascript">
    
    // 格式化时间列
    var fmtDate = function(value) {
        return $.isNumeric(value) ? (new Date(value)).format('yyyy-MM-dd HH:mm:ss') : '';
    }
    
</script>
<script type="text/javascript">
	
	var oListViewCoSysDepositLog;
	
	function initListViewCoSysDepositLog() {
        
        if ( $.isNull( oListViewCoSysDepositLog ) ) {
            
        	oListViewCoSysDepositLog = new ListView({
                'id' : 'listViewCoSysDepositLog', 
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
    $('a[data-toggle="tab"]').on('shown.bs.tab',function(e){
        var target = $(e.target)[0].hash;
        if (target == '#tab_consumeOrder') {
        	initListViewConsumeOrder();
        } else if (target == '#tab_coSysDepositOrder') {
        	initListViewCoSysDepositOrder();
        } else if (target == '#tab_coSysDepositLog') {
        	initListViewCoSysDepositLog();
        } 
    });
</script>					
				</form>
					
				
			</div>
			
		</div>

	</div>

	</div>
			
<script type="text/javascript">

    $(function() {
        
        // 格式化单选框样式
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green', 
            radioClass: 'iradio_square-green'
        });
        
        
        $('#btn_refresh').click(function(){
            window.location.reload();
        });
    });
</script>

</body>
</html>