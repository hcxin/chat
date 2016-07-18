package com.chen.service.impl;

import io.netty.util.CharsetUtil;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

import com.chen.common.EUserStatus;
import com.chen.common.entity.UserConnectInfo;
import com.chen.model.PushUserListVO;
import com.chen.service.IPushMessageService;

@Service
public class PushMessageServiceImpl implements IPushMessageService {
	private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	@Override
	public void pushMessage() {
		
	}

	@Override
	public void formatUserInfo(UserConnectInfo userInfo, PushUserListVO vo) {
		vo.setUserName(userInfo.getUserId());
		vo.setConnectTime(formatter.format(userInfo.getConnectTime()));
		String status = EUserStatus.fromIndex(userInfo.getConnectStatus()).getName();
		//System.out.println("Default Charset in Use=" + getDefaultCharSet()); 
		vo.setConnectStatus(status);
	}
	
	   private static String getDefaultCharSet() {
		    System.out.println("Default Charset=" + Charset.defaultCharset());  
	        System.out.println("file.encoding=" + System.getProperty("file.encoding"));  
	        System.out.println("Default Charset=" + Charset.defaultCharset());  
	        System.out.println("Default Charset in Use=" + getDefaultCharSet());  
	        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());  
	        String enc = writer.getEncoding();  
	        return enc;  
	    }  
}
