package com.chen.common;

public enum EUserStatus {
	CONNECTED("connected", 0), DISCONNECTED("disconnected", 1), UNKNOWN("unknown", 2);

	private String name;
	private Integer index;

	private EUserStatus(String name, Integer index) {
		this.name = name;
		this.index = index;
	}

	public static EUserStatus fromIndex(Integer index) {
		for (EUserStatus c : EUserStatus.values()) {
			if (c.getIndex() == index) {
				return c;
			}
		}
		return UNKNOWN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
