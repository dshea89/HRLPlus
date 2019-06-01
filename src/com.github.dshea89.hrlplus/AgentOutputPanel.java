package com.github.dshea89.hrlplus;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class AgentOutputPanel extends Panel implements ActionListener {
    public int number_of_lines_read = 0;
    public JTabbedPane tabbed_pane;
    public JTextArea agent_output_text = new JTextArea();
    public TextField agent_file_text = new TextField(20);
    public Checkbox auto_update_check = new Checkbox("Auto update", true);
    public Button update_text_button = new Button("Update");
    public static TextField group_file_text = new TextField(20);
    public boolean is_group_dialogue_panel = false;
    public TextField search_text = new TextField(30);
    public List search_output_list;

    public AgentOutputPanel(JTabbedPane var1, boolean var2) {
        this.tabbed_pane = var1;
        this.is_group_dialogue_panel = var2;
        this.update_text_button.addActionListener(this);
        var1.setBackground(new Color(150, 0, 0));
        this.agent_file_text.setBackground(new Color(220, 220, 100));
        this.search_text.setBackground(new Color(150, 0, 0));
        group_file_text.setBackground(new Color(220, 220, 100));
        this.agent_output_text.setFont(new Font("Arial", 1, 14));
        this.agent_output_text.setBackground(Color.BLACK);
        this.agent_output_text.setForeground(Color.GREEN);
        this.agent_output_text.setCaretColor(Color.GREEN);
        this.agent_output_text.setLineWrap(true);
        this.agent_output_text.setWrapStyleWord(true);
        Panel var3 = new Panel();
        var3.setBackground(new Color(150, 0, 0));
        if (var2) {
            var3.add(new Label("Group file:"));
            var3.add(group_file_text);
        } else {
            var3.add(new Label("Agent file:"));
            var3.add(this.agent_file_text);
        }

        var3.add(this.update_text_button);
        var3.add(this.auto_update_check);
        this.setLayout(new BorderLayout());
        this.add("North", var3);
        this.add("Center", new JScrollPane(this.agent_output_text));
        AgentOutputPanel.UpdateThread var4 = new AgentOutputPanel.UpdateThread();
        var4.start();
    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.update_text_button) {
            this.updateTextFromFile();
        }

    }

    public void updateTextFromFile() {
        String var1 = this.agent_file_text.getText().trim();
        if (this.is_group_dialogue_panel) {
            var1 = group_file_text.getText().trim();
        }

        if (!var1.equals("")) {
            String var2 = System.getProperty("user.dir") + File.separator + var1;
            int var3 = 0;
            String var4 = "";

            try {
                File var5 = new File(var2);
                if (!var5.exists()) {
                    return;
                }

                BufferedReader var6 = new BufferedReader(new FileReader(var2));

                String var7;
                for(var7 = var6.readLine(); var7 != null; var7 = var6.readLine()) {
                    ++var3;
                    if (var3 > this.number_of_lines_read) {
                        var4 = var4 + var7 + "\n";
                    }
                }

                var6.close();
                if (var7 != null && !var7.equals("") && var3 > this.number_of_lines_read) {
                    var4 = var4 + var7 + "\n";
                    ++var3;
                }

                if (this.number_of_lines_read < var3 && this.is_group_dialogue_panel) {
                    var4 = var4 + "-----------\n";
                }

                this.number_of_lines_read = var3;
                this.agent_output_text.append(var4);
            } catch (Exception var8) {
                ;
            }

        }
    }

    public class UpdateThread extends Thread {
        public UpdateThread() {
        }

        public void run() {
            while(true) {
                try {
                    if (AgentOutputPanel.this.auto_update_check.getState()) {
                        AgentOutputPanel.this.updateTextFromFile();
                    }

                    sleep(1000L);
                } catch (Exception var2) {
                    ;
                }
            }
        }
    }
}
