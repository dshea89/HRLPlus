package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class for some general things for measuring concepts and conjectures
 *
 * @author Simon Colton, started 1st December 2000
 * @version 1.0
 * @see MeasureConcept
 * @see MeasureConjecture
 */

public class Measure implements Serializable
{
    /** The interestingness score below which the score should be set to
     * zero (so that the effect of interestingness can be delayed).
     */

    public double interestingness_zero_min = 0;

    /** Whether or not to actually normalise values.
     */

    public boolean normalise_values = true;

    /** A flag saying whether a new value has been added to a list of values.
     */

    public boolean measures_need_updating = false;

    /** A flag saying whether any old values have been recalculated.
     */

    public boolean old_measures_have_been_updated = false;

    /** The calculates the normalised measure and adds it to the
     * set of values given.
     */

    public double normalisedValue(double value, Vector all_values)
    {
        measures_need_updating = false;
        if (normalise_values == false)
            return value;
        String dstring = Double.toString(value);
        if (all_values.isEmpty())
        {
            all_values.addElement(dstring);
            all_values.trimToSize();
            return 1.0;
        }
        int pos = all_values.indexOf(dstring);
        if (all_values.size()==1 && pos==0)
            return 1.0;
        double dsize = (new Double(all_values.size())).doubleValue();
        if (all_values.indexOf(dstring)>-1)
        {
            double dpos = (new Double(pos)).doubleValue();
            return dpos/(dsize - 1);
        }
        measures_need_updating = true;
        int i=-1;
        boolean placed_it = false;
        while (!placed_it && i<all_values.size()-1)
        {
            i++;
            double old_value = (new Double((String)all_values.elementAt(i))).doubleValue();
            if (old_value > value)
            {
                all_values.insertElementAt(dstring, i);
                all_values.trimToSize();
                placed_it = true;
            }
        }
        if (!placed_it)
        {
            all_values.addElement(dstring);
            all_values.trimToSize();
            i++;
        }
        return (new Double(i)).doubleValue()/dsize;
    }
}
