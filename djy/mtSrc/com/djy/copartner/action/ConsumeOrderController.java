package com.djy.copartner.action;

import java.math.BigDecimal;
import java.util.Date;
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
import com.djy.co.model.CoPartnerEmpl;
import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.djy.consume.model.ConsumeOrder;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.coupon.enumtype.CouponDiscountStatus;
import com.djy.coupon.model.CouponDiscount;
import com.djy.coupon.service.CouponDiscountService;
import com.djy.point.vo.WxConfigVo;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.utils.DateUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;
import com.frame.wechat.api.utils.SignUtil;

@Controller
@RequestMapping("/copartner/consumeOrder")
public class ConsumeOrderController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(ConsumeOrderController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private ConsumeOrderService consumeOrderService;
	@Autowired
	private CouponDiscountService couponDiscountService;
	
	@RequestMapping("/allOrderList.do")
	public String allOrderList(HttpSession session, Model m) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.get(userId);
		
		List<ConsumeOrder> consumeOrders = consumeOrderService.getAllordersByUser(user);
		
		m.addAttribute("consumeOrders", consumeOrders);
		return "/copartner/consumeOrder/allOrderList";
		
	}
	
	@RequestMapping("/getOrderListByStatus.do")
	public String getOrderListByStatus(
			@RequestParam(value="orderStatus", required=false) int orderStatus,
			HttpSession session, Model m) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.get(userId);
		
		List<ConsumeOrder> consumeOrders = consumeOrderService.findByUserStatus(user, orderStatus);
		
		m.addAttribute("consumeOrders", consumeOrders);
		m.addAttribute("orderStatus", orderStatus);
		return "/copartner/consumeOrder/statusOrderList";
		
	}
	
	@RequestMapping("/cancelCouOrder.do")
	@ResponseBody
	public Result cancelCouOrder(
			@RequestParam("orderid") String orderid,
			HttpSession session,Model m){
		
		Integer orderId = Integer.valueOf(orderid);
		ConsumeOrder consumeOrder = consumeOrderService.get(orderId);
		CouponDiscount couponDiscount = consumeOrder.getCouponDiscount();
		try {
			consumeOrder.setStatus(ConsumeOrderStatus.cancel.getId());
			consumeOrderService.update(consumeOrder);
			if (null != couponDiscount) {//表示这个订单是有使用打折券的
				couponDiscount.setStatus(CouponDiscountStatus.unused.getId());
				couponDiscount.setConsumeOrder(null);//把和订单的关联设置为null
				couponDiscountService.update(couponDiscount);
			}
			return new Result(true, "取消成功");
		} catch (Exception e) {
			return new Result(false, "取消失败");
		}
	}
	
	@RequestMapping("/delCouOrder.do")
	@ResponseBody
	public Result delCouOrder(
			@RequestParam("orderid") String orderid,
			HttpSession session,Model m){
		
		Integer orderId = Integer.valueOf(orderid);
		ConsumeOrder consumeOrder = consumeOrderService.get(orderId);
		try {
			consumeOrder.setStatus(ConsumeOrderStatus.cancel.getId());
			consumeOrderService.delete(consumeOrder);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			return new Result(false, "删除失败");
		}
	}
	
	@RequestMapping("/paySuccess.do")
	public String paySuccess(
			@RequestParam(value="orderId", required=false) Integer orderId,
			HttpServletRequest request,HttpSession session, Model m) {
		ConsumeOrder consumeOrder = consumeOrderService.get(orderId);
		CoPartner coPartner = consumeOrder.getCoPartner();
		String conName = coPartner.getName();
		String discount = "";
		if (null != consumeOrder.getCouponDiscount()) {
			BigDecimal bg = new BigDecimal((consumeOrder.getCouponDiscount().getDiscountPercent())/10);
			double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			discount += f1+"";
		}else {
			discount = "10";
		}
		
		BigDecimal bg1 = new BigDecimal(consumeOrder.getConsumeAmount()-consumeOrder.getPayAmount());
		
		
		String consumeAmount = consumeOrder.getPayAmount()+"元";
		String disAmount = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"元";
		String spreadCode = consumeOrder.getUser().getSpreadCode();
		
		m.addAttribute("conName", conName);
		m.addAttribute("discount", discount);
		m.addAttribute("consumeAmount", consumeAmount);
		m.addAttribute("disAmount", disAmount);
		m.addAttribute("spreadCode", spreadCode);
		
		WxConfigVo wxConfig = WxConfigVo.transfer( SignUtil.getSign(request) );
		m.addAttribute("wxConfig", wxConfig);
		
		return "/copartner/consumeOrder/paySuccess";
		
	}
	
	@RequestMapping("/getEmplOrderList.do")
	public String getEmplOrderList(
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m) {
		
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.get(userId);
		
		CoPartnerEmpl coPartnerEmpl = user.getCoPartnerEmpl();
		
		
		int pageSize = 10;                              //每页显示记录数
		
		long totalItems = consumeOrderService.getNoByEmpl(coPartnerEmpl);//全部数据
		    int startNum = 0;
		    int endNum = 0;
		  
		int totalPages = 0;  //总页数
		
		startNum = (page - 1)*10+1;
		
		if (totalItems%pageSize == 0) {
			totalPages = ((int)totalItems/pageSize);
			endNum = page*10;
		}else {
			totalPages = ((int)totalItems/pageSize)+1;
			if (page == totalPages) {
				endNum = (page - 1)*10 + (int)(totalItems%pageSize);
			}else {
				endNum = page*10;
			}
			
		}
		
		int lastPage = 0;                               //上一页页码  等于1就不在前端显示
		int nextPage = 1;                               //下一页页码  等于1就不在前端显示
		
		if (page != 1) {
			lastPage = page-1;
		}
		
		if (page != totalPages) {
			nextPage = page+1;
		}
		
		int startShowPage = 1;                           //select中可选择的第一项
		int endShowPage = 1;                             //select中可选择的最后一项
		
		if (totalPages < 10) {//表示总页数不足10页
			startShowPage = 1;
			endShowPage = totalPages;
		}else {
			if (page - 2 <= 0) {
				startShowPage = 1;
			}else {
				startShowPage = page -2;
			}
			
			if (page + 7 < totalItems) {
				if (page + 7 < 10) {
					endShowPage = 10;
				}else {
					endShowPage = page + 7;
				}
				
			}else {
				endShowPage = totalPages;
				startShowPage = totalPages - 9;
			}
		}
		
		
		List<ConsumeOrder> consumeOrders = consumeOrderService.getEmplOrders(coPartnerEmpl, page);
        m.addAttribute("consumeOrders", consumeOrders);
        m.addAttribute("page", page);
		m.addAttribute("totalPages", totalPages);
		m.addAttribute("lastPage", lastPage);
		m.addAttribute("nextPage", nextPage);
		m.addAttribute("startShowPage", startShowPage);
		m.addAttribute("endShowPage", endShowPage);
		
		return "/copartner/consumeOrder/emplOrderList";
		
	}
	
}
