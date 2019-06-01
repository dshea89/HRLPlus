package com.github.dshea89.hrlplus;

import java.io.Serializable;

public class GroupAgendaElement implements Serializable {
    Motivation motivation = new Motivation();

    public GroupAgendaElement() {
    }

    public GroupAgendaElement(GroupAgendaElement var1) {
        this.motivation = var1.motivation;
    }

    public String toString() {
        if (this instanceof GroupAgendaTheoryConstituentElement) {
            return ((GroupAgendaTheoryConstituentElement)this).toString();
        } else if (this instanceof GroupAgendaVectorElement) {
            return ((GroupAgendaVectorElement)this).toString();
        } else {
            return this instanceof GroupAgendaStringElement ? ((GroupAgendaStringElement)this).toString() : "";
        }
    }

    public boolean equals(GroupAgendaElement var1) {
        if (this instanceof GroupAgendaTheoryConstituentElement && var1 instanceof GroupAgendaTheoryConstituentElement) {
            return ((GroupAgendaTheoryConstituentElement)this).equals((GroupAgendaTheoryConstituentElement)var1);
        } else if (this instanceof GroupAgendaVectorElement && var1 instanceof GroupAgendaVectorElement) {
            return ((GroupAgendaVectorElement)this).equals((GroupAgendaVectorElement)var1);
        } else if (this instanceof Request && var1 instanceof Request) {
            return ((Request)this).equals((Request)var1);
        } else {
            GroupAgendaVectorElement var2;
            GroupAgendaTheoryConstituentElement var3;
            TheoryConstituent var4;
            if (this instanceof GroupAgendaVectorElement && var1 instanceof GroupAgendaTheoryConstituentElement) {
                var2 = (GroupAgendaVectorElement)this;
                var3 = (GroupAgendaTheoryConstituentElement)var1;
                if (var2.vector.size() == 1) {
                    var4 = (TheoryConstituent)var2.vector.elementAt(0);
                    if (var4.equals(var3.theory_constituent)) {
                        return true;
                    }
                }
            }

            if (var1 instanceof GroupAgendaVectorElement && this instanceof GroupAgendaTheoryConstituentElement) {
                var2 = (GroupAgendaVectorElement)var1;
                var3 = (GroupAgendaTheoryConstituentElement)this;
                if (var2.vector.size() == 1) {
                    var4 = (TheoryConstituent)var2.vector.elementAt(0);
                    if (var4.equals(var3.theory_constituent)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
