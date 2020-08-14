package com.github.dshea89.hrlplus;

import java.io.Serializable;

/** A super class for the individual theorem provers.
 *
 * @author Simon Colton, started 7th December 2000
 * @version 1.0 */

public class Prover extends Explainer implements Serializable
{
    public boolean prove(Conjecture conj, Theory theory) {
        return false;
    }

    public boolean disprove(Conjecture conj, Theory theory) {
        return false;
    }
}
