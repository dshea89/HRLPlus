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

public class StatisticsHandler implements Serializable {
    public JTable statistics_output_table = new JTable();
    public JPanel statistics_output_panel = new JPanel();
    public JScrollPane statistics_output_pane = new JScrollPane();
    public Reflection reflect = new Reflection();

    public StatisticsHandler() {
    }

    public void addChoices(String var1, SortableList var2, SortableList var3, SortableList var4, Timer var5) {
        var2.clear();
        var3.clear();
        var4.clear();
        Object var6 = new Object();
        if (var1.equals("process times")) {
            this.updateProcessTimes(var5);
        } else {
            if (var1.equals("concept versus")) {
                var6 = new Concept();
            }

            if (var1.equals("equivalence versus")) {
                var6 = new Equivalence();
            }

            if (var1.equals("nonexists versus")) {
                var6 = new NonExists();
            }

            if (var1.equals("implicate versus")) {
                var6 = new Implicate();
            }

            if (var1.equals("near_equiv versus")) {
                var6 = new NearEquivalence();
            }

            if (var1.equals("step versus")) {
                var6 = new Step();
            }

            if (var1.equals("entity versus")) {
                var6 = new Entity();
            }

            String[] var7 = new String[]{"int", "double", "float", "long", "boolean", "String", "Vector"};
            Vector var8 = this.reflect.getFieldNames(var6, var7, true);

            for(int var9 = 0; var9 < var8.size(); ++var9) {
                String var10 = (String)var8.elementAt(var9);
                if (var10.indexOf(".") < 0) {
                    var2.addItem((String)var8.elementAt(var9));
                } else {
                    var3.addItem((String)var8.elementAt(var9));
                }
            }

            Vector var11 = this.reflect.getMethodDetails(var6);

            for(int var12 = 0; var12 < var11.size(); ++var12) {
                var4.addItem((String)var11.elementAt(var12));
            }

        }
    }

    public void compileData(String var1, Vector var2, boolean var3, boolean var4, Theory var5, String var6, String var7, String var8, String var9) {
        double var10 = 0.0D;
        if (!var7.equals("")) {
            for(var7 = var7 + " &&  "; var7.indexOf(" && ") >= 0; var7 = var7.substring(var7.indexOf(" && ") + 4, var7.length())) {
                var2.addElement(var7.substring(0, var7.indexOf(" && ")));
            }
        }

        String var12 = var1.substring(0, var1.indexOf(" "));
        Vector var13 = new Vector();
        if (var12.equals("concept")) {
            var13 = var5.concepts;
        }

        if (var12.equals("equivalence")) {
            var13 = var5.equivalences;
        }

        if (var12.equals("nonexists")) {
            var13 = var5.non_existences;
        }

        if (var12.equals("implicate")) {
            var13 = var5.implicates;
        }

        if (var12.equals("near_equiv")) {
            var13 = var5.near_equivalences;
        }

        if (var12.equals("step")) {
            var13 = var5.previous_steps;
        }

        if (var12.equals("entity")) {
            var13 = var5.entities;
        }

        SortableVector var14 = new SortableVector();
        boolean var15 = false;
        Vector var16 = new Vector();

        int var17;
        for(var17 = 0; var17 < var13.size(); ++var17) {
            Object var18 = var13.elementAt(var17);
            if (var6.trim().equals("") || this.reflect.checkCondition(var18, var6)) {
                var14.addElement(var18, var8);
                var16.addElement(new Vector());
            }
        }

        int var26;
        for(var17 = 0; var17 < var2.size(); ++var17) {
            var10 = 0.0D;
            var26 = 0;

            for(Enumeration var19 = var14.elements(); var19.hasMoreElements(); ++var26) {
                Object var20 = var19.nextElement();
                String var21 = this.reflect.getValue(var20, (String)var2.elementAt(var17)).toString();
                if (var3) {
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

        for(var26 = 0; var26 < var2.size(); ++var26) {
            if (var3) {
                var25.addElement("av." + (String)var2.elementAt(var26));
            } else {
                var25.addElement((String)var2.elementAt(var26));
            }
        }

        if (var4) {
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
            var5.graph_handler.statistics_output_table = this.statistics_output_table;
            this.outputToFile(var9, var28);
        } else {
            this.statistics_output_table = new JTable(var16, var25);
            var5.graph_handler.statistics_output_table = this.statistics_output_table;
            this.outputToFile(var9, var16);
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

    public void updateProcessTimes(Timer var1) {
        SortableVector var2 = new SortableVector();

        for(int var3 = 0; var3 < var1.names.size(); ++var3) {
            String var4 = (String)var1.names.elementAt(var3);
            if (!var4.equals("Dummy")) {
                Long var5 = (Long)var1.times.elementAt(var3);
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
