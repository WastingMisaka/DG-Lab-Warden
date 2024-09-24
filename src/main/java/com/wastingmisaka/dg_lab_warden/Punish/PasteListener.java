package com.wastingmisaka.dg_lab_warden.Punish;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.project.Project;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PasteListener implements EditorFactoryListener {
    @Override
    public void editorCreated(EditorFactoryEvent event){
        System.out.println("EditorCreated ...");
        System.out.println("EditorCreated ...");
        System.out.println("EditorCreated ...");
        Editor editor = event.getEditor();
        Project project = event.getEditor().getProject();

        if(project!=null){
            System.out.println("if Entered ...");
            while(true){
                editor.getContentComponent().addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e){
                        if((e.getModifiersEx()&KeyEvent.CTRL_DOWN_MASK)!=0 && e.getKeyCode() == KeyEvent.VK_V){
                            System.out.println("paste-=-==----=-=-=-=-=-=-=--=");
                        }
                    }
                });
            }
        }
    }
}
