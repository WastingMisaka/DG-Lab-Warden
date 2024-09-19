package com.wastingmisaka.dg_lab_warden.ws;

public class wsThread extends Thread{
    @Override
    public void run() {
        System.out.println("wsThread on----------------");
        WebSocketServerMain.main(null);
        System.out.println("wsThread off----------------");
    }
}
