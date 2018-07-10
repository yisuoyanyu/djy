<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld" %>

<!DOCTYPE html>
<html>
<head>
	<title>套打模板信息</title>
	
	<%@include file="../decorators/formHead.jsp"%>
	
	<!-- webuploader -->
	<link rel="stylesheet" type="text/css" href="plugins/upload/webuploader/webuploader.css?v=0.1.5" />
	<script type="text/javascript" src="plugins/upload/webuploader/webuploader.js?v=0.1.5"></script>
	
	<!-- fileUpload 是基于 webuploader 封装 -->
	<link rel="stylesheet" type="text/css" href="js/fileUpload/fileUpload.css" />
	<script type="text/javascript" src="js/fileUpload/fileUpload.js"></script>
	
</head>
<body class="gray-bg">

	<div class="wrapper wrapper-content">
	
		<div class="ibox float-e-margins">
			
			<div class="ibox-title">
				<h5>
					<c:if test="${ empty sysPrtTmpl.id }">新增套打模板</c:if>
					<c:if test="${ not empty sysPrtTmpl.id }">套打模板信息</c:if>
				</h5>
				
				<div class="ibox-tools">
					<a class="close-link" href="javascript:closeForm();"><i class="fa fa-times"></i></a>
				</div>
			</div>
			
			<div class="ibox-content">
				
				<form id="form" class="form-horizontal" method="post" enctype="multipart/form-data">
					
					<% /*--这个hidden的input需要输出到浏览器端--*/ %>
					<input id="id" name="id" type="hidden" value="${ sysPrtTmpl.id }" />
					
					<div class="container">
					
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="code">标识符</label>
								<div class="col-sm-8">
									<c:if test="${ empty sysPrtTmpl.id && not empty AddSysPrtTmpl 
												|| not empty sysPrtTmpl.id && not empty EditSysPrtTmpl }">
										<input id="code" name="code" type="text" value="${ sysPrtTmpl.code }" class="form-control" 
											data-rule-required="true" data-msg-required="请输入标识符" 
										/>
									</c:if>
									<c:if test="${ !(empty sysPrtTmpl.id  && not empty AddSysPrtTmpl 
												|| not empty sysPrtTmpl.id && not empty EditSysPrtTmpl) }">
										<p class="form-control-static">${sysPrtTmpl.code }</p>
									</c:if>
								</div>
							</div>
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="title">标题</label>
								<div class="col-sm-8">
									<c:if test="${ empty sysPrtTmpl.id && not empty AddSysPrtTmpl 
												|| not empty sysPrtTmpl.id && not empty EditSysPrtTmpl }">
										<input id="title" name="title" type="text" value="${ sysPrtTmpl.title }" class="form-control" 
											data-rule-required="true" data-msg-required="请输入标题" />
									</c:if>
									<c:if test="${ !(empty sysPrtTmpl.id  && not empty AddSysPrtTmpl 
												|| not empty sysPrtTmpl.id && not empty EditSysPrtTmpl) }">
										<p class="form-control-static">${sysPrtTmpl.title }</p>
									</c:if>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="fileUpload">模板文件</label>
								<div class="col-sm-8">


<!-- 文件上传 start -->
<div id="fileUpload"></div>
<script type="text/javascript">
    
    var oFileUpload;
    
    function initFileUpload() {
        
        if ( $.isNull( oFileUpload ) ) {
            
            oFileUpload = new FileUpload( {
                
                'id' : 'fileUpload', 
                
                // 是否允许编辑。默认true。
                // true — 允许编辑（上传、删除），false — 不允许编辑。
                'edit' : true, 
                
                // 文件数限制（含已上传、未上传）。默认不限制。
                'fileNumLimit' : 1, 
                
                // 允许上传文件的后缀，多值用“|”分隔，如：“.gif|.jpg|.jpeg|.bmp|.png”
                'acceptSuffix' : '.docx|.doc', 
                
                // 临时上传文件夹属性
                'tmpUpload' : {
                    
                    // 上传临时文件夹路径
                    'path' : 'tmp', 
                    
                    // 上传到临时文件夹的文件路径的参数名称（后端通过该参数名称取值）
                    'param' : 'uploadFiles'
                    
                }, 
                
                // 目标文件夹属性
                'uploadPath' : {
                    
                    // 目标文件夹参数名称（后端通过该参数名称取值）
                    'param' : 'fileUploadPath', 
                    
                    // 目标文件夹路径值
                    'value' : 'files'
                    
                }, 
                
                // 原有文件属性
                'initFile' : {
                    
                    // 原有文件的参数名称（后端通过该参数名称取值）
                    'param' : 'oldFiles', 
                    
                    // 初始化的文件列表
                    'files' : [
                        <c:if test="${ not empty sysPrtTmpl.filePath }">
                        {
                            // 文件标题
                            'title' : '${ sysPrtTmpl.fileTitle }', 
                            
                            // 文件相对路径
                            'filePath' : '${ sysPrtTmpl.filePath }', 
                            
                            // 文件url源路径
                            'fileSrc' : '${ sysPrtTmpl.fileSrc }'
                        }
                        </c:if>
                    ]
                    
                }
            } );
            
        }
        
    }
    
    initFileUpload();
</script>
<!-- 文件上传 end -->
								</div>
							</div>
						</div>
						
						<c:if test="${ not empty sysPrtTmpl.id && not empty EditSysPrtTmpl }">
						<div class="row">
							
							<div class="col-sm-6 form-group">
								<label class="control-label col-sm-4" for="mobile">插入时间</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<fmt:formatDate value="${ sysPrtTmpl.insertTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
									</p>
								</div>
							</div>
							
						</div>
						</c:if>
						
					</div>
					
				</form>
					
				<div class="hr-line-dashed"></div>
				<c:if test="${ empty sysPrtTmpl.id && not empty AddSysPrtTmpl 
								|| not empty sysPrtTmpl.id && not empty EditSysPrtTmpl }">
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
        $( '.i-checks' ).iCheck( {
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green'
        } );
        
        
        function submitForm() {
            $.post(
                'sysPrtTmpl/submitSysPrtTmpl.json', 
                $('#form').serializeArray(), 
                function ( ret ) {
                    if ( ret.success ) {
                        Alert( {
                            msg : '提交成功', 
                            ok : function () {
                                closeForm( {
                                    refreshOpener : true 
                                } );
                            }
                        } );
                    } else {
                        Alert( ret.msg );
                    }
                }
            );
        }
        
        
        $( '#btn_submit' ).click( function () {
            
            // 输入校验
            if ( ! $('#form').valid() )
                return false;
            
            // 提交表单
            preUpload( {
                success : function () {
                    submitForm();
                }
            } );
            
        });
        
        
        $( '#btn_cancel' ).click( function () {
            window.location.reload();
        } );
        
        
    });
    
</script>

</body>
</html>