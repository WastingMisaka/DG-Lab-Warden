package com.wastingmisaka.dg_lab_warden.ws;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.InetSocketAddress;

import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.*;

public class WebSocketServerMain {
    public static void main(String[] args) {
        System.out.println("-=-=-=WebSocketServerMain-=-=-=");
        InetSocketAddress addr = new InetSocketAddress(IP, Integer.parseInt(Port));
        Server server = new Server(addr);
        progress_server = server;
        System.out.println("WebSocketServerOpenOn : " + IP + ":" + Port);

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
