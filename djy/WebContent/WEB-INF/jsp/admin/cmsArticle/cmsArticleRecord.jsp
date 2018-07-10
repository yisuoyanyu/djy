<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>文章信息</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	
	<!-- jsTree -->
	<link href="plugins/jsTree/themes/default/style.min.css?v=3.3.3" rel="stylesheet">
	<script src="plugins/jsTree/jstree.min.js?v=3.3.3" type="text/javascript"></script>
	
	<script src="js/treeView.js" type="text/javascript"></script>
	
	<!-- ueditor -->
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js?v=1.4.3.3"></script>
	
</head>
<body class="gray-bg">
	<c:if test="${not empty AddCmsArticle || not empty EditCmsArticle}">
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
	</c:if>
	<!-- 表单内容 -->
	<div class="formBody">
	<div class="wrapper wrapper-content">
	
		<div class="ibox float-e-margins">
			
			<div class="ibox-title">
				<h5>
					<c:if test="${ empty cmsArticle.id }">新增文章信息</c:if>
					<c:if test="${ not empty cmsArticle.id }">编辑文章信息</c:if>
				</h5>
				
				<!-- <div class="ibox-tools">
					<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
				</div> -->
			</div>
			
			<div class="ibox-content">
				
				<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<!-- 这个hidden的input需要输出到浏览器端 -->
					<input type="hidden" id="id" name="id" value="${ cmsArticle.id }" />
				
					<div class="tabs-container">
						<ul class="nav nav-tabs">
	                        <li class="active"><a data-toggle="tab" href="#tab_basic" aria-expanded="true"> 基础</a>
	                        </li>
	                        <li class=""><a data-toggle="tab" href="#tab_detail" aria-expanded="false">文章详情</a>
	                        </li>
	                    </ul>
	                    
	                    <div class="tab-content">
	                    
	                    	<div id="tab_basic" class="tab-pane active">
	                    		<div class="panel-body">
									<div class="container">
										                    		
										<div class="row">
											<div class="col-sm-6 form-group">
												<label class="control-label col-sm-4" for="code">标识符：</label>
												<div class="col-sm-8">
													<c:if test="${ empty cmsArticle.id && not empty AddCmsArticle 
															|| not empty cmsArticle.id && not empty EditCmsArticle }">
														<input type="text" class="form-control" id="code" name="code" value="${ cmsArticle.code }" 
														data-rule-required="true" data-msg-required="请输入标识符" />
													</c:if>
													
													<c:if test="${ !(empty cmsArticle.id && not empty AddCmsArticle 
																|| not empty cmsArticle.id && not empty EditCmsArticle) }">
														<p class="form-control-static">${ cmsArticle.code }</p>
													</c:if>
												</div>
											</div>
											
											<div class="col-sm-6 form-group">
												<label class="control-label col-sm-4" for="status">状态</label>
												<div class="col-sm-8">
													
													<c:if test="${ empty cmsArticle.id && not empty AddCmsArticle 
																|| not empty cmsArticle.id && not empty EditCmsArticle }">
														<c:forEach var="i" items="${ cmsArticleStatus }" varStatus="s">
															<label class="checkbox-inline i-checks">
																<input name="status" type="radio" value="${ i.id }" 
																	<c:if test="${ i.id==cmsArticle.status || empty cmsArticle.status && s.first }">checked</c:if> 
																	<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择状态"</c:if> 
																/>
																${ i.value }
															</label>
														</c:forEach>
													</c:if>
													
													<c:if test="${ !(empty cmsArticle.id && not empty AddCmsArticle 
																|| not empty cmsArticle.id && not empty EditCmsArticle) }">
														<p class="form-control-static">${ cmsArticle.statusName }</p>
													</c:if>
													
												</div>
											</div>
											
										</div>
										
										<div class="row">
										
											<div class="col-sm-6 form-group">
												<label class="control-label col-sm-4" for="title">标题：</label>
												<div class="col-sm-8">
													<c:if test="${ empty cmsArticle.id && not empty AddCmsArticle 
															|| not empty cmsArticle.id && not empty EditCmsArticle }">
														<input type="text" class="form-control" id="title" name="title" value="${ cmsArticle.title }" 
														data-rule-required="true" data-msg-required="请输入标题" />
													</c:if>
													<c:if test="${ !(empty cmsArticle.id && not empty AddCmsArticle 
																|| not empty cmsArticle.id && not empty EditCmsArticle) }">
														<p class="form-control-static">${ cmsArticle.title }</p>
													</c:if>
													
												</div>
											</div>
											
										
											<div class="col-sm-6 form-group">
												<label class="control-label col-sm-4" for="cmsCatg">内容分类：</label>
												<div class="col-sm-8">
													<c:if test="${ empty cmsArticle.id && not empty AddCmsArticle 
															|| not empty cmsArticle.id && not empty EditCmsArticle }">
													<!--内容分类树型 start  -->
														<input type="hidden" id="cmsCatgId" name="cmsCatgId"  value="${cmsArticle.cmsCatg.id }" />
														<input type="text" class="form-control" id="cmsCatgName" name="cmsCatgName" value="${cmsArticle.cmsCatg.name }"  onclick="showCmsCatgTree();" onfocus="this.blur();"/>
															<div id="dropCont" style="display: none; position: absolute; background-color:#ffffff; border:1px solid #617775; overflow-y:scroll; overflow-x:auto;z-index:800;">
																<div id="cmsCatgTree"></div>
															</div>
														
														<script type="text/javascript">
														
															var oTreeView = new TreeView({
																'id' : 'cmsCatgTree',
																'data' : function(node, cb) {
																	
																	var param = {};
																	
																	var nodeId = node.id;
																	if (nodeId != '#') {
																		param.parentId = nodeId.substr(nodeId.lastIndexOf('_') + 1);
																	}
																	
																	$.ajax({
																		type : 'POST',
																		url : 'cmsCatg/cmsCatgTreeData.json',
																		data : param,
																		dataType : 'json',
																		success : function(ret, textStatus) {
																			if (ret.success) {
																				
																				var data = ret.data;
																				
																				var res = new Array();
																				
																				var cmsCatgs = data.cmsCatgs;
																				if(cmsCatgs != null){
																					for (var i=0; i<cmsCatgs.length; i++) {
																						var cmsCatg = cmsCatgs[i];
																						var obj = {
																							'id' : cmsCatg.id,
																							'text' : cmsCatg.name,
																							'icon' : "fa fa-institution",
																							'parentId' : cmsCatg.parentId ,
																							'children' : cmsCatg.isParent,
																							'status' : cmsCatg.status,
																							'isParent' : cmsCatg.isParent
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
																		if (data.node.original.isParent) {
																			Alert("内容分类选择错误，请勿选择父分类！");
																			return;
																		}
																		$('#cmsCatgId').val(data.selected[0]);
																		$('#cmsCatgName').val(data.instance.get_node(data.selected[0]).text);
																		//alert($('#cmsCatgId').val()+$('#cmsCatgName').val());
																	}
																	hideCmsCatgTree();
																}
															});
															
															function showCmsCatgTree() {
																$('#dropCont').css({
																	width : $('#cmsCatgName').outerWidth(),
																	height : 200
																}).slideDown("fast");
																$("body").bind("mousedown", onBodyDown);
															}
															
															function hideCmsCatgTree() {
																$('#dropCont').fadeOut("fast");
																$("body").unbind("mousedown", onBodyDown);
															}
															
															function onBodyDown(event) {
																if (!(event.target.id == "cmsCatgName" || event.target.id == "dropCont" || $(event.target).parents("#dropCont").length>0)) {
																	hideCmsCatgTree();
																}
															}
															
														</script>
													
													<!--内容分类树型 end  -->
													</c:if>
													
													<c:if test="${ !(empty cmsArticle.id && not empty AddCmsArticle 
															|| not empty cmsArticle.id && not empty EditCmsArticle) }">
														<p class="form-control-static">${ cmsArticle.cmsCatg.name }</p>
													</c:if>
												</div>
														
												
											</div>
											<c:if test="${not empty cmsArticle.id }">
												<div class="col-sm-6 form-group">
													<label class="control-label col-sm-4" for="articleUrl">文章路径：</label>
													<div class="col-sm-8">
														<p class="form-control-static">
															${articleUrl }
														</p>
													</div>
												</div>
											</c:if>
											
										</div>
										
									</div>
								</div>
							</div>
							
							<div id="tab_detail" class="tab-pane">
								<div class="panel-body">
								
								<div class="container">						
									<div class="row">
										<div>
											<h4>商品图文描述</h4>
										</div>
									</div>
								</div>
								
								<script id="container" name="content" type="text/plain" style="width:100%;height:200px;">
									${cmsArticle.content}
								</script>
								<script type="text/javascript">
								    var editor = UE.getEditor('container', {
								        toolbars: [
								            ['fullscreen', 'source', 'preview', '|', 'undo', 'redo', '|', 
								             'removeformat', 'formatmatch', '|', 'blockquote', 'horizontal', '|', 
								             'link', 'unlink', '|', 'insertorderedlist', 'insertunorderedlist', '|', 
								             'simpleupload', 'insertimage'], 
								            ['fontfamily', 'fontsize', 'bold', 'italic', 'underline', 'forecolor', 'backcolor', '|', 
								             'indent', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 
								             'rowspacingtop', 'rowspacingbottom', 'lineheight', '|', 
								             'imagenone', 'imageleft', 'imageright', 'imagecenter']
								        ]
								    });
								</script>
									
								</div>
							</div>
                            		
						</div>
						
					</div>

				</form>
			</div>
		</div>
		</div>
	</div>

				
<script type="text/javascript">
	
	$(function() {
		
		$('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
		
		
		$('#btn_submit').click(function(){

			// 输入校验
            if ( !$('#form').valid() )
                return false;
			
            var content = editor.getContent();
			  
			if (content == '') {
				Alert("文章详情不能为空！");
				return false;
			}  
			  
			  
			$.post(
				'cmsArticle/submitCmsArticle.json', 
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
		
		 // “刷新”按钮点击事件
        $('#btn_refresh').click(function(){
            window.location.reload();
        });
		
	});
</script>

</body>
</html>