/**
 * @author: Haichen Xin
 */
package com.chen.client.handler;

import javax.swing.SwingUtilities;

import com.chen.client.ui.ChatUIMgr;
import com.chen.common.EType;
import com.chen.common.MessageUtil;
import com.chen.common.proto.MsgBuf;
import com.chen.common.proto.MsgBuf.msgBuf;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class ChatClientHandler extends ChannelHandlerAdapter {
	String userName;
	ChatUIMgr mgr;

	public ChatClientHandler(String userName, ChatUIMgr mgr) {
		this.userName = userName;
		this.mgr = mgr;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		msgBuf msg = msgBuf.newBuilder().setType(EType.ACTIVE.getIndex()).setFrom(userName).build();
		ctx.channel().writeAndFlush(msg);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg != null) {
			MsgBuf.msgBuf inMsg = (MsgBuf.msgBuf) msg;
			if (!(inMsg.getType()==EType.HEARTBEAT.getIndex())){
			final String msgStr = MessageUtil.formatMsg(inMsg.getFrom(), inMsg.getMsg());
			System.out.println("server msg: " + msgStr);
			
			SwingUtilities.invokeLater(new Runnable(){  
				  public void run() {
					  mgr.setData(msgStr);
					  mgr.getjList().updateUI();
					  mgr.getUI().updateUI();
				    
				    }  
				}); 
			} else {
				System.out.println("heartbeat");
    			msgBuf heartbeatMsg = msgBuf.newBuilder().setType(EType.HEARTBEAT.getIndex()).build();
    			ctx.channel().writeAndFlush(heartbeatMsg);
			}
		}
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		System.out.println("channel: disconnect");
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		System.out.println("channel: close");
		
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channel: Inactive");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
