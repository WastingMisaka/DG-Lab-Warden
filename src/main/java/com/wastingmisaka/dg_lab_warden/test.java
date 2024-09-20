package com.wastingmisaka.dg_lab_warden;

import java.util.HashMap;
import java.util.Scanner;
import java.util.*;
public class test {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println("Enter the number of elements in the array");

        String a="hso";
        a = sc.nextLine();
        System.out.println(get_wave_name(a));
        String back = middle();
        System.out.println(back);
        // 存放波形名和波形
        Map<String,String> name_wave = new HashMap<String,String>();
//        while(!a.isEmpty()){
//            a = sc.nextLine();
//            //起点
//            if(a.indexOf(':')!=-1){
//                String temp_name = a.substring(a.indexOf('\'')+1,a.indexOf('：')-1);
//            }
//        }
    }

    public static String get_wave_name(String temp){
        String temp_name = temp.substring(temp.indexOf('\'')+1,temp.indexOf(':')-1);
        return temp_name;
    }


    public static int string_to_int(String temp){
        int num = Integer.parseInt(temp);
        return num;
    }
    public static String int_to_hex(int num){
        String hex = String.format("%02x", num);
        return hex;
    }

    public static String middle(){
        String back="";
        String inner = "";
        inner = sc.nextLine();
        while(inner.indexOf(']')==-1){
            inner = inner.trim();
            inner = inner.replaceAll("\\),\\(",",");
            inner = inner.replaceAll("\\), \\(",",");
            inner = inner.replaceAll("\\(\\(","(");
            inner = inner.replaceAll("\\)\\)",")");
            back+=inner;
            //last resort
            inner = sc.nextLine();
        }
        return back;
    }
}
