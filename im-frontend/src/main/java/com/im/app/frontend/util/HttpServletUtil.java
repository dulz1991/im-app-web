package com.im.app.frontend.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

public class HttpServletUtil {
	
	public static HttpServletRequest request;
	public static HttpServletResponse response;
	
	static {
		request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		ServletWebRequest servletWebRequest=new ServletWebRequest(request);
		response = servletWebRequest.getResponse();
	}
	
}
