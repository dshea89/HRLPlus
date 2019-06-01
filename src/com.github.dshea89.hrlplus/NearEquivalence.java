package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class NearEquivalence extends Conjecture implements Serializable {
    public Concept lh_concept = new Concept();
    public Concept rh_concept = new Concept();
    public double score = 0.0D;

    public NearEquivalence() {
    }

    public NearEquivalence(Concept var1, Concept var2, Vector var3, double var4) {
        this.lh_concept = var1;
        this.rh_concept = var2;
        this.counterexamples = var3;
        this.score = var4;
    }

    public String writeConjecture(String var1) {
        Vector var2 = this.lh_concept.definition_writer.lettersForTypes(this.lh_concept.types, var1, new Vector());
        String var3 = this.lh_concept.writeDefinition(var1);
        String var4 = this.rh_concept.writeDefinition(var1);
        if (var1.equals("ascii")) {
            return this.writeAsciiConjecture(var2, var3, var4);
        } else if (var1.equals("otter")) {
            return this.writeOtterConjecture(var2, var3, var4);
        } else if (var1.equals("tptp")) {
            return this.writeTPTPConjecture(var2, var3, var4);
        } else {
            return var1.equals("prolog") ? this.writePrologConjecture(var2, var3, var4) : "";
        }
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
                    var7 = "input_formula(conjecture" + this.id + ",conjecture,(\n     ((" + var2 + ")) \n       <=> (" + var3 + "))).";
                } else {
                    var7 = "input_formula(conjecture" + this.id + ",conjecture,(\n     ! " + var4 + " : \n      ((" + var2 + " )\n       <=> (" + var3 + ")))).";
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

        return var4 + ": " + var2 + " <~> " + var3;
    }

    private String writePrologConjecture(Vector var1, String var2, String var3) {
        String var4 = " for all ";

        for(int var5 = 0; var5 < var1.size(); ++var5) {
            var4 = var4 + (String)var1.elementAt(var5) + " ";
        }

        return var4 + ": " + var2 + " <~> " + var3;
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
                var4 = var4 + "((" + var2 + ") <~> (" + var3 + "))";
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
}
