<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>合作商家信息</title>
	
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
	
	<!--引用百度地图API-->
	<style type="text/css">
		#allmap{height:210px;width:100%;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RICZXAsZd6DBPrWiAsM37HA1NiynsqSP"></script>
	<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
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
					
					<c:if test="${ (not empty EditCoPartner && coPartner.status < normalStatus)}">
						
						<li class="active">
							<a id="btn_submit" role="button" href="javascript:;">
								<i class="fa fa-save"></i>
								审核
							</a>
						</li>
						
					</c:if>
					
					<c:if test="${ (empty coPartner.id || coPartner.status == normalStatus) && not empty EditCoPartner}">
						
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
	
	<!-- 表单内容 -->
	<div class="formBody">
	
		<div class="wrapper wrapper-content">
		
			<div class="ibox float-e-margins">
				
				<div class="ibox-title">
					<h5>
						${coPartner.name } 合作商家信息
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
								<li><a data-toggle="tab" href="#tab_map" aria-expanded="false">商家位置</a></li>
								<li><a data-toggle="tab" href="#tab_img" aria-expanded="false">商家图片</a></li>
								<li><a data-toggle="tab" href="#tab_coMode" aria-expanded="false">合作方式</a></li>
								<c:if test="${ coPartner.status >= normalStatus}">
									<c:if test="${ coPartner.coMode == sysDepositMode}">
										<li class=""><a data-toggle="tab" href="#tab_coSysDepositLog" aria-expanded="false">预存款明细</a></li>
									</c:if>
									<li class=""><a data-toggle="tab" href="#tab_consumeOrder" aria-expanded="false">会员消费</a></li>
									<li class=""><a data-toggle="tab" href="#tab_actCoupon" aria-expanded="false">送券活动</a></li>
									<li class=""><a data-toggle="tab" href="#tab_coPartnerAd" aria-expanded="false">广告活动</a></li>
									<li class=""><a data-toggle="tab" href="#tab_coPartnerTag" aria-expanded="false">商家标签</a></li>
									<li class=""><a data-toggle="tab" href="#tab_coPartnerEmpl" aria-expanded="false">商家员工</a></li>
								</c:if>
							</ul>
							<div class="tab-content">
								
								<div id="tab_basic" class="tab-pane active">
									<div class="panel-body">
<div class="container-fluid">
	
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="userMobile">关联会员</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<c:if test="${ not empty coPartner.user.id }">
						ID：${ coPartner.user.id }
					</c:if>
					<c:if test="${ empty coPartner.user.id }">
						ID：<span id="spanUserID">无</span>
					</c:if>
				</p>
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="userMobile">关联会员手机</label>
			<div class="col-sm-8">
				<input id="userId" name="userId" value="${ coPartner.user.id }" type="hidden" class="form-control"/>
				<c:if test="${ empty coPartner.user.id }">
					<c:if test="${ not empty EditCoPartner }">
						<input id="userMobile" name="userMobile" 
							type="text" value="${coPartner.user.mobile }" class="form-control"  maxlength="11"
							data-rule-required="true" data-msg-required="请输入关联会员手机"
							onblur="searchUser()"/>
					</c:if>
					
					<c:if test="${ !( not empty EditCoPartner ) }">
						<p class="form-control-static">${ coPartner.user.mobile }</p>
					</c:if>
				</c:if>
				<c:if test="${ not empty coPartner.user.id }">
					<p class="form-control-static">
						<c:if test="${not empty coPartner.user.mobile }">
							${ coPartner.user.mobile }
						</c:if>
						<c:if test="${ empty coPartner.user.mobile }">
							无
						</c:if>
					</p>
				</c:if>
			</div>
	<script type="text/javascript">
	    function searchUser() {
	        var mobile = $('#userMobile').val();
	        var coPartnerId = $('#id').val();
	        if (mobile != '') {
	            var param = {};
	            param.mobile = mobile;
	            param.coPartnerId = coPartnerId;
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
	                        $('#userMobile').val(mobile);
	                        $('#spanUserID').text(userId);
	                        
	                    } else if(ret.msg != null) {
	                    	$('#userId').val('');
	                    	$('#spanUserID').text('');
	                        Alert(ret.msg);
	                        return;
	                    } else {
	                    	$('#userId').val('');
	                    	$('#spanUserID').text('');
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
	        }
	    }
	</script>
			</div>
		
		
	</div>
	
	
	
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="password">密码：</label>
			<div class="col-sm-8">
				<input type="hidden" id="oldPassword" name="oldPassword"  value="${coPartner.password }"/>
				<input type="password" class="form-control" id="password" name="password"  
				<c:if test="${ empty coPartner.id }">
					data-rule-required="true" data-msg-required="请输入密码"
				</c:if> 
				/>
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="confirmPassword">确认密码：</label>
			<div class="col-sm-8">
				<input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
					<c:if test="${ empty coPartner.id }">
					data-rule-required="true" data-msg-required="请输入确认密码"
					</c:if> 
					data-rule-equalTo="#password" data-msg-equalTo="两次密码输入不一致" 
				/>
			</div>
		</div>
		
	</div>	
	
	<div class="hr-line-dashed" style="margin:0px 0px 5px 0px;"></div>
	
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="sysEmplMobile">所属职员</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					ID：<span id="spanSysEmplID">
							<c:if test="${empty coPartner.sysEmpl.id }">
								无
							</c:if>
							${coPartner.sysEmpl.id }
						</span>
				</p>
			</div>
		</div>
	
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="sysEmplMobile">所属职员手机</label>
			<input id="sysEmplId" name="sysEmplId" value="${ coPartner.sysEmpl.id }" type="hidden" class="form-control"/>
			<div class="col-sm-8">
				<c:if test="${ not empty EditCoPartner }">
					<input id="sysEmplMobile" name="sysEmplMobile" 
						type="text" value="${coPartner.sysEmpl.mobile }" class="form-control" maxlength="11" onblur="searchSysEmpl()"/>
				</c:if>
				
				<c:if test="${ !( not empty EditCoPartner ) }">
					<p class="form-control-static">${ coPartner.sysEmpl.mobile }</p>
				</c:if>
			</div>
			<script type="text/javascript">
			    function searchSysEmpl() {
			        var mobile = $('#sysEmplMobile').val();
			        if (mobile != '') {
			            var param = {};
			            param.mobile = mobile;
			            $.ajax({
			                type : 'POST',
			                url : 'sysEmpl/getSysEmplByMobile.json', 
			                data : param, 
			                dataType : 'json', 
			                success : function (ret, textStatus) {
			                    if (ret.success) {
			                        var data = ret.data;
			                        var sysEmplId = data.sysEmplId;
			                        
			                        $('#sysEmplId').val(sysEmplId);
			                        $('#sysEmplMobile').val(mobile);
			                        $('#spanSysEmplID').text(sysEmplId);
			                    } else if(ret.msg != null) {
			                    	$('#sysEmplId').val('');
			                    	$('#spanSysEmplID').text('');
			                        Alert(ret.msg);
			                        return;
			                    } else {
			                    	$('#sysEmplId').val('');
			                    	$('#spanSysEmplID').text('');
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
			        }
			    }
			</script>
		</div>
	</div>
	
	<div class="hr-line-dashed" style="margin:0px 0px 5px 0px;"></div>
	
	<c:if test="${ not empty coPartner.id  }">
		<div class="row">
			
			<div class="col-sm-12 form-group">
				<label class="control-label col-sm-2" for="status">状态</label>
				<div class="col-sm-10">
					
					<c:if test="${ not empty EditCoPartner }">
						<c:forEach var="i" items="${ coPartnerStatus }" varStatus="s">
							<label class="checkbox-inline i-checks">
								<input name="status" type="radio" value="${ i.id }" 
									<c:if test="${ i.id==coPartner.status || empty coPartner.status && s.first }">checked</c:if> 
									<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择状态"</c:if> 
								/>
								${ i.value }
							</label>
						</c:forEach>
					</c:if>
					
					<c:if test="${ !( not empty EditCoPartner ) }">
						<p class="form-control-static">${ statusName }</p>
					</c:if>
				</div>
			</div>
			
			
			
		</div>
	</c:if>	
			
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="name">名称</label>
			<div class="col-sm-8">
				
				<c:if test="${ not empty EditCoPartner }">
					<input id="name" name="name" 
						type="text" value="${ coPartner.name }" class="form-control" 
						data-rule-required="true" data-msg-required="请输入名称" />
				</c:if>
				
				<c:if test="${ !( not empty EditCoPartner ) }">
					<p class="form-control-static">${ coPartner.name }</p>
				</c:if>
				
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="businessUicenseNo">营业执照号</label>
			<div class="col-sm-8">
				
				<c:if test="${ not empty EditCoPartner }">
					<input id="businessUicenseNo" name="businessUicenseNo" 
						type="text" value="${ coPartner.businessUicenseNo }" class="form-control"/>
				</c:if>
				
				<c:if test="${ !( not empty EditCoPartner ) }">
					<p class="form-control-static">${ coPartner.businessUicenseNo }</p>
				</c:if>
				
			</div>
		</div>
		
	</div>
	
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="contact">联系人</label>
			<div class="col-sm-8">
				
				<c:if test="${ not empty EditCoPartner }">
					<input id="contact" name="contact" 
						type="text" value="${ coPartner.contact }" class="form-control"/>
				</c:if>
				
				<c:if test="${ !( not empty EditCoPartner ) }">
					<p class="form-control-static">${ coPartner.contact }</p>
				</c:if>
				
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="contactMobile">联系人手机</label>
			<div class="col-sm-8">
				<c:if test="${ not empty EditCoPartner }">
					<input id="contactMobile" name="contactMobile" 
						type="text" value="${coPartner.contactMobile }" class="form-control" />
				</c:if>
				
				<c:if test="${ !( not empty EditCoPartner ) }">
					<p class="form-control-static">${ coPartner.contactMobile }</p>
				</c:if>
				
			</div>

		</div>
		
	</div>
					
	
	<div class="row">
	
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="imageUpload">商家Logo</label>
			<div class="col-sm-8">
<!-- 图片上传 start -->
<div id="logoUpload"></div>
<h5>（建议：分辨率90*90，大小&lt;=500KB，支持JPG、PNG、JPEG）</h5>
<script type="text/javascript">
    
    var oLogoUpload;
    
    function initLogoUpload() {
        
        if ( $.isNull(oLogoUpload) ) {
            
            oLogoUpload = new ImageUpload({
                
                'id' : 'logoUpload', 
                
                <c:if test="${ !(empty coPartner.id  && not empty EditCoPartner || not empty coPartner.id && not empty EditCoPartner) }">
                // 是否允许编辑。默认true。
                // true — 允许编辑（上传、删除），false — 不允许编辑。
                'edit' : false,	
                </c:if>
                
                // 文件数限制（含已上传、未上传）。默认不限制。
                'fileNumLimit' : 1, 
                
                // 临时上传文件夹属性
                'tmpUpload' : {
                    
                    // 上传到临时文件夹的文件路径参数名称（后端通过该参数名称取值）
                    'param' : 'uploadLogos', 
                    
                    // 上传临时文件夹路径
                    'path' : 'tmp'
                    
                }, 
                
                // 目标文件夹属性
                'uploadPath' : {
                    
                    // 目标文件夹参数名称（后端通过该参数名称取值）
                    'param' : 'logoUploadPath', 
                    
                    // 目标文件夹路径值
                    'value' : 'coPartner/images'
                    
                }, 
                
                // 原有图片的属性
                'initImg' : {
                    
                    // 原有图片的参数名称（后端通过该参数名称取值）
                    'param' : 'oldLogos', 
                    
                    // 初始化的图片列表
                    'imgs' : [
                        <c:if test="${ not empty coPartner.logoPath }">
                        {
                            // 图片标题
                            'title' : '', 
                            
                            // 图片相对路径
                            'imgPath' : '${ coPartner.logoPath }', 
                            
                            // 原图url源路径
                            'imgSrc' : '${ coPartner.logoSrc }', 
                            
                            // 缩略图url源路径
                            'imgThumb' : '${ coPartner.logoThumb }'
                        }
                        </c:if>
                    ]
                }
                
            });
            
        }
        
    }
    
    initLogoUpload();
</script>
<!-- 图片上传 end -->
				</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="url">广告语</label>
			<div class="col-sm-8">
				
				<c:if test="${ not empty EditCoPartner }">
					<textarea id="slogan" name="slogan" class="form-control" rows="12" >${ coPartner.slogan }</textarea>
				</c:if>
				
				<c:if test="${ !( not empty EditCoPartner) }">
					<p class="form-control-static">
						${ coPartner.slogan }
					</p>
				</c:if>
				
			</div>
		</div>
	
	</div>
	<c:if test="${ not empty coPartner.user.qrcodeAddress }">
		<div class="row">
			<div class="col-sm-6 form-group">
				<label class="control-label col-sm-4" for="qrcodeAddress">二维码</label>
				<div class="col-sm-8">
					
						<p class="form-control-static">
							<img id="qrcodeAddress" width="300" height="300" src="${coPartner.user.qrcodeAddress}"/>
						</p>
				</div>
	
			</div>
		</div>
	</c:if>		
</div>
					
									</div>
								</div>
							
								<div id="tab_map" class="tab-pane">
									<div class="panel-body">
										
										<div class="container-fluid">
											<div class="row">
												<div class="col-sm-6 form-group">
													<label class="control-label col-sm-4" for="province">所在省</label>
													<div class="col-sm-8">
														
														<c:if test="${ not empty EditCoPartner }">
															<input id="province" name="province" 
																type="text" value="${ coPartner.province }" class="form-control" 
																data-rule-required="true" data-msg-required="请点击地图确认商家所在省"readonly="readonly"/>
														</c:if>
														
														<c:if test="${ !( not empty EditCoPartner ) }">
															<p class="form-control-static">${ coPartner.province }</p>
														</c:if>
														
													</div>
												</div>
												
												<div class="col-sm-6 form-group">
													<label class="control-label col-sm-4" for="city">所在市</label>
													<div class="col-sm-8">
														
														<c:if test="${ not empty EditCoPartner }">
															<input id="city" name="city" 
																type="text" value="${ coPartner.city }" class="form-control"
																data-rule-required="true" data-msg-required="请点击地图确认商家所在市" readonly="readonly"/>
														</c:if>
														
														<c:if test="${ !( not empty EditCoPartner ) }">
															<p class="form-control-static">${ coPartner.city }</p>
														</c:if>
														
													</div>
												</div>
												
											</div>
											
											<div class="row">
												
												<div class="col-sm-6 form-group">
													<label class="control-label col-sm-4" for="county">所在区/县</label>
													<div class="col-sm-8">
														
														<c:if test="${ not empty EditCoPartner }">
															<input id="county" name="county" 
																type="text" value="${ coPartner.county }" class="form-control" 
																data-rule-required="true" data-msg-required="请点击地图确认商家所在区/县" readonly="readonly"/>
														</c:if>
														
														<c:if test="${ !( not empty EditCoPartner ) }">
															<p class="form-control-static">${ coPartner.county }</p>
														</c:if>
														
													</div>
												</div>
												
												<div class="col-sm-6 form-group">
													<label class="control-label col-sm-4" for="town">所在镇/街道</label>
													<div class="col-sm-8">
														
														<c:if test="${ not empty EditCoPartner }">
															<input id="town" name="town" type="text" value="${ coPartner.town }" class="form-control" />
														</c:if>
														
														<c:if test="${ !( not empty EditCoPartner ) }">
															<p class="form-control-static">${ coPartner.town }</p>
														</c:if>
														
													</div>
												</div>
												
											</div>
											
											<div class="row">
												
												<div class="col-sm-6 form-group">
													<label class="control-label col-sm-4" for="address">其它</label>
													<div class="col-sm-8">
														
														<c:if test="${ not empty EditCoPartner }">
															<input id="address" name="address" 
																type="text" value="${ coPartner.address }" class="form-control"/>
														</c:if>
														
														<c:if test="${ !( not empty EditCoPartner ) }">
															<p class="form-control-static">${ coPartner.address }</p>
														</c:if>
														
													</div>
												</div>
												
												<div class="col-sm-6 form-group">
													<label class="control-label col-sm-4" for="search">搜索地址</label>
													<div class="col-sm-8">
														<input id="suggestId" type="text" style="width: 100%;padding-top:8px;border-radius: 0;border-left-width:0px;border-top-width:0px;border-right-width:0px;border-bottom-color:black"/>
														<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
														
													</div>
												</div>
												
											</div>
										</div>
										
										
										<input type="hidden" name="lon" id="lon" value="${coPartner.lon }" />
										<input type="hidden" name="lat" id="lat" value="${coPartner.lat }" />
										
										<div style="color:red;">提示：搜索地址后，请您点击地图上红色中心点确认地址是否正确</div>
										<div id="allmap"></div>
										
<script type="text/javascript">
	var currentLon = $('#lon').val();
	var currentLat = $('#lat').val();
	
	var map;
	
	<!--搜索地址 start -->
	// 百度地图API功能
	function G(id) {
		return document.getElementById(id);
	}
	
	var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
			{"input" : "suggestId"
			,"location" : map
		});
	
	ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
		var str = "";
			var _value = e.fromitem.value;
			var value = "";
			if (e.fromitem.index > -1) {
				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			}    
			str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
			
			value = "";
			if (e.toitem.index > -1) {
				_value = e.toitem.value;
				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			}    
			str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
			G("searchResultPanel").innerHTML = str;
		});

		var myValue;
		ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
		var _value = e.item.value;
			myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
			
			setPlace();
			
		});

		function setPlace(){
			map.clearOverlays();    //清除地图上所有覆盖物
			function myFun(){
				var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
				map.centerAndZoom(pp, 18);
				map.addOverlay(new BMap.Marker(pp));    //添加标注
			}
			var local = new BMap.LocalSearch(map, { //智能搜索
			  onSearchComplete: myFun
			});
			local.search(myValue);
		}
		
		<!--搜索地址 end -->
		
		
		function initCoPartnerMap() {
		
			if ( $.isNull(map) ) {
			
				// 百度地图API功能
				map = new BMap.Map("allmap");    // 创建Map实例
				
				// 用经纬度设置地图中心点
				if(currentLon !='' && currentLat != ''){
					map.clearOverlays(); 
					var point = new BMap.Point(currentLon,currentLat);
					var marker = new BMap.Marker(point);  // 创建标注
					map.addOverlay(marker);              // 将标注添加到地图中
					map.panTo(point); 
					map.centerAndZoom(point, 12);
				} else {
					//根据IP获取当前位置
					var geolocation = new BMap.Geolocation();
					geolocation.getCurrentPosition(function(r){
						if(this.getStatus() == BMAP_STATUS_SUCCESS){
							var point = new BMap.Point(r.point.lng,r.point.lat);
							map.centerAndZoom(point, 12);
							var mk = new BMap.Marker(r.point);
							map.addOverlay(mk);
							map.panTo(r.point);
						}
						else {
							alert('failed'+this.getStatus());
						}        
					},{enableHighAccuracy: true})
				}
				
				map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
				map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
				
				function showInfo(e){
					var geoc = new BMap.Geocoder();//地址解析对象  
					geoc.getLocation(e.point, function (rs) {  
			            var addComp = rs.addressComponents;  
			            var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber; 
			            
			            Confirm({
							msg : '确定要地址是：'+address+'?',
							ok : function() {
								map.clearOverlays();
								var point = new BMap.Point(e.point.lng,e.point.lat); 
								var marker = new BMap.Marker(point);
								map.addOverlay(marker);                     // 将标注添加到地图中
								
								$("#province").val(addComp.province);
								$("#city").val(addComp.city);
								$("#county").val(addComp.district);
								$("#town").val(addComp.street);
								$("#address").val(addComp.streetNumber);
								$("#lon").val(e.point.lng);
								$("#lat").val( e.point.lat);
								
							}
						});
			            
			        }); 
				}
				
				map.addEventListener("click", showInfo);
				
			}
		}
	
	
</script>
									</div>
								</div>
							
								<div id="tab_img" class="tab-pane">
									<div class="panel-body">
<!-- 图片上传 start -->
<h5>（建议：分辨率320*210，大小&lt;=1MB，支持JPG、PNG、JPEG）</h5>
<div id="imageUpload"></div>

<script type="text/javascript">
    
    var oImageUpload;
    
    function initImageUpload() {
        
        if ( $.isNull( oImageUpload ) ) {
            
        	oImageUpload = new ImageUpload({
                
                'id' : 'imageUpload', 
                
                <c:if test="${ !(empty coPartner.id  && not empty EditCoPartner || not empty coPartner.id && not empty EditCoPartner) }">
                // 是否允许编辑。默认true。
                // true — 允许编辑（上传、删除），false — 不允许编辑。
                'edit' : false, 
                </c:if>
                
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
                    'value' : 'coPartner/images'
                    
                }, 
                
                // 原有图片的属性
                'initImg' : {
                    
                    // 原有图片的参数名称（后端通过该参数名称取值）
                    'param' : 'oldImgs', 
                    
                    // 初始化的图片列表
                    'imgs' : [
                        <c:if test="${ not empty coPartner.coPartnerImgs }">
                        <c:forEach var="i" items="${ coPartner.coPartnerImgs }" varStatus="s">
                        <c:if test="${!s.first}">, </c:if>{
                            // 图片标题
                            'title' : '${ i.title }', 
                            
                            // 图片相对路径
                            'imgPath' : '${ i.imgPath }', 
                            
                            // 原图url源路径
                            'imgSrc' : '${ i.imgSrc }', 
                            
                            // 缩略图url源路径
                            'imgThumb' : '${ i.imgThumb }'
                        }
                        </c:forEach>
                        </c:if>
                    ]
                    
                }
                 
            });
            
        }
    }
    
</script>
<!-- 图片上传 end -->

									</div>
								</div>
							
								<div id="tab_coMode" class="tab-pane">
									<div class="panel-body">
									
<div class="container-fluid">
	
	<div class="row">
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="coMode">合作方式</label>
			<div class="col-sm-8">
				
				<c:if test="${ not empty EditCoPartner }">
					<c:forEach var="i" items="${ coPartnerModes }" varStatus="s">
						<label class="checkbox-inline i-checks">
							<input name="coMode" type="radio" value="${ i.id }" 
								<c:if test="${ i.id==coPartner.coMode || empty coPartner.coMode && s.first }">checked</c:if> 
								<c:if test="${ s.first }">data-rule-required="true" data-msg-required="请选择合作方式"</c:if> 
							/>
							${ i.value }
						</label>
					</c:forEach>
					
					<script type="text/javascript">
						$("input[name='coMode']").parent().on('ifChecked', function(event){
							var coMode = $("input[name='coMode']:checked").val();
							checkedCoMode(coMode);
						});
						function checkedCoMode(val) {
							if ( parseInt(val) == 1 ) {
						        $('#divSysDeposit').addClass('hidden');
						    } else if ( parseInt(val) == 2 ) {
						        $('#divSysDeposit').removeClass('hidden');
						    }
						}
					</script>
					
				</c:if>
				
				<c:if test="${ !( not empty EditCoPartner ) }">
					<p class="form-control-static">${ coModeName }</p>
				</c:if>
				
			</div>
		</div>
		
		<div id="divSysSettlePercent" class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="sysSettlePercent">结算折扣百分比</label>
			<div class="col-sm-8">
				
				<c:if test="${ not empty EditCoPartner }">
					<input id="sysSettlePercent" name="sysSettlePercent" 
						type="text" value="<fmt:formatNumber value="${ coPartner.sysSettlePercent }" type="number" groupingUsed="false" maxFractionDigits="2" />" 
						class="form-control" style="width:100px; display:inline;" 
						data-rule-required="true" data-msg-required="请输入结算折扣百分比"
						data-rule-number="true" data-msg-number="结算折扣百分比必须为数值" 
					/> %
					<script type="text/javascript">
						$('#sysSettlePercent').onlyNum();
					</script>
				</c:if>
				
				<c:if test="${ !( not empty EditCoPartner ) }">
					<p class="form-control-static"><fmt:formatNumber value="${ coPartner.sysSettlePercent }" type="number" groupingUsed="false" maxFractionDigits="2" /></p>
				</c:if>
				
			</div>
		</div>
		
	</div>
	
	<div id="divSysDeposit" class="row hidden">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="minSysDeposit">平台最低预存限额</label>
			<div class="col-sm-8">
				
				<c:if test="${ not empty EditCoPartner }">
					<input id="minSysDeposit" name="minSysDeposit" 
						type="text" value="<fmt:formatNumber value="${ coPartner.minSysDeposit }" type="number" groupingUsed="false" maxFractionDigits="2" />" 
						class="form-control" style="width:100px; display:inline;" 
						data-rule-required="true" data-msg-required="请输入平台最低预存限额" 
						data-rule-number="true" data-msg-number="平台最低预存限额必须为数值" 
					/> （元）
					<script type="text/javascript">
						$('#minSysDeposit').onlyNum();
					</script>
				</c:if>
				
				<c:if test="${ !( not empty EditCoPartner ) }">
					<p class="form-control-static"><fmt:formatNumber value="${ coPartner.minSysDeposit }" type="number" groupingUsed="false" maxFractionDigits="2" /></p>
				</c:if>
				
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="maxSysDeposit">平台最高预存限额</label>
			<div class="col-sm-8">
				
				<c:if test="${ not empty EditCoPartner }">
					<input id="maxSysDeposit" name="maxSysDeposit" 
						type="text" value="<fmt:formatNumber value="${ coPartner.maxSysDeposit }" type="number" groupingUsed="false" maxFractionDigits="2" />" 
						class="form-control" style="width:100px; display:inline;" 
						data-rule-required="true" data-msg-required="请输入平台最高预存限额" 
						data-rule-number="true" data-msg-number="平台最高预存限额必须为数值" 
					/> （元）
					<script type="text/javascript">
						$('#maxSysDeposit').onlyNum();
					</script>
				</c:if>
				
				<c:if test="${ !(  not empty EditCoPartner ) }">
					<p class="form-control-static"><fmt:formatNumber value="${ coPartner.maxSysDeposit }" type="number" groupingUsed="false" maxFractionDigits="2" /></p>
				</c:if>
				
			</div>
		</div>
		
	</div>
	
	<div class="row">
			
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="totalCstmConsume">会员消费总额</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					${coPartner.coPartnerAccount.totalCstmConsume }（元）
				</p>
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="totalCstmPay">会员付款总额</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					${coPartner.coPartnerAccount.totalCstmPay }（元）
				</p>
			</div>
		</div>
		
	</div>
									
	<c:if test="${ coPartner.coMode == sysDepositMode}">
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="sysDeposit">平台预存金额</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					${coPartner.coPartnerAccount.sysDeposit }（元）
				</p>
			</div>
		</div>
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="totalSysDeposit">平台充值总额</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					${coPartner.coPartnerAccount.totalSysDeposit }（元）
				</p>
			</div>
		</div>
		
	</div>
	</c:if>
									
	<c:if test="${ coPartner.coMode != sysDepositMode}">
	<div class="row">
		
		<div class="col-sm-6 form-group">
			<label class="control-label col-sm-4" for="totalCstmSettle">平台结算总额</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					${coPartner.coPartnerAccount.totalCstmSettle }（元）
				</p>
			</div>
		</div>
		
	</div>
	</c:if>
</div>
								</div>
									
									</div>
							
								<div id="tab_coSysDepositLog" class="tab-pane">
                            		<div class="panel-body">
                            	
<div id="toolbarCoSysDepositLog" class="btn-group" role="group">
	<h4>
		<span id="expenseTotal">预存款支出总额：<span id="expenseTotalAmount"></span>&nbsp;元；&nbsp;</span>
		<span id="incomeTotal">预存款收入总额：<span id="incomeTotalAmount"></span>&nbsp;元</span>
	</h4>
</div>
                            	
 <table id="listViewCoSysDepositLog" 
       data-toolbar="#toolbarCoSysDepositLog" 
       data-url="coSysDepositLog/coSysDepositLogListData.json?coPartnerId=${coPartner.id}" 
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
		<th data-field="incomeExpenseName" data-width="15%">收支类型</th>
		<th data-field="amount" data-width="15%">金额（元）</th>
		<th data-field="sysDeposit" data-width="15%">平台剩余预存金额（元）</th>
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
	
	
	$(function() {
	
	function searchSysDeposit(){
			
			$.ajax({
				type : 'POST',
				url : 'coSysDepositLog/countCoSysDeposit.do',
				data : {
					'coPartnerId' : $('#id').val()
				},
				dataType : 'json',
				success : function(data, textStatus) {
					if (data.success) {
						$('#expenseTotalAmount').html((data.data.expenseTotalAmount).toFixed(2));
						$('#incomeTotalAmount').html((data.data.incomeTotalAmount).toFixed(2));
						
					} else {
						Alert("error");
					}
				},
				error : function(xmlHttpRequest, textStatus, errorThrown) {
					Alert('连接错误，请联系管理员！');
				}
			});
		}
		
		searchSysDeposit();
		
	});
	
</script>
								</div>
							</div>
							
								<div id="tab_consumeOrder" class="tab-pane">
                            		<div class="panel-body">
                            	
<div id="toolbarConsumeOrder" class="btn-group" role="group">
	<h4>
		<span id="totalCstmConsume">会员消费总额：${coPartner.coPartnerAccount.totalCstmConsume }&nbsp;元；&nbsp;</span>
		<span id="totalCstmPay">会员付款总额：${coPartner.coPartnerAccount.totalCstmPay }&nbsp;元</span>
	</h4>
</div>
                            	
 <table id="listViewConsumeOrder" 
       data-toolbar="#toolbarConsumeOrder" 
       data-url="consumeOrder/consumeOrderListData.json?coPartnerId=${coPartner.id}" 
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
		<th data-field="nickname" data-width="15%">用户名称</th>
		<th data-field="mobile" data-width="15%">用户手机号</th>
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
							
								<div id="tab_actCoupon" class="tab-pane">
                            		<div class="panel-body">
<div id="toolbarActCoupon" class="btn-group" role="group">
	<c:if test="${not empty AddActCoupon }">
		<button id="btn_add_actCoupon" type="button" class="btn btn-outline btn-default">
			<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
			新增
		</button>
	</c:if>
	<c:if test="${not empty DisableActCoupon }">
		<button id="btn_disable_actCoupon" type="button" class="btn btn-outline btn-default">
			<i class="glyphicon glyphicon-ban-circle" aria-hidden="true"></i>
			禁用
		</button>
	</c:if>
	<c:if test="${not empty EnableActCoupon }">
		<button id="btn_enable_actCoupon" type="button" class="btn btn-outline btn-default">
			<i class="glyphicon glyphicon-ok-circle" aria-hidden="true"></i>
			启用
		</button>
	</c:if>
	<c:if test="${not empty DelActCoupon }">
		<button id="btn_del_actCoupon" type="button" class="btn btn-outline btn-default">
			<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>
			删除
		</button>
	</c:if>
</div>
									
<table id="listViewActCoupon"
		       data-toolbar="#toolbarActCoupon"
		       data-url="actCoupon/actCouponListData.json?coPartnerId=${coPartner.id}"
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
				<th data-field="title" data-width="30%">标题</th>
				<th data-field="startTime" data-sortable="true" data-formatter="fmtDate" data-width="15%">活动开始时间</th>
				<th data-field="endTime" data-sortable="true" data-formatter="fmtDate" data-width="15%">活动结束时间</th>
				<th data-field="typeName" data-width="15%">优惠券类型</th>
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
	
	var oListViewActCoupon;
	
	function initListViewActCoupon() {
        
        if ( $.isNull( oListViewActCoupon ) ) {
            
        	oListViewActCoupon = new ListView({
                'id' : 'listViewActCoupon', 
                'rowAttributes' : function(row, index) {
    				// 设置鼠标移到行上显示为手型
    				return {
    					'style' : 'cursor:pointer;'
    				};
    			},
    			'onClickRow' : function(row, $element, field) {       // 行点击事件
    				if ( field != 0 ) {
    					openWin({
    				        'title' : '送券活动详情',
    				        'target' : 'menuTab',
    				        'url' : 'actCoupon/actCouponRecord.do?id=' + row.id
    				    });
    				}
    			}
            });
        }
        
    }
	
	$(function() {
		
		// “新增”按钮点击事件
		$('#btn_add_actCoupon').click(function() {
			
			var id = $('#id').val();
			
			openWin({
		        'title' : '新增送券活动',
		        'target' : 'menuTab',
		        'url' : 'actCoupon/actCouponRecord.do?coPartnerId='+id
		    });
		});
		
		// “禁用”按钮点击事件
		$('#btn_disable_actCoupon').click(function() {
			var status = 2; 
			var ids = oListViewActCoupon.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要禁用的送券活动！');
				return;
			}
			
			Confirm({
				msg : '确认禁用选中送券活动？',
				ok : function() {
					
					$('#btn_disable_actCoupon').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "actCoupon/updateActCoupon.json",
						data : {
							'ids' : ids,
							'status' : status
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_disable_actCoupon').prop('disabled', false);
							if (data.success) {
								oListViewActCoupon.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_disable_actCoupon').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
		// “启用”按钮点击事件
		$('#btn_enable_actCoupon').click(function() {
			var status = 1;
			var ids = oListViewActCoupon.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要启用的送券活动！');
				return;
			}
			
			Confirm({
				msg : '确认启用选中送券活动？',
				ok : function() {
					
					$('#btn_enable_actCoupon').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "actCoupon/updateActCoupon.json",
						data : {
							'ids' : ids,
							'status' : status
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_enable_actCoupon').prop('disabled', false);
							if (data.success) {
								oListViewActCoupon.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_enable_actCoupon').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
		// “删除”按钮点击事件
		$('#btn_del_actCoupon').click(function() {
			
			var ids = oListViewActCoupon.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要删除的送券活动！');
				return;
			}
			
			Confirm({
				msg : '确认删除选中送券活动？',
				ok : function() {
					
					$('#btn_del_actCoupon').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "actCoupon/delActCoupon.json",
						data : {
							'ids' : ids
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_del_actCoupon').prop('disabled', false);
							if (data.success) {
								oListViewActCoupon.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_del_actCoupon').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
	});
	
</script>
								</div>
							</div> 
							
								<div id="tab_coPartnerAd" class="tab-pane">
                            		<div class="panel-body">
<div id="toolbarCoPartnerAd" class="btn-group" role="group">
	<c:if test="${not empty AddCoPartnerAd }">
		<button id="btn_add_coPartnerAd" type="button" class="btn btn-outline btn-default">
			<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
			新增
		</button>
	</c:if>
	<c:if test="${not empty DisableCoPartnerAd }">
		<button id="btn_disable_coPartnerAd" type="button" class="btn btn-outline btn-default">
			<i class="glyphicon glyphicon-ban-circle" aria-hidden="true"></i>
			禁用
		</button>
	</c:if>
	<c:if test="${not empty EnableCoPartnerAd }">
		<button id="btn_enable_coPartnerAd" type="button" class="btn btn-outline btn-default">
			<i class="glyphicon glyphicon-ok-circle" aria-hidden="true"></i>
			启用
		</button>
	</c:if>
	<c:if test="${not empty DelCoPartnerAd }">
		<button id="btn_del_coPartnerAd" type="button" class="btn btn-outline btn-default">
			<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>
			删除
		</button>
	</c:if>
</div>
<table id="listViewCoPartnerAd"
		       data-toolbar="#toolbarCoPartnerAd"
		       data-url="coPartnerAd/coPartnerAdListData.json?coPartnerId=${coPartner.id }"
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
		       data-side-pagination="server">
			<thead>
				<th data-checkbox="true"></th>
				<th data-field="title" data-width="40%">标题</th>
				<th data-field="url"  data-width="25%">图片对应链接</th>
				<th data-field="choiceName"  data-width="10%">精选</th>
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
	
	var oListViewCoPartnerAd;
	
	function initListViewCoPartnerAd() {
        
        if ( $.isNull( oListViewCoPartnerAd ) ) {
            
        	oListViewCoPartnerAd = new ListView({
                'id' : 'listViewCoPartnerAd', 
                'rowAttributes' : function(row, index) {
    				// 设置鼠标移到行上显示为手型
    				return {
    					'style' : 'cursor:pointer;'
    				};
    			},
    			'onClickRow' : function(row, $element, field) {       // 行点击事件
    				if ( field != 0 ) {
    					 openWin({
    				        'title' : '商家活动信息详情',
    				        'target' : 'menuTab',
    				        'url' : 'coPartnerAd/coPartnerAdRecord.do?id=' + row.id
    					 });
    				}
    			}
            });
        }
        
    }
	
	$(function() {
		
		// “新增”按钮点击事件
		$('#btn_add_coPartnerAd').click(function() {
			
			var id = $('#id').val();
			
			openWin({
		        'title' : '新增商家活动',
		        'target' : 'menuTab',
		        'url' : 'coPartnerAd/coPartnerAdRecord.do?coPartnerId='+id
		    });
		});
		
		// “禁用”按钮点击事件
		$('#btn_disable_coPartnerAd').click(function() {
			var status = 0;
			var ids = oListViewCoPartnerAd.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要禁用的商家活动！');
				return;
			}
			
			Confirm({
				msg : '确认禁用选中商家活动？',
				ok : function() {
					
					$('#btn_disable_coPartnerAd').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "coPartnerAd/updateCoPartnerAd.json",
						data : {
							'ids' : ids,
							'status' : status
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_disable_coPartnerAd').prop('disabled', false);
							if (data.success) {
								oListViewCoPartnerAd.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_disable_coPartnerAd').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
		// “启用”按钮点击事件
		$('#btn_enable_coPartnerAd').click(function() {
			var status = 1;
			var ids = oListViewCoPartnerAd.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要启用的广告！');
				return;
			}
			
			Confirm({
				msg : '确认启用选中广告？',
				ok : function() {
					
					$('#btn_enable_coPartnerAd').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "coPartnerAd/updateCoPartnerAd.json",
						data : {
							'ids' : ids,
							'status' : status
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_enable_coPartnerAd').prop('disabled', false);
							if (data.success) {
								oListViewCoPartnerAd.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_enable_coPartnerAd').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
		
		// “删除”按钮点击事件
		$('#btn_del_coPartnerAd').click(function() {
			
			var ids = oListViewCoPartnerAd.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要删除的广告！');
				return;
			}
			
			Confirm({
				msg : '确认删除选中广告？',
				ok : function() {
					
					$('#btn_del_coPartnerAd').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "coPartnerAd/delCoPartnerAd.json",
						data : {
							'ids' : ids
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_del_coPartnerAd').prop('disabled', false);
							if (data.success) {
								oListViewCoPartnerAd.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_del_coPartnerAd').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
	});
	
</script>

								</div>
							</div>     
							
								<div id="tab_coPartnerTag" class="tab-pane">
                            		<div class="panel-body">
<div id="toolbarCoPartnerTag" class="btn-group" role="group">
	<button id="btn_add_coPartnerTag" type="button" class="btn btn-outline btn-default">
		<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
		新增
	</button>
	<button id="btn_del_coPartnerTag" type="button" class="btn btn-outline btn-default">
		<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>
		删除
	</button>
</div>
                            	
                            	
<table id="listViewCoPartnerTag"
		       data-toolbar="#toolbarCoPartnerTag"
		       data-url="coPartnerTag/coPartnerTagListData.json?coPartnerId=${coPartner.id}"
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
		       data-side-pagination="server">
			<thead>
				<th data-checkbox="true"></th>
				<th data-field="title"  data-width="45%">标题</th>
				<th data-field="typeName"  data-width="35%">类型</th>
				<th data-field="sortNumber"  data-width="20%">排序号</th>
			</thead>
		</table>
		
<script type="text/javascript">
    
    // 格式化时间列
    var fmtDate = function(value) {
        return $.isNumeric(value) ? (new Date(value)).format('yyyy-MM-dd HH:mm:ss') : '';
    }
    
</script>
<script type="text/javascript">
	
	var oListViewCoPartnerTag;
	
	function initListViewCoPartnerTag() {
        
        if ( $.isNull( oListViewCoPartnerTag ) ) {
            
        	oListViewCoPartnerTag = new ListView({
        		'id' : 'listViewCoPartnerTag',
    			'rowAttributes' : function(row, index) {
    				// 设置鼠标移到行上显示为手型
    				return {
    					'style' : 'cursor:pointer;'
    				};
    			},
    			'onClickRow' : function(row, $element, field) {       // 行点击事件
    				if ( field != 0 ) {
    					 openWin({
    				        'title' : '商品标签详情',
    				        'target' : 'menuTab',
    				        'url' : 'coPartnerTag/coPartnerTagRecord.do?id=' + row.id
    					 });
    				}
    			}
            });
        }
        
    }
	
	$(function() {
		
		// “新增”按钮点击事件
		$('#btn_add_coPartnerTag').click(function() {
			
			var id = $('#id').val();
			
			openWin({
		        'title' : '新增商品标签',
		        'target' : 'menuTab',
		        'url' : 'coPartnerTag/coPartnerTagRecord.do?coPartnerId='+id
		    });
		});
		
		
		// “删除”按钮点击事件
		$('#btn_del_coPartnerTag').click(function() {
			
			var ids = oListViewCoPartnerTag.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要删除的商品标签！');
				return;
			}
			
			Confirm({
				msg : '确认删除选中商品标签？',
				ok : function() {
					
					$('#btn_del_coPartnerTag').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "coPartnerTag/delCoPartnerTag.json",
						data : {
							'ids' : ids
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_del_coPartnerTag').prop('disabled', false);
							if (data.success) {
								oListViewCoPartnerTag.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_del_coPartnerTag').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
	});
	
</script>
								</div>
							</div> 
								
								<div id="tab_coPartnerEmpl" class="tab-pane">
									<div class="panel-body">
<div id="toolbarCoPartnerEmpl" class="btn-group" role="group">
	<button id="btn_add_coPartnerEmpl" type="button" class="btn btn-outline btn-default">
		<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
		新增
	</button>
	<button id="btn_freeze_coPartnerEmpl" type="button" class="btn btn-outline btn-default">
		<i class="glyphicon glyphicon-ban-circle" aria-hidden="true"></i>
		禁用
	</button>
	<button id="btn_normal_coPartnerEmpl" type="button" class="btn btn-outline btn-default">
		<i class="glyphicon glyphicon-ok-circle" aria-hidden="true"></i>
		启用
	</button>
	<button id="btn_del_coPartnerEmpl" type="button" class="btn btn-outline btn-default">
		<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>
		删除
	</button>
</div>
                            	
                            	
<table id="listViewCoPartnerEmpl"
		       data-toolbar="#toolbarCoPartnerEmpl"
		       data-url="coPartnerEmpl/coPartnerEmplListData.json?coPartnerId=${coPartner.id}"
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
		       data-side-pagination="server">
			<thead>
				<th data-checkbox="true"></th>
				<th data-field="emplID" data-sortable="true" data-width="20%">工号</th>
				<th data-field="realName" data-width="20%">真实姓名</th>
				<th data-field="mobile" data-width="20%">绑定手机</th>
				<th data-field="insertTime" data-sortable="true" data-formatter="fmtDate" data-width="20%">注册时间</th>
				<th data-field="statusName" data-width="20%">状态</th>
			</thead>
		</table>
		
<script type="text/javascript">
    
    // 格式化时间列
    var fmtDate = function(value) {
        return $.isNumeric(value) ? (new Date(value)).format('yyyy-MM-dd HH:mm:ss') : '';
    }
    
</script>
<script type="text/javascript">
	
	var oListViewCoPartnerEmpl;
	
	function initListViewCoPartnerEmpl() {
        
        if ( $.isNull( oListViewCoPartnerEmpl ) ) {
            
        	oListViewCoPartnerEmpl = new ListView({
        		'id' : 'listViewCoPartnerEmpl',
    			'rowAttributes' : function(row, index) {
    				// 设置鼠标移到行上显示为手型
    				return {
    					'style' : 'cursor:pointer;'
    				};
    			},
    			'onClickRow' : function(row, $element, field) {       // 行点击事件
    				if ( field != 0 ) {
    					 openWin({
    				        'title' : '员工详情',
    				        'target' : 'menuTab',
    				        'url' : 'coPartnerEmpl/coPartnerEmplRecord.do?id=' + row.id
    					 });
    				}
    			}
            });
        }
        
    }
	
	$(function() {
		
		// “新增”按钮点击事件
		$('#btn_add_coPartnerEmpl').click(function() {
			
			var id = $('#id').val();
			openWin({
		        'title' : '新增员工',
		        'target' : 'menuTab',
		        'url' : 'coPartnerEmpl/coPartnerEmplRecord.do?coPartnerId='+id
		    });
		});
		
		// “禁用”按钮点击事件
		$('#btn_freeze_coPartnerEmpl').click(function() {
			
			var ids = oListViewCoPartnerEmpl.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要禁用的员工！');
				return;
			}
			
			Confirm({
				msg : '确认禁用选中员工？',
				ok : function() {
					
					$('#btn_freeze_coPartnerEmpl').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "coPartnerEmpl/freezeCoPartnerEmpl.json",
						data : {
							'ids' : ids
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_freeze_coPartnerEmpl').prop('disabled', false);
							if (data.success) {
								oListViewCoPartnerEmpl.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_freeze_coPartnerEmpl').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
		// “启用”按钮点击事件
		$('#btn_normal_coPartnerEmpl').click(function() {
			
			var ids = oListViewCoPartnerEmpl.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要启用的员工！');
				return;
			}
			
			Confirm({
				msg : '确认启用选中员工？',
				ok : function() {
					
					$('#btn_normal_coPartnerEmpl').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "coPartnerEmpl/normalCoPartnerEmpl.json",
						data : {
							'ids' : ids
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_normal_coPartnerEmpl').prop('disabled', false);
							if (data.success) {
								oListViewCoPartnerEmpl.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_normal_coPartnerEmpl').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
		// “删除”按钮点击事件
		$('#btn_del_coPartnerEmpl').click(function() {
			
			var ids = oListViewCoPartnerEmpl.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要删除的员工！');
				return;
			}
			
			Confirm({
				msg : '确认删除选中员工？',
				ok : function() {
					
					$('#btn_del_coPartnerEmpl').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "coPartnerEmpl/delCoPartnerEmpl.json",
						data : {
							'ids' : ids
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_del_coPartnerEmpl').prop('disabled', false);
							if (data.success) {
								oListViewCoPartnerEmpl.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_del_coPartnerEmpl').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
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
        if ( target == '#tab_img' ) {
            initImageUpload();
        } else if ( target == '#tab_map' ) {
        	initCoPartnerMap();
        } else if (target == '#tab_coSysDepositOrder') {
        	initListViewCoSysDepositOrder();
        } else if (target == '#tab_actCoupon') {
        	initListViewActCoupon();
        } else if (target == '#tab_coPartnerAd') {
        	initListViewCoPartnerAd();
        } else if (target == '#tab_coSysDepositLog') {
        	initListViewCoSysDepositLog();
        } else if (target == '#tab_consumeOrder') {
        	initListViewConsumeOrder();
        } else if (target == '#tab_coPartnerTag') {
        	initListViewCoPartnerTag();
        } else if (target == '#tab_coPartnerEmpl') {
        	initListViewCoPartnerEmpl();
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
        
        var val = $("input[name='coMode']:checked").val();
        
        if ( parseInt(val) == 2 ) {
        	$('#divSysDeposit').removeClass('hidden');
        }
        
        function validateForm() {
        	$('#form').validate({ignore:''}).form();
        	
            // 验证 页签内容没有显示隐藏项 的页签
            var keys = [ 'basic','map' ];
            for ( var i=0; i<keys.length; i++ ) {
                var key = keys[i];
                
                var $tab = $('#tab_' + key);
                if ( $tab.length == 0 )
                    continue;
                
                if ( ! $tab.validate({ignore:''}).form() ) {
                    $('a[href="#tab_' + key + '"]').tab('show');
                    return false;
                }
            }
            
            // 验证 合作方式 页签
            var $tab = $('#tab_coMode');
           if ( $tab.length > 0 ) {
                // 验证 结算折扣百分比
                var $div = $('#divSysSettlePercent');
                if ( $div.length > 0 ) {
                    if ( ! $div.validate({ignore:''}).form() ) {
                        $('a[href="#tab_coMode"]').tab('show');
                        return false;
                    }
                }
                
                // “平台预存”模式下
                var coMode = $("input[name='coMode']:checked").val();
                if ( coMode == ${sysDepositMode} ) {
                    var $div = $('#divSysDeposit');
                    if ( $div.length > 0 ) {
                        if ( ! $div.validate({ignore:''}).form() ) {
                            $('a[href="#tab_coMode"]').tab('show');
                            return false;
                        }
                    }
                    
                    var minSysDeposit = $('#minSysDeposit').val();
                    var maxSysDeposit = $('#maxSysDeposit').val();
                    
                    if( Number(minSysDeposit) >= Number(maxSysDeposit) ) {
        				Alert("输入的平台最低预存限额应小于输入的平台最高预存限额！");
        				return false;
        			}
                }
            }
            
            return true;
        }
        
        function submitForm() {
            $.post(
                'coPartner/submitCoPartner.json', 
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
            if ( ! validateForm() ) 
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