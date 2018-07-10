package com.djy.partner.action;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.co.enumtype.CoSysDepositOrderStatus;
import com.djy.co.model.CoPartnerShift;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.co.service.CoPartnerShiftService;
import com.djy.co.service.CoSysDepositOrderService;
import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.djy.partner.vo.CoSysDepositOrderExcelVo;
import com.djy.partner.vo.CoSysDepositOrderVo;
import com.djy.utils.ExportExcel;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;



@Controller
@RequestMapping("/partner/coSysDepositOrder/")
public class PartnerCoSysDepositOrderController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(PartnerCoSysDepositOrderController.class);
	
	@Autowired
	private CoSysDepositOrderService coSysDepositOrderService;
	
	@Autowired
	private CoPartnerShiftService coPartnerShiftService;
	
	
	/**
	 * 加载订单列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param status
	 * @param coPartnerId
	 * @param coPartnerShiftId
	 * @param session
	 * @return
	 */
	@RequestMapping("/coSysDepositOrderListData")
	@ResponseBody
	public Object coSysDepositOrderListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="status", required=false) Integer status,
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId,
			@RequestParam(value="coPartnerShiftId", required=false) Integer coPartnerShiftId,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			if (coPartnerShiftId != null) {
				CoPartnerShift coPartnerShift = coPartnerShiftService.get(coPartnerShiftId);
				
				Date timeStart = null;
				Date timeEnd = null;
				
				if ( !StringUtil.isEmpty( coPartnerShift.getStartTime() ) ) {
					timeStart = DateUtil.getFirstTimeOfDate(coPartnerShift.getStartTime());
				}
				
				if ( !StringUtil.isEmpty( coPartnerShift.getEndTime() ) ) {
					timeEnd = DateUtil.getLastTimeOfDate(coPartnerShift.getEndTime());
				}
				
				param.put("timeStart", timeStart);
				param.put("timeEnd", timeEnd);
				
				status = ConsumeOrderStatus.paySuccess.getId();
				coPartnerId = coPartnerShift.getCoPartner().getId();
				
			}
				
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("status", status);
			param.put("coPartnerId", coPartnerId);
			
			List<CoSysDepositOrder> coSysDepositOrders = coSysDepositOrderService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoSysDepositOrderVo.transferList(coSysDepositOrders));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	
	/**
	 * 平台预存导出excel数据
	 * @param coPartnerShiftId
	 * @param request
	 * @param response
	 */
    @RequestMapping("/exportCoSysDepositOrderExcel")
    public void exportCoSysDepositOrderExcel(
    		@RequestParam(value="coPartnerShiftId", required=false) Integer coPartnerShiftId,
            HttpServletRequest request, HttpServletResponse response
    ) {
        try {
        	
            String[] headers = {"订单编号", "预存金额（元）", "实付金额（元）",  "状态", "插入时间"};
            
            List<CoSysDepositOrderExcelVo> dataset =  new ArrayList<>();
        	
			CoPartnerShift coPartnerShift = coPartnerShiftService.get(coPartnerShiftId);
			Integer status = ConsumeOrderStatus.paySuccess.getId();
			Integer coPartnerId = coPartnerShift.getCoPartner().getId();
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("status", status);
			
			Date timeStart = null;
			Date timeEnd = null;
			
			if ( !StringUtil.isEmpty( coPartnerShift.getStartTime() ) ) {
				timeStart = DateUtil.getFirstTimeOfDate(coPartnerShift.getStartTime());
			}
			
			if ( !StringUtil.isEmpty( coPartnerShift.getEndTime() ) ) {
				timeEnd = DateUtil.getLastTimeOfDate(coPartnerShift.getEndTime());
			}
			
			param.put("timeStart", timeStart);
			param.put("timeEnd", timeEnd);
			
			param.put("coPartnerId", coPartnerId);
			
			List<CoSysDepositOrder> coSysDepositOrders = coSysDepositOrderService.search(param);
			
			for (int i = 0; i < coSysDepositOrders.size(); i++) {

				CoSysDepositOrder coSysDepositOrder = coSysDepositOrders.get(i);

				CoSysDepositOrderExcelVo vo = new CoSysDepositOrderExcelVo();
                
                vo.setNo(coSysDepositOrder.getNo());
                vo.setAmount(coSysDepositOrder.getAmount());
                vo.setPayAmount(coSysDepositOrder.getPayAmount());
                vo.setStatusName(CoSysDepositOrderStatus.fromId(status).getValue());
                vo.setInsertTime(coSysDepositOrder.getInsertTime());
                
                dataset.add(vo);
            }
				

            try {
            	
                request.setCharacterEncoding("UTF-8");

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/x-download");

                String filedisplay = coPartnerShift.getCoPartner().getName()+"平台预存"+".xls";
                filedisplay = new String( filedisplay.getBytes("gb2312"), "ISO8859-1" );        //防止文件名含有中文乱码
                response.setHeader("Content-Disposition", "attachment;filename="+ filedisplay);

                OutputStream out = response.getOutputStream();

                ExportExcel<CoSysDepositOrderExcelVo> ex = new ExportExcel<>();
                ex.exportExcel(headers, dataset, out);

                out.close();

            } catch (FileNotFoundException e) {
            	logger.error("",e);
            } catch (IOException e) {
            	logger.error("",e);
            }
        } catch (Exception e) {
        	logger.error("",e);
        }
    }
	
	
}
