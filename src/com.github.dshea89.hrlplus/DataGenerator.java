package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

/**
 * A super class for the individual data generators.
 */
public class DataGenerator extends Explainer implements Serializable {
    public DataGenerator() {
    }

    /**
     * The method for finding counterexamples.
     */
    public Vector counterexamplesFor(Conjecture conj, Theory theory, int num_required) {
        return new Vector();
    }
}
