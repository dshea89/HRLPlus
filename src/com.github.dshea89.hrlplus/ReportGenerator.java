package com.github.dshea89.hrlplus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JEditorPane;

public class ReportGenerator implements Serializable {
    public String output_text = "";
    public PrintWriter output_file = null;
    public String input_files_directory = "";
    public JEditorPane report_output_text = new JEditorPane();
    public Vector command_line_arguments = new Vector();
    public Vector objects_to_report = new Vector();
    public String sort_key = "";
    public double prune_less_than = 0.0D;
    public int counter = 0;
    public String old_value = "";
    public int number_of_runs_through = 1;

    public ReportGenerator() {
    }

    public void runReports(Theory var1, String[] var2) {
        this.output_text = "";
        this.number_of_runs_through = 1;

        for(int var3 = 0; var3 < var2.length; ++var3) {
            this.sort_key = "";
            this.objects_to_report = new Vector();
            this.old_value = "";
            Report var4 = new Report(this.input_files_directory + var2[var3]);

            for(int var5 = 0; var5 < this.number_of_runs_through; ++var5) {
                var4.pseudo_code_interpreter.local_alias_hashtable.put("theory", var1);
                var4.pseudo_code_interpreter.local_alias_hashtable.put("stage", "start");
                var4.pseudo_code_interpreter.local_alias_hashtable.put("screen", this.report_output_text);
                var4.pseudo_code_interpreter.local_alias_hashtable.put("this", this);
                var4.pseudo_code_interpreter.local_alias_hashtable.put("run_through", new Integer(var5));
                var4.runReport();
                this.counter = 0;
                SortableVector var6 = new SortableVector();
                var6.prune_less_than = this.prune_less_than;

                int var7;
                for(var7 = 0; var7 < this.objects_to_report.size(); ++var7) {
                    if (!this.sort_key.equals("")) {
                        var6.addElement(this.objects_to_report.elementAt(var7), this.sort_key);
                    } else {
                        var6.addElement(this.objects_to_report.elementAt(var7));
                    }
                }

                for(var7 = 0; var7 < var6.size(); ++var7) {
                    var4.pseudo_code_interpreter.local_alias_hashtable = new Hashtable();
                    var4.pseudo_code_interpreter.local_alias_hashtable.put("run_through", new Integer(var5));
                    var4.pseudo_code_interpreter.local_alias_hashtable.put("screen", this.report_output_text);
                    var4.pseudo_code_interpreter.local_alias_hashtable.put("counter", Integer.toString(this.counter));
                    var4.pseudo_code_interpreter.local_alias_hashtable.put("stage", "main");
                    var4.pseudo_code_interpreter.local_alias_hashtable.put("theory", var1);
                    var4.pseudo_code_interpreter.local_alias_hashtable.put("object", var6.elementAt(var7));
                    var4.pseudo_code_interpreter.local_alias_hashtable.put("old_value", this.old_value);
                    var4.pseudo_code_interpreter.local_alias_hashtable.put("this", this);
                    var4.runReport();
                    ++this.counter;
                }

                var4.pseudo_code_interpreter.local_alias_hashtable.put("this", this);
                var4.pseudo_code_interpreter.local_alias_hashtable.put("run_through", new Integer(var5));
                var4.pseudo_code_interpreter.local_alias_hashtable.put("theory", var1);
                var4.pseudo_code_interpreter.local_alias_hashtable.put("screen", this.report_output_text);
                var4.pseudo_code_interpreter.local_alias_hashtable.put("stage", "end");
                var4.runReport();
            }
        }

        this.report_output_text.setText(this.output_text);
        this.report_output_text.setCaretPosition(0);
    }

    public void writeToScreen(String var1) {
        this.output_text = this.output_text + var1;
    }

    public void writeToFile(String var1) {
        this.output_file.write(var1);
    }

    public void writeToScreenAndFile(String var1) {
        this.output_file.write(var1);
        this.output_text = this.output_text + var1;
    }

    public void openFileForWriting(String var1) {
        try {
            this.output_file = new PrintWriter(new BufferedWriter(new FileWriter(var1, false)));
        } catch (Exception var3) {
            this.writeToScreen("cannot open file: " + var1);
        }

    }

    public void openFileForAppend(String var1) {
        try {
            this.output_file = new PrintWriter(new BufferedWriter(new FileWriter(var1, true)));
        } catch (Exception var3) {
            this.writeToScreen("cannot open file: " + var1);
        }

    }

    public void closeFile() {
        this.output_file.close();
    }

    public void clearScreen() {
        this.report_output_text.setText("");
    }

    public String getDate() {
        Date var1 = new Date();
        String var2 = var1.toString();
        return var2.substring(0, var2.lastIndexOf(":") + 3);
    }
}
