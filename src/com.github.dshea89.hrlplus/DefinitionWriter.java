package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

/**
 * A class for writing definitions.
 */
public class DefinitionWriter implements Serializable {
    /**
     * Whether to remove existence variables by writing them as the output of a function.
     */
    public boolean remove_existence_variables = false;

    /**
     * Whether or not to surround each letter by the @ sign. This is in order to tell which letters have been added in.
     */
    public boolean surround_by_at_sign = false;

    public DefinitionWriter() {
    }

    /**
     * This will write the definition for the given concept in the given language.
     */
    public String writeDefinitionWithStartLetters(Concept concept, String language) {
        Vector var3 = this.lettersForTypes(concept.types, language, new Vector());
        return language.equals("prolog") ? var3.toString().toUpperCase() + " : " + this.writeDefinition(concept, language) : var3.toString() + " : " + this.writeDefinition(concept, language);
    }

    /**
     * This will write the definition in the given language, omitting the initial letters and using the letters supplied.
     */
    public String writeDefinition(Concept concept, String language, Vector letters) {
        String var4 = "";
        Vector var5 = (Vector)letters.clone();
        String var6 = "";
        if (language.equals("ascii") || language.equals("otter") || language.equals("tptp")) {
            var6 = " & ";
        }

        if (language.equals("otter_demod")) {
            var6 = ".\n";
        }

        if (language.equals("prolog")) {
            var6 = ", ";
        }

        new Vector();
        Vector var7 = concept.specifications;
        Vector var8 = new Vector();

        int var9;
        for(var9 = 0; var9 < var7.size(); ++var9) {
            Specification var10 = (Specification)var7.elementAt(var9);
            String var11 = this.writeSpecification(var10, letters, var5, language, concept);
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

    /**
     * This will write the definition for this concept in the given language, but will omit the initial letters (in the square brackets).
     */
    public String writeDefinition(Concept concept, String language) {
        String var3 = "";
        Vector var4 = this.lettersForTypes(concept.types, language, new Vector());
        Vector var5 = (Vector)var4.clone();
        String var6 = "";
        if (language.equals("ascii") || language.equals("otter") || language.equals("tptp")) {
            var6 = " & ";
        }

        if (language.equals("otter_demod")) {
            var6 = ".\n";
        }

        if (language.equals("prolog")) {
            var6 = ", ";
        }

        new Vector();
        Vector var7 = concept.specifications;
        Vector var8 = new Vector();

        int var9;
        for(var9 = 0; var9 < var7.size(); ++var9) {
            Specification var10 = (Specification)var7.elementAt(var9);
            String var11 = this.writeSpecification(var10, var4, var5, language, concept);
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

        if (language.equals("otter_demod")) {
            var3 = var3 + ".";
        }

        return var3;
    }

    private String writeSpecification(Specification specification, Vector letters, Vector all_letters, String language, Concept concept) {
        String var6 = "";
        String var7 = "";
        String var8 = " & ";
        String var9 = " -> ";
        String var10 = " | ";
        String var11 = "-";
        if (language.equals("prolog")) {
            var8 = ", ";
            var10 = "; ";
            var11 = " \\+ ";
        }

        if (language.equals("tptp")) {
            var11 = "~";
            var9 = " => ";
        }

        if (specification.isMultiple()) {
            Vector var12 = new Vector();

            int var14;
            for(int var13 = 0; var13 < specification.permutation.size(); ++var13) {
                var14 = new Integer((String)specification.permutation.elementAt(var13));
                String var15 = (String)letters.elementAt(var14);
                var12.addElement(var15);
            }

            if (specification.type.length() > 10 && specification.type.substring(0, 11).equals("embed graph")) {
                var6 = "graph_embeded_by_concept" + specification.type.substring(11, specification.type.length()) + "(" + (String)var12.elementAt(0) + ", " + (String)var12.elementAt(1) + ")";
                return var6;
            }

            Vector var32 = new Vector();
            if (!specification.type.equals("split")) {
                var32 = this.lettersForTypes(specification.multiple_types, language, all_letters);
            }

            var14 = 0;
            int var33 = 0;

            String var19;
            for(int var16 = 0; var33 < specification.multiple_types.size() + specification.redundant_columns.size(); ++var14) {
                String var17 = Integer.toString(var14);
                if (specification.redundant_columns.contains(var17)) {
                    ++var33;
                    var12.insertElementAt("%", var14 + var16);
                }

                int var18 = specification.multiple_variable_columns.indexOf(var17);
                var19 = "";
                if (var18 >= 0) {
                    if (specification.type.equals("split")) {
                        var19 = (String)specification.fixed_values.elementAt(var18);
                    } else {
                        var19 = (String)var32.elementAt(var18);
                    }

                    var12.insertElementAt(var19, var14);
                    ++var33;
                    ++var16;
                }
            }

            if (specification.type.equals("exists") && this.remove_existence_variables && (language.equals("otter") || language.equals("otter_demod"))) {
                var6 = this.writeSpecificationRemovingExistenceVariables(specification, var12, all_letters, language, concept);
            }

            if (!var6.equals("")) {
                return var6;
            }

            int var34 = specification.rh_starts;
            Vector var35 = new Vector();

            for(int var36 = 0; var36 < specification.previous_specifications.size(); ++var36) {
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
                Specification var29 = (Specification)specification.previous_specifications.elementAt(new Integer((String)var35.elementAt(var28)));
                var30 = this.writeSpecification(var29, var12, all_letters, language, concept);
                if (!var30.equals("")) {
                    if (var23.isEmpty() && var29.previous_specifications.size() > 1) {
                        var26 = var29.type;
                    }

                    if (var28 == var34 && var29.previous_specifications.size() > 1) {
                        var27 = var29.type;
                    }

                    if (!language.equals("otter") && !language.equals("otter_demod") || !specification.type.equals("forall") || !var23.contains(var30)) {
                        var23.addElement(var30);
                    }
                }

                if (var30.equals("") && var28 < specification.rh_starts) {
                    --var34;
                }
            }

            boolean var37 = false;
            if (language.equals("ascii") && specification.type.equals("forall")) {
                var7 = var7 + "(";
            }

            int var38;
            if ((language.equals("otter") || language.equals("otter_demod")) && specification.type.equals("forall")) {
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

            if (language.equals("tptp") && specification.type.equals("forall")) {
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
                if (specification.type.equals("forall") && var38 == var34 - 1 && !var21) {
                    var19 = ")" + var9 + "(";
                    var21 = true;
                }

                if (specification.type.equals("disjunct") && var38 == var34 - 1 && !var22) {
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
                if (specification.type.equals("split")) {
                    var6 = var7;
                }

                if (specification.type.equals("size")) {
                    var38 = new Integer((String)specification.permutation.lastElement());
                    var30 = (String)letters.elementAt(var38);
                    if (language.equals("ascii") || language.equals("prolog") || language.equals("otter") || language.equals("otter_demod") || language.equals("tptp")) {
                        var6 = var6 + letters.elementAt(var38) + "=|{";
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

                if (specification.type.equals("exists")) {
                    if (language.equals("otter")) {
                        var6 = var6 + "(";
                    }

                    if (language.equals("otter_demod")) {
                        var6 = var6 + "(";
                    }

                    if (language.equals("ascii") || language.equals("otter") || language.equals("otter_demod")) {
                        var6 = var6 + "exists ";
                    }

                    if (language.equals("tptp")) {
                        var6 = var6 + "? [";
                    }

                    String var40 = " ";
                    if (language.equals("tptp")) {
                        var40 = ",";
                    }

                    if (!language.equals("prolog")) {
                        for(int var39 = 0; var39 < var32.size(); ++var39) {
                            if (language.equals("tptp") && var39 == var32.size() - 1) {
                                var40 = "";
                            }

                            var6 = var6 + (String)var32.elementAt(var39) + var40;
                        }
                    }

                    if (language.equals("tptp")) {
                        var6 = var6 + "] : ";
                    }

                    if (!language.equals("prolog")) {
                        var6 = var6 + "(" + var7 + ")";
                    } else {
                        var6 = var7;
                    }

                    if (language.equals("otter")) {
                        var6 = var6 + ")";
                    }

                    if (language.equals("otter_demod")) {
                        var6 = var6 + ")";
                    }
                }

                if (specification.type.equals("forall")) {
                    var6 = var6 + "(" + var7 + "))";
                }

                if (specification.type.equals("disjunct")) {
                    var6 = var6 + "(" + var7 + ")";
                    if (var24) {
                        var6 = "(" + var6;
                    }

                    if (var25) {
                        var6 = var6 + ")";
                    }
                }

                if (specification.type.equals("negate")) {
                    var6 = var6 + var11 + "(" + var7 + ")";
                }

                if (specification.type.equals("record")) {
                    var6 = var6 + (String)letters.elementAt(0) + " sets record for: (" + var7 + ")";
                }
            }
        } else {
            var6 = var6 + specification.writeDefinition(language, letters);
        }

        return var6;
    }

    public String writeSpecificationRemovingExistenceVariables(Specification specification, Vector letters, Vector all_letters,
                                                               String language, Concept concept) {
        boolean var6 = false;
        Vector var7 = new Vector();
        Vector var8 = (Vector)letters.clone();
        Vector var9 = new Vector();
        Vector var10 = new Vector();

        String var13;
        int var14;
        for(int var11 = 0; var11 < specification.multiple_variable_columns.size(); ++var11) {
            Vector var12 = new Vector();
            var9.addElement(var12);
            var13 = (String)specification.multiple_variable_columns.elementAt(var11);
            var14 = new Integer(var13);
            boolean var15 = false;

            for(int var16 = 0; var16 < specification.previous_specifications.size() && !var15; ++var16) {
                Specification var17 = (Specification)specification.previous_specifications.elementAt(var16);
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
                                String var38 = this.writeSpecification(var17, var8, all_letters, language, concept);
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

            for(int var31 = 0; var31 < specification.multiple_variable_columns.size(); ++var31) {
                var13 = (String)specification.multiple_variable_columns.elementAt(var31);
                if (!var10.contains(var13)) {
                    var14 = new Integer(var13);
                    String var33 = (String)letters.elementAt(var14);
                    var30 = var30 + var33 + " ";
                }
            }

            String var32 = "";
            if (!var30.equals("")) {
                var32 = "exists " + var30 + "(";
            }

            var13 = "";

            for(var14 = 0; var14 < specification.previous_specifications.size(); ++var14) {
                Specification var34 = (Specification)specification.previous_specifications.elementAt(var14);
                if (!var7.contains(var34)) {
                    String var35 = this.writeSpecification(var34, var8, all_letters, language, concept);
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

    /**
     * Returns correct letters for the given types, choice of language and letters already used.
     */
    public Vector lettersForTypes(Vector input_types, String language, Vector letters_already) {
        String var4 = "";
        if (this.surround_by_at_sign) {
            var4 = "@";
        }

        Vector var5 = new Vector();

        for(int var6 = 0; var6 < input_types.size(); ++var6) {
            String var7 = (String)input_types.elementAt(var6);
            String var8 = "";
            if (language.equals("ascii") || language.equals("otter") || language.equals("prolog") || language.equals("tptp") || language.equals("otter_demod")) {
                String[] var9 = new String[]{"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
                String[] var10 = new String[]{"", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
                boolean var11 = false;
                boolean var12 = false;

                for(int var13 = 0; var13 < var9.length && !var12; ++var13) {
                    for(int var14 = 1; var14 < var9.length && !var12; ++var14) {
                        var8 = var9[var13] + var9[var14];
                        if (language.equals("tptp") || language.equals("prolog")) {
                            var8 = var10[var13] + var10[var14];
                        }

                        if (!letters_already.contains(var8)) {
                            var12 = true;
                        }
                    }
                }
            }

            letters_already.addElement(var8);
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
