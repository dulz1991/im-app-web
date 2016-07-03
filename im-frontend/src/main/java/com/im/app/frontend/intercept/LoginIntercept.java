/*package com.im.app.frontend.intercept;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.im.app.base.common.CommonConstant;
import com.im.app.frontend.util.CookieUtil;

@Aspect
@Component
public class LoginIntercept {
	
	@Pointcut("execution(* com.im.app.frontend.controller.*.*(..)) && !execution(* com.im.app.frontend.controller.AccountController.*(..))")
    public void controllerMethod(){};
	
    @Before("controllerMethod()")
    public void before(JoinPoint joinPoint) throws IOException {
    	System.out.println("method start");
    	HttpServletRequest request;
    	HttpServletResponse response;
    	// 调用方法名称
    	String methodName = joinPoint.getSignature().getName();
    	if (methodName.equals("setReqAndRes")) {
    		// 调用参数  
        	Object[] args = joinPoint.getArgs();
        	request = (HttpServletRequest) args[0];
        	response = (HttpServletResponse) args[1];
        	Object target = joinPoint.getTarget();
        	String value = CookieUtil.getCookie(CommonConstant.COOKIE_USER, request);
        	if(StringUtils.isBlank(value)){
        		request.getSession();
        		response.sendRedirect("/account/login");
        	}
    	}
    	
    }
    
    @After("controllerMethod()")
    public void after() {
        System.out.println("method after");
    } 
    
    @AfterReturning("execution(public * com.bjsxt.dao..*.*(..))")
    public void AfterReturning() {
        System.out.println("method AfterReturning");
    } 
    @AfterThrowing("execution(public * com.bjsxt.dao..*.*(..))")
    public void AfterThrowing() {
        System.out.println("method AfterThrowing");
    } 
}
*/