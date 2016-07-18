package com.chen.common.entity;

import io.netty.channel.Channel;

import java.io.Serializable;

public class UserChannelInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Channel channel;
	private UserConnectInfo userConnectInfo;
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public UserConnectInfo getUserConnectInfo() {
		return userConnectInfo;
	}
	public void setUserConnectInfo(UserConnectInfo userConnectInfo) {
		this.userConnectInfo = userConnectInfo;
	}
	
}
