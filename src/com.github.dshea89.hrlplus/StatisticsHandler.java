package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.util.Enumeration;
import java.io.*;
import java.lang.Runtime.*;
import java.awt.*;
import javax.swing.*;

/** A class for compiling data about a theory.
 *
 * @author Simon Colton, started 29th June.
 * @version 1.0 */

public class StatisticsHandler implements Serializable
{
    /** The output table where the data will be recorder.
     */

    public JTable statistics_output_table = new JTable();

    /** The panel which the table will be put into.
     */

    public JPanel statistics_output_panel = new JPanel();

    /** The pane which the panel will be put into.
     */

    public JScrollPane statistics_output_pane = new JScrollPane();

    /** The reflection mechanism for calculating values and
     * checking conditions.
     */

    public Reflection reflect = new Reflection();

    /** This adds choices for plots into the given list.
     */

    public void addChoices(String statistics_type, SortableList statistics_choice_list,
                           SortableList statistics_subchoice_list, SortableList statistics_methods_list,
                           Timer timer)
    {
        statistics_choice_list.clear();
        statistics_subchoice_list.clear();
        statistics_methods_list.clear();
        Object obj = new Object();
        if (statistics_type.equals("process times"))
        {
            updateProcessTimes(timer);
            return;
        }
        if (statistics_type.equals("concept versus"))
            obj = new Concept();
        if (statistics_type.equals("equivalence versus"))
            obj = new Equivalence();
        if (statistics_type.equals("nonexists versus"))
            obj = new NonExists();
        if (statistics_type.equals("implicate versus"))
            obj = new Implicate();
        if (statistics_type.equals("near_equiv versus"))
            obj = new NearEquivalence();
        if (statistics_type.equals("step versus"))
            obj = new Step();
        if (statistics_type.equals("entity versus"))
            obj = new Entity();
        String[] types = {"int", "double", "float", "long", "boolean", "String", "Vector"};
        Vector field_names = reflect.getFieldNames(obj, types, true);
        for (int i=0; i<field_names.size(); i++)
        {
            String field_name = (String)field_names.elementAt(i);
            if (field_name.indexOf(".")<0)
                statistics_choice_list.addItem((String)field_names.elementAt(i));
            else
                statistics_subchoice_list.addItem((String)field_names.elementAt(i));
        }
        Vector method_names = reflect.getMethodDetails(obj);
        for (int i=0; i<method_names.size(); i++)
            statistics_methods_list.addItem((String)method_names.elementAt(i));
    }

    /** This plots a graph given the theory (it gets values from the front end)
     */

    public void compileData(String plot_type, Vector plot_choices,
                            boolean average_values, boolean count_values,
                            Theory theory, String prune_string, String calculation_string,
                            String sort_string, String file_name)
    {
        double total = 0;
        if (!calculation_string.equals(""))
        {
            calculation_string = calculation_string + " &&  ";
            while (calculation_string.indexOf(" && ") >= 0)
            {
                plot_choices.addElement(calculation_string.substring(0, calculation_string.indexOf(" && ")));
                calculation_string =
                        calculation_string.substring(calculation_string.indexOf(" && ") + 4, calculation_string.length());
            }
        }

        String x_axis = plot_type.substring(0, plot_type.indexOf(" "));
        Vector v = new Vector();

        if (x_axis.equals("concept")) v = theory.concepts;
        if (x_axis.equals("equivalence")) v = theory.equivalences;
        if (x_axis.equals("nonexists")) v = theory.non_existences;
        if (x_axis.equals("implicate")) v = theory.implicates;
        if (x_axis.equals("near_equiv")) v = theory.near_equivalences;
        if (x_axis.equals("step")) v = theory.previous_steps;
        if (x_axis.equals("entity")) v = theory.entities;

        SortableVector objects_passing_pruning = new SortableVector();

        int counter=0;
        Vector row_data = new Vector();
        for (int i=0; i<v.size(); i++)
        {
            Object obj = v.elementAt(i);
            if (prune_string.trim().equals("") ||
                    reflect.checkCondition(obj, prune_string))
            {
                objects_passing_pruning.addElement(obj, sort_string);
                row_data.addElement(new Vector());
            }
        }

        for (int i=0; i<plot_choices.size(); i++)
        {
            total = 0;
            int x_value = 0;
            Enumeration en = objects_passing_pruning.elements();
            while (en.hasMoreElements())
            {
                Object obj = en.nextElement();
                String value =
                        (reflect.getValue(obj, (String)plot_choices.elementAt(i))).toString();
                if (average_values)
                {
                    try
                    {
                        double lhd = (new Double(x_value)).doubleValue();
                        total = total + (new Double(value)).doubleValue();
                        value = Double.toString(total/(lhd+1));
                    }
                    catch(Exception e)
                    {
                    }
                }
                Vector row = (Vector)row_data.elementAt(x_value);
                row.addElement(value);
                x_value++;
            }
        }

        Vector column_names = new Vector();
        for (int i=0; i<plot_choices.size(); i++)
        {
            if (average_values)
                column_names.addElement("av." + (String)plot_choices.elementAt(i));
            else
                column_names.addElement((String)plot_choices.elementAt(i));
        }

        if (count_values)
        {
            Vector new_row_data = new Vector();
            Hashtable row_string_hashtable = new Hashtable();
            for (int i=0; i<row_data.size(); i++)
            {
                Vector row_vector = (Vector)row_data.elementAt(i);
                Integer num = (Integer)row_string_hashtable.get(row_vector);
                if (num==null)
                    row_string_hashtable.put(row_vector, new Integer(1));
                else
                    row_string_hashtable.put(row_vector, new Integer(num.intValue()+1));
            }
            Enumeration enum1 = row_string_hashtable.keys();
            while (enum1.hasMoreElements())
            {
                Vector new_row = (Vector)enum1.nextElement();
                new_row.addElement(((Integer)row_string_hashtable.get(new_row)).toString());
                new_row_data.addElement(new_row);
            }
            column_names.addElement("count");
            statistics_output_table = new JTable(new_row_data, column_names);
            theory.graph_handler.statistics_output_table = statistics_output_table;
            outputToFile(file_name, new_row_data);
        }
        else
        {
            statistics_output_table = new JTable(row_data, column_names);
            theory.graph_handler.statistics_output_table = statistics_output_table;
            outputToFile(file_name, row_data);
        }
        statistics_output_panel.remove(statistics_output_pane);
        statistics_output_pane = new JScrollPane(statistics_output_table);
        statistics_output_panel.setLayout(new BorderLayout());
        statistics_output_panel.add("Center", statistics_output_pane);
        statistics_output_panel.doLayout();
        statistics_output_pane.doLayout();
    }

    private void outputToFile(String file_name, Vector row_data)
    {
        if (!file_name.trim().equals(""))
            try
            {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file_name.trim())));
                for (int i=0; i<row_data.size(); i++)
                {
                    Vector row = (Vector)row_data.elementAt(i);
                    for (int j=0; j<row.size(); j++)
                    {
                        String field = (String)row.elementAt(j);
                        out.write(field + " ");
                    }
                    out.write("\n");
                }
                out.close();
            }
            catch(Exception e){}
    }

    public void updateProcessTimes(Timer timer)
    {
        SortableVector sv = new SortableVector();
        for (int i=0; i<timer.names.size(); i++)
        {
            String name = (String)timer.names.elementAt(i);
            if (!name.equals("Dummy"))
            {
                Long ltime = (Long)timer.times.elementAt(i);
                Vector pair = new Vector();
                pair.addElement(name);
                pair.addElement(ltime.toString());
                sv.addElement(pair, ltime);
            }
        }
        Vector column_names = new Vector();
        column_names.addElement("process");
        column_names.addElement("time");

        statistics_output_table = new JTable(sv, column_names);
        statistics_output_panel.remove(statistics_output_pane);
        statistics_output_pane = new JScrollPane(statistics_output_table);
        statistics_output_panel.setLayout(new BorderLayout());
        statistics_output_panel.add("Center", statistics_output_pane);
        statistics_output_panel.doLayout();
        statistics_output_pane.doLayout();
    }
}
