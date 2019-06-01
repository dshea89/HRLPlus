package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Record extends ProductionRule implements Serializable {
    public Record() {
    }

    public boolean isBinary() {
        return false;
    }

    public boolean isCumulative() {
        return true;
    }

    public String getName() {
        return "record";
    }

    public Datatable transformTable(Vector var1, Vector var2, Vector var3, Vector var4) {
        Datatable var5 = (Datatable)var1.elementAt(0);
        Datatable var6 = new Datatable();
        int var7 = -1000000;

        for(int var8 = 0; var8 < var5.size(); ++var8) {
            Row var9 = (Row)var5.elementAt(var8);
            Tuples var10 = new Tuples();
            if (!var9.tuples.isEmpty()) {
                Vector var11 = (Vector)var9.tuples.elementAt(0);
                int var12 = new Integer((String)var11.elementAt(0));
                if (var12 > var7) {
                    var7 = var12;
                    var10.addElement(new Vector());
                }
            }

            Row var13 = new Row(var9.entity, var10);
            var6.addElement(var13);
        }

        return var6;
    }

    public Vector allParameters(Vector var1, Theory var2) {
        Vector var3 = new Vector();
        Concept var4 = (Concept)var1.elementAt(0);
        if (!var4.domain.equals("number")) {
            return var3;
        } else {
            if (var4.arity == 2) {
                String var5 = (String)var4.types.elementAt(0);
                if (((String)var4.types.elementAt(1)).equals("integer")) {
                    var3.addElement(new Vector());
                }
            }

            return var3;
        }
    }

    public Vector newSpecifications(Vector var1, Vector var2, Theory var3, Vector var4) {
        Vector var5 = new Vector();
        Vector var6 = ((Concept)var1.elementAt(0)).specifications;
        Specification var7 = new Specification();
        var7.previous_specifications = var6;
        var7.type = "record";
        var7.permutation.addElement("0");
        var7.multiple_variable_columns.addElement("1");
        var7.multiple_types.addElement("integer");
        var5.addElement(var7);
        return var5;
    }

    public Vector transformTypes(Vector var1, Vector var2) {
        Vector var3 = (Vector)((Concept)var1.elementAt(0)).types.clone();
        Vector var4 = new Vector();
        var4.addElement((String)var3.elementAt(0));
        return var4;
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4) {
        byte var5 = 0;
        return var5;
    }
}
