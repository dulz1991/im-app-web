package com.im.app.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.im.app.base.bean.UserProfile;
import com.im.app.base.bean.UserRelation;
import com.im.app.base.service.UserProfileService;
import com.im.app.base.service.UserRelationService;

@Controller
@RequestMapping("/auth/friend")
public class FriendController extends BaseController {
	
	@Autowired
	private UserRelationService userRelationService;
	@Autowired
	private UserProfileService userProfileService;
	
	@ResponseBody
	@RequestMapping(value = "/getFriends", method=RequestMethod.GET)
	public Map<String, Object> index() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Long userId = this.getCurrentUserId();
		UserRelation userRelation = new UserRelation();
		userRelation.setMyUserId(userId);
		userRelation.setStatus(1);
		List<Map<String, Object>> friends =  userRelationService.getByParm(userRelation);
		resMap.put("friends", friends);
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/friendDtail", method=RequestMethod.GET)
	public Map<String, Object> friendinfo(Long friendUserId) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		UserProfile userProfile = userProfileService.getById(friendUserId);
		resMap.put("userProfile", userProfile);
		return resMap;
	}
	
}
