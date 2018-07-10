<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>送券活动详情</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	
	<!-- jsTree -->
	<link href="plugins/jsTree/themes/default/style.min.css?v=3.3.3" rel="stylesheet">
	<script src="plugins/jsTree/jstree.min.js?v=3.3.3" type="text/javascript"></script>
	
	<script src="js/treeView.js" type="text/javascript"></script>
	
</head>

<body class="gray-bg">

	<div class="wrapper wrapper-content">
	
		<div class="ibox float-e-margins">
			
			<div class="ibox-title">
				<h5>
					送券活动详情
				</h5>
				
				<div class="ibox-tools">
					<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
				</div>
			</div>
			
			<div class="ibox-content">
				
				<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<% /*--这个hidden的input需要输出到浏览器端--*/ %>
					<input type="hidden" id="id" name="id" value="${actCoupon.id }" />
					
					<div class="container">
						
						<div class="row">
						
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="status">状态</label>
								<div class="col-sm-8">
									
									<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
										<c:forEach var="i" items="${ actCouponStatus }" varStatus="s">
											<label class="checkbox-inline i-checks">
												<input name="status" type="radio" value="${ i.id }" 
													<c:if test="${ i.id==actCoupon.status || empty actCoupon.status && s.first }">checked</c:if> 
													<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择状态"</c:if> 
												/>
												${ i.value }
											</label>
										</c:forEach>
									</c:if>
									<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
										<p class="form-control-static">${ actCoupon.statusName }</p>
									</c:if>
								</div>
							</div>
							
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="title">标题</label>
								<div class="col-sm-8">
									
									<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
										<input id="title" name="title" 
											data-rule-required="true" data-msg-required="请输入标题" 
											type="text" value="${ actCoupon.title }" class="form-control" />
									</c:if>
									
									<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
										<p class="form-control-static">${ actCoupon.title }</p>
									</c:if>
									
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="coPartner">合作商家</label>
								<div class="col-sm-8">
									
									<c:if test="${empty coPartner }">
									
										<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
													|| not empty actCoupon.id && not empty EditActCoupon }">
											<!--合作商家树型 start  -->
											<input id="coPartnerId" name="coPartnerId" type="hidden" value="${ actCoupon.coPartner.id }" />
											
											<input id="coPartnerName" name="coPartnerName" 
												type="text" value="${ actCoupon.coPartner.name }" 
												class="form-control" onclick="showCoPartnerTree();" onfocus="this.blur();" 
												placeholder="选择合作商家..." />
											
											<div id="dropCont" style="display: none; position: absolute; background-color:#ffffff; border:1px solid #617775; overflow-y:scroll; overflow-x:auto;z-index:800;">
												<div id="coPartnerTree"></div>
											</div>
										
											<script type="text/javascript">
											
												var oTreeView = new TreeView({
													'id' : 'coPartnerTree',
													'data' : function(node, cb) {
														
														var param = {};
														
														var nodeId = node.id;
														if (nodeId != '#') {
															param.parentId = nodeId.substr(nodeId.lastIndexOf('_') + 1);
														}
														
														$.ajax({
															type : 'POST',
															url : 'coPartner/coPartnerTreeData.json',
															data : param,
															dataType : 'json',
															success : function(ret, textStatus) {
																if (ret.success) {
																	
																	var data = ret.data;
																	
																	var res = new Array();
																	
																	var coPartners = data.coPartners;
																	if(coPartners != null){
																		for (var i=0; i<coPartners.length; i++) {
																			var coPartner = coPartners[i];
																			var obj = {
																				'id' : coPartner.id,
																				'text' : coPartner.name,
																				'icon' : "fa fa-institution",
																				'status' : coPartner.status
																			}
																			res.push(obj);
																		}
																	}
																	
																	cb.call(this, res);
																	
																} else if(ret.msg != null) {
																	Alert(ret.msg);
																} else {
																	Alert('程序错误，请联系管理员！');
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
														
													},
													changed : function(e, data) {
														if ( data && data.selected && data.selected.length ) {
															
															$('#coPartnerId').val(data.selected[0]);
															$('#coPartnerName').val(data.instance.get_node(data.selected[0]).text);
															
														}
														hideCoPartnerTree();
													}
												});
												
												function showCoPartnerTree() {
													$('#dropCont').css({
														width : $('#coPartnerName').outerWidth(),
														height : 200
													}).slideDown("fast");
													$("body").bind("mousedown", onBodyDown);
												}
												
												function hideCoPartnerTree() {
													$('#dropCont').fadeOut("fast");
													$("body").unbind("mousedown", onBodyDown);
												}
												
												function onBodyDown(event) {
													if (!(event.target.id == "coPartnerName" || event.target.id == "dropCont" || $(event.target).parents("#dropCont").length>0)) {
														hideCoPartnerTree();
													}
												}
												
											</script>
											<!--合作商家树型 end  -->
										</c:if>
										
										<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
													|| not empty actCoupon.id && not empty EditActCoupon) }">
											<p class="form-control-static">${ actCoupon.coPartner.name }</p>
										</c:if>
									
									</c:if>
									
									<c:if test="${ not empty coPartner }">
										<input id="coPartnerId" name="coPartnerId" type="hidden" value="${coPartner.id }" />
										<p class="form-control-static">${ coPartner.name }</p>
									</c:if>
									
									
								</div>
							</div>
							
						</div>
						
						<div class="row">
		
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="startTime">活动开始时间</label>
								<div class="col-sm-8">
									<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
										<input id="startTime" name="startTime" type="text" 
											value="<fmt:formatDate value="${ actCoupon.startTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" 
											class="form-control" onfocus="this.blur();" />
										<script type="text/javascript">
											laydate({
												istime: true,
												elem : '#startTime',
												format : 'YYYY-MM-DD hh:mm:ss',
											});
										</script>
									</c:if>
									<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
										<p class="form-control-static">
											<fmt:formatDate value="${ actCoupon.startTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</c:if>
								</div>
							</div>
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="endTime">活动结束时间</label>
								<div class="col-sm-8">
									<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
										<input id="endTime" name="endTime" type="text" 
											value="<fmt:formatDate value="${ actCoupon.endTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" 
											class="form-control" onfocus="this.blur();" />
										<script type="text/javascript">
											laydate({
												istime: true,
												elem : '#endTime',
												format : 'YYYY-MM-DD hh:mm:ss'
											});
										</script>
									</c:if>
									<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
										<p class="form-control-static">
											<fmt:formatDate value="${ actCoupon.endTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</c:if>
								</div>
							</div>
							
						</div>
						
						<div class="hr-line-dashed" style="margin:0px 0px 5px 0px;"></div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="type">优惠券类型</label>
								<div class="col-sm-8">
									
									<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
										<c:forEach var="i" items="${ couponType }" varStatus="s">
											<label class="checkbox-inline i-checks">
												<input name="type" type="radio" value="${ i.id }" 
													<c:if test="${ i.id==actCoupon.type || empty actCoupon.type && s.first }">checked</c:if> 
													<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择优惠券类型"</c:if> 
												/>
												${ i.value }
											</label>
										</c:forEach>
									</c:if>
									
									<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
										<p class="form-control-static">${ actCoupon.typeName }</p>
									</c:if>
									
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="isChoice">精选</label>
								<div class="col-sm-8">
									
									<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
										<c:forEach var="i" items="${ actCouponIsChoice }" varStatus="s">
											<label class="checkbox-inline i-checks">
												<input name="isChoice" type="radio" value="${ i.id }"  
												/>
												${ i.value }
											</label>
										</c:forEach>
									</c:if>
									
									<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
										<p class="form-control-static">
											<c:if test="${ actCoupon.isChoice }">是</c:if>
											<c:if test="${ !actCoupon.isChoice }">否</c:if>
										</p>
									</c:if>
									
								</div>
							</div>
							
						</div>
						
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="discountPercent">折扣百分比</label>
								<div class="col-sm-8">
									
									<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
										<input id="discountPercent" name="discountPercent" 
											type="text" value="<fmt:formatNumber value="${ actCoupon.discountPercent }" type="number" groupingUsed="false" maxFractionDigits="2" />" 
											class="form-control" style="width:100px; display:inline;" 
											data-rule-required="true" data-msg-required="请输入折扣百分比" 
											data-rule-number="true" data-msg-number="折扣百分比必须为数值" 
										/> %
										<script type="text/javascript">
											$('#discountPercent').onlyNum();
										</script>
									</c:if>
									
									<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
										<p class="form-control-static">
											<fmt:formatNumber value="${ actCoupon.discountPercent }" type="number" groupingUsed="false" maxFractionDigits="2" />
										</p>
									</c:if>
									
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="useMinConsume">最低消费金额</label>
								<div class="col-sm-8">
									
									<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
										<input id="useMinConsume" name="useMinConsume" 
											type="text" value="<fmt:formatNumber value="${ actCoupon.useMinConsume }" type="number" groupingUsed="false" maxFractionDigits="2" />" 
											class="form-control" style="width:100px; display:inline;" 
											data-rule-number="true" data-msg-number="最低消费金额必须为数值" 
										/> （元）
										<script type="text/javascript">
											$('#useMinConsume').onlyNum();
										</script>
									</c:if>
									
									<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
										<p class="form-control-static">
											<fmt:formatNumber value="${ actCoupon.useMinConsume }" type="number" groupingUsed="false" maxFractionDigits="2" />
										</p>
									</c:if>
									
								</div>
							</div>
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="useDateType">可使用日期类型</label>
								<div class="col-sm-8">
									
									<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
										<c:forEach var="i" items="${ couponUseDateType }" varStatus="s">
											<label class="checkbox-inline i-checks">
												<input name="useDateType" type="radio" value="${ i.id }"
												<c:if test="${ i.id==actCoupon.useDateType || empty actCoupon.useDateType && s.first }">checked</c:if> 
												data-rule-required="true" data-msg-required="请输入可使用日期类型" />
												${ i.value }
											</label>
										</c:forEach>
										<script type="text/javascript">
											$("input[name='useDateType']").parent().on('ifChecked', function(event){
												var useDateType = $("input[name='useDateType']:checked").val();
												checkedUseDateType(useDateType);
											});
										</script>
										
									</c:if>
									
									<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
										<p class="form-control-static">
											${ actCoupon.useDateTypeName }
										</p>
									</c:if>
									
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="rewardNum">已赠送次数</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<c:if test="${ empty actCoupon.rewardNum }">
											0
										</c:if>
										<fmt:formatNumber value="${ actCoupon.rewardNum }" type="number" />（次）
									</p>
									
								</div>
							</div>
							
						</div>
						
						<div id="useDate" class="hidden">
							<div class="row">
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="useStartDate">打折券使用开始时间</label>
									<div class="col-sm-8">
										<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
											<input id="useStartDate" name="useStartDate" type="text" 
												value="<fmt:formatDate value="${ actCoupon.useStartDate }" type="both" pattern="yyyy-MM-dd" />" 
												class="form-control" onfocus="this.blur();" />
											<script type="text/javascript">
												laydate({
													elem : '#useStartDate',
													format : 'YYYY-MM-DD'
												});
											</script>
										</c:if>
										
										<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
											<p class="form-control-static">
												<fmt:formatDate value="${ actCoupon.useStartDate }" type="both" pattern="yyyy-MM-dd" />
											</p>
										</c:if>
										
									</div>
								</div>
								<div class="col-sm-6 form-group">
									<label class="control-label col-sm-4" for="useEndDate">打折券使用结束时间</label>
									<div class="col-sm-8">
										<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
											<input id="useEndDate" name="useEndDate" type="text" 
												value="<fmt:formatDate value="${ actCoupon.useEndDate }" type="both" pattern="yyyy-MM-dd" />" 
												class="form-control" onfocus="this.blur();" />
											<script type="text/javascript">
												laydate({
													elem : '#useEndDate',
													format : 'YYYY-MM-DD'
												});
											</script>
										</c:if>
										
										<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
											<p class="form-control-static">
												<fmt:formatDate value="${ actCoupon.useEndDate }" type="both" pattern="yyyy-MM-dd" />
											</p>
										</c:if>
										
									</div>
								</div>
								
							</div>
						</div>
						
						<div class="row">
						
								<div id="useDay" class="col-sm-6 form-group hidden">
									<label class="control-label col-sm-4" for="useDays">打折券有效天数</label>
									<div class="col-sm-8">
										<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon }">
											<input id="useDays" name="useDays" 
												type="text" value="<fmt:formatNumber value="${ actCoupon.useDays }" type="number" groupingUsed="false" maxFractionDigits="2" />" 
												class="form-control" style="width:100px; display:inline;" 
												data-rule-number="true" data-msg-number="打折券有效天数必须为数值" 
											/>
											<script type="text/javascript">
												$('#useDays').onlyNum();
											</script>
										</c:if>
										
										<c:if test="${ !(empty actCoupon.id  && not empty AddActCoupon 
												|| not empty actCoupon.id && not empty EditActCoupon) }">
											<p class="form-control-static">
												<fmt:formatNumber value="${ actCoupon.useDays }" type="number" groupingUsed="false" maxFractionDigits="2" />
											</p>
										</c:if>
										
									</div>
								</div>
								
						</div>
						
					</div>
					
				</form>
					
				
				<c:if test="${ empty actCoupon.id && not empty AddActCoupon 
						|| not empty actCoupon.id && not empty EditActCoupon }">
					
					<div class="hr-line-dashed"></div>
					
					<div class="form-group text-center">
						
						<button id="btn_submit" class="btn btn-primary">提交</button>
						
						<button id="btn_cancel" class="btn btn-white">取消</button>
						
					</div>
					
				</c:if>
				
			</div>
			
		</div>

	</div>

				
<script type="text/javascript">
	
function checkedUseDateType(val) {
	if ( parseInt(val) == 1 ) {
        $('#useDate').addClass('hidden');
        $('#useDay').addClass('hidden');
    } else if ( parseInt(val) == 2 ) {
        $('#useDate').removeClass('hidden');
        $('#useDay').addClass('hidden');
    } else if ( parseInt(val) == 3 ) {
        $('#useDate').addClass('hidden');
        $('#useDay').removeClass('hidden');
    }
}
	
    $(function() {
        
        // 格式化单选框样式
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green', 
            radioClass: 'iradio_square-green'
        });
        
        var val = $("input[name='useDateType']:checked").val();
        
        if ( parseInt(val) == 1 ) {
            $('#useDate').addClass('hidden');
            $('#useDay').addClass('hidden');
        } else if ( parseInt(val) == 2 ) {
            $('#useDate').removeClass('hidden');
            $('#useDay').addClass('hidden');
        } else if ( parseInt(val) == 3 ) {
            $('#useDate').addClass('hidden');
            $('#useDay').removeClass('hidden');
        }
        
        function submitForm() {
            $.post(
                'actCoupon/submitActCoupon.json', 
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
        
        $('#btn_cancel').click(function(){
            window.location.reload();
        });
    });
</script>

</body>
</html>