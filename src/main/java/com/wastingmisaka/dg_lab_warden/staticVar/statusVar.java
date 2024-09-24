package com.wastingmisaka.dg_lab_warden.staticVar;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;

public class statusVar {
    // 程序的状态
    public static int MainFunction = 0;
    // 一键开火状态
    public static int firing = 0;
    // A通道开关
    public static boolean a_enabled = false;
    // B通道开关
    public static boolean b_enabled = false;
    // 警告惩罚
    public static boolean warning_enabled = false;
    // 错误惩罚
    public static boolean error_enabled = false;


    // 服务器IP
    public static String IP = "192.168.8.155";
    // 服务器端口号  默认8080
    public static String Port = "8080";
    // 当前的服务器实例
    public static Server progress_server = null;
    // 当前和客户端建立的ws会话
    public static Session progress_session = null;

    // 当前页面警告数
    public static long warning_count = 0;
    // 当前页面错误数
    public static long error_count = 0;

}
