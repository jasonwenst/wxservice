package test.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.lq.wechatserver.entity.SysConfigEntity;
import com.lq.wechatserver.repository.SysConfigRepository;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@Configuration
@PropertySource(value = "classpath:wx.properties")
public class WxConfig {
	
	@Autowired
	Environment environment;
	@Autowired
	private SysConfigRepository repository;
	
	private Map<String, SysConfigEntity> sysDataMap;
	
	private Map<String, SysConfigEntity> loadData() {
		sysDataMap  = new HashMap<String, SysConfigEntity>();
		
		List<SysConfigEntity> list = (List<SysConfigEntity>) repository.findAll();
		
		for(SysConfigEntity entity : list) {
			sysDataMap.put(entity.getCode(), entity);
		}
		return sysDataMap;
	}
	
	public SysConfigEntity getConfigByCode(String code) {
		if(StringUtils.isEmpty(code)) {
			return null;
		}
		
		if(sysDataMap == null) {
			loadData();
		}
		
		return sysDataMap.get(code);
	}

	@Bean
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		
		loadData();
		
		configStorage.setAppId(getConfigByCode("wx.appId").getValue());
		configStorage.setSecret(getConfigByCode("wx.appsecret").getValue());
		configStorage.setToken(getConfigByCode("wx.token").getValue());
		configStorage.setAesKey(getConfigByCode("wx.aesKey").getValue());
		configStorage.setPartnerId(getConfigByCode("wx.partenerId").getValue());
//		configStorage.setPartnerKey(getConfigByCode("wx.partenerKey").getValue());
		
		return configStorage;
	}

	@Bean
	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}

}
