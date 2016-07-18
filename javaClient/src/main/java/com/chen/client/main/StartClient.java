package com.chen.client.main;

import com.chen.client.ui.Login;

/**
 * @author Haichen Xin 
 * start client
 *
 */
public class StartClient {
	private final static String host = "localhost";
	private final static int port = 9898;

	public static void main(String[] args) {
		Login login = new Login();
		login.start(host, port);
	}

}
