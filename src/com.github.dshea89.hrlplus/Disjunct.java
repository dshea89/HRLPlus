package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Disjunct extends ProductionRule implements Serializable {
    public boolean is_cumulative = false;

    public Disjunct() {
    }

    public boolean isBinary() {
        return true;
    }

    public String getName() {
        return "disjunct";
    }

    public Vector allParameters(Vector concept_list, Theory theory) {
        Vector var3 = new Vector();
        Concept var4 = (Concept) concept_list.elementAt(0);
        Concept var5 = (Concept) concept_list.elementAt(1);
        if (var4 == var5) {
            return var3;
        } else if (var4.arity > this.arity_limit) {
            return var3;
        } else if (var4.isGeneralisationOf(var5) < 0 && var5.isGeneralisationOf(var4) < 0) {
            if (var4.types.toString().equals(var5.types.toString())) {
                var3.addElement(new Vector());
            }

            return var3;
        } else {
            return var3;
        }
    }

    public Vector newSpecifications(Vector concept_list, Vector parameters, Theory theory, Vector new_functions) {
        Vector var5 = new Vector();
        Concept var6 = (Concept) concept_list.elementAt(0);
        Concept var7 = (Concept) concept_list.elementAt(1);
        Vector var8 = (Vector)var6.specifications.clone();
        Vector var9 = (Vector)var7.specifications.clone();

        int var12;
        for(int var10 = 0; var10 < var8.size(); ++var10) {
            Specification var11 = (Specification)var8.elementAt(var10);
            var12 = var9.indexOf(var11);
            if (var12 >= 0) {
                var8.removeElementAt(var10);
                var9.removeElementAt(var12);
                var5.addElement(var11);
            }
        }

        Specification var16 = new Specification();
        var16.type = "disjunct";
        var16.rh_starts = var8.size();
        var16.previous_specifications = var8;

        for(int var17 = 0; var17 < var9.size(); ++var17) {
            var16.previous_specifications.addElement(var9.elementAt(var17));
        }

        Vector var18 = new Vector();

        for(var12 = 0; var12 < var16.previous_specifications.size(); ++var12) {
            Specification var13 = (Specification)var16.previous_specifications.elementAt(var12);

            for(int var14 = 0; var14 < var13.permutation.size(); ++var14) {
                String var15 = (String)var13.permutation.elementAt(var14);
                if (!var18.contains(var15)) {
                    var18.addElement(var15);
                }
            }
        }

        for(var12 = 0; var12 < var6.arity; ++var12) {
            String var19 = Integer.toString(var12);
            if (var18.contains(var19)) {
                var16.permutation.addElement(var19);
            } else {
                var16.redundant_columns.addElement(var19);
            }
        }

        var5.addElement(var16);
        return var5;
    }

    public Datatable transformTable(Vector old_datatables, Vector old_concepts, Vector parameters, Vector all_concepts) {
        Datatable var5 = new Datatable();
        Datatable var6 = (Datatable) old_datatables.elementAt(0);
        Datatable var7 = (Datatable) old_datatables.elementAt(1);

        for(int var8 = 0; var8 < var6.size(); ++var8) {
            Row var9 = (Row)var6.elementAt(var8);
            Row var10 = (Row)var7.elementAt(var8);
            var5.addElement(new Row(var9.entity, this.makeDisjunctRow(var9, var10)));
        }

        return var5;
    }

    private Tuples makeDisjunctRow(Row var1, Row var2) {
        Tuples var3 = new Tuples();
        Vector var4 = new Vector();

        int var5;
        Vector var6;
        for(var5 = 0; var5 < var1.tuples.size(); ++var5) {
            var6 = (Vector)var1.tuples.elementAt(var5);
            var3.addElement((Vector)var6.clone());
            var4.addElement(var6.toString());
        }

        for(var5 = 0; var5 < var2.tuples.size(); ++var5) {
            var6 = (Vector)var2.tuples.elementAt(var5);
            String var7 = var6.toString();
            if (!var4.contains(var7)) {
                var3.addElement((Vector)var6.clone());
                var4.addElement(var7);
            }
        }

        return var3;
    }

    public Vector transformTypes(Vector old_concepts, Vector parameters) {
        Concept var3 = (Concept) old_concepts.elementAt(0);
        return var3.types;
    }

    public int patternScore(Vector concept_list, Vector all_concepts, Vector entity_list, Vector non_entity_list) {
        Concept var5 = (Concept) concept_list.elementAt(0);
        Concept var6 = (Concept) concept_list.elementAt(1);
        String var7 = (String) entity_list.elementAt(0);
        Row var8 = var5.datatable.rowWithEntity(var7);
        Row var9 = var6.datatable.rowWithEntity(var7);
        Tuples var10 = this.makeDisjunctRow(var8, var9);
        int var11 = non_entity_list.size();

        for(int var12 = 1; var11 > 0 && var12 < var10.size(); ++var12) {
            Vector var13 = (Vector)var10.elementAt(var12);

            for(int var14 = 1; var11 > 0 && var14 < entity_list.size(); ++var14) {
                String var15 = (String) entity_list.elementAt(var14);
                Row var16 = var5.datatable.rowWithEntity(var15);
                if (!var16.tuples.contains(var13)) {
                    Row var17 = var5.datatable.rowWithEntity(var15);
                    if (!var17.tuples.contains(var13)) {
                        var11 = 0;
                    }
                }
            }
        }

        return var11;
    }
}
