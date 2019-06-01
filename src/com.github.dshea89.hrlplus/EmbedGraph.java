package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class EmbedGraph extends ProductionRule implements Serializable {
    public boolean is_cumulative = false;

    public EmbedGraph() {
    }

    public boolean isBinary() {
        return true;
    }

    public String getName() {
        return "embed_graph";
    }

    public Vector allParameters(Vector var1, Theory var2) {
        Vector var3 = new Vector();
        Concept var4 = (Concept)var1.elementAt(0);
        Concept var5 = (Concept)var1.elementAt(1);
        if (var4.arity == 3 && var5.id.equals("G01")) {
            String var6 = (String)var4.types.elementAt(1);
            String var7 = (String)var4.types.elementAt(2);
            if (var6.equals(var7)) {
                var3.addElement(new Vector());
            }
        }

        return var3;
    }

    public Vector newSpecifications(Vector var1, Vector var2, Theory var3, Vector var4) {
        Specification var5 = new Specification();
        var5.permutation.addElement("0");
        var5.permutation.addElement("1");
        Concept var6 = (Concept)var1.elementAt(0);
        var5.type = "embed graph " + var6.id;
        Vector var7 = new Vector();
        var7.addElement(var5);
        return var7;
    }

    public Datatable transformTable(Vector var1, Vector var2, Vector var3, Vector var4) {
        Datatable var5 = new Datatable();
        Datatable var6 = (Datatable)var1.elementAt(0);

        for(int var7 = 0; var7 < var6.size(); ++var7) {
            Row var8 = (Row)var6.elementAt(var7);
            Row var9 = new Row();
            var9.entity = var8.entity;
            Vector var10 = new Vector();
            Vector var11 = new Vector();
            String var12 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZ1234567890";
            String var13 = "";
            String var14 = "[";

            for(int var15 = 0; var15 < var8.tuples.size(); ++var15) {
                Vector var16 = (Vector)var8.tuples.elementAt(var15);
                String var17 = (String)var16.elementAt(0);
                String var18 = (String)var16.elementAt(1);
                String var19 = "";
                String var20 = "";
                int var21 = var10.indexOf(var17);
                if (var21 >= 0) {
                    var19 = (String)var11.elementAt(var21);
                }

                if (!var10.contains(var17)) {
                    var10.addElement(var17);
                    var19 = var12.substring(0, 1);
                    var11.addElement(var19);
                    var12 = var12.substring(1, var12.length());
                    var13 = var13 + var19;
                }

                int var22 = var10.indexOf(var18);
                if (var22 >= 0) {
                    var20 = (String)var11.elementAt(var22);
                }

                if (!var10.contains(var18)) {
                    var10.addElement(var18);
                    var20 = var12.substring(0, 1);
                    var11.addElement(var20);
                    var12 = var12.substring(1, var12.length());
                    var13 = var13 + var20;
                }

                var14 = var14 + var19 + var20;
                if (var15 < var8.tuples.size() - 1) {
                    var14 = var14 + ",";
                }
            }

            Vector var23 = new Vector();
            var23.addElement(var13 + var14 + "]");
            var9.tuples.addElement(var23);
            var5.addElement(var9);
        }

        return var5;
    }

    public Vector transformTypes(Vector var1, Vector var2) {
        Vector var3 = new Vector();
        Concept var4 = (Concept)var1.elementAt(0);
        var3.addElement(var4.domain);
        var3.addElement("graph");
        return var3;
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4) {
        return 0;
    }
}
