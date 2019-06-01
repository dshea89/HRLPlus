package com.github.dshea89.hrlplus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;

public class GroupFile extends Thread implements Serializable {
    String group_file_name = "group-file";

    public GroupFile() {
    }

    public GroupFile(String var1) {
        this.group_file_name = this.group_file_name;
    }

    public void writeToGroupFile(String var1) {
        System.out.println("writeToGroupFile: " + var1);
        if (!AgentOutputPanel.group_file_text.getText().equals("")) {
            this.group_file_name = AgentOutputPanel.group_file_text.getText();
        }

        boolean var2 = false;

        while(!var2) {
            try {
                PrintWriter var3 = new PrintWriter(new BufferedWriter(new FileWriter(this.group_file_name, true)));
                var3.println(var1 + "\n");
                var3.close();
                var2 = true;
                break;
            } catch (Exception var5) {
                System.out.println(var5);

                try {
                    sleep(50L);
                } catch (Exception var4) {
                    ;
                }
            }
        }

        System.out.println("done writeToGroupFile");
    }
}
