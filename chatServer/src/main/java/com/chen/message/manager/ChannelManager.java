package com.chen.message.manager;

public class ChannelManager {
	private int port;
	private ChannePool channePool;
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public ChannePool getChannePool() {
		return channePool;
	}
	public void setChannePool(ChannePool channePool) {
		this.channePool = channePool;
	}
	
}
