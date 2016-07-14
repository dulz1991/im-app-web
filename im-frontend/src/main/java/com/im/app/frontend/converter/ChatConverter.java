package com.im.app.frontend.converter;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.im.app.base.common.CommonConstant;
import com.im.app.base.util.DateUtil;

public class ChatConverter {
	
	public List<Map<String, Object>> MapListConverter(List<Map<String, Object>> list, Long currentUserId) {
		for (Map<String, Object> map : list) {
			Date create = (Date) map.get("create_time");
			String creatStr = DateUtil.dateToString(create, CommonConstant.DATE_FORMATE_1);
			map.put("create_str", creatStr);
			Long sendUserId = (Long) map.get("send_user_id");
			if(sendUserId == currentUserId){
				map.put("me", true);	
			} else {
				map.put("me", false);
			}
		}
		 Collections.sort(list, new Comparator<Map<String, Object>>() {
	            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	                return (Long) o1.get("id") > (Long) o2.get("id") ? 
	                		((Long) o1.get("id") == (Long) o2.get("id") ? 0 : 1) : -1;
	            }
	        });
		return list;
	}
	
}
