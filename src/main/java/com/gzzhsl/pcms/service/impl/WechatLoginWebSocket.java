package com.gzzhsl.pcms.service.impl;

import com.google.gson.Gson;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.vo.ResultVO;
import com.gzzhsl.pcms.vo.WSMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint("/wechatloginwebsocket")
@Slf4j
public class WechatLoginWebSocket {


    private static Session session;
    @OnOpen
    public void onOpen(Session session){
        this.session = null;
        System.out.println("wechatlogin socket open");
        this.session = session;
    }

    public void sendMsg(ResultVO resultVO){
        if (this.session != null) {
            Gson gson = new Gson();
            try {
                session.getBasicRemote().sendText(gson.toJson(resultVO));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
