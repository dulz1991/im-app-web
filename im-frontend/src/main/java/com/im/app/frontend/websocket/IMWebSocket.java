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

//��ע������ָ��һ��URI���ͻ��˿���ͨ�����URI�����ӵ�WebSocket������Servlet��ע��mapping��������web.xml�����á�
@ServerEndpoint(value="/websocket/{currentUserId}")
public class IMWebSocket {
	
	//��̬������������¼��ǰ������������Ӧ�ð�����Ƴ��̰߳�ȫ�ġ�
	private static int onlineCount = 0;
     
    //concurrent�����̰߳�ȫSet���������ÿ���ͻ��˶�Ӧ��IMWebSocket������Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
    private static CopyOnWriteArraySet<IMWebSocket> webSocketSet = new CopyOnWriteArraySet<IMWebSocket>();
    private static Map<Long, IMWebSocket> webSocketMap = new HashMap<Long, IMWebSocket>();
    
    private static UserProfileService userProfileService;
    private static MessageService messageService;
    
    //��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
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
     * ���ӽ����ɹ����õķ���
     * @param session  ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("currentUserId")Long currentUserId){
        this.session = session;
        webSocketMap.put(currentUserId, this);
        //webSocketSet.add(this);     //����set��
        addOnlineCount();           //��������1
        System.out.println("�������Ӽ��룡��ǰ��������Ϊ" + getOnlineCount());
    }
     
    /**
     * ���ӹرյ��õķ���
     */
    @OnClose
    public void onClose(@PathParam("currentUserId") Long currentUserId){
    	webSocketMap.remove(currentUserId);
        //webSocketSet.remove(this);  //��set��ɾ��
        subOnlineCount();           //��������1    
        System.out.println("��һ���ӹرգ���ǰ��������Ϊ" + getOnlineCount());
    }
     
    /**
     * �յ��ͻ�����Ϣ����õķ���
     * @param message �ͻ��˷��͹�������Ϣ
     * @param session ��ѡ�Ĳ���
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("currentUserId") Long currentUserId) {
    	String[] arr = message.split(",");
    	String friendUserId = arr[0];
    	String msg = arr[1];
        System.out.println("���Կͻ��˵���Ϣ: friendUserId " + friendUserId +"; mag:"+ msg);
        //���͸�ָ���û�
        for(Map.Entry<Long, IMWebSocket> map : webSocketMap.entrySet()) {
			try {
				if(map.getKey().equals(Long.parseLong(friendUserId))) {
					map.getValue().sendMessage(msg, map.getKey(),currentUserId, 2); //2��ʾ�Է�
				}
				if(map.getKey().equals(currentUserId)) {
					map.getValue().sendMessage(msg, Long.parseLong(friendUserId),map.getKey(), 1);//1��ʾ�Լ�
				}
			} catch (IOException | EncodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //Ⱥ����Ϣ
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
     * ��������ʱ����
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("��������");
        error.printStackTrace();
    }
     
    /**
     * ������������漸��������һ����û����ע�⣬�Ǹ����Լ���Ҫ��ӵķ�����
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
