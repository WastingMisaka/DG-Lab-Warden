package com.wastingmisaka.dg_lab_warden.staticVar;

import java.util.Map;

// 存放在堆中的全局变量
public class currentVar {
    // 电流信息
    public static int a_current = 0;
    public static int b_current = 0;
    public static int a_max = 0;
    public static int b_max = 0;

    // 一键开火记录数值
    public static int fire_current = 30;
    // TODO +波形

    public static Map<String,String> wave;
}
