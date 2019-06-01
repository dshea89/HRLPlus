package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Implicate extends Conjecture implements Serializable {
    public Vector overriding_types = new Vector();
    public Concept premise_concept = new Concept();
    public Specification goal = new Specification();

    public Implicate() {
    }

    public Implicate(Concept var1, Specification var2, Step var3) {
        this.premise_concept = var1;
        this.goal = var2;
        this.type = "implicate";
        this.step = var3;
        this.applicability = var1.applicability;
        this.arity = (double)var1.arity;
        if (this.goal.is_entity_instantiations) {
            this.involves_instantiation = true;
        }

        if (this.premise_concept.is_entity_instantiations) {
            this.involves_instantiation = true;
        }

        this.object_type = var1.object_type;
    }

    public Implicate(Vector var1, Vector var2, Specification var3, String var4) {
        this.premise_concept = new Concept();
        this.premise_concept.specifications = var2;
        this.premise_concept.setSkolemisedRepresentation();
        this.premise_concept.types = var1;
        this.premise_concept.domain = var4;
        this.goal = var3;
        this.arity = (double)var1.size();
        if (this.goal.is_entity_instantiations) {
            this.involves_instantiation = true;
        }

        if (this.premise_concept.is_entity_instantiations) {
            this.involves_instantiation = true;
        }

        this.object_type = var4;
    }

    public String getDomain() {
        return this.premise_concept.domain;
    }

    public boolean equals(Implicate var1) {
        if (this.premise_concept != var1.premise_concept) {
            return false;
        } else {
            return this.goal == var1.goal;
        }
    }

    public boolean subsumes(Implicate var1, SpecificationHandler var2) {
        if (this.writeConjecture("otter").equals(var1.writeConjecture("otter"))) {
            return true;
        } else if (this.goal == var1.goal && this.premise_concept.isGeneralisationOf(var1.premise_concept) >= 0) {
            return true;
        } else if (var2.leftSkolemisedSubsumesRight(this, var1, false)) {
            System.out.println(this.writeConjecture("otter"));
            System.out.println("subsumes");
            System.out.println(var1.writeConjecture("otter"));
            System.out.println("-----------------");
            return true;
        } else {
            return false;
        }
    }

    public String writeGoal(String var1) {
        Concept var2 = new Concept();
        var2.specifications.addElement(this.goal);
        var2.types = this.premise_concept.types;
        if (!this.overriding_types.isEmpty()) {
            var2.types = this.overriding_types;
        }

        this.premise_concept.definition_writer.lettersForTypes(this.premise_concept.types, var1, new Vector());
        return var2.writeDefinition(var1);
    }

    public String writeConjectureWithoutUniversalQuantification(String var1) {
        boolean var2 = this.simplify_definitions;
        this.simplify_definitions = true;
        String var3 = this.writeConjectureMain(var1, false, true);
        this.simplify_definitions = var2;
        Hashtable var4 = new Hashtable();

        int var6;
        String var8;
        for(Vector var5 = new Vector(); var3.indexOf("@") >= 0; var3 = var3.substring(0, var6) + var8 + var3.substring(var6 + 3, var3.length())) {
            var6 = var3.indexOf("@");
            String var7 = var3.substring(var6, var6 + 3);
            var8 = (String)var4.get(var7);
            if (var8 == null) {
                String[] var9 = new String[]{"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
                String[] var10 = new String[]{"", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
                boolean var11 = false;
                boolean var12 = false;

                for(int var13 = 0; var13 < var9.length && !var12; ++var13) {
                    for(int var14 = 1; var14 < var9.length && !var12; ++var14) {
                        var8 = var9[var13] + var9[var14];
                        if (var1.equals("tptp")) {
                            var8 = var10[var13] + var10[var14];
                        }

                        if (!var5.contains(var8)) {
                            var12 = true;
                        }
                    }
                }

                var4.put(var7, var8);
                var5.addElement(var8);
            }
        }

        return var3;
    }

    public String writeConjecture(String var1) {
        return this.writeConjectureMain(var1, true, false);
    }

    private String writeConjectureMain(String var1, boolean var2, boolean var3) {
        Concept var4 = new Concept();
        var4.specifications.addElement(this.goal);
        var4.types = this.premise_concept.types;
        if (!this.overriding_types.isEmpty()) {
            var4.types = this.overriding_types;
        }

        Vector var5 = this.premise_concept.definition_writer.lettersForTypes(this.premise_concept.types, var1, new Vector());
        boolean var6 = var4.definition_writer.remove_existence_variables;
        boolean var7 = this.premise_concept.definition_writer.remove_existence_variables;
        if (this.simplify_definitions) {
            var4.definition_writer.remove_existence_variables = true;
            this.premise_concept.definition_writer.remove_existence_variables = true;
        }

        String var8 = "";
        String var9 = "";
        if (var3) {
            var8 = var4.writeDefinitionWithAtSigns(var1);
            var9 = this.premise_concept.writeDefinitionWithAtSigns(var1);
        } else {
            var8 = var4.writeDefinition(var1);
            var9 = this.premise_concept.writeDefinition(var1);
        }

        if (this.simplify_definitions) {
            var4.definition_writer.remove_existence_variables = var6;
            this.premise_concept.definition_writer.remove_existence_variables = var7;
        }

        String var10;
        if (var1.equals("prolog") && var9.equals("")) {
            var10 = (String)this.premise_concept.types.elementAt(0);
            var9 = var10 + "(A)";
        }

        var10 = "";
        if (var1.equals("ascii")) {
            var10 = this.writeAsciiConjecture(var5, var9, var8, var2);
        }

        if (var1.equals("otter")) {
            var10 = this.writeOtterConjecture(var5, var9, var8, var2);
        }

        if (var1.equals("tptp")) {
            var10 = this.writeTPTPConjecture(var5, var9, var8, var2);
        }

        if (var1.equals("prolog")) {
            var10 = this.writePrologConjecture(var5, var9, var8, var2);
        }

        var4.definition_writer.remove_existence_variables = var6;
        this.premise_concept.definition_writer.remove_existence_variables = var7;
        return var10;
    }

    private String writeAsciiConjecture(Vector var1, String var2, String var3, boolean var4) {
        String var5 = " for all ";

        for(int var6 = 0; var6 < var1.size(); ++var6) {
            var5 = var5 + (String)var1.elementAt(var6) + " ";
        }

        return var5 + ": " + var2 + " -> " + var3;
    }

    private String writeOtterConjecture(Vector var1, String var2, String var3, boolean var4) {
        String var5 = "";
        if (var2.equals("") && var3.equals("")) {
            return "";
        } else if (var3.equals("")) {
            return this.writeOtterConjecture(var1, var3, var2, var4);
        } else {
            if ((var1.size() > 1 || this.use_entity_letter) && var4) {
                var5 = var5 + "all ";
            }

            byte var6 = 1;
            if (this.use_entity_letter) {
                var6 = 0;
            }

            for(int var7 = var6; var7 < var1.size() && var4; ++var7) {
                var5 = var5 + (String)var1.elementAt(var7) + " ";
            }

            if ((var1.size() > 1 || this.use_entity_letter) && var4) {
                var5 = var5 + "(";
            }

            if (!var2.equals("") && !var3.equals("")) {
                var5 = var5 + "((" + var2 + ") -> (" + var3 + "))";
            }

            if (var2.equals("")) {
                var5 = var5 + "(" + var3 + ")";
            }

            if ((var1.size() > 1 || this.use_entity_letter) && var4) {
                var5 = var5 + ")";
            }

            return var5;
        }
    }

    private String writePrologConjecture(Vector var1, String var2, String var3, boolean var4) {
        String var5 = "";
        if (var2.equals("") && var3.equals("")) {
            return "";
        } else if (var3.equals("")) {
            return this.writeOtterConjecture(var1, var3, var2, var4);
        } else {
            var5 = var3 + " :- " + var2 + ".";
            if (var2.equals("") && !var3.equals("")) {
                var5 = var3 + ".";
            }

            return var5;
        }
    }

    private String writeTPTPConjecture(Vector var1, String var2, String var3, boolean var4) {
        if (var2.equals("") && var3.equals("")) {
            return "";
        } else {
            String var5 = "[";

            for(int var6 = 1; var6 < var1.size() - 1; ++var6) {
                var5 = var5 + (String)var1.elementAt(var6) + ",";
            }

            if (var1.size() > 1) {
                var5 = var5 + (String)var1.elementAt(var1.size() - 1);
            }

            var5 = var5 + "]";
            String var8 = "";
            boolean var7 = false;
            if (var2.trim().equals("") || var2.trim().equals("()") || var2.trim().equals("(())")) {
                if (var5.equals("[]")) {
                    var8 = "input_formula(conjecture" + this.id + ",conjecture,((" + var3 + "))).";
                } else {
                    var8 = "input_formula(conjecture" + this.id + ",conjecture,(! " + var5 + " : (" + var3 + "))).";
                }

                var7 = true;
            }

            if (var3.trim().equals("") || var3.trim().equals("()") || var3.trim().equals("(())")) {
                if (var5.equals("[]")) {
                    var8 = "input_formula(conjecture" + this.id + ",conjecture,((" + var2 + "))).";
                } else {
                    var8 = "input_formula(conjecture" + this.id + ",conjecture,(! " + var5 + " : (" + var2 + "))).";
                }

                var7 = true;
            }

            if (!var7) {
                if (var5.equals("[]")) {
                    var8 = "input_formula(conjecture" + this.id + ",conjecture,(((" + var2 + ")) => (" + var3 + "))).";
                } else {
                    var8 = "input_formula(conjecture" + this.id + ",conjecture,(! " + var5 + " : ((" + var2 + " ) => (" + var3 + ")))).";
                }
            }

            return var8;
        }
    }

    public Vector possiblePrimeImplicates() {
        Vector var1 = new Vector();
        Vector var2 = new Vector();
        var2.addElement(new Vector());
        Vector var3 = new Vector();
        var3.addElement(this.writeConjectureWithoutUniversalQuantification("otter"));

        for(int var4 = 0; var4 < this.premise_concept.specifications.size() - 1; ++var4) {
            int var5 = var2.size();

            for(int var6 = 0; var6 < var5; ++var6) {
                Vector var7 = (Vector)var2.elementAt(0);
                var2.removeElementAt(0);
                int var8 = 0;
                if (!var7.isEmpty()) {
                    Specification var9 = (Specification)var7.lastElement();
                    var8 = this.premise_concept.specifications.indexOf(var9);
                }

                for(int var14 = var8 + 1; var14 < this.premise_concept.specifications.size(); ++var14) {
                    Specification var10 = (Specification)this.premise_concept.specifications.elementAt(var14);
                    Vector var11 = (Vector)var7.clone();
                    var11.addElement(var10);
                    Implicate var12 = new Implicate(this.premise_concept.types, var11, this.goal, this.premise_concept.domain);
                    String var13 = var12.writeConjectureWithoutUniversalQuantification("otter");
                    if (!var13.equals("") && !var3.contains(var13)) {
                        var1.addElement(var12);
                        var3.addElement(var13);
                    }

                    var2.addElement(var11);
                }
            }
        }

        return var1;
    }
}
