package com.github.dshea89.hrlplus;

import java.io.Serializable;

public class ProofSchemeElement implements Serializable {
    String agenda_line = new String();
    Conjecture associated_conj = new Conjecture();
    Exception e = new Exception();

    public ProofSchemeElement(String var1, Conjecture var2) {
        this.agenda_line = var1;
        this.associated_conj = var2;
    }
}
