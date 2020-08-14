package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.awt.Label;
import java.io.Serializable;

/** A super class for the individual data generators.
 *
 * @author Simon Colton, started 7th December 2000
 * @version 1.0 */

public class DataGenerator extends Explainer implements Serializable
{
    /** The method for finding counterexamples.
     */

    public Vector counterexamplesFor(Conjecture conj, Theory theory, int num_required)
    {
        return new Vector();
    }
}
