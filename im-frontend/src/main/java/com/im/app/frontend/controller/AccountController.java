package com.im.app.frontend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.im.app.base.bean.UserProfile;
import com.im.app.base.common.CommonConstant;
import com.im.app.base.service.UserProfileService;
import com.im.app.frontend.util.CookieUtil;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserProfileService userProfileService;
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		String value = CookieUtil.getCookie(CommonConstant.COOKIE_USER, request);
		if(StringUtils.isBlank(value)){
    		modelAndView.setViewName("account/signIn");
    	} else {
    		modelAndView.setViewName("index");
    	}
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value = "/doLogin", method=RequestMethod.GET)
	public Map<String, Object> doLogin(UserProfile user, HttpServletResponse response) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		valdateUserInfo(resMap, user);
		if(!resMap.get(CommonConstant.ERROR_NO).equals(CommonConstant.ERROR_200)) { 
			return resMap;
		}
		UserProfile u = userProfileService.findUserByUsernameAndPassword(user);
		if (u != null) {
			ObjectMapper mapper = new ObjectMapper();
			String userJson;
			try {
				userJson = mapper.writeValueAsString(u);
			} catch (IOException e) {
				resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
				resMap.put(CommonConstant.ERROR_INFO, "系统异常, 请稍后再试!");
				return resMap;
			}
			CookieUtil.setCookie(CommonConstant.COOKIE_USER, userJson, response);
			return resMap;
		} else {
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			resMap.put(CommonConstant.ERROR_INFO, "用户名或密码错误!");
			return resMap;
		}
	}
	
	private void valdateUserInfo(Map<String, Object> resMap, UserProfile user) {
		if(StringUtils.isBlank(user.getUsernick())){
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			resMap.put(CommonConstant.ERROR_INFO, "用户名不能为空");
			return;
		}
		if(StringUtils.isBlank(user.getPassword())){
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			resMap.put(CommonConstant.ERROR_INFO, "密码不能为空");
			return;
		}
		resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
	}
	
	@ResponseBody
	@RequestMapping(value = "/logout", method=RequestMethod.GET)
	public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			CookieUtil.deleteCookie(CommonConstant.COOKIE_USER, response, request);
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
		} catch (Exception e) {
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
			resMap.put(CommonConstant.ERROR_INFO, "退出失败, 请稍后再试");
		}
		
		return resMap;
	}

	@ResponseBody
	@RequestMapping(value = "/doRegist", method=RequestMethod.GET)
	public Map<String, Object> doRegist(UserProfile user, HttpServletResponse response,HttpServletRequest request) {
		String conformPassword = request.getParameter("conformPassword");
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("conformPassword", conformPassword);
		reqMap.put("sex", user.getSex());
		reqMap.put("usernick", user.getUsernick());
		reqMap.put("password", user.getPassword());
		reqMap.put("avatar", user.getAvatar());
		
		Map<String, Object> resMap = userProfileService.regist(reqMap);
		if(!resMap.get(CommonConstant.ERROR_NO).equals(CommonConstant.ERROR_200)) { 
			return resMap;
		}
		
		return doLogin(user, response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCurrentUser", method=RequestMethod.GET)
	public Map<String, Object> getCurrentUser(HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		UserProfile userProfile = null;
		try {
			String value = CookieUtil.getCookie(CommonConstant.COOKIE_USER, request);
			if(StringUtils.isNotBlank(value)){
				ObjectMapper mapper = new ObjectMapper();
				userProfile = mapper.readValue(value, UserProfile.class);
				resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
				resMap.put("user", userProfile);
			}
		} catch (IOException e) {
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
		}
		return resMap;
	}

}
