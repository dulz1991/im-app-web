package com.im.app.base.bean;

public class BaseEntity {
	
	private String _username;
	private String _createTimeStr;
	private Integer _friendFlag;
	private String _avatar;
	
	public String get_username() {
		return _username;
	}
	public void set_username(String _username) {
		this._username = _username;
	}
	public String get_createTimeStr() {
		return _createTimeStr;
	}
	public void set_createTimeStr(String _createTimeStr) {
		this._createTimeStr = _createTimeStr;
	}
	public Integer get_friendFlag() {
		return _friendFlag;
	}
	public void set_friendFlag(Integer _friendFlag) {
		this._friendFlag = _friendFlag;
	}
	public String get_avatar() {
		return _avatar;
	}
	public void set_avatar(String _avatar) {
		this._avatar = _avatar;
	}
	
	
}
