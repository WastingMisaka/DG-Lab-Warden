package com.wastingmisaka.dg_lab_warden.ws;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import static com.wastingmisaka.dg_lab_warden.staticVar.currentVar.*;
import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.*;

import java.io.IOException;

public class WebSocketServer extends WebSocketAdapter {
    @Override
    public void onWebSocketConnect(Session session) {
        progress_session = session;
        super.onWebSocketConnect(session);
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
            current_current[1] = Integer.parseInt(split[0]);
            current_current[2] = Integer.parseInt(split[1]);
            current_max[1] = Integer.parseInt(split[2]);
            current_max[2] = Integer.parseInt(split[3]);
        }
    }
}
