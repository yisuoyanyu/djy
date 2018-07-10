package com.frame.wxpay.service.impl;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.util.ResourceUtils;

import com.frame.wxpay.config.WXPayParameter;
import com.frame.wxpay.sdk.IWXPayDomain;
import com.frame.wxpay.sdk.WXPayConfig;

public class WXPayConfigImpl extends WXPayConfig {

    private byte[] certData;
    private static WXPayConfigImpl INSTANCE;

    private WXPayConfigImpl() throws Exception {
    	
    	// File file = ResourceUtils.getFile("classpath:apiclient_cert.p12");
    	
    	// String certPath = "D://CERT/common/apiclient_cert.p12";
        // File file = new File(certPath);
    	
    	File file = new File( WXPayParameter.certPath );
    	
    	if ( file.exists() ) {
    		InputStream certStream = new FileInputStream(file);
    		this.certData = new byte[(int) file.length()];
    		certStream.read(this.certData);
    		certStream.close();
    	} else {
    		certData = new byte[0];
    	}
    }

    public static WXPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return WXPayParameter.appID;
    }

    public String getMchID() {
        return WXPayParameter.mchID;
    }

    public String getKey() {
        return WXPayParameter.key;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    protected IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }
}
