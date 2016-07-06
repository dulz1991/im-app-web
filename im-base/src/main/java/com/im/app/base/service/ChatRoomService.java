package com.im.app.base.service;

import java.util.Map;

import com.im.app.base.bean.ChatRoom;

public interface ChatRoomService {

	int insert(ChatRoom chatRoom);
	
	ChatRoom getChatRoomByParm(Map<String, Object> parm);

}
