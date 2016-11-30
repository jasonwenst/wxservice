package com.lq.wechatserver.handler;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lq.wechatserver.entity.UserEntity;
import com.lq.wechatserver.repository.UserRepository;
import com.lq.wechatserver.service.CoreService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 用户关注公众号Handler
 * <p>
 * Created by FirenzesEagle on 2016/7/27 0027.
 * Email:liumingbo2008@gmail.com
 */
@Component
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    protected WxMpConfigStorage configStorage;
    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    protected CoreService coreService;
    
    @Autowired
    private UserRepository userRepository;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        WxMpUser wxMpUser = coreService.getUserInfo(wxMessage.getFromUser(), "zh_CN");
        
        UserEntity user = null;
        if(StringUtils.isNotEmpty(wxMessage.getEventKey())) {
        	int userId = Integer.valueOf(wxMessage.getEventKey().substring(8));
        	user = userRepository.findOne(userId);
        }
        
        
        UserEntity entity = new UserEntity();
        entity.setIsPaied("N");    // 默认未支付
        entity.setIsSubscribed("Y"); // 默认订阅
        entity.setJoinedDate(new Timestamp(wxMpUser.getSubscribeTime()*1000L));
        entity.setNickName(wxMpUser.getNickname());
        entity.setOpenId(wxMpUser.getOpenId());
        entity.setUser(user);
        
        // 保存用户
        userRepository.save(entity);

        logger.info("unionId  = {}", wxMpUser.getUnionId());
        WxMpXmlOutTextMessage m
            = WxMpXmlOutMessage.TEXT()
            .content("亲爱的" + wxMpUser.getNickname() + "你好！")
            .fromUser(wxMessage.getToUser())
            .toUser(wxMessage.getFromUser())
            .build();
        logger.info("subscribeMessageHandler" + m.getContent());
        return m;
    }
};
