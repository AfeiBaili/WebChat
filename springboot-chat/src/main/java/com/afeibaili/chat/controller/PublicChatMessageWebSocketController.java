package com.afeibaili.chat.controller;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.HashSet;
import java.util.Set;

/**
 * @author AfeiB
 * @description 公共聊天消息WebSocket控制层
 * @createDate 2024/8/23 下午3:35
 */
@ServerEndpoint("/")
public class PublicChatMessageWebSocketController {
    public static final Set<Session> publicChatMessages = new HashSet<>();
    public static Integer onlineCounter = 0;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        onlineCounter++;
        publicChatMessages.add(session);
        sendAllMessage("{\"onlineCounter\":" + onlineCounter + "}");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        sendAllMessage(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        onlineCounter--;
        publicChatMessages.remove(session);
        sendAllMessage("{\"onlineCounter\":" + onlineCounter + "}");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        publicChatMessages.remove(session);
    }

    public void sendAllMessage(String message) {
        for (Session ses : publicChatMessages) {
            if (ses.isOpen()) {
                ses.getAsyncRemote().sendText(message);
            } else {
                publicChatMessages.remove(ses);
            }
        }
    }
}
