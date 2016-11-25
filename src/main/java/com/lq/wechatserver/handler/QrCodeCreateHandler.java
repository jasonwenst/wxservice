package com.lq.wechatserver.handler;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

@Component
public class QrCodeCreateHandler extends AbstractHandler {

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		logger.info("receive msg {}", wxMessage.toString());

		if ("qrCode".equalsIgnoreCase(wxMessage.getEventKey())) {
			WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(123, 3600);
			if (ticket == null) {
				logger.info("ticket is null");
				return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
						.content("无法生成二维码").build();
			}
			File file = wxMpService.getQrcodeService().qrCodePicture(ticket);
			logger.info("qrcode create successed!");

			WxMediaUploadResult media = wxMpService.getMaterialService().mediaUpload(WxConsts.MEDIA_IMAGE, file);

			return WxMpXmlOutMessage.IMAGE().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
					.mediaId(media.getMediaId()).build();
		}
		
		
		return WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
				.content("我草，你想干啥！").build();
	}

}
