<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html class="no-js">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width" />
<title>驾车路线规划</title>
<meta name="Keywords" content="油惠站合作商" />
<meta name="Description" content="油惠站合作商" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<style type="text/css">
body, html {
	width: 100%;
	height: 100%;
	margin: 0;
	font-family: "微软雅黑";
}

#allmap {
	height: 100vw;
	width: 100%;
}

#r-result, #r-result table {
	width: 100%;
	height: 70vw;
}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RICZXAsZd6DBPrWiAsM37HA1NiynsqSP"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
</head>
<body>
<style type="text/css">
.sel_n .sel_body_name {
    height: 35px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin: 0 20px 0 30px;
    padding: 8px 7px 7px;
    font-size: 14px;
    color: #FA8722;
}
</style>
	<div id="allmap"></div>
	<div id="r-result" style="position: fixed; overflow: scroll;"></div>
	<script type="text/javascript">

// 百度地图API功能
var map = new BMap.Map("allmap");
map.centerAndZoom(new BMap.Point(sessionStorage.getItem("currentLon"),
		sessionStorage.getItem("currentLat")), 11);

var p1 = new BMap.Point(sessionStorage.getItem("currentLon"),
		sessionStorage.getItem("currentLat"));
var p2 = new BMap.Point(${coPartner.lon},${coPartner.lat});

var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
var driving = new BMap.DrivingRoute(map, {renderOptions: {map: map, panel: "r-result", autoViewport: true}});
driving.search(p1, p2);
</script>
</body>
</html>

