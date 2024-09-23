package com.wastingmisaka.dg_lab_warden.Threads;

import com.wastingmisaka.dg_lab_warden.messageUtils.MessageSender;

import java.io.IOException;

import static com.wastingmisaka.dg_lab_warden.staticVar.currentVar.current_pulse;
import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.progress_session;

public class pulse_send extends Thread{
    MessageSender messageSender = new MessageSender();
    public void run() {
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
                messageSender.message_entry("pulse",1,current_pulse,0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
