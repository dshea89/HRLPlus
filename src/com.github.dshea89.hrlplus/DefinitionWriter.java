package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class DefinitionWriter implements Serializable {
    public boolean remove_existence_variables = false;
    public boolean surround_by_at_sign = false;

    public DefinitionWriter() {
    }

    public String writeDefinitionWithStartLetters(Concept var1, String var2) {
        Vector var3 = this.lettersForTypes(var1.types, var2, new Vector());
        return var2.equals("prolog") ? var3.toString().toUpperCase() + " : " + this.writeDefinition(var1, var2) : var3.toString() + " : " + this.writeDefinition(var1, var2);
    }

    public String writeDefinition(Concept var1, String var2, Vector var3) {
        String var4 = "";
        Vector var5 = (Vector)var3.clone();
        String var6 = "";
        if (var2.equals("ascii") || var2.equals("otter") || var2.equals("tptp")) {
            var6 = " & ";
        }

        if (var2.equals("otter_demod")) {
            var6 = ".\n";
        }

        if (var2.equals("prolog")) {
            var6 = ", ";
        }

        new Vector();
        Vector var7 = var1.specifications;
        Vector var8 = new Vector();

        int var9;
        for(var9 = 0; var9 < var7.size(); ++var9) {
            Specification var10 = (Specification)var7.elementAt(var9);
            String var11 = this.writeSpecification(var10, var3, var5, var2, var1);
            if (!var11.equals("")) {
                var8.addElement(var11);
            }
        }

        for(var9 = 0; var9 < var8.size() - 1; ++var9) {
            var4 = var4 + (String)var8.elementAt(var9) + var6;
        }

        if (!var8.isEmpty()) {
            var4 = var4 + (String)var8.lastElement();
        }

        return var4;
    }

    public String writeDefinition(Concept var1, String var2) {
        String var3 = "";
        Vector var4 = this.lettersForTypes(var1.types, var2, new Vector());
        Vector var5 = (Vector)var4.clone();
        String var6 = "";
        if (var2.equals("ascii") || var2.equals("otter") || var2.equals("tptp")) {
            var6 = " & ";
        }

        if (var2.equals("otter_demod")) {
            var6 = ".\n";
        }

        if (var2.equals("prolog")) {
            var6 = ", ";
        }

        new Vector();
        Vector var7 = var1.specifications;
        Vector var8 = new Vector();

        int var9;
        for(var9 = 0; var9 < var7.size(); ++var9) {
            Specification var10 = (Specification)var7.elementAt(var9);
            String var11 = this.writeSpecification(var10, var4, var5, var2, var1);
            if (!var11.equals("")) {
                var8.addElement(var11);
            }
        }

        for(var9 = 0; var9 < var8.size() - 1; ++var9) {
            var3 = var3 + (String)var8.elementAt(var9) + var6;
        }

        if (!var8.isEmpty()) {
            var3 = var3 + (String)var8.lastElement();
        }

        if (var2.equals("otter_demod")) {
            var3 = var3 + ".";
        }

        return var3;
    }

    private String writeSpecification(Specification var1, Vector var2, Vector var3, String var4, Concept var5) {
        String var6 = "";
        String var7 = "";
        String var8 = " & ";
        String var9 = " -> ";
        String var10 = " | ";
        String var11 = "-";
        if (var4.equals("prolog")) {
            var8 = ", ";
            var10 = "; ";
            var11 = " \\+ ";
        }

        if (var4.equals("tptp")) {
            var11 = "~";
            var9 = " => ";
        }

        if (var1.isMultiple()) {
            Vector var12 = new Vector();

            int var14;
            for(int var13 = 0; var13 < var1.permutation.size(); ++var13) {
                var14 = new Integer((String)var1.permutation.elementAt(var13));
                String var15 = (String)var2.elementAt(var14);
                var12.addElement(var15);
            }

            if (var1.type.length() > 10 && var1.type.substring(0, 11).equals("embed graph")) {
                var6 = "graph_embeded_by_concept" + var1.type.substring(11, var1.type.length()) + "(" + (String)var12.elementAt(0) + ", " + (String)var12.elementAt(1) + ")";
                return var6;
            }

            Vector var32 = new Vector();
            if (!var1.type.equals("split")) {
                var32 = this.lettersForTypes(var1.multiple_types, var4, var3);
            }

            var14 = 0;
            int var33 = 0;

            String var19;
            for(int var16 = 0; var33 < var1.multiple_types.size() + var1.redundant_columns.size(); ++var14) {
                String var17 = Integer.toString(var14);
                if (var1.redundant_columns.contains(var17)) {
                    ++var33;
                    var12.insertElementAt("%", var14 + var16);
                }

                int var18 = var1.multiple_variable_columns.indexOf(var17);
                var19 = "";
                if (var18 >= 0) {
                    if (var1.type.equals("split")) {
                        var19 = (String)var1.fixed_values.elementAt(var18);
                    } else {
                        var19 = (String)var32.elementAt(var18);
                    }

                    var12.insertElementAt(var19, var14);
                    ++var33;
                    ++var16;
                }
            }

            if (var1.type.equals("exists") && this.remove_existence_variables && (var4.equals("otter") || var4.equals("otter_demod"))) {
                var6 = this.writeSpecificationRemovingExistenceVariables(var1, var12, var3, var4, var5);
            }

            if (!var6.equals("")) {
                return var6;
            }

            int var34 = var1.rh_starts;
            Vector var35 = new Vector();

            for(int var36 = 0; var36 < var1.previous_specifications.size(); ++var36) {
                var35.addElement(Integer.toString(var36));
            }

            var19 = "";
            String var20 = "";
            boolean var21 = false;
            boolean var22 = false;
            Vector var23 = new Vector();
            boolean var24 = false;
            boolean var25 = false;
            String var26 = "";
            String var27 = "";

            String var30;
            for(int var28 = 0; var28 < var35.size(); ++var28) {
                Specification var29 = (Specification)var1.previous_specifications.elementAt(new Integer((String)var35.elementAt(var28)));
                var30 = this.writeSpecification(var29, var12, var3, var4, var5);
                if (!var30.equals("")) {
                    if (var23.isEmpty() && var29.previous_specifications.size() > 1) {
                        var26 = var29.type;
                    }

                    if (var28 == var34 && var29.previous_specifications.size() > 1) {
                        var27 = var29.type;
                    }

                    if (!var4.equals("otter") && !var4.equals("otter_demod") || !var1.type.equals("forall") || !var23.contains(var30)) {
                        var23.addElement(var30);
                    }
                }

                if (var30.equals("") && var28 < var1.rh_starts) {
                    --var34;
                }
            }

            boolean var37 = false;
            if (var4.equals("ascii") && var1.type.equals("forall")) {
                var7 = var7 + "(";
            }

            int var38;
            if ((var4.equals("otter") || var4.equals("otter_demod")) && var1.type.equals("forall")) {
                if (var34 == 0) {
                    var21 = true;
                }

                var7 = var7 + "all ";

                for(var38 = 0; var38 < var32.size(); ++var38) {
                    var7 = var7 + (String)var32.elementAt(var38) + " ";
                }

                var7 = var7 + "((";
                var37 = true;
            }

            if (var4.equals("tptp") && var1.type.equals("forall")) {
                if (var34 == 0) {
                    var21 = true;
                }

                var7 = var7 + "! [";

                for(var38 = 0; var38 < var32.size() - 1; ++var38) {
                    var7 = var7 + (String)var32.elementAt(var38) + ",";
                }

                var7 = var7 + (String)var32.elementAt(var32.size() - 1);
                var7 = var7 + "] : ((";
                var37 = true;
            }

            for(var38 = 0; var38 < var23.size() - 1; ++var38) {
                var19 = var8;
                if (var1.type.equals("forall") && var38 == var34 - 1 && !var21) {
                    var19 = ")" + var9 + "(";
                    var21 = true;
                }

                if (var1.type.equals("disjunct") && var38 == var34 - 1 && !var22) {
                    var19 = var10;
                    if (var34 > 1 || var26.equals("split")) {
                        var19 = ")" + var10;
                        var24 = true;
                    }

                    if (var23.size() - var38 > 2 || var27.equals("split")) {
                        var19 = var19 + "(";
                        var25 = true;
                    }

                    var22 = true;
                }

                var7 = var7 + (String)var23.elementAt(var38) + var19;
            }

            if (!var23.isEmpty()) {
                var7 = var7 + (String)var23.lastElement();
            }

            if (var37) {
                var7 = var7 + ")";
            }

            if (!var23.isEmpty()) {
                if (var1.type.equals("split")) {
                    var6 = var7;
                }

                if (var1.type.equals("size")) {
                    var38 = new Integer((String)var1.permutation.lastElement());
                    var30 = (String)var2.elementAt(var38);
                    if (var4.equals("ascii") || var4.equals("prolog") || var4.equals("otter") || var4.equals("otter_demod") || var4.equals("tptp")) {
                        var6 = var6 + var2.elementAt(var38) + "=|{";
                        if (var32.size() > 1) {
                            var6 = var6 + "(";
                        }

                        for(int var31 = 0; var31 < var32.size(); ++var31) {
                            var6 = var6 + (String)var32.elementAt(var31);
                            if (var31 < var32.size() - 1) {
                                var6 = var6 + " ";
                            }
                        }

                        if (var32.size() > 1) {
                            var6 = var6 + ")";
                        }

                        var6 = var6 + ": " + var7 + "}|";
                    }
                }

                if (var1.type.equals("exists")) {
                    if (var4.equals("otter")) {
                        var6 = var6 + "(";
                    }

                    if (var4.equals("otter_demod")) {
                        var6 = var6 + "(";
                    }

                    if (var4.equals("ascii") || var4.equals("otter") || var4.equals("otter_demod")) {
                        var6 = var6 + "exists ";
                    }

                    if (var4.equals("tptp")) {
                        var6 = var6 + "? [";
                    }

                    String var40 = " ";
                    if (var4.equals("tptp")) {
                        var40 = ",";
                    }

                    if (!var4.equals("prolog")) {
                        for(int var39 = 0; var39 < var32.size(); ++var39) {
                            if (var4.equals("tptp") && var39 == var32.size() - 1) {
                                var40 = "";
                            }

                            var6 = var6 + (String)var32.elementAt(var39) + var40;
                        }
                    }

                    if (var4.equals("tptp")) {
                        var6 = var6 + "] : ";
                    }

                    if (!var4.equals("prolog")) {
                        var6 = var6 + "(" + var7 + ")";
                    } else {
                        var6 = var7;
                    }

                    if (var4.equals("otter")) {
                        var6 = var6 + ")";
                    }

                    if (var4.equals("otter_demod")) {
                        var6 = var6 + ")";
                    }
                }

                if (var1.type.equals("forall")) {
                    var6 = var6 + "(" + var7 + "))";
                }

                if (var1.type.equals("disjunct")) {
                    var6 = var6 + "(" + var7 + ")";
                    if (var24) {
                        var6 = "(" + var6;
                    }

                    if (var25) {
                        var6 = var6 + ")";
                    }
                }

                if (var1.type.equals("negate")) {
                    var6 = var6 + var11 + "(" + var7 + ")";
                }

                if (var1.type.equals("record")) {
                    var6 = var6 + (String)var2.elementAt(0) + " sets record for: (" + var7 + ")";
                }
            }
        } else {
            var6 = var6 + var1.writeDefinition(var4, var2);
        }

        return var6;
    }

    public String writeSpecificationRemovingExistenceVariables(Specification var1, Vector var2, Vector var3, String var4, Concept var5) {
        boolean var6 = false;
        Vector var7 = new Vector();
        Vector var8 = (Vector)var2.clone();
        Vector var9 = new Vector();
        Vector var10 = new Vector();

        String var13;
        int var14;
        for(int var11 = 0; var11 < var1.multiple_variable_columns.size(); ++var11) {
            Vector var12 = new Vector();
            var9.addElement(var12);
            var13 = (String)var1.multiple_variable_columns.elementAt(var11);
            var14 = new Integer(var13);
            boolean var15 = false;

            for(int var16 = 0; var16 < var1.previous_specifications.size() && !var15; ++var16) {
                Specification var17 = (Specification)var1.previous_specifications.elementAt(var16);
                if (!var17.isMultiple()) {
                    Vector var18 = var17.permutation;
                    Vector var19 = new Vector();

                    int var20;
                    for(var20 = 0; var20 < var18.size(); ++var20) {
                        String var21 = (String)var18.elementAt(var20);
                        int var22 = new Integer(var21);
                        if (var22 == var14) {
                            var19.addElement(Integer.toString(var20));
                        }
                    }

                    if (var19.size() == 1) {
                        for(var20 = 0; var20 < var17.functions.size(); ++var20) {
                            Function var36 = (Function)var17.functions.elementAt(var20);
                            Vector var37 = (Vector)var36.input_columns.clone();
                            Vector var23 = (Vector)var36.output_columns.clone();

                            int var24;
                            String var25;
                            int var26;
                            String var27;
                            for(var24 = 0; var24 < var37.size(); ++var24) {
                                var25 = (String)var37.elementAt(var24);
                                if (!var25.substring(0, 1).equals(":")) {
                                    var26 = new Integer(var25);
                                    var27 = (String)var18.elementAt(var26);
                                    var37.setElementAt(var27, var24);
                                }
                            }

                            for(var24 = 0; var24 < var23.size(); ++var24) {
                                var25 = (String)var23.elementAt(var24);
                                if (!var25.substring(0, 1).equals(":")) {
                                    var26 = new Integer(var25);
                                    var27 = (String)var18.elementAt(var26);
                                    var23.setElementAt(var27, var24);
                                }
                            }

                            if (!var37.contains(var13) && var23.size() == 1 && ((String)var23.elementAt(0)).equals(var13)) {
                                String var38 = this.writeSpecification(var17, var8, var3, var4, var5);
                                Relation var39 = (Relation)var17.relations.elementAt(0);
                                Definition var40 = (Definition)var39.definitions.elementAt(0);
                                int var41 = var40.text.indexOf("=");
                                if (var41 > 0) {
                                    String var28 = "";
                                    String var29 = "@" + (String)var19.elementAt(0) + "@";
                                    if (var40.text.indexOf(var29) < var41) {
                                        var28 = var38.substring(var38.indexOf("=") + 1, var38.length());
                                    } else {
                                        var28 = var38.substring(0, var38.indexOf("="));
                                    }

                                    var8.setElementAt("(" + var28 + ")", var14);
                                    var10.addElement(var13);
                                    var6 = true;
                                    var12.addElement(var17);
                                    var7.addElement(var17);
                                    var15 = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!var6) {
            return "";
        } else {
            String var30 = "";

            for(int var31 = 0; var31 < var1.multiple_variable_columns.size(); ++var31) {
                var13 = (String)var1.multiple_variable_columns.elementAt(var31);
                if (!var10.contains(var13)) {
                    var14 = new Integer(var13);
                    String var33 = (String)var2.elementAt(var14);
                    var30 = var30 + var33 + " ";
                }
            }

            String var32 = "";
            if (!var30.equals("")) {
                var32 = "exists " + var30 + "(";
            }

            var13 = "";

            for(var14 = 0; var14 < var1.previous_specifications.size(); ++var14) {
                Specification var34 = (Specification)var1.previous_specifications.elementAt(var14);
                if (!var7.contains(var34)) {
                    String var35 = this.writeSpecification(var34, var8, var3, var4, var5);
                    if (!var35.equals("") && var14 > 0 && !var13.equals("")) {
                        var13 = var13 + " & ";
                    }

                    var13 = var13 + var35;
                }
            }

            var32 = var32 + var13;
            if (!var30.equals("")) {
                var32 = var32 + ")";
            }

            return var32;
        }
    }

    public Vector lettersForTypes(Vector var1, String var2, Vector var3) {
        String var4 = "";
        if (this.surround_by_at_sign) {
            var4 = "@";
        }

        Vector var5 = new Vector();

        for(int var6 = 0; var6 < var1.size(); ++var6) {
            String var7 = (String)var1.elementAt(var6);
            String var8 = "";
            if (var2.equals("ascii") || var2.equals("otter") || var2.equals("prolog") || var2.equals("tptp") || var2.equals("otter_demod")) {
                String[] var9 = new String[]{"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
                String[] var10 = new String[]{"", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
                boolean var11 = false;
                boolean var12 = false;

                for(int var13 = 0; var13 < var9.length && !var12; ++var13) {
                    for(int var14 = 1; var14 < var9.length && !var12; ++var14) {
                        var8 = var9[var13] + var9[var14];
                        if (var2.equals("tptp") || var2.equals("prolog")) {
                            var8 = var10[var13] + var10[var14];
                        }

                        if (!var3.contains(var8)) {
                            var12 = true;
                        }
                    }
                }
            }

            var3.addElement(var8);
            var5.addElement(var4 + var8 + var4);
        }

        return var5;
    }

    public Object clone() {
        DefinitionWriter var1 = new DefinitionWriter();
        var1.remove_existence_variables = this.remove_existence_variables;
        var1.surround_by_at_sign = this.surround_by_at_sign;
        return var1;
    }

    public String writeDefinitionForGivenEntities(Concept var1, String var2, Vector var3) {
        String var4 = "";
        String var5 = " | ";
        if (var2.equals("ascii") || var2.equals("otter") || var2.equals("tptp")) {
            var4 = " & ";
        }

        if (var2.equals("otter_demod")) {
            var4 = ".\n";
        }

        if (var2.equals("prolog")) {
            var4 = ", ";
            var5 = "; ";
        }

        String var6 = this.writeDefinition(var1, var2);
        if (!var3.isEmpty()) {
            var6 = var6 + var4 + "a = " + (String)var3.elementAt(0);
        }

        for(int var7 = 1; var7 < var3.size(); ++var7) {
            String var8 = (String)var3.elementAt(var7);
            var6 = var6 + var5 + "a = " + var8;
        }

        return var6;
    }
}
