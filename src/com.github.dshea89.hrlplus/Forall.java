package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

/**
 * A class representing the forall production rule. This production rule takes an old datatable as input and a set of parameterisations.
 * The parameterisation is a list of columns which are to be #removed# in the output datatable.
 */
public class Forall extends ProductionRule implements Serializable {
    /**
     * A compose rule which will be used for this production rule.
     */
    public Compose compose = new Compose();

    /**
     * An exists rule which will be used for this production rule.
     */
    public Exists exists = new Exists();

    /**
     * Whether or not this produces cumulative concepts.
     */
    public boolean is_cumulative = false;

    public Forall() {
    }

    /**
     * Returns false as this is a unary production rule.
     */
    public boolean isBinary() {
        return true;
    }

    /**
     * Returns "forall" as that is the name of this production rule.
     */
    public String getName() {
        return "forall";
    }

    /**
     * Given a vector of two concepts, this will return all the parameterisations for this concept.
     */
    public Vector allParameters(Vector old_concepts, Theory theory) {
        Vector var3 = new Vector();
        Concept var4 = (Concept) old_concepts.elementAt(0);
        Concept var5 = (Concept) old_concepts.elementAt(1);
        Vector var6 = new Vector();

        int var7;
        Concept var8;
        for(var7 = 0; var7 < var4.generalisations.size(); ++var7) {
            var8 = (Concept)var4.generalisations.elementAt(var7);
            var6.addElement(var8);
        }

        for(var7 = 0; var7 < var5.generalisations.size(); ++var7) {
            var8 = (Concept)var5.generalisations.elementAt(var7);
            if (!var6.contains(var8)) {
                var6.addElement(var8);
            }
        }

        if (!var6.contains(var4)) {
            var6.addElement(var4);
        }

        if (!var6.contains(var5)) {
            var6.addElement(var5);
        }

        Vector var10;
        for(var7 = 0; var7 < var6.size(); ++var7) {
            var8 = (Concept)var6.elementAt(var7);
            if (var8.arity <= this.arity_limit) {
                Vector var9 = new Vector();
                var9.addElement(var4);
                var9.addElement(var8);
                var10 = new Vector();
                var10.addElement(var5);
                var10.addElement(var8);
                boolean var11 = this.compose.subobject_overlap;
                this.compose.subobject_overlap = false;
                Vector var12 = this.compose.allParameters(var9, theory);
                Vector var13 = this.compose.allParameters(var10, theory);
                this.compose.subobject_overlap = var11;

                for(int var14 = 0; var14 < var12.size(); ++var14) {
                    Vector var15 = (Vector)var12.elementAt(var14);
                    Vector var16 = this.compose.transformTypes(var9, var15);

                    for(int var17 = 0; var17 < var13.size(); ++var17) {
                        Vector var18 = (Vector)var13.elementAt(var17);
                        Vector var19 = this.compose.transformTypes(var10, var18);
                        if (var16.toString().equals(var19.toString()) && var8.arity < var15.size() && ((String)var15.elementAt(0)).equals("1") && ((String)var18.elementAt(0)).equals("1")) {
                            Vector var20 = new Vector();
                            var20.addElement(var8);
                            var20.addElement(var15);
                            var20.addElement(var18);
                            var3.addElement(var20);
                            Vector var21 = new Vector();
                            var21.addElement(var8);
                            var21.addElement(var15);
                            var21.addElement(var18);
                            var21.addElement("s");
                            var3.addElement(var21);
                        }
                    }
                }
            }
        }

        Vector var22 = new Vector();
        Vector var23 = new Vector();

        for(int var24 = 0; var24 < var3.size(); ++var24) {
            var10 = (Vector)var3.elementAt(var24);
            this.addParametersIfOK(old_concepts, theory, var10, var22, var23);
        }

        return var22;
    }

    private void addParametersIfOK(Vector var1, Theory var2, Vector var3, Vector var4, Vector var5) {
        Vector var6 = this.newSpecifications(var1, var3, var2, new Vector());
        Specification var7 = (Specification)var6.lastElement();

        for(int var8 = var7.rh_starts; var8 < var7.previous_specifications.size(); ++var8) {
            Specification var9 = (Specification)var7.previous_specifications.elementAt(var8);
            boolean var10 = false;

            for(int var11 = 0; var11 < var7.rh_starts && !var10; ++var11) {
                Specification var12 = (Specification)var7.previous_specifications.elementAt(var11);
                if (var12.equals(var9)) {
                    var10 = true;
                }
            }

            if (!var10 && !this.repeatedSpecifications(var5, var6)) {
                var5.addElement(var6);
                var4.addElement(var3);
                break;
            }
        }

    }

    private boolean repeatedSpecifications(Vector var1, Vector var2) {
        boolean var3 = false;

        for(int var4 = 0; var4 < var1.size() && !var3; ++var4) {
            Vector var5 = (Vector)var1.elementAt(var4);
            if (var5.size() == var2.size()) {
                boolean var6 = true;

                for(int var7 = 0; var7 < var5.size() && var6; ++var7) {
                    Specification var8 = (Specification)var5.elementAt(var7);
                    boolean var9 = false;

                    for(int var10 = 0; var10 < var2.size(); ++var10) {
                        Specification var11 = (Specification)var2.elementAt(var10);
                        if (var11.equals(var8)) {
                            var9 = true;
                        }
                    }

                    if (!var9) {
                        var6 = false;
                    }
                }

                if (var6) {
                    var3 = true;
                }
            }
        }

        return var3;
    }

    /**
     * This produces the new specifications for concepts output using the forall production rule.
     */
    public Vector newSpecifications(Vector input_concepts, Vector input_parameters, Theory theory, Vector new_functions) {
        Vector var5 = new Vector();
        Concept var6 = (Concept) input_parameters.elementAt(0);
        Concept var7 = (Concept) input_concepts.elementAt(0);
        Concept var8 = (Concept) input_concepts.elementAt(1);
        Vector var9 = (Vector) input_parameters.elementAt(1);
        Vector var10 = (Vector) input_parameters.elementAt(2);
        if (input_parameters.size() == 4) {
            var7 = (Concept) input_concepts.elementAt(1);
            var8 = (Concept) input_concepts.elementAt(0);
            var9 = (Vector) input_parameters.elementAt(2);
            var10 = (Vector) input_parameters.elementAt(1);
        }

        Vector var11 = new Vector();
        Vector var12 = new Vector();
        var11.addElement(var7);
        var11.addElement(var6);
        var12.addElement(var8);
        var12.addElement(var6);
        Vector var13 = this.compose.newSpecifications(var11, var9, theory, new Vector());
        Vector var14 = this.compose.newSpecifications(var12, var10, theory, new Vector());
        new Vector();
        Specification var16 = new Specification();
        var16.type = "forall";
        Vector var17 = new Vector();
        Vector var18 = this.compose.transformTypes(var11, var9);
        Vector var19 = new Vector();

        int var20;
        for(var20 = 0; var20 < var9.size(); ++var20) {
            String var21 = (String)var9.elementAt(var20);
            if (var21.equals("0")) {
                var19.addElement(Integer.toString(var20));
                var16.multiple_variable_columns.addElement(Integer.toString(var20));
                var16.multiple_types.addElement(var18.elementAt(var20));
            }
        }

        int var22;
        String var23;
        int var24;
        int var25;
        int var26;
        int var27;
        Specification var28;
        Specification var30;
        int var31;
        for(var20 = 0; var20 < var13.size(); ++var20) {
            var28 = (Specification)var13.elementAt(var20);
            if (!var28.involvesColumns(var19)) {
                var30 = var28.copy();
                var30.permutation = new Vector();

                for(var31 = 0; var31 < var28.permutation.size(); ++var31) {
                    var24 = 0;
                    var25 = new Integer((String)var28.permutation.elementAt(var31));

                    for(var26 = 0; var26 < var19.size(); ++var26) {
                        var27 = new Integer((String)var19.elementAt(var26));
                        if (var27 < var25) {
                            ++var24;
                        }
                    }

                    var30.permutation.addElement(Integer.toString(var25 - var24));
                }

                var5.addElement(var30);
            } else {
                var16.previous_specifications.addElement(var28);

                for(var22 = 0; var22 < var28.permutation.size(); ++var22) {
                    var23 = (String)var28.permutation.elementAt(var22);
                    if (!var17.contains(var23)) {
                        var17.addElement(var23);
                    }
                }

                ++var16.rh_starts;
            }
        }

        for(var20 = 0; var20 < var14.size(); ++var20) {
            var28 = (Specification)var14.elementAt(var20);
            if (var28.involvesColumns(var19)) {
                var16.previous_specifications.addElement(var28);

                for(var22 = 0; var22 < var28.permutation.size(); ++var22) {
                    var23 = (String)var28.permutation.elementAt(var22);
                    if (!var17.contains(var23)) {
                        var17.addElement(var23);
                    }
                }
            } else {
                var30 = var28.copy();
                var30.permutation = new Vector();

                for(var31 = 0; var31 < var28.permutation.size(); ++var31) {
                    var24 = 0;
                    var25 = new Integer((String)var28.permutation.elementAt(var31));

                    for(var26 = 0; var26 < var19.size(); ++var26) {
                        var27 = new Integer((String)var19.elementAt(var26));
                        if (var27 < var25) {
                            ++var24;
                        }
                    }

                    var30.permutation.addElement(Integer.toString(var25 - var24));
                }

                boolean var33 = false;

                for(var24 = 0; var24 < var5.size() && !var33; ++var24) {
                    Specification var34 = (Specification)var5.elementAt(var24);
                    if (var34.equals(var30)) {
                        var33 = true;
                    }
                }

                if (!var33) {
                    var5.addElement(var30);
                }
            }
        }

        var20 = 0;

        for(int var29 = 0; var29 < var18.size(); ++var29) {
            String var32 = Integer.toString(var29);
            if (var17.contains(var32) && !var19.contains(var32)) {
                var16.permutation.addElement(Integer.toString(var20));
                ++var20;
            }

            if (!var19.contains(var32) && !var17.contains(var32)) {
                var16.redundant_columns.addElement(Integer.toString(var20));
                ++var20;
            }
        }

        var5.addElement(var16);
        return var5;
    }

    /**
     * This produces the new datatable from the given datatable, using the parameters specified.
     */
    public Datatable transformTable(Vector input_datatables, Vector input_concepts, Vector input_parameters, Vector all_concepts) {
        Datatable var5 = new Datatable();
        Concept var6 = (Concept) input_parameters.elementAt(0);
        Concept var7 = (Concept) input_concepts.elementAt(0);
        Concept var8 = (Concept) input_concepts.elementAt(1);
        Vector var9 = (Vector) input_parameters.elementAt(1);
        Vector var10 = (Vector) input_parameters.elementAt(2);
        Datatable var11 = (Datatable) input_datatables.elementAt(0);
        Datatable var12 = (Datatable) input_datatables.elementAt(1);
        if (input_parameters.size() == 4) {
            var7 = (Concept) input_concepts.elementAt(1);
            var8 = (Concept) input_concepts.elementAt(0);
            var9 = (Vector) input_parameters.elementAt(2);
            var10 = (Vector) input_parameters.elementAt(1);
            var11 = (Datatable) input_datatables.elementAt(1);
            var12 = (Datatable) input_datatables.elementAt(0);
        }

        Vector var13 = new Vector();
        Vector var14 = new Vector();
        var13.addElement(var7);
        var13.addElement(var6);
        var14.addElement(var8);
        var14.addElement(var6);
        Vector var15 = new Vector();
        var15.addElement(var11);
        var15.addElement(var6.datatable);
        Vector var16 = new Vector();
        var16.addElement(var12);
        var16.addElement(var6.datatable);
        Datatable var17 = this.compose.transformTable(var15, var13, var9, all_concepts);
        Datatable var18 = this.compose.transformTable(var16, var14, var10, all_concepts);
        Vector var19 = new Vector();

        int var20;
        for(var20 = var6.arity; var20 < var9.size(); ++var20) {
            var19.addElement(Integer.toString(var20));
        }

        for(var20 = 0; var20 < var11.size(); ++var20) {
            Row var21 = (Row)var11.elementAt(var20);
            Row var22 = var6.calculateRow(all_concepts, var21.entity);
            Row var23 = new Row();
            var23.entity = var22.entity;

            for(int var24 = 0; var24 < var22.tuples.size(); ++var24) {
                Vector var25 = (Vector)var22.tuples.elementAt(var24);
                Tuples var26 = var17.rowWithEntity(var22.entity).tuples;
                Tuples var27 = var18.rowWithEntity(var22.entity).tuples;
                Vector var28 = this.getTuples(var26, var9, var25);
                Vector var29 = this.getTuples(var27, var10, var25);
                if (this.subsumptionOccurs(var28, var29)) {
                    var23.tuples.addElement((Vector)var25.clone());
                }
            }

            var5.addElement(var23);
        }

        return var5;
    }

    private boolean subsumptionOccurs(Vector var1, Vector var2) {
        Vector var3 = new Vector();

        int var4;
        for(var4 = 0; var4 < var2.size(); ++var4) {
            var3.addElement(((Vector)var2.elementAt(var4)).toString());
        }

        for(var4 = 0; var4 < var1.size(); ++var4) {
            Vector var5 = (Vector)var1.elementAt(var4);
            if (!var3.contains(var5.toString())) {
                return false;
            }
        }

        return true;
    }

    private Vector getTuples(Vector var1, Vector var2, Vector var3) {
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var1.size(); ++var5) {
            Vector var6 = (Vector)var1.elementAt(var5);
            Vector var7 = new Vector();
            boolean var8 = true;

            for(int var9 = 0; var9 < var6.size() && var8; ++var9) {
                String var10 = (String)var6.elementAt(var9);
                String var11 = (String)var2.elementAt(var9 + 1);
                if (!var11.equals("0")) {
                    int var12 = new Integer(var11) - 2;
                    String var13 = (String)var3.elementAt(var12);
                    if (!var10.equals(var13)) {
                        var8 = false;
                    }
                } else {
                    var7.addElement(var10);
                }
            }

            if (var8) {
                var4.addElement(var7);
            }
        }

        return var4;
    }

    /**
     * Returns the types of the objects in the columns of the new datatable.
     */
    public Vector transformTypes(Vector old_concepts, Vector parameters) {
        Concept var3 = (Concept) parameters.elementAt(0);
        return (Vector)var3.types.clone();
    }

    /**
     * This assigns a score to a concept depending on whether the production rule can see any likelihood of a pattern.
     */
    public int patternScore(Vector concept_list, Vector all_concepts, Vector entity_list, Vector non_entity_list) {
        return 0;
    }
}
