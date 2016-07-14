package com.im.app.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.im.app.base.bean.ChatRoom;
import com.im.app.base.service.ChatRoomService;
import com.im.app.base.service.MessageService;
import com.im.app.base.service.UserProfileService;
import com.im.app.frontend.converter.ChatConverter;

@Controller
@RequestMapping("/auth/chat")
public class ChatController extends BaseController {
	
	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ChatRoomService chatRoomService;
	
	@ResponseBody
	@RequestMapping(value = "/chatRoom", method=RequestMethod.GET)
	public Map<String, Object> chatRoom(Long friendUserId) {
		Long currentUserId = this.getCurrentUserId();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("myUserId", currentUserId);
		reqMap.put("friendUserId", friendUserId);
		ChatRoom chatRoom = chatRoomService.getChatRoomByParm(reqMap);
		if(chatRoom==null){
			chatRoom = new ChatRoom();
			chatRoom.setMyUserId(currentUserId);
			chatRoom.setFriendUserId(friendUserId);
			chatRoomService.insert(chatRoom);
		}
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<Map<String, Object>> chats = messageService.getByChatRoomId(chatRoom.getId());
		chats = new ChatConverter().MapListConverter(chats, currentUserId);
		resMap.put("currentUserId", currentUserId);
		resMap.put("chats", chats);
		return resMap;
	}
	
}
