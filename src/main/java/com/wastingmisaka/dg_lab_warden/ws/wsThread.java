package com.wastingmisaka.dg_lab_warden.ws;

public class wsThread extends Thread{
    @Override
    public void run() {
        WebSocketServerMain.main(null);
    }
}
