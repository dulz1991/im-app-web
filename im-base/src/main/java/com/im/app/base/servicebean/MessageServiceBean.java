package com.im.app.base.servicebean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.im.app.base.bean.ChatRoom;
import com.im.app.base.bean.Message;
import com.im.app.base.mybatis.mapper.ChatRoomMapper;
import com.im.app.base.mybatis.mapper.MessageMapper;
import com.im.app.base.service.MessageService;

@Component
public class MessageServiceBean implements MessageService {

	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private ChatRoomMapper chatRoomMapper;

	@Override
	public int insert(Message message) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("myUserId", message.getSendUserId());
		reqMap.put("friendUserId", message.getReceiveUserId());
		ChatRoom chatRoom = chatRoomMapper.getChatRoomByParm(reqMap);
		if(chatRoom!=null){
			message.setChatRoomId(chatRoom.getId());
			return messageMapper.insert(message);	
		} else {
			return 0;
		}
		
	}

	@Override
	public List<Map<String, Object>> getByParm(Map<String, Object> parm) {
		return messageMapper.getByParm(parm);
	}

	@Override
	public List<Map<String, Object>> getByChatRoomId(Long chatRoomId) {
		return messageMapper.getByChatRoomId(chatRoomId);
	}
	

}
