package com.djy.admin.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.admin.vo.CouponDiscountVo;
import com.djy.coupon.model.CouponDiscount;
import com.djy.coupon.service.CouponDiscountService;
import com.djy.sys.service.CommonService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;

@Controller
@RequestMapping("/admin/couponDiscount/")
public class AdminCouponDiscountController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCouponDiscountController.class);
	
	@Autowired
	private CouponDiscountService couponDiscountService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCouponDiscount")
	public CouponDiscount preloadCouponDiscount( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return couponDiscountService.get(id);
		}
		return new CouponDiscount();
	}
	
	
	/**
	 * 打折券信息详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/couponDiscountRecord")
	public String couponDiscountRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m, HttpSession session) {
		try {
			
			if( !commonService.validateRes("ReadCouponDiscount" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			CouponDiscount couponDiscount = preloadCouponDiscount(id);
			
			m.addAttribute("couponDiscount", couponDiscount);
			
			return "/admin/couponDiscount/couponDiscountRecord";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	
	
	//------------------------------------------------------------------------------
	
	
	/**
	 * 打折券列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/couponDiscountList")
	public String couponDiscount(HttpSession session) {
		
		try {
			
			if( !commonService.validateRes("ReadCouponDiscount" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			return "/admin/couponDiscount/couponDiscountList";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 加载打折券列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param coPartnerName
	 * @param userId
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param session
	 * @return
	 */
	@RequestMapping("/couponDiscountListData")
	@ResponseBody
	public Object couponDiscountListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="coPartnerName", required=false) String coPartnerName,
			@RequestParam(value="userId", required=false) Integer userId,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
				
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			param.put("coPartnerName", coPartnerName);
			param.put("userId", userId);
			
			Date timeStart = null;
			Date timeEnd = null;
			
			if ( !StringUtil.isEmpty( insertTimeStart ) ) {
				Date date = DateUtil.toDate(insertTimeStart);
				timeStart = DateUtil.getFirstTimeOfDate(date);
			}
			
			if ( !StringUtil.isEmpty( insertTimeEnd ) ) {
				Date date = DateUtil.toDate(insertTimeEnd);
				timeEnd = DateUtil.getLastTimeOfDate(date);
			}
			
			param.put("timeStart", timeStart);
			param.put("timeEnd", timeEnd);
			
			List<CouponDiscount> couponDiscounts = couponDiscountService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CouponDiscountVo.transferList(couponDiscounts));
			
			return map;
			
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	
}
