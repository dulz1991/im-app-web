package com.im.app.base.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.im.app.base.bean.NewsReply;

public interface NewsReplyMapper {

	int insert(NewsReply newsReply);
	
	int update(NewsReply newsReply);

	List<Map<String, Object>> getByParm(@Param("parm") Map<String, Object> parm);
	
	Integer getByParm_count(@Param("parm") Map<String, Object> parm);
	
	Map<String, Object> getById(Long id);

}
