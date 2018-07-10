package com.djy.copartner.vo;

import java.util.List;

import com.djy.co.enumtype.CoPartnerTagType;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerTag;
import com.frame.base.utils.StringUtil;

public class CopartnerVo {

	private String info;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public static CopartnerVo getCopartnerVo(String lat,String lon,CoPartner coPartner) {
		String distance = "";
		if (!StringUtil.isBlank(lat) || !StringUtil.isBlank(lon)) {
		   distance = getDistance(lat, lon, coPartner.getLat(), coPartner.getLon())+"KM";	
		}
		
		String tags = "";
		List<CoPartnerTag> coPartnerTags = coPartner.getCoPartnerTags();
		
		for(int i = 0;i<coPartnerTags.size();i++){
			CoPartnerTag coPartnerTag = coPartnerTags.get(i);
			String joz = "";
			String title = "";
			title = coPartnerTag.getTitle();

			if (coPartnerTag.getType() == CoPartnerTagType.minus.getId()) {
				joz = "<span class=\"activity-jian\">减</span>&nbsp;&nbsp;"+title+"</div>";
			}
			if (coPartnerTag.getType() == CoPartnerTagType.discount.getId()) {
				joz = "<span class=\"activity-zhe\">折</span>&nbsp;&nbsp;"+title+"</div>";
			}
			if (coPartnerTag.getType() == CoPartnerTagType.give.getId()) {
				joz = "<span class=\"activity-song\">送</span>&nbsp;&nbsp;"+title+"</div>";
			}
			
			if (i == 0) {
				tags += "<div class=\"am-text-sm\" style=\"font-size:4vw;padding-top:8vw;color:#666;\">"   
				         +joz;
			}else {
				tags += "<div class=\"am-text-sm\" style=\"font-size:4vw;color:#666;\">"          
				         +joz;
			}
		}
		String info = "<li class=\"am-padding-xs\" style=\"border-top: 1px solid #dedede;\">"
         +"<a href=\"javascript:copartnerDetail("+coPartner.getId()+")\">" 
         +"<div class=\"item\" style=\"margin-top: -8vw;\">"
         +"<div class=\"am-g\">"
         +"<div class=\"am-u-sm-4 am-padding-xs\" style=\"padding-top:3vw;\">"
         +"<img src=\""+coPartner.getLogoSrc()+"\" class=\"am-img-responsive\" style=\"height:90px;width:90px;\" /></div>"
         +"<div class=\"am-u-sm-8 am-padding-xs\" style=\"padding-left: 0rem;\">"
         +" <div class=\"title\" style=\"font-size:5vw;\">"+coPartner.getName()
         +"<span class=\"activity-distance\">"+distance+"</span></div>"
         +"<span style=\"margin-top: 1.5vw;\" class=\"activity-tradename\">["+coPartner.getTown()+"]"+coPartner.getSlogan()+"</span>"       
         +tags
         +"</div>"
         +"</div>"          
         +"</div>"   
         +" </a>"
         +"</li>";
		CopartnerVo copartnerVo = new CopartnerVo();
		copartnerVo.setInfo(info);
		return copartnerVo;
	}
	
	   
	private static double EARTH_RADIUS = 6378.137; 
	    
	    private static double rad(double d) { 
	        return d * Math.PI / 180.0; 
	    }
	
    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);
          
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;
        String distanceStr = distance+"";
        distanceStr = distanceStr.
            substring(0, distanceStr.indexOf("."));
          
        return distanceStr;
    }
}
