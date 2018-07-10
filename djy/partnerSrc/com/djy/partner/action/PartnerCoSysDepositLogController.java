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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.co.enumtype.CoSysDepositLogIncomeExpense;
import com.djy.co.enumtype.CoSysDepositLogType;
import com.djy.co.model.CoPartnerShift;
import com.djy.co.model.CoSysDepositLog;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.co.service.CoPartnerShiftService;
import com.djy.co.service.CoSysDepositLogService;
import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.djy.consume.model.ConsumeOrder;
import com.djy.partner.vo.CoSysDepositLogExcelVo;
import com.djy.partner.vo.CoSysDepositLogVo;
import com.djy.utils.ExportExcel;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;

@Controller
@RequestMapping("/partner/coSysDepositLog/")
public class PartnerCoSysDepositLogController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(PartnerCoSysDepositLogController.class);
	
	@Autowired
	private CoSysDepositLogService coSysDepositLogService;
	
	@Autowired
	private CoPartnerShiftService coPartnerShiftService;
	
	
	//------------------------------------------------------------------------------
	
	
	/**
	 * 加载预存款明细列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param coPartnerId
	 * @param coPartnerShiftId
	 * @return
	 */
	@RequestMapping("/coSysDepositLogListData")
	@ResponseBody
	public Object coSysDepositLogListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId,
			@RequestParam(value="coPartnerShiftId", required=false) Integer coPartnerShiftId
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
				
				coPartnerId = coPartnerShift.getCoPartner().getId();
				
			}
			
			
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("coPartnerId", coPartnerId);
			
			List<CoSysDepositLog> coSysDepositLogs = coSysDepositLogService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoSysDepositLogVo.transferList(coSysDepositLogs));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	/**
	 * 预存款明细导出excel数据
	 * @param coPartnerShiftId
	 * @param request
	 * @param response
	 */
    @RequestMapping("/exportCoSysDepositLogExcel")
    public void exportCoSysDepositLogExcel(
    		@RequestParam(value="coPartnerShiftId", required=false) Integer coPartnerShiftId,
            HttpServletRequest request, HttpServletResponse response
    ) {
        try {
        	
            String[] headers = {"订单编号", "业务类型", "收支类型", "金额（元）", "平台剩余预存金额（元）", "插入时间"};
            
            List<CoSysDepositLogExcelVo> dataset =  new ArrayList<>();
        	
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
			
			List<CoSysDepositLog> coSysDepositLogs = coSysDepositLogService.searchAllCoSysDepositLogs(param);
			
			for (int i = 0; i < coSysDepositLogs.size(); i++) {

				CoSysDepositLog coSysDepositLog = coSysDepositLogs.get(i);

				CoSysDepositLogExcelVo vo = new CoSysDepositLogExcelVo();
                
				ConsumeOrder consumeOrder = coSysDepositLog.getConsumeOrder();
				if (consumeOrder != null) {
					vo.setNo(consumeOrder.getNo());
				}
				CoSysDepositOrder coSysDepositOrder = coSysDepositLog.getCoSysDepositOrder();
				if (coSysDepositOrder != null) {
					vo.setNo(coSysDepositOrder.getNo());
				}
				vo.setTypeName(CoSysDepositLogType.fromId(coSysDepositLog.getType()).getValue());
				vo.setIncomeExpenseName(CoSysDepositLogIncomeExpense.fromId(coSysDepositLog.getIncomeExpense()).getName());
				vo.setAmount(coSysDepositLog.getAmount());
				vo.setSysDeposit(coSysDepositLog.getSysDeposit());
                vo.setInsertTime(coSysDepositLog.getInsertTime());
                
                dataset.add(vo);
            }
				

            try {
            	
                request.setCharacterEncoding("UTF-8");

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/x-download");

                String filedisplay = coPartnerShift.getCoPartner().getName()+"预存明细"+".xls";
                filedisplay = new String( filedisplay.getBytes("gb2312"), "ISO8859-1" );        //防止文件名含有中文乱码
                response.setHeader("Content-Disposition", "attachment;filename="+ filedisplay);

                OutputStream out = response.getOutputStream();

                ExportExcel<CoSysDepositLogExcelVo> ex = new ExportExcel<>();
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
