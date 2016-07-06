package com.im.app.base.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.im.app.base.bean.Message;

public interface MessageMapper {

	int insert(Message message);
	
	List<Map<String, Object>> getByParm(@Param("parm") Map<String, Object> parm);
	
	List<Map<String, Object>> getByChatRoomId(@Param("chatRoomId") Long chatRoomId);
}
