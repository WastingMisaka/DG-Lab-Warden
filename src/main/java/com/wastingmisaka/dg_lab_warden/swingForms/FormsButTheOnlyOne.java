package com.wastingmisaka.dg_lab_warden.swingForms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.wastingmisaka.dg_lab_warden.ws.WebSocketServerMain;
import com.wastingmisaka.dg_lab_warden.ws.wsThread;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import static com.wastingmisaka.dg_lab_warden.staticVar.constVar.current_default;
import static com.wastingmisaka.dg_lab_warden.staticVar.currentVar.*;
import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.MainFunction;

// 窗体
// TODO 电流label 按钮功能 Timer
public class FormsButTheOnlyOne implements ToolWindowFactory {

    private JButton switcher;
    private JLabel a_current_show;
    private JLabel b_current_show;
    private JLabel a_max_show;
    private JLabel b_max_show;
    private JButton setZero;
    private JButton button2;
    private JSlider A_current_slider;
    private JButton FIRE;
    private JPanel mainPanel;

    public JComponent getComponent() {
        return mainPanel;
    }

    public void run(){
        init_();
        timer.start();
    }
    public void init_(){
        // 初始化窗体标签
        a_current_show.setText(current_default[1]);
        b_current_show.setText(current_default[2]);
        a_max_show.setText(current_default[3]);
        b_max_show.setText(current_default[4]);
        // 添加按钮监听
        // 归零
        switcher.addActionListener(e ->{
            if(MainFunction==0){
                switcher.setForeground(Color.RED);
                switcher.setText("关闭连接");
                MainFunction = 1;
            }else{
                switcher.setForeground(Color.GREEN);
                switcher.setText("开启连接");
                MainFunction = 0;
            }

            // TODO 调用发送归零信息
        });
    }

    Timer timer = new Timer(1000, e -> {
        a_current_show.setText(String.valueOf(a_current));
        b_current_show.setText(String.valueOf(b_current));
        a_max_show.setText(String.valueOf(a_max));
        b_max_show.setText(String.valueOf(b_max));
    });

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        final ContentFactory factory = ContentFactory.getInstance();
        {
            new wsThread().start();
            run();
            final Content content1 = factory.createContent(mainPanel, "", false);
            toolWindow.getContentManager().addContent(content1);
        }
    }
}
