package com.chen.message.handler;

import org.apache.log4j.Logger;

import com.chen.common.EType;
import com.chen.common.MessageUtil;
import com.chen.common.proto.MsgBuf.msgBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

public class HeartbeatChannelHandler extends ChannelHandlerAdapter  {
	private static final Logger log = Logger.getLogger(HeartbeatChannelHandler.class);
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
		super.userEventTriggered(ctx, evt);
		if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
            	log.info("READER_IDLE");
                ctx.channel().close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
            	log.info("WRITER_IDLE");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
            	log.info("ALL_IDLE");
    			msgBuf heartbeatMsg = msgBuf.newBuilder().setType(EType.HEARTBEAT.getIndex()).build();
                ctx.channel().writeAndFlush(heartbeatMsg).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }
    	
    }
    
}
