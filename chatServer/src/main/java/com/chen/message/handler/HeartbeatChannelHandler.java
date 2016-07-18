package com.chen.message.handler;

import org.apache.log4j.Logger;

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
		if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("READER_IDLE");
                ctx.channel().close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("WRITER_IDLE");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("ALL_IDLE");
                ByteBuf ping = Unpooled.copiedBuffer("ping".getBytes(CharsetUtil.UTF_8));
                ctx.channel().writeAndFlush(ping).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
        	log.info("server userEventTriggered====" );  
        	super.userEventTriggered(ctx, evt); 
        }
    	
    }
    
}
