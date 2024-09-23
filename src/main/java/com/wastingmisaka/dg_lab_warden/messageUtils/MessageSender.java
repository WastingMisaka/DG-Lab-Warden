package com.wastingmisaka.dg_lab_warden.messageUtils;

import java.io.IOException;

import static com.wastingmisaka.dg_lab_warden.staticVar.currentVar.*;
import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.progress_session;

public class MessageSender {
    // 消息格式： MSG + msg + MSG_end
    String MSG = "{\"clientId\":\"Server\",\"targetId\":\"APP\",\"type\":\"msg\",\"message\":\"";
    String MSG_end = "\"}";


    public void send_message(String msg,String type) throws IOException {
        System.out.println("==========----sended msg: "+msg);
        progress_session.getRemote().sendString(msg);
    }
    public String message_entry(String type,int channel,String mode,int num)throws IOException{
        String back="";
        if(type.equals("strength")){
            // 设定值是否超过上限
            if(mode.equals("2")&&current_current[channel]+num>current_max[channel]){
                num = current_max[channel];
            }
            back = "strength-" +channel + "+" + mode + "+" + num;
        }
        else if(type.equals("pulse")){
            String Pulse = pulse_data.get(mode);
            Pulse = Pulse+","+Pulse+","+Pulse;
            String pulse_channel;
            if(channel == 1)
                pulse_channel = "A";
            else pulse_channel = "B";
            back = "pulse-" +pulse_channel + ":[" + Pulse + "]";
        }
        // TODO 发送心跳包(5分钟空闲连接会被中断）
        else if(type.equals("heartbeat")){
        }
        send_message(qp(back),type);
        return "200";
    }
    // 补充消息前后部分
    public String qp(String t){
        return MSG+t+MSG_end;
    }

}
