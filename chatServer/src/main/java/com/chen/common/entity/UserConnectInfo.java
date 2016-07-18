package com.chen.common.entity;

import java.io.Serializable;
import java.util.Date;

public class UserConnectInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId;
	private Date connectTime;
	private Integer connectStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getConnectTime() {
		return connectTime;
	}

	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}

	public Integer getConnectStatus() {
		return connectStatus;
	}

	public void setConnectStatus(Integer connectStatus) {
		this.connectStatus = connectStatus;
	}
}
