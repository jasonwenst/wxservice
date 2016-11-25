package com.lq.wechatserver.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lq.wechatserver.entity.EmployeeEntity;
import com.lq.wechatserver.repository.EmployeeRepositroy;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 转发客户消息给客服Handler
 *
 * Created by FirenzesEagle on 2016/7/27 0027.
 * Email:liumingbo2008@gmail.com
 */
@Component
public class MsgHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(MsgHandler.class);
	
	@Autowired
	private EmployeeRepositroy repository;
	
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
    	logger.info("msgHandler processed");
    	
    	if("你好".equals(wxMessage.getContent())) {
    		WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("好你个头").build();
    	}
    	
    	long current = System.currentTimeMillis();
    	
    	if(current % 10 == 0) {
    		WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("今天天气不错").build();
    	} else if(current % 10 == 1) {
    		return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("川普当总统了，你还在睡觉，还有脸来问我！").build();
    	} else if(current % 10 == 2) {
    		return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("SB").build();
    	} else if(current % 10 == 3) {
    		return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("你想干啥").build();
    	} else if(current % 10 == 4) {
    		return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("我要草拟").build();
    	} else if(current % 10 == 5) {
    		return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("我要打死你").build();
    	} else if(current % 10 == 6) {
    		return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("哈哈").build();
    	} else if(current % 10 == 7) {
    		return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("我说什么了").build();
    	} else {
    		return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("爱你").build();
    	}
    	
    	return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).content("爱你").build();
    	
//    	return WxMpXmlOutMessage
//                .TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
//                .toUser(wxMessage.getFromUser()).build();
    }
}
