package com.im.app.base.service;

import java.util.List;
import java.util.Map;

import com.im.app.base.bean.News;

public interface NewsService {

	void insert(News news);
	
	void update(News news);

	List<Map<String, Object>> getByParm(Map<String, Object> parm);
	
	Integer getByParm_count(Map<String, Object> parm);
	
	Map<String, Object> getById(Long id);

}
