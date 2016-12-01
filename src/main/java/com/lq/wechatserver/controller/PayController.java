package com.lq.wechatserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lq.wechatserver.service.CoreService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.pay.request.WxPayUnifiedOrderRequest;
import me.chanjar.weixin.mp.bean.pay.result.WxPayUnifiedOrderResult;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping("payment")
public class PayController {
	
	private static final Logger logger  = LoggerFactory.getLogger(PayController.class);
	
	@Autowired
	protected WxMpConfigStorage configStorage;
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	protected CoreService coreService;
	
	@ResponseBody
	@RequestMapping(value = "prepay", method = RequestMethod.POST)
	public ResponseEntity<WxPayUnifiedOrderResult> unionOrderPay(@RequestBody WxPayUnifiedOrderRequest request) throws WxErrorException {
		
		logger.info("prepay processed, request = {}", request.toString());
		
		// appId、mchId、nonceStr、sign 在unifiedOrder中自动获取
		
		WxMpOAuth2AccessToken token = wxMpService.oauth2getAccessToken(request.getAttach()); // 把code放到attach里获得openId
		
//		WxMpOAuth2AccessToken token = new WxMpOAuth2AccessToken();
//		token.setOpenId("dddddd");
		
		request.setDeviceInfo("WEB");  					// 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
		
		WxPayUnifiedOrderResult result = new WxPayUnifiedOrderResult();
		
		request.setOpenid(token.getOpenId());
		request.setOutTradeNo(""); // 商户订单号
		request.setSpbillCreateIp(request.getSpbillCreateIp());  // ip
		
		
		result = wxMpService.getPayService().unifiedOrder(request);
		
		return new ResponseEntity<WxPayUnifiedOrderResult>(result, HttpStatus.OK);
	}

}
