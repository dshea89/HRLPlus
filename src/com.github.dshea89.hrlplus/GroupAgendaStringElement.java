package com.github.dshea89.hrlplus;

public class GroupAgendaStringElement extends GroupAgendaElement {
    String string = new String();

    public GroupAgendaStringElement() {
    }

    public GroupAgendaStringElement(GroupAgendaStringElement groupAgendaStringElement) {
        this.string = groupAgendaStringElement.string;
        this.motivation = groupAgendaStringElement.motivation;
    }

    public GroupAgendaStringElement(String string, Motivation motivation) {
        this.string = string;
        this.motivation = motivation;
    }

    public String toString() {
        return this.string + this.motivation.toString();
    }
}
