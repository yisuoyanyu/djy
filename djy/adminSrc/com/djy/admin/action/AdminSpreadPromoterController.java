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

import com.djy.admin.vo.SpreadPromoterVo;
import com.djy.spread.enumtype.SpreadPromoterStatus;
import com.djy.spread.model.SpreadPromoter;
import com.djy.spread.service.SpreadPromoterService;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;


@Controller
@RequestMapping("/admin/spreadPromoter/")
public class AdminSpreadPromoterController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminSpreadPromoterController.class);
	
	@Autowired
	private SpreadPromoterService spreadPromoterService;	
	
	@Autowired
	private UserService userService;
	
	/**
	 * 调用AdminSpreadPromoterController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadSpreadPromoter")
	public SpreadPromoter preloadSpreadPromoter( @RequestParam(value = "id", required = false) Integer id) {
		SpreadPromoter spreadPromoter = new SpreadPromoter();
		if ( id != null ) {
			spreadPromoter = spreadPromoterService.get(id);
		}
		return spreadPromoter;
	}
	
	
	/**
	 * 推广人信息详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/spreadPromoterRecord")
	public String spreadPromoterRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m, HttpSession session) {
		try {
					
			SpreadPromoter spreadPromoter = preloadSpreadPromoter(id);
			if (spreadPromoter == null) {
				logger.error("推广人不存在，请联系系统管理员！");
				return "";
			}
			
			m.addAttribute("spreadPromoter", spreadPromoter);
			
			m.addAttribute("spreadPromoterStatus", SpreadPromoterStatus.values());
			
			return "/admin/spreadPromoter/spreadPromoterRecord";
		} catch (Exception e) {
			logger.error("", e);
			return "";
		}
		
	}
	
	
	/**
	 * 提交推广人信息
	 * @param spreadPromoter
	 * @param userId
	 * @param emplID
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitSpreadPromoter")
	@ResponseBody
	public Result submitSpreadPromoter( 
			@ModelAttribute("preloadSpreadPromoter") SpreadPromoter spreadPromoter, 
			@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam(value="emplID", required=false) String emplID, 
			HttpSession session) {
		try {
			
			if (userId == null) {
				return new Result(false, "关联会员手机对应会员错误，请重新输入关联会员手机！");
			}
			
			User user = userService.get(userId);
			if (user != null) {
				//判断通过该手机号查询到的会员是否已匹配过其它合作商家
				if (user.getSpreadPromoter() != null && user.getSpreadPromoter().getId() != spreadPromoter.getId()) {
					return new Result(false, "关联会员手机对应会员已存在相匹配的推广人，请重新输入关联会员手机！");
				}
			} else {
				return new Result(false, "不存在该手机号对应会员，请重新输入关联会员手机！");
			}
			
			boolean isNew = false;
			if ( spreadPromoter.getId() == null ) isNew = true;
			
			// 检查工号是否被占用
			if ( emplID != null ) {
				SpreadPromoter tmpSpreadPromoter = spreadPromoterService.getByEmplID( emplID );
				if ( tmpSpreadPromoter != null ) {
					if ( isNew ) {
						return new Result(false, "工号已被占用！");
					} else {
						if ( !tmpSpreadPromoter.getId().equals( tmpSpreadPromoter.getId() ) )
							return new Result(false, "工号已被占用！");
					}
				}
			}
			
			if ( isNew ) {
				
				spreadPromoter.setInsertTime( new Date() );
				
			}
			spreadPromoter.setUser(user);
			
			spreadPromoterService.saveOrUpdate(spreadPromoter);
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	
	/**
	 * 删除推广人
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/delSpreadPromoter")
	@ResponseBody
	public Result delSpreadPromoter( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		
		try {
			
			int num = spreadPromoterService.delSpreadPromoter(ids);
			if(num > 0){
				return new Result(true, "成功删除选中推广人！");
			}else{
				return new Result(false, "删除失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
	}
	
	
	/**
	 * 禁用推广人
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/freezeSpreadPromoter")
	@ResponseBody
	public Result freezeSpreadPromoter( @RequestParam(value="ids[]") Integer[] ids  ,HttpSession session) {
		try {
			
			int num = spreadPromoterService.freezeSpreadPromoter(ids);
			if(num > 0){
				return new Result(true, "成功禁用选中推广人！");
			}else{
				return new Result(false, "禁用失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
		
	}
	
	
	/**
	 * 启用推广人
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/normalSpreadPromoter")
	@ResponseBody
	public Result normalSpreadPromoter( @RequestParam(value="ids[]") Integer[] ids  ,HttpSession session) {
		
		try {
			
			int num = spreadPromoterService.normalSpreadPromoter(ids);
			if(num > 0){
				return new Result(true, "成功启用选中员工！");
			}else{
				return new Result(false, "启用失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
	}
	
	
	/**
	 * 推广人列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/spreadPromoterList")
	public String spreadPromoterlList() {
		
		return "/admin/spreadPromoter/spreadPromoterList";
		
	}
	
	/**
	 * 加载推广人列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param emplID
	 * @param realName
	 * @param mobile
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @return
	 */
	@RequestMapping("/spreadPromoterListData")
	@ResponseBody
	public Object spreadPromoterListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="emplID", required=false) String emplID,
			@RequestParam(value="realName", required=false) String realName,
			@RequestParam(value="mobile", required=false) String mobile,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			param.put("emplID", emplID);
			param.put("realName", realName);
			param.put("mobile", mobile);
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
			List<SpreadPromoter> spreadPromoters = spreadPromoterService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", SpreadPromoterVo.transferList(spreadPromoters));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
}
