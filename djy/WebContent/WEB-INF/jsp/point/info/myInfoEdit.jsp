<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<!doctype html>
<html class="no-js">
	<head>
		<title>修改信息</title>
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
		
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RICZXAsZd6DBPrWiAsM37HA1NiynsqSP"></script>
		  <style type="text/css">
			input::-ms-input-placeholder {
				text-align: left;
				color:#999;
			}
			
			input::-webkit-input-placeholder {
				text-align: left;
				color:#999;
			}
		  </style> 
	</head>
	 <body class="bg_main"> 
  <div class="bg_topmain"> 
   <div class="am-vertical-align" style="text-align:center;height:24vw"> 
    <div class="bus_top_title"> 
     <span class="bus_top_titlespan">商家信息</span> 
    </div> 
    <div class="bus_top_imgdiv"> 
     <img src="img/icon_business.png" class="bus_top_img" /> 
    </div> 
   </div> 
   <img src="img/top-buytaocan.jpg" class="meal_div_top_img" /> 
  </div> 
  <div class="point_shalow_div"> 
   <div class="bus_mian_div">
    <span class="bus_mian_topspan">*为了保障您的账户安全,请您进行身份认证!</span>
   </div> 
   <form id="coPartnerForm" method="post" enctype="multipart/form-data">
   <input id="userId" name="userId" type="hidden" value="${ coPartner.user.id }" />
   <input id="id" name="id" type="hidden" value="${ coPartner.id }" />
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-dianming" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_main_nameinput" type="text" name="name" id="name" value="${coPartner.name}" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\ ]/g,'')" placeholder="请输入加油站名" /> 
     </div> 
    </div> 
   </div> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-xingming1" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_main_nameinput" type="text" name="contact" id="contact" value="${coPartner.contact}" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\ ]/g,'')" placeholder="请输入联系人姓名" /> 
     </div> 
    </div> 
   </div> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-shouji1" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_main_nameinput" type="number" name="contactMobile" id="contactMobile" value="${coPartner.contactMobile}" onchange="checkContactMobile();" placeholder="请输入联系人电话" /> 
     </div> 
    </div> 
   </div> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-zuoji" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_main_nameinput" type="tel" name="telephone" id="telephone" value="${coPartner.telephone}" onchange="checkphone();" placeholder="请输入座机" /> 
     </div> 
    </div> 
   </div> 
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-yingyezhizhao" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_main_nameinput" type="text" name="businessUicenseNo" id="businessUicenseNo" value="${coPartner.businessUicenseNo}" placeholder="请输入营业执照注册号" /> 
     </div> 
    </div> 
   </div>

    <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-yingyezhizhao" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_main_nameinput" type="text" name="slogan" id="slogan" value="${coPartner.slogan}" placeholder="请输入您的油站广告语" /> 
     </div> 
    </div> 
   </div>
   
   <div class="bus_mian_div">
    <span class="bus_mian_topspan">找到您公司的位置，点击屏幕获取地址信息</span>
    <div style="padding-bottom: 1vw"><input id="where" placeholder="输入地址查找" name="where" type="text" style="border-radius: 0;border-left-width:0px;border-top-width:0px;border-right-width:0px;border-bottom-color:black">  
  <input type="button" class="button blue bigrounded" style="width: 30%;padding: 0; box-shadow: 0 0px 0px #58a1ff; height: 8vw; border-radius: 3vw;" value="地图上找" onClick="sear(document.getElementById('where').value);" />  
   </div>
   </div>
    <div id='allmap' style='width: 100%; height: 200px;'></div>
      <div class="lidiv" id="ID_Address" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-site-copy" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv">  
     <input id="province" name="province" readonly="readonly" value="${coPartner.province}" type="text" class="bus_main_nameinput" style="width: 20%" placeholder="省"/>
     <input id="city" name="city" readonly="readonly" value="${coPartner.city}" type="text" class="bus_main_nameinput" style="width: 20%" placeholder="市" />
     <input id="county" name="county" readonly="readonly" value="${coPartner.county}" type="text" class="bus_main_nameinput" style="width: 20%" placeholder="县/区"/>
     </div> 
    </div> 
   </div>
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-jiedao" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_main_nameinput" type="text" value="${coPartner.town}" name="town" id="town" placeholder="请输入公司街道" /> 
     </div> 
    </div> 
   </div> 
    <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-addressqingxi" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_main_nameinput" type="text" value="${coPartner.address}" name="address" id="address" placeholder="请输入公司详细地址" /> 
     </div> 
    </div> 
   </div>
   <div class="lidiv" style="border-bottom:solid 1px #c9c9c9;position:relative;display: none;"> 
    <div class="bus_mian_inputdiv"> 
     <div style="float:left"> 
      <i class="iconfont icon-tubiao04" style="color:#476df6"></i> 
     </div> 
     <div class="bus_main_nameinputdiv"> 
      <input class="bus_main_nameinput" readonly="readonly" type="hidden" value="${coPartner.lon}" name="lon" style="width: 30%" id="lon" placeholder="经度" /> 
        <input class="bus_main_nameinput" readonly="readonly" type="hidden" value="${coPartner.lat}" name="lat" style="width: 30%" id="lat" placeholder="纬度" /> 

     </div> 
    </div> 
   </div>


   <div class="bus_mian_div">
       <span class="bus_mian_topspan">上传门店照片</span>
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
                'edit' : true, 
                
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

   </form>
   <div class="full_ccenter_div"> 
    <span id="btn_submit" class="button blue bigrounded" style="margin-top:6vw;margin-bottom:6vw">　提 交 信 息　</span> 
   </div> 
  </div>  
<script type="text/javascript">
	document.body.addEventListener('touchstart', function () {});
	// 百度地图API功能
	
	var myCity = new BMap.LocalCity();
	var map = new BMap.Map("allmap");    // 创建Map实例

	map.disableDoubleClickZoom();

	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	  
	map.addControl(top_left_navigation);     
	
	myCity.get( function(result){
	    // 初始化地图,设置中心点坐标和地图级别 
	    map.centerAndZoom(new BMap.Point(${coPartner.lon}, ${coPartner.lat}), result.level); 
	    //添加地图类型控件
	    map.addControl(new BMap.MapTypeControl());
	    //开启鼠标滚轮缩放
	    map.enableScrollWheelZoom(true);
	    
	    map.clearOverlays();
	    var point = new BMap.Point(${coPartner.lon},${coPartner.lat}); 
		var marker = new BMap.Marker(point);
		map.addOverlay(marker);                     // 将标注添加到地图中
	}); 
	
	function showInfo(e){
	
        var geoc = new BMap.Geocoder();   //地址解析对象  
        geoc.getLocation(e.point, function (rs) {  
            var addComp = rs.addressComponents;  
            var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;  
             
            $.confirm("确定要地址是：" + address + "?",function(result){
          	  if(result){
          		map.clearOverlays();
				var point = new BMap.Point(e.point.lng,e.point.lat); 
				var marker = new BMap.Marker(point);
				map.addOverlay(marker);                     // 将标注添加到地图中
				
				document.getElementById('lon').value = e.point.lng;  
                document.getElementById('lat').value =  e.point.lat; 
                document.getElementById('province').value = addComp.province; 
                document.getElementById('city').value = addComp.city; 
                document.getElementById('county').value = addComp.district; 
                document.getElementById('town').value = addComp.street; 
                document.getElementById('address').value = addComp.streetNumber; 
          	  }else{
          	    
          	  }
          	})
            
        }); 
	}
	//map.addEventListener("click", showInfo);
	map.addEventListener('click', showInfo);
	map.addEventListener('dragstart', showInfo);
	
	
	function sear(result){//地图搜索  
	    var local = new BMap.LocalSearch(map, {  
	        renderOptions:{map: map}  
	    });  
	    local.search(result);  
	}
	
	function checkContactMobile(){
		var contactMobile = $("#contactMobile").val();
		if (!contactMobile == "") {
			if(!(/^1(3|4|5|7|8)\d{9}$/.test(contactMobile))){
				Alert("请输入正确格式的联系人手机号");
				return false;
			}
		}
	}
	
	function checkphone(){
		 var phone = $("#phone").val();
		 if(!phone == ""){
			 if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(phone)){
				  Alert("请输入正确格式的座机电话");
				  return false;
			  }
		 }
	}
	
	function checkSubmit() {
		
		var fields = ['name','contact', 'contactMobile','businessUicenseNo','slogan','province','city','county','town','address','lon','lat'];
		var fnames = ['加油站名', '联系人', '联系人电话','营业执照','广告语','省份','城市','县/区','街道','详细地址','经度','纬度'];
		for(var i = 0; i < fields.length; i++) {
			if($('#' + fields[i]).val().replace(/(^\s*)|(\s*$)/g, "")=='') {
				$('#'+fields[i]).attr("placeholder", "必填");
				Alert(fnames[i] + '不能为空');
				return false;
			}
		}
		return true;
	}
	
	function submitForm(){
	    
		$.post(
			"info/submitAuthor.do",
			$('#coPartnerForm').serializeArray(),
			function(ret){
				if(ret.success){
					Alert({
						msg : '修改成功',
						ok : function() {
							window.location.replace('info/myInfo.do');
						}
					});
				} else {
					alert(ret.data);
				}
			}
		);
	}
    
	
    $('#btn_submit').click( function () {
        
        if ( !checkSubmit() ) {
            return false;
        }
        
        $('#btn_submit').prop('disabled', true);
        
        // 提交表单
        preUpload( {
            success : function () {
                submitForm();
            }
        } );
        
    } );
    
</script>
 </body>
</html>