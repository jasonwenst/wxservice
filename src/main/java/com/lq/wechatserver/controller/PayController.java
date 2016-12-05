package com.lq.wechatserver.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
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

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	protected WxMpConfigStorage configStorage;
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	protected CoreService coreService;
	
	@RequestMapping(value = "success", method = RequestMethod.GET)
	public String paySuccessed() {
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "prepay", method = RequestMethod.POST)
	public ResponseEntity<WxPayUnifiedOrderResult> unionOrderPay(@RequestBody WxPayUnifiedOrderRequest request)
			throws WxErrorException {

		logger.info("prepay processed, request = {}", request.toString());
		// appId、mchId、nonceStr、sign 在unifiedOrder中自动获取

		WxMpOAuth2AccessToken token = wxMpService.oauth2getAccessToken(request.getAttach()); // 把code放到attach里获得openId

		// WxMpOAuth2AccessToken token = new WxMpOAuth2AccessToken();
		// token.setOpenId("dddddd");

		request.setDeviceInfo("WEB"); // 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"

		WxPayUnifiedOrderResult result = new WxPayUnifiedOrderResult();

		request.setOpenid(token.getOpenId());
		request.setOutTradeNo(System.currentTimeMillis() + ""); // 商户订单号
		request.setSpbillCreateIp(request.getSpbillCreateIp()); // ip

		result = wxMpService.getPayService().unifiedOrder(request);

		return new ResponseEntity<WxPayUnifiedOrderResult>(result, HttpStatus.OK);
	}

	/**
	 * 返回前台H5调用JS支付所需要的参数，公众号支付调用此接口
	 *
	 * @param response
	 * @param request
	 * @throws WxErrorException 
	 */
	@RequestMapping(value = "getJSSDKPayInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> getJSSDKPayInfo(@RequestBody WxPayUnifiedOrderRequest request) throws WxErrorException {
		
		logger.info("getJSSDKPayInfo processed , request = {}!", request.toString());
		
//		WxMpOAuth2AccessToken token = wxMpService.oauth2getAccessToken(request.getAttach()); // 把code放到attach里获得openId
//		
//		WxPayUnifiedOrderRequest prepayInfo = new WxPayUnifiedOrderRequest();
//		prepayInfo.setOpenid(token.getOpenId());
//		prepayInfo.setOutTradeNo(request.getOutTradeNo());
//		prepayInfo.setTotalFee(Integer.valueOf(request.getTotalFee()));
//		prepayInfo.setBody(request.getBody());
//		prepayInfo.setTradeType(request.getTradeType());
//		prepayInfo.setSpbillCreateIp(request.getSpbillCreateIp());
//		// TODO(user) 填写通知回调地址
//		prepayInfo.setNotifyURL("/payment/success?fee="+Integer.valueOf(request.getTotalFee())/100);

//		Map<String, String> payInfo = this.wxMpService.getPayService().getPayInfo(prepayInfo);
		
		Map<String, String> payInfo = new HashMap<String, String>();
		payInfo.put("appId", wxMpService.getWxMpConfigStorage().getAppId());
		payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
		payInfo.put("nonceStr", System.currentTimeMillis() + "");
		payInfo.put("package", "prepay_id=" + "234");
		payInfo.put("signType", "MD5");
		payInfo.put("codeUrl", "12314");
		payInfo.put("paySign", "12314sdfgnh");
		
		
//		jsonObject.addProperty("appId", payInfo.get("appId"));
//		jsonObject.addProperty("timeStamp", payInfo.get("timeStamp"));
//		jsonObject.addProperty("nonceStr", payInfo.get("nonceStr"));
//		jsonObject.addProperty("package", payInfo.get("package"));
//		jsonObject.addProperty("signType", payInfo.get("signType"));
//		jsonObject.addProperty("codeUrl", payInfo.get("codeUrl"));
//		jsonObject.addProperty("paySign", payInfo.get("paySign"));
		
		return new ResponseEntity<Map<String, String>>(payInfo, HttpStatus.OK);
	}

}
