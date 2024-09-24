package com.wastingmisaka.dg_lab_warden.messageUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wastingmisaka.dg_lab_warden.staticVar.currentVar.*;
import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.*;

public class MessageSender {

    // 消息格式： MSG + msg + MSG_end
    String MSG = "{\"clientId\":\"Server\",\"targetId\":\"APP\",\"type\":\"msg\",\"message\":\"";
    String MSG_end = "\"}";

    List<String> pulse_list = new ArrayList<>();
    public void send_message(String msg,String type) throws IOException {
        System.out.println("==========----sended msg: "+msg);
        progress_session.getRemote().sendString(msg);
    }
    public void message_entry(String type,int channel,String mode,int num)throws IOException{
        if(progress_session==null) return;
        if(channel == 1 && !a_enabled) return;
        if(channel == 2 && !b_enabled) return;
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
        else if(type.equals("break")){
            back = "{\"clientId\":\"Server\",\"targetId\":\"APP\",\"type\":\"break\",\"message\":\"209\"}";
            send_message(back,type);
            return;
        }
        // 清除队列中的波形数据
        else if(type.equals("clear")){
            back = "clear-"+channel;
        }
        send_message(qp(back),type);
    }
    // 补充消息前后部分
    public String qp(String t){
        return MSG+t+MSG_end;
    }

}
