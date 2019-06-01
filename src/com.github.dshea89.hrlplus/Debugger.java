package com.github.dshea89.hrlplus;

import java.io.Serializable;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Debugger implements Serializable {
    public Debugger() {
    }

    public void runFunction(Theory var1, String var2) {
        JFrame var3 = new JFrame();
        var3.setSize(500, 500);
        JEditorPane var4 = new JEditorPane("text/html", var2);
        JScrollPane var5 = new JScrollPane(var4);
        var3.getContentPane().add(var5);
        var3.setVisible(true);

        try {
            var4.setPage(var2);
        } catch (Exception var7) {
            ;
        }

    }
}
