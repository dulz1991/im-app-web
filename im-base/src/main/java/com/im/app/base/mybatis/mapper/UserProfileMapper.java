package com.im.app.base.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.im.app.base.bean.UserProfile;
import com.im.app.base.util.PageUtil;


public interface UserProfileMapper {

	void insert(UserProfile user);
	
	void update(UserProfile user);
	
	UserProfile getById(@Param("id") Long id);
	
	List<UserProfile> getByParm(@Param("page") PageUtil page, @Param("user") UserProfile userProfile);
	
	Integer getByParm_count(@Param("user") UserProfile userProfile);

	UserProfile findUserByUsernameAndPassword(@Param("user") UserProfile userProfile);

	UserProfile getByUsernick(@Param("usernick") String usernick);
	
	List<UserProfile> findByIdsMap(@Param("list") List<String> ids);

}
