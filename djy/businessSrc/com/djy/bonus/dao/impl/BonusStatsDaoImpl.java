package com.djy.bonus.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.bonus.dao.BonusStatsDao;
import com.djy.bonus.model.BonusStats;
import com.djy.user.model.User;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("bonusStatsDao")
public class BonusStatsDaoImpl extends BaseDaoImpl<BonusStats> implements BonusStatsDao {
	
	
	@Override
	public double getTotalCstmConsumeByUser(User user) {
		String hql ="select sum(bon.totalCstmConsume) from " + BonusStats.class.getName() + " bon "+"WHERE 1=1";
		hql += " AND bon.sysEmpl.user.id = "+user.getId();
		return (double)this.getUniqueByHql(hql);
	}
	
	
	@Override
	public double getTotalBonusByUser(User user) {
		String hql ="select sum(bon.bonus) from " + BonusStats.class.getName() + " bon "+"WHERE 1=1";
		hql += " AND bon.sysEmpl.user.id = "+user.getId();
		return (double)this.getUniqueByHql(hql);
	}
	
	
	@Override
	public List<BonusStats> getDayAccList(User user, Date getDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<BonusStats> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + BonusStats.class.getName() + " bonusStats WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Integer sysEmplId = (Integer)param.get("sysEmplId");
		if ( !StringUtil.isEmpty( sysEmplId ) ) {
			hql += " AND bonusStats.sysEmpl.id =:sysEmplId";
			params.put("sysEmplId", sysEmplId);
		}
		
		String emplID = (String)param.get("emplID");
		if ( !StringUtil.isEmpty( emplID ) ) {
			hql += " AND bonusStats.sysEmpl.emplID =:emplID";
			params.put("emplID", emplID);
		}
		
		String sysEmplMobile = (String)param.get("sysEmplMobile");
		if ( !StringUtil.isEmpty( sysEmplMobile ) ) {
			hql += " AND bonusStats.sysEmpl.mobile LIKE :mobile";
			params.put("mobile", "%%" + sysEmplMobile + "%%");
		}
		
		String sysEmplName = (String)param.get("sysEmplName");
		if ( !StringUtil.isEmpty( sysEmplName ) ) {
			hql += " AND bonusStats.sysEmpl.name LIKE :name";
			params.put("name", "%%" + sysEmplName + "%%");
		}
		
		String coPartnerName = (String)param.get("coPartnerName");
		if ( !StringUtil.isEmpty( coPartnerName ) ) {
			hql += " AND bonusStats.coPartner.name LIKE :coPartnerName";
			params.put("coPartnerName", "%%" + coPartnerName + "%%");
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND bonusStats.statsDate >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND bonusStats.statsDate <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (bonusStats.coPartner.name LIKE :searchText "
					+ " OR bonusStats.sysEmpl.realName LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY bonusStats." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY bonusStats.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}
	
	
}
