package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * This counts the number of occurrences of each entry in a column.
 */
public class Size extends ProductionRule implements Serializable {
    public Relation integer_relation = null;
    public int number_of_new_functions = 0;
    public int top_number = 10;

    public Size() {
    }

    public boolean isBinary() {
        return false;
    }

    public boolean isCumulative() {
        return false;
    }

    public String getName() {
        return "size";
    }

    public Vector allParameters(Vector concept_list, Theory theory) {
        Concept var3 = (Concept) concept_list.elementAt(0);
        Vector var4 = super.allColumnTuples(var3.arity);
        Vector var5 = new Vector();

        for(int var6 = 0; var6 < var4.size(); ++var6) {
            Vector var7 = (Vector)var4.elementAt(var6);
            boolean var8 = false;

            for(int var9 = 0; !var8 && var9 < var3.functions.size(); ++var9) {
                Function var10 = (Function)var3.functions.elementAt(var9);
                if (var10.output_columns.toString().equals(var7.toString())) {
                    var8 = true;
                }
            }

            if (var3.arity - var7.size() < this.arity_limit && !var8) {
                var5.addElement(var7);
            }
        }

        return var5;
    }

    public Vector newSpecifications(Vector concept_list, Vector parameters, Theory theory, Vector new_functions) {
        Vector var5 = new Vector();
        Concept var6 = (Concept) concept_list.elementAt(0);
        Vector var7 = var6.specifications;
        Vector var8 = ((Concept) concept_list.elementAt(0)).types;
        Vector var9 = new Vector();
        Specification var10 = new Specification();
        var10.type = "size";
        var10.multiple_variable_columns = parameters;

        int var11;
        for(var11 = 0; var11 < parameters.size(); ++var11) {
            var10.multiple_types.addElement(var8.elementAt(new Integer((String) parameters.elementAt(var11))));
        }

        Specification var12;
        int var16;
        int var21;
        Vector var25;
        for(var11 = 0; var11 < var7.size(); ++var11) {
            var12 = (Specification)var7.elementAt(var11);
            boolean var13 = false;
            boolean var14 = var12.involvesColumns(parameters);
            if (!var14) {
                Specification var15 = var12.copy();
                var15.permutation = new Vector();
                var21 = 0;

                while(true) {
                    if (var21 >= var12.permutation.size()) {
                        var5.addElement(var15);
                        break;
                    }

                    var16 = 0;
                    int var17 = new Integer((String)var12.permutation.elementAt(var21));

                    for(int var18 = 0; var18 < parameters.size(); ++var18) {
                        int var19 = new Integer((String) parameters.elementAt(var18));
                        if (var19 < var17) {
                            ++var16;
                        }
                    }

                    var15.permutation.addElement(Integer.toString(var17 - var16));
                    ++var21;
                }
            }

            if (var14) {
                var10.previous_specifications.addElement(var12);
                var25 = var12.permutation;

                for(var21 = 0; var21 < var25.size(); ++var21) {
                    String var28 = (String)var25.elementAt(var21);
                    if (!var9.contains(var28)) {
                        var9.addElement(var28);
                    }
                }
            }
        }

        var11 = 0;

        for(int var20 = 0; var20 < var8.size(); ++var20) {
            String var23 = Integer.toString(var20);
            if (var9.contains(var23) && !parameters.contains(var23)) {
                var10.permutation.addElement(Integer.toString(var11));
                ++var11;
            }

            if (!parameters.contains(var23) && !var9.contains(var23)) {
                var10.redundant_columns.addElement(Integer.toString(var11));
                ++var11;
            }
        }

        var10.permutation.addElement(Integer.toString(var11));
        if (this.integer_relation == null) {
            this.integer_relation = new Relation();
            this.integer_relation.addDefinition("a", "@a@ is an integer");
        }

        var12 = new Specification("[" + var10.permutation.lastElement() + "]", this.integer_relation);
        var5.addElement(var12);
        var5.addElement(var10);

        for(var21 = 0; var21 < var6.functions.size(); ++var21) {
            Function var22 = (Function)var6.functions.elementAt(var21);
            Function var27 = var22.copy();
            if (!var27.containsAColumnFrom(parameters)) {
                var27.removeHoles(parameters);
                new_functions.addElement(var27);
            }
        }

        Vector var26 = this.transformTypes(concept_list, parameters);
        Vector var24 = new Vector();
        var25 = new Vector();

        for(var16 = 0; var16 < var26.size() - 1; ++var16) {
            var24.addElement(Integer.toString(var16));
        }

        var25.addElement(Integer.toString(var26.size() - 1));
        Function var29 = new Function("f" + Integer.toString(this.number_of_new_functions++), var24, var25);
        new_functions.addElement(var29);
        var10.functions.addElement(var29);
        return var5;
    }

    public Datatable transformTable(Vector old_datatables, Vector old_concepts, Vector parameters, Vector all_concepts) {
        Concept var5 = (Concept) old_concepts.elementAt(0);
        Datatable var6 = (Datatable) old_datatables.elementAt(0);
        Datatable var7 = new Datatable();
        Vector var8 = ((Concept) old_concepts.elementAt(0)).specifications;
        Vector var9 = new Vector();
        Vector var10 = new Vector();
        Enumeration var11 = var8.elements();

        Specification var12;
        while(var11.hasMoreElements()) {
            var12 = (Specification)var11.nextElement();
            if (!var12.involvesColumns(parameters)) {
                var10.addElement(var12);
            }
        }

        var11 = var10.elements();

        int var16;
        int var18;
        while(var11.hasMoreElements()) {
            var12 = (Specification)var11.nextElement();
            Specification var13 = var12.copy();
            var13.permutation = new Vector();

            for(int var14 = 0; var14 < var12.permutation.size(); ++var14) {
                int var15 = 0;
                var16 = new Integer((String)var12.permutation.elementAt(var14));

                for(int var17 = 0; var17 < parameters.size(); ++var17) {
                    var18 = new Integer((String) parameters.elementAt(var17));
                    if (var18 < var16) {
                        ++var15;
                    }
                }

                var13.permutation.addElement(Integer.toString(var16 - var15));
            }

            var9.addElement(var13);
        }

        Vector var33 = this.transformTypes(old_concepts, parameters);
        var33.removeElementAt(var33.size() - 1);
        String var34 = var33.toString();
        var11 = all_concepts.elements();
        boolean var35 = false;
        Concept var36 = new Concept();

        while(var11.hasMoreElements() && !var35) {
            var36 = (Concept)var11.nextElement();
            if (var34.equals(var36.types.toString()) && var36.specifications.size() == var9.size()) {
                for(var16 = 0; var16 < var9.size(); ++var16) {
                    Specification var38 = (Specification)var9.elementAt(var16);

                    for(var18 = 0; var18 < var36.specifications.size(); ++var18) {
                        Specification var19 = (Specification)var36.specifications.elementAt(var18);
                        if (var19.equals(var38)) {
                            break;
                        }
                    }

                    if (var18 == var36.specifications.size()) {
                        break;
                    }
                }

                if (var16 == var9.size()) {
                    var35 = true;
                }
            }
        }

        boolean var37 = false;
        Datatable var39 = new Datatable();
        if (!var35) {
            if (var33.size() != 2 || !((String)var33.elementAt(1)).equals("integer")) {
                return new Datatable();
            }

            for(var18 = 0; var18 < var6.size(); ++var18) {
                Row var40 = (Row)var6.elementAt(var18);
                new Row();
                Tuples var21 = new Tuples();

                for(int var22 = 0; var22 <= this.top_number; ++var22) {
                    Vector var23 = new Vector();
                    var23.addElement(Integer.toString(var22));
                    var21.addElement(var23);
                }

                var39.addElement(new Row(var40.entity, var21));
            }

            var37 = true;
        }

        Vector var41 = new Vector();

        for(int var42 = 1; var42 < var5.arity; ++var42) {
            String var20 = Integer.toString(var42);
            if (!parameters.contains(var20)) {
                var41.addElement(Integer.toString(var42 - 1));
            }
        }

        Enumeration var43 = var6.elements();

        while(var43.hasMoreElements()) {
            Row var44 = (Row)var43.nextElement();
            new Row();
            Row var45;
            if (var37) {
                var45 = var39.rowWithEntity(var44.entity);
            } else {
                var45 = var36.calculateRow(all_concepts, var44.entity);
            }

            Row var46 = new Row();
            var46.entity = var45.entity;

            for(int var47 = 0; var47 < var45.tuples.size(); ++var47) {
                Vector var24 = (Vector)var45.tuples.elementAt(var47);
                int var25 = 0;

                for(int var26 = 0; var26 < var44.tuples.size(); ++var26) {
                    Vector var27 = (Vector)var44.tuples.elementAt(var26);
                    int var28 = 0;

                    boolean var29;
                    for(var29 = true; var28 < var41.size() && var29; ++var28) {
                        String var30 = (String)var24.elementAt(var28);
                        int var31 = new Integer((String)var41.elementAt(var28));
                        String var32 = (String)var27.elementAt(var31);
                        if (!var30.equals(var32)) {
                            var29 = false;
                        }
                    }

                    if (var29) {
                        ++var25;
                    }
                }

                Vector var48 = (Vector)var24.clone();
                var48.addElement(Integer.toString(var25));
                var46.tuples.addElement(var48);
            }

            var7.addElement(var46);
        }

        return var7;
    }

    public Vector transformTypes(Vector old_concepts, Vector parameters) {
        Vector var3 = (Vector)((Concept) old_concepts.elementAt(0)).types.clone();
        Vector var4 = super.removeColumns(var3, parameters);
        var4.addElement("integer");
        return var4;
    }

    public int patternScore(Vector concept_list, Vector all_concepts, Vector entity_list, Vector non_entity_list) {
        Concept var5 = (Concept) concept_list.elementAt(0);
        int var6 = 0;
        int var7 = non_entity_list.size();
        Vector var8 = new Vector();
        Integer var9 = new Integer(0);

        int var10;
        String var11;
        Row var12;
        for(var10 = 0; var10 < entity_list.size(); ++var10) {
            var11 = (String) entity_list.elementAt(var10);
            var12 = var5.calculateRow(all_concepts, var11);
            Integer var13 = new Integer(var12.tuples.size());
            boolean var14 = false;

            Vector var16;
            for(int var15 = 0; !var14 && var15 < var8.size(); ++var15) {
                var16 = (Vector)var8.elementAt(var15);
                if (var16.contains(var13)) {
                    var16.addElement(var13);
                    if (var16.size() > var6) {
                        var6 = var16.size();
                        var9 = var13;
                    }
                }
            }

            if (!var14) {
                var16 = new Vector();
                var16.addElement(var13);
                var8.addElement(var16);
                if (1 > var6) {
                    ;
                }

                var6 = 1;
                var9 = var13;
            }

            ++var10;
        }

        for(var10 = 0; var10 < non_entity_list.size(); ++var10) {
            var11 = (String) non_entity_list.elementAt(var10);
            var12 = var5.calculateRow(all_concepts, var11);
            if (var12.tuples.size() == var9) {
                --var7;
            }
        }

        Float var17 = new Float((float)((var6 + var7) / 2));
        int var18 = var17.intValue();
        return var18;
    }
}
