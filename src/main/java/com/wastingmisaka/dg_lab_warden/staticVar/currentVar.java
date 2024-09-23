package com.wastingmisaka.dg_lab_warden.staticVar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 存放在堆中，和电流有关的全局变量
public class currentVar {
    // 电流信息
//    public static int a_current = 0;
//    public static int b_current = 0;
//    public static int a_max = 0;
//    public static int b_max = 0;
    public static int[] current_current={0,0,0,0};
    public static int[] current_max={0,0,0,0};
    public static String current_pulse="";

    // 一键开火记录数值
    public static int fire_current = 30;

    // TODO +波形
    public static Map<String,String> pulse_data = new HashMap<>();
    public static void wave_init(){
        ClassLoader classLoader = currentVar.class.getClassLoader();

        try{
            InputStream inputStream = classLoader.getResourceAsStream("wave.txt");
            if(inputStream == null){
                throw new IOException("Resource not found : pulse_data.txt");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> lines = reader.lines().collect(Collectors.toList());
            int cnt = 0;
            cnt = Integer.parseInt(lines.get(0));
            for(int i=1;i<=cnt;i++){
                int sp = lines.get(i).indexOf(":");
                String name = lines.get(i).substring(0,sp);
                String value = lines.get(i).substring(sp+1);
                pulse_data.put(name,value);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        pulse_data.forEach((k, v)->System.out.println(k+"---"+v));

    }
}
