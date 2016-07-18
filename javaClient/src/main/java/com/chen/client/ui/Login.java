/**
 * Copyright(C) 2016.Haichen Xin. All Rights Reserved.
 * @author: Haichen Xin
 */
package com.chen.client.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.chen.layout.SGLayout;

public class Login {
	String host;
	int port;
	JFrame frame;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel rootPanel;
	JButton loginBtn;
	JButton backBtn;
	JTextField userNameTextField;

	public void start(String host, int port) {
		this.host = host;
		this.port = port;
		frame = new JFrame("login");
		frame.setResizable(false); // Disable maximize
		frame.setLocationRelativeTo(null);// Window display position in the middle
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		rootPanel = new MyPanel();
		rootPanel.setOpaque(false);
		panel1.setOpaque(false);
		panel2.setOpaque(false);
		panel3.setOpaque(false);
		JLabel lable2 = new JLabel("用户名:");
		lable2.setFont(new Font("宋体", Font.BOLD, 15));
		loginBtn = new JButton("登陆");
		backBtn = new JButton("取消");
		loginBtn.setFont(new Font("宋体", Font.BOLD, 15));
		userNameTextField = new JTextField("", 15);
		userNameTextField.setFont(new Font("宋体", Font.BOLD, 15));
		SGLayout rootLayout = new SGLayout(3,1);
		rootLayout.setRowScale(0, 2);
		rootLayout.setRowScale(1, 5);
		rootLayout.setRowScale(2, 3);
		rootPanel.setLayout(rootLayout);
		SGLayout middleLayout = new SGLayout(1,2);
		middleLayout.setColumnScale(1, 2);
		middleLayout.setColumnAlignment(0, 4, 4);
		middleLayout.setColumnAlignment(1, 3, 1);
		middleLayout.setMargins(5, 20, 5, 20);
		panel2.setLayout(middleLayout);
		rootPanel.add(panel1);
		rootPanel.add(panel2);
		rootPanel.add(panel3);
		frame.getContentPane().add(rootPanel);
		panel2.add(lable2);
		panel2.add(userNameTextField);
		panel3.add(loginBtn);
		panel3.add(backBtn);
		loginBtn.addActionListener(new Listener());
		backBtn.addActionListener(new Listener());
		userNameTextField.addActionListener(new Listener());
		frame.setSize(350, 200); // set size
		frame.setVisible(true); // visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class Listener implements ActionListener {
		String userName = "";

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == loginBtn) {
				userName = userNameTextField.getText();
				if (userName != null && userName.trim().length() <= 0) {
					JOptionPane.showMessageDialog(frame, "用户名不能为空");
				} else if (userName != null && userName.trim().length() >= 10) {
					JOptionPane.showMessageDialog(frame, "用户名长度不能大于10");
				} else {
					userName = userNameTextField.getText();
					frame.dispose();
					new Chat().showChatFrame(userName, host, port);
				}
			} else if (e.getSource() == backBtn){
				System.exit(0);
			}
		}
	}
}
