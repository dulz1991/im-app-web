package com.im.app.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.im.app.base.bean.UserProfile;
import com.im.app.base.service.UserProfileService;

@Controller
@RequestMapping("/auth/chat")
public class ChatController extends BaseController {
	
	@Autowired
	private UserProfileService userProfileService;
	
	@ResponseBody
	@RequestMapping(value = "/chatRoom", method=RequestMethod.GET)
	public Map<String, Object> chatRoom(Long friendUserId) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		UserProfile userProfile = userProfileService.getById(friendUserId);
		resMap.put("userProfile", userProfile);
		resMap.put("currentUserId", this.getCurrentUserId());
		return resMap;
	}
	
}
