package com.chen.message.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.chen.common.EType;
import com.chen.common.MessageUtil;
import com.chen.common.proto.MsgBuf;
import com.chen.common.proto.MsgBuf.msgBuf;
import com.chen.message.manager.ChannelManager;

public class ChatChannelHandler extends ChannelHandlerAdapter {
	private static final Logger log = Logger
			.getLogger(ChatChannelHandler.class);
	ChannelManager manager;
	ChannelGroup channelGroup;
	private String uid;
	AttributeKey<String> attributeKey;

	public ChatChannelHandler(ChannelManager manager, ChannelGroup channelGroup) {
		log.info("ChatChannelHandler: init");
		this.manager = manager;
		this.channelGroup = channelGroup;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("ChatChannelHandler: channelActive");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		removeConnection();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		MsgBuf.msgBuf inMsg = (MsgBuf.msgBuf) msg;
		log.info("ChatChannelHandler: channelRead type=[" + inMsg.getType()
				+ "] ,from=:[" + inMsg.getFrom() + "]");
		String from = inMsg.getFrom();
		String to = inMsg.getTo();
		if (inMsg.getType() == EType.ACTIVE.getIndex()) {
			this.uid = inMsg.getFrom();
		} else if (inMsg.getType() == EType.PTP.getIndex()) {
			if (manager.getChannePool().containsKey(from)
					&& manager.getChannePool().containsKey(to)) {
				msgBuf outMsg = msgBuf.newBuilder()
						.setType(EType.PTP.getIndex()).setFrom(from)
						.setMsg(inMsg.getMsg()).build();
				manager.getChannePool().get(to).getChannel()
						.writeAndFlush(outMsg);
			} 
		} else if (inMsg.getType() == EType.PS.getIndex()) {
			msgBuf outMsg = msgBuf.newBuilder().setType(EType.PS.getIndex())
					.setFrom(from).setMsg(inMsg.getMsg()).build();
			channelGroup.writeAndFlush(outMsg);
		} else if (inMsg.getType() == EType.SYS.getIndex()) {
			msgBuf outMsg = msgBuf.newBuilder().setType(EType.SYS.getIndex())
					.setFrom(MessageUtil.SYSTEM).setMsg(inMsg.getMsg()).build();
			channelGroup.writeAndFlush(outMsg);
		} else if (inMsg.getType() == EType.HEARTBEAT.getIndex()) {
			log.info("HEARTBEAT!!");
		}  else if (inMsg.getType() == EType.CLOSE.getIndex()) {
			removeConnection();
		} 

	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		removeConnection();
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		log.info("ChatChannelHandler: close");
		removeConnection();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		removeConnection();
		log.info("ChatChannelHandler: exceptionCaught: hit excepition");
		cause.printStackTrace();
		ctx.close();
		log.info("exceptionCaught: end");
	}

	private void removeConnection(){
		if (uid != null) {
			manager.getChannePool().remove(uid);
		}
	}
}
