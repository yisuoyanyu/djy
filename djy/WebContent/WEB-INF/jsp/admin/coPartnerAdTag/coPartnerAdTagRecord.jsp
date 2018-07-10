<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>商家活动标签</title>
	
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
					<c:if test="${ empty coPartnerAdTag.id }">新增商家活动标签</c:if>
					<c:if test="${ not empty coPartnerAdTag.id }">编辑商家活动标签</c:if>
				</h5>
				
				<div class="ibox-tools">
					<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
				</div>
			</div>
			
			<div class="ibox-content">
				
				<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<% /*--这个hidden的input需要输出到浏览器端--*/ %>
					<input type="hidden" id="id" name="id" value="${coPartnerAdTag.id }" />
					
					<div class="container">
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="status">类型</label>
								<div class="col-sm-8">
									
									<c:if test="${ empty coPartnerAdTag.id  && not empty AddCoPartnerAdTag 
												|| not empty coPartnerAdTag.id && not empty EditCoPartnerAdTag }">
										<c:forEach var="i" items="${ coPartnerAdTagType }" varStatus="s">
											<label class="checkbox-inline i-checks">
												<input name="type" type="radio" value="${ i.id }" 
													<c:if test="${ i.id==coPartnerAdTag.type || empty coPartnerAdTag.type && s.first }">checked</c:if> 
													<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择类型"</c:if> 
												/>
												${ i.value }
											</label>
										</c:forEach>
									</c:if>
									<c:if test="${ !(empty coPartnerAdTag.id  && not empty AddCoPartnerAdTag 
												|| not empty coPartnerAdTag.id && not empty EditCoPartnerAdTag) }">
										<p class="form-control-static">${ coPartnerAdTag.typeName }</p>
									</c:if>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="title">标题</label>
								<div class="col-sm-8">
									
									<c:if test="${ empty coPartnerAdTag.id  && not empty AddCoPartnerAdTag 
												|| not empty coPartnerAdTag.id && not empty EditCoPartnerAdTag }">
										<input id="title" name="title" 
											type="text" value="${ coPartnerAdTag.title }" class="form-control" 
											data-rule-required="true" data-msg-required="请输入标题" />
									</c:if>
									<c:if test="${ !(empty coPartnerAdTag.id  && not empty AddCoPartnerAdTag 
												|| not empty coPartnerAdTag.id && not empty EditCoPartnerAdTag) }">
										<p class="form-control-static">${ coPartnerAdTag.title }</p>
									</c:if>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="coPartner">商家活动</label>
								<div class="col-sm-8">
									
									<c:if test="${empty coPartnerAd }">
									
										<c:if test="${ empty coPartnerAdTag.id && not empty AddCoPartnerAdTag 
													|| not empty coPartnerAdTag.id && not empty EditCoPartnerAdTag }">
											<!--合作商家活动树型 start  -->
											<input id="coPartnerAdId" name="coPartnerAdId" type="hidden" value="${ coPartnerAdTag.coPartnerAd.id }" />
											
											<input id="coPartnerAdTitle" name="coPartnerAdTitle" 
												type="text" value="${ coPartnerAdTag.coPartnerAd.title }" 
												class="form-control" onclick="showCoPartnerAdTree();" onfocus="this.blur();" 
												placeholder="选择商家活动..." />
											
											<div id="dropCont" style="display: none; position: absolute; background-color:#ffffff; border:1px solid #617775; overflow-y:scroll; overflow-x:auto;z-index:800;">
												<div id="coPartnerAdTree"></div>
											</div>
										
											<script type="text/javascript">
											
												var oTreeView = new TreeView({
													'id' : 'coPartnerAdTree',
													'data' : function(node, cb) {
														
														var param = {};
														
														var nodeId = node.id;
														if (nodeId != '#') {
															param.parentId = nodeId.substr(nodeId.lastIndexOf('_') + 1);
														}
														
														$.ajax({
															type : 'POST',
															url : 'coPartnerAd/coPartnerAdTreeData.json',
															data : param,
															dataType : 'json',
															success : function(ret, textStatus) {
																if (ret.success) {
																	
																	var data = ret.data;
																	
																	var res = new Array();
																	
																	var coPartnerAds = data.coPartnerAds;
																	if(coPartnerAds != null){
																		for (var i=0; i<coPartnerAds.length; i++) {
																			var coPartnerAd = coPartnerAds[i];
																			var obj = {
																				'id' : coPartnerAd.id,
																				'text' : coPartnerAd.title,
																				'icon' : "fa fa-institution",
																				'status' : coPartnerAd.status
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
															
															$('#coPartnerAdId').val(data.selected[0]);
															$('#coPartnerAdTitle').val(data.instance.get_node(data.selected[0]).text);
															
														}
														hideCoPartnerAdTree();
													}
												});
												
												function showCoPartnerAdTree() {
													$('#dropCont').css({
														width : $('#coPartnerAdTitle').outerWidth(),
														height : 200
													}).slideDown("fast");
													$("body").bind("mousedown", onBodyDown);
												}
												
												function hideCoPartnerAdTree() {
													$('#dropCont').fadeOut("fast");
													$("body").unbind("mousedown", onBodyDown);
												}
												
												function onBodyDown(event) {
													if (!(event.target.id == "coPartnerAdTitle" || event.target.id == "dropCont" || $(event.target).parents("#dropCont").length>0)) {
														hideCoPartnerAdTree();
													}
												}
												
											</script>
											<!--合作商家树型 end  -->
										</c:if>
										
										<c:if test="${ !(empty coPartnerAdTag.id  && not empty AddCoPartnerAdTag 
													|| not empty coPartnerAdTag.id && not empty EditCoPartnerAdTag) }">
											<p class="form-control-static">${ coPartnerAdTag.coPartnerAd.title }</p>
										</c:if>
									
									</c:if>
									
									<c:if test="${ not empty coPartnerAd }">
										<input id="coPartnerAdId" name="coPartnerAdId" type="hidden" value="${coPartnerAd.id }" />
										<p class="form-control-static">${ coPartnerAd.title }</p>
									</c:if>
									
									
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="sortNumber">排序号</label>
								<div class="col-sm-8">
									<c:if test="${ empty coPartnerAdTag.id  && not empty AddCoPartnerAdTag 
												|| not empty coPartnerAdTag.id && not empty EditCoPartnerAdTag }">
										<input id="sortNumber" name="sortNumber" 
											type="text" value="${coPartnerAdTag.sortNumber }" class="form-control"/>
									</c:if>
									<c:if test="${ !(empty coPartnerAdTag.id  && not empty AddCoPartnerAdTag 
												|| not empty coPartnerAdTag.id && not empty EditCoPartnerAdTag) }">
										<p class="form-control-static">${ coPartnerAdTag.sortNumber }</p>
									</c:if>
								</div>
							</div>
							
						</div>
						
					</div>
					
				</form>
					
				
				<c:if test="${ empty coPartnerAdTag.id  && not empty AddCoPartnerAdTag 
							|| not empty coPartnerAdTag.id && not empty EditCoPartnerAdTag }">
					
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

    $(function() {
        
        // 格式化单选框样式
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green', 
            radioClass: 'iradio_square-green'
        });
        
        
        function submitForm() {
            $.post(
                'coPartnerAdTag/submitCoPartnerAdTag.json', 
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