package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Equal extends ProductionRule implements Serializable {
    public boolean is_cumulative = false;

    public Equal() {
    }

    public boolean isBinary() {
        return false;
    }

    public String getName() {
        return "equal";
    }

    public Vector allParameters(Vector concept_list, Theory theory) {
        Concept var3 = (Concept) concept_list.elementAt(0);
        if (var3.arity > this.arity_limit) {
            return new Vector();
        } else {
            Vector var4 = super.allColumnTuples(var3.arity + 1);
            Vector var5 = new Vector();

            for(int var6 = 0; var6 < var4.size(); ++var6) {
                Vector var7 = (Vector)var4.elementAt(var6);
                if (var7.size() > 1) {
                    boolean var8 = false;
                    Vector var9 = new Vector();
                    String var10 = "";

                    for(int var11 = 0; var11 < var7.size() && !var8; ++var11) {
                        String var12 = (String)var7.elementAt(var11);
                        int var13 = new Integer(var12) - 1;
                        String var14 = (String)var3.types.elementAt(var13);
                        if (var10.equals("")) {
                            var10 = var14;
                        } else if (!var10.equals(var14)) {
                            var8 = true;
                        }

                        var9.addElement((new Integer(var13)).toString());
                    }

                    if (!var8) {
                        var5.addElement(var9);
                    }
                }
            }

            return var5;
        }
    }

    public Vector newSpecifications(Vector concept_list, Vector parameters, Theory theory, Vector new_functions) {
        Concept var5 = (Concept) concept_list.elementAt(0);
        Vector var6 = (Vector)var5.specifications.clone();
        Relation var7 = new Relation();
        String var8 = "a";
        String var9 = "@a@";
        String[] var10 = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        Vector var11 = new Vector();
        var11.addElement("0");

        for(int var12 = 1; var12 < parameters.size(); ++var12) {
            var8 = var8 + var10[var12];
            var9 = var9 + "=@" + var10[var12] + "@";
            var11.addElement(Integer.toString(var12));
        }

        var7.addDefinition(var8, var9, "ascii");
        var7.name = "equal" + parameters.toString();
        Specification var13 = new Specification();
        var13.addRelation(var11, var7);
        var13.permutation = (Vector) parameters.clone();
        var6.addElement(var13);
        return var6;
    }

    public Datatable transformTable(Vector old_datatables, Vector old_concepts, Vector parameters, Vector all_concepts) {
        Datatable var5 = new Datatable();
        Datatable var6 = (Datatable) old_datatables.elementAt(0);

        for(int var7 = 0; var7 < var6.size(); ++var7) {
            Row var8 = (Row)var6.elementAt(var7);
            Tuples var9 = new Tuples();

            for(int var10 = 0; var10 < var8.tuples.size(); ++var10) {
                boolean var11 = true;
                Vector var12 = (Vector)var8.tuples.elementAt(var10);
                String var13 = "";

                for(int var14 = 0; var14 < parameters.size() && var11; ++var14) {
                    String var15 = (String) parameters.elementAt(var14);
                    String var16 = "";
                    if (var15.equals("0")) {
                        var16 = var8.entity;
                    } else {
                        try {
                            int var17 = new Integer(var15) - 1;
                            var16 = (String) var12.elementAt(var17);
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }

                    if (var14 == 0) {
                        var13 = var16;
                    }

                    if (var14 > 0 && !var16.equals(var13)) {
                        var11 = false;
                    }
                }

                if (var11) {
                    var9.addElement(var12);
                }
            }

            var5.addElement(new Row(var8.entity, var9));
        }

        return var5;
    }

    public Vector transformTypes(Vector old_concepts, Vector parameters) {
        Vector var3 = (Vector)((Concept) old_concepts.elementAt(0)).types.clone();
        return var3;
    }

    public int patternScore(Vector concept_list, Vector all_concepts, Vector entity_list, Vector non_entity_list) {
        byte var5 = 0;
        return var5;
    }
}
