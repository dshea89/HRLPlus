package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Examples extends ProductionRule implements Serializable {
    public Vector extracted_split_values_from = new Vector();
    public Vector empirical_allowed_types = new Vector();
    public int tuple_size_limit = 1;
    public Vector fixed_allowed_values = new Vector();
    public Vector allowed_values = new Vector();
    public Vector special_parameterisations = new Vector();

    public Examples() {
    }

    public boolean isBinary() {
        return false;
    }

    public boolean isCumulative() {
        return false;
    }

    public String getName() {
        return "examples";
    }

    private Vector allParametersForTypes(Vector var1, int var2, int var3) {
        boolean var4 = false;
        int var5 = 0;
        String var6 = var1.toString();

        Vector var7;
        Vector var9;
        Vector var11;
        Vector var12;
        for(var7 = new Vector(); !var4 && var5 < this.fixed_allowed_values.size(); ++var5) {
            String var8 = (String)((Vector)this.fixed_allowed_values.elementAt(var5)).elementAt(0);
            if (var8.equals(var6)) {
                var4 = true;
                var9 = (Vector)((Vector)this.fixed_allowed_values.elementAt(var5)).elementAt(1);

                for(int var10 = 0; var10 < var9.size(); ++var10) {
                    var11 = (Vector)var9.elementAt(var10);
                    var12 = new Vector();
                    var12.addElement((Vector)((Vector)var11.elementAt(0)).clone());
                    var12.addElement((Vector)((Vector)var11.elementAt(1)).clone());
                    var7.addElement(var12);
                }
            }
        }

        if (var4) {
            return var7;
        } else {
            Vector var17 = new Vector();
            var17.addElement(var1.toString());
            var9 = new Vector();
            Vector var18 = super.allColumnTuples(var1.size());

            Vector var20;
            for(var5 = 0; var5 < var18.size(); ++var5) {
                var11 = new Vector();
                var12 = (Vector)var18.elementAt(var5);
                if (var12.size() <= this.tuple_size_limit) {
                    for(int var13 = 0; var13 < var12.size(); ++var13) {
                        String var14 = (String)var12.elementAt(var13);
                        int var15 = new Integer(var14);
                        String var16 = (String)var1.elementAt(var15);
                        var11.addElement(var16);
                    }

                    var20 = this.allParametersGivenTypes(var11);

                    for(int var21 = 0; var21 < var20.size(); ++var21) {
                        Vector var22 = (Vector)var20.elementAt(var21);
                        Vector var23 = new Vector();
                        var23.addElement(var12);
                        var23.addElement(var22);
                        var9.addElement(var23);
                    }
                }
            }

            var7 = new Vector();

            for(int var19 = 0; var19 < var9.size(); ++var19) {
                var12 = (Vector)var9.elementAt(var19);
                var20 = new Vector();
                var20.addElement((Vector)((Vector)var12.elementAt(0)).clone());
                var20.addElement((Vector)((Vector)var12.elementAt(1)).clone());
                var7.addElement(var20);
            }

            var17.addElement(var9);
            this.fixed_allowed_values.addElement(var17);
            return var7;
        }
    }

    private Vector allParametersGivenTypes(Vector var1) {
        Vector var2 = new Vector();
        String var3 = (String)var1.elementAt(0);
        Vector var4 = this.getSplitValuesForType(var3);
        Vector var5 = new Vector();

        int var6;
        String var7;
        Vector var8;
        for(var6 = 0; var6 < var4.size(); ++var6) {
            var7 = (String)var4.elementAt(var6);
            var8 = new Vector();
            var8.addElement(var7);
            var5.addElement(var8);
        }

        for(var6 = 1; var6 < var1.size(); ++var6) {
            var7 = (String)var1.elementAt(var6);
            var2.addElement(this.getSplitValuesForType(var7));
        }

        for(var6 = 1; var6 < var1.size(); ++var6) {
            Vector var13 = new Vector();
            var8 = (Vector)var2.elementAt(var6 - 1);

            for(int var9 = 0; var9 < var5.size(); ++var9) {
                Vector var10 = (Vector)var5.elementAt(var9);

                for(int var11 = 0; var11 < var8.size(); ++var11) {
                    Vector var12 = (Vector)var10.clone();
                    var12.addElement((String)var8.elementAt(var11));
                    var13.addElement(var12);
                }
            }

            var5 = var13;
        }

        return var5;
    }

    private Vector getSplitValuesForType(String var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < this.allowed_values.size(); ++var3) {
            String var4 = (String)this.allowed_values.elementAt(var3);
            if (var4.indexOf(var1 + ":") > -1) {
                var2.addElement(var4.substring(var4.lastIndexOf(":") + 1, var4.length()));
            }
        }

        return var2;
    }

    public Vector allParameters(Vector var1, Theory var2) {
        Concept var3 = (Concept)var1.elementAt(0);
        Vector var4 = var3.types;
        if (!this.extracted_split_values_from.contains(var3.id)) {
            this.extracted_split_values_from.addElement(var3.id);

            for(int var5 = 0; var5 < var4.size(); ++var5) {
                String var6 = (String)var4.elementAt(var5);
                if (this.empirical_allowed_types.contains(var6)) {
                    for(int var7 = 0; var7 < var3.datatable.size(); ++var7) {
                        Row var8 = (Row)var3.datatable.elementAt(var7);
                        if (var5 == 0 && !this.allowed_values.contains(var6 + ":" + var8.entity)) {
                            this.allowed_values.addElement(var6 + ":" + var8.entity);
                        }

                        if (var5 > 0) {
                            for(int var9 = 0; var9 < var8.tuples.size(); ++var9) {
                                Vector var10 = (Vector)var8.tuples.elementAt(var9);
                                String var11 = (String)var10.elementAt(var5 - 1);
                                if (!this.allowed_values.contains(var6 + ":" + var11)) {
                                    this.allowed_values.addElement(var6 + ":" + var11);
                                }
                            }
                        }
                    }
                }
            }
        }

        Vector var12 = this.allParametersForTypes(var4, var3.arity, this.arity_limit);

        Vector var15;
        for(int var13 = 0; var13 < this.special_parameterisations.size(); ++var13) {
            var15 = (Vector)this.special_parameterisations.elementAt(var13);
            if (((String)var15.elementAt(0)).equals(var3.id)) {
                var12.addElement(var15.elementAt(1));
            }
        }

        Vector var14 = new Vector();
        var15 = new Vector();

        for(int var16 = 0; var16 < var12.size(); ++var16) {
            Vector var17 = (Vector)var12.elementAt(var16);
            if (var3.arity - ((Vector)var17.elementAt(0)).size() <= this.arity_limit && !var15.contains(var17.toString())) {
                var14.addElement(var17);
                var15.addElement(var17.toString());
            }
        }

        return var14;
    }

    public Vector newSpecifications(Vector var1, Vector var2, Theory var3, Vector var4) {
        Vector var5 = new Vector();
        Vector var6 = ((Concept)var1.elementAt(0)).specifications;
        Concept var7 = (Concept)var1.elementAt(0);
        Vector var8 = var7.types;
        Vector var9 = new Vector();
        Vector var10 = new Vector();
        Vector var11 = (Vector)var2.elementAt(0);
        Vector var12 = (Vector)var2.elementAt(1);
        Specification var14;
        Vector var39;
        if (var11.size() == 1 && var11.elementAt(0).equals("0")) {
            var5 = (Vector)var6.clone();
            String var31 = (String)var12.elementAt(0);
            var14 = new Specification();
            Vector var37 = new Vector();
            var37.addElement("0");
            Relation var38 = new Relation();
            var38.addDefinition("a", "@a@=" + var31, "ascii");
            var38.name = var7.id + "=" + var31;
            var14.relations.addElement(var38);
            var14.permutation = var37;
            var39 = new Vector();
            var39.addElement("0");
            Vector var46 = new Vector();
            var46.addElement(":" + var31 + ":");
            Function var51 = new Function("isinstantiated", var39, var46);
            var14.functions.addElement(var51);
            var14.is_single_entity = true;
            var5.addElement(var14);
            return var5;
        } else {
            int var13;
            Specification var16;
            int var17;
            int var19;
            int var20;
            String var42;
            for(var13 = 0; var13 < var6.size(); ++var13) {
                var14 = (Specification)var6.elementAt(var13);
                boolean var15 = var14.involvesColumns(var11);
                int var18;
                if (!var15) {
                    var16 = var14.copy();
                    var16.permutation = new Vector();
                    var17 = 0;

                    while(true) {
                        if (var17 >= var14.permutation.size()) {
                            var5.addElement(var16);
                            break;
                        }

                        var18 = 0;
                        var19 = new Integer((String)var14.permutation.elementAt(var17));

                        for(var20 = 0; var20 < var11.size(); ++var20) {
                            int var21 = new Integer((String)var11.elementAt(var20));
                            if (var21 < var19) {
                                ++var18;
                            }
                        }

                        var16.permutation.addElement(Integer.toString(var19 - var18));
                        ++var17;
                    }
                }

                if (var15) {
                    var16 = new Specification();
                    var16.type = "split";
                    var16.multiple_variable_columns = (Vector)var11.clone();
                    var16.fixed_values = (Vector)var12.clone();

                    for(var17 = 0; var17 < var11.size(); ++var17) {
                        var16.multiple_types.addElement(var8.elementAt(new Integer((String)var11.elementAt(var17))));
                    }

                    var16.previous_specifications.addElement(var14);
                    var39 = var14.permutation;

                    for(var18 = 0; var18 < var39.size(); ++var18) {
                        var42 = (String)var39.elementAt(var18);
                        if (!var9.contains(var42)) {
                            var9.addElement(var42);
                        }
                    }

                    var10.addElement(var16);
                    var5.addElement(var16);
                }
            }

            int var34;
            for(var13 = 0; var13 < var10.size(); ++var13) {
                var14 = (Specification)var10.elementAt(var13);
                var34 = 0;

                for(int var35 = 0; var35 < var8.size(); ++var35) {
                    String var40 = Integer.toString(var35);
                    if (var9.contains(var40) && !var11.contains(var40)) {
                        var14.permutation.addElement(Integer.toString(var34));
                        ++var34;
                    }

                    if (!var11.contains(var40) && !var9.contains(var40)) {
                        var14.redundant_columns.addElement(Integer.toString(var34));
                        ++var34;
                    }
                }
            }

            Vector var30 = new Vector();
            boolean var32 = false;

            int var23;
            String var24;
            int var33;
            String var52;
            for(var34 = 0; var34 < var10.size(); ++var34) {
                var16 = (Specification)var10.elementAt(var34);

                for(var17 = 0; var17 < var16.previous_specifications.size(); ++var17) {
                    Specification var41 = (Specification)var16.previous_specifications.elementAt(var17);
                    Vector var45 = new Vector();

                    for(var20 = 0; var20 < var41.functions.size(); ++var20) {
                        Vector var48 = new Vector();
                        Function var22 = ((Function)var41.functions.elementAt(var17)).copy();

                        String var25;
                        int var26;
                        String var27;
                        for(var23 = 0; var23 < var22.input_columns.size(); ++var23) {
                            var24 = (String)var22.input_columns.elementAt(var23);
                            var25 = (String)var22.original_input_columns.elementAt(var23);
                            if (!var24.substring(0, 1).equals(":")) {
                                var26 = var11.indexOf(var24);
                                if (var26 >= 0) {
                                    var27 = ":" + (String)var12.elementAt(var26) + ":";
                                    var22.input_columns.setElementAt(var27, var23);
                                    var48.addElement(var25);
                                }
                            }
                        }

                        for(var23 = 0; var23 < var22.output_columns.size(); ++var23) {
                            var24 = (String)var22.output_columns.elementAt(var23);
                            var25 = (String)var22.original_output_columns.elementAt(var23);
                            if (!var24.substring(0, 1).equals(":")) {
                                var26 = var11.indexOf(var24);
                                if (var26 >= 0) {
                                    var27 = ":" + (String)var12.elementAt(var26) + ":";
                                    var22.output_columns.setElementAt(var27, var23);
                                    var48.addElement(var25);
                                }
                            }
                        }

                        String var28;
                        int var29;
                        int var54;
                        for(var23 = 0; var23 < var22.input_columns.size(); ++var23) {
                            var24 = (String)var22.input_columns.elementAt(var23);
                            var25 = (String)var22.original_input_columns.elementAt(var23);
                            if (!var24.substring(0, 1).equals(":")) {
                                var26 = new Integer(var25);
                                var33 = 0;

                                for(var54 = 0; var54 < var48.size(); ++var54) {
                                    var28 = (String)var48.elementAt(var54);
                                    var29 = new Integer(var28);
                                    if (var29 < var26) {
                                        ++var33;
                                    }
                                }

                                var27 = Integer.toString(new Integer(var24) - var33);
                                var22.input_columns.setElementAt(var27, var23);
                            }
                        }

                        for(var23 = 0; var23 < var22.output_columns.size(); ++var23) {
                            var24 = (String)var22.output_columns.elementAt(var23);
                            var25 = (String)var22.original_output_columns.elementAt(var23);
                            if (!var24.substring(0, 1).equals(":")) {
                                var26 = new Integer(var25);
                                var33 = 0;

                                for(var54 = 0; var54 < var48.size(); ++var54) {
                                    var28 = (String)var48.elementAt(var54);
                                    var29 = new Integer(var28);
                                    if (var29 < var26) {
                                        ++var33;
                                    }
                                }

                                var27 = Integer.toString(new Integer(var24) - var33);
                                var22.output_columns.setElementAt(var27, var23);
                            }
                        }

                        var52 = var22.writeFunction();
                        if (!var45.contains(var52)) {
                            var16.functions.addElement(var22);
                            var45.addElement(var52);
                        }

                        if (!var30.contains(var52)) {
                            var4.addElement(var22);
                            var30.addElement(var52);
                        }
                    }
                }
            }

            var32 = false;

            for(var34 = 0; var34 < var7.functions.size(); ++var34) {
                Vector var36 = new Vector();
                Function var43 = (Function)var7.functions.elementAt(var34);
                Function var44 = var43.copy();

                String var47;
                String var49;
                int var50;
                for(var19 = 0; var19 < var44.input_columns.size(); ++var19) {
                    var47 = (String)var44.input_columns.elementAt(var19);
                    var49 = (String)var44.original_input_columns.elementAt(var19);
                    if (!var47.substring(0, 1).equals(":")) {
                        var50 = var11.indexOf(var47);
                        if (var50 >= 0) {
                            var52 = ":" + (String)var12.elementAt(var50) + ":";
                            var44.input_columns.setElementAt(var52, var19);
                            var36.addElement(var49);
                        }
                    }
                }

                for(var19 = 0; var19 < var44.output_columns.size(); ++var19) {
                    var47 = (String)var44.output_columns.elementAt(var19);
                    var49 = (String)var44.original_output_columns.elementAt(var19);
                    if (!var47.substring(0, 1).equals(":")) {
                        var50 = var11.indexOf(var47);
                        if (var50 >= 0) {
                            var52 = ":" + (String)var12.elementAt(var50) + ":";
                            var44.output_columns.setElementAt(var52, var19);
                            var36.addElement(var49);
                        }
                    }
                }

                int var53;
                for(var19 = 0; var19 < var44.input_columns.size(); ++var19) {
                    var47 = (String)var44.input_columns.elementAt(var19);
                    var49 = (String)var44.original_input_columns.elementAt(var19);
                    if (!var47.substring(0, 1).equals(":")) {
                        var50 = new Integer(var49);
                        var33 = 0;

                        for(var23 = 0; var23 < var36.size(); ++var23) {
                            var24 = (String)var36.elementAt(var23);
                            var53 = new Integer(var24);
                            if (var53 < var50) {
                                ++var33;
                            }
                        }

                        var52 = Integer.toString(new Integer(var47) - var33);
                        var44.input_columns.setElementAt(var52, var19);
                    }
                }

                for(var19 = 0; var19 < var44.output_columns.size(); ++var19) {
                    var47 = (String)var44.output_columns.elementAt(var19);
                    var49 = (String)var44.original_output_columns.elementAt(var19);
                    if (!var47.substring(0, 1).equals(":")) {
                        var50 = new Integer(var49);
                        var33 = 0;

                        for(var23 = 0; var23 < var36.size(); ++var23) {
                            var24 = (String)var36.elementAt(var23);
                            var53 = new Integer(var24);
                            if (var53 < var50) {
                                ++var33;
                            }
                        }

                        var52 = Integer.toString(new Integer(var47) - var33);
                        var44.output_columns.setElementAt(var52, var19);
                    }
                }

                var42 = var44.writeFunction();
                if (!var30.contains(var42)) {
                    var4.addElement(var44);
                    var30.addElement(var42);
                }
            }

            return var5;
        }
    }

    public Datatable transformTable(Vector var1, Vector var2, Vector var3, Vector var4) {
        Vector var5 = (Vector)var3.elementAt(0);
        Vector var6 = (Vector)var3.elementAt(1);
        Datatable var7 = (Datatable)var1.elementAt(0);
        Datatable var8 = new Datatable();
        int var10;
        Row var18;
        if (var5.size() == 1 && var5.elementAt(0).equals("0")) {
            String var17 = (String)var6.elementAt(0);

            for(var10 = 0; var10 < var7.size(); ++var10) {
                var18 = (Row)var7.elementAt(var10);
                Row var19 = new Row(var18.entity, new Tuples());
                if (var18.entity.equals(var17)) {
                    var19.tuples.addElement(new Vector());
                }

                var8.addElement(var19);
            }

            return var8;
        } else {
            Vector var9 = new Vector();

            for(var10 = 0; var10 < var5.size(); ++var10) {
                int var11 = new Integer((String)var5.elementAt(var10)) - 1;
                var9.addElement(Integer.toString(var11));
            }

            for(var10 = 0; var10 < var7.size(); ++var10) {
                var18 = (Row)var7.elementAt(var10);
                Tuples var12 = var18.tuples;
                Tuples var13 = new Tuples();

                for(int var14 = 0; var14 < var12.size(); ++var14) {
                    Vector var15 = (Vector)((Vector)var12.elementAt(var14)).clone();

                    int var16;
                    for(var16 = 0; var16 < var5.size() && ((String)var15.elementAt(new Integer((String)var5.elementAt(var16)) - 1)).equals((String)var6.elementAt(var16)); ++var16) {
                        ;
                    }

                    if (var16 == var5.size()) {
                        var13.addElement(super.removeColumns(var15, var9));
                    }
                }

                Row var20 = new Row(var18.entity, var13);
                var8.addElement(var20);
            }

            return var8;
        }
    }

    public Vector transformTypes(Vector var1, Vector var2) {
        Concept var3 = (Concept)var1.elementAt(0);
        Vector var4 = (Vector)var2.elementAt(0);
        Vector var5 = (Vector)var2.elementAt(1);
        if (var4.size() == 1 && var4.elementAt(0).equals("0")) {
            return var3.types;
        } else {
            Vector var6 = (Vector)var3.types.clone();
            return super.removeColumns(var6, var4);
        }
    }

    public int patternScore(Vector var1, Vector var2, Vector var3, Vector var4) {
        return this.patternScore2(var1, var2, var4, var3) == var3.size() ? var4.size() : this.patternScore2(var1, var2, var3, var4);
    }

    private int patternScore2(Vector var1, Vector var2, Vector var3, Vector var4) {
        if (var3.size() == 0) {
            return 0;
        } else {
            Concept var5 = (Concept)var1.elementAt(0);
            if (var5.arity == 1) {
                return 0;
            } else {
                int var6 = 0;
                Vector var7 = super.allColumnTuples(var5.arity);

                for(int var8 = 0; var8 < var7.size() && var6 != var4.size(); ++var8) {
                    Vector var9 = (Vector)var7.elementAt(var8);
                    Vector var10 = new Vector();

                    Vector var16;
                    String var18;
                    for(int var11 = 0; var11 < var4.size(); ++var11) {
                        String var12 = (String)var4.elementAt(var11);
                        Row var13 = var5.calculateRow(var2, var12);
                        Tuples var14 = var13.tuples;

                        for(int var15 = 0; var15 < var14.size(); ++var15) {
                            var16 = (Vector)((Vector)var14.elementAt(var15)).clone();
                            if (this.passesNonEmpiricalTest(var16, var5.types)) {
                                var16.insertElementAt(var12, 0);
                                Vector var17 = super.keepColumns(var16, var9);
                                var18 = var17.toString();
                                if (!var10.contains(var18)) {
                                    var10.addElement(var18);
                                }
                            }
                        }
                    }

                    String var26 = (String)var3.elementAt(0);
                    Row var27 = var5.calculateRow(var2, var26);
                    Tuples var28 = var27.tuples;
                    Vector var29 = new Vector();
                    Vector var30 = new Vector();
                    var16 = new Vector();

                    int var31;
                    Vector var32;
                    for(var31 = 0; var31 < var28.size(); ++var31) {
                        var32 = (Vector)((Vector)var28.elementAt(var31)).clone();
                        if (this.passesNonEmpiricalTest(var32, var5.types)) {
                            var32.insertElementAt(var26, 0);
                            Vector var19 = super.keepColumns(var32, var9);
                            String var20 = var19.toString();
                            if (!var29.contains(var20) && !var10.contains(var20)) {
                                var16.addElement(var20);
                                var29.addElement(var20);
                                var30.addElement(var19);
                            }
                        }
                    }

                    for(var31 = 1; var31 < var3.size() && !var29.isEmpty(); ++var31) {
                        var32 = new Vector();
                        String var33 = (String)var3.elementAt(var31);
                        Row var35 = var5.calculateRow(var2, var33);
                        Tuples var21 = var35.tuples;

                        int var22;
                        for(var22 = 0; var22 < var21.size(); ++var22) {
                            Vector var23 = (Vector)((Vector)var21.elementAt(var22)).clone();
                            var23.insertElementAt(var33, 0);
                            Vector var24 = super.keepColumns(var23, var9);
                            String var25 = var24.toString();
                            if (!var32.contains(var25)) {
                                var32.addElement(var25);
                            }
                        }

                        var22 = 0;

                        while(var22 < var29.size()) {
                            String var39 = (String)var29.elementAt(var22);
                            if (!var32.contains(var39)) {
                                var29.removeElementAt(var22);
                                var30.removeElementAt(var22);
                                var16.removeElementAt(var22);
                            } else {
                                ++var22;
                            }
                        }
                    }

                    if (!var29.isEmpty()) {
                        var6 = var4.size();
                        var18 = (String)var29.elementAt(0);
                        int var34 = var16.indexOf(var18);
                        Vector var36 = (Vector)var30.elementAt(var34);
                        Vector var37 = new Vector();
                        var37.addElement(var9);
                        var37.addElement(var36);
                        Vector var38 = new Vector();
                        var38.addElement(var5.id);
                        var38.addElement(var37);
                        this.special_parameterisations.addElement(var38);
                    }
                }

                return var6;
            }
        }
    }

    private boolean passesNonEmpiricalTest(Vector var1, Vector var2) {
        boolean var3 = true;

        for(int var4 = 0; var4 < var1.size() && var3; ++var4) {
            String var5 = (String)var2.elementAt(var4 + 1);
            if (!this.empirical_allowed_types.contains(var5) && !this.allowed_values.contains(var5 + (String)var1.elementAt(var4))) {
                var3 = false;
            }
        }

        return var3;
    }
}
