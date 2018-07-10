<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	
	<title>交接班信息列表</title>
	<%@include file="../decorators/listViewHead.jsp"%>
	
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
				
				<div class="form-group col-sm-8">
					<label class="control-label col-sm-2 text-nowrap">交接时间</label>
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
			<h4>
				<span id="handover"><button id="btn_handover" type="button" class="btn btn-primary" >交接班</button></span>
			</h4>
		</div>
		<table id="listView"
		       data-toolbar="#toolbar"
		       data-url="coPartnerShift/coPartnerShiftListData.json"
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
				<th data-field="startTime" data-sortable="true" data-formatter="fmtDate" data-width="15%">开始时间</th>
				<th data-field="endTime" data-formatter="fmtDate" data-width="15%">结束时间</th>
				<th data-field="totalCstmConsume" data-width="10%">会员消费总额</th>
				<th data-field="totalCstmPay" data-width="10%">会员实付总额</th>
				<c:if test="${ empty sysDeposit }">
					<th data-field="totalCstmSettle" data-width="10%">平台结算总额</th>
				</c:if>
				<c:if test="${not empty sysDeposit }"> <!-- 预存金额模式下显示 -->
					<th data-field="totalSysDepositPay" data-width="10%">预存实收总额</th>
					<th data-field="totalSysDeposit" data-width="10%">平台预存总额</th>
					<th data-field="startSysDeposit" data-width="10%">期初平台预存额</th>
					<th data-field="endSysDeposit" data-width="10%">期末平台预存额</th>
				</c:if>
				
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
				        'title' : '交接班记录',
				        'target' : 'menuTab',
				        'url' : 'coPartnerShift/coPartnerShiftRecord.do?id=' + row.id
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
					$('#txt_search_insertTimeStart').val('');
					$('#txt_search_insertTimeEnd').val('');
					
				}
			});
		});
		
		// “清除查询”按钮点击事件
		$('#btn_cancelSearch').click(function() {
			$('#btn_clearSearch').click();
		});
		
		// “交接”按钮点击事件
		$('#btn_handover').click(function() {
			
			var now = (new Date()).format('yyyy-MM-dd HH:mm:ss');
			
			var handoverTimeStart = '';
			
			$.ajax({
				type : "POST",
				url : "coPartnerShift/getLastCoPartnerShiftHandoverTime.json",
				data : {
					'handoverTimeEnd' : now
				},
				dataType : "json",
				success : function(data, textStatus) {
					handoverTimeStart = data.handoverTimeStart;
					Confirm({
						msg : handoverTimeStart != null ? ('上个交接班时间 '+ handoverTimeStart +'，确定现在交接班吗？') : ('上个交接班时间不存在，确定现在交接班吗？'),
						ok : function() {
							
							$.ajax({
								type : "POST",
								url : "coPartnerShift/addCoPartnerShift.json",
								data : {},
								dataType : "json",
								success : function(data, textStatus) {
									if(data.success){
										oListView.search();
									}
								},
								error : function(xmlHttpRequest, textStatus, errorThrown) {
									Alert('连接错误，请联系管理员！');
								}
							});
						}
					});
					
				},
				error : function(xmlHttpRequest, textStatus, errorThrown) {
					Alert('连接错误，请联系管理员！');
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