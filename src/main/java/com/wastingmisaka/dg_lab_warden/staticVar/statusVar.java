package com.wastingmisaka.dg_lab_warden.staticVar;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;

public class statusVar {
    // 程序的状态
    public static int MainFunction = 0;
    // 一键开火状态
    public static int firing = 0;


    // 服务器IP
    public static String IP = "192.168.8.155";
    // 服务器端口号  默认8080
    public static String Port = "8080";
    // 当前的服务器实例
    public static Server progress_server;
    // 当前和客户端建立的ws会话
    public static Session progress_session;

    // 当前页面警告数
    public static long warning_count = 0;
    // 当前页面错误数
    public static long error_count = 0;

}
