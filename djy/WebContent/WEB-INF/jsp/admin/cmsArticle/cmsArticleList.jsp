<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	
	<title>文章信息列表</title>
	
	<%@include file="../decorators/listViewHead.jsp"%>
	
	<!-- jsTree -->
	<link href="plugins/jsTree/themes/default/style.min.css?v=3.3.3" rel="stylesheet">
	<script src="plugins/jsTree/jstree.min.js?v=3.3.3" type="text/javascript"></script>
	
	<script src="js/treeView.js" type="text/javascript"></script>
	
</head>
<body>
	
	<div id="panel_search" class="panel panel-default panelSearch">
	
		<div class="panel-heading clearfix">
			<div class="pull-left">
				查询条件：
				<a id="btn_clearSearch" class="text-muted hidden">
					<span>清除查询</span>
					<i class="fa fa-times"></i>
				</a>
			</div>
			<a id="btn_slideSearch" class="text-muted pull-right">
				<span>展开</span>
				<i class="fa fa-angle-down"></i>
			</a>
		</div>
		
		<div id="panel_search_cont" class="panel-body">
			
			<div class="form-horizontal">
				
				<div class="form-group col-sm-4">
					<label class="control-label col-sm-4" for="txt_search_code">标识符</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="txt_search_code">
					</div>
				</div>
				
				<div class="form-group col-sm-8">
					<label class="control-label col-sm-2 text-nowrap">插入时间</label>
					<div class="col-sm-10">
						<div class="form-group col-sm-6">
							<div class="input-group">
								<div class="input-group-addon">从</div>
								<input type="text" class="input-sm form-control layer-date" id="txt_search_insertTimeStart" placeholder="开始日期" onfocus="this.blur();">
							</div>
						</div>
						<div class="form-group col-sm-6">
							<div class="input-group">
								<div class="input-group-addon">至</div>
								<input type="text" class="input-sm form-control layer-date" id="txt_search_insertTimeEnd" placeholder="结束日期" onfocus="this.blur();" >
							</div>
						</div>
					</div>
				</div>
				
				<div class="form-group col-sm-4">
					<label class="control-label col-sm-4" for="txt_search_title">标题</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="txt_search_title">
					</div>
				</div>
				
				<div class="form-group col-sm-4">
					<label class="control-label col-sm-4" for="txt_search_cmsCatg">内容分类</label>
					<div class="col-sm-8">
						<!--内容分类树型 start  -->
						<input type="hidden" id="cmsCatgId" name="cmsCatgId"  value="" />
						<input type="text" class="form-control" id="cmsCatgName" name="cmsCatgName" value=""  onclick="showCmsCatgTree();" onfocus="this.blur();"/>
						
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
										//alert("orgOrStoreId="+data.selected[0]);
										$('#cmsCatgId').val(data.selected[0]);
										$('#cmsCatgName').val(data.instance.get_node(data.selected[0]).text);
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
					</div>
				</div>
				
				<div class="form-group col-sm-4">
					<div class="col-sm-offset-4 col-sm-8">
						<button id="btn_search" type="button" class="btn btn-primary" >查询</button>
						<button id="btn_cancelSearch" type="button" class="btn btn-white hidden" >取消</button>
					</div>
				</div>
					
			</div>
			
		</div>
			
	</div>
	
	<script type="text/javascript">
		function slideUpSearch(param) {
			$('#btn_slideSearch span').text('展开');
			$('#btn_slideSearch i').removeClass('fa-angle-up').addClass('fa-angle-down');
			var callback = param? param.callback: null;
			$('#panel_search_cont').slideUp('slow', 'swing', callback);
		}
		
		function slideDownSearch(param) {
			$('#btn_slideSearch span').text('折叠');
			$('#btn_slideSearch i').removeClass('fa-angle-down').addClass('fa-angle-up');
			var callback = param? param.callback: null;
			$('#panel_search_cont').slideDown('slow', 'swing', callback);
		}
		
		$('#btn_slideSearch').click(function(){
			if ($('#panel_search_cont').css('display') == 'none') {
				slideDownSearch({
					callback : function(){
						resetViewHeight();
					}
				});
			} else {
				slideUpSearch({
					callback : function(){
						resetViewHeight();
					}
				});
			}
		});
		
		// 上架开始结束时间
		var start = {
			elem : '#txt_search_insertTimeStart',
			format : 'YYYY-MM-DD',
			choose : function(datas) {
				end.min = datas;
				end.start = datas;
			},
			clear : function() {
				end.min = null;
				end.start = null;
			}
		}
		var end = {
			elem : '#txt_search_insertTimeEnd',
			format : 'YYYY-MM-DD',
			choose : function(datas) {
				start.max = datas;
			},
			clear : function() {
				start.max = null;
			}
		}
		laydate(start);
		laydate(end);
		
	</script>
	
	
	<div class="view">
		
		<div id="toolbar" class="btn-group" role="group">
			<!--判断当前登录用户是否拥有相应权限，若有权限则可进行相应操作 -->
			<c:if test="${not empty AddCmsArticle }">
				<button id="btn_add" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
					新增
				</button>
			</c:if>
			<c:if test="${not empty DisableCmsArticle }">
				<button id="btn_disable" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-ban-circle" aria-hidden="true"></i>
					禁用
				</button>
			</c:if>
			<c:if test="${not empty EnableCmsArticle }">
				<button id="btn_enable" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-ok-circle" aria-hidden="true"></i>
					启用
				</button>
			</c:if>
			<c:if test="${not empty DelCmsArticle }">
				<button id="btn_del" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>
					删除
				</button>
			</c:if>
		</div>
		
		<table id="listView"
		       data-toolbar="#toolbar"
		       data-url="cmsArticle/cmsArticleListData.json"
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
				<c:if test="${not empty DisableCmsArticle || not empty DisableCmsArticle || not empty DelCmsArticle }">
					<th data-checkbox="true"></th>
				</c:if>
				<th data-field="code" data-width="20%">标识符</th>
				<th data-field="cmsCatgNamePath"  data-width="25%">内容分类</th>
				<th data-field="title"  data-width="20%">标题</th>
				<th data-field="statusName"  data-width="20%">状态</th>
				<th data-field="insertTime" data-sortable="true" data-formatter="fmtDate" data-width="15%">插入时间</th>
			</thead>
		</table>
		
<script type="text/javascript">
    
    // 格式化时间列
    var fmtDate = function(value) {
        return $.isNumeric(value) ? (new Date(value)).format('yyyy-MM-dd HH:mm:ss') : '';
    }
    
</script>
		
	</div>
	

<script type="text/javascript">
	
	var oListView;
	
	function getHeight() {
		return $(window).height() - $('#panel_search').outerHeight(true);
	}
	
	function resetViewHeight() {
		oListView.resetView({
			height: getHeight()
		});
	}
	
	$(function() {
		
		// 初始化视图
		oListView = new ListView({
			'id' : 'listView',
			'height' : getHeight(),
			'rowAttributes' : function(row, index) {
				// 设置鼠标移到行上显示为手型
				return {
					'style' : 'cursor:pointer;'
				};
			},
			'onClickRow' : function(row, $element, field) {       // 行点击事件
				if ( field != 0 ) {
					 openWin({
				        'title' : '文章信息详情',
				        'target' : 'menuTab',
				        'url' : 'cmsArticle/cmsArticleRecord.do?id=' + row.id
					 });
				}
			}
		});
		
		// “查询”按钮点击事件
		$('#btn_search').click(function() {
			slideUpSearch({
				callback : function() {
					resetViewHeight();
					oListView.search({
						'code' : $('#txt_search_code').val(),
						'title' : $('#txt_search_title').val(),
						'cmsCatgId' : $('#cmsCatgId').val(),
						'cmsCatgName' : $('#cmsCatgName').val(),
						'insertTimeStart' : $('#txt_search_insertTimeStart').val(),
						'insertTimeEnd' : $('#txt_search_insertTimeEnd').val()
						
					});
					$('#btn_clearSearch').removeClass('hidden');
					$('#btn_cancelSearch').removeClass('hidden');
				}
			});
		});
		
		// “清除查询”按钮点击事件
		$('#btn_clearSearch').click(function(){
			slideUpSearch({
				callback : function() {
					resetViewHeight();
					oListView.clearSearch();
					$('#btn_clearSearch').addClass('hidden');
					$('#btn_cancelSearch').addClass('hidden');
					$('#txt_search_code').val('');
					$('#txt_search_title').val('');
					$('#cmsCatgId').val('');
					$('#cmsCatgName').val('');
					$('#txt_search_insertTimeStart').val('');
					$('#txt_search_insertTimeEnd').val('');
				}
			});
		});
		
		// “清除查询”按钮点击事件
		$('#btn_cancelSearch').click(function() {
			$('#btn_clearSearch').click();
		});
		
		// “新增”按钮点击事件
		$('#btn_add').click(function() {
			openWin({
		        'title' : '新增文章',
		        'target' : 'menuTab',
		        'url' : 'cmsArticle/cmsArticleRecord.do'
		    });
		});
		
		// “禁用”按钮点击事件
		$('#btn_disable').click(function() {
			
			var ids = oListView.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要禁用的文章！');
				return;
			}
			
			Confirm({
				msg : '确认禁用选中文章？',
				ok : function() {
					
					$('#btn_disable').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "cmsArticle/disableCmsArticle.json",
						data : {
							'ids' : ids
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_disable').prop('disabled', false);
							if (data.success) {
								oListView.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_disable').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
		// “上架”按钮点击事件
		$('#btn_enable').click(function() {
			
			var ids = oListView.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要启用的文章！');
				return;
			}
			
			Confirm({
				msg : '确认启用选中文章？',
				ok : function() {
					
					$('#btn_enable').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "cmsArticle/enableCmsArticle.json",
						data : {
							'ids' : ids
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_enable').prop('disabled', false);
							if (data.success) {
								oListView.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_enable').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
		// “删除”按钮点击事件
		$('#btn_del').click(function() {
			
			var ids = oListView.getSelIds();
			if (ids.length == 0) {
				Alert('请选择要删除的文章！');
				return;
			}
			
			Confirm({
				msg : '确认删除选中文章？',
				ok : function() {
					
					$('#btn_del').prop('disabled', true);
					
					$.ajax({
						type : "POST",
						url : "cmsArticle/delCmsArticle.json",
						data : {
							'ids' : ids
						},
						dataType : "json",
						success : function(data, textStatus) {
							$('#btn_del').prop('disabled', false);
							if (data.success) {
								oListView.refresh();
								Alert(data.msg);
							} else if(data.msg != null) {
								Alert(data.msg);
							} else {
								Alert('程序错误，请联系管理员！');
							}
						},
						error : function(xmlHttpRequest, textStatus, errorThrown) {
							$('#btn_del').prop('disabled', false);
							Alert('连接错误，请联系管理员！');
						}
					});
				}
			});
			
		});
		
		
		$(window).resize(function () {
			
			oListView.resetView({
				height: getHeight()
			});
			
		});
		
	});
	
</script>

</body>
</html>