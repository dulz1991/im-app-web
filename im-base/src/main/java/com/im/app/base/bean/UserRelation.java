package com.im.app.base.bean;

import java.io.Serializable;
import java.util.Date;

public class UserRelation implements Serializable{
	
	private static final long serialVersionUID = 3L;
	
	private Long id;
	private Long myUserId;
	private Long friendUserId;
	private Date createTime;
	private Date updateTime;
	private Integer status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMyUserId() {
		return myUserId;
	}
	public void setMyUserId(Long myUserId) {
		this.myUserId = myUserId;
	}
	public Long getFriendUserId() {
		return friendUserId;
	}
	public void setFriendUserId(Long friendUserId) {
		this.friendUserId = friendUserId;
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
