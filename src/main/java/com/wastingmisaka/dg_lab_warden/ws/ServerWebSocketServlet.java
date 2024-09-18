package com.wastingmisaka.dg_lab_warden.ws;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class ServerWebSocketServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory factory){
        factory.register(WebSocketServer.class);
    }
}
