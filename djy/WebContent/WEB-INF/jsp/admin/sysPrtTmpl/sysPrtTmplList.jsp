<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	
	<title>工作人员列表</title>
	
	<%@include file="../decorators/listViewHead.jsp"%>
	
</head>
<body>
	
	<!------------------------------- 视图部分 start ------------------------------->
	<div class="view">
		
		<div id="toolbar" class="btn-group" role="group">
			<c:if test="${not empty AddSysPrtTmpl }">
				<button id="btn_add" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
					新增
				</button>
			</c:if>
			<c:if test="${not empty DelSysPrtTmpl }">
				<button id="btn_del" type="button" class="btn btn-outline btn-default">
					<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>
					删除
				</button>
			</c:if>
		</div>
		
		<table id="listView"
		       data-toolbar="#toolbar"
		       data-url="sysPrtTmpl/sysPrtTmplListData.json"
		       data-id-field="id"
		       data-unique-id="id"
		       data-sort-name="id"
		       data-sort-order="desc"
		       data-sortable="true"
		       data-show-refresh="true"
		       data-show-toggle="true"
		       data-show-columns="true"
		       data-pagination="true"
		       data-side-pagination="server">
			<thead>
				<th data-checkbox="true"></th>
				<th data-field="code" data-sortable="true" data-width="20%">标识符</th>
				<th data-field="title" data-width="60%">标题</th>
				<th data-field="insertTime" data-formatter="fmtDate" data-width="20%">插入时间</th>
			</thead>
		</table>
		
<script type="text/javascript">
    
    // 格式化时间列
    var fmtDate = function(value) {
        return $.isNumeric(value) ? (new Date(value)).format('yyyy-MM-dd HH:mm:ss') : '';
    }
    
</script>
		
	</div>
	<!------------------------------- 视图部分 end ------------------------------->

<script type="text/javascript">
    
    var oListView;	// 视图对象
    
    
    // 重新计算视图高度
    function getHeight() {
        return $(window).height();
    }
    
    
    // 重新计算并设置视图高度
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
                        'title' : '套打模板：' + row.title,
                        'target' : 'menuTab', 
                        'url' : 'sysPrtTmpl/sysPrtTmplRecord.do?id=' + row.id
                    });
                }
            }
        });
        
        
        // “新增”按钮点击事件
        $('#btn_add').click(function() {
            openWin({
                'title' : '新建套打模板', 
                'target' : 'menuTab', 
                'url' : 'sysPrtTmpl/sysPrtTmplRecord.do'
            });
        });
        
        
        // “删除”按钮点击事件
        $('#btn_del').click( function() {
            
            var ids = oListView.getSelIds();
            if (ids.length == 0) {
                Alert('请选择要删除的套打模板！');
                return;
            }
            
            Confirm({
                msg : '确认删除选中套打模板？', 
                ok : function() {
                    
                    $('#btn_del').prop('disabled', true);
                    
                    $.ajax({
                        type : "POST", 
                        url : "sysPrtTmpl/delSysPrtTmpl.json", 
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
        
        
        // 调整窗口大小时，重新计算并设置视图高度
        $(window).resize(function () {
            resetViewHeight();
        });
        
        
    });
    
</script>

</body>
</html>