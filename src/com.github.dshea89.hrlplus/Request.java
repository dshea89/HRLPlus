package com.github.dshea89.hrlplus;

import java.util.Vector;

public class Request extends GroupAgendaElement implements Cloneable {
    int interestingness;
    String type = "";
    int num;
    String condition = "";
    Vector theory_constituent_vector = new Vector();
    Motivation motivation = new Motivation();
    String string_object = "";
    int num_steps;

    public Request() {
        this.type = this.type;
        this.num = this.num;
        this.condition = this.condition;
        this.theory_constituent_vector = this.theory_constituent_vector;
        this.motivation = this.motivation;
    }

    public Request(String var1, int var2, String var3, Vector var4, Motivation var5) {
        this.type = var1;
        this.num = var2;
        this.condition = var3;
        this.theory_constituent_vector = var4;
        this.motivation = var5;
    }

    public Object clone() {
        Request var1 = new Request();
        var1.type = this.type;
        var1.num = this.num;
        var1.condition = this.condition;
        var1.theory_constituent_vector = (Vector)this.theory_constituent_vector.clone();
        var1.motivation = new Motivation(this.motivation.conjecture_under_discussion, this.motivation.attempted_method);
        return var1;
    }

    public Request(String var1) {
        this.string_object = var1;
    }

    public Request(String var1, int var2) {
        this.string_object = var1;
        this.num_steps = var2;
    }

    public String toString() {
        if (this.string_object.equals("work independently")) {
            return "work independently for " + this.num_steps + " steps";
        } else if (this.motivation.attempted_method.equals("monster-barring")) {
            return this.string_object;
        } else {
            Conjecture conjecture = this.motivation.conjecture_under_discussion;
            String attempted_method = this.motivation.attempted_method;
            String var;
            if (this.num == 1) {
                var = "a";
            } else {
                var = (new Integer(this.num)).toString();
            }

            if (attempted_method.equals("to get discussion started")) {
                return this.condition.equals("null") ? "supply " + var + " " + this.type + ", in order to " + attempted_method : "supply " + var + " " + this.type + " which " + this.condition + " " + this.toString(this.theory_constituent_vector) + ", in order to " + attempted_method;
            } else {
                return "supply " + var + " " + this.type + " which " + this.condition + " " + this.toString(this.theory_constituent_vector) + ", in order to " + attempted_method;
            }
        }
    }

    public String toStringForFile() {
        String var1 = new String();
        String var2 = new String();
        if (this.string_object.equals("work independently")) {
            return "work independently for " + this.num_steps + " steps";
        } else if (this.motivation.attempted_method.equals("monster-barring")) {
            if (this.string_object.equals("perform downgradeEntityToPseudoEntity")) {
                return " please downgrade the entity to a pseudo-entity.";
            } else {
                return this.string_object.equals("perform addEntityToTheory") ? " please add the entity to your theories." : this.string_object;
            }
        } else {
            Conjecture var3 = this.motivation.conjecture_under_discussion;
            String var4 = this.motivation.attempted_method;
            String var5 = this.toStringForFile(this.theory_constituent_vector);
            if (this.type.equals("Entity")) {
                var1 = "entities";
            }

            if (this.type.equals("Conjecture")) {
                var1 = "conjectures";
            }

            if (this.type.equals("Equivalence")) {
                var1 = "equivalence conjectures";
            }

            if (this.type.equals("NearEquivalence")) {
                var1 = "near-equivalence conjectures";
            }

            if (this.type.equals("NonExists")) {
                var1 = "non-existence conjectures";
            }

            if (this.type.equals("Implication")) {
                var1 = "implication conjectures";
            }

            if (this.type.equals("NearImplication")) {
                var1 = "near-implication conjectures";
            }

            if (this.type.equals("Concept")) {
                var1 = "concepts";
            }

            if (this.condition.equals("breaks")) {
                var2 = "break";
            }

            if (this.condition.equals("modifies")) {
                var2 = "modify";
            }

            if (this.condition.equals("covers")) {
                var2 = "cover";
            }

            if (var4.equals("to get discussion started") && var5.equals("")) {
                return "Has anyone got any interesting " + var1 + " to get the discussion started?";
            } else {
                return !var1.equals("") && !var2.equals("") ? "Has anyone got any " + var1 + " which " + var2 + " " + var5 + "? We want to perform " + var4 : "";
            }
        }
    }

    public String toStringForFile(Vector var1) {
        String var2 = new String();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            TheoryConstituent var4 = (TheoryConstituent)var1.elementAt(var3);
            if (var4 instanceof Conjecture) {
                Conjecture var5 = (Conjecture)var4;
                var2 = "the conjecture '" + var5.writeConjecture("ascii") + "'";
            }

            String var6;
            if (var4 instanceof Entity) {
                Entity var7 = (Entity)var4;
                var6 = var7.name;
                var2 = var2 + var6 + "; ";
            }

            if (var4 instanceof Concept) {
                Concept var8 = (Concept)var4;
                var6 = var8.writeDefinition("ascii");
                var2 = var2 + " the concept " + var6 + "; ";
            }
        }

        return var2;
    }

    public String toString(Vector var1) {
        String var2 = "[";

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            TheoryConstituent var4 = (TheoryConstituent)var1.elementAt(var3);
            String var6;
            if (var4 instanceof Conjecture) {
                Conjecture var5 = (Conjecture)var4;
                var6 = var5.id + ": " + var5.writeConjecture("ascii");
                var2 = var2 + var6 + "; ";
            }

            if (var4 instanceof Entity) {
                Entity var7 = (Entity)var4;
                var6 = var7.name;
                var2 = var2 + var6 + "; ";
            }

            if (var4 instanceof Concept) {
                Concept var8 = (Concept)var4;
                var6 = var8.writeDefinition("ascii");
                var2 = var2 + var6 + "; ";
            }

            if (var4 instanceof ProofScheme) {
                ProofScheme var9 = (ProofScheme)var4;
                var6 = "Global conjecture: " + var9.conj.writeConjecture("ascii");
                var2 = var2 + var6;
            }
        }

        return var2 + "]";
    }

    public boolean equals(Request var1) {
        boolean var2 = true;
        if (!this.type.equals(var1.type)) {
            var2 = false;
            return false;
        } else if (this.num != this.num) {
            var2 = false;
            return false;
        } else if (!this.condition.equals(this.condition)) {
            var2 = false;
            return false;
        } else if (!this.theory_constituent_vector.equals(this.theory_constituent_vector)) {
            var2 = false;
            return false;
        } else if (!this.motivation.equals(this.motivation)) {
            var2 = false;
            return false;
        } else if (!this.string_object.equals(this.string_object)) {
            var2 = false;
            return false;
        } else {
            return var2;
        }
    }

    public boolean isEmpty() {
        boolean var1 = true;
        if (!this.type.equals("")) {
            var1 = false;
            return false;
        } else if (this.num != 0) {
            var1 = false;
            return false;
        } else if (!this.condition.equals("")) {
            var1 = false;
            return false;
        } else if (!this.theory_constituent_vector.isEmpty()) {
            var1 = false;
            return false;
        } else if (!this.string_object.equals("")) {
            var1 = false;
            return false;
        } else {
            return var1;
        }
    }
}
