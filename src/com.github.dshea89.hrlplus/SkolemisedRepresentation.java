package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class SkolemisedRepresentation implements Serializable {
    public Vector relation_columns = new Vector();
    public SortableVector ground_variables = new SortableVector();
    public Hashtable variable_relation_position_hashtable = new Hashtable();
    public Hashtable relation_position_hashtable = new Hashtable();
    public Hashtable relation_hashtable = new Hashtable();
    public Vector variables = new Vector();

    public SkolemisedRepresentation() {
    }

    public String writeSkolemisedRepresentation() {
        String var1 = "relation_columns = " + this.relation_columns.toString();
        var1 = var1 + "\nground_variables = " + this.ground_variables.toString();
        var1 = var1 + "\nvariable_relation_position_hashtable = " + this.variable_relation_position_hashtable.toString();
        var1 = var1 + "\nrelation_position_hashtable = " + this.relation_position_hashtable.toString();
        var1 = var1 + "\nrelation_hashtable = " + this.relation_hashtable.toString();
        var1 = var1 + "\nvariables = " + this.variables.toString();
        return var1;
    }
}
