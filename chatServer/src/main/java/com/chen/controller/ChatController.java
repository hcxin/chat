package com.chen.controller;

import io.netty.channel.Channel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chen.common.EType;
import com.chen.common.MessageUtil;
import com.chen.common.entity.Message;
import com.chen.common.entity.UserChannelInfo;
import com.chen.common.entity.UserConnectInfo;
import com.chen.common.proto.MsgBuf.msgBuf;
import com.chen.message.manager.ChannelManager;
import com.chen.model.PushUserListVO;
import com.chen.service.IPushMessageService;

@Controller
@RequestMapping("/chat")
public class ChatController extends BaseController {
	// http://120.25.243.63/qrcode/shadow/getImage?type=1
	private static final Logger log = Logger.getLogger(ChatController.class);
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Autowired
	ChannelManager manager;
	@Resource
	IPushMessageService pushMsgService;

	@RequestMapping("/pushMessage")
	@ResponseBody
	public int pushMessage(@RequestBody Message message) {
		int count = 0;
		if (message != null && StringUtils.isNotEmpty(message.getContent())) {

			Set<String> uuidKeySet = manager.getChannePool().keySet();
			count = uuidKeySet.size();
			for (String key : uuidKeySet) {
				UserChannelInfo ucInfo = manager.getChannePool().get(key);
				Channel channel = ucInfo.getChannel();
				//String sysMsg = MessageUtil.formatMsg(MessageUtil.SYSTEM, message.getContent());
				
				msgBuf outMsg = msgBuf.newBuilder().setType(EType.SYS.getIndex())
						.setFrom(MessageUtil.SYSTEM).setMsg(message.getContent()).build();
				channel.writeAndFlush(sysMsg);
			}
		}
		return count;

	}

	@RequestMapping("/pushMessageToSingle")
	@ResponseBody
	public int pushMessageToSingle(@RequestBody Message message) {
		String sysMsg = null;
		int count = 0;
		if (message != null && StringUtils.isNotEmpty(message.getContent())) {
			sysMsg = MessageUtil.formatMsg(MessageUtil.SYSTEM, message.getContent());
		}

		if (sysMsg != null && message.getUserId() != null) {
			UserChannelInfo ucInfo = manager.getChannePool().get(message.getUserId());
			Channel channel = ucInfo.getChannel();
			channel.writeAndFlush(sysMsg);
			count = 1;
		}
		return count;

	}

	@RequestMapping("/pushIndex")
	public String pushIndex(HttpServletRequest request,
			HttpServletResponse response) {

		return "push";
	}

	@RequestMapping("/getUserList")
	@ResponseBody
	public List<PushUserListVO> getUserList(HttpServletRequest request,
			HttpServletResponse response) {
		List<PushUserListVO> userList = new ArrayList<PushUserListVO>();
		Set<String> uuidKeySet = manager.getChannePool().keySet();
		for (String key : uuidKeySet) {
			UserChannelInfo ucInfo = manager.getChannePool().get(key);
			UserConnectInfo userInfo = ucInfo.getUserConnectInfo();
			PushUserListVO vo = new PushUserListVO();
			pushMsgService.formatUserInfo(userInfo, vo);
			userList.add(vo);
		}

		return userList;
	}
}
