package com.chen.message.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chen.common.MessageUtil;
import com.chen.common.proto.MsgBuf.msgBuf;
import com.chen.message.handler.ChatChannelHandler;
import com.chen.message.handler.HeartbeatChannelHandler;
import com.chen.message.handler.SecurityChannelHandler;
import com.chen.message.manager.ChannelManager;

@Service
public class ChatServer {
	private static final Logger log = Logger.getLogger(ChatServer.class);
	@Autowired
	ChannelManager manager;
	private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	public void start() throws Exception {
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(group);
			serverBootstrap.channel(NioServerSocketChannel.class);
			serverBootstrap.localAddress(new InetSocketAddress(manager.getPort()));
			serverBootstrap.childHandler(new MessageChannelInitializer());

			ChannelFuture f = serverBootstrap.bind().sync();
			log.info(ChatServer.class.getName()
					+ "started and listen on:" + f.channel().localAddress());
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

	class MessageChannelInitializer extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new ProtobufDecoder(msgBuf.getDefaultInstance()));
			ch.pipeline().addLast(new ProtobufEncoder());
/*			ch.pipeline().addLast(new IdleStateHandler(0, 0, 600, TimeUnit.SECONDS));
			ch.pipeline().addLast(new HeartbeatChannelHandler());*/
			
			ch.pipeline().addLast(new SecurityChannelHandler(manager, channelGroup));
			ch.pipeline().addLast(new ChatChannelHandler(manager, channelGroup));
		}

	}

}
