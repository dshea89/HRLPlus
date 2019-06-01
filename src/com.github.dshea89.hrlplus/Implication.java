package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Implication extends Conjecture implements Serializable {
    public Double score = new Double(0.0D);
    public Concept lh_concept = new Concept();
    public Concept rh_concept = new Concept();

    public Implication() {
    }

    public Implication(Concept var1, Concept var2, String var3) {
        this.lh_concept = var1;
        this.rh_concept = var2;
        if (this.lh_concept.is_entity_instantiations || this.rh_concept.is_entity_instantiations) {
            this.involves_instantiation = true;
        }

        this.id = var3;
        this.type = "concept_implication";
        this.object_type = this.lh_concept.object_type;
    }

    public double surprisingness() {
        double var1 = 0.0D;
        double var3 = 0.0D;

        int var5;
        for(var5 = 0; var5 < this.lh_concept.ancestor_ids.size(); ++var5) {
            if (!this.rh_concept.ancestor_ids.contains(this.lh_concept.ancestor_ids.elementAt(var5))) {
                ++var1;
            }
        }

        for(var5 = 0; var5 < this.rh_concept.ancestor_ids.size(); ++var5) {
            if (!this.lh_concept.ancestor_ids.contains(this.rh_concept.ancestor_ids.elementAt(var5))) {
                ++var1;
            } else {
                ++var3;
            }
        }

        var1 /= (double)(this.lh_concept.ancestor_ids.size() + this.rh_concept.ancestor_ids.size()) - var3;
        return var1;
    }

    public String writeConjecture(String var1) {
        String var2 = "";
        Vector var3 = this.lh_concept.definition_writer.lettersForTypes(this.lh_concept.types, var1, new Vector());
        boolean var4 = this.rh_concept.definition_writer.remove_existence_variables;
        boolean var5 = this.rh_concept.definition_writer.remove_existence_variables;
        if (this.simplify_definitions) {
            this.lh_concept.definition_writer.remove_existence_variables = true;
            this.rh_concept.definition_writer.remove_existence_variables = true;
        }

        String var6 = this.lh_concept.writeDefinition(var1);
        String var7 = this.rh_concept.writeDefinition(var1);
        if (var1.equals("ascii")) {
            var2 = this.writeAsciiConjecture(var3, var6, var7);
        }

        if (var1.equals("otter")) {
            var2 = this.writeOtterConjecture(var3, var6, var7);
        }

        if (var1.equals("tptp")) {
            var2 = this.writeTPTPConjecture(var3, var6, var7);
        }

        if (var1.equals("prolog")) {
            var2 = this.writePrologConjecture(var3, var6, var7);
        }

        this.lh_concept.definition_writer.remove_existence_variables = var4;
        this.rh_concept.definition_writer.remove_existence_variables = var5;
        return var2;
    }

    private String writePrologConjecture(Vector var1, String var2, String var3) {
        String var4 = " for all ";

        for(int var5 = 0; var5 < var1.size(); ++var5) {
            var4 = var4 + (String)var1.elementAt(var5) + " ";
        }

        return var4 + ": " + var2 + " => " + var3;
    }

    private String writeTPTPConjecture(Vector var1, String var2, String var3) {
        if (var2.equals("") && var3.equals("")) {
            return "";
        } else {
            String var4 = "[";

            for(int var5 = 1; var5 < var1.size() - 1; ++var5) {
                var4 = var4 + (String)var1.elementAt(var5) + ",";
            }

            if (var1.size() > 1) {
                var4 = var4 + (String)var1.elementAt(var1.size() - 1);
            }

            var4 = var4 + "]";
            String var7 = "";
            boolean var6 = false;
            if (var2.trim().equals("") || var2.trim().equals("()") || var2.trim().equals("(())")) {
                if (var4.equals("[]")) {
                    var7 = "input_formula(conjecture" + this.id + ",conjecture,(\n     (" + var3 + "))).";
                } else {
                    var7 = "input_formula(conjecture" + this.id + ",conjecture,(\n     ! " + var4 + " : \n      (" + var3 + "))).";
                }

                var6 = true;
            }

            if (var3.trim().equals("") || var3.trim().equals("()") || var3.trim().equals("(())")) {
                if (var4.equals("[]")) {
                    var7 = "input_formula(conjecture" + this.id + ",conjecture,(\n     (" + var2 + "))).";
                } else {
                    var7 = "input_formula(conjecture" + this.id + ",conjecture,(\n     ! " + var4 + " : \n      (" + var2 + "))).";
                }

                var6 = true;
            }

            if (!var6) {
                if (var4.equals("[]")) {
                    var7 = "input_formula(conjecture" + this.id + ",conjecture,(\n     ((" + var2 + ")) \n       => (" + var3 + "))).";
                } else {
                    var7 = "input_formula(conjecture" + this.id + ",conjecture,(\n     ! " + var4 + " : \n      ((" + var2 + " )\n       => (" + var3 + ")))).";
                }
            }

            return var7;
        }
    }

    private String writeAsciiConjecture(Vector var1, String var2, String var3) {
        String var4 = " for all ";

        for(int var5 = 0; var5 < var1.size(); ++var5) {
            var4 = var4 + (String)var1.elementAt(var5) + " ";
        }

        return var4 + ": " + var2 + " -> " + var3;
    }

    private String writeOtterConjecture(Vector var1, String var2, String var3) {
        String var4 = "";
        if (var2.equals("") && var3.equals("")) {
            return "";
        } else if (var3.equals("")) {
            return this.writeOtterConjecture(var1, var3, var2);
        } else {
            if (var1.size() > 1 || this.use_entity_letter) {
                var4 = var4 + "all ";
            }

            byte var5 = 1;
            if (this.use_entity_letter) {
                var5 = 0;
            }

            for(int var6 = var5; var6 < var1.size(); ++var6) {
                var4 = var4 + (String)var1.elementAt(var6) + " ";
            }

            if (var1.size() > 1 || this.use_entity_letter) {
                var4 = var4 + "(";
            }

            if (!var2.equals("") && !var3.equals("")) {
                var4 = var4 + "((" + var2 + ") -> (" + var3 + "))";
            }

            if (var2.equals("")) {
                var4 = var4 + "(" + var3 + ")";
            }

            if (var1.size() > 1 || this.use_entity_letter) {
                var4 = var4 + ")";
            }

            return var4;
        }
    }

    public Vector implicates(SpecificationHandler var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < this.rh_concept.specifications.size(); ++var3) {
            Specification var4 = (Specification)this.rh_concept.specifications.elementAt(var3);
            if (!this.lh_concept.specifications.contains(var4)) {
                Concept var5 = new Concept();
                var5.specifications.addElement(var4);
                var5.setSkolemisedRepresentation();
                if (!var5.skolemised_representation.variables.isEmpty() && !var1.leftSkolemisedImpliesRight(this.lh_concept, var5, false)) {
                    Implicate var6 = new Implicate(this.lh_concept, var4, this.step);
                    var6.when_constructed = this.when_constructed;
                    var6.proof_status = this.proof_status;
                    var2.addElement(var6);
                }
            }
        }

        return var2;
    }

    public Vector knownCounterexamples() {
        Vector var1 = new Vector();
        Vector var2 = this.lh_concept.positiveEntities();
        Vector var3 = this.rh_concept.positiveEntities();

        for(int var4 = 0; var4 < var2.size(); ++var4) {
            Entity var5 = (Entity)var2.elementAt(var4);
            boolean var6 = false;

            for(int var7 = 0; var7 < var3.size(); ++var7) {
                Entity var8 = (Entity)var3.elementAt(var7);
                if (var5.toString().equals(var8.toString())) {
                    var6 = true;
                    break;
                }
            }

            if (!var6) {
                var1.addElement(var5);
            }
        }

        return var1;
    }
}
