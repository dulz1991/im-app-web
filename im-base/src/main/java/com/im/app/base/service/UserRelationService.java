package com.im.app.base.service;

import java.util.List;
import java.util.Map;

import com.im.app.base.bean.UserRelation;

public interface UserRelationService {

	void insert(UserRelation userRelation);
	
	void update(UserRelation userRelation);

	List<Map<String, Object>> getByParm(UserRelation userRelation);
	
	Integer getByParm_count(UserRelation userRelation);
	
	UserRelation getById(Long id);

}
