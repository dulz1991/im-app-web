package com.im.app.base.mybatis.config;

public class DataSourceContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
	//private static final String writeMySql = "write";
	//private static final String[] readOnlyMySQL = {"db1","db2"};
	
	 public static void setCustomerType(String customerType) {  
		 contextHolder.set(customerType);  
	 }  
	 public static String getCustomerType() {  
		 return contextHolder.get();  
	 }  
	 public static void clearCustomerType() {  
		 contextHolder.remove();  
	 }  

	 //Ëæ»úÇÐ»»Êý¾Ý¿â
   /* public static void setReadOnlyDb() {
    	if(readOnlyMySQL!=null&&readOnlyMySQL.length!=0){
    		int i = getRandomMysql();
    		if(i<=readOnlyMySQL.length-1){
    			contextHolder.set(readOnlyMySQL[i]);
    		}
    	}
    }  
  
    public static String getReadOnlyDb() {  
        return ((String) contextHolder.get());  
    }  
  
    public static void clearDbType() {  
        contextHolder.remove();  
    }
    
    private static int getRandomMysql(){
    	Random random = new Random();
    	int i = random.nextInt(readOnlyMySQL.length);
    	return i;
    }*/
}