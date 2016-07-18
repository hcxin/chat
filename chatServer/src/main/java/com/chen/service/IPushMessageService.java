package com.chen.service;

import com.chen.common.entity.UserConnectInfo;
import com.chen.model.PushUserListVO;

public interface IPushMessageService {
	
	public void pushMessage();

	public void formatUserInfo(UserConnectInfo userInfo, PushUserListVO vo);

}
