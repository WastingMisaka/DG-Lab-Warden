package com.wastingmisaka.dg_lab_warden.ws;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.InetSocketAddress;

public class WebSocketServerMain {
    public static void main(String[] args) {
        System.out.println("-=-=-=WebSocketServerMain-=-=-=");
        InetSocketAddress addr = new InetSocketAddress("192.168.8.155", 8080);
        Server server = new Server(addr);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Add the WebSocket servlet
        ServletHolder holderEvents = new ServletHolder("ws-events", ServerWebSocketServlet.class);
        context.addServlet(holderEvents, "/Server");

        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
