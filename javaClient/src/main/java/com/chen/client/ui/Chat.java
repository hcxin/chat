/**
 * Copyright(C) 2016.Haichen Xin. All Rights Reserved.
 * @author: Haichen Xin
 */
package com.chen.client.ui;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetSocketAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.chen.client.handler.ChatClientHandler;
import com.chen.common.EType;
import com.chen.common.proto.MsgBuf.msgBuf;
import com.chen.layout.SGLayout;

public class Chat {
	private int port;
	private String host;
	private String userName = "";
	private JFrame chatFrame = new JFrame("群聊");
	ChatUIMgr mgr = new ChatUIMgr();
	private JPanel rootPanel;
	JTextField chatField = new JTextField(20);
	JButton btnSend = new JButton("发送");

	static Channel channel = null;

	void showChatFrame(final String userName, final String host, final int port) {
		this.userName = userName;
		JLabel label = new JLabel(userName + ":");
		this.host = host;
		this.port = port;
		new Thread(new Runnable() {

			public void run() {
				try {
					start(userName, host, port);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}).start();
		
		rootPanel = new MyPanel();
		rootPanel.setOpaque(false);
		SGLayout rootLayout = new SGLayout(2, 1);
		rootLayout.setRowScale(0,8);
		rootLayout.setRowScale(1,2);
		rootLayout.setMargins(15, 15, 10, 15);
		rootPanel.setLayout(rootLayout);
		
		chatFrame.setSize(500, 300);
		JScrollPane chatUI = mgr.getUI();
		chatUI.setSize(new Dimension(100,100));
		JPanel actionPanel = new JPanel();
		actionPanel.setOpaque(false);
		actionPanel.setLayout(new FlowLayout());
		actionPanel.add(label);
		actionPanel.add(chatField);
		actionPanel.add(btnSend);
		chatFrame.getContentPane().add(rootPanel);
		rootPanel.add(chatUI);
		rootPanel.add(actionPanel);
		chatFrame.setLocationRelativeTo(null);
		chatFrame.setResizable(false);
		chatFrame.setVisible(true);
		chatFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (channel != null) {
					msgBuf msg = msgBuf.newBuilder().setType(EType.CLOSE.getIndex()).build();
					channel.writeAndFlush(msg);
					channel.eventLoop().shutdownGracefully();
				}
				System.exit(0);
			}

		});

		btnSend.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					if (!chatField.getText().trim().equals("")) {
						String line = chatField.getText();
						System.out.println(line);
						ChannelFuture writeFuture = null;
						msgBuf msg = msgBuf.newBuilder().setType(EType.PS.getIndex()).setFrom(userName).setMsg(line).build();
						writeFuture = channel.writeAndFlush(msg);
						chatField.setText("");

						if (writeFuture != null) {
							writeFuture.sync();
						}
					}
				} catch (InterruptedException e3) {
					e3.printStackTrace();
				} finally {
					
				}

			}
		});

	}

	public void start(final String userName, String host, int port)
			throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.remoteAddress(new InetSocketAddress(host, port))
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(new ProtobufDecoder(msgBuf.getDefaultInstance()));
							ch.pipeline().addLast(new ProtobufEncoder());
							ch.pipeline().addLast(new ChatClientHandler(userName, mgr));
						}
					});

			channel = b.connect().sync().channel();

			channel.closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

}
