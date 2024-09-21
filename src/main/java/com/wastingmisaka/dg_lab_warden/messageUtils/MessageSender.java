package com.wastingmisaka.dg_lab_warden.messageUtils;

import java.io.IOException;

import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.progress_session;

public class MessageSender {
    // 消息封装为 MSG + msg + MSG_end
    String MSG = "{\"clientId\":\"Server\",\"targetId\":\"APP\",\"type\":\"msg\",\"message\":\"";
    String MSG_end = "\"}";


    public void send_message(String msg,String type) throws IOException {
        if(type.equals("strength")){
            String msg_ = MSG + "strength-" +msg + MSG_end;
            progress_session.getRemote().sendString(msg_);
            //log
            System.out.println("Sended strength: "+msg_);
        }
        if(type.equals("wave")){
            String msg_ = MSG + "pulse-A:[" +msg + "]" +MSG_end;
            progress_session.getRemote().sendString(msg_);

            msg_ = MSG + "pulse-B:[" +msg + "]" +MSG_end;
            progress_session.getRemote().sendString(msg_);
            //log
            System.out.println("Sended pulse-data: "+msg_);
        }
        if(type.equals("heartbeat")){
            String msg_ = MSG + "heartbeat" + MSG_end;
            progress_session.getRemote().sendString(msg_);
            //log
            System.out.println("Sended heartbeat: "+msg_);
        }
    }
}
