package edu.udacity.java.nano.chat;

import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
@Slf4j
public class WebSocketChatServer {

	/**
	 * All chat sessions.
	 */
	private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

	private static void sendMessageToAll(String msg) {
		// TODO: add send message method.
		onlineSessions.forEach((id, session) -> {
			try {
				session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				log.error("Exception in sending the message with exception message :{}", e.getMessage());
			}
		});
	}

	/**
	 * Open connection, 1) add session, 2) add user.
	 */
	@OnOpen
	public void onOpen(Session session) {
		onlineSessions.put(session.getId(), session);
		sendMessageToAll(Message.jsonString("OPEN", "", "", onlineSessions.size()));
	}

	/**
	 * Send message, 1) get username and session, 2) send message to all.
	 */
	@OnMessage
	public void onMessage(Session session, String jsonStr) {
	
		Message message = JSON.parseObject(jsonStr, Message.class);
		sendMessageToAll(
				Message.jsonString("SPEAK", message.getUsername(), message.getMsg(), onlineSessions.size()));
	}

	/**
	 * Close connection, 1) remove session, 2) update user.
	 */
	@OnClose
	public void onClose(Session session) {
		
		onlineSessions.remove(session.getId());
		sendMessageToAll(Message.jsonString("CLOSE", "", "", onlineSessions.size()));
	}

	/**
	 * Print exception.
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

}
