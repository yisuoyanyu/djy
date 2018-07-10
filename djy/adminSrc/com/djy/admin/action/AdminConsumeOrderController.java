package com.djy.admin.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.djy.admin.vo.ConsumeOrderVo;
import com.djy.co.model.CoPartnerEmpl;
import com.djy.co.service.CoPartnerEmplService;
import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.djy.consume.model.ConsumeOrder;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.admin.vo.ConsumeOrderExcelVo;
import com.djy.sys.service.CommonService;
import com.djy.user.model.User;
import com.djy.utils.ExportExcel;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;

@Controller
@RequestMapping("/admin/consumeOrder/")
public class AdminConsumeOrderController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminConsumeOrderController.class);
	
	@Autowired
	private ConsumeOrderService consumeOrderService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CoPartnerEmplService coPartnerEmplService;
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadConsumeOrder")
	public ConsumeOrder preloadConsumeOrder( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return consumeOrderService.get(id);
		}
		return new ConsumeOrder();
	}
	
	
	/**
	 * 用户消费订单信息详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/consumeOrderRecord")
	public String consumeOrderRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m, HttpSession session) {
		try {
			
			if( (!commonService.validateRes("ReadCoSysDepositOrder",session))){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			ConsumeOrder consumeOrder = preloadConsumeOrder(id);
			
			m.addAttribute("consumeOrder", consumeOrder);
			
			return "/admin/consumeOrder/consumeOrderRecord";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	
	
	//------------------------------------------------------------------------------
	
	
	/**
	 * 订单列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/consumeOrderList")
	public String consumeOrderList(HttpSession session) {
		
		try {
			
			if( (!commonService.validateRes("ReadConsumeOrder",session))){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			return "/admin/consumeOrder/consumeOrderList";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 加载消费订单列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param userMobile
	 * @param nickname
	 * @param coPartnerName
	 * @param status
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param coPartnerId
	 * @param coPartnerEmplId
	 * @param emplID
	 * @param session
	 * @return
	 */
	@RequestMapping("/consumeOrderListData")
	@ResponseBody
	public Object consumeOrderListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="userMobile", required=false) String userMobile,
			@RequestParam(value="nickname", required=false) String nickname,
			@RequestParam(value="coPartnerName", required=false) String coPartnerName,
			@RequestParam(value="status", required=false) Integer status,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId,
			@RequestParam(value="coPartnerEmplId", required=false) Integer coPartnerEmplId,
			@RequestParam(value="emplID", required=false) String emplID,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			param.put("userMobile", userMobile);
			param.put("nickname", nickname);
			param.put("coPartnerName", coPartnerName);
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
			if (coPartnerEmplId != null) {
				param.put("coPartnerEmplId", coPartnerEmplId);
				status = ConsumeOrderStatus.paySuccess.getId();
			}
			param.put("status", status);
			param.put("emplID", emplID);
			
			List<ConsumeOrder> consumeOrders = consumeOrderService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", ConsumeOrderVo.transferList(consumeOrders));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	/**
	 * 首次消费会员信息列表
	 * @return
	 */
	@RequestMapping("/firstConsumeUserList")
	public String firstConsumeUserList() {
		return "/admin/consumeOrder/firstConsumeUserList";
	}
	
	/**
	 * 加载首次消费会员信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param sponsorMobile
	 * @param searchText
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param sponsorUserId
	 * @param session
	 * @return
	 */
	@RequestMapping("/firstConsumeUserListData")
	@ResponseBody
	public Object firstConsumeUserListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="sponsorMobile", required=false) String sponsorMobile,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="sponsorUserId", required=false) Integer sponsorUserId,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("sponsorMobile", sponsorMobile);
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
			
			param.put("sponsorUserId", sponsorUserId);
			List<ConsumeOrder> consumeOrders = consumeOrderService.searchFirstConsumeUser(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", ConsumeOrderVo.transferList(consumeOrders));
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
			return null;
		}
		
	}
	
	
	/**
	 * 会员消费导出excel数据
	 * @param coPartnerEmplId
	 * @param request
	 * @param response
	 */
    @RequestMapping("/exportConsumeOrderExcel")
    public void exportConsumeOrderExcel(
    		@RequestParam(value="coPartnerEmplId", required=false) Integer coPartnerEmplId,
            HttpServletRequest request, HttpServletResponse response
    ) {
        try {
        	
            String[] headers = {"订单编号", "用户名称", "用户手机号", "消费金额（元）", "实付金额（元）",  "状态", "消费时间"};
            
            List<ConsumeOrderExcelVo> dataset =  new ArrayList<>();
        	
            String coPartnerName = "";
            
            Map<String, Object> param = new HashMap<String, Object>();
            
            Integer status = ConsumeOrderStatus.paySuccess.getId();
            param.put("status", status);
            
            if (coPartnerEmplId != null) {
				CoPartnerEmpl coPartnerEmpl = coPartnerEmplService.get(coPartnerEmplId);
				param.put("coPartnerId", coPartnerEmpl.getCoPartner().getId());
				
				coPartnerName = coPartnerEmpl.getCoPartner().getName();
			}
			
			List<ConsumeOrder> consumeOrders = consumeOrderService.search(param);
			
			for (int i = 0; i < consumeOrders.size(); i++) {

                ConsumeOrder consumeOrder = consumeOrders.get(i);

                ConsumeOrderExcelVo vo = new ConsumeOrderExcelVo();
                
                vo.setNo(consumeOrder.getNo());
                User user = consumeOrder.getUser();
        		if (user != null) {
        			vo.setNickname(user.getWechatUser().getNickname());
        			vo.setMobile(user.getMobile());
        		}
                vo.setConsumeAmount(consumeOrder.getConsumeAmount());
                vo.setPayAmount(consumeOrder.getPayAmount());
                vo.setStatusName(ConsumeOrderStatus.fromId(status).getValue());
                vo.setInsertTime(consumeOrder.getInsertTime());
                
                dataset.add(vo);
            }
				

            try {
            	
                request.setCharacterEncoding("UTF-8");

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/x-download");
                
                String filedisplay = coPartnerName +"会员消费明细"+".xls";
                filedisplay = new String( filedisplay.getBytes("gb2312"), "ISO8859-1" );        //防止文件名含有中文乱码
                response.setHeader("Content-Disposition", "attachment;filename="+ filedisplay);

                OutputStream out = response.getOutputStream();

                ExportExcel<ConsumeOrderExcelVo> ex = new ExportExcel<>();
                ex.exportExcel(headers, dataset, out);

                out.close();

            } catch (FileNotFoundException e) {
            	logger.error("",e);
                //e.printStackTrace();
            } catch (IOException e) {
            	logger.error("",e);
                //e.printStackTrace();
            }
        } catch (Exception e) {
        	logger.error("",e);
        	//e.printStackTrace();
            //Exceptions.getExceptionMsg(e, logger);
        }
    }
	
	
}
