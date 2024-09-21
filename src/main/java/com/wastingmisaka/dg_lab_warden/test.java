package com.wastingmisaka.dg_lab_warden;

import java.util.Scanner;

public class test {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

    }

    public static void test1(){
        String checker = "1";
        checker = sc.nextLine();
        while(!checker.isEmpty()){
            String writing = "";
            // 加名字
            writing = writing+get_wave_name(checker)+":";

            String temp_line = sc.nextLine();
            while(temp_line.indexOf("]")==-1){
                temp_line = temp_line.trim();
                temp_line = temp_line.replaceAll("\\), \\(",",");
                writing = writing + temp_line;
                // 更新该这个
                temp_line = sc.nextLine();
            }
            // 打印最终结果,转化成json，并扫描下一行的名字
            writing = writing.replaceAll("\\(\\(","(");
            writing = writing.replaceAll("\\)\\)",")");
            writing = writing.replaceAll("\\s+","");
            String jsoned = to_json(writing);
            System.out.println("    "+jsoned);
            checker = sc.nextLine();
        }
    }
    public static String get_wave_name(String t){
        int st = t.indexOf('\'')+1;
        int ed = t.indexOf(':')-1;
        String back = t.substring(st,ed);
        return back;
    }

    public static String to_json(String t){
        String back = "";
        String temp = t;
        temp = temp.replaceAll("\\(","\"");
        temp = temp.replaceAll("\\)","\"");
        for(int i=0;i<temp.length();i++){
            char c = temp.charAt(i);
            if(Character.isDigit(c)){
                String temp_str = ""+c;
                for(int j=i+1;j<temp.length();j++){
                    if(Character.isDigit(temp.charAt(j))){
                        temp_str = temp_str+temp.charAt(j);
                    }else{
                        int temp_num = Integer.parseInt(temp_str);
                        String hex = String.format("%02x",temp_num);
                        back = back + hex;
                        i = j;
                        break;
                    }
                }
            }else{
                back = back + temp.charAt(i);
            }
        }
        back = back.replaceAll(",","\",");
        back = back + "\"";
        return back;
    }
}
