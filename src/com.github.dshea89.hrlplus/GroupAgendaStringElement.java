package com.github.dshea89.hrlplus;

public class GroupAgendaStringElement extends GroupAgendaElement {
    String string = new String();

    public GroupAgendaStringElement() {
    }

    public GroupAgendaStringElement(GroupAgendaStringElement var1) {
        this.string = var1.string;
        this.motivation = var1.motivation;
    }

    public GroupAgendaStringElement(String var1, Motivation var2) {
        this.string = var1;
        this.motivation = var2;
    }

    public String toString() {
        return this.string + this.motivation.toString();
    }
}
