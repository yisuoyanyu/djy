package com.djy.copartner.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.co.enumtype.CoPartnerStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerAd;
import com.djy.co.model.CoPartnerEmpl;
import com.djy.co.service.CoPartnerAdService;
import com.djy.co.service.CoPartnerEmplService;
import com.djy.co.service.CoPartnerService;
import com.djy.consume.model.ConsumeOrder;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.copartner.vo.CopartnerDetailActCouVo;
import com.djy.copartner.vo.CopartnerVo;
import com.djy.copartner.vo.WechatPayVo;
import com.djy.coupon.enumtype.CouponDiscountStatus;
import com.djy.coupon.model.ActCoupon;
import com.djy.coupon.model.CouponDiscount;
import com.djy.coupon.service.ActCouponService;
import com.djy.coupon.service.CouponDiscountService;
import com.djy.fin.enumtype.FinChannel;
import com.djy.fin.model.FinCharge;
import com.djy.fin.service.FinChargeService;
import com.djy.point.vo.WxConfigVo;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.djy.wxPay.model.WxPayCharge;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;
import com.frame.wechat.api.utils.SignUtil;

@Controller
@RequestMapping("/copartner")
public class CopartnerController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(CopartnerController.class);
	
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConsumeOrderService consumeOrderService; 
	
	@Autowired
	private ActCouponService actCouponService;
	
    @Autowired
    private CoPartnerAdService coPartnerAdService;
    
    @Autowired
	private CouponDiscountService couponDiscountService;
    
    @Autowired
    private FinChargeService finChargeService;
    
    @Autowired
    private CoPartnerEmplService coPartnerEmplService;
    
    
    @RequestMapping("/index.do")
	public String index(HttpSession session, Model m) {
		
		try {
			
			WxConfigVo wxConfig = WxConfigVo.transfer(SignUtil.getSign(request));
			m.addAttribute("wxConfig", wxConfig);
			
			
		} catch (Exception e) {
			System.out.println( e.getMessage() );
			logger.error(e.getMessage(), e);
		}
		
		return "/copartner/index";
		
	}
	/*获取商家详情*/
	@RequestMapping("/copartnerDetail.do")
	public String copartnerDetail(
			@RequestParam(value="id", required=false) int id,
			@RequestParam(value="lat", required=false) String lat,
			@RequestParam(value="lon", required=false) String lon,
			HttpSession session,Model m){
		Integer userId = (Integer) session.getAttribute("userId");
  		User user = userService.getById(userId);
  		
  		CoPartner coPartner = coPartnerService.get(id);
  		m.addAttribute("coPartner", coPartner);
  		
  		List<ConsumeOrder> consumeOrders = consumeOrderService.searchByCoPartnerIds( coPartner.getId() );
  		m.addAttribute("consumeOrders", consumeOrders);
  		
  		/*获取送券活动act_coupon以及商家广告co_partner_ad*/
  		List<ActCoupon> actCoupons = actCouponService.getByCopartner(coPartner);
  		//List<CouponDiscount> couponDiscounts = couponDiscountService.getCouByUserCop(user, coPartner);//获取本用户的未使用的抵用券
  		//List<CopartnerDetailActCouVo> copartnerDetailActCouVos = CopartnerDetailActCouVo.getCopDetailActVo(actCoupons, couponDiscounts);
  		List<CopartnerDetailActCouVo> copartnerDetailActCouVos = new ArrayList<>();
  		for(int i = 0;i<actCoupons.size();i++){
  			ActCoupon actCoupon = actCoupons.get(i);
  			Boolean ifGet = couponDiscountService.ifGet(user, actCoupon);
  			CopartnerDetailActCouVo copartnerDetailActCouVo = new CopartnerDetailActCouVo();
  			if (ifGet) {
				copartnerDetailActCouVo.setStatus(1);//已经获得
			}else {
				copartnerDetailActCouVo.setStatus(0);//暂未获取
			}
  			copartnerDetailActCouVo.setActCoupon(actCoupon);
  			copartnerDetailActCouVos.add(copartnerDetailActCouVo);
  		}
  		
  		try {
	  		Boolean isFit;
	  		if (StringUtil.isBlank(lat) || StringUtil.isBlank(lon)) {
	  			isFit = false;
			}else {
				double lats = Double.parseDouble(lat);
	  	  		double lons = Double.parseDouble(lon);
		  	  	Double distances = 6378.138*2*Math.asin(Math.sqrt(Math.pow(Math.sin((lats*Math.PI/180-Double.parseDouble(coPartner.getLat())*Math.PI/180)/2), 2)+Math.cos(lats*Math.PI/180)*Math.cos(Double.parseDouble(coPartner.getLat())*Math.PI/180)*Math.pow(Math.sin((lons*Math.PI/180-Double.parseDouble(coPartner.getLon())*Math.PI/180)/2), 2)));
				BigDecimal b = new BigDecimal(distances);  
				double exactDistance = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue(); 
				
				if (exactDistance <= 3) {
					isFit = true;
				}else {
					isFit = false;
				}
			}
	  		m.addAttribute("isFit", isFit);
  		} catch (Exception e) {
			// TODO: handle exception
  			System.out.println(e.getMessage());
		}
  		List<CoPartnerAd> coPartnerAds = coPartnerAdService.getByCopartner(coPartner);
  		m.addAttribute("copartnerDetailActCouVos", copartnerDetailActCouVos);
  		m.addAttribute("coPartnerAds", coPartnerAds);
  		WxConfigVo wxConfig = WxConfigVo.transfer( SignUtil.getSign(request) );
		m.addAttribute("wxConfig", wxConfig);
		return "/copartner/cop/copartnerDetail";
	}
	
	
	/*支付确认界面*/
	@RequestMapping("/payConfirm.do")
	public String payConfirm(
			@RequestParam(value="copartnerId", required=false) int copartnerId,
			HttpSession session,Model m) {
		Integer userId = (Integer) session.getAttribute("userId");
  		User user = userService.getById(userId);
  		CoPartner coPartner = coPartnerService.get(copartnerId);
  		
  		long discountNum = couponDiscountService.getCouByUserCopNum(user, coPartner);//获取用户在该店可用的打折券
  		long noPayOrderNum = consumeOrderService.getNoPayOrderNum(user,coPartner);
  		
  		CouponDiscount couponDiscount = couponDiscountService.getCalculateDis(user,copartnerId);
  		if (null != couponDiscount) {
			Double percent = couponDiscount.getDiscountPercent()/10;
			m.addAttribute("percent", percent);
			m.addAttribute("conDiscountId", couponDiscount.getId());
		}else {
			m.addAttribute("percent", 11);//设置其为11折
			m.addAttribute("conDiscountId", 0);
		}
  		
  		m.addAttribute("discountNum", discountNum);
  		m.addAttribute("coPartner", coPartner);
  		m.addAttribute("noPayOrderNum", noPayOrderNum);//未付款的订单的数量
  		
  		/*获取加油站的全部正常状态的加油员*/
  		List<CoPartnerEmpl> coPartnerEmpls = coPartnerEmplService.getAvailableEmpl(coPartner);
  		m.addAttribute("coPartnerEmpls", coPartnerEmpls);
  		
		return "/copartner/cop/payConfirm";
		
	}
	
	/*继续支付确认界面*/
	@RequestMapping("/goonPay.do")
	public String goonPay(
			@RequestParam(value="orderId", required=false) int orderId,
			HttpSession session,Model m) {
		Integer userId = (Integer) session.getAttribute("userId");
  		User user = userService.getById(userId);
  		
  		ConsumeOrder consumeOrder = consumeOrderService.get(orderId);
  		
  		m.addAttribute("user", user);
  		m.addAttribute("consumeOrder", consumeOrder);
		return "/copartner/cop/goonPay";
		
	}
	
	/*继续支付获取微信支付界面*/
	@RequestMapping("/getGoonConWehcatPay.do")
	@ResponseBody
	public Result getGoonConWehcatPay(
			@RequestParam(value="orderId", required=false) Integer orderId,
			HttpServletResponse response,HttpServletRequest request,
			HttpSession session,Model m){
		
		try {
			ConsumeOrder consumeOrder = consumeOrderService.get(orderId);
			
			FinChannel finChannel = FinChannel.wxPub;
			
			// 创建支付订单
			FinCharge finCharge = consumeOrder.getFinCharge();//finChargeService.createByConsumeOrder(consumeOrder, finChannel, payerIp);

			
			if ( finChannel.getId() == FinChannel.wxPub.getId() ) {
				
				WxPayCharge wxPayCharge = finCharge.getWxPayCharge();
				
				WechatPayVo vo = new WechatPayVo();
				vo.setAppId( wxPayCharge.getAppid() );
				vo.setTimeStamp( wxPayCharge.getTimeStamp() );
				vo.setNonceStr( wxPayCharge.getNonceStr() );
				vo.setPackages( wxPayCharge.getPackageStr() );
				vo.setSignType( wxPayCharge.getSignType() );
				vo.setPaySign( wxPayCharge.getPaySign() );
				
		  		return new Result(true, vo);
		  		
			} else {
				
				return new Result(true, null);
				
			}
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return new Result(false, "调用失败");
		
		}
		
	}
	
	/*获取微信支付界面，生成对应订单*/
	@RequestMapping("/getConWehcatPay.do")
	@ResponseBody
	public Result getConWehcatPay(
			@RequestParam(value="consumeAmount", required=false) String consumeAmount,//消费金额
			@RequestParam(value="payAmount", required=false) String payAmount,//支付金额
			@RequestParam(value="copartnerId", required=false) Integer copartnerId,
			@RequestParam(value="conDiscountId", required=false) Integer conDiscountId,
			@RequestParam(value="emplId", required=false) Integer emplId,
			HttpServletResponse response,HttpServletRequest request,
			HttpSession session,Model m){
		
		try {
			CoPartnerEmpl coPartnerEmpl = coPartnerEmplService.get(emplId);
			
			Integer userId = (Integer) session.getAttribute("userId");
			User user = userService.getById(userId);
			
			
			CoPartner coPartner = coPartnerService.get(copartnerId);
			
			// 生成会员消费订单
			ConsumeOrder consumeOrder = consumeOrderService.createConsumeOrder(user, coPartner, Double.valueOf(consumeAmount), Double.valueOf(payAmount));
			
			CouponDiscount couponDiscount = couponDiscountService.get(conDiscountId);
			if ( null != couponDiscount ) {
		  		couponDiscount.setConsumeOrder( consumeOrder );
		  		couponDiscount.setStatus(CouponDiscountStatus.occupy.getId());//修改为已占用的状态
		  		couponDiscountService.update( couponDiscount );	//关联打折券和会员消费订单
			}
			
			FinChannel finChannel = FinChannel.wxPub;
			String payerIp = request.getRemoteAddr();
			
			// 创建支付订单
			FinCharge finCharge = finChargeService.createByConsumeOrder(consumeOrder, finChannel, payerIp);
			consumeOrder.setFinCharge(finCharge);
			if (null != coPartnerEmpl) {//设置对订单进行加油员的设置
				consumeOrder.setCoPartnerEmpl(coPartnerEmpl);
			}
			consumeOrderService.update(consumeOrder);
			
			if ( finChannel.getId() == FinChannel.wxPub.getId() ) {
				
				WxPayCharge wxPayCharge = finCharge.getWxPayCharge();
				
				WechatPayVo vo = new WechatPayVo();
				vo.setAppId( wxPayCharge.getAppid() );
				vo.setTimeStamp( wxPayCharge.getTimeStamp() );
				vo.setNonceStr( wxPayCharge.getNonceStr() );
				vo.setPackages( wxPayCharge.getPackageStr() );
				vo.setSignType( wxPayCharge.getSignType() );
				vo.setPaySign( wxPayCharge.getPaySign() );
				
		  		return new Result(true, vo);
		  		
			} else {
				
				return new Result(true, null);
				
			}
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return new Result(false, "调用失败");
		
		}
		
	}
	
	
	@RequestMapping("/getScanWechat.do")
	@ResponseBody
	public Result getScanWechat(
			HttpSession session, Model m){
		try {
			WxConfigVo wxConfig = WxConfigVo.transfer(SignUtil.getSign(request));
			return new Result(true, wxConfig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new Result(false, "扫描出错");
		}
	}
	
	/*显示个人打折券界面*/
	@RequestMapping("/getAdr.do")
	public String getAdr(
			@RequestParam(value="copartnerId", required=false) int copartnerId,
			HttpSession session,Model m){
  		CoPartner coPartner = coPartnerService.get(copartnerId);

  		m.addAttribute("coPartner", coPartner);
  		
		return "/copartner/cop/getAdr";
		
	}
	
	/*显示个人打折券界面*/
	@RequestMapping("/getCoudiscount.do")
	public String getCoudiscount(
			@RequestParam(value="copartnerId", required=false) int copartnerId,
			@RequestParam(value="chuanPrice", required=false) String chuanPrice,
			HttpSession session,Model m){
		Integer userId = (Integer) session.getAttribute("userId");
  		User user = userService.getById(userId);
  		CoPartner coPartner = coPartnerService.get(copartnerId);
  		
  		Double totalPrice = Double.valueOf(chuanPrice);
  		List<CouponDiscount> couponDiscounts = couponDiscountService.getCouByUserCop(user, coPartner,totalPrice);//获取用户在该店可用的打折券
  		
  		m.addAttribute("couponDiscounts", couponDiscounts);
  		m.addAttribute("coPartner", coPartner);
  		
		return "/copartner/cop/getCoudiscount";
		
	}
	
	/*获取扫描到的商家*/
	@RequestMapping("/getScanCopartner.do")
	@ResponseBody
	public Result getScanCopartner(
			@RequestParam(value="url", required=false) String url,
			HttpSession session, Model m){
		
		User user = userService.getUserByUrl(url);
		
		if (null != user) {
			CoPartner coPartner = user.getCoPartner();
			if (null != coPartner) {
				
				if (coPartner.getStatus() == CoPartnerStatus.normal.getId()) {
					
					return new Result(true, coPartner.getId());
					
				}else {
					
					return new Result(false, "该门店暂时处于不可用状态！");
					
				}
				
			}else {
				
				return new Result(false, "请扫描店员提供的二维码！");
				
			}
		}else {
			
			return new Result(false, "请扫描店员提供的二维码！");
			
		}
	}
	
	/*获取首页商家*/
	@RequestMapping("/getRecentCopartner.do")
	@ResponseBody
	public List<CopartnerVo> getRecentCopartner(
			@RequestParam(value="longitude", required=false) String longitude,
			@RequestParam(value="latitude", required=false) String latitude,
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m){
		System.out.println(latitude+longitude);
		try {
			List<CoPartner> coPartners = new ArrayList<>();
			
			if (StringUtil.isBlank(latitude) || StringUtil.isBlank(longitude)) {
				coPartners = coPartnerService.getConsByPage(page);
			}else {
				coPartners = coPartnerService.getConsByDistance(page,latitude, longitude);
			}
			
			
			List<CopartnerVo> copartnerVos = new ArrayList<>();
			for(int i = 0;i<coPartners.size();i++){
				CopartnerVo copartnerVo = CopartnerVo.getCopartnerVo(latitude,longitude,coPartners.get(i));
				copartnerVos.add(copartnerVo);
			}
			return copartnerVos;
		} catch (Exception e) {
			System.out.println( e.getMessage() );
			logger.error(e.getMessage(), e);
			return null;
		}

	}
	
	public static void main(String []args) {
		Double lat = 39.91367;
		Double lon = 116.470183;
		/*用户所在位置*/
		Double lats = 39.910351098831015;
		Double lons = 116.41391149987385;
		
		
	
		Double distance = 6378.138*2*Math.asin(Math.sqrt(Math.pow(Math.sin((lats*Math.PI/180-lat*Math.PI/180)/2), 2)+Math.cos(lats*Math.PI/180)*Math.cos(lat*Math.PI/180)*Math.pow(Math.sin((lons*Math.PI/180-lon*Math.PI/180)/2), 2)));
		BigDecimal   b   =   new   BigDecimal(distance);  
		double  exactDistance   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		System.out.println(exactDistance);
	}
	
}
