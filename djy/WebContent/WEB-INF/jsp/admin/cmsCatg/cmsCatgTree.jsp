<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	
	<title>内容分类树形视图</title>
	
	<%@include file="../decorators/treeViewHead.jsp"%>
	
</head>
<body>
	
	<div class="view">
		
		<div class="btn-group" role="group">
			<span style="margin-top:2%;">
				<button id="btn_add_cmsCatg" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
					新增分类
				</button>
				<button id="btn_edit_cmsCatg" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
					编辑分类
				</button>
				<button id="btn_del_cmsCatg" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>
					删除分类
				</button>
				<button id="btn_disable_cmsCatg" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-ban-circle" aria-hidden="true"></i>
					禁用分类
				</button>
				<button id="btn_enable_cmsCatg" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-ok-circle" aria-hidden="true"></i>
					启用分类
				</button>
				<button id="btn_refresh" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-refresh" aria-hidden="true"></i>
					刷新
				</button>
			</span>
		</div>
		
		<hr/>
		
		<div id="treeView"></div>
	
	</div>
	
<script type="text/javascript">
	
	var oTreeView;
	
	$(function() {
		
		oTreeView = new TreeView({
			'id' : 'treeView',
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
										'text' : cmsCatg.name + "("+ cmsCatg.statusName +")",
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
				
			}
		});
		
		
		// “新增目录”按钮点击事件
		$('#btn_add_cmsCatg').click(function() {
			var url = 'cmsCatg/cmsCatgRecord.do';
			
			var selIds = oTreeView.getSelIds();
			var vals = oTreeView.getSelVals();
			//alert("selIds="+selIds);
			if(typeof(selIds) != 'undefined' && selIds != ''){
				var selId = selIds[selIds.length - 1]; //选中最后一个
				//alert("selId="+selId);
				if(vals[0].status == 0){
					Alert('当前内容已被禁用，不能再次新增！');
					return;
				}
				var id = selId.substr(selId.indexOf('_')+1);
				if (typeof(id) != 'undefined'){
					url += '?parentId=' + id;
				}
			}
			
			openWin({
		        'title' : '新增内容',
		        'target' : 'menuTab',
		        'url' : url
		    });
			
		});
		
		
		// “编辑分类”按钮点击事件
		$('#btn_edit_cmsCatg').click(function() {
			var url = 'cmsCatg/cmsCatgRecord.do';
			
			var selIds = oTreeView.getSelIds();
			
			if(typeof(selIds) != 'undefined' && selIds != ''){
				var selId = selIds[selIds.length - 1]; //选中最后一个
				
				var id = selId.substr(selId.indexOf('_')+1);
				//alert("id="+id);
				if (typeof(id) != 'undefined'){
					url += '?id=' + id;
				}
				
			}else{
				Alert('当前未选择商品目录，请选择相应商品目录！');
				return;
			}
			openWin({
		        'title' : '编辑商品目录',
		        'target' : 'menuTab',
		        'url' : url
		    });
		});
		
		
		// “删除目录”按钮点击事件
		$('#btn_del_cmsCatg').click(function() {
			
			var selIds = oTreeView.getSelIds();
			
			if (typeof(selIds) != 'undefined' && selIds != '') {
				var selId = selIds[selIds.length - 1]; //选中最后一个
				var id = selId.substr(selId.indexOf('_')+1);
				Confirm({
					msg : '删除商品目录将会同时删除其子目录，确认删除？',
					ok : function() {
						
						$('#btn_del_cmsCatg').prop('disabled', true);
						
						$.ajax({
							type : "POST",
							url : "cmsCatg/delCmsCatg.json",
							data : {
								'ids' : [parseInt(id)]
							},
							dataType : "json",
							success : function(data, textStatus) {
								$('#btn_del_cmsCatg').prop('disabled', false);
								if (data.success) {
									oTreeView.refresh();
									Alert(data.msg);
								} else if(data.msg != null) {
									Alert(data.msg);
								} else {
									Alert('程序错误，请联系管理员！');
								}
							},
							error : function(xmlHttpRequest, textStatus, errorThrown) {
								$('#btn_del_cmsCatg').prop('disabled', false);
								Alert('连接错误，请联系管理员！');
							}
						});
					}
				});
			}else{
				Alert('当前未选择商品目录，请选择相应商品目录！');
				return;
			}
			
		});
		
		
		// “禁用目录”按钮点击事件
		$('#btn_disable_cmsCatg').click(function() {
			
			var selIds = oTreeView.getSelIds();
			
			if (typeof(selIds) != 'undefined' && selIds != '') {
				var selId = selIds[selIds.length - 1]; //选中最后一个
				var id = selId.substr(selId.indexOf('_')+1);
				Confirm({
					msg : '禁用商品目录将会同时禁用其子目录，确认禁用？',
					ok : function() {
						
						$('#btn_disable_cmsCatg').prop('disabled', true);
						
						$.ajax({
							type : "POST",
							url : "cmsCatg/disableCmsCatg.json",
							data : {
								'ids' : [parseInt(id)]
							},
							dataType : "json",
							success : function(data, textStatus) {
								$('#btn_disable_cmsCatg').prop('disabled', false);
								if (data.success) {
									oTreeView.refresh();
									Alert(data.msg);
								} else if(data.msg != null) {
									Alert(data.msg);
								} else {
									Alert('程序错误，请联系管理员！');
								}
							},
							error : function(xmlHttpRequest, textStatus, errorThrown) {
								$('#btn_disable_cmsCatg').prop('disabled', false);
								Alert('连接错误，请联系管理员！');
							}
						});
					}
				});
				
			}else{
				Alert('当前未选择商品目录，请选择相应商品代理！');
				return;
			}
			
		});
		
		// “启用目录”按钮点击事件
		$('#btn_enable_cmsCatg').click(function() {
			
			var selIds = oTreeView.getSelIds();
			
			if (typeof(selIds) != 'undefined' && selIds != '') {
				var selId = selIds[selIds.length - 1]; //选中最后一个
				var id = selId.substr(selId.indexOf('_')+1);
				Confirm({
					msg : '启用商品目录将会同时启用其子目录，确认启用？',
					ok : function() {
						
						$('#btn_enable_cmsCatg').prop('disabled', true);
						
						$.ajax({
							type : "POST",
							url : "cmsCatg/enableCmsCatg.json",
							data : {
								'ids' : [parseInt(id)]
							},
							dataType : "json",
							success : function(data, textStatus) {
								$('#btn_enable_cmsCatg').prop('disabled', false);
								if (data.success) {
									oTreeView.refresh();
									Alert(data.msg);
								} else if(data.msg != null) {
									Alert(data.msg);
								} else {
									Alert('程序错误，请联系管理员！');
								}
							},
							error : function(xmlHttpRequest, textStatus, errorThrown) {
								$('#btn_enable_cmsCatg').prop('disabled', false);
								Alert('连接错误，请联系管理员！');
							}
						});
					}
				});
			}else{
				Alert('当前未选择商品目录，请选择相应商品目录！');
				return;
			}
			
		});
		
		// “刷新”按钮点击事件
		$('#btn_refresh').click(function() {
			location.reload();
		});
		
	});
	
</script>

</body>
</html>