package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Specification implements Serializable {
    public boolean is_single_entity = false;
    public boolean is_entity_instantiations = false;
    public int id_number = 0;
    public Vector functions = new Vector();
    public String type = "";
    public Vector multiple_variable_columns = new Vector();
    public Vector redundant_columns = new Vector();
    public Vector multiple_types = new Vector();
    public Vector previous_specifications = new Vector();
    public Vector fixed_values = new Vector();
    public Vector relations = new Vector();
    public Vector permutation = new Vector();
    public int rh_starts = 0;

    public Specification() {
    }

    public Specification(String var1) {
        this.type = var1;
    }

    public Specification(String var1, Relation var2) {
        this.permutation = this.getVector(var1);
        this.addRelation(this.permutation, var2);
    }

    public boolean isEntityInstantiations() {
        if (this.is_entity_instantiations) {
            return true;
        } else if (this.type.equals("")) {
            return this.is_single_entity;
        } else {
            boolean var1 = false;

            for(int var2 = 0; var2 < this.previous_specifications.size() && !var1; ++var2) {
                Specification var3 = (Specification)this.previous_specifications.elementAt(var2);
                var1 = var3.is_entity_instantiations;
            }

            return var1;
        }
    }

    public void addRelation(Vector var1, Relation var2) {
        this.relations.addElement(var2);

        for(int var3 = 0; var3 < var2.function_columns.size(); ++var3) {
            Vector var4 = (Vector)var2.function_columns.elementAt(var3);
            Vector var5 = (Vector)var4.elementAt(0);
            Vector var6 = (Vector)var4.elementAt(1);
            Vector var7 = new Vector();

            for(int var8 = 0; var8 < var5.size(); ++var8) {
                String var9 = (String)var5.elementAt(var8);
                int var10 = new Integer(var9);
                String var11 = (String)var1.elementAt(var10);
                var7.addElement(var11);
            }

            Vector var13 = new Vector();

            for(int var14 = 0; var14 < var6.size(); ++var14) {
                String var16 = (String)var6.elementAt(var14);
                int var17 = new Integer(var16);
                String var12 = (String)var1.elementAt(var17);
                var13.addElement(var12);
            }

            Function var15 = new Function(var2.name, var7, var13);
            this.functions.addElement(var15);
        }

    }

    public Specification copy() {
        Specification var1 = new Specification();
        var1.id_number = this.id_number;
        var1.relations = this.relations;
        var1.permutation = (Vector)this.permutation.clone();
        var1.type = this.type;
        var1.fixed_values = (Vector)this.fixed_values.clone();
        var1.multiple_variable_columns = (Vector)this.multiple_variable_columns.clone();
        var1.multiple_types = (Vector)this.multiple_types.clone();
        var1.previous_specifications = (Vector)this.previous_specifications.clone();
        var1.redundant_columns = (Vector)this.redundant_columns.clone();
        var1.functions = (Vector)this.functions.clone();
        var1.rh_starts = this.rh_starts;
        return var1;
    }

    public boolean equals(Specification var1) {
        if (!this.type.equals(var1.type)) {
            return false;
        } else if (this.rh_starts != var1.rh_starts) {
            return false;
        } else if (!this.compareStringVectors(this.permutation, var1.permutation)) {
            return false;
        } else if (!this.compareStringVectors(this.multiple_variable_columns, var1.multiple_variable_columns)) {
            return false;
        } else if (!this.compareStringVectors(this.multiple_types, var1.multiple_types)) {
            return false;
        } else if (!this.compareStringVectors(this.redundant_columns, var1.redundant_columns)) {
            return false;
        } else if (!this.compareStringVectors(this.fixed_values, var1.fixed_values)) {
            return false;
        } else {
            boolean var2 = true;
            if (this.relations.size() != var1.relations.size()) {
                return false;
            } else {
                int var3;
                for(var3 = 0; var3 < this.relations.size() && var2; ++var3) {
                    Relation var4 = (Relation)this.relations.elementAt(var3);
                    Relation var5 = (Relation)var1.relations.elementAt(var3);
                    if (!var4.equals(var5)) {
                        var2 = false;
                    }
                }

                if (!var2) {
                    return false;
                } else {
                    var3 = 0;
                    if (this.previous_specifications.size() != var1.previous_specifications.size()) {
                        return false;
                    } else {
                        for(; var3 < this.previous_specifications.size() && var2; ++var3) {
                            if (!((Specification)this.previous_specifications.elementAt(var3)).equals((Specification)var1.previous_specifications.elementAt(var3))) {
                                var2 = false;
                            }
                        }

                        return var2;
                    }
                }
            }
        }
    }

    private boolean compareStringVectors(Vector var1, Vector var2) {
        if (var1.size() != var2.size()) {
            return false;
        } else {
            for(int var3 = 0; var3 < var1.size(); ++var3) {
                if (!((String)var1.elementAt(var3)).equals((String)var2.elementAt(var3))) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean isMultiple() {
        return !this.type.equals("");
    }

    public String writeDefinition(String var1, Vector var2) {
        String var3 = "";
        Vector var4 = this.permute(var2);
        Relation var5 = (Relation)this.relations.elementAt(0);
        Definition var6 = var5.getDefinition(var1);
        var3 = var6.write(var4);
        return var3;
    }

    private Vector permute(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < this.permutation.size(); ++var3) {
            int var4 = new Integer((String)this.permutation.elementAt(var3));
            var2.addElement(var1.elementAt(var4));
        }

        return var2;
    }

    public boolean involvesColumns(Vector var1) {
        boolean var2 = false;

        for(int var3 = 0; !var2 && var3 < this.permutation.size(); ++var3) {
            String var4 = (String)this.permutation.elementAt(var3);
            if (var1.contains(var4)) {
                var2 = true;
            }
        }

        return var2;
    }

    private Vector getVector(String var1) {
        Vector var2 = new Vector();
        String var3 = "";

        for(int var4 = 1; var4 < var1.length() - 1; ++var4) {
            String var5 = var1.substring(var4, var4 + 1);
            if (var5.equals(",")) {
                var2.addElement(var3);
                var3 = "";
            } else {
                var3 = var3 + var5;
            }
        }

        var2.addElement(var3);
        return var2;
    }

    public String writeSpec() {
        Vector var1 = new Vector();
        var1.addElement("a");
        var1.addElement("b");
        var1.addElement("c");
        var1.addElement("d");
        var1.addElement("e");
        var1.addElement("f");
        var1.addElement("g");
        if (this.relations.isEmpty()) {
            return "no relations";
        } else {
            Relation var2 = (Relation)this.relations.elementAt(0);
            if (var2.definitions.isEmpty()) {
                return "no definitions in relation 1";
            } else {
                Definition var3 = (Definition)var2.definitions.elementAt(0);
                return var3.write(var1);
            }
        }
    }

    public boolean isNegationOf(Specification var1) {
        if (!this.type.equals("negate")) {
            return false;
        } else if (this.previous_specifications.size() == 0) {
            return false;
        } else {
            Specification var2 = (Specification)this.previous_specifications.elementAt(0);
            return var1 == var2;
        }
    }

    public String fullDetails() {
        return this.fullDetails("");
    }

    private String fullDetails(String var1) {
        String var2 = "";
        var2 = var1 + this.id_number + "  (" + this.type + ")" + " " + this.toString() + "\n" + var1 + this.writeSpec() + "\n";
        var2 = var2 + var1 + "multiple_variable_columns=" + this.multiple_variable_columns.toString() + "\n";
        var2 = var2 + var1 + "redundant_columns=" + this.redundant_columns.toString() + "\n";
        var2 = var2 + var1 + "multiple_types=" + this.multiple_types.toString() + "\n";
        var2 = var2 + var1 + "fixed_values=" + this.fixed_values.toString() + "\n";
        var2 = var2 + var1 + "permutation=" + this.permutation.toString() + "\n";
        var2 = var2 + var1 + "rh_starts=" + Integer.toString(this.rh_starts) + "\n";
        var2 = var2 + var1 + "is_single_entity=" + (new Boolean(this.is_single_entity)).toString() + "\n";
        var2 = var2 + var1 + "is_entity_instantiations=" + (new Boolean(this.is_entity_instantiations)).toString() + "\n";

        int var3;
        for(var3 = 0; var3 < this.functions.size(); ++var3) {
            var2 = var2 + var1 + "function: " + ((Function)this.functions.elementAt(var3)).writeFunction() + "\n";
        }

        if (this.isMultiple()) {
            var1 = var1 + "   ";

            for(var3 = 0; var3 < this.previous_specifications.size(); ++var3) {
                Specification var4 = (Specification)this.previous_specifications.elementAt(var3);
                var2 = var2 + "\n" + var4.fullDetails(var1);
            }
        }

        return var2;
    }

    public Vector columns_used_at_base() {
        if (!this.isMultiple()) {
            return this.permutation;
        } else {
            Vector var1 = new Vector();

            for(int var2 = 0; var2 < this.previous_specifications.size(); ++var2) {
                Specification var3 = (Specification)this.previous_specifications.elementAt(var2);
                Vector var4 = var3.columns_used_at_base();

                for(int var5 = 0; var5 < var4.size(); ++var5) {
                    if (!var1.contains((String)var4.elementAt(var5))) {
                        var1.addElement((String)var4.elementAt(var5));
                    }
                }
            }

            return var1;
        }
    }

    public Vector skolemisedRepresentation() {
        Vector var1 = new Vector();
        Vector var3;
        if (this.type.equals("forall") || this.type.equals("negate") || this.type.equals("size") || this.type.equals("disjunct")) {
            String var2 = Integer.toString(this.id_number);
            var3 = new Vector();
            var3.addElement(var2);
            var3.addElement((Vector)this.permutation.clone());
            var3.trimToSize();
            var1.addElement(var3);
        }

        if (this.type.equals("exists") || this.type.equals("split")) {
            for(int var13 = 0; var13 < this.previous_specifications.size(); ++var13) {
                Specification var15 = (Specification)this.previous_specifications.elementAt(var13);
                Vector var4 = var15.skolemisedRepresentation();

                for(int var5 = 0; var5 < var4.size(); ++var5) {
                    Vector var6 = (Vector)var4.elementAt(var5);
                    String var7 = (String)var6.elementAt(0);
                    Vector var8 = (Vector)var6.elementAt(1);
                    Vector var9 = (Vector)this.permutation.clone();

                    int var10;
                    String var11;
                    int var12;
                    for(var10 = 0; var10 < this.multiple_variable_columns.size(); ++var10) {
                        var11 = "!" + Integer.toString(this.id_number) + "_" + Integer.toString(var10) + "!";
                        if (this.type.equals("split")) {
                            var11 = ":" + (String)this.fixed_values.elementAt(var10) + ":";
                        }

                        var12 = new Integer((String)this.multiple_variable_columns.elementAt(var10));
                        if (var12 >= var9.size()) {
                            var9.addElement(var11);
                        } else {
                            var9.insertElementAt(var11, var12);
                        }
                    }

                    for(var10 = 0; var10 < this.redundant_columns.size(); ++var10) {
                        int var16 = new Integer((String)this.redundant_columns.elementAt(var10));
                        if (var16 >= var9.size()) {
                            var9.addElement("@r@");
                        } else {
                            var9.insertElementAt("@r@", var16);
                        }
                    }

                    for(var10 = 0; var10 < var8.size(); ++var10) {
                        var11 = (String)var8.elementAt(var10);
                        if (!var11.substring(0, 1).equals(":") && !var11.substring(0, 1).equals("!")) {
                            var12 = new Integer(var11);
                            var8.setElementAt(var9.elementAt(var12), var10);
                        }
                    }

                    var1.addElement(var6);
                }
            }
        }

        if (this.relations.isEmpty()) {
            return var1;
        } else {
            Relation var14 = (Relation)this.relations.elementAt(0);
            if (this.type.equals("")) {
                var3 = new Vector();
                var3.addElement(var14.name);
                var3.addElement((Vector)this.permutation.clone());
                var3.trimToSize();
                var1.addElement(var3);
            }

            var1.trimToSize();
            return var1;
        }
    }
}
