package com.im.app.frontend.converter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.im.app.base.common.CommonConstant;
import com.im.app.base.util.DateUtil;

public class NewsConverter {
	
	public List<Map<String, Object>> MapListConverter(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			MapConverter(map);
		}
		return list;
	}
	
	public Map<String, Object> MapConverter(Map<String, Object> map) {
		Date create = (Date) map.get("create_time");
		String creatStr = DateUtil.dateToString(create, CommonConstant.DATE_FORMATE_1);
		map.put("create_str", creatStr);
		return map;
	}

}
