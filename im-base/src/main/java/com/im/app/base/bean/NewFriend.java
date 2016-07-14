package com.im.app.base.bean;

import java.io.Serializable;
import java.util.Date;

public class NewFriend implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7L;
	
	private Long id;
	private Long requestUserId;
	private Long targetUserId;
	private Date createTime;
	private Date updateTime;
	private Integer status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRequestUserId() {
		return requestUserId;
	}
	public void setRequestUserId(Long requestUserId) {
		this.requestUserId = requestUserId;
	}
	public Long getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
