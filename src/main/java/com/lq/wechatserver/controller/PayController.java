package com.lq.wechatserver.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.lq.wechatserver.entity.PayInfoEntity;
import com.lq.wechatserver.entity.PayInfoErrEntity;
import com.lq.wechatserver.messaging.MessageSender;
import com.lq.wechatserver.repository.PayInfoErrRepository;
import com.lq.wechatserver.repository.PayInfoRepository;
import com.lq.wechatserver.service.CoreService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.pay.request.WxPaySendRedpackRequest;
import me.chanjar.weixin.mp.bean.pay.request.WxPayUnifiedOrderRequest;
import me.chanjar.weixin.mp.bean.pay.result.WxPayOrderQueryResult;
import me.chanjar.weixin.mp.bean.pay.result.WxPaySendRedpackResult;
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
	@Autowired
	private PayInfoRepository payInfoRepository;
	@Autowired
	private MessageSender messageSender;
	@Autowired
	private PayInfoErrRepository payInfoErrRepository;

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
	public ResponseEntity<Map<String, String>> getJSSDKPayInfo(@RequestBody WxPayUnifiedOrderRequest request)
			throws WxErrorException {

		logger.info("getJSSDKPayInfo processed , request = {}!", request.toString());

		// WxMpOAuth2AccessToken token =
		// wxMpService.oauth2getAccessToken(request.getAttach()); //
		// 把code放到attach里获得openId
		//
		// WxPayUnifiedOrderRequest prepayInfo = new WxPayUnifiedOrderRequest();
		// prepayInfo.setOpenid(token.getOpenId());
		// prepayInfo.setOutTradeNo(request.getOutTradeNo());
		// prepayInfo.setTotalFee(Integer.valueOf(request.getTotalFee()));
		// prepayInfo.setBody(request.getBody());
		// prepayInfo.setTradeType(request.getTradeType());
		// prepayInfo.setSpbillCreateIp(request.getSpbillCreateIp());
		// // TODO(user) 填写通知回调地址
		// prepayInfo.setNotifyURL("/payment/success?fee="+Integer.valueOf(request.getTotalFee())/100);

		Map<String, String> payInfo = new HashMap<String, String>();
		payInfo.put("outTradeNo", request.getOutTradeNo());
		payInfo.put("totalFee", String.valueOf(request.getTotalFee()));

		// Map<String, String> payInfo =
		// this.wxMpService.getPayService().getPayInfo(prepayInfo);

		payInfo.put("appId", wxMpService.getWxMpConfigStorage().getAppId());
		payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
		payInfo.put("nonceStr", System.currentTimeMillis() + "");
		payInfo.put("package", "prepay_id=" + "234");
		payInfo.put("signType", "MD5");
		payInfo.put("codeUrl", "12314");
		payInfo.put("paySign", "12314sdfgnh");

		return new ResponseEntity<Map<String, String>>(payInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "sendRedpack", method = RequestMethod.POST)
	public void sendRedpack(@RequestBody PayInfoEntity entity) {

		dosendRedpack(entity);
	}

	@RequestMapping(value = "savePayInfo", method = RequestMethod.POST)
	public void savePayInfo(@RequestBody PayInfoEntity entity) {

		messageSender.sendMessage(entity);

		// entity.setCreateTime(new Timestamp(entity.getCreateTimestamp()));
		// payInfoRepository.save(entity);

	}

	
	/**
	 * 发送红包， 如果失败，保存失败信息到 TB_PAY_INFO_ERR
	 * @param entity
	 * @return
	 */
	private WxPaySendRedpackResult dosendRedpack(PayInfoEntity entity) {

		// 检查是否有支付订单
		WxPayOrderQueryResult result;
		try {
			result = wxMpService.getPayService().queryOrder("", entity.getOutTradeNo());

			if ("SUCCESS".equalsIgnoreCase(result.getReturnCode()) && result.getTotalFee() == entity.getTotalFee()
					&& result.getOutTradeNo().equals(entity.getOutTradeNo())) {

				WxPaySendRedpackRequest request = new WxPaySendRedpackRequest();

				request.setMchBillno(wxMpService.getWxMpConfigStorage().getPartnerId() + "23423");
				request.setSendName("vip会员");
				request.setReOpenid(entity.getOpenId());
				request.setTotalAmount(200);
				request.setTotalNum(1);
				request.setWishing("感谢您参加猜灯谜活动，祝您元宵节快乐！");
				request.setClientIp("127.0.0.1");
				request.setActName("猜灯谜抢红包活动");
				request.setRemark("活动");

				// 发送红包
				WxPaySendRedpackResult sendRedpackResult;
				sendRedpackResult = wxMpService.getPayService().sendRedpack(request, new File(""));

				if ("SUCCESS".equalsIgnoreCase(sendRedpackResult.getReturnCode())
						&& "SUCCESS".equalsIgnoreCase(sendRedpackResult.getResultCode())) {
					payInfoRepository.save(entity);
				} else {
					saveErrInfo(entity, sendRedpackResult.getReturnMsg());
				}

			} else {
				saveErrInfo(entity, result.getReturnMsg());
			}
		} catch (WxErrorException e) {
			
			saveErrInfo(entity, e.getMessage());
			logger.error("发送红包失败", e);
		}
		return null;
	}
	
	private void saveErrInfo(PayInfoEntity entity, String msg) {
		PayInfoErrEntity errEntity = new PayInfoErrEntity();
		BeanUtils.copyProperties(entity, errEntity);
		errEntity.setErrMsg(msg);
		errEntity.setPayInfoId(0);
		payInfoErrRepository.save(errEntity);
	}

}
