package com.im.app.base.service;

import java.util.List;
import java.util.Map;

import com.im.app.base.bean.Message;

public interface MessageService {

	int insert(Message message);
	
	List<Map<String, Object>> getByParm(Map<String, Object> parm);

	List<Map<String, Object>> getByChatRoomId(Long chatRoomId);
}
