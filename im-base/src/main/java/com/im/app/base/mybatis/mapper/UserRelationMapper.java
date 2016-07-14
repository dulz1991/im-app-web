package com.im.app.base.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.im.app.base.bean.UserRelation;

public interface UserRelationMapper {

	void insert(UserRelation userRelation);
	
	void update(UserRelation userRelation);

	List<UserRelation> getByParm(@Param("parm") UserRelation user);
	
	Integer getByParm_count(@Param("parm") UserRelation user);
	
	UserRelation getById(Long id);

	UserRelation isFriend(@Param("myUserId") Long currentUserId, @Param("friendUserId") Long friendUserId);

}
