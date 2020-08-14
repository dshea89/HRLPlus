package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.Serializable;

/** A class representing a row found in the datatable of a concept.
 * @author Simon Colton, started 11th December 1999
 * @version 1.0
 */

public class Row implements Serializable
{
    /** The entity to which this row refers.
     */

    public String entity = "";

    /** The tuples calculated for the entity for this row.
     * @see Tuples
     */

    public Tuples tuples = new Tuples();

    /** If a calculation could not occur to find the tuples for the input entity,
     * this void flag is set to true.
     */

    public boolean is_void = false;

    /** Constructs an empty row
     */

    public Row(){}

    /** Constructs a row with the given entity and given tuples.
     * Note that the tuples are sorted first.
     */

    public Row(String input_entity, Tuples input_tuples)
    {
        entity=input_entity;
        tuples=input_tuples;
        tuples.sort();
    }

    /** Constructs a void row.
     */

    public Row(String input_entity, String void_string)
    {
        entity = input_entity;
        if (void_string.equals("void")) is_void = true;
    }
}
