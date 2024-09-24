package com.wastingmisaka.dg_lab_warden.ws;

import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.progress_server;
public class wsThread extends Thread{
    @Override
    public void run() {
        WebSocketServerMain.main(null);
        progress_server = null;
    }
}
