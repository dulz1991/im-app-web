package com.im.app.base.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.im.app.base.bean.NewFriend;

public interface NewFriendMapper {

	void insert(NewFriend newFriend);
	
	void update(NewFriend newFriend);

	List<Map<String, Object>> getByParm(@Param("parm") Map<String, Object> parm);
	
	Integer getByParm_count(@Param("parm") Map<String, Object> parm);
	
	Map<String, Object> getById(Long id);

}
