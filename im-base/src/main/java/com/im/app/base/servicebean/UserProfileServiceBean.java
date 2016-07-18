package com.im.app.base.servicebean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.im.app.base.bean.UserProfile;
import com.im.app.base.common.CommonConstant;
import com.im.app.base.mybatis.mapper.UserProfileMapper;
import com.im.app.base.service.UserProfileService;
import com.im.app.base.util.MD5Util;
import com.im.app.base.util.PageUtil;

@Component
public class UserProfileServiceBean implements UserProfileService {
	
	@Autowired
	private UserProfileMapper userProfileMapper;

	@Override
	public void insert(UserProfile user) {
		userProfileMapper.insert(user);		
	}

	@Override
	public void update(UserProfile user) {
		userProfileMapper.update(user);		
	}

	@Override
	public List<UserProfile> getByParm(PageUtil page, UserProfile user) {
		return userProfileMapper.getByParm(page, user);
	}

	@Override
	public Integer getByParm_count(UserProfile user) {
		return userProfileMapper.getByParm_count(user);
	}

	@Override
	public UserProfile getById(Long id) {
		return userProfileMapper.getById(id);
	}

	@Override
	public UserProfile findUserByUsernameAndPassword(UserProfile user) {
		return userProfileMapper.findUserByUsernameAndPassword(user);
	}

	@Override
	public UserProfile getByUsernick(String usernick) {
		return userProfileMapper.getByUsernick(usernick);
	}

	@Override
	public List<UserProfile> findByIdsMap(List<String> ids) {
		return userProfileMapper.findByIdsMap(ids);
	}

	@Override
	public Map<String, Object> regist(Map<String, Object> reqMap) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Integer sex = (Integer) reqMap.get("sex");
		String usernick = (String) reqMap.get("usernick");
		String password = (String) reqMap.get("password");
		String conformPassword = (String) reqMap.get("conformPassword");
		String avatar = (String) reqMap.get("avatar");
		if(sex==null||StringUtils.isBlank(usernick)||StringUtils.isBlank(password)||StringUtils.isBlank(conformPassword)){
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			resMap.put(CommonConstant.ERROR_INFO, "请确认是否输入完整");
			return resMap;
		}
		if(!password.equals(conformPassword)){
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			resMap.put(CommonConstant.ERROR_INFO, "密码不匹配");
			return resMap;
		}
		UserProfile userProfile = userProfileMapper.getByUsernick(usernick);
		if(userProfile!=null){
			resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_500);
			resMap.put(CommonConstant.ERROR_INFO, "用户名已存在");
			return resMap;
		}
		userProfile = new UserProfile();
		userProfile.setUsernick(usernick);
		userProfile.setSex(sex);
		MD5Util md5Util = new MD5Util("im", "MD5");
		password = md5Util.encode(password);
		userProfile.setPassword(password);
		if(StringUtils.isBlank(avatar)){
			if(sex==1){
				avatar = "/img/avatar/1193484.png";	
			}else if(sex==2){
				avatar = "/img/avatar/1193483.png";
			}
		}
		userProfile.setAvatar(avatar);
		userProfileMapper.insert(userProfile);
		resMap.put(CommonConstant.ERROR_NO, CommonConstant.ERROR_200);
		return resMap;
	}

}
