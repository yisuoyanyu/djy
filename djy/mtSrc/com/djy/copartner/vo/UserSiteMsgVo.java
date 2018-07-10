package com.djy.copartner.vo;

import java.text.SimpleDateFormat;

import com.djy.user.enumtype.MessageType;
import com.djy.user.model.UserSiteMsg;

public class UserSiteMsgVo {

	private String time;
	private String title;
	private String imgUrl;
	private String content;
	private String url;
	private String info;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public static UserSiteMsgVo getUserSiteMsgVo(UserSiteMsg userSiteMsg) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	    String time = sf.format(userSiteMsg.getInsertTime());
		String title = "";
		String imgUrl = "";
		String content = "";
		String url = "";
		
		if (userSiteMsg.getType() == MessageType.system.getId()) {//表示为系统消息
			title = "实时系统消息提醒";
			imgUrl = "../copartner/img/ico_quan.png";//暂时用优惠券图标替代
			content = userSiteMsg.getContent();
			url = "JavaScript:addTimeAndgo('',"+userSiteMsg.getId()+");";//暂时不跳转地方
		}
		if (userSiteMsg.getType() == MessageType.order.getId()) {//表示为订单消息
			title = "成功交易信息提醒";
			imgUrl = userSiteMsg.getConsumeOrder().getCoPartner().getLogoSrc();//商家logo
			content = userSiteMsg.getContent();
			url = "javascript:copartnerDetail("+userSiteMsg.getConsumeOrder().getCoPartner().getId()+")";//跳转到商家详情页面
			url = "JavaScript:addTimeAndgo("+"'"+url+"'"+","+userSiteMsg.getId()+");";
		}
		if (userSiteMsg.getType() == MessageType.coudiscount.getId()) {//表示为打折券消息
			title = "优惠券到账提醒";
			imgUrl = "../copartner/img/ico_quan.png";//优惠券图标
			content = userSiteMsg.getContent();
			url = "couponDiscount/noUserList.do";//跳转到个人没有使用的优惠券的列表中去
			url = "JavaScript:addTimeAndgo("+"'"+url+"'"+","+userSiteMsg.getId()+");";
		}
		
		String info = "<li>"
					  +"<a href=\""+url+"\" >"
					  +"<div class=\"quantime\"><span>"+time+"<span></div>"
					  +"<div class=\"quantitle\">"
					  +title
					  +"</div>"
					  +"<div class=\"am-g\">"
					  +"<div class=\"am-u-sm-4\"><img class=\"quanimg\" src=\""+imgUrl+"\"/></div>"
					  +"<div class=\"am-u-sm-8 msg-detail\">"+content+"</div>"
					  +"</div>"
					  +"<div class=\"am-container\" style=\"margin:10px 0px 0px 0px\">"
					  +"<div class=\"quandetail\"><span>查看详情</span><span style=\"float:right\">></span></div>"
					  +"</div>"
					  +"</a>"
					  +"</li>";
		UserSiteMsgVo userSiteMsgVo = new UserSiteMsgVo();
		userSiteMsgVo.setInfo(info);
		return userSiteMsgVo;
		
	}
}
