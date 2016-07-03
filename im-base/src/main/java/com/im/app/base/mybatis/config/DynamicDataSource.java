package com.im.app.base.mybatis.config;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DynamicDataSource extends AbstractRoutingDataSource {  
	@Override  
    protected Object determineCurrentLookupKey() {  
        return DataSourceContextHolder.getCustomerType();  
    }

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}
