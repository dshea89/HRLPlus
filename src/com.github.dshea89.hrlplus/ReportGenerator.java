package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.awt.TextArea;
import java.awt.event.*;
import java.io.*;
import java.util.Date;
import javax.swing.JEditorPane;

/** A class for handling the output of various aspects of the theory
 * to files, to the screen (GUI) and to the standard out.
 *
 * @author Simon Colton, started 15th October 2001
 * @version 1.0 */

public class ReportGenerator implements Serializable
{
    /** The text output to the screen.
     */

    public String output_text = "";

    /** The output file which is being written to.
     */

    public PrintWriter output_file = null;

    /** The name of the directory where all the report files are.
     */

    public String input_files_directory = "";

    /** The on-screen text area.
     */

    public JEditorPane report_output_text = new JEditorPane();

    /** The command line arguments to give to the individual reports.
     */

    public Vector command_line_arguments = new Vector();

    /** The vector of objects to report over. Note that this is actually
     * set by the report pseudo-code.
     */

    public Vector objects_to_report = new Vector();

    /** The sort field. Note that this is actually
     * set by the report pseudo-code.
     */

    public String sort_key = "";

    /** The value below which objects in the report will not be reported,
     * which can be reset by a report.
     */

    public double prune_less_than = 0;

    /** A counter, which can be reset by a report.
     */

    public int counter = 0;

    /** The old value (set by the report).
     */

    public String old_value = "";

    /** The number of runs_through the reporting cycle (can be set by the report
     * and utilised by the report).
     */

    public int number_of_runs_through = 1;

    /** This executes the reports.
     */

    public void runReports(Theory theory, String report_names[])
    {
        output_text = "";
        number_of_runs_through = 1;
        for (int i=0; i<report_names.length; i++)
        {
            sort_key = "";
            objects_to_report = new Vector();
            old_value = "";
            Report report = new Report(input_files_directory + report_names[i]);
            for (int run_through = 0; run_through<number_of_runs_through; run_through++)
            {
                report.pseudo_code_interpreter.local_alias_hashtable.put("theory", theory);
                report.pseudo_code_interpreter.local_alias_hashtable.put("stage", "start");
                report.pseudo_code_interpreter.local_alias_hashtable.put("screen", report_output_text);
                report.pseudo_code_interpreter.local_alias_hashtable.put("this", this);
                report.pseudo_code_interpreter.local_alias_hashtable.put("run_through", new Integer(run_through));
                report.runReport();
                counter = 0;
                SortableVector sorted_objects = new SortableVector();
                sorted_objects.prune_less_than = prune_less_than;
                for (int j=0; j<objects_to_report.size(); j++)
                {
                    if (!sort_key.equals(""))
                        sorted_objects.addElement(objects_to_report.elementAt(j), sort_key);
                    else
                        sorted_objects.addElement(objects_to_report.elementAt(j));
                }

                for (int j=0; j<sorted_objects.size(); j++)
                {
                    report.pseudo_code_interpreter.local_alias_hashtable = new Hashtable();
                    report.pseudo_code_interpreter.local_alias_hashtable.put("run_through", new Integer(run_through));
                    report.pseudo_code_interpreter.local_alias_hashtable.put("screen", report_output_text);
                    report.pseudo_code_interpreter.local_alias_hashtable.put("counter", Integer.toString(counter));
                    report.pseudo_code_interpreter.local_alias_hashtable.put("stage", "main");
                    report.pseudo_code_interpreter.local_alias_hashtable.put("theory", theory);
                    report.pseudo_code_interpreter.local_alias_hashtable.put("object", sorted_objects.elementAt(j));
                    report.pseudo_code_interpreter.local_alias_hashtable.put("old_value", old_value);
                    report.pseudo_code_interpreter.local_alias_hashtable.put("this", this);
                    report.runReport();
                    counter++;
                }

                report.pseudo_code_interpreter.local_alias_hashtable.put("this", this);
                report.pseudo_code_interpreter.local_alias_hashtable.put("run_through", new Integer(run_through));
                report.pseudo_code_interpreter.local_alias_hashtable.put("theory", theory);
                report.pseudo_code_interpreter.local_alias_hashtable.put("screen", report_output_text);
                report.pseudo_code_interpreter.local_alias_hashtable.put("stage", "end");
                report.runReport();
            }
        }
        report_output_text.setText(output_text);
        report_output_text.setCaretPosition(0);
    }

    public void writeToScreen(String s)
    {
        output_text = output_text + s;
    }

    public void writeToFile(String s)
    {
        output_file.write(s);
    }

    public void writeToScreenAndFile(String s)
    {
        output_file.write(s);
        output_text = output_text + s;
    }

    public void openFileForWriting(String fn)
    {
        try
        {
            output_file = new PrintWriter(new BufferedWriter(new FileWriter(fn, false)));
        }
        catch(Exception e)
        {
            writeToScreen("cannot open file: " + fn);
        }
    }

    public void openFileForAppend(String fn)
    {
        try
        {
            output_file = new PrintWriter(new BufferedWriter(new FileWriter(fn, true)));
        }
        catch(Exception e)
        {
            writeToScreen("cannot open file: " + fn);
        }
    }

    public void closeFile()
    {
        output_file.close();
    }

    public void clearScreen()
    {
        report_output_text.setText("");
    }

    public String getDate()
    {
        Date d = new Date();
        String output = d.toString();
        return output.substring(0, output.lastIndexOf(":")+3);
    }
}
