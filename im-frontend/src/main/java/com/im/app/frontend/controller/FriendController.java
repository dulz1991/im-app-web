package com.im.app.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.im.app.base.bean.NewFriend;
import com.im.app.base.bean.UserProfile;
import com.im.app.base.bean.UserRelation;
import com.im.app.base.common.CommonConstant;
import com.im.app.base.service.NewFriendService;
import com.im.app.base.service.UserProfileService;
import com.im.app.base.service.UserRelationService;

@Controller
@RequestMapping("/auth/friend")
public class FriendController extends BaseController {
	
	@Autowired
	private UserRelationService userRelationService;
	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private NewFriendService newFriendService;
	
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
		resMap.put("isFriend", userRelationService.isFriend(this.getCurrentUserId(),friendUserId));
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getFriendByUsernick", method=RequestMethod.GET)
	public Map<String, Object> getFriendByUsernick(String usernick) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		UserProfile userProfile = userProfileService.getByUsernick(usernick);
		if(userProfile==null){
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			resMap.put(CommonConstant.ERROR_INFO, "未找到");
		}else{
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
			resMap.put("userProfile", userProfile);
		}
		
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addNewFriend", method=RequestMethod.GET)
	public Map<String, Object> addNewFriend(Long friendUserId) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		UserProfile userProfile = userProfileService.getById(friendUserId);
		if(userProfile==null){
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			resMap.put(CommonConstant.ERROR_INFO, "用户不存在或已注销");
			return resMap;
		}
		Long currentUserId = this.getCurrentUserId();
		boolean isFriend = userRelationService.isFriend(currentUserId, friendUserId);
		if(isFriend){
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			resMap.put(CommonConstant.ERROR_INFO, "你们已经是好友了");
			return resMap;
		}
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("requestUserId", currentUserId);
		reqMap.put("targetUserId", friendUserId);
		reqMap.put("status", 1);
		int count = newFriendService.getByParm_count(reqMap);
		if(count<=0){
			NewFriend newFriend = new NewFriend();
			newFriend.setRequestUserId(currentUserId);
			newFriend.setTargetUserId(friendUserId);
			newFriendService.insert(newFriend);	
		}
		
		/*IMWebSocket webSocket =  IMWebSocket.webSocketMap.get(friendUserId);
		webSocket.newFriendNotify(userProfile);*/
		
		resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getNewFriends", method=RequestMethod.GET)
	public Map<String, Object> getNewFriends() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		Long currentUserId = this.getCurrentUserId();
		reqMap.put("targetUserId", currentUserId);
		reqMap.put("status", 1);
		List<Map<String, Object>> friends =  newFriendService.getByParm(reqMap);
		resMap.put("friends", friends);
		resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/agreeNewFriend", method=RequestMethod.POST)
	public Map<String, Object> agreeNewFriend(Long id, Long friendUserId) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Long currentUserId = this.getCurrentUserId();
		NewFriend newFriend = new NewFriend();
		newFriend.setId(id);
		newFriend.setStatus(0);
		newFriendService.update(newFriend);
		UserRelation userRelation = new UserRelation();
		userRelation.setMyUserId(currentUserId);
		userRelation.setFriendUserId(friendUserId);
		userRelation.setStatus(1);
		userRelationService.insert(userRelation);
		userRelation = new UserRelation();
		userRelation.setMyUserId(friendUserId);
		userRelation.setFriendUserId(currentUserId);
		userRelationService.insert(userRelation);
		userRelation.setStatus(1);
		resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/rejectNewFriend", method=RequestMethod.POST)
	public Map<String, Object> rejectNewFriend(Long id, Long friendUserId) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		NewFriend newFriend = new NewFriend();
		newFriend.setId(id);
		newFriend.setStatus(0);
		newFriendService.update(newFriend);
		resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
		return resMap;
	}
	
}
