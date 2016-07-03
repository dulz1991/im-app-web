package com.im.app.frontend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.im.app.base.bean.News;
import com.im.app.base.bean.UserProfile;
import com.im.app.base.common.CommonConstant;
import com.im.app.base.service.NewsService;
import com.im.app.base.service.UserProfileService;
import com.im.app.frontend.util.CookieUtil;

@Controller
@RequestMapping("/auth/mine")
public class MineController extends BaseController {
	
	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private NewsService newsService;
	
	@ResponseBody
	@RequestMapping(value = "/index", method=RequestMethod.GET)
	public Map<String, Object> index() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("userProfile", getCurrentUser());
		return resMap;
	}
	
	@RequestMapping(value = "/userprofile", method=RequestMethod.GET)
	public ModelAndView userprofile() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mine/userprofile");
		modelAndView.addObject("userProfile", getCurrentUser());
		return modelAndView;
	}
	
	@RequestMapping(value = "/settings", method=RequestMethod.GET)
	public ModelAndView settings() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mine/settings");
		return modelAndView;
	}
	
	@RequestMapping(value = "/createnews", method=RequestMethod.GET)
	public ModelAndView createnews() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mine/createnews");
		return modelAndView;
	}
	
	@RequestMapping(value = "/success", method=RequestMethod.GET)
	public ModelAndView success() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mine/success");
		return modelAndView;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/update", method=RequestMethod.POST)
	public Map<String, Object> update(UserProfile user) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserProfile userProfile = this.getCurrentUser();
		if (userProfile != null) {
			user.setId(userProfile.getId());
			userProfileService.update(user);
			map.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
			
			userProfile = userProfileService.getById(userProfile.getId());
			ObjectMapper mapper = new ObjectMapper();
			String userJson = "";
			try {
				userJson = mapper.writeValueAsString(userProfile);
			} catch (IOException e) {
			}
			CookieUtil.setCookie(CommonConstant.COOKIE_USER, userJson, response);
		} else {
			map.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			map.put(CommonConstant.ERROR_INFO, "系统异常，操作失败");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/doCreateNews", method=RequestMethod.POST)
	public Map<String, Object> doCreateNews(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserProfile userProfile = this.getCurrentUser();
			News news = new News();
			news.setNews(content);
			news.setUserId(userProfile.getId());
			newsService.insert(news);
			map.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
		} catch (Exception e) {
			map.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			map.put(CommonConstant.ERROR_INFO, "系统异常, 请稍后再试");
		}
		return map;
	}
	
}
