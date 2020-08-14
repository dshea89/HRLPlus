package com.github.dshea89.hrlplus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import java.io.*;
import javax.swing.JScrollPane;

public class AgentOutputPanel extends Panel implements ActionListener {

    /** The number of lines read from the agent. */

    public int number_of_lines_read = 0;

    /** The tabbed pane which this panel is in. */

    public JTabbedPane tabbed_pane;

    /** The text area for the output from the agent. */

    public JTextArea agent_output_text = new JTextArea();

    /** The text field specifying the filename. */

    public TextField agent_file_text = new TextField(20);

    /** The checkbox for whether to autoupdate. */

    public Checkbox auto_update_check = new Checkbox("Auto update", true);

    /** The button for updating the agent text. */

    public Button update_text_button = new Button("Update");

    /** The textfield for specifying the group dialog file. */

    public static TextField group_file_text = new TextField(20);

    /** Whether or not this panel is recording the group dialogue. */

    public boolean is_group_dialogue_panel = false;

    /** A textfield for the search string. */

    public TextField search_text = new TextField(30);

    /** A list for the results of a search. */

    public List search_output_list;

    public AgentOutputPanel(JTabbedPane tabbed_pane, boolean add_group_text)
    {
        this.tabbed_pane = tabbed_pane;
        is_group_dialogue_panel = add_group_text;
        update_text_button.addActionListener(this);
        tabbed_pane.setBackground(new Color(150,0,0));
        agent_file_text.setBackground(new Color(220,220,100));
        search_text.setBackground(new Color(150,0,0));
        group_file_text.setBackground(new Color(220,220,100));
        agent_output_text.setFont(new Font("Arial", Font.BOLD, 14));
        agent_output_text.setBackground(Color.BLACK);
        agent_output_text.setForeground(Color.GREEN);
        agent_output_text.setCaretColor(Color.GREEN);
        agent_output_text.setLineWrap(true);
        agent_output_text.setWrapStyleWord(true);
        Panel controls_panel = new Panel();

        controls_panel.setBackground(new Color(150,0,0));
        if (add_group_text){
            controls_panel.add(new Label("Group file:"));
            controls_panel.add(group_file_text);
        }
        else{
            controls_panel.add(new Label("Agent file:"));
            controls_panel.add(agent_file_text);
        }
        controls_panel.add(update_text_button);
        controls_panel.add(auto_update_check);
        setLayout(new BorderLayout());
        add(BorderLayout.NORTH, controls_panel);
        add(BorderLayout.CENTER, new JScrollPane(agent_output_text));
        UpdateThread ut = new UpdateThread();
        ut.start();
    }

    public void actionPerformed(ActionEvent event)
    {

        if (event.getSource()==update_text_button){
            updateTextFromFile();
        }
    }

    public void updateTextFromFile()
    {
        String input_filename = agent_file_text.getText().trim();
        if (is_group_dialogue_panel)
            input_filename = group_file_text.getText().trim();
        if (input_filename.equals(""))
            return;
        String fn = System.getProperty("user.dir") + File.separator + input_filename;
        int counter = 0;
        String text_to_append = "";
        try
        {
            File file = new File(fn);
            if (!file.exists())
                return;
            BufferedReader in = new BufferedReader(new FileReader(fn));
            String s = in.readLine();
            while (s!=null)
            {
                counter++;
                if (counter > number_of_lines_read)
                    text_to_append += s + "\n";
                s = in.readLine();
            }
            in.close();
            if (s!=null && !s.equals("") && counter > number_of_lines_read){
                text_to_append += s + "\n";
                counter++;
            }
            if (number_of_lines_read < counter && is_group_dialogue_panel)
                text_to_append += "-----------\n";
            number_of_lines_read = counter;
            agent_output_text.append(text_to_append);
        }
        catch (Exception ex){
        }
    }

    public class UpdateThread extends Thread
    {
        public void run(){
            while(10 < 11){
                try{
                    if (auto_update_check.getState()==true){
                        updateTextFromFile();
                    }
                    sleep(1000);
                }
                catch(Exception ex){
                }
            }
        }
    }
}
