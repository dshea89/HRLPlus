package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** A class for skolemised representations of concepts.
 *
 * @author Simon Colton, started 3rd January 2000
 * @version 1.0 */

public class SkolemisedRepresentation implements Serializable
{
    /** The list of pairs (relation_name, column_vector) in this skolemised
     * representation.
     */

    public Vector relation_columns = new Vector();

    /** The sorted vector of ground variables in this skolemised representation.
     */

    public SortableVector ground_variables = new SortableVector();

    /** The hashtable of pairs (variable, relation_name(column_position))
     * for this skolemiseed representation.
     */

    public Hashtable variable_relation_position_hashtable = new Hashtable();

    /** The hashtable of pairs (relation_name(column_position), variable)
     * for this skolemised representation.
     */

    public Hashtable relation_position_hashtable = new Hashtable();

    /** The hashtable of pairs (relation_name, columns) for this
     * skolemised representation.
     */

    public Hashtable relation_hashtable = new Hashtable();

    /** The vector of variable names (normal, skolemised and ground) in this
     * skolemised representation.
     */

    public Vector variables = new Vector();

    /** The simple constructor.
     */

    public SkolemisedRepresentation()
    {
    }

    /** This writes the skolemised representation in a readable format.
     */

    public String writeSkolemisedRepresentation()
    {
        String output = "relation_columns = " + relation_columns.toString();
        output = output + "\nground_variables = " + ground_variables.toString();
        output = output + "\nvariable_relation_position_hashtable = " + variable_relation_position_hashtable.toString();
        output = output + "\nrelation_position_hashtable = " + relation_position_hashtable.toString();
        output = output + "\nrelation_hashtable = " + relation_hashtable.toString();
        output = output + "\nvariables = " + variables.toString();
        return output;
    }
}
