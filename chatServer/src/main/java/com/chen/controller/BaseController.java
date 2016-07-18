package com.chen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class BaseController {

	public HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		return request;
	}

	public HttpSession getSession() {
		HttpSession session = null;
		HttpServletRequest request = getRequest();
		if (request != null) {
			session = request.getSession();
		}

		return session;
	}
}
