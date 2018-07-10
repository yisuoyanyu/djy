<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>员工信息</title>
	
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
						员工信息详情
					</h5>
					
					<div class="ibox-tools">
						<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
					</div>
				</div>
				
				<div class="ibox-content">
					
					<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
						
						<input id="id" name="id" type="hidden" value="${ coPartnerEmpl.id }" />
						<input id="coPartnerId" name="coPartnerId" type="hidden" value="${ coPartner.id }" />
							
							<div class="tabs-container">
									<ul class="nav nav-tabs">
										<li class="active"><a data-toggle="tab" href="#tab_basic" aria-expanded="true">基础信息</a></li>
										<c:if test="${not empty coPartnerEmpl.id }">
											<li class=""><a data-toggle="tab" href="#tab_consumeOrder" aria-expanded="false">会员消费</a></li>
										</c:if>
									</ul>
									<div class="tab-content">
										
										<div id="tab_basic" class="tab-pane active">
											<div class="panel-body">
		<div class="container-fluid">
			<div class="row">
									
				<div class="col-sm-6 form-group">
					<label class="control-label col-sm-4" for="statusName">绑定会员</label>
					<div class="col-sm-8">
						<c:if test="${empty coPartnerEmpl.id}">
							<div style="margin-top:2px;">
								<span id="nobinduser">
									未绑定
								</span>
								<div id="binduser" style="display:inline-block" class="hidden">
									<p class="form-control-static" data-rule-required="true" data-msg-required="请绑定会员">
										（微信昵称：<span id="nickName">${coPartnerEmpl.user.wechatUser.nickname}</span>）
									</p>
									<p class="form-control-static" data-rule-required="true" data-msg-required="请绑定会员">
										（绑定手机：<span id="bindMobile">${coPartnerEmpl.user.mobile}</span>）
									</p>
								</div>
								<a data-toggle="modal" class="btn btn-primary btn-xs" href="#modal-form"><span id="bindName">绑定</span></a>
							</div>
						</c:if>
						<c:if test="${not empty coPartnerEmpl.id}">
							<div style="margin-top:3px;">
								<div id="binduser" style="display:inline-block">
									<p class="form-control-static" data-rule-required="true" data-msg-required="请绑定会员">
										（微信昵称：<span id="nickName">${coPartnerEmpl.user.wechatUser.nickname}</span>）
									</p>
									<p class="form-control-static" data-rule-required="true" data-msg-required="请绑定会员">
										（绑定手机：<span id="bindMobile">${coPartnerEmpl.user.mobile}</span>）
									</p>
								</div>
								<a data-toggle="modal" class="btn btn-primary btn-xs" href="#modal-form"><span id="bindName">修改绑定</span></a>
							</div>
						</c:if>
					</div>
			</div>
			
			<!-- 绑定会员手机窗口 -->
			<div id="modal-form" class="modal inmodal fade" tabindex="-1" role="dialog" aria-hidden="true">
				 <div class="modal-dialog modal-sm">
		                              <div class="modal-content">
		                                  <div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span>
								<span class="sr-only">关闭</span>
							</button>
							<span class=" modal-title">
								<span class="font-bold">绑定会员手机号</span>
							</span>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<input id="userId" name="userId" value="${ coPartnerEmpl.user.id }" type="hidden" class="form-control"/>
								<label>会员手机：</label> <input type="text" id="userMobile" name="userMobile" value="${coPartnerEmpl.user.mobile }"  placeholder="请输入会员手机号" class="form-control" >
								<div id="remark" style="margin-top:10px;color:red;"></div>
							</div>
						</div>
						<div class="modal-footer">
							<button id="btn_bind" type="button" class="btn btn_sm btn-primary">确认</button>
							<button type="button" class="btn btn_sm btn-white" data-dismiss="modal">关闭</button>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
			    $('#btn_bind').click(function(){
			    	var mobile = $('#userMobile').val();
			        var coPartnerEmplId = $('#id').val();
			        if (mobile != '') {
			            var param = {};
			            param.mobile = mobile;
			            param.coPartnerEmplId = coPartnerEmplId;
			            $.ajax({
			                type : 'POST',
			                url : 'user/getUserByMobile.json', 
			                data : param, 
			                dataType : 'json', 
			                success : function (ret, textStatus) {
			                    if (ret.success) {
			                        var data = ret.data;
			                        var userId = data.userId;
			                        var nickName = data.nickName;
			                        $('#binduser').removeClass('hidden');
			                        $('#nobinduser').addClass('hidden');
			                        $('#userId').val(userId);
			                        $('#userMobile').val(mobile);
			                        $('#nickName').text(nickName);
			                        $('#bindMobile').text(mobile);
			                        $('#remark').text('');
			                        $('#modal-form').modal('hide');
			                        $('#bindName').text('重新绑定');
			                    } else if(ret.msg != null) {
			                    	$('#userId').val('');
			                    	$('#nickName').text('');
			                    	$('#bindMobile').text('');
			                    	$('#remark').text('（提示：'+ret.msg +')');
			                        return;
			                    } else {
			                    	$('#userId').val('');
			                    	$('#nickName').text('');
			                    	$('#bindMobile').text('');
			                    	$('#remark').text('（提示：'+ret.msg +')');
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
			    });
			    
			</script>
			
		</div>
		
		<div class="hr-line-dashed" style="margin:0px 0px 5px 0px;"></div>
		
		<div class="row">
			
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="status">状态</label>
				<div class="col-sm-8">
					
					<c:forEach var="i" items="${ coPartnerEmplStatus }" varStatus="s">
						<label class="checkbox-inline i-checks">
							<input name="status" type="radio" value="${ i.id }" 
								<c:if test="${ i.id==coPartnerEmpl.status || empty coPartnerEmpl.status && s.first }">checked</c:if> 
								<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择状态"</c:if> 
							/>
							${ i.value }
						</label>
					</c:forEach>
				</div>
			</div>
			
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="emplID">工号</label>
				<div class="col-sm-8">
					<input id="emplID" name="emplID" type="text" value="${coPartnerEmpl.emplID }" class="form-control" 
						data-rule-required="true" data-msg-required="请输入工号" />
				</div>
			</div>
			
		</div>
		
		<div class="row">
			
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="realName">真实姓名</label>
				<div class="col-sm-8">
					<input id="realName" name="realName" 
						type="text" value="${ coPartnerEmpl.realName }" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="idcard">身份证号</label>
				<div class="col-sm-8">
					<input id="idcard" name="idcard" type="text" value="${coPartnerEmpl.idcard }" 
						maxlength="18" class="form-control" />
				</div>
			</div>
		</div>
		
		<div class="row">
			
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="mobile">手机号</label>
				<div class="col-sm-8">
					<input id="mobile" name="mobile" 
						type="text" value="${ coPartnerEmpl.mobile }" maxlength="11" class="form-control" />
				</div>
			</div>
			
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="email">邮箱</label>
				<div class="col-sm-8">
					<input id="email" name="email" 
						type="text" value="${coPartnerEmpl.email }" class="form-control" 
							data-rule-email="true" data-msg-email="请输入正确格式的电子邮箱" 
						/>
					</div>
				</div>
				
		</div>
		</div>
											</div>
										</div>
										<c:if test="${not empty coPartnerEmpl.id }">
										<div id="tab_consumeOrder" class="tab-pane">
		                            		<div class="panel-body">
		  <div id="toolbarConsumeOrder" class="btn-group" role="group">
			<h4>
				<a href="consumeOrder/exportConsumeOrderExcel.do?coPartnerEmplId=${coPartnerEmpl.id}"><input type="button" class="btn btn-primary"  value="导出excel"/></a>
			</h4>
		</div>
		 <table id="listViewConsumeOrder" 
		       data-toolbar="#toolbarConsumeOrder" 
		       data-url="consumeOrder/consumeOrderListData.json?coPartnerEmplId=${coPartnerEmpl.id}" 
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
				<th data-field="no" data-width="15%">订单号</th>
				<th data-field="nickname" data-width="15%">会员微信名称</th>
				<th data-field="mobile" data-width="15%">会员手机</th>
				<th data-field="consumeAmount" data-width="10%">消费金额（元）</th>
				<th data-field="payAmount" data-width="10%">支付金额（元）</th>
				<c:if test="${coPartner.coMode != sysDepositMode}">
					<th data-field="settleAmount" data-width="10%">结算金额（元）</th>
				</c:if>
				<th data-field="statusName"  data-width="10%">状态</th>
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
										</c:if>
									</div>
							</div>
		<script type="text/javascript">
		   $('a[data-toggle="tab"]').on('shown.bs.tab',function(e){
		       var target = $(e.target)[0].hash;
		       if (target == '#tab_consumeOrder') {
		       	initListViewConsumeOrder();
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
        
        
        function submitForm() {
        	var coPartnerId = $('#coPartnerId').val();
            $.post(
                'coPartnerEmpl/submitCoPartnerEmpl.json?coPartnerId='+coPartnerId,
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
        	
        	var userId = $('#userId').val();
        	if (userId == '') {
        		Alert("您还未绑定会员，请绑定会员手机号！");
        		return;
        	}
        	
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