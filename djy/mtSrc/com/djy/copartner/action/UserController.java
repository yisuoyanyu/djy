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

import com.djy.cms.model.Faq;
import com.djy.cms.service.FaqService;
import com.djy.consume.model.ConsumeOrder;
import com.djy.copartner.vo.SpreadUserVo;
import com.djy.coupon.service.CouponDiscountService;
import com.djy.point.vo.WxConfigVo;
import com.djy.spread.model.SpreadCommission;
import com.djy.spread.service.SpreadCommissionService;
import com.djy.user.model.User;
import com.djy.user.model.UserAccount;
import com.djy.user.service.UserAccountService;
import com.djy.user.service.UserService;
import com.djy.user.service.UserSiteMsgService;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;
import com.frame.wechat.api.MpApi;
import com.frame.wechat.api.json.QrcodeTicket;
import com.frame.wechat.api.utils.SignUtil;

@Controller
@RequestMapping("/copartner/user")
public class UserController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private CouponDiscountService couponDiscountService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
    private HttpServletRequest request;
	@Autowired
	private FaqService faqService;
	@Autowired
	private UserSiteMsgService userSiteMsgService;
	@Autowired
	private SpreadCommissionService spreadCommissionService;
	
	@RequestMapping("/main.do")
	public String main(HttpSession session, Model m) {
		
		try {
			Integer userId = (Integer) session.getAttribute("userId");
	  		User user = userService.getById(userId);
	  		
	  		long noUserCouNum = couponDiscountService.getNoUserCou(user);
	  		long noReadMsgNum = userSiteMsgService.countAllUnReadByUserId(user);
	  		WxConfigVo wxConfig = WxConfigVo.transfer(SignUtil.getSign(request));
			m.addAttribute("wxConfig", wxConfig);
	  		
	  		m.addAttribute("noUserCouNum", noUserCouNum);
	  		m.addAttribute("noReadMsgNum", noReadMsgNum);
	  		m.addAttribute("user", user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
  		
		return "/copartner/user/main";
		
	}
	
	@RequestMapping("/mySpread.do")
	public String mySpread(HttpServletRequest request,HttpSession session, Model m) {
		Integer userId = (Integer) session.getAttribute("userId");
  		User user = userService.getById(userId);
  		
  		long totalRefnum = userService.getTotalRefNum(user);
  		WxConfigVo wxConfig = WxConfigVo.transfer( SignUtil.getSign(request) );
		m.addAttribute("wxConfig", wxConfig);
  		m.addAttribute("totalRefnum", totalRefnum);
  		m.addAttribute("user", user);
		return "/copartner/user/mySpread";
	}
	
	@RequestMapping("/mySpreadUser.do")
	public String mySpreadUser(HttpServletRequest request,
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m) {
		Integer userId = (Integer) session.getAttribute("userId");
  		User user = userService.getById(userId);
  		
        int pageSize = 10;                              //每页显示记录数
		long totalItems = userService.getTotalRefNum(user);//全部的推广的用户
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
		
		
		List<User> spreadUsers = userService.findMyspreadUser(user, page);
		
		List<SpreadUserVo> spreadUserVos = new ArrayList<>();
		
		for(int i = 0;i<spreadUsers.size();i++){
			User spreadUser = spreadUsers.get(i);
			SpreadUserVo spreadUserVo = new SpreadUserVo();
			spreadUserVo.setUserId(spreadUser.getId());
			spreadUserVo.setInsertTime(spreadUser.getInsertTime());
			spreadUserVo.setNickName(spreadUser.getWechatUser().getNickname());
			
			
			SpreadCommission spreadCommission = spreadCommissionService.getCommByUser(spreadUser);
			
			if (null != spreadCommission) {
				spreadUserVo.setStatusName("已购");
				spreadUserVo.setCommision(spreadCommission.getAmount());
			}else {
				spreadUserVo.setStatusName("未购");
				spreadUserVo.setCommision(0.0);
			}
			
			spreadUserVos.add(spreadUserVo);
		}
		
		    m.addAttribute("spreadUserVos", spreadUserVos);
		    m.addAttribute("page", page);
			m.addAttribute("totalPages", totalPages);
			m.addAttribute("lastPage", lastPage);
			m.addAttribute("nextPage", nextPage);
			m.addAttribute("startShowPage", startShowPage);
			m.addAttribute("endShowPage", endShowPage);
		return "/copartner/user/mySpreadUser";
	}
	
	@RequestMapping("/toFollow.do")
	public String toFollow(
			@RequestParam(value = "spreadCode", required = false) String spreadCode,
			HttpSession session, Model m) {
		User user;
		Integer userId = (Integer) session.getAttribute("userId");
		if (StringUtil.isEmpty(spreadCode)) {
			user = userService.getById(userId);
		} else {
			user = userService.getUserBySpreadCode( spreadCode );
		}
  		m.addAttribute("user", user);
		return "/copartner/user/toFollow";
	}
	
	@RequestMapping("/getSpreadQrcode.do")
	@ResponseBody
	public Result getSpreadQrcode(
			@RequestParam(value = "userId", required = false) Integer userId,
			HttpSession session, Model m){
		
		User user = userService.get(userId);
		
		// 第一次查看自己 的二维码的时候要给它创建
		if ( StringUtil.isBlank( user.getQrcodeAddress() ) 
				|| StringUtil.isBlank( user.getQrcodeUrlStr() ) ) {
			
			QrcodeTicket qrcodeTicket = MpApi.getQrLimitStrSceneTicket( user.getSpreadCode() );
			String QrcodeAddress = MpApi.getSceneUrl( qrcodeTicket.getTicket() );
			String QrcodeUrlStr = qrcodeTicket.getUrl();
			
			user.setQrcodeAddress( QrcodeAddress );
			user.setQrcodeUrlStr( QrcodeUrlStr );
			
			userService.update( user );
		}
		
		return new Result(true, user.getQrcodeAddress());
		
	}
		
	
	@RequestMapping("/helpCenter.do")
	public String helpCenter(HttpSession session, Model m) {
		
		List<Faq> faqs = faqService.getAllFaqBySort();
		m.addAttribute("faqs", faqs);
		return "/copartner/user/helpCenter";
	}
	
	@RequestMapping("/getMoreDiscountChance.do")
	@ResponseBody
	public Result getMoreDiscountChance(
			HttpSession session, Model m){
		try {
			Integer userId = (Integer) session.getAttribute("userId");
	  		User user = userService.getById(userId);
	  		
	  		UserAccount userAccount = user.getUserAccount();
	  		userAccount.setCanReceiveCoupons(userAccount.getCanReceiveCoupons()+1);
	  		userAccountService.update(userAccount);
	  		
	  		return new Result(true, "机会增加一次");
		} catch (Exception e) {
			return new Result(false, "机会增加失败");
		}
	}
	
	@RequestMapping("/mySpreadQrCode.do")
	public String mySpreadQrCode(HttpSession session, Model m){
		Integer userId = (Integer) session.getAttribute("userId");
  		User user = userService.getById(userId);
  		
  	// 第一次查看自己 的二维码的时候要给它创建
		if ( StringUtil.isBlank( user.getQrcodeAddress() ) 
				|| StringUtil.isBlank( user.getQrcodeUrlStr() ) ) {
			
			QrcodeTicket qrcodeTicket = MpApi.getQrLimitStrSceneTicket( user.getSpreadCode() );
			String QrcodeAddress = MpApi.getSceneUrl( qrcodeTicket.getTicket() );
			String QrcodeUrlStr = qrcodeTicket.getUrl();
			
			user.setQrcodeAddress( QrcodeAddress );
			user.setQrcodeUrlStr( QrcodeUrlStr );
			
			userService.update( user );
		}
  			
  		m.addAttribute("myQrcode", user.getQrcodeAddress());
  		
  		return "/copartner/user/mySpreadQrCode";
	}
}
