package com.github.dshea89.hrlplus;

import java.io.Serializable;

/**
 * A class representing a row found in the datatable of a concept.
 */
public class Row implements Serializable {
    /**
     * The entity to which this row refers.
     */
    public String entity = "";

    /**
     * The tuples calculated for the entity for this row.
     */
    public Tuples tuples = new Tuples();

    /**
     * If a calculation could not occur to find the tuples for the input entity, this void flag is set to true.
     */
    public boolean is_void = false;

    /**
     * Constructs an empty row
     */
    public Row() {
    }

    /**
     * Constructs a row with the given entity and given tuples. Note that the tuples are sorted first.
     */
    public Row(String input_entity, Tuples input_tuples) {
        this.entity = input_entity;
        this.tuples = input_tuples;
        this.tuples.sort();
    }

    /**
     * Constructs a void row.
     */
    public Row(String input_entity, String void_string) {
        this.entity = input_entity;
        if (void_string.equals("void")) {
            this.is_void = true;
        }
    }
}
