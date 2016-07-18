package com.chen.common;

import java.nio.charset.Charset;

public class MessageUtil {
	private static final String LEFT_BRACKET = "[";
	private static final String RIGHT_BRACKET = "]";
	private static final String COLON = ":";
	private static final String SPACE = " ";
	public static final String SYSTEM = "系统消息";
	public static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

	public static String formatMsg(String name, String msg) {
		StringBuffer buffer = new StringBuffer();
		if (name != null) {
			buffer.append(LEFT_BRACKET).append(name).append(RIGHT_BRACKET).append(COLON).append(SPACE);
		}

		if (msg != null) {
			buffer.append(msg);
		}

		return buffer.toString();
	}

}
