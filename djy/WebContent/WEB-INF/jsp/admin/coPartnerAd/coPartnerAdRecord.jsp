<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>广告活动信息</title>
	<%@include file="../decorators/formHead.jsp"%>
	<%@include file="../decorators/listViewHead.jsp"%>
	
	<!-- webuploader -->
	<link rel="stylesheet" type="text/css" href="plugins/upload/webuploader/webuploader.css?v=0.1.5" />
	<script type="text/javascript" src="plugins/upload/webuploader/webuploader.js?v=0.1.5"></script>
	
	<!-- imageUpload 是基于 webuploader 封装 -->
	<link rel="stylesheet" type="text/css" href="js/imageUpload/imageUpload.css" />
	<script type="text/javascript" src="js/imageUpload/imageUpload.js"></script>
	
	<!-- jsTree -->
	<link href="plugins/jsTree/themes/default/style.min.css?v=3.3.3" rel="stylesheet">
	<script src="plugins/jsTree/jstree.min.js?v=3.3.3" type="text/javascript"></script>
	
	<script src="js/treeView.js" type="text/javascript"></script>
	
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
					
					<c:if test="${ empty coPartnerAd.id && not empty AddCoPartnerAd 
					|| not empty coPartnerAd.id && not empty EditCoPartnerAd }">
						
						<li class="active">
							<a id="btn_submit" role="button" href="javascript:;">
								<i class="fa fa-save"></i>
								提交
							</a>
						</li>
						
					</c:if>
					
					
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
	
	<div class="wrapper wrapper-content">
	
		<div class="ibox float-e-margins">
			
			<div class="ibox-title">
				<h5>
					<c:if test="${ empty coPartnerAd.id }">新增广告活动</c:if>
					<c:if test="${ not empty coPartnerAd.id }">编辑广告活动</c:if>
				</h5>
				
				<div class="ibox-tools">
					<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
				</div>
			</div>
			
			<div class="ibox-content">
				
				<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<!-- 这个hidden的input需要输出到浏览器端 -->
					<input type="hidden" id="id" name="id" value="${ coPartnerAd.id }" />
					
					<div class="tabs-container">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#tab-basic" aria-expanded="true">基础信息</a></li>
							<c:if test="${not empty coPartnerAd.id}">
								<li class=""><a data-toggle="tab" href="#tab-coPartnerAdTag" aria-expanded="false">活动标签</a></li>
							</c:if>
						</ul>
						<div class="tab-content">
							
							<div id="tab-basic" class="tab-pane active">
								<div class="panel-body">
									<div class="container-fluid">
									                    		
						<div class="row">
						
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="coPartner">合作商家</label>
								<div class="col-sm-8">
									
									<c:if test="${empty coPartner }">
										<c:if test="${ empty coPartnerAd.id && not empty AddCoPartnerAd 
											|| not empty coPartnerAd.id && not empty EditCoPartnerAd }">
											<!--合作商家树型 start  -->
											<input id="coPartnerId" name="coPartnerId" type="hidden" value="${ coPartnerAd.coPartner.id }" />
											
											<input id="coPartnerName" name="coPartnerName" 
												type="text" value="${ coPartnerAd.coPartner.name }" 
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
										
										<c:if test="${ !(empty coPartnerAd.id  && not empty AddCoPartnerAd 
											|| not empty coPartnerAd.id && not empty EditCoPartnerAd) }">
											<p class="form-control-static">
												${ coPartnerAd.coPartner.name }
											</p>
										</c:if>
									</c:if>
									
									<c:if test="${ not empty coPartner }">
										<input id="coPartnerId" name="coPartnerId" type="hidden" value="${coPartner.id }" />
										<p class="form-control-static">${ coPartner.name }</p>
									</c:if>
									
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="status">状态</label>
								<div class="col-sm-8">
									<c:if test="${ empty coPartnerAd.id && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd }">
										<c:forEach var="i" items="${ coPartnerAdStatus }" varStatus="s">
											<label class="checkbox-inline i-checks">
												<input name="status" type="radio" value="${ i.id }" 
													<c:if test="${ i.id==coPartnerAd.status || empty coPartnerAd.status && s.first }">checked</c:if> 
													<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择状态"</c:if> 
												/>
												${ i.value }
											</label>
										</c:forEach>
									</c:if>
									<c:if test="${ !(empty coPartnerAd.id  && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd) }">
										<p class="form-control-static">
											${ coPartnerAd.statusName }
										</p>
									</c:if>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="title">标题</label>
								<div class="col-sm-8">
									<c:if test="${ empty coPartnerAd.id && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd }">
										<input type="text" class="form-control" id="title" name="title" value="${ coPartnerAd.title }" 
										data-rule-required="true" data-msg-required="请输入标题" />
									</c:if>
									
									<c:if test="${ !(empty coPartnerAd.id  && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd) }">
										<p class="form-control-static">
											${ coPartnerAd.title }
										</p>
									</c:if>
									
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="isChoice">精选</label>
								<div class="col-sm-8">
									<c:if test="${ empty coPartnerAd.id && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd }">
										<c:forEach var="i" items="${ coPartnerAdChoice }" varStatus="s">
											<label class="checkbox-inline i-checks">
												<input name="isChoice" type="radio" value="${ i.id }" 
													<c:if test="${ i.id == 1 && coPartnerAd.isChoice || i.id == 0 && !coPartnerAd.isChoice  || empty coPartnerAd.isChoice && s.first }">checked</c:if> 
												/>
												${ i.value }
											</label>
										</c:forEach>
									</c:if>
									
									<c:if test="${ !(empty coPartnerAd.id  && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd) }">
										<p class="form-control-static">
											${ coPartnerAd.choiceName }
										</p>
									</c:if>
									
								</div>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="imageUpload">图片</label>
								<div class="col-sm-8">
<!-- 图片上传 start -->
<div id="imageUpload"></div>
<h5>（建议：分辨率90*90，大小&lt;=500KB，支持JPG、PNG、JPEG）</h5>
<script type="text/javascript">
    
    var oImageUpload;
    
    function initImageUpload() {
        
        if ( $.isNull(oImageUpload) ) {
            
            oImageUpload = new ImageUpload({
                
                'id' : 'imageUpload', 
                
                <c:if test="${ !(empty coPartnerAd.id  && not empty AddCoPartnerAd || not empty coPartnerAd.id && not empty EditCoPartnerAd) }">
                // 是否允许编辑。默认true。
                // true — 允许编辑（上传、删除），false — 不允许编辑。
                'edit' : false,	
                </c:if>
                
                // 文件数限制（含已上传、未上传）。默认不限制。
                'fileNumLimit' : 1, 
                
                // 临时上传文件夹属性
                'tmpUpload' : {
                    
                    // 上传到临时文件夹的文件路径参数名称（后端通过该参数名称取值）
                    'param' : 'uploadImgs', 
                    
                    // 上传临时文件夹路径
                    'path' : 'tmp'
                    
                }, 
                
                // 目标文件夹属性
                'uploadPath' : {
                    
                    // 目标文件夹参数名称（后端通过该参数名称取值）
                    'param' : 'imgUploadPath', 
                    
                    // 目标文件夹路径值
                    'value' : 'coPartnerAd/images'
                    
                }, 
                
                // 原有图片的属性
                'initImg' : {
                    
                    // 原有图片的参数名称（后端通过该参数名称取值）
                    'param' : 'oldImgs', 
                    
                    // 初始化的图片列表
                    'imgs' : [
                        <c:if test="${ not empty coPartnerAd.imgPath }">
                        {
                            // 图片标题
                            'title' : '', 
                            
                            // 图片相对路径
                            'imgPath' : '${ coPartnerAd.imgPath }', 
                            
                            // 原图url源路径
                            'imgSrc' : '${ coPartnerAd.imgSrc }', 
                            
                            // 缩略图url源路径
                            'imgThumb' : '${ coPartnerAd.imgThumb }'
                        }
                        </c:if>
                    ]
                }
                
            });
            
        }
        
    }
    
    initImageUpload();
</script>
<!-- 图片上传 end -->
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="url">点击链接</label>
								<div class="col-sm-8">
									
									<c:if test="${ empty coPartnerAd.id && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd }">
										<textarea id="url" name="url" class="form-control" rows="6" >${ coPartnerAd.url }</textarea>
									</c:if>
									
									<c:if test="${ !(empty coPartnerAd.id  && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd) }">
										<p class="form-control-static">
											${ coPartnerAd.url }
										</p>
									</c:if>
									
								</div>
							</div>
							
						</div>
						
						<div class="row">

							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="startTime">开始时间</label>
								<div class="col-sm-8">
									<c:if test="${ empty coPartnerAd.id && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd }">
										<input id="startTime" name="startTime" type="text" 
											value="<fmt:formatDate value="${ coPartnerAd.startTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" 
											class="form-control" onfocus="this.blur();" data-rule-required="true" data-msg-required="请输入开始时间" />
										<script type="text/javascript">
											laydate({
												istime: true,
												elem : '#startTime',
												format : 'YYYY-MM-DD hh:mm:ss',
											});
										</script>
									</c:if>
									
									<c:if test="${ !(empty coPartnerAd.id  && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd) }">
										<p class="form-control-static">
											<fmt:formatDate value="${ coPartnerAd.startTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</c:if>
									
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="endTime">结束时间</label>
								<div class="col-sm-8">
									<c:if test="${ empty coPartnerAd.id && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd }">
										<input id="endTime" name="endTime" type="text" 
											value="<fmt:formatDate value="${ coPartnerAd.endTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" 
											class="form-control" onfocus="this.blur();" data-rule-required="true" data-msg-required="请输入结束时间" />
										<script type="text/javascript">
											laydate({
												istime: true,
												elem : '#endTime',
												format : 'YYYY-MM-DD hh:mm:ss'
											});
										</script>
									</c:if>
									
									<c:if test="${ !(empty coPartnerAd.id  && not empty AddCoPartnerAd 
										|| not empty coPartnerAd.id && not empty EditCoPartnerAd) }">
										<p class="form-control-static">
											<fmt:formatDate value="${ coPartnerAd.endTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</c:if>
									
								</div>
							</div>
							
						</div>
						
					</div>
					
					</div>
							</div>
							
							<div id="tab-coPartnerAdTag" class="tab-pane">
                            	<div class="panel-body">
                            		<div id="toolbarCoPartnerAdTag" class="btn-group" role="group">
										<button id="btn_add_coPartnerAdTag" type="button" class="btn btn-outline btn-default">
											<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
											新增
										</button>
										<button id="btn_del_coPartnerAdTag" type="button" class="btn btn-outline btn-default">
											<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>
											删除
										</button>
									</div>
                            	
                            	
<table id="listViewCoPartnerAdTag"
		       data-toolbar="#toolbarCoPartnerAdTag"
		       data-url="coPartnerAdTag/coPartnerAdTagListData.json?coPartnerAdId=${coPartnerAd.id }"
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
		       data-side-pagination="server">
			<thead>
				<th data-checkbox="true"></th>
				<th data-field="coPartnerAdTitle" data-width="35%">商家广告活动</th>
				<th data-field="typeName"  data-width="15%">类型</th>
				<th data-field="title"  data-width="35%">标题</th>
				<th data-field="sortNumber"  data-width="15%">排序号</th>
			</thead>
		</table>
		
<script type="text/javascript">
    
    // 格式化时间列
    var fmtDate = function(value) {
        return $.isNumeric(value) ? (new Date(value)).format('yyyy-MM-dd HH:mm:ss') : '';
    }
    
</script>
<script type="text/javascript">
	
	var oListViewCoPartnerAdTag;
	
	function initListViewCoPartnerAdTag() {
        
        if ( $.isNull( oListViewCoPartnerAdTag ) ) {
            
        	oListViewCoPartnerAdTag = new ListView({
        		'id' : 'listViewCoPartnerAdTag',
    			'rowAttributes' : function(row, index) {
    				// 设置鼠标移到行上显示为手型
    				return {
    					'style' : 'cursor:pointer;'
    				};
    			},
    			'onClickRow' : function(row, $element, field) {       // 行点击事件
    				if ( field != 0 ) {
    					 openWin({
    				        'title' : '广告活动标签详情',
    				        'target' : 'menuTab',
    				        'url' : 'coPartnerAdTag/coPartnerAdTagRecord.do?id=' + row.id
    					 });
    				}
    			}
            });
        }
        
    }
	
	$(function() {
		
		// “新增”按钮点击事件
		$('#btn_add_coPartnerAdTag').click(function() {
			
			var id = $('#id').val();
			
			openWin({
		        'title' : '新增广告活动标签',
		        'target' : 'menuTab',
		        'url' : 'coPartnerAdTag/coPartnerAdTagRecord.do?coPartnerAdId='+id
		    });
		});
		
		
		// “删除”按钮点击事件
		$('#btn_del_coPartnerAdTag').click(function() {
			
			var ids = oListViewCoPartnerAdTag.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要删除的广告活动标签！');
				return;
			}
			
			Confirm({
				msg : '确认删除选中广告活动标签？',
				ok : function() {
					
					$('#btn_del_coPartnerAdTag').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "coPartnerAdTag/delCoPartnerAdTag.json",
						data : {
							'ids' : ids
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_del_coPartnerAdTag').prop('disabled', false);
							if (data.success) {
								oListViewCoPartnerAdTag.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_del_coPartnerAdTag').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
		
		$(window).resize(function () {
			
			oListViewCoPartnerAdTag.resetView({
				height: getHeight()
			});
			
		});
		
	});
	
</script>
								</div>
							</div> 
							
						</div>
					</div>
					
<script type="text/javascript">
    $('a[data-toggle="tab"]').on('shown.bs.tab',function(e){
        var target = $(e.target)[0].hash;
        if (target == '#tab-coPartnerAdTag') {
        	initListViewCoPartnerAdTag();
        }
    });
</script>		
				</form>
				
				
			</div>
			
		</div>
		
	</div>
				
<script type="text/javascript">
	
	$(function() {
		
		$('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
		
		
		function submitForm() {
		    $.post(
				'coPartnerAd/submitCoPartnerAd.json', 
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
		
		$('#btn_refresh').click(function(){
            window.location.reload();
        });
		
	});
</script>

</body>
</html>