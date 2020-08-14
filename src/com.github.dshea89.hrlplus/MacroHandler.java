package com.github.dshea89.hrlplus;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*;
import java.io.Serializable;
import java.lang.reflect.*;
import javax.swing.JTable;

public class MacroHandler implements ActionListener, ItemListener, FocusListener, Serializable {

    public int next_highlight_pos = 0;
    public FrontEnd front_end = new FrontEnd();
    public Reflection reflect = new Reflection();

    public void actionPerformed(ActionEvent e) {
        if(front_end.record_macro_check.getState()) {
            String button_name = reflect.getFieldName(front_end, e.getSource());
            if (!button_name.equals("save_macro_button"))
                addCommand("clickButton(\""+button_name+"\");");
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if(front_end.record_macro_check.getState()) {
            int item_number = -1;
            try {
                item_number = (new Integer((String)(e.getItem().toString()))).intValue();
            }
            catch(Exception exc)
            {};
            String item_string = "";
            try {
                if (item_number==-1)
                    item_string = (String)e.getItem();
            }
            catch(Exception exc)
            {};
            String value = "";
            if (e.getStateChange()==1)
                value = "true";
            else
                value = "false";

            String list_name = reflect.getFieldName(front_end, e.getSource());

            if (e.getSource() instanceof List) {
                List list = (List)e.getSource();
                item_string = list.getItem(item_number);
            }

            if (e.getSource() instanceof List)
                addCommand("clickList(\""+list_name+"\", \""+item_string+"\", \""+value+"\");");

            if (e.getSource() instanceof Choice)
                addCommand("clickChoice(\""+list_name+"\", \""+item_string+"\");");

            if (e.getSource() instanceof Checkbox && !list_name.equals("record_macro_check"))
                addCommand("clickCheckbox(\""+list_name+"\", \""+value+"\");");
        }
    }

    public void focusLost(FocusEvent e) {
        if(front_end.record_macro_check.getState()) {
            if (e.getSource() instanceof TextComponent) {
                if (e.getSource() != front_end.macro_text) {
                    String text_name = reflect.getFieldName(front_end, e.getSource());
                    String text_value = ((TextComponent)e.getSource()).getText();
                    String append_text = "setText(\""+text_name+"\", \"" + text_value + "\");";
                    if (!(front_end.macro_text.getText().indexOf(append_text)==
                            front_end.macro_text.getText().length()-append_text.length()))
                        addCommand(append_text);
                }
            }
        }
    }

    public void addListeners(Theory theory) {
        Field[] fields = theory.front_end.getClass().getFields();
        for (int i=0; i<fields.length; i++) {
            try {
                Object obj = fields[i].get(front_end);
                if (obj instanceof List) {
                    ((List)obj).addItemListener(this);
                    ((List)obj).addItemListener(front_end);
                    ((List)obj).addItemListener(theory);
                }
                if (obj instanceof Choice) {
                    ((Choice)obj).addItemListener(this);
                    ((Choice)obj).addItemListener(front_end);
                    ((Choice)obj).addItemListener(theory);
                }
                if (obj instanceof Button) {
                    ((Button)obj).addActionListener(this);
                    ((Button)obj).addActionListener(front_end);
                    ((Button)obj).addActionListener(theory);
                }
                if (obj instanceof TextComponent) {
                    ((TextComponent)obj).addFocusListener(this);
                    ((TextComponent)obj).addFocusListener(front_end);
                    ((TextComponent)obj).addFocusListener(theory);
                    ((TextComponent)obj).addKeyListener(theory);
                }
                if (obj instanceof Checkbox) {
                    ((Checkbox)obj).addItemListener(this);
                    ((Checkbox)obj).addItemListener(front_end);
                    ((Checkbox)obj).addItemListener(theory);
                }
            }
            catch(Exception e)
            {}
        }
    }

    public void playMacro(Theory theory) {
        front_end.play_macro_button.setLabel("Please wait");
        front_end.macro_error_text.setText("");
        Hashtable hashtable = new Hashtable();
        Macro macro = new Macro(theory, theory.macro_to_complete, hashtable);
        theory.macro_to_complete = "";
        macro.changeCommandLineArguments(theory.command_line_arguments);
        macro.pseudo_code_interpreter.input_text_box = front_end.macro_text;
        if (theory.use_front_end==false)
            macro.pseudo_code_interpreter.output_to_system_out = false;
        macro.pseudo_code_interpreter.highlight_pos = next_highlight_pos;
        macro.pseudo_code_interpreter.runPseudoCode(macro.pseudo_code_lines);
        if (macro.waiting_for_theory)
            next_highlight_pos = macro.pseudo_code_interpreter.input_text_box.getSelectionEnd() + 1;
        else {
            next_highlight_pos = 0;
            macro.pseudo_code_interpreter.input_text_box.select(0,0);
        }

        front_end.play_macro_button.setLabel("Play macro");

        if (!macro.pseudo_code_interpreter.line_being_looked_at.trim().equals(""))
            front_end.macro_error_text.setText("stalled here: " + macro.pseudo_code_interpreter.line_being_looked_at);

        if (theory.exit_after_macro && theory.use_front_end==false && theory.macro_to_complete.equals(""))
            System.exit(0);
    }

    public void saveTableToMacro(JTable table) {
        table.clearSelection();
        String table_name = reflect.getFieldName(front_end, table);
        int num_rows = table.getRowCount();
        int num_cols = table.getColumnCount();
        addCommand("clearTable(\"" + table_name + "\");");
        for (int i=0; i<num_rows; i++) {
            for (int j=0; j<num_cols; j++) {
                Object val = table.getValueAt(i,j);
                if (val instanceof String) {
                    if (!((String)val).trim().equals(""))
                        addCommand("setTableValue(\"" + table_name + "\",\"" + val + "\","+ Integer.toString(i) + "," + Integer.toString(j) + ");");
                }
            }
        }
    }

    public void addCommand(String command) {
        front_end.macro_text.append(command+"\n");
    }

    public void focusGained(FocusEvent e)
    {}

    public void actionEvent(ActionEvent e)
    {}

}
