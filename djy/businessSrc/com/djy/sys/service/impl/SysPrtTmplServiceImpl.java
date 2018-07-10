package com.djy.sys.service.impl;


import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.sys.model.SysPrtTmpl;
import com.djy.sys.service.SysPrtTmplService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.TmplPrtUtil;
import com.frame.base.utils.ResponseUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;
import com.frame.upload.Webuploader;


@Service("sysPrtTmplService")
public class SysPrtTmplServiceImpl extends BaseServiceImpl<SysPrtTmpl> implements SysPrtTmplService {
	
	
	@Override
	public SysPrtTmpl getByCode(String code) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("code", code);
		return this.getByFilter(filter);
	}
	
	// ------------------------------------------------------
	
	@Override
	public List<SysPrtTmpl> search(PagingBean pb, Map<String, Object> param) {
		
		HqlFilter filter = new HqlFilter();
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			if ( sortOrder.equals("desc") ) {
				filter.addOrder(sortName, true);
			} else {
				filter.addOrder(sortName);
			}
		} else {
			filter.addOrder("code");
		}
		
		return this.findByFilter(filter, pb);
		
	}
	
	
	// ------------------------------------------------------
	
	
	@Override
	public int delSysPrtTmpl(Integer[] ids) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("id", ids, SqlOperator.in);
		
		// 删除模板文件
		List<SysPrtTmpl> sysPrtTmpls = this.findByFilter( filter );
		for ( SysPrtTmpl sysPrtTmpl : sysPrtTmpls ) {
			String filePath = sysPrtTmpl.getFilePath();
			if ( ! StringUtil.isBlank( filePath ) )
				Webuploader.delFile( filePath );
		}
		
		return this.deleteByFilter( filter );
	}
	
	
	@Override
	public void prtByTmpl(SysPrtTmpl sysPrtTmpl, Map<String, Object> data, String saveAs) {
		
		String filePath = sysPrtTmpl.getFilePath();
		String suffix = filePath.substring( filePath.lastIndexOf(".") ).toLowerCase();
		
		InputStream tmplStream = Webuploader.getFileStream( filePath );
		
		byte[] result = null;
		
		if ( suffix.equals(".doc") ) {
			result = TmplPrtUtil.prtByDoc(tmplStream, data);
		} else if ( suffix.equals(".docx") ) {
			result = TmplPrtUtil.prtByDocx(tmplStream, data);
		}
		
		if ( result == null )
			return;
		
		String filename = saveAs + suffix;
		
		ResponseUtil.responseFile(result, filename);
	}
	
}
