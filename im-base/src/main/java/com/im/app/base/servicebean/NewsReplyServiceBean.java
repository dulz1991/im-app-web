package com.im.app.base.servicebean;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.im.app.base.bean.News;
import com.im.app.base.mybatis.mapper.NewsMapper;
import com.im.app.base.service.NewsService;

@Component
public class NewsReplyServiceBean implements NewsService {

	@Autowired
	private NewsMapper newsMapper;
	
	@Override
	public void insert(News news) {
		newsMapper.insert(news);
	}

	@Override
	public void update(News news) {
		newsMapper.update(news);		
	}

	@Override
	public List<Map<String, Object>> getByParm(Map<String, Object> parm) {
		return newsMapper.getByParm(parm);
	}

	@Override
	public Integer getByParm_count(Map<String, Object> parm) {
		return newsMapper.getByParm_count(parm);
	}

	@Override
	public Map<String, Object> getById(Long id) {
		return newsMapper.getById(id);
	}

}
