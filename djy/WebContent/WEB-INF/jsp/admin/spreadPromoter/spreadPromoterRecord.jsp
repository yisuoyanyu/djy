<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>推广人信息</title>
	
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
				<ul class="nav navbar-nav">
					<li class="active">
						<a id="btn_submit" role="button" href="javascript:;">
							<i class="fa fa-save"></i>
							提交
						</a>
					</li>
				</ul>
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
						推广人信息
					</h5>
					
					<div class="ibox-tools">
						<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
					</div>
				</div>
				
				<div class="ibox-content">
					
					<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
						
						<input id="id" name="id" type="hidden" value="${ spreadPromoter.id }" />
						<div class="tabs-container">
							<ul class="nav nav-tabs">
								<li class="active"><a data-toggle="tab" href="#tab_basic" aria-expanded="true">基础信息</a></li>
								<c:if test="${not empty spreadPromoter.id }">
									<li class=""><a data-toggle="tab" href="#tab_commission" aria-expanded="false">推广佣金</a></li>
								</c:if>
							</ul>
						<div class="tab-content">
								
								<div id="tab_basic" class="tab-pane active">
									<div class="panel-body">
			<div class="container-fluid">
						
							<div class="row">
								
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="status">状态</label>
									<div class="col-sm-8">
										
										<c:forEach var="i" items="${ spreadPromoterStatus }" varStatus="s">
											<label class="checkbox-inline i-checks">
												<input name="status" type="radio" value="${ i.id }" 
													<c:if test="${ i.id==spreadPromoter.status || empty spreadPromoter.status && s.first }">checked</c:if> 
													<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择状态"</c:if> 
												/>
												${ i.value }
											</label>
										</c:forEach>
									</div>
								</div>
								
								
								
							</div>
							
							<div class="row">
								
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="userMobile">关联会员手机</label>
									<div class="col-sm-8">
										<input id="userId" name="userId" value="${ spreadPromoter.user.id }" type="hidden" class="form-control"/>
										<input id="userMobile" name="userMobile" 
											type="text" value="${spreadPromoter.user.mobile }" class="form-control"  
											data-rule-required="true" data-msg-required="请输入关联会员手机" onblur="searchUser()"/>
									</div>
									<script type="text/javascript">
									    function searchUser() {
									        var mobile = $('#userMobile').val();
									        var spreadPromoterId = $('#id').val();
									        if (mobile != '') {
									            var param = {};
									            param.mobile = mobile;
									            param.spreadPromoterId = spreadPromoterId;
									            $.ajax({
									                type : 'POST',
									                url : 'user/getUserByMobile.json', 
									                data : param, 
									                dataType : 'json', 
									                success : function (ret, textStatus) {
									                    if (ret.success) {
									                        var data = ret.data;
									                        var userId = data.userId;
									                        
									                        $('#userId').val(userId);
									                        
									                    } else if(ret.msg != null) {
									                    	$('#userId').val('');
									                        Alert(ret.msg);
									                        return;
									                    } else {
									                    	$('#userId').val('');
									                        Alert('程序错误，请联系管理员！');
									                        return;
									                    }
									                }, 
									                error : function(xmlHttpRequest, textStatus, errorThrown) {
									                    Alert({
									                        msg : '登录超时，请重新登录！', 
									                        ok : function() { 
									                            location.replace('authen/login.do');
									                        }
									                    });
									                }
									            });
									        }
									    }
									</script>
									
								</div>
								
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="emplID">工号</label>
									<div class="col-sm-8">
										<input id="emplID" name="emplID" type="text" value="${spreadPromoter.emplID }" class="form-control" 
											data-rule-required="true" data-msg-required="请输入工号" />
									</div>
								</div>
							
							</div>
						
							<div class="row">
								
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="realName">姓名</label>
									<div class="col-sm-8">
										<input id="realName" name="realName" 
											type="text" value="${ spreadPromoter.realName }" class="form-control" />
									</div>
								</div>
								
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="idcard">身份证号</label>
									<div class="col-sm-8">
										<input id="idcard" name="idcard" type="text" value="${spreadPromoter.idcard }" 
											maxlength="18" class="form-control" />
									</div>
								</div>
							</div>
							
							<div class="row">
								
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="mobile">手机号</label>
									<div class="col-sm-8">
										<input id="mobile" name="mobile" 
											type="text" value="${ spreadPromoter.mobile }" maxlength="11" class="form-control" />
									</div>
								</div>
								
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="email">邮箱</label>
									<div class="col-sm-8">
										<input id="email" name="email" 
											type="text" value="${spreadPromoter.email }" class="form-control" 
											data-rule-email="true" data-msg-email="请输入正确格式的电子邮箱" 
										/>
									</div>
								</div>
								
							</div>
							
							<div class="row">
								
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="spreadConsumeFirstComm">推广首次消费佣金</label>
									<div class="col-sm-8">
									
										<input id="spreadConsumeFirstComm" name="spreadConsumeFirstComm" 
											type="text" value="<fmt:formatNumber value="${ spreadPromoter.spreadConsumeFirstComm }" type="number" groupingUsed="false" maxFractionDigits="2" />" 
											class="form-control" style="width:100px; display:inline;" 
											data-rule-number="true" data-msg-number="指标比例必须为数值" 
										/> （元）
										<script type="text/javascript">
											$('#spreadConsumeFirstComm').onlyNum();
										</script>
									</div>
								</div>
								
							</div>
							
						</div>
									</div>
								</div>
								
								
								<div id="tab_commission" class="tab-pane">
                            		<div class="panel-body">
                            	
 <table id="listViewCommission" 
       data-toolbar="#toolbarCommission" 
       data-url="spreadCommission/spreadCommissionListData.json?spreadPromoterId=${spreadPromoter.id}" 
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
		<th data-field="no" data-width="15%">订单编号</th>
		<th data-field="amount" data-width="15%">金额（元）</th>
		<th data-field="consumeOrderNo" data-width="15%">消费订单</th>
		<th data-field="insertTime" data-sortable="true" data-formatter="fmtDate" data-width="15%">插入时间</th>
	</thead>
</table>
<script type="text/javascript">
    
    // 格式化时间列
    var fmtDate = function(value) {
        return $.isNumeric(value) ? (new Date(value)).format('yyyy-MM-dd HH:mm:ss') : '';
    }
    
</script>
<script type="text/javascript">
	
	var oListViewCommission;
	
	function initListViewCommission() {
        
        if ( $.isNull( oListViewCommission ) ) {
            
        	oListViewCommission = new ListView({
                'id' : 'listViewCommission', 
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
						
					</form>
<script type="text/javascript">
    $('a[data-toggle="tab"]').on('shown.bs.tab',function(e){
        var target = $(e.target)[0].hash;
        if (target == '#tab_commission') {
        	initListViewCommission();
        }
    });
</script>							
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
        
        
        function submitForm() {
        	
        	var userId = $('#userId').val();
        	if (userId == null || userId == '') {
        		Alert("请输入正确的关联会员手机！");
        		return;
        	}
        	
            $.post(
                'spreadPromoter/submitSpreadPromoter.json', 
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
        }
        
        $('#btn_submit').click(function(){
            // 输入校验
            if ( !$('#form').valid() )
                return false;
            submitForm();
        });
        
        
        $('#btn_refresh').click(function(){
            window.location.reload();
        });
        
    });
</script>

</body>
</html>