package com.chen.message.manager;

import java.util.concurrent.ConcurrentHashMap;
import com.chen.common.entity.UserChannelInfo;

public class ChannePool extends ConcurrentHashMap<String, UserChannelInfo> {
	private static final long serialVersionUID = 1L;
}
