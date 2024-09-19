package com.wastingmisaka.dg_lab_warden.swingForms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.wastingmisaka.dg_lab_warden.messageUtils.MessageSender;
import com.wastingmisaka.dg_lab_warden.ws.wsThread;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.wastingmisaka.dg_lab_warden.staticVar.constVar.*;
import static com.wastingmisaka.dg_lab_warden.staticVar.currentVar.*;
import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.*;

// 窗体
// TODO 电流label 按钮功能 Timer
public class FormsButTheOnlyOne implements ToolWindowFactory {

    private JButton switcher;
    private JLabel a_current_show;
    private JLabel b_current_show;
    private JLabel a_max_show;
    private JLabel b_max_show;
    private JButton set_zero;
    private JButton test111;
    private JSlider A_current_slider;
    private JButton FIRE;
    private JPanel mainPanel;
    private JTextField ip_text;
    private JTextField port_text;
    private JLabel slider_label;
    private JLabel QRCode_show;
    private JLabel server_status;
    private JLabel session_status;

    MessageSender messageSender = new MessageSender();
    public JComponent getComponent() {
        return mainPanel;
    }

    public void run(){
        init_();
        update_current.start();
        update_connect_status.start();
    }
    public void init_(){
        // 初始化默认值
        ip_text.setText(IP);
        port_text.setText(Port);
        // 初始化窗体标签
        a_current_show.setText(current_default[1]);
        b_current_show.setText(current_default[2]);
        a_max_show.setText(current_default[3]);
        b_max_show.setText(current_default[4]);
        // 添加按钮监听
            // 功能开关
        switcher.addActionListener(e ->{
            if(MainFunction==0){
                IP = ip_text.getText();
                Port = port_text.getText();
                if(!check_ip_vaild(IP)){
                    JOptionPane.showMessageDialog(null,"IP地址不合法","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                new wsThread().start();
                // 生成并显示二维码
                control_QRCode();
                switcher.setForeground(Color.RED);
                switcher.setText("关闭连接");
                MainFunction = 1;
            }else{
                // 关闭当前的会话和服务器
                if(progress_session!=null)
                    progress_session.close();
                if(progress_server!=null) {
                    try {
                        progress_server.stop();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                control_QRCode();
                switcher.setForeground(Color.GREEN);
                switcher.setText("开启连接");
                MainFunction = 0;
            }
        });
           // 归零按钮
        set_zero.addActionListener(g ->{
            try {
                messageSender.send_message("1+2+0","strength");
                messageSender.send_message("2+2+0","strength");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
          // 开火设置成不能自行在APP降低强度？
        FIRE.addActionListener(f -> {
            if(firing == 0){
                fire_current = A_current_slider.getValue();
                try {
                    messageSender.send_message("1+1+"+fire_current,"strength");
                    messageSender.send_message("2+1+"+fire_current,"strength");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                firing = 1;
                FIRE.setForeground(Color.YELLOW);
                FIRE.setText("停止开火");
            }else{
                try {
                    messageSender.send_message("1+0+"+fire_current,"strength");
                    messageSender.send_message("2+0+"+fire_current,"strength");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                firing = 0;
                FIRE.setForeground(Color.white);
                FIRE.setText("一键开火");
            }
        });
           // 测试用按钮
        test111.addActionListener(g -> {
            JOptionPane.showMessageDialog(null,"一键开火："+A_current_slider.getValue(),"提示",JOptionPane.INFORMATION_MESSAGE);
        });

        // TrackBar
        {
            A_current_slider.setMinimum(0);
            A_current_slider.setMaximum(200);

            A_current_slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    slider_label.setText("一键开火："+A_current_slider.getValue());
                }
            });
        }
    }

    Timer update_current = new Timer(1000, e -> {
        a_current_show.setText("A通道强度："+a_current);
        b_current_show.setText("B通道强度："+b_current);
        a_max_show.setText("A通道上限："+a_max);
        b_max_show.setText("B通道上限："+b_max);
    });

    Timer update_connect_status = new Timer(1000, e -> {
        if(progress_server!=null){
            server_status.setForeground(Color.GREEN);
            server_status.setText("服务器状态：ON");
        }else{
            server_status.setForeground(Color.RED);
            server_status.setText("服务器状态：OFF");
        }
        if(progress_session!=null){
            session_status.setForeground(Color.GREEN);
            session_status.setText("会话状态：ON");
        }else{
            session_status.setForeground(Color.RED);
            session_status.setText("会话状态：OFF");
        }
     });
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        final ContentFactory factory = ContentFactory.getInstance();
        {
            run();
            final Content content1 = factory.createContent(mainPanel, "", false);
            toolWindow.getContentManager().addContent(content1);
        }
    }

    public boolean check_ip_vaild(String ip){
        if(ip.length()<7||ip.length()>15) return false;
        for(int i=0;i<ip.length();i++){
            if(!Character.isDigit(ip.charAt(i))&&ip.charAt(i)!='.'){
                return false;
            }
        }
        String[] temp = ip.split("\\.");
        if(temp.length!=4) return false;
        for(int i=0;i<4;i++){
            if(temp[i].length()>3|| temp[i].isEmpty()) return false;
            int temp_int = Integer.parseInt(temp[i]);
            if(temp_int<0||temp_int>255)   return false;
        }
        return true;
    }

    public void control_QRCode(){
        if(MainFunction==0){
            String qrContent = URL_prefix + IP + ":"+Port+URL_suffix;
            System.out.println("QRCode: "+qrContent);
            BufferedImage qrImage = QRCode.generateQRCodeImage(qrContent,300,300);
            QRCode_show.setText("");
            QRCode_show.setIcon(new ImageIcon(qrImage));
        }else{
            QRCode_show.setText("null");
            QRCode_show.setIcon(null);
            QRCode_show.revalidate();
            QRCode_show.repaint();
        }
    }
}
