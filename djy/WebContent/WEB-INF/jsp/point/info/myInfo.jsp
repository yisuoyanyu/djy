<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>我的信息</title>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		
	<!-- webuploader -->
	<link rel="stylesheet" type="text/css" href="plugins/upload/webuploader/webuploader.css?v=0.1.5" />
	<script type="text/javascript" src="plugins/upload/webuploader/webuploader.js?v=0.1.5"></script>
	
	<!-- imageUpload 是基于 webuploader 封装 -->
	<link rel="stylesheet" type="text/css" href="js/imageUpload/imageUpload.css" />
	<script type="text/javascript" src="js/imageUpload/imageUpload.js"></script>
	
	<script type="text/javascript" src="js/form.js"></script>
	
		  <style type="text/css">
			input::-ms-input-placeholder {
				text-align: left;
				color:blue;
			}
			
			input::-webkit-input-placeholder {
				text-align: left;
				color:blue;
			}
			</style> 
	</head>
	<body class="bg_main"> 
  <div class="bg_topmain"> 
   <div class="am-vertical-align" style="text-align:center;height:24vw"> 
    <div class="bus_top_title"> 
     <span class="bus_top_titlespan">我的信息</span> 
    </div> 
    <div class="bus_top_imgdiv"> 
     <img src="img/icon_business.png" class="bus_top_img" /> 
    </div> 
   </div> 
   <img src="img/top-buytaocan.jpg" class="meal_div_top_img" /> 
  </div> 
  <div class="point_shalow_div"> 
   <div class="bus_mian_div">
    <span class="bus_mian_topspan">*如果您所填信息有误,可点“修改信息”进行修改!</span>
   </div>
      <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-dianming" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_mian_mynameinput" type="text" value="${coPartner.name}" readonly="readonly" /> 
     </div> 
    </div> 
   </div> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-xingming1" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_mian_mynameinput" type="text"  value="${coPartner.contact}" readonly="readonly" /> 
     </div> 
    </div> 
   </div> 
      <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-shouji1" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_mian_mynameinput" type="text"  value="${coPartner.contactMobile}" readonly="readonly" /> 
     </div> 
    </div> 
   </div> 
      <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-zuoji" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_mian_mynameinput" type="text"  value="${coPartner.telephone}" readonly="readonly" /> 
     </div> 
    </div> 
   </div> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-yingyezhizhao" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_mian_mynameinput" type="text" value="${coPartner.businessUicenseNo}" readonly="readonly" /> 
     </div> 
    </div> 
   </div> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-yingyezhizhao" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_mian_mynameinput" type="text" value="${coPartner.slogan}" readonly="readonly" /> 
     </div> 
    </div> 
   </div>
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-site-copy" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input type="text" class="bus_mian_mynameinput" style="width: 20%" value="${coPartner.province}" readonly="readonly"/>
     <input  type="text" class="bus_mian_mynameinput" style="width: 20%" value="${coPartner.city}" readonly="readonly" />
     <input type="text" class="bus_mian_mynameinput" style="width: 20%" value="${coPartner.county}" readonly="readonly"/>
     
    </div> 
    </div> 
   </div> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-jiedao" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_mian_mynameinput" type="text" value="${coPartner.town}" readonly="readonly" /> 
     </div> 
    </div> 
   </div>
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-addressqingxi" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_mian_mynameinput" type="text" value="${coPartner.address}" readonly="readonly" /> 
     </div> 
    </div> 
   </div>
      <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative;display: none;"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-tubiao04" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_mian_mynameinput" type="text"  style="width: 30%"  value="${coPartner.lon}" readonly="readonly"/> 
        <input class="bus_mian_mynameinput" type="text"   style="width: 30%"   value="${coPartner.lat}" readonly="readonly"/> 
     </div> 
    </div> 
   </div>
   
   
   <div class="bus_mian_div">
       <span class="bus_mian_topspan">门店照片</span>
   </div>
   <div style="padding:5px;margin-bottom:5px;">
<!-- 图片上传 start -->
<div id="imageUpload"></div>
<script type="text/javascript">
    
    var oImageUpload;
    
    function initImageUpload() {
        
        if ( $.isNull( oImageUpload ) ) {
            
            oImageUpload = new ImageUpload({
                
                'id' : 'imageUpload', 
                
                // 是否允许编辑。默认true。
                // true — 允许编辑（上传、删除），false — 不允许编辑。
                'edit' : false, 
                
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
    
    initImageUpload();
    
</script>
<!-- 图片上传 end -->
   </div>
   
   
   <div class="full_ccenter_div"> 
    <a href="javascript:location.replace('info/myInfoEdit.do');"><span class="button blue bigrounded" style="margin-top:6vw;margin-bottom:6vw">　修 改 信 息　</span></a> 
   </div> 
  </div> 
  <script type="text/javascript">
  document.body.addEventListener('touchstart', function () {});
  </script>
 </body>
</html>