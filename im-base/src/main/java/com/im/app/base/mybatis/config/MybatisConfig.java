package com.im.app.base.mybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;

import com.im.app.base.mybatis.mapper.ChatRoomMapper;
import com.im.app.base.mybatis.mapper.MessageMapper;
import com.im.app.base.mybatis.mapper.NewFriendMapper;
import com.im.app.base.mybatis.mapper.NewsMapper;
import com.im.app.base.mybatis.mapper.NewsReplyMapper;
import com.im.app.base.mybatis.mapper.UserProfileMapper;
import com.im.app.base.mybatis.mapper.UserRelationMapper;

@Configuration
@ImportResource("classpath:data-sources.xml")
public class MybatisConfig {

	@Value("classpath:mybatis-mapper-config.xml")
	Resource mybatisMapperConfig;
	
	@Autowired
	DataSource dataSource;
	
	@Bean
	public UserProfileMapper UserProfileMapper() throws Exception {
		return newMapperFactoryBean(UserProfileMapper.class).getObject();
	}
	@Bean
	public UserRelationMapper UserRelationMapper() throws Exception {
		return newMapperFactoryBean(UserRelationMapper.class).getObject();
	}
	@Bean
	public NewsMapper NewsMapper() throws Exception {
		return newMapperFactoryBean(NewsMapper.class).getObject();
	}
	@Bean
	public NewsReplyMapper NewsReplyMapper() throws Exception {
		return newMapperFactoryBean(NewsReplyMapper.class).getObject();
	}
	@Bean
	public MessageMapper MessageMapper() throws Exception {
		return newMapperFactoryBean(MessageMapper.class).getObject();
	}
	@Bean
	public ChatRoomMapper ChatRoomMapper() throws Exception {
		return newMapperFactoryBean(ChatRoomMapper.class).getObject();
	}
	@Bean
	public NewFriendMapper NewFriendMapper() throws Exception {
		return newMapperFactoryBean(NewFriendMapper.class).getObject();
	}
	
	<T> MapperFactoryBean<T> newMapperFactoryBean(Class<T> clazz) throws Exception {
		MapperFactoryBean<T> b = new MapperFactoryBean<T>();
		b.setMapperInterface(clazz);
		b.setSqlSessionFactory(sqlSessionFactory());
		return b;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setConfigLocation(mybatisMapperConfig);
		fb.setDataSource(dataSource);
		return fb.getObject();
	}
	
}
