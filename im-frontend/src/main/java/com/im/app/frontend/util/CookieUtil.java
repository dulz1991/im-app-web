package com.im.app.frontend.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	public static void setCookie(String key, String value, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(86400);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static String getCookie(String key, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(key)){
				return cookie.getValue(); 
			}
		}
		return null;
	}
	
	public static void deleteCookie(String key, HttpServletResponse response, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return;
		}
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(key)){
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				break;
			}
		}
	}

}
