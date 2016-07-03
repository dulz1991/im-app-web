package com.im.app.base.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.im.app.base.bean.News;

public interface NewsMapper {

	void insert(News news);
	
	void update(News news);

	List<Map<String, Object>> getByParm(@Param("parm") Map<String, Object> parm);
	
	Integer getByParm_count(@Param("parm") Map<String, Object> parm);
	
	Map<String, Object> getById(Long id);

}
