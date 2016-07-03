package com.im.app.frontend.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.im.app.base.bean.UserProfile;
import com.im.app.base.common.CommonConstant;
import com.im.app.frontend.util.CookieUtil;

public class BaseController {
	
	protected HttpServletRequest request;  
    protected HttpServletResponse response;  
      
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;
        this.response = response;
    }

	public UserProfile getCurrentUser() {
		UserProfile userProfile = null;
		try {
			String value = CookieUtil.getCookie(CommonConstant.COOKIE_USER, request);
			if(StringUtils.isNotBlank(value)){
				ObjectMapper mapper = new ObjectMapper();
				userProfile = mapper.readValue(value, UserProfile.class);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userProfile;
	}
	
	public Long getCurrentUserId() {
		UserProfile user = this.getCurrentUser();
		if (user != null) {
			return user.getId();	
		} else {
			return 0L;
		}
	}
}
