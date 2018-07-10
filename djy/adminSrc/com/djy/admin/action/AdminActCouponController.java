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

import com.djy.admin.vo.ActCouponVo;
import com.djy.co.service.CoPartnerService;
import com.djy.coupon.enumtype.ActCouponIsChoice;
import com.djy.coupon.enumtype.ActCouponStatus;
import com.djy.coupon.enumtype.CouponType;
import com.djy.coupon.enumtype.CouponUseDateType;
import com.djy.coupon.model.ActCoupon;
import com.djy.coupon.service.ActCouponService;
import com.djy.sys.service.CommonService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;


@Controller
@RequestMapping("/admin/actCoupon/")
public class AdminActCouponController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminActCouponController.class);
	
	@Autowired
	private ActCouponService actCouponService;
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private CommonService commonService;
	
	
	/**
	 * 调用AdminActCouponController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadActCoupon")
	public ActCoupon preloadActCoupon( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return actCouponService.get(id);
		}
		return new ActCoupon();
	}
	
	/**
	 * 送券活动信息详情
	 * @param id
	 * @param coPartnerId
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/actCouponRecord")
	public String actCouponRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			@RequestParam(value = "coPartnerId", required = false) Integer coPartnerId, 
			Model m ,HttpSession session) {
		try {
			
			if( !commonService.validateRes("ReadActCoupon" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			m.addAttribute("actCoupon", preloadActCoupon(id));
			
			m.addAttribute("couponType", CouponType.values());
			
			m.addAttribute("couponUseDateType", CouponUseDateType.values());
			
			m.addAttribute("actCouponStatus", ActCouponStatus.values());
			
			m.addAttribute("actCouponIsChoice", ActCouponIsChoice.values());
			
			m.addAttribute("coPartner", coPartnerService.get(coPartnerId));
			
			return "/admin/actCoupon/actCouponRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 提交送券活动信息
	 * @param actCoupon
	 * @param coPartnerId
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitActCoupon")
	@ResponseBody
	public Result submitActCoupon( 
			@ModelAttribute("preloadActCoupon") ActCoupon actCoupon, 
			@RequestParam(value = "coPartnerId", required = false) Integer coPartnerId,
			HttpSession session) {
		try {
			
			if( (!commonService.validateRes("AddActCoupon",session)) 
					&& (!commonService.validateRes("EditActCoupon",session)) ){
					logger.error("没有访问权限，请联系系统管理员");
					return new Result(false,"no access");	
			}
			
			boolean isNew = false;
			if ( actCoupon.getId() == null ) {
				isNew = true;
			}
			
			if ( isNew ) {
				actCoupon.setInsertTime( new Date() );
			} 
			actCoupon.setCoPartner(coPartnerService.get(coPartnerId));
			actCoupon.setLastTime(new Date());
			actCouponService.saveOrUpdate(actCoupon);
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	/**
	 * 删除送券活动信息
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/delActCoupon")
	@ResponseBody
	public Result delActCoupon( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		try {
			
			if(!commonService.validateRes("DelActCoupon",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = actCouponService.delActCoupon(ids);
			if(num > 0){
				return new Result(true, "成功删除选中送券活动！");
			}else{
				return new Result(false, "删除失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	
	/**
	 * 更改送券活动信息状态
	 * @param ids
	 * @param status
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateActCoupon")
	@ResponseBody
	public Result updateActCoupon( @RequestParam(value="ids[]") Integer[] ids ,
			@RequestParam(value="status") Integer status ,HttpSession session) {
		
		try {
			
			if( (!commonService.validateRes("DisableActCoupon",session)) 
					&& (!commonService.validateRes("EnableActCoupon",session)) ){
					logger.error("没有访问权限，请联系系统管理员");
					return new Result(false,"no access");	
			}
			
			int num = actCouponService.updateActCoupon(ids,status);
			if( num > 0 ){
				if ( status == ActCouponStatus.normal.getId() ) {
					return new Result(true, "成功启用选中送券活动！");
				} else {
					return new Result(true, "成功禁用选中送券活动！");
				}
				
			}else{
				return new Result(true, "操作失败！");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	/**
	 * 送券活动列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/actCouponList")
	public String actCouponList(HttpSession session) {
		try {
			if( (!commonService.validateRes("ReadActCoupon",session))){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			return "/admin/actCoupon/actCouponList";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
	}
	
	/**
	 * 加载送券活动信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param title
	 * @param name
	 * @param searchText
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param coPartnerId
	 * @param session
	 * @return
	 */
	@RequestMapping("/actCouponListData")
	@ResponseBody
	public Object actCouponListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="title", required=false) String title,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("title", title);
			param.put("name", name);
			param.put("searchText", searchText);
			
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
			param.put("coPartnerId", coPartnerId);
			List<ActCoupon> actCoupons = actCouponService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", ActCouponVo.transferList(actCoupons));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	
}
