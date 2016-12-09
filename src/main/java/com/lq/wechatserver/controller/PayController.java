package com.lq.wechatserver.controller;

import java.io.File;
import java.util.List;
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

import com.lq.wechatserver.configuration.WxConfig;
import com.lq.wechatserver.entity.PayInfoEntity;
import com.lq.wechatserver.entity.PayInfoErrEntity;
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
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping("payment")
public class PayController {

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);
	
	private static final String KEY_FILE_PAHT = "key_file";

	@Autowired
	protected WxMpConfigStorage configStorage;
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	protected CoreService coreService;
	@Autowired
	private PayInfoRepository payInfoRepository;
	@Autowired
	private PayInfoErrRepository payInfoErrRepository;
	@Autowired
	private WxConfig wxConfig;

	@RequestMapping(value = "success", method = RequestMethod.GET)
	public String paySuccessed() {
		return "success";
	}

	@RequestMapping(value = "redpack", method = RequestMethod.GET)
	public String redpack() {
		return "queryReadpack";
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

		WxMpOAuth2AccessToken token = wxMpService.oauth2getAccessToken(request.getAttach()); // 把code放到attach里获得openId

		WxPayUnifiedOrderRequest prepayInfo = new WxPayUnifiedOrderRequest();
		prepayInfo.setOpenid(token.getOpenId());
		prepayInfo.setOutTradeNo(request.getOutTradeNo());
		prepayInfo.setTotalFee(Integer.valueOf(request.getTotalFee()));
		prepayInfo.setBody(request.getBody());
		prepayInfo.setTradeType(request.getTradeType());
		prepayInfo.setSpbillCreateIp(request.getSpbillCreateIp());
//		prepayInfo.setNotifyURL("/payment/success?fee=" + Integer.valueOf(request.getTotalFee()) / 100);

		Map<String, String> payInfo = this.wxMpService.getPayService().getPayInfo(prepayInfo);

		payInfo.put("outTradeNo", request.getOutTradeNo());
		payInfo.put("totalFee", String.valueOf(request.getTotalFee()));

		return new ResponseEntity<Map<String, String>>(payInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "sendRedpack", method = RequestMethod.POST)
	public void sendRedpack(@RequestBody PayInfoEntity entity) {

		dosendRedpack(entity);
	}

	/**
	 * 发送红包， 如果失败，保存失败信息到 TB_PAY_INFO_ERR
	 * 
	 * @param entity
	 * @return
	 */
	private WxPaySendRedpackResult dosendRedpack(PayInfoEntity entity) {

		// 检查是否有支付订单
		WxPayOrderQueryResult result = new WxPayOrderQueryResult();
		try {
			result = wxMpService.getPayService().queryOrder("", entity.getOutTradeNo());

			if ("SUCCESS".equalsIgnoreCase(result.getReturnCode()) && result.getTotalFee() == entity.getTotalFee()
					&& result.getOutTradeNo().equals(entity.getOutTradeNo())) {

				WxPaySendRedpackRequest request = new WxPaySendRedpackRequest();

				request.setMchBillno(wxMpService.getWxMpConfigStorage().getPartnerId() + String.valueOf(System.currentTimeMillis()).substring(3));
				request.setSendName(wxConfig.getConfigByCode("redpack.sendName").getValue());
				request.setReOpenid(entity.getOpenId());
				request.setTotalAmount(Integer.valueOf(wxConfig.getConfigByCode("redpack.amount").getValue()));
				request.setTotalNum(1);
				request.setWishing(wxConfig.getConfigByCode("redpack.wishing").getValue());
				request.setClientIp("127.0.0.1");
				request.setActName(wxConfig.getConfigByCode("redpack.actName").getValue());
				request.setRemark(wxConfig.getConfigByCode("redpack.remark").getValue());

				// 发送红包
				WxPaySendRedpackResult sendRedpackResult  = wxMpService.getPayService().sendRedpack(request, new File(wxConfig.getConfigByCode(KEY_FILE_PAHT).getValue()));

				if ("SUCCESS".equalsIgnoreCase(sendRedpackResult.getReturnCode())
						&& "SUCCESS".equalsIgnoreCase(sendRedpackResult.getResultCode())) {
					payInfoRepository.save(entity);
				} else {
					saveErrInfo(entity, sendRedpackResult.getReturnMsg());

				}

			} else {
				saveErrInfo(entity, result.getReturnMsg());
			}
		} catch (Exception e) {

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

	
	/**
	 * 查询我的红包
	 * 
	 * @param code
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping(value = "queryRedpack", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<PayInfoEntity>> queryRedpack(@RequestParam String code) throws WxErrorException {

		WxMpOAuth2AccessToken token = wxMpService.oauth2getAccessToken(code); // 把code放到attach里获得openId

		List<PayInfoEntity> infos = payInfoRepository.getPayInfoEntityByOpenId(token.getOpenId());

		return new ResponseEntity<List<PayInfoEntity>>(infos, HttpStatus.OK);

	}

}
