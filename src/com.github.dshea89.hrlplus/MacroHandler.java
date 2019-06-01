package com.github.dshea89.hrlplus;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.List;
import java.awt.TextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Hashtable;
import javax.swing.JTable;

public class MacroHandler implements ActionListener, ItemListener, FocusListener, Serializable {
    public int next_highlight_pos = 0;
    public FrontEnd front_end = new FrontEnd();
    public Reflection reflect = new Reflection();

    public MacroHandler() {
    }

    public void actionPerformed(ActionEvent var1) {
        if (this.front_end.record_macro_check.getState()) {
            String var2 = this.reflect.getFieldName(this.front_end, var1.getSource());
            if (!var2.equals("save_macro_button")) {
                this.addCommand("clickButton(\"" + var2 + "\");");
            }
        }

    }

    public void itemStateChanged(ItemEvent var1) {
        if (this.front_end.record_macro_check.getState()) {
            int var2 = -1;

            try {
                var2 = new Integer(var1.getItem().toString());
            } catch (Exception var8) {
                ;
            }

            String var3 = "";

            try {
                if (var2 == -1) {
                    var3 = (String)var1.getItem();
                }
            } catch (Exception var7) {
                ;
            }

            String var4 = "";
            if (var1.getStateChange() == 1) {
                var4 = "true";
            } else {
                var4 = "false";
            }

            String var5 = this.reflect.getFieldName(this.front_end, var1.getSource());
            if (var1.getSource() instanceof List) {
                List var6 = (List)var1.getSource();
                var3 = var6.getItem(var2);
            }

            if (var1.getSource() instanceof List) {
                this.addCommand("clickList(\"" + var5 + "\", \"" + var3 + "\", \"" + var4 + "\");");
            }

            if (var1.getSource() instanceof Choice) {
                this.addCommand("clickChoice(\"" + var5 + "\", \"" + var3 + "\");");
            }

            if (var1.getSource() instanceof Checkbox && !var5.equals("record_macro_check")) {
                this.addCommand("clickCheckbox(\"" + var5 + "\", \"" + var4 + "\");");
            }
        }

    }

    public void focusLost(FocusEvent var1) {
        if (this.front_end.record_macro_check.getState() && var1.getSource() instanceof TextComponent && var1.getSource() != this.front_end.macro_text) {
            String var2 = this.reflect.getFieldName(this.front_end, var1.getSource());
            String var3 = ((TextComponent)var1.getSource()).getText();
            String var4 = "setText(\"" + var2 + "\", \"" + var3 + "\");";
            if (this.front_end.macro_text.getText().indexOf(var4) != this.front_end.macro_text.getText().length() - var4.length()) {
                this.addCommand(var4);
            }
        }

    }

    public void addListeners(Theory var1) {
        Field[] var2 = var1.front_end.getClass().getFields();

        for(int var3 = 0; var3 < var2.length; ++var3) {
            try {
                Object var4 = var2[var3].get(this.front_end);
                if (var4 instanceof List) {
                    ((List)var4).addItemListener(this);
                    ((List)var4).addItemListener(this.front_end);
                    ((List)var4).addItemListener(var1);
                }

                if (var4 instanceof Choice) {
                    ((Choice)var4).addItemListener(this);
                    ((Choice)var4).addItemListener(this.front_end);
                    ((Choice)var4).addItemListener(var1);
                }

                if (var4 instanceof Button) {
                    ((Button)var4).addActionListener(this);
                    ((Button)var4).addActionListener(this.front_end);
                    ((Button)var4).addActionListener(var1);
                }

                if (var4 instanceof TextComponent) {
                    ((TextComponent)var4).addFocusListener(this);
                    ((TextComponent)var4).addFocusListener(this.front_end);
                    ((TextComponent)var4).addFocusListener(var1);
                    ((TextComponent)var4).addKeyListener(var1);
                }

                if (var4 instanceof Checkbox) {
                    ((Checkbox)var4).addItemListener(this);
                    ((Checkbox)var4).addItemListener(this.front_end);
                    ((Checkbox)var4).addItemListener(var1);
                }
            } catch (Exception var5) {
                ;
            }
        }

    }

    public void playMacro(Theory var1) {
        this.front_end.play_macro_button.setLabel("Please wait");
        this.front_end.macro_error_text.setText("");
        Hashtable var2 = new Hashtable();
        Macro var3 = new Macro(var1, var1.macro_to_complete, var2);
        var1.macro_to_complete = "";
        var3.changeCommandLineArguments(var1.command_line_arguments);
        var3.pseudo_code_interpreter.input_text_box = this.front_end.macro_text;
        if (!var1.use_front_end) {
            var3.pseudo_code_interpreter.output_to_system_out = false;
        }

        var3.pseudo_code_interpreter.highlight_pos = this.next_highlight_pos;
        var3.pseudo_code_interpreter.runPseudoCode(var3.pseudo_code_lines);
        if (var3.waiting_for_theory) {
            this.next_highlight_pos = var3.pseudo_code_interpreter.input_text_box.getSelectionEnd() + 1;
        } else {
            this.next_highlight_pos = 0;
            var3.pseudo_code_interpreter.input_text_box.select(0, 0);
        }

        this.front_end.play_macro_button.setLabel("Play macro");
        if (!var3.pseudo_code_interpreter.line_being_looked_at.trim().equals("")) {
            this.front_end.macro_error_text.setText("stalled here: " + var3.pseudo_code_interpreter.line_being_looked_at);
        }

        if (var1.exit_after_macro && !var1.use_front_end && var1.macro_to_complete.equals("")) {
            System.exit(0);
        }

    }

    public void saveTableToMacro(JTable var1) {
        var1.clearSelection();
        String var2 = this.reflect.getFieldName(this.front_end, var1);
        int var3 = var1.getRowCount();
        int var4 = var1.getColumnCount();
        this.addCommand("clearTable(\"" + var2 + "\");");

        for(int var5 = 0; var5 < var3; ++var5) {
            for(int var6 = 0; var6 < var4; ++var6) {
                Object var7 = var1.getValueAt(var5, var6);
                if (var7 instanceof String && !((String)var7).trim().equals("")) {
                    this.addCommand("setTableValue(\"" + var2 + "\",\"" + var7 + "\"," + Integer.toString(var5) + "," + Integer.toString(var6) + ");");
                }
            }
        }

    }

    public void addCommand(String var1) {
        this.front_end.macro_text.append(var1 + "\n");
    }

    public void focusGained(FocusEvent var1) {
    }

    public void actionEvent(ActionEvent var1) {
    }
}
