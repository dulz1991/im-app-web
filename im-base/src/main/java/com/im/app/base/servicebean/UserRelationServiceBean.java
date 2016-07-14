package com.im.app.base.servicebean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.im.app.base.bean.UserProfile;
import com.im.app.base.bean.UserRelation;
import com.im.app.base.mybatis.mapper.UserRelationMapper;
import com.im.app.base.service.UserRelationService;

@Component
public class UserRelationServiceBean implements UserRelationService {
	
	@Autowired
	private UserRelationMapper userRelationMapper;
	@Autowired
	private UserProfileServiceBean userProfileServiceBean;

	@Override
	public void insert(UserRelation userRelation) {
		userRelationMapper.insert(userRelation);		
	}

	@Override
	public void update(UserRelation userRelation) {
		userRelationMapper.update(userRelation);		
	}

	@Override
	public List<Map<String, Object>> getByParm(UserRelation userRelation) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
		List<UserRelation> friends = userRelationMapper.getByParm(userRelation); 
		for (UserRelation user : friends) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("friendUserId", user.getFriendUserId());
			UserProfile userProfile = userProfileServiceBean.getById(user.getFriendUserId());
			map.put("avatar", userProfile.getAvatar());
			map.put("usernick", userProfile.getUsernick());
			map.put("personalNote", userProfile.getPersonalNote());
			list.add(map);
		}
		return list;
	}

	@Override
	public Integer getByParm_count(UserRelation userRelation) {
		return userRelationMapper.getByParm_count(userRelation);
	}

	@Override
	public UserRelation getById(Long id) {
		return userRelationMapper.getById(id);
	}

	@Override
	public boolean isFriend(Long currentUserId, Long friendUserId) {
		UserRelation userRelation = userRelationMapper.isFriend(currentUserId, friendUserId);
		if (userRelation != null) {
			return true;
		} else {
			return false;	
		}
	}


}
