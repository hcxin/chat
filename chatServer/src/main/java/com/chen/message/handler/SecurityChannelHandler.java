package com.chen.message.handler;

import java.util.Date;

import org.apache.log4j.Logger;

import com.chen.common.EType;
import com.chen.common.EUserStatus;
import com.chen.common.entity.UserChannelInfo;
import com.chen.common.entity.UserConnectInfo;
import com.chen.common.proto.MsgBuf;
import com.chen.message.manager.ChannelManager;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;

public class SecurityChannelHandler extends ChannelHandlerAdapter {
	private static final Logger log = Logger.getLogger(SecurityChannelHandler.class);
	private ChannelManager manager;
	private ChannelGroup channelGroup;
	private String uid;
	private AttributeKey<String> attributeKey;
	
	public SecurityChannelHandler(ChannelManager manager, ChannelGroup channelGroup) {
		log.info("SecurityChannelHandler: init");
		this.manager = manager;
		this.channelGroup = channelGroup;
	}
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	if (msg != null){
    		MsgBuf.msgBuf msgBuf = (MsgBuf.msgBuf)msg;
    		log.info("SecurityServerHandler: channelRead type=["+ msgBuf.getType()+"] ,from=:["+msgBuf.getFrom()+"]");
    		if (msgBuf.getType() != EType.ACTIVE.getIndex()){
    			ctx.channel().close();
    		} else if (msgBuf.getFrom() != null) {
    				channelGroup.add(ctx.channel());
    				this.uid = msgBuf.getFrom();
    				if (AttributeKey.exists(uid)) {
    					attributeKey = AttributeKey.valueOf(uid);
    				} else {
    					attributeKey = AttributeKey.newInstance(uid);
    				}
    				if (!manager.getChannePool().containsKey(uid)) {
    					setUserCannel(ctx);
    				}
    			
    			ctx.fireChannelRead(msg);
    			ctx.pipeline().remove(this);
    			log.info("remove SecurityServerHandler !!");
    			}
    		}
    }
    
	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		log.info("SecurityChannelHandler: close");
		if (attributeKey != null) {
			String uid = ctx.channel().attr(attributeKey).get();
			if (uid != null) {
				manager.getChannePool().remove(uid);
			}
		}
	}
    
	private void setUserCannel(ChannelHandlerContext ctx) {
		ctx.channel().attr(attributeKey).set(uid);
		UserChannelInfo ucInfo = new UserChannelInfo();
		ucInfo.setChannel(ctx.channel());
		UserConnectInfo userConnectInfo = new UserConnectInfo();
		userConnectInfo.setUserId(this.uid);
		userConnectInfo.setConnectTime(new Date());
		userConnectInfo.setConnectStatus(EUserStatus.CONNECTED.getIndex());
		ucInfo.setUserConnectInfo(userConnectInfo);
		manager.getChannePool().put(uid, ucInfo);
	}
}
