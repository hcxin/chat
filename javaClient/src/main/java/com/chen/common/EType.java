package com.chen.common;

public enum EType {
	ACTIVE("active", 0), PTP("point-to-point", 1),PS("publish-subscribe", 2),SYS("system", 3), INACTIVE("inactive", 4), HEARTBEAT("heartbeat", 5), CLOSE("close", 6);

	private String name;
	private Integer index;

	private EType(String name, Integer index) {
		this.name = name;
		this.index = index;
	}

	public static EType fromIndex(Integer index) {
		for (EType c : EType.values()) {
			if (c.getIndex() == index) {
				return c;
			}
		}
		return PTP;
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
