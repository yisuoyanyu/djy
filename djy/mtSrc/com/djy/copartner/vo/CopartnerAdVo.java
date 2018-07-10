package com.djy.copartner.vo;

import java.text.SimpleDateFormat;
import java.util.List;

import com.djy.co.enumtype.CoPartnerTagType;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerAd;
import com.djy.co.model.CoPartnerAdTag;
import com.frame.base.utils.StringUtil;

public class CopartnerAdVo {

	private String info;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public static CopartnerAdVo getCopartnerVo(CoPartner coPartner) {
		List<CoPartnerAd> coPartnerAds = coPartner.getCoPartnerAds();
		
		String ulli = "";
		
		for(int i=0;i<coPartnerAds.size();i++){
			SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
			CoPartnerAd coPartnerAd = coPartnerAds.get(i);
			String startTime;
			String tags = "";
			String url ="";
			String timeSpan = "";
			if (!StringUtil.isBlank(coPartnerAd.getUrl())) {
				url = coPartnerAd.getUrl();
			}else {
				url = "copartnerad/activityDetail.do?activityId="+coPartnerAd.getId();
			}
			List<CoPartnerAdTag> coPartnerAdTags = coPartnerAd.getCoPartnerAdTags();

			if (coPartnerAdTags.size()>0) {
				timeSpan = "<div style=\"font-size:3.5vw;margin-top:8vw;color:#888888;\">";
			}else {
				timeSpan = "<div style=\"font-size:3.5vw;margin-top:3vw;color:#888888;\">";
			}
			
			for(int m = 0;m<coPartnerAdTags.size();m++){
				CoPartnerAdTag coPartnerAdTag = coPartnerAdTags.get(m);
				String spanClass = "";
				
				if (m == 0 && coPartnerAdTags.size()>1) {
					spanClass += "activity-tradename1";
				}else if (m == 0 && coPartnerAdTags.size()==1) {
					spanClass += "activity-tradename2";
				}else {
					spanClass += "activity-tradename";
				}
				if(coPartnerAdTag.getType() == CoPartnerTagType.minus.getId()){//满减
					tags += "<span class=\""+spanClass+"\"><span class=\"activity-jian\">减</span>&nbsp;&nbsp;"+coPartnerAdTag.getTitle()+"</span>";
				}
				if(coPartnerAdTag.getType() == CoPartnerTagType.discount.getId()){//打折
					tags += "<span class=\""+spanClass+"\"><span class=\"activity-zhe\">折</span>&nbsp;&nbsp;"+coPartnerAdTag.getTitle()+"</span>";
				}
				if(coPartnerAdTag.getType() == CoPartnerTagType.give.getId()){//赠送
					tags += "<span class=\""+spanClass+"\"><span class=\"activity-song\">送</span>&nbsp;&nbsp;"+coPartnerAdTag.getTitle()+"</span>";
				}
			}
			if (null != coPartnerAd.getStartTime()) {
				startTime = sf.format(coPartnerAd.getStartTime());
			}else {
				startTime = "现在";
			}
			String endTime;
			if (null != coPartnerAd.getEndTime()) {
				endTime = sf.format(coPartnerAd.getEndTime());
			}else {
				endTime = "无限制";
			}	
				ulli += "<li class=\"am-padding-xs\" style=\"border-top: 1px solid #dedede;\">"
		                +"<a href=\""+url+"\">"
		                +"<div class=\"item\">"
		                +"<div class=\"am-g\">"
		                +"<div class=\"am-u-sm-4 am-padding-xs\" style=\"padding-top:3vw;\">"
				        +"<img src=\""+coPartnerAd.getImgSrc()+"\" style=\"height:90px;\" class=\"am-img-responsive\" /></div>"
				        +"<div class=\"am-u-sm-8 am-padding-xs\">"
				        +"<div class=\"title\" style=\"font-size:4.5vw;font-weight:bolder;\">"+coPartnerAd.getTitle()
				        +"</div>"
		                +tags
		                +timeSpan
				        +"<i class=\"icon iconfont icon-shijian\" style=\"font-size:4vw;\"></i>&nbsp;"+startTime+"-"+endTime+"</div>"
				        +"</div>"
		                +"</div>"
		                +"</a>"
		                +"</li>";
		}
		
		String info="<div data-am-widget=\"titlebar\" class=\"am-titlebar am-titlebar-default\">"
				+"<h2 class=\"am-titlebar-title\">"+coPartner.getName()+"</h2>"
				+"</div>"
                +" <div class=\"listInfo bg_white\">"
                +"<ul class=\"am-list am-margin-0\">"
                +ulli
		        +"</ul>"
		        +"</div>"
		        +"<div class=\"blank1\"></div>";	
		CopartnerAdVo copartnerAdVo = new CopartnerAdVo();
		copartnerAdVo.setInfo(info);
		return copartnerAdVo;
	}
}
