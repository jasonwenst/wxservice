package test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpPayService;
import me.chanjar.weixin.mp.bean.pay.request.WxPayUnifiedOrderRequest;

public class PayTest {
	
	@Autowired
	private WxMpPayService wxMpPayService;
	
	
	@Test
	public void test() {
		
		WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
		
		request.setAppid("");
		request.setMchId(""); 			 // 商户号
		request.setDeviceInfo("WEB");	// 微信公众平台
		request.setNonceStr(""); 		// 随机数
		request.setSign("");  			// 签名
		request.setBody(""); 			//商品描述
		request.setDetail("");  		// 商品详情
		
		try {
			wxMpPayService.unifiedOrder(request);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
