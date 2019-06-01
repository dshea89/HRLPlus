package com.github.dshea89.hrlplus;

public class GroupAgendaTheoryConstituentElement extends GroupAgendaElement {
    TheoryConstituent theory_constituent = new TheoryConstituent();

    public GroupAgendaTheoryConstituentElement() {
    }

    public GroupAgendaTheoryConstituentElement(TheoryConstituent var1, Motivation var2) {
        this.theory_constituent = var1;
        this.motivation = var2;
    }

    public GroupAgendaTheoryConstituentElement(GroupAgendaTheoryConstituentElement var1) {
        this.theory_constituent = var1.theory_constituent;
        this.motivation = var1.motivation;
    }

    public boolean equals(GroupAgendaTheoryConstituentElement var1) {
        if (this.theory_constituent instanceof Conjecture && var1.theory_constituent instanceof Conjecture) {
            Conjecture var2 = (Conjecture)this.theory_constituent;
            Conjecture var3 = (Conjecture)var1.theory_constituent;
            if (var2.equals(var3) && this.motivation.equals(var1.motivation)) {
                return true;
            }
        }

        if (this.theory_constituent instanceof Concept && var1.theory_constituent instanceof Concept && (Concept)this.theory_constituent == (Concept)var1.theory_constituent && this.motivation.equals(var1.motivation)) {
            return true;
        } else {
            if (this.theory_constituent instanceof Entity && var1.theory_constituent instanceof Entity) {
                Entity var4 = (Entity)this.theory_constituent;
                Entity var5 = (Entity)var1.theory_constituent;
                if (var4.equals(var5) && this.motivation.equals(var1.motivation)) {
                    return true;
                }
            }

            return false;
        }
    }

    public String toString() {
        String var1 = "";
        if (!(this.theory_constituent instanceof Conjecture) && !(this.theory_constituent instanceof Entity) && !(this.theory_constituent instanceof Concept)) {
            var1 = "can't write this object as a string";
        } else {
            if (this.theory_constituent instanceof Conjecture) {
                Conjecture var2 = (Conjecture)this.theory_constituent;
                var1 = var2.id + ": " + var2.writeConjecture("ascii");
            }

            if (this.theory_constituent instanceof Entity) {
                Entity var3 = (Entity)this.theory_constituent;
                var1 = var3.toString();
            }

            if (this.theory_constituent instanceof Concept) {
                Concept var4 = (Concept)this.theory_constituent;
                var1 = var4.id + ": " + var4.writeDefinition("ascii");
            }
        }

        var1 = var1 + this.motivation.toString();
        return var1;
    }
}
