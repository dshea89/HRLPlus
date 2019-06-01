package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Conjecture extends TheoryConstituent implements Serializable {
    public String explained_by = "";
    public boolean involves_segregated_concepts = false;
    public Vector parent_conjectures = new Vector();
    public Vector child_conjectures = new Vector();
    public boolean simplify_definitions = false;
    public boolean involves_instantiation = false;
    public boolean use_entity_letter = false;
    public Vector axiom_names = new Vector();
    public double mw_max_proof_time = 0.0D;
    public double mw_av_proof_time = 0.0D;
    public double mw_min_proof_time = -1.0D;
    public double mw_diff_proof_time = 0.0D;
    public Vector proof_attempts_information = new Vector();
    public boolean is_trivially_true = false;
    public boolean is_removed = false;
    public boolean is_prime_implicate = false;
    public Vector counterexamples = new Vector();
    public String proof_status = "open";
    public double proof_length = 0.0D;
    public double proof_time = 0.0D;
    public double wc_proof_time = 0.0D;
    public double proof_level = 0.0D;
    public double arity = 0.0D;
    public double applicability = 0.0D;
    public double normalised_applicability = 0.0D;
    public String type = "";
    public double interestingness = 0.0D;
    public int num_modifications = 0;
    public double complexity = 0.0D;
    public double comprehensibility = 0.0D;
    public double normalised_comprehensibility = 0.0D;
    public double surprisingness = 0.0D;
    public double normalised_surprisingness = 0.0D;
    public double plausibility = 0.0D;
    public Step step = new Step();
    public String object_type = "";
    public String lakatos_method = "no";
    public String proof = "";

    public Conjecture() {
    }

    public String writeConjectureHTML(String var1) {
        return this.replaceLTForHTML(this.writeConjecture(var1));
    }

    public String writeConjecture(String var1) {
        return "";
    }

    public String writeConjecture() {
        return this.writeConjecture("ascii");
    }

    public String toString() {
        return this.writeConjecture();
    }

    public String writeConcept() {
        return "";
    }

    public String getDomain() {
        return "";
    }

    public String fullDetails(String[] var1, int var2, String[] var3) {
        return this.fullASCIIDetails(var1, var2, var3);
    }

    public String fullASCIIDetails(String[] var1, int var2, String[] var3) {
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var1.length; ++var5) {
            var4.addElement(var1[var5]);
        }

        String var10 = "";
        if (var4.contains("id")) {
            var10 = var10 + "(" + this.id + ")";
        }

        if (var4.contains("simplified format")) {
            boolean var6 = this.simplify_definitions;
            this.simplify_definitions = true;
            var10 = var10 + this.writeConjecture("otter") + "\n";
            this.simplify_definitions = var6;
        }

        if (var4.contains("ascii format")) {
            var10 = var10 + this.writeConjecture("ascii") + "\n";
        }

        if (var4.contains("otter format")) {
            var10 = var10 + this.writeConjecture("otter") + "\n";
        }

        if (var4.contains("prolog format")) {
            var10 = var10 + this.writeConjecture("prolog") + "\n";
        }

        if (var4.contains("tptp format")) {
            var10 = var10 + this.writeConjecture("tptp") + "\n";
        }

        if (!var10.equals("")) {
            var10 = var10 + "-----------------\n";
        }

        if (var4.contains("proof_status")) {
            var10 = var10 + "The proof status of this theorem is: " + this.proof_status + "\n";
        }

        int var7;
        String var8;
        if (this.proof_attempts_information.isEmpty()) {
            if (var4.contains("prover_details")) {
                var10 = var10 + "There have been no proof attempts on this conjecture.\n";
            }
        } else {
            var10 = var10 + "Prover details:\n";

            for(var7 = 0; var7 < this.proof_attempts_information.size(); ++var7) {
                var8 = (String)this.proof_attempts_information.elementAt(var7);
                int var9 = var8.indexOf(":");
                var10 = var10 + var8.substring(0, var9) + " took " + var8.substring(var9 + 1, var8.lastIndexOf(":")) + " miliseconds, and the proof status was: " + var8.substring(var8.lastIndexOf(":") + 1, var8.length()) + "\n";
            }

            if (var4.contains("mw_max_ptime")) {
                var10 = var10 + "Max proof time in MathWeb = " + this.decimalPlaces(this.mw_max_proof_time, var2) + "\n";
            }

            if (var4.contains("mw_min_ptime")) {
                var10 = var10 + "Min proof time in MathWeb = " + this.decimalPlaces(this.mw_min_proof_time, var2) + "\n";
            }

            if (var4.contains("mw_av_ptime")) {
                var10 = var10 + "Average proof time in MathWeb = " + this.decimalPlaces(this.mw_av_proof_time, var2) + "\n";
            }

            if (var4.contains("mw_diff_ptime")) {
                var10 = var10 + "Largest - smallest proof time in MathWeb = " + this.decimalPlaces(this.mw_diff_proof_time, var2) + "\n";
            }
        }

        if (var4.contains("explained_by")) {
            var10 = var10 + "Explained by: " + this.explained_by + "\n";
        }

        if (var4.contains("proof")) {
            if (this.proof.equals("")) {
                var10 = var10 + "The proof was not recorded.\n";
            } else {
                var10 = var10 + "proof length = " + this.proof_length;
                var10 = var10 + ", proof level = " + this.proof_level;
                var10 = var10 + "\nProof:\n" + this.proof + "\n";
            }
        }

        if (var4.contains("counter")) {
            if (this.counterexamples.isEmpty()) {
                var10 = var10 + "No counterexample to this conjecture has been found.\n";
            } else {
                var10 = var10 + "The counterexamples to this conjecture were: ";

                for(var7 = 0; var7 < this.counterexamples.size(); ++var7) {
                    Entity var12 = (Entity)this.counterexamples.elementAt(var7);
                    var10 = var10 + var12.name + " ";
                }

                var10 = var10 + "\n";
            }
        }

        if (!var10.equals(var10)) {
            var10 = var10 + "-----------------\n";
        }

        if (var4.contains("total score")) {
            var10 = var10 + "Total score = " + this.decimalPlaces(this.interestingness, var2) + "\n";
        }

        if (var4.contains("arity")) {
            var10 = var10 + "Arity = " + this.decimalPlaces(this.arity, var2) + "\n";
        }

        if (var4.contains("applicability")) {
            var10 = var10 + "Applicability = " + this.decimalPlaces(this.applicability, var2) + " (" + this.decimalPlaces(this.normalised_applicability, var2) + ")\n";
        }

        if (var4.contains("complexity")) {
            var10 = var10 + "Complexity = " + this.decimalPlaces(this.complexity, var2) + "\n";
        }

        if (var4.contains("comprehensibility")) {
            var10 = var10 + "Comprehensibility = " + this.decimalPlaces(this.comprehensibility, var2) + " (" + this.decimalPlaces(this.normalised_comprehensibility, var2) + ")\n";
        }

        if (var4.contains("surprisingness")) {
            var10 = var10 + "Surprisingness = " + this.decimalPlaces(this.surprisingness, var2) + " (" + this.decimalPlaces(this.normalised_surprisingness, var2) + ")\n";
        }

        if (var4.contains("plausibility")) {
            var10 = var10 + "Plausibility = " + this.decimalPlaces(this.plausibility, var2) + "\n";
        }

        if (!var10.equals(var10)) {
            var10 = var10 + "-----------------\n";
        }

        String var11 = var10;
        if (var4.contains("const time")) {
            String var13 = "s";
            var8 = Long.toString(this.when_constructed).trim();
            if (var8.equals("1")) {
                var13 = "";
            }

            var10 = var10 + "Constructed after " + Long.toString(this.when_constructed) + " second" + var13;
        }

        if (var4.contains("step")) {
            var10 = var10 + "Construction step leading to this conjecture: " + this.step.asString() + "\n";
        }

        if (var4.contains("lh_concept")) {
            if (!var10.equals(var10)) {
                var10 = var10 + "-----------------\n";
            }

            var11 = var10;
            if (this instanceof Equivalence) {
                var10 = var10 + "The left hand concept of this equivalence has these details:\n";
                var10 = var10 + ((Equivalence)this).lh_concept.fullDetails("ascii", var3, var2);
            }

            if (this instanceof NearEquivalence) {
                var10 = var10 + "The left hand concept of this near_equivalence has these details:\n";
                var10 = var10 + ((NearEquivalence)this).lh_concept.fullDetails("ascii", var3, var2);
            }

            if (this instanceof NearImplication) {
                var10 = var10 + "The left hand concept of this near_implication has these details:\n";
                var10 = var10 + ((NearImplication)this).lh_concept.fullDetails("ascii", var3, var2);
            }

            if (this instanceof Implication) {
                var10 = var10 + "The left hand concept of this implication has these details:\n";
                var10 = var10 + ((Implication)this).lh_concept.fullDetails("ascii", var3, var2);
            }

            if (this instanceof Implicate) {
                var10 = var10 + "The left hand concept of this implicate has these details:\n";
                var10 = var10 + ((Implicate)this).premise_concept.fullDetails("ascii", var3, var2);
            }

            if (this instanceof NonExists) {
                var10 = var10 + "The non-existent concept has these details:\n";
                var10 = var10 + ((NonExists)this).concept.fullDetails("ascii", var3, var2);
            }
        }

        if (var4.contains("rh_concept")) {
            if (!var11.equals(var10)) {
                var10 = var10 + "-----------------\n";
            }

            if (this instanceof Equivalence) {
                var10 = var10 + "The right hand concept of this equivalence has these details:\n";
                var10 = var10 + ((Equivalence)this).rh_concept.fullDetails(var3, var2);
            }

            if (this instanceof NearEquivalence) {
                var10 = var10 + "The right hand concept of this equivalence has these details:\n";
                var10 = var10 + ((NearEquivalence)this).rh_concept.fullDetails(var3, var2);
            }

            if (this instanceof NearImplication) {
                var10 = var10 + "The right hand concept of this near_implication has these details:\n";
                var10 = var10 + ((NearImplication)this).rh_concept.fullDetails("ascii", var3, var2);
            }

            if (this instanceof Implication) {
                var10 = var10 + "The right hand concept of this equivalence has these details:\n";
                var10 = var10 + ((Implication)this).rh_concept.fullDetails(var3, var2);
            }
        }

        return var10;
    }

    public String fullHTMLDetails(String[] var1, int var2, String[] var3) {
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var1.length; ++var5) {
            var4.addElement(var1[var5]);
        }

        String var13 = "";
        if (var4.contains("id")) {
            var13 = var13 + "(" + this.id + ")";
        }

        String var6 = "";
        int var7 = 0;
        if (var4.contains("simplified format")) {
            ++var7;
            var6 = "otter";
        }

        if (var4.contains("ascii format")) {
            ++var7;
            var6 = "ascii";
        }

        if (var4.contains("otter format")) {
            ++var7;
            var6 = "otter";
        }

        if (var4.contains("prolog format")) {
            ++var7;
            var6 = "prolog";
        }

        if (var4.contains("tptp format")) {
            ++var7;
            var6 = "tptp";
        }

        String var8 = "";
        boolean var9;
        if (var7 == 1) {
            var9 = this.simplify_definitions;
            if (var4.contains("simplified format")) {
                this.simplify_definitions = true;
            }

            var13 = var13 + "<table border=1 cellpadding=3 fgcolor=white bgcolor=\"#ffff99\"><tr><td><font color=blue><b>" + this.replaceLTForHTML(this.writeConjecture(var6)) + "</b></font></td></tr></table><br>\n";
            if (var4.contains("simplified format")) {
                this.simplify_definitions = var9;
            }
        }

        if (var7 > 1) {
            var6 = "ascii";
            var13 = var13 + "<ul>\n";
            if (var4.contains("simplified format")) {
                var9 = this.simplify_definitions;
                this.simplify_definitions = true;
                var13 = var13 + "<li>" + this.replaceLTForHTML(this.writeConjecture("otter")) + "</li>\n";
                this.simplify_definitions = var9;
            }

            if (var4.contains("ascii format")) {
                var13 = var13 + "<li>" + this.replaceLTForHTML(this.writeConjecture("ascii")) + "</li>\n";
            }

            if (var4.contains("otter format")) {
                var13 = var13 + "<li>" + this.replaceLTForHTML(this.writeConjecture("otter")) + "</li>\n";
            }

            if (var4.contains("prolog format")) {
                var13 = var13 + "<li>" + this.replaceLTForHTML(this.writeConjecture("prolog")) + "</li>\n";
            }

            if (var4.contains("tptp format")) {
                var13 = var13 + "<li>" + this.replaceLTForHTML(this.writeConjecture("tptp")) + "</li>\n";
            }
        }

        if (!var13.equals("")) {
            var13 = var13 + "<hr>\n";
        }

        String var14 = var13;
        var13 = var13 + "\n<table border=0><tr><td align=center><font size=4 color=red>Details</font></td>";
        var13 = var13 + "<td align=center><font size=4 color=green>Solution</font></td></tr>\n";
        var13 = var13 + "<tr valign=top><td><table border=1><td>Measure</td><td>Value</td><td>Normalised</td></tr>\n";
        if (var4.contains("proof_status")) {
            var13 = var13 + "<tr><td>Proof status</td><td>" + this.proof_status + "</td><td></td></tr>\n";
        }

        if (var4.contains("explained_by")) {
            var13 = var13 + "<tr><td>Explained by</td><td>" + this.explained_by + "</td><td></td></tr>\n";
        }

        if (var4.contains("proof")) {
            var13 = var13 + "<tr><td>Proof length</td><td>" + this.proof_length + "</td><td></td></tr>\n";
            var13 = var13 + "<tr><td>Proof level</td><td>" + this.proof_level + "</td><td></td></tr>\n";
        }

        int var10;
        if (!this.proof_attempts_information.isEmpty()) {
            for(var10 = 0; var10 < this.proof_attempts_information.size(); ++var10) {
                String var11 = (String)this.proof_attempts_information.elementAt(var10);
                int var12 = var11.indexOf(":");
                var13 = var13 + "<tr><td>" + var11.substring(0, var12) + " time</td><td>" + var11.substring(var12 + 1, var11.lastIndexOf(":")) + "</td><td>" + var11.substring(var11.lastIndexOf(":") + 1, var11.length()) + "</td></tr>\n";
            }

            if (var4.contains("mw_max_ptime")) {
                var13 = var13 + "<tr><td>Max MathWeb proof time</td><td>" + this.decimalPlaces(this.mw_max_proof_time, var2) + "</td><td></td></tr>\n";
            }

            if (var4.contains("mw_min_ptime")) {
                var13 = var13 + "<tr><td>Min MathWeb proof time</td><td>" + this.decimalPlaces(this.mw_min_proof_time, var2) + "</td><td></td></tr>\n";
            }

            if (var4.contains("mw_av_ptime")) {
                var13 = var13 + "<tr><td>Average MathWeb proof time</td><td>" + this.decimalPlaces(this.mw_av_proof_time, var2) + "</td><td></td></tr>\n";
            }

            if (var4.contains("mw_diff_ptime")) {
                var13 = var13 + "<tr><td>Largest - smallest MathWeb proof time</td><td></td><td>" + this.decimalPlaces(this.mw_diff_proof_time, var2) + "</td></tr>\n";
            }
        }

        if (var4.contains("total score")) {
            var13 = var13 + "<tr><td>Total score</td><td>" + this.decimalPlaces(this.interestingness, var2) + "</td><td></td></tr>\n";
        }

        if (var4.contains("arity")) {
            var13 = var13 + "<tr><td>Arity</td><td>" + this.decimalPlaces(this.arity, var2) + "</td><td></td></tr>\n";
        }

        if (var4.contains("applicability")) {
            var13 = var13 + "<tr><td>Applicability</td><td>" + this.decimalPlaces(this.applicability, var2) + "</td><td>" + this.decimalPlaces(this.normalised_applicability, var2) + "</td></tr>\n";
        }

        if (var4.contains("complexity")) {
            var13 = var13 + "<tr><td>Complexity</td><td>" + this.decimalPlaces(this.complexity, var2) + "</td><td></td></tr>\n";
        }

        if (var4.contains("comprehensibility")) {
            var13 = var13 + "<tr><td>Comprehensibility</td><td>" + this.decimalPlaces(this.comprehensibility, var2) + "</td><td>" + this.decimalPlaces(this.normalised_comprehensibility, var2) + "</td></tr>\n";
        }

        if (var4.contains("surprisingness")) {
            var13 = var13 + "<tr><td>Surprisingness</td><td>" + this.decimalPlaces(this.surprisingness, var2) + "</td><td>" + this.decimalPlaces(this.normalised_surprisingness, var2) + "</td></tr>\n";
        }

        if (var4.contains("plausibility")) {
            var13 = var13 + "<tr><td>Plausibility</td><td>" + this.decimalPlaces(this.plausibility, var2) + "</td><td>" + "N/A" + "</td></tr>\n";
        }

        if (var4.contains("const time")) {
            var13 = var13 + "<tr><td>Constructed after</td><td>" + Long.toString(this.when_constructed) + " seconds</td><td></tr></tr>\n";
        }

        if (var4.contains("step")) {
            var13 = var13 + "<tr><td>Construction step</td><td>" + this.step.asString() + "</td><td></td></tr>\n";
        }

        var13 = var13 + "</table>\n";
        var13 = var13 + "</td><td>";
        if (this.proof_attempts_information.isEmpty() && var4.contains("prover_details")) {
            var13 = var13 + "There have been no proof attempts on this conjecture.\n";
        }

        if (var4.contains("counter") && !this.counterexamples.isEmpty()) {
            if (this.counterexamples.size() == 1) {
                var13 = var13 + "The counterexample to this conjecture is:<p> ";
            } else {
                var13 = var13 + "The counterexamples to this conjecture are:<p> ";
            }

            for(var10 = 0; var10 < this.counterexamples.size(); ++var10) {
                Entity var15 = (Entity)this.counterexamples.elementAt(var10);
                var13 = var13 + var15.name + "<br>";
            }

            var13 = var13 + "<p>";
        }

        if (var4.contains("proof")) {
            if (this.proof.equals("")) {
                if (this.proof_status.equals("proved") && !this.is_trivially_true) {
                    var13 = var13 + "The proof was not recorded.\n";
                }
            } else if (!this.is_trivially_true) {
                var13 = var13 + "Proof:\n";
                var13 = var13 + "<pre>\n" + this.proof + "</pre>";
            }
        }

        var13 = var13 + "</td></tr></table>\n";
        if (var4.contains("lh_concept")) {
            if (!var14.equals(var13)) {
                var13 = var13 + "<hr>\n";
            }

            var14 = var13;
            var13 = var13 + "<font size=5 color=red>\n";
            if (this instanceof Equivalence) {
                var13 = var13 + "Left hand concept of this equivalence:</font><p>\n";
                var13 = var13 + ((Equivalence)this).lh_concept.fullDetails("html", var3, var2);
            }

            if (this instanceof NearEquivalence) {
                var13 = var13 + "Left hand concept of this near equivalence:</font><p>\n";
                var13 = var13 + ((NearEquivalence)this).lh_concept.fullDetails("html", var3, var2);
            }

            if (this instanceof NearImplication) {
                var13 = var13 + "Left hand concept of this near implication:</font><p>\n";
                var13 = var13 + ((NearImplication)this).lh_concept.fullDetails("html", var3, var2);
            }

            if (this instanceof Implication) {
                var13 = var13 + "Left hand concept of this implication:</font><p>\n";
                var13 = var13 + ((Implication)this).lh_concept.fullDetails("html", var3, var2);
            }

            if (this instanceof Implicate) {
                var13 = var13 + "Left hand concept of this implicate:</font><p>\n";
                var13 = var13 + ((Implicate)this).premise_concept.fullDetails("html", var3, var2);
            }

            if (this instanceof NonExists) {
                var13 = var13 + "Non-existent concept:</font><p>:\n";
                var13 = var13 + ((NonExists)this).concept.fullDetails("html", var3, var2);
            }
        }

        if (var4.contains("rh_concept")) {
            if (!var14.equals(var13)) {
                var13 = var13 + "<hr>\n";
            }

            var13 = var13 + "<font size=5 color=red>\n";
            if (this instanceof Equivalence) {
                var13 = var13 + "Right hand concept of this equivalence:</font><p>\n";
                var13 = var13 + ((Equivalence)this).rh_concept.fullDetails("html", var3, var2);
            }

            if (this instanceof NearEquivalence) {
                var13 = var13 + "Right hand concept of this near equivalence:</font><p>\n";
                var13 = var13 + ((NearEquivalence)this).rh_concept.fullDetails("html", var3, var2);
            }

            if (this instanceof NearImplication) {
                var13 = var13 + "Right hand concept of this near implication:</font><p>\n";
                var13 = var13 + ((NearImplication)this).rh_concept.fullDetails("html", var3, var2);
            }

            if (this instanceof Implication) {
                var13 = var13 + "Right hand concept of this implication:</font><p>\n";
                var13 = var13 + ((Implication)this).rh_concept.fullDetails("html", var3, var2);
            }

            if (!(this instanceof Equivalence) && !(this instanceof NearEquivalence) && !(this instanceof NearImplication) && !(this instanceof Implication)) {
                var13 = var13 + "</font>\n";
            }
        }

        return var13;
    }

    public boolean decideForced() {
        if (this instanceof Equivalence) {
            return ((Equivalence)this).rh_concept.construction.forced;
        } else if (this instanceof NearEquivalence) {
            return ((NearEquivalence)this).rh_concept.construction.forced;
        } else if (this instanceof NearImplication) {
            return ((NearImplication)this).rh_concept.construction.forced;
        } else if (this instanceof Implication) {
            return ((Implication)this).rh_concept.construction.forced;
        } else {
            return this instanceof NonExists ? ((NonExists)this).concept.construction.forced : false;
        }
    }

    public boolean subsumes(Conjecture var1) {
        return false;
    }

    public void checkSegregation(Agenda var1) {
        if (this instanceof NearEquivalence) {
            NearEquivalence var2 = (NearEquivalence)this;
            this.involves_segregated_concepts = var1.conceptsAreSegregated(var2.lh_concept, var2.rh_concept);
        }

        if (this instanceof NearImplication) {
            NearImplication var3 = (NearImplication)this;
            this.involves_segregated_concepts = var1.conceptsAreSegregated(var3.lh_concept, var3.rh_concept);
        }

        if (this instanceof Equivalence) {
            Equivalence var4 = (Equivalence)this;
            this.involves_segregated_concepts = var1.conceptsAreSegregated(var4.lh_concept, var4.rh_concept);
        }

        if (this instanceof Implication) {
            Implication var5 = (Implication)this;
            this.involves_segregated_concepts = var1.conceptsAreSegregated(var5.lh_concept, var5.rh_concept);
        }

    }

    public boolean conjectureBrokenByEntity(String var1, Vector var2) {
        System.out.println("starting conjectureBrokenByEntity");
        System.out.println("conj is:" + this.writeConjecture());
        System.out.println("entity is:" + var1);
        System.out.println("I'm returning false...");
        Row var4;
        Row var5;
        if (this instanceof Equivalence) {
            Equivalence var3 = (Equivalence)this;
            var4 = var3.lh_concept.calculateRow(var2, var1);
            var5 = var3.rh_concept.calculateRow(var2, var1);
            if (var4.tuples.toString().equals(var5.tuples.toString())) {
                return false;
            }
        }

        if (this instanceof NearEquivalence) {
            NearEquivalence var6 = (NearEquivalence)this;
            var4 = var6.lh_concept.calculateRow(var2, var1);
            var5 = var6.rh_concept.calculateRow(var2, var1);
            if (var4.tuples.toString().equals(var5.tuples.toString())) {
                return false;
            }
        }

        if (this instanceof Implication) {
            Implication var7 = (Implication)this;
            var4 = var7.lh_concept.calculateRow(var2, var1);
            var5 = var7.rh_concept.calculateRow(var2, var1);
            if (var4.tuples.isEmpty()) {
                return false;
            }

            if (var4.tuples.toString().equals(var5.tuples.toString())) {
                return false;
            }
        }

        if (this instanceof NearImplication) {
            NearImplication var8 = (NearImplication)this;
            var4 = var8.lh_concept.calculateRow(var2, var1);
            var5 = var8.rh_concept.calculateRow(var2, var1);
            if (var4.tuples.isEmpty()) {
                return false;
            }

            if (var4.tuples.toString().equals(var5.tuples.toString())) {
                return false;
            }
        }

        if (this instanceof NonExists) {
            NonExists var9 = (NonExists)this;
            var4 = var9.concept.calculateRow(var2, var1);
            if (var4.tuples.isEmpty()) {
                return false;
            }
        }

        if (this instanceof Implicate) {
            if (this.parent_conjectures.isEmpty()) {
                return false;
            } else {
                Conjecture var10 = (Conjecture)this.parent_conjectures.elementAt(0);
                return var10.conjectureBrokenByEntity(var1, var2);
            }
        } else {
            System.out.println("...except here -- i'm now returning true");
            return true;
        }
    }

    public Conjecture reconstructConjecture(Theory var1, AgentWindow var2) {
        String var3 = (new Integer(var1.conjectures.size())).toString();

        for(int var4 = 0; var4 < var1.conjectures.size(); ++var4) {
            Conjecture var5 = (Conjecture)var1.conjectures.elementAt(var4);
            if (this.writeConjecture("ascii").equals(var5.writeConjecture("ascii"))) {
                return var5;
            }
        }

        Concept var8;
        if (this instanceof NearImplication || this instanceof NearEquivalence) {
            Concept var11 = new Concept();
            Concept var12 = new Concept();
            if (this instanceof NearImplication) {
                var11 = ((NearImplication)this).lh_concept;
                var12 = ((NearImplication)this).rh_concept;
            }

            if (this instanceof NearEquivalence) {
                var11 = ((NearEquivalence)this).lh_concept;
                var12 = ((NearEquivalence)this).rh_concept;
            }

            for(int var6 = 0; var6 < var1.conjectures.size(); ++var6) {
                Conjecture var7 = (Conjecture)var1.conjectures.elementAt(var6);
                new Concept();
                new Concept();
                Concept var9;
                if (var7 instanceof Implication) {
                    var8 = ((Implication)var7).lh_concept;
                    var9 = ((Implication)var7).rh_concept;
                    if (var8.equals(var11) && var9.equals(var12)) {
                        return var7;
                    }

                    if (var8.equals(var12) && var9.equals(var11)) {
                        return var7;
                    }
                }

                if (var7 instanceof Equivalence) {
                    var8 = ((Equivalence)var7).lh_concept;
                    var9 = ((Equivalence)var7).rh_concept;
                    if (var8.equals(var11) && var9.equals(var12)) {
                        return var7;
                    }

                    if (var8.equals(var12) && var9.equals(var11)) {
                        return var7;
                    }
                }
            }
        }

        Object var13 = null;
        Object var16 = null;
        Conjecture var14 = new Conjecture();
        int var15;
        if (this instanceof Implicate) {
            for(var15 = 0; var15 < var1.concepts.size(); ++var15) {
                var8 = (Concept)var1.concepts.elementAt(var15);
                if (var8.writeDefinition("ascii").equals(((Implicate)this).premise_concept.writeDefinition("ascii"))) {
                    var13 = var8;
                }
            }

            if (var13 == null) {
                var13 = ((Implicate)this).premise_concept.reconstructConcept(var1, var2);
            }
        }

        Concept var17;
        NonExists var18;
        if (this instanceof NonExists) {
            for(var15 = 0; var15 < var1.concepts.size(); ++var15) {
                var8 = (Concept)var1.concepts.elementAt(var15);
                if (var8.writeDefinition("ascii").equals(((NonExists)this).concept.writeDefinition("ascii"))) {
                    var18 = new NonExists(var8, var3);
                    var18.counterexamples = var18.getCountersToConjecture();
                    return var18;
                }
            }

            var13 = ((NonExists)this).concept.reconstructConcept(var1, var2);
            if (var13 instanceof Concept) {
                var17 = (Concept)var13;
                NonExists var22 = new NonExists(var17, var3);
                var22.counterexamples = var22.getCountersToConjecture();
                return var22;
            }

            if (var13 instanceof Equivalence) {
                Equivalence var21 = (Equivalence)var13;
                var8 = var21.rh_concept;
                var18 = new NonExists(var8, var3);
                var18.counterexamples = var18.getCountersToConjecture();
                return var18;
            }

            if (var13 instanceof Implication) {
                Implication var20 = (Implication)var13;
                var8 = var20.rh_concept;
                var18 = new NonExists(var8, var3);
                var18.counterexamples = var18.getCountersToConjecture();
                return var18;
            }

            if (var13 instanceof NonExists) {
                return (NonExists)var13;
            }
        }

        Concept var10;
        int var19;
        Implication var25;
        if (this instanceof Implication) {
            var17 = ((Implication)this).lh_concept;
            var8 = ((Implication)this).rh_concept;

            for(var19 = 0; var19 < var1.concepts.size(); ++var19) {
                var10 = (Concept)var1.concepts.elementAt(var19);
                if (var10.writeDefinition("ascii").equals(var17.writeDefinition("ascii"))) {
                    var13 = var10;
                }

                if (var10.writeDefinition("ascii").equals(var8.writeDefinition("ascii"))) {
                    var16 = var10;
                }
            }

            if (var13 == null) {
                var13 = var17.reconstructConcept(var1, var2);
            }

            if (var16 == null) {
                var16 = var8.reconstructConcept(var1, var2);
            }

            if (var13 instanceof Concept && var16 instanceof Concept) {
                var25 = new Implication((Concept)var13, (Concept)var16, var3);
                var25.counterexamples = var25.getCountersToConjecture();
                if (var25.counterexamples.isEmpty()) {
                    return var25;
                }

                return new NearImplication((Concept)var13, (Concept)var16, var25.counterexamples, 0.0D);
            }
        }

        Equivalence var26;
        if (this instanceof Equivalence) {
            var17 = ((Equivalence)this).lh_concept;
            var8 = ((Equivalence)this).rh_concept;

            for(var19 = 0; var19 < var1.concepts.size(); ++var19) {
                var10 = (Concept)var1.concepts.elementAt(var19);
                if (var10.writeDefinition("ascii").equals(var17.writeDefinition("ascii"))) {
                    var13 = var10;
                }

                if (var10.writeDefinition("ascii").equals(var8.writeDefinition("ascii"))) {
                    var16 = var10;
                }
            }

            if (var13 == null) {
                var13 = var17.reconstructConcept(var1, var2);
            }

            if (var16 == null) {
                var16 = var8.reconstructConcept(var1, var2);
            }

            if (var13 instanceof Concept && var16 instanceof Concept) {
                var26 = new Equivalence((Concept)var13, (Concept)var16, var3);
                Vector var24 = var26.getCountersToConjecture();
                if (var24.isEmpty()) {
                    return var26;
                }

                return new NearEquivalence((Concept)var13, (Concept)var16, var24, 0.0D);
            }

            Equivalence var23;
            if (var13 instanceof NonExists && var16 instanceof Concept) {
                var18 = (NonExists)var13;
                var23 = new Equivalence(var18.concept, (Concept)var16, var3);
                var23.counterexamples = var23.getCountersToConjecture();
                if (var23.counterexamples.isEmpty()) {
                    return var23;
                }

                return new NearEquivalence(var18.concept, (Concept)var16, var23.counterexamples, 0.0D);
            }

            if (var13 instanceof Concept && var16 instanceof NonExists) {
                var18 = (NonExists)var16;
                var23 = new Equivalence((Concept)var13, var18.concept, var3);
                var23.counterexamples = var23.getCountersToConjecture();
                if (var23.counterexamples.isEmpty()) {
                    return var23;
                }

                return new NearEquivalence((Concept)var13, var18.concept, var23.counterexamples, 0.0D);
            }
        }

        if (this instanceof NearEquivalence) {
            var17 = ((NearEquivalence)this).lh_concept;
            var8 = ((NearEquivalence)this).rh_concept;

            for(var19 = 0; var19 < var1.concepts.size(); ++var19) {
                var10 = (Concept)var1.concepts.elementAt(var19);
                if (var10.writeDefinition("ascii").equals(var17.writeDefinition("ascii"))) {
                    var13 = var10;
                }

                if (var10.writeDefinition("ascii").equals(var8.writeDefinition("ascii"))) {
                    var16 = var10;
                }
            }

            if (var13 == null) {
                var13 = var17.reconstructConcept(var1, var2);
            }

            if (var16 == null) {
                var16 = var8.reconstructConcept(var1, var2);
            }

            if (var13 instanceof Concept && var16 instanceof Concept) {
                var26 = new Equivalence((Concept)var13, (Concept)var16, var3);
                var26.counterexamples = var26.getCountersToConjecture();
                if (var26.counterexamples.isEmpty()) {
                    return var26;
                }

                return new NearEquivalence((Concept)var13, (Concept)var16, var26.counterexamples, 0.0D);
            }
        }

        if (this instanceof NearImplication) {
            var17 = ((NearImplication)this).lh_concept;
            var8 = ((NearImplication)this).rh_concept;

            for(var19 = 0; var19 < var1.concepts.size(); ++var19) {
                var10 = (Concept)var1.concepts.elementAt(var19);
                if (var10.writeDefinition("ascii").equals(var17.writeDefinition("ascii"))) {
                    var13 = var10;
                }

                if (var10.writeDefinition("ascii").equals(var8.writeDefinition("ascii"))) {
                    var16 = var10;
                }
            }

            if (var13 == null) {
                var13 = var17.reconstructConcept(var1, var2);
            }

            if (var16 == null) {
                var16 = var8.reconstructConcept(var1, var2);
            }

            if (var13 instanceof Concept && var16 instanceof Concept) {
                var25 = new Implication((Concept)var13, (Concept)var16, var3);
                var25.counterexamples = var25.getCountersToConjecture();
                if (var25.counterexamples.isEmpty()) {
                    return var25;
                }

                return new NearImplication((Concept)var13, (Concept)var16, var25.counterexamples, 0.0D);
            }
        }

        if (var13 instanceof Conjecture) {
            return (Conjecture)var13;
        } else {
            return var16 instanceof Conjecture ? (Conjecture)var16 : var14;
        }
    }

    public Conjecture reconstructConjectureWithNewEntity(Theory var1, AgentWindow var2, Entity var3) {
        var2.writeToFrontEnd("started reconstructConjectureWithNewEntity");
        var2.writeToFrontEnd("initially the conj is " + this.writeConjecture("ascii"));
        new Conjecture();
        Vector var5 = this.conceptsInConjecture();
        Concept var6 = new Concept();

        for(int var7 = 0; var7 < var1.concepts.size(); ++var7) {
            Concept var8 = (Concept)var1.concepts.elementAt(var7);
            if (var8.is_object_of_interest_concept) {
                var6 = var8;
                break;
            }
        }

        Vector var14 = var1.categorisations;
        var1.categorisations.removeAllElements();
        new Vector();
        Hashtable var9 = new Hashtable();

        for(int var10 = 0; var10 < var5.size(); ++var10) {
            Concept var11 = (Concept)var5.elementAt(var10);
            if (var11.domain.equals(var6.domain)) {
                var11.updateDatatable(var5, var3);
                String var12 = var11.datatable.firstTuples();
                var1.addCategorisation(var11);
                Vector var13 = (Vector)var9.get(var12);
                if (var13 != null) {
                    var13.addElement(var11);
                } else {
                    var13 = new Vector();
                    var13.addElement(var11);
                    var9.put(var12, var13);
                }
            }
        }

        var1.categorisations = var14;
        var2.writeToFrontEnd("now however the conj is " + this.reconstructConjecture(var1, var2).writeConjecture("ascii"));
        var2.writeToFrontEnd("finished reconstructConjectureWithNewEntity");
        return this.reconstructConjecture(var1, var2);
    }

    public boolean equals(Conjecture var1) {
        if (this.writeConjecture("ascii").equals("") && var1.writeConjecture("ascii").equals("")) {
            return true;
        } else if (this instanceof Equivalence && var1 instanceof Equivalence) {
            Equivalence var9 = (Equivalence)this;
            Equivalence var13 = (Equivalence)var1;
            if (!var9.lh_concept.writeDefinition("ascii").equals(var13.lh_concept.writeDefinition("ascii"))) {
                return false;
            } else {
                return var9.rh_concept.writeDefinition("ascii").equals(var13.rh_concept.writeDefinition("ascii"));
            }
        } else if (this instanceof NearEquivalence && var1 instanceof NearEquivalence) {
            NearEquivalence var7 = (NearEquivalence)this;
            NearEquivalence var12 = (NearEquivalence)var1;
            if (!var7.lh_concept.writeDefinition("ascii").equals(var12.lh_concept.writeDefinition("ascii"))) {
                return false;
            } else {
                return var7.rh_concept.writeDefinition("ascii").equals(var12.rh_concept.writeDefinition("ascii"));
            }
        } else if (this instanceof NearImplication && var1 instanceof NearImplication) {
            NearImplication var6 = (NearImplication)this;
            NearImplication var11 = (NearImplication)var1;
            if (!var6.lh_concept.writeDefinition("ascii").equals(var11.lh_concept.writeDefinition("ascii"))) {
                return false;
            } else {
                return var6.rh_concept.writeDefinition("ascii").equals(var11.rh_concept.writeDefinition("ascii"));
            }
        } else if (this instanceof Implication && var1 instanceof Implication) {
            Implication var5 = (Implication)this;
            Implication var10 = (Implication)var1;
            if (!var5.lh_concept.writeDefinition("ascii").equals(var10.lh_concept.writeDefinition("ascii"))) {
                return false;
            } else {
                return var5.rh_concept.writeDefinition("ascii").equals(var10.rh_concept.writeDefinition("ascii"));
            }
        } else if (this instanceof Implicate && var1 instanceof Implicate) {
            Implicate var4 = (Implicate)this;
            Implicate var8 = (Implicate)var1;
            if (!var4.premise_concept.writeDefinition("ascii").equals(var8.premise_concept.writeDefinition("ascii"))) {
                return false;
            } else {
                return var4.goal == var8.goal;
            }
        } else if (this instanceof NonExists && var1 instanceof NonExists) {
            NonExists var2 = (NonExists)this;
            NonExists var3 = (NonExists)var1;
            return var2.concept.writeDefinition("ascii").equals(var3.concept.writeDefinition("ascii"));
        } else {
            return false;
        }
    }

    public boolean equals(String var1) {
        String var2 = this.writeConjecture("ascii");
        if (var1.equals("") && var2.equals("")) {
            return true;
        } else {
            return var1.equals(var2);
        }
    }

    public boolean equals(Object var1) {
        if (var1 instanceof String) {
            return this.equals((String)var1);
        } else if (var1 instanceof Conjecture) {
            return this.equals((Conjecture)var1);
        } else {
            System.out.println("Warning: Conjecture equals method - not a conjecture or string");
            return false;
        }
    }

    public Vector conceptsInConjecture() {
        Vector var1 = new Vector();
        Concept var2 = new Concept();
        Concept var3 = new Concept();
        if (this instanceof Equivalence) {
            Equivalence var4 = (Equivalence)this;
            var2 = var4.lh_concept;
            var3 = var4.rh_concept;
        }

        if (this instanceof NearEquivalence) {
            NearEquivalence var5 = (NearEquivalence)this;
            var2 = var5.lh_concept;
            var3 = var5.rh_concept;
        }

        if (this instanceof Implication) {
            Implication var6 = (Implication)this;
            var2 = var6.lh_concept;
            var3 = var6.rh_concept;
        }

        if (this instanceof NearImplication) {
            NearImplication var7 = (NearImplication)this;
            var2 = var7.lh_concept;
            var3 = var7.rh_concept;
        }

        if (this instanceof NonExists) {
            NonExists var8 = (NonExists)this;
            var2 = var8.concept;
        }

        if (this instanceof Implicate) {
            Implicate var9 = (Implicate)this;
            var2 = var9.premise_concept;
        }

        var1.addElement(var2);
        if (!var3.writeDefinition("ascii").equals("")) {
            var1.addElement(var3);
        }

        return var1;
    }

    public Vector getCountersToConjecture() {
        Vector var1 = new Vector();
        if (this instanceof Equivalence) {
            return ((Equivalence)this).knownCounterexamples();
        } else if (this instanceof Implication) {
            return ((Implication)this).knownCounterexamples();
        } else if (!(this instanceof NearEquivalence) && !(this instanceof NearImplication)) {
            if (this instanceof NonExists) {
                NonExists var2 = (NonExists)this;
                var1 = var2.concept.positiveEntities();
            }

            return var1;
        } else {
            return this.counterexamples;
        }
    }

    public boolean relatesConcepts(Vector var1, Theory var2) {
        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Concept var4 = (Concept)var1.elementAt(var3);
        }

        boolean var11 = true;
        Vector var12 = this.conceptsInConjecture();

        for(int var5 = 0; var5 < var12.size(); ++var5) {
            Concept var6 = (Concept)var12.elementAt(var5);
            if (!var1.contains(var6)) {
                Vector var7 = var6.getAncestors(var2);
                boolean var8 = false;

                for(int var9 = 0; var9 < var7.size(); ++var9) {
                    Concept var10 = (Concept)var7.elementAt(var9);
                    if (var1.contains(var10)) {
                        var8 = true;
                        break;
                    }
                }

                if (!var8) {
                    return false;
                }
            }
        }

        return var11;
    }

    public boolean relatesAllConcepts(Vector var1, Theory var2) {
        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Concept var4 = (Concept)var1.elementAt(var3);
        }

        Vector var19 = this.conceptsInConjecture();

        for(int var20 = 0; var20 < var19.size(); ++var20) {
            Concept var5 = (Concept)var19.elementAt(var20);
        }

        boolean var21 = true;

        for(int var22 = 0; var22 < var1.size(); ++var22) {
            Concept var6 = (Concept)var1.elementAt(var22);
            Vector var7 = var6.definition_writer.lettersForTypes(var6.types, "ascii", new Vector());

            for(int var8 = 0; var8 < var19.size(); ++var8) {
                Concept var9 = (Concept)var19.elementAt(var8);
                Vector var10 = var9.definition_writer.lettersForTypes(var9.types, "ascii", new Vector());
                if (var10.size() == var7.size()) {
                    String var12;
                    if (var10.equals(var7)) {
                        if (var9.writeDefinition().equals(var6.writeDefinition())) {
                            break;
                        }

                        String var11 = var6.writeDefinition();
                        var12 = this.writeConjecture();
                        boolean var13 = false;
                        if (var12.length() > var11.length()) {
                            int var14 = var11.length();
                            int var15 = var12.length() - var11.length();

                            for(int var16 = 0; var16 < var15; ++var16) {
                                String var17 = var12.substring(var16, var16 + var14);
                                var17.equals(var11);
                                if (var17.equals(var11)) {
                                    var13 = true;
                                    break;
                                }
                            }

                            if (!var13) {
                                var21 = false;
                            }
                        }
                    } else {
                        for(int var23 = 0; var23 < var7.size(); ++var23) {
                            var12 = (String)var7.elementAt(var23);
                            var10.removeElementAt(var23);
                            var10.insertElementAt(var12, var23);
                        }

                        if (var9.writeDefinition().equals(var6.writeDefinition())) {
                            break;
                        }

                        var21 = false;
                    }
                } else {
                    var21 = false;
                }
            }

            if (!var21) {
                break;
            }
        }

        return var21;
    }

    public boolean theoryContainsConjecture(Vector var1) {
        for(int var2 = 0; var2 < var1.size(); ++var2) {
            Conjecture var3 = (Conjecture)var1.elementAt(var2);
            if (this.writeConjecture().equals(var3.writeConjecture())) {
                return true;
            }
        }

        return false;
    }

    public Conjecture reconstructConjecture_old(Theory var1, AgentWindow var2) {
        String var3 = (new Integer(var1.conjectures.size())).toString();

        for(int var4 = 0; var4 < var1.conjectures.size(); ++var4) {
            Conjecture var5 = (Conjecture)var1.conjectures.elementAt(var4);
            if (this.writeConjecture("ascii").equals(var5.writeConjecture("ascii"))) {
                return var5;
            }
        }

        Object var13 = new TheoryConstituent();
        Object var14 = new TheoryConstituent();
        Object var6 = new Conjecture();
        new Vector();
        int var8;
        Concept var9;
        if (this instanceof Implicate) {
            for(var8 = 0; var8 < var1.concepts.size(); ++var8) {
                var9 = (Concept)var1.concepts.elementAt(var8);
                if (var9.writeDefinition("ascii").equals(((Implicate)this).premise_concept.writeDefinition("ascii"))) {
                    var13 = var9;
                }
            }

            if (((TheoryConstituent)var13).equals("null")) {
                var13 = ((Implicate)this).premise_concept.reconstructConcept(var1, var2);
            }
        }

        Concept var17;
        if (this instanceof NonExists) {
            for(var8 = 0; var8 < var1.concepts.size(); ++var8) {
                var9 = (Concept)var1.concepts.elementAt(var8);
                if (var9.writeDefinition("ascii").equals(((NonExists)this).concept.writeDefinition("ascii"))) {
                    var13 = var9;
                }
            }

            if (((TheoryConstituent)var13).equals("null")) {
                var13 = ((NonExists)this).concept.reconstructConcept(var1, var2);
            }

            if (var13 instanceof Concept) {
                var17 = (Concept)var13;
                NonExists var22 = new NonExists(var17, var3);
                var22.counterexamples = var22.getCountersToConjecture();
                return var22;
            }

            if (var13 instanceof Conjecture) {
                Conjecture var16 = (Conjecture)var13;
                NonExists var26;
                Concept var28;
                if (var16 instanceof Equivalence) {
                    Equivalence var21 = (Equivalence)var16;
                    var28 = var21.rh_concept;
                    var26 = new NonExists(var28, var3);
                    var26.counterexamples = var26.getCountersToConjecture();
                    return var26;
                }

                if (var16 instanceof Implication) {
                    Implication var19 = (Implication)var16;
                    var28 = var19.rh_concept;
                    var26 = new NonExists(var28, var3);
                    var26.counterexamples = var26.getCountersToConjecture();
                    return var26;
                }
            }
        }

        int var10;
        Concept var11;
        if (this instanceof Implication) {
            var17 = ((Implication)this).lh_concept;
            var9 = ((Implication)this).rh_concept;

            for(var10 = 0; var10 < var1.concepts.size(); ++var10) {
                var11 = (Concept)var1.concepts.elementAt(var10);
                if (var11.writeDefinition("ascii").equals(var17.writeDefinition("ascii"))) {
                    var13 = var11;
                }

                if (var11.writeDefinition("ascii").equals(var9.writeDefinition("ascii"))) {
                    var14 = var11;
                }
            }

            if (((TheoryConstituent)var13).equals("null")) {
                var13 = var17.reconstructConcept(var1, var2);
            }

            if (((TheoryConstituent)var14).equals("null")) {
                var14 = var9.reconstructConcept(var1, var2);
            }
        }

        if (this instanceof Equivalence) {
            var17 = ((Equivalence)this).lh_concept;
            System.out.println(" left_concept ancestors are: " + var17.construction_history);
            var9 = ((Equivalence)this).rh_concept;
            System.out.println(" right_concept ancestors are: " + var9.construction_history);
            System.out.println("trying to reconstruct an equiv");
            System.out.println("left_concept is " + var17.writeDefinition("ascii"));
            System.out.println("ESP INTERESTED IN THIS ONE: right_concept is " + var9.writeDefinition("ascii"));
            System.out.println("looking to see whether got them already...");

            for(var10 = 0; var10 < var1.concepts.size(); ++var10) {
                var11 = (Concept)var1.concepts.elementAt(var10);
                System.out.println("current concept is " + var11.writeDefinition("ascii"));
                if (var11.writeDefinition("ascii").equals(var17.writeDefinition("ascii"))) {
                    System.out.println("this is same as the left concept, so setting tc1 to it");
                    var13 = var11;
                    if (var11.equals("null")) {
                        System.out.println("tc1 is still null (odd...)");
                    } else {
                        System.out.println("tc1 isn't null now");
                    }
                }

                if (var11.writeDefinition("ascii").equals(var9.writeDefinition("ascii"))) {
                    System.out.println("we'd expect this to be true at some point...");
                    System.out.println("this is same as the right concept, so setting tc2 to it");
                    var14 = var11;
                    if (var11.equals("null")) {
                        System.out.println("tc2 is still null (odd...)");
                    } else {
                        System.out.println("tc2 isn't null now");
                    }
                }
            }

            boolean var18 = ((TheoryConstituent)var13).equals("null");
            boolean var20 = ((TheoryConstituent)var14).equals("null");
            if (var18) {
                var13 = var17.reconstructConcept(var1, var2);
            }

            if (var20) {
                var14 = var9.reconstructConcept(var1, var2);
            }

            if (!var18 && !var20) {
                if (var13 instanceof Concept && var14 instanceof Concept) {
                    System.out.println(" left_concept tc1 ancestors are: " + ((Concept)var13).construction_history);
                    System.out.println(" right_concept tc1 ancestors are: " + ((Concept)var14).construction_history);
                }

                Equivalence var12 = new Equivalence((Concept)var13, (Concept)var14, "dummy");
                System.out.println("new_equiv is " + var12.writeConjecture());
            }
        }

        if (this instanceof NearEquivalence) {
            var17 = ((NearEquivalence)this).lh_concept;
            var9 = ((NearEquivalence)this).rh_concept;

            for(var10 = 0; var10 < var1.concepts.size(); ++var10) {
                var11 = (Concept)var1.concepts.elementAt(var10);
                if (var11.writeDefinition("ascii").equals(var17.writeDefinition("ascii"))) {
                    var13 = var11;
                }

                if (var11.writeDefinition("ascii").equals(var9.writeDefinition("ascii"))) {
                    var14 = var11;
                }
            }

            if (((TheoryConstituent)var13).equals("null")) {
                System.out.println(" left_concept tc1 ancestors are: " + ((Concept)var13).construction_history);
                var13 = var17.reconstructConcept(var1, var2);
                System.out.println(" left_concept tc1 ancestors are: " + ((Concept)var13).construction_history);
            }

            if (((TheoryConstituent)var14).equals("null")) {
                System.out.println(" right_concept tc1 ancestors are: " + ((Concept)var14).construction_history);
                var14 = var9.reconstructConcept(var1, var2);
                System.out.println(" right_concept tc1 ancestors are: " + ((Concept)var14).construction_history);
            }
        }

        if (this instanceof NearImplication) {
            var17 = ((NearImplication)this).lh_concept;
            var9 = ((NearImplication)this).rh_concept;

            for(var10 = 0; var10 < var1.concepts.size(); ++var10) {
                var11 = (Concept)var1.concepts.elementAt(var10);
                if (var11.writeDefinition("ascii").equals(var17.writeDefinition("ascii"))) {
                    var13 = var11;
                }

                if (var11.writeDefinition("ascii").equals(var9.writeDefinition("ascii"))) {
                    var14 = var11;
                }
            }

            if (((TheoryConstituent)var13).equals("null")) {
                var13 = var17.reconstructConcept(var1, var2);
            }

            if (((TheoryConstituent)var14).equals("null")) {
                var14 = var9.reconstructConcept(var1, var2);
            }
        }

        Conjecture var15;
        if (var13 instanceof Conjecture) {
            return var15 = (Conjecture)var13;
        } else if (var14 instanceof Conjecture) {
            return var15 = (Conjecture)var14;
        } else if (var13 instanceof Concept && var14 instanceof Concept) {
            var17 = (Concept)var13;
            var9 = (Concept)var14;
            System.out.println(" tc1_concept ancestors are: " + var17.construction_history);
            System.out.println(" tc2_concept ancestors are: " + var9.construction_history);
            Vector var7 = var17.getConjectures(var1, var9);

            for(var10 = 0; var10 < var7.size(); ++var10) {
                ;
            }

            for(var10 = 0; var10 < var7.size(); ++var10) {
                Conjecture var24 = (Conjecture)var7.elementAt(var10);
                if (this instanceof Implicate && var24 instanceof Implicate) {
                    return (Implicate)var24;
                }

                if (this instanceof NonExists && var24 instanceof NonExists) {
                    return (NonExists)var24;
                }

                if (this instanceof Equivalence && var24 instanceof Equivalence) {
                    return (Equivalence)var24;
                }

                NearEquivalence var23;
                if (this instanceof Equivalence && var24 instanceof NearEquivalence) {
                    var23 = (NearEquivalence)var24;
                    if (((Equivalence)this).lh_concept.writeDefinition("ascii").equals(var23.lh_concept.writeDefinition("ascii"))) {
                        if (!var23.counterexamples.isEmpty()) {
                            return (NearEquivalence)var24;
                        }

                        var6 = new Equivalence(var23.lh_concept, var23.rh_concept, var3);
                    }
                }

                if (this instanceof NearEquivalence && var24 instanceof Equivalence) {
                    return (Equivalence)var24;
                }

                if (this instanceof NearEquivalence && var24 instanceof NearEquivalence) {
                    return (NearEquivalence)var24;
                }

                Implication var25;
                if (this instanceof Implication && var24 instanceof Implication) {
                    var25 = (Implication)var24;
                    if (((Implication)this).lh_concept.writeDefinition("ascii").equals(var25.lh_concept.writeDefinition("ascii"))) {
                        return var25;
                    }
                }

                if (this instanceof NearImplication && var24 instanceof Implication) {
                    var25 = (Implication)var24;
                    if (((NearImplication)this).lh_concept.writeDefinition("ascii").equals(var25.lh_concept.writeDefinition("ascii"))) {
                        return var25;
                    }
                }

                NearImplication var27;
                if (this instanceof Implication && var24 instanceof NearImplication) {
                    var27 = (NearImplication)var24;
                    if (((Implication)this).lh_concept.writeDefinition("ascii").equals(var27.lh_concept.writeDefinition("ascii"))) {
                        var6 = var27;
                    }
                }

                if (this instanceof Implication && var24 instanceof NearEquivalence) {
                    var23 = (NearEquivalence)var24;
                    if (((Implication)this).lh_concept.writeDefinition("ascii").equals(var23.lh_concept.writeDefinition("ascii"))) {
                        if (var23.counterexamples.isEmpty()) {
                            var6 = new Implication(var23.lh_concept, var23.rh_concept, var3);
                        } else {
                            var6 = var23;
                        }
                    }
                }

                if (this instanceof NearImplication && var24 instanceof NearImplication) {
                    var27 = (NearImplication)var24;
                    if (((NearImplication)this).lh_concept.writeDefinition("ascii").equals(var27.lh_concept.writeDefinition("ascii"))) {
                        var6 = var27;
                    }
                }
            }

            return (Conjecture)var6;
        } else {
            return (Conjecture)var6;
        }
    }

    public String printConjectureInformation(MeasureConjecture var1) {
        byte var2 = 2;
        String var3 = "\n\n" + this.id + ": " + this.writeConjecture();
        var3 = var3 + "\nThis conjecture discusses these entities: ";
        if (this instanceof Equivalence) {
            Equivalence var4 = (Equivalence)this;
            var3 = var3 + var1.removeDuplicates(var4.lh_concept.getPositives(), var4.rh_concept.getPositives());
        }

        if (this instanceof NearEquivalence) {
            NearEquivalence var6 = (NearEquivalence)this;
            var3 = var3 + var1.removeDuplicates(var6.lh_concept.getPositives(), var6.rh_concept.getPositives());
        }

        if (this instanceof Implication) {
            Implication var7 = (Implication)this;
            var3 = var3 + var7.lh_concept.getPositives();
        }

        if (this instanceof NearImplication) {
            NearImplication var8 = (NearImplication)this;
            var3 = var3 + var8.lh_concept.getPositives();
        }

        if (this instanceof NonExists) {
            NonExists var9 = (NonExists)this;
            var3 = var3 + var9.concept.getPositives();
        }

        var3 = var3 + "\nThis has negatives " + this.counterexamples;
        double var10 = this.plausibility * this.applicability;
        var3 = var3 + "\n It has arity " + this.arity + "\n It has applicability " + this.decimalPlaces(this.applicability, var2) + "\n It has comprehensibility " + this.decimalPlaces(this.comprehensibility, var2) + "\n It has surprisingness " + this.decimalPlaces(this.surprisingness, var2) + "\n It has plausibility " + this.decimalPlaces(this.plausibility, var2) + "\n It has plausibility*applicability " + this.decimalPlaces(var10, var2) + "\n It has interestingness " + this.decimalPlaces(this.interestingness, var2) + "\n It was found by the lakatos method: " + this.lakatos_method;
        return var3;
    }
}
