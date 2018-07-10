package com.djy.co.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.djy.co.model.CoPartnerImg;
import com.djy.co.service.CoPartnerImgService;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.upload.Webuploader;

@Service("coPartnerImgService")
public class CoPartnerImgServiceImpl extends BaseServiceImpl<CoPartnerImg> implements CoPartnerImgService {

	private static Logger logger = LoggerFactory.getLogger( CoPartnerImgServiceImpl.class );
	
	@Override
	public Boolean delCoPartnerImg( CoPartnerImg coPartnerImg ) {
		try {
			String imgPath = coPartnerImg.getImgPath();
			this.delete( coPartnerImg );
			Webuploader.delFile(imgPath);
		} catch(Exception e) {
			logger.error("",e);
			return false;
		}
		return true;
	}
	
}
