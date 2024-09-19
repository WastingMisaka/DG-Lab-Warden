package com.wastingmisaka.dg_lab_warden.ws;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import static com.wastingmisaka.dg_lab_warden.staticVar.currentVar.*;
import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.progress_session;

import java.io.IOException;

public class WebSocketServer extends WebSocketAdapter {
    @Override
    public void onWebSocketConnect(Session session) {
        progress_session = session;
        super.onWebSocketConnect(session);
        System.out.println("WebSocket opened: " + session.getRemoteAddress().getHostString());
        // 发送绑定信息
        //{"clientId":"APP","message":"targetId","targetId":"","type":"bind"}
        //Message(type=bind, message=200, clientId=Server, targetId=APP)
        try {
            getSession().getRemote().sendString("{\"clientId\":\"APP\",\"message\":\"targetId\",\"targetId\":\"\",\"type\":\"bind\"}");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            getSession().getRemote().sendString("{\"clientId\":\"Server\",\"message\":\"200\",\"targetId\":\"APP\",\"type\":\"bind\"}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onWebSocketText(String message) {
        System.out.println("Received message: " + message);
        try {
            get_data(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        System.out.println("WebSocket closed: " + statusCode + " - " + reason);
        progress_session = null;
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        System.err.println("WebSocket error: " + cause.getMessage());
    }

    public void get_data(String msg){
        int st = msg.indexOf("th-");
        if(st!=-1){
            st+=2;
            int ed = msg.indexOf("\",\"ta");
            String sub_msg = msg.substring(st+1, ed);
            String[] split = sub_msg.split("\\+");
            a_current = Integer.parseInt(split[0]);
            b_current = Integer.parseInt(split[1]);
            a_max = Integer.parseInt(split[2]);
            b_max = Integer.parseInt(split[3]);
        }
    }

    public void user_close(){
        getSession().close();
        System.out.println("-----user_close-=-------");
    }
}
