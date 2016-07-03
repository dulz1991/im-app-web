package com.im.app.base.bean;

import java.io.Serializable;
import java.util.Date;

public class NewsReply implements Serializable{

	private static final long serialVersionUID = 5L;
	
	private Long id;
	private Long newsId;
	private Long replyUserId;
	private String replyContent;
	private Long replyToUserId;
	private Date createTime;
	private Integer status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(Long replyUserId) {
		this.replyUserId = replyUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getNewsId() {
		return newsId;
	}
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
	public Long getReplyToUserId() {
		return replyToUserId;
	}
	public void setReplyToUserId(Long replyToUserId) {
		this.replyToUserId = replyToUserId;
	}
	
	
}
