package com.im.app.base.service;

import java.util.List;
import java.util.Map;

import com.im.app.base.bean.UserProfile;
import com.im.app.base.util.PageUtil;

public interface UserProfileService {

	void insert(UserProfile user);
	
	void update(UserProfile user);

	List<UserProfile> getByParm(PageUtil page, UserProfile user);

	Integer getByParm_count(UserProfile user);
	
	UserProfile getById(Long id);

	UserProfile findUserByUsernameAndPassword(UserProfile user);
	
	UserProfile getByUsernick(String username);
	
	List<UserProfile> findByIdsMap(List<String> ids);
	
	Map<String, Object> regist(Map<String, Object> reqMap);
	
}
