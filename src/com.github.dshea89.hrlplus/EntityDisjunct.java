package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class EntityDisjunct extends ProductionRule implements Serializable {
    public int max_num_entities_in_disjunction = 3;
    public boolean is_cumulative = false;
    public boolean is_entity_instantiations = true;

    public EntityDisjunct() {
    }

    public boolean isBinary() {
        return false;
    }

    public String getName() {
        return "entity_disjunct";
    }

    public Vector allParameters(Vector var1, Theory var2) {
        Vector var3 = new Vector();
        return var3;
    }

    public Vector newSpecifications(Vector var1, Vector var2, Theory var3, Vector var4) {
        Concept var5 = (Concept)var1.elementAt(0);
        new Vector();
        Vector var6 = var5.specifications;
        Vector var7 = var5.types;
        String var8 = (String)var7.elementAt(0);
        Concept var9 = null;

        for(int var10 = 0; var10 < var3.concepts.size(); ++var10) {
            var9 = (Concept)var3.concepts.elementAt(var10);
            if (var9.is_object_of_interest_concept && var9.types.toString().equals("[" + var8 + "]")) {
                break;
            }
        }

        if (var9 == null) {
            return new Vector();
        } else {
            Vector var18 = new Vector();
            var18.addElement(var9);
            Vector var11 = new Vector();

            for(int var12 = 0; var12 < var2.size(); ++var12) {
                Vector var13 = new Vector();
                Vector var14 = new Vector();
                Vector var15 = new Vector();
                var13.addElement(var14);
                var13.addElement(var15);
                var14.addElement("0");
                var15.addElement((String)var2.elementAt(var12));
                String var16 = (String)var2.elementAt(var12);
                Concept var17 = new Concept();
                var17.specifications = var3.split.newSpecifications(var18, var13, var3, new Vector());
                var17.arity = 1;
                var17.types = (Vector)var9.types.clone();
                var17.id = "ed_dummy";
                var11.addElement(var17);
            }

            Concept var19 = (Concept)var11.elementAt(0);

            for(int var20 = 1; var20 < var11.size(); ++var20) {
                Concept var21 = (Concept)var11.elementAt(var20);
                var19 = this.disjoinDummyConcepts(var19, var21, var3);
            }

            return var19.specifications;
        }
    }

    public Concept disjoinDummyConcepts(Concept var1, Concept var2, Theory var3) {
        Vector var4 = new Vector();
        var4.addElement(var1);
        var4.addElement(var2);
        Concept var5 = new Concept();
        var5.specifications = var3.disjunct.newSpecifications(var4, new Vector(), var3, new Vector());
        var5.arity = 1;
        var5.types = (Vector)var1.types.clone();
        return var5;
    }

    public Datatable transformTable(Vector var1, Vector var2, Vector var3, Vector var4) {
        Datatable var5 = (Datatable)var1.elementAt(0);
        Datatable var6 = new Datatable();

        for(int var7 = 0; var7 < var5.size(); ++var7) {
            Row var8 = (Row)var5.elementAt(var7);
            Tuples var9 = new Tuples();
            if (var3.contains(var8.entity)) {
                var9.addElement(new Vector());
            }

            Row var10 = new Row(var8.entity, var9);
            var6.addElement(var10);
        }

        return var6;
    }

    public Vector transformTypes(Vector var1, Vector var2) {
        Concept var3 = (Concept)var1.elementAt(0);
        Vector var4 = new Vector();
        var4.addElement(var3.types.elementAt(0));
        return var4;
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4) {
        return 0;
    }
}
