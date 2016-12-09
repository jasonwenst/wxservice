package test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lq.wechatserver.configuration.WxConfig;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import test.configuration.BaseJUnit4Test;


@ContextConfiguration(classes = WxConfig.class)
@Configuration
@ComponentScan("com.lq.wechatserver")
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateMenuTest extends BaseJUnit4Test{
	
	
	@Autowired
	private WxConfig wxConfig;
	
	@Test
	public void createMenu() {
		WxMpService wxMpService = wxConfig.wxMpService();
		try {
			wxMpService.getMenuService().menuCreate(getMenuInstance());
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public  WxMenu getMenuInstance() {
		WxMpService wxMpService = wxConfig.wxMpService();
		WxMenu menu = new WxMenu();
		
		WxMenuButton button1 = new WxMenuButton();
//		button1.setType(WxConsts.BUTTON_VIEW);
		button1.setName("我的商城");
		
		WxMenuButton button11 = new WxMenuButton();
		button11.setType(WxConsts.BUTTON_VIEW);
		button11.setName("阿里店铺");
//		button11.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://58doublewin.1688.com", "snsapi_base", ""));
		button11.setUrl("http://58doublewin.1688.com");
		
		WxMenuButton button12 = new WxMenuButton();
		button12.setType(WxConsts.BUTTON_CLICK);
		button12.setName("我的二维码");
//		button12.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://58doublewin.1688.com", "snsapi_base", ""));
		button12.setKey("qrcode");
		
		WxMenuButton button13 = new WxMenuButton();
		button13.setType(WxConsts.BUTTON_VIEW);
		button13.setName("加入会员");
		button13.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://123.56.233.132/wechatserver/payment", "snsapi_base", ""));

		WxMenuButton button14 = new WxMenuButton();
		button14.setType(WxConsts.BUTTON_VIEW);
		button14.setName("成功页面");
		button14.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://123.56.233.132/wechatserver/payment/success?code=8", "snsapi_base", ""));

		WxMenuButton button15 = new WxMenuButton();
		button15.setType(WxConsts.BUTTON_VIEW);
		button15.setName("成功页面");
		button15.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://123.56.233.132/wechatserver/payment/redpack", "snsapi_base", ""));

		button1.getSubButtons().add(button11);
		button1.getSubButtons().add(button12);
		button1.getSubButtons().add(button13);
		button1.getSubButtons().add(button14);
		button1.getSubButtons().add(button15);
		
		WxMenuButton button2 = new WxMenuButton();
		button2.setType(WxConsts.BUTTON_VIEW);
		button2.setName("最前沿");
//		button2.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://58doublewin.1688.com", "snsapi_base", ""));
		button2.setUrl("http://58doublewin.1688.com");
	
		WxMenuButton button3 = new WxMenuButton();
		button3.setType(WxConsts.BUTTON_VIEW);
		button3.setName("最前沿");
//		button3.setUrl(wxMpService.oauth2buildAuthorizationUrl("http://58doublewin.1688.com", "snsapi_base", ""));
		button3.setUrl("http://58doublewin.1688.com");
		
		menu.getButtons().add(button1);
		menu.getButtons().add(button2);
		menu.getButtons().add(button3);
		
		return menu;
		
	}

}
