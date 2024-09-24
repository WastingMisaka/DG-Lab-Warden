package com.wastingmisaka.dg_lab_warden.swingForms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.wastingmisaka.dg_lab_warden.Threads.StaticCodeChecker;
import com.wastingmisaka.dg_lab_warden.Threads.inspectionPunish;
import com.wastingmisaka.dg_lab_warden.Threads.pulse_send;
import com.wastingmisaka.dg_lab_warden.messageUtils.MessageSender;
import com.wastingmisaka.dg_lab_warden.ws.wsThread;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.wastingmisaka.dg_lab_warden.staticVar.constVar.*;
import static com.wastingmisaka.dg_lab_warden.staticVar.currentVar.*;
import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.*;

// 窗体
public class DG_LAB implements ToolWindowFactory {

    private JButton switcher;
    private JLabel a_current_show;
    private JLabel b_current_show;
    private JLabel a_max_show;
    private JLabel b_max_show;
    private JButton set_zero;
    private JButton test111;
    private JButton FIRE;
    private JPanel panel1;
    private JTextField ip_text;
    private JTextField port_text;
    private JLabel QRCode_show;
    private JLabel server_status;
    private JLabel session_status;
    private JCheckBox a_checkBox;
    private JCheckBox b_checkBox;
    private JCheckBox error_checkBox;
    private JCheckBox warning_checkBox;
    private JList pulse_select;
    private JSpinner fire_spinner;
    private JPanel MainPanel;
    private JPanel Layer1;
    private JPanel Layer2;
    private JPanel Layer2Data;
    private JPanel Layer2QRCode;
    private JPanel Layer3;
    private JSpinner error_spinner;
    private JSpinner warning_spinner;

    MessageSender messageSender = new MessageSender();
    public void run(){
        new pulse_send().start();
        new StaticCodeChecker().start();
        new inspectionPunish().start();
        init_();
        // 启动计时器
        update_current.start();
        update_connect_status.start();
    }
    public void init_(){
        // 加载wave波形
        wave_init();
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
                if(!check_ip_valid(IP)){
                    JOptionPane.showMessageDialog(null,"IP地址不合法","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!check_port_valid(Port)){
                    JOptionPane.showMessageDialog(null,"端口号不合法","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 开启 ws
                new wsThread().start();
                // 生成并显示二维码
                control_QRCode();
                switcher.setForeground(Color.RED);
                switcher.setText("关闭连接");
                MainFunction = 1;
            }else{
                try {
                    messageSender.message_entry("break",0,"0",0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
                // TODO set zero : checkbox disable auto-increment
                error_checkBox.setSelected(false);
                warning_checkBox.setSelected(false);
                messageSender.message_entry("strength",1,"2",0);
                messageSender.message_entry("strength",2,"2",0);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
          // 开火设置成不能自行在APP降低强度？
        FIRE.addActionListener(f -> {
            if(firing == 0){
                fire_current = (int)fire_spinner.getValue();
                try {
                    messageSender.message_entry("strength",1,"1",fire_current);
                    messageSender.message_entry("strength",2,"1",fire_current);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                firing = 1;
                FIRE.setForeground(Color.YELLOW);
                FIRE.setText("停止开火");
            }else{
                try {
                    messageSender.message_entry("strength",1,"0",fire_current);
                    messageSender.message_entry("strength",2,"0",fire_current);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                firing = 0;
                FIRE.setForeground(Color.white);
                FIRE.setText("一键开火");
            }
        });

        //波形选择面板
        String[] options1 = {"呼吸","潮汐","连击","快速按捏","按捏渐强",
            "心跳节奏","压缩","节奏步伐","颗粒摩擦","渐变弹跳","波浪涟漪",
                "雨水冲刷","变速敲击","信号灯","挑逗01","挑逗02"
        };
        pulse_select.setListData(options1);
        pulse_select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pulse_select.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    String selected = (String)pulse_select.getSelectedValue();
                    if(selected!=null)
                        current_pulse = selected;
                    else
                        current_pulse = "empty";
                }
            }
        });
        // Spinner
        {
            fire_spinner.setModel(new SpinnerNumberModel(30, 0, 200, 5));
            error_spinner.setModel(new SpinnerNumberModel(10, 0, 200, 5));
            warning_spinner.setModel(new SpinnerNumberModel(5, 0, 200, 5));

            //轮盘Listener
            error_spinner.addChangeListener(changeEvent -> {
                up_per_error = (int)error_spinner.getValue();
            });
            warning_spinner.addChangeListener(changeEvent -> {
                up_per_warning = (int)warning_spinner.getValue();
            });
        }
        // 复选框Listener
        error_checkBox.addChangeListener(changeEvent -> {
            if(error_checkBox.isSelected())
                error_enabled = true;
            else error_enabled = false;
        });
        warning_checkBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if(warning_checkBox.isSelected())
                    warning_enabled = true;
                else warning_enabled = false;
            }
        });
        a_checkBox.addChangeListener(changeEvent -> {
            if(a_checkBox.isSelected())
                a_enabled = true;
            else{
                try {
                    messageSender.message_entry("strength",1,"2",0);
                    a_enabled = false;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        b_checkBox.addChangeListener(changeEvent -> {
            if(b_checkBox.isSelected())
                b_enabled = true;
            else{
                try {
                    messageSender.message_entry("strength",2,"2",0);
                    b_enabled = false;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    Timer update_current = new Timer(1000, e -> {
        if(progress_session!=null){
            a_current_show.setText("A通道强度："+current_current[1]);
            b_current_show.setText("B通道强度："+current_current[2]);
            a_max_show.setText("A通道上限："+current_max[1]);
            b_max_show.setText("B通道上限："+current_max[2]);
        }
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
            final Content content1 = factory.createContent(MainPanel, "", false);
            toolWindow.getContentManager().addContent(content1);
        }
    }

    public boolean check_ip_valid(String ip){
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
    public boolean check_port_valid(String port){
        if(port.isEmpty()||port.length()>5) return false;
        for(int i=0;i<port.length();i++){
            if(port.charAt(i)<'0'||port.charAt(i)>'9') return false;
        }
        int p = Integer.parseInt(port);
        return p >= 0 && p <= 65535;
    }

    public void control_QRCode(){
        if(MainFunction==0){
            String qrContent = URL_prefix + IP + ":"+Port+URL_suffix;
            BufferedImage qrImage = QRCode.generateQRCodeImage(qrContent,200,200);
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
