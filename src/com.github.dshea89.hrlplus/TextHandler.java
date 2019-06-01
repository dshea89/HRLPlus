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

public class TextHandler implements Serializable {
    JTable statistics_output_table = new JTable();
    JPanel statistics_output_panel = new JPanel();
    JScrollPane statistics_output_pane = new JScrollPane();
    Vector column_names = new Vector();
    Reflection reflect = new Reflection();

    public TextHandler() {
    }

    public boolean getGrepPositions(String var1, String var2, Vector var3, int var4) {
        int var5 = var1.indexOf("@");
        if (var5 >= 0) {
            String var6 = var1.substring(0, var5);
            String var7 = var1.substring(var5 + 1, var1.length());
            Vector var8 = new Vector();
            boolean var9 = this.getGrepPositions(var6, var2, var8, var4);
            if (var9) {
                int var10 = new Integer((String)var8.elementAt(1));
                Vector var11 = new Vector();
                boolean var12 = this.getGrepPositions(var7, var2, var11, var10);
                if (var12) {
                    var3.addElement(var8.elementAt(0));
                    var3.addElement(var11.elementAt(1));
                    return true;
                }
            }
        }

        int var13 = var2.indexOf(var1, var4);
        int var14 = var13 + var1.length();
        if (var13 > 0) {
            var3.addElement(Integer.toString(var13));
            var3.addElement(Integer.toString(var14));
            return true;
        } else {
            return false;
        }
    }

    public void addChoices(String var1, SortableList var2, SortableList var3, SortableList var4) {
        var2.clear();
        var3.clear();
        var4.clear();
        Object var5 = new Object();
        if (var1.equals("concept versus")) {
            var5 = new Concept();
        }

        if (var1.equals("equivalence versus")) {
            var5 = new Equivalence();
        }

        if (var1.equals("nonexists versus")) {
            var5 = new NonExists();
        }

        if (var1.equals("implicate versus")) {
            var5 = new Implicate();
        }

        if (var1.equals("near_equiv versus")) {
            var5 = new NearEquivalence();
        }

        if (var1.equals("step versus")) {
            var5 = new Step();
        }

        if (var1.equals("entity versus")) {
            var5 = new Entity();
        }

        String[] var6 = new String[]{"int", "double", "float", "long", "boolean", "String", "Vector"};
        Vector var7 = this.reflect.getFieldNames(var5, var6, true);

        for(int var8 = 0; var8 < var7.size(); ++var8) {
            String var9 = (String)var7.elementAt(var8);
            if (var9.indexOf(".") < 0) {
                var2.addItem((String)var7.elementAt(var8));
            } else {
                var3.addItem((String)var7.elementAt(var8));
            }
        }

        Vector var10 = this.reflect.getMethodDetails(var5);

        for(int var11 = 0; var11 < var10.size(); ++var11) {
            var4.addItem((String)var10.elementAt(var11));
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
            if (this.reflect.checkCondition(var18, var6)) {
                var14.addElement(var18, var8);
                var16.addElement(new Vector());
            }
        }

        Enumeration var19;
        for(var17 = 0; var17 < var2.size(); ++var17) {
            var10 = 0.0D;
            int var25 = 0;

            for(var19 = var14.elements(); var19.hasMoreElements(); ++var25) {
                Object var20 = var19.nextElement();
                String var21 = this.reflect.getValue(var20, (String)var2.elementAt(var17)).toString();
                if (var3) {
                    try {
                        double var22 = new Double((double)var25);
                        var10 += new Double(var21);
                        var21 = Double.toString(var10 / (var22 + 1.0D));
                    } catch (Exception var24) {
                        ;
                    }
                }

                Vector var31 = (Vector)var16.elementAt(var25);
                var31.addElement(var21);
            }
        }

        this.column_names = new Vector();

        for(var17 = 0; var17 < var2.size(); ++var17) {
            if (var3) {
                this.column_names.addElement("av." + (String)var2.elementAt(var17));
            } else {
                this.column_names.addElement((String)var2.elementAt(var17));
            }
        }

        if (var4) {
            Vector var26 = new Vector();
            Hashtable var27 = new Hashtable();

            Vector var29;
            for(int var28 = 0; var28 < var16.size(); ++var28) {
                var29 = (Vector)var16.elementAt(var28);
                Integer var30 = (Integer)var27.get(var29);
                if (var30 == null) {
                    var27.put(var29, new Integer(1));
                } else {
                    var27.put(var29, new Integer(var30 + 1));
                }
            }

            var19 = var27.keys();

            while(var19.hasMoreElements()) {
                var29 = (Vector)var19.nextElement();
                var29.addElement(((Integer)var27.get(var29)).toString());
                var26.addElement(var29);
            }

            this.column_names.addElement("count");
            this.statistics_output_table = new JTable(var26, this.column_names);
            this.outputToFile(var9, var26);
        } else {
            this.statistics_output_table = new JTable(var16, this.column_names);
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
}
