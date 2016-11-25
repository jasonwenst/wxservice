package com.lq.wechatserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@Configuration
@PropertySource(value = "classpath:wx.properties")
public class WxConfig {
	
//	@Value("#{wxProperties.appid}")
//	private String appid;
//
//	@Value("#{wxProperties.appsecret}")
//	private String appsecret;
//
//	@Value("#{wxProperties.token}")
//	private String token;
//
//	@Value("#{wxProperties.aeskey}")
//	private String aesKey;
//
//	@Value("#{wxProperties.partener_id}")
//	private String partenerId;
//
//	@Value("#{wxProperties.partener_key}")
//	private String partenerKey;
	
	@Autowired
	Environment environment;

	@Bean
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		configStorage.setAppId(environment.getProperty("appid"));
		configStorage.setSecret(environment.getProperty("appsecret"));
		configStorage.setToken(environment.getProperty("token"));
		configStorage.setAesKey(environment.getProperty("aesKey"));
		configStorage.setPartnerId(environment.getProperty("partenerId"));
		configStorage.setPartnerKey(environment.getProperty("partenerKey"));
		return configStorage;
	}

	@Bean
	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}

}
