package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * WebSocket message model
 */
@Data
public class Message {
	
	private String msg; 
	private String username;
	private String type;
	private int onlineCount;
	
	
	public Message(String type,String username, String msg, int onlineCount) {
        this.type = type;
        this.username = username;
        this.msg = msg;
        this.onlineCount = onlineCount;
    }
	
	 public static String jsonString(String messageType,String userName, String messageText, int onlineTotal) {
	        return JSON.toJSONString(new Message(messageType,userName, messageText, onlineTotal));
	    }
	
}
