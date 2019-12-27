package com.github.dshea89.hrlplus;

import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * A class for compiling data about a theory.
 */
public class StatisticsHandler implements Serializable {
    /**
     * The output table where the data will be recorder.
     */
    public JTable statistics_output_table = new JTable();

    /**
     * The panel which the table will be put into.
     */
    public JPanel statistics_output_panel = new JPanel();

    /**
     * The pane which the panel will be put into.
     */
    public JScrollPane statistics_output_pane = new JScrollPane();

    /**
     * The reflection mechanism for calculating values and checking conditions.
     */
    public Reflection reflect = new Reflection();

    public StatisticsHandler() {
    }

    /**
     * This adds choices for plots into the given list.
     */
    public void addChoices(String statistics_type, SortableList statistics_choice_list, SortableList statistics_subchoice_list,
                           SortableList statistics_methods_list, Timer timer) {
        statistics_choice_list.clear();
        statistics_subchoice_list.clear();
        statistics_methods_list.clear();
        Object var6 = new Object();
        if (statistics_type.equals("process times")) {
            this.updateProcessTimes(timer);
        } else {
            if (statistics_type.equals("concept versus")) {
                var6 = new Concept();
            }

            if (statistics_type.equals("equivalence versus")) {
                var6 = new Equivalence();
            }

            if (statistics_type.equals("nonexists versus")) {
                var6 = new NonExists();
            }

            if (statistics_type.equals("implicate versus")) {
                var6 = new Implicate();
            }

            if (statistics_type.equals("near_equiv versus")) {
                var6 = new NearEquivalence();
            }

            if (statistics_type.equals("step versus")) {
                var6 = new Step();
            }

            if (statistics_type.equals("entity versus")) {
                var6 = new Entity();
            }

            String[] var7 = new String[]{"int", "double", "float", "long", "boolean", "String", "Vector"};
            Vector var8 = this.reflect.getFieldNames(var6, var7, true);

            for(int var9 = 0; var9 < var8.size(); ++var9) {
                String var10 = (String)var8.elementAt(var9);
                if (var10.indexOf(".") < 0) {
                    statistics_choice_list.addItem((String)var8.elementAt(var9));
                } else {
                    statistics_subchoice_list.addItem((String)var8.elementAt(var9));
                }
            }

            Vector var11 = this.reflect.getMethodDetails(var6);

            for(int var12 = 0; var12 < var11.size(); ++var12) {
                statistics_methods_list.addItem((String)var11.elementAt(var12));
            }

        }
    }

    /**
     * This plots a graph given the theory (it gets values from the front end)
     */
    public void compileData(String plot_type, Vector plot_choices, boolean average_values, boolean count_values, Theory theory,
                            String prune_string, String calculation_string, String sort_string, String file_name) {
        double var10 = 0.0D;
        if (!calculation_string.equals("")) {
            for(calculation_string = calculation_string + " &&  "; calculation_string.indexOf(" && ") >= 0; calculation_string = calculation_string.substring(calculation_string.indexOf(" && ") + 4, calculation_string.length())) {
                plot_choices.addElement(calculation_string.substring(0, calculation_string.indexOf(" && ")));
            }
        }

        String var12 = plot_type.substring(0, plot_type.indexOf(" "));
        Vector var13 = new Vector();
        if (var12.equals("concept")) {
            var13 = theory.concepts;
        }

        if (var12.equals("equivalence")) {
            var13 = theory.equivalences;
        }

        if (var12.equals("nonexists")) {
            var13 = theory.non_existences;
        }

        if (var12.equals("implicate")) {
            var13 = theory.implicates;
        }

        if (var12.equals("near_equiv")) {
            var13 = theory.near_equivalences;
        }

        if (var12.equals("step")) {
            var13 = theory.previous_steps;
        }

        if (var12.equals("entity")) {
            var13 = theory.entities;
        }

        SortableVector var14 = new SortableVector();
        boolean var15 = false;
        Vector var16 = new Vector();

        int var17;
        for(var17 = 0; var17 < var13.size(); ++var17) {
            Object var18 = var13.elementAt(var17);
            if (prune_string.trim().equals("") || this.reflect.checkCondition(var18, prune_string)) {
                var14.addElement(var18, sort_string);
                var16.addElement(new Vector());
            }
        }

        int var26;
        for(var17 = 0; var17 < plot_choices.size(); ++var17) {
            var10 = 0.0D;
            var26 = 0;

            for(Enumeration var19 = var14.elements(); var19.hasMoreElements(); ++var26) {
                Object var20 = var19.nextElement();
                String var21 = this.reflect.getValue(var20, (String)plot_choices.elementAt(var17)).toString();
                if (average_values) {
                    try {
                        double var22 = new Double((double)var26);
                        var10 += new Double(var21);
                        var21 = Double.toString(var10 / (var22 + 1.0D));
                    } catch (Exception var24) {
                        ;
                    }
                }

                Vector var32 = (Vector)var16.elementAt(var26);
                var32.addElement(var21);
            }
        }

        Vector var25 = new Vector();

        for(var26 = 0; var26 < plot_choices.size(); ++var26) {
            if (average_values) {
                var25.addElement("av." + (String)plot_choices.elementAt(var26));
            } else {
                var25.addElement((String)plot_choices.elementAt(var26));
            }
        }

        if (count_values) {
            Vector var28 = new Vector();
            Hashtable var27 = new Hashtable();

            Vector var31;
            for(int var29 = 0; var29 < var16.size(); ++var29) {
                var31 = (Vector)var16.elementAt(var29);
                Integer var33 = (Integer)var27.get(var31);
                if (var33 == null) {
                    var27.put(var31, new Integer(1));
                } else {
                    var27.put(var31, new Integer(var33 + 1));
                }
            }

            Enumeration var30 = var27.keys();

            while(var30.hasMoreElements()) {
                var31 = (Vector)var30.nextElement();
                var31.addElement(((Integer)var27.get(var31)).toString());
                var28.addElement(var31);
            }

            var25.addElement("count");
            this.statistics_output_table = new JTable(var28, var25);
            theory.graph_handler.statistics_output_table = this.statistics_output_table;
            this.outputToFile(file_name, var28);
        } else {
            this.statistics_output_table = new JTable(var16, var25);
            theory.graph_handler.statistics_output_table = this.statistics_output_table;
            this.outputToFile(file_name, var16);
        }

        this.statistics_output_panel.remove(this.statistics_output_pane);
        this.statistics_output_pane = new JScrollPane(this.statistics_output_table);
        this.statistics_output_panel.setLayout(new BorderLayout());
        this.statistics_output_panel.add("Center", this.statistics_output_pane);
        this.statistics_output_panel.doLayout();
        this.statistics_output_pane.doLayout();
    }

    private void outputToFile(String var1, Vector var2) {
        if (!var1.trim().equals("")) {
            try {
                PrintWriter var3 = new PrintWriter(new BufferedWriter(new FileWriter(var1.trim())));

                for(int var4 = 0; var4 < var2.size(); ++var4) {
                    Vector var5 = (Vector)var2.elementAt(var4);

                    for(int var6 = 0; var6 < var5.size(); ++var6) {
                        String var7 = (String)var5.elementAt(var6);
                        var3.write(var7 + " ");
                    }

                    var3.write("\n");
                }

                var3.close();
            } catch (Exception var8) {
                ;
            }
        }

    }

    public void updateProcessTimes(Timer timer) {
        SortableVector var2 = new SortableVector();

        for(int var3 = 0; var3 < timer.names.size(); ++var3) {
            String var4 = (String)timer.names.elementAt(var3);
            if (!var4.equals("Dummy")) {
                Long var5 = (Long)timer.times.elementAt(var3);
                Vector var6 = new Vector();
                var6.addElement(var4);
                var6.addElement(var5.toString());
                var2.addElement(var6, var5);
            }
        }

        Vector var7 = new Vector();
        var7.addElement("process");
        var7.addElement("time");
        this.statistics_output_table = new JTable(var2, var7);
        this.statistics_output_panel.remove(this.statistics_output_pane);
        this.statistics_output_pane = new JScrollPane(this.statistics_output_table);
        this.statistics_output_panel.setLayout(new BorderLayout());
        this.statistics_output_panel.add("Center", this.statistics_output_pane);
        this.statistics_output_panel.doLayout();
        this.statistics_output_pane.doLayout();
    }
}
