package com.im.app.base.bean;

import java.io.Serializable;
import java.util.Date;

public class Message extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2L;
	
	private Long id;
	private Long sendUserId;
	private Long receiveUserId;
	private Long chatRoomId;
	private String message;
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}
	public Long getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(Long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public Long getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	
	
}
