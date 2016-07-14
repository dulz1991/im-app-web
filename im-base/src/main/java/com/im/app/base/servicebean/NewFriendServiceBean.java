package com.im.app.base.servicebean;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.im.app.base.bean.NewFriend;
import com.im.app.base.mybatis.mapper.NewFriendMapper;
import com.im.app.base.service.NewFriendService;

@Component
public class NewFriendServiceBean implements NewFriendService {

	@Autowired 
	private NewFriendMapper newFriendMapper;
	
	@Override
	public void insert(NewFriend newFriend) {
		newFriendMapper.insert(newFriend);		
	}

	@Override
	public void update(NewFriend newFriend) {
		newFriendMapper.update(newFriend);		
	}

	@Override
	public List<Map<String, Object>> getByParm(Map<String, Object> parm) {
		return newFriendMapper.getByParm(parm);
	}

	@Override
	public Integer getByParm_count(Map<String, Object> parm) {
		return newFriendMapper.getByParm_count(parm);
	}

	@Override
	public Map<String, Object> getById(Long id) {
		return newFriendMapper.getById(id);
	}


}
