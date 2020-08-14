package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.lang.String;
import java.io.Serializable;
import java.awt.event.*;
import java.awt.*;
import java.lang.reflect.*;
import javax.swing.*;

/** A class for individual macros which set front end settings (and can do other things);
 *
 * @author Simon Colton, started 24th April 2002
 * @version 1.0 */

public class Macro extends PseudoCodeUser implements Serializable
{
    public boolean use_tutorial = true;
    public String next_tutor_text = "";
    public boolean waiting_for_theory = false;
    public int tutorial_stage = 0;
    public Theory theory = null;
    public Reflection reflect = new Reflection();
    public JEditorPane tutorial_text = new JEditorPane("text/html","");

    public Macro(Theory given_theory, String pseudo_code, Hashtable hashtable)
    {
        getCodeLines(pseudo_code);
        original_alias_hashtable = hashtable;
        original_alias_hashtable.put("this",this);
        original_alias_hashtable.put("theory",given_theory);
        pseudo_code_interpreter.local_alias_hashtable = original_alias_hashtable;
        theory = given_theory;
    }

    public Component getComponent(String component_name)
    {
        try
        {
            Field f = theory.front_end.getClass().getField(component_name);
            return (Component)f.get(theory.front_end);
        }
        catch(Exception e)
        {
        }
        return null;
    }

    public void clickButton(String button_name)
    {
        Button button = (Button)getComponent(button_name);
        if (button!=null)
        {
            theory.actionPerformed(new ActionEvent(button,ActionEvent.ACTION_PERFORMED, "button"));
            theory.front_end.actionPerformed(new ActionEvent(button,ActionEvent.ACTION_PERFORMED, "button"));
        }
    }

    public void setText(String text_component_name, String text_value)
    {
        TextComponent text_component = (TextComponent)getComponent(text_component_name);
        if (text_component!=null)
            text_component.setText(text_value);
    }

    public void clickCheckbox(String check_box_name, String check_value)
    {
        Checkbox check_box = (Checkbox)getComponent(check_box_name);
        if (check_box!=null)
        {
            if (check_value.equals("true"))
                check_box.setState(true);
            if (check_value.equals("false"))
                check_box.setState(false);
            theory.itemStateChanged(new ItemEvent(check_box,ItemEvent.ITEM_STATE_CHANGED,
                    "0",ItemEvent.ITEM_STATE_CHANGED));
            theory.front_end.itemStateChanged(new ItemEvent(check_box,ItemEvent.ITEM_STATE_CHANGED,
                    "0",ItemEvent.ITEM_STATE_CHANGED));
        }
    }

    public void clickList(String list_name, String list_item_name, String check_value)
    {
        List list = (List)getComponent(list_name);
        if (list!=null)
        {
            Vector items = new Vector();
            for (int j=0; j<list.getItemCount(); j++)
                items.addElement(list.getItem(j));
            int item_num = items.indexOf(list_item_name);
            String item_num_string = Integer.toString(item_num);
            if (check_value.equals("true"))
                list.select(item_num);
            if (check_value.equals("false"))
                list.deselect(item_num);
            theory.itemStateChanged(new ItemEvent(list,ItemEvent.ITEM_STATE_CHANGED,
                    item_num_string,ItemEvent.ITEM_STATE_CHANGED));
            theory.front_end.itemStateChanged(new ItemEvent(list,ItemEvent.ITEM_STATE_CHANGED,
                    item_num_string,ItemEvent.ITEM_STATE_CHANGED));
        }
    }

    public void clickChoice(String choice_name, String list_item_name)
    {
        Choice choice = (Choice)getComponent(choice_name);
        if (choice!=null)
        {
            Vector items = new Vector();
            for (int j=0; j<choice.getItemCount(); j++)
                items.addElement(choice.getItem(j));

            int item_num = items.indexOf(list_item_name);
            String item_num_string = Integer.toString(item_num);
            choice.select(item_num);
            theory.itemStateChanged(new ItemEvent(choice,ItemEvent.ITEM_STATE_CHANGED,
                    item_num_string,ItemEvent.ITEM_STATE_CHANGED));
            theory.front_end.itemStateChanged(new ItemEvent(choice,ItemEvent.ITEM_STATE_CHANGED,
                    item_num_string,ItemEvent.ITEM_STATE_CHANGED));
        }
    }

    public void setTableValue(String table_name, String value, int row, int col)
    {
        JTable table = (JTable)getComponent(table_name);
        table.setValueAt(value, row, col);
    }

    public void clearTable(String table_name)
    {
        JTable table = (JTable)getComponent(table_name);
        int num_rows = table.getRowCount();
        int num_cols = table.getColumnCount();
        for (int i=0; i<num_rows; i++)
        {
            for (int j=0; j<num_cols; j++)
                table.setValueAt("", i, j);
        }
    }

    public void waitForTheory()
    {
        int i=0;
        boolean add_to_next_macro = false;
        theory.macro_to_complete = "";
        for (i=0; i<pseudo_code_lines.size(); i++)
        {
            String pseudo_code_line = (String)pseudo_code_lines.elementAt(i);
            if (add_to_next_macro)
                theory.macro_to_complete = theory.macro_to_complete + pseudo_code_line + "\n";
            if (pseudo_code_line.trim().equals("waitForTheory();"))
                add_to_next_macro = true;
        }
        pseudo_code_interpreter.break_now = true;
        waiting_for_theory = true;
    }

    public void noteToUser(String note)
    {
        JOptionPane.showMessageDialog(null, note);
    }

    public void tutorLine(String line)
    {
        next_tutor_text = next_tutor_text + line;
    }

    public void tutor(String header)
    {
        if (use_tutorial)
        {
            JOptionPane.showMessageDialog(null, next_tutor_text, header, JOptionPane.INFORMATION_MESSAGE);
            next_tutor_text = "";
        }
    }

    public void tutor(String header, String help_text)
    {
        if (use_tutorial)
        {
            JOptionPane.showMessageDialog(null, help_text + next_tutor_text, header, JOptionPane.INFORMATION_MESSAGE);
            next_tutor_text = "";
        }
    }

    public void showScreen(String screen_name)
    {
        HR hr = (HR)theory.front_end.main_frame;
        hr.changeScreen(screen_name);
    }

    public void useTutorial()
    {
        use_tutorial = true;
        Object[] options = { "Yes", "No" };
        int i = JOptionPane.showOptionDialog(null, "Do you want to read the tutorial\nassociated with this macro?",
                "Run Tutorial?",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);
        if (i==JOptionPane.CLOSED_OPTION || i==1)
            use_tutorial = false;
    }

}
