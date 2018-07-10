package com.djy.wxPay.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.consume.model.ConsumeOrder;
import com.djy.wxPay.model.WxPayCharge;
import com.djy.wxPay.service.WxPayChargeService;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/wxPayCharge")
public class WxPayChargeController extends WebController {

	private static Logger logger = LoggerFactory.getLogger( WxPayChargeController.class );
	
	@Autowired
	private WxPayChargeService wxPayChargeService;
	
	
	/**
	 * 完成支付操作
	 * @param timeStamp 订单时间戳
	 * @param nonceStr 订单随机数
	 * @return
	 */
	@RequestMapping("/payFinish.do")
	@ResponseBody
	public Result payfinish(
			@RequestParam(value="timeStamp", required=false) String timeStamp,
			@RequestParam(value="nonceStr", required=false) String nonceStr
			) {
		
		WxPayCharge wxPayCharge = wxPayChargeService.getByNonceStr(timeStamp, nonceStr);
		
		wxPayChargeService.dealPayFinish( wxPayCharge );
		
		ConsumeOrder consumeOrder = wxPayCharge.getFinCharge().getConsumeOrder();
		
		return new Result(true, consumeOrder.getId());
		
	}
	
	
}
