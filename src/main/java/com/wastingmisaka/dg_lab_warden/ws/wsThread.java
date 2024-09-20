package com.wastingmisaka.dg_lab_warden.ws;

import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.progress_server;
public class wsThread extends Thread{
    @Override
    public void run() {
        System.out.println("wsThread on----------------");
        WebSocketServerMain.main(null);
        progress_server = null;
        System.out.println("wsThread off----------------");
    }
}
