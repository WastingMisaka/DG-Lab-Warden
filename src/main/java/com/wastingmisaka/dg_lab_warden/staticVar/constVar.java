package com.wastingmisaka.dg_lab_warden.staticVar;
// 常量
public class constVar {
    // 二维码地址
    public static final String URL =
            "https://www.dungeon-lab.com/app-download.php#DGLAB-SOCKET#ws://192.168.37.232:8080/Server";

    // 服务器端口号
    public static final String Port = "8080";

    // 服务端的实际地址
    public static final String SERVER_URL = "ws://192.168.37.232:8080/Server";

    // 软件限制的电流最大强度
    public static final int A_MAX = 200;
    public static final int B_MAX = 200;

    // 默认文本
    public static final String[] current_default={"0","当前强度A: 0","当前强度B: 0","当前上限A: 0","当前上限B: 0","unreachable_current_default"};
}
