package com.im.app.base.servicebean;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.im.app.base.bean.ChatRoom;
import com.im.app.base.mybatis.mapper.ChatRoomMapper;
import com.im.app.base.service.ChatRoomService;

@Component
public class ChatRoomServiceBean implements ChatRoomService {

	@Autowired
	private ChatRoomMapper chatRoomMapper;

	@Override
	public int insert(ChatRoom chatRoom) {
		return chatRoomMapper.insert(chatRoom);
	}

	@Override
	public ChatRoom getChatRoomByParm(Map<String, Object> parm) {
		return chatRoomMapper.getChatRoomByParm(parm);
	}


}
