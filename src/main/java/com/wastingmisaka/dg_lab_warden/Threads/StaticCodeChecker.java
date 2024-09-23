package com.wastingmisaka.dg_lab_warden.Threads;

import com.intellij.codeInsight.daemon.impl.DaemonCodeAnalyzerImpl;
import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.List;

import static com.wastingmisaka.dg_lab_warden.staticVar.statusVar.*;

public class StaticCodeChecker extends Thread{
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
            for (Project p : openProjects) {
                Editor editor = FileEditorManager.getInstance(p).getSelectedTextEditor();
                if (editor != null) {
                    // 获取当前文件
                    VirtualFile[] files = FileEditorManager.getInstance(p).getOpenFiles();
                    if (files.length > 0) {
                        List<HighlightInfo> highlightInfos = DaemonCodeAnalyzerImpl.getHighlights(editor.getDocument(), HighlightSeverity.ERROR, p);
                        List<HighlightInfo> highlightInfos2 = DaemonCodeAnalyzerImpl.getHighlights(editor.getDocument(), HighlightSeverity.WARNING, p);
                        long warnings = highlightInfos2.stream()
                                .filter(info -> info.getSeverity() == HighlightSeverity.WARNING)
                                .count();
                        long errors = highlightInfos.stream()
                                .filter(info -> info.getSeverity() == HighlightSeverity.ERROR)
                                .count();
                        // 更新当前的错误数和警告数
                        warning_count = warnings;
                        error_count = errors;
                    }
                }
            }
        }
    }
}
