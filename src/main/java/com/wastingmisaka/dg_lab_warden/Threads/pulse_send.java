package com.wastingmisaka.dg_lab_warden.Threads;

import com.wastingmisaka.dg_lab_warden.messageUtils.MessageSender;

import java.io.IOException;

import static com.wastingmisaka.dg_lab_warden.staticVar.currentVar.current_pulse;
import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.*;

public class pulse_send extends Thread{
    boolean green = true;
    MessageSender messageSender = new MessageSender();
    public void run() {
        String sending_pulse = "hso";
        while(true){
            if(progress_session==null||current_pulse.isEmpty()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            try {
                if(sending_pulse!=current_pulse){
                    messageSender.message_entry("clear",1,"0",0);
                    messageSender.message_entry("clear",2,"0",0);
                    sending_pulse = current_pulse;
                }
                if(a_enabled){
                    messageSender.message_entry("pulse",1,current_pulse,0);
                    green = false;
                }
                if(b_enabled){
                    messageSender.message_entry("pulse",2,current_pulse,1);
                    green = false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
