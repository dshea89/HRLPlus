package com.github.dshea89.hrlplus;

import java.io.Serializable;

public class Prover extends Explainer implements Serializable {
    public Prover() {
    }

    public boolean prove(Conjecture conjecture, Theory theory) {
        return false;
    }

    public boolean disprove(Conjecture conjecture, Theory theory) {
        return false;
    }
}
