package com.im.app.base.mybatis.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.im.app.base.bean.ChatRoom;

public interface ChatRoomMapper {

	int insert(ChatRoom chatRoom);
	
	ChatRoom getChatRoomByParm(@Param("parm") Map<String, Object> parm);
	
}
