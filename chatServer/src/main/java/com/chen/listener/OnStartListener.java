package com.chen.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ServletContextAware;

import com.chen.message.manager.ChannelManager;
import com.chen.message.server.ChatServer;

public class OnStartListener implements ApplicationContextAware ,ApplicationListener<ContextRefreshedEvent>,ServletContextAware{
	@Resource
	ChatServer chatServer;
	@Autowired
	ChannelManager manager;
    private static Log log = LogFactory.getLog(OnStartListener.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        	new Thread(){
        		@Override
        		public void run() {
        			try {
        			chatServer.start();
        			} catch (Exception e) {
        				log.error("server start failed,hit exception!!", e);
        			}
        			
        		}
        	}.start();
    	
    	
    }

    @Override
    public void setServletContext(ServletContext servletcontext) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {
        // TODO Auto-generated method stub
        
    }


}
