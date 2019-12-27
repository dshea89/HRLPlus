package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

/**
 * A super class for all the production rules. It has methods which need to be available to all production rules.
 */
public class ProductionRule implements Serializable {
    public int number_of_new_functions = 0;

    /**
     * A string which displays a reason why the PR returned an empty set of parameterisations.
     */
    public String parameter_failure_reason = "";

    /**
     * The arity limit for this production rule (i.e. the maximum arity of the concepts it is allowed to produce).
     */
    public int arity_limit = 3;

    /**
     * The tier (in terms of the agenda) of this production rule
     */
    public int tier = 0;

    /**
     * The time when the stopwatch was started.
     */
    public long stopwatch_starting_time = 0L;

    public ProductionRule() {
    }

    /**
     * Returns true if the production rule requires two concepts as input.
     */
    public boolean isBinary() {
        return false;
    }

    /**
     * Whether or not this produces cumulative concepts.
     */
    public boolean isCumulative() {
        return false;
    }

    /**
     * Returns the name of the production rule.
     */
    public String getName() {
        return "";
    }

    /**
     * Transforms the datatables given to it into a new datatable.
     */
    public Datatable transformTable(Vector old_datatables, Vector old_concepts, Vector parameters, Vector all_concepts) {
        return null;
    }

    /**
     * Produces the object types for the columns of the new datatable produced.
     */
    public Vector transformTypes(Vector old_concepts, Vector parameters) {
        return null;
    }

    public Vector allParameters(Vector concept_list, Theory theory) {
        Vector var3 = new Vector();
        Vector var4 = new Vector();
        var4.addElement("not yet");
        var3.addElement(var4);
        return var3;
    }

    /**
     * This checks whether the given permutation is the identity permutation.
     */
    public boolean isIdentityPermutation(Vector parameters) {
        int var2;
        for(var2 = 0; var2 < parameters.size(); ++var2) {
            int var3 = new Integer((String)parameters.elementAt(var2));
            if (var3 != var2) {
                break;
            }
        }

        return var2 == parameters.size();
    }

    /**
     * This produces new specifications for a new concept.
     */
    public Vector newSpecifications(Vector concept_list, Vector parameters, Theory theory, Vector new_functions) {
        return new Vector();
    }

    /**
     * This removes any permutations which are the identity permutation from a list of permutations.
     */
    public void removeIdentityPermutations(Vector parameters) {
        int var2 = 0;

        while(var2 < parameters.size()) {
            if (this.isIdentityPermutation((Vector)parameters.elementAt(var2))) {
                parameters.removeElementAt(var2);
            } else {
                ++var2;
            }
        }

    }

    /**
     * This removes the given columns from a datatable.
     */
    public Datatable removeColumns(Datatable old_datatable, Vector parameters) {
        Datatable var3 = new Datatable();

        for(int var4 = 0; var4 < old_datatable.size(); ++var4) {
            Row var5 = (Row)old_datatable.elementAt(var4);
            String var6 = var5.entity;
            Tuples var7 = var5.tuples;
            Tuples var8 = new Tuples();

            for(int var9 = 0; var9 < var7.size(); ++var9) {
                Vector var10 = (Vector)((Vector)var7.elementAt(var9)).clone();
                var10.insertElementAt(var6, 0);
                Vector var11 = this.removeColumns(var10, parameters);
                var11.removeElementAt(0);
                var8.addElement(var11);
            }

            var8.removeDuplicates();
            Row var12 = new Row(var6, var8);
            if (var8.size() > 0) {
                var3.addElement(var12);
            }
        }

        return var3;
    }

    /**
     * This interchanges the elements of a vector with two elements.
     */
    public Vector swap(Vector concepts) {
        Vector var2 = new Vector();
        var2.addElement(concepts.elementAt(1));
        var2.addElement(concepts.elementAt(0));
        return var2;
    }

    /**
     * This returns a new vector with the repeated elements removed.
     */
    public Vector removeRepeatedElements(Vector input) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < input.size(); ++var3) {
            if (!var2.contains(input.elementAt(var3))) {
                var2.addElement(input.elementAt(var3));
            }
        }

        return var2;
    }

    /**
     * This returns all possible tuples of columns which do not include column, given the arity of the original concept.
     */
    public Vector allColumnTuples(int arity) {
        Vector var2 = new Vector();
        var2.addElement(new Vector());
        int var3 = 0;

        for(int var4 = 1; var4 < arity; ++var4) {
            int var5 = var2.size();

            for(int var6 = var3; var6 < var5; ++var6) {
                var3 = var5;
                Vector var7 = (Vector)var2.elementAt(var6);
                int var8 = 1;
                if (!var7.isEmpty()) {
                    var8 = new Integer((String)var7.lastElement());
                }

                for(int var9 = var8 + 1; var9 <= arity; ++var9) {
                    new Vector();
                    if (!var7.contains(Integer.toString(var9 - 1))) {
                        Vector var10 = (Vector)var7.clone();
                        var10.addElement(Integer.toString(var9 - 1));
                        var2.addElement(var10);
                    }
                }
            }
        }

        var2.removeElementAt(0);
        return var2;
    }

    /**
     * This removes the given columns from the given tuple.
     */
    public Vector removeColumns(Vector tuple, Vector parameters) {
        for(int var3 = 0; var3 < parameters.size(); ++var3) {
            int var4 = new Integer((String)parameters.elementAt(var3));
            tuple.removeElementAt(var4 - var3);
        }

        return tuple;
    }

    /**
     * This keeps only the given columns from the given tuple. Columns are assumed to be in the correct order.
     */
    public Vector keepColumns(Vector tuple, Vector parameters) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < parameters.size(); ++var4) {
            int var5 = new Integer((String)parameters.elementAt(var4));
            var3.addElement(tuple.elementAt(var5));
        }

        return var3;
    }

    /**
     * This assigns a score to a concept depending on whether the production rule can see any likelihood of a pattern.
     */
    public int patternScore(Vector concept_list, Vector all_concepts, Vector entity_list, Vector non_entity_list, String object_types) {
        return !this.object_type_after_step(concept_list).equals(object_types) ? 0 : this.patternScore(concept_list, all_concepts, entity_list, non_entity_list);
    }

    /**
     * Returns the type of objects of interest which will be involved in the resulting concept.
     */
    public String object_type_after_step(Vector concept_list) {
        Concept var2 = (Concept)concept_list.elementAt(0);
        return var2.object_type;
    }

    public int patternScore(Vector concept_list, Vector all_concepts, Vector entity_list, Vector non_entity_list) {
        return 0;
    }

    /**
     * This starts a stopwatch (to be used to stop datatable constructions which are taking too long).
     */
    public void startStopWatch() {
        this.stopwatch_starting_time = new Long(System.currentTimeMillis());
    }

    /**
     * This tells how long (in milliseconds) that the stopwatch has been running.
     */
    public long stopWatchTime() {
        long var1 = new Long(System.currentTimeMillis());
        return var1 - this.stopwatch_starting_time;
    }

    /**
     * Finds a concept in the given concept with matching specifications
     */
    public Concept getConceptFromSpecs(Vector specs, Vector concepts, String types) {
        Concept var4 = null;
        Vector var5 = this.sortSpecs(specs);
        boolean var6 = false;

        for(int var7 = 0; var7 < concepts.size() && !var6; ++var7) {
            var4 = (Concept)concepts.elementAt(var7);
            if (types.equals(var4.types.toString()) && var4.specifications.size() == specs.size()) {
                int var8;
                for(var8 = 0; var8 < var5.size(); ++var8) {
                    Specification var9 = (Specification)var5.elementAt(var8);

                    int var10;
                    for(var10 = 0; var10 < var4.specifications.size(); ++var10) {
                        Specification var11 = (Specification)var4.specifications.elementAt(var10);
                        if (var11.equals(var9)) {
                            break;
                        }
                    }

                    if (var10 == var4.specifications.size()) {
                        break;
                    }
                }

                if (var8 == specs.size()) {
                    var6 = true;
                }
            }
        }

        return var4;
    }

    public Vector sortSpecs(Vector specs) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < specs.size(); ++var3) {
            Specification var4 = (Specification)specs.elementAt(var3);
            if (!var2.contains(var4)) {
                int var5 = 0;

                boolean var6;
                for(var6 = false; var5 < var2.size() && !var6; ++var5) {
                    Specification var7 = (Specification)var2.elementAt(var5);
                    if (var4.id_number < var7.id_number) {
                        var2.insertElementAt(var4, var5);
                        var6 = true;
                    }
                }

                if (!var6) {
                    var2.addElement(var4);
                }
            }
        }

        return var2;
    }
}
