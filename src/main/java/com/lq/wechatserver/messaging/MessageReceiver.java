package com.lq.wechatserver.messaging;

import java.io.File;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.lq.wechatserver.entity.PayInfoEntity;
import com.lq.wechatserver.repository.PayInfoRepository;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.pay.request.WxPaySendRedpackRequest;
import me.chanjar.weixin.mp.bean.pay.result.WxPayOrderQueryResult;


@Component
public class MessageReceiver {
	static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

	private static final String ORDER_RESPONSE_QUEUE = "payinfo-queue";
	
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private PayInfoRepository repositroy;
	@Autowired
	private MessageSender sender;	
	
	
	
	@JmsListener(destination = ORDER_RESPONSE_QUEUE)
	public void receiveMessage(final Message<PayInfoEntity> message) throws JMSException {
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		MessageHeaders headers =  message.getHeaders();
		LOG.info("Application : headers received : {}", headers);
		
		PayInfoEntity entity = message.getPayload();
		LOG.info("Application : response received : {}",entity);
		
		
		try {
			WxPayOrderQueryResult result = wxMpService.getPayService().queryOrder("", entity.getOutTradeNo());
			if("SUCCESS".equalsIgnoreCase(result.getReturnCode()) && result.getTotalFee() == entity.getTotalFee() &&
					result.getOutTradeNo().equals(entity.getOutTradeNo())) {
				
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
				
				wxMpService.getPayService().sendRedpack(request, new File(""));   // 获取证书
				
				repositroy.save(entity);
				
			}
		} catch (WxErrorException e) {
//			sender.sendMessage(entity);
			LOG.error("红包发送失败", e);
			
			throw new RuntimeException();
			
		}
		
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
}
