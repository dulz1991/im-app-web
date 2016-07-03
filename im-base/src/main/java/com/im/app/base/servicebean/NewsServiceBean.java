package com.im.app.base.servicebean;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.im.app.base.bean.NewsReply;
import com.im.app.base.mybatis.mapper.NewsReplyMapper;
import com.im.app.base.service.NewsReplyService;

@Component
public class NewsServiceBean implements NewsReplyService {

	@Autowired
	private NewsReplyMapper newsReplyMapper;
	
	@Override
	public int insert(NewsReply newsReply) {
		return newsReplyMapper.insert(newsReply);
	}

	@Override
	public int update(NewsReply newsReply) {
		return newsReplyMapper.update(newsReply);		
	}

	@Override
	public List<Map<String, Object>> getByParm(Map<String, Object> parm) {
		return newsReplyMapper.getByParm(parm);
	}

	@Override
	public Integer getByParm_count(Map<String, Object> parm) {
		return newsReplyMapper.getByParm_count(parm);
	}

	@Override
	public Map<String, Object> getById(Long id) {
		return newsReplyMapper.getById(id);
	}

}
