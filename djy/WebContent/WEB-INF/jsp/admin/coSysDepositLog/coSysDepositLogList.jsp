<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	
	<title>平台预存款明细列表</title>
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
					<label class="control-label col-sm-4" for="txt_search_name">合作商名称</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="txt_search_name">
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
					<label class="control-label col-sm-4" for="txt_search_incomeExpense">收支类型</label>
					<div class="col-sm-8">
						<select class="form-control" name="txt_search_incomeExpense" id="txt_search_incomeExpense" data-placeholder="请选择">
							<option value="">请选择</option>
							<c:forEach var="i" items="${coSysDepositLogIncomeExpense}">
								<option value="${i.id }" >${i.name }</option>
							</c:forEach>
						</select>
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
				<span id="expenseTotal">预存款支出总额：<span id="expenseTotalAmount"></span>元；&nbsp;</span>
				<span id="incomeTotal">预存款收入总额：<span id="incomeTotalAmount"></span>元</span>
			</h4>
		</div>
		
		<table id="listView" 
		       data-toolbar="#toolbar" 
		       data-url="coSysDepositLog/coSysDepositLogListData.json" 
		       data-id-field="id" 
		       data-unique-id="id" 
		       data-sort-name="id" 
		       data-sort-order="desc" 
		       data-sortable="true" 
		       data-search="false" 
		       data-strict-search="false" 
		       data-show-refresh="false" 
		       data-show-toggle="false" 
		       data-show-columns="false" 
		       data-pagination="true" 
		       data-page-size="10" 
		       data-side-pagination="server">
			<thead>
				<th data-field="coPartnerName" data-width="30%">合作商家</th>
				<th data-field="typeName" data-width="15%">业务类型</th>
				<th data-field="incomeExpenseName" data-width="10%">收支类型</th>
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
				return;
			}
		});
		
		function searchSysDeposit(flag){
			
			//若是点击“清空查询”或“取消”按钮，则将参数赋值为空
			
			if (flag) {
				$('#txt_search_name').val('');
				$('#txt_search_incomeExpense').val('');
				$('#txt_search_insertTimeStart').val('');
				$('#txt_search_insertTimeEnd').val('');
			}
			
			$.ajax({
				type : 'POST',
				url : 'coSysDepositLog/countCoSysDeposit.do',
				data : {
					'coPartnerName' : $('#txt_search_name').val(),
					'incomeExpense' : $('#txt_search_incomeExpense').val(),
					'insertTimeStart' : $('#txt_search_insertTimeStart').val(),
					'insertTimeEnd' : $('#txt_search_insertTimeEnd').val()
				},
				dataType : 'json',
				success : function(data, textStatus) {
					if (data.success) {
						var incomeExpense = $('#txt_search_incomeExpense').val();
						//alert("incomeExpense="+incomeExpense);
						
						if (incomeExpense == '') {
							$('#expenseTotalAmount').html((data.data.expenseTotalAmount).toFixed(2));
							$('#incomeTotalAmount').html((data.data.incomeTotalAmount).toFixed(2));
							$('#expenseTotal').removeClass('hidden');
							$('#incomeTotal').removeClass('hidden');
						} else if (incomeExpense == -1) {
							$('#expenseTotalAmount').html((data.data.expenseTotalAmount).toFixed(2));
							$('#incomeTotal').addClass('hidden');
							$('#expenseTotal').removeClass('hidden');
						} else if (incomeExpense == 1){
							$('#incomeTotalAmount').html((data.data.incomeTotalAmount).toFixed(2));
							$('#expenseTotal').addClass('hidden');
							$('#incomeTotal').removeClass('hidden');
						} else {
							$('#expenseTotal').addClass('hidden');
							$('#incomeTotal').addClass('hidden');
						}
						
						
					} else {
						Alert("error");
					}
				},
				error : function(xmlHttpRequest, textStatus, errorThrown) {
					Alert('连接错误，请联系管理员！');
				}
			});
		}
		var flag = false;
		searchSysDeposit(flag);
		
		// “查询”按钮点击事件
		$('#btn_search').click(function() {
			slideUpSearch({
				callback : function() {
					resetViewHeight();
					oListView.search({
						'coPartnerName' : $('#txt_search_name').val(),
						'incomeExpense' : $('#txt_search_incomeExpense').val(),
						'insertTimeStart' : $('#txt_search_insertTimeStart').val(),
						'insertTimeEnd' : $('#txt_search_insertTimeEnd').val()
						
					});
					$('#btn_clearSearch').removeClass('hidden');
					$('#btn_cancelSearch').removeClass('hidden');
					
					flag = false;
					searchSysDeposit(flag);
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
					$('#txt_search_name').val('');
					$('#txt_search_incomeExpense').val(''),
					$('#txt_search_insertTimeStart').val('');
					$('#txt_search_insertTimeEnd').val('');
				}
			});
			
			flag = true;
			searchSysDeposit(flag);
		});
		
		// “清除查询”按钮点击事件
		$('#btn_cancelSearch').click(function() {
			$('#btn_clearSearch').click();
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