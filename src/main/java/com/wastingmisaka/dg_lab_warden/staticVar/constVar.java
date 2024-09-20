package com.wastingmisaka.dg_lab_warden.staticVar;
// 常量
public class constVar {
    // 二维码前缀
    public static final String URL_prefix = "https://www.dungeon-lab.com/app-download.php#DGLAB-SOCKET#ws://";
    // 二维码后缀
    public static final String URL_suffix = "/Server";

    // 软件限制的电流强度上下限
    public static final int A_MIN = 0;
    public static final int B_MIN = 0;
    public static final int A_MAX = 200;
    public static final int B_MAX = 200;

    // 默认文本
    public static final String[] current_default={"0","当前强度A: 0","当前强度B: 0","当前上限A: 0","当前上限B: 0","unreachable_current_default"};
}
