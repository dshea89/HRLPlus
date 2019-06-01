package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class ProofScheme extends TheoryConstituent implements Serializable {
    Conjecture conj = new Conjecture();
    Vector proof_vector = new Vector();
    String proof_tree = new String();
    public String lakatos_method = "no";

    public ProofScheme() {
    }

    public ProofScheme(Conjecture var1, Vector var2) {
        this.conj = var1;
        this.proof_vector = var2;
    }

    public ProofScheme(Theory var1, String var2) {
        String var3 = var2.substring(1, var2.indexOf("=") - 1);
        this.conj = this.constructConjecture(var1, var3);
        this.proof_vector = this.proof_vector;
        String var4 = var2.substring(var2.indexOf("=") + 1, var2.length());
        String var5 = this.omitBrackets(var4);
        boolean var7 = false;
        int var6 = -1;

        while(var6 < var5.length()) {
            new String();
            int var10 = var5.indexOf(",", var6 + 1);
            String var8;
            Conjecture var9;
            if (var10 <= 0) {
                var8 = var5.substring(var6 + 1);
                var9 = this.constructConjecture(var1, var8);
                this.proof_vector.addElement(var9);
                break;
            }

            var8 = var5.substring(var6 + 1, var10);
            var9 = this.constructConjecture(var1, var8);
            var6 = var10;
            this.proof_vector.addElement(var9);
        }

    }

    public String omitBrackets(String var1) {
        String var3 = new String();

        for(int var2 = 0; var2 < var1.length(); ++var2) {
            char var4 = var1.charAt(var2);
            if (var4 == '{' | var4 == '}') {
                var3 = var3;
            } else if (var4 == '=') {
                var3 = var3.substring(0, var3.length() - 1);
                var3 = var3 + ",";
            } else {
                var3 = var3 + var4;
            }
        }

        return var3;
    }

    public Conjecture constructConjecture(Theory var1, String var2) {
        Concept var3 = new Concept();
        Concept var4 = new Concept();
        int var8;
        Concept var9;
        int var10;
        String var11;
        String var12;
        if (var2.indexOf("<") > 0) {
            var10 = var2.indexOf("<");
            var11 = var2.substring(0, var10);
            var12 = var2.substring(var10 + 3);

            for(var8 = 0; var8 < var1.concepts.size(); ++var8) {
                var9 = (Concept)var1.concepts.elementAt(var8);
                if (var9.id.equals(var11)) {
                    var3 = var9;
                }

                if (var9.id.equals(var12)) {
                    var4 = var9;
                }
            }

            return new Equivalence(var3, var4, "lemma_in_proof");
        } else if (var2.indexOf("-") > 0) {
            var10 = var2.indexOf("-");
            var11 = var2.substring(0, var10);
            var12 = var2.substring(var10 + 2);

            for(var8 = 0; var8 < var1.concepts.size(); ++var8) {
                var9 = (Concept)var1.concepts.elementAt(var8);
                if (var9.id.equals(var11)) {
                    var3 = var9;
                }

                if (var9.id.equals(var12)) {
                    var4 = var9;
                }
            }

            return new Implication(var3, var4, "lemma_in_proof");
        } else {
            System.out.println("got a nonexists");
            String var5 = var2;

            for(int var6 = 0; var6 < var1.concepts.size(); ++var6) {
                Concept var7 = (Concept)var1.concepts.elementAt(var6);
                if (var7.id.equals(var5)) {
                    var3 = var7;
                }
            }

            return new NonExists(var3, "lemma_in_proof");
        }
    }

    public String writeProofScheme() {
        String var1 = new String();
        var1 = var1 + "     **************         ";
        var1 = var1 + "\nproofscheme.conj is " + this.conj.writeConjecture();
        var1 = var1 + "\nproofscheme.proof_vector is:";

        for(int var2 = 0; var2 < this.proof_vector.size(); ++var2) {
            Conjecture var3 = (Conjecture)this.proof_vector.elementAt(var2);
            var1 = var1 + "\n(" + var2 + ") " + var3.writeConjecture();
        }

        var1 = var1 + "\n     **************         ";
        return var1;
    }

    public boolean isEmpty() {
        return this.conj.writeConjecture().equals("") && this.proof_vector.isEmpty();
    }
}
