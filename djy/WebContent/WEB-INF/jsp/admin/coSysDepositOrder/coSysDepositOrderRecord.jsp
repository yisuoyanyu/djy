<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>平台预存订单信息</title>
	<%@include file="../decorators/formHead.jsp"%>
	<%@include file="../decorators/listViewHead.jsp"%>
	
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
						平台预存订单信息
					</h5>
					
				</div>
				
				<div class="ibox-content">
					
					<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<% /*这个hidden的input需要输出到浏览器端*/ %>
					<input id="id" name="id" value="${ coSysDepositOrder.id }" type="hidden" />
					<div class="tabs-container">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#tab-basic" aria-expanded="true">基础信息</a></li>
						</ul>
						<div class="tab-content">
							
							<div id="tab-basic" class="tab-pane active">
								<div class="panel-body">
									<div class="container-fluid">
								
								
								<c:if test="${ not empty coSysDepositOrder.id }">
									<div class="row">
										<div class="col-sm-6 form-group">
											<label class="control-label col-sm-4" for="no">订单编号</label>
											<div class="col-sm-8">
												<p class="form-control-static">${ coSysDepositOrder.no }</p>
											</div>
										</div>
										<div class="col-sm-6 form-group">
											<label class="control-label col-sm-4" for="payAmount">支付金额</label>
											<div class="col-sm-8">
												<c:if test="${ !(empty coSysDepositOrder.id && not empty AddCoSysDepositOrder ) }">
													<p class="form-control-static">
														<fmt:formatNumber value="${ coSysDepositOrder.payAmount  }" type="number" maxFractionDigits="2" /> 元
													</p>
												</c:if>
											</div>
										</div>
									</div>
							</c:if>
								
								<div class="row">
									
									<div class="col-sm-6 form-group">
										<label class="control-label col-sm-4" for="coPartner">合作商家</label>
										<div class="col-sm-8">
											
											<c:if test="${ empty coPartner }">
											
												<c:if test="${ empty coSysDepositOrder.id && not empty AddCoSysDepositOrder }">
													<!--合作商家树型 start  -->
													<input id="coPartnerId" name="coPartnerId" type="hidden" value="${ coSysDepositOrder.coPartner.id }" />
													
													<input id="coPartnerName" name="coPartnerName" 
														type="text" value="${ coSysDepositOrder.coPartner.name }" 
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
																	url : 'coPartner/coPartnerTreeData.json?coMode=2',
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
												
												<c:if test="${ !(empty coSysDepositOrder.id && not empty AddCoSysDepositOrder ) }">
													<p class="form-control-static">${ coSysDepositOrder.coPartner.name }</p>
												</c:if>
											
											</c:if>
											
											<c:if test="${ not empty coPartner }">
												<input id="coPartnerId" name="coPartnerId" type="hidden" value="${coPartner.id }" />
												<p class="form-control-static">${ coPartner.name }</p>
											</c:if>
											
											
										</div>
									</div>
									
									<div class="col-sm-6 form-group">
										<label class="control-label col-sm-4" for="amount">预存金额</label>
										<div class="col-sm-8">
											<c:if test="${ empty coSysDepositOrder.id && not empty AddCoSysDepositOrder }">
												<input id="amount" name="amount" 
													type="text" value="<fmt:formatNumber value="${coSysDepositOrder.amount }" type="number" groupingUsed="false" maxFractionDigits="2" />" 
													class="form-control" style="width:100px; display:inline;"
													data-rule-number="true" data-msg-number="预存金额必须为数值"
												/>（元）
												<script type="text/javascript">
													$('#amount').onlyNum();
												</script>
											</c:if>
											<c:if test="${ !(empty coSysDepositOrder.id && not empty AddCoSysDepositOrder ) }">
												<p class="form-control-static">
													<fmt:formatNumber value="${ coSysDepositOrder.amount  }" type="number" maxFractionDigits="2" /> 元
												</p>
											</c:if>
										</div>
									</div>
									
								</div>
								<c:if test="${ not empty coSysDepositOrder.id }">
									<div class="row">
										<div class="col-sm-6 form-group">
											<label class="control-label col-sm-4" for="statusName">订单状态</label>
											<div class="col-sm-8">
												<p class="form-control-static">
													${ coSysDepositOrder.statusName }
												</p>
											</div>
										</div>
									</div>
								</c:if>
							</div>
					
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
       
        if (target == '#tab-depositLog') {
        	initListView();
        }
    });
</script>  					
				</form>
				
				<c:if test="${ empty coSysDepositOrder.id && not empty AddCoSysDepositOrder }">
					
					<div class="hr-line-dashed"></div>
					
					<div class="form-group text-center">
						
						<button id="btn_submit" class="btn btn-primary">提交</button>
						
						<button id="btn_cancel" class="btn btn-white">取消</button>
						
					</div>
					
				</c:if>
				
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
            $.post(
                'coSysDepositOrder/submitCoSysDepositOrder.json', 
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
            
            // 提交表单
            preUpload( {
                success : function () {
                    submitForm();
                }
            } );
            
        });
        
        
        $('#btn_cancel').click(function(){
            window.location.reload();
        });
        
        
    });
</script>


</body>
</html>