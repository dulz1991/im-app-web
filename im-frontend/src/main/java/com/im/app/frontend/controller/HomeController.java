package com.im.app.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.im.app.base.bean.NewsReply;
import com.im.app.base.common.CommonConstant;
import com.im.app.base.service.NewsReplyService;
import com.im.app.base.service.NewsService;
import com.im.app.frontend.converter.NewsConverter;

@Controller
@RequestMapping("/auth/home")
public class HomeController extends BaseController {
	
	@Autowired
	private NewsService newsService;
	@Autowired
	private NewsReplyService newsReplyService;
	
	@ResponseBody
	@RequestMapping(value = "/newsList", method=RequestMethod.GET)
	public Map<String, Object> getNewsList() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("status", 1);
		String firstNewsId = this.request.getParameter("firstNewsId");
		String lastNewsId = this.request.getParameter("lastNewsId");
		reqMap.put("firstNewsId", firstNewsId);
		reqMap.put("lastNewsId", lastNewsId);
		List<Map<String, Object>> list = newsService.getByParm(reqMap);
		list = new NewsConverter().MapListConverter(list);
		resMap.put("list", list);
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/newsDetail", method=RequestMethod.GET)
	public Map<String, Object> newsdetail(Long id) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> news =  newsService.getById(id);
		news = new NewsConverter().MapConverter(news);
		resMap.put("news", news);
		if(news.get("user_id").equals(this.getCurrentUserId())){
			resMap.put("isChat", false);	
		} else {
			resMap.put("isChat", true);
		}
		List<Map<String, Object>> commentList = getcomments(id);
		resMap.put("commentList", commentList);
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getcomments", method=RequestMethod.GET)
	public List<Map<String, Object>> getcomments(Long newsId) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("status", 1);
		reqMap.put("newsId", newsId);
		String lastReplyId = this.request.getParameter("lastReplyId");
		reqMap.put("lastReplyId", lastReplyId);
		List<Map<String, Object>> commentList = newsReplyService.getByParm(reqMap);
		return commentList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sumbitComment", method=RequestMethod.POST)
	public Map<String, Object> sumbitComment(NewsReply newsReply) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		newsReply.setReplyUserId(this.getCurrentUserId());
		int count = newsReplyService.insert(newsReply);
		if (count > 0) {
			Map<String, Object> reply = newsReplyService.getById(newsReply.getId());
			reqMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
			reqMap.put("reply", reply);
		} else {
			reqMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			reqMap.put(CommonConstant.ERROR_INFO, "∆¿¬€ ß∞‹, «Î…‘∫Û‘Ÿ ‘!");
		}
		return reqMap;
	}
	
	
	
	
}
