package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class HoldBackChecker extends DataGenerator implements Serializable {
    public Hashtable counterexamples = new Hashtable();

    public HoldBackChecker() {
    }

    public void addCounterexample(String var1, String var2) {
        Vector var3;
        if (this.counterexamples.get(var1) == null) {
            var3 = new Vector();
            var3.addElement(var2);
            this.counterexamples.put(var1, var3);
        } else {
            var3 = (Vector)this.counterexamples.get(var1);
            var3.addElement(var2);
        }

    }

    public Vector counterexamplesFor(Conjecture conj, Theory theory, int num_required) {
        Vector var4 = new Vector();
        String var7;
        Vector var8;
        int var9;
        Tuples var10;
        Tuples var11;
        if (conj instanceof Equivalence) {
            String var5 = "";
            Equivalence var6 = (Equivalence) conj;
            var7 = (String)var6.lh_concept.types.elementAt(0);
            var8 = (Vector)this.counterexamples.get(var7);
            if (var8 == null || var8.isEmpty()) {
                return new Vector();
            }

            for(var9 = 0; var9 < var8.size() && num_required > var4.size(); ++var9) {
                var5 = (String)var8.elementAt(var9);
                var10 = var6.lh_concept.calculateRow(theory.concepts, var5).tuples;
                var10.sort();
                var11 = var6.rh_concept.calculateRow(theory.concepts, var5).tuples;
                var11.sort();
                String var12 = var10.toString();
                String var13 = var11.toString();
                if (!var12.equals(var13)) {
                    Entity var14 = new Entity();
                    var14.name = var5;
                    var14.conjecture = conj;
                    var4.addElement(var14);
                    var8.removeElementAt(var9);
                    --var9;
                }
            }
        }

        String var17;
        if (conj instanceof Implication) {
            Implication var15 = (Implication) conj;
            var17 = "";
            var7 = (String)var15.lh_concept.types.elementAt(0);
            var8 = (Vector)this.counterexamples.get(var7);
            if (var8 == null || var8.isEmpty()) {
                return new Vector();
            }

            for(var9 = 0; var9 < var8.size() && num_required > var4.size(); ++var9) {
                var17 = (String)var8.elementAt(var9);
                var10 = var15.lh_concept.calculateRow(theory.concepts, var17).tuples;
                var11 = var15.rh_concept.calculateRow(theory.concepts, var17).tuples;
                if (!var11.subsumes(var10)) {
                    Entity var19 = new Entity();
                    var19.name = var17;
                    var19.conjecture = conj;
                    var8.removeElementAt(var9);
                    var4.addElement(var19);
                    --var9;
                }
            }
        }

        if (conj instanceof NonExists) {
            NonExists var16 = (NonExists) conj;
            var17 = "";
            var7 = (String)var16.concept.types.elementAt(0);
            var8 = (Vector)this.counterexamples.get(var7);
            if (var8 == null || var8.isEmpty()) {
                return new Vector();
            }

            for(var9 = 0; var9 < var8.size() && num_required > var4.size(); ++var9) {
                var17 = (String)var8.elementAt(var9);
                var10 = var16.concept.calculateRow(theory.concepts, var17).tuples;
                if (!var10.isEmpty()) {
                    var8.removeElementAt(var9);
                    Entity var18 = new Entity();
                    var18.name = var17;
                    var18.conjecture = conj;
                    var4.addElement(var18);
                    --var9;
                }
            }
        }

        return var4;
    }
}
