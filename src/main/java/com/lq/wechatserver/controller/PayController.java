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
		
		WxPayUnifiedOrderResult result = new WxPayUnifiedOrderResult();
		
		request.setAppid(configStorage.getAppId());
		request.setOpenid("");
		
		result = wxMpService.getPayService().unifiedOrder(request);
		
		return new ResponseEntity<WxPayUnifiedOrderResult>(result, HttpStatus.OK);
	}

}
