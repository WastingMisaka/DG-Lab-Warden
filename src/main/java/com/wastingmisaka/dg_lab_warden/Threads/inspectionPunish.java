package com.wastingmisaka.dg_lab_warden.Threads;

import com.wastingmisaka.dg_lab_warden.messageUtils.MessageSender;

import java.io.IOException;

import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.*;

public class inspectionPunish extends Thread{
    long warning_active=0;
    long error_active=0;
    long warning_weight = 5;
    long error_weight = 30;
    MessageSender messageSender = new MessageSender();
    public void run() {
        while(true){
            if(progress_session==null){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // 静态代码检查 警告、错误数变化
            if(warning_active!=warning_count||error_active!=error_count){
                long modify = (warning_count-warning_active)*warning_weight+(error_count-error_active)*error_weight;
                // 增加错误
                if(modify>0){
                    try {
                        messageSender.message_entry("strength",1,"1",(int)modify);
                        messageSender.message_entry("strength",2,"1",(int)modify);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                // 解决错误
                else{
                    try {
                        messageSender.message_entry("strength",1,"0",(int)modify);
                        messageSender.message_entry("strength",2,"0",(int)modify);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
   }
}
