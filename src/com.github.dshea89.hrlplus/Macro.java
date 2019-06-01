package com.github.dshea89.hrlplus;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Component;
import java.awt.List;
import java.awt.TextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class Macro extends PseudoCodeUser implements Serializable {
    public boolean use_tutorial = true;
    public String next_tutor_text = "";
    public boolean waiting_for_theory = false;
    public int tutorial_stage = 0;
    public Theory theory = null;
    public Reflection reflect = new Reflection();
    public JEditorPane tutorial_text = new JEditorPane("text/html", "");

    public Macro(Theory var1, String var2, Hashtable var3) {
        this.getCodeLines(var2);
        this.original_alias_hashtable = var3;
        this.original_alias_hashtable.put("this", this);
        this.original_alias_hashtable.put("theory", var1);
        this.pseudo_code_interpreter.local_alias_hashtable = this.original_alias_hashtable;
        this.theory = var1;
    }

    public Component getComponent(String var1) {
        try {
            Field var2 = this.theory.front_end.getClass().getField(var1);
            return (Component)var2.get(this.theory.front_end);
        } catch (Exception var3) {
            return null;
        }
    }

    public void clickButton(String var1) {
        Button var2 = (Button)this.getComponent(var1);
        if (var2 != null) {
            this.theory.actionPerformed(new ActionEvent(var2, 1001, "button"));
            this.theory.front_end.actionPerformed(new ActionEvent(var2, 1001, "button"));
        }

    }

    public void setText(String var1, String var2) {
        TextComponent var3 = (TextComponent)this.getComponent(var1);
        if (var3 != null) {
            var3.setText(var2);
        }

    }

    public void clickCheckbox(String var1, String var2) {
        Checkbox var3 = (Checkbox)this.getComponent(var1);
        if (var3 != null) {
            if (var2.equals("true")) {
                var3.setState(true);
            }

            if (var2.equals("false")) {
                var3.setState(false);
            }

            this.theory.itemStateChanged(new ItemEvent(var3, 701, "0", 701));
            this.theory.front_end.itemStateChanged(new ItemEvent(var3, 701, "0", 701));
        }

    }

    public void clickList(String var1, String var2, String var3) {
        List var4 = (List)this.getComponent(var1);
        if (var4 != null) {
            Vector var5 = new Vector();

            int var6;
            for(var6 = 0; var6 < var4.getItemCount(); ++var6) {
                var5.addElement(var4.getItem(var6));
            }

            var6 = var5.indexOf(var2);
            String var7 = Integer.toString(var6);
            if (var3.equals("true")) {
                var4.select(var6);
            }

            if (var3.equals("false")) {
                var4.deselect(var6);
            }

            this.theory.itemStateChanged(new ItemEvent(var4, 701, var7, 701));
            this.theory.front_end.itemStateChanged(new ItemEvent(var4, 701, var7, 701));
        }

    }

    public void clickChoice(String var1, String var2) {
        Choice var3 = (Choice)this.getComponent(var1);
        if (var3 != null) {
            Vector var4 = new Vector();

            int var5;
            for(var5 = 0; var5 < var3.getItemCount(); ++var5) {
                var4.addElement(var3.getItem(var5));
            }

            var5 = var4.indexOf(var2);
            String var6 = Integer.toString(var5);
            var3.select(var5);
            this.theory.itemStateChanged(new ItemEvent(var3, 701, var6, 701));
            this.theory.front_end.itemStateChanged(new ItemEvent(var3, 701, var6, 701));
        }

    }

    public void setTableValue(String var1, String var2, int var3, int var4) {
        JTable var5 = (JTable)this.getComponent(var1);
        var5.setValueAt(var2, var3, var4);
    }

    public void clearTable(String var1) {
        JTable var2 = (JTable)this.getComponent(var1);
        int var3 = var2.getRowCount();
        int var4 = var2.getColumnCount();

        for(int var5 = 0; var5 < var3; ++var5) {
            for(int var6 = 0; var6 < var4; ++var6) {
                var2.setValueAt("", var5, var6);
            }
        }

    }

    public void waitForTheory() {
        boolean var1 = false;
        boolean var2 = false;
        this.theory.macro_to_complete = "";

        for(int var4 = 0; var4 < this.pseudo_code_lines.size(); ++var4) {
            String var3 = (String)this.pseudo_code_lines.elementAt(var4);
            if (var2) {
                this.theory.macro_to_complete = this.theory.macro_to_complete + var3 + "\n";
            }

            if (var3.trim().equals("waitForTheory();")) {
                var2 = true;
            }
        }

        this.pseudo_code_interpreter.break_now = true;
        this.waiting_for_theory = true;
    }

    public void noteToUser(String var1) {
        JOptionPane.showMessageDialog((Component)null, var1);
    }

    public void tutorLine(String var1) {
        this.next_tutor_text = this.next_tutor_text + var1;
    }

    public void tutor(String var1) {
        if (this.use_tutorial) {
            JOptionPane.showMessageDialog((Component)null, this.next_tutor_text, var1, 1);
            this.next_tutor_text = "";
        }

    }

    public void tutor(String var1, String var2) {
        if (this.use_tutorial) {
            JOptionPane.showMessageDialog((Component)null, var2 + this.next_tutor_text, var1, 1);
            this.next_tutor_text = "";
        }

    }

    public void showScreen(String var1) {
        HR var2 = this.theory.front_end.main_frame;
        var2.changeScreen(var1);
    }

    public void useTutorial() {
        this.use_tutorial = true;
        Object[] var1 = new Object[]{"Yes", "No"};
        int var2 = JOptionPane.showOptionDialog((Component)null, "Do you want to read the tutorial\nassociated with this macro?", "Run Tutorial?", 0, 2, (Icon)null, var1, var1[0]);
        if (var2 == -1 || var2 == 1) {
            this.use_tutorial = false;
        }

    }
}
