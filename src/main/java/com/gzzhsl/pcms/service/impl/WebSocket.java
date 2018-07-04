package com.gzzhsl.pcms.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSockets = new  CopyOnWriteArraySet<WebSocket>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        log.info("【websocket消息】 有新的连接， 连接总数是：" + webSockets.size());
    }
    @OnClose
    public void onClose() {
        webSockets.remove(this);
        log.info("【websocket消息】 断开连接， 连接总数是：" + webSockets.size());
    }
    @OnMessage
    public void onMessage(String msg) {
        log.info("【websocket消息】 收到客户端发来消息：{}", msg);
    }
    public void sendMessage(String msg) {
        for (WebSocket webSocket : webSockets) {
            log.info("【websocket消息】 广播消息， message={}：", msg);
            try {
                webSocket.session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
