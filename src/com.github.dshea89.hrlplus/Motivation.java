package com.github.dshea89.hrlplus;

import java.io.Serializable;

public class Motivation implements Serializable {
    Conjecture conjecture_under_discussion = new Conjecture();
    Entity entity_under_discussion = new Entity();
    ProofScheme proofscheme_under_discussion = new ProofScheme();
    String attempted_method = "";

    public Motivation() {
    }

    public Motivation(Conjecture var1, String var2) {
        this.conjecture_under_discussion = var1;
        this.attempted_method = var2;
    }

    public Motivation(Entity var1, String var2) {
        this.entity_under_discussion = var1;
        this.attempted_method = var2;
    }

    public Motivation(ProofScheme var1, String var2) {
        this.proofscheme_under_discussion = var1;
        this.attempted_method = var2;
    }

    public String toString() {
        String var1 = new String();
        if (this.attempted_method.equals("to get discussion started")) {
            return " (to get discussion started)";
        } else if (!this.conjecture_under_discussion.writeConjecture("ascii").equals("")) {
            return " (in order to perform " + this.attempted_method + " on " + this.conjecture_under_discussion.id + ": " + this.conjecture_under_discussion.writeConjecture("ascii") + ")";
        } else {
            return !this.entity_under_discussion.name.equals("") ? " (in order to perform " + this.attempted_method + " on " + this.entity_under_discussion.name + ")" : var1;
        }
    }

    public boolean equals(Motivation var1) {
        return this.conjecture_under_discussion.equals(var1.conjecture_under_discussion) && this.entity_under_discussion.equals(var1.entity_under_discussion) && this.attempted_method.equals(var1.attempted_method);
    }
}
