package com.im.app.frontend.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jackson.map.ObjectMapper;

import com.im.app.base.bean.Message;
import com.im.app.base.bean.UserProfile;
import com.im.app.base.common.CommonConstant;
import com.im.app.base.service.MessageService;
import com.im.app.base.service.UserProfileService;
import com.im.app.base.servicebean.MessageServiceBean;
import com.im.app.base.servicebean.UserProfileServiceBean;
import com.im.app.base.util.DateUtil;
import com.im.app.base.util.SpringContextUtil;

//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint(value="/websocket/{currentUserId}")
public class IMWebSocket {
	
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
     
    //concurrent包的线程安全Set，用来存放每个客户端对应的IMWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<IMWebSocket> webSocketSet = new CopyOnWriteArraySet<IMWebSocket>();
    private static Map<Long, IMWebSocket> webSocketMap = new HashMap<Long, IMWebSocket>();
    
    private static UserProfileService userProfileService;
    private static MessageService messageService;
    
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    static {
    	Map<String, Object> map = new LinkedHashMap<String, Object>();
    	map = (Map<String, Object>) SpringContextUtil.getBean(UserProfileServiceBean.class);
    	userProfileService = (UserProfileService) map.get("userProfileServiceBean");
    	map.clear();
    	map = (Map<String, Object>) SpringContextUtil.getBean(MessageServiceBean.class);
    	messageService = (MessageService) map.get("messageServiceBean");
    }
     
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("currentUserId")Long currentUserId){
        this.session = session;
        webSocketMap.put(currentUserId, this);
        //webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }
     
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("currentUserId") Long currentUserId){
    	webSocketMap.remove(currentUserId);
        //webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1    
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
     
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("currentUserId") Long currentUserId) {
    	String[] arr = message.split(",");
    	String friendUserId = arr[0];
    	String msg = arr[1];
        System.out.println("来自客户端的消息: friendUserId " + friendUserId +"; mag:"+ msg);
        //发送给指定用户
        for(Map.Entry<Long, IMWebSocket> map : webSocketMap.entrySet()) {
			try {
				if(map.getKey().equals(Long.parseLong(friendUserId))) {
					map.getValue().sendMessage(msg, map.getKey(),currentUserId, 2); //2表示对方
				}
				if(map.getKey().equals(currentUserId)) {
					map.getValue().sendMessage(msg, Long.parseLong(friendUserId),map.getKey(), 1);//1表示自己
				}
			} catch (IOException | EncodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //群发消息
        /*for(IMWebSocket item: webSocketSet){             
            try {
            	item.sendMessage(message, 1L);	
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            } catch (EncodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
    }
     
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
     
    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @param userId 
     * @param currentUserId 
     * @param i 
     * @throws IOException
     * @throws EncodeException 
     */
    public void sendMessage(String message, Long friendUserId, Long currentUserId, int friendFlag) throws IOException, EncodeException{
    	Message msg = new Message();
    	if (friendFlag == 1) {
    		msg.setSendUserId(currentUserId);
    		msg.setReceiveUserId(friendUserId);
    	} else {
    		msg.setSendUserId(friendUserId);
    		msg.setReceiveUserId(currentUserId);
    	}
		UserProfile userProfile = userProfileService.getById(currentUserId);
		msg.set_avatar(userProfile.getAvatar());
		msg.set_username(userProfile.getUsernick());
    	msg.setMessage(message);
    	Date date = new Date();
    	msg.setCreateTime(date);
    	msg.set_createTimeStr(DateUtil.dateToString(date, CommonConstant.DATE_FORMATE_1));
    	msg.set_friendFlag(friendFlag);
    	
    	messageService.insert(msg);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	String msg2Json = mapper.writeValueAsString(msg);
    	
    	this.session.getBasicRemote().sendText(msg2Json);
    	//this.session.getBasicRemote().sendObject(msg2Json);
        //this.session.getAsyncRemote().sendText(message);
    }
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
        IMWebSocket.onlineCount++;
    }
     
    public static synchronized void subOnlineCount() {
        IMWebSocket.onlineCount--;
    }
    
    /*public Long getCurrentUserId(EndpointConfig config) {
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute("userInfo");
		if (userProfile == null) {
			return 0L;
		} else {
			return userProfile.getId();
		}
	}*/

}
