package com.im.app.base.util;

import java.security.MessageDigest;

public class MD5Util {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",  "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" }; 

	private Object salt; 
	private String algorithm; 

	public MD5Util(Object salt, String algorithm) { 
		this.salt = salt; 
		this.algorithm = algorithm; 
	} 
		
	public String encode(String rawPass) { 
		String result = null; 
		try { 
			MessageDigest md = MessageDigest.getInstance(algorithm); 
			//���ܺ���ַ���  
			result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes("utf-8"))); 
		} catch (Exception ex) { 
		} 
		return result; 
	} 
		   
	public boolean isPasswordValid(String encPass, String rawPass) { 
		String pass1 = "" + encPass; 
		String pass2 = encode(rawPass); 
		return pass1.equals(pass2); 
	} 
		    
	private String mergePasswordAndSalt(String password) { 
		if (password == null) { 
			password = ""; 
		} 
		if ((salt == null) || "".equals(salt)) { 
			return password; 
		} else { 
			return password + "{" + salt.toString() + "}"; 
		} 
	} 

		/** 
		  * ת���ֽ�����Ϊ16�����ִ� 
		  * @param b �ֽ����� 
		  * @return 16�����ִ� 
		  */ 
	private static String byteArrayToHexString(byte[] b) { 
		StringBuffer resultSb = new StringBuffer(); 
		for (int i = 0; i < b.length; i++) { 
			resultSb.append(byteToHexString(b[i])); 
		} 
		return resultSb.toString(); 
	} 

	private static String byteToHexString(byte b) { 
		int n = b; 
		if (n < 0) 
			n = 256 + n; 
		int d1 = n / 16; 
		int d2 = n % 16; 
		return hexDigits[d1] + hexDigits[d2]; 
	} 
		    
	public static void main(String[] args) { 
		String salt = "im"; 
		MD5Util encoderMd5 = new MD5Util(salt, "MD5"); 
		String encode = encoderMd5.encode("1111"); 
		System.out.println(encode); 
		boolean passwordValid = encoderMd5.isPasswordValid("083a8db3ff5b9b4ece3ef2bde03226c8", "test"); 
		System.out.println(passwordValid); 

		/*MD5Util encoderSha = new MD5Util(salt, "SHA"); 
		String pass2 = encoderSha.encode("test"); 
		System.out.println(pass2); 
		boolean passwordValid2 = encoderSha.isPasswordValid("1e4346dcb54c1444e91668e04b8ca4f74f42958e", "test"); 
		System.out.println(passwordValid2); */
	}
		

}
