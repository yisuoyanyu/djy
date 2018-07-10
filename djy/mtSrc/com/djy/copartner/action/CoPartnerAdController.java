package com.djy.copartner.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerAd;
import com.djy.co.service.CoPartnerAdService;
import com.djy.co.service.CoPartnerService;
import com.djy.copartner.vo.CopartnerAdVo;
import com.djy.copartner.vo.CopartnerVo;
import com.djy.point.vo.WxConfigVo;
import com.frame.base.web.controller.WebController;
import com.frame.wechat.api.utils.SignUtil;

@Controller
@RequestMapping("/copartner/copartnerad")
public class CoPartnerAdController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(CoPartnerAdController.class);
	
	@Autowired
	private CoPartnerService coPartnerService;
	@Autowired
	private CoPartnerAdService coPartnerAdService;
	@Autowired
    private HttpServletRequest request;
	
	@RequestMapping("/adList.do")
	public String index(HttpSession session, Model m) {
		
		WxConfigVo wxConfig = WxConfigVo.transfer(SignUtil.getSign(request));
		m.addAttribute("wxConfig", wxConfig);
		return "/copartner/copartnerad/adList";
		
	}
	
	/*获取活动列表*/
	@RequestMapping("/getRecentCopartnerAd.do")
	@ResponseBody
	public List<CopartnerAdVo> getRecentCopartnerAd(
			@RequestParam(value="longitude", required=false) String longitude,
			@RequestParam(value="latitude", required=false) String latitude,
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m){
		
		List<CoPartner> coPartners = coPartnerService.getAdsByDistance(page,latitude, longitude);
		
		List<CopartnerAdVo> copartnerAdVos = new ArrayList<>();
		
		for(int i=0;i<coPartners.size();i++){
			CoPartner coPartner = coPartners.get(i);
			if (coPartner.getCoPartnerAds().size() > 0) {
				CopartnerAdVo copartnerAdVo = CopartnerAdVo.getCopartnerVo(coPartners.get(i));
				copartnerAdVos.add(copartnerAdVo);
			}
			
		}
		
		return copartnerAdVos;
	}
	
	/*活动详情页面*/
	@RequestMapping("/activityDetail.do")
	public String activityDetail(
			@RequestParam(value="activityId", required=false) Integer activityId,
			HttpSession session, Model m) {
		
		CoPartnerAd coPartnerAd = coPartnerAdService.get(activityId);
		
		CoPartner coPartner = coPartnerAd.getCoPartner();
		List<CoPartnerAd> coPartnerAds = coPartner.getCoPartnerAds();
		for(int i = 0;i<coPartnerAds.size();i++){
			CoPartnerAd coPartnerAd2 = coPartnerAds.get(i);
			if (coPartnerAd.getId() == coPartnerAd2.getId()) {
				coPartnerAds.remove(i);
			}
		}
		m.addAttribute("coPartnerAds", coPartnerAds);
		m.addAttribute("coPartnerAd", coPartnerAd);
		WxConfigVo wxConfig = WxConfigVo.transfer(SignUtil.getSign(request));
		m.addAttribute("wxConfig", wxConfig);
		return "/copartner/copartnerad/activityDetail";
		
	}
}
