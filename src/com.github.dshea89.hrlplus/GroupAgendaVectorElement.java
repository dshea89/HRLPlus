package com.github.dshea89.hrlplus;

import java.util.Vector;

public class GroupAgendaVectorElement extends GroupAgendaElement {
    Vector vector = new Vector();

    public GroupAgendaVectorElement() {
    }

    public GroupAgendaVectorElement(GroupAgendaVectorElement var1) {
        this.vector = var1.vector;
    }

    public GroupAgendaVectorElement(Vector var1, Motivation var2) {
        this.vector = var1;
        this.motivation = var2;
    }

    public boolean equals(GroupAgendaVectorElement var1) {
        boolean var2 = true;
        if (!this.vector.equals(var1.vector)) {
            var2 = false;
            return false;
        } else if (!this.motivation.equals(this.motivation)) {
            var2 = false;
            return false;
        } else {
            return var2;
        }
    }

    public String toString() {
        String var1 = "";

        for(int var2 = 0; var2 < this.vector.size(); ++var2) {
            if (this.vector.elementAt(var2) instanceof Concept) {
                var1 = var1 + ((Concept)this.vector.elementAt(var2)).writeDefinition("ascii");
            }

            if (this.vector.elementAt(var2) instanceof Conjecture) {
                var1 = var1 + ((Conjecture)this.vector.elementAt(var2)).writeConjecture("ascii");
            }

            if (this.vector.elementAt(var2) instanceof Entity) {
                var1 = var1 + ((Entity)this.vector.elementAt(var2)).toString();
            } else {
                var1 = this.vector.elementAt(var2).toString();
            }

            var1 = var1 + " , ";
        }

        var1 = var1 + this.motivation.toString();
        return var1;
    }
}
