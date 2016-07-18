/**
 * Copyright(C) 2016.Haichen Xin. All Rights Reserved.
 * @author: Haichen Xin
 */
package com.chen.client.ui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

public class ChatUIMgr {
	private JScrollPane ui = null;
	JList<Object> jList = null;
	private Vector<String> data = new Vector<String>();

	public JScrollPane getUI() {
		if (ui == null) {
			jList = getjList();
			ui = new JScrollPane((jList));
		}
		return ui;
	}

	public void update(String msg) {
		data.add(msg);
		getUI().validate();
		getUI().repaint();
	}

	public JList<Object> getjList() {
		if (jList == null) {
			jList = new JList<Object>(data);
			jList.setAutoscrolls(true);
			jList.setBackground(new Color(0,255,255));
		}

		return jList;
	}

	public void setjList(JList<Object> jList) {
		this.jList = jList;
	}

	public Vector<String> getData() {
		return data;
	}

	public void setData(String msg) {
		this.data.add(msg);
	}

}
